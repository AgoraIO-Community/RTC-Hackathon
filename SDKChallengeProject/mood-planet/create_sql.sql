/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.7.27 : Database - vediortc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vediortc` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `vediortc`;

/*Table structure for table `message_t` */

DROP TABLE IF EXISTS `message_t`;

CREATE TABLE `message_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_user_id` int(11) NOT NULL,
  `receiver_user_id` int(11) NOT NULL,
  `content` varchar(30) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4;

/*Data for the table `message_t` */

insert  into `message_t`(`id`,`sender_user_id`,`receiver_user_id`,`content`,`createtime`,`updatetime`) values 
(1,1,2,'123','2020-04-04 20:00:13','2020-04-04 20:00:13'),
(2,1,2,'1234','2020-04-04 20:30:58','2020-04-04 20:30:58'),
(3,2,1,'12345','2020-04-04 23:31:28','2020-04-04 23:31:28'),
(4,2,1,'12345','2020-04-04 23:33:00','2020-04-04 23:33:00'),
(5,2,1,'12345','2020-04-04 23:33:25','2020-04-04 23:33:25'),
(6,2,1,'12345','2020-04-04 23:34:48','2020-04-04 23:34:48'),
(7,1,2,'hello,xiaofang','2020-04-08 02:14:55','2020-04-08 02:14:55'),
(8,2,1,'hi,xiaoming','2020-04-08 02:15:15','2020-04-08 02:15:15'),
(9,1,2,'yeah','2020-04-08 02:21:08','2020-04-08 02:21:08'),
(10,2,1,'e','2020-04-08 02:22:08','2020-04-08 02:22:08'),
(11,1,2,'测试','2020-04-08 02:23:45','2020-04-08 02:23:45'),
(12,1,2,'test','2020-04-08 02:24:47','2020-04-08 02:24:47'),
(13,2,1,'testttttt','2020-04-08 02:26:05','2020-04-08 02:26:05'),
(14,2,1,'ttw','2020-04-08 02:27:23','2020-04-08 02:27:23'),
(15,2,1,'eeee','2020-04-08 02:31:25','2020-04-08 02:31:25'),
(16,1,2,'yyyyyy','2020-04-08 02:31:36','2020-04-08 02:31:36'),
(17,3,1,'我是robot<br>','2020-04-08 02:33:05','2020-04-08 02:33:05'),
(18,1,2,'ooo<div><br></div>','2020-04-08 02:33:28','2020-04-08 02:33:28'),
(19,1,2,'123','2020-04-08 02:34:17','2020-04-08 02:34:17'),
(20,1,2,'hhahaha','2020-04-08 02:34:28','2020-04-08 02:34:28'),
(21,2,1,'ahhah','2020-04-08 02:35:03','2020-04-08 02:35:03'),
(22,2,1,'<div>???<br></div>','2020-04-08 02:35:28','2020-04-08 02:35:28'),
(23,1,2,'??//','2020-04-08 02:35:59','2020-04-08 02:35:59'),
(80,2,1,'465','2020-04-15 03:12:12','2020-04-15 03:12:12'),
(81,1,2,'65487','2020-04-15 03:12:28','2020-04-15 03:12:28'),
(82,2,1,'46579','2020-04-15 03:12:39','2020-04-15 03:12:39'),
(83,2,1,'654897765465','2020-04-15 03:13:06','2020-04-15 03:13:06'),
(84,2,1,'hi,ming.from fang','2020-04-15 21:53:39','2020-04-15 21:53:39'),
(85,1,2,'sqy hemm','2020-04-18 18:35:57','2020-04-18 18:35:57'),
(86,2,1,'enen','2020-04-18 18:36:15','2020-04-18 18:36:15'),
(87,2,1,'xiaofang','2020-04-19 16:24:23','2020-04-19 16:24:23'),
(88,1,2,'eee','2020-04-19 16:24:38','2020-04-19 16:24:38');

/*Table structure for table `role_t` */

DROP TABLE IF EXISTS `role_t`;

CREATE TABLE `role_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `rolename` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `role_t` */

/*Table structure for table `user_info_t` */

DROP TABLE IF EXISTS `user_info_t`;

CREATE TABLE `user_info_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `img` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_info_t` */

insert  into `user_info_t`(`id`,`user_id`,`phone`,`email`,`sex`,`img`) values 
(1,1,'13800000001','123@qq.com','男','8820f327-16bf-4a88-b262-1bd229479890.jpg'),
(2,2,'165498321','uid@qq.com','男','02349fdf-c298-4e7c-8917-6b9efaa21184.jpg');

/*Table structure for table `user_match_t` */

DROP TABLE IF EXISTS `user_match_t`;

CREATE TABLE `user_match_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user1_id` int(11) NOT NULL,
  `user2_id` int(11) NOT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_match_t` */

insert  into `user_match_t`(`id`,`user1_id`,`user2_id`,`createtime`,`updatetime`) values 
(1,2,1,'2020-04-02 19:38:11','2020-04-02 19:38:11'),
(2,1,3,'2020-04-03 02:21:53','2020-04-03 02:21:53');

/*Table structure for table `user_mood_t` */

DROP TABLE IF EXISTS `user_mood_t`;

CREATE TABLE `user_mood_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `mood_type` int(5) DEFAULT NULL COMMENT '0,文字；1，声音；2，图片',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_mood_t` */

insert  into `user_mood_t`(`id`,`user_id`,`mood_type`,`createtime`,`updatetime`) values 
(1,1,0,'2020-04-01 14:07:11','2020-04-01 14:07:11'),
(2,1,0,'2020-04-01 16:09:20','2020-04-01 16:09:20'),
(3,1,0,'2020-04-01 16:10:09','2020-04-01 16:10:09'),
(4,1,0,'2020-04-01 22:32:09','2020-04-01 22:32:09'),
(5,1,0,'2020-04-02 01:16:50','2020-04-02 01:16:50'),
(6,2,0,'2020-04-02 17:20:21','2020-04-02 17:20:21'),
(7,3,0,'2020-04-03 02:12:09','2020-04-03 02:12:09'),
(8,1,0,'2020-04-14 03:27:53','2020-04-14 03:27:53'),
(9,1,0,'2020-04-19 16:23:13','2020-04-19 16:23:13');

/*Table structure for table `user_mood_word_t` */

DROP TABLE IF EXISTS `user_mood_word_t`;

CREATE TABLE `user_mood_word_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_mood_id` int(11) NOT NULL,
  `content` varchar(50) DEFAULT NULL,
  `img` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`user_id`,`user_mood_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_mood_word_t` */

insert  into `user_mood_word_t`(`id`,`user_id`,`user_mood_id`,`content`,`img`,`createtime`,`updatetime`) values 
(1,1,3,'abc',NULL,'2020-04-01 16:10:09','2020-04-01 16:10:09'),
(2,1,4,'abc',NULL,'2020-04-01 22:32:09','2020-04-01 22:32:09'),
(3,1,5,'abcdef',NULL,'2020-04-02 01:16:50','2020-04-02 01:16:50'),
(4,2,6,'我是小芳',NULL,'2020-04-02 17:20:21','2020-04-02 17:20:21'),
(5,3,7,'我是robot1',NULL,'2020-04-03 02:12:09','2020-04-03 02:12:09'),
(6,1,8,'图片test','729fc043-434e-4c5f-957c-6f6e8217338d.jpg','2020-04-14 03:27:53','2020-04-14 03:27:53'),
(7,1,9,'hahahhah','5f6332d7-7992-4762-b231-3bf2d19de66f.jpg','2020-04-19 16:23:13','2020-04-19 16:23:13');

/*Table structure for table `user_t` */

DROP TABLE IF EXISTS `user_t`;

CREATE TABLE `user_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_t` */

insert  into `user_t`(`id`,`username`,`password`) values 
(1,'xiaoming','123'),
(2,'xiaofang','123'),
(3,'robot1','123');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
