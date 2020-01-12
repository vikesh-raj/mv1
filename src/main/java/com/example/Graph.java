package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Graph {
    public List<String> vertices = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();

    public static class Edge {
        int source;
        int target;
        int weight;

        public Edge(int source, int target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public String toString(List<String> vertices) {
            return "( " + vertices.get(this.source) + ", " + vertices.get(this.target) + ", " + this.weight + " )"; 
        }
    }

    public int vertexIndex(String vertex) {
        for (int i = 0; i < this.vertices.size(); i++) {
            String v = this.vertices.get(i);
            if (v.equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    public Edge newEdge(String source, String target, int weight) {
        int si = this.vertexIndex(source);
        int ti = this.vertexIndex(target);
        return new Edge(si, ti, weight);
    }

    public void showGraph() {
        System.out.println("Vertices = " + String.join(", ", this.vertices));
        System.out.print("Edges = ");
        for (Edge edge: this.edges) {
            System.out.print(edge.toString(this.vertices));
        }
        System.out.println("");
    }

    public List<Edge> findEdges(int vertex) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (Edge edge: this.edges) {
            if(edge.source == vertex) {
                edges.add(edge);
            }
        }
        return edges;
    }

    public List<Edge> sortedEdges() {
        this.edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return Integer.compare(e1.weight, e2.weight);
            }
        });
        return this.edges;
    }

    public boolean hasEdge(int source, int target) {
        for (Edge e: this.edges) {
            if(e.source == source && e.target == target) {
                return true;
            }
        }
        return false;
    }

    public void addReverseEdges() {
        List<Edge> reverseEdges = new ArrayList<>();
        for(Edge e: this.edges) {
            if(!hasEdge(e.target, e.source)) {
                reverseEdges.add(new Edge(e.target, e.source, e.weight));
            }
        }
        this.edges.addAll(reverseEdges);
    }
}
