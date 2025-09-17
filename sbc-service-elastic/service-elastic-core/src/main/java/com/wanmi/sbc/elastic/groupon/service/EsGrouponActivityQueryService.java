package com.wanmi.sbc.elastic.groupon.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreNameListByStoreIdsResquest;
import com.wanmi.sbc.customer.bean.vo.StoreNameVO;
import com.wanmi.sbc.elastic.api.request.groupon.EsGrouponActivityPageRequest;
import com.wanmi.sbc.elastic.api.response.groupon.EsGrouponActivityPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.groupon.model.root.EsGrouponActivity;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoBatchByActivityIdAndGoodsIdRequest;
import com.wanmi.sbc.goods.api.response.groupongoodsinfo.GrouponGoodsInfoBatchByActivityIdAndGoodsIdResponse;
import com.wanmi.sbc.goods.bean.dto.GrouponGoodsInfoByActivityIdAndGoodsIdDTO;
import com.wanmi.sbc.goods.bean.vo.GrouponGoodsInfoByActivityIdAndGoodsIdVO;
import com.wanmi.sbc.marketing.api.provider.grouponcate.GrouponCateQueryProvider;
import com.wanmi.sbc.marketing.api.request.grouponcate.GrouponCateByIdsRequest;
import com.wanmi.sbc.marketing.api.response.grouponcate.GrouponCateListResponse;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityForManagerVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponCateVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author: HouShuai
 * @date: 2020/12/8 11:18
 * @description:
 */
@Service
public class EsGrouponActivityQueryService {

    @Autowired
    private GrouponGoodsInfoQueryProvider grouponGoodsInfoQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GrouponCateQueryProvider grouponCateQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    public BaseResponse<EsGrouponActivityPageResponse> page(EsGrouponActivityPageRequest request) {
        //es语法
        BoolQuery boolQuery = this.esCriteria(request);
        NativeQueryBuilder builder = NativeQuery.builder().withQuery(a -> a.bool(boolQuery))
                .withPageable(request.getPageable());
        List<SortOptions> sorts = getSorts(request);
        if(CollectionUtils.isNotEmpty(sorts)) {
            builder = builder.withSort(sorts);
        }
        Page<EsGrouponActivity> esGrouponActivityPage = esBaseService.commonPage(builder.build(),
                EsGrouponActivity.class, EsConstants.GROUPON_ACTIVITY);
        //设置最低拼团价、店铺名称-转换vo
        Page<GrouponActivityForManagerVO> newPage = this.wraperGrouponForManagerVO(esGrouponActivityPage);
        MicroServicePage<GrouponActivityForManagerVO> microPage = new MicroServicePage<>(newPage,
                request.getPageable());
        EsGrouponActivityPageResponse finalRes = new EsGrouponActivityPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }


    private Page<GrouponActivityForManagerVO> wraperGrouponForManagerVO(Page<EsGrouponActivity> grouponActivityPage) {
        List<GrouponGoodsInfoByActivityIdAndGoodsIdDTO> list = new ArrayList<>();
        List<EsGrouponActivity> grouponActivityList = grouponActivityPage.getContent();
        if (CollectionUtils.isEmpty(grouponActivityList)) {
            return new PageImpl<>(Collections.emptyList());
        }
        grouponActivityList.forEach(grouponActivity -> {
            list.add(GrouponGoodsInfoByActivityIdAndGoodsIdDTO.builder()
                    .grouponActivityId(grouponActivity.getGrouponActivityId())
                    .goodsId(grouponActivity.getGoodsId()).build());
        });
        //拼团商品构造查询条件
        GrouponGoodsInfoBatchByActivityIdAndGoodsIdRequest request =
                GrouponGoodsInfoBatchByActivityIdAndGoodsIdRequest.builder().list(list).build();
        BaseResponse<GrouponGoodsInfoBatchByActivityIdAndGoodsIdResponse>
                grouponGoodsInfoResponse = grouponGoodsInfoQueryProvider
                .batchByActivityIdAndGoodsId(request);
        List<GrouponGoodsInfoByActivityIdAndGoodsIdVO> grouponGoodsInfos = grouponGoodsInfoResponse.getContext()
                .getList();

        //根据storeIds查询店铺信息，并塞入值
        List<Long> storeIds = grouponActivityList.stream()
                .filter(d -> d.getStoreId() != null)
                .map(v -> Long.valueOf(v.getStoreId()))
                .collect(Collectors.toList());
        List<StoreNameVO> storeNameList = storeQueryProvider.listStoreNameByStoreIds(new StoreNameListByStoreIdsResquest(storeIds)).getContext()
                .getStoreNameList();

        List<String> grouponCateIds = grouponActivityList.stream()
                .map(EsGrouponActivity::getGrouponCateId)
                .collect(Collectors.toList());

        //获取分类信息
        Map<String, String> mapResult = this.getGrouponCateMap(grouponCateIds);
        //设置最低拼团价\商家名称
        return grouponActivityPage.map(entity -> {
            GrouponActivityForManagerVO vo = this.copyBean(entity);

            this.setPrice(grouponGoodsInfos, entity, vo);

            this.setStoreName(storeNameList, entity, vo);

            if (MapUtils.isNotEmpty(mapResult)) {
                vo.setGrouponCateName(mapResult.get(vo.getGrouponCateId()));
            }
            return vo;
        });
    }

    /**
     * 查询拼团分类信息表
     *
     * @param grouponCateIds
     * @return
     */
    private Map<String, String> getGrouponCateMap(List<String> grouponCateIds) {
        if (CollectionUtils.isEmpty(grouponCateIds)) {
            return Collections.emptyMap();
        }
        GrouponCateByIdsRequest idsRequest = GrouponCateByIdsRequest.builder().grouponCateIds(grouponCateIds).build();
        BaseResponse<GrouponCateListResponse> result = grouponCateQueryProvider.getByIds(idsRequest);
        List<GrouponCateVO> grouponCateVOList = result.getContext().getGrouponCateVOList();
        return grouponCateVOList.stream().collect(Collectors.toMap(GrouponCateVO::getGrouponCateId, GrouponCateVO::getGrouponCateName));
    }

    /**
     * copyBean
     *
     * @param esGrouponActivity
     * @return
     */
    private GrouponActivityForManagerVO copyBean(EsGrouponActivity esGrouponActivity) {
        EsGrouponActivity grouponActivity = Objects.requireNonNull(esGrouponActivity);
        GrouponActivityForManagerVO forManagerVO = new GrouponActivityForManagerVO();
        BeanUtils.copyProperties(grouponActivity, forManagerVO);
        return forManagerVO;
    }


    /**
     * 设置拼团价格
     *
     * @param grouponGoodsInfos
     * @param entity
     * @param vo
     */
    private void setPrice(List<GrouponGoodsInfoByActivityIdAndGoodsIdVO> grouponGoodsInfos,
                          EsGrouponActivity entity, GrouponActivityForManagerVO vo) {
        vo.setGrouponPrice(BigDecimal.ZERO);
        if (CollectionUtils.isNotEmpty(grouponGoodsInfos)) {
            Optional<GrouponGoodsInfoByActivityIdAndGoodsIdVO> grouponGoodsInfoOptional = grouponGoodsInfos.stream()
                    .filter(g -> entity.getGrouponActivityId().equals(g.getGrouponActivityId()))
                    .filter(g -> entity.getGoodsId().equals(g.getGoodsId()))
                    .findFirst();

            grouponGoodsInfoOptional.ifPresent(grouponGoodsInfo -> {
                vo.setGrouponPrice(grouponGoodsInfo.getGrouponPrice());
                vo.setGoodsInfoId(grouponGoodsInfo.getGoosInfoId());
            });
        }
    }

    /**
     * 设置商家名称
     *
     * @param storeNameList
     * @param entity
     * @param vo
     */
    private void setStoreName(List<StoreNameVO> storeNameList, EsGrouponActivity entity, GrouponActivityForManagerVO vo) {
        if (CollectionUtils.isNotEmpty(storeNameList)) {
            Optional<StoreNameVO> storeVoOptional = storeNameList.stream()
                    .filter(g -> entity.getStoreId().equals(String.valueOf(g.getStoreId())))
                    .findFirst();

            storeVoOptional.ifPresent(storeNameVO -> vo.setSupplierName(storeNameVO.getStoreName()));
        }
    }

    /**
     * 排序
     * @return
     */
    public List<SortOptions> getSorts(EsGrouponActivityPageRequest request) {
        List<SortOptions> sortBuilders = new ArrayList<>();
        if(StringUtils.isNotBlank(request.getSortColumn())) {
            sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field(request.getSortColumn())
                            .order(SortOrder.Desc.name().equalsIgnoreCase(request.getSortRole()) ? SortOrder.Desc : SortOrder.Asc))));
            return sortBuilders;
        }
        if(MapUtils.isNotEmpty(request.getSortMap())) {
            request.getSortMap()
                    .forEach((k, v) -> sortBuilders.add(SortOptions.of(a -> a.field(b -> b.field(k)
                            .order(SortOrder.Desc.name().equalsIgnoreCase(v) ? SortOrder.Desc : SortOrder.Asc)))));
        }
        return sortBuilders;
    }


    private BoolQuery esCriteria(EsGrouponActivityPageRequest request) {

//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();
        // 批量查询-id 主键List
        // 批量查询-活动IDList
        if (CollectionUtils.isNotEmpty(request.getGrouponActivityIdList())) {
//            bool.must(QueryBuilders.termsQuery("grouponActivityId", request.getGrouponActivityIdList()));
            bool.must(terms(g -> g.field("customerId").terms(t -> t.value(request.getGrouponActivityIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 活动ID
        if (StringUtils.isNotEmpty(request.getGrouponActivityId())) {
//            bool.must(QueryBuilders.termQuery("grouponActivityId", request.getGrouponActivityId()));
            bool.must(term(g -> g.field("grouponActivityId").value(request.getGrouponActivityId())));
        }

        // 拼团人数
        if (request.getGrouponNum() != null) {
//            bool.must(QueryBuilders.termQuery("grouponNum", request.getGrouponNum()));
            bool.must(term(g -> g.field("grouponNum").value(request.getGrouponNum())));
        }

        // 大于或等于 搜索条件:开始时间开始
        if (request.getStartTimeBegin() != null) {
//            bool.must(QueryBuilders.rangeQuery("startTime").gte(DateUtil.format(request.getStartTimeBegin(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("startTime")
                    .gte(JsonData.of(DateUtil.format(request.getStartTimeBegin(), DateUtil.FMT_TIME_4)))));
        }
        // 小于或等于 搜索条件:开始时间截止
        if (request.getStartTimeEnd() != null) {
//            bool.must(QueryBuilders.rangeQuery("startTime").lte(DateUtil.format(request.getStartTimeEnd(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("startTime")
                    .lte(JsonData.of(DateUtil.format(request.getStartTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        // 大于或等于 搜索条件:结束时间开始
        if (request.getEndTimeBegin() != null) {
//            bool.must(QueryBuilders.rangeQuery("endTime").gte(DateUtil.format(request.getEndTimeBegin(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("endTime")
                    .gte(JsonData.of(DateUtil.format(request.getEndTimeBegin(), DateUtil.FMT_TIME_4)))));
        }
        // 小于或等于 搜索条件:结束时间截止
        if (request.getEndTimeEnd() != null) {
//            bool.must(QueryBuilders.rangeQuery("endTime").lte(DateUtil.format(request.getEndTimeEnd(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("endTime")
                    .lte(JsonData.of(DateUtil.format(request.getEndTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        //拼团活动开始时间
        if (Objects.nonNull(request.getStartTime())) {
//            bool.must(QueryBuilders.rangeQuery("startTime").gte(DateUtil.format(request.getStartTime(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("startTime")
                    .gte(JsonData.of(DateUtil.format(request.getStartTime(), DateUtil.FMT_TIME_4)))));
        }

        //拼团活动结束时间
        if (Objects.nonNull(request.getEndTime())) {
//            bool.must(QueryBuilders.rangeQuery("endTime").lte(DateUtil.format(request.getEndTime(), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("endTime")
                    .lte(JsonData.of(DateUtil.format(request.getEndTime(), DateUtil.FMT_TIME_4)))));
        }

        // 模糊查询 - 拼团分类ID
        if (StringUtils.isNotEmpty(request.getGrouponCateId())) {
//            bool.must(QueryBuilders.wildcardQuery("grouponCateId", "*" + request.getGrouponCateId() + "*"));
            bool.must(wildcard(g -> g.field("grouponCateId").value("*" + request.getGrouponCateId() + "*")));
        }

        // 是否自动成团，0：否，1：是
        if (Objects.nonNull(request.getAutoGroupon())) {
//            bool.must(QueryBuilders.termQuery("autoGroupon", request.getAutoGroupon()));
            bool.must(term(g -> g.field("autoGroupon").value(request.getAutoGroupon())));
        }

        // 是否包邮，0：否，1：是
        if (Objects.nonNull(request.getFreeDelivery())) {
//            bool.must(QueryBuilders.termQuery("freeDelivery", request.getFreeDelivery()));
            bool.must(term(g -> g.field("freeDelivery").value(request.getFreeDelivery())));
        }

        // 模糊查询 - spu编号
        if (StringUtils.isNotEmpty(request.getGoodsId())) {
//            bool.must(QueryBuilders.wildcardQuery("goodsId", "*" + request.getGoodsId() + "*"));
            bool.must(wildcard(g -> g.field("goodsId").value("*" + request.getGoodsId() + "*")));
        }

        //模糊查询 - spu编码
        if (StringUtils.isNotEmpty(request.getGoodsNo())) {
//            bool.must(QueryBuilders.wildcardQuery("goodsNo", "*" + request.getGoodsNo() + "*"));
            bool.must(wildcard(g -> g.field("goodsNo").value("*" + request.getGoodsNo() + "*")));
        }

        // 模糊查询 - spu商品名称
        if (StringUtils.isNotEmpty(request.getGoodsName())) {
//            bool.must(QueryBuilders.wildcardQuery("goodsName", "*" + request.getGoodsName() + "*"));
            bool.must(wildcard(g -> g.field("goodsName").value("*" + request.getGoodsName() + "*")));
        }

        // 模糊查询 - 店铺ID
        if (StringUtils.isNotEmpty(request.getStoreId())) {
//            bool.must(QueryBuilders.termQuery("storeId", request.getStoreId()));
            bool.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }

        // 是否精选，0：否，1：是
        if (Objects.nonNull(request.getSticky())) {
//            bool.must(QueryBuilders.termQuery("sticky", request.getSticky()));
            bool.must(term(g -> g.field("sticky").value(request.getSticky())));
        }

        // 是否审核通过，0：待审核，1：审核通过，2：审核不通过
        if (Objects.nonNull(request.getAuditStatus())) {
//            bool.must(QueryBuilders.termQuery("auditStatus", request.getAuditStatus().toValue()));
            bool.must(term(g -> g.field("auditStatus").value(request.getAuditStatus().toValue())));
        }


        // 是否删除，0：否，1：是
        if (Objects.nonNull(request.getDelFlag())) {
//            bool.must(QueryBuilders.termQuery("delFlag", request.getDelFlag().toValue()));
            bool.must(term(g -> g.field("delFlag").value(request.getDelFlag().toValue())));
        }

        if (Objects.nonNull(request.getTabType())) {
            switch (request.getTabType()) {
                //进行中
                case STARTED:
                    /*bool.must(QueryBuilders.rangeQuery("startTime").to(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    bool.must(QueryBuilders.rangeQuery("endTime").from(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    bool.must(QueryBuilders.termQuery("auditStatus", AuditStatus.CHECKED.toValue()));*/
                    bool.must(range(g -> g.field("startTime").to(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4))));
                    bool.must(range(g -> g.field("endTime").from(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4))));
                    bool.must(term(g -> g.field("auditStatus").value(AuditStatus.CHECKED.toValue())));
                    break;
                //未生效
                case NOT_START:
//                    bool.must(QueryBuilders.rangeQuery("startTime").from(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4),false));
//                    bool.must(QueryBuilders.termQuery("auditStatus", AuditStatus.CHECKED.toValue()));
                    bool.must(range(g -> g.field("startTime").from(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4))));
                    bool.must(term(g -> g.field("auditStatus").value(AuditStatus.CHECKED.toValue())));
                    break;
                //已结束
                case ENDED:
//                    bool.must(QueryBuilders.rangeQuery("endTime").to(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4),false));
//                    bool.must(QueryBuilders.termQuery("auditStatus", AuditStatus.CHECKED.toValue()));
                    bool.must(range(g -> g.field("endTime").to(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4))));
                    bool.must(term(g -> g.field("auditStatus").value(AuditStatus.CHECKED.toValue())));
                    break;
                //待审核
                case WAIT_CHECK:
//                    bool.must(QueryBuilders.termQuery("auditStatus", AuditStatus.WAIT_CHECK.toValue()));
                    bool.must(term(g -> g.field("auditStatus").value(AuditStatus.WAIT_CHECK.toValue())));
                    break;
                //审核失败
                case NOT_PASS:
//                    bool.must(QueryBuilders.termQuery("auditStatus", AuditStatus.NOT_PASS.toValue()));
                    bool.must(term(g -> g.field("auditStatus").value(AuditStatus.NOT_PASS.toValue())));
                    break;
                case WILL_AND_ALREADY_START:
                    /*bool.must(QueryBuilders.rangeQuery("endTime").from(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4)));
                    bool.must(QueryBuilders.termQuery("auditStatus", AuditStatus.CHECKED.toValue()));*/
                    bool.must(range(g -> g.field("endTime").from(DateUtil.format(LocalDateTime.now(), DateUtil.FMT_TIME_4))));
                    bool.must(term(g -> g.field("auditStatus").value(AuditStatus.CHECKED.toValue())));
                    break;
                default:
                    break;
            }
        }

        // 批量查询-spuIDList
        if (CollectionUtils.isNotEmpty(request.getSpuIdList())) {
//            bool.must(QueryBuilders.termsQuery("goodsId", request.getSpuIdList()));
            bool.must(terms(g -> g.field("goodsId").terms(t -> t.value(request.getSpuIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        //商品类型
        if (request.getGoodsType() != null) {
//            bool.must(QueryBuilders.termQuery("goodsType", request.getGoodsType()));
            bool.must(term(g -> g.field("goodsType").value(request.getGoodsType())));
        }
        return bool.build();
    }
}
