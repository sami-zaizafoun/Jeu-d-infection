Pour compiler, placez vous � l'ext�rieur du dossier src.

Le fichier "src" contient, 3 classes executables:
	- Partie (int)NombreDeMachines_(int)MachinesInfect�es_(Double)Probabilit�DeLien_(int)ProfondeurAttaque_(int)ProfondeurDefense_(boolean)alphabeta
	- Comparisons
	- Test (int)ProfondeurAttaque_(int)ProfondeurDefense

===========================================================================================

A savoir:
	Si vous lancez Partie, par d�faut, tous les �tats seront afficher dans la console, si vous souhaitez donc faire de tr�s gros r�seaux il peut �tre pr�f�rable d'enlever l'affichage dans le code.

Pr�cisions:
	
La classe Test � �t� utilis� pour r�aliser les tests et a subit des modifications. Toutefois il permet d'observer le changement du r�sultat final en fonction de la profondeur de recherche. (Tester avec 1 1 puis 2 2)

La classe Comparisons permet d'observer l'impact de la probabilit� de lien sur le nombre de noeuds visit�s.