// Matthew Kerr
// October 21, 2013

import java.math.BigInteger;

public class LinkedList
{
   private class Node
   {
      private Comparable data;
      private Node next;
      private Node prev;
   
      public Node(Comparable data, Node next, Node prev)
      {
         this.data = data;
         this.next = next;  
         this.prev = prev;    
      }
   }
   
   private Node head;
   private int size;
   public int data_comparisons;
   public int data_assignments;
   public int loop_comparisons; 
   public int loop_assignments;
   public int other_operations;
   
   public LinkedList()
   {
      this.head = new Node(null, null, null);
      this.head.next = head;
      this.head.prev = head;
      this.size = 0;
      this.data_assignments = 0;
      this.data_comparisons = 0;    
      this.loop_assignments = 0;
      this.loop_comparisons = 0;
      this.other_operations = 0;
   }

   private void clearCount()
   {
      this.data_assignments = 0;
      this.data_comparisons = 0;
      this.loop_assignments = 0;
      this.loop_comparisons = 0;
      this.other_operations = 0;
   }

   private int totalOperations()
   {
      return this.data_comparisons + this.data_assignments + this.loop_comparisons + this.loop_assignments + this.other_operations;
   }

   @Override
   public boolean equals(Object obj)
   {
      // compare two linked lists
      if (this.getClass().getSimpleName().equals(obj.getClass().getSimpleName()))
      {
         LinkedList that = (LinkedList) obj;
         if (this.size == that.size)
         {
            Node this_curr = this.head.next;
            Node that_curr = that.head.next;
            for (; this_curr != this.head; this_curr = this_curr.next, that_curr = that_curr.next)
            {
               if (!this_curr.data.equals(that_curr.data))
               {
                  return false;
               }
            }
            return true;
         }
      }
      return false;
   }

   @Override
   public String toString()
   {
      String result = "";
      for (Node curr = this.head.next; curr != this.head; curr = curr.next)
      {
         result += curr.data.toString() + " ";
      }
      return result;
   }

   private Node find(int index)
   {
      data_comparisons++; // if statement check
      if (index < 0 || index > size)
      {
         other_operations++; // return statement
         return null;
      }
      
      other_operations++; // initializing Node curr
      data_assignments++; // assigning curr = this.head.next
      Node curr = this.head.next;
      
      loop_assignments++; // initial for loop assignment
      for (int i = 0; i < index; i++)
      {
         loop_comparisons++; // for loop check returned true
         
         data_assignments++; // assigning curr = curr.next
         curr = curr.next;
      }
      loop_comparisons++; // for loop check returned false
      
      other_operations++; // return statement
      return curr;
   }

   public boolean add(Comparable data)
   {
      // add to end
      Node newNode = new Node(data, this.head, this.head.prev);
      this.head.prev.next = newNode;
      this.head.prev = newNode;
      this.size++;
      return true;
   }

   public void clear()
   {
      this.head.next = this.head;
      this.head.prev = this.head;
      this.size = 0;
   }

   public LinkedList clone()
   {
      LinkedList list = new LinkedList();
      for (Node curr = this.head.next; curr != this.head; curr = curr.next)
      {
         list.add(curr.data);
      }
      return list;
   }

   public int size()
   {
      return this.size;
   }

   public boolean isEmpty()
   {
      return this.size == 0;
   }
   
   public int[] bubbleSortCount()
   {
      // returning int[] indexes:
      // 0 = total operations
      // 1 = data_assignments
      // 2 = data_comparisons
      // 3 = loop_assignments
      // 4 = loop_comparisons
      // 5 = other_operations
      clearCount(); // reset all counters
      
      this.other_operations++; // initializing boolean something_swapped
      this.loop_assignments++; // assigning boolean something_swapped = true
      boolean something_swapped = true; // makes this a "smart" bubble sort
      
      this.other_operations++; // initializing Node last_sorted
      this.data_assignments++; // assigning Node last_sorted = this.head
      Node last_sorted = this.head;
      
      this.other_operations++; // initializing Node curr
      this.data_assignments++; // assigning Node curr = null
      Node curr = null;
      
      this.other_operations++; // calculating this.size - 1
      this.loop_assignments++; // initial outer for loop assignment
      for (int i = this.size - 1; i > 0 && something_swapped; i--)
      {
         this.loop_comparisons++; // outer for loop check i > 0 returned true
         this.loop_comparisons++; // outer for loop check something_swapped returned true
         
         this.loop_assignments++; // assigning something_swapped = true
         something_swapped = false;
         
         this.data_assignments++; // assigning curr = this.head.next      
         curr = this.head.next;
              
         this.loop_assignments++; // initial inner for loop assignment
         for (int x = 0; x < i; x++)
         {
            this.loop_comparisons++; // inner for loop check returned true
            
            this.other_operations++; // calling compareTo
            this.data_comparisons++; // if statement check
            if (curr.data.compareTo(curr.next.data) > 0)
            {
               this.data_assignments++; // assigning Comparable temp = curr.data
               Comparable temp = curr.data;
               
               this.data_assignments++; // assigning curr.data = curr.next.data
               curr.data = curr.next.data;
               
               this.data_assignments++; // assigning curr.next.data = temp
               curr.next.data = temp;
               
               this.loop_assignments++; // assigning something_swapped = true
               something_swapped = true;  
            }
            this.data_assignments++; // assigning curr = curr.next
            curr = curr.next;
            
            this.loop_assignments++; // inner for loop assignment of x++ that is about to occur
         }
         this.loop_comparisons++; // inner for loop check returned false
         
         this.loop_assignments++; // outer for loop assignment of i-- that is about to occur        
      }
      this.loop_comparisons++; // outer for loop check returned false
      
      this.other_operations++; // return statement that will execute next
      
      int[] result = {totalOperations(), this.data_assignments, this.data_comparisons, this.loop_assignments, this.loop_comparisons, this.other_operations};
      
      return result;
   }
   
   public long bubbleSortTimed()
   {
      long start_time = System.nanoTime();
      boolean something_swapped = true;
      Node last_sorted = this.head;
      Node curr = null;
      for (int i = this.size - 1; i > 0 && something_swapped; i--)
      {
         something_swapped = false;
         curr = this.head.next;
         for (int x = 0; x < i; x++)
         {
            if (curr.data.compareTo(curr.next.data) > 0)
            {
               Comparable temp = curr.data;
               curr.data = curr.next.data;
               curr.next.data = temp;
               something_swapped = true;
            }
            curr = curr.next;
            
         }
      }
      long end_time = System.nanoTime();
      long total_time = end_time - start_time;
      return total_time;
   }

   public int[] selectionSortCount()
   {
      // returning int[] indexes:
      // 0 = total operations
      // 1 = data_assignments
      // 2 = data_comparisons
      // 3 = loop_assignments
      // 4 = loop_comparisons
      // 5 = other_operations
      clearCount(); // reset all counters
   
      this.other_operations++; // initializing Node start
      Node start;
      
      this.other_operations++; // initializing Node smallest
      Node smallest;
      
      this.other_operations++; // initializing Node curr
      Node curr;
      
      this.other_operations++; // initializing Comparable temp
      Comparable temp;
      
      this.loop_assignments++; // inital outer for loop assignment
      for (start = this.head.next; start.next != this.head; start = start.next)
      {
         this.loop_comparisons++; // outer for loop check returned true
         
         this.other_operations++; // assigning smallest = start
         smallest = start;
         
         this.loop_assignments++; // inital inner for loop assignment
         for (curr = start.next; curr.data != null; curr = curr.next)
         {
            this.loop_comparisons++; // inner for loop check returned true
            
            this.other_operations++; // calling compareTo()
            this.data_comparisons++; // if statement check
            if (curr.data.compareTo(smallest.data) < 0)
            {
               this.other_operations++; // assigning smallest = curr
               smallest = curr; 
            }
            
            this.loop_assignments++; // inner for loop assignment of curr = curr.next that is about to occur
         }
         this.loop_comparisons++; // inner for loop check returned false
         
         this.data_assignments++; // assigning temp = start.data
         temp = start.data;
         
         this.data_assignments++; // assigning start.data = smallest.data
         start.data = smallest.data;
         
         this.data_assignments++; // assigning smallest.data = temp
         smallest.data = temp;
         
         this.loop_assignments++; // outer for loop assignment of start = start.next that is about to occur
      }
      this.loop_comparisons++; // outer for loop check returned false
      
      this.other_operations++; // return statement that will execute next
      
      int[] result = {totalOperations(), this.data_assignments, this.data_comparisons, this.loop_assignments, this.loop_comparisons, this.other_operations};
      
      return result;
   }
   
   public long selectionSortTimed()
   {
      long start_time = System.nanoTime(); 
      Node start, smallest, curr;
      Comparable temp;
      for (start = this.head.next; start.next != this.head; start = start.next)
      {
         smallest = start;
         for (curr = start.next; curr.data != null; curr = curr.next)
         {
            if (curr.data.compareTo(smallest.data) < 0)
            {
               smallest = curr;
            }
         }
         temp = start.data;
         start.data = smallest.data;
         smallest.data = temp;
      }
      
      long end_time = System.nanoTime();
      long total_time = end_time - start_time;
      return total_time;
   }
   
   public int[] insertionSortShiftCount()
   {
      // returning int[] indexes:
      // 0 = total operations
      // 1 = data_assignments
      // 2 = data_comparisons
      // 3 = loop_assignments
      // 4 = loop_comparisons
      // 5 = other_operations
      clearCount();
      
      this.other_operations++; // initializing Node last_sorted
      Node last_sorted;
      
      this.other_operations++; // initializing Node sorted_walker
      Node sorted_walker;
      
      this.other_operations++; // initializing Comparable first_unsorted_data
      Comparable first_unsorted_data;
      
      this.loop_assignments++; // initial outer for loop assignment
      for (last_sorted = this.head.next; last_sorted != this.head.prev; last_sorted = last_sorted.next)
      {
         this.loop_comparisons++; // outer for loop check returned true
         
         this.loop_assignments++; // assigning first_unsorted_data = last_sorted.next.data
         first_unsorted_data = last_sorted.next.data;
         
         this.loop_assignments++; // initial inner for loop assignment
         for (sorted_walker = last_sorted; sorted_walker != this.head && sorted_walker.data.compareTo(first_unsorted_data) > 0; sorted_walker = sorted_walker.prev)
         {
            this.other_operations++; // calling compareTo() in for loop check
            this.loop_comparisons += 2; // both inner for loop checks returned true
            
            this.data_assignments++;
            sorted_walker.next.data = sorted_walker.data;
            
            this.loop_assignments++; // inner for loop assignment of sorted_walker = sorted_walker.prev that is about to occur
         }
         this.other_operations++; // calling compareTo() in for loop check
         this.loop_comparisons++; // inner for loop check returned false
         
         this.data_assignments++; // assigning sorted_walker.next.data = first_unsorted_data
         sorted_walker.next.data = first_unsorted_data;
         
         this.loop_assignments++; // inner for loop assignment of last_sorted = last_sorted.next that is about to occur
      }
      this.loop_comparisons++; // outer for loop check returned false
      
      this.other_operations++; // return statement that will execute next
      
      int[] result = {totalOperations(), this.data_assignments, this.data_comparisons, this.loop_assignments, this.loop_comparisons, this.other_operations};
      
      return result;
   }
   
   public long insertionSortShiftTimed()
   {
      long start_time = System.nanoTime();
      Node last_sorted, sorted_walker;
      Comparable first_unsorted_data;
      for (last_sorted = this.head.next; last_sorted != this.head.prev; last_sorted = last_sorted.next)
      {
         first_unsorted_data = last_sorted.next.data;
         for (sorted_walker = last_sorted; sorted_walker != this.head && sorted_walker.data.compareTo(first_unsorted_data) > 0; sorted_walker = sorted_walker.prev)
         {
            sorted_walker.next.data = sorted_walker.data;
         }
         sorted_walker.next.data = first_unsorted_data;
      }
      long end_time = System.nanoTime();
      long total_time = end_time - start_time;
      return total_time;
   }
   
   public int[] insertionSortCutCount()
   {
      // returning int[] indexes:
      // 0 = total operations
      // 1 = data_assignments
      // 2 = data_comparisons
      // 3 = loop_assignments
      // 4 = loop_comparisons
      // 5 = other_operations
      clearCount();
      
      this.other_operations++; // initializing Node last_sorted
      Node last_sorted;
      
      this.other_operations++; // initializing Node sorted_walker
      Node sorted_walker;
      
      this.other_operations++; // initializing Node first_unsorted_node
      Node first_unsorted_node;
      
      this.loop_assignments++; // initial outer for loop assignment
      for (last_sorted = this.head.next; last_sorted != this.head.prev;)
      {
         this.loop_comparisons++; // outer for loop check returned true
         
         this.loop_assignments++; // assigning first_unsorted_node = last_sorted.next
         first_unsorted_node = last_sorted.next;
         
         this.loop_assignments++; // inital inner for loop assignment
         for (sorted_walker = last_sorted; sorted_walker != this.head && sorted_walker.data.compareTo(first_unsorted_node.data) > 0; sorted_walker = sorted_walker.prev)
         {
            this.other_operations++; // calling compareTo() in for loop check
            this.loop_comparisons += 2; // both inner for loop checks returned true
            this.loop_assignments++; // inner for loop assignment of sorted_walker = sorted_walker.prev that is about to occur
         }
         this.other_operations++; // calling compareTo() in for loop check
         this.data_comparisons++; // inner for loop check returned false
         
         this.data_comparisons++; // if statement check     
         if (sorted_walker != last_sorted)
         {
            this.data_assignments++; // assigning last_sorted.next = first_unsorted_node.next
            last_sorted.next = first_unsorted_node.next;
            
            this.data_assignments++; // assigning last_sorted.next.prev = last_sorted
            last_sorted.next.prev = last_sorted;
            
            this.data_assignments++; // assigning first_unsorted_node.prev = sorted_walker
            first_unsorted_node.prev = sorted_walker;
            
            this.data_assignments++; // assigning first_unsorted_node.next = sorted_walker.next
            first_unsorted_node.next = sorted_walker.next;
            
            this.data_assignments++; // assigning sorted_walker.next.prev = first_unsorted_node
            sorted_walker.next.prev = first_unsorted_node;
            
            this.data_assignments++; // assigning sorted_walker.next = first_unsorted_node
            sorted_walker.next = first_unsorted_node;   
         }      
         else
         {
            this.loop_assignments++; // assigning sorted_walker = sorted_walker.next
            sorted_walker = sorted_walker.next;
            
            this.loop_assignments++; // assigning last_sorted = last_sorted.next
            last_sorted = last_sorted.next;   
         }
      // no loop_assignment increment here because this sort does not use one on the outer loop   
      }
      this.loop_comparisons++; // outer for loop check returned false
      
      this.other_operations++; // return statement that will execute next
      
      int[] result = {totalOperations(), this.data_assignments, this.data_comparisons, this.loop_assignments, this.loop_comparisons, this.other_operations};
      
      return result;
   }
   
   public long insertionSortCutTimed()
   {
      long start_time = System.nanoTime(); 
      Node last_sorted, sorted_walker;
      Node first_unsorted_node = null;
      for (last_sorted = this.head.next; last_sorted != this.head.prev;)
      {
         first_unsorted_node = last_sorted.next;
         for (sorted_walker = last_sorted; sorted_walker != this.head && sorted_walker.data.compareTo(first_unsorted_node.data) > 0; sorted_walker = sorted_walker.prev);       
         if (sorted_walker != last_sorted)
         {
            last_sorted.next = first_unsorted_node.next;
            last_sorted.next.prev = last_sorted;
            first_unsorted_node.prev = sorted_walker;
            first_unsorted_node.next = sorted_walker.next;
            sorted_walker.next.prev = first_unsorted_node;
            sorted_walker.next = first_unsorted_node;
         }
         else
         {
            sorted_walker = sorted_walker.next;
            last_sorted = last_sorted.next;
         }
      }
      long end_time = System.nanoTime();
      return end_time - start_time;
   }
   
   public int[] mergeSortCount()
   {
      // returning int[] indexes:
      // 0 = total operations
      // 1 = data_assignments
      // 2 = data_comparisons
      // 3 = loop_assignments
      // 4 = loop_comparisons
      // 5 = other_operations
      clearCount(); // reset all counters
      
      doMergeSortCount(0, this.size);
      
      int[] result = {totalOperations(), this.data_assignments, this.data_comparisons, this.loop_assignments, this.loop_comparisons, this.other_operations};
      return result;
   }
   
   public long mergeSortTimed()
   {
      long start_time = System.nanoTime();
      
      doMergeSortTimed(0, this.size);
      
      long end_time = System.nanoTime();
      return end_time - start_time;
   }
   
   private void doMergeSortCount(int p, int q)
   {
      this.data_comparisons++; // if statement check
      if (q - p < 2)
      {
         this.other_operations++; // return statement
         return;
      }
      
      this.other_operations++; // initializing int m
      this.data_assignments++; // assigning the above operations to m
      int m = ((p + q) / 2);      
      
      this.other_operations++; // recursive merge sort call
      doMergeSortCount(p, m);
      
      this.other_operations++; // recursive merge sort call
      doMergeSortCount(m, q);
      
      this.other_operations++; // mergeCount call
      mergeCount(find(p), find(m), find(q), (q-p)); 
   }
   
   private void doMergeSortTimed(int p, int q)
   {
      if (q - p < 2)
      {
         return;
      }
      int m = ((p + q) / 2);      
      doMergeSortTimed(p, m);
      doMergeSortTimed(m, q);
      mergeTimed(find(p), find(m), find(q), (q-p)); 
   }
   
   private void mergeCount(Node p, Node m, Node q, int temp_size)
   {
      this.other_operations++; // calling compareTo
      this.data_comparisons++; // if statement check
      if (m.prev.data.compareTo(m.data) <= 0)
      {
         this.other_operations++; // return statement
         return;
      }
      
      this.other_operations++; // calculating q-p
      this.other_operations++; // initializing Comparable[] temp
      Comparable[] temp = new Comparable[temp_size]; 
      
      this.other_operations++; // initializing Node i
      this.data_assignments++; // assigning Node i = find(p)
      Node i = p;
      
      this.other_operations++; // initializing Node j
      this.data_assignments++; // assigning Node j = find(m)
      Node j = m;
      
      this.other_operations++; // initializing k
      this.data_assignments++; // assigning k = 0
      int k = 0;
      
      while (i != m && j != q)
      {
         this.loop_comparisons++; // while loop check returned true
         
         this.other_operations++; // compareTo() call
         this.data_comparisons++; // if statement check
         if (i.data.compareTo(j.data) <= 0)
         {
            this.data_assignments++; // assigning i.data = temp[k]
            temp[k] = i.data;
            
            this.loop_assignments++; // assigning i = i.next
            i = i.next;
         }
         else
         {
            this.data_assignments++; // assigning temp[k] = j.data
            temp[k] = j.data;
            
            this.loop_assignments++; // assigning j = j.next
            j = j.next; 
         }
         this.data_assignments++; // incrementing k
         k++;
      }
      this.loop_comparisons++; // while loop check returned false
      
      while (i != m)
      {
         this.loop_comparisons++; // while loop check returned true
         
         this.data_assignments++; // assigning temp[k] = i.data
         temp[k] = i.data;
         
         this.loop_assignments++; // i = i.next
         i = i.next;
         
         this.data_assignments++; // incrementing k
         k++;
      }
      this.loop_comparisons++; // while loop check returned false
      
      while (j != q)
      {
         this.loop_comparisons++; // while loop check returned true
         
         this.data_assignments++; // assigning temp[k] = j.data
         temp[k] = j.data;
         
         this.loop_assignments++; // assigning j = j.next
         j = j.next;
         
         this.data_assignments++; // incrementing k
         k++;
      }
      this.loop_comparisons++; // while loop check returned false
      
      this.data_assignments++; // assigning Node curr = p
      Node curr = p;
      
      this.loop_assignments++; // inital for loop assignment
      for (int z = 0; z < temp.length; z++)
      {
         this.loop_comparisons++; // for loop check returned true
         
         this.data_assignments++; // assigning curr.data = temp[z]
         curr.data = temp[z];
         
         this.data_assignments++; // assigning curr = curr.next
         curr = curr.next;
         
         this.loop_assignments++; // for loop assignment of z++ that is about to occur
      }
      this.loop_comparisons++; // for loop check returned false
   }
   
   private void mergeTimed(Node p, Node m, Node q, int temp_size)
   {
      if (m.prev.data.compareTo(m.data) <= 0)
      {
         return;
      }
      Comparable[] temp = new Comparable[temp_size]; 
      Node i = p;
      Node j = m;
      int k = 0;
      while (i != m && j != q)
      {
         if (i.data.compareTo(j.data) <= 0)
         {
            temp[k] = i.data;
            i = i.next;
         }
         else
         {
            temp[k] = j.data;
            j = j.next; 
         }
         k++;
      }
      while (i != m)
      {
         temp[k] = i.data;
         i = i.next;
         k++;
      }
      while (j != q)
      {
         temp[k] = j.data;
         j = j.next;
         k++;
      }
      Node curr = p;
      for (int z = 0; z < temp.length; z++)
      {
         curr.data = temp[z];
         curr = curr.next;
      }
   }
   
   public int[] quickSortCount()
   {
      // returning int[] indexes:
      // 0 = total operations
      // 1 = data_assignments
      // 2 = data_comparisons
      // 3 = loop_assignments
      // 4 = loop_comparisons
      // 5 = other_operations
      clearCount(); // reset all counters
      
      this.other_operations++; // initial quick sort call
      doQuickSortCount(0, this.size - 1);
      
      int[] result = {totalOperations(), this.data_assignments, this.data_comparisons, this.loop_assignments, this.loop_comparisons, this.other_operations};
      return result;
   }
   
   public long quickSortTimed()
   {
      long start_time = System.nanoTime();
   
      doQuickSortTimed(0, this.size - 1);
      
      long end_time = System.nanoTime();
      return end_time - start_time;
   }
   
   private void doQuickSortCount(int p, int q)
   {
      this.other_operations++; // calculating q - p
      this.data_comparisons++; // if statement check
      if (q - p < 2)
      {
         other_operations++; // return statement
         return;
      }
      this.other_operations++; // calling partitionCount()
      this.data_assignments++; // assigning m = partitionCount(p, q)
      int m = partitionCount(p, q);
      
      this.other_operations++; // recursive call to doQuickSortCount(p, m)
      doQuickSortCount(p, m);
      
      this.other_operations++; // recursive call to doQuickSortCount(m+1, q)
      doQuickSortCount(m+1, q);
   }
   
   private void doQuickSortTimed(int p, int q)
   {
      if (q - p < 2)
      {
         return;
      }
      int m = partitionTimed(p, q);
      doQuickSortTimed(p, m);
      doQuickSortTimed(m+1, q);
   }
   
   private int partitionCount(int p, int q)
   {
      this.other_operations++; // find(p) call
      this.data_assignments++; // assigning pivot = find(p).data
      Comparable pivot = find(p).data;
      
      this.data_assignments++; // assigning i = p
      int i = p;
      
      this.data_assignments++; // assigning j = q
      int j = q;
      
      while (i < j)
      {
         this.loop_comparisons++; // while loop check returned true
         
         for (; i < j && find(j).data.compareTo(pivot) >= 0; j--)
         {
            this.other_operations++; // find(j) call in for loop check
            this.other_operations++; // compareTo() call in for loop check
            this.loop_comparisons++; // for loop check returned true
            this.loop_assignments++; // for loop assignment of j-- that is about to occur
         }
         this.other_operations++; // find(j) call in for loop check
         this.other_operations++; // compareTo() call in for loop check
         this.loop_comparisons++; // for loop check returned false
         
         data_comparisons++; // if statement check
         if (i < j)
         {
            this.other_operations++; // find(j) call
            this.other_operations++; // find(i) call
            this.data_assignments++; // assigning find(i).data = find(j).data
            find(i).data = find(j).data;
         }
         
         for (; i < j && find(i).data.compareTo(pivot) <= 0; i++)
         {
            this.other_operations++; // find(i) call in for loop check
            this.other_operations++; // compareTo call in for loop check
            this.loop_comparisons++; // for loop check returned true
            this.loop_assignments++; // for loop assignment of i++ that is about to occur
         }
         this.other_operations++; // find(i) call in for loop check
         this.other_operations++; // compareTo() call in for loop check
         this.loop_comparisons++; // for loop check returned false
         
         this.data_comparisons++; // if statement check
         if (i < j)
         {
            this.other_operations++; // find(i) call
            this.other_operations++; // find(j) call
            this.data_assignments++; // assigning find(j).data = find(i).data
            find(j).data = find(i).data;
         }
      }
      
      this.other_operations++; // find(j) call
      this.data_assignments++; // assigning find(j).data = pivot
      find(j).data = pivot;
      
      this.other_operations++; // return statement
      return j;
   }
   
   private int partitionTimed(int p, int q)
   {
      Comparable pivot = find(p).data;
      int i = p;
      int j = q;
      while (i < j)
      {
         for (; i < j && find(j).data.compareTo(pivot) >= 0; j--);         
         if (i < j)
         {
            find(i).data = find(j).data;
         }
         
         for (; i < j && find(i).data.compareTo(pivot) <= 0; i++);         
         if (i < j)
         {
            find(j).data = find(i).data;
         }
      }
      find(j).data = pivot;
      return j;
   }
   
   public int[] shellSortCount()
   {
      // returning int[] indexes:
      // 0 = total operations
      // 1 = data_assignments
      // 2 = data_comparisons
      // 3 = loop_assignments
      // 4 = loop_comparisons
      // 5 = other_operations
      clearCount(); // reset all counters
      
      other_operations++; // initializing d
      loop_assignments++; // assigning d = 1
      int d = 1;
      
      other_operations++; // initializing j
      int j;
      
      other_operations++; // initializing n
      loop_assignments++; // assigning n
      int n = this.size;
      
      while (9*d < n)
      {
         loop_comparisons++; // while loop check returned true
         
         loop_assignments++; // assigning d = 3*d + 1
         d = 3*d + 1;
      }
      loop_comparisons++; // while loop check returned false
      
      while (d > 0)
      {
         loop_comparisons++; // while loop check returned true
         
         loop_assignments++; // initial for loop assignment i = d
         for (int i = d; i < n; i++)
         {
            loop_comparisons++; // for loop check returned true
            
            other_operations++; // find(i) call
            other_operations++; // initializing Comparable temp
            //data_assignments++; // assigning temp = find(i).data
            Comparable temp = find(i).data;
            
            loop_assignments++; // assigning j = i
            j = i;
            
            while (j >= d && find(j-d).data.compareTo(temp) > 0)
            {
               other_operations++; // find(j-d) call
               other_operations++; // compareTo() call
               loop_comparisons++; // while loop check returned true
               
               other_operations++; // find(j-d) call
               other_operations++; // find(j) call
               data_assignments++; // assigning find(j).data = find(j-d).data
               find(j).data = find(j-d).data;
               
               loop_assignments++; // assigning j-= d
               j -= d;
            }
            other_operations++; // while loop find(j-d) call
            other_operations++; // while loop compareTo() call
            loop_comparisons++; // while loop check returned false
            
            other_operations++; // find(j) call
            data_assignments++; // assigning find(j).data = temp
            find(j).data = temp;
            
            loop_assignments++; // for loop assignment of i++ that is about to occur
         }
         loop_assignments++; // assigning d/= 3
         d /= 3;
      }
      loop_comparisons++; // while loop check returned false
      
      int[] result = {totalOperations(), this.data_assignments, this.data_comparisons, this.loop_assignments, this.loop_comparisons, this.other_operations};
      return result;
   }
   
   public long shellSortTimed()
   {
      long start_time = System.nanoTime();
      
      int d = 1;
      int j;
      int n = this.size;
      
      while (9*d < n)
      {
         d = 3*d + 1;
      }
      
      while (d > 0)
      {
         for (int i = d; i < n; i++)
         {
            Comparable temp = find(i).data;
            j = i;
            while (j >= d && find(j-d).data.compareTo(temp) > 0)
            {
               find(j).data = find(j-d).data;
               j -= d;
            }
            find(j).data = temp;
         }
         d /= 3;
      }
      
      long end_time = System.nanoTime();
      return end_time - start_time;
   }
   
}