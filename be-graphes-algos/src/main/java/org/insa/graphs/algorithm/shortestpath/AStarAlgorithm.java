package org.insa.graphs.algorithm.shortestpath;

import java.util.HashMap;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {
//data.getMaximumspeed
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    // Avec cout en rapidité
    @Override
    public void initialiser(BinaryHeap<Label>  labels, HashMap<Node,Label> hashm ){
    	Graph g = data.getGraph();
    	final ShortestPathData data = getInputData();
    	Integer vitesse = data.getMaximumSpeed();
    	// SI neg trouver la vitesse max du graphe 
    	if (vitesse<0) {
    		vitesse=data.getGraph().getGraphInformation().getMaximumSpeed();	
    	}
    	if (data.getMode()==AbstractInputData.Mode.LENGTH) {
	    	for(Node node : g.getNodes()) { // on parcours les différents node du graphe g
	        	hashm.put(node, new LabelStar(node,node.getPoint().distanceTo(data.getDestination().getPoint())));// pour chaque noeud on créé un label associé dans la hashmap
	        }
    	}else {
    		for(Node node : g.getNodes()) { // on parcours les différents node du graphe g
    			
	        	hashm.put(node, new LabelStar(node,node.getPoint().distanceTo(data.getDestination().getPoint())/vitesse)); // pour chaque noeud on créé un label associé dans la hashmap
	        }
    	}
    	
    	
    }
}
