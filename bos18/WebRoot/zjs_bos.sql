/*
Navicat MySQL Data Transfer

Source Server         : zjs
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : zjs_bos

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2015-07-25 11:06:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `auth_function`
-- ----------------------------
DROP TABLE IF EXISTS `auth_function`;
CREATE TABLE `auth_function` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) default NULL,
  `code` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  `page` varchar(255) default NULL,
  `generatemenu` varchar(255) default NULL,
  `zindex` int(11) default NULL,
  `pid` varchar(32) default NULL,
  PRIMARY KEY  (`id`),
  KEY `AK_Key_2` (`name`),
  KEY `FK_Reference_10` (`pid`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`pid`) REFERENCES `auth_function` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_function
-- ----------------------------
INSERT INTO `auth_function` VALUES ('11', '基础档案', 'jichudangan', null, null, '1', '0', null);
INSERT INTO `auth_function` VALUES ('112', '收派标准', 'standard', null, 'page_base_standard.action', '1', '1', '11');
INSERT INTO `auth_function` VALUES ('113', '取派员设置', 'staff', null, 'page_base_staff.action', '1', '2', '11');
INSERT INTO `auth_function` VALUES ('114', '区域设置', 'region', null, 'page_base_region.action', '1', '3', '11');
INSERT INTO `auth_function` VALUES ('115', '管理分区', 'subarea', null, 'page_base_subarea.action', '1', '4', '11');
INSERT INTO `auth_function` VALUES ('116', '管理定区/调度排班', 'decidedzone', null, 'page_base_decidedzone.action', '1', '5', '11');
INSERT INTO `auth_function` VALUES ('12', '受理', 'shouli', null, null, '1', '1', null);
INSERT INTO `auth_function` VALUES ('121', '业务受理', 'noticebill', null, 'page_qupai_noticebill_add.action', '1', '0', '12');
INSERT INTO `auth_function` VALUES ('122', '工作单快速录入', 'quickworkordermanage', null, 'page_qupai_quickworkorder.action', '1', '1', '12');
INSERT INTO `auth_function` VALUES ('124', '工作单导入', 'workordermanageimport', null, 'page_qupai_workorderimport.action', '1', '3', '12');
INSERT INTO `auth_function` VALUES ('13', '调度', 'diaodu', null, null, '1', '2', null);
INSERT INTO `auth_function` VALUES ('131', '查台转单', 'changestaff', null, null, '1', '0', '13');
INSERT INTO `auth_function` VALUES ('132', '人工调度', 'personalassign', null, 'page_qupai_diaodu.action', '1', '1', '13');
INSERT INTO `auth_function` VALUES ('14', '物流配送流程管理', 'zhongzhuan', null, null, '1', '3', null);
INSERT INTO `auth_function` VALUES ('141', '启动配送流程', 'start', null, 'workOrderManageAction_list.action', '1', '0', '14');
INSERT INTO `auth_function` VALUES ('142', '查看个人任务', 'personaltask', null, 'taskAction_findPersonalTask.action', '1', '1', '14');
INSERT INTO `auth_function` VALUES ('143', '查看我的组任务', 'grouptask', null, 'taskAction_findGroupTask.action', '1', '2', '14');

-- ----------------------------
-- Table structure for `auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) default NULL,
  `code` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `AK_Key_2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES ('297efcf84eaa48e0014eaa54bba30000', '仓库管理员', 'ckgly', '');
INSERT INTO `auth_role` VALUES ('297efcf8765719e30176571a5c1b0000', '普通用户角色', 'common', '');

-- ----------------------------
-- Table structure for `bc_decidedzone`
-- ----------------------------
DROP TABLE IF EXISTS `bc_decidedzone`;
CREATE TABLE `bc_decidedzone` (
  `id` varchar(32) NOT NULL,
  `name` varchar(30) default NULL,
  `staff_id` varchar(32) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_decidedzone_staff` (`staff_id`),
  CONSTRAINT `FK_decidedzone_staff` FOREIGN KEY (`staff_id`) REFERENCES `bc_staff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_decidedzone
-- ----------------------------
INSERT INTO `bc_decidedzone` VALUES ('dq001', '育新定区', 'qpy002');
INSERT INTO `bc_decidedzone` VALUES ('qpy002', 'test', 'qpy001');

-- ----------------------------
-- Table structure for `bc_region`
-- ----------------------------
DROP TABLE IF EXISTS `bc_region`;
CREATE TABLE `bc_region` (
  `id` varchar(32) NOT NULL,
  `province` varchar(50) default NULL,
  `city` varchar(50) default NULL,
  `district` varchar(50) default NULL,
  `postcode` varchar(50) default NULL,
  `shortcode` varchar(30) default NULL,
  `citycode` varchar(30) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_region
-- ----------------------------
INSERT INTO `bc_region` VALUES ('QY001', '北京市', '北京市', '东城区', '110101', 'BJSBJSDCQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY002', '北京市', '北京市', '西城区', '110102', 'BJSBJSXCQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY003', '北京市', '北京市', '朝阳区', '110105', 'BJSBJSCYQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY004', '北京市', '北京市', '丰台区', '110106', 'BJSBJSFTQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY005', '北京市', '北京市', '石景山区', '110107', 'BJSBJSSJSQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY006', '北京市', '北京市', '海淀区', '110108', 'BJSBJSHDQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY007', '北京市', '北京市', '门头沟区', '110109', 'BJSBJSMTGQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY008', '北京市', '北京市', '房山区', '110111', 'BJSBJSFSQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY009', '北京市', '北京市', '通州区', '110112', 'BJSBJSTZQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY010', '北京市', '北京市', '顺义区', '110113', 'BJSBJSSYQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY011', '北京市', '北京市', '昌平区', '110114', 'BJSBJSCPQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY012', '北京市', '北京市', '大兴区', '110115', 'BJSBJSDXQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY013', '北京市', '北京市', '怀柔区', '110116', 'BJSBJSHRQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY014', '北京市', '北京市', '平谷区', '110117', 'BJSBJSPGQ', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY015', '北京市', '北京市', '密云县', '110228', 'BJSBJSMYX', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY016', '北京市', '北京市', '延庆县', '110229', 'BJSBJSYQX', 'beijingshi');
INSERT INTO `bc_region` VALUES ('QY017', '河北省', '石家庄市', '长安区', '130102', 'HBSSJZSZAQ', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY018', '河北省', '石家庄市', '桥东区', '130103', 'HBSSJZSQDQ', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY019', '河北省', '石家庄市', '桥西区', '130104', 'HBSSJZSQXQ', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY020', '河北省', '石家庄市', '新华区', '130105', 'HBSSJZSXHQ', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY021', '河北省', '石家庄市', '井陉矿区', '130107', 'HBSSJZSJXKQ', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY022', '河北省', '石家庄市', '裕华区', '130108', 'HBSSJZSYHQ', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY023', '河北省', '石家庄市', '井陉县', '130121', 'HBSSJZSJXX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY024', '河北省', '石家庄市', '正定县', '130123', 'HBSSJZSZDX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY025', '河北省', '石家庄市', '栾城县', '130124', 'HBSSJZSLCX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY026', '河北省', '石家庄市', '行唐县', '130125', 'HBSSJZSXTX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY027', '河北省', '石家庄市', '灵寿县', '130126', 'HBSSJZSLSX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY028', '河北省', '石家庄市', '高邑县', '130127', 'HBSSJZSGYX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY029', '河北省', '石家庄市', '深泽县', '130128', 'HBSSJZSSZX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY030', '河北省', '石家庄市', '赞皇县', '130129', 'HBSSJZSZHX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY031', '河北省', '石家庄市', '无极县', '130130', 'HBSSJZSWJX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY032', '河北省', '石家庄市', '平山县', '130131', 'HBSSJZSPSX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY033', '河北省', '石家庄市', '元氏县', '130132', 'HBSSJZSYSX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY034', '河北省', '石家庄市', '赵县', '130133', 'HBSSJZSZX', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY035', '河北省', '石家庄市', '辛集市', '130181', 'HBSSJZSXJS', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY036', '河北省', '石家庄市', '藁城市', '130182', 'HBSSJZSGCS', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY037', '河北省', '石家庄市', '晋州市', '130183', 'HBSSJZSJZS', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY038', '河北省', '石家庄市', '新乐市', '130184', 'HBSSJZSXLS', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY039', '河北省', '石家庄市', '鹿泉市', '130185', 'HBSSJZSLQS', 'shijiazhuangshi');
INSERT INTO `bc_region` VALUES ('QY040', '天津市', '天津市', '和平区', '120101', 'TJSTJSHPQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY041', '天津市', '天津市', '河东区', '120102', 'TJSTJSHDQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY042', '天津市', '天津市', '河西区', '120103', 'TJSTJSHXQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY043', '天津市', '天津市', '南开区', '120104', 'TJSTJSNKQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY044', '天津市', '天津市', '河北区', '120105', 'TJSTJSHBQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY045', '天津市', '天津市', '红桥区', '120106', 'TJSTJSHQQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY046', '天津市', '天津市', '滨海新区', '120116', 'TJSTJSBHXQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY047', '天津市', '天津市', '东丽区', '120110', 'TJSTJSDLQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY048', '天津市', '天津市', '西青区', '120111', 'TJSTJSXQQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY049', '天津市', '天津市', '津南区', '120112', 'TJSTJSJNQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY050', '天津市', '天津市', '北辰区', '120113', 'TJSTJSBCQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY051', '天津市', '天津市', '武清区', '120114', 'TJSTJSWQQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY052', '天津市', '天津市', '宝坻区', '120115', 'TJSTJSBCQ', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY053', '天津市', '天津市', '宁河县', '120221', 'TJSTJSNHX', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY054', '天津市', '天津市', '静海县', '120223', 'TJSTJSJHX', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY055', '天津市', '天津市', '蓟县', '120225', 'TJSTJSJX', 'tianjinshi');
INSERT INTO `bc_region` VALUES ('QY056', '山西省', '太原市', '小店区', '140105', 'SXSTYSXDQ', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY057', '山西省', '太原市', '迎泽区', '140106', 'SXSTYSYZQ', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY058', '山西省', '太原市', '杏花岭区', '140107', 'SXSTYSXHLQ', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY059', '山西省', '太原市', '尖草坪区', '140108', 'SXSTYSJCPQ', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY060', '山西省', '太原市', '万柏林区', '140109', 'SXSTYSWBLQ', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY061', '山西省', '太原市', '晋源区', '140110', 'SXSTYSJYQ', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY062', '山西省', '太原市', '清徐县', '140121', 'SXSTYSQXX', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY063', '山西省', '太原市', '阳曲县', '140122', 'SXSTYSYQX', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY064', '山西省', '太原市', '娄烦县', '140123', 'SXSTYSLFX', 'taiyuanshi');
INSERT INTO `bc_region` VALUES ('QY065', '山西省', '太原市', '古交市', '140181', 'SXSTYSGJS', 'taiyuanshi');

-- ----------------------------
-- Table structure for `bc_staff`
-- ----------------------------
DROP TABLE IF EXISTS `bc_staff`;
CREATE TABLE `bc_staff` (
  `id` varchar(32) NOT NULL,
  `name` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `haspda` char(1) default NULL,
  `deltag` char(1) default NULL,
  `station` varchar(40) default NULL,
  `standard` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_staff
-- ----------------------------
INSERT INTO `bc_staff` VALUES ('qpy001', '张三', '13145678653', '1', '1', '运输二公司', '10-20公斤');
INSERT INTO `bc_staff` VALUES ('qpy002', '李四', '13145678653', '1', '0', '运输三公司', '10-20公斤');

-- ----------------------------
-- Table structure for `bc_subarea`
-- ----------------------------
DROP TABLE IF EXISTS `bc_subarea`;
CREATE TABLE `bc_subarea` (
  `id` varchar(32) NOT NULL,
  `decidedzone_id` varchar(32) default NULL,
  `region_id` varchar(32) default NULL,
  `addresskey` varchar(100) default NULL,
  `startnum` varchar(30) default NULL,
  `endnum` varchar(30) default NULL,
  `single` char(1) default NULL,
  `position` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_area_decidedzone` (`decidedzone_id`),
  KEY `FK_area_region` (`region_id`),
  CONSTRAINT `FK_area_decidedzone` FOREIGN KEY (`decidedzone_id`) REFERENCES `bc_decidedzone` (`id`),
  CONSTRAINT `FK_area_region` FOREIGN KEY (`region_id`) REFERENCES `bc_region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bc_subarea
-- ----------------------------
INSERT INTO `bc_subarea` VALUES ('fq001', 'qpy002', 'QY006', '育新小区', '1', '10', '0', '建材城东路100号');

-- ----------------------------
-- Table structure for `qp_noticebill`
-- ----------------------------
DROP TABLE IF EXISTS `qp_noticebill`;
CREATE TABLE `qp_noticebill` (
  `id` varchar(32) NOT NULL,
  `staff_id` varchar(32) default NULL,
  `customer_id` varchar(32) default NULL,
  `customer_name` varchar(20) default NULL,
  `delegater` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `pickaddress` varchar(200) default NULL,
  `arrivecity` varchar(20) default NULL,
  `product` varchar(20) default NULL,
  `pickdate` date default NULL,
  `num` int(11) default NULL,
  `weight` double default NULL,
  `volume` varchar(20) default NULL,
  `remark` varchar(255) default NULL,
  `ordertype` varchar(20) default NULL,
  `user_id` varchar(32) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_Reference_2` (`user_id`),
  KEY `FK_Reference_3` (`staff_id`),
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`staff_id`) REFERENCES `bc_staff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qp_noticebill
-- ----------------------------
INSERT INTO `qp_noticebill` VALUES ('297efcf87655ff9c017656020c2c0001', 'qpy002', '1', '张三', '张三', '1', '北京软件园9号楼', '', '', null, null, null, '', '', '自动', '1');

-- ----------------------------
-- Table structure for `qp_workbill`
-- ----------------------------
DROP TABLE IF EXISTS `qp_workbill`;
CREATE TABLE `qp_workbill` (
  `id` varchar(32) NOT NULL,
  `noticebill_id` varchar(32) default NULL,
  `type` varchar(20) default NULL,
  `pickstate` varchar(20) default NULL,
  `buildtime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `attachbilltimes` int(11) default NULL,
  `remark` varchar(255) default NULL,
  `staff_id` varchar(32) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_Reference_4` (`staff_id`),
  KEY `FK_workbill_noticebill_fk` (`noticebill_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`staff_id`) REFERENCES `bc_staff` (`id`),
  CONSTRAINT `FK_workbill_noticebill_fk` FOREIGN KEY (`noticebill_id`) REFERENCES `qp_noticebill` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qp_workbill
-- ----------------------------
INSERT INTO `qp_workbill` VALUES ('297efcf87655ff9c017656020c360002', '297efcf87655ff9c017656020c2c0001', '新', '未取件', '2020-12-12 16:12:23', '0', '', 'qpy002');

-- ----------------------------
-- Table structure for `qp_workordermanage`
-- ----------------------------
DROP TABLE IF EXISTS `qp_workordermanage`;
CREATE TABLE `qp_workordermanage` (
  `id` varchar(32) NOT NULL,
  `arrivecity` varchar(20) default NULL,
  `product` varchar(20) default NULL,
  `num` int(11) default NULL,
  `weight` double default NULL,
  `floadreqr` varchar(255) default NULL,
  `prodtimelimit` varchar(40) default NULL,
  `prodtype` varchar(40) default NULL,
  `sendername` varchar(20) default NULL,
  `senderphone` varchar(20) default NULL,
  `senderaddr` varchar(200) default NULL,
  `receivername` varchar(20) default NULL,
  `receiverphone` varchar(20) default NULL,
  `receiveraddr` varchar(200) default NULL,
  `feeitemnum` int(11) default NULL,
  `actlweit` double default NULL,
  `vol` varchar(20) default NULL,
  `managerCheck` varchar(1) default NULL,
  `updatetime` date default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qp_workordermanage
-- ----------------------------
INSERT INTO `qp_workordermanage` VALUES ('11', '1', '1', '1', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `role_function`
-- ----------------------------
DROP TABLE IF EXISTS `role_function`;
CREATE TABLE `role_function` (
  `role_id` varchar(32) NOT NULL,
  `function_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`role_id`,`function_id`),
  KEY `FK_Reference_20` (`function_id`),
  CONSTRAINT `FK_Reference_20` FOREIGN KEY (`function_id`) REFERENCES `auth_function` (`id`),
  CONSTRAINT `FK_Reference_30` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_function
-- ----------------------------
INSERT INTO `role_function` VALUES ('297efcf84eaa48e0014eaa54bba30000', '12');
INSERT INTO `role_function` VALUES ('297efcf8765719e30176571a5c1b0000', '12');
INSERT INTO `role_function` VALUES ('297efcf84eaa48e0014eaa54bba30000', '121');
INSERT INTO `role_function` VALUES ('297efcf8765719e30176571a5c1b0000', '121');
INSERT INTO `role_function` VALUES ('297efcf84eaa48e0014eaa54bba30000', '122');
INSERT INTO `role_function` VALUES ('297efcf8765719e30176571a5c1b0000', '122');
INSERT INTO `role_function` VALUES ('297efcf84eaa48e0014eaa54bba30000', '124');
INSERT INTO `role_function` VALUES ('297efcf8765719e30176571a5c1b0000', '124');
INSERT INTO `role_function` VALUES ('297efcf84eaa48e0014eaa54bba30000', '13');
INSERT INTO `role_function` VALUES ('297efcf84eaa48e0014eaa54bba30000', '131');
INSERT INTO `role_function` VALUES ('297efcf84eaa48e0014eaa54bba30000', '132');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(32) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `salary` double default NULL,
  `birthday` date default NULL,
  `gender` varchar(10) default NULL,
  `station` varchar(40) default NULL,
  `telephone` varchar(11) default NULL,
  `remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', null, null, null, null, null, null);
INSERT INTO `t_user` VALUES ('297efcf84eaa668d014eaa6747e80000', 'zhangsan', '123', '1', '2015-07-01', '女', '总公司', '111', null);
INSERT INTO `t_user` VALUES ('297efcf84eaa68cd014eaa694fe60000', 'lisi', '81dc9bdb52d04dc20036dbd8313ed055', '111', '2015-07-01', '', '', '11', null);

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `FK_Reference_50` (`role_id`),
  CONSTRAINT `FK_Reference_40` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK_Reference_50` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('297efcf84eaa668d014eaa6747e80000', '297efcf84eaa48e0014eaa54bba30000');
INSERT INTO `user_role` VALUES ('297efcf84eaa68cd014eaa694fe60000', '297efcf84eaa48e0014eaa54bba30000');
INSERT INTO `user_role` VALUES ('297efcf84eaa668d014eaa6747e80000', '297efcf8765719e30176571a5c1b0000');
INSERT INTO `user_role` VALUES ('297efcf84eaa68cd014eaa694fe60000', '297efcf8765719e30176571a5c1b0000');
