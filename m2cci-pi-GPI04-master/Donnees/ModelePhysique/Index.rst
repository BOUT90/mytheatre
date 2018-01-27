Modèle physique
===============


Schéma SQL
----------

:download:`Schema.oracle.sql`.

..  literalinclude:: Schema.oracle.sql
    :language: sql
    :linenos:

Contraintes application
-----------------------

Les contraintes ci-dessous ne sont pas assurées par la base de données
et doivent donc être assurées par l'application:

..  sql:constraint:: C_70PlacesLibres

    Le théâtre conserve toujours 70 places (toutes catégories
    confondues) qui seront vendues au guichet, dans l’heure qui précède
    le début du spectacle.

..  sql:constraint:: CIS_APourSpectacle

    LesSpectacles[noSpec] ⊆ LesRepresentations[noSpec]

    :source: association APourRepresentation
