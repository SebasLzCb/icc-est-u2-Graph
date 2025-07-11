package controller;

import models.Node;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private Set<Node> nodes;

    public Graph() {
        nodes = new HashSet<>();
    }

    public Node addNode(int value) {
        Node node = new Node(value);
        nodes.add(node);
        return node;
    }

    public void addEdge(Node src, Node dest) {
        src.addNeighbor(dest);
        dest.addNeighbor(src);
    }

public void printGraph() {
    List<Node> ordenados = nodes.stream()
        .sorted(Comparator.comparingInt(Node::getValue))
        .toList();

    for (Node node : ordenados) {
        System.out.print("Vertex " + node.getValue() + ":");
        List<Node> vecinos = node.getNeighbors().stream()
            .sorted(Comparator.comparingInt(Node::getValue))
            .toList();
        for (Node vecino : vecinos) {
            System.out.print(" -> " + vecino.getValue());
        }
        System.out.println();
    }
}


    public void getDFS(Node startNode) {
        Set<Node> visited = new HashSet<>();
        getDFSUtil(startNode, visited);
    }

    private void getDFSUtil(Node node, Set<Node> visited) {
        if (node == null || visited.contains(node)) return;
        System.out.print(node.getValue() + " ");
        visited.add(node);
        for (Node neighbor : node.getNeighbors()) {
            getDFSUtil(neighbor, visited);
        }
    }

    public void getBFS(Node startNode) {
        Set<Node> visited = new HashSet<>();
        Set<Node> queue = new HashSet<>();
        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            Node current = queue.iterator().next();
            queue.remove(current);
            System.out.print(current.getValue() + " ");

            for (Node neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    public int[][] getAdjacencyMatrix() {
        int size = nodes.size();
        int[][] matrix = new int[size][size];
        Node[] nodeArray = nodes.toArray(new Node[0]);

        for (int i = 0; i < size; i++) {
            Node node = nodeArray[i];
            for (Node neighbor : node.getNeighbors()) {
                int j = findIndex(nodeArray, neighbor);
                if (j != -1) {
                    matrix[i][j] = 1;
                }
            }
        }
        return matrix;
    }

    public void printAdjacencyMatrix() {
        int[][] matrix = getAdjacencyMatrix();
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    private int findIndex(Node[] array, Node node) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(node)) return i;
        }
        return -1;
    }
}
