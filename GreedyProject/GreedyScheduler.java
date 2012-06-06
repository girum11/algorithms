package edu.calpoly.csc349.Schedule;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;

public class GreedyScheduler implements Scheduler {

	public TimeRange[] makeSchedule(TimeRange toCover, TimeRange[] employees) {

		if (employees.length == 0)
			return null;
		if (employees[0] == null)
			return null;

		TimeRange[] copy = new TimeRange[employees.length];

		for (int i = 0; i < employees.length; i++) {
			copy[i] = employees[i];
		}

		Arrays.sort(copy, new Comparator<TimeRange>() {
			public int compare(TimeRange t1, TimeRange t2) {
				return t1.getStart() - t2.getStart();
			}
		});

		int lastEnd = toCover.getStart();
		int index = 0;
		TimeRange max = copy[0];
		ArrayList<TimeRange> arrayList = new ArrayList<TimeRange>();

		do {
			if (index >= copy.length || copy[index].getStart() > max.getEnd())
				return null;
			if (index < copy.length && copy[index].getStart() > lastEnd)
				return null;

			while (index < copy.length && copy[index].getStart() <= lastEnd) {
				if (copy[index].getEnd() > max.getEnd()) {
					max = copy[index];
				}
				index++;
			}

			if (max.getStart() > lastEnd)
				return null;

			arrayList.add(max);
			lastEnd = max.getEnd();
		} while (max.getEnd() < toCover.getEnd());

		TimeRange[] schedule = new TimeRange[arrayList.size()];
		arrayList.toArray(schedule);

		if (schedule[0].getStart() > toCover.getStart())
			return null;
		if (schedule[schedule.length - 1].getEnd() < toCover.getEnd())
			return null;

		return schedule;

	}
}
