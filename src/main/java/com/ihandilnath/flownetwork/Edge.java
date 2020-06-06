package com.ihandilnath.flownetwork;

/**
 * This class defines a POJO representation of an edge in a network
 */
public class Edge {

    // The vertice from which the edge connects
    private final int from;

    // The vertice to which the edge connects
    private final int to;

    // The capacity of the edge
    private final double capacity;

    // The flow through the edge initialized to zero.
    // Flow is computed when fed to the MaximumFlow#compute method
    private double flow;

    public Edge(int from, int to, double capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getFlow() {
        return flow;
    }

    /**
     * This method returns the other vertice associated in an edge
     * @param arg
     * @return
     */
    public int getOther(int arg){
        if(arg == from){
            return to;
        } else{
            return from;
        }
    }

    /**
     * This method computes the residual capacity to a vertex
     * @param vertex
     * @return
     */
    public double residualCapacityTo(int vertex){
        if(vertex == from) // Backward edge
            return flow;
        else // Forward edge
            return capacity - flow;
    }

    /**
     * This method adds residual flow to an edge
     * @param vertex
     * @param delta
     */
    public void addResidualFlowTo(int vertex, double delta){
        if(vertex == from)
            flow -= delta;
        else if(vertex == to){
            flow += delta;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f",this.flow, this.capacity);
    }
}

