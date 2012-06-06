package edu.calpoly.csc349.Knapsack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class BBKnapsack {

   public static double totalWeight;
   public static int finalWeight, maxValue, steps, cuts, newSlns, boundValue;
   public static LinkedList<Node> decisionTree = new LinkedList<Node>();
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
      public double weight;
      public double value;
      public boolean push;
      public boolean skip;

      public Node(double weight, double value, boolean push, boolean skip) {
         this.weight = weight;
         this.value = value;
         this.push = push;
         this.skip = skip;
      }
   }

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      Node[] items;

      System.out.print("Enter total weight, num items, and item wgt/val: ");
      totalWeight = in.nextInt();
      items = new Node[in.nextInt()];

      for (int x = 0; x < items.length; x++)
         items[x] = new Node(in.nextInt(), in.nextInt(), false, false);

      Arrays.sort(items, new Comparator<Node>() {
         public int compare(Node i1, Node i2) {
            if ((i1.value / i1.weight) > i2.value / i2.weight)
               return -1;
            else if ((i1.value / i1.weight) < i2.value / i2.weight)
               return 1;
            else
               return 0;
         }
      });

      BBMax(items);

      System.out.printf("\nAnswer after %d steps, %d cuts, %d new slns: ",
       steps, cuts, newSlns);

      for (Item x : solution) {
         System.out.printf("w %d v %d  ", (int) x.weight, (int) x.value);
      }

      System.out.printf("\nTotal weight: %d  Total value: %d\n", finalWeight,
       maxValue);

      System.out.printf("\n");
   }

   public static void BBMax(Node[] items) {
      int weight = 0, value = 0, i = 0;

      while (i >= 0) {
         if (i == items.length) {
            if (value > maxValue) {
               maxValue = value;
               finalWeight = weight;
               newSlns++;
               solution.clear();
               for (Node x : decisionTree) {
                  solution.addFirst(new Item(x.weight, x.value));
               }
            }
            i--;
         }
         else if (!items[i].push && !items[i].skip
          && boundFunction(items, i, weight, value)) {
            i--;
            if (!decisionTree.isEmpty() && 
             decisionTree.peek().weight == items[i].weight)
               decisionTree.pop();
            cuts++;
         }
         else if (!items[i].push && !items[i].skip &&
          weight + items[i].weight <= totalWeight) {
            items[i].push = true;
            weight += items[i].weight;
            value += items[i].value;
            decisionTree.push(items[i]);
            i++;
         }
         else if (!items[i].push && !items[i].skip &&
          weight + items[i].weight > totalWeight) {
            items[i].skip = true;
            i++;
            steps++;
         }
         else if (items[i].push && !items[i].skip) {
            items[i].push = false;
            items[i].skip = true;
            weight -= items[i].weight;
            value -= items[i].value;
            i++;
            if (!decisionTree.isEmpty())
               decisionTree.pop();
            steps++;
         }
         else if (!items[i].push && items[i].skip) {
            items[i].skip = false;
            i--;
         }
      }
   }

   public static boolean boundFunction(Node[] items, int i, double weight,
    double value) {
      for (int j = i; j < items.length; j++) {
         if (weight == totalWeight) {
            break;
         }
         else if (j < items.length && (weight + items[j].weight) <= totalWeight) {
            value += items[j].value;
            weight += items[j].weight;
         }
         else if (j < items.length && (weight + items[j].weight) > totalWeight) {
            value += (int) (items[j].value * (((totalWeight - weight)) / (items[j].weight)));
            weight = totalWeight;
            break;
         }
         else
            break;
      }

      boundValue = (int) value;

      if (boundValue > maxValue)
         return false;
      else
         return true;
   }
}
