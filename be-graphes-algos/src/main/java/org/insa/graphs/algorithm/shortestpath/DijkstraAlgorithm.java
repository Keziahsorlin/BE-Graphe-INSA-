package org.insa.graphs.algorithm.shortestpath;


import java.util.HashMap;
import java.util.List;

import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
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
        Graph g = data.getGraph();
        labels.insert(new Label(data.getOrigin()));
        // hashm.put(
        for(Node node : g.getNodes()) {
        	hashm.put(node, new Label(node));
        }
        Label Petit = null;
        // Associer Node à Label par l'intérmédiaire de la HashMap
        // 
        Node depart = data.getOrigin();
        Node dest = data.getDestination();
        while (!labels.isEmpty()) {
        	Petit = labels.deleteMin();
        	if (Petit.getSommet() == dest) break;
        	Petit.setMark(true);
        	for(Arc arc : Petit.getSommet().getSuccessors()) {
                	 if (!hashm.get(arc.getOrigin()).getMark()){// get key label marque
                		Node node = arc.getDestination();
                		Label lab = hashm.get(node);
                        double Cout = Petit.getCost() + arc.getLength();
                        
                        if (lab.getMark()) continue;

                        if (lab.getCost() > Cout){
                        	lab.setCost(Cout);
                            lab.setFather(arc);
                            lab.setSommet(node);
                            notifyNodeReached(node);
                            labels.insert(lab);
                            // Si ma destination a un parent alors je l'enlève de la heap à ajouter
                            // if dest!=null
                            // remove du labels
                        }
                	 }
        	}
        }
        // tant ue le noeud que j'atteins n'est pas le noeud de départ dans la data
        Node fin = dest; //Le point le plus court de notre destination
        Path Chemin_Optim = null; // path qu'il faut suivre
        while(fin != depart) {
        	
        }
        

       	          		//if (y.getcost() > x.getcost() + coutArc(x,y)) //Compareto {
       	          		//cout (y) = cout(x) + coutArc(x,y);
       	          		//if exist(y,tas) {
       	          		//	update(y,tas)
       	          		//}else{
       	          		//		insert(y,tas);
       	          		
        
        // Tant que "heap pas vide": mettre a jour les labels
        // Il faut que je code le dijkstra en remplaçaant toutes mes variables par les variables d'accès hashmap
        /*
         * while (sommet not(GetMark) {
         * 	x=min(tas);
         * 	x.SetMark(true);
         * 	for( successors) {
         * 	 if !(y.getMark()){
	         * 	if (y.getcost() > x.getcost() + coutArc(x,y)) //Compareto {
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
