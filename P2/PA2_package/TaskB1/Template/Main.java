import java.util.*;

/*
ID:
Describe your algorithm and explain your time complexity here.
*/
public class Main{
    ///You can declare static variables if needed.

    public static int solve(int n, int[] a, int minC, int maxC) {
        ///Return number of possible battle plan.

        return 0;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int minC = scanner.nextInt();
        int maxC = scanner.nextInt();
        int[] a = new int[n]; 
        
        for (int i = 0; i < n; i++) a[i] = scanner.nextInt(); 
        System.out.println(solve(n, a, minC, maxC));
    }
}
