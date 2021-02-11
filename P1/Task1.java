import java.util.Scanner;

public class Task1 {
	
	int[][] strassen(int a[][], int b[][]) {
		int n = a.length;
		int[][] product = new int[n][n];
		return product;
	}
	void run() {
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(); // size of matric

		int a[][] = new int[n][n];
		int b[][] = new int[n][n];

		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				a[i][j] = sc.nextInt();
			}
		}
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				b[i][j] = sc.nextInt();
			}
		}
		
		int[][] product = strassen(a,b);
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				System.out.printf("%d ", product[i][j]);
			}
			System.out.printf("\n");
		}
	}
	public static void main(String[] args) {
		Task1 strassen = new Task1();
		strassen.run();
	}
}
