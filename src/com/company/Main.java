package com.company;

public class Main {
    public static void main(String[] args) {
        Data d = new Data();
        Graph g = d.implicationGraph();
        System.out.println("Liste d'incidence du graphe des implications : " + "\n"+ g.toString());
        System.out.println("Composantes fortement connexes : " + g.stronglyConnectedComponent());
    }
}
