package model;
import java.util.*;

public class Util {
	public static List<ArrayList<Integer>> enumerations;
	
	public static double[] random_double (long count, double min, double max) {
		return new Random(System.currentTimeMillis()).doubles(count, min, max).toArray();
	}
	
	public static double[] average_vec (int length, double[][] vecs) {
		double[] avg = new double[length];
		
		for (int j = 0; j < length; j++) {
			avg[j] = 0;
			int count = 0;	//# of vecs that have value at j
			for (int i = 0; i < vecs.length; i++) {
				if (vecs[i].length > j) {
					avg[j] += vecs[i][j];
					count++;
				}
			}
			avg[j] /= count;
		}
		
		return avg;
	}
	
	public static List<ArrayList<Integer>> enumerate(int[] A, int k) {
		Util.enumerations = new ArrayList<ArrayList<Integer>> ();
		boolean[] B = new boolean[A.length];
		Util.enumerate(A, k, 0, 0, B);
		return Util.enumerations;
	}
	
	public static void enumerate(int[] A, int k, int start, int currLen, boolean[] used) {

		if (currLen == k) {
			ArrayList<Integer> l = new ArrayList<Integer> ();
			for (int i = 0; i < A.length; i++) {
				if (used[i] == true) {
					l.add(new Integer(i));
				}
			}
			Util.enumerations.add(l);
			return;
		}
		if (start == A.length) {
			return;
		}
		// For every index we have two options,
		// 1.. Either we select it, means put true in used[] and make currLen+1
		used[start] = true;
		enumerate(A, k, start + 1, currLen + 1, used);
		// 2.. OR we dont select it, means put false in used[] and dont increase
		// currLen
		used[start] = false;
		enumerate(A, k, start + 1, currLen, used);
	}

}
