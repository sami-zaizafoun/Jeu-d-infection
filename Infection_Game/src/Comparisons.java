package src;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;

public class Comparisons {

     /**
      * Fichier d'experimentations.
      * @param  args        arguments renseignées par l'utilisateur.
      * @throws IOException Erreur sur les arguments.
      */
     public static void main(String [] args) throws IOException{

          /* INTIALISATION */
          try{
               int nbMachine = 5;
               int infectedMachine = 0;
               ArrayList<Double> linkProb = new ArrayList<Double>(
                    Arrays.asList(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0)
               );

               //Liste de profondeur si on souhaite faire un test sur la profondeur.
               //Il faut ensuite itérer sur cette liste.
               ArrayList<Integer> profondeur = new ArrayList<Integer>(
                    Arrays.asList(1,2,3,4,5,6,7,8,9,10)
               );
               boolean alphabeta = true;
               int nbTirage = 10;

               ArrayList<Integer> res = new ArrayList<Integer>(
                    Arrays.asList(0,0,0,0,0,0,0,0,0,0)
               );
               ArrayList<Integer> res2 = new ArrayList<Integer>(
                    Arrays.asList(0,0,0,0,0,0,0,0,0,0)
               );

               ArrayList infectedNode = new ArrayList<Integer>();
               for(int i = 0; i < infectedMachine ; i++) {

                    infectedNode.add(i);

               }

               int nbCalculs = 0;
               /* LAUNCH TEST */

               for(int i = 0 ; i < linkProb.size() ; i++){
                    int prof = 0;
                         for(int j = 0 ; j < nbTirage ; j++){
                              prof++;
                              Graph graph = new Graph(nbMachine,infectedNode);
                              graph.computeNetwork(linkProb.get(i));

                              State state = new State(graph,infectedNode, 0);
                              Joueur player = new Joueur();
                              State state2 = new State(state);
                              while(!state.isFinished()){
                                   state = player.minmax(state,3);
                                   state = player.minmax(state,3);

                              }
                              while(!state2.isFinished()){
                                   state2 = player.alphabeta(state2,-9999,9999,3);
                                   state2 = player.alphabeta(state2,-9999,9999,3);
                              }
                              res.set(i, res.get(i)+player.minMaxCount);
                              player.minMaxCount = 0;
                              res2.set(i, res2.get(i)+player.alphabetaCount);
                              player.alphabetaCount = 0;
               }
          }

               for(int i = 0 ; i < res.size(); i++){
                    res.set(i,res.get(i)/10);
                    res2.set(i,res2.get(i)/10);
               }

               System.out.println("Les chiffres correspondent au nombre de calculs effectuees pendant toute la duree de la partie.\n");
               System.out.println("Probabilites utilisees => "+linkProb);
               System.out.println("\nRESULTAT MINMAX : \n"+res);

               System.out.println("\nRESULTAT ALPHABETA: \n"+res2);

          }catch(Exception e){
               System.out.println("Erreur d'arguments");
          }

     }

     public static ArrayList<Double> fillProb(ArrayList<Double> list,int maxValue, Double step){
          for(Double i = 0.1 ; i <= maxValue; i+= step){
               list.add(i);
          }
          return list;
     }
}
