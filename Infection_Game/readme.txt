Pour compiler, placez vous à l'extérieur du dossier src.

Le fichier "src" contient, 3 classes executables:
	- Partie (int)NombreDeMachines_(int)MachinesInfectées_(Double)ProbabilitéDeLien_(int)ProfondeurAttaque_(int)ProfondeurDefense_(boolean)alphabeta
	- Comparisons
	- Test (int)ProfondeurAttaque_(int)ProfondeurDefense

===========================================================================================

A savoir:
	Si vous lancez Partie, par défaut, tous les états seront afficher dans la console, si vous souhaitez donc faire de très gros réseaux il peut être préférable d'enlever l'affichage dans le code.

Précisions:
	
La classe Test à été utilisé pour réaliser les tests et a subit des modifications. Toutefois il permet d'observer le changement du résultat final en fonction de la profondeur de recherche. (Tester avec 1 1 puis 2 2)

La classe Comparisons permet d'observer l'impact de la probabilité de lien sur le nombre de noeuds visités.