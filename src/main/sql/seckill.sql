-- 执行秒杀存储过程
DELIMITER $$ --转换分隔符
CREATE PROCEDURE `seckill`.`execute_seckill`
(in v_seckill_id bigint, in v_phone bigint,
in v_kill_time TIMESTAMP, out r_result int)
  BEGIN
    DECLARE insert_count int DEFAULT 0;
    DECLARE update_count int DEFAULT 0;
    START TRANSACTION;
    INSERT IGNORE INTO success_killed(seckill_id, user_phone, state)
    VALUES (v_seckill_id, v_phone, 0);
    SELECT ROW_COUNT() INTO insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK;
      SET r_result = -1;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK;
      SET r_result = -2;
    ELSE
      UPDATE
        seckill
      SET
        number=number-1
      where seckill_id=v_seckill_id
      and start_time <= v_kill_time
      and end_time >= v_kill_time
      and number > 0;
      SELECT ROW_COUNT() INTO update_count;
      IF(update_count = 0) THEN
        ROLLBACK;
        SET r_result = 0;
      ELSEIF(update_count < 0) THEN
        ROLLBACK;
        SET r_result = -2;
      ELSE
        COMMIT;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$$ --存储过程定义结束

DELIMITER ;

SET @r_result = -3;
--执行存储过程
call execute_seckill(1001, '12345678912', now(), @r_result);
--返回结果
SELECT @r_result;

--减少网络延迟,进一步优化行级锁持有时间
--简单逻辑可以借助存储过程实现优化

