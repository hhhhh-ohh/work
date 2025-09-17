package com.wanmi.sbc.elastic.customer.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelByIdsRequest;
import com.wanmi.sbc.customer.api.request.quicklogin.ThirdLoginRelationByCustomerRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelBaseResponse;
import com.wanmi.sbc.customer.api.response.quicklogin.ThirdLoginRelationResponse;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerBatchModifyRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerPageRequest;
import com.wanmi.sbc.elastic.api.response.customer.DistributionCustomerExportResponse;
import com.wanmi.sbc.elastic.api.response.customer.EsDistributionCustomerListResponse;
import com.wanmi.sbc.elastic.api.response.customer.EsDistributionCustomerPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.customer.EsDistributionCustomerVO;
import com.wanmi.sbc.elastic.customer.model.root.EsDistributionCustomer;
import com.wanmi.sbc.elastic.customer.repository.EsDistributionCustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author: HouShuai
 * @date: 2020/12/7 11:29
 * @description: 分销员查询
 */
@Slf4j
@Service
public class EsDistributionCustomerQueryService {

    @Autowired
    private EsDistributionCustomerRepository esDistributionCustomerRepository;

    @Autowired
    private DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired
    private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    /**
     * 查询分销员列表
     *
     * @param request
     * @return
     */
    public EsDistributionCustomerPageResponse page(EsDistributionCustomerPageRequest request) {

        NativeQuery searchQuery = this.esCriteria(request);

        Page<EsDistributionCustomer> esDistributionCustomerPage = esBaseService.commonPage(searchQuery,
                EsDistributionCustomer.class, EsConstants.DISTRIBUTION_CUSTOMER);
        List<EsDistributionCustomer> esDistributionCustomerList = esDistributionCustomerPage.getContent();

        if (CollectionUtils.isEmpty(esDistributionCustomerList)) {
            return EsDistributionCustomerPageResponse.builder()
                    .distributionCustomerVOPage(new MicroServicePage<>())
                    .build();
        }
        Map<String, String> distributorLevelMap = this.getDistributorLevelMap(esDistributionCustomerList);
        Page<EsDistributionCustomerVO> newPage = esDistributionCustomerPage.map(entity -> {
            EsDistributionCustomerVO esDistributionCustomerVO = EsDistributionCustomerVO.builder().build();
            BeanUtils.copyProperties(entity, esDistributionCustomerVO);
            if (MapUtils.isNotEmpty(distributorLevelMap)) {
                String distributorLevelName = distributorLevelMap.get(esDistributionCustomerVO.getDistributorLevelId());
                esDistributionCustomerVO.setDistributorLevelName(distributorLevelName);
            }
            return esDistributionCustomerVO;
        });

        MicroServicePage<EsDistributionCustomerVO> microServicePage = new MicroServicePage<>(newPage, request.getPageable());

        return EsDistributionCustomerPageResponse.builder()
                .distributionCustomerVOPage(microServicePage)
                .build();
    }

    /**
     * 导出分销员信息
     *
     * @param request
     * @return
     */
    public BaseResponse<DistributionCustomerExportResponse> export(EsDistributionCustomerPageRequest request) {

        //分批取出数据
        List<DistributionCustomerVO> dataRecords = this.getDataRecords(request);

        List<DistributorLevelVO> distributorLevelList = distributorLevelQueryProvider.listAll().getContext().getDistributorLevelList();
        Map<String, String> map = distributorLevelList.stream().collect(Collectors.toMap(DistributorLevelVO::getDistributorLevelId, DistributorLevelVO::getDistributorLevelName));
        List<DistributionCustomerVO> dataRecordList = dataRecords.stream()
                .peek(distributionCustomerVO -> distributionCustomerVO.setDistributorLevelName(map.get(distributionCustomerVO.getDistributorLevelId())))
                .collect(Collectors.toList());
        DistributionCustomerExportResponse finalRes = new DistributionCustomerExportResponse(dataRecordList);
        return BaseResponse.success(finalRes);
    }

    /**
     * @description
     * @author  xuyunpeng
     * @date 2021/6/7 3:49 下午
     * @param request
     * @return
     */
    public Long count(EsDistributionCustomerPageRequest request) {
        NativeQuery searchQuery = this.esCriteria(request);
        Page<EsDistributionCustomer> esDistributionCustomerPage = esBaseService.commonPage(searchQuery,
                EsDistributionCustomer.class, EsConstants.DISTRIBUTION_CUSTOMER);
        return esDistributionCustomerPage.getTotalElements();
    }


    /**
     * 分批取数据
     *
     * @param request
     * @return
     */
    private List<DistributionCustomerVO> getDataRecords(EsDistributionCustomerPageRequest request) {
        NativeQuery searchQuery = this.esCriteria(request);
        Page<EsDistributionCustomer> esDistributionCustomerPage = esBaseService.commonPage(searchQuery,
                EsDistributionCustomer.class, EsConstants.DISTRIBUTION_CUSTOMER);
        Page<DistributionCustomerVO> newPage = this.copyPage(esDistributionCustomerPage);
        return newPage.getContent();
    }


    /**
     * 根据分销员id，查询分销员信息
     *
     * @param queryRequest
     * @return
     */
    public BaseResponse<EsDistributionCustomerListResponse> listByIds(EsDistributionCustomerBatchModifyRequest queryRequest) {
        List<String> distributionIds = queryRequest.getDistributionIds();
        List<EsDistributionCustomer> esDistributionCustomerList = esDistributionCustomerRepository.findByDistributionIdIn(distributionIds);
        List<DistributionCustomerShowPhoneVO> esDistributionCustomerVOList = this.copyBeanList(esDistributionCustomerList);
        EsDistributionCustomerListResponse response = EsDistributionCustomerListResponse.builder().list(esDistributionCustomerVOList).build();
        return BaseResponse.success(response);
    }


    /**
     * EsDistributionCustomer转DistributionCustomerVO
     *
     * @param esDistributionCustomerPage
     * @return
     */
    private Page<DistributionCustomerVO> copyPage(Page<EsDistributionCustomer> esDistributionCustomerPage) {
        return esDistributionCustomerPage.map(entity -> {
            DistributionCustomerVO distributionCustomerVO = new DistributionCustomerVO();
            BeanUtils.copyProperties(entity, distributionCustomerVO);
            ThirdLoginRelationByCustomerRequest relationByCustomerRequest = ThirdLoginRelationByCustomerRequest.builder()
                    .customerId(entity.getCustomerId())
                    .thirdLoginType(ThirdLoginType.WECHAT)
                    .build();
            BaseResponse<ThirdLoginRelationResponse> thirdLoginRelationResponseBaseResponse = thirdLoginRelationQueryProvider.listThirdLoginRelationByCustomer(relationByCustomerRequest);
            ThirdLoginRelationVO thirdLoginRelation = thirdLoginRelationResponseBaseResponse.getContext().getThirdLoginRelation();
            if (Objects.nonNull(thirdLoginRelation)) {
                distributionCustomerVO.setHeadImg(thirdLoginRelation.getHeadimgurl());
            }
            return distributionCustomerVO;
        });
    }


    /**
     * 获取分销员等级信息
     *
     * @param esDistributionCustomerList
     * @return
     */
    private Map<String, String> getDistributorLevelMap(List<EsDistributionCustomer> esDistributionCustomerList) {
        if (CollectionUtils.isEmpty(esDistributionCustomerList)) {
            return Collections.emptyMap();
        }
        //取出分销员等级id
        List<String> idList = esDistributionCustomerList.stream()
                .map(EsDistributionCustomer::getDistributorLevelId)
                .collect(Collectors.toList());

        DistributorLevelByIdsRequest idsRequest = DistributorLevelByIdsRequest.builder().idList(idList).build();
        BaseResponse<DistributorLevelBaseResponse> listByIds = distributorLevelQueryProvider.listByIds(idsRequest);
        List<DistributorLevelBaseVO> list = listByIds.getContext().getList();
        return Optional.ofNullable(list).orElseGet(Collections::emptyList)
                .stream().collect(Collectors.toMap(DistributorLevelBaseVO::getDistributorLevelId,
                        DistributorLevelBaseVO::getDistributorLevelName));

    }

    /**
     * EsDistributionCustomer转 DistributionCustomerShowPhoneVO
     *
     * @param esDistributionCustomerList
     * @return
     */
    private List<DistributionCustomerShowPhoneVO> copyBeanList(List<EsDistributionCustomer> esDistributionCustomerList) {

        return Optional.ofNullable(esDistributionCustomerList)
                .orElseGet(Collections::emptyList).stream()
                .map(entity -> {
                    DistributionCustomerShowPhoneVO distributionCustomerVO = DistributionCustomerShowPhoneVO.builder().build();
                    BeanUtils.copyProperties(entity, distributionCustomerVO);
                    return distributionCustomerVO;
                }).collect(Collectors.toList());
    }

    /**
     * 分销员列表查询条件
     *
     * @return
     */
    public NativeQuery esCriteria(EsDistributionCustomerPageRequest request) {

//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();

        if (CollectionUtils.isNotEmpty(request.getDistributionIdList())) {
//            bool.must(QueryBuilders.termsQuery("distributionId", request.getDistributionIdList()));
            bool.must(terms(g -> g.field("distributionId").terms(t -> t.value(request.getDistributionIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 分销员等级ID
        if (StringUtils.isNotEmpty(request.getDistributorLevelId())) {
//            bool.must(QueryBuilders.termQuery("distributorLevelId", request.getDistributorLevelId()));
            bool.must(term(g -> g.field("distributorLevelId").value(request.getDistributorLevelId())));
        }

        // 分销员标识UUID
        if (StringUtils.isNotEmpty(request.getDistributionId())) {
//            bool.must(QueryBuilders.termQuery("distributionId", request.getDistributionId()));
            bool.must(term(g -> g.field("distributionId").value(request.getDistributionId())));
        }

        // 模糊查询 - 会员ID
        if (StringUtils.isNotEmpty(request.getCustomerId())) {
//            bool.must(QueryBuilders.wildcardQuery("customerId", "*" + request.getCustomerId() + "*"));
            bool.must(wildcard(g -> g.field("customerId").value("*" + request.getCustomerId() + "*")));
        }

        // 模糊查询 - 会员名称
        if (StringUtils.isNotEmpty(request.getCustomerName())) {
//            bool.must(QueryBuilders.wildcardQuery("customerName", "*" + request.getCustomerName() + "*"));
            bool.must(wildcard(g -> g.field("customerName").value("*" + request.getCustomerName() + "*")));
        }

        // 模糊查询 - 会员登录账号|手机号
        if (StringUtils.isNotEmpty(request.getCustomerAccount())) {
//            bool.must(QueryBuilders.wildcardQuery("customerAccount", "*" + request.getCustomerAccount() + "*"));
            bool.must(wildcard(g -> g.field("customerAccount").value("*" + request.getCustomerAccount() + "*")));
        }

        // 模糊查询 - 会员ID
        if (CollectionUtils.isNotEmpty(request.getEmployeeCustomerIds())) {
//            bool.must(QueryBuilders.termsQuery("customerId", request.getEmployeeCustomerIds()));
            bool.must(terms(g -> g.field("customerId").terms(t -> t.value(request.getEmployeeCustomerIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 大于或等于 搜索条件:创建时间开始
        // 小于或等于 搜索条件:创建时间截止
        if (ObjectUtils.allNotNull(request.getCreateTimeEnd(), request.getCreateTimeEnd())) {
            /*bool.must(QueryBuilders.rangeQuery("createTime")
                    .gte(DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("createTime")
                    .gte(JsonData.of(DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        // 是否删除标志 0：否，1：是
        if (Objects.nonNull(request.getDelFlag())) {
//            bool.must(QueryBuilders.termQuery("delFlag", request.getDelFlag().toValue()));
            bool.must(term(g -> g.field("delFlag").value(request.getDelFlag().toValue())));
        }

        // 是否禁止分销 0: 启用中  1：禁用中
        if (Objects.nonNull(request.getForbiddenFlag())) {
//            bool.must(QueryBuilders.termQuery("forbiddenFlag", request.getForbiddenFlag().toValue()));
            bool.must(term(g -> g.field("forbiddenFlag").value(request.getForbiddenFlag().toValue())));
        }

        // 是否有分销员资格0：否，1：是
        if (Objects.nonNull(request.getDistributorFlag())) {
//            bool.must(QueryBuilders.termQuery("distributorFlag", request.getDistributorFlag().toValue()));
            bool.must(term(g -> g.field("distributorFlag").value(request.getDistributorFlag().toValue())));
        }

        // 邀新人数-从
        if (Objects.nonNull(request.getInviteCountStart()) && request.getInviteCountStart().compareTo(0) > 0) {
//            bool.must(QueryBuilders.rangeQuery("inviteCount").gt(request.getInviteCountStart()));
            bool.must(range(a -> a.field("inviteCount").gt(JsonData.of(request.getInviteCountStart()))));
        }

        // 邀新人数-至
        if (Objects.nonNull(request.getInviteCountEnd()) && request.getInviteCountEnd().compareTo(0) > 0) {
//            bool.must(QueryBuilders.rangeQuery("inviteCount").lt(request.getInviteCountEnd()));
            bool.must(range(a -> a.field("inviteCount").lt(JsonData.of(request.getInviteCountEnd()))));
        }

        // 有效邀新人数-从
        if (Objects.nonNull(request.getInviteAvailableCountStart()) && request.getInviteAvailableCountStart().compareTo(0) > 0) {
//            bool.must(QueryBuilders.rangeQuery("inviteAvailableCount").gt(request.getInviteAvailableCountStart()));
            bool.must(range(a -> a.field("inviteAvailableCount").gt(JsonData.of(request.getInviteAvailableCountStart()))));
        }

        // 有效邀新人数-至
        if (Objects.nonNull(request.getInviteAvailableCountEnd()) && request.getInviteAvailableCountEnd().compareTo(0) > 0) {
//            bool.must(QueryBuilders.rangeQuery("inviteAvailableCount").lt(request.getInviteAvailableCountEnd()));
            bool.must(range(a -> a.field("inviteAvailableCount").lt(JsonData.of(request.getInviteAvailableCountEnd()))));
        }

        // 邀新奖金(元)-从
        if (Objects.nonNull(request.getRewardCashStart()) && request.getRewardCashStart().compareTo(BigDecimal.ZERO) > 0) {
//            bool.must(QueryBuilders.rangeQuery("rewardCash").gt(request.getRewardCashStart().doubleValue()));
            bool.must(range(a -> a.field("rewardCash").gt(JsonData.of(request.getRewardCashStart().doubleValue()))));
        }

        // 邀新奖金(元)-至
        if (Objects.nonNull(request.getRewardCashEnd()) && request.getRewardCashEnd().compareTo(BigDecimal.ZERO) > 0) {
//            bool.must(QueryBuilders.rangeQuery("rewardCash").lt(request.getRewardCashEnd().doubleValue()));
            bool.must(range(a -> a.field("rewardCash").lt(JsonData.of(request.getRewardCashEnd().doubleValue()))));
        }

        // 分销订单(笔)-从
        if (Objects.nonNull(request.getDistributionTradeCountStart()) && request.getDistributionTradeCountStart().compareTo(0) >= 0) {
//            bool.must(QueryBuilders.rangeQuery("distributionTradeCount").gte(request.getDistributionTradeCountStart()));
            bool.must(range(a -> a.field("distributionTradeCount").gte(JsonData.of(request.getDistributionTradeCountStart()))));
        }

        // 分销订单(笔)-至
        if (Objects.nonNull(request.getDistributionTradeCountEnd()) && request.getDistributionTradeCountEnd().compareTo(0) >= 0) {
//            bool.must(QueryBuilders.rangeQuery("distributionTradeCount").lte(request.getDistributionTradeCountEnd()));
            bool.must(range(a -> a.field("distributionTradeCount").lte(JsonData.of(request.getDistributionTradeCountEnd()))));
        }

        // 销售额(元)-从
        if (Objects.nonNull(request.getSalesStart()) && request.getSalesStart().compareTo(BigDecimal.ZERO) >= 0) {
//            bool.must(QueryBuilders.rangeQuery("sales").gte(request.getSalesStart().doubleValue()));
            bool.must(range(a -> a.field("sales").gte(JsonData.of(request.getSalesStart().doubleValue()))));
        }

        // 销售额(元)-至
        if (Objects.nonNull(request.getSalesEnd()) && request.getSalesEnd().compareTo(BigDecimal.ZERO) >= 0) {
//            bool.must(QueryBuilders.rangeQuery("sales").lte(request.getSalesEnd().doubleValue()));
            bool.must(range(a -> a.field("sales").lte(JsonData.of(request.getSalesEnd().doubleValue()))));
        }

        // 分销佣金(元)-从
        if (Objects.nonNull(request.getCommissionStart()) && request.getCommissionStart().compareTo(BigDecimal.ZERO) > 0) {
//            bool.must(QueryBuilders.rangeQuery("commission").gt(request.getCommissionStart().doubleValue()));
            bool.must(range(a -> a.field("commission").gt(JsonData.of(request.getCommissionStart().doubleValue()))));
        }

        // 分销佣金(元)-至
        if (Objects.nonNull(request.getCommissionEnd()) && request.getCommissionEnd().compareTo(BigDecimal.ZERO) > 0) {
//            bool.must(QueryBuilders.rangeQuery("commission").lt(request.getCommissionEnd().doubleValue()));
            bool.must(range(a -> a.field("commission").lt(JsonData.of(request.getCommissionEnd().doubleValue()))));
        }

        /*SortOrder sortOrder = StringUtils.equals(request.getSortRole(), "asc") ? SortOrder.ASC : SortOrder.DESC;
        FieldSortBuilder order = SortBuilders.fieldSort(request.getSortColumn()).order(sortOrder);
        return new NativeSearchQueryBuilder()
                .withQuery(bool)
                .withPageable(request.getPageable())
                .withSort(order)
                .build();*/
        SortOrder sortOrder = StringUtils.equalsIgnoreCase(request.getSortRole(), "asc") ? SortOrder.Asc : SortOrder.Desc;
        return NativeQuery.builder()
                .withQuery(g -> g.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(a -> a.field(b-> b.field(request.getSortColumn()).order(sortOrder)))
                .build();
    }
}
