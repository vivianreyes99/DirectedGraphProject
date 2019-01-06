/**
 * Program #3
 * Names: Vivian Reyes
 * Accounts: cssc0794
 */

package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.DirectedGraph;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import edu.sdsu.cs.datastructures.*;

public class App {


    public static void main(String[] args) throws IOException {
        FileReader fileReader;
        BufferedReader cities;
        IGraph graph = new DirectedGraph();
        if (args.length == 0) {

            fileReader = reader("layout.csv");
            cities = new BufferedReader(fileReader);
            graph = createGraph(cities);
            printGraph(graph);
        }
        if (args.length == 1) {
            fileReader = reader(args[0]);
            cities = new BufferedReader(fileReader);
            graph = createGraph(cities);
            printGraph(graph);

        } else {
            System.out.println("Error: Unable to open filename. Verify the" +
                    " file exists, is accessible, and meets the syntax " +
                    "requirements.");
            System.exit(0);
        }
    }

    private static FileReader reader(String line) {
        try {
            return new FileReader(line);
        } catch (IOException e) {
            System.out.println("Error: Unable to open filename. Verify the" +
                    " file exists, is accessible, and meets the syntax" +
                    " requirements.");
            System.exit(-1);
        }
        return null;
    }

    private static IGraph createGraph(BufferedReader reader) {
        IGraph graph = new DirectedGraph();
        try {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                if (arr.length == 1) {
                    graph.add(arr[0]);
                }
                if (arr.length == 2) {
                    if (!graph.contains(arr[0])) {
                        graph.add(arr[0]);
                    }
                    if (!graph.contains(arr[1])) {
                        graph.add(arr[1]);
                    }
                    graph.connect(arr[0], arr[1]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }


    private static void printGraph(IGraph test){
        System.out.println(test.toString());
        System.out.println("Enter two cities to compute their shortest " +
                "path, separated by a comma and one space after the comma: ");
        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        String[] split = line.split(", ");
        if(split.length != 2){
            System.out.println("Please enter two valid ciies separated " +
                    "by a comma and one space after the comma");
        }
        String start = split[0];
        String end = split[1];
        List path = test.shortestPath(start, end);
        int distance = path.size();
        System.out.print("The shortest path between " + start +
                " and " + end + " are the vertices " + path + " with " +
                "a distance of " + distance);
    }
}
