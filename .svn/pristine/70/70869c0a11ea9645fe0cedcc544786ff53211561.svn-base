package com.wanmi.sbc.vas.commodityscoringalgorithm.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.vas.api.request.commodityscoringalgorithm.CommodityScoringAlgorithmQueryRequest;
import com.wanmi.sbc.vas.commodityscoringalgorithm.model.root.CommodityScoringAlgorithm;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>商品评分算法动态查询条件构建器</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
public class CommodityScoringAlgorithmWhereCriteriaBuilder {
    public static Specification<CommodityScoringAlgorithm> build(CommodityScoringAlgorithmQueryRequest queryRequest) {
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

            // 模糊查询 - 列名
            if (StringUtils.isNotEmpty(queryRequest.getKeyType())) {
                predicates.add(cbuild.like(root.get("key"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getKeyType()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 权值
            if (queryRequest.getWeight() != null) {
                predicates.add(cbuild.equal(root.get("weight"), queryRequest.getWeight()));
            }

            // 标签ID
            if (queryRequest.getTagId() != null) {
                predicates.add(cbuild.equal(root.get("tagId"), queryRequest.getTagId()));
            }

            // 0: 否 1：是
            if (queryRequest.getIsSelected() != null) {
                predicates.add(cbuild.equal(root.get("isSelected"), queryRequest.getIsSelected()));
            }

            // 0:否 1:是
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

            // 大于或等于 搜索条件:更新时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:更新时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            // 模糊查询 - 更新人
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
