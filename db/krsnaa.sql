DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyCode` varchar(20) NOT NULL,
  `CompanyName` varchar(100) NOT NULL,
  `CreatedBy` int(11) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedBy` int(11) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_CompanyCode_Unique` (`CompanyCode`)
);
--
-- Dumping data for table `company`
--

INSERT INTO `company` (`Id`, `CompanyCode`, `CompanyName`, `CreatedBy`, `CreatedDate`, `ModifiedBy`, `ModifiedDate`) VALUES
(1, 'KRSNAA', 'Krsnaa Diagnostics Pvt Ltd', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20');

-- --------------------------------------------------------
--
-- Table structure for table `designation`
--

DROP TABLE IF EXISTS `designation`;
CREATE TABLE IF NOT EXISTS `designation` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyId` int(11) NOT NULL,
  `DesignationCode` varchar(20) NOT NULL,
  `DesignationName` varchar(100) NOT NULL,
  `CreatedBy` int(11) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedBy` int(11) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_Designation_DesignationCode_U` (`DesignationCode`),
  KEY `IX_Designation_CompanyId` (`CompanyId`)
);

--
-- Dumping data for table `designation`
--

INSERT INTO `designation` (`Id`, `CompanyId`, `DesignationCode`, `DesignationName`, `CreatedBy`, `CreatedDate`, `ModifiedBy`, `ModifiedDate`) VALUES
(1, 1, 'MRI-Tech', 'MRI - Technician', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20'),
(2, 1, 'CT-Tech', 'CT Technician', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20'),
(3, 1, 'CRE', 'Customer Relationship Executive', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20'),
(4, 1, 'CenterManager', 'Center Manager', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20');


--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyId` int(11) NOT NULL,
  `EmployeeCode` varchar(20) NOT NULL,
  `FristName` varchar(50) NOT NULL,
  `MiddleName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) NOT NULL,
  `RegionId` int(11) NOT NULL,
  `DesignationId` int(11) NOT NULL,
  `ProfilePhotoPath` varchar(100) NOT NULL DEFAULT 'noImage.jpg',
  `CreatedBy` int(11) NOT NULL,
  `CreatedDate` datetime NOT NULL,
  `ModifiedBy` int(11) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_Employee_EmployeeCode_U` (`EmployeeCode`),
  KEY `IX_Employee_CompanyId` (`CompanyId`),
  KEY `IX_Employee_Region_RegionId` (`RegionId`),
  KEY `IX_Employee_Designation_DesignationId` (`DesignationId`)
);


--
-- Table structure for table `medicalcenter`
--

DROP TABLE IF EXISTS `medicalcenter`;
CREATE TABLE IF NOT EXISTS `medicalcenter` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `RegionId` int(11) NOT NULL,
  `CenterCode` varchar(20) NOT NULL,
  `CenterName` varchar(100) NOT NULL,
  `CreatedBy` int(11) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedBy` int(11) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_MedicalCenter_CenterCode_U` (`CenterCode`) USING BTREE,
  KEY `IX_MedicalCenters_Regions` (`RegionId`)
);

--
-- Dumping data for table `medicalcenter`
--

INSERT INTO `medicalcenter` (`Id`, `RegionId`, `CenterCode`, `CenterName`, `CreatedBy`, `CreatedDate`, `ModifiedBy`, `ModifiedDate`) VALUES
(1, 1, 'MH-SainathHospital', 'Sainath Hospital', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20'),
(2, 1, 'MH-VikhePatil', 'Vikhe Patil Hospital', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20'),
(3, 2, 'KA-Bijapur', 'Bijapur Hospital', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20');


--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
CREATE TABLE IF NOT EXISTS `region` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `CompanyId` int(11) NOT NULL,
  `RegionCode` varchar(20) NOT NULL,
  `RegionName` varchar(100) NOT NULL,
  `CreatedBy` int(11) DEFAULT NULL,
  `CreatedDate` datetime DEFAULT NULL,
  `ModifiedBy` int(11) DEFAULT NULL,
  `ModifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `IX_Region_RegionCode_U` (`RegionCode`) USING BTREE,
  KEY `IX_Region_CompanyId` (`CompanyId`) USING BTREE
);

--
-- Dumping data for table `region`
--

INSERT INTO `region` (`Id`, `CompanyId`, `RegionCode`, `RegionName`, `CreatedBy`, `CreatedDate`, `ModifiedBy`, `ModifiedDate`) VALUES
(1, 1, 'MH', 'Maharashtra', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20'),
(2, 1, 'KA', 'Karnataka', 1, '2019-08-03 10:32:20', 1, '2019-08-03 10:32:20');




DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uuid` char(36) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `avatar_type` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'gravatar',
  `avatar_location` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password_changed_at` timestamp NULL DEFAULT NULL,
  `active` tinyint(3) UNSIGNED NOT NULL DEFAULT 1,
  `confirmation_code` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `confirmed` tinyint(1) NOT NULL DEFAULT 0,
  `timezone` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_login_at` timestamp NULL DEFAULT NULL,
  `last_login_ip` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `to_be_logged_out` tinyint(1) NOT NULL DEFAULT 0,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_email_unique` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `uuid`, `first_name`, `last_name`, `email`, `avatar_type`, `avatar_location`, `password`, `password_changed_at`, `active`, `confirmation_code`, `confirmed`, `timezone`, `last_login_at`, `last_login_ip`, `to_be_logged_out`, `remember_token`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 'd2844bb5-d0e1-4420-91ab-516513e0df1c', 'Kamlesh', 'Sancheti', 'kamlesh.sancheti77@gmail.com', 'gravatar', NULL, 'K@ml3$h77', '2019-08-03 03:58:07', 1, 'bab90a578c74262bfea143ce411d0037', 1, 'America/New_York', '2019-07-28 09:13:38', '::1', 0, 'imDildD0wVavhL8UoMKru2JqNp9u5t7F5eQV2WyM7BXUDw6T1lzrO2l5MhkL', '2019-07-28 07:03:45', '2019-08-03 03:58:07', NULL);



-- --------------------------------------------------------
--
-- Constraints for table `designation`
--
ALTER TABLE `designation`
  ADD CONSTRAINT `FK_Designation_Company_CompanyId` FOREIGN KEY (`CompanyId`) REFERENCES `company` (`Id`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FK_Employee_Company_CompanyId` FOREIGN KEY (`CompanyId`) REFERENCES `company` (`Id`),
  ADD CONSTRAINT `FK_Employee_Designation_DesignationId` FOREIGN KEY (`DesignationId`) REFERENCES `designation` (`Id`),
  ADD CONSTRAINT `FK_Employee_Region_RegionId` FOREIGN KEY (`RegionId`) REFERENCES `region` (`Id`);

--
-- Constraints for table `medicalcenter`
--
ALTER TABLE `medicalcenter`
  ADD CONSTRAINT `FK_MedicalCenter_RegionId` FOREIGN KEY (`RegionId`) REFERENCES `region` (`Id`);

--
-- Constraints for table `region`
--
ALTER TABLE `region`
  ADD CONSTRAINT `FK_Region_Company_CompanyId` FOREIGN KEY (`CompanyId`) REFERENCES `company` (`Id`);
COMMIT;