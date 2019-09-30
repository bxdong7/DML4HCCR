package model;
import java.util.*;

public class Approach {
	public static int OPTION_MIN = 1;
	public static int OPTION_MAX = 10;
	public String path = "/Users/bd11/Documents/Research/Dataset/Handwriting/HWDB1.1tst_gnt/Training/";
	public LearnMet lm;
	public double t;
	
	public Approach () {
		lm = new LearnMet (path, 0.1);
	}
	
	public Approach (double _t) {
		t = _t;
		lm = new LearnMet (path, t);
	}
	
	public IWA heuristic () {
		List<IWA> individual_indices;
		IWA ret;
		
		ret = null;
		//calculate the FR for each individual indice
		individual_indices = new ArrayList<IWA> ();
		for (int index = Approach.OPTION_MIN; index <= Approach.OPTION_MAX; index++) {
			int[] indices = new int[1];
			indices[0] = index;
			ret = lm.learn(indices);
			ret = lm.test(ret);
			if (ret.FR <= t )
				return ret;
			else
				individual_indices.add(ret);
		}
		
		//sort
		Collections.sort(individual_indices);
		
		for (IWA iwa : individual_indices) {
			System.out.println(iwa.toString() + "\n");
		}
		
		//incrementally learn
		for (int j = 2; j <= OPTION_MAX; j++) {
			int[] indices = new int[j];
			for (int i = 0; i < j; i++)
				indices[i] = individual_indices.get(i).indices[0];
			
			ret = lm.learn(indices);
			ret = lm.test(ret);
			System.out.println(ret.toString() + "\n");
			if (ret.FR <= t)
				return ret;
		}
		
		return ret;
	}
	
	public IWA exhaustive () {
		List<IWA> iwas;
		IWA best_iwa;
		
		best_iwa = null;
		iwas = new ArrayList<IWA> ();
		int[] indices = new int[OPTION_MAX];
		
		for (int i = 0; i < OPTION_MAX; i++)
			indices[i] = i+1;
		
		for (int size = 2; size <= 5; size++) {
			System.out.println("_________________________________");
			System.out.println("size = " + size);
			iwas.clear();
			List<ArrayList<Integer>> enumerations = Util.enumerate(indices, size);
			for (ArrayList<Integer> enumeration : enumerations) {
				int[] indice = new int[size];
				for (int i = 0; i < enumeration.size(); i++)
					indice[i] = enumeration.get(i).intValue();
				
				IWA iwa = lm.learn(indice);
				iwa = lm.test(iwa);
				System.out.println(iwa.toString() + "\n");
				iwas.add(iwa);
			}
			Collections.sort(iwas);
			if ((best_iwa == null) || (best_iwa.FR > iwas.get(0).FR))
				best_iwa = iwas.get(0);
			System.out.println("Best IWA: " + iwas.get(0).toString() + "--------------------------------\n");	
		}
		
		return best_iwa;
	}
	
	//L is the limit
	public IWA hybrid_exhaustive (double L) {
		List<IWA> individual_indices;
		IWA ret;
		List<IWA> iwas;
		IWA best_iwa;
		
		best_iwa = null;
		iwas = new ArrayList<IWA> ();
		ret = null;
		//calculate the FR for each individual indice
		individual_indices = new ArrayList<IWA> ();
		for (int index = Approach.OPTION_MIN; index <= Approach.OPTION_MAX; index++) {
			int[] indices = new int[1];
			indices[0] = index;
			ret = lm.learn(indices);
			ret = lm.test(ret);
			if (ret.FR <= t)
				return ret;
			else if (ret.FR <= L)
				individual_indices.add(ret);
		}
		
		//sort
		Collections.sort(individual_indices);
		
		for (IWA iwa : individual_indices) {
			System.out.println(iwa.toString() + "\n");
		}
		
		int[] indices = new int[individual_indices.size()];
		for (int i = 0; i < individual_indices.size(); i++) 
			indices[i] = individual_indices.get(i).indices[0];
		
		for (int size = 2; size <= Math.min(5, indices.length); size++) {
			System.out.println("_________________________________");
			System.out.println("size = " + size);
			iwas.clear();
			List<ArrayList<Integer>> enumerations = Util.enumerate(indices, size);
			for (ArrayList<Integer> enumeration : enumerations) {
				int[] indice = new int[size];
				for (int i = 0; i < enumeration.size(); i++)
					indice[i] = enumeration.get(i).intValue();
				
				IWA iwa = lm.learn(indice);
				iwa = lm.test(iwa);
				System.out.println(iwa.toString() + "\n");
				iwas.add(iwa);
			}
			Collections.sort(iwas);
			if ((best_iwa == null) || (best_iwa.FR > iwas.get(0).FR))
				best_iwa = iwas.get(0);
			System.out.println("Best IWA: " + iwas.get(0).toString() + "--------------------------------\n");	
		}
		
		return best_iwa;
	}
	
	//L is the limit
		public IWA hybrid_greedy (double L) {
			List<IWA> individual_indices;
			IWA ret;
			
			ret = null;
			//calculate the FR for each individual indice
			individual_indices = new ArrayList<IWA> ();
			for (int index = Approach.OPTION_MIN; index <= Approach.OPTION_MAX; index++) {
				int[] indices = new int[1];
				indices[0] = index;
				ret = lm.learn(indices);
				ret = lm.test(ret);
				if (ret.FR <= t)
					return ret;
				else if (ret.FR <= L)
					individual_indices.add(ret);
			}
			
			//sort
			Collections.sort(individual_indices);
			
			for (IWA iwa : individual_indices) {
				System.out.println(iwa.toString() + "\n");
			}
			
			//incrementally learn
			for (int j = 2; j <= individual_indices.size(); j++) {
				int[] indices = new int[j];
				for (int i = 0; i < j; i++)
					indices[i] = individual_indices.get(i).indices[0];
				
				ret = lm.learn(indices);
				ret = lm.test(ret);
				System.out.println(ret.toString() + "\n");
				if (ret.FR <= t)
					return ret;
			}
			
			return ret;
		}
}
