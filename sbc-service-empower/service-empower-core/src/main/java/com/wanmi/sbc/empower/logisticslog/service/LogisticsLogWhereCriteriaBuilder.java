package com.wanmi.sbc.empower.logisticslog.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.empower.api.request.logisticslog.LogisticsLogQueryRequest;
import com.wanmi.sbc.empower.logisticslog.model.root.LogisticsLog;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>物流记录动态查询条件构建器</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
public class LogisticsLogWhereCriteriaBuilder {
    public static Specification<LogisticsLog> build(LogisticsLogQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-主键List
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 主键
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 模糊查询 - 订单号
            if (StringUtils.isNotEmpty(queryRequest.getOrderNo())) {
                predicates.add(
                        cbuild.like(
                                root.get("orderNo"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getOrderNo()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 快递单号
            if (StringUtils.isNotEmpty(queryRequest.getLogisticNo())) {
                predicates.add(
                        cbuild.like(
                                root.get("logisticNo"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getLogisticNo()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 购买人编号
            if (StringUtils.isNotEmpty(queryRequest.getCustomerId())) {
                predicates.add(
                        cbuild.like(
                                root.get("customerId"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getCustomerId()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否结束
            if (queryRequest.getEndFlag() != null) {
                predicates.add(cbuild.equal(root.get("endFlag"), BoolFlag.fromValue(queryRequest.getEndFlag())));
            }

            // 模糊查询 -
            // 监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status=
            // abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑
            if (StringUtils.isNotEmpty(queryRequest.getStatus())) {
                predicates.add(
                        cbuild.like(
                                root.get("status"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getStatus()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
            if (StringUtils.isNotEmpty(queryRequest.getState())) {
                predicates.add(
                        cbuild.like(
                                root.get("state"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getState()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 监控状态相关消息，如:3天查询无记录，60天无变化
            if (StringUtils.isNotEmpty(queryRequest.getMessage())) {
                predicates.add(
                        cbuild.like(
                                root.get("message"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getMessage()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 快递公司编码是否出过错
            if (StringUtils.isNotEmpty(queryRequest.getAutoCheck())) {
                predicates.add(
                        cbuild.like(
                                root.get("autoCheck"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getAutoCheck()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 本地物流公司标准编码
            if (StringUtils.isNotEmpty(queryRequest.getComOld())) {
                predicates.add(
                        cbuild.like(
                                root.get("comOld"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getComOld()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 快递纠正新编码
            if (StringUtils.isNotEmpty(queryRequest.getComNew())) {
                predicates.add(
                        cbuild.like(
                                root.get("comNew"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getComNew()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 是否签收标记
            if (StringUtils.isNotEmpty(queryRequest.getIsCheck())) {
                predicates.add(
                        cbuild.like(
                                root.get("isCheck"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getIsCheck()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 手机号
            if (StringUtils.isNotEmpty(queryRequest.getPhone())) {
                predicates.add(
                        cbuild.like(
                                root.get("phone"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getPhone()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 出发地城市
            if (StringUtils.isNotEmpty(queryRequest.getFrom())) {
                predicates.add(
                        cbuild.like(
                                root.get("from"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getFrom()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 目的地城市
            if (StringUtils.isNotEmpty(queryRequest.getTo())) {
                predicates.add(
                        cbuild.like(
                                root.get("to"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getTo()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商品图片
            if (StringUtils.isNotEmpty(queryRequest.getGoodsImg())) {
                predicates.add(
                        cbuild.like(
                                root.get("goodsImg"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getGoodsImg()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 商品名称
            if (StringUtils.isNotEmpty(queryRequest.getGoodsName())) {
                predicates.add(
                        cbuild.like(
                                root.get("goodsName"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getGoodsName()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 订阅申请状态
            if (queryRequest.getSuccessFlag() != null) {
                predicates.add(
                        cbuild.equal(root.get("successFlag"), BoolFlag.fromValue(queryRequest.getSuccessFlag())));
            }

            // 大于或等于 搜索条件:签收时间开始
            if (queryRequest.getCheckTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("checkTime"), queryRequest.getCheckTimeBegin()));
            }
            // 小于或等于 搜索条件:签收时间截止
            if (queryRequest.getCheckTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("checkTime"), queryRequest.getCheckTimeEnd()));
            }

            // 模糊查询 - 本地发货单号
            if (StringUtils.isNotEmpty(queryRequest.getDeliverId())) {
                predicates.add(
                        cbuild.like(
                                root.get("deliverId"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getDeliverId()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标识：0：未删除；1：已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("createTime"), queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("createTime"), queryRequest.getCreateTimeEnd()));
            }

            // 模糊查询 - 创建人
            if (StringUtils.isNotEmpty(queryRequest.getCreatePerson())) {
                predicates.add(
                        cbuild.like(
                                root.get("createPerson"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getCreatePerson()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:更新时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(
                        cbuild.greaterThanOrEqualTo(
                                root.get("updateTime"), queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:更新时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(
                        cbuild.lessThanOrEqualTo(
                                root.get("updateTime"), queryRequest.getUpdateTimeEnd()));
            }

            // 模糊查询 - 更新人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(
                        cbuild.like(
                                root.get("updatePerson"),
                                StringUtil.SQL_LIKE_CHAR
                                        .concat(
                                                XssUtils.replaceLikeWildcard(
                                                        queryRequest.getUpdatePerson()))
                                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            if (BoolFlag.YES == queryRequest.getHasDetailsFlag()) {
                predicates.add(cbuild.equal(root.get("hasDetailsFlag"), queryRequest.getHasDetailsFlag()));
            }
            // 会员展示，当未确认收货且已签到的物流信息只能显示昨天的物流信天的物流信息
            if (BoolFlag.YES.equals(queryRequest.getCustomerShowLimit())) {
                Predicate p1 = cbuild.and(cbuild.equal(root.get("isCheck"), "1"), cbuild.greaterThan(root.get("checkTime"), LocalDate.now().minusDays(1).atTime(LocalTime.MIN)));
                Predicate p2 = cbuild.equal(root.get("isCheck"), "0");
                Predicate p3 = cbuild.or(p1,p2);
                Predicate p4 = cbuild.equal(root.get("endFlag"), BoolFlag.NO);
                Predicate p5 = cbuild.and(p3,p4);
                predicates.add(p5);
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
