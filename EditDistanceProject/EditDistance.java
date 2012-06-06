// Girum Ibssa
// Edit Distance Project

package edu.calpoly.csc349;

import java.util.*;

public class EditDistance {
	
	public static final int COPY_COST = 1;
	public static final int INS_COST = 6;
	public static final int DEL_COST = 3;
	public static final int REPL_COST = 7;
	public static final int PERM2_COST = 2;
	public static final int PERM3_COST = 3;
	public static final int CHBL_COST = 1;
	public static final int CHCS_COST = 1;
	
	enum Operation {
		Copy, Ins1, Ins2, Ins3, Del1, Del2, Del3,
		 Repl, Prm2, Prm3, ChBl, ChCs;
		
		public int count;
		
		Operation() {
			count = 0;
		}	
	
	};

   private static class SubSln {
   	public int cost;
   	public EnumSet<Operation> usedToGetHere;
   	public int from;
   	public int to;
   	
   	SubSln() {
   		this.cost = Integer.MAX_VALUE;
   		usedToGetHere = EnumSet.noneOf(Operation.class);
   	}
   }
   
	public static SubSln[][] slns;
	public static int row, col;
	 
	public static void main(String[] args) {
		String s1, s2;
		Scanner myScan = new Scanner(System.in);
		
		List<String> list = new LinkedList<String>();
		ListIterator<String> iter;
	
		s1 = myScan.nextLine();
		s2 = myScan.nextLine();
		
		slns = new SubSln[s1.length()+1][s2.length()+1];
		
		for (row = 0; row <= s1.length(); row++) {
			for (col = 0; col <= s2.length(); col++) {
				slns[row][col] = new SubSln();
				baseCase();
				copy(s1, s2);
				ins1();
				ins2();
				ins3();
				del1();
				del2();
				del3();
				repl();
				prm2(s1, s2);
				prm3(s1, s2);
				chBl(s1, s2);
				chCs(s1, s2);
			}
		}
		
		printCosts(s1, s2);
		
		row = slns.length-1;
		col = slns[0].length-1;
		
		System.out.printf("Edits are:\n");
		
		printEdits(list, s1, s2);
		iter = list.listIterator(list.size());
		
		while(iter.hasPrevious()) {
			System.out.printf("%s", iter.previous());
		}
		
		outputTable(s1, s2);
		
	}
	
	public static void takeBackCounts(int row, int col) {
		for (Operation oper: slns[row][col].usedToGetHere)
			if (slns[row][col].usedToGetHere.contains(oper)) {
				slns[row][col].usedToGetHere.remove(oper);
				oper.count--;
			}
	}
	
	public static void baseCase() {
		if (row == 0 && col == 0) {
			slns[row][col].cost = 0;
		}
	}
	
	public static void copy(String s1, String s2) {
		if (row-1 >= 0 && col-1 >= 0 && 
		 s1.charAt(row-1) == s2.charAt(col-1)) {
			if (slns[row-1][col-1].cost + COPY_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row-1][col-1].cost + COPY_COST;
				slns[row][col].usedToGetHere.add(Operation.Copy);
				Operation.Copy.count++;
			}
			else if (slns[row-1][col-1].cost + COPY_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row-1][col-1].cost + COPY_COST;
				slns[row][col].usedToGetHere.add(Operation.Copy);
				Operation.Copy.count++;
			}
		}
	}
	
	public static void ins1() {
		if (col-1 >= 0) {
			if (slns[row][col-1].cost + INS_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row][col-1].cost + INS_COST;
				slns[row][col].usedToGetHere.add(Operation.Ins1);
				Operation.Ins1.count++;
			}
			else if (slns[row][col-1].cost + INS_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row][col-1].cost + INS_COST;
				slns[row][col].usedToGetHere.add(Operation.Ins1);
				Operation.Ins1.count++;
			}
		}
	}
	
	public static void ins2() {
		if (col-2 >= 0) {
			if (slns[row][col-2].cost + INS_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row][col-2].cost + INS_COST;
				slns[row][col].usedToGetHere.add(Operation.Ins2);
				Operation.Ins2.count++;
			}
			else if (slns[row][col-2].cost + INS_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row][col-2].cost + INS_COST;
				slns[row][col].usedToGetHere.add(Operation.Ins2);
				Operation.Ins2.count++;
			}
		}
	}
	
	public static void ins3() {
		if (col-3 >= 0) {
			if (slns[row][col-3].cost + INS_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row][col-3].cost + INS_COST;
				slns[row][col].usedToGetHere.add(Operation.Ins3);
				Operation.Ins3.count++;
			}
			else if (slns[row][col-3].cost + INS_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row][col-3].cost + INS_COST;
				slns[row][col].usedToGetHere.add(Operation.Ins3);
				Operation.Ins3.count++;
			}
		}
	}
	
	public static void del1() {
		if (row-1 >= 0) {
			if (slns[row-1][col].cost + DEL_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row-1][col].cost + DEL_COST;
				slns[row][col].usedToGetHere.add(Operation.Del1);
				Operation.Del1.count++;
			}
			else if (slns[row-1][col].cost + DEL_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row-1][col].cost + DEL_COST;
				slns[row][col].usedToGetHere.add(Operation.Del1);
				Operation.Del1.count++;
			}
		}
	}
	
	public static void del2() {
		if (row-2 >= 0) {
			if (slns[row-2][col].cost + DEL_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row-2][col].cost + DEL_COST;
				slns[row][col].usedToGetHere.add(Operation.Del2);
				Operation.Del2.count++;
			}
			else if (slns[row-2][col].cost + DEL_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row-2][col].cost + DEL_COST;
				slns[row][col].usedToGetHere.add(Operation.Del2);
				Operation.Del2.count++;
			}
		}
	}
	
	public static void del3() {
		if (row-3 >= 0) {
			if (slns[row-3][col].cost + DEL_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row-3][col].cost + DEL_COST;
				slns[row][col].usedToGetHere.add(Operation.Del3);
				Operation.Del3.count++;
			}
			else if (slns[row-3][col].cost + DEL_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row-3][col].cost + DEL_COST;
				slns[row][col].usedToGetHere.add(Operation.Del3);
				Operation.Del3.count++;
			}
		}
	}
	
	public static void repl() {
		if (row-1 >= 0 && col-1 >= 0) {
			if (slns[row-1][col-1].cost + REPL_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row-1][col-1].cost + REPL_COST;
				slns[row][col].usedToGetHere.add(Operation.Repl);
				Operation.Repl.count++;
			}
			else if (slns[row-1][col-1].cost + REPL_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row-1][col-1].cost + REPL_COST;
				slns[row][col].usedToGetHere.add(Operation.Repl);
				Operation.Repl.count++;
			}
		}
	}
	
	public static void prm2(String s1, String s2) {
		if (row-2 >= 0 && col-2 >= 0 &&
		 ((s1.charAt(row-1) == s2.charAt(col-1) &&
		   s1.charAt(row-2) == s2.charAt(col-2)) ||
		  (s1.charAt(row-1) == s2.charAt(col-2) &&
		   s1.charAt(row-2) == s2.charAt(col-1)))) {
			if (slns[row-2][col-2].cost + PERM2_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row-2][col-2].cost + PERM2_COST;
				slns[row][col].usedToGetHere.add(Operation.Prm2);
				Operation.Prm2.count++;
			}
			else if (slns[row-2][col-2].cost + PERM2_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row-2][col-2].cost + PERM2_COST;
				slns[row][col].usedToGetHere.add(Operation.Prm2);
				Operation.Prm2.count++;
			}
		}
	}
	
	public static void prm3(String s1, String s2) {
		if (row-3 >= 0 && col-3 >= 0 &&
		 ((s1.charAt(row-1) == s2.charAt(col-1) &&
		   s1.charAt(row-2) == s2.charAt(col-2) &&
		   s1.charAt(row-3) == s2.charAt(col-3)) ||
		  (s1.charAt(row-1) == s2.charAt(col-1) &&
		   s1.charAt(row-2) == s2.charAt(col-3) &&
		   s1.charAt(row-3) == s2.charAt(col-2)) ||
		  (s1.charAt(row-1) == s2.charAt(col-2) &&
		   s1.charAt(row-2) == s2.charAt(col-1) &&
		   s1.charAt(row-3) == s2.charAt(col-3)) ||
		  (s1.charAt(row-1) == s2.charAt(col-2) &&
		   s1.charAt(row-2) == s2.charAt(col-3) &&
		   s1.charAt(row-3) == s2.charAt(col-1)) ||
		  (s1.charAt(row-1) == s2.charAt(col-3) &&
			s1.charAt(row-2) == s2.charAt(col-1) &&
			s1.charAt(row-3) == s2.charAt(col-2)) ||
		  (s1.charAt(row-1) == s2.charAt(col-3) &&
		   s1.charAt(row-2) == s2.charAt(col-2) &&
		   s1.charAt(row-3) == s2.charAt(col-1)))) {
			if (slns[row-3][col-3].cost + PERM3_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row-3][col-3].cost + PERM3_COST;
				slns[row][col].usedToGetHere.add(Operation.Prm3);
				Operation.Prm3.count++;
			}
			else if (slns[row-3][col-3].cost + PERM3_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row-3][col-3].cost + PERM3_COST;
				slns[row][col].usedToGetHere.add(Operation.Prm3);
				Operation.Prm3.count++;
			}
		}
	}
	
	public static void chBl(String s1, String s2) {
		int i, j, minI, minJ, minCost;
		
		if (row-1 >= 0 && col-1 >= 0 && 
		 s1.charAt(row-1) == ' ' && s2.charAt(col-1) == ' ') {
			minI = 0;
			minJ = 0;
			minCost = slns[row][col].cost;
				
			for (i = 0; row-1-i >= 0 && s1.charAt(row-1-i) == ' '; i++) {
				for (j = 0; col-1-j >= 0 && s2.charAt(col-1-j) == ' '; j++) {
					if (i != j && row-i >= 0 && col-j >=0 && 
					 slns[row-1-i][col-1-j].cost + CHBL_COST <= minCost) {
						if (row-1-i >= 0 && col-1-j >= 0 &&
						 slns[row-1-i][col-1-j].cost + CHBL_COST < minCost) {
							minI = i+1;
							minJ = j+1;
							minCost = slns[row-i-1][col-j-1].cost + CHBL_COST;
						}
						else if (row-1-i >= 0 && col-1-j >= 0 &&
						 minCost == slns[row][col].cost) {
							minI = i+1;
							minJ = j+1;
						}
					}
				}
			}
			if ((minI>0 || minJ>0) && row-minI >= 0 && col-minJ >= 0 &&
			 slns[row-minI][col-minJ].cost + CHBL_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].from = minI;
				slns[row][col].to = minJ;
				slns[row][col].cost = slns[row-minI][col-minJ].cost + CHBL_COST;
				slns[row][col].usedToGetHere.add(Operation.ChBl);
				Operation.ChBl.count++;
			}
			else if ((minI>0 || minJ>0) && row-minI >= 0 && col-minJ >= 0 &&
			 slns[row-minI][col-minJ].cost + CHBL_COST == slns[row][col].cost) {
				slns[row][col].from = minI;
				slns[row][col].to = minJ;
				slns[row][col].cost = slns[row-minI][col-minJ].cost + CHBL_COST;
				slns[row][col].usedToGetHere.add(Operation.ChBl);
				Operation.ChBl.count++;
			}
		}
	}
	
	public static void chCs(String s1, String s2) {
		if (row-1 >= 0 && col-1 >= 0 &&
		 Character.toString(s1.charAt(row-1)).
		 compareToIgnoreCase(Character.toString(s2.charAt(col-1))) == 0 &&
		 s1.charAt(row-1) != s2.charAt(col-1)) {
			if (slns[row-1][col-1].cost + CHCS_COST < slns[row][col].cost) {
				takeBackCounts(row, col);
				slns[row][col].cost = slns[row-1][col-1].cost + CHCS_COST;
				slns[row][col].usedToGetHere.add(Operation.ChCs);
				Operation.ChCs.count++;	 
			}
			else if (slns[row-1][col-1].cost + CHCS_COST == slns[row][col].cost) {
				slns[row][col].cost = slns[row-1][col-1].cost + CHCS_COST;
				slns[row][col].usedToGetHere.add(Operation.ChCs);
				Operation.ChCs.count++;	 
			}
		}
	}
	
	
	public static void printCosts(String s1, String s2) {
		System.out.printf("Best cost is: %d\n\n", slns[s1.length()][s2.length()].cost);
		
		System.out.printf("Copy used %d times.\n" +
		 "Ins1 used %d times.\n" +
		 "Ins2 used %d times.\n" +
		 "Ins3 used %d times.\n" +
		 "Del1 used %d times.\n" +
		 "Del2 used %d times.\n" +
		 "Del3 used %d times.\n" +
		 "Repl used %d times.\n" +
		 "Prm2 used %d times.\n" +
		 "Prm3 used %d times.\n" +
		 "ChBl used %d times.\n" +
		 "ChCs used %d times.\n\n",
		 Operation.Copy.count, Operation.Ins1.count, Operation.Ins2.count,
		 Operation.Ins3.count, Operation.Del1.count, Operation.Del2.count,
		 Operation.Del3.count, Operation.Repl.count, Operation.Prm2.count,
		 Operation.Prm3.count, Operation.ChBl.count, Operation.ChCs.count);
	}
	
	public static void printEdits(List<String> list, String s1, String s2) {
		while (row > 0 || col > 0) {
			if (slns[row][col].usedToGetHere.contains(Operation.Copy)) {
				list.add("Copy '" + s1.charAt(row-1) + "' unchanged\n");
				row--;
				col--;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Ins1)) {
				list.add("Insert '" + s2.charAt(col-1) + "'\n");
				col--;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Ins2)) {
				list.add("Insert '" + s2.substring(col-2, col) + "'\n");
				col -= 2;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Ins3)) {
				list.add("Insert '" + s2.substring(col-3, col) + "'\n");
				col -= 3;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Del1)) {
				list.add("Delete 1 chars\n");
				row--;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Del2)) {
				list.add("Delete 2 chars\n");
				row -= 2;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Del3)) {
				list.add("Delete 3 chars\n");
				row -= 3;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Repl)) {
				list.add("Replace '" + s1.charAt(row-1) + "' with '" + 
				 s2.charAt(col-1) + "'\n");
				row--;
				col--;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Prm2)) {
				list.add("Swap '" + s1.substring(row-2, row) + "' to '" +
				 s2.substring(col-2, col) + "'\n");
				row -= 2;
				col -= 2;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.Prm3)) {
				list.add("Swap '" + s1.substring(row-3, row) + "' to '" +
						 s2.substring(col-3, col) + "'\n");
				row -= 3;
				col -= 3;
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.ChBl)) {
				list.add("Convert " + (slns[row][col].from) + " blanks to " + 
				 (slns[row][col].to) + " blanks\n");
				int tempI = row, tempJ = col;
				row -= (slns[tempI][tempJ].from);
				col -= (slns[tempI][tempJ].to);
			}
			else if (slns[row][col].usedToGetHere.contains(Operation.ChCs)) {
				list.add("Convert case from '" + s1.charAt(row-1) + "' to '" +
				 s2.charAt(col-1) + "'\n");
				row--;
				col--;
			}
		}
	}
	
	public static void outputTable(String s1, String s2) {
		int i, j;
		
		System.out.printf("\n                        ");
		
		i = row-1;
		j = col-1;
		for (j=1; j <= s2.length(); j++) {
			System.out.printf("(%c%-18s", s2.charAt(j-1), ")");
		}
		
		System.out.printf("\n\n");
		
		for (i = 0; i < slns.length; i++) {
			if (i == 0)
				System.out.printf("    ");
			else
				System.out.printf("(%c) ", s1.charAt(i-1));
			
			for (j = 0; j < slns[i].length; j++) {
				System.out.printf("%-20d", slns[i][j].cost);
			}
			System.out.printf("\n    ");
			for (j = 0; j < slns[i].length; j++) {
				String outputString = "";
				
				for (Operation oper: slns[i][j].usedToGetHere) {
					outputString = outputString + oper.toString() + ",";
				}
				if (outputString.length() > 0)
					outputString = outputString.substring(0, outputString.length()-1);
				System.out.printf("%-20s", outputString);
			}
			System.out.printf("\n\n");
		}
	}
	
}
