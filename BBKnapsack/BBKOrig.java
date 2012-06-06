package edu.calpoly.csc349.Knapsack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class BBKnapsack {

	public static double totalWeight;
	public static int finalWeight, maxValue, steps, cuts, newSlns, boundValue;
   public static LinkedList<Node> decisionTree = new LinkedList<Node>();
   public static LinkedList<Node> dtCopy = new LinkedList<Node>();
   public static LinkedList<Item> solution = new LinkedList<Item>();
   
   private static class Item {
      public double value;
      public double weight;

      public Item(double weight, double value) {
         this.weight = weight;
         this.value = value;
      }
   }
   
   private static class Node {
   	public double nodeWeight;
   	public double nodeValue;
   	public boolean wentLeft;
   	public boolean wentRight;
   	
   	public Node(double nodeWeight, double nodeValue, boolean wentLeft, boolean wentRight) {
   		this.nodeWeight = nodeWeight;
   		this.nodeValue = nodeValue;
   		this.wentLeft = wentLeft;
   		this.wentRight = wentRight;
   	}
   }
   
   
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
      Item[] items;
      int j = 0;
      
      System.out.print("Enter total weight, num items, and item wgt/val: ");
      totalWeight = in.nextInt();
      items = new Item[in.nextInt()];
      
      for (int x = 0; x < items.length; x++)      
      	items[x] = new Item(in.nextInt(), in.nextInt());

      Arrays.sort(items, new Comparator<Item>() {
      	public int compare(Item i1, Item i2) {
      		if ((i1.value/i1.weight) > i2.value/i2.weight)
      			return -1;
      		else if ((i1.value/i1.weight) < i2.value/i2.weight)
      			return 1;
      		else
      			return 0;
      	}
      });       
      
      decisionTree.push(new Node(0, 0, false, false));
      BBMax(items);
       
		System.out.printf("\nAnswer after %d steps, %d cuts, %d new slns: ",
		 steps, cuts, newSlns);
		
		for (int i = 0; i < dtCopy.size()-1; i++) {
			if ((dtCopy.get(i+1).nodeWeight - dtCopy.get(i).nodeWeight)
			 == items[i].weight) {
				solution.add(items[i]);
			}
		}
		
		for (Item x: solution) {
			System.out.printf("w %d v %d  ", (int)x.weight, (int)x.value);
		}
		
		System.out.printf("\nTotal weight: %d  Total value: %d\n",
		 finalWeight, maxValue);
		
		System.out.printf("\n");
	}
	
	public static void BBMax(Item[] items) {
      int weight = 0, value = 0, i = 0;

      while (!decisionTree.isEmpty()) {	
      	while (i < items.length) {
      		if (decisionTree.isEmpty()) {
      			break;
      		}
      		else if (!decisionTree.peek().wentLeft && 
      		 !decisionTree.peek().wentRight &&
      		 boundFunction(items, i, weight, value)) {
            	if (!decisionTree.get(1).wentRight) {
      				weight -= items[i-1].weight;
      				value -= items[i-1].value;
            	}
            	
      			i--;
      			decisionTree.pop();
      			cuts++;
      		}      		
      		else if (!decisionTree.peek().wentLeft && 
      		 weight + items[i].weight <= totalWeight) {
      			
      			decisionTree.peek().wentLeft = true;
      			weight += items[i].weight;
      			value += items[i].value; 
      			decisionTree.push(new Node(weight, value, false, false));
      			checkMax(value, weight, i, items);
      			i++;
      		}
      		else if ((decisionTree.peek().wentLeft && !decisionTree.peek().wentRight) ||
      		 (!decisionTree.peek().wentRight && weight + items[i].weight > totalWeight)) {
      			
      			decisionTree.peek().wentRight = true;
      			decisionTree.push(new Node(weight, value, false, false));
      			checkMax(value, weight, i, items);
      			i++;
      			steps++;
      		}
      		else {
            	if (decisionTree.size() > 1 && !decisionTree.get(1).wentRight) {
      				weight -= items[i-1].weight;
      				value -= items[i-1].value;
            	}
      			i--;
      			decisionTree.pop();
      		}
      	}
      	if (decisionTree.size() > 1 && !decisionTree.get(1).wentRight) {
				weight -= items[i-1].weight;
				value -= items[i-1].value;
      	}
      	if (!decisionTree.isEmpty()) {
	      	i--;
	      	decisionTree.pop();
      	}
      }
	}
	
	public static void checkMax(int value, int weight, int i, Item[] items) {
		if (value > maxValue && i == items.length-1) {
			maxValue = value;
			finalWeight = weight;
			newSlns++;
			dtCopy.clear();
			for (Node x: decisionTree) {
				dtCopy.addFirst(x);
			}
		}
	}
	
	
	public static boolean boundFunction(Item[] items, int i, double weight, double value) {
		for (int j = i; j < items.length; j++) {
			if (weight == totalWeight) {
				break;
			}
			else if (j < items.length && (weight + items[j].weight) <= totalWeight) {
				value += items[j].value;
				weight += items[j].weight;
			}
			else if (j < items.length && (weight + items[j].weight) > totalWeight) {
				value += (int)(items[j].value * (((totalWeight - weight))/(items[j].weight)));
				weight = totalWeight;
				break;
			}
			else
				break;
		}
		
		boundValue = (int)value;
		
		if (boundValue > maxValue)
			return false;
		else
			return true;

	}
	
}
