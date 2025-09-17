package com.wanmi.sbc.elastic.coupon.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoInitRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoPageRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponInfoDTO;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponInfoVO;
import com.wanmi.sbc.elastic.coupon.mapper.EsCouponInfoMapper;
import com.wanmi.sbc.elastic.coupon.model.root.EsCouponInfo;
import com.wanmi.sbc.elastic.coupon.repository.EsCouponInfoRepository;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateListCouponDetailRequest;
import com.wanmi.sbc.goods.bean.dto.CouponInfoForScopeNamesDTO;
import com.wanmi.sbc.goods.bean.vo.CouponInfoForScopeNamesVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCateRelaQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCateRelaListByCouponIdsRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoListByPageRequest;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.CouponCateRelaVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;


/**
 * 优惠券Service
 */
@Slf4j
@Service
public class EsCouponInfoService {

    @Autowired
    private EsCouponInfoRepository esCouponInfoRepository;

    @Autowired
    private EsCouponInfoMapper esCouponInfoMapper;

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private CouponCateRelaQueryProvider couponCateRelaQueryProvider;

    @Autowired
    private CouponInfoQueryProvider couponInfoQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esCouponInfo.json")
    private Resource mapping;

    /**
     * 初始化ES数据
     */
    public void init(EsCouponInfoInitRequest esCouponInfoInitRequest) {
        boolean initCouponInfo = true;
        int pageNum = esCouponInfoInitRequest.getPageNum();
        Integer pageSize = esCouponInfoInitRequest.getPageSize();
        CouponInfoListByPageRequest request = KsBeanUtil.convert(esCouponInfoInitRequest, CouponInfoListByPageRequest.class);
        try {
            while (initCouponInfo) {
                request.putSort("createTime", SortType.DESC.toValue());
                request.setPageNum(pageNum);
                request.setPageSize(pageSize);
                if (CollectionUtils.isNotEmpty(esCouponInfoInitRequest.getIdList())) {
                    request.setCouponIds(esCouponInfoInitRequest.getIdList());
                    request.setPageNum(0);
                    request.setPageSize(esCouponInfoInitRequest.getIdList().size());
                    //idList不为空时退出循环
                    initCouponInfo = false;
                }
                List<CouponInfoVO> couponInfos = couponInfoQueryProvider.listByPage(request).getContext().getCouponInfos();
                if (CollectionUtils.isEmpty(couponInfos)) {
                    initCouponInfo = false;
                    log.info("==========ES初始化优惠券结束，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsCouponInfo> esCouponInfoDTOList = esCouponInfoMapper.couponInfoToEsCouponInfo(couponInfos);
                    this.saveAll(esCouponInfoDTOList);
                    log.info("==========ES初始化优惠券成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化优惠券异常，异常pageNum:{}==============", pageNum);
            log.error("==========ES初始化优惠券异常，异常{}", e);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040001, new Object[]{pageNum});
        }

    }

    /**
     * 保存优惠券ES数据
     *
     * @param esCouponInfoList
     * @return
     */
    public Iterable<EsCouponInfo> saveAll(List<EsCouponInfo> esCouponInfoList) {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.DOC_COUPON_INFO_TYPE, mapping);
        return esCouponInfoRepository.saveAll(esCouponInfoList);
    }

    /**
     * 保存优惠券ES数据
     *
     * @param esCouponInfoDTO
     * @return
     */
    public EsCouponInfo save(EsCouponInfoDTO esCouponInfoDTO) {
        EsCouponInfo esCouponInfo = esCouponInfoMapper.couponInfoToEsCouponInfo(esCouponInfoDTO);
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.DOC_COUPON_INFO_TYPE, mapping);
        return esCouponInfoRepository.save(esCouponInfo);
    }

    /**
     * 根据优惠券ID删除对应ES数据
     *
     * @param couponId
     */
    public void deleteById(String couponId) {
        esCouponInfoRepository.deleteById(couponId);
    }

    /**
     * 分页查询ES优惠券信息
     *
     * @param request
     * @return
     */
    public Page<EsCouponInfo> page(EsCouponInfoPageRequest request) {
        return esBaseService.commonPage(this.getSearchQuery(request), EsCouponInfo.class, EsConstants.DOC_COUPON_INFO_TYPE);
    }

    /**
     * 包装分类名称和限制范围
     *
     * @param esCouponInfoList
     */
    public void wrapperScopeNamesAndCateNames(List<EsCouponInfoVO> esCouponInfoList) {
        if (CollectionUtils.isEmpty(esCouponInfoList)) {
            return;
        }
        Map<String, List<String>> cateIdsMap = esCouponInfoList.stream().collect(Collectors.toMap(EsCouponInfoVO::getCouponId, EsCouponInfoVO::getCateIds));
        List<CouponCateRelaVO> cateRelaVOList = couponCateRelaQueryProvider.listByCateIdsMap(new CouponCateRelaListByCouponIdsRequest(cateIdsMap)).getContext().getCateRelaVOList();
        Map<String, CouponCateRelaVO> cateNamesMap = cateRelaVOList.stream().collect(Collectors.toMap(CouponCateRelaVO::getCouponId, Function.identity()));
        Map<String, List<String>> scopeIdsMap = esCouponInfoList.stream().collect(Collectors.toMap(EsCouponInfoVO::getCouponId, EsCouponInfoVO::getScopeIds));

        List<CouponInfoForScopeNamesDTO> dtoList = esCouponInfoList.stream().map(couponInfo -> {
            CouponInfoForScopeNamesDTO couponInfoForScopeNamesDTO = new CouponInfoForScopeNamesDTO();
            couponInfoForScopeNamesDTO.setCouponId(couponInfo.getCouponId());
            couponInfoForScopeNamesDTO.setCouponType(com.wanmi.sbc.goods.bean.enums.CouponType.fromValue(couponInfo.getCouponType().toValue()));
            couponInfoForScopeNamesDTO.setScopeType(com.wanmi.sbc.goods.bean.enums.ScopeType.fromValue(couponInfo.getScopeType().toValue()));
            couponInfoForScopeNamesDTO.setPlatformFlag(couponInfo.getPlatformFlag());
            couponInfoForScopeNamesDTO.setStoreId(couponInfo.getStoreId());
            return couponInfoForScopeNamesDTO;
        }).collect(Collectors.toList());

        List<CouponInfoForScopeNamesVO> couponInfoForScopeNamesVOS = goodsCateQueryProvider.couponDetail(new GoodsCateListCouponDetailRequest(dtoList, null, scopeIdsMap)).getContext().getVoList();
        Map<String, List<String>> scopeNamesMap = couponInfoForScopeNamesVOS.stream().collect(Collectors.toMap(CouponInfoForScopeNamesVO::getCouponId, CouponInfoForScopeNamesVO::getScopeNames));

        esCouponInfoList.stream().forEach(esCouponInfo -> {
            CouponCateRelaVO couponCateRelaVO = cateNamesMap.get(esCouponInfo.getCouponId());
            esCouponInfo.setCateNames(Objects.nonNull(couponCateRelaVO.getCouponCateName()) ? couponCateRelaVO.getCouponCateName() : Lists.newArrayList());
            esCouponInfo.setIsFree(Objects.nonNull(couponCateRelaVO) ? couponCateRelaVO.getIsFree() : null);
            esCouponInfo.setScopeNames(scopeNamesMap.get(esCouponInfo.getCouponId()));
            CouponStatus couponStatus = null;
            if (Objects.equals(RangeDayType.DAYS, esCouponInfo.getRangeDayType())) {
                couponStatus = CouponStatus.DAYS;
            } else {
                if (esCouponInfo.getStartTime() != null && esCouponInfo.getEndTime() != null) {
                    if (LocalDateTime.now().isBefore(esCouponInfo.getStartTime())) {
                        couponStatus = CouponStatus.NOT_START;
                    } else if (LocalDateTime.now().isAfter(esCouponInfo.getEndTime())) {
                        couponStatus = CouponStatus.ENDED;
                    } else {
                        couponStatus = CouponStatus.STARTED;
                    }
                }
            }
            esCouponInfo.setCouponStatus(couponStatus);

        });
    }

    /**
     * 优惠券列表查询条件封装
     *
     * @return
     */
    private BoolQuery getBoolQueryBuilder(EsCouponInfoPageRequest request) {
        BoolQuery.Builder bool = QueryBuilders.bool();
        Long storeId = request.getStoreId();
        List<CouponType> couponTypes = request.getCouponTypes();
        DefaultFlag isMarketingChose = request.getIsMarketingChose();
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime();
        DefaultFlag platform = request.getPlatformFlag();
        ScopeType scopeType = request.getScopeType();
        CouponStatus couponStatus = request.getCouponStatus();
        String likeCouponName = request.getLikeCouponName();
        DeleteFlag deleteFlag = request.getDelFlag();
        List<Long> storeIds = request.getStoreIds();
        List<String> couponIds = request.getCouponIds();
        CouponMarketingType couponMarketingType = request.getCouponMarketingType();
        List<CouponMarketingType> couponMarketingTypes = request.getCouponMarketingTypes();

        //券ID列表
        if (CollectionUtils.isNotEmpty(couponIds)) {
            bool.must(terms(g -> g.field("couponId").terms(v -> v.value(couponIds.stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //店铺ID
        if (Objects.nonNull(storeId)) {
//            bool.must(termQuery("storeId", storeId));
            bool.must(term(g -> g.field("storeId").value(storeId)));
        }

        if (CollectionUtils.isNotEmpty(storeIds) && Objects.equals(DefaultFlag.YES,request.getSupplierFlag())){
//            bool.must(termsQuery("storeId",storeIds));
            bool.must(terms(g -> g.field("storeId").terms(v -> v.value(storeIds.stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

//        if (CouponType.STOREFRONT_VOUCHER == couponType && !Constants.BOSS_DEFAULT_STORE_ID.equals(storeId)) {
//            BoolQueryBuilder boolSub = QueryBuilders.boolQuery();
//            boolSub.should(termQuery("storeIds", storeId));
//            boolSub.should(termQuery("participateType", ParticipateType.ALL.toValue()));
//            bool.must(boolSub);
//        }

        if (Objects.equals(DefaultFlag.YES, isMarketingChose)) {
//            BoolQueryBuilder orBq = QueryBuilders.boolQuery();
            BoolQuery.Builder orBq = QueryBuilders.bool();

//            BoolQueryBuilder b1 = QueryBuilders.boolQuery();
            BoolQuery.Builder b1 = QueryBuilders.bool();
//            b1.mustNot(QueryBuilders.rangeQuery("endTime").lt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
            b1.mustNot(QueryBuilders.range(g -> g.field("endTime").lt(JsonData.of(DateUtil.format(LocalDateTime.now()
                    , DateUtil.FMT_TIME_4)))));

//            BoolQueryBuilder b2 = QueryBuilders.boolQuery();
            BoolQuery.Builder b2 = QueryBuilders.bool();
//            b2.must(QueryBuilders.termQuery("rangeDayType", RangeDayType.DAYS.toValue()));
            b2.must(QueryBuilders.term(g -> g.field("rangeDayType").value(RangeDayType.DAYS.toValue())));

            orBq.should(g -> g.bool(b1.build())).should(g -> g.bool(b2.build()));
            bool.must(a -> a.bool(orBq.build()));
        }

        if (Objects.nonNull(startTime)) {

//            BoolQueryBuilder orBq = QueryBuilders.boolQuery();
            BoolQuery.Builder orBq = QueryBuilders.bool();

//            BoolQueryBuilder b1 = QueryBuilders.boolQuery();
            BoolQuery.Builder b1 = QueryBuilders.bool();
//            b1.must(QueryBuilders.rangeQuery("startTime").gte(DateUtil.format(startTime, DateUtil.FMT_TIME_4)));
            b1.must(QueryBuilders.range(g -> g.field("startTime").gte(JsonData.of(DateUtil.format(startTime
                    , DateUtil.FMT_TIME_4)))));

//            BoolQueryBuilder b2 = QueryBuilders.boolQuery();
            BoolQuery.Builder b2 = QueryBuilders.bool();
//            b2.must(QueryBuilders.termQuery("rangeDayType", RangeDayType.RANGE_DAY.toValue()));
            b2.must(QueryBuilders.term(g -> g.field("rangeDayType").value(RangeDayType.RANGE_DAY.toValue())));

//            orBq.must(b1);
//            orBq.must(b2);
            orBq.must(a -> a.bool(b1.build())).must(a -> a.bool(b2.build()));
            bool.must(a -> a.bool(orBq.build()));
        }

        if (Objects.nonNull(endTime)) {
//            BoolQueryBuilder orBq = QueryBuilders.boolQuery();
            BoolQuery.Builder orBq = QueryBuilders.bool();

//            BoolQueryBuilder b1 = QueryBuilders.boolQuery();
            BoolQuery.Builder b1 = QueryBuilders.bool();
//            b1.must(QueryBuilders.rangeQuery("endTime").lte(DateUtil.format(endTime, DateUtil.FMT_TIME_4)));
            b1.must(QueryBuilders.range(g -> g.field("endTime").lte(JsonData.of(DateUtil.format(endTime
                    , DateUtil.FMT_TIME_4)))));

//            BoolQueryBuilder b2 = QueryBuilders.boolQuery();
            BoolQuery.Builder b2 = QueryBuilders.bool();
//            b2.must(QueryBuilders.termQuery("rangeDayType", RangeDayType.RANGE_DAY.toValue()));
            b2.must(QueryBuilders.term(g -> g.field("rangeDayType").value(RangeDayType.RANGE_DAY.toValue())));

            /*orBq.must(b1);
            orBq.must(b2);*/
            orBq.must(a -> a.bool(b1.build())).must(a -> a.bool(b2.build()));
            bool.must(a -> a.bool(orBq.build()));
        }


        //是否平台优惠券 1平台 0店铺
        if (Objects.nonNull(platform)) {
//            bool.must(termQuery("platformFlag", platform.toValue()));
            bool.must(term(g -> g.field("platformFlag").value(platform.toValue())));
        }


        //优惠券类型 0通用券 1店铺券 3门店券
        if (CollectionUtils.isNotEmpty(couponTypes)) {
            List<Integer> couponTypeValues = couponTypes.stream().map(CouponType::toValue).collect(Collectors.toList());
//            bool.must(termsQuery("couponType", couponTypeValues));
            bool.must(terms(g -> g.field("couponType").terms(v -> v.value(couponTypeValues.stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (Objects.nonNull(couponMarketingType)) {
//            bool.must(termQuery("couponMarketingType", couponMarketingType.toValue()));
            bool.must(term(g -> g.field("couponMarketingType").value(couponMarketingType.toValue())));
        }

        // 营销优惠券类型 0满减券 1满折券 2运费券
        if (CollectionUtils.isNotEmpty(couponMarketingTypes)) {
            List<Integer> couponMarketingTypeValues = couponMarketingTypes.stream().map(CouponMarketingType::toValue).collect(Collectors.toList());
//            bool.must(termsQuery("couponMarketingType", couponMarketingTypeValues));
            bool.must(terms(g -> g.field("couponMarketingType").terms(v -> v.value(couponMarketingTypeValues.stream().map(FieldValue::of).collect(Collectors.toList())))));
        }


        //使用范围
        if (Objects.nonNull(scopeType)) {
//            bool.must(termQuery("scopeType", scopeType.toValue()));
            bool.must(term(g -> g.field("scopeType").value(scopeType.toValue())));
        }

        //模糊查询名称
        if (StringUtils.isNotEmpty(likeCouponName)) {
            /*bool.must(QueryBuilders.wildcardQuery("couponName", StringUtil.ES_LIKE_CHAR.concat(
                    XssUtils.replaceEsLikeWildcard(likeCouponName.trim())).concat(StringUtil.ES_LIKE_CHAR)));*/
            bool.must(wildcard(g -> g.field("couponName").wildcard( StringUtil.ES_LIKE_CHAR.concat(XssUtils.replaceEsLikeWildcard(likeCouponName.trim())).concat(StringUtil.ES_LIKE_CHAR))));
        }

        //删除标记
        if (Objects.nonNull(deleteFlag)) {
//            bool.must(termQuery("delFlag", deleteFlag.toValue()));
            bool.must(term(g -> g.field("delFlag").value(deleteFlag.toValue())));
        }

        if (Objects.nonNull(couponStatus)) {
//            BoolQueryBuilder b1 = QueryBuilders.boolQuery();
//            BoolQueryBuilder b2 = QueryBuilders.boolQuery();
//            BoolQueryBuilder b3 = QueryBuilders.boolQuery();
            BoolQuery.Builder b1 = QueryBuilders.bool();
            BoolQuery.Builder b2 = QueryBuilders.bool();
            BoolQuery.Builder b3 = QueryBuilders.bool();
            switch (couponStatus) {
                case STARTED://进行中

//                    b1.must(QueryBuilders.rangeQuery("startTime").lt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    b1.must(QueryBuilders.range(g -> g.field("startTime").lt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));

//                    b1.must(QueryBuilders.rangeQuery("endTime").gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    b1.must(QueryBuilders.range(g -> g.field("endTime").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));

//                    b3.must(QueryBuilders.termQuery("rangeDayType", RangeDayType.RANGE_DAY.toValue()));
                    b3.must(QueryBuilders.term(g -> g.field("rangeDayType").value(RangeDayType.RANGE_DAY.toValue())));

//                    bool.must(b1).must(b2).must(b3);
                    bool.must(a -> a.bool(b1.build())).must(a -> a.bool(b2.build())).must(a -> a.bool(b3.build()));
                    break;
                case NOT_START://未生效
//                    b1.must(QueryBuilders.rangeQuery("startTime").gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    b1.must(QueryBuilders.range(g -> g.field("startTime").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));

//                    b2.must(QueryBuilders.termQuery("rangeDayType", RangeDayType.RANGE_DAY.toValue()));
                    b2.must(QueryBuilders.term(g -> g.field("rangeDayType").value(RangeDayType.RANGE_DAY.toValue())));

//                    bool.must(b1).must(b2);
                    bool.must(a -> a.bool(b1.build())).must(a -> a.bool(b2.build()));
                    break;
                case DAYS://领取生效

//                    b1.must(QueryBuilders.termQuery("rangeDayType", RangeDayType.DAYS.toValue()));
                    b1.must(QueryBuilders.term(g -> g.field("rangeDayType").value(RangeDayType.DAYS.toValue())));

//                    bool.must(b1);
                    bool.must(a -> a.bool(b1.build()));
                    break;
                case ENDED://已结束
//                    b1.must(QueryBuilders.rangeQuery("endTime").lt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    b1.must(QueryBuilders.range(g -> g.field("endTime").lt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));

//                    b2.must(QueryBuilders.termQuery("rangeDayType", RangeDayType.RANGE_DAY.toValue()));
                    b2.must(QueryBuilders.term(g -> g.field("rangeDayType").value(RangeDayType.RANGE_DAY.toValue())));

//                    bool.must(b1).must(b2);
                    bool.must(a -> a.bool(b1.build())).must(a -> a.bool(b2.build()));
                    break;
                default:
                    break;
            }
        }

        return bool.build();
    }

    private Query getSearchQuery(EsCouponInfoPageRequest request) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        builder.withIndices(EsConstants.DOC_COUPON_INFO_TYPE);
//        builder.withQuery(getBoolQueryBuilder(request));
//        builder.withPageable(request.getPageable());
//        builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
//        return builder.build();
        return NativeQuery.builder()
                .withQuery(g -> g.bool(getBoolQueryBuilder(request)))
                .withPageable(request.getPageable())
                .withSort(g -> g.field(r -> r.field("createTime").order(SortOrder.Desc)))
                .build();
    }


}
