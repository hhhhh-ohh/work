package com.wanmi.sbc.goods.wechatvideosku.service;

import com.wanmi.sbc.common.enums.EditStatus;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.goods.api.request.wechatvideo.wechatsku.WechatSkuQueryRequest;
import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.wechatvideosku.model.root.WechatSku;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>微信视频号带货商品动态查询条件构建器</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
public class WechatSkuWhereCriteriaBuilder {
    public static Specification<WechatSku> build(WechatSkuQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<WechatSku, GoodsInfo> goodsInfo = root.join("goodsInfo");
            // 批量查询-idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }
            if (CollectionUtils.isNotEmpty(queryRequest.getCompanyInfoIds())) {
                predicates.add(goodsInfo.get("companyInfoId").in(queryRequest.getCompanyInfoIds()));
            }

            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsIds())) {
                predicates.add(root.get("goodsId").in(queryRequest.getGoodsIds()));
            }
            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsInfoIds())) {
                predicates.add(root.get("goodsInfoId").in(queryRequest.getGoodsInfoIds()));
            }


            // id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 微信端sku_id
            if (StringUtils.isNotEmpty(queryRequest.getWechatSkuId())) {
                predicates.add(cbuild.like(root.get("wechatSkuId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getWechatSkuId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 微信端商品id
            if (queryRequest.getProductId() != null) {
                predicates.add(cbuild.equal(root.get("productId"), queryRequest.getProductId()));
            }

            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoId())) {
                predicates.add(cbuild.equal(root.get("goodsInfoId"),queryRequest.getGoodsInfoId()));
            }


            if (StringUtils.isNotEmpty(queryRequest.getGoodsId())) {
                predicates.add(cbuild.equal(root.get("goodsId"),queryRequest.getGoodsId()));
            }

            if (queryRequest.getEditStatus()!= null) {
                if (queryRequest.getEditStatus().equals(EditStatus.unchecked)) {
                    predicates.add(root.get("editStatus").in(queryRequest.getEditStatus(),EditStatus.checking));
                }else if(queryRequest.getEditStatus().equals(EditStatus.failure)){
                    predicates.add(cbuild.or(cbuild.equal(root.get("editStatus"), queryRequest.getEditStatus()),
                            cbuild.equal(root.get("wechatShelveStatus"), WechatShelveStatus.VIOLATION_UN_SHELVE)));
                }else {
                    predicates.add(cbuild.equal(root.get("editStatus"), queryRequest.getEditStatus()));
                }
            }

            if (queryRequest.getNotEditStatus()!= null) {
                predicates.add(cbuild.notEqual(root.get("editStatus"), queryRequest.getNotEditStatus()));
            }

            // 微信端上下架状态，0:下架1:上架
            if (queryRequest.getWechatShelveStatus()!= null) {
                predicates.add(cbuild.equal(root.get("wechatShelveStatus"), queryRequest.getWechatShelveStatus()));
            }

            if (CollectionUtils.isNotEmpty(queryRequest.getWechatShelveStatusList())) {
                predicates.add(root.get("wechatShelveStatus").in(queryRequest.getWechatShelveStatusList()));
            }

            // 大于或等于 搜索条件:createTime开始
            if (queryRequest.getCreateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeBegin()));
            }
            // 小于或等于 搜索条件:createTime截止
            if (queryRequest.getCreateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("createTime"),
                        queryRequest.getCreateTimeEnd()));
            }
            if (StringUtils.isNotBlank(queryRequest.getGoodsInfoName())) {
                predicates.add(cbuild.like(goodsInfo.get("goodsInfoName"), new StringBuffer().append("%")
                        .append(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoName().trim())).append("%").toString()));
            }
            if (queryRequest.getCompanyInfoId()!=null) {
                predicates.add(cbuild.equal(goodsInfo.get("companyInfoId"), queryRequest.getCompanyInfoId()));
            }

            // 是否删除，0，否，1是
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
