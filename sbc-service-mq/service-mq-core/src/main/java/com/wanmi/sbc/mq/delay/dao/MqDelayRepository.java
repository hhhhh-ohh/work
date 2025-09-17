package com.wanmi.sbc.mq.delay.dao;

import com.wanmi.sbc.mq.delay.bean.MqDelay;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhanggaolei
 * @className MqDelayRepository
 * @description TODO
 * @date 2021/9/14 7:43 下午
 **/
@Repository
public interface MqDelayRepository extends JpaRepository<MqDelay, Long> ,JpaSpecificationExecutor<MqDelay> {

    List<MqDelay> findAllByExprTimeBeforeAndStatus(LocalDateTime dateTime,int status, Pageable pageable);

    @Query("from MqDelay m where m.exprTime>?1 and m.status=0 and m.serverIp=?2 and m.id > ?3 order by m.id ASC ")
    List<MqDelay> findAllByNoSend(LocalDateTime dateTime,String serverIp,Long maxId, Pageable pageable);
}
