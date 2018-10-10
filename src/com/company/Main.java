package com.company;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        SATProblem d = new SATProblem();
        Graph graph;
        if (args == null)
            graph = d.implicationGraph("formule-2-sat.txt");
        else
            graph = d.implicationGraph(args[0]);

        LinkedList<LinkedList<Integer>> stronglyConnectedComponents = graph.stronglyConnectedComponent();

        if(d.isSatisfiable(stronglyConnectedComponents))
            System.out.println("Le problème est satisfaisable.");
        else
            System.out.println("Le problème n'est pas satisfaisable.");
    }
}
