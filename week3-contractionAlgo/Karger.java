c

public class Karger {
    public static void main(String[] args) throws FileNotFoundException {
        final long startTime = System.currentTimeMillis();
        PriorityQueue<Integer> cuts = new PriorityQueue<>();

        // Graph graph = makeGraph("input.txt");
        for (int i = 0; i < 100; i++) {
            //graph is now a Graph object
           Graph graph = makeGraph("input.txt");
            // Graph g = graph.copy(graph);
             
            // karger calculate the min cut and change the graph
            cuts.add(karger(graph));
        }
        System.out.println("mincut is " + cuts.peek());
        final long endTime = System.currentTimeMillis();
        System.out.println("TOtal time: " + (endTime - startTime));
    }

    private static int karger(Graph gr) {
        Graph graph = gr;
        Random random = new Random();

        while (graph.size() > 2) {
            ArrayList<Integer> keys = graph.getKeys();
            int start = keys.get(random.nextInt(keys.size()));
            ArrayList<Integer> edges = graph.getEdges(start);
            int finish = edges.get(random.nextInt(edges.size()));

            // adding edges from the finish node
            for (int edge: graph.getEdges(finish)) {
                if (edge != start) {
                    graph.addEdge(start, edge);
                }
            }

            // delete the references to the finish node
            for (int source: graph.getEdges(finish)) {
                for (int i = 0; i < graph.getEdges(source).size(); i++) {
                    if (graph.getEdges(source).get(i) == finish) {
                        graph.removeEdge(source, i);
                    }
                }
                if (source != start) {
                    graph.addEdge(source, start);
                }
            }
            graph.removeVertice(finish);
        }
        int mincut = graph.getEdges(graph.getKeys().get(0)).size();
        return mincut;
    }
    private static Graph makeGraph (String filename) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filename));

        Graph graph = new Graph();

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Scanner lineScanner = new Scanner(line);

            int source = Integer.parseInt(lineScanner.next());

            graph.map.put(source, new ArrayList<Integer>());

            while (lineScanner.hasNext()) {
                int token = Integer.parseInt(lineScanner.next());
                graph.map.get(source).add(token);
            }
        }
        return graph;
    }
}

class Graph implements Cloneable {
    public HashMap<Integer, ArrayList<Integer>> map;

    public Graph() {
        this.map = new HashMap<Integer, ArrayList<Integer>>();
    }

    public Graph(HashMap<Integer, ArrayList<Integer>> map) {
        this.map = new HashMap<Integer, ArrayList<Integer>>(map);
    }

    public int size() {
        return this.map.size();
    }

    public void deleteNode(int vertice) {
        this.map.remove(vertice);
    }

    public String toString() {
        for (int key : getKeys()) {
            System.out.print(key + ": ");
            System.out.println(this.map.get(key));
        }
        return this.map.toString();
    }

    public ArrayList<Integer> getKeys() {
        return new ArrayList<Integer>(this.map.keySet());
    }

    public ArrayList<Integer> getEdges(int start) {
        return this.map.get(start);
    }

    public void addEdge(int start, int newEdge) {
        this.map.get(start).add(newEdge);
    }

    public void removeEdge(int start, int edgeIndex) {
        this.map.get(start).remove(edgeIndex);
    }

    public void removeVertice(int vertice) {
        this.map.remove(vertice);
    }

    public Graph copy(Graph g) {
        HashMap<Integer, ArrayList<Integer>> oldMap = map;
        Graph newGraph = new Graph();
        HashMap<Integer, ArrayList<Integer>> newMap = newGraph.map;
        
        //loop through every entry in oldMap and add each entry to newMap then return newGraph
        for (Entry<Integer, ArrayList<Integer>> entry: oldMap.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Integer> oldValue = entry.getValue();
             
            oldValgue.stream().forEach(i -> newValue.add(i));
            

            newMap.put(key, newValue);
        }

        return newGraph;
    }
}