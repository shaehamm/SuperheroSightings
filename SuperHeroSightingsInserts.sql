use SuperHeroSightings;

insert into Quirk (`Name`, `Description`)
values
	('One for All', 'Contains the quirks of the pervious users and can be passed down.'),
    ('Explosion', 'Sweat has nitroglycerin-like properties that can be detonated at will.'),
    ('Half-Cold Half-Hot','Generates ice from the right side of the body and fire from the left.');
 
 insert into Hero (`Name`, Alignment, QuirkId)
 values
	('All Might', 'Good', 1),
    ('Deku', 'Good', 1),
    ('Kacchan', 'Neutral-Good', 2),
    ('Shouto', 'Good', 3);
    
insert into Org (`Name`, `Description`, Address, ContactInfo)
values
	('UA', 'School for Heros', 'Somewhere in Japan', '555-4567'),
    ('League of Villans', 'Organization for villans', 'Unknown', 'No info');
    
insert into HeroOrg (HeroId, OrgId)
values
	(1, 1),
    (2, 1),
    (3, 1),
    (4, 1);
    
insert into Location (`Name`, Address, Latitude, Longitude)
values
	('Japan', 'UA Address', 89.01, 3.123),
    ('Coffee Shop', 'Shibuya', 87.2231, 4.5523);

insert into Sighting (`Date`, `Description`, LocationId, HeroId)
values
	('2020-03-14', 'Foresty area', 1, 1);