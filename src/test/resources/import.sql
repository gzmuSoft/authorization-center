/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云—授权中心
 Source Server Type    : PostgreSQL
 Source Server Version : 120001
 Source Host           : 118.24.1.170:5432
 Source Catalog        : gzmu
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120001
 File Encoding         : 65001

 Date: 07/02/2020 18:28:26
*/


-- ----------------------------
-- Table structure for auth_center_res
-- ----------------------------
DROP TABLE IF EXISTS "test"."auth_center_res";
CREATE TABLE "test"."auth_center_res"
(
    "id"          int8 NOT NULL                               DEFAULT nextval('sys_res_seq'::regclass),
    "name"        varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "spell"       varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "url"         varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "describe"    varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "method"      varchar(255) COLLATE "pg_catalog"."default" DEFAULT 'GET'::character varying,
    "sort"        int2 NOT NULL                               DEFAULT '1'::smallint,
    "create_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "create_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "modify_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "modify_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "remark"      varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "is_enable"   bool NOT NULL                               DEFAULT true
)
;
ALTER TABLE "test"."auth_center_res"
    OWNER TO "postgres";

-- ----------------------------
-- Records of auth_center_res
-- ----------------------------
BEGIN;
INSERT INTO "test"."auth_center_res"
VALUES (1, NULL, NULL, '/me/routes', '获取当前登录用户的路由信息', 'GET', 1, NULL, '2020-02-06 15:08:14', NULL,
        '2020-02-06 15:08:14', NULL, 't');
INSERT INTO "test"."auth_center_res"
VALUES (3, NULL, NULL, '/me/menu', NULL, 'GET', 1, NULL, '2020-02-07 09:58:05', NULL, '2020-02-07 09:58:05', NULL, 't');
INSERT INTO "test"."auth_center_res"
VALUES (2, 'index', NULL, 'menu.dashboard', '路由-仪表盘', 'mdi-view-dashboard', 1, NULL, '2020-02-06 15:56:37', NULL,
        '2020-02-06 15:56:37', 'menu.system', 't');
COMMIT;

-- ----------------------------
-- Table structure for auth_center_role_res
-- ----------------------------
DROP TABLE IF EXISTS "test"."auth_center_role_res";
CREATE TABLE "test"."auth_center_role_res"
(
    "id"          int8 NOT NULL                               DEFAULT nextval('sys_role_res_seq'::regclass),
    "name"        varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "spell"       varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "role_id"     int8 NOT NULL,
    "res_id"      int8 NOT NULL,
    "sort"        int2,
    "create_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "create_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "modify_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "modify_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "remark"      varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "is_enable"   bool NOT NULL                               DEFAULT true
)
;
ALTER TABLE "test"."auth_center_role_res"
    OWNER TO "postgres";

-- ----------------------------
-- Records of auth_center_role_res
-- ----------------------------
BEGIN;
INSERT INTO "test"."auth_center_role_res"
VALUES (1, NULL, NULL, 1, 1, NULL, NULL, '2020-02-06 15:27:46', NULL, '2020-02-06 15:27:46', NULL, 't');
INSERT INTO "test"."auth_center_role_res"
VALUES (2, NULL, NULL, 1, 2, NULL, NULL, '2020-02-06 15:57:37', NULL, '2020-02-06 15:57:37', NULL, 't');
INSERT INTO "test"."auth_center_role_res"
VALUES (3, NULL, NULL, 1, 3, NULL, NULL, '2020-02-07 09:58:21', NULL, '2020-02-07 09:58:21', NULL, 't');
COMMIT;

-- ----------------------------
-- Table structure for client_details
-- ----------------------------
DROP TABLE IF EXISTS "test"."client_details";
CREATE TABLE "test"."client_details"
(
    "id"                     int8                                        NOT NULL DEFAULT nextval('client_details_seq'::regclass),
    "name"                   varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "client_id"              varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
    "resource_ids"           varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
    "client_secret"          varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
    "scope"                  varchar(256) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'READ'::character varying,
    "grant_types"            varchar(256) COLLATE "pg_catalog"."default" NOT NULL,
    "redirect_url"           varchar(256) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "authorities"            varchar(256) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "access_token_validity"  int4,
    "refresh_token_validity" int4,
    "additional_information" varchar(4096) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "auto_approve_scopes"    varchar(256) COLLATE "pg_catalog"."default" NOT NULL DEFAULT ''::character varying,
    "spell"                  varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "sort"                   int2                                        NOT NULL DEFAULT '1'::smallint,
    "create_user"            varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "create_time"            timestamp(0)                                         DEFAULT CURRENT_TIMESTAMP,
    "modify_user"            varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "modify_time"            timestamp(0)                                         DEFAULT CURRENT_TIMESTAMP,
    "remark"                 varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "is_enable"              bool                                        NOT NULL DEFAULT true
)
;
ALTER TABLE "test"."client_details"
    OWNER TO "postgres";

-- ----------------------------
-- Records of client_details
-- ----------------------------
BEGIN;
INSERT INTO "test"."client_details"
VALUES (4, NULL, 'test', 'test', '$2a$12$zE5sRSfd9nuuw2FwuccjO.ep1uCRRWd4ND8cKfKLZ6Oq1.wXB293G', 'READ',
        'password,refresh_token,sms,email,authorization_code', 'http://example.com', NULL, 600000, 600000, '{"a":"1"}',
        '', NULL, 1, NULL, '2019-12-26 22:04:44', NULL, '2019-12-26 22:04:44', NULL, 't');
INSERT INTO "test"."client_details"
VALUES (2, NULL, 'study', 'study', '$2a$12$G3.2TbqlG5q5c/h7L4YBSO11eVHkh.1aLrxxQOFWKZ7oFjz3H5dsi', 'READ',
        'password,refresh_token,sms,email,authorization_code', 'http://example.com', NULL, 600000, 600000, '{"a":"1"}',
        '', NULL, 1, NULL, '2019-12-26 22:04:44', NULL, '2019-12-26 22:04:44', NULL, 't');
INSERT INTO "test"."client_details"
VALUES (1, NULL, 'lesson-cloud', 'lesson-cloud', '$2a$12$mevxR8T/xecGIrlvgkEqMecoZHbggqzg0efnOOJ/WM8KmJmVj/cQ.', 'READ',
        'password,refresh_token,sms,email,authorization_code', 'http://127.0.0.1:8081/#/login', NULL, 600000, 600000,
        '{"a":"1"}', '', NULL, 1, NULL, '2019-12-23 23:39:07', NULL, '2019-12-23 23:39:07', NULL, 't');
INSERT INTO "test"."client_details"
VALUES (3, NULL, 'gzmu-auth', 'gzmu-auth', '$2a$12$IDY73g8L/jU1.bZ5ylrnpu/4mwY3mZ9E9L2GnSuE/JVjfYQPy.tw6', 'READ',
        'password,refresh_token,sms,email,authorization_code', 'http://127.0.0.1:8081/#/login', NULL, 600000, 600000,
        '{"a":"1"}', '', NULL, 1, NULL, '2019-12-26 22:04:44', NULL, '2019-12-26 22:04:44', NULL, 't');
COMMIT;

-- ----------------------------
-- Table structure for semester
-- ----------------------------
DROP TABLE IF EXISTS "test"."semester";
CREATE TABLE "test"."semester"
(
    "id"          int8 NOT NULL                               DEFAULT nextval('semester_seq'::regclass),
    "name"        varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "spell"       varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "school_id"   int8 NOT NULL,
    "start_date"  date,
    "end_date"    date,
    "sort"        int2,
    "create_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "create_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "modify_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "modify_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "remark"      varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "is_enable"   bool NOT NULL                               DEFAULT true
)
;
ALTER TABLE "test"."semester"
    OWNER TO "postgres";

-- ----------------------------
-- Records of semester
-- ----------------------------
BEGIN;
INSERT INTO "test"."semester"
VALUES (1, '2018夏季小学期', '2018xiajixiaoxueqi', 1, '2018-07-13', '2019-08-16', 30, 'yms', '2019-08-07 16:03:26', 'yms',
        '2019-08-07 16:47:36', '这是一个小学期', 't');
INSERT INTO "test"."semester"
VALUES (2, '2018-2019上学期', '2018-2019shangxueqi', 1, '2018-09-01', '2019-01-04', 12, 'yms', '2020-02-05 00:08:20',
        'yms', '2020-02-04 16:08:35', '这是一个学期', 't');
INSERT INTO "test"."semester"
VALUES (3, '2018-2019下学期', '2018-2019xiaxueqi', 1, '2019-03-01', '2019-07-06', 15, 'yms', '2020-02-05 00:08:20', 'yms',
        '2020-02-04 16:08:35', '这是一个学期', 't');
INSERT INTO "test"."semester"
VALUES (4, '2019夏季小学期', '2019xiajixiaoxueqi', 1, '2019-07-13', '2019-08-16', 52, 'yms', '2019-08-07 16:47:36', 'yms',
        '2019-08-07 16:47:36', '这是一个小学期', 't');
INSERT INTO "test"."semester"
VALUES (5, '2019-2020上学期', '2019-2020shangxueqi', 1, '2019-09-01', '2020-01-04', 64, 'yms', '2020-02-05 00:08:20',
        'yms', '2020-02-04 16:08:35', '这是一个学期', 't');
INSERT INTO "test"."semester"
VALUES (6, '2019-2020下学期', '2019-2020xiaxueqi', 1, '2020-03-01', '2019-07-08', 79, 'yms', '2020-02-05 00:08:20', 'yms',
        '2020-02-04 16:08:35', '这是一个学期', 't');
INSERT INTO "test"."semester"
VALUES (7, '2020夏季小学期', '2020xiajixiaoxueqi', 1, '2020-07-13', '2020-08-14', 90, 'yms', '2019-08-07 16:47:37', 'yms',
        '2019-08-07 16:47:37', '这是一个小学期', 't');
INSERT INTO "test"."semester"
VALUES (8, '2020-2021上学期', '2020-2021shangxueqi', 1, '2020-09-01', '2021-01-03', 59, 'yms', '2020-02-05 00:08:20',
        'yms', '2020-02-04 16:08:35', '这是一个学期', 't');
COMMIT;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS "test"."student";
CREATE TABLE "test"."student"
(
    "id"                   int8 NOT NULL                                DEFAULT nextval('student_seq'::regclass),
    "name"                 varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "spell"                varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "user_id"              int8,
    "school_id"            int8,
    "college_id"           int8,
    "dep_id"               int8,
    "specialty_id"         int8,
    "classes_id"           int8,
    "no"                   varchar(20) COLLATE "pg_catalog"."default"   DEFAULT NULL::character varying,
    "gender"               varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "id_number"            varchar(18) COLLATE "pg_catalog"."default"   DEFAULT NULL::character varying,
    "birthday"             date,
    "enter_date"           date,
    "academic"             varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "graduation_date"      date,
    "graduate_institution" varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "original_major"       varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "resume"               varchar(2048) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "sort"                 int2,
    "create_user"          varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "create_time"          timestamp(0)                                 DEFAULT CURRENT_TIMESTAMP,
    "modify_user"          varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "modify_time"          timestamp(0)                                 DEFAULT CURRENT_TIMESTAMP,
    "remark"               varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "is_enable"            bool NOT NULL                                DEFAULT true
)
;
ALTER TABLE "test"."student"
    OWNER TO "postgres";

-- ----------------------------
-- Records of student
-- ----------------------------
BEGIN;
INSERT INTO "test"."student"
VALUES (4, '林雅南', 'lín yǎ nán ', 4, 29, 30, 31, 32, 34, '201742060004', '女', '522526202002050004', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 76, 'yms', '2020-02-04 17:53:11', 'yms',
        '2020-02-04 17:53:11', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (5, '江奕云', 'jiāng yì yún ', 5, 1, 18, 19, 20, 22, '201742060005', '男', '522526202002050005', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 7, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (6, '刘柏宏', 'liú bǎi hóng ', 6, 29, 30, 3, 8, 12, '201742060006', '男', '522526202002050006', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 30, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (7, '阮建安', 'ruǎn jiàn ān ', 7, 1, 18, 3, 4, 7, '201742060007', '女', '522526202002050007', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 96, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (8, '林子帆', 'lín zǐ fān ', 8, 29, 44, 3, 13, 14, '201742060008', '男', '522526202002050008', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 95, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (9, '夏志豪', 'xià zhì háo ', 9, 29, 44, 19, 24, 27, '201742060009', '男', '522526202002050009', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 21, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (10, '吉茹定', 'jí rú dìng ', 10, 29, 44, 3, 13, 14, '201742060010', '女', '522526202002050010', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 53, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (11, '李中冰', 'lǐ zhōng bīng ', 11, 1, 2, 19, 24, 28, '201742060011', '男', '522526202002050011', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 21, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (12, '黄文隆', 'huáng wén lóng ', 12, 1, 18, 3, 8, 10, '201742060012', '男', '522526202002050012', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 59, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (1, '张吉惟', 'zhāng jí wéi ', 1, 1, 2, 19, 20, 22, '201742060001', '女', '522526202002050001', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 64, 'yms', '2019-08-05 22:46:10', 'yms',
        '2019-08-07 16:00:41', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (2, '林国瑞', 'lín guó ruì ', 2, 1, 2, 45, 48, 49, '201742060002', '男', '522526202002050002', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 19, 'yms', '2019-08-07 16:40:28', 'yms',
        '2019-08-07 16:40:28', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (3, '林玟书', 'lín mín shū ', 3, 1, 2, 3, 8, 10, '201742060003', '男', '522526202002050003', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 64, 'yms', '2020-02-04 17:49:04', 'yms',
        '2020-02-04 17:49:04', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (13, '谢彦文', 'xiè yàn wén ', 13, 29, 44, 45, 48, 49, '201742060013', '女', '522526202002050013', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 0, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (16, '刘姿婷', 'liú zī tíng ', 16, 29, 30, 31, 37, 39, '201742060016', '女', '522526202002050016', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 90, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (17, '荣姿康', 'róng zī kāng ', 17, 29, 30, 31, 37, 39, '201742060017', '男', '522526202002050017', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (18, '吕致盈', 'lǚ zhì yíng ', 18, 1, 18, 19, 20, 21, '201742060018', '男', '522526202002050018', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 35, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (19, '方一强', 'fāng yī qiáng ', 19, 1, 2, 3, 13, 16, '201742060019', '女', '522526202002050019', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 2, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (20, '黎芸贵', 'lí yún guì ', 20, 1, 18, 19, 20, 22, '201742060020', '男', '522526202002050020', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 85, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (21, '郑伊雯', 'zhèng yī wén ', 21, 29, 44, 45, 46, 47, '201742060021', '男', '522526202002050021', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 60, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (22, '雷进宝', 'léi jìn bǎo ', 22, 1, 18, 19, 24, 26, '201742060022', '女', '522526202002050022', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 92, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (23, '吴美隆', 'wú měi lóng ', 23, 1, 2, 3, 8, 12, '201742060023', '男', '522526202002050023', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (24, '吴心真', 'wú xīn zhēn ', 24, 1, 2, 3, 8, 12, '201742060024', '男', '522526202002050024', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 13, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (25, '王美珠', 'wáng měi zhū ', 25, 29, 30, 31, 32, 33, '201742060025', '女', '522526202002050025', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 93, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (26, '郭芳天', 'guō fāng tiān ', 26, 29, 30, 31, 32, 33, '201742060026', '男', '522526202002050026', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 25, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (27, '李雅惠', 'lǐ yǎ huì ', 27, 29, 30, 31, 37, 38, '201742060027', '男', '522526202002050027', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 67, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (28, '陈文婷', 'chén wén tíng ', 28, 29, 30, 31, 32, 34, '201742060028', '女', '522526202002050028', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 90, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (29, '曹敏侑', 'cáo mǐn yòu ', 29, 1, 2, 3, 13, 14, '201742060029', '男', '522526202002050029', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 81, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (30, '王依婷', 'wáng yī tíng ', 30, 1, 18, 19, 24, 25, '201742060030', '男', '522526202002050030', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 45, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (31, '陈婉璇', 'chén wǎn xuán ', 31, 1, 2, 3, 13, 17, '201742060031', '女', '522526202002050031', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 41, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (32, '吴美玉', 'wú měi yù ', 32, 29, 30, 31, 37, 38, '201742060032', '男', '522526202002050032', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 53, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (33, '蔡依婷', 'cài yī tíng ', 33, 29, 44, 45, 48, 50, '201742060033', '男', '522526202002050033', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 54, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (34, '郑昌梦', 'zhèng chāng mèng ', 34, 29, 30, 31, 37, 39, '201742060034', '女', '522526202002050034', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 11, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (35, '林家纶', 'lín jiā lún ', 35, 29, 30, 31, 37, 39, '201742060035', '男', '522526202002050035', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 49, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (36, '黄丽昆', 'huáng lì kūn ', 36, 29, 30, 31, 32, 33, '201742060036', '男', '522526202002050036', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (37, '李育泉', 'lǐ yù quán ', 37, 29, 30, 40, 41, 43, '201742060037', '女', '522526202002050037', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 57, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (38, '黄芸欢', 'huáng yún huān ', 38, 1, 2, 3, 13, 14, '201742060038', '男', '522526202002050038', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 50, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (39, '吴韵如', 'wú yùn rú ', 39, 29, 30, 40, 41, 42, '201742060039', '男', '522526202002050039', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 9, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (40, '李肇芬', 'lǐ zhào fēn ', 40, 1, 2, 3, 8, 10, '201742060040', '女', '522526202002050040', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 70, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (41, '卢木仲', 'lú mù zhòng ', 41, 1, 2, 3, 8, 10, '201742060041', '男', '522526202002050041', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 3, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (42, '李成白', 'lǐ chéng bái ', 42, 29, 30, 31, 32, 33, '201742060042', '男', '522526202002050042', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 40, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (43, '方兆玉', 'fāng zhào yù ', 43, 1, 2, 3, 13, 16, '201742060043', '女', '522526202002050043', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 51, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (44, '刘翊惠', 'liú yì huì ', 44, 29, 44, 45, 48, 50, '201742060044', '男', '522526202002050044', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 86, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (45, '丁汉臻', 'dīng hàn zhēn ', 45, 1, 2, 3, 4, 6, '201742060045', '男', '522526202002050045', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 78, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (46, '吴佳瑞', 'wú jiā ruì ', 46, 29, 30, 40, 41, 43, '201742060046', '女', '522526202002050046', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (47, '舒绿珮', 'shū lǜ pèi ', 47, 29, 44, 45, 48, 49, '201742060047', '男', '522526202002050047', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 76, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (48, '周白芷', 'zhōu bái zhǐ ', 48, 29, 30, 31, 32, 35, '201742060048', '男', '522526202002050048', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 51, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (49, '张姿妤', 'zhāng zī yú ', 49, 1, 2, 3, 8, 11, '201742060049', '女', '522526202002050049', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 64, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (50, '张虹伦', 'zhāng hóng lún ', 50, 29, 30, 31, 37, 38, '201742060050', '男', '522526202002050050', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 87, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (51, '周琼玟', 'zhōu qióng mín ', 51, 1, 2, 3, 13, 17, '201742060051', '男', '522526202002050051', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 68, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (52, '倪怡芳', 'ní yí fāng ', 52, 1, 2, 3, 13, 14, '201742060052', '女', '522526202002050052', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 50, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (53, '郭贵妃', 'guō guì fēi ', 53, 29, 30, 40, 41, 42, '201742060053', '男', '522526202002050053', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 69, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (54, '杨佩芳', 'yáng pèi fāng ', 54, 1, 2, 3, 4, 5, '201742060054', '男', '522526202002050054', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 29, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (55, '黄文旺', 'huáng wén wàng ', 55, 29, 44, 45, 48, 50, '201742060055', '女', '522526202002050055', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 16, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (56, '黄盛玫', 'huáng shèng méi ', 56, 29, 30, 31, 32, 36, '201742060056', '男', '522526202002050056', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 74, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (57, '郑丽青', 'zhèng lì qīng ', 57, 1, 18, 19, 24, 26, '201742060057', '男', '522526202002050057', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 90, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (58, '许智云', 'xǔ zhì yún ', 58, 1, 18, 19, 24, 26, '201742060058', '女', '522526202002050058', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 12, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (59, '张孟涵', 'zhāng mèng hán ', 59, 29, 30, 31, 37, 38, '201742060059', '男', '522526202002050059', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 5, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (60, '李小爱', 'lǐ xiǎo ài ', 60, 29, 44, 45, 48, 50, '201742060060', '男', '522526202002050060', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 71, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (61, '王恩龙', 'wáng ēn lóng ', 61, 1, 18, 19, 24, 26, '201742060061', '女', '522526202002050061', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 85, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (62, '朱政廷', 'zhū zhèng tíng ', 62, 1, 18, 19, 24, 25, '201742060062', '男', '522526202002050062', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 71, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (63, '邓诗涵', 'dèng shī hán ', 63, 1, 18, 19, 24, 28, '201742060063', '男', '522526202002050063', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 16, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (64, '陈政倩', 'chén zhèng qiàn ', 64, 29, 30, 31, 32, 35, '201742060064', '女', '522526202002050064', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 40, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (65, '吴俊伯', 'wú jun4 bó ', 65, 29, 44, 45, 46, 47, '201742060065', '男', '522526202002050065', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 47, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (66, '阮馨学', 'ruǎn xīn xué ', 66, 1, 2, 3, 8, 11, '201742060066', '男', '522526202002050066', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 45, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (67, '翁惠珠', 'wēng huì zhū ', 67, 29, 30, 40, 41, 43, '201742060067', '女', '522526202002050067', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 81, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (68, '吴思翰', 'wú sī hàn ', 68, 29, 44, 45, 46, 47, '201742060068', '男', '522526202002050068', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (69, '林佩玲', 'lín pèi líng ', 69, 1, 2, 3, 13, 16, '201742060069', '男', '522526202002050069', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 59, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (70, '邓海来', 'dèng hǎi lái ', 70, 1, 2, 3, 4, 5, '201742060070', '女', '522526202002050070', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 21, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (71, '陈翊依', 'chén yì yī ', 71, 1, 2, 3, 13, 14, '201742060071', '男', '522526202002050071', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 0, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (72, '李建智', 'lǐ jiàn zhì ', 72, 1, 18, 19, 20, 22, '201742060072', '男', '522526202002050072', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (73, '武淑芬', 'wǔ shū fēn ', 73, 1, 18, 19, 20, 21, '201742060073', '女', '522526202002050073', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 11, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (74, '金雅琪', 'jīn yǎ qí ', 74, 1, 18, 19, 24, 28, '201742060074', '男', '522526202002050074', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 62, 'yms', '2020-02-04 17:53:47', 'yms',
        '2020-02-04 17:53:47', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (75, '赖怡宜', 'lài yí yí ', 75, 29, 30, 40, 41, 43, '201742060075', '男', '522526202002050075', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 17, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (76, '黄育霖', 'huáng yù lín ', 76, 1, 2, 3, 4, 6, '201742060076', '女', '522526202002050076', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 43, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (77, '张仪湖', 'zhāng yí hú ', 77, 1, 18, 19, 24, 26, '201742060077', '男', '522526202002050077', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 63, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (78, '王俊民', 'wáng jun4 mín ', 78, 1, 18, 19, 24, 27, '201742060078', '男', '522526202002050078', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 85, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (79, '张诗刚', 'zhāng shī gāng ', 79, 29, 30, 40, 41, 43, '201742060079', '女', '522526202002050079', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 68, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (80, '林慧颖', 'lín huì yǐng ', 80, 1, 2, 3, 13, 17, '201742060080', '男', '522526202002050080', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 55, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (81, '沈俊君', 'shěn jun4 jun1 ', 81, 1, 2, 3, 4, 7, '201742060081', '男', '522526202002050081', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 44, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (82, '陈淑妤', 'chén shū yú ', 82, 1, 2, 3, 8, 11, '201742060082', '女', '522526202002050082', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 95, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (83, '李姿伶', 'lǐ zī líng ', 83, 1, 2, 3, 13, 16, '201742060083', '男', '522526202002050083', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 91, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (84, '高咏钰', 'gāo yǒng yù ', 84, 29, 30, 31, 32, 36, '201742060084', '男', '522526202002050084', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 3, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (85, '黄彦宜', 'huáng yàn yí ', 85, 29, 30, 40, 41, 42, '201742060085', '女', '522526202002050085', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 37, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (86, '周孟儒', 'zhōu mèng rú ', 86, 1, 2, 3, 4, 5, '201742060086', '男', '522526202002050086', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 78, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (87, '潘欣臻', 'pān xīn zhēn ', 87, 1, 18, 19, 20, 21, '201742060087', '男', '522526202002050087', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 8, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (88, '李祯韵', 'lǐ zhēn yùn ', 88, 29, 30, 31, 37, 38, '201742060088', '女', '522526202002050088', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 63, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (89, '叶洁启', 'yè jié qǐ ', 89, 29, 44, 45, 48, 49, '201742060089', '男', '522526202002050089', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 51, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (90, '梁哲宇', 'liáng zhé yǔ ', 90, 1, 2, 3, 13, 15, '201742060090', '男', '522526202002050090', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 63, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (93, '卢志铭', 'lú zhì míng ', 93, 29, 44, 45, 46, 47, '201742060093', '男', '522526202002050093', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 47, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (94, '张茂以', 'zhāng mào yǐ ', 94, 29, 30, 40, 41, 43, '201742060094', '女', '522526202002050094', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 93, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (96, '蔡宜芸', 'cài yí yún ', 96, 1, 2, 3, 4, 7, '201742060096', '男', '522526202002050096', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 44, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (97, '林珮瑜', 'lín pèi yú ', 97, 1, 2, 3, 8, 12, '201742060097', '女', '522526202002050097', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (98, '黄柏仪', 'huáng bǎi yí ', 98, 29, 30, 31, 32, 34, '201742060098', '男', '522526202002050098', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 80, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (99, '周逸珮', 'zhōu yì pèi ', 99, 29, 30, 31, 32, 35, '201742060099', '男', '522526202002050099', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 36, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (100, '夏雅惠', 'xià yǎ huì ', 100, 1, 18, 19, 20, 21, '201742060100', '女', '522526202002050100', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 76, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (101, '王采珮', 'wáng cǎi pèi ', 101, 1, 2, 3, 13, 15, '201742060101', '男', '522526202002050101', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 44, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (102, '林孟霖', 'lín mèng lín ', 102, 29, 44, 45, 46, 47, '201742060102', '男', '522526202002050102', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 51, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (103, '林竹水', 'lín zhú shuǐ ', 103, 29, 44, 45, 46, 47, '201742060103', '女', '522526202002050103', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 9, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (104, '王怡乐', 'wáng yí lè ', 104, 1, 2, 3, 4, 6, '201742060104', '男', '522526202002050104', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 70, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (105, '王爱乐', 'wáng ài lè ', 105, 29, 30, 31, 32, 34, '201742060105', '男', '522526202002050105', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 14, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (106, '金佳蓉', 'jīn jiā róng ', 106, 1, 2, 3, 8, 10, '201742060106', '女', '522526202002050106', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 81, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (107, '韩健毓', 'hán jiàn yù ', 107, 1, 2, 3, 8, 12, '201742060107', '男', '522526202002050107', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 42, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (108, '李士杰', 'lǐ shì jié ', 108, 1, 18, 19, 24, 26, '201742060108', '男', '522526202002050108', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 30, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (109, '陈萱珍', 'chén xuān zhēn ', 109, 29, 30, 31, 37, 38, '201742060109', '女', '522526202002050109', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 60, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (110, '苏姿婷', 'sū zī tíng ', 110, 29, 30, 31, 32, 34, '201742060110', '男', '522526202002050110', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 32, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (111, '张政霖', 'zhāng zhèng lín ', 111, 1, 18, 19, 20, 21, '201742060111', '男', '522526202002050111', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 77, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (112, '李志宏', 'lǐ zhì hóng ', 112, 29, 44, 45, 48, 50, '201742060112', '女', '522526202002050112', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 78, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (113, '陈素达', 'chén sù dá ', 113, 29, 30, 31, 37, 39, '201742060113', '男', '522526202002050113', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (114, '陈虹荣', 'chén hóng róng ', 114, 29, 30, 40, 41, 42, '201742060114', '男', '522526202002050114', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 9, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (115, '何美玲', 'hé měi líng ', 115, 29, 44, 45, 48, 50, '201742060115', '女', '522526202002050115', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 62, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (117, '张俞幸', 'zhāng yú xìng ', 117, 1, 18, 19, 24, 28, '201742060117', '男', '522526202002050117', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 82, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (118, '黄秋萍', 'huáng qiū píng ', 118, 29, 44, 45, 48, 50, '201742060118', '女', '522526202002050118', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 65, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (119, '潘吉维', 'pān jí wéi ', 119, 1, 2, 3, 8, 10, '201742060119', '男', '522526202002050119', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 53, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (120, '陈智筠', 'chén zhì jun1 ', 120, 1, 2, 3, 8, 10, '201742060120', '男', '522526202002050120', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 25, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (121, '蔡书玮', 'cài shū wěi ', 121, 1, 2, 3, 4, 6, '201742060121', '女', '522526202002050121', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 42, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (122, '陈信峰', 'chén xìn fēng ', 122, 1, 18, 19, 24, 25, '201742060122', '男', '522526202002050122', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 0, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (123, '林培伦', 'lín péi lún ', 123, 29, 30, 40, 41, 43, '201742060123', '男', '522526202002050123', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 6, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (124, '查瑜舜', 'chá yú shùn ', 124, 29, 30, 31, 32, 36, '201742060124', '女', '522526202002050124', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 0, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (125, '黎慧萱', 'lí huì xuān ', 125, 29, 30, 31, 32, 35, '201742060125', '男', '522526202002050125', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 81, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (126, '郑士易', 'zhèng shì yì ', 126, 1, 2, 3, 13, 15, '201742060126', '男', '522526202002050126', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 2, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (127, '陈建豪', 'chén jiàn háo ', 127, 29, 30, 31, 32, 33, '201742060127', '女', '522526202002050127', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 83, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (129, '徐紫富', 'xú zǐ fù ', 129, 1, 18, 19, 20, 22, '201742060129', '男', '522526202002050129', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 39, 'yms', '2020-02-04 17:53:49', 'yms',
        '2020-02-04 17:53:49', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (130, '张博海', 'zhāng bó hǎi ', 130, 29, 44, 45, 48, 50, '201742060130', '女', '522526202002050130', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 28, 'yms', '2020-02-04 17:53:49', 'yms',
        '2020-02-04 17:53:49', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (131, '黎宏儒', 'lí hóng rú ', 131, 1, 2, 3, 13, 14, '201742060131', '男', '522526202002050131', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 62, 'yms', '2020-02-04 17:53:49', 'yms',
        '2020-02-04 17:53:49', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (132, '柯乔喜', 'kē qiáo xǐ ', 132, 1, 2, 3, 13, 15, '201742060132', '男', '522526202002050132', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 94, 'yms', '2020-02-04 17:53:49', 'yms',
        '2020-02-04 17:53:49', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (14, '傅智翔', 'fù zhì xiáng ', 14, 1, 2, 3, 8, 11, '201742060014', '男', '522526202002050014', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 60, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (15, '洪振霞', 'hóng zhèn xiá ', 15, 29, 30, 31, 32, 35, '201742060015', '男', '522526202002050015', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 50, 'yms', '2020-02-04 17:53:46', 'yms',
        '2020-02-04 17:53:46', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (116, '李仪琳', 'lǐ yí lín ', 116, 1, 2, 3, 8, 10, '201742060116', '男', '522526202002050116', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 74, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (128, '吴怡婷', 'wú yí tíng ', 128, 29, 44, 45, 48, 50, '201742060128', '男', '522526202002050128', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 71, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (91, '黄晓萍', 'huáng xiǎo píng ', 91, 29, 30, 40, 41, 43, '201742060091', '女', '522526202002050091', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 36, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (92, '杨雅萍', 'yáng yǎ píng ', 92, 1, 2, 3, 13, 16, '201742060092', '男', '522526202002050092', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 59, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
INSERT INTO "test"."student"
VALUES (95, '林婉婷', 'lín wǎn tíng ', 95, 1, 2, 3, 8, 11, '201742060095', '男', '522526202002050095', '2020-02-05',
        '2017-09-01', '本科', '2021-06-30', '无', '无', '我是一个学生', 96, 'yms', '2020-02-04 17:53:48', 'yms',
        '2020-02-04 17:53:48', '这是一个学生', 't');
COMMIT;

-- ----------------------------
-- Table structure for sys_data
-- ----------------------------
DROP TABLE IF EXISTS "test"."sys_data";
CREATE TABLE "test"."sys_data"
(
    "id"          int8                                       NOT NULL DEFAULT nextval('sys_data_seq'::regclass),
    "name"        varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
    "spell"       varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "parent_id"   int8                                                DEFAULT '0'::bigint,
    "brief"       varchar(2048) COLLATE "pg_catalog"."default"        DEFAULT NULL::character varying,
    "type"        int2                                                DEFAULT '0'::smallint,
    "sort"        int2                                                DEFAULT '1'::smallint,
    "create_user" varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "create_time" timestamp(0)                                        DEFAULT CURRENT_TIMESTAMP,
    "modify_user" varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "modify_time" timestamp(0)                                        DEFAULT CURRENT_TIMESTAMP,
    "remark"      varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "is_enable"   bool                                       NOT NULL DEFAULT true
)
;
ALTER TABLE "test"."sys_data"
    OWNER TO "postgres";

-- ----------------------------
-- Records of sys_data
-- ----------------------------
BEGIN;
INSERT INTO "test"."sys_data"
VALUES (28, '特殊人才班', 'tè shū rén cái bān ', 24, NULL, 4, 89, 'yms', '2020-02-04 17:11:11', 'yms', '2020-02-04 17:11:11',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (4, '软件工程专业', 'ruǎn jiàn gōng chéng zhuān yè ', 3, NULL, 3, 43, 'yms', '2019-08-07 15:59:35', 'yms',
        '2019-08-12 00:27:08', '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (5, '火箭班', 'huǒ jiàn bān ', 4, NULL, 4, 80, 'yms', '2019-08-07 15:59:35', 'yms', '2019-08-12 00:27:08', '这是一个班级',
        't');
INSERT INTO "test"."sys_data"
VALUES (6, '普通班', 'pǔ tōng bān ', 4, NULL, 4, 51, 'yms', '2019-08-07 16:40:58', 'yms', '2019-08-12 00:27:08', '这是一个班级',
        't');
INSERT INTO "test"."sys_data"
VALUES (7, '放养班', 'fàng yǎng bān ', 4, NULL, 4, 8, 'yms', '2019-08-07 16:41:11', 'yms', '2019-08-12 00:27:08', '这是一个班级',
        't');
INSERT INTO "test"."sys_data"
VALUES (8, '计算机科学专业', 'jì suàn jī kē xué zhuān yè ', 3, NULL, 3, 61, 'yms', '2020-02-04 16:40:09', 'yms',
        '2020-02-04 16:40:09', '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (10, '火箭班', 'huǒ jiàn bān ', 8, NULL, 4, 40, 'yms', '2020-02-04 16:41:13', 'yms', '2020-02-04 16:41:13',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (11, '普通班', 'pǔ tōng bān ', 8, NULL, 4, 33, 'yms', '2020-02-04 16:41:17', 'yms', '2020-02-04 16:41:17', '这是一个班级',
        't');
INSERT INTO "test"."sys_data"
VALUES (12, '放养班', 'fàng yǎng bān ', 8, NULL, 4, 30, 'yms', '2020-02-04 16:41:19', 'yms', '2020-02-04 16:41:19',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (13, '物联网专业', 'wù lián wǎng zhuān yè ', 3, NULL, 3, 55, 'yms', '2020-02-04 16:42:26', 'yms',
        '2020-02-04 16:42:26', '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (14, '火箭班', 'huǒ jiàn bān ', 13, NULL, 4, 64, 'yms', '2020-02-04 16:43:04', 'yms', '2020-02-04 16:43:04',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (15, '普通班', 'pǔ tōng bān ', 13, NULL, 4, 16, 'yms', '2020-02-04 16:43:06', 'yms', '2020-02-04 16:43:06',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (16, '放养班', 'fàng yǎng bān ', 13, NULL, 4, 40, 'yms', '2020-02-04 16:43:10', 'yms', '2020-02-04 16:43:10',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (17, '扩招班', 'kuò zhāo bān ', 13, NULL, 4, 85, 'yms', '2020-02-04 16:43:25', 'yms', '2020-02-04 16:43:25',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (18, '认知科学学院', 'rèn zhī kē xué xué yuàn ', 1, NULL, 1, 29, 'yms', '2020-02-04 16:43:31', 'yms',
        '2020-02-04 16:43:31', '这是一个学院', 't');
INSERT INTO "test"."sys_data"
VALUES (19, '认知科学系', 'rèn zhī kē xué xì ', 18, NULL, 2, 50, 'yms', '2020-02-04 16:43:32', 'yms', '2020-02-04 16:43:32',
        '这是一个系部', 't');
INSERT INTO "test"."sys_data"
VALUES (20, '认知神经科学专业', 'rèn zhī shén jīng kē xué zhuān yè ', 19, NULL, 3, 85, 'yms', '2020-02-04 16:43:35', 'yms',
        '2020-02-04 16:43:35', '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (21, '火箭班', 'huǒ jiàn bān ', 20, NULL, 4, 1, 'yms', '2020-02-04 16:45:08', 'yms', '2020-02-04 16:45:08',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (22, '普通班', 'pǔ tōng bān ', 20, NULL, 4, 82, 'yms', '2020-02-04 16:45:11', 'yms', '2020-02-04 16:45:11',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (23, '放养班', 'fàng yǎng bān ', 20, NULL, 4, 83, 'yms', '2020-02-04 16:45:13', 'yms', '2020-02-04 16:45:13',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (24, '应用心理学专业', 'yīng yòng xīn lǐ xué zhuān yè ', 19, NULL, 3, 0, 'yms', '2020-02-04 16:45:14', 'yms',
        '2020-02-04 16:45:14', '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (25, '火箭班', 'huǒ jiàn bān ', 24, NULL, 4, 6, 'yms', '2020-02-04 17:11:06', 'yms', '2020-02-04 17:11:06',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (26, '普通班', 'pǔ tōng bān ', 24, NULL, 4, 24, 'yms', '2020-02-04 17:11:08', 'yms', '2020-02-04 17:11:08',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (27, '放养班', 'fàng yǎng bān ', 24, NULL, 4, 96, 'yms', '2020-02-04 17:11:10', 'yms', '2020-02-04 17:11:10',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (1, '贵州民族大学', 'guì zhōu mín zú dà xué ', 0, NULL, 0, 34, 'yms', '2019-08-07 15:58:37', 'yms',
        '2019-08-07 15:58:37', '这是一个大学', 't');
INSERT INTO "test"."sys_data"
VALUES (2, '数据科学与信息工程学院', 'shù jù kē xué yǔ xìn xī gōng chéng xué yuàn ', 1, NULL, 1, 77, 'yms', '2019-08-07 15:59:35',
        'yms', '2019-08-12 00:27:08', '这是一个学院', 't');
INSERT INTO "test"."sys_data"
VALUES (3, '数信系', 'shù xìn xì ', 2, NULL, 2, 64, 'yms', '2019-08-07 15:59:35', 'yms', '2019-08-12 00:27:08', '这是一个系部',
        't');
INSERT INTO "test"."sys_data"
VALUES (35, '塑料班', 'sù liào bān ', 32, NULL, 4, 80, 'yms', '2020-02-04 17:22:01', 'yms', '2020-02-04 17:22:01',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (36, '老年人班', 'lǎo nián rén bān ', 32, NULL, 4, 45, 'yms', '2020-02-04 17:22:30', 'yms', '2020-02-04 17:22:30',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (29, '加利敦大学', 'jiā lì dūn dà xué ', 0, NULL, 0, 91, 'yms', '2020-02-04 17:16:14', 'yms', '2020-02-04 17:16:14',
        '这是一个大学', 't');
INSERT INTO "test"."sys_data"
VALUES (38, '大神班', 'dà shén bān ', 37, NULL, 4, 30, 'yms', '2020-02-04 17:23:50', 'yms', '2020-02-04 17:23:50',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (30, 'PC学院', 'PCxué yuàn ', 29, NULL, 1, 37, 'yms', '2020-02-04 17:17:26', 'yms', '2020-02-04 17:17:26',
        '这是一个学院', 't');
INSERT INTO "test"."sys_data"
VALUES (39, '萌新班', 'méng xīn bān ', 37, NULL, 4, 44, 'yms', '2020-02-04 17:24:01', 'yms', '2020-02-04 17:24:01',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (31, 'Game系', 'Gamexì ', 30, NULL, 2, 63, 'yms', '2020-02-04 17:18:40', 'yms', '2020-02-04 17:18:40', '这是一个系部',
        't');
INSERT INTO "test"."sys_data"
VALUES (32, 'LOL专业', 'LOLzhuān yè ', 31, NULL, 3, 55, 'yms', '2020-02-04 17:19:57', 'yms', '2020-02-04 17:19:57',
        '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (33, '王者班', 'wáng zhě bān ', 32, NULL, 4, 36, 'yms', '2020-02-04 17:21:04', 'yms', '2020-02-04 17:21:04',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (42, '借电脑班', 'jiè diàn nǎo bān ', 41, NULL, 4, 80, 'yms', '2020-02-04 17:26:06', 'yms', '2020-02-04 17:26:06',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (43, '自带电脑班', 'zì dài diàn nǎo bān ', 41, NULL, 4, 56, 'yms', '2020-02-04 17:26:16', 'yms',
        '2020-02-04 17:26:16', '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (47, '猫头鹰班', 'māo tóu yīng bān ', 46, NULL, 4, 66, 'yms', '2020-02-04 17:29:08', 'yms', '2020-02-04 17:29:08',
        '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (49, '不想出门班', 'bú xiǎng chū mén bān ', 48, NULL, 4, 5, 'yms', '2020-02-04 17:30:24', 'yms',
        '2020-02-04 17:30:24', '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (50, '懒得聊天班', 'lǎn dé liáo tiān bān ', 48, NULL, 4, 43, 'yms', '2020-02-04 17:30:38', 'yms',
        '2020-02-04 17:30:38', '这是一个班级', 't');
INSERT INTO "test"."sys_data"
VALUES (44, '肥宅学院', 'féi zhái xué yuàn ', 29, NULL, 1, 70, 'yms', '2020-02-04 17:26:56', 'yms', '2020-02-04 17:26:56',
        '这是一个学院', 't');
INSERT INTO "test"."sys_data"
VALUES (45, '人才系', 'rén cái xì ', 44, NULL, 2, 84, 'yms', '2020-02-04 17:27:38', 'yms', '2020-02-04 17:27:38', '这是一个系部',
        't');
INSERT INTO "test"."sys_data"
VALUES (40, 'Study系', 'Studyxì ', 30, NULL, 2, 24, 'yms', '2020-02-04 17:24:28', 'yms', '2020-02-04 17:24:28', '这是一个系部',
        't');
INSERT INTO "test"."sys_data"
VALUES (37, 'DOTA专业', 'DOTAzhuān yè ', 31, NULL, 3, 68, 'yms', '2020-02-04 17:23:14', 'yms', '2020-02-04 17:23:14',
        '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (41, '3D建模专业', '3Djiàn mó zhuān yè ', 40, NULL, 3, 40, 'yms', '2020-02-04 17:25:20', 'yms',
        '2020-02-04 17:25:20', '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (46, '夜猫子专业', 'yè māo zǐ zhuān yè ', 45, NULL, 3, 58, 'yms', '2020-02-04 17:28:48', 'yms', '2020-02-04 17:28:48',
        '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (48, '自闭专业', 'zì bì zhuān yè ', 45, NULL, 3, 85, 'yms', '2020-02-04 17:30:08', 'yms', '2020-02-04 17:30:08',
        '这是一个专业', 't');
INSERT INTO "test"."sys_data"
VALUES (34, '黄金班', 'huáng jīn bān ', 32, NULL, 4, 64, 'yms', '2020-02-04 17:21:26', 'yms', '2020-02-04 17:21:26',
        '这是一个班级', 't');
COMMIT;

-- ----------------------------
-- Table structure for sys_res
-- ----------------------------
DROP TABLE IF EXISTS "test"."sys_res";
CREATE TABLE "test"."sys_res"
(
    "id"          int8                                       NOT NULL DEFAULT nextval('sys_res_seq'::regclass),
    "name"        varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "spell"       varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "url"         varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "describe"    varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "method"      varchar(255) COLLATE "pg_catalog"."default"         DEFAULT 'GET'::character varying,
    "sort"        int2                                       NOT NULL DEFAULT '1'::smallint,
    "create_user" varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "create_time" timestamp(0)                                        DEFAULT CURRENT_TIMESTAMP,
    "modify_user" varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "modify_time" timestamp(0)                                        DEFAULT CURRENT_TIMESTAMP,
    "remark"      varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "is_enable"   bool                                       NOT NULL DEFAULT true,
    "scopes"      varchar(55) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'ALL'::character varying
)
;
ALTER TABLE "test"."sys_res"
    OWNER TO "postgres";

-- ----------------------------
-- Records of sys_res
-- ----------------------------
BEGIN;
INSERT INTO "test"."sys_res"
VALUES (2, 'Oauth2', NULL, '/oauth/**', 'oauth2 授权相关', 'ALL', 1, NULL, '2019-08-06 06:11:23', NULL,
        '2019-08-06 06:11:23', NULL, 't', 'ALL');
INSERT INTO "test"."sys_res"
VALUES (3, 'Oauth2-提交', NULL, '/authorization/form', 'oauth2 授权码模式表单提交路径', 'POST', 1, NULL, '2019-08-06 06:23:57', NULL,
        '2019-08-06 06:23:57', NULL, 't', 'ALL');
INSERT INTO "test"."sys_res"
VALUES (6, '密码接口', NULL, '/encrypt', '加密密码', 'GET', 1, NULL, '2020-01-01 13:49:00', NULL, '2020-01-01 13:49:00', NULL,
        't', 'ALL');
INSERT INTO "test"."sys_res"
VALUES (7, '学生信息', NULL, '/student*/**', '学生信息', 'GET', 1, NULL, '2020-01-02 09:54:26', NULL, '2020-01-02 09:54:26',
        NULL, 't', 'ALL');
INSERT INTO "test"."sys_res"
VALUES (4, '应用健康信息', NULL, '/actuator*/**', '应用健康信息', 'ALL', 1, NULL, '2020-01-01 13:48:34', NULL,
        '2020-01-01 13:48:34', NULL, 't', 'ALL');
INSERT INTO "test"."sys_res"
VALUES (5, 'JWK', NULL, '/.well-known/jwks.json', 'JWK', 'GET', 1, NULL, '2020-01-01 13:48:51', NULL,
        '2020-01-01 13:48:51', NULL, 't', 'ALL');
INSERT INTO "test"."sys_res"
VALUES (1, 'home', NULL, '/', 'api 主界面', 'ALL', 1, NULL, '2019-08-06 06:55:21', NULL, '2019-08-06 06:55:21', NULL, 't',
        'ALL');
INSERT INTO "test"."sys_res"
VALUES (8, '当前用户信息', NULL, '/auth/me', '当前用户信息', 'GET', 1, NULL, '2020-01-16 13:33:41', NULL, '2020-01-16 13:33:41',
        NULL, 't', 'ALL');
INSERT INTO "test"."sys_res"
VALUES (9, '获取所有学期信息', NULL, '/api/semester/search/all', NULL, 'GET', 1, NULL, '2020-01-25 11:27:07', NULL,
        '2020-01-25 11:27:07', NULL, 't', 'ALL');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "test"."sys_role";
CREATE TABLE "test"."sys_role"
(
    "id"          int8                                        NOT NULL DEFAULT nextval('sys_role_seq'::regclass),
    "name"        varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "spell"       varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "des"         varchar(128) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "icon_cls"    varchar(55) COLLATE "pg_catalog"."default"           DEFAULT 'status_online'::character varying,
    "parent_id"   int8                                        NOT NULL DEFAULT '0'::bigint,
    "sort"        int2,
    "create_user" varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "create_time" timestamp(0)                                         DEFAULT CURRENT_TIMESTAMP,
    "modify_user" varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "modify_time" timestamp(0)                                         DEFAULT CURRENT_TIMESTAMP,
    "remark"      varchar(255) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "is_enable"   bool                                        NOT NULL DEFAULT true
)
;
ALTER TABLE "test"."sys_role"
    OWNER TO "postgres";

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO "test"."sys_role"
VALUES (3, 'ROLE_ADMIN', NULL, NULL, 'status_online', 0, 3, NULL, '2019-06-23 10:48:42', NULL, '2019-08-07 15:20:27',
        NULL, 't');
INSERT INTO "test"."sys_role"
VALUES (4, 'ROLE_TEACHER', NULL, NULL, 'status_online', 0, 4, NULL, '2019-06-23 10:48:42', NULL, '2019-08-07 15:20:27',
        NULL, 't');
INSERT INTO "test"."sys_role"
VALUES (5, 'ROLE_STUDENT', NULL, NULL, 'status_online', 0, 5, NULL, '2019-12-28 22:49:47', NULL, '2019-12-28 22:50:29',
        NULL, 't');
INSERT INTO "test"."sys_role"
VALUES (2, 'ROLE_NO_LOGIN', NULL, '不需要登录就可以访问', 'status_online', 0, 2, NULL, '2019-06-23 10:48:42', NULL,
        '2019-08-07 15:20:27', NULL, 't');
INSERT INTO "test"."sys_role"
VALUES (1, 'ROLE_PUBLIC', NULL, '需要登录，但不需要任何权限', 'status_online', 0, 1, NULL, '2019-08-06 14:37:01', NULL,
        '2019-08-07 15:19:51', NULL, 't');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS "test"."sys_role_res";
CREATE TABLE "test"."sys_role_res"
(
    "id"          int8 NOT NULL                               DEFAULT nextval('sys_role_res_seq'::regclass),
    "name"        varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "spell"       varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "role_id"     int8,
    "res_id"      int8,
    "sort"        int2,
    "create_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "create_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "modify_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "modify_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "remark"      varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "is_enable"   bool NOT NULL                               DEFAULT true
)
;
ALTER TABLE "test"."sys_role_res"
    OWNER TO "postgres";

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
BEGIN;
INSERT INTO "test"."sys_role_res"
VALUES (3, NULL, NULL, 2, 3, NULL, NULL, '2020-01-01 14:01:24', NULL, '2020-01-01 14:01:24', NULL, 't');
INSERT INTO "test"."sys_role_res"
VALUES (2, NULL, NULL, 2, 2, NULL, NULL, '2020-01-01 14:01:15', NULL, '2020-01-01 14:01:15', NULL, 't');
INSERT INTO "test"."sys_role_res"
VALUES (5, NULL, NULL, 2, 5, NULL, NULL, '2020-01-01 14:01:36', NULL, '2020-01-01 14:01:36', NULL, 't');
INSERT INTO "test"."sys_role_res"
VALUES (4, NULL, NULL, 2, 4, NULL, NULL, '2020-01-01 14:01:32', NULL, '2020-01-01 14:01:32', NULL, 't');
INSERT INTO "test"."sys_role_res"
VALUES (1, NULL, NULL, 2, 1, NULL, NULL, '2020-01-01 14:01:09', NULL, '2020-01-01 14:01:09', NULL, 't');
INSERT INTO "test"."sys_role_res"
VALUES (6, NULL, NULL, 2, 6, NULL, NULL, '2020-01-01 14:01:51', NULL, '2020-01-01 14:01:51', NULL, 't');
INSERT INTO "test"."sys_role_res"
VALUES (7, NULL, NULL, 2, 7, NULL, NULL, '2020-01-02 09:54:41', NULL, '2020-01-02 09:54:41', NULL, 't');
INSERT INTO "test"."sys_role_res"
VALUES (8, NULL, NULL, 1, 8, NULL, NULL, '2020-01-16 13:34:10', NULL, '2020-01-16 13:34:10', NULL, 't');
INSERT INTO "test"."sys_role_res"
VALUES (9, NULL, NULL, 1, 9, NULL, NULL, '2020-01-25 11:28:41', NULL, '2020-01-25 11:28:41', NULL, 't');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "test"."sys_user";
CREATE TABLE "test"."sys_user"
(
    "id"            int8                                       NOT NULL DEFAULT nextval('sys_user_seq'::regclass),
    "name"          varchar(55) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "spell"         varchar(255) COLLATE "pg_catalog"."default"         DEFAULT ''::character varying,
    "password"      varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "status"        varchar(25) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 'NORMAL'::character varying,
    "image"         varchar(255) COLLATE "pg_catalog"."default"         DEFAULT '图标：images/guest.jpg'::character varying,
    "email"         varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "phone"         varchar(20) COLLATE "pg_catalog"."default"          DEFAULT NULL::character varying,
    "online_status" bool                                                DEFAULT true,
    "sort"          int2,
    "create_user"   varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "create_time"   timestamp(0)                                        DEFAULT CURRENT_TIMESTAMP,
    "modify_user"   varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "modify_time"   timestamp(0)                                        DEFAULT CURRENT_TIMESTAMP,
    "remark"        varchar(255) COLLATE "pg_catalog"."default"         DEFAULT NULL::character varying,
    "is_enable"     bool                                       NOT NULL DEFAULT true,
    "avatar"        varchar(255) COLLATE "pg_catalog"."default"         DEFAULT '图标：images/guest.jpg'::character varying
)
;
ALTER TABLE "test"."sys_user"
    OWNER TO "postgres";

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO "test"."sys_user"
VALUES (3, 'teacher', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL',
        'http://image.japoul.cn/me.jpg', 'lizhongyue247@163.com', '13765308260', 'f', 1, NULL, '2019-04-20 17:07:50',
        'admin', '2019-08-20 22:55:17', NULL, 't', 'http://image.japoul.cn/me.jpg');
INSERT INTO "test"."sys_user"
VALUES (1, 'admin', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL',
        'http://image.japoul.cn/me.jpg', 'lizhongyue248@163.com', '13765308262', 'f', 1, NULL, '2019-04-20 17:07:50',
        'admin', '2019-08-20 22:55:17', NULL, 't', 'http://image.japoul.cn/me.jpg');
INSERT INTO "test"."sys_user"
VALUES (2, 'student', '', '$2a$12$yBk1iLIL9lV0rflIrqdyvebBIKuTmdgMn5/bjg946AQz9jpF5bQuC', 'NORMAL',
        'http://image.japoul.cn/me.jpg', 'lizhongyue246@163.com', '13765308261', 'f', 1, NULL, '2019-04-20 17:07:50',
        'admin', '2019-08-20 22:55:17', NULL, 't', 'http://image.japoul.cn/me.jpg');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "test"."sys_user_role";
CREATE TABLE "test"."sys_user_role"
(
    "id"          int8 NOT NULL                               DEFAULT nextval('sys_user_role_seq'::regclass),
    "name"        varchar(254) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "spell"       varchar(254) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "user_id"     int8 NOT NULL,
    "role_id"     int8 NOT NULL,
    "sort"        int2,
    "create_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "create_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "modify_user" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "modify_time" timestamp(0)                                DEFAULT CURRENT_TIMESTAMP,
    "remark"      varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "is_enable"   bool NOT NULL                               DEFAULT true
)
;
ALTER TABLE "test"."sys_user_role"
    OWNER TO "postgres";

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO "test"."sys_user_role"
VALUES (1, NULL, NULL, 1, 3, NULL, NULL, '2020-01-01 13:47:16', NULL, '2020-01-01 13:47:16', NULL, 't');
INSERT INTO "test"."sys_user_role"
VALUES (4, NULL, NULL, 1, 5, NULL, NULL, '2020-01-02 13:28:14', NULL, '2020-01-02 13:28:14', NULL, 't');
INSERT INTO "test"."sys_user_role"
VALUES (2, NULL, NULL, 2, 5, NULL, NULL, '2020-01-01 13:47:27', NULL, '2020-01-01 13:47:27', NULL, 't');
INSERT INTO "test"."sys_user_role"
VALUES (3, NULL, NULL, 3, 4, NULL, NULL, '2020-01-01 13:47:27', NULL, '2020-01-01 13:47:27', NULL, 't');
COMMIT;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS "test"."teacher";
CREATE TABLE "test"."teacher"
(
    "id"                   int8 NOT NULL                                DEFAULT nextval('teacher_seq'::regclass),
    "name"                 varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "spell"                varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "user_id"              int8,
    "school_id"            int8,
    "college_id"           int8,
    "dep_id"               int8,
    "gender"               varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "birthday"             date,
    "nation"               varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "degree"               varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "academic"             varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "graduation_date"      date,
    "major"                varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "graduate_institution" varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "major_research"       varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "resume"               varchar(2048) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "work_date"            date,
    "prof_title"           varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "prof_title_ass_date"  date,
    "is_academic_leader"   bool NOT NULL                                DEFAULT false,
    "subject_category"     varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "id_number"            varchar(18) COLLATE "pg_catalog"."default"   DEFAULT NULL::character varying,
    "sort"                 int2,
    "create_user"          varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "create_time"          timestamp(0)                                 DEFAULT CURRENT_TIMESTAMP,
    "modify_user"          varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "modify_time"          timestamp(0)                                 DEFAULT CURRENT_TIMESTAMP,
    "remark"               varchar(255) COLLATE "pg_catalog"."default"  DEFAULT NULL::character varying,
    "is_enable"            bool NOT NULL                                DEFAULT true
)
;
ALTER TABLE "test"."teacher"
    OWNER TO "postgres";

-- ----------------------------
-- Records of teacher
-- ----------------------------
BEGIN;
INSERT INTO "test"."teacher"
VALUES (1, '李秉贵', 'lǐ bǐng guì ', 1, 1, 2, 3, '女', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '宇宙社会学专业', '认知科学学院',
        '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010005', 39, 'yms', '2019-08-01 12:29:21', 'yms',
        '2019-08-07 16:41:39', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (2, '李厚福', 'lǐ hòu fú ', 2, 1, 30, 3, '男', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '环境工程专业',
        '化工与生态工程学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010006', 44, 'yms',
        '2019-08-07 16:41:39', 'yms', '2019-08-07 16:41:39', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (3, '李开富', 'lǐ kāi fù ', 3, 1, 2, 3, '男', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '图书管理学专业', '工程管理学院',
        '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010007', 89, 'yms', '2020-01-18 10:22:07', 'yms',
        '2020-01-18 10:22:07', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (4, '王子久', 'wáng zǐ jiǔ ', 4, 29, 2, 40, '女', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '土木工程专业',
        '材料科学与工程学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010008', 15, 'yms',
        '2020-02-05 04:46:24', 'yms', '2020-02-05 04:46:24', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (5, '刘永生', 'liú yǒng shēng ', 5, 1, 44, 19, '女', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '植被与地质专业',
        '化工与生态工程学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010009', 71, 'yms',
        '2020-02-05 04:46:32', 'yms', '2020-02-05 04:46:32', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (6, '刘宝瑞', 'liú bǎo ruì ', 6, 1, 2, 31, '男', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '航天制造工程专业',
        '材料科学与工程学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010010', 26, 'yms',
        '2020-02-05 04:46:41', 'yms', '2020-02-05 04:46:41', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (7, '关玉和', 'guān yù hé ', 7, 1, 44, 19, '男', '2020-02-05', 'China', NULL, '双学位博士', '2020-06-01', '历史学专业',
        '民族文化学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010011', 43, 'yms', '2020-02-05 04:47:16',
        'yms', '2020-02-05 04:47:16', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (8, '王仁兴', 'wáng rén xìng ', 8, 29, 44, 45, '女', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '应用数学专业',
        '数据科学与信息工程学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010012', 51, 'yms',
        '2020-02-05 04:47:20', 'yms', '2020-02-05 04:47:20', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (9, '李际泰', 'lǐ jì tài ', 9, 29, 44, 45, '男', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '网络信息工程专业',
        '数据科学与信息工程学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010001', 25, 'yms',
        '2020-02-05 04:47:22', 'yms', '2020-02-05 04:47:22', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (10, '罗元发', 'luó yuán fā ', 10, 1, 44, 45, '男', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '机械自动化专业',
        '数据科学与信息工程学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010002', 34, 'yms',
        '2020-02-05 04:47:25', 'yms', '2020-02-05 04:47:25', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (11, '刘造时', 'liú zào shí ', 11, 1, 30, 3, '女', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '计算机科学专业',
        '数据科学与信息工程学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010003', 53, 'yms',
        '2020-02-05 04:47:28', 'yms', '2020-02-05 04:47:28', '这是一位大学教师', 't');
INSERT INTO "test"."teacher"
VALUES (12, '刘乃超', 'liú nǎi chāo ', 12, 29, 18, 19, '男', '2020-02-05', 'China', NULL, '博士', '2020-06-01', '犯罪心理学专业',
        '认知科学学院', '混日子', '这是一位教师', NULL, NULL, NULL, 'f', NULL, '522501194910010004', 52, 'yms', '2020-02-05 04:47:31',
        'yms', '2020-02-05 04:47:31', '这是一位大学教师', 't');
COMMIT;

-- ----------------------------
-- Primary Key structure for table auth_center_res
-- ----------------------------
ALTER TABLE "test"."auth_center_res"
    ADD CONSTRAINT "sys_res_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table auth_center_role_res
-- ----------------------------
CREATE INDEX "sys_role_res_sys_res_id_fk_copy1" ON "test"."auth_center_role_res" USING btree (
                                                                                                "res_id"
                                                                                                "pg_catalog"."int8_ops"
                                                                                                ASC NULLS LAST
    );
CREATE INDEX "sys_role_res_sys_role_id_fk_copy1" ON "test"."auth_center_role_res" USING btree (
                                                                                                 "role_id"
                                                                                                 "pg_catalog"."int8_ops"
                                                                                                 ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table auth_center_role_res
-- ----------------------------
ALTER TABLE "test"."auth_center_role_res"
    ADD CONSTRAINT "sys_role_res_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table client_details
-- ----------------------------
ALTER TABLE "test"."client_details"
    ADD CONSTRAINT "client_details_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table semester
-- ----------------------------
CREATE INDEX "fk_school" ON "test"."semester" USING btree (
                                                             "school_id" "pg_catalog"."int8_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table semester
-- ----------------------------
ALTER TABLE "test"."semester"
    ADD CONSTRAINT "semester_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table student
-- ----------------------------
ALTER TABLE "test"."student"
    ADD CONSTRAINT "student_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_data
-- ----------------------------
ALTER TABLE "test"."sys_data"
    ADD CONSTRAINT "sys_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_res
-- ----------------------------
ALTER TABLE "test"."sys_res"
    ADD CONSTRAINT "sys_res_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "test"."sys_role"
    ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_role_res
-- ----------------------------
CREATE INDEX "sys_role_res_sys_res_id_fk" ON "test"."sys_role_res" USING btree (
                                                                                  "res_id" "pg_catalog"."int8_ops" ASC
                                                                                  NULLS LAST
    );
CREATE INDEX "sys_role_res_sys_role_id_fk" ON "test"."sys_role_res" USING btree (
                                                                                   "role_id" "pg_catalog"."int8_ops" ASC
                                                                                   NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_role_res
-- ----------------------------
ALTER TABLE "test"."sys_role_res"
    ADD CONSTRAINT "sys_role_res_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table sys_user
-- ----------------------------
ALTER TABLE "test"."sys_user"
    ADD CONSTRAINT "sys_user_email_uindex" UNIQUE ("email");
ALTER TABLE "test"."sys_user"
    ADD CONSTRAINT "sys_user_name_uindex" UNIQUE ("name");
ALTER TABLE "test"."sys_user"
    ADD CONSTRAINT "sys_user_phone_uindex" UNIQUE ("phone");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "test"."sys_user"
    ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_user_role
-- ----------------------------
CREATE INDEX "fk_ur_role" ON "test"."sys_user_role" USING btree (
                                                                   "role_id" "pg_catalog"."int8_ops" ASC NULLS LAST
    );
CREATE INDEX "fk_ur_user" ON "test"."sys_user_role" USING btree (
                                                                   "user_id" "pg_catalog"."int8_ops" ASC NULLS LAST
    );

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "test"."sys_user_role"
    ADD CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table teacher
-- ----------------------------
ALTER TABLE "test"."teacher"
    ADD CONSTRAINT "teacher_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table auth_center_role_res
-- ----------------------------
ALTER TABLE "test"."auth_center_role_res"
    ADD CONSTRAINT "auth_center_role_res_sys_role_id_fk" FOREIGN KEY ("role_id") REFERENCES "test"."sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "test"."auth_center_role_res"
    ADD CONSTRAINT "sys_role_res_copy1_res_id_fkey" FOREIGN KEY ("res_id") REFERENCES "test"."sys_res" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table semester
-- ----------------------------
ALTER TABLE "test"."semester"
    ADD CONSTRAINT "semester_school_ibfk" FOREIGN KEY ("school_id") REFERENCES "test"."sys_data" ("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- ----------------------------
-- Foreign Keys structure for table sys_role_res
-- ----------------------------
ALTER TABLE "test"."sys_role_res"
    ADD CONSTRAINT "sys_role_res_sys_res_id_fk" FOREIGN KEY ("res_id") REFERENCES "test"."sys_res" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "test"."sys_role_res"
    ADD CONSTRAINT "sys_role_res_sys_role_id_fk" FOREIGN KEY ("role_id") REFERENCES "test"."sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table sys_user_role
-- ----------------------------
ALTER TABLE "test"."sys_user_role"
    ADD CONSTRAINT "fk_ur_role" FOREIGN KEY ("role_id") REFERENCES "test"."sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "test"."sys_user_role"
    ADD CONSTRAINT "fk_ur_user" FOREIGN KEY ("user_id") REFERENCES "test"."sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
