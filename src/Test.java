class Test {

	public static void main(String args[]) {

		var c1 = new ComplexNumber(1, 2);
		var c2 = new ComplexNumber(3, -9);
		var c3 = new ComplexNumber(0, 69);

		ComplexNumber.add(c1, c2, c3).show();

		Matrix m = Matrix.getZeroMatrix(3);
		m.show();
		m = Matrix.getZeroMatrix(2, 5);
		m.show();
		m = Matrix.getIdentityMatrix(4);
		m.show();
	}
}
