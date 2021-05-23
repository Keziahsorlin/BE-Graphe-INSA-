package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.*; // assertEquals, assertTrue , fail

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Label;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.BinaryGraphReader;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.IntStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;

import org.insa.graphs.algorithm.shortestpath.*;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;

import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DijkstrastraAlgorithmTest {
	//rajouter une classe initaliser pour définir un run en a* ou dijkstra
	private static Graph graphe,graphe2;
	private static List NoeudRand ;
	static String dijk= "dijkstra";
	String astr= "astra";
	String bell = "Bellman";
	
	
	
	
	@BeforeClass
	public static void initAll() throws IOException {	
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr/"))));
		graphe = reader.read();
		GraphReader reader2 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr/"))));
		graphe2 = reader2.read();
		//ArrayList<Node> 
		
			
		
	    
	}
	
	public ShortestPathSolution Soluce(Graph g, int mode, String algo , int deb, int fin) {
		ShortestPathData data = new ShortestPathData(graphe, graphe.get(deb),graphe.get(fin), ArcInspectorFactory.getAllFilters().get(mode));
		
		// 1 Car 
		if ( algo == dijk) {
			DijkstraAlgorithm Dijkstra= new DijkstraAlgorithm(data);
			return Dijkstra.run();
		}else if ( algo == bell) {
			BellmanFordAlgorithm Bellman = new BellmanFordAlgorithm(data);
			return  Bellman.run();
			
		}else if ( algo == astr) {
			DijkstraAlgorithm AStar= new AStarAlgorithm(data);
			return AStar.run();
		}else {
			System.out.println("Erreur sur le type d'algo");
			return null;
		}
		
	}
	ArrayList<ShortestPathSolution> array= new ArrayList<ShortestPathSolution>();
	
	
	@Test
	public 	void testValidePath() throws IOException{
		//test sur chemin origine = arrivee
		for (int i=0 ; i <4; i++) {
			assertTrue((Soluce(graphe, i ,dijk , 0, 0)).getPath().isValid());
			assertTrue((Soluce(graphe, i ,dijk , 0, 359997)).getPath().isValid());
			assertTrue((Soluce(graphe, i ,dijk , 85921, 359997)).getPath().isValid());
			
		}
	
	}
	// tous les modes sont testés
	   @Test
	    public void testIsFeasible() {
		   for (int i=0 ; i <4; i++) {
	    	// courte
	    	assertEquals(Status.OPTIMAL, Soluce(graphe, i ,dijk, 0, 359997).getStatus());
	    	assertEquals(Status.OPTIMAL, Soluce(graphe, i ,astr, 0, 359997).getStatus());
	    	// + long
	    	assertEquals(Status.OPTIMAL, Soluce(graphe, i ,dijk, 285, 300).getStatus());
		   }
	    }
	   
	@Test
	public 	void testOracle() throws IOException{
		// on arrondi la distance en m 
		// le resultat Bellman et dijkstra est le même et astra dijkstra
		
		
		//distance
		
		
		assertEquals((int)((Soluce(graphe, 1 ,bell, 285, 300).getPath().getLength())), (int)((Soluce(graphe, 1 ,dijk, 285, 300).getPath().getLength())));
		assertEquals((int)((Soluce(graphe, 1 ,astr, 285, 300).getPath().getLength())), (int)((Soluce(graphe, 1 ,dijk, 285, 300).getPath().getLength())));
		
		//Temps
		assertEquals((int)((Soluce(graphe, 3 ,bell, 285, 300).getPath().getMinimumTravelTime())), (int)((Soluce(graphe, 3 ,dijk, 285, 300).getPath().getMinimumTravelTime())));
		assertEquals((int)((Soluce(graphe, 3 ,astr, 285, 300).getPath().getMinimumTravelTime())), (int)((Soluce(graphe, 3 ,dijk, 285, 300).getPath().getMinimumTravelTime())));
		// Temps fastest inf à shortest 
		
		assertTrue(((int)((Soluce(graphe, 3 ,dijk, 285, 300).getPath().getMinimumTravelTime()))) <= ((int)((Soluce(graphe, 1 ,dijk, 285, 300).getPath().getMinimumTravelTime()))));
		
		// et distance shortest  inf  fastest 
		assertTrue(((int)((Soluce(graphe, 1 ,dijk, 285, 300).getPath().getLength()))) <= ((int)((Soluce(graphe, 3 ,dijk, 285, 300).getPath().getLength()))));
		
		// Distance A -> C <= A ->B ->C Inégalité triangulaire
		
		assertTrue((((int)((Soluce(graphe, 1 ,dijk, 89082, 34751).getPath().getLength()))) + (int)((Soluce(graphe, 1 ,dijk, 34751, 128846).getPath().getLength()))) >= ((int)((Soluce(graphe, 1 ,dijk, 89082, 128846).getPath().getLength()))));
		
		
	}
	   
	@Test
	public 	void testsansOracle() throws IOException{
		ArrayList<ShortestPathSolution> array = new ArrayList<ShortestPathSolution>();
			
	}
	@Test
	public 	void testDoRun() throws IOException{
		
		//test vide chemin origin = depart, test 
		// test Lorsque Voiture sur un chemin non alloué pour les voitures
		// temps distance
		// regarder si short est vraiment shorter que fast
		// de même fast est plus long en taille que short
		//randomiser des noeuds tq on test une grande possibilité de chemin pour j test
		
		
		/*
		for(int i=0, i<j,i++) {
			List 
		}*/
	}
	// Test 
	public void Oracle() throws IOException{
		
	}
    
     
    
}





