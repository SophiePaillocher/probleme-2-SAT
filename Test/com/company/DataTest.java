package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    Data d = new Data();
    @Test
    public void dataTest(){

        Graph g = d.implicationGraph();
        System.out.println(g.toString());
    }
}