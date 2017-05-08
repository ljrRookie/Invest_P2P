/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.5.28 : Database - atguigu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`atguigu` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `atguigu`;

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

insert  into `customer`(`id`,`name`,`salary`) values (1,'方志',200);

/*Table structure for table `feedback_table` */

DROP TABLE IF EXISTS `feedback_table`;

CREATE TABLE `feedback_table` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `department` varchar(30) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `feedback_table` */

insert  into `feedback_table`(`id`,`department`,`content`) values (7,'理财部','sfafd'),(8,'咨询部','dsf'),(9,'咨询部','hello'),(10,'技术部','fasfe'),(12,'理财部','啊理我'),(13,'咨询部','不知道你们部门美女多不多');

/*Table structure for table `user_table` */

DROP TABLE IF EXISTS `user_table`;

CREATE TABLE `user_table` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `password` varchar(40) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `imageurl` varchar(60) DEFAULT 'http://192.168.191.1:8080/P2PInvest/images/tx.png',
  `iscredit` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `user_table` */

insert  into `user_table`(`id`,`name`,`password`,`phone`,`imageurl`,`iscredit`) values (1,'李雷','fa7d5dcd109a27b6fa057412ef454626','13212341234','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(2,'shkstart','e10adc3949ba59abbe56e057f20f883e','13012341234','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(3,'HanMeimei','2cf07917d46c0d312f36d9d21237baa2','13212341234','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(4,'Tom','b1e6d1bd047f43af0ab59556c394a376','13712344321','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(5,'范冰冰','03e3ea991be309de78456bf0f017fa84','13712367898','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(7,'2432','81dc9bdb52d04dc20036dbd8313ed055','13022332245','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(8,'songhk','827ccb0eea8a706c4c34a16891f84e7b','13033332222','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(9,'cody','e10adc3949ba59abbe56e057f20f883e','18611119374','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(10,'Lilei','e10adc3949ba59abbe56e057f20f883e','13545667864','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(19,'阿狸','b2ca678b4c936f905fb82f2733f5297f','14477','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(20,'qq123','111','13011112222','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(21,'悟空','698d51a19d8a121ce581499d7b701668','13811112222','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(22,'song nanshen','e10adc3949ba59abbe56e057f20f883e','13012345678','http://192.168.191.1:8080/P2PInvest/images/tx.png',1),(23,'æç©º1','698d51a19d8a121ce581499d7b701668','13811112223','http://192.168.191.1:8080/P2PInvest/images/tx.png',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
