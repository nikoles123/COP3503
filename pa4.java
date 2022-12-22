/* COP 3503C Assignment 4
This program is written by: Nikole Solano */

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class Main 
{
  //shows which directions you can traverse in
  final public static int [] xDirs = {1, -1, 0, 0};
  final public static int [] yDirs = {0, 0, 1, -1};

  public static int r;
  public static int c;
  public static char[][] board;
  public static HashMap<Character, LinkedList<Integer>> teleport = new HashMap<Character, LinkedList<Integer>>();

  public static boolean valid(int x, int y)
  {
    if(x >= 0 && x < r && y>=0 && y < c)
      return true;
    else
      return false;
  }
  public static int findExit(int source, int exit)
  {
    //set up BFS
    LinkedList<Integer> q = new LinkedList<Integer>();

    q.add(source);

    //store -1 for haven't visited
    int [] visited = new int[r*c];
    Arrays.fill(visited, -1);
    visited[source] = 0;
    int dist = 0;

    //run till the queue is done
    while(q.size() > 0)
    {
      int size1 = q.size();

      
      for(int j = 0; j < size1; j++)
      {
        //get the next item. If it's our desination, return 

        int cur = q.poll();
        visited[cur] = 0;
        //System.out.print(cur);
      
        if(cur == exit)
          return dist;
        int curX = cur/c;
        int curY = cur%c;
        //its a letter
        
        if(board[curX][curY] != '.' && board[curX][curY] != '!' && board[curX][curY] != '*' && board[curX][curY] != '$')
        {
          LinkedList<Integer> teleportList = teleport.get(board[curX][curY]);
          for(int k = 0; k < teleportList.size(); k++)
          {
            int next = teleportList.get(k);
            if(visited[next] == 0)
            continue;
          
          visited[next] = 0;
          q.add(next);
          }
          
        }
        

        //try moving
        for(int i = 0; i < xDirs.length; i++)
        {

          int nextX = cur/c + xDirs[i];
          int nextY = cur%c + yDirs[i];

          //if we're off the board or it's a banned square or we've been there before, skip it
          if(!valid(nextX, nextY))
            continue;
          
          if(board[nextX][nextY] == '!'){
            continue;
          }
          int linear_coordinate = nextX * c + nextY;
          if(visited[linear_coordinate] == 0)
            continue;
          
          visited[linear_coordinate] = 0;
          q.add(linear_coordinate);
        }    
      }
      dist +=1;
    }
    //never made it
    return -1;
  }

  public static int find(char s)
  {
    for(int i = 0; i < r; i++)
    {
      for(int j = 0; j < c; j++)
      {
        if(board[i][j] == s)
        {
          //how many cells are there before the curr cell
          return i * r + j;
        }
      }
    }
    //user did not input 'Sastry'
    return -1;
  }
  
  public static void main(String[] args) throws FileNotFoundException
  {
    File file = new File("in.txt");
    Scanner scan = new Scanner(file);
    r = scan.nextInt();
    c = scan.nextInt();
    board = new char[r][c];

    for(int i = 0; i < r; i++)
    {
      board[i] = scan.next().toCharArray();
    }

    //find other keys
    for(int k = 0; k < r; k++)
    {
      for(int l = 0; l < c; l++)
      {
        if(board[k][l] != '.' && board[k][l] != '!' && board[k][l] != '*' && board[k][l] != '$')
        {
          //if the letter is in the hash map
          if(teleport.containsKey(board[k][l]))
          {
            //add to linked list
            teleport.get(board[k][l]).add((k * r + l));
          }
          //if the letter is not in the hash map
          else
          {
            //add a new key and create linked list
            teleport.put(board[k][l], new LinkedList<Integer>());
            teleport.get(board[k][l]).add((k * r + l));
          }
        }
      }
    }
    
    /*
    //testing for printing input
    for(int i = 0; i < r; i++)
    {
      System.out.println(board[i]);
    }
    */
    
    
    int source = find('*'); //single linearized position of the knight
    int exit = find('$');

    if(source == -1)
    {
      System.out.println("Sastry not found in the maze!!");
      System.exit(1);
    }
    if(exit == -1)
    {
      System.out.println("Exit not found in the maze!!");
      System.exit(1);
    }

    //System.out.println("Position of Sastry = " +source);
    //System.out.println("Position of exit = " +exit);

    int minMoves = findExit(source, exit);
    System.out.println(minMoves);
    
    scan.close();
  }
}

