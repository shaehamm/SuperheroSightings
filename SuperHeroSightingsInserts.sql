use SuperHeroSightings;

insert into Quirk (`Name`, `Description`)
values
	('Acid', 'Creates corrosive liquid from the skin.'),
	('All For One', 'Steals Quirks from others, and allows the user to wield the stolen powers.'),
	('Black Hole', 'Creates small black holes through the user\'s fingers.'),
	('Dark Shadow', 'Grants the user a shadowy, moster-like being from within their body that they can materialize/de-materialize at will.'),
	('Dragon', 'Transforms into a dragon.'),
    ('Earphone Jack', 'Grants the user long, controllable earlobes that work like headphone cables.'),
	('Explosion', 'Sweat has nitroglycerin-like properties that can be detonated at will.'),
	('Fierce Wings', 'User has two large wings made up of dozens of feathers that can be manipulated by the mind.'),
	('Half-Cold Half-Hot','Generates ice from the right side of the body and fire from the left.'),
	('Manifest', 'Enhances the user\'s limbs with the characteristics of anything they consume.'),
	('One for All', 'Contains the quirks of the pervious users and can be passed down.'),
	('Warp Gate', 'Creates and manipulates a dark fog that acts as a portal.');
 
 insert into Hero (`Name`, Alignment, QuirkId)
 values
	('All For One', 'Evil', 2),
	('All Might', 'Good', 11),
	('Deku', 'Good', 11),
	('Earphone Jack', 'Good', 6),
    ('Hawks', 'Good', 8),
	('Kacchan', 'Neutral-Good', 7),
	('Kurogiri', 'Evil', 12),
	('Pinky', 'Good', 1),
	('Ryukyu', 'Good', 5),
	('Shouto', 'Good', 9),
	('Suneater', 'Good', 10),
	('Thirteen', 'Good', 3),
    ('Tomura Shigaraki', 'Evil', 2),
    ('Tsukuyomi', 'Neutral-Good', 4);
    
insert into Org (`Name`, `Description`, Address, ContactInfo)
values
   	('Endeavor Agency', 'Agency headed by the No. 1 hero Endeavor.', 'Downtown, Japan', '555-3566'),
    ('League of Villans', 'Organization for powerful villans.', 'Unknown', 'No info'),
   	('Ryukyu Agency', 'Agency headed by the No. 3 hero Ryukyu.', 'Espa Avenue, Japan', '555-1098'),
    ('U.A. High School', 'Number one ranked school for heroics.', 'Musutafu, Japan', '555-4567');
 
insert into HeroOrg (HeroId, OrgId)
values
	(1, 2),
    (2, 4),
    (3, 4),
    (4, 4),
    (5, 1),
    (6, 4),
    (7, 2),
    (8, 4),
    (9, 3),
    (10, 1),
    (10, 4),
    (11, 4),
    (12, 4),
    (13, 2),
    (14, 4);
    
insert into Location (`Name`, Address, Latitude, Longitude)
values
	('Deika City', 'Deika, Japan', 87.226541, 4.5523),
	('Heights Alliance', 'U.A. High School Address', 89.01, 3.123),
    ('Isamu Academy High', 'Isamu Address', 87.2231, 4.5523),
    ('Juko News', 'Downtown Shibuya, Japan', 87.2231, 4.5523),
    ('Kanidoge', 'Osaka, Japan', 87.2231, 4.5523),
    ('Kiyashi Ward Shopping Mall', 'Kiyashi, Japan', 87.2231, 4.5523),
    ('Lunch Rush Cafeteria', 'U.A. High School Address', 87.2231, 4.5523),
    ('Nighteye Agency', 'Shibuya, Japan', 87.2231, 4.5523),
    ('Sport Festival Stadium', 'U.A. High School Address', 87.2231, 4.5523),
    ('Tatooin Station', 'Third District, Japan', 87.2231, 4.5523),
    ('Teachers Lounge', 'U.A. High School Address', 87.2231, 4.5523),
    ('Tokyo Sky Egg', 'Tokyo, Japan', 80.6231, 6.11523);

insert into Sighting (`Date`, `Description`, LocationId, HeroId)
values
	('2020-03-14', 'Foresty area', 1, 1);