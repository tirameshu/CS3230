import java.util.Scanner;

/**
 * 0. Store maxPieces = 1
 * 1. For each column, attempt a vertical cut (O(n) for checking cells on both sides of cut) -> temp = fn(cake)
 * 	1.1 For each valid cut,
 * 		1.1.1 Check if any of the resultant two pieces has no toppings. If yes, return 1.
 * 		1.1.2 Otherwise, cut recursively for the resultant two pieces -> return fn(left) + fn(right).
 * 	1.2 For each invalid cut, return 1 (base case).
 * 	1.3 At the end of each col, maxPieces = max(maxPieces, temp).
 * 2. Repeat for row.
 */

public class Task2 {

	// O(n)
	boolean verticalCut(int[][] matrix, int cols, int rows, int colToCut) {
		for (int row = 0; row < rows; row++) {
			int leftCell = matrix[row][colToCut-1]; // since colToCut starts from 1
			int rightCell = matrix[row][colToCut];
			if (leftCell == rightCell) {
				return false; // cutting through topping: invalid
			}
		}
		return true;
	}

	int recursiveSolve(int[][] matrix, int cols, int rows, int colCut) {
		// check if any resultant piece is empty


		// also only do vertical cut for those to right of col cut;
		// those to left alr checked.

		if (verticalCut(matrix, cols, rows, colToCut) == true) { // valid cut
			return recursiveSolve(matrix, ) + recursiveSolve(right);
		}

		if (horizontalCut() == true) { // valid cut
			task2.recursiveSolve(left, right);
		}
		return 1;
	}

	static int solve(int nToppings, int[] x1, int[] y1, int[] x2, int[] y2, int cols, int rows) {
		Task2 task2 = new Task2();
		int temp = 1; // default 1
		int maxPieces = 1;

		int[][] matrix = new int[rows][cols]; // default cell = 0, no topping
		int colStart, colEnd, rowStart, rowEnd;

		// populate matrix w toppings
		for (int topping = 0; topping < nToppings; topping++) {
			// locate each topping
			colStart = x1[topping];
			colEnd = x2[topping];
			rowStart = y1[topping];
			rowEnd = y2[topping];

			for (int rowPtr = rowStart; rowPtr < rowEnd; rowPtr++) {
				for (int colPtr = colStart; colPtr < colEnd; colPtr++) {
					matrix[rowPtr][colPtr] = topping + 1; // +1 for actual topping number
				}
			}
		}

//		for (int col = 1; col < cols; col++) {
//			if (task2.verticalCut(matrix, cols, rows, col) == true) {
//				System.out.println("can cut along col " + col);
//			} else {
//				System.out.println("cannot cut along col " + col);
//			}
//		}

		// 1. Vertical attempt
		// start cutting from col 1, which is in between
		// matrix[0][0] and matrix[0][1]
		for (int col = 1; col < cols; col++) {
			if (verticalCut(matrix, cols, rows, col) == true) {
				temp = task2.recursiveSolve(matrix, cols, rows, col);
			}

			maxPieces = max(maxPieces, temp);
		}

		// 2. Horizontal attempt
		for (int row = 1; row < rows; row++) { // start cutting from row 1
			if (horizontalCut(matrix, cols, rows, rowToCut) == true) {
				temp = task2.recursiveSolve(matrix);
			}

			maxPieces = max(maxPieces, temp);
		}

		return maxPieces;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int cols = sc.nextInt();
		int rows = sc.nextInt();
		int nToppings = sc.nextInt();
		int x1[] = new int[nToppings];
		int y1[] = new int[nToppings];
		int x2[] = new int[nToppings];
		int y2[] = new int[nToppings];

		for(int i=0; i<nToppings; i++) {
			x1[i] = sc.nextInt();
			y1[i] = sc.nextInt();
			x2[i] = sc.nextInt();
			y2[i] = sc.nextInt();
		}

		System.out.println(solve(nToppings, x1, y1, x2, y2, cols, rows));
	}
}
