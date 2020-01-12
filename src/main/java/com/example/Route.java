package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Route {

    static Graph createGraph() {
        Graph g = new Graph();
        g.vertices = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        g.edges = new ArrayList<>(Arrays.asList(
            g.newEdge("a", "b", 2),
            g.newEdge("a", "c", 10),
            g.newEdge("b", "c", 4)
        ));
        return g;
    }

    static class VertexInfo {
        int index;
        int distance;
        int path;
        boolean visited;
    }
    static int maxDistance = 1000;

    static int minVertex(List<VertexInfo> vs, Graph g) {
        int min = maxDistance;
        int index = -1;
        for(int i = 0; i < vs.size(); i++) {
            VertexInfo vi = vs.get(i);
            vi.index = i;
            if (!vi.visited && vi.distance < min) {
                min = vi.distance;
                index = i;
            }
        }
        return index;
    }

    static List<VertexInfo> initVertexInfo(int start, Graph g) {
        List<VertexInfo> vs = new ArrayList<>(g.vertices.size());
        for(int i = 0; i < g.vertices.size(); i++) {
            VertexInfo vi = new VertexInfo();
            vi.index = i;
            vi.path = -1;
            vi.distance = maxDistance;
            if (vi.index == start) {
                vi.distance = 0;
            }
            vs.add(vi);
        }
        return vs;
    }

    static void calculateShortest(String start, Graph g) {
        int verticesCount = g.vertices.size();
        int startIndex = g.vertexIndex(start);
        if (startIndex == -1) {
            System.out.println("No such vertex : " + start);
            return;
        }
        List<VertexInfo> vs = initVertexInfo(startIndex, g);
        for (int i = 0; i < verticesCount; i++) {
            int next = minVertex(vs, g);
            if (next < 0) {
                break;
            }
            VertexInfo u = vs.get(next);
            u.visited = true;

            for (Graph.Edge edge: g.edges) {
                if (edge.source != u.index) {
                    continue;
                }
                int newDistance = u.distance + edge.weight;
                VertexInfo v = vs.get(edge.target);
                if (!v.visited && newDistance < v.distance) {
                    v.path = u.index;
                    v.distance = newDistance;
                }
            }
        }

        // print path
        for (int i = 0; i < verticesCount; i++) {
            VertexInfo vi = vs.get(i);
            List<String> path = new ArrayList<>();
            String vertex = g.vertices.get(vi.index);
            int pathIndex = vi.path;
            while(pathIndex != -1) {
                path.add(0, g.vertices.get(pathIndex));
                pathIndex = vs.get(pathIndex).path;
            }
            if (vi.path != -1 || vi.index == startIndex) {
                path.add(vertex);
            }
            String p = String.join(" -> ", path);
            if (path.isEmpty()) {
                p = "No path.";
            }
            System.out.println("Vertex " + vertex + " Distance : " + vi.distance + " , Path : " + p);
        }
    }

    public static void main(String[] args) {
        Graph g = createGraph();
        g.addReverseEdges();
        g.showGraph();
        calculateShortest("a", g);
        calculateShortest("b", g);
    }
}