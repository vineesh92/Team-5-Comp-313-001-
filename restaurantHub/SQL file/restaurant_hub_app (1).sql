-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 10, 2019 at 11:15 AM
-- Server version: 5.6.39-cll-lve
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `restaurant_hub_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `adminlogin`
--

CREATE TABLE `adminlogin` (
  `id` int(11) NOT NULL,
  `Username` varchar(100) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `right` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adminlogin`
--

INSERT INTO `adminlogin` (`id`, `Username`, `Password`, `right`) VALUES
(1, 'manasamohanan2011@gmail.com', 'admin@123', 1),
(4, 'vishnurajnonline@gmail.com', 'admin', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_food`
--

CREATE TABLE `tbl_food` (
  `id` int(11) NOT NULL,
  `food_type` varchar(50) NOT NULL,
  `food_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_foodcategories`
--

CREATE TABLE `tbl_foodcategories` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `image` text NOT NULL,
  `owner_id` int(10) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_images`
--

CREATE TABLE `tbl_images` (
  `id` int(11) NOT NULL,
  `image_id` int(11) NOT NULL,
  `img_url` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_mobileuser`
--

CREATE TABLE `tbl_mobileuser` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `image` text NOT NULL,
  `login_with` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_offers`
--

CREATE TABLE `tbl_offers` (
  `id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_restourant`
--

CREATE TABLE `tbl_restourant` (
  `id` int(11) NOT NULL,
  `owner_id` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` text NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL,
  `ratting` int(11) NOT NULL,
  `zipcode` int(10) NOT NULL,
  `Vegtype` varchar(50) NOT NULL,
  `phone_no` varchar(30) NOT NULL,
  `time` text NOT NULL,
  `totime` varchar(50) NOT NULL,
  `video` text NOT NULL,
  `email` varchar(100) NOT NULL,
  `about` text NOT NULL,
  `fb` text NOT NULL,
  `tt` text NOT NULL,
  `gl` text NOT NULL,
  `tax` varchar(10) NOT NULL,
  `del_charge` varchar(10) NOT NULL,
  `menu_pdf` varchar(300) NOT NULL,
  `currency` varchar(10) NOT NULL,
  `ptype` varchar(20) NOT NULL,
  `puname` varchar(200) NOT NULL,
  `client` text NOT NULL,
  `secret` text NOT NULL,
  `password` varchar(255) NOT NULL,
  `signature` text NOT NULL,
  `sandbox_id` text NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `thumimage` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_userfeedback`
--

CREATE TABLE `tbl_userfeedback` (
  `id` int(11) NOT NULL,
  `res_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `ratting` text NOT NULL,
  `comment` text NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `notification` tinyint(1) NOT NULL,
  `user_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `webservice`
--

CREATE TABLE `webservice` (
  `id` int(11) NOT NULL,
  `desc` text NOT NULL,
  `url` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `webservice`
--

INSERT INTO `webservice` (`id`, `desc`, `url`) VALUES
(1, 'Display Restaurant List', 'http://vishnurajn.com/res/restaurantdetail.php'),
(2, 'Display Only One Restaurant Detail', 'http://vishnurajn.com/res/restaurantdetail.php?value=\"\"'),
(3, 'Search Restaurant Detail\r\n\r\n', 'http://vishnurajn.com/res/restaurantdetail.php?search=\"\"'),
(4, 'Display Food type All Restaurant ', 'http://vishnurajn.com/res/foodtype.php'),
(5, 'Display Food type only One Restaurant ', 'http://vishnurajn.com/res/foodtype.php?value=\"\"'),
(6, 'Display Food Category', 'http://vishnurajn.com/res/foodcategory.php'),
(7, 'Display all Restaurant Offers', 'http://vishnurajn.com/res/offersdetail.php'),
(8, 'Display only one Restaurant Offers', 'http://vishnurajn.com/res/offersdetail.php?value=\"\"'),
(9, 'Create Mobile User', 'http://vishnurajn.com/res/adduser.php?username=\"\"&&email=\"\"'),
(10, 'Update Mobile User', 'http://vishnurajn.com/res/updateuser.php?username=\"\"&&email=\"\"'),
(11, 'Add User Feedback', 'http://vishnurajn.com/res/userfeedback.php?res_id=\"\"&&user_id=\"\"&&ratting=\"\"&&comment=\"\"'),
(12, 'View Only One Restaurant User Feedback', 'http://vishnurajn.com/res/userfeedback.php?value=19');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adminlogin`
--
ALTER TABLE `adminlogin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_food`
--
ALTER TABLE `tbl_food`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_foodcategories`
--
ALTER TABLE `tbl_foodcategories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_images`
--
ALTER TABLE `tbl_images`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_mobileuser`
--
ALTER TABLE `tbl_mobileuser`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_offers`
--
ALTER TABLE `tbl_offers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_restourant`
--
ALTER TABLE `tbl_restourant`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_userfeedback`
--
ALTER TABLE `tbl_userfeedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `webservice`
--
ALTER TABLE `webservice`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adminlogin`
--
ALTER TABLE `adminlogin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_food`
--
ALTER TABLE `tbl_food`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=606;

--
-- AUTO_INCREMENT for table `tbl_foodcategories`
--
ALTER TABLE `tbl_foodcategories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `tbl_images`
--
ALTER TABLE `tbl_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=258;

--
-- AUTO_INCREMENT for table `tbl_mobileuser`
--
ALTER TABLE `tbl_mobileuser`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;

--
-- AUTO_INCREMENT for table `tbl_offers`
--
ALTER TABLE `tbl_offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_restourant`
--
ALTER TABLE `tbl_restourant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `tbl_userfeedback`
--
ALTER TABLE `tbl_userfeedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=156;

--
-- AUTO_INCREMENT for table `webservice`
--
ALTER TABLE `webservice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
