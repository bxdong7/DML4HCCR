package parser;
import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IOHelper {
	
	public static long getUnsignedInt(byte[] data)
	{
	    ByteBuffer bb = ByteBuffer.wrap(data);
	    bb.order(ByteOrder.LITTLE_ENDIAN);
	    return bb.getInt() & 0xffffffffl;
	}
	
	public static void read_gnt (String path) {
		String line;
		char ch;
		BufferedReader br;
		int sample_size, tag_code, width, height;
		int[][] bitmap;
		
		try {
			br = new BufferedReader(new FileReader(path));
			line = br.readLine();
			while (line != null) {
				System.out.println(new BigInteger(line.getBytes()).toString(2));
				
				sample_size = (int) IOHelper.getUnsignedInt(line.substring(0, 2).getBytes());
				tag_code = (int) IOHelper.getUnsignedInt(line.substring(2, 6).getBytes());
				width = (int) IOHelper.getUnsignedInt(line.substring(6, 8).getBytes());
				height = (int) IOHelper.getUnsignedInt(line.substring(8, 10).getBytes());
				bitmap = new int[height][width];
				System.out.println(sample_size + ", " + tag_code + ", " + width + "," + height);
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						bitmap[i][j] = new Integer(line.substring(10+width*i+j, 10+width*i+j+1)).intValue();
						System.out.print(bitmap[i][j] + ", ");
					}
					System.out.println();
				}
				
				line = null;
			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
