import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;


class Main {

  public static void printSol(char sol[][], int m, int n)
  {
    for(int i = 0; i < m; i++)
    {
      for(int j = 0; j < n; j++)
      {
        //formatting
        if(j == 0)
        {
          System.out.print("[ ");
        }

        if(sol[i][j] == '\0')
        {
          System.out.printf(" , ");
        }
        else
        {
          System.out.format("%c , ", sol[i][j]);
        }
        if(j == n - 1)
        {
          System.out.print("]");
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args) throws FileNotFoundException 
  {
    File file = new File("in.txt");
    Scanner in = new Scanner(file);

    int k = in.nextInt();
    int m,n,s;

    for(int i = 0; i < k; i++)
    {
      m = in.nextInt();
      n = in.nextInt();
      s = in.nextInt();

      ////make a blank matrix
      char [][] board = new char[m][n];

      for(int j = 0; j < m; j++)
      {
        for(int l = 0; l < n; l++)
        {
          //fill the matrix
        }
      }

      for(int o = 0; o < s; s++)
      {
        //scan in word
        //check to see if its in the matrix
      }
    }

    in.close();
  }
}

