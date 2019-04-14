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
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Network network = getUserInput();
        exportNetworkToFile(network, "before");
        System.out.println(String.format("Maximum flow of network is %.2f.", new MaximumFlow().of(network, 0, network.getNoOfVertices() - 1)));
        exportNetworkToFile(network, "after");
    }

    private static Network getUserInput() {

        Scanner sc = new Scanner(System.in);
        System.out.print("" +
                "==================================================\n" +
                "MAXIMUM FLOW NETWORK by Ihan Dilnath - W1673607\n" +
                "5SENG002 - ALGORITHMS: THEORY, DESIGN AND IMPLEMENTATION\n" +
                "\n");

        int V = 0;
        do{
            System.out.print("Please enter the number of vertices in your flow network: ");
            try {
                V = sc.nextInt();
                if(V <= 0){
                    throw new Exception("No. of Vertices (V) cannot be zero. Please enter a correct value again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter numerical value only.");
                sc.nextLine();
                continue;
            } catch (Exception e){
                System.out.println(e.getMessage());
                continue;
            }
        }while(V <= 0);

        System.out.println();

        boolean _continue = true;
        List<Edge> listOfEdges = new ArrayList<>();
        int from;
        int to;
        double capacity;
        do{

            while (true) {
                System.out.print(String.format("Enter the number of vertex from 0 to %d (inclusive) from which the edge originates: ", V-1));
                try {
                    from = sc.nextInt();
                    if (from < 0 || from >= V) {
                        throw new Exception("No. of vertex entered is invalid. Please enter a correct value again.");
                    }
                    break;
                } catch (InputMismatchException ie) {
                    System.out.println("Please enter numerical value only.");
                    sc.nextLine();
                    continue;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }

            System.out.println();

            while (true) {
                System.out.print(String.format("Enter the number of vertex from 0 to %d (inclusive) to which the edge connects: ", V-1));
                try {
                    to = sc.nextInt();
                    if (to < 0 || to >= V) {
                        throw new Exception("No. of vertex entered is invalid. Please enter a correct value again.");
                    } else if(to == from){
                        throw new Exception("You entered same value for \"to\" as \"from\". " +
                                "Thereby, you cannot have an edge connecting a node to itself. Please enter another value again.");
                    }

                    break;
                } catch (InputMismatchException ie) {
                    System.out.println("Please enter numerical value only.");
                    sc.nextLine();
                    continue;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }

            System.out.println();

            while (true) {
                System.out.print("Enter the capacity of edge: ");
                try {
                    capacity = sc.nextDouble();
                    if (capacity <= 0) {
                        throw new Exception("Capacity entered is invalid. Please enter a correct value again.");
                    }
                    break;
                } catch (InputMismatchException ie) {
                    System.out.println("Please enter numerical value only.");
                    sc.nextLine();
                    continue;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }

            listOfEdges.add(new Edge(from, to, capacity));

            System.out.println();

            sc.nextLine();

            System.out.print("Do you wish to add more edges [Y]/[N]: ");

            switch (sc.nextLine().toLowerCase()){
                case "n":
                    _continue = false;
                    System.out.println("\n");
                    break;
                default:
                    System.out.println("\n");
                    continue;
            }

        }while (_continue);

        Network network = new Network(V);
        for(Edge edge: listOfEdges){
            network.addEdge(edge);
        }

        return network;

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
            e.printStackTrace();
            System.out.println("Unable to write image to file. Please try again later");
        }

    }

}
