package model;
import java.util.*;
import java.io.*;

public class LearnMet {
	public ArrayList<Plot> training;	//all the plots
	public int k = 10;	//# of clusters
	public int num_per_cluster = 30;	//# of handwriting under each label
	public int ppe = 100;	//# of samples per epoch
	public int maxEpoch = 3000;	//max # of epochs
	public int max_clustering_round = 300;
	public double t = 0.1;	//error threshold
	
	public LearnMet () {
		
	}
	
	public LearnMet (double _t) {
		t = _t;
	}
	
	//path: the directory that stores all the BMP files
	public LearnMet (String path, double _t) {
		File[] files = new File(path).listFiles();
		training = new ArrayList<Plot> ();
		this.vistFiles(files);
		t = _t;
	}
	
	public void vistFiles (File[] files) {
		for (File file : files) {
	        if (file.isDirectory()) {
	   	        vistFiles(file.listFiles()); // Calls same method again.
	        } else if (!file.isHidden()) {
	        		training.add(new Plot(file.getAbsolutePath()));
	        }
	    }
	}
	
	public Plot[] takeSamples () {
		int must_index;
		Random rnd;
		Plot[] samples;
		List<Integer> indice;	//the indice of not must samples
		
		rnd = new Random(System.currentTimeMillis());
		samples = new Plot[ppe];
		//take a random number, and put everything in it
		must_index = rnd.nextInt(num_per_cluster);
		for (int i = 0; i < k; i++) {
			samples[i] = this.training.get(must_index + i * num_per_cluster);
		}
		
		//randomly take samples from the remaining set
		indice = new ArrayList<Integer> ();
		for (int i = 0; i < training.size(); i++) {
			if (i % num_per_cluster != must_index)
				indice.add(new Integer(i));
		}
		Collections.shuffle(indice);
		for (int i = k; i < ppe; i++) 
			samples[i] = this.training.get(indice.get(i).intValue());
		
		return samples;
	}
	
	//calculate the distance between a and b based on the weights on indices
	public double calculate_pairwise_distance (Plot a, Plot b, int[] indices, double[] weights) {
		double dist;
		
		dist = 0;
		for (int i = 0; i < indices.length; i++) {
			dist += Distance.calculate_distance(a, b, indices[i]) * weights[i];
		}
		
		return dist;
	}
	
	public double[][][] calculate_pairwise_distances (int[] samples, int[] indices) {
		int num_of_metrics;
		Plot a, b;
		
		num_of_metrics = indices.length;
		double[][][] dist = new double[ppe][ppe][num_of_metrics];
		for (int i = 0; i < ppe; i++) {
			a = this.training.get(samples[i]);
			for (int j = 0; j < ppe; j++) {
				b = this.training.get(samples[j]);
				for (int k = 0; k < num_of_metrics; k++) 
					dist[i][j][k] = Distance.calculate_distance(a, b, indices[k]);
			}
		}
		
		return dist;
	}
	
	//generate the centroid from a list of plots
	public Plot generate_centroid (List<Plot> l) {
		Plot centroid;
		int max_width, max_height, max_diagonal;
		
		//find the max width, height and diagonal of l
		max_width = max_height = max_diagonal = 0;
		for (Plot p : l) {
			if (p.width > max_width) 
				max_width = p.width;
			if (p.height > max_height)
				max_height = p.height;
			if (p.diagonal > max_diagonal)
				max_diagonal = p.diagonal;
		}
		centroid = new Plot();
		centroid.width = max_width;
		centroid.height = max_height;
		centroid.diagonal = max_diagonal;
		centroid.label = "";
		
		//set the count, and vecs
		centroid.count = 0;
		centroid.horizontal_longest = centroid.horizontal_longest_pos = centroid.vertical_longest = centroid.vertical_longest_pos
				= centroid.diagonal_longest = centroid.diagonal_longest_pos = 0;
		for (Plot p : l) {
			centroid.count += p.count;
			centroid.horizontal_longest += p.horizontal_longest;
			centroid.horizontal_longest_pos += p.horizontal_longest_pos;
			centroid.vertical_longest += p.vertical_longest;
			centroid.vertical_longest_pos += p.vertical_longest_pos;
			centroid.diagonal_longest += p.diagonal_longest;
			centroid.diagonal_longest_pos += p.diagonal_longest_pos;
		}
		centroid.count /= l.size();
		centroid.horizontal_longest /= l.size();
		centroid.horizontal_longest_pos /= l.size();
		centroid.vertical_longest /= l.size();
		centroid.vertical_longest_pos /= l.size();
		centroid.diagonal_longest /= l.size();
		centroid.diagonal_longest_pos /= l.size();
		
		double[][] horizontal_bit_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++) 
			horizontal_bit_vecs[i] = l.get(i).horizontal_bit_vec;
		centroid.horizontal_bit_vec = Util.average_vec(max_height, horizontal_bit_vecs);
		
		double[][] horizontal_first_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++)
			horizontal_first_vecs[i] = l.get(i).horizontal_first_vec;
		centroid.horizontal_first_vec = Util.average_vec(max_height, horizontal_first_vecs);
		
		double[][] horizontal_last_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++)
			horizontal_last_vecs[i] = l.get(i).horizontal_last_vec;
		centroid.horizontal_last_vec = Util.average_vec(max_height, horizontal_last_vecs);
		
		double[][] vertical_bit_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++) 
			vertical_bit_vecs[i] = l.get(i).vertical_bit_vec;
		centroid.vertical_bit_vec = Util.average_vec(max_width, vertical_bit_vecs);
		
		double[][] vertical_first_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++)
			vertical_first_vecs[i] = l.get(i).vertical_first_vec;
		centroid.vertical_first_vec = Util.average_vec(max_width, vertical_first_vecs);
		
		double[][] vertical_last_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++)
			vertical_last_vecs[i] = l.get(i).vertical_last_vec;
		centroid.vertical_last_vec = Util.average_vec(max_width, vertical_last_vecs);
		
		double[][] diagonal_bit_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++) 
			diagonal_bit_vecs[i] = l.get(i).diagonal_bit_vec;
		centroid.diagonal_bit_vec = Util.average_vec(max_diagonal, diagonal_bit_vecs);
		
		double[][] diagonal_first_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++)
			diagonal_first_vecs[i] = l.get(i).diagonal_first_vec;
		centroid.diagonal_first_vec = Util.average_vec(max_diagonal, diagonal_first_vecs);
		
		double[][] diagonal_last_vecs = new double[l.size()][];
		for (int i = 0; i < l.size(); i++)
			diagonal_last_vecs[i] = l.get(i).diagonal_last_vec;
		centroid.diagonal_last_vec = Util.average_vec(max_diagonal, diagonal_last_vecs);
		
		return centroid;
	}
	
	//return the estimated labels of each sample
	public int[] clustering (Plot[] samples, int[] indices, double[] weights) {
		Plot[] centroids;	
		int[] classes;	//the index of clusters of the samples 
		boolean flag;	//signal if the class of any sample changes
		int round;
		
		centroids = new Plot[k];		
		//pick the initial centroid first
		for (int i = 0; i < k; i++)
			centroids[i] = samples[samples.length-1-i];
		
		classes = new int[samples.length];
		for (int i = 0; i < samples.length; i++)
			classes[i] = i;
		
		round = 0;
		//do 
		do {
			//assign each sample to the closest centroid
			flag = false;
			for (int i = 0; i < samples.length; i++) {
				Plot sample = samples[i];
				double closest_distance = Double.MAX_VALUE;
				int closest_centroid = -1;
				for (int j = 0; j < k; j++) {
					Plot centroid = centroids[j];
					double distance = this.calculate_pairwise_distance(sample, centroid, indices, weights);
					if (distance < closest_distance) {
						closest_distance = distance;
						closest_centroid = j;
					}
				}
				if (classes[i] != closest_centroid) {
					flag = true;
					classes[i] = closest_centroid;
				}
			}
			
			//update the centroid
			for (int j = 0; j < k; j++) {
				//find the samples that are assigned to it
				List<Plot> l = new ArrayList<Plot> ();
				for (int i = 0; i < samples.length; i++) {
					if (classes[i] == j) {
						l.add(samples[i]);
					}
				}
				
				//calculate the new centroid
				centroids[j] = this.generate_centroid(l);
			}
			
			round++;
		}
		//until no change
		while (flag && (round < this.max_clustering_round));
		
		return classes;
	}
	
	//calculate the average distance for the pairs from the samples
	public double calculate_D (Plot[] samples, List<Pair> pairs, int[] indices, double[] weights) {
		double sum_d;
		int pair_num;
		
		pair_num = pairs.size();
		
		if (pair_num == 0)
			return 0;
		
		sum_d = 0;
		for (Pair p : pairs) {
			sum_d += this.calculate_pairwise_distance(samples[p.left], samples[p.right], indices, weights);
		}
		
		return sum_d / pair_num;
	}
	
	//indices includes the index of metrics to be used
	//return the weights for the indices
	public IWA learn (int[] indices) {
		int metrics_num;
		double[] weights, best_weights;
		double min_FR;
		int times;	//count times of while loop
		Plot[] samples;
		double FR, FP, FN, TP, TN, DFP, DFN;
		List<Pair> FP_pairs, FN_pairs;
		long t1, t2;
		
		metrics_num = indices.length;
		
		//assign random weights
		if (metrics_num == 1) {
			weights = new double[1];
			weights[0] = 1;
		}
		else
			weights = Util.random_double(metrics_num, 1, 10);
		times = 0;
		min_FR = Double.MAX_VALUE;
		best_weights = weights;
		
		t1 = System.currentTimeMillis();
		//do
		do {
			//randomly pick samples
			samples = this.takeSamples();
			
			//clustering
			int[] classes = this.clustering(samples, indices, weights);
			
			//calculate FR
			FP = FN = TP = TN = 0;
			FP_pairs = new ArrayList<Pair> ();
			FN_pairs = new ArrayList<Pair> ();
			for (int i = 0; i < samples.length; i++) {
				for (int j = i+1; j < samples.length; j++) {
					if ((samples[i].label.equals(samples[j].label)) && (classes[i] == classes[j])) 
						TP++;
					else if ((samples[i].label.equals(samples[j].label)) && (classes[i] != classes[j])) {
						FN++;
						FN_pairs.add(new Pair(i, j));
					}
					else if ((!samples[i].label.equals(samples[j].label)) && (classes[i] == classes[j])) {
						FP++;
						FP_pairs.add(new Pair(i, j));
					}
					else
						TN++;
				}
			}
			FR = (FP + FN) / (TP + TN + FP + FN);
			if (FR <= min_FR) {
				min_FR = FR;
				best_weights = weights;
			}
			times++;
			if ((FR <= t) || (times == this.maxEpoch))
				break;
			
			//weight adjustment
			//calculate DFN, DFP
			DFN = this.calculate_D(samples, FN_pairs, indices, weights);
			DFP = this.calculate_D(samples, FP_pairs, indices, weights);
			for (int i = 0; i < metrics_num; i++) {
				int[] indices_i = new int[1];
				indices_i[0] = indices[i];
				double[] weights_i = new double[1];
				weights_i[0] = weights[i];
				double DFN_i = this.calculate_D(samples, FN_pairs, indices_i, weights_i);
				double DFP_i = this.calculate_D(samples, FP_pairs, indices_i, weights_i);
				
				weights[i] = Math.max(0, weights[i] - DFN_i/DFN + DFP_i/DFP);
			}
			
		}
		//while 
		while ((times <= maxEpoch) && (indices.length > 1));
		
		t2 = System.currentTimeMillis();
		
		if (FR <= t)
			return (new IWA(indices, weights, FR, times, t2-t1));
		else
			return (new IWA(indices, best_weights, FR, times, t2-t1));
	}
	
	public IWA test (IWA iwa) {
		int[] indices;
		double[] weights;
		double FR, FP, FN, TP, TN, DFP, DFN;
		List<Pair> FP_pairs, FN_pairs;
		Plot[] testing;
		
		testing = new Plot[this.training.size()];
		testing = this.training.toArray(testing);
		indices = iwa.indices;
		weights = iwa.weights;
		int[] classes = this.clustering(testing, indices, weights);
		//calculate FR
		FP = FN = TP = TN = 0;
		FP_pairs = new ArrayList<Pair> ();
		FN_pairs = new ArrayList<Pair> ();
		for (int i = 0; i < testing.length; i++) {
			for (int j = i+1; j < testing.length; j++) {
				if ((testing[i].label.equals(testing[j].label)) && (classes[i] == classes[j])) 
					TP++;
				else if ((testing[i].label.equals(testing[j].label)) && (classes[i] != classes[j])) {
					FN++;
					FN_pairs.add(new Pair(i, j));
				}
				else if ((!testing[i].label.equals(testing[j].label)) && (classes[i] == classes[j])) {
					FP++;
					FP_pairs.add(new Pair(i, j));
				}
				else
					TN++;
			}
		}
		FR = (FP + FN) / (TP + TN + FP + FN);
		iwa.FR = FR;
		return iwa;
	}
}
