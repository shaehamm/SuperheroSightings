drop database if exists SuperHeroSightingsTest;
create database SuperHeroSightingsTest;
use SuperHeroSightingsTest;

create table Quirk(
	Id int primary key auto_increment,
	`Name` varchar(30),
    `Description` varchar(250)
);

create table Hero(
	Id int primary key auto_increment,
	`Name` varchar(30) not null,
    Alignment varchar(15),
    QuirkId int not null,
    foreign key (QuirkId) references Quirk(Id)
);

create table Org(
	Id int primary key auto_increment,
    `Name` varchar(30) not null,
    `Description` varchar(250) not null,
    Address varchar(100) not null,
    ContactInfo varchar(50) not null
);

create table HeroOrg(
	HeroId int not null,
    OrgId int not null,
    primary key Id (HeroId, OrgId),
    foreign key (HeroId) references Hero(Id),
    foreign key (OrgId) references Org(Id)
);

create table Location(
	Id int primary key auto_increment,
    `Name` varchar(50) not null,
    Address varchar(50) not null,
    Latitude decimal(10,8) not null,
    Longitude decimal(11, 8) not null
);

create table Sighting(
	Id int primary key auto_increment,
    `Date` date not null,
    `Description` varchar(250) not null,
    LocationId int not null,
    HeroId int not null,
    foreign key (LocationId) references Location(Id),
    foreign key (HeroId) references Hero(Id)
);