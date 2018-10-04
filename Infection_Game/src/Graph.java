package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Graph {

		private HashMap graphic;
		private int nbNode;

		/**
		 * Representation of a network by list of successors.
		 * @param nbNode   Number of node (machines) in the network.
		 * @param infected ArrayList containing the infected nodes.
		 */
		public Graph(int nbNode, ArrayList infected) {
			this.graphic = new HashMap();
			this.nbNode=nbNode;
		}

		/**
		 * Getter of the Hashmap, representation of the network.
		 * @return The hashmap representing the network.
		 */
		public HashMap getGraphic() {
			return this.graphic;
		}

		/**
		 * Manualy set the network.
		 * @param graph Network to set.
		 */
		public void setGraphic(HashMap graph) {
			this.graphic = graph;
		}

		/**
		 * Get the number of nodes in the network.
		 * @return Number of nodes in the network.
		 */
		public int getNbNode() {
			return this.nbNode;
		}

		/**
		 * Set the successors list of a given node.
		 * @param node          Node from which set the successors.
		 * @param newSuccessors New list of successors for the given node.
		 */
		public void setSuccessors(int node, ArrayList<Integer> newSuccessors) {

			this.getGraphic().put(node, newSuccessors);

		}

		/**
		 * Remove a successors of a node.
		 * @param node        Node from which remove a successors.
		 * @param removedNode Successors to remove.
		 */
		public void removeSuccessors(int node, int removedNode) {
			ArrayList<Integer> update = new ArrayList<Integer>(this.getSuccessors(node));
			update.remove((Object)removedNode);
			this.graphic.put(node, update);

		}

		/**
		 * Get the list of successors of a node.
		 * @param  node Node from which we want the list of successors.
		 * @return      List of successors of the given node.
		 */
		public ArrayList<Integer> getSuccessors(Integer node){
			return (ArrayList<Integer>)graphic.get(node);
		}

		/**
		 * Build a random network with a link probability between two nodes.
		 * @param prob Link probability between two nodes.
		 */
		public void computeNetwork(double prob) {
			Random r = new Random();
			graphic = new HashMap<Integer,ArrayList<Integer>>();
			for( int x = 0; x < nbNode; x++) {
				graphic.put(x,new ArrayList<Integer>());
			}
			for(int i = 0 ; i < this.getNbNode() ; i++) {
				ArrayList<Integer> linked = new ArrayList<Integer>();
				for(int y = i+1 ; y < this.getNbNode() ; y++) {
					Boolean createLink = randomProb(prob);
					if( createLink) {
						ArrayList<Integer> currentList1 = (ArrayList<Integer>) graphic.get(i);
						currentList1.add(y);
						graphic.put(i,currentList1);

						ArrayList<Integer> currentList2 = (ArrayList<Integer>) graphic.get(y);
						currentList2.add(i);
						graphic.put(y,currentList2);
					}
				}
			}
			this.graphic=graphic;
		}

		/**
		 * Decide if a link is about to be create.
		 * @param  prob Probability of a link.
		 * @return      True if a link will be created, false otherwise.
		 */
		private Boolean randomProb(double prob) {
			int i = new Random().nextInt(100);
			if(i<100*prob) {
				return true;
			}else {
				return false;
			}
		}

		/**
		 * Representation of a Graph with a String.
		 * @return String representing a Graph.
		 */
		@Override
		public String toString() {
			StringBuilder res = new StringBuilder();
			for(int i = 0 ; i < nbNode; i++) {
				res.append(i+" : "+this.graphic.get(i)+'\n');
			}
		    return res.toString();
		  }
}
