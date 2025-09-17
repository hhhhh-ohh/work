package com.wanmi.sbc.empower.deliveryrecord.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.empower.api.request.deliveryrecord.DeliveryRecordDadaQueryRequest;
import com.wanmi.sbc.empower.deliveryrecord.model.root.DeliveryRecordDada;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>达达配送记录动态查询条件构建器</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
public class DeliveryRecordDadaWhereCriteriaBuilder {
    public static Specification<DeliveryRecordDada> build(DeliveryRecordDadaQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-订单号List
            if (CollectionUtils.isNotEmpty(queryRequest.getOrderNoList())) {
                predicates.add(root.get("orderNo").in(queryRequest.getOrderNoList()));
            }

            // 订单号
            if (StringUtils.isNotEmpty(queryRequest.getOrderNo())) {
                predicates.add(cbuild.equal(root.get("orderNo"), queryRequest.getOrderNo()));
            }

            // 模糊查询 - 第三方店铺编码
            if (StringUtils.isNotEmpty(queryRequest.getShopNo())) {
                predicates.add(cbuild.like(root.get("shopNo"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getShopNo()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 城市编码
            if (StringUtils.isNotEmpty(queryRequest.getCityCode())) {
                predicates.add(cbuild.like(root.get("cityCode"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCityCode()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 订单金额;不含配送费
            if (queryRequest.getCargoPrice() != null) {
                predicates.add(cbuild.equal(root.get("cargoPrice"), queryRequest.getCargoPrice()));
            }

            // 是否需要垫付;1:是 0:否 (垫付订单金额，非运费)
            if (queryRequest.getIsPrepay() != null) {
                predicates.add(cbuild.equal(root.get("isPrepay"), queryRequest.getIsPrepay()));
            }

            // 配送距离;单位:米
            if (queryRequest.getDistance() != null) {
                predicates.add(cbuild.equal(root.get("distance"), queryRequest.getDistance()));
            }

            // 实际运费
            if (queryRequest.getFee() != null) {
                predicates.add(cbuild.equal(root.get("fee"), queryRequest.getFee()));
            }

            // 运费
            if (queryRequest.getDeliverFee() != null) {
                predicates.add(cbuild.equal(root.get("deliverFee"), queryRequest.getDeliverFee()));
            }

            // 小费
            if (queryRequest.getTipsFee() != null) {
                predicates.add(cbuild.equal(root.get("tipsFee"), queryRequest.getTipsFee()));
            }

            // 是否使用保价费;0:不使用,1:使用
            if (queryRequest.getIsUseInsurance() != null) {
                predicates.add(cbuild.equal(root.get("isUseInsurance"), queryRequest.getIsUseInsurance()));
            }

            // 保价费
            if (queryRequest.getInsuranceFee() != null) {
                predicates.add(cbuild.equal(root.get("insuranceFee"), queryRequest.getInsuranceFee()));
            }

            // 0:接受订单1:待接单2:待取货3:配送中4:已完成5:已取消7:已过期8:指派单9:返回妥投异常中10:妥投异常完成100:骑士到店1000:创建达达运单失败
            if (queryRequest.getDeliveryStatus() != null) {
                predicates.add(cbuild.equal(root.get("deliveryStatus"), queryRequest.getDeliveryStatus()));
            }

            // 删除标识;0:未删除1:已删除
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
