import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class DfsScc {
    static boolean[] visited;
    static Stack<Integer> finishingTimeStack;

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();
        PriorityQueue<Integer> maxSizesOfScc = new PriorityQueue<>(Collections.reverseOrder());
        graph.makeGraph("input.txt");

        Graph revGraph = graph.makeRevGraph();

        int N = graph.size();

        finishingTimeStack = new Stack<>();

        visited = new boolean[N + 1];

        for (int i = N; i >= 1; i--) {
            if (!visited[i])
                DFS(revGraph, i);
        }
        // System.out.println("finishingtimeStack: " + finishingTimeStack);

        visited = new boolean[N + 1];
        ArrayList<Integer> scc = new ArrayList<>();
        while (!finishingTimeStack.isEmpty()) {
            int node = finishingTimeStack.pop();
            if (!DfsScc.visited[node]) {
                scc = new ArrayList<>();
                findScc(graph, node, scc);
                //System.out.println("scc: " + scc);
                maxSizesOfScc.add(scc.size());
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(maxSizesOfScc.poll());
        }
    }

    public static void DFS(Graph g,
                    int node) {
        DfsScc.visited[node] = true;

        ArrayList<Integer> heads = g.graph.get(node);

        if (heads != null) {
            for (int head: heads) {
                if (!DfsScc.visited[head]) {
                    DFS(g, head);
                }
            }
        }

        DfsScc.finishingTimeStack.push(node);
    }

    public static void findScc(Graph g,
                        int node,
                        ArrayList<Integer> scc) {

        DfsScc.visited[node] = true;
        scc.add(node);

        ArrayList<Integer> heads = g.getHeads(node);

        if (heads != null) {
            for (int head: heads) {
                if (!DfsScc.visited[head]) {
                    findScc(g, head, scc);
                }
            }
        }
    }
}

class Graph {
    public HashMap<Integer, ArrayList<Integer>> graph;
    int size;

    public Graph() {
        this.graph = new HashMap<Integer, ArrayList<Integer>>();
        this.size = 0;
    }

    public void makeGraph (String filename) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filename));

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Scanner lineScanner = new Scanner(line);

            int tail = Integer.parseInt(lineScanner.next());
            int head = Integer.parseInt(lineScanner.next());

            if (tail > this.size) {
                this.size = tail;
            }

            if (head > this.size) {
                this.size = head;
            }

            addEdge(this, tail, head);
        }
    }

    public Graph makeRevGraph() {
        Graph revGraph = new Graph();

        ArrayList<Integer> tails = new ArrayList<Integer>(this.graph.keySet());

        for (int tail: tails) {
            ArrayList<Integer> heads = this.graph.get(tail);
            for (int head: heads) {
                addEdge(revGraph, head, tail);
            }

        }
        revGraph.size = size();
        return revGraph;
    }

    public HashMap<Integer, ArrayList<Integer>> getGraph() {
        return graph;
    }

    private boolean addEdge(Graph g, int tail, int head) {
        if (g.graph.get(tail) == null) {
            ArrayList<Integer> newArrList = new ArrayList<Integer>();
            g.graph.put(tail, newArrList);
        }

        ArrayList<Integer> heads = g.graph.get(tail);
        return heads.add(head);
    }

    public int size() {
        return this.size;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int tail : getTails()) {
            sb.append(tail);
            sb.append(": ");
            sb.append(this.graph.get(tail));
            sb.append("\n");
        }
        return sb.toString();
    }

    public ArrayList<Integer> getTails() {
        return new ArrayList<Integer>(this.graph.keySet());
    }

    public ArrayList<Integer> getHeads(int tail) {
        return this.graph.get(tail);
    }
}