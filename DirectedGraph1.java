/**
 * Program #3
 * Names: Vivian Reyes
 * Accounts: cssc0794
 */

package edu.sdsu.cs.datastructures;

import java.util.HashMap;
import java.util.Map;

import java.util.*;

public class DirectedGraph<V> implements IGraph<V> {
    private Map<V, List<V>> map;
    private List<V> vertices;
    private List edges;

    public DirectedGraph() {
        edges = new LinkedList();
        vertices = new LinkedList();
        map = new HashMap<>();
    }


    public void add(V vertexName) {
        if (!vertices.contains(vertexName)) {
            vertices.add(vertexName);
            map.put(vertexName, new LinkedList());
        }
    }

    public void connect(V start, V destination) {
        if (map.containsKey(start)
                && map.containsKey(destination))
            map.get(start).add(destination);
        else throw new NoSuchElementException();
    }

    public void clear() {
        vertices.clear();
        map.clear();
    }

    public boolean contains(V label) {
        return map.containsKey(label);
    }

    public void disconnect(V start, V destination) {
        if (!map.containsKey(start) || !map.containsKey(destination))
            throw new NoSuchElementException();
        map.get(start).remove(destination);

    }

    public boolean isConnected(V start, V destination) {
        if (!contains(start) || !contains(destination)){
            throw new NoSuchElementException();
        }
        Queue<V> queue = new PriorityQueue<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            V current = queue.poll();
            List neighbors = map.get(current);
            if (neighbors.contains(destination)) {
                return true;
            } else {
                for (int i = 0; i < neighbors.size(); i++) {
                    queue.offer((V) neighbors.get(i));
                }
            }
        }
        return false;
    }

    public Iterable<V> neighbors(V vertexName) {
        if (!contains(vertexName)) throw new NoSuchElementException();
        Iterable neighbors = map.get(vertexName);
        return neighbors;
    }

    public void remove(V vertexName) {
        if (!contains(vertexName)) throw new NoSuchElementException();
        vertices.remove(vertexName);
        map.remove(vertexName);
        for (V v : map.keySet()) {
            List delete = map.get(v);
            if (delete.contains(vertexName)) {
                map.get(v).remove(vertexName);
            }
        }
    }

    public List shortestPath(V start, V destination) {
        if (!contains(start) || !contains(destination)){
            throw new NoSuchElementException();
        }

        List path = new LinkedList();
        Map<V, V> predecessors = new HashMap<>();
        Set unvisitedNodes = new HashSet(vertices);
        Map<V, Integer> distanceTable = distanceT();
        Queue<V> queue = new PriorityQueue<>();

        distanceTable.put(start, 0);
        queue.offer(start);

        while (!queue.isEmpty()) {
            V current = queue.poll();
            if (!unvisitedNodes.contains(destination)) break;

            if (!unvisitedNodes.contains(current)) continue;

            unvisitedNodes.remove(current);

            LinkedList<V> neighborList = (LinkedList<V>) neighbors(current);
            for (V neighbor : neighborList) {
                int cost = distanceTable.get(current) + 1;
                if (cost < distanceTable.get(neighbor)) {
                    distanceTable.put(neighbor, cost);
                    predecessors.put(neighbor, current);
                }
                queue.offer(neighbor);
            }
        }

        V step = destination;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }

    private Map<V, Integer> distanceT() {
        Map<V, Integer> unvisited = new HashMap<>();
        for (V vertex : map.keySet()) {
            unvisited.put(vertex, Integer.MAX_VALUE);
        }
        return unvisited;
    }

    public int size() {
        return map.size();
    }

    public Iterable<V> vertices() {
        return vertices;
    }

    public IGraph<V> connectedGraph(V origin) {
        if (!map.containsKey(origin)) {
            throw new NoSuchElementException();
        }

        IGraph graph = new DirectedGraph();
        Queue<V> queue = new PriorityQueue<>();
        graph.add(origin);
        queue.offer(origin);
        while (!queue.isEmpty()) {
            V current = queue.poll();
            Iterable neighbors = neighbors(current);
            for (Object v : neighbors) {

                if (isConnected(origin, (V) v) && !graph.contains(v)){
                    graph.add(v);
                    graph.connect(current, v);
                    queue.offer((V) v);
                }
            }
        }
        return graph;
    }

    public String toString() {

        System.out.println("The vertices in this graph, all with a weight" +
                " of 1, include, ");
        for (V v : map.keySet()) {
            System.out.println(v + " ");
        }
        System.out.println();
        System.out.println("The connections in this graph include, ");
        for (V v : map.keySet()) {
            Iterable connections = neighbors(v);
            for (Object i : connections) {
                System.out.println(v + " is connected to " + i);
            }
        }
        return "End of graph";
    }
}

