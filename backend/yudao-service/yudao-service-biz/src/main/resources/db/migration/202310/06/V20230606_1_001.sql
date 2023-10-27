SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

--
-- Table structure for table `infra_api_access_log`
--

DROP TABLE IF EXISTS `infra_api_access_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_api_access_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT '0' COMMENT '用户类型',
  `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求地址',
  `request_params` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求参数',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `begin_time` datetime NOT NULL COMMENT '开始请求时间',
  `end_time` datetime NOT NULL COMMENT '结束请求时间',
  `duration` int NOT NULL COMMENT '执行时长',
  `result_code` int NOT NULL DEFAULT '0' COMMENT '结果码',
  `result_msg` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '结果提示',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='API 访问日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_api_access_log`
--

LOCK TABLES `infra_api_access_log` WRITE;
/*!40000 ALTER TABLE `infra_api_access_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `infra_api_access_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_api_error_log`
--

DROP TABLE IF EXISTS `infra_api_error_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_api_error_log` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `trace_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '链路追踪编号\n     *\n     * 一般来说，通过链路追踪编号，可以将访问日志，错误日志，链路追踪日志，logger 打印日志等，结合在一起，从而进行排错。',
  `user_id` int NOT NULL DEFAULT '0' COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT '0' COMMENT '用户类型',
  `application_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名\n     *\n     * 目前读取 spring.application.name',
  `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求方法名',
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求地址',
  `request_params` varchar(8000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求参数',
  `user_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `exception_time` datetime NOT NULL COMMENT '异常发生时间',
  `exception_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常名\n     *\n     * {@link Throwable#getClass()} 的类全名',
  `exception_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '异常导致的消息\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getMessage(Throwable)}',
  `exception_root_cause_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '异常导致的根消息\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getRootCauseMessage(Throwable)}',
  `exception_stack_trace` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '异常的栈轨迹\n     *\n     * {@link cn.iocoder.common.framework.util.ExceptionUtil#getServiceException(Exception)}',
  `exception_class_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常发生的类全名\n     *\n     * {@link StackTraceElement#getClassName()}',
  `exception_file_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常发生的类文件\n     *\n     * {@link StackTraceElement#getFileName()}',
  `exception_method_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常发生的方法名\n     *\n     * {@link StackTraceElement#getMethodName()}',
  `exception_line_number` int NOT NULL DEFAULT '0' COMMENT '异常发生的方法所在行\n     *\n     * {@link StackTraceElement#getLineNumber()}',
  `process_status` tinyint NOT NULL DEFAULT '0' COMMENT '处理状态',
  `process_time` datetime DEFAULT NULL COMMENT '处理时间',
  `process_user_id` int NOT NULL DEFAULT '0' COMMENT '处理用户编号',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统异常日志';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `infra_config`
--

DROP TABLE IF EXISTS `infra_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数分组',
  `type` tinyint NOT NULL COMMENT '参数类型',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键名',
  `value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键值',
  `visible` bit(1) NOT NULL COMMENT '是否可见',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_config`
--

LOCK TABLES `infra_config` WRITE;
/*!40000 ALTER TABLE `infra_config` DISABLE KEYS */;
INSERT INTO `infra_config` VALUES 
(2,'biz',1,'用户管理-账号初始密码','sys.user.init-password','123456',_binary '\0','初始化密码 123456',1,'2021-01-05 17:03:48',1,'2022-03-20 02:25:51',_binary '\0'),
(7,'url',2,'MySQL 监控的地址','url.druid','',_binary '','',1,'2023-04-07 13:41:16',1,'2023-04-07 14:33:38',_binary '\0'),
(8,'url',2,'SkyWalking 监控的地址','url.skywalking','',_binary '','',1,'2023-04-07 13:41:16',1,'2023-04-07 14:57:03',_binary '\0'),
(9,'url',2,'Spring Boot Admin 监控的地址','url.spring-boot-admin','',_binary '','',1,'2023-04-07 13:41:16',1,'2023-04-07 14:52:07',_binary '\0'),
(10,'url',2,'Swagger 接口文档的地址','url.swagger','',_binary '','',1,'2023-04-07 13:41:16',1,'2023-04-07 14:59:00',_binary '\0');
/*!40000 ALTER TABLE `infra_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_data_source_config`
--

DROP TABLE IF EXISTS `infra_data_source_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_data_source_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源连接',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据源配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_data_source_config`
--

LOCK TABLES `infra_data_source_config` WRITE;
/*!40000 ALTER TABLE `infra_data_source_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `infra_data_source_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_database_column`
--

DROP TABLE IF EXISTS `infra_database_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_database_column` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `table_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表编号',
  `column_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字段名',
  `data_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字段类型',
  `column_comment` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字段描述',
  `nullable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否允许为空',
  `default_value` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '默认值',
  `java_type` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Java 属性类型',
  `dict_type` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `example` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据示例',
  `required` bit(1) NOT NULL COMMENT '前端必传',
  `related_table` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联表',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNI_infra_database_column_name` (`column_name`,`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码生成表字段定义';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_database_column`
--

LOCK TABLES `infra_database_column` WRITE;
/*!40000 ALTER TABLE `infra_database_column` DISABLE KEYS */;
INSERT INTO `infra_database_column` VALUES 
('01059639-b9d6-4641-bf43-6466c39f5232','16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('012aea52-f2ca-453a-a025-88dae8754198','4e4cc482-af80-4ebb-a8b7-4855f77b0b89','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('018dd57d-0f8d-4b65-9b36-d7df6020f1e3','376e9077-6e6d-47d3-b75d-b7aefdddccaa','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('019bbe65-beeb-457c-b3d1-9ff15b5133fd','9127fb54-c0da-4077-99ac-21945d84cc78','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('01b77e62-382a-409f-a9ba-7fe0f79c58e0','c336cb46-f45a-4f10-91fb-88003fd5dac6','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('01f577d8-8392-43cf-ba4d-023fd523ad6e','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('02e99be3-3244-44c2-a4ad-68dbfc5e2ef2','e1a06dd2-bba2-49f8-ae05-8c172f216d61','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('031d57cc-2e1e-4fd9-87ef-8d0004e98817','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','parent_type','TINYINT(4)','父表类型0 infra_database_column 1 infra_interface_param',_binary '\0','0','Integer','','',_binary '\0','',2,1,'2023-08-09 17:13:10',1,'2023-08-09 17:13:10',_binary '\0'),
('032003af-94a1-46d0-afe4-b4425d372081','21b4e945-9bf5-4718-829c-059259da899c','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('041f882a-a941-4877-a0f3-cb16fd87edf7','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','template_content','VARCHAR(1024)','模版内容',_binary '\0','\'\'','String','','测试内容',_binary '','',6,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('0762ac78-6dd4-40b9-b6db-898d8f5120d9','4e4cc482-af80-4ebb-a8b7-4855f77b0b89','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('083ac816-3f7b-42cd-acb4-a762d7d38cae','0526ebf1-9d5a-4226-a47b-c4384acff4eb','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-18 08:52:03',1,'2023-08-18 08:52:03',_binary '\0'),
('08a77119-3873-4c10-98b4-aa1da6476a59','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','input_type','VARCHAR(50)','入参类型',_binary '\0','\'\'','String','','void',_binary '\0','',8,1,'2023-08-14 09:32:55',1,'2023-08-14 09:32:55',_binary '\0'),
('094d0f27-e3aa-48b9-a233-6a4dc0449c6f','8e9c5164-801f-47e3-99d6-c28dd803a9e5','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('09f0181c-b888-4491-9b01-6a0d9c743b6c','62ca5e40-4a79-49bb-af2b-977e7205fb4e','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('0a748610-7c6a-41ed-bf97-8a8d3c08e283','bc3f458b-d80d-4526-abcf-07d7277cccb3','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('0b642447-7a53-4539-82c0-4eac4af503ef','bc3f458b-d80d-4526-abcf-07d7277cccb3','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('0e08e19a-eb2a-47ec-82fd-5aabc3a2917b','376e9077-6e6d-47d3-b75d-b7aefdddccaa','default_value','VARCHAR(100)','默认值',_binary '\0','\'\'','String','','年龄',_binary '','',6,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('10409cd7-7c5d-4b5d-afb7-0b34650cf4a5','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','read_time','DATETIME','阅读时间',_binary '','NULL','LocalDateTime','','',_binary '\0','',10,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('124c4cc6-1f0a-4da7-b274-432b3f19c8e8','209dd887-1db4-4260-9f9a-10efa33b439f','color_type','VARCHAR(100)','颜色类型',_binary '\0','\'\'','String','','',_binary '\0','',6,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('12c05fbf-524d-4e1c-b08e-991f5333f7a9','4e4cc482-af80-4ebb-a8b7-4855f77b0b89','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('13b5c858-1e89-4c71-ac69-7de31a1a863b','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('143b9a7a-ce85-4243-b933-d2adc66b128c','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','name','VARCHAR(100)','接口名',_binary '\0','\'\'','String','','get',_binary '\0','',1,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('15452aa4-743a-4778-b53b-7f7ebcb0b69e','9127fb54-c0da-4077-99ac-21945d84cc78','code','VARCHAR(64)','模版编码',_binary '\0','\'\'','String','','SEND_TEST',_binary '','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('157cf37f-2a9f-4828-8d1a-080c20ea125c','0526ebf1-9d5a-4226-a47b-c4384acff4eb','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-08-18 08:52:03',1,'2023-08-18 08:52:03',_binary '\0'),
('167d5f70-7cdb-425a-8e2d-df3b70057fe1','0a9647f1-dbe7-48c2-b332-624a05a83df0','name','VARCHAR(100)','表名称',_binary '\0','\'\'','String','','infra_database',_binary '','',1,1,'2023-08-09 16:16:07',1,'2023-08-09 16:19:20',_binary '\0'),
('17b2566d-1b72-4c81-a20a-f188c5aa5ef8','285770c9-d338-4913-95bb-7c50fe968d12','comment','VARCHAR(255)','描述',_binary '\0','\'\'','String','','字段名',_binary '\0','',2,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('18a0b8eb-a761-4ddb-a702-c6a893601afa','8e9c5164-801f-47e3-99d6-c28dd803a9e5','remark','VARCHAR(500)','备注',_binary '\0','\'\'','String','','',_binary '\0','',8,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('18b32d45-6d37-4229-b4ae-dc42279f77a1','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','validation','VARCHAR(50)','校验注解',_binary '\0','\'\'','String','','NotBlank',_binary '','',3,1,'2023-08-09 17:13:10',1,'2023-08-09 17:25:58',_binary '\0'),
('195a2e4d-7139-4b57-861c-b533b40a78a6','285770c9-d338-4913-95bb-7c50fe968d12','required','BIT(1)','前端必传',_binary '\0','0','Boolean','','1',_binary '\0','',8,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('1a1e6cd7-7312-4d73-8635-682cca442a74','e1a06dd2-bba2-49f8-ae05-8c172f216d61','login_ip','VARCHAR(50)','最后登录IP',_binary '\0','\'\'','String','','232.342.343.23',_binary '\0','',12,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('1a2c936a-83d3-4ed0-a441-326f524af2fe','62ca5e40-4a79-49bb-af2b-977e7205fb4e','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('1b0d5584-520a-456d-b5ca-dcaa2565bafa','0a9647f1-dbe7-48c2-b332-624a05a83df0','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-08-09 16:16:07',1,'2023-08-09 16:16:07',_binary '\0'),
('1b207d45-76ec-41f2-814f-7be4c5ebec5a','209dd887-1db4-4260-9f9a-10efa33b439f','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('1d61e3e9-5261-49bc-93e5-a6743bfdb538','376e9077-6e6d-47d3-b75d-b7aefdddccaa','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('1e48c12c-768c-4274-954a-299abb03e9e8','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('20d7afb4-029c-44ac-9a71-7945b62f6346','9127fb54-c0da-4077-99ac-21945d84cc78','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('20d9067d-a1ec-4232-8da9-9b6e442f7474','c336cb46-f45a-4f10-91fb-88003fd5dac6','leader_user_id','BIGINT','负责人',_binary '\0','0','Long','','1',_binary '\0','system_user',4,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('215b8620-e294-4c65-8db8-173714492088','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-08-09 17:13:10',1,'2023-08-09 17:16:19',_binary '\0'),
('2178990a-748d-4990-992f-143bfb7bd55c','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','content','TEXT','公告内容',_binary '\0','','String','','半生编码',_binary '','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('21cb8b1d-ab82-4786-9fd7-a90c66355e18','4e4cc482-af80-4ebb-a8b7-4855f77b0b89','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('238680fe-6c06-4678-8fe6-815ba9c91f11','0526ebf1-9d5a-4226-a47b-c4384acff4eb','column_names','VARCHAR(1024)','字段',_binary '\0','\'\'','List<String>','','name,type',_binary '','',4,1,'2023-08-18 08:52:03',1,'2023-08-18 08:56:31',_binary '\0'),
('23e598de-816f-4244-a07e-806a2f6fc73d','c336cb46-f45a-4f10-91fb-88003fd5dac6','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('24f4ea0f-8e90-4729-872d-abba4ca26b84','4e4cc482-af80-4ebb-a8b7-4855f77b0b89','menu_id','BIGINT','菜单ID',_binary '\0','0','Long','','',_binary '\0','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2736ba11-b7f3-4199-865a-79431d32ec91','376e9077-6e6d-47d3-b75d-b7aefdddccaa','column_comment','VARCHAR(500)','字段描述',_binary '\0','\'\'','String','','年龄',_binary '','',4,1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0'),
('27b7f4d7-8594-4b4a-b0c2-d4cda3141a43','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','name','VARCHAR(100)','模块名称',_binary '\0','\'\'','String','','user',_binary '\0','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2907aef1-396c-4de5-9aa5-41dc9010a9eb','16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2a177131-1368-42c4-a8a5-8956639b456e','e1a06dd2-bba2-49f8-ae05-8c172f216d61','email','VARCHAR(50)','用户邮箱',_binary '\0','\'\'','String','','yudao@iocoder.cn',_binary '\0','',7,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2a17b852-8f1b-4fe8-9931-62d292a31c66','e1a06dd2-bba2-49f8-ae05-8c172f216d61','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2a43f523-f8f1-41bb-af01-6c7b74e6b2f5','0526ebf1-9d5a-4226-a47b-c4384acff4eb','table_id','VARCHAR(50)','表编号',_binary '\0','\'\'','UUID','','3242323423',_binary '','infra_database_table',1,1,'2023-08-18 08:52:03',1,'2023-08-18 08:56:31',_binary '\0'),
('2b61a90c-ca41-4b6c-8409-7c72fb6bd284','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2c43e99e-29e0-4da6-8aa8-b45c230ee66c','bc3f458b-d80d-4526-abcf-07d7277cccb3','name','VARCHAR(100)','类名',_binary '\0','\'\'','String','','baseVO',_binary '\0','',1,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('2cd6d5fa-c491-4c94-b4f1-813b0c8dd744','62ca5e40-4a79-49bb-af2b-977e7205fb4e','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2d865a84-3e87-4bcf-b887-c3d979269bb6','8e9c5164-801f-47e3-99d6-c28dd803a9e5','name','VARCHAR(30)','角色名称',_binary '\0','\'\'','String','','',_binary '\0','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2e47f6da-9d90-4c13-98e3-16a00b5f86c6','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','is_transaction','BIT(1)','是否开启事务',_binary '\0','0','Boolean','','',_binary '\0','',6,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('2e549c78-99f6-442e-b4bb-3f41b87cc591','376e9077-6e6d-47d3-b75d-b7aefdddccaa','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0'),
('2f3ea1d7-02f0-4ec9-92c7-f1fc2de8c1d4','e1a06dd2-bba2-49f8-ae05-8c172f216d61','sex','TINYINT','用户性别',_binary '\0','0','Integer','','1',_binary '\0','',9,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('2f921882-be0c-4dd0-b0ee-8f6f2c707c31','0a9647f1-dbe7-48c2-b332-624a05a83df0','remark','VARCHAR(500)','备注',_binary '\0','\'\'','String','','备注',_binary '\0','',3,1,'2023-08-09 16:16:07',1,'2023-08-09 16:16:07',_binary '\0'),
('2fa965c9-9b3b-4ee4-90fb-9f1a84750b75','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','status','TINYINT','公告状态（0正常 1关闭）',_binary '\0','0','Integer','','0',_binary '','',4,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('31124c1d-3419-4301-879a-aa53a41ad20c','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-09-20 10:06:36',1,'2023-09-20 10:06:36',_binary '\0'),
('312bf5a2-ecf8-4f01-b11c-d10e88438484','376e9077-6e6d-47d3-b75d-b7aefdddccaa','sort','INT','排序',_binary '\0','0','Integer','','1',_binary '','',12,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('317fedbf-2a88-4133-a64d-191915bba1ba','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('31a2390e-c16b-44e2-8863-188a547d482d','62ca5e40-4a79-49bb-af2b-977e7205fb4e','remark','VARCHAR(500)','备注',_binary '','NULL','String','','快乐的备注',_binary '\0','',5,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('349bac4f-c580-4cce-84b2-407069f5c930','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','type','VARCHAR(100)','字典类型',_binary '\0','\'\'','String','','system_user_sex',_binary '','',2,1,'2023-09-20 10:06:36',1,'2023-09-20 10:06:36',_binary '\0'),
('35cff5a0-1546-4398-8d47-15710ee6cc64','0a9647f1-dbe7-48c2-b332-624a05a83df0','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-09 16:16:07',1,'2023-08-09 16:16:07',_binary '\0'),
('35f154df-32ef-4603-b936-d452e7a45ce4','e1a06dd2-bba2-49f8-ae05-8c172f216d61','remark','VARCHAR(500)','备注',_binary '','NULL','String','','我是一个用户',_binary '\0','',4,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('361f9270-95ad-475b-912f-45f938d8e465','376e9077-6e6d-47d3-b75d-b7aefdddccaa','nullable','BIT(1)','是否允许为空',_binary '\0','0','Boolean','','true',_binary '','',5,1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0'),
('3644ebd6-bd33-49b1-b4b3-18ad6f193d6d','209dd887-1db4-4260-9f9a-10efa33b439f','type_id','VARCHAR(50)','字典类型',_binary '\0','\'\'','UUID','','c0fcc1e7-02a0-635b-0702-2c96f6482dae',_binary '\0','infra_dict_type',4,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('3666eec6-11b1-4249-b7a3-6aac00c7eb6d','21b4e945-9bf5-4718-829c-059259da899c','user_id','BIGINT','用户ID',_binary '\0','0','Long','','0',_binary '\0','system_user',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('37004228-b6b2-4301-ae7f-6e38e62991a8','4e4cc482-af80-4ebb-a8b7-4855f77b0b89','role_id','BIGINT','角色ID',_binary '\0','0','Long','','',_binary '\0','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('37539afc-f098-4c2e-902f-778405a16bf2','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','comment','VARCHAR(200)','描述',_binary '\0','\'\'','String','','获取',_binary '\0','',2,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('382c4170-7179-46a6-9492-b8b9f267d06e','8e9c5164-801f-47e3-99d6-c28dd803a9e5','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('384b0bea-4cd8-4f76-80c2-10be8eacc9b1','9127fb54-c0da-4077-99ac-21945d84cc78','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('3b45ad03-bffe-4566-a789-f9ba462010ed','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','message','VARCHAR(1024)','报错信息',_binary '\0','\'\'','String','','用户账号由 数字、字母 组成',_binary '','',5,1,'2023-08-09 17:13:10',1,'2023-08-09 17:25:58',_binary '\0'),
('3c0d7404-ffb4-402b-9c91-f187c15cff49','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','user_id','BIGINT','用户id',_binary '\0','0','Long','','25025',_binary '','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('3cfcef38-7507-43dd-8272-88dc27f48101','376e9077-6e6d-47d3-b75d-b7aefdddccaa','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('3dbb0927-9b08-4bac-baeb-32fcf9e15649','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-09 17:13:11',1,'2023-08-09 17:13:11',_binary '\0'),
('3dc099a6-59bb-4cd7-8271-3c02e3fad9df','0526ebf1-9d5a-4226-a47b-c4384acff4eb','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-18 08:52:03',1,'2023-08-18 08:52:03',_binary '\0'),
('3f66fe3f-6b82-4599-92fe-8688961a6a2c','e1a06dd2-bba2-49f8-ae05-8c172f216d61','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('4143a1d4-ad6f-4759-8e9b-a48ae50bf90c','285770c9-d338-4913-95bb-7c50fe968d12','parent_id','VARCHAR(50)','父id',_binary '\0','\'\'','UUID','','\'\'',_binary '\0','',10,1,'2023-08-09 15:02:46',1,'2023-08-09 15:02:46',_binary '\0'),
('426f241e-26ed-465f-a4a2-96955950d1e8','209dd887-1db4-4260-9f9a-10efa33b439f','css_class','VARCHAR(100)','css 样式',_binary '\0','\'\'','String','','',_binary '\0','',7,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('4353728c-7080-4a46-b01d-83f09d4d1ca5','c336cb46-f45a-4f10-91fb-88003fd5dac6','status','TINYINT','部门状态（0正常 1停用）',_binary '\0','0','Integer','','',_binary '','',7,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('43cb7d9f-bd53-4c7b-9aef-41367f27c8d8','8e9c5164-801f-47e3-99d6-c28dd803a9e5','code','VARCHAR(100)','角色权限字符串',_binary '\0','\'\'','String','','',_binary '\0','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('44a9058a-83a3-4d69-8a71-0718f33a4891','285770c9-d338-4913-95bb-7c50fe968d12','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-08-09 09:22:34',1,'2023-08-09 15:02:46',_binary '\0'),
('467d731a-05fd-4679-8d05-2a476b606fd1','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('476b6165-eea6-440f-a6c6-04703324f9a4','cac76521-8eec-4d07-87f8-df26fe2aed4e','inherit_class','VARCHAR(125)','继承类',_binary '\0','\'\'','String','','baseVO',_binary '\0','',4,1,'2023-08-19 12:05:23',1,'2023-08-19 12:05:23',_binary '\0'),
('47bebd1c-1b0f-4f02-a9cc-0a0ad27115cd','c336cb46-f45a-4f10-91fb-88003fd5dac6','name','VARCHAR(30)','部门名称',_binary '\0','\'\'','String','','芋道',_binary '','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('490abd70-d214-4da0-8e68-db67d5d77f41','62ca5e40-4a79-49bb-af2b-977e7205fb4e','sort','INT','显示顺序',_binary '\0','0','Integer','','1024',_binary '','',3,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('4a72a612-184d-4cee-90c1-a8a547134249','8e9c5164-801f-47e3-99d6-c28dd803a9e5','type','TINYINT','角色类型',_binary '\0','NULL','Integer','','',_binary '\0','',7,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('4cd58a40-e443-4606-91eb-0a0ca3d6bfcd','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','name','VARCHAR(100)','字典名称',_binary '\0','\'\'','String','','用户性别',_binary '','',1,1,'2023-09-20 10:06:35',1,'2023-09-20 10:06:35',_binary '\0'),
('4d22d96d-b9c5-4b99-a360-107b21c9c07e','ba35bc1c-c14c-44af-9506-f5f57cc79fae','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-16 11:53:49',1,'2023-08-16 11:53:49',_binary '\0'),
('4dd38f6e-e628-4324-9871-819884f04e84','0526ebf1-9d5a-4226-a47b-c4384acff4eb','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-18 08:52:03',1,'2023-08-18 08:52:03',_binary '\0'),
('4f997f0a-59c7-4255-b117-5d6ed52d2597','bc3f458b-d80d-4526-abcf-07d7277cccb3','type','TINYINT(4)','0 基类 1 入参类 2 出参类 3 入参子类 4 出参子类',_binary '\0','0','Integer','','0',_binary '\0','',3,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('4fb3cc37-c71f-4914-9d7e-c972fd2b93ba','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('4fd241a3-66e9-4333-8cfe-52ade9adad45','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('5020b4c0-c9e4-4b0f-b4fd-ba3e9fe03d14','9127fb54-c0da-4077-99ac-21945d84cc78','status','TINYINT','状态',_binary '\0','0','Integer','','',_binary '','',7,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('513d62d9-1b95-446d-8cb4-f10910d6dd4d','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','status','TINYINT','状态（0正常 1停用）',_binary '\0','0','Integer','','0',_binary '\0','',3,1,'2023-09-20 10:06:36',1,'2023-09-20 10:06:36',_binary '\0'),
('525e3d6a-2ec6-4896-ac2d-5b8b615cdd14','9127fb54-c0da-4077-99ac-21945d84cc78','params','VARCHAR(255)','参数数组',_binary '\0','\'\'','List<String>','','',_binary '\0','',6,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('52feec4d-891f-4e19-a435-087db2644325','ba35bc1c-c14c-44af-9506-f5f57cc79fae','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-16 11:53:49',1,'2023-08-16 11:53:49',_binary '\0'),
('53c4edab-f4cc-4801-bdd9-d479612a8bab','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','parent_id','VARCHAR(50)','父ID',_binary '\0','\'\'','String','','3242',_binary '\0','',3,1,'2023-07-24 08:44:59',1,'2023-07-24 18:27:50',_binary '\0'),
('5503401c-4c64-41c0-a5e5-40f13bd72d2d','9127fb54-c0da-4077-99ac-21945d84cc78','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('55504728-ac74-45a5-8361-df6ed669b34d','376e9077-6e6d-47d3-b75d-b7aefdddccaa','table_id','VARCHAR(50)','表编号',_binary '\0','\'\'','UUID','','2342533242',_binary '','infra_database_table',1,1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0'),
('561e4540-64a9-4cb7-8ca3-7406f8e65e45','285770c9-d338-4913-95bb-7c50fe968d12','parent_type','TINYINT(4)','父类类型 0 接口 1 子类',_binary '\0','0','Integer','','0',_binary '\0','',11,1,'2023-08-09 15:02:46',1,'2023-08-09 15:02:46',_binary '\0'),
('5645a3e6-a52c-48a6-a1e2-17e5eb7d515e','cac76521-8eec-4d07-87f8-df26fe2aed4e','inherit_type','TINYINT(4)','继承类型2 VO类 3 子类',_binary '\0','0','Integer','','0',_binary '\0','',5,1,'2023-08-19 12:05:23',1,'2023-08-19 12:05:23',_binary '\0'),
('571d0f97-ceb0-4ff8-a74a-39273b81fe5a','cac76521-8eec-4d07-87f8-df26fe2aed4e','type','TINYINT(4)','子类类型 0 入参 1 出参',_binary '\0','0','Integer','','0',_binary '\0','',6,1,'2023-08-19 12:05:23',1,'2023-08-19 12:05:23',_binary '\0'),
('580c8767-f137-4420-bcfa-4eeb75ef8321','4e4cc482-af80-4ebb-a8b7-4855f77b0b89','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('5aa4aef1-f27a-4be4-b163-b95a742a15a7','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','method','VARCHAR(50)','调用方法',_binary '\0','\'\'','String','','GET',_binary '\0','',3,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('5c193f26-b2df-4bf1-871c-1d2958faa99f','ba35bc1c-c14c-44af-9506-f5f57cc79fae','name','VARCHAR(125)','映射名',_binary '\0','\'\'','String','','validations',_binary '\0','',2,1,'2023-08-16 11:53:48',1,'2023-08-16 11:53:48',_binary '\0'),
('5d116e67-420e-44c5-aabe-0298056f9f03','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','user_type','TINYINT','用户类型',_binary '\0','0','Integer','','1',_binary '','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('5d1bacea-4f36-49a3-9434-9617b921b927','8e9c5164-801f-47e3-99d6-c28dd803a9e5','data_scope','TINYINT','数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',_binary '\0','1','Integer','','',_binary '\0','',4,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('5f68d2d2-58f6-402a-bac2-970109806bbb','0526ebf1-9d5a-4226-a47b-c4384acff4eb','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-18 08:52:03',1,'2023-08-18 08:52:03',_binary '\0'),
('61cc90d7-34eb-4d8f-85be-47a3c6fb7740','cac76521-8eec-4d07-87f8-df26fe2aed4e','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-19 12:05:24',1,'2023-08-19 12:05:24',_binary '\0'),
('628253b4-ad68-49fd-8cb3-d0cb1c96a1b7','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-09-20 10:06:36',1,'2023-09-20 10:06:36',_binary '\0'),
('62ce234c-7ec7-4840-979e-8b8aa6c3a20f','16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('62ef96d2-b312-4791-b3a4-948369806206','ba35bc1c-c14c-44af-9506-f5f57cc79fae','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-16 11:53:49',1,'2023-08-16 11:53:49',_binary '\0'),
('63c19fc9-1b79-43e6-b9e1-93ab2ea464c6','e1a06dd2-bba2-49f8-ae05-8c172f216d61','password','VARCHAR(100)','密码',_binary '\0','\'\'','String','','2132',_binary '\0','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('63c4f3a8-0ebc-4a2c-939b-8d7a4d7dce1a','209dd887-1db4-4260-9f9a-10efa33b439f','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('64583fa3-f6ae-4e61-b4ee-9e6fe3f9f7f2','cac76521-8eec-4d07-87f8-df26fe2aed4e','parent_id','VARCHAR(50)','接口ID',_binary '\0','\'\'','UUID','','362331',_binary '','infra_interface',1,1,'2023-08-19 12:05:23',1,'2023-08-20 13:01:12',_binary '\0'),
('6469a95c-305d-4ee6-9664-4447329a0f84','285770c9-d338-4913-95bb-7c50fe968d12','example','VARCHAR(1024)','示例',_binary '\0','\'\'','String','','示例',_binary '\0','',7,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('654eae2e-b6db-43ad-9830-dcd1d832e401','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('6583e137-b7bb-4c6a-ba9c-e0023bb0e76b','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-09 17:13:11',1,'2023-08-09 17:13:11',_binary '\0'),
('664b8578-38a1-47cd-b828-49e5efb7d1af','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','template_id','BIGINT','模版编号',_binary '\0','0','Long','','13013',_binary '','',3,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('66c00f1b-4972-4d1b-bb7a-2e4ac70eb0f8','16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('6704b5dd-c95d-45e9-984d-34682aee064e','ba35bc1c-c14c-44af-9506-f5f57cc79fae','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-08-16 11:53:48',1,'2023-08-16 11:53:48',_binary '\0'),
('6ae862ca-e94c-46ec-934f-219432eda481','0a9647f1-dbe7-48c2-b332-624a05a83df0','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-09 16:16:07',1,'2023-08-09 16:16:07',_binary '\0'),
('6be08a57-135c-4826-870e-b6fe6488f996','c336cb46-f45a-4f10-91fb-88003fd5dac6','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('6c1dfc4b-d2e1-4674-a722-33012bdaad6e','9127fb54-c0da-4077-99ac-21945d84cc78','content','VARCHAR(1024)','模版内容',_binary '\0','\'\'','String','','我是模版内容',_binary '','',4,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('6dad3402-f2b5-469e-8ba6-315fd60bfc65','c336cb46-f45a-4f10-91fb-88003fd5dac6','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('6eff99b7-6da8-4768-8f54-ded4dd7c543d','0a9647f1-dbe7-48c2-b332-624a05a83df0','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-09 16:16:07',1,'2023-08-09 16:16:07',_binary '\0'),
('6fef0c8e-6dfe-4f3e-a7f4-a702856548f1','4e4cc482-af80-4ebb-a8b7-4855f77b0b89','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('71deadeb-087f-4e05-81ad-e0042bbf6e18','0526ebf1-9d5a-4226-a47b-c4384acff4eb','index_name','VARCHAR(100)','索引名称',_binary '\0','\'\'','String','','IX_infra_database_column_name',_binary '','',3,1,'2023-08-18 08:52:03',1,'2023-08-18 08:56:31',_binary '\0'),
('728042a7-2765-42ca-99b8-6533f7be92a3','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('73707b7b-cb24-407b-af12-463540ebb8f7','c336cb46-f45a-4f10-91fb-88003fd5dac6','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('73cc9449-6aaa-4ac6-a806-317c8453a7b6','c336cb46-f45a-4f10-91fb-88003fd5dac6','email','VARCHAR(50)','邮箱',_binary '\0','\'\'','String','','yudao@iocoder.cn',_binary '\0','',6,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('73dc1b3e-f2f5-4695-a045-d9b6217a3798','209dd887-1db4-4260-9f9a-10efa33b439f','value','VARCHAR(100)','字典键值',_binary '\0','\'\'','String','','1',_binary '\0','',3,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('73f35b6f-afdd-4ce5-a8ee-67b51614f440','ba35bc1c-c14c-44af-9506-f5f57cc79fae','is_list','BIT(1)','是否是list',_binary '\0','0','Boolean','','0',_binary '\0','',3,1,'2023-08-16 11:53:48',1,'2023-08-16 11:53:48',_binary '\0'),
('745a95ad-95f5-4854-9528-a4bb0e2f192f','209dd887-1db4-4260-9f9a-10efa33b439f','status','TINYINT','状态（0正常 1停用）',_binary '\0','0','Integer','','0',_binary '\0','',5,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('74e31a26-c3d0-4187-a46b-da6ac1c55b22','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('7546d2c7-e0f2-4185-8baf-4c2ba37daff3','8e9c5164-801f-47e3-99d6-c28dd803a9e5','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('756715a8-9251-433c-8e01-4949ae7fa81e','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','type','TINYINT(4)','模块类型',_binary '\0','0','Integer','','0 分组 1 模块',_binary '\0','',4,1,'2023-07-24 08:44:59',1,'2023-07-24 08:44:59',_binary '\0'),
('79865483-a097-4ceb-b0f7-dbbc8bdfca96','9127fb54-c0da-4077-99ac-21945d84cc78','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('79a37110-8da1-4b74-9b0a-48d502881124','e1a06dd2-bba2-49f8-ae05-8c172f216d61','dept_id','BIGINT','部门ID',_binary '\0','0','Long','','1',_binary '\0','system_dept',5,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('7a4933a2-7dbe-44a4-9526-8f166a1adf9a','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','output_extend_class','VARCHAR(100)','出参继承类',_binary '\0','\'\'','String','','baseVO',_binary '\0','',11,1,'2023-08-14 09:32:55',1,'2023-08-14 09:32:55',_binary '\0'),
('7fc26fd3-cffd-4c32-a1b8-d058d700486d','c336cb46-f45a-4f10-91fb-88003fd5dac6','sort','INT','显示顺序',_binary '\0','0','Integer','','1',_binary '','',3,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('805ff4fe-9680-45a7-b1aa-1bb109ce3455','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-09 17:13:10',1,'2023-08-09 17:13:10',_binary '\0'),
('807fe034-f2a6-413c-a773-5e2f0e90c46d','21b4e945-9bf5-4718-829c-059259da899c','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('813ba2d9-d263-4f30-b4dd-bac4c2595b44','e1a06dd2-bba2-49f8-ae05-8c172f216d61','mobile','VARCHAR(11)','手机号码',_binary '\0','\'\'','String','','15601691300',_binary '\0','',8,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('85747c79-3737-4afa-928b-e580401b40d0','209dd887-1db4-4260-9f9a-10efa33b439f','label','VARCHAR(100)','字典标签',_binary '\0','\'\'','String','','管理后台',_binary '\0','',2,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('86605d63-26fd-49e2-a1db-2e6f6af8a615','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','template_nickname','VARCHAR(63)','模版发送人名称',_binary '\0','\'\'','String','','芋艿',_binary '','',5,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('86b70789-a105-4e46-87b0-2f93cafc4be4','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','template_code','VARCHAR(64)','模板编码',_binary '\0','\'\'','String','','test_01',_binary '','',4,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('87375ec2-dd21-4ada-b3f7-15d2af4fc123','62ca5e40-4a79-49bb-af2b-977e7205fb4e','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('882a1372-befe-43af-a9a1-be1c82917865','bc3f458b-d80d-4526-abcf-07d7277cccb3','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('88890bcd-d88b-4e34-b1e7-9ba4d001d897','8e9c5164-801f-47e3-99d6-c28dd803a9e5','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('88a6ba5c-0c05-46f9-acd8-67c11823d0c3','285770c9-d338-4913-95bb-7c50fe968d12','related_id','VARCHAR(100)','关联字段id',_binary '\0','\'\'','String','','23423423',_binary '\0','',5,1,'2023-08-09 09:22:34',1,'2023-08-22 10:23:33',_binary '\0'),
('8ac010f6-6719-441d-96b0-50686f164b83','21b4e945-9bf5-4718-829c-059259da899c','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('8b09d6d2-dbc3-4bbc-9708-ed8ef64fb926','285770c9-d338-4913-95bb-7c50fe968d12','related_type','TINYINT(4)','关联字段类型 1 column_id 2 VO_id 3 SubClass_id',_binary '\0','0','Integer','','0',_binary '\0','',6,1,'2023-08-09 09:22:34',1,'2023-08-20 11:07:38',_binary '\0'),
('8b363d63-48e0-46c2-b768-b8f23bb271c7','cac76521-8eec-4d07-87f8-df26fe2aed4e','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-19 12:05:23',1,'2023-08-19 12:05:23',_binary '\0'),
('8bbcaf4e-06d0-408d-82a6-5c248f8c8375','62ca5e40-4a79-49bb-af2b-977e7205fb4e','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('8d74fb5f-dc9b-4f13-bac6-897253d4cc83','21b4e945-9bf5-4718-829c-059259da899c','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('8eca9dd1-c71c-42c5-a89e-3d1d4328265c','285770c9-d338-4913-95bb-7c50fe968d12','inout_type','TINYINT(4)','出参入参 0 入参 1出参',_binary '\0','0','Integer','','0',_binary '\0','',12,1,'2023-08-09 15:02:46',1,'2023-08-09 15:02:46',_binary '\0'),
('8efbd977-40a4-4fcd-aa3e-34e757b14e5b','c336cb46-f45a-4f10-91fb-88003fd5dac6','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('8fbcf77b-dd1d-4568-badc-95065be5329b','e1a06dd2-bba2-49f8-ae05-8c172f216d61','status','TINYINT','帐号状态（0正常 1停用）',_binary '\0','0','Integer','','0',_binary '\0','',11,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('90c3ffcb-8bb4-460e-8b89-2c422586b668','0a9647f1-dbe7-48c2-b332-624a05a83df0','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-09 16:16:07',1,'2023-08-09 16:16:07',_binary '\0'),
('9128c758-a09b-4c53-9d6d-7b058a0d955d','bc3f458b-d80d-4526-abcf-07d7277cccb3','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('91b5797a-1398-40cc-a7ab-cbeca3f10d40','e1a06dd2-bba2-49f8-ae05-8c172f216d61','avatar','VARCHAR(512)','用户头像',_binary '\0','\'\'','String','','https://www.iocoder.cn/xxx.png',_binary '\0','',10,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('938b2051-f8df-4f92-a08e-57ce782370c4','73e7f661-5194-491e-acda-8f73316a3b93','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-17 18:32:29',1,'2023-08-17 18:32:29',_binary '\0'),
('966d84d7-6236-4a29-a003-26d5fdd0ae65','285770c9-d338-4913-95bb-7c50fe968d12','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('96fbdd5f-9ca7-4417-b261-8eab8ceca9cb','e1a06dd2-bba2-49f8-ae05-8c172f216d61','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('98285206-16b3-4ff5-a02b-2e82c917df17','8e9c5164-801f-47e3-99d6-c28dd803a9e5','sort','INT','显示顺序',_binary '\0','0','Integer','','',_binary '\0','',3,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('99635296-352f-4038-b255-fe94f35be2e8','376e9077-6e6d-47d3-b75d-b7aefdddccaa','required','BIT(1)','前端必传',_binary '\0','0','Boolean','','true',_binary '','',10,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('9a64fe84-845c-4dc4-a881-966af7f01573','16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','role_id','BIGINT','角色ID',_binary '\0','0','Long','','',_binary '\0','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('9aa0c4de-f159-42bd-be1f-a4a2d95680d2','e1a06dd2-bba2-49f8-ae05-8c172f216d61','nickname','VARCHAR(30)','用户昵称',_binary '\0','\'\'','String','','芋艿',_binary '','',3,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('9ab9ab08-1f5c-4132-bb0e-f2662d2e070f','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('9aeec1f2-902c-461d-bed6-e61aa0d34a68','21b4e945-9bf5-4718-829c-059259da899c','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('9bad68bf-5374-47cc-a8fc-90040b61f7dd','e1a06dd2-bba2-49f8-ae05-8c172f216d61','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('9d47e261-9882-4576-b5b6-1ff4c78bd2d7','cac76521-8eec-4d07-87f8-df26fe2aed4e','comment','VARCHAR(1024)','描述',_binary '\0','\'\'','String','','接口类',_binary '\0','',3,1,'2023-08-19 12:05:23',1,'2023-08-19 12:05:23',_binary '\0'),
('9f5bf9ea-a8cc-4fb1-b825-af11ee8f0550','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','input_extend_class','VARCHAR(100)','入参继承类',_binary '\0','\'\'','String','','baseVO',_binary '\0','',9,1,'2023-08-14 09:32:55',1,'2023-08-14 09:32:55',_binary '\0'),
('9fa46b81-1123-4641-863f-6d2888783011','62ca5e40-4a79-49bb-af2b-977e7205fb4e','status','TINYINT','状态,参见 CommonStatusEnum 枚举类',_binary '\0','0','Integer','','1',_binary '','',4,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('a056971a-c6d2-4e60-93bf-e7751b7aa627','0a9647f1-dbe7-48c2-b332-624a05a83df0','comment','VARCHAR(500)','表描述',_binary '\0','\'\'','String','','32',_binary '','',2,1,'2023-08-09 16:16:07',1,'2023-08-09 16:19:20',_binary '\0'),
('a09d8e90-ec7a-4fbb-b477-701f18c0aa7c','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','authorize','VARCHAR(50)','权限',_binary '\0','\'\'','String','','',_binary '\0','',4,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('a16863b2-2127-48ff-a886-d1d9dfcabcf0','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','read_status','BIT(1)','是否已读',_binary '\0','b\'0\'','Boolean','','true',_binary '','',9,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('a18b92ab-461f-4f5f-9e87-042fe2f5d807','8e9c5164-801f-47e3-99d6-c28dd803a9e5','data_scope_dept_ids','VARCHAR(500)','数据范围(指定部门数组)',_binary '\0','\'[]\'','List<Long>','','',_binary '\0','',5,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:59',_binary '\0'),
('a2a63a66-21fe-4dc0-85c9-403384e03811','209dd887-1db4-4260-9f9a-10efa33b439f','sort','INT','字典排序',_binary '\0','0','Integer','','1',_binary '\0','',1,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('a492b5ae-9934-42c7-83a7-d18e8c705e2c','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','input_servlet','BIT(1)','入参传HttpServletResponse',_binary '\0','0','Boolean','','0',_binary '\0','',12,1,'2023-09-25 11:19:17',1,'2023-09-25 11:46:09',_binary '\0'),
('a5a05271-b106-413f-b474-5d3ba717f2ca','8e9c5164-801f-47e3-99d6-c28dd803a9e5','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('a5b5c482-f794-4e63-888b-3b86d9bdae1e','73e7f661-5194-491e-acda-8f73316a3b93','id','BIGINT','主键ID',_binary '\0','','Long','','',_binary '\0','',0,1,'2023-08-17 18:32:29',1,'2023-08-17 18:32:29',_binary '\0'),
('a6e0ce56-5d9c-4f8b-9389-f915d1410dd3','62ca5e40-4a79-49bb-af2b-977e7205fb4e','name','VARCHAR(50)','岗位名称',_binary '\0','\'\'','String','','小博主',_binary '','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('a823105b-86ce-4b49-addc-f09defbd8dc0','9127fb54-c0da-4077-99ac-21945d84cc78','type','TINYINT','类型',_binary '\0','0','Integer','','1',_binary '','',5,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('a84480a0-2705-40f3-bb09-a5434db55a36','285770c9-d338-4913-95bb-7c50fe968d12','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('a920d5d2-012a-4e9c-9a58-1e82e247b903','21b4e945-9bf5-4718-829c-059259da899c','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('a9a14c19-c636-4c3e-b060-03a09cd1d452','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','output_type','VARCHAR(50)','出参类型',_binary '\0','\'\'','String','','void',_binary '\0','',10,1,'2023-08-14 09:32:55',1,'2023-08-14 09:32:55',_binary '\0'),
('aacf4ccc-de91-49ea-b786-dc28ed838ecb','376e9077-6e6d-47d3-b75d-b7aefdddccaa','data_type','VARCHAR(100)','字段类型',_binary '\0','\'\'','String','','int(11)',_binary '','',3,1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0'),
('ab7e105c-59c4-4934-8fbe-b1685a347cc9','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-09 17:13:11',1,'2023-08-09 17:13:11',_binary '\0'),
('abe6d15b-3301-4d7d-8a8f-3b3c06835453','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('acbdc233-708c-4939-b4c5-59d865b09280','8e9c5164-801f-47e3-99d6-c28dd803a9e5','status','TINYINT','角色状态（0正常 1停用）',_binary '\0','0','Integer','','',_binary '\0','',6,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('ae012ea8-d7cc-4129-9f4f-a00a0fd78a85','0a9647f1-dbe7-48c2-b332-624a05a83df0','business_name','VARCHAR(30)','模块名',_binary '\0','\'\'','String','','codegen',_binary '','',4,1,'2023-08-09 16:16:07',1,'2023-08-09 16:19:20',_binary '\0'),
('b2318e23-40e1-4f25-97f8-826273992a05','cac76521-8eec-4d07-87f8-df26fe2aed4e','name','VARCHAR(125)','子类名称',_binary '\0','\'\'','String','','column',_binary '','',2,1,'2023-08-19 12:05:23',1,'2023-08-19 17:37:22',_binary '\0'),
('b2a23a67-bacc-45b4-a80f-2979bec91aab','376e9077-6e6d-47d3-b75d-b7aefdddccaa','related_table','VARCHAR(200)','关联表',_binary '\0','\'\'','String','','system_user',_binary '','',11,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('b2c0d63e-e518-4f7a-83cf-a5864c0afec5','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','parent_id','VARCHAR(50)','父表ID',_binary '\0','\'\'','UUID','','',_binary '\0','',1,1,'2023-08-09 17:13:10',1,'2023-08-09 17:16:02',_binary '\0'),
('b30abf58-0745-477d-b7d9-33d68a383f88','73e7f661-5194-491e-acda-8f73316a3b93','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-17 18:32:29',1,'2023-08-17 18:32:29',_binary '\0'),
('b34055dd-2ba5-4306-b6ae-4ab220c878d3','209dd887-1db4-4260-9f9a-10efa33b439f','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('b51f96aa-eb78-4ad8-99a1-d9138b2f8a9f','ba35bc1c-c14c-44af-9506-f5f57cc79fae','mapping_table','VARCHAR(125)','映射表',_binary '\0','\'\'','String','','InfraInterfaceValidation',_binary '\0','',5,1,'2023-08-16 11:53:48',1,'2023-08-16 11:53:48',_binary '\0'),
('b6ad382e-f6db-4ea4-a52c-2418bf77991e','9127fb54-c0da-4077-99ac-21945d84cc78','remark','VARCHAR(255','备注',_binary '\0','\'\'','String','','我是备注',_binary '\0','',8,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('b7d4e8ec-ab89-49c5-9584-d74387abad7b','73e7f661-5194-491e-acda-8f73316a3b93','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-17 18:32:29',1,'2023-08-17 18:32:29',_binary '\0'),
('b87b159e-fd93-4cc8-8b9d-c5ac304a60ac','cac76521-8eec-4d07-87f8-df26fe2aed4e','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-19 12:05:24',1,'2023-08-19 12:05:24',_binary '\0'),
('b8ec1692-9c44-4d19-843c-e33b49f0869e','16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','user_id','BIGINT','用户ID',_binary '\0','0','Long','','',_binary '\0','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('b9ced482-c749-461e-a81b-a532f1dd5ae5','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('baaf3587-e9a8-4ef8-a895-ebf1536af054','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','template_type','INT','模版类型',_binary '\0','0','Integer','','2',_binary '','',7,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('bb797fa4-2bf8-43d1-9572-ce71383e096f','21b4e945-9bf5-4718-829c-059259da899c','post_id','BIGINT','岗位ID',_binary '\0','0','Long','','0',_binary '\0','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('bb96efa6-11c5-4468-9dcb-c144645fed48','376e9077-6e6d-47d3-b75d-b7aefdddccaa','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('bc904f86-5d98-4e13-a40a-aaf8326ef2c7','cac76521-8eec-4d07-87f8-df26fe2aed4e','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-19 12:05:24',1,'2023-08-19 12:05:24',_binary '\0'),
('be8e11e7-dcee-4cce-94d3-89173ad516ee','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','module_id','VARCHAR(50)','接口模块',_binary '\0','\'\'','UUID','','',_binary '\0','infra_interface_module',7,1,'2023-07-25 14:17:08',1,'2023-08-14 09:32:55',_binary '\0'),
('bf387975-5299-46d7-9169-e605ac2f7582','376e9077-6e6d-47d3-b75d-b7aefdddccaa','dict_type','VARCHAR(200)','字典类型',_binary '\0','\'\'','String','','sys_gender',_binary '\0','',8,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('c0950de4-5474-4477-81da-bed16c61bebf','e1a06dd2-bba2-49f8-ae05-8c172f216d61','login_date','DATETIME','最后登录时间',_binary '','NULL','LocalDateTime','','',_binary '\0','',13,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('c1b68625-983a-4c42-863f-3d7e0bd4b2f1','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-09-20 10:06:36',1,'2023-09-20 10:06:36',_binary '\0'),
('c78f9133-bd60-4da0-9dbf-20b7e3f15290','c336cb46-f45a-4f10-91fb-88003fd5dac6','phone','VARCHAR(11)','联系电话',_binary '\0','\'\'','String','','15601691000',_binary '\0','',5,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('c8a03def-e521-442d-b609-2609674d5106','ba35bc1c-c14c-44af-9506-f5f57cc79fae','table_id','VARCHAR(50)','表ID',_binary '\0','\'\'','UUID','','324562',_binary '\0','infra_database_table',1,1,'2023-08-16 11:53:48',1,'2023-08-16 11:53:48',_binary '\0'),
('c90cf366-538d-402d-b152-08ae25e21353','209dd887-1db4-4260-9f9a-10efa33b439f','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('c96867ba-29f8-46f4-9207-7232a607cf46','bc3f458b-d80d-4526-abcf-07d7277cccb3','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('c96cd9f7-bcbe-473e-a43b-f851b700288a','bc3f458b-d80d-4526-abcf-07d7277cccb3','comment','VARCHAR(200)','描述',_binary '\0','\'\'','String','','VO类',_binary '\0','',2,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('ca5cb26c-8fe6-45ba-a9e4-dc59be98544a','bc3f458b-d80d-4526-abcf-07d7277cccb3','parent_id','VARCHAR(50)','父类ID',_binary '\0','\'\'','String','','23424552',_binary '\0','',4,1,'2023-08-10 17:34:17',1,'2023-08-24 10:33:47',_binary '\0'),
('cb4e8d8c-acc9-4edb-981b-3ff48c41c2aa','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','comment','VARCHAR(500)','模块描述',_binary '\0','\'\'','String','','用户',_binary '\0','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('cb8041ae-0ed7-40e8-8b69-95d7b5409246','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('cf89d984-fedf-43a5-8e01-861fd4795763','285770c9-d338-4913-95bb-7c50fe968d12','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('cff8e930-e761-4ac6-9639-a72dac09b955','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','template_params','VARCHAR(255)','模版参数',_binary '\0','\'\'','Map<String, Object>','','',_binary '','',8,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('d07976b2-41a1-4bcb-8036-56b30517a4af','376e9077-6e6d-47d3-b75d-b7aefdddccaa','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('d48c80ad-170d-4b40-90b0-5cbabbad097d','0526ebf1-9d5a-4226-a47b-c4384acff4eb','index_type','VARCHAR(20)','索引类型',_binary '\0','\'\'','String','','INDEX',_binary '','',2,1,'2023-08-18 08:52:03',1,'2023-08-18 08:56:31',_binary '\0'),
('d49b58e1-a4a3-4c1c-82e0-036e9b8ee61a','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-09-20 10:06:35',1,'2023-09-20 10:06:35',_binary '\0'),
('d49ebc28-c52d-43f9-9478-afdd885d0a6e','73e7f661-5194-491e-acda-8f73316a3b93','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-17 18:32:29',1,'2023-08-17 18:32:29',_binary '\0'),
('d5c9e528-34b8-40df-9101-0584546ec668','ba35bc1c-c14c-44af-9506-f5f57cc79fae','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-16 11:53:49',1,'2023-08-16 11:53:49',_binary '\0'),
('d7267b4d-32bc-4308-b7b6-33460b05b392','62ca5e40-4a79-49bb-af2b-977e7205fb4e','code','VARCHAR(64)','岗位编码',_binary '\0','\'\'','String','','yudao',_binary '','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('d7347e56-cd3e-4b62-ad84-d67755c37d1d','209dd887-1db4-4260-9f9a-10efa33b439f','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('d833bbfe-d2f5-49c7-9e96-c184128241fa','376e9077-6e6d-47d3-b75d-b7aefdddccaa','example','VARCHAR(64)','数据示例',_binary '\0','\'\'','String','','1024',_binary '\0','',9,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('dbb2c865-a951-42e9-9033-4e2215665ab3','e1a06dd2-bba2-49f8-ae05-8c172f216d61','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('dc19695b-9784-458f-855c-006ed8becf05','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('dcce6cb6-eb3e-484b-bacf-6c187564028b','209dd887-1db4-4260-9f9a-10efa33b439f','remark','VARCHAR(500)','备注',_binary '\0','\'\'','String','','',_binary '\0','',8,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('dd5b8890-5b05-4cb0-b3dc-edf8c5b04db3','285770c9-d338-4913-95bb-7c50fe968d12','is_list','BIT(1)','是否是list',_binary '\0','0','Boolean','','1',_binary '\0','',3,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('e012303f-dc51-4efb-86ed-129c19873955','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','type','TINYINT','公告类型（1通知 2公告）',_binary '\0','0','Integer','','0',_binary '','',3,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('e0b054a6-5ac6-4d83-8a3d-bdb03cb1bfeb','376e9077-6e6d-47d3-b75d-b7aefdddccaa','java_type','VARCHAR(32)','Java 属性类型',_binary '\0','\'\'','String','','userAge',_binary '','',7,1,'2023-08-17 18:47:40',1,'2023-08-17 18:47:40',_binary '\0'),
('e19f68f2-b57e-471e-beb0-e607ff277083','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','validation_condition','VARCHAR(1024)','校验条件',_binary '\0','\'\'','String','','regexp = \"^[a-zA-Z0-9]{4,30}$\"',_binary '','',4,1,'2023-08-09 17:13:10',1,'2023-08-09 17:25:58',_binary '\0'),
('e28d3e1e-98ee-463e-9d43-cefaca5526b0','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('e402f0d0-da98-4bfa-b72c-554701efff65','0a9647f1-dbe7-48c2-b332-624a05a83df0','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-09 16:16:07',1,'2023-08-09 16:16:07',_binary '\0'),
('e63f3260-23dc-4ffc-804a-3efdf6e048d5','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-09 17:13:10',1,'2023-08-09 17:13:10',_binary '\0'),
('e6901bbd-6489-40c9-bc9a-9b44651e3261','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('e778bd69-389d-4097-9f2a-ffcb7210662b','0526ebf1-9d5a-4226-a47b-c4384acff4eb','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-08-18 08:52:03',1,'2023-08-18 08:52:03',_binary '\0'),
('e81bd8d8-3afe-4968-95bb-6f5248273046','285770c9-d338-4913-95bb-7c50fe968d12','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('e8225fbb-522b-4439-ac73-e521685d24b2','c336cb46-f45a-4f10-91fb-88003fd5dac6','parent_id','BIGINT','父部门id',_binary '\0','0','Long','','1',_binary '\0','',2,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('e8936840-ee2b-4d56-82a4-fc74f05a439a','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('e941d8a3-8973-4bd1-b6c5-45d711ad1601','9127fb54-c0da-4077-99ac-21945d84cc78','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('e99611b1-7d64-4266-977d-2431b4cfeaca','9127fb54-c0da-4077-99ac-21945d84cc78','name','VARCHAR(63)','模板名称',_binary '\0','\'\'','String','','测试模版',_binary '','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('e9bfe361-b4d7-4f8f-8c47-88e6807695b4','ba35bc1c-c14c-44af-9506-f5f57cc79fae','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-16 11:53:49',1,'2023-08-16 11:53:49',_binary '\0'),
('eb978885-5e9f-4e7a-a27d-0e6da3b1518c','73e7f661-5194-491e-acda-8f73316a3b93','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-08-17 18:32:29',1,'2023-08-17 18:32:29',_binary '\0'),
('ec214d65-e60f-44ef-bbce-7fe1d31f674b','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-09-20 10:06:36',1,'2023-09-20 10:06:36',_binary '\0'),
('ec6ac848-cd33-4efa-addd-a18ccadb76ce','285770c9-d338-4913-95bb-7c50fe968d12','variable_type','VARCHAR(100)','参数类型',_binary '\0','\'\'','String','','string',_binary '\0','',4,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('f145eafb-56b9-427a-b8b2-8e458a75e98d','209dd887-1db4-4260-9f9a-10efa33b439f','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('f18551be-59c9-4210-8617-1b740c4678d9','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','update_time','TIMESTAMP','更新时间',_binary '\0','CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1001,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('f2188300-5198-466b-8b92-c45140010a67','e1a06dd2-bba2-49f8-ae05-8c172f216d61','post_ids','VARCHAR(255)','岗位编号数组',_binary '','NULL','List<Long>','','[1]',_binary '\0','',6,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('f221073c-289d-4503-bf86-6189a5ecf631','8e9c5164-801f-47e3-99d6-c28dd803a9e5','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('f33a91a5-9c5b-4e95-aafb-302026716271','16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','create_time','TIMESTAMP','创建时间',_binary '\0','CURRENT_TIMESTAMP','LocalDateTime','','',_binary '\0','',1000,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('f50da2eb-ce90-49a9-893e-5a7adf346512','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('f546673a-d0d4-45a2-8a36-62855c357fc0','16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('f581b655-03a6-4e92-98ae-c0c66adcff47','ba35bc1c-c14c-44af-9506-f5f57cc79fae','annotate','VARCHAR(1024)','注解',_binary '\0','\'\'','String','','@Transient(InfraInterfaceValidationResolver.class)',_binary '\0','',4,1,'2023-08-16 11:53:48',1,'2023-08-16 11:53:48',_binary '\0'),
('f584212e-f469-4e1b-9001-4a1607107bc4','bc3f458b-d80d-4526-abcf-07d7277cccb3','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-08-10 17:34:17',1,'2023-08-10 17:34:17',_binary '\0'),
('f5e0d6ac-57f9-4901-8d0f-899f8f83cfdb','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('f6d986a4-2cbd-4b7f-b06d-f3e4c2d57d93','842da8a4-f2ff-4ca0-b169-170a1f88ef3c','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('f8ea28fa-825b-4889-bb51-98f5e6d93100','285770c9-d338-4913-95bb-7c50fe968d12','name','VARCHAR(100)','参数名',_binary '\0','\'\'','String','','name',_binary '\0','',1,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('fa1acbb0-cb06-4668-91e3-26fbad23841b','62ca5e40-4a79-49bb-af2b-977e7205fb4e','creator_id','BIGINT','创建人ID',_binary '\0','0','Long','','',_binary '\0','',1002,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('fb681285-c479-453c-ad9a-db494e56c26a','376e9077-6e6d-47d3-b75d-b7aefdddccaa','column_name','VARCHAR(100)','字段名',_binary '\0','\'\'','String','','user_age',_binary '','',2,1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0'),
('fba46a68-1f55-4372-b7c4-0b0758ac1950','cac76521-8eec-4d07-87f8-df26fe2aed4e','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-08-19 12:05:24',1,'2023-08-19 12:05:24',_binary '\0'),
('fc51a021-51ac-4cf0-80aa-ab906304c256','3a4f68eb-f789-4fc2-abb5-e86399d35fe7','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('fc98d7d1-b6f0-4970-98da-743d3457c097','e1a06dd2-bba2-49f8-ae05-8c172f216d61','username','VARCHAR(30)','用户账号',_binary '\0','\'\'','String','','yudao',_binary '','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('fd044d93-59cb-489c-bc25-85f868d46708','cac76521-8eec-4d07-87f8-df26fe2aed4e','id','VARCHAR(50)','主键ID',_binary '\0','','UUID','','',_binary '\0','',0,1,'2023-08-19 12:05:23',1,'2023-08-19 12:05:23',_binary '\0'),
('fd22baec-a8b7-4f03-96b0-5458c9d9936b','9127fb54-c0da-4077-99ac-21945d84cc78','nickname','VARCHAR(255)','发送人名称',_binary '\0','\'\'','String','','土豆',_binary '','',3,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('fd88f706-1f7d-4eb7-8ed0-cd9a3ad11e41','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','updater_id','BIGINT','修改人ID',_binary '\0','0','Long','','',_binary '\0','',1003,1,'2023-09-20 10:06:36',1,'2023-09-20 10:06:36',_binary '\0'),
('fde1fa21-378f-479d-bd73-35cdc51e3b57','285770c9-d338-4913-95bb-7c50fe968d12','deleted','TINYINT(4)','是否删除',_binary '\0','0','boolean','','',_binary '\0','',1004,1,'2023-08-09 09:22:34',1,'2023-08-09 09:22:34',_binary '\0'),
('ff088946-e596-47c7-abbf-d73b92f422c0','8e93110a-90fe-4e9c-9fb5-f7fa480dc892','remark','VARCHAR(500)','备注',_binary '\0','\'\'','String','','\'\'',_binary '\0','',4,1,'2023-09-20 10:06:36',1,'2023-09-20 10:06:36',_binary '\0'),
('ff6a8fd2-1bea-4f13-b872-80caf475f749','7f7c2842-fbc4-4b37-9ec9-4949e36307ed','title','VARCHAR(50)','公告标题',_binary '\0','\'\'','String','','小博主',_binary '','',1,1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0');
/*!40000 ALTER TABLE `infra_database_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_database_index`
--

DROP TABLE IF EXISTS `infra_database_index`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_database_index` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `table_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表编号',
  `index_type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '索引类型',
  `index_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '索引名称',
  `column_names` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字段',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNI_infra_database_index_name` (`index_name`,`index_type`,`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='索引';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_database_index`
--

LOCK TABLES `infra_database_index` WRITE;
/*!40000 ALTER TABLE `infra_database_index` DISABLE KEYS */;
INSERT INTO `infra_database_index` VALUES 
('120f3902-b0ff-4f6f-8c02-b2358e55f70c','cac76521-8eec-4d07-87f8-df26fe2aed4e','UNIQUE INDEX','UK_infra_interface_subclass','[\"name\",\"interface_id\",\"type\"]','2023-08-19 04:05:24','2023-08-19 04:05:24',1,1,0),
('29e6cdc9-5b2c-4d27-a09b-cd7153b8d404','209dd887-1db4-4260-9f9a-10efa33b439f','UNIQUE INDEX','un_infra_dict_data','[\"type_id\",\"value\"]','2023-09-20 02:13:54','2023-09-20 02:13:54',1,1,0),
('4abaccb7-f2a3-47d5-909f-02fc9082db08','bc3f458b-d80d-4526-abcf-07d7277cccb3','UNIQUE INDEX','UK_infra_interface_param_class','[\"name\"]','2023-08-10 09:36:29','2023-08-10 09:36:29',1,1,0),
('5dfa879c-8703-4100-b5b3-d7910ede95bd','ba35bc1c-c14c-44af-9506-f5f57cc79fae','UNIQUE INDEX','UN_infra_database_mapping','[\"table_id\",\"name\"]','2023-08-16 06:08:04','2023-08-16 06:08:04',1,1,0),
('65ae7744-e028-474b-b52b-e4132feaab90','bb3acb96-f7d5-4b8a-97c7-3f02abb12153','UNIQUE INDEX','UNI_infra_database_validation','[\"parent_id\",\"validation\"]','2023-08-09 09:13:11','2023-08-09 09:13:11',1,1,0),
('7441d4ad-b801-4d0c-91dd-8d5f9b219b27','285770c9-d338-4913-95bb-7c50fe968d12','UNIQUE INDEX','UK_infra_interface_param','[\"name\",\"parent_id\",\"inout_type\"]','2023-08-09 07:02:46','2023-09-05 10:52:44',1,1,0),
('8101f976-268d-4dd9-a957-2258e9d8f32d','376e9077-6e6d-47d3-b75d-b7aefdddccaa','UNIQUE INDEX','UK_infra_database_column','[\"column_name\",\"table_id\"]','2023-08-17 10:47:40','2023-08-17 10:47:40',1,1,0),
('bbd232d2-d56e-41f1-b5cd-aac77457af05','f8d21972-62c8-4f31-92ec-5edadb800a46','INDEX','dfd','[\"dd\"]','2023-08-17 08:33:50','2023-08-17 08:33:50',1,1,0),
('d92d0194-c979-42dd-958b-1d456b43a0d6','0526ebf1-9d5a-4226-a47b-c4384acff4eb','UNIQUE INDEX','UK_infra_database_index','[\"index_name\",\"table_id\"]','2023-08-18 00:52:03','2023-08-18 00:52:03',1,1,0),
('dbbda193-25e6-4117-8b2e-3d99cb9adb45','0a9647f1-dbe7-48c2-b332-624a05a83df0','UNIQUE INDEX','UNI_infra_database_table_name','[\"name\"]','2023-08-09 08:16:49','2023-08-09 08:16:49',1,1,0);
/*!40000 ALTER TABLE `infra_database_index` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_database_mapping`
--

DROP TABLE IF EXISTS `infra_database_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_database_mapping` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `table_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表ID',
  `name` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '映射名',
  `is_list` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是list',
  `annotate` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '注解',
  `mapping_table` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '映射表',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UN_infra_database_mapping` (`table_id`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据库表映射';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_database_mapping`
--

LOCK TABLES `infra_database_mapping` WRITE;
/*!40000 ALTER TABLE `infra_database_mapping` DISABLE KEYS */;
INSERT INTO `infra_database_mapping` VALUES 
('330bf45e-a019-4818-a793-b597e436ab3d','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','inputParams',_binary '','@Transient(InfraInterfaceInputParamResolver.class)','infra_interface_param','2023-08-19 03:33:18','2023-08-19 03:33:18',1,1,0),
('48c9b35d-d5ee-468d-9b9e-d030269289eb','376e9077-6e6d-47d3-b75d-b7aefdddccaa','validations',_binary '','@Transient(InfraInterfaceValidationResolver.class)','infra_interface_validation','2023-08-17 10:47:40','2023-08-17 10:47:40',1,1,0),
('924a6cdd-7165-4f0c-953f-50d27d4f8404','f8d21972-62c8-4f31-92ec-5edadb800a46','dfd',_binary '\0','sdfsdfsdf','infra_database_column','2023-08-17 08:33:50','2023-08-17 08:33:50',1,1,0),
('96d3d349-1a15-4ee5-bbdb-9f9055769290','285770c9-d338-4913-95bb-7c50fe968d12','validations',_binary '','@Transient(InfraInterfaceValidationResolver.class)','infra_interface_validation','2023-08-20 03:07:38','2023-08-20 03:07:38',1,1,0),
('9fff6d2a-c6e6-411f-aa67-9cc0d5c88f21','0a9647f1-dbe7-48c2-b332-624a05a83df0','mappings',_binary '','@OneToMany(mappedBy = \"table\")','infra_database_mapping','2023-08-16 10:47:03','2023-08-16 10:47:03',1,1,0),
('c9c1d711-47bc-4ad6-9924-4647c3a0f0d6','cac76521-8eec-4d07-87f8-df26fe2aed4e','subclassParams',_binary '','@Transient(InfraInterfaceSubclassParamResolver.class)','infra_interface_param','2023-08-19 04:05:24','2023-08-20 03:51:40',1,1,0),
('d2dcc5af-0577-4154-929f-25d3ba947bad','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','outputSubclasses',_binary '','@Transient(InfraInterfaceOutputSubclassResolver.class)','infra_interface_subclass','2023-08-20 03:35:39','2023-08-20 03:35:39',1,1,0),
('edc6bb96-fa72-462c-ab4b-525765daa532','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','outputParams',_binary '','@Transient(InfraInterfaceOutputParamResolver.class)','infra_interface_param','2023-08-19 03:33:18','2023-08-19 03:33:18',1,1,0),
('ef8eb67d-0e6f-445b-a58a-749a9819be44','7a6ac56d-422e-40a0-a0e6-1578dfb1811d','inputSubclasses',_binary '','@Transient(InfraInterfaceInputSubclassResolver.class)','infra_interface_subclass','2023-08-20 03:35:39','2023-08-20 03:35:39',1,1,0);
/*!40000 ALTER TABLE `infra_database_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_database_table`
--

DROP TABLE IF EXISTS `infra_database_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_database_table` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名称',
  `comment` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表描述',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `business_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模块名',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNI_infra_database_table_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据库表定义';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_database_table`
--

LOCK TABLES `infra_database_table` WRITE;
/*!40000 ALTER TABLE `infra_database_table` DISABLE KEYS */;
INSERT INTO `infra_database_table` VALUES 
('0526ebf1-9d5a-4226-a47b-c4384acff4eb','infra_database_index','数据库表索引','','codegen',1,'2023-08-18 08:52:03',1,'2023-08-18 08:52:03',_binary '\0'),
('0a9647f1-dbe7-48c2-b332-624a05a83df0','infra_database_table','数据库表定义','','codegen',1,'2023-08-09 16:16:07',1,'2023-08-09 16:16:07',_binary '\0'),
('16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','system_user_role','用户和角色关联表','','permission',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('209dd887-1db4-4260-9f9a-10efa33b439f','infra_dict_data','字典数据表','','data',1,'2023-09-20 10:13:54',1,'2023-09-20 10:13:54',_binary '\0'),
('21b4e945-9bf5-4718-829c-059259da899c','system_user_post','用户岗位表','','dept',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('285770c9-d338-4913-95bb-7c50fe968d12','infra_interface_param','接口参数','','codegen',1,'2023-08-09 09:22:33',1,'2023-08-09 09:22:33',_binary '\0'),
('376e9077-6e6d-47d3-b75d-b7aefdddccaa','infra_database_column','数据库表字段','','codegen',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0'),
('3a4f68eb-f789-4fc2-abb5-e86399d35fe7','infra_interface_module','接口模块','','codegen',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('4e4cc482-af80-4ebb-a8b7-4855f77b0b89','system_role_menu','角色和菜单关联表','','permission',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('62ca5e40-4a79-49bb-af2b-977e7205fb4e','system_post','岗位信息表','','dept',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('7a6ac56d-422e-40a0-a0e6-1578dfb1811d','infra_interface','接口','','codegen',1,'2023-07-25 11:59:34',1,'2023-07-25 11:59:34',_binary '\0'),
('7f7c2842-fbc4-4b37-9ec9-4949e36307ed','system_notice','通知公告表','','notify',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('842da8a4-f2ff-4ca0-b169-170a1f88ef3c','system_notify_message','站内信消息表','','notify',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('8e93110a-90fe-4e9c-9fb5-f7fa480dc892','infra_dict_type','字典类型表','','data',1,'2023-09-20 10:06:35',1,'2023-09-20 10:06:35',_binary '\0'),
('8e9c5164-801f-47e3-99d6-c28dd803a9e5','system_role','角色信息表','','permission',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('9127fb54-c0da-4077-99ac-21945d84cc78','system_notify_template','站内信模板表','','notify',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('ba35bc1c-c14c-44af-9506-f5f57cc79fae','infra_database_mapping','数据库表映射','','codegen',1,'2023-08-16 11:53:48',1,'2023-08-16 11:53:48',_binary '\0'),
('bb3acb96-f7d5-4b8a-97c7-3f02abb12153','infra_interface_validation','代码生成表校验定义','','codegen',1,'2023-08-09 17:13:10',1,'2023-08-09 17:13:10',_binary '\0'),
('bc3f458b-d80d-4526-abcf-07d7277cccb3','infra_interface_vo_class','接口VO类','','codegen',1,'2023-08-10 17:34:17',1,'2023-08-24 08:51:08',_binary '\0'),
('c336cb46-f45a-4f10-91fb-88003fd5dac6','system_dept','部门表','','dept',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0'),
('cac76521-8eec-4d07-87f8-df26fe2aed4e','infra_interface_subclass','接口子类','','codegen',1,'2023-08-19 12:05:23',1,'2023-08-19 12:05:23',_binary '\0'),
('e1a06dd2-bba2-49f8-ae05-8c172f216d61','system_user','用户信息表','','user',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0');
/*!40000 ALTER TABLE `infra_database_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_dict_data`
--

DROP TABLE IF EXISTS `infra_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_dict_data` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `sort` int NOT NULL DEFAULT '0' COMMENT '字典排序',
  `label` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `value` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `type_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `color_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '颜色类型',
  `css_class` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'css 样式',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `un_infra_dict_data` (`type_id`,`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_dict_data`
--

LOCK TABLES `infra_dict_data` WRITE;
/*!40000 ALTER TABLE `infra_dict_data` DISABLE KEYS */;
INSERT INTO `infra_dict_data` VALUES 
('46171f5b-5794-11ee-873c-e073e73d10a7',1,'男','1','dbffed9d-db1b-1f14-80e9-0feba9cc54c6',0,'default','A','性别男','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46173c0f-5794-11ee-873c-e073e73d10a7',1,'管理后台','1','c0fcc1e7-02a0-635b-0702-2c96f6482dae',0,'','','代码生成的场景枚举 - 管理后台','2022-02-02 05:15:06','2023-09-20 09:01:23',1,1,0),
('461747a5-5794-11ee-873c-e073e73d10a7',2,'用户 APP','2','c0fcc1e7-02a0-635b-0702-2c96f6482dae',0,'','','代码生成的场景枚举 - 用户 APP','2022-02-02 05:15:19','2023-09-20 09:01:23',1,1,0),
('461748d8-5794-11ee-873c-e073e73d10a7',1,'数据库','1','34949cd7-b2bf-57e6-07c0-a432d61da5cf',0,'default','','','2022-03-14 16:25:28','2023-09-20 09:01:23',1,1,0),
('4617492d-5794-11ee-873c-e073e73d10a7',10,'本地磁盘','10','34949cd7-b2bf-57e6-07c0-a432d61da5cf',0,'default','','','2022-03-14 16:25:41','2023-09-20 09:01:23',1,1,0),
('4617497e-5794-11ee-873c-e073e73d10a7',11,'FTP 服务器','11','34949cd7-b2bf-57e6-07c0-a432d61da5cf',0,'default','','','2022-03-14 16:26:06','2023-09-20 09:01:23',1,1,0),
('461749c9-5794-11ee-873c-e073e73d10a7',12,'SFTP 服务器','12','34949cd7-b2bf-57e6-07c0-a432d61da5cf',0,'default','','','2022-03-14 16:26:22','2023-09-20 09:01:23',1,1,0),
('46174a0c-5794-11ee-873c-e073e73d10a7',20,'S3 对象存储','20','34949cd7-b2bf-57e6-07c0-a432d61da5cf',0,'default','','','2022-03-14 16:26:31','2023-09-20 09:01:23',1,1,0),
('46174a4d-5794-11ee-873c-e073e73d10a7',103,'短信登录','103','6398cf43-47ad-a20d-43dd-1dbf77a9ba17',0,'default','','','2022-05-09 15:57:58','2023-09-20 09:01:23',1,1,0),
('46174a93-5794-11ee-873c-e073e73d10a7',1,'password','password','9a353673-29a0-dbbc-2628-ec406ed80618',0,'default','','密码模式','2022-05-11 16:22:05','2023-09-20 09:01:23',1,1,0),
('46174ad8-5794-11ee-873c-e073e73d10a7',2,'authorization_code','authorization_code','9a353673-29a0-dbbc-2628-ec406ed80618',0,'primary','','授权码模式','2022-05-11 16:22:59','2023-09-20 09:01:23',1,1,0),
('46174e42-5794-11ee-873c-e073e73d10a7',3,'implicit','implicit','9a353673-29a0-dbbc-2628-ec406ed80618',0,'success','','简化模式','2022-05-11 16:23:40','2023-09-20 09:01:23',1,1,0),
('46174e9d-5794-11ee-873c-e073e73d10a7',4,'client_credentials','client_credentials','9a353673-29a0-dbbc-2628-ec406ed80618',0,'default','','客户端模式','2022-05-11 16:23:51','2023-09-20 09:01:23',1,1,0),
('46174eee-5794-11ee-873c-e073e73d10a7',5,'refresh_token','refresh_token','9a353673-29a0-dbbc-2628-ec406ed80618',0,'info','','刷新模式','2022-05-11 16:24:02','2023-09-20 09:01:23',1,1,0),
('46174f4c-5794-11ee-873c-e073e73d10a7',10,'微信小程序','10','83100158-f4c4-59e1-19d8-6851fd0a4d07',0,'default','','终端 - 微信小程序','2022-12-10 02:51:11','2023-09-20 09:01:23',1,1,0),
('46174f91-5794-11ee-873c-e073e73d10a7',20,'H5 网页','20','83100158-f4c4-59e1-19d8-6851fd0a4d07',0,'default','','终端 - H5 网页','2022-12-10 02:51:30','2023-09-20 09:01:23',1,1,0),
('46174fd5-5794-11ee-873c-e073e73d10a7',11,'微信公众号','11','83100158-f4c4-59e1-19d8-6851fd0a4d07',0,'default','','终端 - 微信公众号','2022-12-10 02:54:16','2023-09-20 09:01:23',1,1,0),
('46175029-5794-11ee-873c-e073e73d10a7',31,'苹果 App','31','83100158-f4c4-59e1-19d8-6851fd0a4d07',0,'default','','终端 - 苹果 App','2022-12-10 02:54:42','2023-09-20 09:01:23',1,1,0),
('4617506b-5794-11ee-873c-e073e73d10a7',32,'安卓 App','32','83100158-f4c4-59e1-19d8-6851fd0a4d07',0,'default','','终端 - 安卓 App','2022-12-10 02:55:02','2023-09-20 09:01:23',1,1,0),
('461750ac-5794-11ee-873c-e073e73d10a7',1,'系统内置','1','7e60703e-c696-22cb-74de-35a0f599aaee',0,'danger','','参数类型 - 系统内置','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('461750f7-5794-11ee-873c-e073e73d10a7',0,'初始化','0','7e5eb37b-d5f0-df78-5edd-a5ed73879029',0,'primary','','邮件发送状态 - 初始化\n','2023-01-26 01:53:49','2023-09-20 09:01:23',1,1,0),
('4617513a-5794-11ee-873c-e073e73d10a7',10,'发送成功','10','7e5eb37b-d5f0-df78-5edd-a5ed73879029',0,'success','','邮件发送状态 - 发送成功','2023-01-26 01:54:28','2023-09-20 09:01:23',1,1,0),
('4617517f-5794-11ee-873c-e073e73d10a7',20,'发送失败','20','7e5eb37b-d5f0-df78-5edd-a5ed73879029',0,'danger','','邮件发送状态 - 发送失败','2023-01-26 01:54:50','2023-09-20 09:01:23',1,1,0),
('461751c1-5794-11ee-873c-e073e73d10a7',30,'不发送','30','7e5eb37b-d5f0-df78-5edd-a5ed73879029',0,'info','','邮件发送状态 - 不发送','2023-01-26 01:55:06','2023-09-20 09:01:23',1,1,0),
('46175202-5794-11ee-873c-e073e73d10a7',1,'通知公告','1','a2ad49c5-5ed5-cf77-a0be-3792ae0e3184',0,'primary','','站内信模版的类型 - 通知公告','2023-01-28 02:35:59','2023-09-20 09:01:23',1,1,0),
('46175245-5794-11ee-873c-e073e73d10a7',2,'系统消息','2','a2ad49c5-5ed5-cf77-a0be-3792ae0e3184',0,'success','','站内信模版的类型 - 系统消息','2023-01-28 02:36:20','2023-09-20 09:01:23',1,1,0),
('4617528d-5794-11ee-873c-e073e73d10a7',10,'Vue2 Element UI 标准模版','10','e1e54b68-f540-b89e-0eb5-c0482666d86c',0,'','','','2023-04-12 16:03:55','2023-09-20 09:01:23',1,1,0),
('461752ce-5794-11ee-873c-e073e73d10a7',20,'Vue3 Element Plus 标准模版','20','e1e54b68-f540-b89e-0eb5-c0482666d86c',0,'','','','2023-04-12 16:04:08','2023-09-20 09:01:23',1,1,0),
('46175313-5794-11ee-873c-e073e73d10a7',21,'Vue3 Element Plus Schema 模版','21','e1e54b68-f540-b89e-0eb5-c0482666d86c',0,'','','','2023-04-12 16:04:26','2023-09-20 09:01:23',1,1,0),
('461767c0-5794-11ee-873c-e073e73d10a7',30,'Vue3 vben 模版','30','e1e54b68-f540-b89e-0eb5-c0482666d86c',0,'','','','2023-04-12 16:04:26','2023-09-20 09:01:23',1,1,0),
('4617686e-5794-11ee-873c-e073e73d10a7',2,'自定义','2','7e60703e-c696-22cb-74de-35a0f599aaee',0,'primary','','参数类型 - 自定义','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('461768c5-5794-11ee-873c-e073e73d10a7',1,'通知','1','686f4dae-c34e-6205-4a6d-138bd64012a6',0,'success','','通知','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('4617690c-5794-11ee-873c-e073e73d10a7',2,'公告','2','686f4dae-c34e-6205-4a6d-138bd64012a6',0,'info','','公告','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176954-5794-11ee-873c-e073e73d10a7',0,'其它','0','65bd7d58-e946-ed83-85c3-493cc0e4b9c2',0,'default','','其它操作','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('4617699c-5794-11ee-873c-e073e73d10a7',1,'查询','1','65bd7d58-e946-ed83-85c3-493cc0e4b9c2',0,'info','','查询操作','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('461769df-5794-11ee-873c-e073e73d10a7',2,'新增','2','65bd7d58-e946-ed83-85c3-493cc0e4b9c2',0,'primary','','新增操作','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176a21-5794-11ee-873c-e073e73d10a7',3,'修改','3','65bd7d58-e946-ed83-85c3-493cc0e4b9c2',0,'warning','','修改操作','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176a7e-5794-11ee-873c-e073e73d10a7',2,'女','2','dbffed9d-db1b-1f14-80e9-0feba9cc54c6',1,'success','','性别女','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176ac6-5794-11ee-873c-e073e73d10a7',4,'删除','4','65bd7d58-e946-ed83-85c3-493cc0e4b9c2',0,'danger','','删除操作','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176b0c-5794-11ee-873c-e073e73d10a7',5,'导出','5','65bd7d58-e946-ed83-85c3-493cc0e4b9c2',0,'default','','导出操作','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176b50-5794-11ee-873c-e073e73d10a7',6,'导入','6','65bd7d58-e946-ed83-85c3-493cc0e4b9c2',0,'default','','导入操作','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176b93-5794-11ee-873c-e073e73d10a7',1,'开启','0','07ad08f7-f97e-1ad6-196c-08fc54b27796',0,'primary','','开启状态','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176bd8-5794-11ee-873c-e073e73d10a7',2,'关闭','1','07ad08f7-f97e-1ad6-196c-08fc54b27796',0,'info','','关闭状态','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176c1a-5794-11ee-873c-e073e73d10a7',1,'目录','1','fe8a1cc3-bb2d-87d9-97f2-78db48e9cec8',0,'','','目录','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176cf1-5794-11ee-873c-e073e73d10a7',2,'菜单','2','fe8a1cc3-bb2d-87d9-97f2-78db48e9cec8',0,'','','菜单','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176d40-5794-11ee-873c-e073e73d10a7',3,'按钮','3','fe8a1cc3-bb2d-87d9-97f2-78db48e9cec8',0,'','','按钮','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176d83-5794-11ee-873c-e073e73d10a7',1,'内置','1','3450f5e3-2b00-51e8-1f67-0fff9bf1f435',0,'danger','','内置角色','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176dcf-5794-11ee-873c-e073e73d10a7',2,'自定义','2','3450f5e3-2b00-51e8-1f67-0fff9bf1f435',0,'primary','','自定义角色','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176f7d-5794-11ee-873c-e073e73d10a7',1,'全部数据权限','1','4bcb55e2-b37f-2ee7-c4d2-6fd16342a887',0,'','','全部数据权限','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46176fd4-5794-11ee-873c-e073e73d10a7',2,'指定部门数据权限','2','4bcb55e2-b37f-2ee7-c4d2-6fd16342a887',0,'','','指定部门数据权限','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('4617701b-5794-11ee-873c-e073e73d10a7',3,'本部门数据权限','3','4bcb55e2-b37f-2ee7-c4d2-6fd16342a887',0,'','','本部门数据权限','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46177061-5794-11ee-873c-e073e73d10a7',4,'本部门及以下数据权限','4','4bcb55e2-b37f-2ee7-c4d2-6fd16342a887',0,'','','本部门及以下数据权限','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('461770a8-5794-11ee-873c-e073e73d10a7',5,'仅本人数据权限','5','4bcb55e2-b37f-2ee7-c4d2-6fd16342a887',0,'','','仅本人数据权限','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('461770ec-5794-11ee-873c-e073e73d10a7',0,'成功','0','f66eda93-a446-b6cd-6567-ab9e5a6bf28b',0,'success','','登陆结果 - 成功','2021-01-17 22:17:36','2023-09-20 09:01:23',1,1,0),
('46177131-5794-11ee-873c-e073e73d10a7',10,'账号或密码不正确','10','f66eda93-a446-b6cd-6567-ab9e5a6bf28b',0,'primary','','登陆结果 - 账号或密码不正确','2021-01-17 22:17:54','2023-09-20 09:01:23',1,1,0),
('46177178-5794-11ee-873c-e073e73d10a7',20,'用户被禁用','20','f66eda93-a446-b6cd-6567-ab9e5a6bf28b',0,'warning','','登陆结果 - 用户被禁用','2021-01-17 22:17:54','2023-09-20 09:01:23',1,1,0),
('461771c7-5794-11ee-873c-e073e73d10a7',30,'验证码不存在','30','f66eda93-a446-b6cd-6567-ab9e5a6bf28b',0,'info','','登陆结果 - 验证码不存在','2021-01-17 22:17:54','2023-09-20 09:01:23',1,1,0),
('4617720a-5794-11ee-873c-e073e73d10a7',31,'验证码不正确','31','f66eda93-a446-b6cd-6567-ab9e5a6bf28b',0,'info','','登陆结果 - 验证码不正确','2021-01-17 22:17:54','2023-09-20 09:01:23',1,1,0),
('4617725c-5794-11ee-873c-e073e73d10a7',100,'未知异常','100','f66eda93-a446-b6cd-6567-ab9e5a6bf28b',0,'danger','','登陆结果 - 未知异常','2021-01-17 22:17:54','2023-09-20 09:01:23',1,1,0),
('461772a1-5794-11ee-873c-e073e73d10a7',1,'是','true','349af514-88aa-8b29-dcf0-0fee0f8fddf0',0,'danger','','Boolean 是否类型 - 是','2021-01-18 19:20:55','2023-09-20 09:01:23',1,1,0),
('461772e6-5794-11ee-873c-e073e73d10a7',1,'否','false','349af514-88aa-8b29-dcf0-0fee0f8fddf0',0,'info','','Boolean 是否类型 - 否','2021-01-18 19:20:55','2023-09-20 09:01:23',1,1,0),
('4617732b-5794-11ee-873c-e073e73d10a7',1,'永不超时','1','82d42cff-2b29-0152-fb4d-967943774704',0,'primary','','Redis 未设置超时的情况','2021-01-25 16:53:17','2023-09-20 09:01:23',1,1,0),
('4617736f-5794-11ee-873c-e073e73d10a7',1,'动态超时','2','82d42cff-2b29-0152-fb4d-967943774704',0,'info','','程序里动态传入超时时间，无法固定','2021-01-25 16:55:00','2023-09-20 09:01:23',1,1,0),
('461773b5-5794-11ee-873c-e073e73d10a7',3,'固定超时','3','82d42cff-2b29-0152-fb4d-967943774704',0,'success','','Redis 设置了过期时间','2021-01-25 16:55:26','2023-09-20 09:01:23',1,1,0),
('461773f8-5794-11ee-873c-e073e73d10a7',1,'单表（增删改查）','1','297aedc4-5635-11c1-c8f9-3417ef6180c8',0,'','','','2021-02-04 23:09:06','2023-09-20 09:01:23',1,1,0),
('46177462-5794-11ee-873c-e073e73d10a7',2,'树表（增删改查）','2','297aedc4-5635-11c1-c8f9-3417ef6180c8',0,'','','','2021-02-04 23:14:46','2023-09-20 09:01:23',1,1,0),
('461774a6-5794-11ee-873c-e073e73d10a7',0,'初始化中','0','f4ec6ce8-27be-accd-08e9-04b987995d44',0,'primary','','','2021-02-06 23:46:49','2023-09-20 09:01:23',1,1,0),
('461774eb-5794-11ee-873c-e073e73d10a7',0,'运行中','0','daacb260-29b2-0883-fe2f-b492c9b7f657',0,'primary','','RUNNING','2021-02-08 02:04:24','2023-09-20 09:01:23',1,1,0),
('4617752d-5794-11ee-873c-e073e73d10a7',1,'成功','1','daacb260-29b2-0883-fe2f-b492c9b7f657',0,'success','','','2021-02-08 02:06:57','2023-09-20 09:01:23',1,1,0),
('46177570-5794-11ee-873c-e073e73d10a7',2,'失败','2','daacb260-29b2-0883-fe2f-b492c9b7f657',0,'warning','','失败','2021-02-08 02:07:38','2023-09-20 09:01:23',1,1,0),
('461775b8-5794-11ee-873c-e073e73d10a7',1,'会员','1','aa41b08f-89c8-cc27-a555-d3a923942df8',0,'primary','','','2021-02-25 16:16:27','2023-09-20 09:01:23',1,1,0),
('461775fc-5794-11ee-873c-e073e73d10a7',2,'管理员','2','aa41b08f-89c8-cc27-a555-d3a923942df8',0,'success','','','2021-02-25 16:16:34','2023-09-20 09:01:23',1,1,0),
('4617763d-5794-11ee-873c-e073e73d10a7',0,'未处理','0','898b77bc-1cf0-7ff2-af14-8435c65ec980',0,'primary','','','2021-02-25 23:07:19','2023-09-20 09:01:23',1,1,0),
('46177680-5794-11ee-873c-e073e73d10a7',1,'已处理','1','898b77bc-1cf0-7ff2-af14-8435c65ec980',0,'success','','','2021-02-25 23:07:26','2023-09-20 09:01:23',1,1,0),
('461776c5-5794-11ee-873c-e073e73d10a7',2,'已忽略','2','898b77bc-1cf0-7ff2-af14-8435c65ec980',0,'danger','','','2021-02-25 23:07:34','2023-09-20 09:01:23',1,1,0),
('46177711-5794-11ee-873c-e073e73d10a7',2,'阿里云','ALIYUN','cc7734c5-456e-b0d2-2b9d-95e96bc7d252',0,'primary','','','2021-04-04 17:05:26','2023-09-20 09:01:23',1,1,0),
('46177754-5794-11ee-873c-e073e73d10a7',1,'验证码','1','e58871c4-ed05-c2d2-6eba-ea3a4f08c22e',0,'warning','','','2021-04-05 13:50:57','2023-09-20 09:01:23',1,1,0),
('46177796-5794-11ee-873c-e073e73d10a7',2,'通知','2','e58871c4-ed05-c2d2-6eba-ea3a4f08c22e',0,'primary','','','2021-04-05 13:51:08','2023-09-20 09:01:23',1,1,0),
('461777da-5794-11ee-873c-e073e73d10a7',0,'营销','3','e58871c4-ed05-c2d2-6eba-ea3a4f08c22e',0,'danger','','','2021-04-05 13:51:15','2023-09-20 09:01:23',1,1,0),
('4617781c-5794-11ee-873c-e073e73d10a7',0,'初始化','0','40f9670a-1222-060a-0d26-049e690c5331',0,'primary','','','2021-04-11 12:18:33','2023-09-20 09:01:23',1,1,0),
('4617785d-5794-11ee-873c-e073e73d10a7',1,'发送成功','10','40f9670a-1222-060a-0d26-049e690c5331',0,'success','','','2021-04-11 12:18:43','2023-09-20 09:01:23',1,1,0),
('4617789f-5794-11ee-873c-e073e73d10a7',2,'发送失败','20','40f9670a-1222-060a-0d26-049e690c5331',0,'danger','','','2021-04-11 12:18:49','2023-09-20 09:01:23',1,1,0),
('461778e1-5794-11ee-873c-e073e73d10a7',3,'不发送','30','40f9670a-1222-060a-0d26-049e690c5331',0,'info','','','2021-04-11 12:19:44','2023-09-20 09:01:23',1,1,0),
('46177925-5794-11ee-873c-e073e73d10a7',0,'等待结果','0','8f324f5c-425a-78a4-ba2b-ff66e13e922b',0,'primary','','','2021-04-11 12:27:43','2023-09-20 09:01:23',1,1,0),
('46177967-5794-11ee-873c-e073e73d10a7',1,'接收成功','10','8f324f5c-425a-78a4-ba2b-ff66e13e922b',0,'success','','','2021-04-11 12:29:25','2023-09-20 09:01:23',1,1,0),
('461779c2-5794-11ee-873c-e073e73d10a7',2,'接收失败','20','8f324f5c-425a-78a4-ba2b-ff66e13e922b',0,'danger','','','2021-04-11 12:29:31','2023-09-20 09:01:23',1,1,0),
('46177a07-5794-11ee-873c-e073e73d10a7',0,'调试(钉钉)','DEBUG_DING_TALK','cc7734c5-456e-b0d2-2b9d-95e96bc7d252',0,'info','','','2021-04-12 16:20:37','2023-09-20 09:01:23',1,1,0),
('46177a4c-5794-11ee-873c-e073e73d10a7',1,'自动生成','1','fe0d7936-165f-6b61-be4a-49ae9f22594e',0,'warning','','','2021-04-20 16:06:48','2023-09-20 09:01:23',1,1,0),
('46177a8e-5794-11ee-873c-e073e73d10a7',2,'手动编辑','2','fe0d7936-165f-6b61-be4a-49ae9f22594e',0,'primary','','','2021-04-20 16:07:14','2023-09-20 09:01:23',1,1,0),
('46177ad0-5794-11ee-873c-e073e73d10a7',1,'正常','1','f4ec6ce8-27be-accd-08e9-04b987995d44',0,'success','','正常状态','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0),
('46177b13-5794-11ee-873c-e073e73d10a7',100,'账号登录','100','6398cf43-47ad-a20d-43dd-1dbf77a9ba17',0,'primary','','账号登录','2021-10-05 16:52:02','2023-09-20 09:01:23',1,1,0),
('46177b58-5794-11ee-873c-e073e73d10a7',101,'社交登录','101','6398cf43-47ad-a20d-43dd-1dbf77a9ba17',0,'info','','社交登录','2021-10-05 16:52:17','2023-09-20 09:01:23',1,1,0),
('46177b9a-5794-11ee-873c-e073e73d10a7',200,'主动登出','200','6398cf43-47ad-a20d-43dd-1dbf77a9ba17',0,'primary','','主动登出','2021-10-05 16:52:58','2023-09-20 09:01:23',1,1,0),
('46177bdc-5794-11ee-873c-e073e73d10a7',202,'强制登出','202','6398cf43-47ad-a20d-43dd-1dbf77a9ba17',0,'danger','','强制退出','2021-10-05 16:53:41','2023-09-20 09:01:23',1,1,0),
('46177c38-5794-11ee-873c-e073e73d10a7',2,'暂停','2','f4ec6ce8-27be-accd-08e9-04b987995d44',0,'danger','','停用状态','2021-01-05 09:03:48','2023-09-20 09:01:23',1,1,0);
/*!40000 ALTER TABLE `infra_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_dict_type`
--

DROP TABLE IF EXISTS `infra_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_dict_type` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典名称',
  `type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_dict_type`
--

LOCK TABLES `infra_dict_type` WRITE;
/*!40000 ALTER TABLE `infra_dict_type` DISABLE KEYS */;
INSERT INTO `infra_dict_type` VALUES 
('07ad08f7-f97e-1ad6-196c-08fc54b27796','系统状态','common_status',0,'','2021-01-05 09:03:48','2022-02-01 08:21:28',1,1,0),
('297aedc4-5635-11c1-c8f9-3417ef6180c8','代码生成模板类型','infra_codegen_template_type',0,'','2021-02-04 23:08:06','2022-05-16 12:26:50',1,1,0),
('3450f5e3-2b00-51e8-1f67-0fff9bf1f435','角色类型','system_role_type',0,'角色类型','2022-02-16 05:01:46','2022-02-16 05:01:46',1,1,0),
('34949cd7-b2bf-57e6-07c0-a432d61da5cf','文件存储器','infra_file_storage',0,'文件存储器','2022-03-14 16:24:38','2022-03-14 16:24:38',1,1,0),
('349af514-88aa-8b29-dcf0-0fee0f8fddf0','Boolean 是否类型','infra_boolean_string',0,'boolean 转是否','2021-01-18 19:20:08','2022-02-01 08:37:10',1,1,0),
('40f9670a-1222-060a-0d26-049e690c5331','短信发送状态','system_sms_send_status',0,'','2021-04-11 12:18:03','2022-02-01 08:35:09',1,1,0),
('4bcb55e2-b37f-2ee7-c4d2-6fd16342a887','数据权限类型','system_data_scope',0,'','2021-01-05 09:03:48','2022-05-16 12:29:32',1,1,0),
('6398cf43-47ad-a20d-43dd-1dbf77a9ba17','登陆日志的类型','system_login_type',0,'登陆日志的类型','2021-10-05 16:50:46','2022-02-01 08:35:56',1,1,0),
('65bd7d58-e946-ed83-85c3-493cc0e4b9c2','操作类型','system_operate_type',0,'','2021-01-05 09:03:48','2022-02-16 01:32:21',1,1,0),
('686f4dae-c34e-6205-4a6d-138bd64012a6','通知类型','system_notice_type',0,'','2021-01-05 09:03:48','2022-02-01 08:35:26',1,1,0),
('7e5eb37b-d5f0-df78-5edd-a5ed73879029','邮件发送状态','system_mail_send_status',0,'邮件发送状态','2023-01-26 01:53:13','2023-01-26 01:53:13',1,1,0),
('7e60703e-c696-22cb-74de-35a0f599aaee','参数类型','infra_config_type',0,'','2021-01-05 09:03:48','2022-02-01 08:36:54',1,1,0),
('82d42cff-2b29-0152-fb4d-967943774704','Redis 超时类型','infra_redis_timeout_type',0,'RedisKeyDefine.TimeoutTypeEnum','2021-01-25 16:52:50','2022-02-01 08:50:29',1,1,0),
('83100158-f4c4-59e1-19d8-6851fd0a4d07','终端','terminal',0,'终端','2022-12-10 02:50:50','2022-12-10 02:53:11',1,1,0),
('898b77bc-1cf0-7ff2-af14-8435c65ec980','API 异常数据的处理状态','infra_api_error_log_process_status',0,'','2021-02-25 23:07:01','2022-02-01 08:50:53',1,1,0),
('8f324f5c-425a-78a4-ba2b-ff66e13e922b','短信接收状态','system_sms_receive_status',0,'','2021-04-11 12:27:14','2022-02-01 08:35:14',1,1,0),
('9a353673-29a0-dbbc-2628-ec406ed80618','OAuth 2.0 授权类型','system_oauth2_grant_type',0,'OAuth 2.0 授权类型（模式）','2022-05-11 16:20:52','2022-05-11 08:25:49',1,1,0),
('a2ad49c5-5ed5-cf77-a0be-3792ae0e3184','站内信模版的类型','system_notify_template_type',0,'站内信模版的类型','2023-01-28 02:35:10','2023-01-28 02:35:10',1,1,0),
('aa41b08f-89c8-cc27-a555-d3a923942df8','用户类型','user_type',0,'','2021-02-25 16:15:51','2021-02-25 16:15:51',1,1,0),
('c0fcc1e7-02a0-635b-0702-2c96f6482dae','代码生成的场景枚举','infra_codegen_scene',0,'代码生成的场景枚举','2022-02-02 05:14:45','2022-03-10 08:33:46',1,1,0),
('cc7734c5-456e-b0d2-2b9d-95e96bc7d252','短信渠道编码','system_sms_channel_code',0,'','2021-04-04 17:04:50','2022-02-15 18:09:08',1,1,0),
('daacb260-29b2-0883-fe2f-b492c9b7f657','定时任务日志状态','infra_job_log_status',0,'','2021-02-08 02:03:51','2022-02-01 08:50:43',1,1,0),
('dbffed9d-db1b-1f14-80e9-0feba9cc54c6','用户性别','system_user_sex',0,'','2021-01-05 09:03:48','2022-05-16 12:29:32',1,1,0),
('e1e54b68-f540-b89e-0eb5-c0482666d86c','代码生成的前端类型','infra_codegen_front_type',0,'','2023-04-12 15:57:52','2023-04-12 15:57:52',1,1,0),
('e58871c4-ed05-c2d2-6eba-ea3a4f08c22e','短信模板的类型','system_sms_template_type',0,'','2021-04-05 13:50:43','2022-02-01 08:35:06',1,1,0),
('f4ec6ce8-27be-accd-08e9-04b987995d44','定时任务状态','infra_job_status',0,'','2021-02-06 23:44:16','2022-02-01 08:51:11',1,1,0),
('f66eda93-a446-b6cd-6567-ab9e5a6bf28b','登陆结果','system_login_result',0,'登陆结果','2021-01-17 22:17:11','2022-02-01 08:36:00',1,1,0),
('fe0d7936-165f-6b61-be4a-49ae9f22594e','错误码的类型','system_error_code_type',0,'','2021-04-20 16:06:30','2022-02-01 08:36:49',1,1,0),
('fe8a1cc3-bb2d-87d9-97f2-78db48e9cec8','菜单类型','system_menu_type',0,'','2021-01-05 09:03:48','2022-05-16 12:29:32',1,1,0);
/*!40000 ALTER TABLE `infra_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_file`
--

DROP TABLE IF EXISTS `infra_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件编号',
  `config_id` bigint DEFAULT NULL COMMENT '配置编号',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件名',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件路径',
  `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件 URL',
  `type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件类型',
  `size` int NOT NULL COMMENT '文件大小',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_file`
--

LOCK TABLES `infra_file` WRITE;
/*!40000 ALTER TABLE `infra_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `infra_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_file_config`
--

DROP TABLE IF EXISTS `infra_file_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_file_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置名',
  `storage` tinyint NOT NULL COMMENT '存储器',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `master` bit(1) NOT NULL COMMENT '是否为主配置',
  `config` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '存储配置',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `infra_file_content`
--

DROP TABLE IF EXISTS `infra_file_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_file_content` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `config_id` bigint NOT NULL COMMENT '配置编号',
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `content` mediumblob NOT NULL COMMENT '文件内容',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_file_content`
--
--
-- Table structure for table `infra_interface`
--

DROP TABLE IF EXISTS `infra_interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_interface` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '接口名',
  `comment` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
  `method` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '调用方法',
  `authorize` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限',
  `is_transaction` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启事务',
  `module_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '接口模块',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  `input_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '入参类型',
  `input_extend_class` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '入参继承类',
  `output_type` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '出参类型',
  `output_extend_class` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '出参继承类',
  `input_servlet` bit(1) NOT NULL DEFAULT b'0' COMMENT '入参传HttpServletResponse',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_interface`
--

LOCK TABLES `infra_interface` WRITE;
/*!40000 ALTER TABLE `infra_interface` DISABLE KEYS */;
INSERT INTO `infra_interface` VALUES 
('07208668-8cb2-49b2-ae49-b1791e0ed882','delete','删除字典类型','delete','infra:dict:delete',_binary '\0','acdb3e01-5e3d-476b-bf2d-86619fbd40ae','2023-09-22 03:40:52','2023-09-22 08:48:24',1,1,0,'param','','param','',_binary '\0'),
('1f62ad16-17af-4261-a6bf-370b2a34160b','delete','删除部门','delete','system:dept:delete',_binary '\0','4f94136e-cdfd-4cc6-9b05-f160c3196879','2023-09-05 10:44:43','2023-09-05 10:44:43',1,1,0,'param','','param','',_binary '\0'),
('241eff10-4fc6-4b3c-aae4-0a55af704951','list-all-simple','获得全部字典类型列表','get','',_binary '\0','acdb3e01-5e3d-476b-bf2d-86619fbd40ae','2023-09-22 06:36:39','2023-09-22 06:36:39',1,1,0,'void','','VOClassList','DictTypeBase',_binary '\0'),
('381ef119-7a67-4787-b6b3-be578609729d','get','查询字典数据详细','get','infra:dict:query',_binary '\0','82ef8482-f1b9-4a83-bd32-e2292cca653e','2023-09-21 08:48:57','2023-09-22 08:29:06',1,1,0,'param','','VOClass','DictDataBase',_binary '\0'),
('3b52c661-4df5-415a-a00f-56142feb6369','list-all-simple','获得全部字典数据列表','get','',_binary '\0','82ef8482-f1b9-4a83-bd32-e2292cca653e','2023-09-21 08:14:53','2023-09-21 08:14:53',1,1,0,'void','','VOClassList','DictDataBase',_binary '\0'),
('51f7b230-149c-4689-b527-f37a9649befd','list','获取部门列表','get','system:dept:query',_binary '\0','4f94136e-cdfd-4cc6-9b05-f160c3196879','2023-09-05 10:53:07','2023-09-05 10:53:07',1,1,0,'VOClass','','VOClassList','DeptBase',_binary '\0'),
('54166b46-e467-47b6-bc89-fedbd50fa55e','update','修改字典数据','put','infra:dict:update',_binary '\0','82ef8482-f1b9-4a83-bd32-e2292cca653e','2023-09-21 07:52:09','2023-09-22 08:29:43',1,1,0,'VOClass','DictDataBase','param','',_binary '\0'),
('5ea12bb8-b40f-40ca-8f36-4b6f7885a28c','create','新增字典数据','post','infra:dict:create',_binary '\0','82ef8482-f1b9-4a83-bd32-e2292cca653e','2023-09-20 10:16:24','2023-09-22 08:34:02',1,1,0,'VOClass','DictDataBase','param','',_binary '\0'),
('6788bbe5-85ff-4536-b16b-7ae243b0e59e','create','创建字典类型','post','infra:dict:create',_binary '\0','acdb3e01-5e3d-476b-bf2d-86619fbd40ae','2023-09-22 03:18:57','2023-09-22 08:48:30',1,1,0,'VOClass','DictTypeBase','param','',_binary '\0'),
('7dfa7b1f-9760-4789-a3bf-edef86482b68','page','获得字典类型的分页列表','get','infra:dict:query',_binary '\0','82ef8482-f1b9-4a83-bd32-e2292cca653e','2023-09-22 02:41:25','2023-09-22 08:41:45',1,1,0,'VOClass','PageParam','VOClassPage','DictDataBase',_binary '\0'),
('80affdbd-854e-4669-8219-ce1e013118be','list-all-simple','获取部门精简信息列表','get','',_binary '\0','4f94136e-cdfd-4cc6-9b05-f160c3196879','2023-09-06 10:01:26','2023-09-06 10:01:26',1,1,0,'void','','VOClassList','DeptBase',_binary '\0'),
('8a581520-2d75-491b-9008-36d901721574','update','更新部门','put','system:dept:update',_binary '\0','4f94136e-cdfd-4cc6-9b05-f160c3196879','2023-09-05 10:08:40','2023-09-05 10:08:40',1,1,0,'VOClass','DeptBase','param','',_binary '\0'),
('8d8504f3-2039-4dc5-973e-7d6f33813c37','delete','删除字典数据','delete','infra:dict:delete',_binary '\0','82ef8482-f1b9-4a83-bd32-e2292cca653e','2023-09-21 07:58:33','2023-09-22 08:43:20',1,1,0,'param','','param','',_binary '\0'),
('b4c2297f-b314-4f47-bf3c-01137d6ae1b1','export','导出数据类型','get','system:dict:query',_binary '\0','acdb3e01-5e3d-476b-bf2d-86619fbd40ae','2023-09-25 07:52:44','2023-09-25 07:52:44',1,1,0,'VOClass','','void','',_binary ''),
('bcf640e5-f694-4846-b544-0103b0acab6b','update','修改字典类型','put','infra:dict:update',_binary '\0','acdb3e01-5e3d-476b-bf2d-86619fbd40ae','2023-09-22 03:38:34','2023-09-22 08:48:37',1,1,0,'VOClass','DictTypeBase','param','',_binary '\0'),
('d1404186-21aa-4435-9a5a-ce1f5a23c07a','page','获得字典类型的分页列表','get','infra:dict:query',_binary '\0','acdb3e01-5e3d-476b-bf2d-86619fbd40ae','2023-09-22 06:13:42','2023-09-22 08:48:44',1,1,0,'VOClass','PageParam','VOClassPage','DictTypeBase',_binary '\0'),
('d97f8b54-8ddb-4012-bf56-bdccf5349447','get','查询字典类型详细','get','infra:dict:query',_binary '\0','acdb3e01-5e3d-476b-bf2d-86619fbd40ae','2023-09-22 06:34:12','2023-09-22 08:48:50',1,1,0,'param','','VOClass','DictTypeBase',_binary '\0'),
('dea9ac66-367e-4734-af66-509926cc8a61','create','创建部门','post','system:dept:create',_binary '\0','4f94136e-cdfd-4cc6-9b05-f160c3196879','2023-09-05 10:01:55','2023-09-05 10:01:55',1,1,0,'VOClass','DeptBase','param','',_binary '\0'),
('f5b2fbfa-1844-4833-8482-09959511caa6','export','导出字典数据','get','system:dict:export',_binary '\0','82ef8482-f1b9-4a83-bd32-e2292cca653e','2023-09-21 09:02:41','2023-09-21 09:02:41',1,1,0,'param','','void','',_binary '\0');
/*!40000 ALTER TABLE `infra_interface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_interface_module`
--

DROP TABLE IF EXISTS `infra_interface_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_interface_module` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模块名称',
  `comment` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模块描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  `parent_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '父ID',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '模块类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口模块';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_interface_module`
--

LOCK TABLES `infra_interface_module` WRITE;
/*!40000 ALTER TABLE `infra_interface_module` DISABLE KEYS */;
INSERT INTO `infra_interface_module` VALUES 
('0fde120d-ce5d-48e3-ad64-285c8ef82839','data','基础数据','2023-09-20 09:08:42','2023-09-20 09:08:42',1,1,0,'f2453ed8-4697-45e7-a5fd-ed87ca78f11b',0),
('4f94136e-cdfd-4cc6-9b05-f160c3196879','dept','部门管理','2023-09-05 09:34:54','2023-09-05 09:45:21',1,1,0,'93fdb50f-3a13-4df0-8cff-87d8cbd4952a',1),
('5aa5cea6-8dbe-47b3-8cfa-02c10c39bc12','admin','客户端','2023-07-21 17:11:33','2023-07-21 17:11:33',1,1,0,'',0),
('6c6fccc3-4e2e-4763-a0dd-198c4d75b797','codegen','代码生成','2023-08-25 01:42:37','2023-08-25 01:42:37',1,1,0,'f2453ed8-4697-45e7-a5fd-ed87ca78f11b',0),
('82ef8482-f1b9-4a83-bd32-e2292cca653e','dict-data','字典数据','2023-09-20 09:09:01','2023-09-20 09:09:01',1,1,0,'0fde120d-ce5d-48e3-ad64-285c8ef82839',1),
('93fdb50f-3a13-4df0-8cff-87d8cbd4952a','dept','部门','2023-08-25 01:42:37','2023-08-25 01:42:37',1,1,0,'d6f6f5fc-0c97-4623-97c5-2cb2d3dad0ca',0),
('a7de2f1c-278b-4b94-9a74-1bd708f289ed','app','移动端','2023-07-21 17:11:43','2023-07-21 17:11:43',1,1,0,'',0),
('acdb3e01-5e3d-476b-bf2d-86619fbd40ae','dict-type','字典类型','2023-09-22 03:16:34','2023-09-22 03:16:34',1,1,0,'0fde120d-ce5d-48e3-ad64-285c8ef82839',1),
('d6f6f5fc-0c97-4623-97c5-2cb2d3dad0ca','system','系统管理','2023-07-21 17:11:57','2023-07-21 17:11:57',1,1,0,'5aa5cea6-8dbe-47b3-8cfa-02c10c39bc12',0),
('ee94e964-4126-4c98-8536-ceb3076d9a81','user','用户','2023-08-25 01:42:37','2023-08-25 01:42:37',1,1,0,'d6f6f5fc-0c97-4623-97c5-2cb2d3dad0ca',0),
('f2453ed8-4697-45e7-a5fd-ed87ca78f11b','infra','基础设施','2023-08-25 01:42:37','2023-08-25 01:42:37',1,1,0,'5aa5cea6-8dbe-47b3-8cfa-02c10c39bc12',0);
/*!40000 ALTER TABLE `infra_interface_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_interface_param`
--

DROP TABLE IF EXISTS `infra_interface_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_interface_param` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键ID',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名',
  `comment` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
  `is_list` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是list',
  `variable_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数类型',
  `related_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '关联字段id',
  `related_type` tinyint NOT NULL DEFAULT '0' COMMENT '关联字段类型 1 column_id 2 VO_id 3 SubClass_id',
  `example` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '示例',
  `required` bit(1) NOT NULL DEFAULT b'0' COMMENT '前端必传',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  `parent_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '父id',
  `parent_type` tinyint NOT NULL DEFAULT '0' COMMENT '父类类型 0 接口 1 子类',
  `inout_type` tinyint NOT NULL DEFAULT '0' COMMENT '出参入参 0 入参 1出参',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_infra_interface_param` (`name`,`parent_id`,`inout_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口参数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_interface_param`
--

LOCK TABLES `infra_interface_param` WRITE;
/*!40000 ALTER TABLE `infra_interface_param` DISABLE KEYS */;
INSERT INTO `infra_interface_param` VALUES 
('08f2084f-06b2-4402-8f41-301cfd7e8348','sort','显示顺序',_binary '\0','Integer','490abd70-d214-4da0-8e68-db67d5d77f41',1,'1024',_binary '\0','2023-09-07 02:36:36','2023-09-07 02:36:36',1,1,0,'b9a8a71a-05a9-4996-8602-945b0b8a5ab7',0,0),
('0aed5e0f-3d35-4324-93c9-f185956cec6d','outParam','',_binary '\0','Long','',0,'',_binary '\0','2023-09-20 09:46:46','2023-09-20 09:46:46',1,1,0,'d9ae260e-87a9-44e4-a5ef-2df3c47f9f44',0,1),
('0fcebd9e-f558-44bc-a70a-c6eeb2b03cdc','id','字典类型编号',_binary '\0','UUID','',0,'1024',_binary '','2023-09-22 06:36:39','2023-09-22 06:36:39',1,1,0,'241eff10-4fc6-4b3c-aae4-0a55af704951',0,1),
('10254a3d-0083-4bd9-b565-a2482ce98f9a','createTime','创建时间',_binary '\0','LocalDateTime','',0,'时间戳格式',_binary '','2023-09-22 02:41:25','2023-09-22 02:41:25',1,1,0,'7dfa7b1f-9760-4789-a3bf-edef86482b68',0,1),
('12141b39-0acd-4ab8-9fe7-53a055e79c91','suub','',_binary '\0','Subclass','d9715a8d-b0d4-4869-b88a-297bd13b4d2e',3,'',_binary '\0','2023-09-07 02:36:36','2023-09-07 02:36:36',1,1,0,'b9a8a71a-05a9-4996-8602-945b0b8a5ab7',0,0),
('12c6214c-b914-4a49-9590-cc1b08126147','createTime','创建时间',_binary '\0','LocalDateTime[]','',0,'',_binary '\0','2023-09-25 07:52:45','2023-09-25 07:52:45',1,1,0,'b4c2297f-b314-4f47-bf3c-01137d6ae1b1',0,0),
('138f59ee-d352-4228-a28f-6b29bf3b2920','id','字典类型编号',_binary '\0','UUID','',0,'1024',_binary '','2023-09-22 06:34:12','2023-09-22 06:34:12',1,1,0,'d97f8b54-8ddb-4012-bf56-bdccf5349447',0,1),
('14873726-51ce-4f21-b181-7fd9589b3e84','status','展示状态',_binary '\0','Integer','',0,'1',_binary '\0','2023-09-22 02:41:25','2023-09-22 02:41:25',1,1,0,'7dfa7b1f-9760-4789-a3bf-edef86482b68',0,0),
('1868a7b5-af89-459d-a7df-53a761561680','createTime','创建时间',_binary '\0','LocalDateTime[]','',0,'',_binary '\0','2023-09-22 06:13:42','2023-09-22 06:13:42',1,1,0,'d1404186-21aa-4435-9a5a-ce1f5a23c07a',0,0),
('1923c480-1b54-42c5-bab7-205c12f7e3a4','name','部门名称',_binary '\0','String','',0,'芋道',_binary '','2023-09-06 09:31:12','2023-09-06 09:31:12',1,1,0,'0c71b317-f2f8-46ea-acd5-93a38f7564ff',0,1),
('1d4e463d-886f-4cde-b4ab-26e78e5e958a','status','展示状态',_binary '\0','Integer','',0,'',_binary '\0','2023-09-25 05:58:06','2023-09-25 05:58:06',1,1,0,'8288383b-fe65-4002-8f26-7c34e47d0a8f',0,0),
('1f718996-b65d-402e-924a-8fe84297edb9','outParam','',_binary '\0','Boolean','',0,'',_binary '\0','2023-09-22 03:40:52','2023-09-22 03:40:52',1,1,0,'07208668-8cb2-49b2-ae49-b1791e0ed882',0,1),
('20592213-ea03-46bc-a067-154083b95537','label','字典标签',_binary '\0','String','',0,'芋道',_binary '\0','2023-09-22 02:41:25','2023-09-22 02:41:25',1,1,0,'7dfa7b1f-9760-4789-a3bf-edef86482b68',0,0),
('266d956a-f4ab-4e6c-82e8-f36c4546f634','name','字典类型名称',_binary '\0','String','',0,'',_binary '\0','2023-09-25 05:58:06','2023-09-25 05:58:06',1,1,0,'8288383b-fe65-4002-8f26-7c34e47d0a8f',0,0),
('2ccd85ea-1d13-4be4-a9f0-a8cb1ac0ea6d','status','展示状态,参见 CommonStatusEnum 枚举类',_binary '\0','Integer','',0,'1',_binary '\0','2023-09-05 10:53:07','2023-09-05 10:53:07',1,1,0,'51f7b230-149c-4689-b527-f37a9649befd',0,0),
('349f4f74-febf-4f0e-9ea3-f12cd75e60f8','name','字典类型名称,模糊匹配',_binary '\0','String','',0,'芋道',_binary '\0','2023-09-25 07:52:45','2023-09-25 07:52:45',1,1,0,'b4c2297f-b314-4f47-bf3c-01137d6ae1b1',0,0),
('36a00e65-f4ab-499b-b8da-0039114cee81','id','',_binary '\0','UUID','',0,'2322q',_binary '','2023-09-21 08:37:51','2023-09-21 08:37:51',1,1,0,'24d0114e-6abc-4d73-94fb-e4be70fdec20',0,0),
('376cbc34-fcf4-44e3-b8eb-203eff51ae77','table_id','表编号',_binary '\0','UUID','55504728-ac74-45a5-8361-df6ed669b34d',1,'2342533242',_binary '\0','2023-09-07 02:03:54','2023-09-07 02:03:54',1,1,0,'701e2c90-fbf4-48b5-9286-e6dc38357a6d',1,0),
('3e7d246c-ff6f-4107-9ae2-dd3db3538c20','code','岗位编码',_binary '\0','String','d7267b4d-32bc-4308-b7b6-33460b05b392',1,'yudao',_binary '\0','2023-09-07 02:13:26','2023-09-07 02:13:26',1,1,0,'a81751ff-a3d5-4b28-9810-f98b347b3fe5',0,0),
('3ef1d988-928e-44d6-aa97-46cf99af08da','id','部门编号',_binary '\0','Long','',0,'1024',_binary '','2023-09-05 10:53:08','2023-09-05 10:53:08',1,1,0,'51f7b230-149c-4689-b527-f37a9649befd',0,1),
('44d146f8-63da-430b-b882-c70428cf2ca7','id','部门编号',_binary '\0','Long','',0,'1024',_binary '','2023-09-06 10:01:26','2023-09-06 10:01:26',1,1,0,'80affdbd-854e-4669-8219-ce1e013118be',0,1),
('49f09188-1690-439d-8645-1e4d228cbca9','id','字典数据编号',_binary '\0','UUID','',0,'102321',_binary '','2023-09-21 08:48:57','2023-09-21 08:48:57',1,1,0,'381ef119-7a67-4787-b6b3-be578609729d',0,1),
('4a11577e-e937-412b-940d-8e2b82e509ef','id','字典类型编号',_binary '\0','UUID','',0,'1023',_binary '','2023-09-22 06:13:42','2023-09-22 06:13:42',1,1,0,'d1404186-21aa-4435-9a5a-ce1f5a23c07a',0,1),
('4aed5026-25ff-4372-a4a3-180a9cd53509','createTime','创建时间',_binary '\0','LocalDateTime','',0,'时间戳格式',_binary '','2023-09-22 06:13:42','2023-09-22 06:13:42',1,1,0,'d1404186-21aa-4435-9a5a-ce1f5a23c07a',0,1),
('4b273837-32ed-45a4-9658-f258ca03128a','user_id','用户ID',_binary '\0','Long','3666eec6-11b1-4249-b7a3-6aac00c7eb6d',1,'0',_binary '\0','2023-09-07 02:36:36','2023-09-07 02:36:36',1,1,0,'b9a8a71a-05a9-4996-8602-945b0b8a5ab7',0,0),
('4cb5b616-db29-48b0-9b1d-d52dfdab94e3','subin','',_binary '\0','Subclass','2eb11db9-f547-47e0-93d8-27130a3dad86',3,'',_binary '\0','2023-09-07 02:09:32','2023-09-07 02:09:32',1,1,0,'b79c2413-99ee-4f37-ae76-4f1edcd9dcbc',0,0),
('4f17956f-9cb4-4d63-8579-de4d3805f889','comment','模块描述',_binary '\0','String','cb4e8d8c-acc9-4edb-981b-3ff48c41c2aa',1,'用户',_binary '\0','2023-09-07 02:36:36','2023-09-07 02:36:36',1,1,0,'d9715a8d-b0d4-4869-b88a-297bd13b4d2e',1,0),
('53384a00-4d77-40db-bc00-5748f73c1455','status','状态,参见 CommonStatusEnum 枚举类',_binary '\0','Integer','',0,'1',_binary '','2023-09-05 10:53:08','2023-09-05 10:53:08',1,1,0,'51f7b230-149c-4689-b527-f37a9649befd',0,1),
('5586fac7-71af-4d75-9d14-8b1efad1e15b','DictDataExportInput','字典类型导出 Request',_binary '\0','Subclass','1f1e6034-97bb-4b49-b28e-ad9a7a900978',3,'',_binary '\0','2023-09-21 09:02:41','2023-09-21 09:02:41',1,1,0,'f5b2fbfa-1844-4833-8482-09959511caa6',0,0),
('56d471b6-d823-425e-89b0-16de2188b823','type','字典类型',_binary '\0','String','',0,'',_binary '\0','2023-09-25 05:58:06','2023-09-25 05:58:06',1,1,0,'8288383b-fe65-4002-8f26-7c34e47d0a8f',0,0),
('5750b169-a4a2-4353-b486-73429997a8d6','status','展示状态',_binary '\0','Integer','',0,'1',_binary '\0','2023-09-25 07:52:45','2023-09-25 07:52:45',1,1,0,'b4c2297f-b314-4f47-bf3c-01137d6ae1b1',0,0),
('5dfcca81-d56d-46b9-ab29-38e257ea5d4c','name','部门名称',_binary '\0','','',0,'芋道',_binary '','2023-09-06 09:49:44','2023-09-06 09:49:44',1,1,0,'cafa26f4-04ec-4689-a0b8-a779d8749ab9',0,1),
('632f93df-23f1-4a1f-9ae7-233848dbed59','id','编号',_binary '\0','UUID','',0,'434543',_binary '','2023-09-22 06:34:12','2023-09-22 06:34:12',1,1,0,'d97f8b54-8ddb-4012-bf56-bdccf5349447',0,0),
('64d03582-9ad9-48e3-a996-b670a49dd14c','status','状态',_binary '\0','Integer','',0,'1',_binary '\0','2023-09-21 09:02:41','2023-09-21 09:02:41',1,1,0,'1f1e6034-97bb-4b49-b28e-ad9a7a900978',1,0),
('6ad1b33d-8e62-41db-8fd2-5e6b2f417503','dictType','字典类型,模糊匹配',_binary '\0','String','',0,'sys_common_sex',_binary '\0','2023-09-22 02:41:25','2023-09-22 02:41:25',1,1,0,'7dfa7b1f-9760-4789-a3bf-edef86482b68',0,0),
('6d1b7f0c-424a-40a9-9671-9146786525cc','outParam','',_binary '\0','String','',0,'',_binary '\0','2023-09-20 09:58:47','2023-09-21 10:37:57',1,1,0,'5ea12bb8-b40f-40ca-8f36-4b6f7885a28c',0,1),
('6f2e699c-5b2e-4fc3-b24f-882f6c8bef7e','sub1','',_binary '\0','Subclass','de32063a-970f-42c8-b877-bc5cfaa36391',3,'',_binary '\0','2023-09-07 02:13:26','2023-09-07 02:13:26',1,1,0,'a81751ff-a3d5-4b28-9810-f98b347b3fe5',0,0),
('737df1b1-4898-41ce-bdfd-31885cc64db8','id','部门编号',_binary '\0','Long','',0,'1024',_binary '','2023-09-05 10:44:43','2023-09-05 10:44:43',1,1,0,'1f62ad16-17af-4261-a6bf-370b2a34160b',0,0),
('73c63c6f-10da-4a89-bb11-e2e232a2a580','type','字典类型',_binary '\0','String','',0,'',_binary '','2023-09-22 06:13:42','2023-09-22 06:13:42',1,1,0,'d1404186-21aa-4435-9a5a-ce1f5a23c07a',0,1),
('7836867e-521f-43a1-bb4d-75161714a99f','response','',_binary '\0','HttpServletResponse','',0,'',_binary '\0','2023-09-21 09:02:41','2023-09-21 09:02:41',1,1,0,'f5b2fbfa-1844-4833-8482-09959511caa6',0,0),
('78d7fa40-9541-49e0-9fc5-71a8f57333ac','create_time','创建时间',_binary '\0','LocalDateTime','a920d5d2-012a-4e9c-9a58-1e82e247b903',1,'',_binary '\0','2023-09-07 02:13:26','2023-09-07 02:13:26',1,1,0,'a81751ff-a3d5-4b28-9810-f98b347b3fe5',0,0),
('7a023590-629e-4470-949d-68a10b610326','type','字典类型,模糊匹配',_binary '\0','String','',0,'sys_common_sex',_binary '\0','2023-09-25 07:52:45','2023-09-25 07:52:45',1,1,0,'b4c2297f-b314-4f47-bf3c-01137d6ae1b1',0,0),
('7d5cd0bd-93bc-4862-92af-1748830a9c90','label','字典标签',_binary '\0','String','',0,'芋道',_binary '\0','2023-09-21 09:02:41','2023-09-21 09:02:41',1,1,0,'1f1e6034-97bb-4b49-b28e-ad9a7a900978',1,0),
('7f903563-e28c-4913-a0f8-f1ef463e1eb5','id','编号',_binary '\0','UUID','',0,'123423',_binary '\0','2023-09-21 07:58:33','2023-09-21 07:58:33',1,1,0,'8d8504f3-2039-4dc5-973e-7d6f33813c37',0,0),
('82a95905-662b-4595-a98b-175445a73849','dictType','字典类型',_binary '\0','String','',0,'sys_common_sex',_binary '\0','2023-09-21 09:02:41','2023-09-21 09:02:41',1,1,0,'1f1e6034-97bb-4b49-b28e-ad9a7a900978',1,0),
('832a63e5-b8c8-40f2-abc4-0b37fff8c586','outParam','',_binary '\0','Boolean','',0,'',_binary '\0','2023-09-05 10:08:40','2023-09-05 10:08:40',1,1,0,'8a581520-2d75-491b-9008-36d901721574',0,1),
('845ce245-977c-4903-bf3e-001c76e394c9','parentId','父部门 ID',_binary '\0','','',0,'1024',_binary '','2023-09-06 09:45:06','2023-09-06 09:45:06',1,1,0,'14256ebd-dc82-449d-a090-28c1aefb3ab0',0,1),
('8bcf9103-b4b7-4cda-89bf-72e49889ae65','outParam','',_binary '\0','Boolean','',0,'',_binary '\0','2023-09-22 03:38:34','2023-09-22 03:38:34',1,1,0,'bcf640e5-f694-4846-b544-0103b0acab6b',0,1),
('939ca6ce-1e81-4e22-9646-5b4504338730','createTime','创建时间',_binary '\0','LocalDateTime','',0,'时间戳格式',_binary '','2023-09-21 08:48:57','2023-09-21 08:48:57',1,1,0,'381ef119-7a67-4787-b6b3-be578609729d',0,1),
('94a46505-5322-482b-ae8c-72474a1c3657','createTime','创建时间',_binary '\0','LocalDateTime[]','',0,'',_binary '\0','2023-09-25 05:58:06','2023-09-25 05:58:06',1,1,0,'8288383b-fe65-4002-8f26-7c34e47d0a8f',0,0),
('94d8e222-ff62-45c0-9def-678799b2844c','remark','备注',_binary '\0','String','2f921882-be0c-4dd0-b0ee-8f6f2c707c31',1,'备注',_binary '\0','2023-09-07 02:09:32','2023-09-07 02:09:32',1,1,0,'b79c2413-99ee-4f37-ae76-4f1edcd9dcbc',0,0),
('97d71661-3c0e-4d9a-9ad8-ec49e1a779b0','id','编号',_binary '\0','UUID','',0,'10243343',_binary '','2023-09-21 08:48:57','2023-09-21 08:48:57',1,1,0,'381ef119-7a67-4787-b6b3-be578609729d',0,0),
('9aa8fe0a-8b40-435b-addd-597d5c193d19','id','主键ID',_binary '\0','Long','019bbe65-beeb-457c-b3d1-9ff15b5133fd',1,'',_binary '\0','2023-09-07 02:36:36','2023-09-07 02:36:36',1,1,0,'d9715a8d-b0d4-4869-b88a-297bd13b4d2e',1,0),
('9f3c8858-0610-41d5-856d-6627e98267b6','parentId','父部门 ID',_binary '\0','','',0,'1025',_binary '','2023-09-06 09:49:44','2023-09-06 09:49:44',1,1,0,'cafa26f4-04ec-4689-a0b8-a779d8749ab9',0,1),
('a5156b48-3bd6-41d9-ab44-235875ff02ec','dictType','字典类型',_binary '\0','String','',0,'system_user_sex',_binary '','2023-09-22 10:05:09','2023-09-22 10:05:09',1,1,0,'5ea12bb8-b40f-40ca-8f36-4b6f7885a28c',0,0),
('a8f9f27d-8e0a-4954-a9a0-fc1bbeaa8800','id','部门编号',_binary '\0','Long','',0,'1024',_binary '','2023-09-06 09:31:12','2023-09-06 09:31:12',1,1,0,'0c71b317-f2f8-46ea-acd5-93a38f7564ff',0,1),
('aea40a07-1be5-4fba-ad3e-b616e05c2f50','id','字典类型编号',_binary '\0','UUID','',0,'1024',_binary '\0','2023-09-22 03:38:34','2023-09-22 03:38:34',1,1,0,'bcf640e5-f694-4846-b544-0103b0acab6b',0,0),
('b5ff4618-d504-470f-94af-9871d7d3fae2','name','部门名称',_binary '\0','','',0,'芋道',_binary '','2023-09-06 09:45:06','2023-09-06 09:45:06',1,1,0,'14256ebd-dc82-449d-a090-28c1aefb3ab0',0,1),
('b91102e7-2efc-4bce-8668-d63190b3077e','createTime','创建时间',_binary '\0','LocalDateTime','',0,'时间戳格式',_binary '','2023-09-22 06:34:12','2023-09-22 06:34:12',1,1,0,'d97f8b54-8ddb-4012-bf56-bdccf5349447',0,1),
('b9a02979-f385-4ad3-828e-23eb73d84fc3','id','字典数据编号',_binary '\0','UUID','',0,'234332',_binary '','2023-09-22 02:41:25','2023-09-22 02:41:25',1,1,0,'7dfa7b1f-9760-4789-a3bf-edef86482b68',0,1),
('bd7aeecf-4f9c-4d72-88f5-73e19c518509','user_idd','用户ID',_binary '\0','Long','b8ec1692-9c44-4d19-843c-e33b49f0869e',1,'',_binary '\0','2023-09-07 03:47:23','2023-09-08 02:49:52',1,1,0,'bdb10e68-4599-488a-b652-4c9db3776ce5',0,0),
('bfc3d567-191b-4530-9efd-c0a58c64d791','outParam','',_binary '\0','Boolean','',0,'',_binary '\0','2023-09-21 07:58:33','2023-09-21 07:58:33',1,1,0,'8d8504f3-2039-4dc5-973e-7d6f33813c37',0,1),
('c7fe2fff-d77d-4f03-9d9b-6009595b4af6','id','编号',_binary '\0','UUID','',0,'1024',_binary '','2023-09-22 03:40:52','2023-09-22 03:40:52',1,1,0,'07208668-8cb2-49b2-ae49-b1791e0ed882',0,0),
('c96f845f-7beb-40c0-a04b-5123a2ae673d','name','部门名称,模糊匹配',_binary '\0','String','',0,'芋道',_binary '\0','2023-09-05 10:53:07','2023-09-05 10:53:07',1,1,0,'51f7b230-149c-4689-b527-f37a9649befd',0,0),
('ca708f33-4db1-4f83-ad27-7852d3cddf5e','parentId','父部门 ID',_binary '\0','Long','',0,'1025',_binary '','2023-09-06 09:31:13','2023-09-06 09:31:13',1,1,0,'0c71b317-f2f8-46ea-acd5-93a38f7564ff',0,1),
('ccbed7ff-de18-4729-9a26-24d7dea2d6b5','status','展示状态',_binary '\0','Integer','',0,'1',_binary '\0','2023-09-22 06:13:42','2023-09-22 06:13:42',1,1,0,'d1404186-21aa-4435-9a5a-ce1f5a23c07a',0,0),
('d6fa4694-46a4-4e4d-a4c0-46e9a550f1a1','id','字典数据编号',_binary '\0','UUID','',0,'1024',_binary '','2023-09-21 07:52:10','2023-09-21 07:52:10',1,1,0,'54166b46-e467-47b6-bc89-fedbd50fa55e',0,0),
('d7d5ae64-957d-4705-8c5f-5590b9f8db9c','createTime','创建时间',_binary '\0','LocalDateTime','',0,'时间戳格式',_binary '','2023-09-21 08:37:51','2023-09-21 08:37:51',1,1,0,'24d0114e-6abc-4d73-94fb-e4be70fdec20',0,1),
('dad2df59-e23a-4c02-8b87-014a192df840','id','部门编号',_binary '\0','Long','',0,'1024',_binary '','2023-09-05 10:08:40','2023-09-05 10:08:40',1,1,0,'8a581520-2d75-491b-9008-36d901721574',0,0),
('dea05dc3-8f61-47bf-a5a0-6bab7fb96381','outParam','',_binary '\0','Long','',0,'',_binary '\0','2023-09-05 10:01:55','2023-09-05 10:01:55',1,1,0,'dea9ac66-367e-4734-af66-509926cc8a61',0,1),
('deeb3aaa-362b-47b3-b820-9271164b17f8','outParam','',_binary '\0','Boolean','',0,'',_binary '\0','2023-09-21 07:52:10','2023-09-21 07:52:10',1,1,0,'54166b46-e467-47b6-bc89-fedbd50fa55e',0,1),
('e023fcfe-a8b1-4108-abbe-d93fc00e6046','type','字典类型,模糊匹配',_binary '\0','String','',0,'sys_common_sex',_binary '\0','2023-09-22 06:13:42','2023-09-22 06:13:42',1,1,0,'d1404186-21aa-4435-9a5a-ce1f5a23c07a',0,0),
('e4dba7a6-5cea-4aed-be64-238eb35b678c','outParam','',_binary '\0','UUID','',0,'',_binary '\0','2023-09-22 03:18:57','2023-09-22 03:18:57',1,1,0,'6788bbe5-85ff-4536-b16b-7ae243b0e59e',0,1),
('e569646f-cf0c-41b0-96e1-5bacc0024f30','outParam','',_binary '\0','Boolean','',0,'',_binary '\0','2023-09-05 10:44:43','2023-09-05 10:44:43',1,1,0,'1f62ad16-17af-4261-a6bf-370b2a34160b',0,1),
('e66ffafe-7f4a-4f2c-a276-3aff1ea08dbf','id','部门编号',_binary '\0','','',0,'1024',_binary '','2023-09-06 09:45:06','2023-09-06 09:45:06',1,1,0,'14256ebd-dc82-449d-a090-28c1aefb3ab0',0,1),
('ea1ea580-ecbe-4532-a5a7-2e98a335c6e0','id','部门编号',_binary '\0','','',0,'1024',_binary '','2023-09-06 09:49:44','2023-09-06 09:49:44',1,1,0,'cafa26f4-04ec-4689-a0b8-a779d8749ab9',0,1),
('ea3dd3bc-e57b-4723-aa42-696f564de10e','id','字典数据编号',_binary '\0','UUID','',0,'1024ddd',_binary '','2023-09-21 08:37:51','2023-09-21 08:37:51',1,1,0,'24d0114e-6abc-4d73-94fb-e4be70fdec20',0,1),
('ec7c7860-50e7-498b-ad50-0093b4e43401','dictType','字典类型',_binary '\0','String','4cd58a40-e443-4606-91eb-0a0ca3d6bfcd',1,'用户性别',_binary '','2023-09-21 08:14:54','2023-09-21 08:14:54',1,1,0,'3b52c661-4df5-415a-a00f-56142feb6369',0,1),
('f6f5a99c-ad4b-4b2e-b0de-86de4cebbd49','name','字典类型名称,模糊匹配',_binary '\0','String','',0,'芋道',_binary '\0','2023-09-22 06:13:42','2023-09-22 06:13:42',1,1,0,'d1404186-21aa-4435-9a5a-ce1f5a23c07a',0,0),
('fa9952c8-9aed-4c02-a992-ca16fb2babbd','createTime','创建时间',_binary '\0','LocalDateTime','',0,'时间戳格式',_binary '','2023-09-05 10:53:08','2023-09-05 10:53:08',1,1,0,'51f7b230-149c-4689-b527-f37a9649befd',0,1),
('fdc89e33-3b06-4283-8b01-0635374a71ae','sub1','434',_binary '\0','Subclass','701e2c90-fbf4-48b5-9286-e6dc38357a6d',3,'',_binary '\0','2023-09-07 02:03:54','2023-09-07 02:03:54',1,1,0,'8da02266-4007-4aca-972e-a3428f34d138',0,0),
('ffebe536-bb10-4f41-a1aa-c56991eb04ee','related_id','关联字段id',_binary '\0','String','88a6ba5c-0c05-46f9-acd8-67c11823d0c3',1,'23423423',_binary '\0','2023-09-07 02:09:32','2023-09-07 02:09:32',1,1,0,'b79c2413-99ee-4f37-ae76-4f1edcd9dcbc',0,0);
/*!40000 ALTER TABLE `infra_interface_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_interface_subclass`
--

DROP TABLE IF EXISTS `infra_interface_subclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_interface_subclass` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `parent_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '接口ID',
  `name` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '子类名称',
  `comment` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
  `inherit_class` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '继承类',
  `inherit_type` tinyint NOT NULL DEFAULT '0' COMMENT '继承类型2 VO类 3 子类',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '子类类型 0 入参 1 出参',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_infra_interface_subclass` (`name`,`parent_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口子类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_interface_subclass`
--

LOCK TABLES `infra_interface_subclass` WRITE;
/*!40000 ALTER TABLE `infra_interface_subclass` DISABLE KEYS */;
INSERT INTO `infra_interface_subclass` VALUES 
('1f1e6034-97bb-4b49-b28e-ad9a7a900978','f5b2fbfa-1844-4833-8482-09959511caa6','DictDataExportInput','字典类型导出 Request','',0,0,'2023-09-21 09:02:41','2023-09-21 09:02:41',1,1,0),
('2eb11db9-f547-47e0-93d8-27130a3dad86','b79c2413-99ee-4f37-ae76-4f1edcd9dcbc','subin','','InterfaceParamBase',2,0,'2023-09-07 02:09:32','2023-09-07 02:09:32',1,1,0),
('701e2c90-fbf4-48b5-9286-e6dc38357a6d','8da02266-4007-4aca-972e-a3428f34d138','sub1','','InterfaceModuleBase',2,0,'2023-09-07 02:03:54','2023-09-07 02:03:54',1,1,0),
('d9715a8d-b0d4-4869-b88a-297bd13b4d2e','b9a8a71a-05a9-4996-8602-945b0b8a5ab7','suub','','RoleMenuBase',2,0,'2023-09-07 02:36:36','2023-09-07 02:36:36',1,1,0),
('de32063a-970f-42c8-b877-bc5cfaa36391','a81751ff-a3d5-4b28-9810-f98b347b3fe5','sub1','','InterfaceBase',2,0,'2023-09-07 02:13:26','2023-09-07 02:13:26',1,1,0);
/*!40000 ALTER TABLE `infra_interface_subclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_interface_validation`
--

DROP TABLE IF EXISTS `infra_interface_validation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_interface_validation` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `parent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '父表',
  `validation` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '校验注解',
  `validation_condition` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '校验条件',
  `message` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '报错信息',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `parent_type` tinyint NOT NULL DEFAULT '0' COMMENT '父表类型0 infra_database_column 1 infra_interface_param',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UNI_infra_database_validation_name` (`validation`,`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码生成表校验定义';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_interface_validation`
--

LOCK TABLES `infra_interface_validation` WRITE;
/*!40000 ALTER TABLE `infra_interface_validation` DISABLE KEYS */;
INSERT INTO `infra_interface_validation` VALUES 
('03725e41-a112-47d8-8190-8d5bb9e554ac','b2a23a67-bacc-45b4-a80f-2979bec91aab','NotNull','','关联表不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('0a668136-24ef-40c9-9b9a-a9805b7a5361','aacf4ccc-de91-49ea-b786-dc28ed838ecb','NotEmpty','','物理类型不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('0c1a1e2b-3986-4a8a-bdee-ecfb901a9094','2736ba11-b7f3-4199-865a-79431d32ec91','NotEmpty','','字段描述不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('16adcd2f-ffff-4559-8f95-00609802925b','238680fe-6c06-4678-8fe6-815ba9c91f11','NotEmpty','','字段不能为空',1,'2023-08-18 08:52:02',1,'2023-08-18 08:52:02',_binary '\0',0),
('18f4183b-7163-4d1c-acdf-0d01d30dd0f4','9063ba24-d9d4-49c0-9e5b-0a008a4d6a57','NotBlank','','DFDFDFDFD',1,'2023-08-17 17:16:21',1,'2023-08-17 17:16:21',_binary '\0',0),
('1a966fb8-16eb-4f2e-baaa-cc72f9f2679e','5020b4c0-c9e4-4b0f-b4fd-ba3e9fe03d14','InEnum','value = CommonStatusEnum.class','状态必须是 {value}',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('1d0af306-ccfb-41bf-a927-54a3f6876f57','cb4e8d8c-acc9-4edb-981b-3ff48c41c2aa','NotEmpty','','模块描述不能为空',1,'2023-07-24 18:25:31',1,'2023-07-24 18:25:31',_binary '\0',0),
('252c460e-8242-4374-9555-e7c363e99c8c','50895052-88c8-40f3-bcb2-f9e6e56b8478','NotNull','','v1',1,'2023-08-20 12:54:43',1,'2023-08-20 12:54:43',_binary '\0',1),
('2b9a45d6-90f1-4bb7-ba89-ef5dab2d669d','7fc26fd3-cffd-4c32-a1b8-d058d700486d','NotNull','','显示顺序不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('32e50601-65dd-4e8c-a87e-8c54f0e7fe0e','0e08e19a-eb2a-47ec-82fd-5aabc3a2917b','Pattern','regexp = \"^[\\\\x00-\\\\xff]*$\"','默认值不合法',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('3487ed23-b83c-4f51-a2aa-d625239cf744','5d116e67-420e-44c5-aabe-0298056f9f03','NotNull','','用户类型不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('35a58091-a6cb-4500-bb60-d8ca67e92b82','cff8e930-e761-4ac6-9639-a72dac09b955','NotNull','','模版参数不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('38c45dcf-466b-4b5f-a913-60f70691984a','a6e0ce56-5d9c-4f8b-9389-f915d1410dd3','Size','max = 50','岗位名称长度不能超过50个字符',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('41c70959-056e-4e46-ae90-69587f5a37b5','a6e0ce56-5d9c-4f8b-9389-f915d1410dd3','NotBlank','','岗位名称不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('43345067-2fcc-4b3e-97a8-7af6e36438b4','690a9ce1-272a-4ddf-b8e0-089623e37cf8','Max','','tt',1,'2023-08-20 13:12:04',1,'2023-08-20 13:12:04',_binary '\0',1),
('47230d7f-4ae0-431b-9d20-b3ce249bd75e','a823105b-86ce-4b49-addc-f09defbd8dc0','NotNull','','模版类型不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('4b048107-dfa6-48d0-8942-323beecc652d','ff6a8fd2-1bea-4f13-b872-80caf475f749','NotBlank','','公告标题不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('4b66e801-9933-408e-afbc-71f7a7177a79','fb681285-c479-453c-ad9a-db494e56c26a','NotEmpty','','字段名不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('4e2faa59-f1b0-4b10-bfda-1c059e557754','fb681285-c479-453c-ad9a-db494e56c26a','Pattern','regexp = \"^[a-z_]+$\"','字段名只能是小写英文和_',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('533c9e98-2f3a-4dea-a5d1-2707f06c7045','82a95905-662b-4595-a98b-175445a73849','Size','max = 100','字典类型类型长度不能超过100个字符',1,'2023-09-21 17:02:41',1,'2023-09-21 17:02:41',_binary '\0',1),
('57facd33-0a60-4854-acf7-c53d7d396798','2a7a92a1-0e99-41ab-af89-b8c7cf792a1e','NotEmpty','','outputva12',1,'2023-08-22 10:30:01',1,'2023-08-23 11:04:06',_binary '\0',1),
('5c02dbce-8487-4aaa-90d3-a8dcf7ad0901','27b7f4d7-8594-4b4a-b0c2-d4cda3141a43','NotEmpty','','模块名称不能为空',1,'2023-07-24 18:25:31',1,'2023-07-24 18:25:31',_binary '\0',0),
('5dbe4637-b38f-47c5-8403-3c3bbcb816d8','5020b4c0-c9e4-4b0f-b4fd-ba3e9fe03d14','NotNull','','状态不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('5df592e6-03fc-46bb-bc41-2bb08fcc405b','c78f9133-bd60-4da0-9dbf-20b7e3f15290','Size','max = 11','联系电话长度不能超过11个字符',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('61aeeb03-211d-4c73-97e5-4fed2e98ed63','20592213-ea03-46bc-a067-154083b95537','Size','max = 100','字典标签长度不能超过100个字符',1,'2023-09-22 10:41:25',1,'2023-09-22 10:41:25',_binary '\0',1),
('638e82e7-b303-490a-9c61-92f1aa168024','a997d265-4890-43d0-a3df-c6116022bb19','NotBlank','','outputsubVa2',1,'2023-08-22 10:30:01',1,'2023-08-23 11:05:31',_binary '\0',1),
('6d76f3bf-8899-49b0-94b5-16182f94d35c','e0b054a6-5ac6-4d83-8a3d-bdb03cb1bfeb','NotEmpty','','Java 属性类型不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('7486bc42-c3e1-4ef4-a8fe-86cd2afb5710','15b26969-4b58-4bf0-aa7b-bccb4fc2e55f','Range','','3-9',1,'2023-08-26 16:22:01',1,'2023-08-26 16:22:01',_binary '\0',1),
('783ab5bc-b544-49f6-b96a-865035817f51','ae012ea8-d7cc-4129-9f4f-a00a0fd78a85','NotNull','','业务名不能为空',1,'2023-08-09 16:19:20',1,'2023-08-09 16:19:20',_binary '\0',0),
('795b6622-45c6-4f4f-9b40-4c7c150a90b8','27b7f4d7-8594-4b4a-b0c2-d4cda3141a43','Pattern','regexp = \"^[A-Za-z0-9]+$\"','模块名称只能是英文和数字',1,'2023-07-24 18:25:31',1,'2023-07-24 18:25:31',_binary '\0',0),
('7cd141be-1419-42b7-a0cc-d0e697207248','aea40a07-1be5-4fba-ad3e-b616e05c2f50','NotNull','','字典类型编号不能为空',1,'2023-09-22 11:38:34',1,'2023-09-22 11:38:34',_binary '\0',1),
('7dbec638-2b8b-4e20-aebe-650cdecf42f7','73cc9449-6aaa-4ac6-a806-317c8453a7b6','Email','','邮箱格式不正确',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('814e4b17-e659-41af-8505-5bdf8036d001','aacf4ccc-de91-49ea-b786-dc28ed838ecb','Pattern','regexp = \"^(BIT|TINYINT|SMALLINT|MEDIUMINT|INT|BIGINT|FLOAT|DOUBLE|DECIMAL|DATE|TIME|YEAR|DATETIME|TIMESTAMP|VARCHAR|CHAR|TEXT)[0-9(),]*$\"','物理类型不合法',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('818b6d19-0ac6-4109-bea8-eeaa35cc2335','312bf5a2-ecf8-4f01-b11c-d10e88438484','NotNull','','排序不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('81d1e367-4668-4093-94ab-7124f25d5f56','baaf3587-e9a8-4ef8-a895-ebf1536af054','NotNull','','模版类型不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('83770e2a-3f87-4e5e-bfd3-c9f195eb6cd5','47bebd1c-1b0f-4f02-a9cc-0a0ad27115cd','Size','max = 30','部门名称长度不能超过30个字符',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('85193347-168f-433e-be49-06e94f5dd7d8','132be88a-cde9-41f1-a671-8f2ec6193381','Max','','dd',1,'2023-08-20 13:07:51',1,'2023-08-20 13:07:51',_binary '\0',1),
('857caa57-e7cd-4b81-a3c5-2833e1ce7b30','15452aa4-743a-4778-b53b-7f7ebcb0b69e','NotNull','','模版编码不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('85b19fb2-0622-47d2-b3a6-a94baaa4ce28','664b8578-38a1-47cd-b828-49e5efb7d1af','NotNull','','模版编号不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('87ed3813-300b-463d-8ec6-15eb514fd35c','86605d63-26fd-49e2-a1db-2e6f6af8a615','NotNull','','模版发送人名称不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('8cd8159c-0c14-46f6-a093-39d73bac8598','6ad1b33d-8e62-41db-8fd2-5e6b2f417503','Size','max = 100','字典类型类型长度不能超过100个字符',1,'2023-09-22 10:41:25',1,'2023-09-22 10:41:25',_binary '\0',1),
('94635445-9fc4-4d5e-aaec-029318455a93','9d78aac4-debb-475d-b294-3cac71eb3bb1','Max','','最大为8',1,'2023-08-26 16:22:01',1,'2023-08-26 16:22:01',_binary '\0',1),
('94d21332-33a7-4e5d-975a-1f5bf7e4779d','d48c80ad-170d-4b40-90b0-5cbabbad097d','NotEmpty','','索引类型不能为空',1,'2023-08-18 08:52:02',1,'2023-08-18 08:52:02',_binary '\0',0),
('9a9ab35b-1579-40b8-983e-985ebc623348','3c0d7404-ffb4-402b-9c91-f187c15cff49','NotNull','','用户编号不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('9b901a76-3079-4074-82ab-28510eda82ce','e99611b1-7d64-4266-977d-2431b4cfeaca','NotEmpty','','模版名称不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('9d138953-62ad-4016-91ec-ffa1dd6da4f6','ae012ea8-d7cc-4129-9f4f-a00a0fd78a85','Pattern','regexp = \"^[a-z_]+$\"','表名称只能是小写英文和_',1,'2023-08-09 16:19:20',1,'2023-08-09 16:19:20',_binary '\0',0),
('a0d907b3-31cd-437a-862f-a790b5a3686d','dad2df59-e23a-4c02-8b87-014a192df840','NotNull','','部门编号不能为空',1,'2023-09-05 18:08:40',1,'2023-09-05 18:08:40',_binary '\0',1),
('a2787ac6-af44-44d0-91e0-25c7995c39df','041f882a-a941-4877-a0f3-cb16fd87edf7','NotNull','','模版内容不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('a676b917-6bdc-42ea-8bd7-03b48786accf','18b32d45-6d37-4229-b4ae-dc42279f77a1','NotBlank','','校验注解不能为空',1,'2023-08-09 17:20:21',1,'2023-08-09 17:20:21',_binary '\0',0),
('aa6edd09-82fc-42d5-b12e-2b754b03c154','71deadeb-087f-4e05-81ad-e0042bbf6e18','NotEmpty','','索引名称不能为空',1,'2023-08-18 08:52:02',1,'2023-08-18 08:52:02',_binary '\0',0),
('ab1ade2b-ea78-402e-ba60-1910497d2419','167d5f70-7cdb-425a-8e2d-df3b70057fe1','Pattern','regexp = \"^[a-z_]+$\"','表名称只能是小写英文和_',1,'2023-08-09 16:19:20',1,'2023-08-09 16:19:20',_binary '\0',0),
('ab5ae8b2-388d-41ac-b7d9-31cb5abaf744','7d5cd0bd-93bc-4862-92af-1748830a9c90','Size','max = 100','字典标签长度不能超过100个字符',1,'2023-09-21 17:02:41',1,'2023-09-21 17:02:41',_binary '\0',1),
('ab94640c-adc4-4d02-9c6b-0d206fac8f41','d7267b4d-32bc-4308-b7b6-33460b05b392','Size','max = 64','岗位编码长度不能超过64个字符',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('b0430368-99aa-4cdb-bfef-9d68704a70e4','3377f494-7133-442f-a3b5-c09fdccfaed4','NotBlank','','inputva1',1,'2023-08-22 10:30:01',1,'2023-08-23 11:03:36',_binary '\0',1),
('b07e70de-af1f-437b-93fc-de998baa499c','e012303f-dc51-4efb-86ed-129c19873955','NotNull','','公告类型不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('ba5fe048-2f85-4f88-9029-346004ae7f65','','NotEmpty','','不能为空',1,'2023-08-17 16:33:49',1,'2023-08-17 16:33:49',_binary '\0',0),
('be744bd4-b6b3-4fdb-9bfe-4140e850773f','e19f68f2-b57e-471e-beb0-e607ff277083','NotNull','','校验条件不能为空',1,'2023-08-09 17:20:21',1,'2023-08-09 17:20:52',_binary '\0',0),
('c30b55b6-35f2-4ae6-a9b7-6c89832b0097','1718fc9f-c6ef-437f-80da-a4f49ce86590','Pattern','','inputsubcalssPaVa2',1,'2023-08-22 10:30:01',1,'2023-08-23 11:05:11',_binary '\0',1),
('cf416e2a-b5d1-4928-a5a3-b05d8e9cb804','490abd70-d214-4da0-8e68-db67d5d77f41','NotNull','','显示顺序不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('d34e50b9-7994-4a27-8ef1-43d53bbad8cd','ff6a8fd2-1bea-4f13-b872-80caf475f749','Size','max = 50','公告标题不能超过50个字符',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('d59cd065-a6d2-4cd6-be67-254e8e226ac7','3b45ad03-bffe-4566-a789-f9ba462010ed','NotNull','','报错信息不能为空',1,'2023-08-09 17:20:21',1,'2023-08-09 17:20:52',_binary '\0',0),
('dc59e68a-58bf-4966-a9f1-a7b92bed8394','86b70789-a105-4e46-87b0-2f93cafc4be4','NotNull','','模板编码不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('e0dd7b0f-91fb-4d59-8752-e02f133f7657','73cc9449-6aaa-4ac6-a806-317c8453a7b6','Size','max = 50','邮箱长度不能超过50个字符',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('e2ffb75b-0c68-4137-92b0-b546e5931d28','a16863b2-2127-48ff-a886-d1d9dfcabcf0','NotNull','','是否已读不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('e43286b5-f3a0-4a29-83d2-3e7484e3822d','d6fa4694-46a4-4e4d-a4c0-46e9a550f1a1','NotNull','','字典数据编号不能为空',1,'2023-09-21 15:52:10',1,'2023-09-21 15:52:10',_binary '\0',1),
('e55a8fe1-e382-4344-9be5-60fd91ed031d','6c1dfc4b-d2e1-4674-a722-33012bdaad6e','NotEmpty','','模版内容不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('e5c998f4-0c7d-4db9-ae75-30c925dd1eae','7e8afa35-a927-4ed9-bd9f-479010e51110','NotBlank','','不能为空33',1,'2023-08-17 16:41:10',1,'2023-08-17 17:01:48',_binary '\0',0),
('e729f72c-53aa-45d5-950b-be2898eca503','47bebd1c-1b0f-4f02-a9cc-0a0ad27115cd','NotBlank','','部门名称不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('e8fecd65-4086-4d65-8e88-08f4f74b865a','99635296-352f-4038-b255-fe94f35be2e8','NotNull','','是否允许前端必传不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('ec6a4e31-a628-40d1-aaee-213b8abccb29','361f9270-95ad-475b-912f-45f938d8e465','NotNull','','是否允许为空不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('edc15933-09e8-4b37-b1e3-d12581b06877','d7267b4d-32bc-4308-b7b6-33460b05b392','NotBlank','','岗位编码不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('edd0c75d-86ea-4daf-8eb4-5a4578747ecc','0e08e19a-eb2a-47ec-82fd-5aabc3a2917b','NotNull','','默认值不能为空',1,'2023-08-17 18:47:39',1,'2023-08-17 18:47:39',_binary '\0',0),
('f72ea201-5d05-400f-ac0e-d18171c0457a','167d5f70-7cdb-425a-8e2d-df3b70057fe1','NotNull','','表名称不能为空',1,'2023-08-09 16:19:20',1,'2023-08-09 16:19:20',_binary '\0',0),
('fb9756d5-7005-4f32-95b4-0cb089e46291','fd22baec-a8b7-4f03-96b0-5458c9d9936b','NotEmpty','','发送人名称不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0),
('fbe18413-7e30-477f-8f13-338ef0d9b3c4','4353728c-7080-4a46-b01d-83f09d4d1ca5','NotNull','','状态不能为空',1,'2023-07-24 08:44:58',1,'2023-07-24 08:44:58',_binary '\0',0);
/*!40000 ALTER TABLE `infra_interface_validation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_interface_vo_class`
--

DROP TABLE IF EXISTS `infra_interface_vo_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_interface_vo_class` (
  `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类名',
  `comment` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '0 基类 1 入参类 2 出参类 3 入参子类 4 出参子类',
  `parent_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '父类ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '修改人ID',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `UK_infra_interface_param_class` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口参数类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_interface_vo_class`
--

LOCK TABLES `infra_interface_vo_class` WRITE;
/*!40000 ALTER TABLE `infra_interface_vo_class` DISABLE KEYS */;
INSERT INTO `infra_interface_vo_class` VALUES 
('165c8897-9b43-48f9-894d-fae5d5ecda82','DictTypeBase','字典类型表',0,'8e93110a-90fe-4e9c-9fb5-f7fa480dc892','2023-09-20 02:06:35','2023-09-21 07:31:29',1,1,0),
('547782c7-376c-11ee-98db-00163e021dba','DatabaseTableBase','数据库表定义',0,'0a9647f1-dbe7-48c2-b332-624a05a83df0','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('57ff9d61-376c-11ee-98db-00163e021dba','UserRoleBase','用户和角色关联表',0,'16f0d4c9-9ceb-4139-9df9-bc2801dc95fe','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('5d0f7160-376c-11ee-98db-00163e021dba','UserPostBase','用户岗位表',0,'21b4e945-9bf5-4718-829c-059259da899c','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('619c9473-376c-11ee-98db-00163e021dba','InterfaceParamBase','接口参数',0,'285770c9-d338-4913-95bb-7c50fe968d12','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('645cf87b-376c-11ee-98db-00163e021dba','InterfaceModuleBase','接口模块',0,'3a4f68eb-f789-4fc2-abb5-e86399d35fe7','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('6751174c-376c-11ee-98db-00163e021dba','RoleMenuBase','角色和菜单关联表',0,'4e4cc482-af80-4ebb-a8b7-4855f77b0b89','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('6d4607da-376c-11ee-98db-00163e021dba','PostBase','岗位信息表',0,'62ca5e40-4a79-49bb-af2b-977e7205fb4e','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('70455db9-376c-11ee-98db-00163e021dba','InterfaceBase','接口',0,'7a6ac56d-422e-40a0-a0e6-1578dfb1811d','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('741679d0-376c-11ee-98db-00163e021dba','NoticeBase','通知公告表',0,'7f7c2842-fbc4-4b37-9ec9-4949e36307ed','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('76cb4ec2-376c-11ee-98db-00163e021dba','NotifyMessageBase','站内信消息表',0,'842da8a4-f2ff-4ca0-b169-170a1f88ef3c','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('7a302837-376c-11ee-98db-00163e021dba','RoleBase','角色信息表',0,'8e9c5164-801f-47e3-99d6-c28dd803a9e5','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('7be344ea-e783-4b02-9267-2f63f08d98b6','DatabaseMapping','数据库表映射',0,'ba35bc1c-c14c-44af-9506-f5f57cc79fae','2023-08-16 03:53:48','2023-08-29 10:19:41',1,1,0),
('7d26ad91-376c-11ee-98db-00163e021dba','InterfaceValidationBase','代码生成表校验定义',0,'bb3acb96-f7d5-4b8a-97c7-3f02abb12153','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('807743ca-376c-11ee-98db-00163e021dba','NotifyTemplateBase','站内信模板表',0,'9127fb54-c0da-4077-99ac-21945d84cc78','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('861845b8-376c-11ee-98db-00163e021dba','DeptBase','部门表',0,'c336cb46-f45a-4f10-91fb-88003fd5dac6','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('8b0b3235-376c-11ee-98db-00163e021dba','UserBase','用户信息表',0,'e1a06dd2-bba2-49f8-ae05-8c172f216d61','2023-08-10 10:57:50','2023-08-29 10:19:41',0,0,0),
('92fa69ef-58ed-11ee-98db-00163e021dba','PageParam','分页参数类',1,'','2023-09-22 02:17:13','2023-09-22 02:17:13',0,0,0),
('95f6a762-434a-413f-b9c8-3c86beb5df6f','DictDataBase','字典数据表',0,'209dd887-1db4-4260-9f9a-10efa33b439f','2023-09-20 02:13:53','2023-09-21 07:32:00',1,1,0),
('ad08d1a8-4102-4bff-ac78-79f7b30c0002','DatabaseColumnBase','数据库表字段',0,'376e9077-6e6d-47d3-b75d-b7aefdddccaa','2023-08-17 10:47:39','2023-08-29 10:19:41',1,1,0),
('ae9b933f-248d-4efe-90c0-28844850f20a','DatabaseIndexBase','数据库表索引',0,'0526ebf1-9d5a-4226-a47b-c4384acff4eb','2023-08-18 00:52:03','2023-08-29 10:19:41',1,1,0),
('b6e2e230-0754-4dee-b054-5480829c9ddc','InterfaceSubclassBase','接口子类',0,'cac76521-8eec-4d07-87f8-df26fe2aed4e','2023-08-19 04:05:23','2023-08-29 10:19:41',1,1,0);
/*!40000 ALTER TABLE `infra_interface_vo_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infra_job`
--

DROP TABLE IF EXISTS `infra_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_job` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务名称',
  `status` tinyint NOT NULL COMMENT '任务状态',
  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
  `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '处理器的参数',
  `cron_expression` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'CRON 表达式',
  `retry_count` int NOT NULL DEFAULT '0' COMMENT '重试次数',
  `retry_interval` int NOT NULL DEFAULT '0' COMMENT '重试间隔',
  `monitor_timeout` int NOT NULL DEFAULT '0' COMMENT '监控超时时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='定时任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_job`

--
-- Table structure for table `infra_job_log`
--

DROP TABLE IF EXISTS `infra_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infra_job_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志编号',
  `job_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务编号',
  `handler_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '处理器的名字',
  `handler_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '处理器的参数',
  `execute_index` tinyint NOT NULL DEFAULT '1' COMMENT '第几次执行',
  `begin_time` datetime NOT NULL COMMENT '开始执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束执行时间',
  `duration` int NOT NULL DEFAULT '0' COMMENT '执行时长',
  `status` tinyint NOT NULL COMMENT '任务状态',
  `result` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '结果数据',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='定时任务日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infra_job_log`

--
-- Table structure for table `infra_test_demo`
--
--
-- Table structure for table `qrtz_blob_triggers`
--

DROP TABLE IF EXISTS `qrtz_blob_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_blob_triggers`
--

LOCK TABLES `qrtz_blob_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_blob_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_blob_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_calendars`
--

DROP TABLE IF EXISTS `qrtz_calendars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_calendars`
--

LOCK TABLES `qrtz_calendars` WRITE;
/*!40000 ALTER TABLE `qrtz_calendars` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_calendars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_cron_triggers`
--

DROP TABLE IF EXISTS `qrtz_cron_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_cron_triggers`
--

--
-- Table structure for table `qrtz_fired_triggers`
--

DROP TABLE IF EXISTS `qrtz_fired_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE,
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_fired_triggers`
--

LOCK TABLES `qrtz_fired_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_fired_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_fired_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_job_details`
--

DROP TABLE IF EXISTS `qrtz_job_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_job_details`
--

--
-- Table structure for table `qrtz_locks`
--

DROP TABLE IF EXISTS `qrtz_locks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_locks`
--

LOCK TABLES `qrtz_locks` WRITE;
/*!40000 ALTER TABLE `qrtz_locks` DISABLE KEYS */;
INSERT INTO `qrtz_locks` VALUES 
('schedulerName','STATE_ACCESS'),
('schedulerName','TRIGGER_ACCESS');
/*!40000 ALTER TABLE `qrtz_locks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_paused_trigger_grps`
--

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_paused_trigger_grps`
--

LOCK TABLES `qrtz_paused_trigger_grps` WRITE;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_paused_trigger_grps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_scheduler_state`
--

DROP TABLE IF EXISTS `qrtz_scheduler_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_scheduler_state`
--

LOCK TABLES `qrtz_scheduler_state` WRITE;
/*!40000 ALTER TABLE `qrtz_scheduler_state` DISABLE KEYS */;
INSERT INTO `qrtz_scheduler_state` VALUES 
('schedulerName','sunny1697421391410',1697798382874,15000);
/*!40000 ALTER TABLE `qrtz_scheduler_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simple_triggers`
--

DROP TABLE IF EXISTS `qrtz_simple_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simple_triggers`
--

LOCK TABLES `qrtz_simple_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simple_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simple_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_simprop_triggers`
--

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_simprop_triggers`
--

LOCK TABLES `qrtz_simprop_triggers` WRITE;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` DISABLE KEYS */;
/*!40000 ALTER TABLE `qrtz_simprop_triggers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qrtz_triggers`
--

DROP TABLE IF EXISTS `qrtz_triggers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qrtz_triggers`
--
--
-- Table structure for table `system_dept`
--

DROP TABLE IF EXISTS `system_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父部门id',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `leader_user_id` bigint NOT NULL DEFAULT '0' COMMENT '负责人',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_dept`
--

LOCK TABLES `system_dept` WRITE;
/*!40000 ALTER TABLE `system_dept` DISABLE KEYS */;
INSERT INTO `system_dept` VALUES 
(100,'总部门',0,0,1,'15888888888','',0,1,'2021-01-05 17:03:47',1,'2022-06-19 00:29:10',_binary '\0');
/*!40000 ALTER TABLE `system_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_error_code`
--

DROP TABLE IF EXISTS `system_error_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_error_code` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '错误码编号',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '错误码类型',
  `application_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '应用名',
  `code` int NOT NULL DEFAULT '0' COMMENT '错误码编码',
  `message` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '错误码错误提示',
  `memo` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错误码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_error_code`
--
--
-- Table structure for table `system_login_log`
--

DROP TABLE IF EXISTS `system_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_login_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `log_type` bigint NOT NULL COMMENT '日志类型',
  `trace_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT '0' COMMENT '用户类型',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户账号',
  `result` tinyint NOT NULL COMMENT '登陆结果',
  `user_ip` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户 IP',
  `user_agent` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '浏览器 UA',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_login_log`
--

--
-- Table structure for table `system_mail_account`
--

DROP TABLE IF EXISTS `system_mail_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_mail_account` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mail` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `host` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SMTP 服务器域名',
  `port` int NOT NULL COMMENT 'SMTP 服务器端口',
  `ssl_enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启 SSL',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮箱账号表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_mail_account`
--

LOCK TABLES `system_mail_account` WRITE;
/*!40000 ALTER TABLE `system_mail_account` DISABLE KEYS */;
INSERT INTO `system_mail_account` VALUES 
(1,'7684413@qq.com','7684413@qq.com','123457','127.0.0.1',8080,_binary '\0',1,'2023-01-25 17:39:52',1,'2023-04-12 23:04:49',_binary '\0'),
(2,'ydym_test@163.com','ydym_test@163.com','WBZTEINMIFVRYSOE','smtp.163.com',465,_binary '',1,'2023-01-26 01:26:03',1,'2023-04-12 22:39:38',_binary '\0');
/*!40000 ALTER TABLE `system_mail_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_mail_log`
--

DROP TABLE IF EXISTS `system_mail_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_mail_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT '0' COMMENT '用户类型',
  `to_mail` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接收邮箱地址',
  `account_id` bigint NOT NULL COMMENT '邮箱账号编号',
  `from_mail` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送邮箱地址',
  `template_id` bigint NOT NULL COMMENT '模板编号',
  `template_code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `template_nickname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模版发送人名称',
  `template_title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件标题',
  `template_content` varchar(10240) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件内容',
  `template_params` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮件参数',
  `send_status` tinyint NOT NULL DEFAULT '0' COMMENT '发送状态',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `send_message_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '发送返回的消息 ID',
  `send_exception` varchar(4096) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '发送异常',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮件日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_mail_log`
--

LOCK TABLES `system_mail_log` WRITE;
/*!40000 ALTER TABLE `system_mail_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_mail_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_mail_template`
--

DROP TABLE IF EXISTS `system_mail_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_mail_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
  `code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `account_id` bigint NOT NULL COMMENT '发送的邮箱账号编号',
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送人名称',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板标题',
  `content` varchar(10240) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
  `params` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数数组',
  `status` tinyint NOT NULL COMMENT '开启状态',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='邮件模版表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_mail_template`
--

LOCK TABLES `system_mail_template` WRITE;
/*!40000 ALTER TABLE `system_mail_template` DISABLE KEYS */;
INSERT INTO `system_mail_template` VALUES 
(14,'测试模版','test_01',2,'芋艿','一个标题','<p>你是 {key01} 吗？</p><p><br></p><p>是的话，赶紧 {key02} 一下！</p>','[\"key01\",\"key02\"]',0,NULL,1,'2023-01-26 01:27:40',1,'2023-01-27 10:32:16',_binary '\0');
/*!40000 ALTER TABLE `system_mail_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_menu`
--

DROP TABLE IF EXISTS `system_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_menu` (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `type` tinyint NOT NULL COMMENT '菜单类型',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `parent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '路由地址',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '#' COMMENT '菜单图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '组件路径',
  `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '组件名',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '菜单状态',
  `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
  `keep_alive` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否缓存',
  `always_show` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否总是显示',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `IX_SYSTEM_MENU_NAME` (`name`,`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_menu`
--

LOCK TABLES `system_menu` WRITE;
/*!40000 ALTER TABLE `system_menu` DISABLE KEYS */;
INSERT INTO `system_menu` VALUES 
('06cb82c5-23b0-11ee-a1af-047c1649ee1e','系统管理','',1,10,'','/system','system','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb84a7-23b0-11ee-a1af-047c1649ee1e','用户管理','system:user:list',2,1,'06cb82c5-23b0-11ee-a1af-047c1649ee1e','user','user','system/user/index','SystemUser',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb859a-23b0-11ee-a1af-047c1649ee1e','用户查询','system:user:query',3,1,'06cb84a7-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb863f-23b0-11ee-a1af-047c1649ee1e','用户新增','system:user:create',3,2,'06cb84a7-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb86c3-23b0-11ee-a1af-047c1649ee1e','用户修改','system:user:update',3,3,'06cb84a7-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8752-23b0-11ee-a1af-047c1649ee1e','用户删除','system:user:delete',3,4,'06cb84a7-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb87ea-23b0-11ee-a1af-047c1649ee1e','用户导出','system:user:export',3,5,'06cb84a7-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb886c-23b0-11ee-a1af-047c1649ee1e','用户导入','system:user:import',3,6,'06cb84a7-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb88eb-23b0-11ee-a1af-047c1649ee1e','重置密码','system:user:update-password',3,7,'06cb84a7-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb89f5-23b0-11ee-a1af-047c1649ee1e','角色查询','system:role:query',3,1,'06cb8b02-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8a7d-23b0-11ee-a1af-047c1649ee1e','角色新增','system:role:create',3,2,'06cb8b02-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8b02-23b0-11ee-a1af-047c1649ee1e','角色管理','',2,2,'06cb82c5-23b0-11ee-a1af-047c1649ee1e','role','peoples','system/role/index','SystemRole',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8b94-23b0-11ee-a1af-047c1649ee1e','角色修改','system:role:update',3,3,'06cb8b02-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8c1a-23b0-11ee-a1af-047c1649ee1e','角色删除','system:role:delete',3,4,'06cb8b02-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8ca6-23b0-11ee-a1af-047c1649ee1e','角色导出','system:role:export',3,5,'06cb8b02-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8d2b-23b0-11ee-a1af-047c1649ee1e','菜单查询','infra:menu:query',3,1,'06cb90b9-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8db0-23b0-11ee-a1af-047c1649ee1e','菜单新增','infra:menu:create',3,2,'06cb90b9-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8e2e-23b0-11ee-a1af-047c1649ee1e','菜单修改','infra:menu:update',3,3,'06cb90b9-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8eb4-23b0-11ee-a1af-047c1649ee1e','菜单删除','infra:menu:delete',3,4,'06cb90b9-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8f32-23b0-11ee-a1af-047c1649ee1e','部门查询','system:dept:query',3,1,'06cb9698-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb8faf-23b0-11ee-a1af-047c1649ee1e','部门新增','system:dept:create',3,2,'06cb9698-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9031-23b0-11ee-a1af-047c1649ee1e','部门修改','system:dept:update',3,3,'06cb9698-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb90b9-23b0-11ee-a1af-047c1649ee1e','菜单管理','',2,3,'06cc093d-23b0-11ee-a1af-047c1649ee1e','menu','tree-table','infra/menu/index','SystemMenu',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9156-23b0-11ee-a1af-047c1649ee1e','部门删除','system:dept:delete',3,4,'06cb9698-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb91d2-23b0-11ee-a1af-047c1649ee1e','岗位查询','system:post:query',3,1,'06cb9c34-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9259-23b0-11ee-a1af-047c1649ee1e','岗位新增','system:post:create',3,2,'06cb9c34-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb92d9-23b0-11ee-a1af-047c1649ee1e','岗位修改','system:post:update',3,3,'06cb9c34-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb935d-23b0-11ee-a1af-047c1649ee1e','岗位删除','system:post:delete',3,4,'06cb9c34-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb93db-23b0-11ee-a1af-047c1649ee1e','岗位导出','system:post:export',3,5,'06cb9c34-23b0-11ee-a1af-047c1649ee1e','','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb945e-23b0-11ee-a1af-047c1649ee1e','字典查询','infra:dict:query',3,1,'06cb9fe7-23b0-11ee-a1af-047c1649ee1e','#','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb94dd-23b0-11ee-a1af-047c1649ee1e','字典新增','infra:dict:create',3,2,'06cb9fe7-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb955d-23b0-11ee-a1af-047c1649ee1e','字典修改','infra:dict:update',3,3,'06cb9fe7-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9611-23b0-11ee-a1af-047c1649ee1e','字典删除','infra:dict:delete',3,4,'06cb9fe7-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9698-23b0-11ee-a1af-047c1649ee1e','部门管理','',2,4,'06cb82c5-23b0-11ee-a1af-047c1649ee1e','dept','tree','system/dept/index','SystemDept',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9720-23b0-11ee-a1af-047c1649ee1e','字典导出','infra:dict:export',3,5,'06cb9fe7-23b0-11ee-a1af-047c1649ee1e','#','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb97a7-23b0-11ee-a1af-047c1649ee1e','配置查询','infra:config:query',3,1,'06cbbb47-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9829-23b0-11ee-a1af-047c1649ee1e','配置新增','infra:config:create',3,2,'06cbbb47-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb98a4-23b0-11ee-a1af-047c1649ee1e','配置修改','infra:config:update',3,3,'06cbbb47-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9929-23b0-11ee-a1af-047c1649ee1e','配置删除','infra:config:delete',3,4,'06cbbb47-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb99ad-23b0-11ee-a1af-047c1649ee1e','配置导出','infra:config:export',3,5,'06cbbb47-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9a28-23b0-11ee-a1af-047c1649ee1e','公告查询','system:notice:query',3,1,'06cbbfc2-23b0-11ee-a1af-047c1649ee1e','#','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9ab3-23b0-11ee-a1af-047c1649ee1e','公告新增','system:notice:create',3,2,'06cbbfc2-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9b30-23b0-11ee-a1af-047c1649ee1e','公告修改','system:notice:update',3,3,'06cbbfc2-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9bb4-23b0-11ee-a1af-047c1649ee1e','公告删除','system:notice:delete',3,4,'06cbbfc2-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9c34-23b0-11ee-a1af-047c1649ee1e','岗位管理','',2,5,'06cb82c5-23b0-11ee-a1af-047c1649ee1e','post','post','system/post/index','SystemPost',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9cba-23b0-11ee-a1af-047c1649ee1e','操作查询','infra:operate-log:query',3,1,'06cc2f77-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9d41-23b0-11ee-a1af-047c1649ee1e','日志导出','infra:operate-log:export',3,2,'06cc2f77-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9dd0-23b0-11ee-a1af-047c1649ee1e','登录查询','infra:login-log:query',3,1,'06cc302f-23b0-11ee-a1af-047c1649ee1e','#','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9e55-23b0-11ee-a1af-047c1649ee1e','日志导出','infra:login-log:export',3,3,'06cc302f-23b0-11ee-a1af-047c1649ee1e','#','#','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9ee5-23b0-11ee-a1af-047c1649ee1e','令牌列表','infra:oauth2-token:page',3,1,'06cbc776-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9f62-23b0-11ee-a1af-047c1649ee1e','令牌删除','infra:oauth2-token:delete',3,2,'06cbc776-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cb9fe7-23b0-11ee-a1af-047c1649ee1e','字典管理','',2,6,'06cc093d-23b0-11ee-a1af-047c1649ee1e','dict','dict','infra/dict/index','SystemDictType',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cba06d-23b0-11ee-a1af-047c1649ee1e','任务新增','infra:job:create',3,2,'06cbccce-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cba0ef-23b0-11ee-a1af-047c1649ee1e','任务修改','infra:job:update',3,3,'06cbccce-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cba170-23b0-11ee-a1af-047c1649ee1e','任务删除','infra:job:delete',3,4,'06cbccce-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cba1f5-23b0-11ee-a1af-047c1649ee1e','状态修改','infra:job:update',3,5,'06cbccce-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cba277-23b0-11ee-a1af-047c1649ee1e','任务导出','infra:job:export',3,7,'06cbccce-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbbb47-23b0-11ee-a1af-047c1649ee1e','配置管理','',2,6,'06cc093d-23b0-11ee-a1af-047c1649ee1e','config','edit','infra/config/index','InfraConfig',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbbc63-23b0-11ee-a1af-047c1649ee1e','设置角色菜单权限','system:permission:assign-role-menu',3,6,'06cb8b02-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-06 17:53:44',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbbd04-23b0-11ee-a1af-047c1649ee1e','设置角色数据权限','system:permission:assign-role-data-scope',3,7,'06cb8b02-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-06 17:56:31',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbbe0e-23b0-11ee-a1af-047c1649ee1e','设置用户角色','system:permission:assign-user-role',3,8,'06cb8b02-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-07 10:23:28',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbbe96-23b0-11ee-a1af-047c1649ee1e','获得 Redis 监控信息','infra:redis:get-monitor-info',3,1,'06cbe743-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-26 01:02:31',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbbf28-23b0-11ee-a1af-047c1649ee1e','获得 Redis Key 列表','infra:redis:get-key-list',3,2,'06cbe743-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-01-26 01:02:52',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbbfc2-23b0-11ee-a1af-047c1649ee1e','通知公告','',2,8,'06cb82c5-23b0-11ee-a1af-047c1649ee1e','notice','message','system/notice/index','SystemNotice',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc07f-23b0-11ee-a1af-047c1649ee1e','任务触发','infra:job:trigger',3,8,'06cbccce-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-02-07 13:03:10',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc10a-23b0-11ee-a1af-047c1649ee1e','数据库文档','',2,4,'06cc2dab-23b0-11ee-a1af-047c1649ee1e','db-doc','table','infra/dbDoc/index','InfraDBDoc',0,_binary '',_binary '',_binary '',1,'2021-02-08 01:41:47',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc18f-23b0-11ee-a1af-047c1649ee1e','监控平台','',2,13,'06cc09ca-23b0-11ee-a1af-047c1649ee1e','skywalking','eye-open','infra/skywalking/index','InfraSkyWalking',0,_binary '',_binary '',_binary '',1,'2021-02-08 20:41:31',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc213-23b0-11ee-a1af-047c1649ee1e','访问日志','',2,1,'06cbc3b5-23b0-11ee-a1af-047c1649ee1e','api-access-log','log','infra/apiAccessLog/index','InfraApiAccessLog',0,_binary '',_binary '',_binary '',1,'2021-02-26 01:32:59',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc29f-23b0-11ee-a1af-047c1649ee1e','审计日志','',1,9,'06cc0a5b-23b0-11ee-a1af-047c1649ee1e','operate-log','log','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc32b-23b0-11ee-a1af-047c1649ee1e','日志导出','infra:api-access-log:export',3,2,'06cbc213-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-02-26 01:32:59',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc3b5-23b0-11ee-a1af-047c1649ee1e','API 日志','',2,8,'06cc0a5b-23b0-11ee-a1af-047c1649ee1e','api-access-log','log','','',0,_binary '',_binary '',_binary '',1,'2021-02-26 02:18:24',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc43c-23b0-11ee-a1af-047c1649ee1e','错误日志','infra:api-error-log:query',2,2,'06cbc3b5-23b0-11ee-a1af-047c1649ee1e','api-error-log','log','infra/apiErrorLog/index','InfraApiErrorLog',0,_binary '',_binary '',_binary '',1,'2021-02-26 07:53:20',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc4d5-23b0-11ee-a1af-047c1649ee1e','日志处理','infra:api-error-log:update-status',3,2,'06cbc43c-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-02-26 07:53:20',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc55a-23b0-11ee-a1af-047c1649ee1e','日志导出','infra:api-error-log:export',3,3,'06cbc43c-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-02-26 07:53:20',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc5e1-23b0-11ee-a1af-047c1649ee1e','任务查询','infra:job:query',3,1,'06cbccce-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-03-10 01:26:19',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc662-23b0-11ee-a1af-047c1649ee1e','日志查询','infra:api-access-log:query',3,1,'06cbc213-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-03-10 01:28:04',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc6e6-23b0-11ee-a1af-047c1649ee1e','日志查询','infra:api-error-log:query',3,1,'06cbc43c-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-03-10 01:29:09',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc776-23b0-11ee-a1af-047c1649ee1e','令牌管理','',2,2,'06cbf410-23b0-11ee-a1af-047c1649ee1e','token','online','infra/oauth2/token/index','SystemTokenClient',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc7fa-23b0-11ee-a1af-047c1649ee1e','文件列表','',2,5,'06cbecc4-23b0-11ee-a1af-047c1649ee1e','file','upload','infra/file/index','InfraFile',0,_binary '',_binary '',_binary '',1,'2021-03-12 20:16:20',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc87e-23b0-11ee-a1af-047c1649ee1e','文件查询','infra:file:query',3,1,'06cbc7fa-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-03-12 20:16:20',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc90d-23b0-11ee-a1af-047c1649ee1e','文件删除','infra:file:delete',3,4,'06cbc7fa-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-03-12 20:16:20',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbc992-23b0-11ee-a1af-047c1649ee1e','短信管理','',1,11,'06cc2c3e-23b0-11ee-a1af-047c1649ee1e','sms','validCode','','',0,_binary '',_binary '',_binary '',1,'2021-04-05 01:10:16',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbca1d-23b0-11ee-a1af-047c1649ee1e','短信渠道','',2,0,'06cbc992-23b0-11ee-a1af-047c1649ee1e','sms-channel','phone','infra/sms/channel/index','SystemSmsChannel',0,_binary '',_binary '',_binary '',1,'2021-04-01 11:07:15',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcaaa-23b0-11ee-a1af-047c1649ee1e','短信渠道查询','infra:sms-channel:query',3,1,'06cbca1d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 11:07:15',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcb35-23b0-11ee-a1af-047c1649ee1e','短信渠道创建','infra:sms-channel:create',3,2,'06cbca1d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 11:07:15',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcbba-23b0-11ee-a1af-047c1649ee1e','短信渠道更新','infra:sms-channel:update',3,3,'06cbca1d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 11:07:15',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcc46-23b0-11ee-a1af-047c1649ee1e','短信渠道删除','infra:sms-channel:delete',3,4,'06cbca1d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 11:07:15',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbccce-23b0-11ee-a1af-047c1649ee1e','定时任务','',2,12,'06cc093d-23b0-11ee-a1af-047c1649ee1e','job','job','infra/job/index','InfraJob',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcd5f-23b0-11ee-a1af-047c1649ee1e','短信模板','',2,1,'06cbc992-23b0-11ee-a1af-047c1649ee1e','sms-template','phone','infra/sms/template/index','SystemSmsTemplate',0,_binary '',_binary '',_binary '',1,'2021-04-01 17:35:17',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcdeb-23b0-11ee-a1af-047c1649ee1e','短信模板查询','infra:sms-template:query',3,1,'06cbcd5f-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 17:35:17',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbce74-23b0-11ee-a1af-047c1649ee1e','短信模板创建','infra:sms-template:create',3,2,'06cbcd5f-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 17:35:17',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcef7-23b0-11ee-a1af-047c1649ee1e','短信模板更新','infra:sms-template:update',3,3,'06cbcd5f-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 17:35:17',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcf78-23b0-11ee-a1af-047c1649ee1e','短信模板删除','infra:sms-template:delete',3,4,'06cbcd5f-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 17:35:17',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbcfff-23b0-11ee-a1af-047c1649ee1e','短信模板导出','infra:sms-template:export',3,5,'06cbcd5f-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-01 17:35:17',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbd085-23b0-11ee-a1af-047c1649ee1e','发送测试短信','infra:sms-template:send-sms',3,6,'06cbcd5f-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-11 00:26:40',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbd10a-23b0-11ee-a1af-047c1649ee1e','短信日志','',2,2,'06cbc992-23b0-11ee-a1af-047c1649ee1e','sms-log','phone','infra/sms/log/index','SystemSmsLog',0,_binary '',_binary '',_binary '',1,'2021-04-11 08:37:05',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbd190-23b0-11ee-a1af-047c1649ee1e','短信日志查询','infra:sms-log:query',3,1,'06cbd10a-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-11 08:37:05',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbd234-23b0-11ee-a1af-047c1649ee1e','短信日志导出','infra:sms-log:export',3,5,'06cbd10a-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-11 08:37:05',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe2a0-23b0-11ee-a1af-047c1649ee1e','MySQL 监控','',2,9,'06cc09ca-23b0-11ee-a1af-047c1649ee1e','druid','druid','infra/druid/index','InfraDruid',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe36e-23b0-11ee-a1af-047c1649ee1e','错误码管理','',2,12,'06cc093d-23b0-11ee-a1af-047c1649ee1e','error-code','code','infra/errorCode/index','SystemErrorCode',0,_binary '',_binary '',_binary '',1,'2021-04-13 21:46:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe403-23b0-11ee-a1af-047c1649ee1e','错误码查询','infra:error-code:query',3,1,'06cbe36e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-13 21:46:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe48d-23b0-11ee-a1af-047c1649ee1e','错误码创建','infra:error-code:create',3,2,'06cbe36e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-13 21:46:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe51d-23b0-11ee-a1af-047c1649ee1e','错误码更新','infra:error-code:update',3,3,'06cbe36e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-13 21:46:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe59e-23b0-11ee-a1af-047c1649ee1e','错误码删除','infra:error-code:delete',3,4,'06cbe36e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-13 21:46:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe63d-23b0-11ee-a1af-047c1649ee1e','错误码导出','infra:error-code:export',3,5,'06cbe36e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2021-04-13 21:46:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe6be-23b0-11ee-a1af-047c1649ee1e','Java 监控','',2,11,'06cc09ca-23b0-11ee-a1af-047c1649ee1e','admin-server','server','infra/server/index','InfraAdminServer',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe743-23b0-11ee-a1af-047c1649ee1e','Redis 监控','',2,10,'06cc09ca-23b0-11ee-a1af-047c1649ee1e','redis','redis','infra/redis/index','InfraRedis',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe7cf-23b0-11ee-a1af-047c1649ee1e','表单构建','infra:build:list',2,2,'06cc2e57-23b0-11ee-a1af-047c1649ee1e','build','build','infra/build/index','InfraBuild',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe8ee-23b0-11ee-a1af-047c1649ee1e','系统接口','infra:swagger:list',2,3,'06cc2dab-23b0-11ee-a1af-047c1649ee1e','swagger','swagger','infra/swagger/index','InfraSwagger',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbe979-23b0-11ee-a1af-047c1649ee1e','文件配置','',2,0,'06cbecc4-23b0-11ee-a1af-047c1649ee1e','file-config','config','infra/fileConfig/index','InfraFileConfig',0,_binary '',_binary '',_binary '',1,'2022-03-15 14:35:28',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbea06-23b0-11ee-a1af-047c1649ee1e','文件配置查询','infra:file-config:query',3,1,'06cbe979-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-03-15 14:35:28',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbea9e-23b0-11ee-a1af-047c1649ee1e','文件配置创建','infra:file-config:create',3,2,'06cbe979-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-03-15 14:35:28',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbeb28-23b0-11ee-a1af-047c1649ee1e','文件配置更新','infra:file-config:update',3,3,'06cbe979-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-03-15 14:35:28',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbebb8-23b0-11ee-a1af-047c1649ee1e','文件配置删除','infra:file-config:delete',3,4,'06cbe979-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-03-15 14:35:28',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbec41-23b0-11ee-a1af-047c1649ee1e','文件配置导出','infra:file-config:export',3,5,'06cbe979-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-03-15 14:35:28',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbecc4-23b0-11ee-a1af-047c1649ee1e','文件管理','',2,5,'06cc2c3e-23b0-11ee-a1af-047c1649ee1e','file','download','','',0,_binary '',_binary '',_binary '',1,'2022-03-16 23:47:40',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbed4d-23b0-11ee-a1af-047c1649ee1e','敏感词管理','',2,13,'06cc093d-23b0-11ee-a1af-047c1649ee1e','sensitive-word','education','infra/sensitiveWord/index','SystemSensitiveWord',0,_binary '',_binary '',_binary '',1,'2022-04-07 16:55:03',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbeddd-23b0-11ee-a1af-047c1649ee1e','敏感词查询','infra:sensitive-word:query',3,1,'06cbed4d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-07 16:55:03',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbee61-23b0-11ee-a1af-047c1649ee1e','敏感词创建','infra:sensitive-word:create',3,2,'06cbed4d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-07 16:55:03',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbeefb-23b0-11ee-a1af-047c1649ee1e','敏感词更新','infra:sensitive-word:update',3,3,'06cbed4d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-07 16:55:03',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbef7d-23b0-11ee-a1af-047c1649ee1e','敏感词删除','infra:sensitive-word:delete',3,4,'06cbed4d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-07 16:55:03',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf005-23b0-11ee-a1af-047c1649ee1e','敏感词导出','infra:sensitive-word:export',3,5,'06cbed4d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-07 16:55:03',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf084-23b0-11ee-a1af-047c1649ee1e','数据源配置','',2,1,'06cc2c3e-23b0-11ee-a1af-047c1649ee1e','data-source-config','rate','infra/dataSourceConfig/index','InfraDataSourceConfig',0,_binary '',_binary '',_binary '',1,'2022-04-27 14:37:32',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf11a-23b0-11ee-a1af-047c1649ee1e','数据源配置查询','infra:data-source-config:query',3,1,'06cbf084-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-27 14:37:32',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf1aa-23b0-11ee-a1af-047c1649ee1e','数据源配置创建','infra:data-source-config:create',3,2,'06cbf084-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-27 14:37:32',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf23c-23b0-11ee-a1af-047c1649ee1e','数据源配置更新','infra:data-source-config:update',3,3,'06cbf084-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-27 14:37:32',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf2c7-23b0-11ee-a1af-047c1649ee1e','数据源配置删除','infra:data-source-config:delete',3,4,'06cbf084-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-27 14:37:32',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf34f-23b0-11ee-a1af-047c1649ee1e','数据源配置导出','infra:data-source-config:export',3,5,'06cbf084-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-04-27 14:37:32',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf410-23b0-11ee-a1af-047c1649ee1e','OAuth','',1,10,'06cc2c3e-23b0-11ee-a1af-047c1649ee1e','oauth','people','','',0,_binary '',_binary '',_binary '',1,'2022-05-09 23:38:17',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf492-23b0-11ee-a1af-047c1649ee1e','应用管理','',2,0,'06cbf410-23b0-11ee-a1af-047c1649ee1e','oauth2/application','tool','infra/oauth2/client/index','SystemOAuth2Client',0,_binary '',_binary '',_binary '',1,'2022-05-10 16:26:33',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf51f-23b0-11ee-a1af-047c1649ee1e','客户端查询','infra:oauth2-client:query',3,1,'06cbf492-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-05-10 16:26:33',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf5a5-23b0-11ee-a1af-047c1649ee1e','客户端创建','infra:oauth2-client:create',3,2,'06cbf492-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-05-10 16:26:33',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf628-23b0-11ee-a1af-047c1649ee1e','客户端更新','infra:oauth2-client:update',3,3,'06cbf492-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-05-10 16:26:33',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf6b0-23b0-11ee-a1af-047c1649ee1e','客户端删除','infra:oauth2-client:delete',3,4,'06cbf492-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2022-05-10 16:26:33',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf738-23b0-11ee-a1af-047c1649ee1e','报表管理','',1,40,'','/report','chart','','',0,_binary '',_binary '',_binary '',1,'2022-07-10 20:22:15',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf7c1-23b0-11ee-a1af-047c1649ee1e','报表设计器','',2,1,'06cbf738-23b0-11ee-a1af-047c1649ee1e','jimu-report','example','report/jmreport/index','GoView',0,_binary '',_binary '',_binary '',1,'2022-07-10 20:26:36',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf85b-23b0-11ee-a1af-047c1649ee1e','基础设施','',1,20,'','/infra','monitor','','',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf8e4-23b0-11ee-a1af-047c1649ee1e','地区管理','',2,14,'06cc2c3e-23b0-11ee-a1af-047c1649ee1e','area','row','infra/area/index','SystemArea',0,_binary '',_binary '',_binary '',1,'2022-12-23 17:35:05',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf96e-23b0-11ee-a1af-047c1649ee1e','邮箱管理','',2,11,'06cc2c3e-23b0-11ee-a1af-047c1649ee1e','mail','email','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 17:27:44',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbf9f6-23b0-11ee-a1af-047c1649ee1e','邮箱账号','',2,0,'06cbf96e-23b0-11ee-a1af-047c1649ee1e','mail-account','user','infra/mail/account/index','SystemMailAccount',0,_binary '',_binary '',_binary '',1,'2023-01-25 09:33:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfa7e-23b0-11ee-a1af-047c1649ee1e','账号查询','infra:mail-account:query',3,1,'06cbf9f6-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 09:33:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfb06-23b0-11ee-a1af-047c1649ee1e','账号创建','infra:mail-account:create',3,2,'06cbf9f6-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 09:33:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfb8e-23b0-11ee-a1af-047c1649ee1e','账号更新','infra:mail-account:update',3,3,'06cbf9f6-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 09:33:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfc16-23b0-11ee-a1af-047c1649ee1e','账号删除','infra:mail-account:delete',3,4,'06cbf9f6-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 09:33:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfc9c-23b0-11ee-a1af-047c1649ee1e','邮件模版','',2,0,'06cbf96e-23b0-11ee-a1af-047c1649ee1e','mail-template','education','infra/mail/template/index','SystemMailTemplate',0,_binary '',_binary '',_binary '',1,'2023-01-25 12:05:31',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfd38-23b0-11ee-a1af-047c1649ee1e','模版查询','infra:mail-template:query',3,1,'06cbfc9c-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 12:05:31',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfdc1-23b0-11ee-a1af-047c1649ee1e','模版创建','infra:mail-template:create',3,2,'06cbfc9c-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 12:05:31',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfe42-23b0-11ee-a1af-047c1649ee1e','模版更新','infra:mail-template:update',3,3,'06cbfc9c-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 12:05:31',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbfec7-23b0-11ee-a1af-047c1649ee1e','模版删除','infra:mail-template:delete',3,4,'06cbfc9c-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-25 12:05:31',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbff4d-23b0-11ee-a1af-047c1649ee1e','邮件记录','',2,0,'06cbf96e-23b0-11ee-a1af-047c1649ee1e','mail-log','log','infra/mail/log/index','SystemMailLog',0,_binary '',_binary '',_binary '',1,'2023-01-26 02:16:50',1,'2023-07-16 16:09:03',_binary '\0'),
('06cbffd2-23b0-11ee-a1af-047c1649ee1e','日志查询','infra:mail-log:query',3,1,'06cbff4d-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-26 02:16:50',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc0065-23b0-11ee-a1af-047c1649ee1e','发送测试邮件','infra:mail-template:send-mail',3,5,'06cbfc9c-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-26 23:29:15',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc00eb-23b0-11ee-a1af-047c1649ee1e','站内信管理','',1,11,'06cb82c5-23b0-11ee-a1af-047c1649ee1e','notify','message','','',0,_binary '',_binary '',_binary '',1,'2023-01-28 10:25:18',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc017e-23b0-11ee-a1af-047c1649ee1e','模板管理','',2,0,'06cc00eb-23b0-11ee-a1af-047c1649ee1e','notify-template','education','system/notify/template/index','SystemNotifyTemplate',0,_binary '',_binary '',_binary '',1,'2023-01-28 02:26:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc020a-23b0-11ee-a1af-047c1649ee1e','站内信模板查询','system:notify-template:query',3,1,'06cc017e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-28 02:26:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc029d-23b0-11ee-a1af-047c1649ee1e','站内信模板创建','system:notify-template:create',3,2,'06cc017e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-28 02:26:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc0322-23b0-11ee-a1af-047c1649ee1e','站内信模板更新','system:notify-template:update',3,3,'06cc017e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-28 02:26:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc03ab-23b0-11ee-a1af-047c1649ee1e','站内信模板删除','system:notify-template:delete',3,4,'06cc017e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-28 02:26:42',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc0435-23b0-11ee-a1af-047c1649ee1e','发送测试站内信','system:notify-template:send-notify',3,5,'06cc017e-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-28 10:54:43',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc04b9-23b0-11ee-a1af-047c1649ee1e','消息记录','',2,0,'06cc00eb-23b0-11ee-a1af-047c1649ee1e','notify-message','edit','system/notify/message/index','SystemNotifyMessage',0,_binary '',_binary '',_binary '',1,'2023-01-28 04:28:22',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc054a-23b0-11ee-a1af-047c1649ee1e','站内信消息查询','system:notify-message:query',3,1,'06cc04b9-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-01-28 04:28:22',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc05da-23b0-11ee-a1af-047c1649ee1e','大屏设计器','',2,2,'06cbf738-23b0-11ee-a1af-047c1649ee1e','go-view','dashboard','report/goview/index','JimuReport',0,_binary '',_binary '',_binary '',1,'2023-02-07 00:03:19',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc065f-23b0-11ee-a1af-047c1649ee1e','创建项目','report:go-view-project:create',3,1,'06cc05da-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-02-07 19:25:14',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc06e7-23b0-11ee-a1af-047c1649ee1e','更新项目','report:go-view-project:delete',3,2,'06cc05da-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-02-07 19:25:34',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc0770-23b0-11ee-a1af-047c1649ee1e','查询项目','report:go-view-project:query',3,0,'06cc05da-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-02-07 19:25:53',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc07f5-23b0-11ee-a1af-047c1649ee1e','使用 SQL 查询数据','report:go-view-data:get-by-sql',3,3,'06cc05da-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-02-07 19:26:15',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc087a-23b0-11ee-a1af-047c1649ee1e','使用 HTTP 查询数据','report:go-view-data:get-by-http',3,4,'06cc05da-23b0-11ee-a1af-047c1649ee1e','','','','',0,_binary '',_binary '',_binary '',1,'2023-02-07 19:26:35',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc093d-23b0-11ee-a1af-047c1649ee1e','基础数据','',2,2,'06cbf85b-23b0-11ee-a1af-047c1649ee1e','data','','','',0,_binary '',_binary '',_binary '',0,'2023-07-16 16:09:02',0,'2023-07-16 16:09:03',_binary '\0'),
('06cc09ca-23b0-11ee-a1af-047c1649ee1e','监控','',2,3,'06cbf85b-23b0-11ee-a1af-047c1649ee1e','monitor','','','',0,_binary '',_binary '',_binary '',0,'2023-07-16 16:09:02',0,'2023-07-16 16:09:03',_binary '\0'),
('06cc0a5b-23b0-11ee-a1af-047c1649ee1e','日志','',1,5,'06cbf85b-23b0-11ee-a1af-047c1649ee1e','log','','','',0,_binary '',_binary '',_binary '',0,'2023-07-16 16:09:02',0,'2023-07-16 16:09:03',_binary '\0'),
('06cc2c3e-23b0-11ee-a1af-047c1649ee1e','基础管理','',1,6,'06cbf85b-23b0-11ee-a1af-047c1649ee1e','manage','','','',0,_binary '',_binary '',_binary '',0,'2023-07-16 16:09:02',0,'2023-07-16 16:09:03',_binary '\0'),
('06cc2dab-23b0-11ee-a1af-047c1649ee1e','文档','',1,4,'06cbf85b-23b0-11ee-a1af-047c1649ee1e','document','','','',0,_binary '',_binary '',_binary '',0,'2023-07-16 16:09:02',0,'2023-07-16 16:09:03',_binary '\0'),
('06cc2e57-23b0-11ee-a1af-047c1649ee1e','代码生成','',2,1,'06cbf85b-23b0-11ee-a1af-047c1649ee1e','code-create','','','',0,_binary '',_binary '',_binary '',0,'2023-07-16 16:09:02',0,'2023-07-16 16:09:03',_binary '\0'),
('06cc2ee0-23b0-11ee-a1af-047c1649ee1e','数据库表','',2,3,'06cc2e57-23b0-11ee-a1af-047c1649ee1e','infra/codegen/database-table','','infra/codegen/DatabaseTable','DatabaseTable',0,_binary '',_binary '',_binary '',0,'2023-07-16 16:09:02',0,'2023-07-16 16:09:03',_binary '\0'),
('06cc2f77-23b0-11ee-a1af-047c1649ee1e','操作日志','',2,1,'06cbc29f-23b0-11ee-a1af-047c1649ee1e','operate-log','form','infra/operatelog/index','SystemOperateLog',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('06cc302f-23b0-11ee-a1af-047c1649ee1e','登录日志','',2,2,'06cbc29f-23b0-11ee-a1af-047c1649ee1e','login-log','logininfor','infra/loginlog/index','SystemLoginLog',0,_binary '',_binary '',_binary '',1,'2021-01-05 17:03:48',1,'2023-07-16 16:09:03',_binary '\0'),
('71e08d4c-ea02-494d-96b9-4209d0a2d3cc','接口模块','',2,4,'06cc2e57-23b0-11ee-a1af-047c1649ee1e','interface-module','','infra/codegen/InterfaceModule','InterfaceModule',0,_binary '',_binary '',_binary '',1,'2023-07-21 20:11:50',1,'2023-07-22 08:09:56',_binary '\0'),
('d7cbe9a4-b2c0-4007-ace6-130a9d51b882','系统接口','',2,5,'06cc2e57-23b0-11ee-a1af-047c1649ee1e','interface','','infra/codegen/Interface','Interface',0,_binary '',_binary '',_binary '',1,'2023-07-21 20:11:50',1,'2023-07-22 08:09:56',_binary '\0');
/*!40000 ALTER TABLE `system_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_notice`
--

DROP TABLE IF EXISTS `system_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '公告标题',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告内容',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '公告类型（1通知 2公告）',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_notice`
--
--
-- Table structure for table `system_notify_message`
--

DROP TABLE IF EXISTS `system_notify_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_notify_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_type` tinyint NOT NULL DEFAULT '0' COMMENT '用户类型',
  `template_id` bigint NOT NULL DEFAULT '0' COMMENT '模版编号',
  `template_code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模板编码',
  `template_nickname` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模版发送人名称',
  `template_content` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模版内容',
  `template_type` int NOT NULL DEFAULT '0' COMMENT '模版类型',
  `template_params` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模版参数',
  `read_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已读',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='站内信消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_notify_message`
--
--
-- Table structure for table `system_notify_template`
--

DROP TABLE IF EXISTS `system_notify_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_notify_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模板名称',
  `code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模版编码',
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '发送人名称',
  `content` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模版内容',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '类型',
  `params` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '参数数组',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='站内信模板表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_notify_template`
--

LOCK TABLES `system_notify_template` WRITE;
/*!40000 ALTER TABLE `system_notify_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_notify_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth2_access_token`
--

DROP TABLE IF EXISTS `system_oauth2_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_oauth2_access_token` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `access_token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问令牌',
  `refresh_token` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '刷新令牌',
  `client_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `scopes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '授权范围',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1061 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth2 访问令牌';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth2_access_token`
--
--
-- Table structure for table `system_oauth2_approve`
--

DROP TABLE IF EXISTS `system_oauth2_approve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_oauth2_approve` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `client_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `scope` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '授权范围',
  `approved` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否接受',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth2 批准表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth2_approve`
--

LOCK TABLES `system_oauth2_approve` WRITE;
/*!40000 ALTER TABLE `system_oauth2_approve` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_oauth2_approve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth2_client`
--

DROP TABLE IF EXISTS `system_oauth2_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_oauth2_client` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `client_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `secret` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端密钥',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用名',
  `logo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '应用图标',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '应用描述',
  `status` tinyint NOT NULL COMMENT '状态',
  `access_token_validity_seconds` int NOT NULL COMMENT '访问令牌的有效期',
  `refresh_token_validity_seconds` int NOT NULL COMMENT '刷新令牌的有效期',
  `redirect_uris` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '可重定向的 URI 地址',
  `authorized_grant_types` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '授权类型',
  `scopes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '授权范围',
  `auto_approve_scopes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '自动通过的授权范围',
  `authorities` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限',
  `resource_ids` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源',
  `additional_information` varchar(4096) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '附加信息',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth2 客户端表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth2_client`
--

LOCK TABLES `system_oauth2_client` WRITE;
/*!40000 ALTER TABLE `system_oauth2_client` DISABLE KEYS */;
INSERT INTO `system_oauth2_client` VALUES 
(1,'default','admin123','芋道源码','http://test.yudao.iocoder.cn/a5e2e244368878a366b516805a4aabf1.png','我是描述',0,1800,43200,'[\"https://www.iocoder.cn\",\"https://doc.iocoder.cn\"]','[\"password\",\"authorization_code\",\"implicit\",\"refresh_token\"]','[\"user.read\",\"user.write\"]','[]','[\"user.read\",\"user.write\"]','[]','{}',1,'2022-05-11 21:47:12',1,'2022-07-05 16:23:52',_binary '\0'),
(40,'test','test2','biubiu','http://test.yudao.iocoder.cn/277a899d573723f1fcdfb57340f00379.png',NULL,0,1800,43200,'[\"https://www.iocoder.cn\"]','[\"password\",\"authorization_code\",\"implicit\"]','[\"user_info\",\"projects\"]','[\"user_info\"]','[]','[]','{}',1,'2022-05-12 00:28:20',1,'2022-06-19 00:26:13',_binary '\0'),
(41,'yudao-sso-demo-by-code','test','基于授权码模式，如何实现 SSO 单点登录？','http://test.yudao.iocoder.cn/fe4ed36596adad5120036ef61a6d0153654544d44af8dd4ad3ffe8f759933d6f.png',NULL,0,1800,43200,'[\"http://127.0.0.1:18080\"]','[\"authorization_code\",\"refresh_token\"]','[\"user.read\",\"user.write\"]','[]','[]','[]',NULL,1,'2022-09-29 13:28:31',1,'2022-09-29 13:28:31',_binary '\0'),
(42,'yudao-sso-demo-by-password','test','基于密码模式，如何实现 SSO 单点登录？','http://test.yudao.iocoder.cn/604bdc695e13b3b22745be704d1f2aa8ee05c5f26f9fead6d1ca49005afbc857.jpeg',NULL,0,1800,43200,'[\"http://127.0.0.1:18080\"]','[\"password\",\"refresh_token\"]','[\"user.read\",\"user.write\"]','[]','[]','[]',NULL,1,'2022-10-04 17:40:16',1,'2022-10-04 20:31:21',_binary '\0');
/*!40000 ALTER TABLE `system_oauth2_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth2_code`
--

DROP TABLE IF EXISTS `system_oauth2_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_oauth2_code` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `code` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '授权码',
  `client_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `scopes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '授权范围',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `redirect_uri` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '可重定向的 URI 地址',
  `state` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '状态',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth2 授权码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth2_code`
--

LOCK TABLES `system_oauth2_code` WRITE;
/*!40000 ALTER TABLE `system_oauth2_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_oauth2_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_oauth2_refresh_token`
--

DROP TABLE IF EXISTS `system_oauth2_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_oauth2_refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `refresh_token` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '刷新令牌',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `client_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '客户端编号',
  `scopes` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '授权范围',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth2 刷新令牌';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_oauth2_refresh_token`
--
--
-- Table structure for table `system_operate_log`
--

DROP TABLE IF EXISTS `system_operate_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_operate_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `trace_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL DEFAULT '0' COMMENT '用户类型',
  `module` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模块标题',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作名',
  `type` bigint NOT NULL DEFAULT '0' COMMENT '操作分类',
  `content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
  `exts` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '{}' COMMENT '拓展字段',
  `request_method` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求方法名',
  `request_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '请求地址',
  `user_ip` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户 IP',
  `user_agent` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '浏览器 UA',
  `java_method` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'Java 方法名',
  `java_method_args` varchar(8000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'Java 方法的参数',
  `start_time` datetime NOT NULL COMMENT '操作时间',
  `duration` int NOT NULL DEFAULT '0' COMMENT '执行时长',
  `result_code` int NOT NULL DEFAULT '0' COMMENT '结果码',
  `result_msg` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '结果提示',
  `result_data` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '结果数据',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=511 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_operate_log`
--

--
-- Table structure for table `system_post`
--

DROP TABLE IF EXISTS `system_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `code` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位编码',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '岗位名称',
  `sort` int NOT NULL COMMENT '显示顺序',
  `status` tinyint NOT NULL COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_post`
--

LOCK TABLES `system_post` WRITE;
/*!40000 ALTER TABLE `system_post` DISABLE KEYS */;
INSERT INTO `system_post` VALUES 
(1,'ceo','董事长',1,0,'',1,'2021-01-06 17:03:48',1,'2023-02-11 15:19:04',_binary '\0'),
(2,'se','项目经理',2,0,'',1,'2021-01-05 17:03:48',1,'2021-12-12 10:47:47',_binary '\0'),
(4,'user','普通员工',4,0,'111',1,'2021-01-05 17:03:48',1,'2023-02-11 15:19:00',_binary '\0');
/*!40000 ALTER TABLE `system_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_role`
--

DROP TABLE IF EXISTS `system_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint NOT NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `data_scope_dept_ids` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '[]' COMMENT '数据范围(指定部门数组)',
  `status` tinyint NOT NULL COMMENT '角色状态（0正常 1停用）',
  `type` tinyint NOT NULL COMMENT '角色类型',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_role`
--

LOCK TABLES `system_role` WRITE;
/*!40000 ALTER TABLE `system_role` DISABLE KEYS */;
INSERT INTO `system_role` VALUES 
(1,'超级管理员','super_admin',1,1,'[]',0,1,'超级管理员',1,'2021-01-05 17:03:48',1,'2022-02-22 05:08:21',_binary '\0');
/*!40000 ALTER TABLE `system_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_role_menu`
--

DROP TABLE IF EXISTS `system_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单ID',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_role_menu`
--

LOCK TABLES `system_role_menu` WRITE;
/*!40000 ALTER TABLE `system_role_menu` DISABLE KEYS */;
INSERT INTO `system_role_menu` VALUES 
(1,1,'06cbc3b5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(2,1,'06cbe6be-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(3,1,'06cbe2a0-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(4,1,'06cbf410-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(5,1,'06cbe743-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(6,1,'06cc2e57-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(7,1,'06cbe855-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(8,1,'06cb9ee5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(9,1,'06cb9f62-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(10,1,'06cbc776-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(11,1,'06cba0ef-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(12,1,'06cba170-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(13,1,'06cba277-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(14,1,'06cba06d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(15,1,'06cbc5e1-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(16,1,'06cbc07f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(17,1,'06cc087a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(18,1,'06cc07f5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(19,1,'06cb9b30-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(20,1,'06cb9bb4-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(21,1,'06cb9ab3-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(22,1,'06cb9a28-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(23,1,'06cc065f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(24,1,'06cbd085-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(25,1,'06cc0435-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(26,1,'06cc0065-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(27,1,'06cbf8e4-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(28,1,'06cc093d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(29,1,'06cc2c3e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(30,1,'06cbf85b-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(31,1,'06cc05da-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(32,1,'06cb955d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(33,1,'06cb9611-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(34,1,'06cb9720-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(35,1,'06cb94dd-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(36,1,'06cb945e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(37,1,'06cb9fe7-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(38,1,'06cbccce-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(39,1,'06cbc29f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(40,1,'06cbf5a5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(41,1,'06cbf6b0-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(42,1,'06cbf628-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(43,1,'06cbf51f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(44,1,'06cbba2c-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(45,1,'06cb92d9-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(46,1,'06cb935d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(47,1,'06cb93db-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(48,1,'06cb9259-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(49,1,'06cb91d2-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(50,1,'06cb9c34-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(51,1,'06cbf492-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(52,1,'06cbf738-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(53,1,'06cbf7c1-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(54,1,'71e08d4c-ea02-494d-96b9-4209d0a2d3cc',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(55,1,'06cc2f77-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(56,1,'06cb9cba-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(57,1,'06cbee61-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(58,1,'06cbef7d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(59,1,'06cbf005-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(60,1,'06cbeefb-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(61,1,'06cbeddd-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(62,1,'06cbed4d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(63,1,'06cbc10a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(64,1,'06cc2ee0-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(65,1,'06cbf084-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(66,1,'06cbf1aa-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(67,1,'06cbf2c7-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(68,1,'06cbf34f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(69,1,'06cbf23c-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(70,1,'06cbf11a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(71,1,'06cbc7fa-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(72,1,'06cbc90d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(73,1,'06cbc87e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(74,1,'06cbecc4-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(75,1,'06cbe979-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(76,1,'06cbea9e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(77,1,'06cbebb8-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(78,1,'06cbec41-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(79,1,'06cbeb28-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(80,1,'06cbea06-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(81,1,'06cc2dab-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(82,1,'06cc0a5b-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(83,1,'06cbc4d5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(84,1,'06cbc32b-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(85,1,'06cbc55a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(86,1,'06cb9d41-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(87,1,'06cb9e55-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(88,1,'06cbc662-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(89,1,'06cbc6e6-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(90,1,'06cbffd2-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(91,1,'06cc06e7-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(92,1,'06cc0770-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(93,1,'06cc017e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(94,1,'06cbfdc1-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(95,1,'06cbfec7-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(96,1,'06cbfe42-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(97,1,'06cbfd38-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(98,1,'06cc04b9-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(99,1,'06cba1f5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(100,1,'06cbbbdd-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(101,1,'06cba2fb-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(102,1,'06cbb981-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(103,1,'06cb86c3-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(104,1,'06cb8752-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(105,1,'06cb886c-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(106,1,'06cb87ea-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(107,1,'06cb863f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(108,1,'06cb859a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(109,1,'06cb84a7-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(110,1,'06cc302f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(111,1,'06cb9dd0-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(112,1,'06cc09ca-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(113,1,'06cbc18f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(114,1,'06cbd10a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(115,1,'06cbd234-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(116,1,'06cbd190-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(117,1,'06cbcd5f-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(118,1,'06cbce74-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(119,1,'06cbcf78-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(120,1,'06cbcfff-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(121,1,'06cbcef7-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(122,1,'06cbcdeb-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(123,1,'06cbca1d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(124,1,'06cbcb35-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(125,1,'06cbcc46-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(126,1,'06cbcbba-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(127,1,'06cbcaaa-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(128,1,'06cbc992-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(129,1,'06cc029d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(130,1,'06cc03ab-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(131,1,'06cc0322-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(132,1,'06cc020a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(133,1,'06cc054a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(134,1,'06cc00eb-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(135,1,'06cbe8ee-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(136,1,'d7cbe9a4-b2c0-4007-ace6-130a9d51b882',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(137,1,'06cb82c5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(138,1,'06cbbf28-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(139,1,'06cbbe96-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(140,1,'06cb8e2e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(141,1,'06cb8eb4-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(142,1,'06cb8db0-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(143,1,'06cb8d2b-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(144,1,'06cb90b9-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(145,1,'06cbe7cf-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(146,1,'06cb8b94-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(147,1,'06cb8c1a-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(148,1,'06cb8ca6-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(149,1,'06cb8a7d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(150,1,'06cb89f5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(151,1,'06cb8b02-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(152,1,'06cbbe0e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(153,1,'06cbbd04-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(154,1,'06cbbc63-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(155,1,'06cbc213-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(156,1,'06cbfb06-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(157,1,'06cbfc16-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(158,2,'06cbfb8e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-10-13 18:11:50',_binary '\0'),
(159,1,'06cbfa7e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(160,1,'06cbbfc2-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(161,1,'06cbfc9c-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(162,1,'06cbff4d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(163,1,'06cbf96e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(164,1,'06cbf9f6-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(165,1,'06cb9031-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(166,1,'06cb9156-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(167,1,'06cb8faf-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(168,1,'06cb8f32-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(169,1,'06cb9698-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(170,1,'06cb98a4-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(171,1,'06cb9929-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(172,1,'06cb99ad-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(173,1,'06cb9829-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(174,1,'06cb97a7-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(175,1,'06cbbb47-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(176,1,'06cb88eb-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(177,1,'06cbc43c-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(178,1,'06cbe48d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(179,1,'06cbe59e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(180,1,'06cbe63d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(181,1,'06cbe51d-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(182,1,'06cbe403-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(183,1,'06cbe36e-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0'),
(184,1,'06cbbac5-23b0-11ee-a1af-047c1649ee1e',0,'2023-07-24 08:44:57',0,'2023-07-24 08:44:57',_binary '\0');
/*!40000 ALTER TABLE `system_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_sensitive_word`
--

DROP TABLE IF EXISTS `system_sensitive_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_sensitive_word` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '敏感词',
  `description` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `tags` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标签数组',
  `status` tinyint NOT NULL COMMENT '状态',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='敏感词';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_sensitive_word`
--
--
-- Table structure for table `system_sms_channel`
--

DROP TABLE IF EXISTS `system_sms_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_sms_channel` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `signature` varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信签名',
  `code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `status` tinyint NOT NULL COMMENT '开启状态',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `api_key` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信 API 的账号',
  `api_secret` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信 API 的秘钥',
  `callback_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信发送回调 URL',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信渠道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_sms_channel`
--

--
-- Table structure for table `system_sms_code`
--

DROP TABLE IF EXISTS `system_sms_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_sms_code` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `code` varchar(6) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '验证码',
  `create_ip` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建 IP',
  `scene` tinyint NOT NULL DEFAULT '0' COMMENT '发送场景',
  `today_index` int NOT NULL DEFAULT '0' COMMENT '今日发送的第几条',
  `used` tinyint NOT NULL COMMENT '是否使用',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  `used_ip` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '使用 IP',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_mobile` (`mobile`) USING BTREE COMMENT '手机号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='手机验证码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_sms_code`
--

LOCK TABLES `system_sms_code` WRITE;
/*!40000 ALTER TABLE `system_sms_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_sms_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_sms_log`
--

DROP TABLE IF EXISTS `system_sms_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_sms_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `channel_id` bigint NOT NULL DEFAULT '0' COMMENT '短信渠道编号',
  `channel_code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信渠道编码',
  `template_id` bigint NOT NULL DEFAULT '0' COMMENT '模板编号',
  `template_code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '模板编码',
  `template_type` tinyint NOT NULL DEFAULT '0' COMMENT '短信类型',
  `template_content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信内容',
  `template_params` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信参数',
  `api_template_id` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信 API 的模板编号',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `user_type` tinyint NOT NULL DEFAULT '0' COMMENT '用户类型',
  `send_status` tinyint NOT NULL DEFAULT '0' COMMENT '发送状态',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `send_code` int NOT NULL DEFAULT '0' COMMENT '发送结果的编码',
  `send_msg` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '发送结果的提示',
  `api_send_code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信 API 发送结果的编码',
  `api_send_msg` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信 API 发送失败的提示',
  `api_request_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信 API 发送返回的唯一请求 ID',
  `api_serial_no` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '短信 API 发送返回的序号',
  `receive_status` tinyint NOT NULL DEFAULT '0' COMMENT '接收状态',
  `receive_time` datetime DEFAULT NULL COMMENT '接收时间',
  `api_receive_code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'API 接收结果的编码',
  `api_receive_msg` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'API 接收结果的说明',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_sms_log`
--

LOCK TABLES `system_sms_log` WRITE;
/*!40000 ALTER TABLE `system_sms_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_sms_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_sms_template`
--

DROP TABLE IF EXISTS `system_sms_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_sms_template` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` tinyint NOT NULL COMMENT '短信签名',
  `status` tinyint NOT NULL COMMENT '开启状态',
  `code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板编码',
  `name` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板名称',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '模板内容',
  `params` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数数组',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `api_template_id` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信 API 的模板编号',
  `channel_id` bigint NOT NULL COMMENT '短信渠道编号',
  `channel_code` varchar(63) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '短信渠道编码',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='短信模板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_sms_template`
--


--
-- Table structure for table `system_social_user`
--

DROP TABLE IF EXISTS `system_social_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_social_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `type` tinyint NOT NULL COMMENT '社交平台的类型',
  `openid` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '社交 openid',
  `token` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '社交 token',
  `raw_token_info` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始 Token 数据，一般是 JSON 格式',
  `nickname` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `raw_user_info` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始用户数据，一般是 JSON 格式',
  `code` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '最后一次的认证 code',
  `state` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后一次的认证 state',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社交用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_social_user`
--

LOCK TABLES `system_social_user` WRITE;
/*!40000 ALTER TABLE `system_social_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_social_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_social_user_bind`
--

DROP TABLE IF EXISTS `system_social_user_bind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_social_user_bind` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键(自增策略)',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` tinyint NOT NULL COMMENT '用户类型',
  `social_type` tinyint NOT NULL COMMENT '社交平台的类型',
  `social_user_id` bigint NOT NULL COMMENT '社交用户的编号',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社交绑定表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_social_user_bind`
--

LOCK TABLES `system_social_user_bind` WRITE;
/*!40000 ALTER TABLE `system_social_user_bind` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_social_user_bind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_tenant`
--

--
-- Table structure for table `system_user`
--

DROP TABLE IF EXISTS `system_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `dept_id` bigint NOT NULL DEFAULT '0' COMMENT '部门ID',
  `post_ids` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位编号数组',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机号码',
  `sex` tinyint DEFAULT '0' COMMENT '用户性别',
  `avatar` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像地址',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_username` (`username`,`update_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user`
--

LOCK TABLES `system_user` WRITE;
/*!40000 ALTER TABLE `system_user` DISABLE KEYS */;
INSERT INTO `system_user` VALUES 
(1,'admin','$2a$10$mRMIYLDtRHlf6.9ipiqH1.Z.bh/R9dO9d5iHiGYPigi6r5KOoR2Wm','管理员','管理员',103,'[1]','','15612345678',1,'',0,'0:0:0:0:0:0:0:1','2023-10-20 10:19:21',1,'2021-01-05 17:03:47',1,'2023-10-20 10:19:21',_binary '\0');
/*!40000 ALTER TABLE `system_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user_post`
--

DROP TABLE IF EXISTS `system_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_user_post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `post_id` bigint NOT NULL DEFAULT '0' COMMENT '岗位ID',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户岗位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user_post`
--

LOCK TABLES `system_user_post` WRITE;
/*!40000 ALTER TABLE `system_user_post` DISABLE KEYS */;
INSERT INTO `system_user_post` VALUES 
(112,1,1,1,'2022-05-02 07:25:24',1,'2022-05-02 07:25:24',_binary '\0');
/*!40000 ALTER TABLE `system_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user_role`
--

DROP TABLE IF EXISTS `system_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `creator_id` bigint NOT NULL DEFAULT '0' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint NOT NULL DEFAULT '0' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user_role`
--

LOCK TABLES `system_user_role` WRITE;
/*!40000 ALTER TABLE `system_user_role` DISABLE KEYS */;
INSERT INTO `system_user_role` VALUES 
(1,1,1,1,'2022-01-11 13:19:45',1,'2022-05-12 12:35:17',_binary '\0');
/*!40000 ALTER TABLE `system_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-22 16:51:08


SET FOREIGN_KEY_CHECKS = 1;