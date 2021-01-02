class Matrix {

	private double matrix[][];

	static Matrix getZeroMatrix(int ... n) {

		if (n.length <= 0) return null;

		double m[][] = new double[n[0]][];
		final int size = (n.length == 1) ? n[0] : n[1]; // Uses the first variable provided as size if second value doesn't exists
		if (size <= 0) return null; // This check isn't necessary if the size is also $n[0] but it's efficient enough

		for (int i = 0; i < n[0]; i++) {
			m[i] = new double[size];
			for (int j = 0; j < size; j++)
				m[i][j] = 0;
		}

		return new Matrix(m);
	}

	static Matrix getIdentityMatrix(int n) {

		if (n <= 0) return null;

		double m[][] = new double[n][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				m[i][j] = (i == j) ? 1 : 0;

		return new Matrix(m);
	}

	Matrix(double matrix[][]) {

		// Assigning a new 2d array to the instance variable
		// instead of just typing "this.matrix = matrix"
		// to avoid passing in the reference to the parameter
		// instead of actually creating a new 2-dimensional array

		this.matrix = new double[matrix.length][matrix[0].length];
		
		for (int i = 0; i < matrix.length; i++) 
			for (int j = 0; j < matrix[i].length; j++) 
				this.matrix[i][j] = matrix[i][j];

	}

	// Basically turns a 1D array into a 2D array and then converts that to a Matrix object.
	// Also, the conversion is done such as the final $matrix will have all of its values
	// on one line, not one column
	Matrix(double matrix[]) {

		this.matrix = new double[1][matrix.length];

		for (int i = 0; i < matrix.length; i++)
			this.matrix[0][i] = matrix[i];

	}

	double[][] getData() {
		return matrix;
	}

	//Prints the matrix to the screen
	void show() {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}

		System.out.println();
	}

}
