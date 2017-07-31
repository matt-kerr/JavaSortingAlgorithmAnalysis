// Matthew Kerr
// October 21, 2013

import java.math.BigInteger;
import java.lang.Integer;
import java.util.Random;
import java.io.File;
import java.io.PrintWriter;

public class SortTester
{
   // cloning these LinkedLists in calcStats() to ensure that all sort tests are running over the exact same data
   private static final LinkedList list_initial_500_ascending = generateList(500, "ASCENDING");
   private static final LinkedList list_initial_500_descending = generateList(500, "DESCENDING");
   private static final LinkedList list_initial_500_random = generateList(500, "RANDOM");
   private static final LinkedList list_initial_1000_ascending = generateList(1000, "ASCENDING");
   private static final LinkedList list_initial_1000_descending = generateList(1000, "DESCENDING");
   private static final LinkedList list_initial_1000_random = generateList(1000, "RANDOM");
   private static final LinkedList list_initial_5000_ascending = generateList(5000, "ASCENDING");
   private static final LinkedList list_initial_5000_descending = generateList(5000, "DESCENDING");
   private static final LinkedList list_initial_5000_random = generateList(5000, "RANDOM");
   private static final LinkedList list_initial_10000_ascending = generateList(10000, "ASCENDING");
   private static final LinkedList list_initial_10000_descending = generateList(10000, "DESCENDING");
   private static final LinkedList list_initial_10000_random = generateList(10000, "RANDOM");
   private static final String[] row_headers = {"500", "1000", "5000", "10000"};
   
   public static void main(String[] args)
   {
      PrintWriter out_file = null;
      PrintWriter sort_csv = null;
      try
      {
         File file = new File("sort_results.txt");
         File file_csv = new File("sort_csv.csv");
         out_file = new PrintWriter(file);
         sort_csv = new PrintWriter(file_csv);
      }
      catch (Exception e)
      {
         System.out.println("Error creating outfile.  Make sure sort_results.txt and/or sort_csv.csv are not already open.");
         System.exit(-1);
      }
      
      System.out.println(">> Calculating ascending order count stats");
      int[][][] all_stats_ascending = {calcStats(500, "ASCENDING"), calcStats(1000, "ASCENDING"), calcStats(5000, "ASCENDING"), calcStats(10000, "ASCENDING")};
      System.out.println(">> Calculating descending order count stats");
      int[][][] all_stats_descending = {calcStats(500, "DESCENDING"), calcStats(1000, "DESCENDING"), calcStats(5000, "DESCENDING"), calcStats(10000, "DESCENDING")};
      System.out.println(">> Calculating random order count stats");
      int[][][] all_stats_random = {calcStats(500, "RANDOM"), calcStats(1000, "RANDOM"), calcStats(5000, "RANDOM"), calcStats(10000, "RANDOM")};
      
      System.out.println(">> Merging together all the count stats");
      int[][] stats_ascending_data_assignments = calcOutput(all_stats_ascending, 1);
      int[][] stats_descending_data_assignments = calcOutput(all_stats_descending, 1);
      int[][] stats_random_data_assignments = calcOutput(all_stats_random, 1);
      int[][] stats_ascending_total_operations = calcOutput(all_stats_ascending, 0);
      int[][] stats_descending_total_operations = calcOutput(all_stats_descending, 0);
      int[][] stats_random_total_operations = calcOutput(all_stats_random, 0);
      
      System.out.println(">> Calculating ascending order time stats");
      long[][] time_ascending = {calcTime(500, "ASCENDING"), calcTime(1000, "ASCENDING"), calcTime(5000, "ASCENDING"), calcTime(10000, "ASCENDING")};
      System.out.println(">> Calculating descending order time stats");
      long[][] time_descending = {calcTime(500, "DESCENDING"), calcTime(1000, "DESCENDING"), calcTime(5000, "DESCENDING"), calcTime(10000, "DESCENDING")};
      System.out.println(">> Calculating random order time stats");
      long[][] time_random = {calcTime(500, "RANDOM"), calcTime(1000, "RANDOM"), calcTime(5000, "RANDOM"), calcTime(10000, "RANDOM")};
      System.out.println(">> Writing out stats to \"sort_results.txt\" and \"sort_csv.csv\".\n");
      System.out.println("=======================================================\n");
      writeCountStats("Data Assignments: Ascending Order", stats_ascending_data_assignments, out_file, sort_csv);
      writeCountStats("Data Assignments: Descending Order", stats_descending_data_assignments, out_file, sort_csv);
      writeCountStats("Data Assignments: Random Order", stats_random_data_assignments, out_file, sort_csv);
      writeCountStats("Total Operations: Ascending Order", stats_ascending_total_operations, out_file, sort_csv);
      writeCountStats("Total Operations: Descending Order", stats_descending_total_operations, out_file, sort_csv);
      writeCountStats("Total Operations: Random Order", stats_random_total_operations, out_file, sort_csv);
      writeTimeStats("Execution Time (seconds): Ascending Order", time_ascending, out_file, sort_csv);
      writeTimeStats("Execution Time (seconds): Descending Order", time_descending, out_file, sort_csv);
      writeTimeStats("Execution Time (seconds): Random Order", time_random, out_file, sort_csv);
      out_file.close();
      sort_csv.close();
   }
   
   private static int[][] calcOutput(int[][][] all_stats, int stats_index)
   {
      int[][] result = new int[all_stats.length][all_stats[0].length];
      for (int i = 0; i < all_stats.length; i++)
      {
         for (int x = 0; x < all_stats[0].length; x++)
         {
            result[i][x] = all_stats[i][x][stats_index];
         }
      }
      return result;
   }
   
   private static int[][] calcStats(int size, String order)
   {
      LinkedList list = getListClone(size, order);
      LinkedList list_bubble = list.clone();
      LinkedList list_selection = list.clone();
      LinkedList list_insertion_shift = list.clone();
      LinkedList list_insertion_cut = list.clone();
      int[][] stats_count = {list_bubble.bubbleSortCount(), list_selection.selectionSortCount(), list_insertion_shift.insertionSortShiftCount(), list_insertion_cut.insertionSortCutCount()};
      return stats_count; 
   }
   
   private static long[] calcTime(int size, String order)
   {
      LinkedList list = getListClone(size, order);
      LinkedList list_bubble = list.clone();
      LinkedList list_selection = list.clone();
      LinkedList list_insertion_shift = list.clone();
      LinkedList list_insertion_cut = list.clone();
      long[] stats_count = {list_bubble.bubbleSortTimed(), list_selection.selectionSortTimed(), list_insertion_shift.insertionSortShiftTimed(), list_insertion_cut.insertionSortCutTimed()};
      return stats_count;
   }
   
   private static LinkedList getListClone(int size, String order)
   {
      LinkedList list = null;
      if (order.equals("ASCENDING"))
      {
         if (size == 500)
         {
            list = list_initial_500_ascending.clone();      
         }
         else if (size == 1000)
         {
            list = list_initial_1000_ascending.clone();      
         }
         else if (size == 5000)
         {
            list = list_initial_5000_ascending.clone();      
         }
         else if (size == 10000)
         {
            list = list_initial_10000_ascending.clone();      
         }
      }
      else if (order.equals("DESCENDING"))
      {
         if (size == 500)
         {
            list = list_initial_500_descending.clone();      
         }
         else if (size == 1000)
         {
            list = list_initial_1000_descending.clone();      
         }
         else if (size == 5000)
         {
            list = list_initial_5000_descending.clone();      
         }
         else if (size == 10000)
         {
            list = list_initial_10000_descending.clone();      
         }
      }
      else if (order.equals("RANDOM"))
      {
         if (size == 500)
         {
            list = list_initial_500_random.clone();      
         }
         else if (size == 1000)
         {
            list = list_initial_1000_random.clone();      
         }
         else if (size == 5000)
         {
            list = list_initial_5000_random.clone();      
         }
         else if (size == 10000)
         {
            list = list_initial_10000_random.clone();      
         }
      }
      else
      {
         System.out.println("Error: Invalid parameter given to calcStats()");
      }   
      return list;
   }
   
   private static void writeCountStats(String header, int[][] ara, PrintWriter out_file, PrintWriter sort_csv)
   {
      System.out.println("========== " + header + " ==========");
      out_file.println("========== " + header + " ==========");
      sort_csv.println(header);
      System.out.printf("\t%-15s%-15s%-15s%s\n", "Bubble", "Selection", "Insert-Shift", "Insert-Cut");
      out_file.printf("        %-15s%-15s%-15s%s", "Bubble", "Selection", "Insert-Shift", "Insert-Cut");
      sort_csv.println(",Bubble,Selection,Insert-Shift,Insert-Cut");
      out_file.println();
      for (int i = 0; i < ara.length; i++)
      {
         System.out.printf("%-8s", row_headers[i]);
         out_file.printf("%-8s", row_headers[i]);
         sort_csv.print(row_headers[i] + ",");
         for (int x = 0; x < ara[0].length; x++)
         {
            System.out.printf("%-15d", ara[i][x]);
            out_file.printf("%-15d", ara[i][x]);
            sort_csv.print(ara[i][x]);
            if (x != ara[0].length - 1)
            {
               sort_csv.print(",");
            }
         }
         System.out.println();
         out_file.println();
         sort_csv.println();
      }
      System.out.println();
      out_file.println();
   }
   
   private static void writeTimeStats(String header, long[][] ara, PrintWriter out_file, PrintWriter sort_csv)
   {
      System.out.println("========== " + header + " ==========");
      out_file.println("========== " + header + " ==========");
      sort_csv.println(header);
      System.out.printf("\t%-15s%-15s%-15s%s\n", "Bubble", "Selection", "Insert-Shift", "Insert-Cut");
      out_file.printf("        %-15s%-15s%-15s%s", "Bubble", "Selection", "Insert-Shift", "Insert-Cut");
      sort_csv.println(",Bubble,Selection,Insert-Shift,Insert-Cut");
      out_file.println();
      for (int i = 0; i < ara.length; i++)
      {
         System.out.printf("%-8s", row_headers[i]);
         out_file.printf("%-8s", row_headers[i]);
         sort_csv.print(row_headers[i] + ",");
         for (int x = 0; x < ara[0].length; x++)
         {
            double result = (double) ara[i][x] / 1000000000;
            System.out.printf("%-15.9f", result);
            out_file.printf("%-15.9f", result);
            sort_csv.printf("%.9f", result);
            if (x != ara[0].length - 1)
            {
               sort_csv.print(",");
            }
         }
         System.out.println();
         out_file.println();
         sort_csv.println();
      }
      System.out.println();
      out_file.println();
   }
      
   private static LinkedList generateList(int size, String order)
   {
      LinkedList list = new LinkedList();
      if (order.equals("ASCENDING"))
      {
         for (int i = 0; i < size; i++)
         {
            list.add(new BigInteger(Integer.toString(i)));
         }
      }
      else if (order.equals("DESCENDING"))
      {
         for (int i = size - 1; i >= 0; i--)
         {
            list.add(new BigInteger(Integer.toString(i)));
         }
      }
      else if (order.equals("RANDOM"))
      {
         Random gen = new Random();
         for (int i = 0; i < size; i++)
         {
            int value = gen.nextInt(1000000) + 1;
            list.add(new BigInteger(Integer.toString(value)));
         }
      }
      else
      {
         System.out.println("Error: Invalid list order type.");
      }
      return list;
   }
}