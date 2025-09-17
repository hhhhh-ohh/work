-- 分四步来执行，上线之前保证以下各个步骤脚本执行成功，创建新表对之前业务没有影响，可以先执行到线上
-- 第一步，创建新表，把数据先迁移过来
DROP TABLE IF EXISTS `sbc-setting`.`store_resource_new`;
CREATE TABLE `sbc-setting`.`store_resource_new`  (
  `resource_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '素材ID',
  `resource_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '资源类型(0:图片,1:视频)',
  `cate_id` bigint(20) NOT NULL COMMENT '素材分类ID',
  `store_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺标识',
  `company_info_id` int(11) NULL DEFAULT NULL COMMENT '商家标识',
  `resource_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材KEY',
  `resource_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材名称',
  `artwork_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  `server_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'oss服务器类型，对应system_config的config_type',
  PRIMARY KEY (`resource_id`) USING BTREE,
  INDEX `idx_store_id`(`store_id`) USING BTREE,
  INDEX `idx_company_info_id`(`company_info_id`) USING BTREE,
  INDEX `idx_resource_type`(`resource_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '店铺素材资源表';

DROP TABLE IF EXISTS `sbc-setting`.`store_resource_cate_new`;
CREATE TABLE `sbc-setting`.`store_resource_cate_new`  (
  `cate_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '素材分类id',
  `store_id` bigint(20) NULL DEFAULT NULL COMMENT '店铺标识',
  `company_info_id` int(11) NULL DEFAULT NULL COMMENT '商家标识',
  `cate_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `cate_parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父分类ID',
  `cate_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类图片',
  `cate_path` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类层次路径,例1|01|001',
  `cate_grade` tinyint(4) NOT NULL COMMENT '分类层级',
  `pin_yin` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音',
  `s_pin_yin` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简拼',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  `sort` tinyint(4) NULL DEFAULT NULL COMMENT '排序',
  `is_default` tinyint(4) NULL DEFAULT NULL COMMENT '是否默认,0:否1:是',
  PRIMARY KEY (`cate_id`) USING BTREE,
  INDEX `idx_store_id`(`store_id`) USING BTREE,
  INDEX `idx_company_info_id`(`company_info_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '店铺素材资源分类表';

DROP TABLE IF EXISTS `sbc-setting`.`system_resource_new`;
CREATE TABLE `sbc-setting`.`system_resource_new`  (
  `resource_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '素材资源ID',
  `resource_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '资源类型(0:图片,1:视频)',
  `cate_id` bigint(20) NOT NULL COMMENT '素材分类ID',
  `resource_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材KEY',
  `resource_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材名称',
  `artwork_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  `server_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'oss服务器类型，对应system_config的config_type',
  PRIMARY KEY (`resource_id`) USING BTREE,
  INDEX `idx_resource_type`(`resource_type`) USING BTREE,
  INDEX `idx_cate_id`(`cate_id`) USING BTREE,
  INDEX `idx_server_type`(`server_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平台素材资源表';

DROP TABLE IF EXISTS `sbc-setting`.`system_resource_cate_new`;
CREATE TABLE `sbc-setting`.`system_resource_cate_new`  (
  `cate_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '素材资源分类id',
  `cate_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `cate_parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父分类ID',
  `cate_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类图片',
  `cate_path` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类层次路径,例1|01|001',
  `cate_grade` tinyint(4) NOT NULL COMMENT '分类层级',
  `pin_yin` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音',
  `s_pin_yin` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简拼',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  `sort` tinyint(4) NULL DEFAULT NULL COMMENT '排序',
  `is_default` tinyint(4) NULL DEFAULT NULL COMMENT '是否默认,0:否1:是',
  PRIMARY KEY (`cate_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '素材资源分类表';

-- 迁移数据
INSERT INTO `sbc-setting`.`store_resource_new` SELECT
	`store_resource`.`resource_id`,
	`store_resource`.`resource_type`,
	`store_resource`.`cate_id`,
	`store_resource`.`store_id`,
	`store_resource`.`company_info_id`,
	`store_resource`.`resource_key`,
	`store_resource`.`resource_name`,
	`store_resource`.`artwork_url`,
	`store_resource`.`create_time`,
	`store_resource`.`update_time`,
	`store_resource`.`del_flag`,
	`store_resource`.`server_type`
FROM
	`sbc-setting`.`store_resource`;

INSERT INTO `sbc-setting`.`store_resource_cate_new` SELECT
`store_resource_cate`.`cate_id`,
`store_resource_cate`.`store_id`,
`store_resource_cate`.`company_info_id`,
`store_resource_cate`.`cate_name`,
`store_resource_cate`.`cate_parent_id`,
`store_resource_cate`.`cate_img`,
`store_resource_cate`.`cate_path`,
`store_resource_cate`.`cate_grade`,
`store_resource_cate`.`pin_yin`,
`store_resource_cate`.`s_pin_yin`,
`store_resource_cate`.`create_time`,
`store_resource_cate`.`update_time`,
`store_resource_cate`.`del_flag`,
`store_resource_cate`.`sort`,
`store_resource_cate`.`is_default`
FROM
`sbc-setting`.`store_resource_cate`;

INSERT INTO `sbc-setting`.`system_resource_new` SELECT
	`system_resource`.`resource_id`,
	`system_resource`.`resource_type`,
	`system_resource`.`cate_id`,
	`system_resource`.`resource_key`,
	`system_resource`.`resource_name`,
	`system_resource`.`artwork_url`,
	`system_resource`.`create_time`,
	`system_resource`.`update_time`,
	`system_resource`.`del_flag`,
	`system_resource`.`server_type`
FROM
	`sbc-setting`.`system_resource`;

INSERT INTO `sbc-setting`.`system_resource_cate_new` SELECT
`system_resource_cate`.`cate_id`,
`system_resource_cate`.`cate_name`,
`system_resource_cate`.`cate_parent_id`,
`system_resource_cate`.`cate_img`,
`system_resource_cate`.`cate_path`,
`system_resource_cate`.`cate_grade`,
`system_resource_cate`.`pin_yin`,
`system_resource_cate`.`s_pin_yin`,
`system_resource_cate`.`create_time`,
`system_resource_cate`.`update_time`,
`system_resource_cate`.`del_flag`,
`system_resource_cate`.`sort`,
`system_resource_cate`.`is_default`
FROM
`sbc-setting`.`system_resource_cate`;


-- 第二步，使用存储过程把可能会冲突的主键及相关字段刷新处理
DELIMITER $$
# 如果存储过程存在, 则删除
DROP PROCEDURE IF EXISTS `sbc-setting`.`proc_split`$$
CREATE DEFINER=`root`@`%` PROCEDURE `sbc-setting`.`proc_split`(  
		z_cate_id bigint,
    z_cate_path varchar(255),
		identity_system_cate_id bigint,
    cate_path_end varchar(1000),  
    delim char(1)  
)  
    NOT DETERMINISTIC  
    CONTAINS SQL  
    SQL SECURITY DEFINER  
    COMMENT ''  
begin  
    declare strlen int;  
    declare last_index int;  
    declare cur_index int;  
    declare cur_char VARCHAR(200);  
    declare len int;  
		declare oldValue BIGINT;
		declare newValue BIGINT;
		declare grade int;
		declare replaceValue varchar(1000);
		declare posi int;
		declare dealSubValue varchar(1000);
    set cur_index=1;  
    set last_index=0;  
    set strlen=length(cate_path_end);  
		set replaceValue = z_cate_path;
		set grade=0; 
		set posi=0;
		set dealSubValue=replaceValue;
    WHILE(cur_index<=strlen) DO     
    begin  
        if substring(cate_path_end from cur_index for 1)=delim or cur_index=strlen then  
            set len=cur_index-last_index-1;  
						set grade = grade+1;
            if cur_index=strlen then  
               set len=len+1;  
            end if;  
						set oldValue = substring(cate_path_end from (last_index+1) for len);
						if(oldValue>0) then  
               set newValue=oldValue+identity_system_cate_id;  		 
							#三层替换时，防止存在第二层替换后的值，存在和第三层一样的情况
							if(grade>1) then
							set posi = posi + LOCATE("|",dealSubValue);
								set replaceValue = CONCAT(SUBSTRING(replaceValue,1,posi),REPLACE(SUBSTRING(replaceValue,posi+1,LENGTH(replaceValue)),oldValue,newValue));	
							set dealSubValue = SUBSTRING(replaceValue,posi+1,LENGTH(replaceValue));		
							else
								set replaceValue = REPLACE(replaceValue,oldValue,newValue);
								set dealSubValue = replaceValue;
							end if;
            end if;  
            set last_index=cur_index;  
        end if;  
        set cur_index=cur_index+1;  
    END;  
    end while;  
		UPDATE `sbc-setting`.`store_resource_cate_new` set cate_path = replaceValue where cate_id = z_cate_id;
end   $$
DELIMITER ;

DELIMITER $$
# 如果存储过程存在, 则删除
DROP PROCEDURE IF EXISTS `sbc-setting`.`store_resource_transfer_system_resource`$$
# 创建存储过程
CREATE DEFINER=`root`@`%` PROCEDURE `sbc-setting`.`store_resource_transfer_system_resource`()
BEGIN
		DECLARE identity_system_resource_id bigint default 0;
		DECLARE identity_system_cate_id bigint default 0;
		DECLARE identity_store_resource_id bigint default 0;
		DECLARE identity_store_cate_id bigint default 0;
		-- 声明一些需要用到的变量
		DECLARE done int DEFAULT FALSE;
		DECLARE z_cate_id bigint;
		DECLARE z_cate_path varchar(255);
		DECLARE splitchar char(1);
		DECLARE cate_path_end varchar(255);
		-- 声明游标
		DECLARE cate_path_list CURSOR FOR
		SELECT `cate_id`,`cate_path` FROM `sbc-setting`.`store_resource_cate_new`  where  cate_grade > 1;
		-- 声明 是否没有记录
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		#获取system_resource_new表中主键最大值
    SELECT MAX(resource_id) INTO identity_system_resource_id FROM `sbc-setting`.`system_resource_new`;
		#获取system_resource_cate_new表中主键最大值
		SELECT MAX(cate_id) INTO identity_system_cate_id FROM `sbc-setting`.`system_resource_cate_new`;
		#获取store_resource_new表中主键最大值
    SELECT MAX(resource_id) INTO identity_store_resource_id FROM `sbc-setting`.`store_resource_new`;
		#获取store_resource_cate_new表中主键最大值
		SELECT MAX(cate_id) INTO identity_store_cate_id FROM `sbc-setting`.`store_resource_cate_new`;
		#最大主键值加上100
		set @identity_system_resource_id = identity_system_resource_id + identity_store_resource_id + 100;
		set @identity_system_cate_id = identity_system_cate_id + identity_store_cate_id + 100;
		#store_resource_new表刷新resource_id和cate_id字段
		UPDATE `sbc-setting`.`store_resource_new` set resource_id = resource_id + @identity_system_resource_id, cate_id = cate_id + @identity_system_cate_id;
		#store_resource_cate_new表需要根据层级刷新cate_parent_id和cate_id字段，若是多层级，需要刷新cate_parent_id
		UPDATE `sbc-setting`.`store_resource_cate_new` set cate_parent_id = cate_parent_id + @identity_system_cate_id, cate_id = cate_id + @identity_system_cate_id where cate_grade > 1;
		#store_resource_cate_new表单层级刷新cate_id字段
		UPDATE `sbc-setting`.`store_resource_cate_new` set cate_id = cate_id + @identity_system_cate_id where cate_grade = 1;
		#cate_path???
		-- 打开游标
		OPEN cate_path_list;
		-- 开始循环
		read_loop:
			LOOP
				-- 注意声明变量类型和接受数据的顺序一致
				FETCH cate_path_list INTO z_cate_id,z_cate_path;
				-- 如果没有结果集就退出循环
				IF done THEN
					LEAVE read_loop;
				END IF;
				-- 去除最后一个字符|
				set splitchar = SUBSTRING(z_cate_path,LENGTH(z_cate_path),LENGTH(z_cate_path));
				set cate_path_end = IF(STRCMP('|',splitchar),z_cate_path,SUBSTRING(z_cate_path,1,LENGTH(z_cate_path)-1));
				call `sbc-setting`.proc_split(z_cate_id,z_cate_path,@identity_system_cate_id,cate_path_end,'|');
			-- 结束循环
			END LOOP;
		-- 关闭游标
			CLOSE cate_path_list;
			
    END $$
DELIMITER ;

call `sbc-setting`.store_resource_transfer_system_resource();



-- 第三步，system表加入store表缺省字段，并创建索引；把store表数据迁移到system表中
-- 平台素材资源表
ALTER TABLE `sbc-setting`.`system_resource_new`
ADD COLUMN `company_info_id` int(11) DEFAULT NULL COMMENT '商家标识' AFTER `cate_id`,
ADD COLUMN `store_id` bigint(20) DEFAULT -1 COMMENT '店铺标识' AFTER `cate_id`,
ADD INDEX `idx_company_info_id` (`company_info_id`),
ADD INDEX `idx_store_id`(`store_id`);


-- 素材资源分类表
ALTER TABLE `sbc-setting`.`system_resource_cate_new`
ADD COLUMN `company_info_id` int(11) DEFAULT NULL COMMENT '商家标识' AFTER `cate_id`,
ADD COLUMN `store_id` bigint(20) DEFAULT -1 COMMENT '店铺标识' AFTER `cate_id`,
ADD INDEX `idx_company_info_id` (`company_info_id`),
ADD INDEX `idx_store_id`(`store_id`);


-- 迁移原有店铺图片素材数据
INSERT INTO `sbc-setting`.`system_resource_new` SELECT
	`store_resource_new`.`resource_id`,
	`store_resource_new`.`resource_type`,
	`store_resource_new`.`cate_id`,
	`store_resource_new`.`store_id`,
	`store_resource_new`.`company_info_id`,
	`store_resource_new`.`resource_key`,
	`store_resource_new`.`resource_name`,
	`store_resource_new`.`artwork_url`,
	`store_resource_new`.`create_time`,
	`store_resource_new`.`update_time`,
	`store_resource_new`.`del_flag`,
	`store_resource_new`.`server_type`
FROM
	`sbc-setting`.`store_resource_new`;


-- 迁移原有店铺图片素材分类数据
INSERT INTO `sbc-setting`.`system_resource_cate_new` SELECT
`store_resource_cate_new`.`cate_id`,
`store_resource_cate_new`.`store_id`,
`store_resource_cate_new`.`company_info_id`,
`store_resource_cate_new`.`cate_name`,
`store_resource_cate_new`.`cate_parent_id`,
`store_resource_cate_new`.`cate_img`,
`store_resource_cate_new`.`cate_path`,
`store_resource_cate_new`.`cate_grade`,
`store_resource_cate_new`.`pin_yin`,
`store_resource_cate_new`.`s_pin_yin`,
`store_resource_cate_new`.`create_time`,
`store_resource_cate_new`.`update_time`,
`store_resource_cate_new`.`del_flag`,
`store_resource_cate_new`.`sort`,
`store_resource_cate_new`.`is_default`
FROM
`sbc-setting`.`store_resource_cate_new`;


-- 第四步，删除无用的表及存储过程
DROP TABLE IF EXISTS `sbc-setting`.`store_resource_new`;
DROP TABLE IF EXISTS `sbc-setting`.`store_resource_cate_new`;
DROP PROCEDURE IF EXISTS `sbc-setting`.`proc_split`;
DROP PROCEDURE IF EXISTS `sbc-setting`.`store_resource_transfer_system_resource`;