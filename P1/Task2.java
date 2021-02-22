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
	public static int n, originalCols, originalRows;
	public static int[] x1, x2, y1, y2;

	public Task2(int n, int[] x1, int[] y1, int[] x2, int[] y2, int cols, int rows) {
		this.n = n;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.originalCols = cols;
		this.originalRows = rows;
	}

	/**
	 * Check valid cut by checking if col to cut is within any [x1, x2)
	 * 	so that this is actly O(n).
	 * As this will be used recursively, boundary rows and columns will change.
	 * Also check validity by ensuring each piece has topping.
	 */
	boolean verticalCut(int colToCut, int startRow, int startCol, int endRow, int endCol) {
		boolean hasTopping = false;
		boolean leftHasTopping = false;
		boolean rightHasTopping = false;

//		System.out.println("Attempting to cut col " + colToCut);

		for (int i = 0; i < n; i++) { // O(n)
			if (y2[i] <= startRow || y1[i] >= endRow) {
				continue; // only check for toppings in range!!!!
			}
			// if colToCut == x1[i], cut is on left border of topping
			// if colToCut == x2[i], cut is on the right border of topping
			if (x1[i] < colToCut && colToCut < x2[i]) {
//				System.out.println("Cutting through topping on col " + colToCut + "!");
				return false;
			}

			if (!hasTopping) {
				// checking if current topping is on left or right of the cut
				if (startCol <= x1[i] && x2[i] <= colToCut) { // check if topping is on left slice
					leftHasTopping = true;
//					System.out.println("Topping " + (i+1) + " [" + x1[i] + ", " + x2[i] + ") on left");
				} else if (colToCut <= x1[i] && x2[i] <= endCol) { // check if topping is on right slice
					rightHasTopping = true;
//					System.out.println("Topping " + (i+1) + " [" + x1[i] + ", " + x2[i] + ") on right");
				}

				// However, even if this is true, cut can be invalid if cut thru toppings in future loops,
				// so don't return/ break here.
				hasTopping = leftHasTopping && rightHasTopping;
			}

		}

		return hasTopping;
	}

	boolean horizontalCut(int rowToCut, int startRow, int startCol, int endRow, int endCol) {
		boolean hasTopping = false;
		boolean topHasTopping = false;
		boolean bottomHasTopping = false;

//		System.out.println("Attempting to cut row " + rowToCut);

		for (int i = 0; i < n; i++) { // O(n)
			if (x2[i] <= startCol || x1[i] >= endCol) {
//				System.out.println("Cutting through topping on row " + rowToCut + "!");
				continue; // only check for toppings in range!!!!
			}
			// if rowToCut == y1[i], cut is above topping
			// if rowToCut == y2[i], cut is below topping
			if (y1[i] < rowToCut && rowToCut < y2[i]) {
				return false;
			}

			if (!hasTopping) {
				// checking if current topping is on left or right of the cut
				if (startRow <= y1[i] && y2[i] <= rowToCut) { // check if topping is above slice
					topHasTopping = true;
//					System.out.println("Topping " + (i+1) + " [" + y1[i] + ", " + y2[i] + ") on top");
				} else if (rowToCut <= y1[i] && y2[i] <= endRow) { // check if topping is below slice
					bottomHasTopping = true;
//					System.out.println("Topping " + (i+1) + " [" + y1[i] + ", " + y2[i] + ") below");
				}

				// However, even if this is true, cut can be invalid if cut thru toppings in future loops,
				// so don't return/ break here.
				hasTopping = topHasTopping && bottomHasTopping;
			}

		}

		return hasTopping;
	}

	/**
	 * for each block of cake (can contain multiple toppings):
	 * 		for each topping:
	 * 			check if topping in range
	 * 			get the topping's horizontal boundary (row = y2): valid cut along this?
	 * 				if yes, cut and return solve(resultant pieces)
	 * 			get the topping's vertical boundary (col = x2): valid cut along this?
	 * 				if yes, cut and return solve(resultant pieces)
	 *
	 * 		return 1: cannot cut along any topping
	 */
	int solve(int startRow, int startCol, int endRow, int endCol) {
		if (startRow == endRow - 1 && startCol == endCol - 1) {
			return 1;
		}
		for (int i = 0; i < n; i++) {
			int rowToCut = y2[i]; // horizontal boundary of a topping
			if (horizontalCut(rowToCut, startRow, startCol, endRow, endCol)) { // can cut along this row
//				System.out.println("Can cut through row " + rowToCut);
				return solve(startRow, startCol, rowToCut, endCol) + solve(rowToCut, startCol, endRow, endCol);
			}

			int colToCut = x2[i]; // vertical boundary of a topping
			if (verticalCut(colToCut, startRow, startCol, endRow, endCol)) { // can cut along this col
//				System.out.println("Can cut through col " + colToCut);
				return solve(startRow, startCol, endRow, colToCut) + solve(startRow, colToCut, endRow, endCol);
			}
		}

		return 1;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int cols = sc.nextInt();
		int rows = sc.nextInt();
		int n = sc.nextInt();
		int x1[] = new int[n];
		int y1[] = new int[n];
		int x2[] = new int[n];
		int y2[] = new int[n];

		for(int i=0; i<n; i++) {
			x1[i] = sc.nextInt();
			y1[i] = sc.nextInt();
			x2[i] = sc.nextInt();
			y2[i] = sc.nextInt();
		}

		Task2 task2 = new Task2(n, x1, y1, x2, y2, cols, rows);

		System.out.println(task2.solve(0, 0, rows, cols));
	}
}
