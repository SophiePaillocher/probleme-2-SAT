package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Data {

    public Graph implicationGraph() {
        Path formulaPath = Paths.get("formule-2-sat.txt");
        List<String> lines = null;
        try {
            lines = Files.readAllLines(formulaPath);
        } catch (IOException e) {
            System.out.println("Impossible de lire le fichier");
        }
        assert lines != null;
        int nombreVariables = Integer.valueOf(lines.get(0));
        Graph graph = new Graph(nombreVariables*2);
        for (int i = 1 ; i < lines.size() ; i++){
            String[] split = lines.get(i).split(" ");
            int litteral1 = Integer.valueOf(split[0]);
            int litteral2 = Integer.valueOf(split[1]);
            graph.addArc(-litteral1, litteral2,"");
            graph.addArc(-litteral2, litteral1, "");
        }
        return graph;
    }

}

