import java.lang.Math;

/**
 * @author TheTrueKuro
 * @version 1.0
 *
 * Class created for processing and creating complex numbers
 */

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

		this.real = c.real;
		this.imaginary = c.imaginary;
	}

	public static ComplexNumber add(final ComplexNumber ... c) {

		double real = 0;
		double imaginary = 0;

		for (int i = 0; i < c.length; i++) {
			real += c[i].real;
			imaginary += c[i].imaginary;
		}

		return new ComplexNumber(real, imaginary);
	}

	public static ComplexNumber add(final ComplexNumber c, double ... d) {

		double real = c.real;
		double imaginary = c.imaginary;

		for (int i = 0; i < d.length; i++)
			real += d[i];

		return new ComplexNumber(real, imaginary);
	}

	public static ComplexNumber multiply(final ComplexNumber ... c) {

		if (c.length < 2) return null;

		ComplexNumber c_fin = new ComplexNumber(c[0]);

		for (int i = 1; i < c.length; i++) {
			
			double real, imaginary;

			real = c_fin.real * c[i].real - c_fin.imaginary * c[i].imaginary;
			imaginary = c_fin.real * c[i].imaginary + c_fin.imaginary * c[i].real;

			c_fin = new ComplexNumber(real, imaginary);
		}

		return c_fin;
	}

	public static ComplexNumber multiply(final ComplexNumber c, double d) {

		ComplexNumber c_fin = new ComplexNumber(c.real*d, c.imaginary*d);

		return c_fin;
	}

	public static ComplexNumber multiply(double d, final ComplexNumber c) {

		ComplexNumber c_fin = new ComplexNumber(c.real*d, c.imaginary*d);

		return c_fin;
	}

	public static ComplexNumber pow(final ComplexNumber c, int exponent) {

		ComplexNumber new_c = new ComplexNumber(c);

		for (int i = 2; i <= exponent; i++) {

			new_c = multiply(new_c, c);
		}

		return new_c;
	}

	ComplexNumber conjugate() {
		return new ComplexNumber(this.real, -this.imaginary);
	}

	double getAbs() {

		double abs = Math.sqrt(real*real + imaginary*imaginary);
		return abs;
	}	       

	double getAngle() {

		if (real == 0) return (imaginary >= 0) ? Math.PI/2 : -Math.PI/2;

		final double tan = imaginary/real;
		double angle = Math.atan(tan);

		if (real < 0) angle += Math.PI;

		return angle;
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
