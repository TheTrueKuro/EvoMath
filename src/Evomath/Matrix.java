package Evomath;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author TheTrueKuro
 * @version 1.0
 *
 * Class made for creating and processing matrices.
 *
 * There are some static methods such as getIdentityMatrix and getZeroMatrix
 * which return a matrix. Apart from them the only ways to create a matrix is to
 * use a constructor and pass in as a parameter a 2D double type array, a 1D 
 * double type array or another Matrix object to basically return a copy of it.
 *
 * The Matrix object created uses an internal final double[][] array meaning the object
 * itself can't be modified after created.
 *
 * The rest of the methods have pretty self-explanatory names which indicate the specific
 * math operation it performs.
 *
 * There is also a void show() method which prints the Matrix object on the screen
 */

public class Matrix {

	final private double matrix[][];
	final private int numRows, numColumns;

	public static Matrix getZeroMatrix(int ... n) {

		if (n.length <= 0) return null;

		final int size = (n.length == 1) ? n[0] : n[1]; // Uses the first variable provided as size if second value doesn't exists
		if (n[0] <= 0 || size <= 0) return null; // This check isn't necessary if the size is also $n[0] but it's efficient enough

		double m[][] = new double[n[0]][size];

		return new Matrix(m);
	}

	public static Matrix getIdentityMatrix(int n) {

		if (n <= 0) return null;

		double m[][] = new double[n][n];

		for (int i = 0; i < n; i++)
			m[i][i] = 1;

		return new Matrix(m);
	}

	public static Matrix add(Matrix ... m) {

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

	public static Matrix multiply(Matrix ... m) {

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

	public Matrix(final Matrix matrix) {

		this.matrix = new double[matrix.getSize()[0]][matrix.getSize()[1]];
		this.numRows = matrix.getSize()[0];
		this.numColumns = matrix.getSize()[1];
		
		final double[][] data = matrix.getData();

		for (int i = 0; i < this.numRows; i++) 
			for (int j = 0; j < this.numColumns; j++) 
				this.matrix[i][j] = data[i][j];
	}

	public Matrix(double matrix[][]) {

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
	public Matrix(double matrix[]) {

		this.matrix = new double[1][matrix.length];
		this.numRows = 1;
		this.numColumns = matrix.length;

		for (int i = 0; i < matrix.length; i++)
			this.matrix[0][i] = matrix[i];

	}

	public Matrix transpose() {

		final int rows = this.numColumns;
		final int columns = this.numRows;

		double[][] new_matrix = new double[rows][columns];

		for (int i = 0; i < rows; i++) 
			for (int j = 0; j < columns; j++)
				new_matrix[i][j] = this.matrix[j][i];

		return new Matrix(new_matrix);
	}

	//Multiplication of matrix with a scalar
	public Matrix multiply(double d) {

		double[][] new_m = new double[numRows][numColumns];

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numColumns; j++)
				new_m[i][j] = matrix[i][j] * d;

		return new Matrix(new_m);
	}

	public double getDeterminant() {

		if (numColumns != numRows) {
			System.out.println("Only square matrices can have determinants");
			return -1;
		}

		if (numRows == 2)
			return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

		double determinant = 0;

		for (int i = 0; i < numColumns; i++) {

			determinant += matrix[0][i] * getComplement(matrix, 0, i);
		}

		return determinant;
	}

	//Gets algebric complement of an element of a matrix
	private double getComplement(double[][] d, int y, int x) {

		double determinant = 0;
		double[][] new_d = new double[d.length-1][d[0].length-1];

		int y_bias = 0;

		outer: for (int i = 0; i < d.length; i++) {
			       
			int x_bias = 0;

			for (int j = 0; j < d[i].length; j++) {
				if (i == y) {
					y_bias = 1;
					continue outer;
				}
				if (j == x) {
					x_bias = 1;
					continue;
				}
				new_d[i - y_bias][j - x_bias] = d[i][j];
			}
		}

		if (new_d.length > 2) {
			for (int i = 0; i < new_d.length; i++) {
				determinant += new_d[0][i] * getComplement(new_d, 0, i);
			}
		}

		else if (new_d.length > 1)
			determinant += new_d[0][0] * new_d[1][1] - new_d[0][1] * new_d[1][0];

		else
			determinant = new_d[0][0];
		
		int sign = ((x+y)%2 == 0) ? 1 : (-1);
		return sign * determinant;
	}

	public Matrix getInverse() {

		if (numRows != numColumns) return null;

		double determinant = getDeterminant();

		if (determinant == 0) return null;

		Matrix adjacency_matrix = getAdjacencyMatrix();
		return adjacency_matrix.multiply(1/determinant);
	}

	public Matrix getAdjacencyMatrix() {

		if (numRows != numColumns) return null;

		double[][] d = new double[numRows][numColumns];

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numColumns; j++)
				d[i][j] = getComplement(matrix, i, j);

		return new Matrix(d).transpose();
	}

	public int getRank() {
	
		int rank = numColumns;

		Matrix M = new Matrix(this);
		double[][] m = M.matrix;

		for (int i = 0; i < rank; i++) {
				
			if (m[i][i] == 0) {

				boolean swapped = false;

				for (int k = i+1; k < numRows; k++) {
					if (m[k][i] != 0) {
						M = M.swapRows(k, i);
						m = M.matrix;
						swapped = true;
						break;
					}
				}

				if (!swapped) {
					M = M.swapColumns(i, rank-1);
					m = M.matrix;
					rank--;
				}

				i--;
				continue;
			}
				
			for (int j = i; j < numRows-1; j++) {
				M.addToRow(j+1, M.getRow(i).multiply(-m[j+1][i]/m[i][i]));
			}

		}

		return rank;
	}

	public Matrix swapRows(int row1, int row2) {

		if (row1 < 0 || row1 >= numRows) return null;
		if (row2 < 0 || row2 >= numRows) return null;

		double m[][] = new double[numRows][numColumns];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {

				if (i == row1) {
					m[i][j] = matrix[row2][j];
				}

				else if (i == row2) {
					m[i][j] = matrix[row1][j];
				}

				else {
					m[i][j] = matrix[i][j];
				}
			}
		}

		return new Matrix(m);
	}

	public Matrix getRow(int row) {

		if (row < 0 || row >= numRows) return null;

		double m[] = new double[numColumns];

		for (int i = 0; i < numColumns; i++)
			m[i] = matrix[row][i];

		return new Matrix(m);
	}

	public Matrix swapColumns(int col1, int col2) {

		if (col1 < 0 || col1 >= numColumns) return null;
		if (col2 < 0 || col2 >= numColumns) return null;

		double m[][] = new double[numRows][numColumns];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (j == col1) {
					m[i][j] = matrix[i][col2];
				}

				else if (j == col2) {
					m[i][j] = matrix[i][col1];
				}

				else {
					m[i][j] = matrix[i][j];
				}
			}
		}

		return new Matrix(m);
	}

	public Matrix getColumn(int col) {

		if (col < 0 || col >= numColumns) return null;

		double m[] = new double[numRows];

		for (int i = 0; i < numRows; i++)
			m[i] = matrix[i][col];

		return new Matrix(m);
	}

	//Function to add a vector (Matrix with numRows=1) to an existing row
	public Matrix addToRow(int row, Matrix vector) {

		if (vector.numColumns != this.numColumns) return null;

		double m[][] = matrix.clone();

		for (int i = 0; i < numColumns; i++) 
			m[row][i] += vector.matrix[0][i];

		return new Matrix(m);
	}

	public boolean equals(Matrix matrix) {

		if (this.numRows != matrix.numRows || this.numColumns != matrix.numColumns)
			return false;

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (this.matrix[i][j] != matrix.matrix[i][j])
					return false;
			}
		}

		return true;
	}

	public double[][] getData() {
		return matrix;
	}

	public int[] getSize() {

		int size[] = {numRows, numColumns};
		return size;
	}

	// Prints the matrix to the screen.
	// The varargs takes only one additional argument which
	// sets the formating for the decimal places. For example
	// calling show(3) for 3.141592 only shows 3.142
	public void show(int ... decimal) {

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
