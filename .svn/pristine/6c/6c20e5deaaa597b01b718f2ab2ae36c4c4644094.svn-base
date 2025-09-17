package com.wanmi.sbc.elastic.activitycoupon.service;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.activitycoupon.mapper.EsActivityCouponMapper;
import com.wanmi.sbc.elastic.activitycoupon.mapper.EsCouponScopeMapper;
import com.wanmi.sbc.elastic.activitycoupon.repository.EsActivityCouponRepository;
import com.wanmi.sbc.elastic.activitycoupon.repository.EsCouponScopeRepository;
import com.wanmi.sbc.elastic.activitycoupon.request.EsActivityCouponCriteriaBuilder;
import com.wanmi.sbc.elastic.activitycoupon.root.EsActivityCoupon;
import com.wanmi.sbc.elastic.activitycoupon.root.EsCouponScope;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponInitRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsActivityCouponModifyRequest;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponScopePageRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsActivityCouponDTO;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponScopeDTO;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponMarketingScopeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCacheListRequest;
import com.wanmi.sbc.marketing.bean.vo.CouponCacheVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券Service
 */
@Slf4j
@Service
public class EsCouponScopeService {

    @Autowired
    private EsCouponScopeRepository esCouponScopeRepository;

    @Autowired
    private EsActivityCouponRepository esActivityCouponRepository;

    @Autowired
    private EsActivityCouponMapper esActivityCouponMapper;

    @Autowired
    private EsCouponScopeMapper esCouponScopeMapper;

    @Autowired
    private CouponMarketingScopeQueryProvider couponMarketingScopeQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private EsActivityCouponService esActivityCouponService;


    @WmResource("mapping/esCouponScope.json")
    private Resource mapping;

    @WmResource("mapping/esActivityCoupon.json")
    private Resource activityCouponMapping;

    private final static Integer DEFAULT_PAGE_SIZE = 10000;



    /**
     * 初始化 活动优惠券和优惠券范围
     */
    @Async
    public void init(EsActivityCouponInitRequest initRequest) {

        clearAndRebuildIndex(initRequest);
        boolean initCouponScope = true;
        int pageNum = initRequest.getPageNum();
        Integer pageSize = initRequest.getPageSize();


        try {
            while (initCouponScope) {
                if (CollectionUtils.isNotEmpty(initRequest.getIdList())) {
                    pageNum = 0;
                    pageSize = initRequest.getIdList().size();
                    initCouponScope = Boolean.FALSE;
                }
                CouponCacheListRequest request =new CouponCacheListRequest();
                request.setPageNum(pageNum);
                request.setCouponActivityIds(initRequest.getIdList());
                request.setPageSize(pageSize);
                List<CouponCacheVO> couponCacheVOList = couponMarketingScopeQueryProvider
                        .cachePage(request)
                        .getContext().getCacheList();

                if (CollectionUtils.isEmpty(couponCacheVOList)) {
                    initCouponScope = false;
                    log.info("==========ES初始化活动优惠券结束，结束pageNum:{}==============", pageNum);
                } else {

                    List<EsActivityCouponDTO> activityCouponDTOS = EsActivityCouponDTO.translateByCache(couponCacheVOList);
                    List<EsActivityCoupon> esActivityCoupons = esActivityCouponMapper.couponInfoDTOToEsCouponInfo(activityCouponDTOS);
                     esActivityCouponRepository.saveAll(esActivityCoupons);
                    List<List<CouponCacheVO>> splitCacheList = Lists.partition(couponCacheVOList, 5000);
                    for (int i = 0; i < splitCacheList.size(); i++) {
                        try {
                            List<CouponCacheVO> splitList = splitCacheList.get(i);
                            List<EsCouponScopeDTO> scopes = EsCouponScopeDTO.translateByCache(splitList);
                            List<EsCouponScope> esCouponScopeList = esCouponScopeMapper.couponInfoDTOToEsCouponInfo(scopes);
                            //手动删除索引时，重新设置mapping
                             esCouponScopeRepository.saveAll(esCouponScopeList);
                        } catch (Exception e) {
                            log.error("初始化esCouponScope失败", e);
                        }

                    }
                    log.info("==========ES初始化活动优惠券成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化活动优惠券异常，异常pageNum:{}==============", pageNum);
            log.error("==========ES初始化活动优惠券范围异常", e);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040001, new Object[]{pageNum});
        }


    }


    /***
     * 清理-重建索引
     * @param request 初始化ES请求
     */
    protected void clearAndRebuildIndex(EsActivityCouponInitRequest request) {
        boolean isClear = request.isClearEsIndex();
        boolean isMapping = false;

        if (esBaseService.exists(EsConstants.DOC_COUPON_SCOPE_TYPE) || esBaseService.exists(EsConstants.DOC_ACTIVITY_COUPON_TYPE)) {
            if (isClear) {
                log.info("商品spu->删除索引");
                esBaseService.deleteIndex(EsConstants.DOC_COUPON_SCOPE_TYPE);
                esBaseService.deleteIndex(EsConstants.DOC_ACTIVITY_COUPON_TYPE);
                isMapping = true;
            }
        } else { //主要考虑第一次新增活动优惠券，此时还没有索引的时候
            isMapping = true;
        }

        if (isMapping) {
            //重建商品索引
            esBaseService.existsOrCreate(EsConstants.DOC_COUPON_SCOPE_TYPE, mapping, false);
            esBaseService.existsOrCreate(EsConstants.DOC_ACTIVITY_COUPON_TYPE, activityCouponMapping, false);
        }
    }

    /**
     * 保存优惠券ES数据
     *
     * @param esCouponScopes
     * @return
     */
    public Iterable<EsCouponScope> saveAll(List<EsCouponScopeDTO> esCouponScopes) {
        List<EsCouponScope> scopes = esCouponScopeMapper.couponInfoDTOToEsCouponInfo(esCouponScopes);
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.DOC_COUPON_SCOPE_TYPE, mapping);
        return esCouponScopeRepository.saveAll(scopes);
    }


    /**
     * 根据活动id删除对应ES数据
     *
     * @param activityId
     */
    public void deleteByActivityId(String activityId) {
        esCouponScopeRepository.deleteByActivityId(activityId);
    }


    /**
     * 更新优惠券活动范围部分字段
     *
     * @param request
     */
    public void modifyScope(EsActivityCouponModifyRequest request) {
//        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();
//        updateByQueryRequest.indices(EsConstants.DOC_COUPON_SCOPE_TYPE);
        UpdateQuery updateQuery = esActivityCouponService.fillEsModifyRequest(request);
        try {
//            BulkByScrollResponse bulkByScrollResponse = restHighLevelClient.updateByQuery(updateByQueryRequest,
//                    RequestOptions.DEFAULT);
            ByQueryResponse bulkByScrollResponse = elasticsearchTemplate.updateByQuery(updateQuery,
                    IndexCoordinates.of(EsConstants.DOC_COUPON_SCOPE_TYPE));
            this.failures(bulkByScrollResponse);
        } catch (Exception e) {
            log.error("EsCouponScope更新失败", e);
        }
    }



    private void failures(ByQueryResponse bulkByScrollResponse) {
        List<ByQueryResponse.Failure> bulkFailures = bulkByScrollResponse.getFailures();
        if (CollectionUtils.isEmpty(bulkFailures)) {
            return;
        }
        bulkFailures.forEach(err ->
                log.error("优惠券活动范围更新失败", err.getCause()));
    }


    /**
     * 分页查询ES优惠券信息
     *
     * @param request
     * @return
     */
    public Page<EsCouponScope> page(EsCouponScopePageRequest request) {
        request.setPageSize(DEFAULT_PAGE_SIZE);
        return esBaseService.commonPage(this.getSearchQuery(request), EsCouponScope.class, EsConstants.DOC_COUPON_SCOPE_TYPE);
    }

    private Query getSearchQuery(EsCouponScopePageRequest request) {
        /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        EsActivityCouponCriteriaBuilder bq = KsBeanUtil.convert(request, EsActivityCouponCriteriaBuilder.class);
        builder.withQuery(bq.getWhereCriteria());
        builder.withPageable(request.getPageable());
        builder.withSort(SortBuilders.fieldSort("couponType").order(SortOrder.ASC));
        builder.withSort(SortBuilders.fieldSort("denomination").order(SortOrder.DESC));
        builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.ASC));
        return builder.build();*/
        EsActivityCouponCriteriaBuilder bq = KsBeanUtil.convert(request, EsActivityCouponCriteriaBuilder.class);
        return NativeQuery.builder().withQuery(a -> a.bool(bq.getWhereCriteria()))
                .withPageable(request.getPageable())
                .withSort(SortOptions.of(g -> g.field(a -> a.field("couponType").order(SortOrder.Asc))),
                        SortOptions.of(g -> g.field(a -> a.field("denomination").order(SortOrder.Desc))),
                        SortOptions.of(g -> g.field(a -> a.field("createTime").order(SortOrder.Asc)))).build();
    }


}
