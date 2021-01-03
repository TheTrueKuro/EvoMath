class Test {

	public static void main(String args[]) {

		double[] m = {1, 2, 3};

		Matrix matrix = new Matrix(m);

		matrix.show();
		matrix.transpose().show();
	}
}
