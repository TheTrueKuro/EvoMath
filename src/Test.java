class Test {

	public static void main(String args[]) {

		double[][] d1 = {
			{1, 0, -1},
			{3, 7, 9}
		};

		double[][] d2 = {
			{1, 4},
			{2, 5},
			{3, 6}
		};

		Matrix m1 = new Matrix(d1);
		Matrix m2 = new Matrix(d2);
		Matrix.multiply(m1, m2).show();

	}	
}
