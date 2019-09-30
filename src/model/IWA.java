package model;
import java.util.*;

public class IWA implements Comparable<IWA> {
	public int[] indices;
	public double[] weights;
	public double FR;
	public int epoches;
	public long time;
	
	public IWA () {
		
	}
	
	public IWA (int[] _i, double[] _w, double _f, int _e, long _t) {
		indices = _i;
		weights = _w;
		FR = _f;
		epoches = _e;
		time = _t;
	}
	
	public int compareTo (IWA iwa) {
		if (this.FR < iwa.FR) 
			return -1;
		else if (this.FR > iwa.FR)
			return 1;
		else 
			return 0;
	}
	
	public String toString () {
		String str;
		str = "Accuracy: " + new Double(1-FR).toString() + "\n Epoches: " + new Integer(epoches).toString() + 
				"\n Time: " + new Long(time).toString() + "ms\n Indices: " + Arrays.toString(indices) + 
				"\n Weights: " + Arrays.toString(weights);
		
		return str;
	}
}
