package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Partie {

		public static int nbMachine;
		public static void main(String [] args) throws IOException{

			//6 paramètres à l'execution
			//nbMachine | nbInfected | probaLinked | profAttack | profDef | elagage
		try{
			nbMachine = Integer.parseInt(args[0]);
			int infectedMachine = Integer.parseInt(args[1]);
			double linkProb = Double.parseDouble(args[2]);
			int profAttack = Integer.parseInt(args[3]);
			int profDefense = Integer.parseInt(args[4]);
			boolean alphabeta = Boolean.valueOf(args[5]);

			ArrayList infectedNode = new ArrayList<Integer>();
			for(int i = 0; i < infectedMachine ; i++) {

				infectedNode.add(i);

			}

			System.out.println("\nParametres de la partie: \nNombre de machines: "+nbMachine+"\nNombre de machines initialement infectees: "+infectedMachine);
			System.out.println("Probabilite de lien entre deux machines: "+(linkProb*100)+"%\nProfondeur d'attaque: "+profAttack+"\nProfondeur de defense: "+profDefense);
			System.out.println("Elagage alphabeta: "+alphabeta);
			Graph graph = new Graph(nbMachine,infectedNode);
			graph.computeNetwork(linkProb);

			State state = new State(graph,infectedNode, 0);
			Joueur player = new Joueur();


			while(!state.isFinished()) {

				if(alphabeta){
					state = player.alphabeta(state,-9999,9999,profAttack);
					System.out.println(state);
					state = player.alphabeta(state,-9999,9999,profDefense);
					System.out.println(state);
				}else{
					state = player.minmax(state,profAttack);
					System.out.println(state);
					state = player.minmax(state,profDefense);
					System.out.println(state);
				}
			}

			int nbCalculs = 0;
			if(alphabeta){
				nbCalculs = player.alphabetaCount;
			}else{
				nbCalculs = player.minMaxCount;
			}
			System.out.println("COMPTE RENDU: \n");
			System.out.println("Nombre de calculs effectues durant la partie: "+nbCalculs);
			System.out.println("Nombre de machines infectee(s): "+state.getInfected().size()+". Nombre de machines saine(s): "+safeMachine(state));
			System.out.println("Nombre de lien(s) entre machines saines conservee(s): "+safeLink(state,Partie.nbMachine));

		}catch(Exception e){
			System.out.println("Erreurs d'arguments");
		}
		}

		/**
		 * Get the number of non infected machines in a state.
		 * @param  state Current state
		 * @return       Number of non infected machines in the given state.
		 */
		public static int safeMachine(State state){
			return  state.graph.getNbNode() - state.getInfected().size();
		}

		/**
		 * Get the number of safe link in a state.
		 * @param  state     Current state.
		 * @param  nbMachine Number machines in the state.
		 * @return           Number of link between non infected machines.
		 */
		public static int safeLink(State state, int nbMachine){
			int res = 0;

			ArrayList<Integer> listInfected = state.getInfected();
			for(int i = 0 ; i < nbMachine ; i++ ){
				if(!listInfected.contains(i)){
					res += state.getSuccesors(i).size();
				}
			}
			return res/2;
		}

}
