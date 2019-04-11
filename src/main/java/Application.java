import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

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

        exportNetworkToFile(network, "before");
        new MaximumFlow().of(network, 0, 5);
        exportNetworkToFile(network, "after");

    }

    public static void exportNetworkToFile(Network network, String title){

        Graph<Integer, Edge> graph = new DirectedSparseGraph<>();

        for(int i=0; i<network.getNoOfVertices(); i++){
            graph.addVertex(i);
            for(Edge edge: network.getAdj()[i]){
                graph.addEdge(edge, edge.getFrom(), edge.getTo(), EdgeType.DIRECTED);
            }
        }

        Layout<Integer, String> layout = new CircleLayout(graph);
        layout.setSize(new Dimension(500,500)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
        VisualizationImageServer<Integer,String> vis =
                new VisualizationImageServer<>(layout, layout.getSize());
        vis.setPreferredSize(new Dimension(500,500)); //Sets the viewing area size
        vis.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vis.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());
        vis.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        BufferedImage image = (BufferedImage) vis.getImage(
                new Point2D.Double(vis.getGraphLayout().getSize().getWidth() / 2,
                        vis.getGraphLayout().getSize().getHeight() / 2),
                new Dimension(vis.getGraphLayout().getSize()));

        File outputfile = new File(String.format("network_output\\%s_network_%s.png",title, new Date().toString().replaceAll("[\\:\\s]","").trim()));
        outputfile.getParentFile().mkdirs();

        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            // Exception handling
        }



    }

}
