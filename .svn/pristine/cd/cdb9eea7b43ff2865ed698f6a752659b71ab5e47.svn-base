package com.wanmi.sbc.message.storenoticesend.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.message.api.request.storenoticesend.StoreNoticeSendQueryRequest;
import com.wanmi.sbc.message.storenoticesend.model.root.StoreNoticeSend;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>商家公告动态查询条件构建器</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
public class StoreNoticeSendWhereCriteriaBuilder {
    public static Specification<StoreNoticeSend> build(StoreNoticeSendQueryRequest queryRequest) {
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

            // 模糊查询 - 公告标题
            if (StringUtils.isNotEmpty(queryRequest.getTitle())) {
                predicates.add(cbuild.like(root.get("title"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTitle()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 公告内容
            if (StringUtils.isNotEmpty(queryRequest.getContent())) {
                predicates.add(cbuild.like(root.get("content"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getContent()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 发送状态 0：未发送 1：发送中 2：已发送 3：发送失败 4：已撤回
            if (queryRequest.getSendStatus() != null) {
                predicates.add(cbuild.equal(root.get("sendStatus"), queryRequest.getSendStatus()));
            }

            // 接收范围 0：全部 1：商家 2：供应商
            if (queryRequest.getReceiveScope() != null) {
                predicates.add(cbuild.equal(root.get("receiveScope"), queryRequest.getReceiveScope()));
            }

            // 商家范围 0：全部 1：自定义商家
            if (queryRequest.getSupplierScope() != null) {
                predicates.add(cbuild.equal(root.get("supplierScope"), queryRequest.getSupplierScope()));
            }

            // 供应商范围 0：全部 1：自定义供应商
            if (queryRequest.getProviderScope() != null) {
                predicates.add(cbuild.equal(root.get("providerScope"), queryRequest.getProviderScope()));
            }

            // 推送时间类型 0：立即、1：定时
            if (queryRequest.getSendTimeType() != null) {
                predicates.add(cbuild.equal(root.get("sendTimeType"), queryRequest.getSendTimeType()));
            }

            // 大于或等于 搜索条件:发送时间开始
            if (queryRequest.getSendTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("sendTime"),
                        queryRequest.getSendTimeBegin()));
            }
            // 小于或等于 搜索条件:发送时间截止
            if (queryRequest.getSendTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("sendTime"),
                        queryRequest.getSendTimeEnd()));
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

            // 模糊查询 - createPerson
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

            // 模糊查询 - updatePerson
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识 0：未删除 1：删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[0]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
