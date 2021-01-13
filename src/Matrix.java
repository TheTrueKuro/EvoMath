import java.math.BigDecimal;
import java.math.RoundingMode;

class Matrix {

	private double matrix[][];
	private int numRows, numColumns;

	static Matrix getZeroMatrix(int ... n) {

		if (n.length <= 0) return null;

		final int size = (n.length == 1) ? n[0] : n[1]; // Uses the first variable provided as size if second value doesn't exists
		if (n[0] <= 0 || size <= 0) return null; // This check isn't necessary if the size is also $n[0] but it's efficient enough

		double m[][] = new double[n[0]][size];

		return new Matrix(m);
	}

	static Matrix getIdentityMatrix(int n) {

		if (n <= 0) return null;

		double m[][] = new double[n][n];

		for (int i = 0; i < n; i++)
			m[i][i] = 1;

		return new Matrix(m);
	}

	static Matrix add(Matrix ... m) {

		if (m.length == 0) return null;

		final int rows = m[0].getSize()[0];
		final int columns = m[0].getSize()[1];

		double[][] new_matrix = new double[rows][columns];

		for (int i = 0; i < m.length; i++) {
			if (rows != m[i].getSize()[0] || columns != m[i].getSize()[1]) return null;

			new_matrix = add(new_matrix, m[i].getData());
		}

		return new Matrix(new_matrix);
	}

	private static double[][] add(final double[][] m1, final double[][] m2) {

		// Adding m2 to m1 would change m1 even outside the method so a new double[][] array is needed
		double[][] m = new double[m1.length][m1[0].length];

		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m1[i].length; j++)
				m[i][j] = m1[i][j] + m2[i][j];
		}

		return m;
	}

	static Matrix multiply(Matrix ... m) {

		if (m.length < 2) return null;

		Matrix new_m = new Matrix(m[0]);

		for (int i = 1; i < m.length; i++) {

			if (new_m.numColumns != m[i].getSize()[0]) return null;
			
			new_m = new Matrix(multiply(new_m.getData(), m[i].getData()));	
		}

		return new_m;
	}

	private static double[][] multiply(final double[][] d1, final double[][] d2) {
		
		//Transpose d2
		var buff = new Matrix(d2);
		double[][] d2t = buff.transpose().getData();
	
		double[][] d = new double[d1.length][d2t.length];

		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[i].length; j++) {
				d[i][j] = Utilities.sum(Utilities.multiply(d1[i], d2t[j]));
			}
		}

		return d;
	}

	Matrix(final Matrix matrix) {

		this.matrix = new double[matrix.getSize()[0]][matrix.getSize()[1]];
		this.numRows = matrix.getSize()[0];
		this.numColumns = matrix.getSize()[1];
		
		final double[][] data = matrix.getData();

		for (int i = 0; i < this.numRows; i++) 
			for (int j = 0; j < this.numColumns; j++) 
				this.matrix[i][j] = data[i][j];
	}

	Matrix(double matrix[][]) {

		// Assigning a new 2d array to the instance variable
		// instead of just typing "this.matrix = matrix"
		// to avoid passing in the reference to the parameter
		// instead of actually creating a new 2-dimensional array

		this.matrix = new double[matrix.length][matrix[0].length];
		this.numRows = matrix.length;
		this.numColumns = matrix[0].length;
		
		for (int i = 0; i < matrix.length; i++) 
			for (int j = 0; j < matrix[i].length; j++) 
				this.matrix[i][j] = matrix[i][j];

	}

	// Basically turns a 1D array into a 2D array and then converts that to a Matrix object.
	// Also, the conversion is done such as the final $matrix will have all of its values
	// on one line, not one column
	Matrix(double matrix[]) {

		this.matrix = new double[1][matrix.length];
		this.numRows = 1;
		this.numColumns = matrix.length;

		for (int i = 0; i < matrix.length; i++)
			this.matrix[0][i] = matrix[i];

	}

	Matrix transpose() {

		final int rows = this.numColumns;
		final int columns = this.numRows;

		double[][] new_matrix = new double[rows][columns];

		for (int i = 0; i < rows; i++) 
			for (int j = 0; j < columns; j++)
				new_matrix[i][j] = this.matrix[j][i];

		return new Matrix(new_matrix);
	}

	//Multiplication of matrix with a scalar
	Matrix multiply(double d) {

		double[][] new_m = new double[numRows][numColumns];

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numColumns; j++)
				new_m[i][j] = matrix[i][j] * d;

		return new Matrix(new_m);
	}

	double[][] getData() {
		return matrix;
	}

	int[] getSize() {

		int size[] = {numRows, numColumns};
		return size;
	}

	// Prints the matrix to the screen.
	// The varargs takes only one additional argument which
	// sets the formating for the decimal places. For example
	// calling show(3) for 3.141592 only shows 3.142
	void show(int ... decimal) {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++)
				if (decimal.length > 0) {
					var bd = new BigDecimal(matrix[i][j]).setScale(decimal[0], RoundingMode.HALF_UP);
					System.out.print(bd.doubleValue() + " ");
				}
				else
					System.out.print(matrix[i][j] + " ");
			System.out.println();
		}

		System.out.println();
	}

}
