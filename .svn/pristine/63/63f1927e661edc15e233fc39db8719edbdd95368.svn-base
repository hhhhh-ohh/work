package com.wanmi.sbc.empower.minimsgcustomerrecord.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordQueryRequest;
import com.wanmi.sbc.empower.minimsgcustomerrecord.model.root.MiniMsgCustomerRecord;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>客户订阅消息信息表动态查询条件构建器</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
public class MiniMsgCustomerRecordWhereCriteriaBuilder {
    public static Specification<MiniMsgCustomerRecord> build(MiniMsgCusRecordQueryRequest queryRequest) {
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

            // 推送活动id
            if (queryRequest.getMessageActivityId() != null) {
                predicates.add(cbuild.equal(root.get("messageActivityId"), queryRequest.getMessageActivityId()));
            }

            // 模版主键ID
            if (queryRequest.getTemplateSettingId() != null) {
                predicates.add(cbuild.equal(root.get("templateSettingId"), queryRequest.getTemplateSettingId()));
            }

            // 模糊查询 - 第三方用户id
            if (StringUtils.isNotEmpty(queryRequest.getOpenId())) {
                predicates.add(cbuild.like(root.get("openId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getOpenId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 会员Id
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(cbuild.like(root.get("customerId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCustomerId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 推送结果状态码
            if (StringUtils.isNotEmpty(queryRequest.getErrCode())) {
                predicates.add(cbuild.like(root.get("errCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getErrCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 推送结果描述
            if (StringUtils.isNotEmpty(queryRequest.getErrMsg())) {
                predicates.add(cbuild.like(root.get("errMsg"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getErrMsg()))
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

            // 是否推送标志 0：否，1：是
            if (queryRequest.getSendFlag() != null) {
                predicates.add(cbuild.equal(root.get("sendFlag"), queryRequest.getSendFlag()));
            }

            // 触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时
            if (queryRequest.getTriggerNodeId() != null) {
                predicates.add(cbuild.equal(root.get("triggerNodeId"), queryRequest.getTriggerNodeId()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
