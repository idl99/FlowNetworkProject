/**
 * W1673607 - 2016030 - IHAN DILNATH
 */

public class Edge {

    private final int from,to; // v is from, w is to
    private final double capacity;
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

    public void setFlow(double flow) {
        this.flow = flow;
    }

    public int getOther(int arg){
        if(arg == from){
            return to;
        } else{
            return from;
        }
    }

    public double residualCapacityTo(int vertex){
        if(vertex == from) // Backward edge
            return flow;
        else // Forward edge
            return capacity - flow;
    }

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

