package Evomath;

import java.lang.Math;

/**
 * @author TheTrueKuro
 * @version 1.0
 *
 * Class created for processing and creating complex numbers
 */

public class ComplexNumber {

	private double real, imaginary;

	public ComplexNumber() {

		this.real = this.imaginary = 0;
	}

	public ComplexNumber(double real, double imaginary) {
		
		this.real = real;
		this.imaginary = imaginary;
	}

	public ComplexNumber(final ComplexNumber c) {

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

	public ComplexNumber conjugate() {
		return new ComplexNumber(this.real, -this.imaginary);
	}

	public double getAbs() {

		double abs = Math.sqrt(real*real + imaginary*imaginary);
		return abs;
	}	       

	public double getAngle() {

		if (real == 0) return (imaginary >= 0) ? Math.PI/2 : -Math.PI/2;

		final double tan = imaginary/real;
		double angle = Math.atan(tan);

		if (real < 0) angle += Math.PI;

		return angle;
	}

	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imaginary;
	}

	public String toString() {

		String sign;

		if (imaginary >= 0) sign = "+";
		else sign = "";

		String text = real + sign + imaginary + "i";
		return text;
	}	
}
