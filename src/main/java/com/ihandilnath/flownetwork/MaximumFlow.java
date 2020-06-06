package com.ihandilnath.flownetwork;

import java.util.LinkedList;
import java.util.List;

public class MaximumFlow {

    private boolean[] visited;
    private Edge[] edgeTo;
    private double maximumFlow;

    public double compute(Network network, int source, int sink){

        maximumFlow = 0.0;

        while(hasAugmentingPath(network,source,sink)){

            double bottleNeck = Double.POSITIVE_INFINITY;

            // Compute bottleneck capacity
            for(int v = sink; v!= source; v = edgeTo[v].getOther(v)){
                bottleNeck = Math.min(bottleNeck, edgeTo[v].residualCapacityTo(v));
            }

            // Updating residual values
            for(int v = sink; v!= source; v = edgeTo[v].getOther(v)){
                edgeTo[v].addResidualFlowTo(v,bottleNeck);
            }

            maximumFlow += bottleNeck;
        }

        return maximumFlow;
    }

    /**
     * This method uses the breadth first search algorithm and determines if there is an augmenting path from source to sink.
     * This method has an asymptotic complexity of O(V*E) as augmenting path is searched by iterating through all vertices and all edges.
     * @param network
     * @param source
     * @param sink
     * @return
     */
    private boolean hasAugmentingPath(Network network, int source, int sink){

        edgeTo = new Edge[network.getNoOfVertices()];
        visited = new boolean[network.getNoOfVertices()];

        List<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        while(!queue.isEmpty()){ // O(V) - Iterating through all the vertices

            // Breadth first search algorithm
            int v = ((LinkedList<Integer>) queue).poll();

            for(Edge edge: (network.getAdj())[v]){ // O(E) Iterating through all edges
                int w = edge.getOther(v);
                if(edge.residualCapacityTo(w) > 0 && !visited[w]){
                    edgeTo[w] = edge;
                    visited[w] = true;
                    queue.add(w);
                }
            }

        }

        return visited[sink];
    }

}
