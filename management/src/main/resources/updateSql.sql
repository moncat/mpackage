
-- 修改数据表以扩展数据2017年10月9日

ALTER TABLE `t_br_product`
ADD COLUMN `price`  decimal(10,2) NULL COMMENT '价格' AFTER `product_alias`,
ADD COLUMN `sales`  int(10) NULL COMMENT '销量' AFTER `price`,
ADD COLUMN `tmall_url`  varchar(255) NULL COMMENT '天猫的url地址' AFTER `sales`,
ADD COLUMN `jd_url`  varchar(255) NULL COMMENT '京东的url地址' AFTER `tmall_url`,
ADD COLUMN `more_data1`  varchar(255) NULL AFTER `source`,
ADD COLUMN `more_data2`  varchar(255) NULL AFTER `more_data1`;

ALTER TABLE `t_br_product`
CHANGE COLUMN `price` `jd_price`  decimal(10,2) NULL DEFAULT NULL COMMENT '京东价格' AFTER `product_alias`,
MODIFY COLUMN `sales`  int(10) NULL DEFAULT NULL COMMENT '从天猫获得销量，京东获取不到销量' AFTER `jd_price`,
ADD COLUMN `tmall_price`  decimal(10,2) NULL COMMENT '天猫价格' AFTER `jd_price`;


ALTER TABLE `t_br_product_image`
ADD COLUMN `tmall_url`  varchar(255) NULL COMMENT '天猫的图片url' AFTER `bevol_url`
ADD COLUMN `jd_url`  varchar(255) NULL COMMENT '京东的图片url' AFTER `tmall_url`,
ADD COLUMN `source`  tinyint(4) NULL  COMMENT '数据来源 1药监局 2美丽修行 3京东 4天猫' AFTER `image_type`,
MODIFY COLUMN `image_type`  tinyint(4) NULL DEFAULT NULL COMMENT '图片类型 1平面图 2立体图 3商标证，授权书 4其他未确定类型' AFTER `file_type`;


CREATE TABLE `t_br_product_spec` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `pid` bigint(20) DEFAULT NULL COMMENT '产品id',
  `spec_key_id` bigint(20) DEFAULT NULL COMMENT '规格键id',
  `spec_key_name` varchar(255) DEFAULT NULL COMMENT '规格键名称',
  `spec_value_id` bigint(20) DEFAULT NULL COMMENT '规格值id',
  `spec_value_name` varchar(255) DEFAULT NULL COMMENT '规格值名称',  
  `remark` varchar(2000) DEFAULT NULL COMMENT '备注',
  `source` tinyint(4) DEFAULT NULL COMMENT '来源  1 天猫 ； 2京东',
  `more_data1` varchar(255) DEFAULT NULL,
  `more_data2` varchar(255) DEFAULT NULL,
  `item_order` int(10) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `is_active` tinyint(4) DEFAULT NULL COMMENT '是否有效  1有  0无',
  `del_flg` tinyint(4) DEFAULT NULL COMMENT '是否删除  1已删除  0未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品规格表';


CREATE TABLE `t_br_spec_key` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `key_name` varchar(255) DEFAULT NULL COMMENT '规格键名称',
  `remark` varchar(2000) DEFAULT NULL COMMENT '备注',
  `source` tinyint(4) DEFAULT NULL COMMENT '来源  1 天猫 ； 2京东',
  `more_data1` varchar(255) DEFAULT NULL,
  `more_data2` varchar(255) DEFAULT NULL,
  `item_order` int(10) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `is_active` tinyint(4) DEFAULT NULL COMMENT '是否有效  1有  0无',
  `del_flg` tinyint(4) DEFAULT NULL COMMENT '是否删除  1已删除  0未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规格键表';


INSERT INTO `t_br_spec_key` VALUES (2389971818168320, '规格', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2389971818184704, '净含量', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2389971818184705, '适合肤质', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2389971818184706, '功效', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2389971818184707, '类别', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2389971818184708, '分类', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086592, '保质期', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086593, '产品包装', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086594, '香味', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086595, '香调', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086596, '妆效', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086597, '颜色', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086598, '商品毛重', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086599, '防晒指数', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086600, '性别', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086601, '质地', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086602, '类型', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086603, '起泡程度', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086604, '上市时间', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086605, '是否防水', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);
INSERT INTO `t_br_spec_key` VALUES (2393835711086606, '是否为特殊用途化妆品', NULL, 3, NULL, NULL, NULL, '2017-10-10 17:10:26', NULL, NULL, NULL, 1, 0);


CREATE TABLE `t_br_spec_value` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `key_id` bigint(20) DEFAULT NULL COMMENT 'key的Id',
  `value_name` varchar(255) DEFAULT NULL COMMENT '规格值名称',
  `remark` varchar(2000) DEFAULT NULL COMMENT '备注',
  `source` tinyint(4) DEFAULT NULL COMMENT '来源  1 天猫 ； 2京东',
  `more_data1` varchar(255) DEFAULT NULL,
  `more_data2` varchar(255) DEFAULT NULL,
  `item_order` int(10) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `is_active` tinyint(4) DEFAULT NULL COMMENT '是否有效  1有  0无',
  `del_flg` tinyint(4) DEFAULT NULL COMMENT '是否删除  1已删除  0未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规格值表';


CREATE TABLE `t_br_product_comment_statistics` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `pid` bigint(20) DEFAULT NULL COMMENT '产品id',
  `tmall_num_all` varchar(10) DEFAULT NULL COMMENT '天猫全部评价个数',
  `tmall_num_img` varchar(10) DEFAULT NULL COMMENT '天猫带图评价个数',
  `tmall_num_more` varchar(10) DEFAULT NULL COMMENT '天猫追评个数',
  `jd_num_all` varchar(10) DEFAULT NULL COMMENT '京东全部评价个数',
  `jd_num_img` varchar(10) DEFAULT NULL COMMENT '京东带图评价个数',
  `jd_num_more` varchar(10) DEFAULT NULL COMMENT '京东追评个数',
  `jd_num_good` varchar(10) DEFAULT NULL COMMENT '京东好评个数',
  `jd_num_middle` varchar(10) DEFAULT NULL COMMENT '京东中评个数',
  `jd_num_bad` varchar(10) DEFAULT NULL COMMENT '京东查评个数',
  `remark` varchar(2000) DEFAULT NULL COMMENT '备注',
  `more_data1` varchar(255) DEFAULT NULL,
  `more_data2` varchar(255) DEFAULT NULL,
  `item_order` int(10) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `is_active` tinyint(4) DEFAULT NULL COMMENT '是否有效  1有  0无',
  `del_flg` tinyint(4) DEFAULT NULL COMMENT '是否删除  1已删除  0未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论统计表';

;


update t_br_product set tmall_url ="0" ,jd_url = "0";

update t_br_product_image set source =12


-- 2017年10月26日
UPDATE `t_br_product` set jd_url ='0'  where jd_url is null;
UPDATE `t_br_product` set  tmall_url ='0' where tmall_url is null;


-- 2017年11月27日
新增用户表
用户验证表







