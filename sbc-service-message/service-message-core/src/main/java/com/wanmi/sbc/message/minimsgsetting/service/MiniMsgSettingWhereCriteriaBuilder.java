package com.wanmi.sbc.message.minimsgsetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.message.api.request.minimsgsetting.MiniMsgSettingQueryRequest;
import com.wanmi.sbc.message.minimsgsetting.model.root.MiniMsgSetting;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>小程序订阅消息配置表动态查询条件构建器</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
public class MiniMsgSettingWhereCriteriaBuilder {
    public static Specification<MiniMsgSetting> build(MiniMsgSettingQueryRequest queryRequest) {
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

            // 授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 5 付费会员购买成功
            if (queryRequest.getNodeId() != null) {
                predicates.add(cbuild.equal(root.get("nodeId"), queryRequest.getNodeId()));
            }

            // 模糊查询 - 授权节点名称
            if (StringUtils.isNotEmpty(queryRequest.getNodeName())) {
                predicates.add(cbuild.like(root.get("nodeName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getNodeName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 授权频次
            if (queryRequest.getNum() != null) {
                predicates.add(cbuild.equal(root.get("num"), queryRequest.getNum()));
            }

            // 模糊查询 - 选择订阅消息
            if (StringUtils.isNotEmpty(queryRequest.getMessage())) {
                predicates.add(cbuild.like(root.get("message"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getMessage()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否开启 0：否，1：是
            if (queryRequest.getStatus() != null) {
                predicates.add(cbuild.equal(root.get("status"), queryRequest.getStatus()));
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
