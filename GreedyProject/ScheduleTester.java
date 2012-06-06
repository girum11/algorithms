package edu.calpoly.csc349.Schedule;

import java.util.Random;

public class ScheduleTester extends GreedyScheduler {

	public TimeRange[] result;
	public TimeRange toCover;
	public Random rn = new Random();

	public static void main(String[] args) {
		new ScheduleTester();
	}

	public ScheduleTester() {
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
		test8();
		test9();
	}

	/*
	 * First test -- Tests empty case. Passes in empty TimeRange[] array and
	 * tests to see if it's null. HOWEVER, the professor's version of
	 * GreedyScheduler throws an exception here.
	 */
	public void test1() {
		TimeRange[] employees = new TimeRange[10];

		System.out.println("First test: ");
		result = makeSchedule(new TimeRange(0, 4), employees);
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		System.out.println("");
	}

	/*
	 * Second test -- A simple test. Just a straight-up test case from the spec
	 * of the Greedy lab. I also modify the array, and ran it again to ensure
	 * that result doesn't actually change anything. It doesn't, everything's a
	 * shallow copy.
	 */
	public void test2() {
		TimeRange[] employees2 = { new TimeRange(5, 25), new TimeRange(40, 55),
		 new TimeRange(1, 20), new TimeRange(25, 45), new TimeRange(20, 35),
		 new TimeRange(33, 50), new TimeRange(35, 40), new TimeRange(25, 40) };

		System.out.println("Second test: ");
		toCover = new TimeRange(5, 55);
		result = makeSchedule(toCover, employees2);
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		employees2[2] = new TimeRange(1, 40);
		result = makeSchedule(toCover, employees2);
		System.out.println("\nModified and ran a second time, got:");
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		System.out.println("");

	}

	/*
	 * Third test -- This is my million employees case. My algorithm handles the
	 * entire thing in about 1 second. I usually get an optimal size of ~520,
	 * which is about usually what the professor's version gets. It's approximate
	 * because the employees are randomly generated, with the end time always
	 * greater than the start time.
	 */
	public void test3() {
		TimeRange[] employees3 = new TimeRange[1000000];

		System.out.println("Third test: ");
		toCover = new TimeRange(5, 500000);
		for (int i = 0; i < employees3.length; i++) {
			int startTime = rn.nextInt(1000000);
			int endTime = startTime + rn.nextInt(1000);
			employees3[i] = new TimeRange(startTime, endTime);
		}
		result = makeSchedule(toCover, employees3);
		if (result == null)
			System.out.println("Was null.\n");
		else {
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
			System.out.println("Optimal size is " + result.length + "\n");
		}
	}

	// Fourth test -- Null case. Here I make the algorithm return null by sending
	// it a set of employees with a discontinuity at time == 20.
	public void test4() {
		TimeRange[] employees4 = { new TimeRange(0, 5), new TimeRange(0, 12),
		 new TimeRange(10, 20), new TimeRange(21, 50) };

		System.out.println("Fourth test: ");
		toCover = new TimeRange(0, 50);
		result = makeSchedule(toCover, employees4);
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		System.out.println("");
	}

	// Fifth test -- Null case. The employees can't cover time == 1.
	public void test5() {
		TimeRange[] employees5 = { new TimeRange(2, 4), new TimeRange(4, 10) };

		System.out.println("Fifth test: ");
		toCover = new TimeRange(1, 10);
		result = makeSchedule(toCover, employees5);
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		System.out.println("");
	}

	// Sixth test -- Null case. The employees can't cover time == 8, 9, 10.
	public void test6() {
		TimeRange[] employees6 = { new TimeRange(1, 4), new TimeRange(4, 8) };

		System.out.println("Sixth test: ");
		toCover = new TimeRange(1, 10);
		result = makeSchedule(toCover, employees6);
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		System.out.println("");
	}

	/*
	 * Seventh test -- Null case. Trying to break the code by having one employee
	 * that shouldn't ever be factored since he's "inside" another employee's
	 * TimeRange.
	 */
	public void test7() {
		TimeRange[] employees7 = { new TimeRange(0, 4), new TimeRange(2, 6),
		 new TimeRange(4, 5) };

		System.out.println("Seventh test: ");
		toCover = new TimeRange(1, 10);
		result = makeSchedule(toCover, employees7);
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		System.out.println("");
	}

	/*
	 * Eighth test -- Duplicate case. Seeing if duplicates hurts the algorithm at
	 * all. Guess not.
	 */
	public void test8() {
		TimeRange[] employees8 = { new TimeRange(1, 3), new TimeRange(1, 3),
		 new TimeRange(1, 3), new TimeRange(3, 9), new TimeRange(3, 9),
		 new TimeRange(3, 9) };

		System.out.println("Eighth test: ");
		toCover = new TimeRange(1, 10);
		result = makeSchedule(toCover, employees8);
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		System.out.println("");
	}

	// Ninth test -- Still trying to break my code... =\
	public void test9() {
		TimeRange[] employees9 = { new TimeRange(2, 4), new TimeRange(4, 8),
		 new TimeRange(8, 11) };

		System.out.println("Ninth test: ");
		toCover = new TimeRange(1, 10);
		result = makeSchedule(toCover, employees9);
		if (result == null)
			System.out.println("Was null.\n");
		else
			for (TimeRange x : result)
				System.out.println("From " + x.getStart() + " to " + x.getEnd());
		System.out.println("");
	}

}
