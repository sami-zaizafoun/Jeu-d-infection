package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class State {

	public Graph graph;
	private ArrayList<Integer> infected;
	public int player;

	/**
	 * State of the current match.
	 * @param graph    Network represented by lists of successors.
	 * @param infected List if infected nodes in the network.
	 * @param player   ID of the player (0 => attacker | 1 => defender).
	 */
	public State(Graph graph,ArrayList<Integer> infected, int player) {

		this.graph = graph;
		this.player = player;
		this.infected = infected;

	}

	/**
	 * Constructor for paste a state (mostely used for getAttack and getDefense).
	 * @param e State to paste.
	 */
	public State(State e) {
		this(e.graph,e.infected,e.player);
	}

	/**
	 * Infect a given node.
	 * @param nodeIndex Node to infect.
	 */
	public void setInfected(int nodeIndex) {
		this.infected.add(nodeIndex);
	}

	/**
	 * Determine if the game is over.
	 * @return true if the number of node able to be infected is non equal to 0.
	 */
	public boolean isFinished() {

		if(infected.isEmpty()) {
			return false;
		}
		for(Integer infectedNode : infected) {
			ArrayList<Integer> successors = getSuccesors(infectedNode);
			for(Integer victim : successors) {
				if(!infected.contains(victim)) {
					return false;
				}
			}
		}

		return true;

	}

	/**
	 * Evaluation function.
	 * @return Number of connection between two non-infected nodes.
	 */
	public int getValue() {
		int res = 0;

		for(int i = 0 ; i < graph.getNbNode() ; i++) {
			ArrayList<Integer> successors = getSuccesors(i);
			for(Integer node : successors) {
				if(!infected.contains(i) && !infected.contains(node)) {
					res += 1;
				}
			}
		}
		return res;
	}
	/**
	 * Test if
	 * @param  successors [description]
	 * @return            [description]
	 */
	private Boolean containsInfected(ArrayList<Integer> successors) {

		for(int i = 0 ; i < successors.size() ; i++) {
			if(infected.contains(successors.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get all the potentials attacks.
	 * @return An ArrayList of the potentials attack states.
	 */
	public ArrayList<State> getAttack(){
		ArrayList<State> listAttack = new ArrayList<State>();
		ArrayList<ArrayList<Integer>> alreadyDone = new ArrayList<ArrayList<Integer>>();

		if(infected.isEmpty()) {
			for(int i = 0 ; i < this.graph.getNbNode() ; i++) {
				ArrayList<Integer> firstInfected = new ArrayList<Integer>(infected);
				listAttack.add(new State(this.graph,firstInfected,player+1));
				((State) listAttack.get(i)).setInfected(i);
			}
		}else {
			for(Integer infectedNode : infected) {

				ArrayList<Integer> successors = getSuccesors(infectedNode);

				for(Integer newInfected : successors) {

					ArrayList<Integer> updateInfected = new ArrayList<Integer>(this.infected);

					if(!infected.contains(newInfected)) {

						updateInfected.add(newInfected);
						//newInfected.remove((int)0);
						if(!alreadyDone.contains(updateInfected)) {
							listAttack.add(new State(this.graph,updateInfected,player+1));
							alreadyDone.add(updateInfected);
						}
					}
				}
			}
		}
		return listAttack;
	}

	/**
	 * Get all the potentials defenses.
	 * @return An ArrayList of the potentials defense states.
	 */
	public ArrayList<State> getDefense() {

		ArrayList<State> listDefense = new ArrayList<State>();

		for(int i = 0 ; i < graph.getNbNode() ; i++) {

			if(!infected.contains(i)) {

				ArrayList<Integer> successors = getSuccesors(i);
				ArrayList<ArrayList<Integer>> powerset = powerset(successors);

				for(int j = 0 ; j < powerset.size(); j++) {

					if(!powerset.get(j).equals(successors)) {
						Graph newGraph = new Graph(graph.getNbNode(),infected);
						newGraph.setGraphic(new HashMap<Integer, ArrayList<Integer>>(graph.getGraphic()));
						newGraph.setSuccessors(i, powerset.get(j));
						for(Integer node : successors) {
							if(!powerset.get(j).contains(node)) {
								newGraph.removeSuccessors(node, i);
							}
						}
						listDefense.add(new State(newGraph,infected,player-1));
					}
				}
			}
		}

		return listDefense;

	}

	/**
	 * Function used in getDefense
	 * @param  list List of successors of a node.
	 * @return      Powerset of the successors list.
	 */
	public static ArrayList<ArrayList<Integer>> powerset(ArrayList<Integer> list) {
		  ArrayList<ArrayList<Integer>> ps = new ArrayList<ArrayList<Integer>>();
		  ps.add(new ArrayList<Integer>());   // add the empty set

		  // for every item in the original list
		  for (int item : list) {
		    ArrayList<ArrayList<Integer>> newPs = new ArrayList<ArrayList<Integer>>();

		    for (ArrayList<Integer> subset : ps) {
		      // copy all of the current powerset's subsets
		      newPs.add(subset);

		      // plus the subsets appended with the current item
		      ArrayList<Integer> newSubset = new ArrayList<Integer>(subset);
		      newSubset.add(item);
		      newPs.add(newSubset);
		    }

		    // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
		    ps = newPs;
		  }
		  return ps;
		}

	/**
	 * Get the successors list of a node.
	 * @param  node Node from which we want the successors list.
	 * @return      The list of successors of the given node.
	 */
	public ArrayList<Integer> getSuccesors(Integer node){
		return (ArrayList<Integer>)this.graph.getGraphic().get(node);
	}

	/**
	 * Play the best future test returned by getAttack()
	 */
	public State playAttack(int i) {

		ArrayList<State> getAttack = this.getAttack();
		return getAttack.get(i);

	}

	public State playDefense(int i) {

		ArrayList<State> getDefense = this.getDefense();
		return getDefense.get(i);
	}

	public ArrayList<Integer> getInfected(){
		return infected;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("\nCette etat vaut: "+this.getValue()+". Infected nodes: ");
		for(int index: this.infected) {
			res.append(index+" ");
		}
		res.append("\n"+this.graph);
		return res.toString();
	}

}
