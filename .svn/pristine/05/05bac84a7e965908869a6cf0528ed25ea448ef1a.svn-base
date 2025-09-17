package com.wanmi.sbc.marketing.giftcard.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardScopeType;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCard;

import jakarta.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @description  礼品卡查询构造器
 * @author  wur
 * @date: 2022/12/12 9:44
 **/
@Service
public class GiftCardWhereCriteriaBuilder {

    public static Specification<GiftCard> build(GiftCardPageRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 礼品卡类型
            if (Objects.nonNull(queryRequest.getGiftCardType())){
                predicates.add(cbuild.equal(root.get("giftCardType"), queryRequest.getGiftCardType()));
            }

            // 名字模糊查询
            if (StringUtils.isNotBlank(queryRequest.getName())) {
                predicates.add(cbuild.like(root.get("name"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getName()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            //适用商品类型
            if(Objects.nonNull(queryRequest.getScopeType())){
                if (Objects.equals(NumberUtils.INTEGER_ZERO, queryRequest.getScopeType())) {
                    predicates.add(cbuild.equal(root.get("scopeType"), GiftCardScopeType.ALL));
                } else if (Objects.equals(NumberUtils.INTEGER_ONE, queryRequest.getScopeType())) {
                    predicates.add(root.get("scopeType").in(Arrays.asList(GiftCardScopeType.STORE, GiftCardScopeType.BRAND
                            , GiftCardScopeType.CATE)));
                } else if(Objects.equals(Integer.valueOf(2), queryRequest.getScopeType())) {
                    predicates.add(cbuild.equal(root.get("scopeType"), GiftCardScopeType.GOODS));
                }
            }

            //状态
            if (Objects.nonNull(queryRequest.getStatus())) {
                if (Objects.equals(NumberUtils.INTEGER_ZERO, queryRequest.getStatus())) {
                    Predicate p1 = cbuild.notEqual(root.get("expirationType"), ExpirationType.SPECIFIC_TIME);
                    Predicate p2 = cbuild.greaterThanOrEqualTo(root.get("expirationTime"), LocalDateTime.now());
                    predicates.add(cbuild.or(p1, p2));
                } else if (Objects.equals(NumberUtils.INTEGER_ONE, queryRequest.getStatus())) {
                    predicates.add(cbuild.equal(root.get("expirationType"), ExpirationType.SPECIFIC_TIME));
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("expirationTime"),  LocalDateTime.now()));
                }
            }

            //面值区间
            if (Objects.nonNull(queryRequest.getParBegin())) {
                //大于等于
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("parValue"), queryRequest.getParBegin()));
            }
            if (Objects.nonNull(queryRequest.getParEnd())) {
                //小于等于
                predicates.add(cbuild.lessThanOrEqualTo(root.get("parValue"), queryRequest.getParEnd()));
            }

            //剩余库存区间
            if (Objects.nonNull(queryRequest.getStockBegin())) {
                //大于等于
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("stock"), queryRequest.getStockBegin()));
            }
            if (Objects.nonNull(queryRequest.getStockEnd())) {
                //小于等于
                predicates.add(cbuild.lessThanOrEqualTo(root.get("stock"), queryRequest.getStockEnd()));
            }
            if(Objects.nonNull(queryRequest.getScopeGoodsNum())){
                if(queryRequest.getScopeGoodsNum()>0){
                    //大于等于
                    predicates.add(cbuild.greaterThanOrEqualTo(root.get("scopeGoodsNum"), queryRequest.getScopeGoodsNum()));
                } else {
                    predicates.add(cbuild.equal(root.get("scopeGoodsNum"), queryRequest.getScopeGoodsNum()));
                }
            }

            // 删除标记  0：正常，1：删除
            predicates.add(cbuild.equal(root.get("delFlag"), DeleteFlag.NO));

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}