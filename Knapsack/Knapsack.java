package edu.calpoly.csc349;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Knapsack {
   static private class SubSln {
      public int value;          // Best total value for this subsolution
      public boolean choice;     // Did we select the item, or not?
   }
   
   // One knapsack item
   static private class Item {
      public int value;   // Value of the item
      public int weight;  // Weight of the item

      public Item(int value, int weight) {
         this.value = value;
         this.weight = weight;
      }
   }

   // Prompt for total weight, number of items, and item data.  Set up
   // array of items, and call findBestPack, printing back out the value and
   // weight of each item selected.
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      Item[] items;
      int weight;
      
      System.out.print("Enter total weight, num items, and item data: ");
      weight = in.nextInt();
      items = new Item[in.nextInt()];
      for (int x = 0; x < items.length; x++)
         items[x] = new Item(in.nextInt(), in.nextInt());
      
      System.out.print("Best pack is: ");
      for (Item item : findBestPack(weight, items))
         System.out.printf("v %d w %d  ", item.value, item.weight);
   }
   
   // Set up a subsolution table with these constraints:
   // 1. row dimension represents a constraint to a subset of the items.  As
   // row increases, we allow another new item in the subset, in the order
   // in which the items appear in the array.
   //
   // 2. col dimension represents a constraint to a lower total weight.
   //
   // 3. Topmost row, and leftmost column, represent, respectively, the base
   // case with no items, and the base case with no weight allowed.
   //
   // Traverse the table, in row-major order, filling in subsolutions, by
   // choosing, at each subsolution, whether or not to use the last item in
   // the allowed set (the item for which the current row applies).  Using
   // the last item means you get its value, but you must build on a 
   // subsolution that has one fewer item and less weight.  Not using an item 
   // means you don't get its value, but you may build on a subsolution that 
   // one fewer item and the same weight.
   //
   // Finally, traverse back up the table from the lower right, using the
   // "choice" breadcrumbs to determine which items were chosen.
   public static List<Item> findBestPack(int totWgt, Item[] items) {
      SubSln[][] table = new SubSln[items.length + 1][totWgt + 1];
      List<Item> rtn = new LinkedList<Item>();
      SubSln base = new SubSln(), newSln;
      int weight, numItems, alt;
      Item thisItem;
      
      for (weight = 0; weight <= totWgt; weight++)  // (1)
         table[0][weight] = base;
      for (numItems = 0; numItems < items.length; numItems++)
         table[numItems][0] = base;
      
      for (numItems = 1; numItems <= items.length; numItems++) {
         thisItem = items[numItems-1];   // (2)
         for (weight = 1; weight <= totWgt; weight++) {
            newSln = new SubSln();  // (3)
            newSln.value = table[numItems-1][weight].value;
            if (thisItem.weight <= weight) {
               alt = table[numItems-1][weight - thisItem.weight].value
                + thisItem.value; // (4)
               if (alt > newSln.value) {
                  newSln.value = alt;
                  newSln.choice = true; // (5)
               }
            }
            table[numItems][weight] = newSln;
         }
      }
      
      weight = totWgt;
      for (numItems = items.length; numItems > 0; numItems--) {
         if (table[numItems][weight].choice) {
            rtn.add(0, items[numItems-1]);
            weight -= items[numItems-1].weight;  // (6)
         }
         
         
      }
      
      return rtn;
   }
}
