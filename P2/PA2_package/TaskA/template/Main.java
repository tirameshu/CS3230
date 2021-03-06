/*
Name: Wang Xinman
Describe your algorithm and explain your time complexity here:

Constraints:
- space complexity: O(N)
- time complexity: O(N^2)

- Let position be the index of the item in S (sorted)
- Let rank be the index of the item in a_1, ..., a_i
- S has n/2 items

- At item i:
	- prefix(i) = [1, 2, ..., i]
	- rank(i) must be in the range: [i, i + len(prefix(i)]
	- of all options in above mentioned range, get the highest
	-- if highest already obtained, get next highest available
*/

import java.util.*;

class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		Integer[] a = new Integer[N];
		int[] b = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = sc.nextInt();
		}
		for (int i = 0; i < N; i++) {
			b[i] = sc.nextInt();
		}
		long answer = 0;

		HashMap<Integer, Integer> mapAToB = new HashMap<>();
		HashMap<Integer, Integer> valToAvail = new HashMap<>(); // value -> no. of available items w this val

		// map for a_i to b_i
		for (int i = 0; i < N; i++) {
			mapAToB.put(a[i], b[i]); // a[i] distinct
			if (valToAvail.containsKey(b[i])) {
				int num = valToAvail.get(b[i]);
				num += 1;
				valToAvail.replace(b[i], num);
			} else {
				valToAvail.put(b[i], 1);
			}
		}

		// sort a by value of item to A
		Arrays.sort(a);

		for (int i = 1; i <= N/2; i++) {

//			System.out.printf("==== At iteration %d ====\n", i);

			// 1-base because len(prefix(i)) starts at 1
			int lowerI = i;
			int upperI = i+i;

			// get subarray [i, i+i] of a, exclusive of upperI
			// change to 0-base
			Integer[] options = Arrays.copyOfRange(a, 0, upperI-1); // O(N)

			// sort options in desc order of value to B
			Arrays.sort(options, (a1, a2) -> mapAToB.get(a2) - mapAToB.get(a1));

			int toAdd = 0;

			for (int opA: options) {
				int valB = mapAToB.get(opA);
//				System.out.printf("option: %d, valB: %d\n", opA, valB);

				// alr in desc order of value to B
				boolean avail = valToAvail.get(valB) > 0;
				if (avail) {
					// not yet obtained
					toAdd = valB;
					int remaining = valToAvail.get(valB);
					remaining -= 1;
					valToAvail.replace(valB, remaining);
					break;
				}
			}

//			System.out.printf("To add: %d\n", toAdd);

			answer += toAdd;
		}

		System.out.println(answer);
	}
}