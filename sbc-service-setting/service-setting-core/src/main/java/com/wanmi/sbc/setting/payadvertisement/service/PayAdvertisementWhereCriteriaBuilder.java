package com.wanmi.sbc.setting.payadvertisement.service;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementQueryRequest;
import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisement;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.apache.commons.lang3.StringUtils;
import com.wanmi.sbc.common.util.XssUtils;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>支付广告页配置动态查询条件构建器</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
public class PayAdvertisementWhereCriteriaBuilder {
    public static Specification<PayAdvertisement> build(PayAdvertisementQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {

            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-支付广告idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 支付广告id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 广告名称
            if (StringUtils.isNotEmpty(queryRequest.getAdvertisementName())) {
                predicates.add(cbuild.like(root.get("advertisementName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAdvertisementName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 大于或等于 搜索条件:投放开始时间开始
            if (queryRequest.getStartTime() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("startTime"),
                        queryRequest.getStartTime()));
            }

            // 小于或等于 搜索条件:投放结束时间截止
            if (queryRequest.getEndTime() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"),
                        queryRequest.getEndTime()));
            }

            // 模糊查询 - 1:全部店铺  2:部分店铺
            if (Objects.nonNull(queryRequest.getStoreType())) {
                predicates.add(cbuild.equal(root.get("storeType"), queryRequest.getStoreType()));
            }

            // 订单金额
            if (queryRequest.getOrderPrice() != null) {
                predicates.add(cbuild.equal(root.get("orderPrice"), queryRequest.getOrderPrice()));
            }

            // 模糊查询 - 广告图片
            if (StringUtils.isNotEmpty(queryRequest.getAdvertisementImg())) {
                predicates.add(cbuild.like(root.get("advertisementImg"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAdvertisementImg()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 目标客户 1:全平台客户 -1:指定客户 other:部分客户
            if (StringUtils.isNotEmpty(queryRequest.getJoinLevel())) {
                predicates.add(cbuild.like(root.get("joinLevel"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getJoinLevel()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 删除标记  0：正常，1：删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 是否暂停（1：暂停，0：正常）
            if (queryRequest.getIsPause() != null) {
                predicates.add(cbuild.equal(root.get("isPause"), queryRequest.getIsPause()));
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

            // 活动状态
            if (Objects.nonNull(queryRequest.getQueryTab())){
                switch (queryRequest.getQueryTab()) {
                    case Constants.ONE://进行中
                        predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"), LocalDateTime.now()));
                        predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                        predicates.add(cbuild.equal(root.get("isPause"), 0));
                        break;
                    case Constants.TWO://暂停中
                        predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"), LocalDateTime.now()));
                        predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                        predicates.add(cbuild.equal(root.get("isPause"), 1));
                        break;
                    case Constants.THREE://未开始
                        predicates.add(cbuild.greaterThan(root.get("startTime"), LocalDateTime.now()));
                        break;
                    case Constants.FOUR://已结束
                        predicates.add(cbuild.lessThan(root.get("endTime"), LocalDateTime.now()));
                        break;
                    case Constants.FIVE: // 进行中&未开始
                        predicates.add(cbuild.greaterThanOrEqualTo(root.get("endTime"), LocalDateTime.now()));
                        predicates.add(cbuild.equal(root.get("isPause"), 0));
                        break;
                    default:
                        break;
                }
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
