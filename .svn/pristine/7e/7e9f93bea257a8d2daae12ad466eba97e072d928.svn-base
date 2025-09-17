package com.wanmi.sbc.setting.storemessagenode.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.setting.api.request.storemessagenode.StoreMessageNodeQueryRequest;
import com.wanmi.sbc.setting.storemessagenode.model.root.StoreMessageNode;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>商家消息节点动态查询条件构建器</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
public class StoreMessageNodeWhereCriteriaBuilder {
    public static Specification<StoreMessageNode> build(StoreMessageNodeQueryRequest queryRequest) {
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

            // 模糊查询 - 所属菜单名称
            if (StringUtils.isNotEmpty(queryRequest.getMenuName())) {
                predicates.add(cbuild.like(root.get("menuName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMenuName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 节点类型名称
            if (StringUtils.isNotEmpty(queryRequest.getTypeName())) {
                predicates.add(cbuild.like(root.get("typeName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTypeName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 推送节点名称
            if (StringUtils.isNotEmpty(queryRequest.getPushName())) {
                predicates.add(cbuild.like(root.get("pushName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPushName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 功能标识，用于鉴权
            if (StringUtils.isNotEmpty(queryRequest.getFunctionName())) {
                predicates.add(cbuild.like(root.get("functionName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getFunctionName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 精确查询 - 节点标识
            if (StringUtils.isNotEmpty(queryRequest.getNodeCode())) {
                predicates.add(cbuild.equal(root.get("nodeCode"), queryRequest.getNodeCode()));
            }

            // 模糊查询 - 节点通知内容模板
            if (StringUtils.isNotEmpty(queryRequest.getNodeContext())) {
                predicates.add(cbuild.like(root.get("nodeContext"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getNodeContext()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 平台类型 0:平台 1:商家 2:供应商
            if (queryRequest.getPlatformType() != null) {
                predicates.add(cbuild.equal(root.get("platformType"), queryRequest.getPlatformType()));
            }

            // 删除标志 0:未删除 1:删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 模糊查询 - createPerson
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
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

            // 模糊查询 - updatePerson
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
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

            cquery.orderBy(cbuild.asc(root.get("sort")), cbuild.asc(root.get("id")));

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
