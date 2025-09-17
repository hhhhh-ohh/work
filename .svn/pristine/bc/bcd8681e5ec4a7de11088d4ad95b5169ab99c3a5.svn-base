package com.wanmi.sbc.customer.store.service;

import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.api.request.store.StoreQueryRequest;
import com.wanmi.sbc.customer.store.model.root.Store;
import com.wanmi.sbc.customer.util.XssUtils;

import jakarta.persistence.criteria.Predicate;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>店铺分页查询条件动态条件构建</p>
 * Created by of628-wenzhi on 2018-09-11-下午5:05.
 */
public class StoreWhereCriteriaBuilder {
    public static Specification<Store> build(StoreQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            //批量查询店铺ID
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIds())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIds()));
            }

            //公司id
            if (queryRequest.getCompanyInfoId() != null) {
                predicates.add(cbuild.equal(root.get("companyInfoId"), queryRequest.getCompanyInfoId()));
            }

            //公司id - 批量
            if (CollectionUtils.isNotEmpty(queryRequest.getCompanyInfoIdList())) {
                predicates.add(root.get("companyInfoId").in(queryRequest.getCompanyInfoIdList()));
            }

            //大于或等于签约开始时间
            if (queryRequest.getGteContractStartDate() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("contractStartDate"), queryRequest
                        .getGteContractStartDate()));
            }

            //小于或等于签约开始时间
            if (queryRequest.getLteContractEndDate() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("contractEndDate"), queryRequest
                        .getLteContractEndDate()));
            }

            if (StringUtils.isNotBlank(queryRequest.getKeywords())) {
                String[] t_keywords = StringUtils.split(queryRequest.getKeywords());
                for (String keyword : t_keywords) {
                    String pattern = StringUtil.SQL_LIKE_CHAR.concat(XssUtils
                            .replaceLikeWildcard(keyword.trim())).concat(StringUtil.SQL_LIKE_CHAR);
                    predicates.add(
                            cbuild.or(
                                    cbuild.like(root.get("storeName"), pattern),
                                    cbuild.like(root.get("supplierName"), pattern),
                                    cbuild.like(root.get("storePinyinName"), pattern),
                                    cbuild.like(root.get("supplierPinyinName"), pattern)
                            )
                    );
                }
            } else if (StringUtils.isNotBlank(queryRequest.getStoreName())) {
                predicates.add(cbuild.like(root.get("storeName"), StringUtil.SQL_LIKE_CHAR.concat(XssUtils
                        .replaceLikeWildcard(queryRequest.getStoreName().trim())).concat(StringUtil.SQL_LIKE_CHAR)));
            }

            if (StringUtils.isNotBlank(queryRequest.getSupplierName())) {
                predicates.add(cbuild.like(root.get("supplierName"), StringUtil.SQL_LIKE_CHAR.concat(XssUtils
                        .replaceLikeWildcard(queryRequest.getSupplierName().trim())).concat(StringUtil.SQL_LIKE_CHAR)));
            }

            //批量区县ID
            if (CollectionUtils.isNotEmpty(queryRequest.getAreaIds())) {
                predicates.add(root.get("areaId").in(queryRequest.getAreaIds()));
            }

            //批量城市ID
            if (CollectionUtils.isNotEmpty(queryRequest.getCityIds())) {
                predicates.add(root.get("cityId").in(queryRequest.getCityIds()));
            }

            //批量省份ID
            if (CollectionUtils.isNotEmpty(queryRequest.getProvinceIds())) {
                predicates.add(root.get("provinceId").in(queryRequest.getProvinceIds()));
            }

            //批量全部区域ID
            if (CollectionUtils.isNotEmpty(queryRequest.getAllAreaIds())) {
                predicates.add(cbuild.or(root.get("areaId").in(queryRequest.getAllAreaIds()), root.get("cityId").in
                        (queryRequest.getAllAreaIds()), root.get("provinceId").in(queryRequest.getAllAreaIds())));
            }

            //店铺状态
            if (queryRequest.getStoreState() != null) {
                predicates.add(cbuild.equal(root.get("storeState"), queryRequest.getStoreState()));
            }

            //店铺类型
            if (queryRequest.getStoreType() != null) {
                predicates.add(cbuild.equal(root.get("storeType"), queryRequest.getStoreType()));
            }

            if(CollectionUtils.isNotEmpty(queryRequest.getStoreTypeList())) {
                List<StoreType> storeTypes = new ArrayList<>();
                queryRequest.getStoreTypeList().forEach(a-> storeTypes.add(StoreType.fromValue(a)));
                predicates.add(root.get("storeType").in(storeTypes));
            }

            //屏蔽商家类型
            if (Objects.nonNull(queryRequest.getNotShowStoreType())) {
                predicates.add(cbuild.notEqual(root.get("storeType"), queryRequest.getNotShowStoreType()));
            }

            //审核状态
            if (queryRequest.getAuditState() != null) {
                predicates.add(cbuild.equal(root.get("auditState"), queryRequest.getAuditState()));
            }

            //自营标记
            if (queryRequest.getCompanyType() != null) {
                predicates.add(cbuild.equal(root.get("companyType"), queryRequest.getCompanyType()));
            }

            //删除标记
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
