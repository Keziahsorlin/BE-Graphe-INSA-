package org.insa.graphs.algorithm.shortestpath;


import java.util.HashMap;
import java.util.List;

import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Label;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        BinaryHeap<Label> labels = new BinaryHeap<Label>();
        HashMap <Node,Label> hashm = new HashMap<Node,Label>();
        
        // Associer Node à Label par l'intérmédiaire de la HashMap
        // 
        // Tant que "heap pas vide": mettre a jour les labels
        // Il faut que je code le dijkstra en remplaçaant toutes mes variables par les variables d'accès hashmap
        /*
         * while (sommet not(get(Node).mark) {
         * 	x=min(tas);
         * 	mark(x) = true;
         * 	for( successors) {
         * 	 if !(mark(y)){
	         * 	if cout(y) > cout(x) + coutArc(x,y) {
	         * 		cout (y) = cout(x) + coutArc(x,y);
	         * 		if exist(y,tas) {
	         * 			update(y,tas)
	         * 		}else{
	         * 				insert(y,tas);
	         * 		}
         *		}
         *	 }
         *	}
         *}
         */
       
        
        
        return solution;
        
    }

}
