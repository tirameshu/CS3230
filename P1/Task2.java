import java.util.Scanner;

public class Task2 {
	static int solve(int n, int[] x1, int[] y1, int[] x2, int[] y2, int r, int c) {
		return 0;
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int r = sc.nextInt();
		int c = sc.nextInt();
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
		System.out.println(solve(n, x1, y1, x2, y2, r, c));
	}
}
