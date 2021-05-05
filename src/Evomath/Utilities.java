/**
 * @author TheTrueKuro
 * @version 1.1
 *
 * Java class for common operations such as
 * the sum or product of an array, multiplication of tuples
 * and others.
 */

package Evomath;

public class Utilities {

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

	public static ComplexNumber sum(ComplexNumber[] c) {

		ComplexNumber s = new ComplexNumber();

		for (int i = 0; i < c.length; i++)
			s = ComplexNumber.add(s, c[i]);

		return s;
	}

	public static double product(double[] d) {

		double p = 1;

		for (int i = 0; i < d.length; i++)
			p *= d[i];

		return p;
	}

	public static long product(long[] l) {

		long p = 1;

		for (int i = 0; i < l.length; i++)
			p *= l[i];

		return p;
	}

	public static ComplexNumber product(ComplexNumber[] c) {

		if (c.length < 1) return null;

		ComplexNumber p = new ComplexNumber(c[0]);

		for (int i = 1; i < c.length; i++)
			p = ComplexNumber.multiply(p, c[i]);

		return p;
	}

	public static double max(double[] d) {

		double max = d[0];

		for (int i = 1; i < d.length; i++) {

			max = max < d[i] ? d[i] : max;
		}

		return max;
	}

	public static long max(long[] l) {

		long max = l[0];

		for (int i = 1; i < l.length; i++) {

			max = max < l[i] ? l[i] : max;
		}

		return max;
	}

	public static int indexOfMax(double[] d) {

		double max = d[0];
		int index = 0;

		for (int i = 1; i < d.length; i++) {

			if (max < d[i]) {
				max = d[i];
				index = i;
			} 
		}

		return index;
	}

	public static int indexOfMax(long[] l) {

		long max = l[0];
		int index = 0;

		for (int i = 1; i < l.length; i++) {

			if (max < l[i]) {
				max = l[i];
				index = i;
			}
		}

		return index;
	}

	public static long fact(int f) {

		if (f < 0) return -1;

		if (f == 0) return 1;

		return f * fact(f - 1);
	}

	public static long perm(int poss, int total) {

		//Check if the order is reversed and poss actually has
		//the value total should have
		if (poss > total) {
			total += poss;
			poss = total - poss;
			total = total - poss;
		}

		long p = 1;

		for (int i = 0; i < poss; i++) {
			p *= total;
			total--;
		}

		return p;
	}

	public static long combinations(int poss, int total) {

		//Check if the order is reversed and poss actually has
		//the value total should have
		if (poss > total) {
			total += poss;
			poss = total - poss;
			total = total - poss;
		}

		long p = perm(poss, total);
		p /= fact(poss);

		return p;
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
