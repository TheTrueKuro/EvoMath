public class ComplexNumber {

	private double real, imaginary;

	ComplexNumber() {

		this.real = this.imaginary = 0;
	}

	ComplexNumber(double real, double imaginary) {
		
		this.real = real;
		this.imaginary = imaginary;
	}

	public static ComplexNumber add(ComplexNumber c1, ComplexNumber c2) {

		ComplexNumber c3 = new ComplexNumber(c1.getReal() + c2.getReal(), c1.getImaginary() + c2.getImaginary());
		return c3;
	}

	public static ComplexNumber add(ComplexNumber c1, double d1) {

		ComplexNumber c2 = new ComplexNumber(c1.getReal() + d1, c1.getImaginary());
		return c2;
	}

	public static ComplexNumber add(double d1, ComplexNumber c1) {
		ComplexNumber c2 = new ComplexNumber(d1 + c1.getReal(), c1.getImaginary());
		return c2;
	}

	double getReal() {
		return real;
	}

	double getImaginary() {
		return imaginary;
	}

	void show() {

		char sign;

		if (imaginary >= 0) sign = '+';
		else sign = 0;

		System.out.print(real);
	        System.out.print(sign);
		System.out.println(imaginary + "i");
	}
	
}
