class Test {

	public static void main(String args[]) {
	
		double m1[][] = {
						{1, 2, 3},
						{4, 5, 6},
						{7, 8, 9},
						{10, 11, 12},
					   };

		double m2[][] = {
						 {0, 1, 0},
						 {2, 3, 5},
						 {8, 4, 2},
						 {6, 7, 9}
						};

		Matrix M1 = new Matrix(m1);
		Matrix M2 = new Matrix(m2); 

		System.out.println(M1.getRank());
		System.out.println(M2.getRank());
		System.out.println(Matrix.add(M1, M2).getRank());
	}
}
