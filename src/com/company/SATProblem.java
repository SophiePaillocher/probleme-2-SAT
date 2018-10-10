package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class SATProblem {

    int literalNumber;

    public List<String> read (String fileName){
        Path formulaPath = Paths.get(fileName);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(formulaPath);
        } catch (IOException e) {
            System.out.println("Impossible de lire le fichier");
        }
        return lines;
    }



    /**
     * Construit le graphe des implications à partir d'un fichier de données
     * @return le graphe d'implication
     */
    public Graph implicationGraph(String fileName) {
        List<String> lines = read(fileName);

        Graph graph = null;

        for (String line : lines){

            if (graph == null) {

                try {
                    literalNumber = Integer.valueOf(line);
                } catch (NumberFormatException e){
                    System.out.println("Mauvais format de fichier");
                }

                graph = new Graph(literalNumber*2+1);
                continue;
            }


            String[] split = line.split(" ");
            int litteral1 = Integer.valueOf(split[0]);
            int litteral2 = Integer.valueOf(split[1]);

            graph.addArc(-litteral1 + literalNumber , litteral2 + literalNumber ,"");
            graph.addArc(-litteral2 + literalNumber , litteral1 + literalNumber , "");

        }
        return graph;
    }


    /**
     * Sert à savoir si un problème 2-SAT est satisfaisable ou non
     * @param stronglyConnectedComponents La liste des composantes fortement connexes du graphe associé au problème
     * @return true si le problème est satisfaisable et false sinon
     */
    public boolean isSatisfiable(LinkedList<LinkedList<Integer>> stronglyConnectedComponents){



        for (int iterator = 0; iterator < stronglyConnectedComponents.size(); iterator++){
            LinkedList<Integer> stronglyConnectedComponent = stronglyConnectedComponents.get(iterator);

            for (int iterator2 = 0; iterator2 < stronglyConnectedComponent.size(); iterator2++)
                stronglyConnectedComponent.set(iterator2, stronglyConnectedComponent.get(iterator2) - literalNumber);


            for (int literal = 1; literal <= literalNumber ; literal++)
                if (stronglyConnectedComponent.contains(literal) & stronglyConnectedComponent.contains(-literal))
                    return false;
        }

        return true;
    }

}

