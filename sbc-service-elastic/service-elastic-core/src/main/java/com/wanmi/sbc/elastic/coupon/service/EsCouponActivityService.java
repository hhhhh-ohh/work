package com.wanmi.sbc.elastic.coupon.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityAddListByActivityIdRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityInitRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityPageRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsMagicCouponActivityPageRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponActivityDTO;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.coupon.mapper.EsCouponActivityMapper;
import com.wanmi.sbc.elastic.coupon.model.root.EsCouponActivity;
import com.wanmi.sbc.elastic.coupon.repository.EsCouponActivityRepository;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityListPageRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityBaseVO;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;


/**
 * 优惠券活动Service
 */
@Slf4j
@Service
public class EsCouponActivityService {

    @Autowired
    private EsCouponActivityRepository esCouponActivityRepository;

    @Autowired
    private EsCouponActivityMapper esCouponActivityMapper;

    @Autowired
    private CouponActivityQueryProvider couponActivityQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esCouponActivity.json")
    private Resource mapping;

    /**
     * 初始化ES数据
     */
    public void init(EsCouponActivityInitRequest esCouponActivityInitRequest){

        boolean initCouponActivity = true;
        int pageNum = Objects.nonNull(esCouponActivityInitRequest.getPageNum())?esCouponActivityInitRequest.getPageNum():0;
        Integer pageSize = esCouponActivityInitRequest.getPageSize();
        CouponActivityListPageRequest request = KsBeanUtil.convert(esCouponActivityInitRequest,CouponActivityListPageRequest.class);
        try {
            while (initCouponActivity) {
                request.putSort("createTime", SortType.DESC.toValue());
                request.setPageNum(pageNum);
                request.setPageSize(pageSize);
                if(CollectionUtils.isNotEmpty(esCouponActivityInitRequest.getIdList())){
                    request.setActivityIds(esCouponActivityInitRequest.getIdList());
                    request.setPageNum(0);
                    request.setPageSize(esCouponActivityInitRequest.getIdList().size());
                    initCouponActivity = false;//idList不为空时退出循环
                }
                List<CouponActivityBaseVO> couponActivityBaseVOS = couponActivityQueryProvider.listByPage(request).getContext().getCouponActivityBaseVOList();
                if (CollectionUtils.isEmpty(couponActivityBaseVOS)){
                    initCouponActivity = false;
                    log.info("==========ES初始化优惠券活动结束，结束pageNum:{}==============",pageNum);
                }else {
                    List<EsCouponActivity> esCouponInfoDTOList = esCouponActivityMapper.couponInfoToEsCouponActivity(couponActivityBaseVOS);
                    this.saveAll(esCouponInfoDTOList);
                    log.info("==========ES初始化优惠券活动成功，当前pageNum:{}==============",pageNum);
                    pageNum++;
                }
            }
        }catch (Exception e){
            log.info("==========ES初始化优惠券活动异常，异常pageNum:{}==============",pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040003,new Object[]{pageNum});
        }

    }

    /**
     * 保存优惠券活动ES数据
     * @param esCouponInfoDTOList
     * @return
     */
    public Iterable<EsCouponActivity>  saveAll(List<EsCouponActivity> esCouponInfoDTOList){
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.DOC_COUPON_ACTIVITY, mapping);
        return esCouponActivityRepository.saveAll(esCouponInfoDTOList);
    }



    /**
     * 保存优惠券活动ES数据
     * @param esCouponInfoDTO
     * @return
     */
    public EsCouponActivity save(EsCouponActivityDTO esCouponInfoDTO){
        EsCouponActivity esCouponActivity = esCouponActivityMapper.couponInfoToEsCouponActivity(esCouponInfoDTO);
        return save(esCouponActivity);
    }

    private EsCouponActivity save(EsCouponActivity esCouponActivity) {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.DOC_COUPON_ACTIVITY, mapping);
        return esCouponActivityRepository.save(esCouponActivity);
    }

    /**
     * 根据活动ID批量新增ES数据
     * @param request
     * @return
     */
    public void saveAllById(EsCouponActivityAddListByActivityIdRequest request){
        CouponActivityListPageRequest pageRequest = new CouponActivityListPageRequest();
        pageRequest.setActivityIds(request.getActivityIdList());
        List<CouponActivityBaseVO> couponActivityBaseVOS = couponActivityQueryProvider.listByPage(pageRequest).getContext().getCouponActivityBaseVOList();
        if (CollectionUtils.isEmpty(couponActivityBaseVOS)){
            log.info("==========根据活动ID查询不到数据，批量新增ES数据失败，活动ID集合:{}==============",request.getActivityIdList());
           return;
        }

        List<EsCouponActivity> esCouponInfoDTOList = esCouponActivityMapper.couponInfoToEsCouponActivity(couponActivityBaseVOS);
        this.saveAll(esCouponInfoDTOList);
    }

    /**
     * 根据优惠券活动ID删除对应ES数据
     * @param activityId
     */
    public void deleteById(String activityId){
        esCouponActivityRepository.deleteById(activityId);
    }

    /**
     * 开启活动
     * @param activityId
     */
    public void start(String activityId) {
        EsCouponActivity esCouponActivity = esCouponActivityRepository.findById(activityId).orElseThrow(() -> new SbcRuntimeException(ElasticErrorCodeEnum.K040002,new Object[]{activityId}));
        esCouponActivity.setPauseFlag(DefaultFlag.NO);
        save(esCouponActivity);
    }

    /**
     * 暂停活动
     * @param activityId
     */
    public void pause(String activityId) {
        EsCouponActivity esCouponActivity = esCouponActivityRepository.findById(activityId).orElseThrow(() -> new SbcRuntimeException(ElasticErrorCodeEnum.K040002,new Object[]{activityId}));
        esCouponActivity.setPauseFlag(DefaultFlag.YES);
        save(esCouponActivity);
    }


    /**
     * 分页查询ES优惠券活动信息
     * @param request
     * @return
     */
    public Page<EsCouponActivity> page(EsCouponActivityPageRequest request){
        return esBaseService.commonPage(this.getSearchQuery(request), EsCouponActivity.class,
                EsConstants.DOC_COUPON_ACTIVITY);
    }

    /**
     * @description 分页查询ES魔方优惠券活动信息
     * @author  EDZ
     * @date 2021/6/11 11:36
     * @param request
     * @return org.springframework.data.domain.Page<com.wanmi.sbc.elastic.coupon.model.root.EsCouponActivity>
     **/
    public Page<EsCouponActivity> magicPage(EsMagicCouponActivityPageRequest request){
        return esBaseService.commonPage(this.getMagicSearchQuery(request), EsCouponActivity.class,
                EsConstants.DOC_COUPON_ACTIVITY);
    }

    /**
     * 优惠券活动列表查询条件封装
     *
     * @return
     */
    private BoolQuery getBoolQueryBuilder(EsCouponActivityPageRequest request) {
        BoolQuery.Builder bool = QueryBuilders.bool();
        if(CollectionUtils.isNotEmpty(request.getCouponActivityIdList())){
//            bool.must(termsQuery("activityId",request.getCouponActivityIdList()));
            bool.must(terms(a -> a.field("activityId").terms(v -> v.value(request.getCouponActivityIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        if (Objects.nonNull(request.getStoreId())) {
//            bool.must(termQuery("storeId", request.getStoreId()));
            bool.must(term(a -> a.field("storeId").value(request.getStoreId())));
        } else {
            if (request.getPluginType() == null) {
                if (Objects.equals(DefaultFlag.YES,request.getSupplierFlag())){
//                    bool.must(termQuery("platformFlag", 0));
                    bool.must(term(a -> a.field("platformFlag").value(0)));
//                    bool.mustNot(termQuery("pluginType", PluginType.O2O.toValue()));
                    bool.mustNot(term(a -> a.field("pluginType").value(PluginType.O2O.toValue())));
                }else {
//                    bool.must(termQuery("platformFlag", 1));
                    bool.must(term(a -> a.field("platformFlag").value(1)));
//                    bool.mustNot(termQuery("pluginType", PluginType.O2O.toValue()));
                    bool.mustNot(term(a -> a.field("pluginType").value(PluginType.O2O.toValue())));
                }
            } else {
//                bool.must(termQuery("pluginType", PluginType.O2O.toValue()));
                bool.must(term(a -> a.field("pluginType").value(PluginType.O2O.toValue())));
            }
        }

        if (CollectionUtils.isNotEmpty(request.getStoreIds()) && Objects.equals(DefaultFlag.YES,request.getSupplierFlag())){
//            bool.must(termsQuery("storeId",request.getStoreIds()));
            bool.must(terms(a -> a.field("storeId").terms(v -> v.value(request.getStoreIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (StringUtils.isNotEmpty(request.getActivityName())) {
//            bool.must(QueryBuilders.wildcardQuery("activityName", StringUtil.ES_LIKE_CHAR.concat(XssUtils.replaceEsLikeWildcard(request.getActivityName().trim())).concat(StringUtil.ES_LIKE_CHAR)));
            bool.must(wildcard(a -> a.field("activityName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getActivityName().trim()))));
        }

        //活动类型筛选
        if (CouponActivityType.ALL_COUPONS.equals(request.getCouponActivityType())
                || CouponActivityType.SPECIFY_COUPON.equals(request.getCouponActivityType())
                || CouponActivityType.STORE_COUPONS.equals(request.getCouponActivityType())
                || CouponActivityType.REGISTERED_COUPON.equals(request.getCouponActivityType())
                || CouponActivityType.RIGHTS_COUPON.equals(request.getCouponActivityType())
                || CouponActivityType.DISTRIBUTE_COUPON.equals(request.getCouponActivityType())
                || CouponActivityType.POINTS_COUPON.equals(request.getCouponActivityType())
                || CouponActivityType.ENTERPRISE_REGISTERED_COUPON.equals(request.getCouponActivityType())
                || CouponActivityType.DRAW_COUPON.equals(request.getCouponActivityType())) {
//            bool.must(termQuery("couponActivityType", request.getCouponActivityType().toValue()));
            bool.must(term(a -> a.field("couponActivityType").value(request.getCouponActivityType().toValue())));
        }

        //只查询权益、分销、积分券
        if(request.getCouponLimit()){
            /*BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            BoolQueryBuilder q1 = QueryBuilders.boolQuery();
            BoolQueryBuilder q2 = QueryBuilders.boolQuery();
            BoolQueryBuilder q3 = QueryBuilders.boolQuery();
            BoolQueryBuilder q4 = QueryBuilders.boolQuery();

            q1.must(QueryBuilders.termQuery("couponActivityType", CouponActivityType.RIGHTS_COUPON.toValue()));
            q2.must(QueryBuilders.termQuery("couponActivityType", CouponActivityType.DISTRIBUTE_COUPON.toValue()));
            q3.must(QueryBuilders.termQuery("couponActivityType", CouponActivityType.POINTS_COUPON.toValue()));
            q4.must(QueryBuilders.termQuery("couponActivityType", CouponActivityType.DRAW_COUPON.toValue()));

            boolQueryBuilder.should(q1).should(q2).should(q3).should(q4);
            bool.must(boolQueryBuilder);*/
            BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
            BoolQuery.Builder q1 = QueryBuilders.bool();
            BoolQuery.Builder q2 = QueryBuilders.bool();
            BoolQuery.Builder q3 = QueryBuilders.bool();
            BoolQuery.Builder q4 = QueryBuilders.bool();
            q1.must(term(a -> a.field("couponActivityType").value(CouponActivityType.RIGHTS_COUPON.toValue())));
            q2.must(term(a -> a.field("couponActivityType").value(CouponActivityType.DISTRIBUTE_COUPON.toValue())));
            q3.must(term(a -> a.field("couponActivityType").value(CouponActivityType.POINTS_COUPON.toValue())));
            q4.must(term(a -> a.field("couponActivityType").value(CouponActivityType.DRAW_COUPON.toValue())));
            boolQueryBuilder.should(a -> a.bool(q1.build())).should(a -> a.bool(q2.build())).should(a -> a.bool(q3.build())).should(a -> a.bool(q4.build()));
            bool.must(a -> a.bool(boolQueryBuilder.build()));
        }


        if (Objects.nonNull(request.getStartTime())) {
//            bool.must(QueryBuilders.rangeQuery("startTime").gte(DateUtil.format(request.getStartTime(), DateUtil.FMT_TIME_4)));
            bool.must(range(a -> a.field("startTime").gte(JsonData.of(DateUtil.format(request.getStartTime(), DateUtil.FMT_TIME_4)))));
        }

        if (Objects.nonNull(request.getEndTime())) {
//            bool.must(QueryBuilders.rangeQuery("endTime").lte(DateUtil.format(request.getEndTime(), DateUtil.FMT_TIME_4)));
            bool.must(range(a -> a.field("endTime").lte(JsonData.of(DateUtil.format(request.getEndTime(), DateUtil.FMT_TIME_4)))));
        }

        if (Objects.nonNull(request.getQueryTab())) {
//            BoolQueryBuilder b1 = QueryBuilders.boolQuery();
//            BoolQueryBuilder b2 = QueryBuilders.boolQuery();
//            BoolQueryBuilder b3 = QueryBuilders.boolQuery();
//            BoolQueryBuilder b4 = QueryBuilders.boolQuery();
//            BoolQueryBuilder b5 = QueryBuilders.boolQuery();
            BoolQuery.Builder b1 = QueryBuilders.bool();
            BoolQuery.Builder b2 = QueryBuilders.bool();
            BoolQuery.Builder b3 = QueryBuilders.bool();
            BoolQuery.Builder b4 = QueryBuilders.bool();
            BoolQuery.Builder b5 = QueryBuilders.bool();
            switch (request.getQueryTab()) {
                case STARTED://进行中
//                    b1.must(QueryBuilders.rangeQuery("startTime").lt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
//                    b2.must(QueryBuilders.rangeQuery("endTime").gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
//                    b3.must(b1).must(b2);
                    b1.must(range(a -> a.field("startTime").lt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
                    b2.must(range(a -> a.field("endTime").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
                    b3.must(a -> a.bool(b1.build())).must(a -> a.bool(b2.build()));

                    /*b4.must(QueryBuilders.termQuery("couponActivityType", CouponActivityType.RIGHTS_COUPON.toValue()));
                    b4.mustNot(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("startTime")));
                    b4.mustNot(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("endTime")));*/
                    b4.must(term(a -> a.field("couponActivityType").value(CouponActivityType.RIGHTS_COUPON.toValue())));
                    b4.mustNot(exists(a1 -> a1.field("startTime")));
                    b4.mustNot(exists(a1 -> a1.field("endTime")));

//                    b5.should(b3).should(b4);
                    b5.should(a -> a.bool(b3.build())).should(a -> a.bool(b4.build()));

//                    bool.must(b5);
                    bool.must(a -> a.bool(b5.build()));

//                    bool.must(QueryBuilders.termQuery("pauseFlag", 0));
                    bool.must(term(a -> a.field("pauseFlag").value(0)));

//                    bool.mustNot(QueryBuilders.termQuery("couponActivityType", 1));
                    bool.mustNot(term(a -> a.field("couponActivityType").value(1)));

                    if (PluginType.O2O == request.getPluginType()) {
//                        bool.must(matchQuery("auditState", AuditState.CHECKED.getStatusId()));
                        bool.must(match(a -> a.field("auditState").query(AuditState.CHECKED.getStatusId())));
                    }

                    break;
                case PAUSED://暂停中

//                    b1.must(QueryBuilders.rangeQuery("startTime").lte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    b1.must(range(a -> a.field("startTime").lte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));

//                    b2.must(QueryBuilders.rangeQuery("endTime").gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    b2.must(range(a -> a.field("endTime").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));

//                    b3.must(b1).must(b2);
                    b3.must(a -> a.bool(b1.build())).must(a -> a.bool(b2.build()));

//                    b4.must(QueryBuilders.termQuery("couponActivityType", CouponActivityType.RIGHTS_COUPON.toValue()));
                    b4.must(term(a -> a.field("couponActivityType").value(CouponActivityType.RIGHTS_COUPON.toValue())));
//                    b4.mustNot(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("startTime")));
                    b4.mustNot(exists(a1 -> a1.field("startTime")));
//                    b4.mustNot(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery("endTime")));
                    b4.mustNot(exists(a1 -> a1.field("endTime")));

//                    b5.should(b3).should(b4);
                    b5.should(a -> a.bool(b3.build())).should(a -> a.bool(b4.build()));

//                    bool.must(b5);
                    bool.must(a -> a.bool(b5.build()));

//                    bool.must(QueryBuilders.termQuery("pauseFlag", 1));
                    bool.must(term(a -> a.field("pauseFlag").value(1)));

                    if (PluginType.O2O == request.getPluginType()) {
//                        bool.must(matchQuery("auditState", AuditState.CHECKED.getStatusId()));
                        bool.must(match(a -> a.field("auditState").query(AuditState.CHECKED.getStatusId())));
                    }
                    break;
                case NOT_START://未开始
//                    bool.must(QueryBuilders.rangeQuery("startTime").gt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    bool.must(range(a -> a.field("startTime").gt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));

                    if (PluginType.O2O == request.getPluginType()) {
//                        bool.must(matchQuery("auditState", AuditState.CHECKED.getStatusId()));
                        bool.must(match(a -> a.field("auditState").query(AuditState.CHECKED.getStatusId())));
                    }
                    break;
                case ENDED://已结束
//                    bool.must(QueryBuilders.rangeQuery("endTime").lt(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    bool.must(range(a -> a.field("endTime").lt(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));

                    if (PluginType.O2O == request.getPluginType()) {
//                        bool.must(matchQuery("auditState", AuditState.CHECKED.getStatusId()));
                        bool.must(match(a -> a.field("auditState").query(AuditState.CHECKED.getStatusId())));
                    }
                    break;
                case REQUEST_WAIT_CHECK:
//                    bool.must(matchQuery("auditState", AuditState.NON_CHECKED.getStatusId()));
                    bool.must(match(a -> a.field("auditState").query(AuditState.NON_CHECKED.getStatusId())));
                    break;
                case REQUEST_NOT_PASS:
//                    bool.must(matchQuery("auditState", AuditState.REJECTED.getStatusId()));
                    bool.must(match(a -> a.field("auditState").query(AuditState.REJECTED.getStatusId())));
                    break;
                default:
                    break;
            }
        }

        if (StringUtils.isNotBlank(request.getJoinLevel())) {
//            bool.must(QueryBuilders.termsQuery("joinLevels", request.getJoinLevel()));
            bool.must(terms(a -> a.field("joinLevels").terms(v -> v.value(Arrays.stream(request.getJoinLevel().split(",")).map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (request.getBossJoinLevel() != null) {
            if (request.getBossJoinLevel() == -1) {
//                bool.must(QueryBuilders.termQuery("joinLevels", -1));
                bool.must(term(a -> a.field("joinLevels").value(-1)));
            } else if (request.getBossJoinLevel() == 0){
//                bool.mustNot(QueryBuilders.termQuery("joinLevels", -1));
                bool.mustNot(term(a -> a.field("joinLevels").value(-1)));
            }else {
//                bool.must(QueryBuilders.termQuery("joinLevels", request.getBossJoinLevel()));
                bool.must(term(a -> a.field("joinLevels").value(request.getBossJoinLevel())));
            }
        }

        return bool.build();

    }

    private Query getSearchQuery(EsCouponActivityPageRequest request) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        builder.withIndices(EsConstants.DOC_COUPON_ACTIVITY);
//        builder.withQuery(getBoolQueryBuilder(request));
//        builder.withPageable(request.getPageable());
//        builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
//        return builder.build();
        NativeQuery builder =
                NativeQuery.builder()
                        .withQuery(g -> g.bool(getBoolQueryBuilder(request)))
                        .withPageable(request.getPageable())
                        .withSort(g -> g.field(r -> r.field("createTime").order(SortOrder.Desc)))
                        .build();
        return builder;
    }


    public BoolQuery getMagicBoolQueryBuilder(EsMagicCouponActivityPageRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();
        if (Objects.nonNull(request.getStoreId())) {
            // 店铺id
//            bool.must(termQuery("storeId", request.getStoreId()));
            bool.must(term(a -> a.field("storeId").value(request.getStoreId())));
        }
        if (Objects.nonNull(request.getPlatformFlag()) && PluginType.O2O != request.getPluginType()) {
            // 是否平台 0店铺 1平台
//            bool.must(termQuery("platformFlag", request.getPlatformFlag().toValue()));
            bool.must(term(a -> a.field("platformFlag").value(request.getPlatformFlag().toValue())));
        }

        if (PluginType.O2O == request.getPluginType()) {
//            bool.must(termQuery("pluginType", PluginType.O2O.toValue()));
            bool.must(term(a -> a.field("pluginType").value(PluginType.O2O.toValue())));
//            bool.must(matchQuery("auditState", AuditState.CHECKED.getStatusId()));
            bool.must(match(a -> a.field("auditState").query(AuditState.CHECKED.getStatusId())));
        } else if (DefaultFlag.NO == request.getPlatformFlag()) {
//            bool.mustNot(termQuery("pluginType", PluginType.O2O.toValue()));
            bool.mustNot(term(a -> a.field("pluginType").value(PluginType.O2O.toValue())));
        }

        if (StringUtils.isNotEmpty(request.getActivityName())) {
            // 活动名称
//            bool.must(
//                    QueryBuilders.wildcardQuery(
//                            "activityName",
//                            StringUtil.ES_LIKE_CHAR
//                                    .concat(XssUtils.replaceEsLikeWildcard(request.getActivityName().trim()))
//                                    .concat(StringUtil.ES_LIKE_CHAR)));
            bool.must(wildcard(a -> a.field("activityName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getActivityName().trim()))));
        }
        // 默认全场赠券的活动
        if (request.getCouponActivityType() != null) {
//            bool.must(termQuery("couponActivityType", request.getCouponActivityType().toValue()));
            bool.must(term(a -> a.field("couponActivityType").value(request.getCouponActivityType().toValue())));
        } else {
//            bool.must(termQuery("couponActivityType", CouponActivityType.ALL_COUPONS.toValue()));
            bool.must(term(a -> a.field("couponActivityType").value(CouponActivityType.ALL_COUPONS.toValue())));
        }
        // endTime>=当前时间：筛选出未开始和进行中的活动
//        bool.must(
//                QueryBuilders.rangeQuery("endTime")
//                        .gte(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
        bool.must(range(a -> a.field("endTime").gte(JsonData.of(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)))));
        //暂停活动不展示
//        bool.must(termQuery("pauseFlag", DefaultFlag.NO.toValue()));
        bool.must(term(a -> a.field("pauseFlag").value(DefaultFlag.NO.toValue())));
        //删除标记
//        bool.must(termQuery("delFlag", DeleteFlag.NO.toValue()));
        bool.must(term(a -> a.field("delFlag").value(DeleteFlag.NO.toValue())));
        return bool.build();
    }

    public Query getMagicSearchQuery(EsMagicCouponActivityPageRequest request) {
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        builder.withIndices(EsConstants.DOC_COUPON_ACTIVITY);
//        builder.withQuery(getMagicBoolQueryBuilder(request));
//        builder.withPageable(request.getPageable());
//        builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
//        return builder.build();
        NativeQuery builder =
                NativeQuery.builder()
                        .withQuery(getMagicBoolQueryBuilder(request)._toQuery())
                        .withPageable(request.getPageable())
                        .withSort(g -> g.field(r -> r.field("createTime").order(SortOrder.Desc)))
                        .build();
        return builder;
    }
}
