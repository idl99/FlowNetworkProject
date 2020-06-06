package com.ihandilnath.flownetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines a POJO representation of a network
 */
public class Network {

    // Adjacency array indexed by vertex and each element contains list of edges associated with the given vertex
    // For ex: arr[v] == [ list of edges associated (outgoing / incoming) with v ]
    private List<Edge>[] adj;

    public Network(int noOfVertices) {
        adj = new ArrayList[noOfVertices];
        for (int i = 0; i < noOfVertices; i++){
            adj[i] = new ArrayList<>();
        }
    }

    /**
     * This method returns the number of vertices in the network
     * @return number of vertices in the network
     */
    public int getNoOfVertices() {
        return getAdj().length;
    }

    /**
     * This method returns the adjacency array of vertices and associated edges in the network
     * @return adjacency array of vertices and associated edges in the network
     */
    public List<Edge>[] getAdj() {
        return adj;
    }

    /**
     * This method adds an edge to the network
     * @param edge edge to be added to the network
     */
    public void addEdge(Edge edge){
        int v = edge.getFrom();
        int w = edge.getTo();
        adj[v].add(edge); // Adding edge to index of adjacency matrix where index == "from" vertex
        adj[w].add(edge); // Adding edge to index of adjacency matrix where index == "to" vertex
    }

}
