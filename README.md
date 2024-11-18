Project Description: In response to the dynamic landscape of modern collaborative work, our project stands as a pioneering solution, redefining the contours of project management. Meticulously crafted to meet the evolving needs of individuals and teams engaged in diverse projects, the platform encompasses a rich array of features designed to empower users, foster collaboration, and provide a seamless project management experience.

List of Features:

Customizable Workspaces
Add Spaces Button
Resources Integration
Real-Time Project Overview Table
Member Collaboration and Addition
Profile Insights
Dynamic Progress Indicator
Intuitive Icons for Workspaces
User-Friendly Interface
These features collectively contribute to the project's goal of providing a dynamic and user-centric platform for project management in collaborative workspaces. Each feature is carefully designed to enhance the platform's functionality, user experience, and collaboration.
## Screenshot
![Screenshot 2024-06-27 124917](https://github.com/Heisenberg293/WorksLink0/assets/148477986/3dd82421-eae0-48d7-98a2-6b3cd433af42)
![Screenshot 2024-06-27 124948](https://github.com/Heisenberg293/WorksLink0/assets/148477986/7bd88f4b-8741-48b0-932c-58f2ae677b76)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/5be53ee3-c4e7-4454-81c9-718bf78197f3)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/2c51c5d1-2bc4-4aed-a13e-b6b94217aff6)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/bf40da02-4587-436d-b82a-f1fffbf3986b)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/82cc511c-bd2d-4023-a54c-193a49c57ccc)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/ba697f33-5dd1-4f26-86ed-39955066223c)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/500776fe-6b01-4322-97b3-f83fceb730f9)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/ac785954-8bf8-4120-ac16-b57274f58c10)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/c94c43c1-18e6-4b40-b816-570f3c568ee7)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/0ed34e12-29c6-4255-8439-b9c47bb1a624)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/74599b8f-cff5-44a0-a145-eb40dddb18f9)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/dd626445-5575-4246-81c4-16658c384bae)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/2d5a1453-9432-4eca-acb7-02bf1cbde24b)

![image](https://github.com/Heisenberg293/WorksLink0/assets/148477986/8cfa99b8-ee01-424a-ba7d-b849da317cd5)

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



-------------------------------------------------
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
-------------------------------------------------

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
-------------------------------------------------

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
-------------------------------------------------

-- Create table structure for 'assignedspace' table
CREATE TABLE `workslink`.`assignedspace` (
  `userName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `assignedSpace` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `spaceOwnerName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
-------------------------------------------------

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
