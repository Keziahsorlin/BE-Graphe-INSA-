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

//@RunWith(Parameterized.class)
public class DijkstrastraAlgorithmTest {
	private static Graph graphe,graphe2,graphe3;
	//private static List NoeudRand ;
	static String dijk= "dijkstra";
	String astr= "astra";
	String bell = "Bellman";
	
	
	
	
	@BeforeClass
	public static void initAll() throws IOException {	
		GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre-dense.mapgr/"))));
		graphe = reader.read();
		GraphReader reader2 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr/"))));
		graphe2 = reader2.read();
		GraphReader reader3 = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream("/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/carre.mapgr/"))));
		graphe3 = reader3.read();
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
		// Sur chaque mode 
		for (int i=0 ; i <4; i++) {
			assertTrue((Soluce(graphe, i ,dijk , 0, 0).getPath().isValid())); // le chemin A > A ne doit rien donner donc non réalisable
			assertTrue((Soluce(graphe, i ,dijk , 0, 359997)).getPath().isValid()); // Dijkstra Un test sur un court un chemin pour voir si le chemin créé est correct
			assertTrue((Soluce(graphe, i ,astr , 0, 359997)).getPath().isValid()); // la même en astar
			assertTrue((Soluce(graphe, i ,dijk , 85921, 359997)).getPath().isValid()); // Plus gros chemin test du chemin créé
			//test correct
		}
	
	}
	// tous les modes sont testés
	@Test
	public void testIsFeasible() {
	   for (int i=0 ; i <4; i++) {
    	// courte
    	assertEquals(Status.OPTIMAL, Soluce(graphe, i ,dijk, 0, 359997).getStatus()); // voir si les chemins donnés sont optimaux
    	assertEquals(Status.OPTIMAL, Soluce(graphe, i ,astr, 0, 359997).getStatus());// la même en astar
    	// + long
    	assertEquals(Status.OPTIMAL, Soluce(graphe, i ,dijk, 285, 300).getStatus()); // sur un plus gros chemin test d'optimalité
	   }
    }
	   
	@Test
	public void testOracle() throws IOException{
		// on arrondi la distance en m 
		// le resultat Bellman et dijkstra est le même et astra dijkstra
		//test vide chemin origin = depart, test 
				// test Lorsque Voiture sur un chemin non alloué pour les voitures
				// temps distance
				// regarder si short est vraiment shorter que fast
				// de même fast est plus long en taille que short
		//////// Comparaison entre les Algos 
		//distance
		
		//Comparer Bellman avec le dijkstra et astra sur le même chemin et voir si l'on retourne la même longueur
		assertEquals((int)((Soluce(graphe, 1 ,bell, 285, 300).getPath().getLength())), (int)((Soluce(graphe, 1 ,dijk, 285, 300).getPath().getLength())));
		assertEquals((int)((Soluce(graphe, 1 ,astr, 285, 300).getPath().getLength())), (int)((Soluce(graphe, 1 ,dijk, 285, 300).getPath().getLength())));
		//Avec chaque mode Comp
		for (int i=0; i<4;i++) {
			//Comparer Bellman avec le dijkstra et astra sur le même chemin et voir si l'on retourne la même longueur + temps quelque soit le mode
			ShortestPathSolution SolutionBell = Soluce(graphe2, i ,bell, 6619, 2137);
			ShortestPathSolution SolutionDijk= Soluce(graphe2, i ,dijk, 6619, 2137);
			ShortestPathSolution SolutionAstr = Soluce(graphe2, i ,astr, 6619, 2137);
			assertEquals(((int)((SolutionAstr.getPath().getLength()))),((int)((SolutionBell .getPath().getLength()))));
			assertEquals(((int)((SolutionBell .getPath().getLength()))),((int)((SolutionDijk.getPath().getLength()))));
			//temps
			
			assertEquals(((int)((SolutionAstr.getPath().getMinimumTravelTime()))),((int)((SolutionBell .getPath().getMinimumTravelTime()))));
			assertEquals(((int)((SolutionBell .getPath().getMinimumTravelTime()))),((int)((SolutionDijk.getPath().getMinimumTravelTime()))));
			// les algorithmes donnent l'optimal donc les mêmes chemins impliquent les mêmes solutions
			// resultat correct
		}
		//Temps sur une plus grosse carte
		assertEquals((int)((Soluce(graphe, 3 ,bell, 285, 300).getPath().getMinimumTravelTime())), (int)((Soluce(graphe, 3 ,dijk, 285, 300).getPath().getMinimumTravelTime())));
		assertEquals((int)((Soluce(graphe, 3 ,astr, 285, 300).getPath().getMinimumTravelTime())), (int)((Soluce(graphe, 3 ,bell, 285, 300).getPath().getMinimumTravelTime())));
		// LE résultat en temps est donné correct
		
		/*reader.close();
		reader2.close();
		*/
	}
	 
	@Test
	public 	void testsansOracle() throws IOException{
	
		////////////////DIJKSTRA
				
		// Temps fastest inf à shortest 
		
		assertTrue(((int)((Soluce(graphe, 3 ,dijk, 285, 300).getPath().getMinimumTravelTime()))) <= ((int)((Soluce(graphe, 1 ,dijk, 285, 300).getPath().getMinimumTravelTime()))));
		// resultat correct
		
		// et distance shortest  inf  fastest 
		assertTrue(((int)((Soluce(graphe, 1 ,dijk, 285, 300).getPath().getLength()))) <= ((int)((Soluce(graphe, 3 ,dijk, 285, 300).getPath().getLength()))));
		// resultat correct
		
		// Distance A -> C <= A ->B ->C Inégalité triangulaire
		
		assertTrue((((int)((Soluce(graphe, 1 ,dijk, 89082, 34751).getPath().getLength()))) + (int)((Soluce(graphe, 1 ,dijk, 34751, 128846).getPath().getLength()))) >= ((int)((Soluce(graphe, 1 ,dijk, 89082, 128846).getPath().getLength()))));
		
		assertTrue((((int)((Soluce(graphe2, 1 ,dijk, 6619, 6169).getPath().getLength()))) + (int)((Soluce(graphe2, 1 ,dijk, 6169, 21376).getPath().getLength()))) >= ((int)((Soluce(graphe2, 1 ,dijk, 6619, 21376).getPath().getLength()))));
		// resultat correct
		
		// Test Temps A  -> C <= A ->B ->C Optimalité
		assertTrue((((int)((Soluce(graphe2, 1 ,dijk, 6619, 6169).getPath().getMinimumTravelTime()))) + (int)((Soluce(graphe2, 1 ,dijk, 6169, 21376).getPath().getMinimumTravelTime()))) >= ((int)((Soluce(graphe2, 1 ,dijk, 6619, 21376).getPath().getMinimumTravelTime()))));
		// resultat correct
		for (int i=0;i<4;i++) {
			//Inegalité triangulaire tout mode
			assertTrue((((int)((Soluce(graphe3, i ,dijk, 2, 1).getPath().getLength()))) + (int)((Soluce(graphe3, i ,dijk, 1, 11).getPath().getLength()))) >= ((int)((Soluce(graphe3, i ,dijk, 2, 11).getPath().getLength()))));
			// Temps A ->C Tout mode
			assertTrue((((int)((Soluce(graphe3, i ,dijk, 2, 1).getPath().getMinimumTravelTime()))) + (int)((Soluce(graphe3, i ,dijk, 1, 11).getPath().getMinimumTravelTime()))) >= ((int)((Soluce(graphe3, i ,dijk, 2, 11).getPath().getMinimumTravelTime()))));
			
		}
		// resultat correct
		
		////////////////ASTRA // resultat correct (Les mêmes que dijkstra)
		// Temps fastest inf à shortest 
		
		assertTrue(((int)((Soluce(graphe, 3 ,astr, 285, 300).getPath().getMinimumTravelTime()))) <= ((int)((Soluce(graphe, 1 ,astr, 285, 300).getPath().getMinimumTravelTime()))));
		
		// et distance shortest  inf  fastest 
		assertTrue(((int)((Soluce(graphe, 1 ,astr, 285, 300).getPath().getLength()))) <= ((int)((Soluce(graphe, 3 ,astr, 285, 300).getPath().getLength()))));
		
		// Distance A -> C <= A ->B ->C Inégalité triangulaire
		
		assertTrue((((int)((Soluce(graphe, 1 ,astr, 89082, 34751).getPath().getLength()))) + (int)((Soluce(graphe, 1 ,astr, 34751, 128846).getPath().getLength()))) >= ((int)((Soluce(graphe, 1 ,astr, 89082, 128846).getPath().getLength()))));
		
		assertTrue((((int)((Soluce(graphe2, 1 ,astr, 6619, 6169).getPath().getLength()))) + (int)((Soluce(graphe2, 1 ,astr, 6169, 21376).getPath().getLength()))) >= ((int)((Soluce(graphe2, 1 ,astr, 6619, 21376).getPath().getLength()))));
		for (int i=0;i<4;i++) {
			//Inegalité triangulaire tout mode
			assertTrue((((int)((Soluce(graphe3, i ,astr, 2, 1).getPath().getLength()))) + (int)((Soluce(graphe3, i ,astr, 1, 11).getPath().getLength()))) >= ((int)((Soluce(graphe3, i ,astr, 2, 11).getPath().getLength()))));
			// Temps A ->C Tout mode
			assertTrue((((int)((Soluce(graphe3, i ,astr, 2, 1).getPath().getMinimumTravelTime()))) + (int)((Soluce(graphe3, i ,astr, 1, 11).getPath().getMinimumTravelTime()))) >= ((int)((Soluce(graphe3, i ,astr, 2, 11).getPath().getMinimumTravelTime()))));
			
		}
		
		// Test Temps A  -> C <= A ->B ->C Optimalité
		assertTrue((((int)((Soluce(graphe2, 1 ,astr, 6619, 6169).getPath().getMinimumTravelTime()))) + (int)((Soluce(graphe2, 1 ,astr, 6169, 21376).getPath().getMinimumTravelTime()))) >= ((int)((Soluce(graphe2, 1 ,astr, 6619, 21376).getPath().getMinimumTravelTime()))));
		
			
	}
	
}





