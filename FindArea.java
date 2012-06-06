import java.util.Random;
import java.util.Scanner;

public class FindArea {
	static final int MAX_VAL = 999;

	// Simple class to represent a rectangular area
	static class Rectangle {
		public int top; // top row
		public int left; // leftmost column
		public int bottom; // bottom row (disclusive)
		public int right; // rightmost column (disclusive)
	}

	// Fill "vals" with random integers in the range [-MAX_VAL, MAX_VAL], using
	// "seed" to prime the random number generation.
	static void makeTest(int vals[][], int seed) {
		Random rnd = new Random(seed);

		for (int row = 0; row < vals.length; row++)
			for (int col = 0; col < vals[row].length; col++)
				vals[row][col] = rnd.nextInt(2 * MAX_VAL + 1) - MAX_VAL;
	}

	// Input two values for dimension and seed, create a 2-D array with the
	// specified dimensions, fill it randomly, and then solve for highest-total
	// rectangle, printint out the result.
	public static void main(String[] args) {
		int dim, seed;
		Rectangle bestArea = new Rectangle();
		double maxBright;
		Scanner in = new Scanner(System.in);
		int[][] vals;

		dim = in.nextInt();
		seed = in.nextInt();

		makeTest(vals = new int[dim][dim], seed);

		maxBright = findMaxBright(vals, bestArea);
		System.out.printf("Max brightness is %f for (%d, %d, %d, %d)\n",
		 maxBright, bestArea.top, bestArea.left, bestArea.bottom,
		 bestArea.right);
	}

	// Traverse vals to find the rectangular area within it having the highest
	// total brightness. This is currently O(n^3), with n the number of
	// elements.
	static double findMaxBright(int[][] vals, Rectangle area) {

		int row, col, top, left, bottom, right;
		double tot, rtn = Double.MIN_VALUE;
		double[][] precomp = new double[vals.length][vals.length];

		for (row = 0; row < vals.length; row++) {
			tot = 0;
			for (col = 0; col < vals[row].length; col++) {
				for (int x = row; x >= 0; x--)
					tot += vals[x][col];
				precomp[row][col] = tot;
			}
		}

		for (top = 0; top < vals.length; top++)
			for (left = 0; left < vals[top].length; left++)
				for (bottom = top + 1; bottom <= vals.length; bottom++)
					for (right = left + 1; right <= vals[0].length; right++) {
						tot = precomp[bottom - 1][right - 1];
						if (left > 0)
							tot -= precomp[bottom - 1][left - 1];
						if (top > 0)
							tot -= precomp[top - 1][right - 1];
						if (left > 0 && top > 0)
							tot += precomp[top - 1][left - 1];
						if (tot > rtn) {
							rtn = tot;
							area.top = top;
							area.bottom = bottom;
							area.left = left;
							area.right = right;
						}
					}

		return rtn;
	}
}
