..  _`Audit1`:

Audit #1
=========

Bilan :

- La réalisation de l'IHM dans le diagramme de Gantt indique le codage et non la conception. #50
- Il n'est pas nécéssaire de passer trop de temps sur la réalisation des diagrammes de Gantt. Il est plus intéréssant de se consacrer à l'utilisation de GitHub.
- Bien partager les taches par personnes.
- Noter les modification apportées aux données originales fournies par le client. #42
- Modification des diagrammes. Il y réside des incohérences. #43
- Type de Spectacle, notion présente dans les scénario mais inconnue de la BD nécéssitera une issue.
- IHMA du Scénario 1 est à revoir en respectant le formalisme.
- Faute d'orthographe dans la maquette du scénario 1 = acceuil
- Est il pertinent d'afficher les spectacles passés ?
- Ajout d'un commentaire IHMC "par déffaut affichade des spectacles disponibles (dont la date de représentation n'est pas passée)".
- If faut utiliser IHMA comme un pivot entre MdT et IHMC.
- Dans la page DetailSpectacle IHMC, le bouton précédent doit être remplacer par : "retour au filtrage".
- Nous n'implémentons pas un MdT mais un scénario ou une tâche. (corréction diapo Audit 1)
- Les calendrier sont affichés dans la langue par déffaut du navigateur web. Il est intéréssant de fixer cela en Françcais.
- Quand un utilisateur selectionne une date incorrecte dans les calendriers, un message s'affiche.
- Quand un utilisateur selectionne une date dans le premier calendrier, la date du deuxième calendrier se met à jour en prennant pour valeur la date du premier calendrier.
- Si le résumé d'un spectacle est null, ne pas affiché null dans la page DétailSpectacle.
- Dans cette même page, durée doit être spécifiée en minute (ajout du texte minute aprés la durée) et les dates doivent être affichées correctement (pas sous forme système).
- Il faut synchronier IHM et PL. Les boutons de selection des dates et de reservation sont inutil ici.
- La release doit être en totale cohérence avec l'ensemble du projet.
- Si un bouton ne fonctionne pas mais figure dans l'IMHC (validée par le client), ce bouton doit alors être présent dans la réalisation. (possibilité de le griser ou de le rendre inutilisable)
- Au niveau des audits, il peut être intéréssant de situer les décisions prises par rapport à un scénario donné.
- Les choix pris doivent être justifiés dans le scénario.
- Les différentes altérnatives présentent dans les scénario peuvent être traitées indépendament les unes des autres.
- Il est judicieux de donner la ligne du paragraphe utilisé pour justifier d'un choix. On peut aussi relier ce choix à une issue.


