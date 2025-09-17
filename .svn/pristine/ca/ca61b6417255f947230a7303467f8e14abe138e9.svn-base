package com.wanmi.sbc.setting.pickupsetting.service;

import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingQueryRequest;
import com.wanmi.sbc.setting.pickupsetting.model.root.PickupSetting;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>pickup_setting动态查询条件构建器</p>
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
public class PickupSettingWhereCriteriaBuilder {
    public static Specification<PickupSetting> build(PickupSettingQueryRequest queryRequest) {
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

            // 店铺ID
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 模糊查询 - 自提点名称
            if (StringUtils.isNotEmpty(queryRequest.getName())) {
                predicates.add(cbuild.like(root.get("name"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 自提点联系电话
            if (StringUtils.isNotEmpty(queryRequest.getPhone())) {
                predicates.add(cbuild.equal(root.get("phone"), queryRequest.getPhone()));
            }

            // 区号
            if (StringUtils.isNotEmpty(queryRequest.getAreaCode())) {
                predicates.add(cbuild.equal(root.get("areaCode"), queryRequest.getAreaCode()));
            }

            // 省份
            if (queryRequest.getProvinceId() != null) {
                predicates.add(cbuild.equal(root.get("provinceId"), queryRequest.getProvinceId()));
            }

            // 市
            if (queryRequest.getCityId() != null) {
                predicates.add(cbuild.equal(root.get("cityId"), queryRequest.getCityId()));
            }

            // 区
            if (queryRequest.getAreaId() != null) {
                predicates.add(cbuild.equal(root.get("areaId"), queryRequest.getAreaId()));
            }

            // 街道
            if (queryRequest.getStreetId() != null) {
                predicates.add(cbuild.equal(root.get("streetId"), queryRequest.getStreetId()));
            }

            // 模糊查询 - 详细街道地址
            if (StringUtils.isNotEmpty(queryRequest.getPickupAddress())) {
                predicates.add(cbuild.like(root.get("pickupAddress"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getPickupAddress()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否是默认地址
            if (queryRequest.getIsDefaultAddress() != null) {
                predicates.add(cbuild.equal(root.get("isDefaultAddress"), queryRequest.getIsDefaultAddress()));
            }

            // 模糊查询 - 自提时间说明
            if (StringUtils.isNotEmpty(queryRequest.getRemark())) {
                predicates.add(cbuild.like(root.get("remark"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getRemark()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - 自提点照片
            if (StringUtils.isNotEmpty(queryRequest.getImageUrl())) {
                predicates.add(cbuild.like(root.get("imageUrl"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getImageUrl()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 经度
            if (queryRequest.getLongitude() != null) {
                predicates.add(cbuild.equal(root.get("longitude"), queryRequest.getLongitude()));
            }

            // 纬度
            if (queryRequest.getLatitude() != null) {
                predicates.add(cbuild.equal(root.get("latitude"), queryRequest.getLatitude()));
            }

            // 审核状态,0:未审核1 审核通过2审核失败
            if (queryRequest.getAuditStatus() != null) {
                predicates.add(cbuild.equal(root.get("auditStatus"), queryRequest.getAuditStatus()));
            }

            // 模糊查询 - 驳回理由
            if (StringUtils.isNotEmpty(queryRequest.getAuditReason())) {
                predicates.add(cbuild.like(root.get("auditReason"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getAuditReason()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 是否启用 1:启用 0:停用
            if (queryRequest.getEnableStatus() != null) {
                predicates.add(cbuild.equal(root.get("enableStatus"), queryRequest.getEnableStatus()));
                predicates.add(cbuild.equal(root.get("auditStatus"), 1));
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

            // 是否删除标志 0：否，1：是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 大于或等于 搜索条件:删除时间开始
            if (queryRequest.getDeleteTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("deleteTime"),
                        queryRequest.getDeleteTimeBegin()));
            }
            // 小于或等于 搜索条件:删除时间截止
            if (queryRequest.getDeleteTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("deleteTime"),
                        queryRequest.getDeleteTimeEnd()));
            }

            // 模糊查询 - 删除人
            if (StringUtils.isNotEmpty(queryRequest.getDeletePerson())) {
                predicates.add(cbuild.like(root.get("deletePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getDeletePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            //自提类型
            if (queryRequest.getStoreType() != null){
                predicates.add(cbuild.equal(root.get("storeType"), queryRequest.getStoreType()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
