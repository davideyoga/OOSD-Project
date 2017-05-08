-- phpMyAdmin SQL Dump
-- version 4.4.15.5
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: May 08, 2017 at 01:58 PM
-- Server version: 5.5.49-log
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gamingplatform`
--

-- --------------------------------------------------------

--
-- Table structure for table `game`
--

CREATE TABLE IF NOT EXISTS `game` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(128) NOT NULL,
  `exp` int(10) unsigned NOT NULL,
  `description` text,
  `image` varchar(128) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `game`
--

INSERT INTO `game` (`id`, `name`, `exp`, `description`, `image`) VALUES
(10, 'Game1', 25, 'Game1 description', 'default.png'),
(11, 'Game2', 50, 'Game2 description', 'default.png'),
(12, 'Game3', 75, 'Game3 description', 'default.png'),
(13, 'Game4', 100, 'Game4 description', 'default.png'),
(14, 'Game5', 125, 'Game5 description', 'default.png');

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` text
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`id`, `name`, `description`) VALUES
(1, 'Amministratori', NULL),
(2, 'Moderatori', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `groupsservice`
--

CREATE TABLE IF NOT EXISTS `groupsservice` (
  `id_groups` int(11) unsigned NOT NULL,
  `id_service` int(11) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupsservice`
--

INSERT INTO `groupsservice` (`id_groups`, `id_service`) VALUES
(1, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `level`
--

CREATE TABLE IF NOT EXISTS `level` (
  `id` int(10) unsigned NOT NULL,
  `name` int(128) NOT NULL,
  `trophy` varchar(128) NOT NULL,
  `icon` varchar(256) NOT NULL,
  `exp` int(10) unsigned NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `level`
--

INSERT INTO `level` (`id`, `name`, `trophy`, `icon`, `exp`) VALUES
(1, 1, 'TrofeoLivello1', 'IconLivello1', 0),
(2, 2, 'TrofeoLivello2', 'IconLivello2', 10),
(3, 7, 'TrofeoLivello7', 'IconLivello7', 60),
(4, 3, 'TrofeoLivello3', 'IconLivello3', 20),
(5, 4, 'TrofeoLivello4', 'IconLivello4', 30),
(6, 5, 'TrofeoLivello5', 'IconLivello5', 40),
(7, 6, 'TrofeoLivello6', 'IconLivello6', 50);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE IF NOT EXISTS `review` (
  `id_user` int(10) unsigned NOT NULL,
  `id_game` int(10) unsigned NOT NULL,
  `title` varchar(128) NOT NULL,
  `body` text NOT NULL,
  `vote` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE IF NOT EXISTS `service` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` text
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`id`, `name`, `description`) VALUES
(1, 'user', NULL),
(2, 'game', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL,
  `username` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password` varchar(40) NOT NULL,
  `exp` int(10) unsigned NOT NULL,
  `avatar` varchar(128) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `name`, `surname`, `email`, `password`, `exp`, `avatar`) VALUES
(1, 'admin', 'Admin', 'GamingPlatform', 'admin@gamingplatform.it', 'd033e22ae348aeb5660fc2140aec35850c4da997', 0, 'default.png'),
(4, 'pippo', 'pippo', 'pippo', 'pippo@pippo.pippo', 'd012f68144ed0f121d3cc330a17eec528c2e7d59', 0, 'default.png'),
(6, 'Nymeria', 'Valentina', 'Cecchini', 'valentina.cecchini.94@gmail.com', 'pippo', 30, ''),
(7, 'ValentOne', 'Stefano', 'Valentina', 'stefanovalentini@gmail.com', 'pippo', 20, ''),
(8, 'Greg', 'Luca', 'Di Gregorio Zitella', 'lucaDiGreg@aaa.it', 'pippo', 10, ''),
(9, 'Mic', 'Davide', 'Micarelli', 'davide@micarelli.it', 'pippo', 50, '');

-- --------------------------------------------------------

--
-- Table structure for table `usergame`
--

CREATE TABLE IF NOT EXISTS `usergame` (
  `id` int(10) NOT NULL,
  `id_user` int(10) unsigned NOT NULL,
  `id_game` int(10) unsigned NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `usergroups`
--

CREATE TABLE IF NOT EXISTS `usergroups` (
  `id_user` int(10) unsigned NOT NULL,
  `id_groups` int(10) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usergroups`
--

INSERT INTO `usergroups` (`id_user`, `id_groups`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `userlevel`
--

CREATE TABLE IF NOT EXISTS `userlevel` (
  `id` int(11) NOT NULL,
  `id_user` int(10) unsigned NOT NULL,
  `id_level` int(10) unsigned NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `userlevel`
--

INSERT INTO `userlevel` (`id`, `id_user`, `id_level`, `date`) VALUES
(1, 6, 5, '2017-04-15 00:00:00'),
(2, 7, 4, '2017-05-01 00:00:00'),
(3, 8, 2, '2017-04-18 00:00:00'),
(4, 9, 7, '2017-05-04 00:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `groupsservice`
--
ALTER TABLE `groupsservice`
  ADD KEY `id_groups` (`id_groups`),
  ADD KEY `id_service` (`id_service`),
  ADD KEY `id_groups_2` (`id_groups`),
  ADD KEY `id_service_2` (`id_service`);

--
-- Indexes for table `level`
--
ALTER TABLE `level`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD KEY `id_user` (`id_user`,`id_game`),
  ADD KEY `review_ibfk_1` (`id_game`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `usergame`
--
ALTER TABLE `usergame`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_user_2` (`id_user`),
  ADD UNIQUE KEY `id_game_2` (`id_game`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_game` (`id_game`),
  ADD KEY `id_user_3` (`id_user`),
  ADD KEY `id_game_3` (`id_game`);

--
-- Indexes for table `usergroups`
--
ALTER TABLE `usergroups`
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_group` (`id_groups`);

--
-- Indexes for table `userlevel`
--
ALTER TABLE `userlevel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_level` (`id_level`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `game`
--
ALTER TABLE `game`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `level`
--
ALTER TABLE `level`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `usergame`
--
ALTER TABLE `usergame`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `userlevel`
--
ALTER TABLE `userlevel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `groupsservice`
--
ALTER TABLE `groupsservice`
  ADD CONSTRAINT `groupsservice_ibfk_1` FOREIGN KEY (`id_groups`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `groupsservice_ibfk_2` FOREIGN KEY (`id_service`) REFERENCES `service` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `review_ibfk_1` FOREIGN KEY (`id_game`) REFERENCES `game` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `review_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `usergame`
--
ALTER TABLE `usergame`
  ADD CONSTRAINT `usergame_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usergame_ibfk_2` FOREIGN KEY (`id_game`) REFERENCES `game` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `usergroups`
--
ALTER TABLE `usergroups`
  ADD CONSTRAINT `usergroups_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usergroups_ibfk_2` FOREIGN KEY (`id_groups`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `userlevel`
--
ALTER TABLE `userlevel`
  ADD CONSTRAINT `userlevel_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `userlevel_ibfk_2` FOREIGN KEY (`id_level`) REFERENCES `level` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
