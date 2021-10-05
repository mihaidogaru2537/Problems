import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.exit;
/*
	This is a solution for the most popular K scores problem.
	Author: Dogaru Mihai-Sorin
 */
public class PopularScore {

	// Returns a list with the first K numbers with greatest frequency value.
	public static List<Integer> solve(int n, int k, List<Integer> numbers) {

		// The list with solution.
		List<Integer> res = new ArrayList<>();

		// Insert each number in a hashmap, value represents its frequency.
		HashMap<Integer, Integer> myH = new HashMap<>();

		for (Integer x : numbers)
			myH.merge(x, 1, Integer::sum);

		// Insert each number in a maxHeap
		PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(
				myH.entrySet().size(),
				(o1, o2) -> Integer.compare(o2.getValue(), o1.getValue())
		);

		Iterator<Map.Entry<Integer, Integer>> iter = myH.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, Integer> elem = iter.next();
			maxHeap.add(elem);

		}

		// Remove all keys which have this highest value.
		// And append the key to the solution.
		for (int i = 0; i < k; i++)
			try {
				res.add(maxHeap.remove().getKey());
			} catch (NoSuchElementException e) {
				// If the heap gets empty
				i = k;
			}

		return res;
	}

	public static void main(String[] args) {

		int n = 0;
		int k = 0;
		List<Integer> myNumbers = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Read N from keyboard
		try {
			n = Integer.parseInt(reader.readLine());

			if (n <= 0)
				throw(new IllegalArgumentException());

		} catch (IOException e) {
			System.out.println("There was an IO exception.");
			exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Please insert N as a number on a single line.");
			exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println("Please insert N >= 1");
			exit(1);
		}

		// Read K from keyboard
		try {
			k = Integer.parseInt(reader.readLine());
			if (k <= 0)
				throw(new IllegalArgumentException());
		} catch (IOException e) {
			System.out.println("Something went wrong with IO");
			exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Please insert K as a number on a single line.");
			exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println("Please insert K >= 1");
			exit(1);
		}

		// Read the array from keyboard
		try {
			myNumbers = Stream.of(reader.readLine().replaceAll("\\s+$", "").split(" "))
					.map(Integer::parseInt).collect(Collectors.toList());

			if (myNumbers.size() != n)
				throw (new IllegalArgumentException());

		} catch (IOException e) {
			System.out.println("Something went wrong with IO.");
			exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Please insert array as numbers separated by space on a single line.");
			exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println("You did not enter as many numbers as you said you would.");
			exit(1);
		}

		// Print the solution
		for(Integer element : solve(n, k, myNumbers)) {
			System.out.print(element + " ");
		}

		System.out.println();
	}
}
