package com.wanmi.sbc.elastic.activitycoupon.service;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.activitycoupon.mapper.EsActivityCouponMapper;
import com.wanmi.sbc.elastic.activitycoupon.repository.EsActivityCouponRepository;
import com.wanmi.sbc.elastic.activitycoupon.request.EsActivityCouponCriteriaBuilder;
import com.wanmi.sbc.elastic.activitycoupon.root.EsActivityCoupon;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponModifyRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponPageRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsActivityCouponDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 优惠券Service
 */
@Slf4j
@Service
public class EsActivityCouponService {

    @Autowired
    private EsActivityCouponRepository esActivityCouponRepository;

    @Autowired
    private EsBaseService esBaseService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsActivityCouponMapper esActivityCouponMapper;


    @WmResource("mapping/esActivityCoupon.json")
    private Resource mapping;



    /**
     * 保存优惠券ES数据
     *
     * @param esActivityCouponDTOS
     * @return
     */
    public Iterable<EsActivityCoupon> saveAll(List<EsActivityCouponDTO> esActivityCouponDTOS) {
        if (CollectionUtils.isNotEmpty(esActivityCouponDTOS)) {
            //手动删除索引时，重新设置mapping
            esBaseService.existsOrCreate(EsConstants.DOC_ACTIVITY_COUPON_TYPE, mapping);
            List<EsActivityCoupon> esActivityCoupons = esActivityCouponMapper.couponInfoDTOToEsCouponInfo(esActivityCouponDTOS);
            return esActivityCouponRepository.saveAll(esActivityCoupons);
        } else {
            return Lists.newArrayList();
        }
    }


    /**
     * 更新优惠券活动范围部分字段
     *
     * @param request
     */
    public void modifyScope(EsActivityCouponModifyRequest request) {
        /*UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
        updateByQueryRequest.indices(EsConstants.DOC_ACTIVITY_COUPON_TYPE);
        fillEsModifyRequest(updateByQueryRequest,request);
        try {
            BulkByScrollResponse bulkByScrollResponse = elasticsearchTemplate.updateByQuery(updateByQueryRequest,
                    RequestOptions.DEFAULT);
            this.failures(bulkByScrollResponse);
        } catch (IOException e) {
            log.error("EsActivityCoupon更新失败", e);
        }*/
        UpdateQuery updateQuery = fillEsModifyRequest(request);
        ByQueryResponse byQueryResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                IndexCoordinates.of(EsConstants.DOC_ACTIVITY_COUPON_TYPE));
        this.failures(byQueryResponse);
    }

    public UpdateQuery fillEsModifyRequest(EsActivityCouponModifyRequest request) {
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        if (StringUtils.isEmpty(request.getCouponCateId())) {
//            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            if (StringUtils.isNotEmpty(request.getActivityId())){
//                TermsQueryBuilder activityIdBuilder = QueryBuilders.termsQuery("activityId",Lists.newArrayList(request.getActivityId()));
//                boolQueryBuilder.must(activityIdBuilder);
                boolQueryBuilder.must(a -> a.term(v -> v.field("activityId").value(request.getActivityId())));
            }
            if (StringUtils.isNotEmpty(request.getCouponId())) {
//                TermsQueryBuilder couponIdBuilder = QueryBuilders.termsQuery("couponId", Lists.newArrayList(request.getCouponId()));
//                boolQueryBuilder.must(couponIdBuilder);
                boolQueryBuilder.must(a -> a.term(v -> v.field("couponId").value(request.getCouponId())));
            }
        } else {
            if (request.isDelete()) {
//                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//                boolQueryBuilder.must(QueryBuilders.termQuery("couponCateId", Lists.newArrayList(request.getCouponCateId())));
//                updateByQueryRequest.setQuery(boolQueryBuilder);
                boolQueryBuilder.must(a -> a.term(v -> v.field("couponCateId").value(request.getCouponCateId())));
            } else {
//                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//                TermQueryBuilder couponCateIdBuilder = QueryBuilders.termQuery("couponCateId", Lists.newArrayList(request.getCouponCateId()));
//                TermQueryBuilder platformFlagBuilder = QueryBuilders.termQuery("platformFlag", Lists.newArrayList(DefaultFlag.NO.toValue()));
//                boolQueryBuilder.must(couponCateIdBuilder);
//                boolQueryBuilder.must(platformFlagBuilder);
//                updateByQueryRequest.setQuery(boolQueryBuilder);
                boolQueryBuilder.must(a -> a.term(v -> v.field("couponCateId").value(request.getCouponCateId())));
                boolQueryBuilder.must(a -> a.term(v -> v.field("platformFlag").value(DefaultFlag.NO.toValue())));
            }

        }
//        updateByQueryRequest.setScript(this.getScript(request));
//        updateByQueryRequest.setAbortOnVersionConflict(false);
        return UpdateQuery.builder(NativeQuery.builder().withQuery(a -> a.bool(boolQueryBuilder.build())).build()).withScript(this.getScript(request))
                .withScriptType(ScriptType.INLINE)
                .withRefreshPolicy(RefreshPolicy.IMMEDIATE)
                .withAbortOnVersionConflict(false).build();
    }

    private void failures(ByQueryResponse bulkByScrollResponse) {
        List<ByQueryResponse.Failure> bulkFailures = bulkByScrollResponse.getFailures();
        if (CollectionUtils.isEmpty(bulkFailures)) {
            return;
        }
        bulkFailures.forEach(err ->
                log.error("活动优惠券更新失败", err.getCause()));
    }


    private String  getScript(EsActivityCouponModifyRequest request) {
        if (Objects.nonNull(request.getHasLeft())) {
            return "ctx._source.hasLeft=" + request.getHasLeft().toValue();
        } else if (Objects.nonNull(request.getPauseFlag())) {
            return "ctx._source.pauseFlag=" + request.getPauseFlag().toValue();
        } else if (Objects.nonNull(request.getActivityEndTime())) {
            return "ctx._source.activityEndTime='" + DateUtil.format(request.getActivityEndTime(), DateUtil.FMT_TIME_4) + "'";
        } else if(StringUtils.isNotEmpty(request.getCouponCateId())){
            return "ctx._source.couponCateId.removeIf(couponCateId -> couponCateId == "  + request.getCouponCateId()+ ")";
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"暂无更新项");
        }

    }

    /**
     * 根据活动id删除对应ES数据
     *
     * @param activityId
     */
    public void deleteByActivityId(String activityId) {
        esActivityCouponRepository.deleteByActivityId(activityId);
    }



    /**
     * 分页查询ES优惠券信息
     *
     * @param request
     * @return
     */
    public Page<EsActivityCoupon> page(EsActivityCouponPageRequest request) {
        return esBaseService.commonPage(this.getSearchQuery(request), EsActivityCoupon.class, EsConstants.DOC_ACTIVITY_COUPON_TYPE);
    }



    private Query getSearchQuery(EsActivityCouponPageRequest request) {
        /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        EsActivityCouponCriteriaBuilder bq =  KsBeanUtil.convert(request, EsActivityCouponCriteriaBuilder.class);
        builder.withQuery(bq.getWhereCriteria());
        builder.withPageable(request.getPageable());
        builder.withSort(SortBuilders.fieldSort("activityStartTime").order(SortOrder.ASC));
        builder.withSort(SortBuilders.fieldSort("couponType").order(SortOrder.ASC));
        builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.ASC));
        return builder.build();*/
        EsActivityCouponCriteriaBuilder bq =  KsBeanUtil.convert(request, EsActivityCouponCriteriaBuilder.class);
        return NativeQuery.builder().withQuery(a -> a.bool(bq.getWhereCriteria()))
                .withPageable(request.getPageable())
                .withSort(SortOptions.of(g -> g.field(a -> a.field("activityStartTime").order(SortOrder.Asc))),
                        SortOptions.of(g -> g.field(a -> a.field("couponType").order(SortOrder.Asc))),
                        SortOptions.of(g -> g.field(a -> a.field("createTime").order(SortOrder.Asc)))).build();
    }



}
