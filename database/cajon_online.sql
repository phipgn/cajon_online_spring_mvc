-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 21, 2019 at 06:32 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cajon_online`
--

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `carts`
--

INSERT INTO `carts` (`id`, `user_id`) VALUES
(4, 1),
(5, 8),
(6, 9),
(7, 10);

-- --------------------------------------------------------

--
-- Table structure for table `cart_detail`
--

CREATE TABLE `cart_detail` (
  `id` int(11) NOT NULL,
  `cart_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `address` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `phone` varchar(11) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `created_date` datetime NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `user_id`, `name`, `address`, `phone`, `created_date`, `status`) VALUES
(47, 10, 'Ph&#7841;m Lý Qu&#7923;nh', '123/46 Lò Lu, Tr&#432;&#7901;ng Th&#7841;nh, Qu&#7853;n 9, TP.HCM', '0984466373', '2019-09-21 11:29:32', 0),
(48, 10, 'Ph&#7841;m Lý Qu&#7923;nh', '123/46 Lò Lu, Tr&#432;&#7901;ng Th&#7841;nh, Qu&#7853;n 9, TP.HCM', '0984466373', '2019-09-21 11:29:45', 0);

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_name` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `product_image` varchar(100) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `order_detail`
--

INSERT INTO `order_detail` (`id`, `order_id`, `product_id`, `product_name`, `product_image`, `quantity`, `unit_price`) VALUES
(76, 47, 6, 'Tr&#7889;ng cajon giá r&#7867;', 'cajon-dcd-1.jpg', 1, 600000),
(77, 47, 5, 'Cajon Pyle String Pro', 'cajon-pyle-string-pro.jpg', 1, 790000),
(78, 47, 4, 'Tr&#7889;ng cajon cao c&#7845;p h&#7885;a ti&#7871;t &#273;&#7897;c &#273;áo', 'cajon-gon-bops.jpg', 1, 6900000),
(79, 47, 7, 'Tr&#7889;ng cajon giá t&#7889;t', 'cajon-hcaj1nt.jpg', 2, 990000),
(80, 47, 19, 'Tr&#7889;ng cajon cao c&#7845;p LP &#273;en', 'cajon-lp1428ny.jpg', 1, 9900000),
(81, 47, 20, 'Cajon trung c&#7845;p t&#7863;ng kèm bao da', 'cajon-echoslap.jpg', 1, 700000),
(82, 47, 21, 'Cajon ch&#7845;t l&#432;&#7907;ng cao &#272;ài Loan', 'cajon-box-drum.jpg', 3, 2500000),
(83, 48, 4, 'Tr&#7889;ng cajon cao c&#7845;p h&#7885;a ti&#7871;t &#273;&#7897;c &#273;áo', 'cajon-gon-bops.jpg', 1, 6900000),
(84, 48, 5, 'Cajon Pyle String Pro', 'cajon-pyle-string-pro.jpg', 1, 790000),
(85, 48, 6, 'Tr&#7889;ng cajon giá r&#7867;', 'cajon-dcd-1.jpg', 1, 600000);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(300) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `image` varchar(300) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `description` varchar(1000) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `unit_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `image`, `description`, `unit_price`) VALUES
(4, 'Tr&#7889;ng cajon cao c&#7845;p h&#7885;a ti&#7871;t &#273;&#7897;c &#273;áo', 'cajon-gon-bops.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 6900000),
(5, 'Cajon Pyle String Pro', 'cajon-pyle-string-pro.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 790000),
(6, 'Tr&#7889;ng cajon giá r&#7867;', 'cajon-dcd-1.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 600000),
(7, 'Tr&#7889;ng cajon giá t&#7889;t', 'cajon-hcaj1nt.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 990000),
(19, 'Tr&#7889;ng cajon cao c&#7845;p LP &#273;en', 'cajon-lp1428ny.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 9900000),
(20, 'Cajon trung c&#7845;p t&#7863;ng kèm bao da', 'cajon-echoslap.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 700000),
(21, 'Cajon ch&#7845;t l&#432;&#7907;ng cao &#272;ài Loan', 'cajon-box-drum.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 2500000),
(22, 'Cajon g&#7895; cao c&#7845;p &#273;&#7887;', 'cajon-pcjd15.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 3000000),
(23, 'Cajon g&#7895; ch&#7845;t l&#432;&#7907;ng cao', 'cajon-pro-ab-d6.jpg', 'Dáng: &#273;&#7913;ng. Kích th&#432;&#7899;c: 29 x 29 x 46 cm. N&#432;&#7899;c s&#417;n: PU cao c&#7845;p. Dây: snare. B&#7843;o hành: 1 n&#259;m.', 690000);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'user'),
(2, 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `password` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `address` varchar(150) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `phone` varchar(10) COLLATE utf8mb4_vietnamese_ci NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_vietnamese_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `name`, `address`, `phone`, `role_id`) VALUES
(3, 'quantri', '123456', '', '', '0', 2),
(5, 'quanly', '123456', '', '', '0', 2),
(10, 'lyquynh', '123456', 'Ph&#7841;m Lý Qu&#7923;nh', '123/4687 Lò Lu, Tr&#432;&#7901;ng Th&#7841;nh, Qu&#7853;n 9, TP.HCM', '0984466373', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cart_detail`
--
ALTER TABLE `cart_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `carts`
--
ALTER TABLE `carts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `cart_detail`
--
ALTER TABLE `cart_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=158;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
