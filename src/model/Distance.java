package model;

public class Distance {
	public static double calculate_manhattan_distance (double[] v1, double[] v2) {
		double distance = 0;
		
		int max_length = Math.max(v1.length, v2.length);
		for (int i = 0; i < max_length; i++) {
			if (i >= v1.length) 
				distance += v2[i];
			else if (i >= v2.length) 
				distance += v1[i];
			else
				distance += Math.abs(v1[i] - v2[i]);
		}
		
		return distance;
	}
	
	public static double calculate_euclidean_distance (double[] v1, double[] v2) {
		double distance = 0;
		
		int max_length = Math.max(v1.length, v2.length);
		for (int i = 0; i < max_length; i++) {
			if (i >= v1.length) 
				distance += v2[i] * v2[i];
			else if (i >= v2.length) 
				distance += v1[i] * v1[i];
			else
				distance += (v1[i] - v2[i]) * (v1[i] - v2[i]);
		}
		
		return distance;
	}
	
	public static double calculate_count_disntance (Plot a, Plot b) {
		return Math.abs(a.count - b.count);
	}
	
	public static double calculate_horizontal_bit_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.horizontal_bit_vec, b.horizontal_bit_vec);
	}
	
	public static double calculate_horizontal_bit_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.horizontal_bit_vec, b.horizontal_bit_vec);
	}

	public static double calculate_vertical_bit_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.vertical_bit_vec, b.vertical_bit_vec);
	}
	
	public static double calculate_vertical_bit_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.vertical_bit_vec, b.vertical_bit_vec);
	}

	public static double calculate_diagonal_bit_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.diagonal_bit_vec, b.diagonal_bit_vec);
	}
	
	public static double calculate_diagonal_bit_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.diagonal_bit_vec, b.diagonal_bit_vec);
	}
	
	public static double calculate_horizontal_first_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.horizontal_first_vec, b.horizontal_first_vec);
	}
	
	public static double calculate_horizontal_first_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.horizontal_first_vec, b.horizontal_first_vec);
	}

	public static double calculate_vertical_first_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.vertical_first_vec, b.vertical_first_vec);
	}
	
	public static double calculate_vertical_first_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.vertical_first_vec, b.vertical_first_vec);
	}

	public static double calculate_diagonal_first_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.diagonal_first_vec, b.diagonal_first_vec);
	}
	
	public static double calculate_diagonal_first_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.diagonal_first_vec, b.diagonal_first_vec);
	}
	
	public static double calculate_horizontal_last_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.horizontal_last_vec, b.horizontal_last_vec);
	}
	
	public static double calculate_horizontal_last_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.horizontal_last_vec, b.horizontal_last_vec);
	}

	public static double calculate_vertical_last_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.vertical_last_vec, b.vertical_last_vec);
	}
	
	public static double calculate_vertical_last_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.vertical_last_vec, b.vertical_last_vec);
	}

	public static double calculate_diagonal_last_vec_manhattan_distance (Plot a, Plot b) {
		return Distance.calculate_manhattan_distance(a.diagonal_last_vec, b.diagonal_last_vec);
	}
	
	public static double calculate_diagonal_last_vec_euclidean_distance (Plot a, Plot b) {
		return Distance.calculate_euclidean_distance(a.diagonal_last_vec, b.diagonal_last_vec);
	}
	
	public static double calculate_horizontal_longest_distance (Plot a, Plot b) {
		return Math.abs(a.horizontal_longest - b.horizontal_longest);
	}
	
	public static double calculate_horizontal_longest_pos_distance (Plot a, Plot b) {
		return Math.abs(a.horizontal_longest_pos - b.horizontal_longest_pos);
	}
	
	public static double calculate_vertical_longest_distance (Plot a, Plot b) {
		return Math.abs(a.vertical_longest - b.vertical_longest);
	}
	
	public static double calculate_vertical_longest_pos_distance (Plot a, Plot b) {
		return Math.abs(a.vertical_longest_pos - b.vertical_longest_pos);
	}
	
	public static double calculate_diagonal_longest_distance (Plot a, Plot b) {
		return Math.abs(a.diagonal_longest - b.diagonal_longest);
	}
	
	public static double calculate_diagonal_longest_pos_distance (Plot a, Plot b) {
		return Math.abs(a.diagonal_longest_pos - b.diagonal_longest_pos);
	}
	
	//21, 22, 24, 20, 25, 1, 23, 7, 18, 5
	//14, 8, 17, 11, 19
	//14, 8, 18, 19, 25
	
	//option: 1 - 25
	public static double calculate_distance (Plot a, Plot b, int option) {
		double distance;
		switch (option) {
		case 1:
			distance = Distance.calculate_horizontal_bit_vec_manhattan_distance(a, b);
			break;
		case 2:
			distance = Distance.calculate_horizontal_first_vec_manhattan_distance(a, b);
			break;
		case 3:
			distance = Distance.calculate_vertical_first_vec_manhattan_distance(a, b);
			break;
		case 4:
			distance = Distance.calculate_vertical_first_vec_euclidean_distance(a, b);
			break;
		case 5:
			distance = Distance.calculate_diagonal_first_vec_manhattan_distance(a, b);
			break;
		case 6:
			distance = Distance.calculate_diagonal_first_vec_euclidean_distance(a, b);
			break;
		case 7:
			distance = Distance.calculate_horizontal_last_vec_manhattan_distance(a, b);
			break;
		case 8:
			distance = Distance.calculate_vertical_last_vec_manhattan_distance(a, b);
			break;
		case 9:
			distance = Distance.calculate_vertical_last_vec_euclidean_distance(a, b);
			break;
		case 10:
			distance = Distance.calculate_diagonal_last_vec_euclidean_distance(a, b);
			break;
		default:
			distance = 0;
		}
		return distance;
		
		/*
		switch (option) {
		case 1:
			distance = Distance.calculate_horizontal_first_vec_manhattan_distance(a, b);
			break;
		case 2:
			distance = Distance.calculate_horizontal_last_vec_manhattan_distance(a, b);
			break;
		case 3:
			distance = Distance.calculate_diagonal_last_vec_manhattan_distance(a, b);
			break;
		case 4:
			distance = Distance.calculate_diagonal_last_vec_euclidean_distance(a, b);
			break;
		case 5:
			distance = Distance.calculate_horizontal_longest_distance(a, b);
			break;
		case 6:
			distance = Distance.calculate_horizontal_longest_pos_distance(a, b);
			break;
		case 7:
			distance = Distance.calculate_vertical_longest_distance(a, b);
			break;
		case 8:
			distance = Distance.calculate_vertical_longest_pos_distance(a, b);
			break;
		case 9:
			distance = Distance.calculate_diagonal_longest_distance(a, b);
			break;
		case 10:
			distance = Distance.calculate_diagonal_longest_pos_distance(a, b);
			break;
		default:
			distance = 0;
		}
		return distance;
		*/
		/*
		switch (option) {
		case 1:
			distance = Distance.calculate_count_disntance(a, b);
			break;
		case 2:
			distance = Distance.calculate_horizontal_bit_vec_manhattan_distance(a, b);
			break;
		case 3:
			distance = Distance.calculate_horizontal_bit_vec_euclidean_distance(a, b);
			break;
		case 4:
			distance = Distance.calculate_vertical_bit_vec_manhattan_distance(a, b);
			break;
		case 5:
			distance = Distance.calculate_vertical_bit_vec_euclidean_distance(a, b);
			break;
		case 6:
			distance = Distance.calculate_diagonal_bit_vec_manhattan_distance(a, b);
			break;
		case 7:
			distance = Distance.calculate_diagonal_bit_vec_euclidean_distance(a, b);
			break;
		case 8:
			distance = Distance.calculate_horizontal_first_vec_manhattan_distance(a, b);
			break;
		case 9:
			distance = Distance.calculate_horizontal_first_vec_euclidean_distance(a, b);
			break;
		case 10:
			distance = Distance.calculate_vertical_first_vec_manhattan_distance(a, b);
			break;
		case 11:
			distance = Distance.calculate_vertical_first_vec_euclidean_distance(a, b);
			break;
		case 12:
			distance = Distance.calculate_diagonal_first_vec_manhattan_distance(a, b);
			break;
		case 13:
			distance = Distance.calculate_diagonal_first_vec_euclidean_distance(a, b);
			break;
		case 14:
			distance = Distance.calculate_horizontal_last_vec_manhattan_distance(a, b);
			break;
		case 15:
			distance = Distance.calculate_horizontal_last_vec_euclidean_distance(a, b);
			break;
		case 16:
			distance = Distance.calculate_vertical_last_vec_manhattan_distance(a, b);
			break;
		case 17:
			distance = Distance.calculate_vertical_last_vec_euclidean_distance(a, b);
			break;
		case 18:
			distance = Distance.calculate_diagonal_last_vec_manhattan_distance(a, b);
			break;
		case 19:
			distance = Distance.calculate_diagonal_last_vec_euclidean_distance(a, b);
			break;
		case 20:
			distance = Distance.calculate_horizontal_longest_distance(a, b);
			break;
		case 21:
			distance = Distance.calculate_horizontal_longest_pos_distance(a, b);
			break;
		case 22:
			distance = Distance.calculate_vertical_longest_distance(a, b);
			break;
		case 23:
			distance = Distance.calculate_vertical_longest_pos_distance(a, b);
			break;
		case 24:
			distance = Distance.calculate_diagonal_longest_distance(a, b);
			break;
		case 25:
			distance = Distance.calculate_diagonal_longest_pos_distance(a, b);
			break;
		default:
			distance = 0;
		}
		return distance;
		*/
	}
}
