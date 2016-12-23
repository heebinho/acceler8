-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 23, 2016 at 03:34 PM
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
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Task`
--

INSERT INTO `Task` (`id`, `name`, `meters`, `team`, `ts`) VALUES
(2, 'Test Task 1', 1000, 40, '2016-12-10 08:15:52'),
(4, 'Test Task 2', 2000, 40, '2016-12-10 19:21:09'),
(5, 'Test Task 3', 3000, 40, '2016-12-10 19:21:24'),
(6, 'Test Task 4', 4000, 40, '2016-12-10 19:21:35'),
(7, 'Test Task 5', 5000, 40, '2016-12-10 19:21:52'),
(8, 'Test Task 6', 6000, 40, '2016-12-10 19:22:08'),
(9, 'Test Task 7', 7000, 40, '2016-12-10 19:22:23'),
(10, 'Test Task 8', 8000, 40, '2016-12-10 19:22:56'),
(11, 'Test Task 9', 9000, 40, '2016-12-10 19:23:09'),
(12, 'Test Task 10', 10000, 40, '2016-12-10 19:23:22'),
(14, 'Test Task 2', 2000, 42, '2016-12-10 19:34:31');

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
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Team`
--

INSERT INTO `Team` (`id`, `name`, `description`, `city`, `country`, `website`, `isPrivate`, `coach`) VALUES
(40, 'Test Team 1', 'Nicht Privat', 'Bern', 'Schweiz', 'www.testteam1.ch', 0, 30),
(42, 'Test Team 2', 'Test Description 2 - Non Privat', 'Test City 2', 'Test Country 2', 'www.testteam2.ch', 0, 30),
(43, 'Test Team 3', 'Test Description 3 - Non Privat', 'Test City 3', 'Test Country 3', 'www.testteam3.ch', 0, 30),
(44, 'Test Team 4', 'Test Description 4 - Non Privat', 'Test City 4', 'Test Country 4', 'www.testteam4.ch', 0, 30),
(45, 'Test Team 5', 'Test Description 5 - Non Privat', 'Test City 5', 'Test Country 5', 'www.testteam5.ch', 0, 30),
(46, 'Test Team 6', 'Test Description 6 - Privat', 'Test City 6', 'Test Country 6', 'www.testteam6.ch', 1, 30),
(47, 'Test Team 7', 'Test Description 7 - Privat', 'Test City 7', 'Test Country 7', 'www.testteam7.ch', 1, 30),
(48, 'Test Team 8', 'Test Description 8 - Privat', 'Test City 8', 'Test Country 8', 'www.testteam8.ch', 1, 30),
(49, 'Test Team 9', 'Test Description 9 - Privat', 'Test City 9', 'Test Country 9', 'www.testteam9.ch', 1, 30),
(51, 'Test Team 10', 'Test Description 10 - Privat', 'Test City 10', 'Test Country 10', 'www.testteam10.ch', 1, 30);

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
(40, 30),
(42, 30),
(43, 30),
(44, 30),
(45, 30),
(46, 30),
(47, 30),
(48, 30),
(49, 30),
(51, 30),
(40, 31),
(42, 31),
(40, 32),
(40, 33),
(40, 34),
(40, 35),
(40, 36),
(40, 37),
(40, 38),
(40, 39);

-- --------------------------------------------------------

--
-- Table structure for table `Token`
--

CREATE TABLE IF NOT EXISTS `Token` (
  `token` char(36) NOT NULL,
  `user_id` int(11) NOT NULL,
  `type` varchar(8) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Token`
--

INSERT INTO `Token` (`token`, `user_id`, `type`, `ts`, `email`) VALUES
('ee039232-6e68-4643-8a0a-af30f024fb3c', 30, 'password', '2016-12-19 21:02:30', 'acceler8.user1@public-files.de'),
('82f56a1f-6cf8-485f-b902-ece8f2196265', 30, 'password', '2016-12-20 19:38:35', 'acceler8.user1@public-files.de');

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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `email`, `token`, `password`, `ts`, `validated`, `strava_code`, `strava_token`, `strava_token_public`, `strava_id`) VALUES
(30, 'acceler8.user1@public-files.de', 'e0c21304-d6df-43e4-a0c1-2af5a375eb95', 0x243261243130245333466d71464f4f6c7263557535474d724c4654626568795876596a49656675444977366535377833376836765a77594873396a43, '2016-12-20 19:44:22', 1, '63f4f8b1265ce5c91ec201751f105484a42b65fe', 'cc2b0149c8ab534bf810eb8e86d54f947f4bb695', '', 18901648),
(31, 'acceler8.user2@public-files.de', '66b1ce61-0503-44a8-a85c-9431a0107989', 0x243261243130244b78797736445a322e465754515376576c597771632e4549596f717262714e474a7346535575475458683472612e68332f4d362f53, '2016-12-22 10:27:27', 1, '7cb281698fb307f9ce57db9eef17b6bd88b13ccb', '6aedcd077bc94638395444a5d12c545c529d3825', '', 18902830),
(32, 'acceler8.user3@public-files.de', '67b1a6e0-f400-4377-9bec-c33bba10e9f9', 0x2432612431302462506f694d32746c584344533770575432654d65576559494c4d465131615378582f4e4e6a4c4b61384937623976716c3265517232, '2016-12-20 19:09:01', 1, '7d6cf1ba7a64457f315d80dd279097483cd38c5c', '40fc334425705ecefbb8eaa6c0f43f02131ec26e', NULL, 18905018),
(33, 'acceler8.user4@public-files.de', '81025c4f-8277-4192-a74a-c7c26736fd54', 0x24326124313024306c57726543335a6f714b507233574c46704e324e7536306f78625435326b636a552e55394b75346a474269634c4e4a4a31305036, '2016-12-20 19:10:15', 1, '8f94bddbd44766c6d256f228409043cffce2a2d0', '034b72d221bda1a9caae3b51060906401921f696', NULL, 18905042),
(34, 'acceler8.user5@public-files.de', '0e2792b6-8a6a-4aae-82bd-f412004bfa10', 0x24326124313024786b5664757a4f5054625454583459586c57496c474f5141657a41623832794f642e414b79394b6767353559416146316474446665, '2016-12-20 19:11:26', 1, 'e3251cbc75da3b2c46884246580259e3e0d9a5ca', 'abc0696fa7f39a96c5428ffa3a75a47e1c2d01e8', NULL, 18905059),
(35, 'acceler8.user6@public-files.de', 'd3b60f01-6465-48ff-8b25-5de06d66d79f', 0x243261243130247a49474e3535634b55636d42386f66353576637757754559686f5846686e38714b737a4f63782e6f766d43576b70454e6d4b53436d, '2016-12-20 19:12:30', 1, '18af8564300526003664ce4e0f44191aa55739b1', '315fcf0707fbaa889202044bdeab1e09055a8401', NULL, 18905074),
(36, 'acceler8.user7@public-files.de', '70abc551-5e33-435d-b263-7fe7e33aaece', 0x243261243130244576517a6c6961396741643556384930677343336e75674e41726d6665316a5030472e356a716e327a7571536753746b6f624e5075, '2016-12-20 19:13:44', 1, 'e67024380e07bd0f422db385f3100c73030aa67a', 'f8926f51cc12784630f9d93b257a853c18076846', NULL, 18905098),
(37, 'acceler8.user9@public-files.de', '716b03fe-ef48-4bf3-80ab-534d10614e01', 0x2432612431302441694f76465937426a4d5155682f344c50434359654f7273344855487247714c33553735326b6f6b4d6f614a69327a7833414f4261, '2016-12-20 19:15:46', 1, '1ac535cc90472d4da2950fe0f31eeaddf3c9e94b', 'ff1a7efef6bd3d77c44825509c09b3378a872548', NULL, 18905136),
(38, 'acceler8.user10@public-files.de', '98923906-04ba-47cb-ba5c-70fd8e81ed9f', 0x24326124313024756636644f6e4c4833696e324f55747564764133616534526b7032714d5a585452594f4b713567667a346246563748576a636c7357, '2016-12-20 19:16:56', 1, '1bac8823a1d452db342a817b163ee54e069e10b5', 'a5543d3ca1ec61acca883e6051f01b8b94a57e59', NULL, 18905164),
(39, 'acceler8.user8@public-files.de', '2be75db9-8803-408d-b503-b1f66d832be1', 0x24326124313024485074774b42494f3936506769494c326a4161473075397a534c48514549726f5968337956645170694f79695951464d4651395732, '2016-12-20 19:14:53', 1, '80fb4344f9a77e81e827d06b937d5c145e4422ae', '8183c40b71a301fdec27ad5b0f39630cce0165c1', NULL, 18905120);

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
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `Team`
--
ALTER TABLE `Team`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=53;
--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=40;
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
