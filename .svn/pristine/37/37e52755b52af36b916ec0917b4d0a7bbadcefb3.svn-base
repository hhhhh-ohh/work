package com.wanmi.sbc.marketing.electroniccoupon.service.criteria;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponQueryRequest;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCoupon;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * <p>电子卡券表动态查询条件构建器</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
public class ElectronicCouponWhereCriteriaBuilder {
    public static Specification<ElectronicCoupon> build(ElectronicCouponQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 批量查询-电子卡券idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            // 电子卡券id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 电子卡券名称
            if (StringUtils.isNotEmpty(queryRequest.getCouponName())) {
                predicates.add(cbuild.like(root.get("couponName"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getCouponName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否删除 0 否  1 是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 店铺id
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 绑定标识
            if (queryRequest.getBindingFlag() != null) {
                Predicate bindingPre = cbuild.equal(root.get("bindingFlag"), queryRequest.getBindingFlag());

               if (CollectionUtils.isNotEmpty(queryRequest.getIncludeIds())) {
                   CriteriaBuilder.In in = cbuild.in(root.get("id"));
                   for (Long id : queryRequest.getIncludeIds()) {
                       in.value(id);
                   }
                   predicates.add(cbuild.or(bindingPre, in));
               } else {
                   predicates.add(bindingPre);
               }
            }
            // 创建时间
            if(queryRequest.getCreateTime() != null){
                predicates.add(cbuild.equal(root.get("createTime"),queryRequest.getCreateTime()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
