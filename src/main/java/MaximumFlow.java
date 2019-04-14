import java.util.LinkedList;
import java.util.List;

public class MaximumFlow {

    private boolean[] visited;
    private Edge[] edgeTo;
    private double value;

    public double of(Network network, int source, int sink){
        value = 0.0;
        while(hasAugmentingPath(network,source,sink)){

            double bottle_neck = Double.POSITIVE_INFINITY;

            // Compute bottleneck capacity
            for(int v = sink; v!= source; v = edgeTo[v].getOther(v)){
                bottle_neck = Math.min(bottle_neck, edgeTo[v].residualCapacityTo(v));
            }

            // Updating residual values
            for(int v = sink; v!= source; v = edgeTo[v].getOther(v)){
                edgeTo[v].addResidualFlowTo(v,bottle_neck);
            }

            value += bottle_neck;
        }
        return value;
    }

    private boolean hasAugmentingPath(Network network, int source, int sink){
        
        // Asymptotic complexity of O(V*E) as augmenting path is searched by iterating through all vertices and all edges
        
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
