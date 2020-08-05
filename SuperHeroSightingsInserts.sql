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
    ('League of Villains', 'Organization for powerful villains.', 'Unknown', 'No info'),
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
	('2020-03-14', 'Walking around toward the evening, with a briefcase', 2, 2),
    ('2020-03-11', 'Possible spotting of All For One in a black sedan with tinted windows', 1, 1),
    ('2020-02-24', 'Was talking with someone, possibly All Might?', 11, 3),
    ('2020-02-17', 'Seen flying around briefly outside', 12, 5),
    ('2020-01-18', 'Was seen breifly before vanishing around an alleyway', 6, 7),
    ('2020-01-13', 'Sitting at a table and eating dumplings', 7, 2),
    ('2020-01-01', 'Waiting in line to get the special of the day', 7, 6),
    ('2019-12-31', 'Window shopping, or maybe waiting for someone', 6, 11),
    ('2019-11-19', 'Training by himself during the early morning', 9, 10),
    ('2019-09-22', 'Seen near the school gates, maybe talking with a different student', 3, 8),
    ('2019-08-26', 'Walking around by herself while listening to music', 4, 4),
    ('2019-08-05', 'Practicing with some new gear during the morning', 9, 3),
    ('2019-05-25', 'Walked out of the agency during the late evening', 8, 2),
    ('2019-03-12', 'Seen walking into the agency around midday', 8, 9);