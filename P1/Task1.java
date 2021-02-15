import java.util.Scanner;

public class Task1 {

	void printMatrix(int[][] matrix) {
		int n = matrix.length; // assume sq

		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				System.out.printf("%d ", matrix[i][j]);
			}
			System.out.printf("\n");
		}
	}

	int[][] naiveMultiply(int[][] a, int[][] b) {
		int n = a.length; // size of sq matrix
		assert(a.length == b.length);

		int[][] result = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result[i][j] = 0;
				for (int k = 0; k < n; k++) {
					result[i][j] += a[i][k] * b[k][j];
				}
			}
		}

		return result;
	}

	int[][] multiply(int[][] a, int[][] b) {
		int n = a.length; // size of sq matrix
		assert(a.length == b.length);
		
		int[][] result;

		if (n <= 64) {
			result = naiveMultiply(a, b);
		} else {
			result = strassen(a, b);
		}

		return result;
	}

	int[][] combine(int[][] left, int[][] right, char op) {
		int n = left.length; // size of sq matrix
		assert(left.length == right.length); // sanity check

		int[][] result = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (op == '+') {
					result[i][j] = left[i][j] + right[i][j];
				} else if (op == '-') {
					result[i][j] = left[i][j] - right[i][j];
				}
			}
		}

		return result;
	}

	int[][] add(int[][] left, int[][] right) {
		return combine(left, right, '+');
	}

	int[][] sub(int[][] left, int[][] right) {
		return combine(left, right, '-');
	}

	void split(int original[][], int[][] part, int startingRow, int startingCol) {
		int n = part.length;

		int partRow, originalRow, partCol, originalCol;
		// assuming part is sq
		for ( partRow = 0, originalRow = startingRow; partRow < n; partRow++, originalRow++) {
			for (partCol = 0, originalCol = startingCol; partCol < n; partCol++, originalCol++) {
				part[partRow][partCol] = original[originalRow][originalCol];
			}
		}

		//System.out.println("splitting " + n + "x" + n);
		//printMatrix(part);
	}

	void join(int[][] part, int[][] whole, int startingRow, int startingCol) {
		int n = part.length;

		int wholeRow, partRow, wholeCol, partCol;
		// assuming part is sq
		for (partRow = 0, wholeRow = startingRow; partRow < n; wholeRow++, partRow++) {
			for (partCol = 0, wholeCol = startingCol; partCol < n; wholeCol++, partCol++) {
				whole[wholeRow][wholeCol] = part[partRow][partCol];
			}
		}

		//System.out.println("joining " + n + "x" + n);
		//printMatrix(whole);
	}
	
	int[][] strassen(int[][] a, int[][] b) {
		int n = a.length; // size of sq matrix

		int[][] c = new int[n][n]; // final product

		if (n == 1) {
			c[0][0] = a[0][0] * b[0][0];
		} else {
			// Split each matrix into 4 parts,
			// each part is a sq matrix (n/2 x n/2)
			// as n is even.
			int[][] a11 = new int[n / 2][n / 2];
			int[][] a12 = new int[n / 2][n / 2];
			int[][] a21 = new int[n / 2][n / 2];
			int[][] a22 = new int[n / 2][n / 2];

			int[][] b11 = new int[n / 2][n / 2];
			int[][] b12 = new int[n / 2][n / 2];
			int[][] b21 = new int[n / 2][n / 2];
			int[][] b22 = new int[n / 2][n / 2];

			split(a, a11, 0, 0);
			split(a, a12, 0, n / 2);
			split(a, a21, n / 2, 0);
			split(a, a22, n / 2, n / 2);

			split(b, b11, 0, 0);
			split(b, b12, 0, n / 2);
			split(b, b21, n / 2, 0);
			split(b, b22, n / 2, n / 2);

			// Strassen’s idea

			// P1 = a11 x (b12 - b22)
			int[][] P1 = multiply(a11, sub(b12, b22));

			// P2 = (a11 + a12) x b22
			int[][] P2 = multiply(add(a11, a12), b22);

			// P3 = (a21 + a22) x b11
			int[][] P3 = multiply(add(a21, a22), b11);

			// P4 = a22 x (b21 - b11)
			int[][] P4 = multiply(a22, sub(b21, b11));

			// P5 = (a11 + a22) x (b11 + b22)
			int[][] P5 = multiply(add(a11, a22), add(b11, b22));

			// P6 = (a12 - a22) x (b21 + b22)
			int[][] P6 = multiply(sub(a12, a22), add(b21, b22));

			// P7 = (a11 - a21) x (b11 + b12)
			int[][] P7 = multiply(sub(a11, a21), add(b11, b12));

			// 4 quadrants of C
			int[][] c11 = add(sub(add(P5, P4),P2), P6);
//			printMatrix(c11);
			int[][] c12 = add(P1, P2);
//			printMatrix(c12);
			int[][] c21 = add(P3, P4);
//			printMatrix(c21);
			int[][] c22 = sub(sub(add(P5, P1), P3), P7);
//			printMatrix(c22);

			// Step 3: Join 4 halves into one result matrix
			join(c11, c, 0, 0);
			join(c12, c, 0, n / 2);
			join(c21, c, n / 2, 0);
			join(c22, c, n / 2, n / 2);
		}

		// Step 4: Return result
		return c;
	}

	void run() {
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(); // size of matric, a power of 2

		int[][] a = new int[n][n];
		int[][] b = new int[n][n];

		for (int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				a[i][j] = sc.nextInt();
			}
		}

		for (int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				b[i][j] = sc.nextInt();
			}
		}
		
		int[][] product = multiply(a,b);

		printMatrix(product);
	}
	public static void main(String[] args) {
		Task1 strassen = new Task1();
		strassen.run();
	}
}
