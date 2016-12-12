-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 12, 2016 at 01:49 AM
-- Server version: 10.0.23-MariaDB-0+deb8u1
-- PHP Version: 5.6.20-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Acceler8`
--

-- --------------------------------------------------------

--
-- Table structure for table `Task`
--

CREATE TABLE IF NOT EXISTS `Task` (
`id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `meters` int(11) NOT NULL,
  `team` int(11) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Task`
--

INSERT INTO `Task` (`id`, `name`, `meters`, `team`, `ts`) VALUES
(1, 'Run', 9000, 25, '2016-12-12 00:31:47'),
(3, 'Warm-down', 3000, 25, '2016-12-01 00:32:00'),
(8, 'Interval Run', 4000, 25, '2016-12-12 00:46:16'),
(9, 'Run 2', 5000, 25, '2016-12-12 00:46:51');

-- --------------------------------------------------------

--
-- Table structure for table `Team`
--

CREATE TABLE IF NOT EXISTS `Team` (
`id` int(11) NOT NULL,
  `name` text NOT NULL,
  `description` text,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `website` varchar(255) NOT NULL,
  `isPrivate` tinyint(1) NOT NULL,
  `coach` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Team`
--

INSERT INTO `Team` (`id`, `name`, `description`, `city`, `country`, `website`, `isPrivate`, `coach`) VALUES
(25, 'Team RMG', 'Our Project Team', 'Zürich', 'Switzerland', '', 0, 28),
(27, 'Team IT', 'Operations IT Team', 'Zürich', 'Switzerland', '', 0, 29),
(36, 'Private Team', 'A private test team', 'Zürich', 'Switzerland', '', 1, 28),
(38, 'T', 't', 't', 't', '', 0, 28);

-- --------------------------------------------------------

--
-- Table structure for table `TeamMember`
--

CREATE TABLE IF NOT EXISTS `TeamMember` (
  `team_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `TeamMember`
--

INSERT INTO `TeamMember` (`team_id`, `user_id`) VALUES
(25, 23),
(25, 28),
(36, 28),
(25, 29);

-- --------------------------------------------------------

--
-- Table structure for table `Token`
--

CREATE TABLE IF NOT EXISTS `Token` (
  `token` char(36) NOT NULL,
  `userID` int(11) NOT NULL,
  `type` varchar(8) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Token`
--

INSERT INTO `Token` (`token`, `userID`, `type`, `ts`, `email`) VALUES
('d2f5936a-5bd5-4a14-83ee-3ae87d453e10', 12, 'password', '2016-11-11 22:50:46', 'renatoheeb@gmail.com'),
('251fc3f6-45bc-4dbe-9b18-4dc4dbf802ca', 28, 'password', '2016-11-28 02:01:56', 'renatoheeb@gmail.com'),
('c4ff5867-f750-459f-b01f-a8cd6e804b5d', 28, 'password', '2016-11-28 02:16:04', 'renatoheeb@gmail.com'),
('a784f636-61b0-468e-8acc-a91dfee646ca', 28, 'password', '2016-11-28 02:24:04', 'renatoheeb@gmail.com'),
('c7b20326-2842-4ce1-a390-2b8d5e8b9b4a', 28, 'password', '2016-11-28 02:49:07', 'renatoheeb@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE IF NOT EXISTS `User` (
`id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `password` binary(60) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `validated` tinyint(1) NOT NULL,
  `strava_code` varchar(255) DEFAULT NULL,
  `strava_token` varchar(255) DEFAULT NULL,
  `strava_token_public` varchar(255) DEFAULT NULL,
  `strava_id` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `email`, `token`, `password`, `ts`, `validated`, `strava_code`, `strava_token`, `strava_token_public`, `strava_id`) VALUES
(23, 'm.gantenbein@gmx.ch', NULL, 0x2432612431302443797259394753754e766178755536473147782f56656f466765626563572f67337043384c5769644c716646746d61736f69363932, '2016-11-28 19:53:37', 1, NULL, NULL, NULL, 17450356),
(28, 'renatoheeb@gmail.com', 'd6748431-3062-42b7-8aa4-03dce1217886', 0x243261243130244a5055652e514d374561344e472e616675595465782e684f75763536324d6871746b4b545651724b564372544b4d71694f6d6d4f47, '2016-12-10 18:41:44', 1, '8669e8665cc36ddcb03f8f4a7ce3c1fa0cbbbf00', '3feca3cf0576a610c1e4584a91bc23702fd87fc2', 'e78c9c482e29f13959790c450c171b9168e0f764', 15283400),
(29, 'pocev_84@hotmail.com', '991700ea-bb4c-4263-b122-de56972d2d00', 0x24326124313024544f673466596c2f72376a38753930693533772e562e6d6551385674332f594466555a7052686b694a2f4d364848764c6f5a5a6f75, '2016-12-10 23:08:50', 1, '7e74a5d46e33adefab0ab92805c667f75837e477', '77ce4a64693d20f2ee36b81548e7cc12376796f3', NULL, 18387369);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Task`
--
ALTER TABLE `Task`
 ADD PRIMARY KEY (`id`), ADD KEY `team` (`team`);

--
-- Indexes for table `Team`
--
ALTER TABLE `Team`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_coach` (`coach`);

--
-- Indexes for table `TeamMember`
--
ALTER TABLE `TeamMember`
 ADD PRIMARY KEY (`user_id`,`team_id`), ADD KEY `fk_team` (`team_id`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Task`
--
ALTER TABLE `Task`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `Team`
--
ALTER TABLE `Team`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=30;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Task`
--
ALTER TABLE `Task`
ADD CONSTRAINT `Task_ibfk_1` FOREIGN KEY (`team`) REFERENCES `Team` (`id`);

--
-- Constraints for table `Team`
--
ALTER TABLE `Team`
ADD CONSTRAINT `fk_coach` FOREIGN KEY (`coach`) REFERENCES `User` (`id`);

--
-- Constraints for table `TeamMember`
--
ALTER TABLE `TeamMember`
ADD CONSTRAINT `fk_team` FOREIGN KEY (`team_id`) REFERENCES `Team` (`id`),
ADD CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
