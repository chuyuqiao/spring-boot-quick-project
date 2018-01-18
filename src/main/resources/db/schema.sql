CREATE TABLE user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(255) DEFAULT NULL COMMENT '账号',
  password varchar(255) DEFAULT NULL COMMENT '密码',
  nickname varchar(255) DEFAULT '' COMMENT '昵称',
  roles varchar(255) DEFAULT NULL COMMENT '身份',
  PRIMARY KEY (id)
);
