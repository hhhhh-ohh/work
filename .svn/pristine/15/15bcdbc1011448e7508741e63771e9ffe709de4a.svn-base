package com.wanmi.sbc.mq.delay.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhanggaolei
 * @className MqDelay
 * @description TODO
 * @date 2021/9/14 7:32 下午
 **/
@Data
@Entity
@Table(name = "mq_delay")
public class MqDelay implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ttl")
    private Long ttl;

    @Column(name = "topic")
    private String topic;

    @Column(name = "data")
    private String data;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "expr_time")
    private LocalDateTime exprTime;

    @Column(name = "server_ip")
    private String serverIp;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 消息状态，0-未发送，1-已发送
     */
    @Column(name = "status")
    private Integer status=0;

    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "send_time")
    private LocalDateTime sendTime;

    /**
     * 日志链路id
     */
    @Column(name = "trace_id")
    private String traceId;
}
