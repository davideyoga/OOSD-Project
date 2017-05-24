-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 24, 2017 at 06:30 PM
-- Server version: 5.6.35
-- PHP Version: 7.0.15

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

CREATE TABLE `game` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(128) NOT NULL,
  `exp` int(10) UNSIGNED NOT NULL,
  `description` text,
  `image` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `game`
--

INSERT INTO `game` (`id`, `name`, `exp`, `description`, `image`) VALUES
(10, 'Game1', 25, 'Game1 description', 'default.png'),
(11, 'Game2', 50, 'Game2 description', 'default.png'),
(12, 'Game3', 75, 'Game3 description', 'default.png'),
(13, 'Game4', 100, 'Game4 description', 'default.png'),
(14, 'Game5', 125, 'Game5 description test', '1495643055062unnamed(1).png'),
(15, 'Game6', 88, 'Game6 description', '1495643209010unnamed.png');

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`id`, `name`, `description`) VALUES
(1, 'Amministratori', NULL),
(2, 'Moderators', 'Mods group');

-- --------------------------------------------------------

--
-- Table structure for table `groupsservice`
--

CREATE TABLE `groupsservice` (
  `id_groups` int(11) UNSIGNED NOT NULL,
  `id_service` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groupsservice`
--

INSERT INTO `groupsservice` (`id_groups`, `id_service`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 6);

-- --------------------------------------------------------

--
-- Table structure for table `level`
--

CREATE TABLE `level` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` int(128) NOT NULL,
  `trophy` varchar(128) NOT NULL,
  `icon` varchar(256) NOT NULL,
  `exp` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `level`
--

INSERT INTO `level` (`id`, `name`, `trophy`, `icon`, `exp`) VALUES
(0, 0, '0', 'zero.png', 0),
(1, 1, 'TrofeoLivello1', 'one.png', 100),
(2, 2, 'TrofeoLivello2', '1495117523605two.png', 200),
(3, 3, 'TrofeoLivello3', 'three.png', 300),
(4, 4, 'TrofeoLivello4', 'four.png', 400),
(5, 5, 'TrofeoLivello5', 'five.png', 500),
(6, 6, 'TrofeoLivello6', 'six.png', 600),
(7, 7, 'TrofeoLivello7', 'seven.png', 700),
(8, 8, 'TrofeoLivello8', '1495642831734eigth.png', 800);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id_user` int(10) UNSIGNED NOT NULL,
  `id_game` int(10) UNSIGNED NOT NULL,
  `title` varchar(128) NOT NULL,
  `body` text NOT NULL,
  `vote` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id_user`, `id_game`, `title`, `body`, `vote`) VALUES
(11, 10, ' ', '9pyoiguk', 5),
(11, 15, ' ', 'test review edit', 4);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`id`, `name`, `description`) VALUES
(1, 'user', NULL),
(2, 'game', 'game service'),
(3, 'level', 'Allow the user to edit/add/delete levels/trophies'),
(4, 'groups', NULL),
(5, 'service', NULL),
(6, 'review', 'review service');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(10) UNSIGNED NOT NULL,
  `username` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `password` varchar(40) NOT NULL,
  `exp` int(10) UNSIGNED NOT NULL,
  `avatar` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `name`, `surname`, `email`, `password`, `exp`, `avatar`) VALUES
(4, 'pippo', 'pippo', 'pippo', 'pippo@pippo.pippo', 'd012f68144ed0f121d3cc330a17eec528c2e7d59', 0, 'default.png'),
(7, 'ValentOne', 'Stefano2', 'Valentina', 'stefanovalentini@gmail.com', 'e284cb61b7e902c547f76f6e515a742107e08250', 0, 'default.png'),
(8, 'Greg', 'Luca', 'Di Gregorio Zitella', 'lucaDiGreg@aaa.it', '86f7e437faa5a7fce15d1ddcb9eaeaea377667b8', 10, 'default.png'),
(9, 'Mic', 'Davide', 'Micarelli', 'davide@micarelli.it', 'd012f68144ed0f121d3cc330a17eec528c2e7d59', 0, 'default.png'),
(11, 'admin', 'admin', 'admin', 'admin@admin.admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 0, '1495640622980Screen Shot 2017-05-24 at 17.38.35.png');

-- --------------------------------------------------------

--
-- Table structure for table `usergame`
--

CREATE TABLE `usergame` (
  `id` int(10) NOT NULL,
  `id_user` int(10) UNSIGNED NOT NULL,
  `id_game` int(10) UNSIGNED NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `usergroups`
--

CREATE TABLE `usergroups` (
  `id_user` int(10) UNSIGNED NOT NULL,
  `id_groups` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usergroups`
--

INSERT INTO `usergroups` (`id_user`, `id_groups`) VALUES
(4, 2),
(11, 1);

-- --------------------------------------------------------

--
-- Table structure for table `userlevel`
--

CREATE TABLE `userlevel` (
  `id` int(11) NOT NULL,
  `id_user` int(10) UNSIGNED NOT NULL,
  `id_level` int(10) UNSIGNED NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `userlevel`
--

INSERT INTO `userlevel` (`id`, `id_user`, `id_level`, `date`) VALUES
(10, 11, 0, '2017-05-24 17:43:43');

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
  ADD UNIQUE KEY `id_groups_2` (`id_groups`,`id_service`),
  ADD KEY `id_groups` (`id_groups`),
  ADD KEY `id_service` (`id_service`);

--
-- Indexes for table `level`
--
ALTER TABLE `level`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD UNIQUE KEY `id_user_2` (`id_user`,`id_game`) USING BTREE,
  ADD KEY `review_ibfk_1` (`id_game`),
  ADD KEY `id_user` (`id_user`);

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
  ADD KEY `id_user_3` (`id_user`),
  ADD KEY `id_game_3` (`id_game`);

--
-- Indexes for table `usergroups`
--
ALTER TABLE `usergroups`
  ADD UNIQUE KEY `id_user_2` (`id_user`,`id_groups`),
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
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `level`
--
ALTER TABLE `level`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `usergame`
--
ALTER TABLE `usergame`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `userlevel`
--
ALTER TABLE `userlevel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
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
  ADD CONSTRAINT `usergame_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `usergame_ibfk_2` FOREIGN KEY (`id_game`) REFERENCES `game` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

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
  ADD CONSTRAINT `userlevel_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `userlevel_ibfk_2` FOREIGN KEY (`id_level`) REFERENCES `level` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
