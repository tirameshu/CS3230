#include <cstdio>

/*
ID:
Describe your algorithm and explain your time complexity here.
*/

///You can declare global variables if needed.

int solve(int n, std::vector<int> a, int minC, int maxC) {
    ///Return number of possible battle plans.
    return 0;
}
int main() {
    int n, minC, maxC;
    scanf("%d%d%d", &n, &minC, &maxC);

    std::vector<int> a(n);
    for(int i = 0; i < n; i++) scanf("%d", &a[i]);

    printf("%d\n", solve(n, a, minC, maxC));
}
