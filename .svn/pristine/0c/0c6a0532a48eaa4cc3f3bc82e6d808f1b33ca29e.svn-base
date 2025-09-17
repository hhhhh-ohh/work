package com.wanmi.sbc.setting.tasklog.service;

import com.wanmi.sbc.setting.api.request.TaskLogCountByParamsRequest;
import com.wanmi.sbc.setting.tasklog.model.root.TaskLog;
import com.wanmi.sbc.setting.tasklog.repository.TaskLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description 定时任务日志服务
 * @author malianfeng
 * @date 2021/9/8 18:33
 */
@Service
public class TaskLogService {

    @Autowired TaskLogRepository taskLogRepository;

    /**
     * 新增定时任务日志
     * @param taskLog 定时任务日志
     */
    public void add(TaskLog taskLog) {
        taskLogRepository.save(taskLog);
    }

    /**
     * 统计数量
     * @param request
     * @return 日志数
     */
    public Long count(TaskLogCountByParamsRequest request){
        return this.taskLogRepository.count(this.build(request));
    }

    public Specification<TaskLog> build(TaskLogCountByParamsRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 业务ID
            if (StringUtils.isNotEmpty(queryRequest.getBizId())) {
                predicates.add(cbuild.equal(root.get("bizId"), queryRequest.getBizId()));
            }

            // 店铺ID
            if (Objects.nonNull(queryRequest.getStoreId())) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 业务类型
            if (Objects.nonNull(queryRequest.getTaskBizType())) {
                predicates.add(cbuild.equal(root.get("taskBizType"), queryRequest.getTaskBizType()));
            }

            // 业务结果
            if (Objects.nonNull(queryRequest.getTaskResult())) {
                predicates.add(cbuild.equal(root.get("taskResult"), queryRequest.getTaskResult()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}

