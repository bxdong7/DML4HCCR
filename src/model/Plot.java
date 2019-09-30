package model;
import java.io.*;

public class Plot {
	public static int NO_VALUE = -1;
	public static int W;	//the maximum width among all plots
	public static int H;	//the maximum height
	public int[][] bitmap;
	public String label;
	public int width;
	public int height;
	public int diagonal;	//min(w, h)
	
	//features
	public double count;
	public double[] horizontal_bit_vec;	//# of 1s in each line
	public double[] vertical_bit_vec;
	public double[] diagonal_bit_vec;
	public double[] horizontal_first_vec;	//index of the first 1 in each line
	public double[] vertical_first_vec;
	public double[] diagonal_first_vec;
	public double[] horizontal_last_vec;	//index of the last 1 in each line
	public double[] vertical_last_vec;
	public double[] diagonal_last_vec;
	public double horizontal_longest;	//length of the longest line
	public double horizontal_longest_pos;
	public double vertical_longest;
	public double vertical_longest_pos;
	public double diagonal_longest;
	public double diagonal_longest_pos;

	
	public Plot () {
		
	}
	
	public Plot (String path) {
		// path = "/Users/bd11/Documents/Research/Dataset/Handwriting/HWDB1.1tst_gnt/BMP/01760163/2"
		int index1, index2;
		BufferedReader br;
		FileReader fr;
		LineNumberReader lnr;
		String line;
		
		// intercept the label
		index2 = path.lastIndexOf('/');	//index of last /
		index1 = path.lastIndexOf('/', index2-1);
		label = path.substring(index1+1, index2);
		
		// store the bitmap
		try {
			fr = new FileReader(path);
			lnr = new LineNumberReader(fr);
			
			while (lnr.skip(Long.MAX_VALUE) > 0)
			{
			      // Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
			}
			height = lnr.getLineNumber() + 1;	//number of lines in the file
			lnr.close();
			fr.close();
			
			br = new BufferedReader(new FileReader(path));
			line = br.readLine();
			width = line.length();
			diagonal = Math.min(width, height);
			bitmap = new int[height][width];
			
			index1 = 0;
			while (line != null) {
				for (index2 = 0; index2 < width; index2++) {
					bitmap[index1][index2] = line.charAt(index2) - '0';
				}
				index1 ++;
				line = br.readLine();
			}
			
			br.close();
			
			this.get_features();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void get_features () {
		count = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (bitmap[i][j] == 1)
					count++;
			}
		}
		count /= (height * width);
		
		horizontal_bit_vec = new double[height];
		for (int i = 0; i < height; i++) {
			double num = 0;
			for (int j = 0; j < width; j++) {
				if (bitmap[i][j] == 1)
					num++;
			}
			horizontal_bit_vec[i] = (num + 1) / width;
		}
		
		vertical_bit_vec = new double[width];
		for (int j = 0; j < width; j++) {
			double num = 0;
			for (int i = 0; i < height; i++) {
				if (bitmap[i][j] == 1) 
					num++;
			}
			vertical_bit_vec[j] = (num + 1) / height;
		}
		
		diagonal_bit_vec = new double[diagonal];
		for (int j = 0; j < diagonal; j++) {
			double num = 0;
			for (int k = 0; k < diagonal; k++) {
				num += bitmap[height-1-k][j];
			}
			diagonal_bit_vec[j] = (num + 1) / diagonal;
		}
		
		horizontal_first_vec = new double[height];
		for (int i = 0; i < height; i++) {
			horizontal_first_vec[i] = NO_VALUE;
			for (int j = 0; j < width; j++) {
				if (bitmap[i][j] == 1) {
					horizontal_first_vec[i] = j + 1;
					horizontal_first_vec[i] /= width;
					break;
				}
			}
		}
		
		vertical_first_vec = new double[width];
		for (int j = 0; j < width; j++) {
			vertical_first_vec[j] = NO_VALUE;
			for (int i = 0; i < height; i++) {
				if (bitmap[i][j] == 1) {
					vertical_first_vec[j] = i + 1;
					vertical_first_vec[j] /= height;
					break;
				}
			}
		}
		
		diagonal_first_vec = new double[diagonal];
		for (int j = 0; j < diagonal; j++) {
			diagonal_first_vec[j] = NO_VALUE;
			for (int k = 0; k < diagonal; k++) {
				if (bitmap[height-1-k][j] == 1) {
					diagonal_first_vec[j] = k + 1;
					diagonal_first_vec[j] /= diagonal;
					break;
				}
			}
		}
		
		horizontal_last_vec = new double[height];
		for (int i = 0; i < height; i++) {
			horizontal_last_vec[i] = NO_VALUE;
			for (int j = width-1; j >= 0; j--) {
				if (bitmap[i][j] == 1) {
					horizontal_last_vec[i] = j + 1;
					horizontal_last_vec[i] /= width;
					break;
				}
			}
		}
		
		vertical_last_vec = new double[width];
		for (int j = 0; j < width; j++) {
			vertical_last_vec[j] = NO_VALUE;
			for (int i = height - 1; i >= 0; i--) {
				if (bitmap[i][j] == 1) {
					vertical_last_vec[j] = i + 1;
					vertical_last_vec[j] /= height;
					break;
				}
			}
		}
		
		diagonal_last_vec = new double[diagonal];
		for (int j = 0; j < diagonal; j++) {
			diagonal_last_vec[j] = NO_VALUE;
			for (int k = diagonal - 1; k >= 0; k--) {
				if (bitmap[height-k-1][k] == 1) {
					diagonal_last_vec[j] = k + 1;
					diagonal_last_vec[j] /= diagonal;
					break;
				}
			}
		}
		
		horizontal_longest = 0;
		horizontal_longest_pos = -1;
		for (int i = 0; i < height; i++) {
			if (horizontal_bit_vec[i] > horizontal_longest) {
				horizontal_longest = horizontal_bit_vec[i];
				horizontal_longest_pos = (i + 1.0) / width;
			}
		}
		
		vertical_longest = 0;
		vertical_longest_pos = -1;
		for (int j = 0; j < width; j++) {
			if (vertical_bit_vec[j] > vertical_longest) {
				vertical_longest = vertical_bit_vec[j];
				vertical_longest_pos = (j + 1.0) / height;
			}
		}
		
		diagonal_longest = 0;
		diagonal_longest_pos = -1;
		for (int k = 0; k < diagonal; k++) {
			if (diagonal_bit_vec[k] > diagonal_longest) {
				diagonal_longest = diagonal_bit_vec[k];
				diagonal_longest_pos = (k + 1.0) / diagonal;
			}
		}
	}	
}
