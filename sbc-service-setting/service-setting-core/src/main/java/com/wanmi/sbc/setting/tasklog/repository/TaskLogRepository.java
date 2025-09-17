package com.wanmi.sbc.setting.tasklog.repository;

import com.wanmi.sbc.setting.tasklog.model.root.TaskLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @description 定时任务日志持久层
 * @author malianfeng
 * @date 2021/9/8 17:56
 */
public interface TaskLogRepository extends JpaRepository<TaskLog, Long>, JpaSpecificationExecutor<TaskLog> {

}

