Relations
---------

Le modèle logique est décrit sous la forme d'un schéma relationnel composé
de différentes tables.

Les signatures des tables sont basées sur les conventions suivantes:
* ``>`` avant un nom d'attribut indique que l'attribut est membre d'une clé principale
* ``>`` après un nom d'attribut indique que l'attribut fait partie d'une clé étrangère

LesPhotos
'''''''''

..  sql:table:: LesPhotos(noPhoto, contenu)

    <p, c> ∈ LesPhotos ⇐⇒ la photo identifiée par p est c.

    :source: class Photo
    :source: association LesPhotos


LesPhotosSpectacles
'''''''''''''''''''

..  sql:table:: LesPhotosSpectacles(noPhoto, noSpec)

    <p, s> ∈ LesPhotosSpectacles ⇐⇒ la photo identifiée par p illustre le spectacle s.

    :source: association Illustre

LesZones
''''''''

..  sql:table:: LesZones(numZ, nomC)

    <z, c> ∈ LesZones ⇐⇒ les places de la zone z sont dans la catégorie c.

    :source: class Zone
    :source: association LesZones

LesCategories
'''''''''''''

..  sql:table:: LesCategories(nomC, prix)

    <c, p> ∈ LesCategories ⇐⇒ p est le prix des places se situant dans une zone de catégorie c.

    :source: class Categorie
    :source: association LesCategories


LesSieges
'''''''''

..  sql:table:: LesSieges(noPlace, noRang, numZ)

    <p, r, z> ∈ LesSièges ⇐⇒ le siège de numéro de place p dans le rang r est dans la zone z.

    :source: class Siege
    :source: association LesSieges
    :source: association EstDansZone

LesSpectacles
'''''''''''''

..  sql:table:: LesSpectacles(noSpec, nomS, duree, resume)

    <s, t, d, r> ∈ LesSpectacles ⇐⇒ le spectacle de numéro s a pour titre t, d est sa durée et r son résumé.

    :source: class Spectacle
    :source: association LesSpectacles

LesRepresentations
''''''''''''''''''

..  sql:table:: LesRepresentations(dateRep, noSpec)

    <d, s> ∈ LesReprésentations ⇐⇒ d est la date d’une représentation pour le spectacle s.

    :source: class Representation
    :source: association APourRepresentation
    :source: association LesSpectacles

LesTickets
''''''''''

..  sql:table:: LesTickets(noSerie, noSpec, dateRep, noPlace, noRang, dateEmission, noDossier)

    <t, s, d, p, r, e, n> ∈ LesTickets ⇐⇒ le ticket dont le numéro de série est t correspondant
    à la vente du siège <p, r> pour la représentation <s, d>, a été émis à la date e.
    Il est associé au dossier n.

    e < d.

    :source: associationclass Tickets
    :source: association LesTickets
    :source: association EstVenduDans

LesDossiers
'''''''''''

..  sql:table:: LesDossiers(noDossier, montant)

    <d, m> ∈ LesDossiers ⇐⇒ m est le montant total des ventes de tickets associées au dossier d.

    :source: class Dossier
    :source: association LesDossiers

