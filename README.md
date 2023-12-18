Creator:
Tabassum sumaiya
Hasibul Hossain
Shakibul Hasan Prince
Jahid Hossain Rana

This is a simple project managment sytem 
created Using Javafx, CSS, Database
Created for Advanced OOP Project  fall 2023
Sql
-- Create table structure for 'user' table
CREATE TABLE `workslink`.`user` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `userName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `dob` date,
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `questions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `user_bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `user_img` mediumblob,
  PRIMARY KEY (`id`),
  INDEX `userName` USING HASH (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- Create table structure for 'space_info' table
CREATE TABLE `workslink`.`space_info` (
  `user_id` int(50),
  `space_Id` int(100) NOT NULL AUTO_INCREMENT,
  `space_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `space_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `start_date` date,
  `end_date` date,
  `calcDays` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `members` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`space_Id`),
  INDEX `space_Id` USING BTREE (`space_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create table structure for 'user_requests' table
CREATE TABLE `workslink`.`user_requests` (
  `request_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_user_id` int(11),
  `receiver_user_id` int(11),
  `request_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'PENDING',
  `sender_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`request_id`),
  INDEX `request_id` USING BTREE (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create table structure for 'task_info' table
CREATE TABLE `workslink`.`task_info` (
  `space_Id` int(100),
  `task_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `task_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `task_start_date` date,
  `priority` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `assigned` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create table structure for 'assignedspace' table
CREATE TABLE `workslink`.`assignedspace` (
  `userName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `assignedSpace` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `spaceOwnerName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create table structure for 'members' table
CREATE TABLE `workslink`.`members` (
  `userID` int(100),
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `userName` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `dob` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`id`),
  INDEX `index_userID` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
