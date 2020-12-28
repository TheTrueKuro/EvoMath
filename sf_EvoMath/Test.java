class Test {

	public static void main(String args[]) {

		ComplexNumber c1 = new ComplexNumber(2, -1);
		ComplexNumber c2 = new ComplexNumber(-6, -9);
		ComplexNumber c3 = new ComplexNumber(4, 20);
		c1.show();
		c2.show();
		ComplexNumber.add(c1, c2).show();
		ComplexNumber.add(c1, 3).show();
		ComplexNumber.add(3, c1).show();
	}
}
