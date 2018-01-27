--=======================================================================
--                     Schema de la base de données
--=======================================================================

create table LesPhotos
(
	numPhoto integer, url varchar(150),
	constraint LesPhotos_c0 primary key (numPhoto),
	constraint LesPhotos_c1 check (numPhoto > 0)
);

create table LesPhotosSpectacles
(
	numPhoto integer, numSpec integer,
	constraint LesPhotosSpectacles_c0 primary key (numPhoto, numSpec),
	constraint LesPhotosSpectacles_c3 foreign key (numPhoto)
	references LesPhotos (numPhoto)
);

create table LesCategories
(
	nomC varchar(20), prix number(8,2),
	constraint LesCategories_c0 primary key (nomC),
	constraint LesCategories_c1 check (prix > 0),
	constraint LesCategories_c2 check (nomC in ('orchestre','1er balcon', '2nd balcon','poulailler'))
);

create table LesZones
(
	numZ integer, nomC varchar(20),
	constraint LesZones_c0 primary key (numZ),
	constraint LesZones_c1 foreign key (nomC)
	references LesCategories (nomC),
	constraint LesZones_c2 check (numZ > 0)
);

create table LesSieges
(
	numPlace integer, numRang integer, numZ integer,
	constraint LesSieges_c0 primary key (numPlace, numRang),
	constraint LesSieges_c1 foreign key (numZ)
	references LesZones(numZ),
	constraint LesSieges_c2 check (numPlace > 0),
	constraint LesSieges_c3 check (numRang > 0)
);

create table LesSpectacles
(
	numSpec integer, nomS varchar(20), duree integer, resume varchar(300),
	constraint LesSpectacles_c0 primary key(numSpec),
	constraint LesSpectacles_c1 check (numSpec > 0),
	constraint LesSpectacles_c2 check (duree > 0)
);

create table LesRepresentations
(
	dateRep date, numSpec integer,
	constraint LesRepresentations_c0 primary key (dateRep, numSpec),
	constraint LesRepresentations_c1 foreign key (numSpec)
	references LesSpectacles (numSpec)
);

create table LesDossiers
(
	numDossier integer, montant integer,
	constraint LesDossiers_c0 primary key (numDossier),
	constraint LesDossier_c1 check (numDossier > 0)
);

create table LesTickets
(
	numSerie integer, numSpec integer, dateRep date, numPlace integer, numRang integer, dateEmission date, numDossier integer,
	constraint LesTickets_c0 primary key (numSerie),
	constraint LesTickets_c1 unique (numSpec, dateRep, numPlace, numRang),
	constraint LesTickets_c2 foreign key (numSpec, dateRep)
	references LesRepresentations (numSpec, dateRep),
	constraint LesTickets_c3 foreign key (numPlace, numRang)
	references LesSieges (numPlace, numRang),
	constraint LesTickets_c4 foreign key (numDossier)
	references LesDossiers (numDossier),
	constraint LesTickets_c5 check (dateEmission < dateRep)
);

create table LESUTILISATEURS
(
	PASSWORD varchar(20), LOGIN varchar(20), NOM varchar(20), PRENOM varchar(20), EMAIL varchar (20), STATUS integer,	
	constraint LESUTILISATEURS_c0 primary key (LOGIN)
);



