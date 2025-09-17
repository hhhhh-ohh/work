package com.wanmi.sbc.marketing.electroniccoupon.service.criteria;

import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicImportRecordQueryRequest;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicImportRecord;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>卡密导入记录表动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-01-26 17:36:55
 */
public class ElectronicImportRecordWhereCriteriaBuilder {
    public static Specification<ElectronicImportRecord> build(ElectronicImportRecordQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-批次idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 批次id
            if (StringUtils.isNotEmpty(queryRequest.getId())) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 卡券id
            if (queryRequest.getCouponId() != null) {
                predicates.add(cbuild.equal(root.get("couponId"), queryRequest.getCouponId()));
            }

            // 大于或等于 搜索条件:销售结束时间开始
            if (queryRequest.getSaleEndTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("saleEndTime"),
                        queryRequest.getSaleEndTimeBegin()));
            }
            // 小于或等于 搜索条件:销售结束时间截止
            if (queryRequest.getSaleEndTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("saleEndTime"),
                        queryRequest.getSaleEndTimeEnd()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
