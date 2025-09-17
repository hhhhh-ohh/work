package com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatcateaudit.WechatCateAuditQueryRequest;
import com.wanmi.sbc.goods.wechatvideocate.wechatcateaudit.model.root.WechatCateAudit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>微信类目审核状态动态查询条件构建器</p>
 * @author 
 * @date 2022-04-09 17:02:02
 */
public class WechatCateAuditWhereCriteriaBuilder {
    public static Specification<WechatCateAudit> build(WechatCateAuditQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            // id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 微信返回的审核id
            if (StringUtils.isNotEmpty(queryRequest.getAuditId())) {
                predicates.add(cbuild.like(root.get("auditId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAuditId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 微信类目id
            if (queryRequest.getWechatCateId() != null) {
                predicates.add(cbuild.equal(root.get("wechatCateId"), queryRequest.getWechatCateId()));
            }


            // 审核状态，0：待审核，1：审核通过，2：审核不通过
            if (queryRequest.getAuditStatus() != null) {
                predicates.add(cbuild.equal(root.get("auditStatus"), queryRequest.getAuditStatus()));
            }

            // 模糊查询 - 审核不通过原因
            if (StringUtils.isNotEmpty(queryRequest.getRejectReason())) {
                predicates.add(cbuild.like(root.get("rejectReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getRejectReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:createTime开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:createTime截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeEnd()));
            }

            // 模糊查询 - createPerson
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(cbuild.like(root.get("createPerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCreatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:updateTime开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:updateTime截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
