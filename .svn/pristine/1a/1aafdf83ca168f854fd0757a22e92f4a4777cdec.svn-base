package com.wanmi.sbc.setting.helpcenterarticlerecord.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.setting.api.request.helpcenterarticlerecord.HelpCenterArticleRecordQueryRequest;
import com.wanmi.sbc.setting.helpcenterarticlerecord.model.root.HelpCenterArticleRecord;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>帮助中心文章记录动态查询条件构建器</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
public class HelpCenterArticleRecordWhereCriteriaBuilder {
    public static Specification<HelpCenterArticleRecord> build(HelpCenterArticleRecordQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键IdList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键Id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 文章id
            if (queryRequest.getArticleId() != null) {
                predicates.add(cbuild.equal(root.get("articleId"), queryRequest.getArticleId()));
            }

            // 模糊查询 - customerId
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.like(root.get("customerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 解决状态  0：已解决，1：未解决
            if (queryRequest.getSolveType() != null) {
                predicates.add(cbuild.equal(root.get("solveType"), queryRequest.getSolveType()));
            }

            // 大于或等于 搜索条件:解决时间开始
            if (queryRequest.getSolveTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("solveTime"),
                        queryRequest.getSolveTimeBegin()));
            }
            // 小于或等于 搜索条件:解决时间截止
            if (queryRequest.getSolveTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("solveTime"),
                        queryRequest.getSolveTimeEnd()));
            }

            // 删除标记  0：正常，1：删除
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
