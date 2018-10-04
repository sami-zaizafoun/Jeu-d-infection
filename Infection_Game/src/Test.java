package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.IOException;

/**
 * Test class for make tests on a fixe graph.
 */
public class Test {


	public static void main(String [] args) throws IOException{

	try{
		int profAttack = Integer.parseInt(args[0]);
		int profDefense = Integer.parseInt(args[1]);

		ArrayList<Integer> zero = new ArrayList<Integer>(
				Arrays.asList(1,2,3,4,5));
		ArrayList<Integer> un = new ArrayList<Integer>(
				Arrays.asList(0,2));
		ArrayList<Integer> deux = new ArrayList<Integer>(
				Arrays.asList(0,1));
		ArrayList<Integer> trois = new ArrayList<Integer>(
				Arrays.asList(0));
		ArrayList<Integer> quatre = new ArrayList<Integer>(
				Arrays.asList(0));
		ArrayList<Integer> cinq = new ArrayList<Integer>(
				Arrays.asList(0,6,7,8));
		ArrayList<Integer> six = new ArrayList<Integer>(
				Arrays.asList(5,8));
		ArrayList<Integer> sept = new ArrayList<Integer>(
				Arrays.asList(5,8));
		ArrayList<Integer> huit = new ArrayList<Integer>(
				Arrays.asList(5,6,7));

		HashMap<Integer,ArrayList<Integer>> graph = new HashMap<Integer,ArrayList<Integer>>();
		graph.put(0,zero);
		graph.put(1,un);
		graph.put(2,deux);
		graph.put(3,trois);
		graph.put(4,quatre);
		graph.put(5,cinq);
		graph.put(6,six);
		graph.put(7,sept);
		graph.put(8,huit);

		ArrayList<Integer> infected = new ArrayList<Integer>();

		Graph graphTest = new Graph(9,infected);
		graphTest.setGraphic(graph);

		Joueur player = new Joueur();

			State state = new State(graphTest,infected,0);

			State state2 = new State(state);
			//System.out.println("ALPHABETA");
			while(!state.isFinished()) {

				//System.out.println(state);
				state = player.alphabeta(state,-9999,9999,profAttack);
				//System.out.println(state);
				state = player.alphabeta(state,-9999,9999,profDefense);
			}

			//System.out.println("MINIMAX");

			while(!state2.isFinished()) {

				//System.out.println(state2);
				state2 = player.minmax(state2,profAttack);
				//System.out.println(state2);
				state2 = player.minmax(state2,profDefense);
			}

			System.out.println("Calculs de alphabeta : "+player.alphabetaCount);
			System.out.println("Calculs de minmax : "+player.minMaxCount);


		System.out.println("Compte rendu de partie: ");
		System.out.println("Nombre de machines infectee(s): "+state.getInfected().size()+". Nombre de machines saine(s): "+Partie.safeMachine(state));
		System.out.println("Nombre de lien(s) entre machines saines conservee(s): "+Partie.safeLink(state,9));

	}catch(Exception e){
		System.out.println("Vous devez renseignez en arguments : profondeur de l'attaquant | profondeur du defenseur");
	}

	}
}
