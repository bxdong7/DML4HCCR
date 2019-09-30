package main;
import model.*;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Approach a;
		IWA ret;
		double L = 0.42;
		
		a = new Approach(0.1);
		
		//ret = a.heuristic();
		//System.out.println(ret.toString());
		
		//ret = a.exhaustive();
		//System.out.println(ret.toString());
		
		//ret = a.hybrid_greedy(L);
		//System.out.println(ret.toString());
		
		ret = a.hybrid_exhaustive(L);	
		System.out.println(ret.toString());
	}

}
