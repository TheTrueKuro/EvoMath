class Matrix {

	private double matrix[][];

	static Matrix getZeroMatrix(int n) {

		double m[][] = new double[n][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				m[i][j] = 0;

		return new Matrix(m);
	}

	Matrix(double matrix[][]) {

		// Assigning a new matrix to the instance variable
		// instead of just typing this.matrix = matrix
		// to avoid passing in the reference to the parameter
		// instead of actually creating a new 2-dimensional array

		this.matrix = new double[matrix.length][matrix[0].length];
		
		for (int i = 0; i < matrix.length; i++) 
			for (int j = 0; j < matrix[i].length; j++) 
				this.matrix[i][j] = matrix[i][j];

	}

	Matrix(double matrix[]) {

		this.matrix = new double[1][matrix.length];

		for (int i = 0; i < matrix.length; i++)
			this.matrix[0][i] = matrix[i];

	}

	void show() {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}
	}

}
