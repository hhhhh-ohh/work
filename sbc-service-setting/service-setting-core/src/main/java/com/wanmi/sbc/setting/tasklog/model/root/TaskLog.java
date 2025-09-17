package com.wanmi.sbc.setting.tasklog.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.TaskBizType;
import com.wanmi.sbc.common.enums.TaskResult;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 定时任务执行日志实体
 * @author malianfeng
 * @date 2021/9/8 17:11
 */
@Data
@Entity
@Table(name = "task_log")
public class TaskLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 业务类型，0精准发券 1消息发送 2商品调价
     */
    @Column(name = "task_biz_type")
    @Enumerated
    private TaskBizType taskBizType;

    /**
     * 业务ID
     */
    @Column(name = "biz_id")
    private String bizId;

    /**
     * 店铺ID
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 任务执行结果，0执行失败 1执行成功
     */
    @Column(name = "task_result")
    @Enumerated
    private TaskResult taskResult;

    /**
     * 任务备注信息
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 异常堆栈信息
     */
    @Column(name = "stack_message")
    private String stackMessage;

    /**
     * 创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

}

