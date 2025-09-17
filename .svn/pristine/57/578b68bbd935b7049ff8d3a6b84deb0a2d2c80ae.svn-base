package com.wanmi.sbc.order.util;

import com.wanmi.sbc.order.api.request.orderperformance.OrderPerformanceByUniqueCodesRequest;
import com.wanmi.sbc.order.orderperformance.model.root.OrderPerformanceDetail;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class QueryConditionsUtil {
    public static Specification<OrderPerformanceDetail> getWhereCriteria(OrderPerformanceByUniqueCodesRequest request) {

        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();


            // 城市和区域的OR条件
            List<Predicate> locationPredicates = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(request.getCityList())) {
                locationPredicates.add(root.get("cityId").in(request.getCityList()));
            }
            if (CollectionUtils.isNotEmpty(request.getAreaList())) {
                locationPredicates.add(root.get("areaId").in(request.getAreaList()));
            }

            // 如果有城市或区域条件，则使用OR连接
            if (!locationPredicates.isEmpty()) {
                if (locationPredicates.size() == 1) {
                    predicates.add(locationPredicates.get(0));
                } else {
                    predicates.add(cbuild.or(locationPredicates.toArray(new Predicate[0])));
                }
            }

            // 按区域查询
            if (request.getAreaId() != null) {
                predicates.add(cbuild.equal(root.get("areaId"), request.getAreaId()));
            }

            // 创建开始时间
            if (request.getStartTime() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"), request.getStartTime()));
            }

            // 创建结束时间
            if (request.getEndTime() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"), request.getEndTime()));
            }

            return cbuild.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
