package Evomath;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Date;

/**
 * @author TheTrueKuro
 * @version 1.1
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

		if (n.length <= 0) throw new EvomathException("No parameters given. At least 1 required.");

		final int size = (n.length == 1) ? n[0] : n[1]; // Uses the first variable provided as size if second value doesn't exists
		if (n[0] <= 0 || size <= 0) throw new EvomathException("Error. Matrix size is less than 1"); // This check isn't necessary if the size is also $n[0] but it's efficient enough

		double m[][] = new double[n[0]][size];

		return new Matrix(m);
	}

	public static Matrix getIdentityMatrix(int n) {

		if (n <= 0) throw new EvomathException("Error. Matrix size is less than 1");

		double m[][] = new double[n][n];

		for (int i = 0; i < n; i++)
			m[i][i] = 1;

		return new Matrix(m);
	}

	public static Matrix getRandomMatrix(int rows, int columns) {

		if (rows <= 0 || columns <= 0)
			throw new EvomathException("Error. Number of rows and columns can't be less than 0.");

		double m[][] = new double[rows][columns];

		Random generator = new Random();
		Date date = new Date();
		long time = date.getTime();
		generator.setSeed(time);

		date = null;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				m[i][j] = 0.1 * generator.nextDouble() * (generator.nextBoolean() ? 1 : -1);
			}
		}

		return new Matrix(m);
	}

	public static Matrix add(Matrix ... m) {

		if (m.length == 0) throw new EvomathException("Error. Matrix size is less than 1");

		final int rows = m[0].numRows;
		final int columns = m[0].numColumns;

		double[][] new_matrix = new double[rows][columns];

		for (int i = 0; i < m.length; i++) {
			if (rows != m[i].numRows || columns != m[i].numColumns) throw new EvomathException("Number of columns of first matrix isn't equal to number of rows of next matrix. Matrix multiplication error");

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

	public static Matrix multiply(final Matrix ... m) {

		if (m.length < 2) throw new EvomathException("There has to be more than 1 matrix to multiply them");

		var new_m = m[0].matrix;

		for (int i = 1; i < m.length; i++) {

			if (new_m[0].length != m[i].numRows) throw new EvomathException("Number of columns of first matrix isn't equal to number of rows of next matrix. Matrix multiplication error");
			
			new_m = multiply(new_m, m[i].matrix);	
		}

		return new Matrix(new_m);
	}
	
	private static double[][] multiply(final double[][] d1, final double[][] d2) {
		//TODO Fix heap memory issues	
		double[][] d = new double[d1.length][d2[0].length];
		Thread[] threads = new Thread[4];

		for (int i = 0; i < d.length; i++) {
			if (i % 4 == 0 && i > 0) {
				for (var thread : threads) {
					try {
						thread.join();
					}
					catch(InterruptedException e) {
						System.out.println("Multiplication error " + e);
					}
				}
			}

			var buff = new Matrix.ThreadedMultiplication(i%4, d, d1, d2, i);
			threads[i % 4] = buff.t;
			buff.t.start();
		}

		for (var thread : threads) {
			if (thread != null && thread.isAlive()) {
				try {
					thread.join();
				}
				catch(InterruptedException e) {
					System.out.println("Multiplication error " + e);
				}
			}
		}

		return d;
	}

	private static class ThreadedMultiplication implements Runnable {

		Thread t;
		volatile double result[][];
		final double[][] m1;
		final double[][] m2;
		int row;

		ThreadedMultiplication(int index, double[][] result, final double[][] m1, final double[][] m2, int row) {

			final String name = "Thread: " + index;
			t = new Thread(this, name);
			this.result = result;
			this.m1 = m1;
			this.m2 = m2;
			this.row = row;
		}

		public void run() {

			for (int i = 0; i < m2[0].length; i++) {
				result[row][i] = 0;
				for (int j = 0; j < m2.length; j++) {
					result[row][i] += m1[row][j] * m2[j][i];
				}
			}
		}
	}

	public Matrix(final Matrix matrix) {

		this.matrix = new double[matrix.numRows][matrix.numColumns];
		this.numRows = matrix.numRows;
		this.numColumns = matrix.numColumns;
		
		final double[][] data = matrix.matrix;

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

	public double get(int i, int j) {

		if ((i < 0 || i >= numRows) || (j < 0 || j >= numColumns))
			throw new EvomathException("Index out of bounds");

		return matrix[i][j];
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

		if (numRows != numColumns) throw new EvomathException("Matrix has to be square to get the inverse");

		double determinant = getDeterminant();

		if (determinant == 0) return null;

		Matrix adjacency_matrix = getAdjacencyMatrix();
		return adjacency_matrix.multiply(1/determinant);
	}

	public Matrix getAdjacencyMatrix() {

		if (numRows != numColumns) throw new EvomathException("Matrix has to be square to get adjacency matrix");

		double[][] d = new double[numRows][numColumns];

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numColumns; j++)
				d[i][j] = getComplement(matrix, i, j);

		return new Matrix(d).transpose();
	}

	public int getRank() {
	
		int rank = numColumns;

		Matrix M = new Matrix(this);

		for (int i = 0; i < rank; i++) {
				
			double[][] m = M.matrix;

			if (m[i][i] == 0) {

				boolean swapped = false;

				for (int k = i+1; k < numRows; k++) {
					if (m[k][i] != 0) {
						M = M.swapRows(k, i);
						swapped = true;
						break;
					}
				}

				if (!swapped) {
					M = M.swapColumns(i, rank-1);
					rank--;
				}

				i--;
				continue;
			}
				
			for (int j = 0; j < numRows; j++) {
				if (j != i) 
					M = M.addToRow(j, M.getRow(i).multiply(-m[j][i]/m[i][i]));
			}

		}

		return rank;
	}

	public Matrix swapRows(int row1, int row2) {

		if (row1 < 0 || row1 >= numRows) throw new EvomathException("Row index is invalid. It has to be from [0; numRows)");
		if (row2 < 0 || row2 >= numRows) throw new EvomathException("Row index is invalid. It has to be from [0; numRows)");

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

		if (row < 0 || row >= numRows) throw new EvomathException("Row index is invalid. It has to be from [0; numRows)");

		double m[] = new double[numColumns];

		for (int i = 0; i < numColumns; i++)
			m[i] = matrix[row][i];

		return new Matrix(m);
	}

	public Matrix removeRow(int row) {

		if (row < 0 || row >= numRows) throw new EvomathException("Row index is invalid. It has to be from [0; numRows)");

		double[][] m = new double[numRows-1][numColumns];

		for (int i = 0; i < numRows; i++) {
			if (i == row)
				continue;
			for (int j = 0; j < numColumns; j++) {
				m[i - (i > row ? 1 : 0)][j] = matrix[i][j];
			}
		}

		return new Matrix(m);
	}

	public Matrix swapColumns(int col1, int col2) {

		if (col1 < 0 || col1 >= numColumns) throw new EvomathException("Column index is invalid. It has to be from [0; numColumns)");
		if (col2 < 0 || col2 >= numColumns) throw new EvomathException("Column index is invalid. It has to be from [0; numColumns)");

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

		if (col < 0 || col >= numColumns) throw new EvomathException("Column index is invalid. It has to be from [0; numColumns)");

		double m[] = new double[numRows];

		for (int i = 0; i < numRows; i++)
			m[i] = matrix[i][col];

		return (new Matrix(m)).transpose();
	}

	public Matrix removeColumn(int col) {

		if (col < 0 || col >= numColumns) throw new EvomathException("Column index is invalid. It has to be from [0; numColumns)");

		double[][] m = new double[numRows][numColumns-1];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (j == col)
					continue;
				m[i][j - (j > col ? 1 : 0)] = matrix[i][j];
			}
		}

		return new Matrix(m);
	}

	//Function to add a vector (Matrix with numRows=1) to an existing row
	public Matrix addToRow(int row, Matrix vector) {

		if (vector.numColumns != this.numColumns) throw new EvomathException("The number of columns of the vector and matrix doesn't match");

		double m[][] = matrix.clone();

		for (int i = 0; i < numColumns; i++) {
			m[row][i] += vector.matrix[0][i];
			if (m[row][i] < 1e-15 && m[row][i] > -1e-15)
				m[row][i] = 0;
		}

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
		double[][] data = new double[numRows][numColumns];
		for (int i = 0; i < numRows; i++)
			data[i] = matrix[i].clone();
		return data;
	}

	public int[] getSize() {

		int size[] = {numRows, numColumns};
		return size;
	}

	public String toString() {

		String text = "";

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++)
				text += String.valueOf(matrix[i][j]) + " ";
			text += "\n";
		}

		text += "\n";
		return text;
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
