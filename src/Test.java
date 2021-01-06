class Test {

	public static void main(String args[]) {

		var c1 = new ComplexNumber(1, 0);
		c1.show();
		System.out.println(c1.getAbs() + "(cos(" + c1.getAngle() + ") + i*sin(" + c1.getAngle() + "))");
	}	
}
