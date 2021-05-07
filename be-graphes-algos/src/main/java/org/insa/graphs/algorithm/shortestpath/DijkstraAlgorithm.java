package org.insa.graphs.algorithm.shortestpath;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.AbstractSolution.Status;
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
        BinaryHeap<Label> labels = new BinaryHeap<Label>(); //création du tas binaire de labels ( labels )
        HashMap <Node,Label> hashm = new HashMap<Node,Label>(); // Création de la hashmap (hashm)
        Graph g = data.getGraph(); // g prends le graphe des données
        labels.insert(new Label(data.getOrigin())); // On insert dans le tas l'origine de notre graphe
        // hashm.put(
        for(Node node : g.getNodes()) { // on parcours les différents node du graphe g
        	hashm.put(node, new Label(node)); // pour chaque noeud on créé un label associé dans la hashmap
        }
        Label Petit = null; // création  d'un label (Petit) désignant le plus petit
        boolean obtain=false;
        Node depart = data.getOrigin(); // Noeud de départ
        Node dest = data.getDestination(); // noeud de fin
        while (!labels.isEmpty() && !(obtain)) { // tant que le tas n'est pas vide on continue 
        	Petit = labels.deleteMin(); // le plus petit label est remove et donné à Petit
        	if (Petit.getSommet() == dest) break; // Si notre plus petit est arrivé à destination alors le while se termine
        	Petit.setMark(true); // Le label a été visité
        	for(Arc arc : Petit.getSommet().getSuccessors()) { // pour tous le succeurs du Label observé (Petit) on regarde : si ils ont été marqué, 
                	 if (!hashm.get(arc.getOrigin()).getMark()){// get key label marque (Marqué ou non ? ) si non :
                		Node node = arc.getDestination(); // on recupère leur destination 
                		Label lab = hashm.get(node); // lab  prends le label associé à node (destination)
                        double Cout = Petit.getCost() + arc.getLength(); // on recupère le Cout  de passage dans l'arc et le cout pour arriver jusqu'à PETIT
                        lab.setMark(true);			// il faut marquer le label 
                        if (lab.getMark()) continue; // si le label a bien été marqué on continue
                        
						if (node==dest) obtain=true;		
                        if (lab.getCost() > Cout){ // Si le cout de ce chemin est moindre que celauienregistré auparavant alors : on l'enregistre dans la Heap
                        	// le nouveau label remplace l'ancien
                        	
                        	lab.setCost(Cout);
                            lab.setFather(arc);
                            lab.setSommet(node);
                            notifyNodeReached(node);//
                            if (node.hasSuccessors() || node==dest) { // Si il a des successeurs ou si le noeud est la destination alors on l'insert
                            	labels.insert(lab); // enregistrement dans la heap 
                            }
                            // Il faut donc que j'enregistre dans la hashmap le nouveau chemin le plus rapide menant au node 
                            hashm.put(node, lab);
                            
                            // Si ma destination a un parent alors je l'enlève de la heap à ajouter
                            
                            // remove du labels
                        }
                	 }
        	}
        }
        // gérer le cas ou il n'y a pas de plus court chemin :si (pas de marquage sur le label de Dest alors Inf 
        if(!(hashm.get(dest).getMark())) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE); 
        }else {
        	this.notifyDestinationReached(data.getDestination());
	        // parcourir tous les pères du label associé au noeud destination Hashmap 
        	ArrayList<Arc> list = new ArrayList<Arc>();  // commence à Dest
	        
	        Arc parcours = hashm.get(dest).getFather();
	        list.add(parcours);
	        while (!(parcours.getOrigin()==depart)) {// tant que le noeud à l'origine de l'arc n'est pas le départ on continue
	        	parcours = hashm.get(parcours.getOrigin()).getFather(); // on récupère l'arc père
	        	list.add(parcours); // on rajoute à la liste l'arc
	        }
	        	
	        Collections.reverse(list);// inverse la liste des arcs obtenu
		    solution = new ShortestPathSolution(data,Status.OPTIMAL,new Path(data.getGraph(),list)); 	
	     }
	        	// pour chaque noeud on regarde le label associé dans la hashmap 
	        // collections.reverse pour avoir la liste d'arc inversé 
	       
 
        return solution;
        
    }

}
