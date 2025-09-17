package com.wanmi.sbc.goods.flashsalegoods.service;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsQueryRequest;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.flashsalegoods.model.root.FlashSaleGoods;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.wanmi.sbc.common.util.DateUtil.FMT_TIME_1;

/**
 * <p>抢购商品表动态查询条件构建器</p>
 *
 * @author bob
 * @date 2019-06-11 14:54:31
 */
public class FlashSaleGoodsWhereCriteriaBuilder {
    public static Specification<FlashSaleGoods> build(FlashSaleGoodsQueryRequest queryRequest) {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>(10);
            Join<FlashSaleGoods, Goods> flashSaleGoodsJoin = root.join("goods");
            Join<FlashSaleGoods, GoodsInfo> flashSaleGoodsInfoJoin = root.join("goodsInfo");
            // 批量查询-idList
            if (CollectionUtils.isNotEmpty(queryRequest.getIdList())) {
                predicates.add(root.get("id").in(queryRequest.getIdList()));
            }

            if (CollectionUtils.isNotEmpty(queryRequest.getGoodsinfoIds())) {
                predicates.add(root.get("goodsInfoId").in(queryRequest.getGoodsinfoIds()));
            }

            // id
            if (queryRequest.getId() != null) {
                predicates.add(cbuild.equal(root.get("id"), queryRequest.getId()));
            }

            // 模糊查询 - 活动日期：2019-06-11
            if (StringUtils.isNotEmpty(queryRequest.getActivityDate())) {
                predicates.add(cbuild.equal(root.get("activityDate"), queryRequest.getActivityDate()));
            }

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

            // 模糊查询 - 活动时间：13:00 activityFullTime
            if (StringUtils.isNotEmpty(queryRequest.getActivityTime())) {
                predicates.add(cbuild.equal(root.get("activityTime"), queryRequest.getActivityTime()));
            }

            // 模糊查询 - 活动时间：13:00 activityFullTime
            if (StringUtils.isNotEmpty(queryRequest.getActivityTime())) {
                predicates.add(cbuild.like(root.get("activityTime"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getActivityTime()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }
            // 活动日期+时间(场次)
            if (queryRequest.getActivityFullTime() != null) {
                predicates.add(cbuild.equal(root.get("activityFullTime"), queryRequest.getPrice()));
            }

            // 模糊查询 - skuID
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoId())) {
                predicates.add(cbuild.like(root.get("goodsInfoId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 模糊查询 - spuID
            if (StringUtils.isNotEmpty(queryRequest.getGoodsId())) {
                predicates.add(cbuild.like(root.get("goodsId"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getGoodsId()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // 抢购价
            if (queryRequest.getPrice() != null) {
                predicates.add(cbuild.equal(root.get("price"), queryRequest.getPrice()));
            }

            // 抢购库存
            if (queryRequest.getStock() != null) {
                predicates.add(cbuild.equal(root.get("stock"), queryRequest.getStock()));
            }

            // 抢购销量
            if (queryRequest.getSalesVolume() != null) {
                predicates.add(cbuild.equal(root.get("salesVolume"), queryRequest.getSalesVolume()));
            }

            // 模糊查询 - 分类ID
            if (queryRequest.getCateId() != null) {
                predicates.add(cbuild.equal(root.get("cateId"), queryRequest.getCateId()));
            }

            // 限购数量
            if (queryRequest.getMaxNum() != null) {
                predicates.add(cbuild.equal(root.get("maxNum"), queryRequest.getMaxNum()));
            }

            // 起售数量
            if (queryRequest.getMinNum() != null) {
                predicates.add(cbuild.equal(root.get("minNum"), queryRequest.getMinNum()));
            }

            // 商家ID
            if (queryRequest.getStoreId() != null) {
                predicates.add(cbuild.equal(root.get("storeId"), queryRequest.getStoreId()));
            }

            // 批量查询 - 店铺ID
            if (CollectionUtils.isNotEmpty(queryRequest.getStoreIds())) {
                predicates.add(root.get("storeId").in(queryRequest.getStoreIds()));
            }

            // 包邮标志，0：不包邮 1:包邮
            if (queryRequest.getPostage() != null) {
                predicates.add(cbuild.equal(root.get("postage"), queryRequest.getPostage()));
            }

            // 删除标志，0:未删除 1:已删除
            if (queryRequest.getDelFlag() != null) {
                predicates.add(cbuild.equal(root.get("delFlag"), queryRequest.getDelFlag()));
            }

            // 类型 1:限时购 0:秒杀
            if (queryRequest.getType() != null) {
                predicates.add(cbuild.equal(root.get("type"), queryRequest.getType()));
            }

            // 商品名称模糊查询
            if (StringUtils.isNotEmpty(queryRequest.getGoodsName())) {
                predicates.add(cbuild.like(flashSaleGoodsJoin.get("goodsName"), new StringBuffer().append("%")
                        .append(XssUtils.replaceLikeWildcard(queryRequest.getGoodsName().trim())).append("%").toString()));
            }

            // SKU编号查询
            if (StringUtils.isNotEmpty(queryRequest.getGoodsInfoNo())) {
                predicates.add(cbuild.like(flashSaleGoodsInfoJoin.get("goodsInfoNo"), new StringBuffer().append("%")
                        .append(XssUtils.replaceLikeWildcard(queryRequest.getGoodsInfoNo().trim())).append("%").toString()));
            }
            LocalDateTime nowTime = LocalDateTime.now();
            if (Objects.nonNull(queryRequest.getPromotionStatus())){
                // 查询进行中
                if (Constants.ZERO==queryRequest.getPromotionStatus()){
                    //正在进行
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("startTime"), nowTime));
                    predicates.add(cbuild.greaterThan(root.get("endTime"), nowTime));
                } else if(Constants.ONE==queryRequest.getPromotionStatus()){
                    //查询即将开始，预热时间到开始时间内
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("preStartTime"), nowTime));
                    predicates.add(cbuild.greaterThan(root.get("startTime"), nowTime));
                }else if(Constants.TWO==queryRequest.getPromotionStatus()){
                    predicates.add(cbuild.lessThanOrEqualTo(root.get("preStartTime"), nowTime));
                    predicates.add(cbuild.greaterThan(root.get("endTime"), nowTime));
                }
                // 前端列表查询非暂停状态数据
                predicates.add(cbuild.equal(root.get("status"), Constants.ZERO));
            }

            if (queryRequest.getQueryDataType() != null) {
                // 秒杀逻辑
                if (Objects.isNull(queryRequest.getType()) || Constants.ZERO==queryRequest.getType()){
                    LocalDateTime begin = LocalDateTime.now();
                    LocalDateTime end = begin.minus(Constants.FLASH_SALE_LAST_HOUR, ChronoUnit.HOURS);
                    switch (queryRequest.getQueryDataType()) {
                        case 0:
                            //未开始
                            predicates.add(cbuild.greaterThan(root.get("activityFullTime"), begin));
                            break;
                        case 1:
                            //正在进行
                            predicates.add(cbuild.lessThanOrEqualTo(root.get("activityFullTime"), begin));
                            predicates.add(cbuild.greaterThan(root.get("activityFullTime"), end));
                            break;
                        case 2:
                            //已结束
                            predicates.add(cbuild.lessThanOrEqualTo(root.get("activityFullTime"), end));
                            break;
                        case 3:
                            //未开始与正在进行
                            predicates.add(cbuild.greaterThan(root.get("activityFullTime"), end));
                            break;
                        default:
                            break;
                    }
                }else {
                    //限时购逻辑
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
            }

            // 大于或等于 搜索条件:创建时间开始
            if (queryRequest.getActivityFullTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("activityFullTime"),
                        queryRequest.getActivityFullTimeBegin()));
            }
            // 小于或等于 搜索条件:创建时间截止
            if (queryRequest.getActivityFullTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("activityFullTime"),
                        queryRequest.getActivityFullTimeEnd()));
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

            // 大于或等于 搜索条件:更新时间开始
            if (queryRequest.getUpdateTimeBegin() != null) {
                predicates.add(cbuild.greaterThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeBegin()));
            }
            // 小于或等于 搜索条件:更新时间截止
            if (queryRequest.getUpdateTimeEnd() != null) {
                predicates.add(cbuild.lessThanOrEqualTo(root.get("updateTime"),
                        queryRequest.getUpdateTimeEnd()));
            }

            // 模糊查询 - 更新人
            if (StringUtils.isNotEmpty(queryRequest.getUpdatePerson())) {
                predicates.add(cbuild.like(root.get("updatePerson"), StringUtil.SQL_LIKE_CHAR
                        .concat(XssUtils.replaceLikeWildcard(queryRequest.getUpdatePerson()))
                        .concat(StringUtil.SQL_LIKE_CHAR)));
            }

            // Modify by zhengyang
            // for task 1036271 抢购商品按是否只显示有货商品开关显示
            if (Constants.yes.equals(queryRequest.getStockFlag())) {
                predicates.add(cbuild.gt(root.get("stock"), BigDecimal.ZERO));
            }

            if (Constants.yes.equals(queryRequest.getVendibility())) {
                Predicate p1 = cbuild.equal(flashSaleGoodsInfoJoin.get("vendibility"), DefaultFlag.YES.toValue());
                Predicate p2 = cbuild.isNull(flashSaleGoodsInfoJoin.get("vendibility"));
                predicates.add(cbuild.or(p1, p2));
                Predicate p3 = cbuild.equal(flashSaleGoodsInfoJoin.get("providerStatus"), Constants.yes);
                Predicate p4 = cbuild.isNull(flashSaleGoodsInfoJoin.get("providerStatus"));
                predicates.add(cbuild.or(p3, p4));
                predicates.add(cbuild.equal(flashSaleGoodsInfoJoin.get("addedFlag"), AddedFlag.YES.toValue()));
                predicates.add(cbuild.equal(flashSaleGoodsInfoJoin.get("delFlag"), DeleteFlag.NO));
                predicates.add(cbuild.equal(flashSaleGoodsInfoJoin.get("auditStatus"), CheckStatus.CHECKED));
            }

            // 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
            if (Objects.nonNull(queryRequest.getGoodsType())) {
                predicates.add(cbuild.equal(flashSaleGoodsInfoJoin.get("goodsType"), queryRequest.getGoodsType()));
            }

            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }
}
