package com.ihandilnath.flownetwork; /**
 * W1673607 - 2016030 - IHAN DILNATH
 */

import java.util.ArrayList;
import java.util.List;

public class Network {

    private final int noOfVertices; //Number of vertices
    private List<Edge>[] adj; // Adjacency array indexed by vertex, containing list of edges

    public Network(int noOfVertices) {
        this.noOfVertices = noOfVertices;
        adj = new ArrayList[noOfVertices];
        for (int i = 0; i < noOfVertices; i++){
            adj[i] = new ArrayList<>();
        }
    }

    public int getNoOfVertices() {
        return noOfVertices;
    }

    public List<Edge>[] getAdj() {
        return adj;
    }

    public void setAdj(List<Edge>[] adj) {
        this.adj = adj;
    }

    public void addEdge(Edge e){
        int v = e.getFrom();
        int w = e.getTo();
        adj[v].add(e); // Adding edge to index of adjacency matrix, where index == "from" vertex
        adj[w].add(e); // Adding edge to index of adjacency matrix, where index == "to" vertex
    }

    public Iterable<Edge> adj(int v){
        return adj[v];
    }

}
