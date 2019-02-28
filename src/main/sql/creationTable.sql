CREATE TABLE `users` (
  `id` int(200) NOT NULL AUTO_INCREMENT,
  `isAdmin` varchar (5) NOT NULL DEFAULT false,
  `user_identifiant` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `mail` varchar (30) NOT NULL,
  `dob` date NOT NULL,
  `pass` varchar (500) NOT NULL,
  `description` varchar (5000) DEFAULT '',
  `defaultAvatar` varchar (50) DEFAULT 'favicon.ico' ,
  `theme` varchar (10) DEFAULT 'clair',
  PRIMARY KEY (`id`)
);

CREATE TABLE `posts` (
  `id` int (200) NOT NULL AUTO_INCREMENT,
  `user_id` varchar (30) NOT NULL,
  `date_of_post` date NOT NULL,
  `time_of_post` time NOT NULL,
  `texte` varchar (5000) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `stalks` (
  `user_id` int (200) NOT NULL,
  `user_stalked` varchar (5000) DEFAULT NULL
);

CREATE TABLE `postLike` (
  `post_id` int (200) NOT NULL,
  `userLike_id` int (200) NOT NULL
);


