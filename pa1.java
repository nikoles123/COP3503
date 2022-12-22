import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/* COP 3503C Assignment 1
This program was written by: Nikole Solano */

class Main {
  //method if the array is sorted
  public static int [] getCandidatePair(int num[], int target)
  {
    //array of size 2
    int [] result = new int[2];
    //two trackers i and j 
    int i = 0;
    int length = num.length;
    int j = length - 1;
    //condition to make sure we dont loop over anything twice
    while(i < j)
    {
      //if sum == target, pair found
      if(num[i] + num[j] == target)
      {
        result[0] = num[i];
        result[1] = num[j];
        return result;
      }
      //if sum < target, i needs to move up one
      else if(num[i] + num[j] < target)
      {
        i++;
      }
      //if sum > target, j needs to move down one
      else
      {
        j--;
      }
    }
    return result;
  }

  public static void output(int size, int [] num, int caseNum, int target, int sortType)
  {
    //method if array is unsorted
    if(sortType == 0)
    {
      //flag will keep track of if a solution is found
      int flag = 0;

      //create a new hashmap
      Map<Integer, Integer> map = new HashMap<>();
      //loop through num array
      for (int i = 0; i < num.length; i++) 
      {
        //trying to find the complement
        int complement = target - num[i];
        //the number you're searching for in the array
        if (map.containsKey(complement)) 
        {
          System.out.println("Test case#" + caseNum + ": Target " + target + " achievable by " + complement + " and " + num[i]);
          flag = 1;
          break;
        }
        map.put(num[i], i);
      }
      //if we did not find a solution
      if(flag == 0)
      {
        //print out no solution statement
        System.out.println("Test case#" + caseNum + ": Target " + target + " is NOT achievable.");
      }
    }
    else
    {
      int [] result;
      result = getCandidatePair(num, target); 
      if(result[0] != 0 && result[1] != 0)
      {
        System.out.println("Test case#" + caseNum + ": Target " + target + " achievable by " + result[0] + " and " + result [1]);
      }
      else
      {
        //print out no solution statement
        System.out.println("Test case#" + caseNum + ": Target " + target + " is NOT achievable.");
      }
    }
  }

  public static void main(String[] args) throws FileNotFoundException
  {
    File file = new File("in.txt");
    Scanner in = new Scanner(file);
    //scan in how many test cases
    int n = in.nextInt();
    for(int i = 1; i <= n; i++)
    {
      //sorted/unsorted
      int sortType;
      sortType = in.nextInt();
      //scan in size of the array
      int size = in.nextInt();
      //create a new array of that size
      int[] num = new int[size];
      //scan in and add each number to the array
      for(int j = 0; j < size; j++)
      {
        int curNum = in.nextInt();
        num[j] = curNum;
      }
      //scan in the target
      int target = in.nextInt();
      //this will print and calculate the output 
      output(size, num, i, target, sortType);
    }
    in.close();
  }
}