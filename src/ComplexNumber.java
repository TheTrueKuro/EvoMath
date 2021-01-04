import java.lang.Math;

class ComplexNumber {

	private double real, imaginary;

	ComplexNumber() {

		this.real = this.imaginary = 0;
	}

	ComplexNumber(double real, double imaginary) {
		
		this.real = real;
		this.imaginary = imaginary;
	}

	ComplexNumber(final ComplexNumber c) {

		this.real = c.getReal();
		this.imaginary = c.getImaginary();
	}

	public static ComplexNumber add(final ComplexNumber ... c) {

		double real = 0;
		double imaginary = 0;

		for (int i = 0; i < c.length; i++) {
			real += c[i].getReal();
			imaginary += c[i].getImaginary();
		}

		return new ComplexNumber(real, imaginary);
	}

	public static ComplexNumber add(final ComplexNumber c, double ... d) {

		double real = c.getReal();
		double imaginary = c.getImaginary();

		for (int i = 0; i < d.length; i++)
			real += d[i];

		return new ComplexNumber(real, imaginary);
	}

	public static ComplexNumber multiply(final ComplexNumber ... c) {

		if (c.length < 2) return null;

		ComplexNumber c_fin = new ComplexNumber(c[0]);

		for (int i = 1; i < c.length; i++) {
			
			double real, imaginary;

			real = c_fin.getReal() * c[i].getReal() - c_fin.getImaginary() * c[i].getImaginary();
			imaginary = c_fin.getReal() * c[i].getImaginary() + c_fin.getImaginary() * c[i].getReal();

			c_fin = new ComplexNumber(real, imaginary);
		}

		return c_fin;
	}

	ComplexNumber conjugate() {
		return new ComplexNumber(this.real, -this.imaginary);
	}

	double getAbs() {

		double abs = Math.sqrt(real*real + imaginary*imaginary);
		return abs;
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

		System.out.print(real); // Writing print(real + sign) actually adds the code value of $sign to the $real variable
	        System.out.print(sign);
		System.out.println(imaginary + "i");
	}	
}
