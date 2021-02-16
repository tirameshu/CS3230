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

	int recurseHorizontal(int startRow, int startCol, int endRow, int endCol) {
		return 0;
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

		for (int i = 0; i < nToppings; i++) { // O(n)
			// if colToCut == x1[i], cut is on left border of topping
			// if colToCut == x2[i], cut is on the right border of topping
			if (x1[i] < colToCut && colToCut < x2[i]) {
				System.out.println("col " + colToCut + " cannot be cut");
				return false;
			}

			if (!hasTopping) {
				// checking if current topping is on left or right of the cut
				if (startCol <= x1[i] && x2[i] <= colToCut) { // check if topping is on left slice
					leftHasTopping = true;
					System.out.println("Topping " + i + " [" + x1[i] + ", " + x2[i] + ") on left");
				} else if (colToCut <= x1[i] && x2[i] <= endCol) { // check if topping is on right slice
					rightHasTopping = true;
					System.out.println("Topping " + i + " [" + x1[i] + ", " + x2[i] + ") on right");
				}

				// However, even if this is true, cut can be invalid if cut thru toppings in future loops,
				// so don't return/ break here.
				hasTopping = leftHasTopping && rightHasTopping;
			}

		}

		return hasTopping;
	}

	int recurseVertical(int startRow, int startCol, int endRow, int endCol) {
		// col = 1 is the column between (0, 0) and (0, 1)
		for (int colToCut = startCol+1; colToCut < endCol; colToCut++) {

			// for every part cake, try slicing every col possible incrementally
			if (verticalCut(colToCut, startRow, startCol, endRow, endCol) == true) {

				// Find max of subcase
				int left = Math.max(recurseHorizontal(startRow, startCol, originalRows, colToCut), recurseVertical(startRow, startCol, originalRows, colToCut));
				System.out.println("left: " + left);
				int right = Math.max(recurseHorizontal(startRow, colToCut, originalRows, originalCols), recurseVertical(startRow, colToCut, originalRows, originalCols));
				System.out.println("right: " + right);
				int temp = left + right;

				return temp;
			} else {
				continue;
//				System.out.println("cannot cut col " + col);
			}
		}

		// when startCol == endCol - 1
		return 1;
	}

	static int solve(Task2 task2) {
		return Math.max(task2.recurseHorizontal(0, 0, originalRows, originalCols), task2.recurseVertical(0, 0, originalRows, originalCols));

//		// col = 1 is the column between (0, 0) and (0, 1)
//		for (int col = 1; col < originalCols; col++) {
//			if (task2.verticalCut(col, 0, 0, originalRows, originalCols) == true) {
//				// only do vertical cut for those to right of col cut;
//				// those to left alr checked vertically.
//				temp = task2.recurseHorizontal(0, 0, originalRows, col) + task2.recurseVertical(0, col, originalRows, originalCols);
////				System.out.println("can cut col " + col);
//			} else {
////				System.out.println("cannot cut col " + col);
//			}
//
//			maxPieces = Math.max(maxPieces, temp);
//		}

		// 2. Horizontal attempt
//		for (int row = 1; row < rows; row++) { // start cutting from row 1
//			if (horizontalCut(Task2) == true) {
//				temp = task2.recursiveSolve(task2);
//			}
//
//			maxPieces = Math.max(maxPieces, temp);
//		}
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
