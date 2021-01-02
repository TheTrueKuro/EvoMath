class ComplexNumber {

	private double real, imaginary;

	ComplexNumber() {

		this.real = this.imaginary = 0;
	}

	ComplexNumber(double real, double imaginary) {
		
		this.real = real;
		this.imaginary = imaginary;
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
