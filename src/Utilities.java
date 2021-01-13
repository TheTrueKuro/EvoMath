/**
 * @author TheTrueKuro
 * @version 1.0
 *
 * Java class for common operations such as
 * the sum or product of an array, multiplication of tuples
 * and others.
 */

class Utilities {

	public static double sum(double[] d) {

		double s = 0;

		for (int i = 0; i < d.length; i++)
			s += d[i];

		return s;
	}

	public static long sum(long[] l) {

		long s = 0;

		for (int i = 0; i < l.length; i++)
			s += l[i];

		return s;
	}

	public static ComplexNumber sum(final ComplexNumber[] c) {

		ComplexNumber sum = new ComplexNumber();

		for (int i = 0; i < c.length; i++)
			sum = ComplexNumber.add(sum, c[i]);

		return sum;
	}

	//Multiplication of tuples
	public static double[] multiply(final double[] d1, final double[] d2) {
		
		if (d1.length != d2.length) return null;

		double[] d = new double[d1.length];

		for (int i = 0; i < d.length; i++)
			d[i] = d1[i] * d2[i];

		return d;
	}

	public static ComplexNumber[] multiply(ComplexNumber[] c1, ComplexNumber[] c2) {

		if (c1.length != c2.length) return null;

		ComplexNumber[] c = new ComplexNumber[c1.length];

		for (int i = 0; i < c.length; i++)
			c[i] = ComplexNumber.multiply(c1[i], c2[i]);

		return c;
	}

}
