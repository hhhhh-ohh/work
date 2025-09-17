package com.wanmi.sbc.vas.recommend.caterelatedrecommend.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.vas.api.request.recommend.caterelatedrecommend.CateRelatedRecommendQueryRequest;
import com.wanmi.sbc.vas.recommend.caterelatedrecommend.model.root.CateRelatedRecommend;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>分类相关性推荐动态查询条件构建器</p>
 * @author lvzhenwei
 * @date 2020-11-26 10:55:53
 */
public class CateRelatedRecommendWhereCriteriaBuilder {
    public static Specification<CateRelatedRecommend> build(CateRelatedRecommendQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 分类id
            if (StringUtils.isNotEmpty(queryRequest.getCateId())) {
                predicates.add(cbuild.like(root.get("cateId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCateId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 关联分类id
            if (StringUtils.isNotEmpty(queryRequest.getRelatedCateId())) {
                predicates.add(cbuild.like(root.get("relatedCateId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getRelatedCateId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 批量查询-关联分类ids
            if (CollectionUtils.isNotEmpty(queryRequest.getRelatedCateIds())) {
                predicates.add(root.get("relatedCateId").in(queryRequest.getRelatedCateIds()));
            }

            // 提升度
            if (queryRequest.getLift() != null) {
                predicates.add(cbuild.equal(root.get("lift"), queryRequest.getLift()));
            }

            // 权重
            if (queryRequest.getWeight() != null) {
                predicates.add(cbuild.equal(root.get("weight"), queryRequest.getWeight()));
            }

            // 类型，0：关联分析，1：手动关联
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
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
