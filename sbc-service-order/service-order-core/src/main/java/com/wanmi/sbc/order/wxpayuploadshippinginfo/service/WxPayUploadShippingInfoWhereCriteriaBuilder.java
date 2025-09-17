package com.wanmi.sbc.order.wxpayuploadshippinginfo.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.WxPayUploadShippingInfoQueryRequest;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.model.root.WxPayUploadShippingInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>微信小程序支付发货信息动态查询条件构建器</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
public class WxPayUploadShippingInfoWhereCriteriaBuilder {
    public static Specification<WxPayUploadShippingInfo> build(WxPayUploadShippingInfoQueryRequest queryRequest) {
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

            // 支付类型，0：商品订单支付；1：授信还款
            if (queryRequest.getPayType() != null) {
                predicates.add(cbuild.equal(root.get("payType"), queryRequest.getPayType()));
            }

            // 模糊查询 - 支付订单id
            if (StringUtils.isNotEmpty(queryRequest.getBusinessId())) {
                predicates.add(cbuild.like(root.get("businessId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getBusinessId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 批量查询 - 支付订单id
            if (CollectionUtils.isNotEmpty(queryRequest.getBusinessIdList())) {
                predicates.add(root.get("businessId").in(queryRequest.getBusinessIdList()));
            }

            // 模糊查询 - 接口调用返回结果内容
            if (StringUtils.isNotEmpty(queryRequest.getResultContext())) {
                predicates.add(cbuild.like(root.get("resultContext"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getResultContext()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 结果状态，0：待处理；1：处理成功；2：处理失败
            if (queryRequest.getResultStatus() != null) {
                predicates.add(cbuild.equal(root.get("resultStatus"), queryRequest.getResultStatus()));
            }

            // 处理失败次数
            if (queryRequest.getErrorNum() != null) {
                predicates.add(cbuild.equal(root.get("errorNum"), queryRequest.getErrorNum()));
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
