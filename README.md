# DirectedGraphProject
This application loads data from a CVS file and builds a weighted directed graph
and computes the shortest path between two cities.
The CSV file contains either a vertex entry or a connection entry. 
Dikstra's Shortest Path algorithm is used from scratch to compute shortest path. 

//main.java class
- Verifies that the required files exist in their correct locations.
- Declares and instantiates a DirectedGraph object of type IGraph.
- Reads in the input file and builds a graph 
- Displays Information about the Graph (e.g., vertices, connections, degree of each vertex) by printing it to the screen
- Allows the user to select the starting and ending vertices in the graph
- and computes the shortest path between them. The application
- displays both the distance and route. 

//DirectedGraph1.java
- graph data structure implemnting the IGrpah interface

//IGraph.java
- interface 
