-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 18, 2022 at 03:08 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `librarydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(10) NOT NULL,
  `name` text NOT NULL,
  `password` varchar(86) NOT NULL,
  `email` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `name`, `password`, `email`) VALUES
(1, 'dilshara1', 'Dilshara Mithum', '12345', 'dilsharamithum3@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `bookdetails`
--

CREATE TABLE `bookdetails` (
  `BookID` int(11) NOT NULL,
  `BookName` varchar(56) NOT NULL,
  `Author` varchar(26) DEFAULT NULL,
  `Quantity` int(11) NOT NULL,
  `BookShelf` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bookdetails`
--

INSERT INTO `bookdetails` (`BookID`, `BookName`, `Author`, `Quantity`, `BookShelf`) VALUES
(1, 'wfafwa', 'fwafwa', 27, 2),
(2, 'bdzb', 'bdbdz', 1, 5),
(3, 'hhhh', 'sfes', 3, 53),
(4, 'hhhh', 'sfes', 3, 53),
(5, 'hhhh', 'sfes', 2, 53),
(6, 'hhhhwafg', 'sfes', 1, 53);

-- --------------------------------------------------------

--
-- Table structure for table `dayfines`
--

CREATE TABLE `dayfines` (
  `ID` int(11) NOT NULL,
  `Fines` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `dayfines`
--

INSERT INTO `dayfines` (`ID`, `Fines`) VALUES
(1, 70);

-- --------------------------------------------------------

--
-- Table structure for table `fines`
--

CREATE TABLE `fines` (
  `ID` int(11) NOT NULL,
  `FinesAmount` int(11) NOT NULL,
  `MemberID` int(11) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fines`
--

INSERT INTO `fines` (`ID`, `FinesAmount`, `MemberID`, `Date`) VALUES
(1, 50, 1650, '2022-09-08'),
(2, 50, 1250, '1899-08-16'),
(3, 50, 800, '2022-10-14'),
(4, 50, 800, '2022-10-14'),
(5, 50, 600, '2022-10-14'),
(6, 50, 1600, '2022-10-14'),
(7, 50, 1600, '2022-10-14'),
(8, 50, 1900, '2022-10-14'),
(9, 50, 450, '2022-10-14');

-- --------------------------------------------------------

--
-- Table structure for table `issuebooks`
--

CREATE TABLE `issuebooks` (
  `id` int(11) NOT NULL,
  `BookID` int(11) NOT NULL,
  `BookName` varchar(56) NOT NULL,
  `MemberID` int(11) NOT NULL,
  `MemberName` varchar(26) NOT NULL,
  `IssueDate` date NOT NULL,
  `ReturnDate` date NOT NULL,
  `Status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `issuebooks`
--

INSERT INTO `issuebooks` (`id`, `BookID`, `BookName`, `MemberID`, `MemberName`, `IssueDate`, `ReturnDate`, `Status`) VALUES
(1, 1, 'wfafwa', 1, 'Dilshara', '2022-10-05', '2022-10-26', 'Returned'),
(2, 1, 'wfafwa', 1, 'Dilshara', '2022-10-12', '2022-10-19', 'Returned'),
(3, 1, 'wfafwa', 1, 'Dilshara', '2022-10-06', '2022-10-20', 'Returned'),
(4, 1, 'wfafwa', 1, 'Dilshara', '2022-10-06', '2022-10-27', 'Returned'),
(5, 2, 'bdzb', 1, 'Dilshara', '2022-09-04', '2022-09-19', 'Returned'),
(6, 3, 'hhhh', 1, 'Dilshara', '2022-09-01', '2022-09-11', 'Returned'),
(7, 1, 'wfafwa', 1, 'Dilshara', '2022-09-06', '2022-09-28', 'Returned'),
(8, 1, 'wfafwa', 1, 'Dilshara', '2022-09-06', '2022-10-02', 'Returned'),
(9, 1, 'wfafwa', 1, 'Dilshara', '2022-09-05', '2022-09-12', 'Returned'),
(10, 1, 'wfafwa', 1, 'Dilshara', '2022-09-05', '2022-09-06', 'Returned'),
(11, 2, 'bdzb', 1, 'Dilshara', '2022-09-05', '2022-09-06', 'Pending'),
(12, 3, 'hhhh', 1, 'Dilshara', '2022-05-02', '2022-10-05', 'Returned'),
(13, 1, 'wfafwa', 1, 'Dilshara', '2022-10-02', '2022-10-12', 'Pending'),
(14, 1, '', 1, '', '2022-10-04', '2022-10-18', 'Pending'),
(15, 3, 'hhhh', 1, 'Dilshara', '2022-10-16', '2022-10-31', 'Pending'),
(16, 1, '', 1, '', '2022-10-23', '2022-10-31', 'Pending'),
(17, 1, '', 1, '', '2022-10-16', '2022-10-23', 'Pending'),
(18, 1, '', 1, '', '2022-10-17', '2022-10-31', 'Pending'),
(19, 1, 'wfafwa', 1, 'Invalid Member ID', '2022-10-16', '2022-10-30', 'Pending'),
(20, 1, '', 1, '', '2022-10-23', '2022-10-31', 'Pending'),
(21, 1, 'bdzb', 1, 'Dilshara', '2022-10-23', '2022-10-31', 'Pending'),
(22, 1, 'wfafwa', 1, 'Dilshara', '2022-10-28', '2022-10-29', 'Pending'),
(23, 4, 'hhhh', 1, 'Dilshara', '2022-08-09', '2022-08-22', 'Pending'),
(24, 5, 'hhhh', 1, 'Dilshara', '2022-08-01', '2022-08-22', 'Pending'),
(25, 6, 'hhhhwafg', 1, 'Dilshara', '2022-08-24', '2022-08-29', 'Pending'),
(26, 2, 'bdzb', 2, 'Gimhana', '2022-08-01', '2022-09-20', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `MemberID` int(11) NOT NULL,
  `Name` varchar(26) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `ContactNum` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`MemberID`, `Name`, `Email`, `ContactNum`) VALUES
(1, 'Dilshara', 'dilsharamithum3@gmail.com', 767654324),
(2, 'Gimhana', 'wfawfaw@ga.bn', 545454);

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `ID` int(11) NOT NULL,
  `BookID` int(11) NOT NULL,
  `BookName` varchar(56) NOT NULL,
  `MemberID` int(11) NOT NULL,
  `MemberName` varchar(26) NOT NULL,
  `ReserveDate` date NOT NULL,
  `ToReserveDate` date NOT NULL,
  `Status` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`ID`, `BookID`, `BookName`, `MemberID`, `MemberName`, `ReserveDate`, `ToReserveDate`, `Status`) VALUES
(3, 1, 'wfafwa', 1, 'Dilshara', '2022-10-16', '2022-10-23', 'Released'),
(4, 1, 'wfafwa', 1, 'Dilshara', '2022-10-16', '2022-10-23', 'Released'),
(5, 2, 'bdzb', 1, 'Dilshara', '2022-10-23', '2022-10-31', 'Released'),
(6, 1, 'wfafwa', 1, 'Dilshara', '2022-10-23', '2022-10-31', 'Released'),
(7, 1, 'wfafwa', 1, 'Dilshara', '2022-10-28', '2022-10-29', 'Released'),
(8, 1, 'wfafwa', 2, 'Gimhana', '2022-10-17', '2022-10-31', 'Reserved');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `bookdetails`
--
ALTER TABLE `bookdetails`
  ADD PRIMARY KEY (`BookID`);

--
-- Indexes for table `dayfines`
--
ALTER TABLE `dayfines`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `fines`
--
ALTER TABLE `fines`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `issuebooks`
--
ALTER TABLE `issuebooks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`MemberID`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `bookdetails`
--
ALTER TABLE `bookdetails`
  MODIFY `BookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `fines`
--
ALTER TABLE `fines`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `issuebooks`
--
ALTER TABLE `issuebooks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `MemberID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
