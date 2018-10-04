package src;

import java.util.ArrayList;

public class Joueur {

	int minMaxCount, alphabetaCount;
	ArrayList<Integer> futureList;
	/**
	 * AI player.
	 */
	public Joueur() {
		this.minMaxCount = 0;
		this.alphabetaCount = 0;

	}

	/**
	 * Minmax algorithm
	 * @param  s         Current state.
	 * @param  d         Depth of the research.
	 * @return           The optimal futur state.
	 */
	public State minmax(State s, int d) { //m est une valeur, c est un coup
		minMaxCount++;
		State c = new State(s);

		if(d==0 || s.isFinished()) {
			return c;

		}else {
			if(s.player == 1) { //Maximising player ==> Defender
				int b = -9999;
				int m = 0;
				ArrayList<State> defenseList = s.getDefense();
				for(State stateA : defenseList) {
					m = minmax(stateA,d-1).getValue();
					if(b < m) {
						b=m;
						c = new State(stateA);
					}
				}
			}else { //Minimizing player ==> Attacker
				int b = 9999;
				int m = 0;
				ArrayList<State> attackList = s.getAttack();
				for(State stateD : attackList) {
					m = minmax(stateD,d-1).getValue();
					if(b>m) {
						b=m;
						c = new State(stateD);
					}
				}
			}
		return c;
		}
	}

	/**
	 * The alphabeta function is decomposed in two parts. Firstly we get the
	 * the list of possible future states. After that, we let the "true"
	 * alphabeta algortihm do its job and return us the more interesting value
	 * for the current player. But in this alphabeta we fill a list that contains
	 * the values of each future state. Then we cross the value returned by
	 * alphabeta with this list. And as that list is representing our list of
	 * future state, the index of the best value (returned by the algorithm),
	 * will be the index of the future state to play.
	 * @param  s     Current state.
	 * @param  alpha Lower bound for the research.
	 * @param  beta  Upper bound for the research.
	 * @param  d     Depth of research.
	 * @return       The state corresponding to the value return by alphabeta.
	 */
	public State alphabeta(State s, int alpha, int beta, int d){
		if(!s.isFinished()){
			ArrayList<State> next;
			if(s.player == 0){
				next = s.getAttack();
			}else{
				next = s.getDefense();
			}
			int key = truealphabeta(s,alpha,beta,d);
			for(int i = 0 ; i < futureList.size() ; i++){
				if(key == futureList.get(i)){
					return next.get(i);
				}
			}
		}
		return s;
	}

	/**
	 * Return an optimal value of interest.
	 * @param  s     Current state.
	 * @param  alpha Lower bound for the research.
	 * @param  beta  Upper bound for the research.
	 * @param  d     Depth of research.
	 * @return       Optimal value for the futur state.
	 */
	public int truealphabeta(State s,int alpha,int beta, int d) {
		alphabetaCount ++;
		if(d==0 || s.isFinished()) {
			return s.getValue();
		}else {
			if(s.player == 1) { //Maximizing player
				ArrayList<State> defenseList = s.getDefense();
				ArrayList<Integer> res = initList(defenseList.size());
				for(int i = 0 ; i < defenseList.size() ; i++) {
					State stateD = defenseList.get(i);
					int m = truealphabeta(stateD,alpha,beta,d-1);
					alpha = Math.max(alpha, m);
					res.set(i,m);
					if(alpha >= beta) {
						return alpha;
					}
				}
				this.futureList= new ArrayList<Integer>(res);
				return alpha;
			}else {	//Minimising player
				ArrayList<State> attackList = s.getAttack();
				ArrayList<Integer> res = initList(attackList.size());
				for(int i = 0 ; i < attackList.size() ; i++) {
					State stateA = attackList.get(i);
					int m = truealphabeta(stateA,alpha,beta,d-1);
					beta = Math.min(beta, m);
					res.set(i,m);
					if(alpha >= beta) {
						return beta;
					}
				}
				this.futureList= new ArrayList<Integer>(res);
				return beta;
			}
		}

	}

	/**
	 * Init list with a given size filled with 0.
	 * @param  size Desired size of the list.
	 * @return      ArrayList of zero of the given size.
	 */
	public static ArrayList<Integer> initList(int size){
		ArrayList<Integer> res = new ArrayList<Integer>();
		for(int i = 0 ; i < size ; i++){
			res.add(0);
		}
		return res;
	}
}
