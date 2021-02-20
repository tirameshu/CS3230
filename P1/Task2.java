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
	public static int nToppings, originalCols, originalRows;
	public static int[] x1, x2, y1, y2;

	public Task2(int nToppings, int[] x1, int[] y1, int[] x2, int[] y2, int cols, int rows) {
		this.nToppings = nToppings;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.originalCols = cols;
		this.originalRows = rows;
	}

	/**
	 * Check valid cut by checking if col to cut is within any [x1, x2)
	 * 	so that this is actly O(nToppings).
	 * As this will be used recursively, boundary rows and columns will change.
	 * Also check validity by ensuring each piece has topping.
	 */
	boolean verticalCut(int colToCut, int startRow, int startCol, int endRow, int endCol) {
		boolean hasTopping = false;
		boolean leftHasTopping = false;
		boolean rightHasTopping = false;

//		System.out.println("Attempting to cut col " + colToCut);

		for (int i = 0; i < nToppings; i++) { // O(n)
			if (y2[i] <= startRow || y1[i] >= endRow) {
				continue; // only check for toppings in range!!!!
			}
			// if colToCut == x1[i], cut is on left border of topping
			// if colToCut == x2[i], cut is on the right border of topping
			if (x1[i] < colToCut && colToCut < x2[i]) {
//				System.out.println("col " + colToCut + " cannot be cut");
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

		for (int i = 0; i < nToppings; i++) { // O(n)
			if (x2[i] <= startCol || x1[i] >= endCol) {
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

	int recurseHorizontal(int startRow, int startCol, int endRow, int endCol) {
		// row = 1 is the row between (0, 0) and (1, 0)
		for (int rowToCut = startRow+1; rowToCut < endRow; rowToCut++) {

			// for every part cake, try slicing every col possible incrementally
			if (horizontalCut(rowToCut, startRow, startCol, endRow, endCol) == true) {

				// Find max of subcase
				int top = Math.max(recurseHorizontal(startRow, startCol, rowToCut, endCol), recurseVertical(startRow, startCol, rowToCut, endCol));
//				System.out.println("top: " + top);
				int bottom = Math.max(recurseHorizontal(rowToCut, startCol, endRow, endCol), recurseVertical(rowToCut, startCol, endRow, endCol));
//				System.out.println("bottom: " + bottom);
				int temp = top + bottom;

				return temp;
			} else {
//				System.out.println("cannot cut row " + rowToCut);
				continue;
			}
		}

		// when startRow == endRow - 1
		return 1;
	}

	int recurseVertical(int startRow, int startCol, int endRow, int endCol) {
		// col = 1 is the column between (0, 0) and (0, 1)
		for (int colToCut = startCol+1; colToCut < endCol; colToCut++) {

			// for every part cake, try slicing every col possible incrementally
			if (verticalCut(colToCut, startRow, startCol, endRow, endCol) == true) {

				// Find max of subcase
				int left = Math.max(recurseHorizontal(startRow, startCol, endRow, colToCut), recurseVertical(startRow, startCol, endRow, colToCut));
//				System.out.println("left: " + left);
				int right = Math.max(recurseHorizontal(startRow, colToCut, endRow, endCol), recurseVertical(startRow, colToCut, endRow, endCol));
//				System.out.println("right: " + right);
				int temp = left + right;

				return temp;
			} else {
//				System.out.println("cannot cut col " + colToCut);
				continue;
			}
		}

		// when startCol == endCol - 1
//		System.out.println("No col can be cut for range (" + startRow + ", " + startCol +"); ("
//				+ endRow + ", " + endCol + ")");
		return 1;
	}

	static int solve(Task2 task2) {
		return Math.max(task2.recurseHorizontal(0, 0, originalRows, originalCols), task2.recurseVertical(0, 0, originalRows, originalCols));
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

		Task2 task2 = new Task2(nToppings, x1, y1, x2, y2, cols, rows);

		System.out.println(solve(task2));
	}
}
