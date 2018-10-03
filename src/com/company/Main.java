package com.company;

public class Main {
    public static void main(String[] args) {
        Data d = new Data();
        Graph g = d.implicationGraph();
        System.out.println(g.toString());
    }
}
