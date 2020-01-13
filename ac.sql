/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : ac

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2020-01-13 13:40:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `ac_sqcode`
-- ----------------------------
DROP TABLE IF EXISTS `ac_sqcode`;
CREATE TABLE `ac_sqcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '授权码表',
  `name` varchar(255) DEFAULT NULL COMMENT '授权名称',
  `sqcode` varchar(255) DEFAULT NULL COMMENT '授权码',
  `realpath` varchar(255) DEFAULT NULL COMMENT '真实存储地址',
  `sqentityssid` varchar(255) DEFAULT NULL COMMENT '授权信息ssid',
  `ssid` varchar(255) DEFAULT NULL COMMENT '唯一标识',
  `string1` varchar(255) DEFAULT NULL,
  `string2` varchar(255) DEFAULT NULL,
  `integer1` int(11) DEFAULT NULL,
  `integer2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ac_sqcode
-- ----------------------------
INSERT INTO `ac_sqcode` VALUES ('1', 'aaaaaaaaaaaa', 'bbbb', null, '1', '1', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('2', '11', 'aaa', null, 'f2417bb4aa8e45ddb42b0ccee542c358', '2a5c58c9acf041488d3883883b007a41', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('3', '22', 'bbb', null, '879cd5a962ac4de89004273c02bd6d57', '00c2fd8029584c16a96f86ddf8086396', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('4', '11', 'aaa', null, '4c4d32639f5a423792dab423d1f96576', '35fba54ee9b94e5e9777c86e1ee3a83c', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('5', '22', 'bbb', null, '3e1b6f01f0fd4f2ca752696b7e560c88', '6e4eb5f931244ea193247358d743214f', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('6', '11', 'aaaaa', null, '106f0da6177a44059284d0b02e9dd2c5', '2ef1046a28a840fcadc15346ae0c333f', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('7', '22', 'bbbb', null, '106f0da6177a44059284d0b02e9dd2c5', '9acd7ad217924f0395789d67feb76d2f', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('8', '1', '41575253575357574141414441475444', 'D:\\java\\AC\\sq\\20191121101505\\bjkj', '676b0c25c1a34b588a4c48d857d0af34', '7f02fa6becf9469bbeb7d5b06752586d', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('9', '1', 'aaaa', 'D:\\java\\AC\\sq\\20191121120155\\jd\\javatrm.ini', '4901e21aa21c46e18d2f0e066ac4fac6', '18954fcaa0b24bd4a8f6a101aae09a67', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('10', '1', 'bbb', 'D:\\java\\AC\\sq\\20191121133805\\44\\javatrm.ini', '7f0ebee01aaf4f7dbb5d6e1fdcc96668', '4918524afa26418883dbfdabb082be96', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('11', '1', 'haha', 'D:\\java\\AC\\sq\\20191121134120\\qiguo\\javatrm.ini', '9ee210fb3a2e4a379f8c1930c40b735d', '89e3c77903734173b74ac61dee2192f3', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('12', '1', 'qqqq', 'D:\\java\\AC\\sq\\20191121134548\\qinguo\\javatrm.ini', '0e1bd53ff5f84d6f9c0810fbdf43f927', 'e0fa2312738b4aaaa045863202920598', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('13', '0', 'ccccccc', 'D:\\java\\AC\\sq\\20191121135716\\chuguo\\javatrm.ini', 'bfdf497a1c7d4d9688a003bc9ff66def', '8351f33dc0b1403aa4e2e068a89787f7', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('14', '1', '999999999999', 'D:\\java\\AC\\sq\\20191121144637\\weiguo\\javatrm.ini', '64c43105459a44d2b17cccb643003c82', '10aedab500864ed3bf3efb75ed3cf490', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('15', '一号机', 'aaaaaaaaaa', 'D:\\java\\AC\\sq\\20191121145956\\zhonghua\\javatrm.ini', '968c17d1ce8647bbaf8cb4fc7dd97cea', 'af84c118b73841c6b436777316ae1c33', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('16', '二号机', 'wwwwwwwwww', 'D:\\java\\AC\\sq\\20191121145956\\zhonghua\\javatrm.ini', '968c17d1ce8647bbaf8cb4fc7dd97cea', '4573b577fe4f4648a411fd6a4fc90f49', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('17', '三号机', 'mmmmmmmmmmm', 'D:\\java\\AC\\sq\\20191121145956\\zhonghua\\javatrm.ini', '968c17d1ce8647bbaf8cb4fc7dd97cea', '06952458612d484cb0b53cf185c3e38e', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('18', '1', 'zhongguo', 'D:\\java\\AC\\sq\\20191121150712\\junren\\javatrm.ini', '5b5f30c5b3e9459a952fd437e3eaf307', '5af73fe10f074da99d27883500b446f2', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('19', '2', 'jiefangjun', 'D:\\java\\AC\\sq\\20191121150712\\junren\\javatrm.ini', '5b5f30c5b3e9459a952fd437e3eaf307', 'efa6d56116b64ba49ce7990e598bc21c', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('20', '3', 'xianzailaile', 'D:\\java\\AC\\sq\\20191121150712\\junren\\javatrm.ini', '5b5f30c5b3e9459a952fd437e3eaf307', 'dc1b5c1ca08743c4a1a7cd476338f1f1', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('21', 'sq1', 'aaaaaaaaaaa', 'D:\\java\\AC\\sq\\20191122171001\\haoda\\javatrm.ini', 'be7adb91ac8840ceb04184b732efb5df', 'cdc50c043c034348867bd2795142aeed', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('22', 'sq2', 'bbbbbbbbbbb', 'D:\\java\\AC\\sq\\20191122171002\\haoda\\javatrm.ini', 'be7adb91ac8840ceb04184b732efb5df', 'ec7c40f95b304b9d9ca76254f2a4d0b0', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('23', 'sq3', 'cccccccccccccc', 'D:\\java\\AC\\sq\\20191122171002\\haoda\\javatrm.ini', 'be7adb91ac8840ceb04184b732efb5df', '67cff75ee9c749d09395341b34a17b6d', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('24', '1', 'qqwwww', 'D:\\java\\AC\\sq\\20191122171733\\renda\\javatrm.ini', 'de751822b97143d78c31c4073989c450', 'd268256217ae44cc9cb8173e48bb8784', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('25', 'sq1', 'aaaaaaaaaaa', 'D:\\java\\AC\\sq\\20191126100745\\danwei\\javatrm.ini', '1dd0dcfff34e4842af9696dfa45b5eeb', '691cce46c29a44f485910b4d57ec193e', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('26', 'sq2', 'bbbbbbbbbbb', 'D:\\java\\AC\\sq\\20191126100745\\danwei\\javatrm.ini', '1dd0dcfff34e4842af9696dfa45b5eeb', '39b56aa4a2654aa4a115edf773157896', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('27', 'sq3', 'cccccccccccccc', 'D:\\java\\AC\\sq\\20191126100745\\danwei\\javatrm.ini', '1dd0dcfff34e4842af9696dfa45b5eeb', '2b848a5519c842cdab260969ff26f488', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('28', '1', '41575253575357574141414441475444', 'D:\\java\\AC\\sq\\20191126104709\\taobb\\javatrm.ini', 'c084e206ac634a93a6f106f202d1fba7', '671f7a61afec44d6b123b2cd5bb4d864', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('29', '1', '535756535753575741414144414754444243454344444943', 'D:\\java\\AC\\sq\\20191126110355\\taiwan\\javatrm.ini', 'b455c16052e646129fcf7afdac063a04', '2721764ec33b4d27b2d6f46fbcd62275', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('30', '1', '535756535753575741414144414754444243454344444943', 'D:\\java\\AC\\sq\\20191126110954\\44\\javatrm.ini', '0e404c980f2a4e60b2865dabd17ecaed', 'a09b916b811344ef8934ad5a13411c30', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('31', '1', '535756535753575741414144414754444243454344444943', 'D:\\java\\AC\\sq\\20191126111630\\44\\javatrm.ini', 'edce714648b64f7593ce02bfc3e6882a', '9b4239d900074b05bb13d5c98b326acd', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('32', '1', '535756535753575741414144414754444243454344444943', 'D:\\java\\AC\\sq\\20191126112536\\44\\javatrm.ini', 'aad86993cfb149968c06f962c15232ca', '75261a5a70b545d880be7707bbe8c979', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('33', '1', 'ssss', 'D:\\java\\AC\\sq\\20191126165033\\44\\javatrm.ini', 'ed7f3760c2ac4f3b9a0fc18de2b0d5df', 'ee84e655f2ff440491fe4e064e3141b4', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('34', '1', 'aaaa', 'D:\\java\\AC\\sq\\20191127103519\\44\\javatrm.ini', '28bb967f7a1e42098e4fdb98a5e57334', '7603549104d349899b67903d8f0f48b9', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('35', '1', '535756535753575741414144414754444243454344444943', 'D:\\java\\AC\\sq\\20191209110007\\nx\\javatrm.ini', 'bf22c4733d5e41439c7cbb78439adf77', '4b57639645ff48e69e45a6ec8910c41f', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('36', '1', '1111111111', 'D:\\java\\AC\\sq\\2020\\01\\08\\44\\javatrm.ini', '6f1dee2cbb144e909b6e843573043f22', '802afe007b584290853bd10c5a30a015', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('37', '1', 'sss', 'D:\\java\\AC\\sq\\2020\\01\\13\\44\\javatrm.ini', '0ae8af92209c4358bae02c99ae929844', 'fe2d6aeb6b1444f89d55f1ac8ff51762', null, null, null, null);
INSERT INTO `ac_sqcode` VALUES ('40', '1', '1111', 'D:\\java\\AC\\sq\\2020\\01\\13\\44\\javatrm.ini', 'b615607b54cd461c949a5424cce0faea', 'f0bf6d8e45b548b9ba17c9c09fa94b36', null, null, null, null);

-- ----------------------------
-- Table structure for `ac_sqentityplus`
-- ----------------------------
DROP TABLE IF EXISTS `ac_sqentityplus`;
CREATE TABLE `ac_sqentityplus` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '授权表',
  `username` varchar(255) DEFAULT NULL COMMENT '申请人名称',
  `servertype` varchar(255) DEFAULT NULL COMMENT '授权服务类型',
  `batypessid` varchar(255) DEFAULT NULL COMMENT '授权类型ssid',
  `foreverBool` int(11) DEFAULT '0' COMMENT '是否是永久授权',
  `startTime` varchar(255) DEFAULT NULL COMMENT '授权开始时间',
  `cpuCode` varchar(255) DEFAULT NULL COMMENT 'CPU序列号',
  `sqsize` int(11) DEFAULT NULL COMMENT '授权台数',
  `sqDay` int(11) DEFAULT NULL COMMENT '授权总天数',
  `companyname` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `clientName` varchar(255) DEFAULT NULL COMMENT '单位名称',
  `unitCode` varchar(255) DEFAULT NULL COMMENT '单位代码',
  `sortNum` int(11) DEFAULT '0' COMMENT '排序，多台客户端的时候',
  `gnlist` varchar(255) DEFAULT NULL COMMENT '功能列表',
  `companymsg` text COMMENT '公司简介',
  `ssid` varchar(255) DEFAULT NULL COMMENT '唯一标识',
  `state` int(11) DEFAULT '1' COMMENT '状态/0删除/1正常',
  `string1` varchar(255) DEFAULT NULL,
  `string2` varchar(255) DEFAULT NULL,
  `integer1` int(11) DEFAULT NULL,
  `integer2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ac_sqentityplus
-- ----------------------------
INSERT INTO `ac_sqentityplus` VALUES ('1', '销售人员', 'court', '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-14 17:41:14', '535756535753575741414144414754444243454344444943', '11', '1', '阿里巴巴传说', '测试单位', '0000', '1', 'record_f|asr_f|tts_f|fd_f|ph_f', '是中国最大的电商平台', '1', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('2', '销售员', 'court', '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-20 18:36:02', '535756535753575741414144414754444243454344444943', null, '88', null, '淘宝', 'taobao', '0', 'record_f|asr_f|s_v|ga_t|common_o|c_e|{companyname:阿里巴巴,companymsg:真是个号电商}', null, '4c4d32639f5a423792dab423d1f96576', '0', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('3', '销售员', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-20 18:06:13', 'bbb', null, '88', null, '淘宝', 'taobao', '0', 'record_f|asr_f|s_v|ga_t|common_o|c_e|{companyname:阿里巴巴,companymsg:真是个号电商}', null, '3e1b6f01f0fd4f2ca752696b7e560c88', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('4', '大哥哥', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-20 18:54:07', 'bbbb', null, '88', null, '京东商城', 'jd', '0', 'record_f|asr_f|ph_f|o_v|jw_t|hk_o|s_e|{companyname:京东,companymsg:我是大佬}', null, '106f0da6177a44059284d0b02e9dd2c5', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('5', '张三', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-21 10:15:05', '41575253575357574141414441475444', null, '99', null, '北京科技', 'bjkj', '0', 'record_f|asr_f|tts_f|s_v|ga_t|common_o|c_e|{companyname:张氏集团,companymsg:大公司来的}', null, '676b0c25c1a34b588a4c48d857d0af34', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('6', '测试人', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-26 09:34:12', '535756535753575741414144414754444243454344444943', null, '99', null, '京东销售部', 'jd', '0', 'record_f|asr_f|s_v|ga_t|common_o|c_e|{companyname:京东集团,companymsg:饭堂}', null, '4901e21aa21c46e18d2f0e066ac4fac6', '0', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('7', '11', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-25 18:00:40', '535756535753575741414144414754444243454344444943', null, '66', null, '33', '44', '0', 'record_f|s_v|ga_t|common_o|c_e|{companyname:22,companymsg:aaaa}', null, '7f0ebee01aaf4f7dbb5d6e1fdcc96668', '0', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('8', '齐国商人', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-21 13:41:20', 'haha', '100', '1000', '齐国皇朝', '齐国电器', 'qiguo', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|o_v|fy_t|avst_o|s_e|{companyname:齐国皇朝,companymsg:齐国最大的企业}', null, '9ee210fb3a2e4a379f8c1930c40b735d', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('9', '秦国商人', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '1', '2019-11-21 13:45:48', 'qqqq', '88', '0', '秦国皇朝', '秦国咸阳高科技', 'qinguo', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|o_v|jw_t|hk_o|s_e|{companyname:秦国皇朝,companymsg:是个好公司}', '是个好公司', '0e1bd53ff5f84d6f9c0810fbdf43f927', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('10', '楚国丞相', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-21 13:57:16', 'ccccccc', '77', '200', '楚国南方企业有限公司', '南方电子商务', 'chuguo', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|o_v|jcw_t|nx_o|s_e|{companyname:楚国南方企业有限公司,companymsg:绝对是超大的公司}', '绝对是超大的公司', 'bfdf497a1c7d4d9688a003bc9ff66def', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('11', '魏国大夫', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-21 14:46:37', '999999999999', '66', '60', '魏国商贸有限公司', '魏国交通银行', 'weiguo', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|s_v|jw_t|nx_o|s_e|{companyname:魏国商贸有限公司,companymsg:是个信誉不错的公司}', '是个信誉不错的公司', '64c43105459a44d2b17cccb643003c82', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('12', '中华商人', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-21 14:59:56', 'mmmmmmmmmmm', '77', '90', '中华大公司', '中华电子有限公司', 'zhonghua', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|o_v|fy_t|avst_o|s_e|{companyname:中华大公司,companymsg:有前途的企业}', '有前途的企业', '968c17d1ce8647bbaf8cb4fc7dd97cea', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('13', '中国人民解放军', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-21 15:07:12', 'xianzailaile', '22', '88', '中国国防部', '中国下乡', 'junren', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|s_v|ga_t|common_o|c_e|{companyname:中国国防部,companymsg:开始语音识别测试}', '开始语音识别测试', '5b5f30c5b3e9459a952fd437e3eaf307', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('14', '新加入测试', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-22 17:10:01', 'cccccccccccccc', '3', '90', '大公司', '好的哪位', 'haoda', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|s_v|ga_t|common_o|c_e|{companyname:大公司,companymsg:其实是大公司来的}', '其实是大公司来的', 'be7adb91ac8840ceb04184b732efb5df', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('15', '总裁顾问', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-22 17:17:33', 'qqwwww', '8', '30', '任达集团', '海星审讯', 'renda', '0', 'record_f|asr_f|ph_f|s_v|ga_t|common_o|c_e|{companyname:任达集团,companymsg:好吧}', '好吧', 'de751822b97143d78c31c4073989c450', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('16', '测试人一', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-26 10:07:45', 'cccccccccccccc', '8', '99', '测试公司', '测试单位', 'danwei', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|s_v|ga_t|common_o|c_e|{companyname:测试公司,companymsg:aaaaaaa}', 'aaaaaaa', '1dd0dcfff34e4842af9696dfa45b5eeb', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('17', '测试麟', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-26 10:47:09', '41575253575357574141414441475444', '8', '22', '大公文惠', '小淘宝', 'taobb', '0', 'asr_f|o_v|jw_t|hk_o|s_e|{companyname:大公文惠,companymsg:aabbb}', 'aabbb', 'c084e206ac634a93a6f106f202d1fba7', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('18', '测试麟', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-26 11:03:55', '535756535753575741414144414754444243454344444943', '12', '99', '顺泰伟诚', '台湾工业公司', 'taiwan', '0', 'asr_f|o_v|jw_t|hk_o|s_e|{companyname:顺泰伟诚,companymsg:是一个大企业}', '是一个大企业', 'b455c16052e646129fcf7afdac063a04', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('19', '11', null, '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-26 11:09:54', '535756535753575741414144414754444243454344444943', '55', '66', '22', '33', '44', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|s_v|ga_t|common_o|c_e|{companyname:22,companymsg:aaa}', 'aaa', '0e404c980f2a4e60b2865dabd17ecaed', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('20', '11', 'court', '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-26 11:16:30', '535756535753575741414144414754444243454344444943', '55', '66', '22', '33', '44', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|s_v|ga_t|common_o|c_e|{companyname:22,companymsg:aaaa}', 'aaaa', 'edce714648b64f7593ce02bfc3e6882a', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('21', '11', 'police', '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-26 11:25:36', '535756535753575741414144414754444243454344444943', '55', '66', '22', '33', '44', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|s_v|ga_t|common_o|c_e|{companyname:22,companymsg:aaaaa}', 'aaaaa', 'aad86993cfb149968c06f962c15232ca', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('22', '11', 'police', '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-26 16:50:33', 'ssss', '55', '88', '22', '33', '44', '0', 'record_f|o_v|jw_t|nx_o|s_e|{companyname:22,companymsg:aaa}', 'aaa', 'ed7f3760c2ac4f3b9a0fc18de2b0d5df', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('23', '1111', 'police', '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-11-27 14:21:36', '535756535753575741414144414754444243454344444943', '55', '66', '222222', '3333', '44', '0', 'aaaaaaa|s_v|ga_t|common_o|c_e|{companyname:222222,companymsg:aaa}', 'aaa', '28bb967f7a1e42098e4fdb98a5e57334', '0', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('24', '测试麟', 'police', '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2019-12-09 11:00:07', '535756535753575741414144414754444243454344444943', '1', '99', '宁夏公司', '宁夏法院', 'nx', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|o_v|fy_t|nx_o|c_e|{companyname:宁夏公司,companymsg:没有的}', '没有的', 'bf22c4733d5e41439c7cbb78439adf77', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('25', '11', 'police', '92cb9dc8db0e4ecba7206f9ac9e3defc', '0', '2020-01-08 14:29:15', '1111111111', '55', '66', '22', '33', '44', '0', 'record_f|asr_f|tts_f|fd_f|ph_f|aaaaaaa|s_v|ga_t|common_o|c_e|{companyname:22,companymsg:aaaaa}', 'aaaaa', '6f1dee2cbb144e909b6e843573043f22', '1', null, null, null, null);
INSERT INTO `ac_sqentityplus` VALUES ('26', '11', 'police', 'e090c3b065cc4258ad4a0afc4eb54682', '0', '2020-01-13 09:41:50', '1111', '55', '66', '22', '33', '44', '0', 'record_f|s_v|ga_t|common_o|c_e|{companyname:22,companymsg:77}', '77', 'b615607b54cd461c949a5424cce0faea', '1', null, null, null, null);

-- ----------------------------
-- Table structure for `ac_user`
-- ----------------------------
DROP TABLE IF EXISTS `ac_user`;
CREATE TABLE `ac_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员表',
  `loginaccount` varchar(255) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `ssid` varchar(255) DEFAULT NULL COMMENT '唯一标识',
  `userbool` int(11) DEFAULT '1' COMMENT '0禁用/1正常',
  `lastlogintime` timestamp NULL DEFAULT NULL COMMENT '最后一次登陆时间',
  `string1` varchar(255) DEFAULT NULL,
  `string2` varchar(255) DEFAULT NULL,
  `integer1` int(11) DEFAULT NULL,
  `integer2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ac_user
-- ----------------------------
INSERT INTO `ac_user` VALUES ('1', 'admin', 'admin', '1', '1', '2020-01-13 13:36:01', null, null, null, null);

-- ----------------------------
-- Table structure for `base_gninfo`
-- ----------------------------
DROP TABLE IF EXISTS `base_gninfo`;
CREATE TABLE `base_gninfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '授权功能表',
  `name` varchar(255) DEFAULT NULL COMMENT '授权代号',
  `title` varchar(255) DEFAULT NULL COMMENT '功能标题',
  `bgntypessid` varchar(255) DEFAULT NULL COMMENT '功能类型ssid',
  `ssid` varchar(255) DEFAULT NULL COMMENT '唯一标识',
  `string1` varchar(255) DEFAULT NULL,
  `string2` varchar(255) DEFAULT NULL,
  `integer1` int(11) DEFAULT NULL,
  `integer2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_gninfo
-- ----------------------------
INSERT INTO `base_gninfo` VALUES ('1', 'record_f', '笔录管理', '1', '1', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('2', 'asr_f', '语音识别', '1', '2', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('3', 'tts_f', '语音播报', '1', '3', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('4', 'fd_f', '设备控制', '1', '4', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('5', 'ph_f', '测谎仪', '1', '5', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('6', 's_v', '单机版', '2', '6', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('7', 'o_v', '联机版', '2', '7', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('8', 'ga_t', '公安', '3', '8', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('9', 'jw_t', '纪委', '3', '9', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('10', 'jcw_t', '监察委', '3', '10', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('11', 'fy_t', '法院', '3', '11', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('12', 'common_o', '通用', '4', '12', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('13', 'hk_o', 'HK', '4', '13', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('14', 'nx_o', 'NX', '4', '14', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('15', 'avst_o', 'AVST', '4', '15', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('16', 'c_e', '客户端版', '5', '16', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('17', 's_e', '服务端版', '5', '17', null, null, null, null);
INSERT INTO `base_gninfo` VALUES ('20', 'aaaaaaa', '我我', '1', 'cb121bb9ec5d4206be887e7a00c15df7', null, null, null, null);

-- ----------------------------
-- Table structure for `base_gntype`
-- ----------------------------
DROP TABLE IF EXISTS `base_gntype`;
CREATE TABLE `base_gntype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '授权栏目表',
  `typename` varchar(255) DEFAULT NULL COMMENT '栏目名称',
  `typecode` varchar(255) DEFAULT NULL COMMENT '栏目代码',
  `type` int(11) DEFAULT '1' COMMENT '0多选框/1单选框',
  `ordernum` int(11) DEFAULT '0' COMMENT '排序',
  `ssid` varchar(255) DEFAULT NULL COMMENT '唯一标识',
  `string1` varchar(255) DEFAULT NULL,
  `string2` varchar(255) DEFAULT NULL,
  `integer1` int(11) DEFAULT NULL,
  `integer2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_gntype
-- ----------------------------
INSERT INTO `base_gntype` VALUES ('1', '客户端的功能列表', '', '0', '0', '1', null, null, null, null);
INSERT INTO `base_gntype` VALUES ('2', '单机版/联机版', 'version', '1', '1', '2', null, null, null, null);
INSERT INTO `base_gntype` VALUES ('3', '分支版本', 'fen', '1', '0', '3', null, null, null, null);
INSERT INTO `base_gntype` VALUES ('4', 'OEM版本', 'oem', '1', '0', '4', null, null, null, null);
INSERT INTO `base_gntype` VALUES ('5', '客户端版/服务端版', 'duan', '1', '0', '5', null, null, null, null);

-- ----------------------------
-- Table structure for `base_type`
-- ----------------------------
DROP TABLE IF EXISTS `base_type`;
CREATE TABLE `base_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `typename` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `ordernum` int(11) DEFAULT '0' COMMENT '排序',
  `ssid` varchar(255) DEFAULT NULL COMMENT '类型ssid',
  `string1` varchar(255) DEFAULT NULL,
  `string2` varchar(255) DEFAULT NULL,
  `integer1` varchar(255) DEFAULT NULL,
  `integer2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_type
-- ----------------------------
INSERT INTO `base_type` VALUES ('1', '测试', '0', '92cb9dc8db0e4ecba7206f9ac9e3defc', null, null, null, null);
INSERT INTO `base_type` VALUES ('2', '临时', '0', 'e090c3b065cc4258ad4a0afc4eb54682', null, null, null, null);
INSERT INTO `base_type` VALUES ('3', '销售', '0', 'ae94cc0b5b6b44679b2f8c5ea1a486f6', null, null, null, null);
