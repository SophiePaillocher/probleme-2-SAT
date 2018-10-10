package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph<Label> {

    private class Edge {
        public int source;
        public int destination;
        public Label label;

        public Edge(int from, int to, Label label) {
            this.source = from;
            this.destination = to;
            this.label = label;
        }
    }

    private int cardinal;
    private ArrayList<LinkedList<Edge>> incidency;


    public Graph(int size) {
        cardinal = size;
        incidency = new ArrayList<LinkedList<Edge>>(size+1);
        for (int i = 0;i<cardinal;i++) {
            incidency.add(i, new LinkedList<Edge>());
        }
    }

    /**
     *Donne le cardinal du graphe
     * @return le cardinal du graphe
     */
    public int order() {
        return cardinal;
    }

    /**
     * Transpose un graphe
     * @return le graphe transposé
     */
    public Graph<Label> transpose() {
        Graph<Label> result = new Graph<>(cardinal);
        for (LinkedList<Edge> out_v : incidency) {
            for (Edge e : out_v) {
                result.addArc(e.destination,e.source,e.label);
            }
        }
        return result;
    }

    /**
     * Ajoute une arrête à la liste d'incidence du graphe
     * @param source le sommet source de l'arrête
     * @param dest le sommet destination de l'arrête
     * @param label le label de l'arrête
     */
    public void addArc(int source, int dest, Label label) {
        incidency.get(source).addLast(new Edge(source,dest,label));
    }


    public void innerDfs(int source,
                         ArrayList<Boolean> visited,
                         LinkedList<Integer> result)
    {
        if (visited.get(source)) return;
        visited.set(source,true);
        for (Edge out : incidency.get(source)) {
            innerDfs(out.destination,visited,result);
        }
        result.addLast(source);
        return;
    }


    public LinkedList<LinkedList<Integer>>
    fullDfsFromList(LinkedList<Integer> vertices)
    {
        ArrayList<Boolean> visited = new ArrayList<>(cardinal);
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();
        for (int i = 0; i < cardinal; i++) {
            visited.add(i,false);
        }

        for (Integer source : vertices) {
            if (visited.get(source)) continue;
            LinkedList<Integer> partial = new LinkedList<>();
            innerDfs(source,visited,partial);
            result.addLast(partial);
        }
        return result;

    }


    public LinkedList<LinkedList<Integer>> fullDfs() {
        LinkedList<Integer> vertices = new LinkedList<>();
        for (int i = 0; i < cardinal; i++) {
            vertices.addLast(i);
        }
        return fullDfsFromList(vertices);
    }


    public LinkedList<LinkedList<Integer>>
    stronglyConnectedComponent(Graph<Label> backward)
    {
        LinkedList<LinkedList<Integer>> firstOrdering = fullDfs();
        LinkedList<Integer> flattening = new LinkedList<>();
        for (LinkedList<Integer> vertices : firstOrdering) {
            for (Integer vertex : vertices) {
                flattening.addFirst(vertex);
            }
        }
        return backward.fullDfsFromList(flattening);
    }

    /**
     * Permet d'obtenir la liste des composantes fortement connexes d'un graphe
     * @return la liste des composantes fortement connexes
     */
    public LinkedList<LinkedList<Integer>> stronglyConnectedComponent() {
        return this.stronglyConnectedComponent(this.transpose());
    }


    public String toString() {
        String result = new String("");
        result.concat(cardinal + "\n");
        for (int i = 0; i<cardinal;i++) {
            for (Edge e : incidency.get(i)) {
                result += e.source + " " + e.destination + " "
                        + e.label.toString() + "\n";
            }
        }
        return result;

    }


    public interface ArcFunction<Label,K> {
        public K apply(int source, int dest, Label label, K accu);
    }


    public interface ArcConsumer<Label> {
        public void apply(int source, int dest, Label label);
    }


    public <K> K foldEdges(ArcFunction<Label,K> f, K init) {
        for (LinkedList<Edge> adj : this.incidency) {
            for (Edge e : adj) {
                init = f.apply(e.source, e.destination, e.label, init);
            }
        };
        return init;
    }

    
    public void iterEdges(ArcConsumer<Label> f) {
        for (LinkedList<Edge> adj : this.incidency) {
            for (Edge e : adj) {
                f.apply(e.source, e.destination, e.label);
            }
        }
    }
}
