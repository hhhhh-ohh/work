package com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration.RecommendPositionConfigurationQueryRequest;
import com.wanmi.sbc.vas.recommend.recommendpositionconfiguration.model.root.RecommendPositionConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>推荐坑位设置动态查询条件构建器</p>
 * @author lvzhenwei
 * @date 2020-11-17 14:14:13
 */
public class RecommendPositionConfigurationWhereCriteriaBuilder {
    public static Specification<RecommendPositionConfiguration> build(RecommendPositionConfigurationQueryRequest queryRequest) {
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

            // 模糊查询 - 坑位名称
            if (StringUtils.isNotEmpty(queryRequest.getName())) {
                predicates.add(cbuild.like(root.get("name"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
            }

            // 模糊查询 - 坑位标题
            if (StringUtils.isNotEmpty(queryRequest.getTitle())) {
                predicates.add(cbuild.like(root.get("title"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTitle()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 推荐内容
            if (StringUtils.isNotEmpty(queryRequest.getContent())) {
                predicates.add(cbuild.like(root.get("content"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getContent()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 推荐策略类型，0：热门推荐；1：基于商品相关性推荐
            if (queryRequest.getTacticsType() != null) {
                predicates.add(cbuild.equal(root.get("tacticsType"), queryRequest.getTacticsType()));
            }

            // 推荐上限
            if (queryRequest.getUpperLimit() != null) {
                predicates.add(cbuild.equal(root.get("upperLimit"), queryRequest.getUpperLimit()));
            }

            // 坑位开关，0：关闭；1：开启
            if (queryRequest.getIsOpen() != null) {
                predicates.add(cbuild.equal(root.get("isOpen"), queryRequest.getIsOpen()));
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
