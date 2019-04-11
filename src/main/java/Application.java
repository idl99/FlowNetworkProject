import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Application {

    public static void main(String[] args) {

        Edge zeroToOne = new Edge(0,1, 10);
        Edge zeroToThree = new Edge(0,3, 10);
        Edge oneToTwo = new Edge(1,2, 4);
        Edge oneToThree = new Edge(1,3, 2);
        Edge oneToFour = new Edge(1,4, 8);
        Edge twoToFive = new Edge(2,5, 10);
        Edge threeToFour = new Edge(3,4, 9);
        Edge fourToTwo = new Edge(4,2, 6);
        Edge fourToFive = new Edge(4,5,10);

        Network network = new Network(6);
        network.addEdge(zeroToOne);
        network.addEdge(zeroToThree);
        network.addEdge(oneToTwo);
        network.addEdge(oneToThree);
        network.addEdge(oneToFour);
        network.addEdge(twoToFive);
        network.addEdge(threeToFour);
        network.addEdge(fourToTwo);
        network.addEdge(fourToFive);

        display(network);

        System.out.println(String.format("Maximum flow of network is %.2f.",
                new MaximumFlow().of(network, 0, 5)));

    }

    public static void display(Network network){

//        for (int i=0; i<network.getNoOfVertices(); i++) {
//
//            System.out.printf("----- VERTEX %d -----\n\n", i);
//
//            Iterator<Edge> iterator = network.adj(i).iterator();
//            while(iterator.hasNext()){
//                Edge edge = iterator.next();
//                System.out.printf("Edge incident from %d to %d with capacity of %.2f.\n",edge.getFrom(), edge.getFrom(), edge.getCapacity());
//            }
//
//            System.out.println();
//
//        }

        SparseMultigraph<Integer, Edge> graph = new SparseMultigraph();
        for(int i=0; i<network.getNoOfVertices(); i++){
            graph.addVertex(i);
            for(Edge edge: network.getAdj()[i]){
                graph.addEdge(edge, edge.getFrom(), edge.getTo(), EdgeType.DIRECTED);
            }
        }

        System.out.println(graph.toString());

        Layout<Integer, String> layout = new CircleLayout(graph);
        layout.setSize(new Dimension(500,500)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
        BasicVisualizationServer<Integer,String> vv =
                new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(500,500)); //Sets the viewing area size

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        // Rotating the layout such that nodes are laid out from L to R
        Dimension d = layout.getSize();
        Point2D center = new Point2D.Double(d.width/2, d.height/2);
        vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).rotate(-Math.PI, center);

        JFrame frame = new JFrame("Maximum Flow Network - Ihan Dilnath");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);

    }

}
