-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Cze 15, 2025 at 08:38 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aplikacjafitness`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `klienci`
--

CREATE TABLE `klienci` (
  `id` int(11) NOT NULL,
  `login` text NOT NULL,
  `haslo` text NOT NULL,
  `imie` text NOT NULL,
  `max_kalorie` int(11) NOT NULL,
  `max_bialko` int(11) NOT NULL,
  `max_wegle` int(11) NOT NULL,
  `max_tluszcze` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `klienci`
--

INSERT INTO `klienci` (`id`, `login`, `haslo`, `imie`, `max_kalorie`, `max_bialko`, `max_wegle`, `max_tluszcze`) VALUES
(3, '123', '321', '123', 466, 29, 58, 12);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `posilki`
--

CREATE TABLE `posilki` (
  `id` int(11) NOT NULL,
  `nazwa` text NOT NULL,
  `kalorie` int(11) NOT NULL,
  `wegle` int(11) NOT NULL,
  `bialko` int(11) NOT NULL,
  `tluszcze` int(11) NOT NULL,
  `data` date NOT NULL,
  `klient_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `zapisaneposilki`
--

CREATE TABLE `zapisaneposilki` (
  `id` int(11) NOT NULL,
  `nazwa` text NOT NULL,
  `kalorie` int(11) NOT NULL,
  `bialko` int(11) NOT NULL,
  `wegle` int(11) NOT NULL,
  `tluszcze` int(11) NOT NULL,
  `klient_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indeksy dla zrzutÃ³w tabel
--

--
-- Indeksy dla tabeli `klienci`
--
ALTER TABLE `klienci`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `posilki`
--
ALTER TABLE `posilki`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_klient_id` (`klient_id`);

--
-- Indeksy dla tabeli `zapisaneposilki`
--
ALTER TABLE `zapisaneposilki`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_klienci_id` (`klient_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `klienci`
--
ALTER TABLE `klienci`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `posilki`
--
ALTER TABLE `posilki`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `zapisaneposilki`
--
ALTER TABLE `zapisaneposilki`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `posilki`
--
ALTER TABLE `posilki`
  ADD CONSTRAINT `fk_klient_id` FOREIGN KEY (`klient_id`) REFERENCES `klienci` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `zapisaneposilki`
--
ALTER TABLE `zapisaneposilki`
  ADD CONSTRAINT `fk_klienci_id` FOREIGN KEY (`klient_id`) REFERENCES `klienci` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
