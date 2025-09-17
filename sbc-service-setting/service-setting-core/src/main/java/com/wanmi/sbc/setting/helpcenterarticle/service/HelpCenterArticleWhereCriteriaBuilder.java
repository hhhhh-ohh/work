package com.wanmi.sbc.setting.helpcenterarticle.service;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.setting.api.request.helpcenterarticle.HelpCenterArticleQueryRequest;
import com.wanmi.sbc.setting.helpcenterarticle.model.root.HelpCenterArticle;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>帮助中心文章信息动态查询条件构建器</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
public class HelpCenterArticleWhereCriteriaBuilder {
    public static Specification<HelpCenterArticle> build(HelpCenterArticleQueryRequest queryRequest) {
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

            // 模糊查询 - 文章标题
            if (StringUtils.isNotEmpty(queryRequest.getArticleTitle())) {
                predicates.add(cbuild.like(root.get("articleTitle"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getArticleTitle()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 文章分类id
            if (queryRequest.getArticleCateId() != null) {
                predicates.add(cbuild.equal(root.get("articleCateId"), queryRequest.getArticleCateId()));
            }

            // 模糊查询 - 文章内容
            if (StringUtils.isNotEmpty(queryRequest.getArticleContent())) {
                predicates.add(cbuild.like(root.get("articleContent"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getArticleContent()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 文章状态，0:展示，1:隐藏
            if (queryRequest.getArticleType() != null) {
                predicates.add(cbuild.equal(root.get("articleType"), DefaultFlag.fromValue(queryRequest.getArticleType())));
            }

            // 查看次数
            if (queryRequest.getViewNum() != null) {
                predicates.add(cbuild.equal(root.get("viewNum"), queryRequest.getViewNum()));
            }

            // 解决次数
            if (queryRequest.getSolveNum() != null) {
                predicates.add(cbuild.equal(root.get("solveNum"), queryRequest.getSolveNum()));
            }

            // 未解决次数
            if (queryRequest.getUnresolvedNum() != null) {
                predicates.add(cbuild.equal(root.get("unresolvedNum"), queryRequest.getUnresolvedNum()));
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
