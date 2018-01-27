..  _`Concepts`:

Concepts
========

..  ..
    Ne pas changer ce fichier

Les spectacles
--------------
Un spectacle est identifié par un numéro et on connaı̂t son nom. Un spectacle est
aussi associé à une durée, un résumé et 0, une ou plusieurs photos. Un spectacle fait généralement
l’objet de plusieurs représentations proposées à des moments différents. Une représentation débute à
un moment donné exprimé à la granularité de l’heure (par exemple : 28/11/2017 20H).

La salle
--------
La salle est découpée en zones numérotées, regroupant chacune un ensemble de sièges. Un
siège est identifié par un numéro de rang, et un numéro de place dans le rang. Une zone est associée
à une catégorie tarifaire (orchestre, balcon, poulailler, etc). Tous les siège de la même zone sont dans
la même catégorie. Le tarif de base associé à chaque catégorie est fixé pour l’ensemble de tous les
spectacles.

Les ventes
----------
Chaque siège vendue fait l’objet de l’émission d’un ticket identifié par un numéro de
série et estampillé par la date au moment de la transaction (instant à la granularité de la seconde).
Un achat peut concerner plusieurs sièges à la vente pour diverses représentations. Chaque achat se
traduit par la création d’un dossier (identifié par un numéro) auquel est associé le prix global de la
vente.

Le théâtre conserve toujours 70 places (toutes catégories confon-
dues) qui seront vendues au guichet, dans l’heure qui précède le début du spectacle.

Réduction
---------
Les personnes qui achètent des tickets peuvent être des spectateurs ordinaires ou
des adhérents et bénéficier d’un tarif réduit. Certaines personnes (étudiants, scolaires, demandeurs
d’emploi, séniors, etc.) bénéficient aussi d’une réduction. Le justificatif de la réduction obtenue est
demandé à la personne détenant le billet, à l’entrée du spectacle. Le prix d’un ticket est calculé en
fonction de la réduction qui dépend de son type (adhérent, sénior, étudiant, sans réduction, etc.). A
chacun de ces types est associé un taux de réduction (20% pour adhérent, 50% pour sénior, 30% pour
étudiant, 0% pour sans réduction, etc.).
Pour certaines représentations, le prix des tickets fait l’objet d’une promotion. Ainsi, à chaque
représentation est associé un taux de promotion (0 : pas de promotion, 0,5 : promotion de 50,
etc.).
Le prix final est donc déterminé par la réduction accordée et la promotion du moment qui vient
s’ajouter à la réduction.

Configurations de la salle
--------------------------
Selon les spectacles, toutes les places de certaines zones sont retirées de
la vente : en effet, dans certaines mises en scène, une partie des tribunes est occupée par le spectacle.
La configuration de la salle, c’est-à-dire l’ensemble des zones dont les places sont mises à la vente, est
la même pour toutes les représentations d’un même spectacle.

Achats en ligne
---------------
L’application doit être mise en ligne afin de permettre à des utilisateurs identifiés
d’acheter des tickets. Les utilisateurs sont identifiés par leur login. Ils sont décrits par leur nom,
leur prénom, leur adresse électronique et leur mot de passe. On distingue les responsables de la
programmation et les clients enregistrés.

Réservations
------------
Un utilisateur peut réserver des tickets avant de procéder à leur achat.
Une réservation ne peut être achetée que par l’utilisateur qui l’a effectué.

Annulations
-----------
Une réservation (et tous les tickets associés) peut être annulée soit par
l’administrateur de l’application soit par le client qui l’a réservée. Il arrive
aussi que le responsable de la programmation annule une représentation.
Dans ce cas, il n’est plus possible ni de réserver,
ni d’acheter des places pour cette représentation ; toutes les réservations sont annulées.
