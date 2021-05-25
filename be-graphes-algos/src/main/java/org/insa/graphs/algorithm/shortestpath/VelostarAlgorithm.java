package org.insa.graphs.algorithm.shortestpath;

import java.util.HashMap;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.RoadInformation.RoadType;

public class VelostarAlgorithm extends DijkstraAlgorithm {
	// on va jouer sur des coefs différents en fonction de la route à prendre tq : 
//data.getMaximumspeed
    public VelostarAlgorithm(ShortestPathData data) {
        super(data);
    }
    // Avec cout en rapidité
    @Override
    public void initialiser(BinaryHeap<Label>  labels, HashMap<Node,Label> hashm ){
    	Graph g = data.getGraph();
    	final ShortestPathData data = getInputData();
    	Integer vitesse = 25; // partons sur le principe que Vmoy à vélo 25km/h et que la vitesse autorisée sera toujours au dessus de 30 quelque soit l'arc (rationnel
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
	@Override	
	public double Evaluateur(Arc arc,AbstractInputData.Mode mode){
		double Cout=0;
		int coef = 2;
		RoadType type = arc.getRoadInformation().getType();
    	if (type == RoadType.CYCLEWAY) {
			coef = 1;
        }else if (type == RoadType.MOTORWAY){
			coef =1000000;
		} else if (type == RoadType.PEDESTRIAN ){
			coef = 1;
		}  else if (type == RoadType.TRUNK){
			coef =1000000;// interdit donc au pire des cas on roule à côté illégalement sur la bande d'arrêt d'urgence
		}  else if (type == RoadType.PRIMARY){
			coef =1000000;// interdit donc au pire des cas on roule à côté illégalement sur la bande d'arrêt d'urgence
		}  else if ((type == RoadType.SECONDARY)  ||  (type == RoadType.SECONDARY_LINK) || (type == RoadType.COASTLINE) ){
			coef =10; // assez dangereux
		} else if ((type == RoadType.TERTIARY) ){
			coef= 4;//moins dangereux
		}	else if ((type == RoadType.UNCLASSIFIED)|| (type == RoadType.SERVICE)  ){
			coef= 8;//parfois entre deux route primaire reste dangereux
		}else if ((type == RoadType.RESIDENTIAL) || (type == RoadType.LIVING_STREET)||  (type == RoadType.ROUNDABOUT) ){
			coef= 3;//moins dangereux
		}else if ((type == RoadType.TRACK) ){
			coef= 2;//très peu emprunté par les voitures
		}else{
			coef = 100000; // c'est dangereux est à éviter (il reste les liens entre autoroute etc..)
		}  
		if (mode == AbstractInputData.Mode.LENGTH) {
			Cout=arc.getLength()*(double)(coef); 
		}else {
			Cout=arc.getMinimumTravelTime()*(double)(coef);
		}
		return Cout;
	} 
}
