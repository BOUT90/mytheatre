Contraintes
-----------

Contraintes de domaines
'''''''''''''''''''''''

*   domaine(contenu) = domaine (résumé) = long
*   domaine(dateRep) = date(heure)
*   domaine(dateEmission)= date(seconde)
*   domaine(nomC) = {’orchestre’, ’balcon’, ’poulailler’}
*   domaine(numZ) = domaine(noPlace) = domaine(noPhoto) = domaine(duree)
    = domaine(noRang) = domaine(noSpec) = domaine(noDossier) = entier > 0
*   domaine(prix) = reel > 0


Contraintes d'intégrité référentielle
'''''''''''''''''''''''''''''''''''''

..  sql:constraint:: CIR_EstDeCategorie

    LesZones[nomC] ⊆ LesCategories[nomC]

    :source: association EstDeCategorie



..  sql:constraint:: CIR_EstDansZone

    LesSieges[numZ] ⊆ LesZones[numZ]

    :source: association EstDansZone


..  sql:constraint:: CIR_APourRepresentation

    LesRepresentations[noSpec] = LesSpectacles[noSpec]

    :source: association APourRepresentation



..  sql:constraint:: CIR_LesPhotosSpectacles_photos

    LesPhotosSpectacles[noPhoto] ⊆ LesPhotos[noPhoto]

    :source: association LesPhotosSpectacles


..  sql:constraint:: CIR_LesPhotosSpectacles_spectacles

    LesPhotosSpectacles[noSpec] ⊆ LesPhotos[noSpec]

    :source: association LesPhotosSpectacles




..  sql:constraint:: CIR_Ticket_representation

    LesTickets[noSpec, dateRep] ⊆ LesRepresentations[noSpec, dateRep]

    :source: associationclass Ticket


..  sql:constraint:: CIR_Ticket_siege

    LesTickets[noPlace, noRang] ⊆ LesSieges[noPlace, noRang]

    :source: associationclass Ticket



..  sql:constraint:: CIR_EstVenduDans

    LesTickets[noDossier] ⊆ LesDossiers[noDossier]

    :source: association EstVenduDans


Contraintes Additionnelles
''''''''''''''''''''''''''


..  sql:constraint:: C_Ticket_EmissionAnterieure

    Un ticket ne peut pas être émis après qu'une représentation aie lieu.

    LesTickets.dateEmission < LesTickets.dateRep

..  sql:constraint:: C_70PlacesLibres

    Le théâtre conserve toujours 70 places (toutes catégories
    confondues) qui seront vendues au guichet, dans l’heure qui précède
    le début du spectacle.

