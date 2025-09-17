package com.wanmi.sbc.goods.flashpromotionactivity.service;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashPromotionActivityQueryRequest;
import com.wanmi.sbc.goods.flashpromotionactivity.model.root.FlashPromotionActivity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.wanmi.sbc.common.util.DateUtil.FMT_TIME_1;

/**
 * <p>抢购商品表动态查询条件构建器</p>
 *
 * @author xufeng
 * @date 2022-02-14 14:54:31
 */
public class FlashPromotionActivityWhereCriteriaBuilder {
    public static Specification<FlashPromotionActivity> build(FlashPromotionActivityQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>(10);

            // 模糊查询
            if (StringUtils.isNotEmpty(queryRequest.getActivityName())) {
                predicates.add(cbuild.like(root.get("activityName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getActivityName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            if (StringUtils.isNotEmpty(queryRequest.getStartTimeStr())) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("startTime"), DateUtil.parse(queryRequest.getStartTimeStr(), FMT_TIME_1)));
            }

            if (StringUtils.isNotEmpty(queryRequest.getEndTimeStr())) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"), DateUtil.parse(queryRequest.getEndTimeStr(), FMT_TIME_1)));
            }

            // 商家ID
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 批量查询 - 店铺ID
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIds())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIds()));
            }

            // 删除标志，0:未删除 1:已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            if (queryRequest.getQueryDataType() != null) {
                //限时购逻辑
                LocalDateTime nowTime = LocalDateTime.now();
                switch (queryRequest.getQueryDataType()) {
                    case 0:
                        //未开始
                        predicates.add(cbuild.greaterThan(root.get("startTime"), nowTime));
                        break;
                    case 1:
                        //正在进行
                        predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"), nowTime));
                        predicates.add(cbuild.greaterThan(root.get("endTime"), nowTime));
                        break;
                    case 2:
                        //已结束
                        predicates.add(cbuild.lessThanOrEqualTo(root.get("endTime"), nowTime));
                        break;
                    case 3:
                        //未开始与正在进行
                        predicates.add(cbuild.greaterThan(root.get("endTime"), nowTime));
                        break;
                    default:
                        break;
                }
                // 已结束的不看启动暂停状态
                if (Constants.TWO != queryRequest.getQueryDataType()) {
                    if (Constants.FOUR == queryRequest.getQueryDataType()) {
                        //暂停
                        predicates.add(cbuild.equal(root.get("status"), Constants.ONE));
                        predicates.add(cbuild.greaterThan(root.get("endTime"), nowTime));
                    }else {
                        predicates.add(cbuild.equal(root.get("status"), Constants.ZERO));
                    }
                }
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
