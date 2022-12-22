/* COP 3503C Assignment 5
This program was written by: Nikole Solano */

import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

class Main {
  public static int cities, roads, capital, length;
  public static int cityTreasure = 0, roadTreasure = 0;
  public static List<Edge> edges = new ArrayList<>();
  public static void main(String[] args) throws FileNotFoundException {
    //scanning in all the variables
    File file = new File("in.txt");
    Scanner scan = new Scanner(file);
    cities = scan.nextInt();
    roads = scan.nextInt();
    capital = scan.nextInt() - 1;

    int u, v, w;

    //for loop that scans in the connections and their weight
    for(int i = 0; i < roads; i++)
    {
      //scan in the connections of the roads (index u and v)
      u = scan.nextInt() - 1;
      v = scan.nextInt() - 1;
      w = scan.nextInt();

     edges.add(new Edge(u,v,w));
    }

    length = scan.nextInt();

    Graph graph = new Graph(edges, cities);

    //run Dijkstras alg
    findTreasure(graph, capital, cities);
    
    //output
    System.out.println("In city: " + cityTreasure);
    System.out.println("On the road: " + roadTreasure);

    scan.close();
  }

  //run dijkstra's algorith on a given graph
  public static void findTreasure(Graph graph, int capital, int cities)
  {
    //modified version of code from class
    //create a min-heap and push soruce node having distance 0
    PriorityQueue<Node> minHeap;
    minHeap = new PriorityQueue<>(Comparator.comparingInt(node->node.weight));
    minHeap.add(new Node(capital, 0));

    //set initial distance from source to 'v' as infinity
    List<Integer> dist;
    dist = new ArrayList<>(Collections.nCopies(cities, Integer.MAX_VALUE));

    //distance from the source to itself is zero
    dist.set(capital, 0);

    //boolean array to track verticies for which minimum
    //cost is already found 
    boolean [] done = new boolean[cities];
    done[capital] = true;

    //stores predecessor of a vertex (to a print path)
    int[] prev = new int[cities];
    prev[capital] = -1;

    //continuting to the right side

    //run till min-heap is empty
    while(!minHeap.isEmpty())
    {
      //remove and return the best vertex
      Node node = minHeap.poll();

      //get the vertex number 
      int u = node.vertex;

      //do for each neighbor 'v' of 'u'
      for(Edge edge: graph.adjList.get(u))
      {
        int v = edge.dest;
        int weight = edge.weight;

        if(u == v)
        {
          v = edge.source;
        }

        int remainder = length - node.weight;

      //not at node we were just at
       if(prev[u] != v)
       {
          //it's on the road
          if(remainder < weight)
          {
            if(done[v] == false)
            {
              //we havent visited it
              roadTreasure++;
            }
            //been to the road
            else
            {
              //we have been on this road before
              //make sure it's not the same treasure as we have perviously vsiited
              //making sure the position on the road has not already been visitied
              if(remainder != (edge.weight) - (length - dist.get(v)))
              {
                roadTreasure++;
              }
            }
            continue;
          }
          //in a city
          else if(remainder == edge.weight)
          {
            //we havent been there
            if(done[v] == false)
            {
              //mark as visited
              cityTreasure++;
              done[v] = true;
            }
          }
       }
        //relaxation step
        if(!done[v] && (dist.get(u) + weight) < dist.get(v))
        {
          dist.set(v, dist.get(u) + weight);
          prev[v] = u;
          minHeap.add(new Node(v, dist.get(v)));
        }
      }
      done[u] = true;
    }
  }
}

//from class code
//a class to store a graph edge
class Edge
{
  int source, dest, weight;

  public Edge(int source, int dest, int weight)
  {
    this.source = source;
    this.dest = dest;
    this.weight = weight;
  }
}
//a class to store a heap node
class Node
{
  int vertex, weight;

  public Node(int vertex, int weight)
  {
    this.vertex = vertex;
    this.weight = weight;
  }
}

//a class to represent a graph object
class Graph
{
  //a list of lists to represent an adjacency lust
  List<List<Edge>> adjList = null;

  //constructor
  Graph(List<Edge> edges, int n)
  {
    adjList = new ArrayList<>();
    for(int i = 0; i < n; i++)
    {
      adjList.add(new ArrayList<>());
    }

    //add edges to the bidirectional graph
    for(Edge edge: edges){
      adjList.get(edge.source).add(edge);
      adjList.get(edge.dest).add(edge);
    }
  }
}