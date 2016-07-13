--数据库创建脚本
CREATE TABLE seckill(
`seckill_id` bigint not NULL auto_increment comment '商品库存id',
`name` varchar(120) not NULL comment '商品名称',
`number` int not null comment '商品数量',
`start_time` TIMESTAMP NOT NULL comment '秒杀开启时间',
`end_time` TIMESTAMP NOT NULL DEFAULT '2016-01-01 00:00:00' comment '秒杀结束时间',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT '秒杀库存表';

insert into seckill(name, number, start_time, end_time)
VALUES
('1000元秒杀iphone6',100,'2016-07-01 00:00:00', '2016-07-02 00:00:00'),
('500元秒杀ipad2',200,'2016-07-01 00:00:00', '2016-07-02 00:00:00'),
('400元秒杀小米4',300,'2016-07-01 00:00:00', '2016-07-02 00:00:00'),
('100元秒杀红米',400,'2016-07-01 00:00:00', '2016-07-02 00:00:00');

CREATE TABLE success_killed(
`seckill_id` bigint NOT NULL comment '秒杀商品id',
`user_phone` bigint NOT NULL comment '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 comment '状态显示 -1:无效 0:成功 1:已付款 2:已发货',
`create_time` TIMESTAMP NOT NULL comment '创建时间',
PRIMARY KEY (seckill_id, user_phone),/*联合主键*/
KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8 COMMENT '秒杀库存表'