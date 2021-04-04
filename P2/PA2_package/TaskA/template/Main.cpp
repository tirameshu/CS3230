/*
Name:
Describe your algorithm and explain your time complexity here:

*/

#include <iostream>
#include <vector> 

using namespace std;

int main() {
	int n;
	cin >> n;
	vector<int> a(n), b(n);
	for (int i = 0; i < n; i++) {
		cin >> a[i];
	}
	for (int i = 0; i < n; i++) {
		cin >> b[i];
	}

	long long answer = 0;
	///write your code here
	
	cout << answer << endl;

	return 0;
}
