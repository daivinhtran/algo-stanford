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
    private static int t = 0;
    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph();
        PriorityQueue<Integer> maxSizesOfScc = new PriorityQueue<>(Collections.reverseOrder());
        graph.makeGraph("input.txt");

        Graph revGraph = graph.makeRevGraph();

        int N = graph.size();

        Stack<Integer> finishingTimeStack = DFS(revGraph);
        System.out.println(finishingTimeStack);
        boolean[] visisted = new boolean[N + 1];

        maxSizesOfScc = findSCC(graph, finishingTimeStack);
        for (int i = 0; i < 5; i++) {
            System.out.println(maxSizesOfScc.poll());
        }
    }

    public static Stack<Integer> DFS(Graph g) {
        int N = g.size();
        int s = N;
        boolean[] visisted = new boolean[N + 1];
        Stack<Integer> myStack = new Stack<>();
        Stack<Integer> finishStack = new Stack<>();

        for (int i = N; i >= 1; i--) {
            if (!visisted[i]) {
                myStack.push(i);
                visisted[i] = true;

                while (!myStack.isEmpty()) {
                    int v = myStack.peek();
                    // not outgoing edges from v
                    ArrayList<Integer> heads = g.getHeads(v);
                    if (heads == null) {
                        finishStack.push(myStack.pop());
                    } else {
                        boolean allAdjacentVisited = true;
                        for (int head: heads) {
                            if (!visisted[head]) {
                                allAdjacentVisited = false;

                                myStack.push(head);
                                visisted[head] = true;
                            }
                        }

                        if (allAdjacentVisited) {
                            finishStack.push(myStack.pop());
                        }
                    }
                }
            }
        }

        return finishStack;
    }

    public static PriorityQueue<Integer>
            findSCC(Graph g, Stack<Integer> finishingTimeStack) {

        PriorityQueue<Integer> maxPQ = 
            new PriorityQueue<>(Collections.reverseOrder());

        int N = finishingTimeStack.size();
        boolean[] visited = new boolean[N + 1];

        int tail;
        int counter = 0;

        while (!finishingTimeStack.isEmpty()) {
            int leader = finishingTimeStack.peek();
            visited[leader] = true;
            counter = 1;

            System.out.println("leader: " + leader);

            ArrayList<Integer> heads = g.getHeads(leader);

            while (heads != null) {
                for (int head: heads) {
                    if (!visited[head]) {
                        visited[head] = true;
                        heads = g.getHeads(head);
                        counter++;
                        break;
                    } else {
                        heads = null;
                    }
                }

            }
            maxPQ.add(counter);
            for (int i = 0; i < counter; i++) {
                finishingTimeStack.pop();
            }
        }
        return maxPQ;
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