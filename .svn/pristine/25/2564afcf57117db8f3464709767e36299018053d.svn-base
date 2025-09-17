package com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingQueryRequest;
import com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.model.root.GoodsCorrelationModelSetting;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>动态查询条件构建器</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
public class GoodsCorrelationModelSettingWhereCriteriaBuilder {
    public static Specification<GoodsCorrelationModelSetting> build(GoodsCorrelationModelSettingQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年
            if (queryRequest.getStatisticalRange() != null) {
                predicates.add(cbuild.equal(root.get("statisticalRange"), queryRequest.getStatisticalRange()));
            }

            // 预估订单数据量
            if (queryRequest.getTradeNum() != null) {
                predicates.add(cbuild.equal(root.get("tradeNum"), queryRequest.getTradeNum()));
            }

            // 支持度
            if (queryRequest.getSupport() != null) {
                predicates.add(cbuild.equal(root.get("support"), queryRequest.getSupport()));
            }

            // 置信度
            if (queryRequest.getConfidence() != null) {
                predicates.add(cbuild.equal(root.get("confidence"), queryRequest.getConfidence()));
            }

            // 提升度
            if (queryRequest.getLift() != null) {
                predicates.add(cbuild.equal(root.get("lift"), queryRequest.getLift()));
            }

            // 选中状态 0：未选中，1：选中
            if (queryRequest.getCheckStatus() != null) {
                predicates.add(cbuild.equal(root.get("checkStatus"), queryRequest.getCheckStatus()));
            }

            // 是否删除标志 0：否，1：是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeEnd()));
            }

            // 模糊查询 - 创建人
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:修改时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:修改时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            // 模糊查询 - 修改人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
