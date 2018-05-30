# 创建数据库
CREATE database zhihu;
USE zhihu;

# 用户表
CREATE TABLE `zhihu_user` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
   `character_url` varchar(50) DEFAULT NULL COMMENT '特征url',
   `head_url` varchar(200) DEFAULT NULL COMMENT '头像地址',
   `simple_description` varchar(200) DEFAULT NULL COMMENT '一句话简介',
   `thanks` int(11) DEFAULT NULL COMMENT '感谢数',
   `agrees` int(11) DEFAULT NULL COMMENT '赞同数',
   `collects` int(11) DEFAULT NULL COMMENT '被赞同树',
   `followers` int(11) DEFAULT NULL COMMENT '关注者数',
   `followees` int(11) DEFAULT NULL COMMENT '关注了个数',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `character_url` (`character_url`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 回答表
CREATE TABLE `zhihu_answer` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `character_url` varchar(50) DEFAULT NULL COMMENT '特征url',
   `answer_url` varchar(200) DEFAULT NULL COMMENT '回答url',
   `question_id` int(11) DEFAULT NULL COMMENT '问题id',
   `question_title` varchar(200) DEFAULT NULL COMMENT '问题标题',
   `answer_id` int(11) DEFAULT NULL COMMENT '回答id',
   `agrees` int(11) DEFAULT NULL COMMENT '赞同数',
   `comments` int(11) DEFAULT NULL COMMENT '评论数',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `question_id` (`question_id`,`answer_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 # 文章表
CREATE TABLE `zhihu_article` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `character_url` varchar(50) DEFAULT NULL COMMENT '特征url',
   `article_id` int(11) DEFAULT NULL COMMENT '文章id',
   `article_url` varchar(200) DEFAULT NULL COMMENT '文章url',
   `article_title` varchar(200) DEFAULT NULL COMMENT '文章标题',
   `agrees` int(11) DEFAULT NULL COMMENT '赞同数',
   `comments` int(11) DEFAULT NULL COMMENT '评论数',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `article_id` (`article_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 回答文本表
CREATE TABLE `zhihu_answer_text` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `question_id` int(11) DEFAULT NULL COMMENT '问题id',
   `answer_id` int(11) DEFAULT NULL COMMENT '回答id',
   `content` text COMMENT '文本内容',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `question_id` (`question_id`,`answer_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 文章文本表
CREATE TABLE `zhihu_article_text` (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `article_id` int(11) DEFAULT NULL COMMENT '文章id',
   `content` text COMMENT '文本内容',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`),
   UNIQUE KEY `article_id` (`article_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
