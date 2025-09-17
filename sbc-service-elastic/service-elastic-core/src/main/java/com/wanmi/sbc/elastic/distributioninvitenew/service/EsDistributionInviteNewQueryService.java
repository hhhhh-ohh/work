package com.wanmi.sbc.elastic.distributioninvitenew.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetCustomerIdResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewPageRequest;
import com.wanmi.sbc.elastic.api.response.distributioninvitenew.EsDistributionInviteNewPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.distributioninvitenew.model.root.EsInviteNewRecord;
import com.wanmi.sbc.elastic.distributioninvitenew.repository.EsDistributionInviteNewRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author houshuai
 * @date 2021/1/6 14:29
 * @description <p> 邀新记录查询Service </p>
 */
@Service
public class EsDistributionInviteNewQueryService {

    @Autowired
    private EsDistributionInviteNewRepository esDistributionInviteNewRepository;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private EsBaseService esBaseService;
    /**
     * 分页查询邀新记录
     *
     * @param request
     * @return
     */
    public BaseResponse<EsDistributionInviteNewPageResponse> findDistributionInviteNewRecord(EsDistributionInviteNewPageRequest request) {
        NativeQuery searchQuery = this.searchQuery(request);
        // 设置返回精准数量，以支持用户在页面上看到1W+数据
        searchQuery.setTrackTotalHits(Boolean.TRUE);
        Page<EsInviteNewRecord> esInviteNewRecordPage = esBaseService.commonPage(searchQuery,
                EsInviteNewRecord.class, EsConstants.INVITE_NEW_RECORD);
        return BaseResponse.success(this.pageHelper(esInviteNewRecordPage, request.getPageNum()));
    }

    /**
     * 填充返回信息
     *
     * @param inviteNewRecords
     * @param pageNum
     * @return
     */
    private EsDistributionInviteNewPageResponse pageHelper(Page<EsInviteNewRecord> inviteNewRecords, int pageNum) {
        EsDistributionInviteNewPageResponse distributionInviteNewPageResponse = new EsDistributionInviteNewPageResponse();
        distributionInviteNewPageResponse.setCurrentPage(pageNum);

        //结果为空
        if (CollectionUtils.isEmpty(inviteNewRecords.getContent())) {
            distributionInviteNewPageResponse.setRecordList(Collections.emptyList());
            distributionInviteNewPageResponse.setTotal(0L);
            return distributionInviteNewPageResponse;
        }

        //先把属性值复制到CustomerDetailResponse对象里
        List<DistributionInviteNewForPageVO> distributionInviteNewForPageVOList = inviteNewRecords.getContent().stream()
                .map(inviteNewRecord -> {
                    DistributionInviteNewForPageVO distributionInviteNewForPageVO = new DistributionInviteNewForPageVO();
                    //对象拷贝
                    BeanUtils.copyProperties(inviteNewRecord, distributionInviteNewForPageVO);

                    //填充受邀人的名称和账号
                    if (StringUtils.isNotBlank(inviteNewRecord.getInvitedNewCustomerId())) {
                        CustomerGetByIdRequest request = new CustomerGetByIdRequest(inviteNewRecord.getInvitedNewCustomerId());
                        CustomerGetByIdResponse customerResponse = customerQueryProvider.getCustomerInfoById(request).getContext();
                        if (StringUtils.isNotBlank(customerResponse.getCustomerId())) {
                            CustomerDetailByCustomerIdRequest customerDetailByRequest = new CustomerDetailByCustomerIdRequest(customerResponse.getCustomerId());
                            CustomerDetailGetCustomerIdResponse customerDetailResponse = customerDetailQueryProvider.getCustomerDetailByCustomerId(customerDetailByRequest).getContext();
                            distributionInviteNewForPageVO.setInvitedNewCustomerName(customerDetailResponse.getCustomerName());
                            distributionInviteNewForPageVO.setInvitedNewCustomerAccount(customerResponse.getCustomerAccount());
                        }
                        distributionInviteNewForPageVO.setInvitedNewCustomerHeadImg(customerResponse.getHeadImg());
                    }

                    //填充分销员的名称和账号
                    if (StringUtils.isNotBlank(inviteNewRecord.getRequestCustomerId())) {
                        CustomerGetByIdRequest request = new CustomerGetByIdRequest(inviteNewRecord.getRequestCustomerId());
                        CustomerGetByIdResponse customerResponse = customerQueryProvider.getCustomerInfoById(request).getContext();
                        if (StringUtils.isNotBlank(customerResponse.getCustomerId())) {
                            CustomerDetailByCustomerIdRequest customerDetailByRequest = new CustomerDetailByCustomerIdRequest(customerResponse.getCustomerId());
                            CustomerDetailGetCustomerIdResponse customerDetailResponse = customerDetailQueryProvider.getCustomerDetailByCustomerId(customerDetailByRequest).getContext();
                            distributionInviteNewForPageVO.setRequestCustomerName(customerDetailResponse.getCustomerName());
                            distributionInviteNewForPageVO.setRequestCustomerAccount(customerResponse.getCustomerAccount());
                        }
                        distributionInviteNewForPageVO.setRequestCustomerHeadImg(customerResponse.getHeadImg());
                    }
                    return distributionInviteNewForPageVO;
                }).collect(Collectors.toList());


        distributionInviteNewPageResponse.setTotal(inviteNewRecords.getTotalElements());
        distributionInviteNewPageResponse.setRecordList(distributionInviteNewForPageVOList);
        return distributionInviteNewPageResponse;
    }

    /**
     * 查询条件
     * @return  NativeSearchQuery
     */
    private NativeQuery searchQuery(EsDistributionInviteNewPageRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();
        // 支持参数批量邀新记录表主键：idList
        if (CollectionUtils.isNotEmpty(request.getIdList())) {
            request.setRecordIdList(request.getIdList());
        }
        // 批量查询-邀新记录表主键List
        if (CollectionUtils.isNotEmpty(request.getRecordIdList())) {
//            bool.must(QueryBuilders.termsQuery("recordId", request.getRecordIdList()));
            bool.must(terms(g -> g.field("recordId").terms(v -> v.value(request.getRecordIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }
        //奖励是否入账
        if (null != request.getIsRewardRecorded()) {
//            bool.must(QueryBuilders.termQuery("rewardRecorded", request.getIsRewardRecorded().toValue()));
            bool.must(term(g -> g.field("rewardRecorded").value(request.getIsRewardRecorded().toValue())));
        }
        //入账类型
        if (null != request.getRewardFlag()) {
//            bool.must(QueryBuilders.termQuery("rewardFlag", request.getRewardFlag().toValue()));
            bool.must(term(g -> g.field("rewardFlag").value(request.getRewardFlag().toValue())));
        }
        //未入账原因
        if (null != request.getFailReasonFlag()) {
//            bool.must(QueryBuilders.termQuery("failReasonFlag", request.getFailReasonFlag().toValue()));
            bool.must(term(g -> g.field("failReasonFlag").value(request.getFailReasonFlag().toValue())));
        }
        // 是否有效邀新
        if (null != request.getAvailableDistribution()) {
//            bool.must(QueryBuilders.termQuery("availableDistribution", request.getAvailableDistribution().toValue()));
            bool.must(term(g -> g.field("availableDistribution").value(request.getAvailableDistribution().toValue())));
        }

        //是否分销员(空：全部，0：否，1：是 )
        if (StringUtils.isNotBlank(request.getIsDistributor())) {
//            bool.must(QueryBuilders.termQuery("distributor", Integer.parseInt(request.getIsDistributor())));
            bool.must(term(g -> g.field("distributor").value(Integer.parseInt(request.getIsDistributor()))));
        }

        //受邀人ID
        if (StringUtils.isNotBlank(request.getInvitedNewCustomerId())) {
//            bool.must(QueryBuilders.termQuery("invitedNewCustomerId", request.getInvitedNewCustomerId()));
            bool.must(term(g -> g.field("invitedNewCustomerId").value(request.getInvitedNewCustomerId())));
        }

        //邀请人ID
        if (StringUtils.isNotBlank(request.getRequestCustomerId())) {
//            bool.must(QueryBuilders.termQuery("requestCustomerId", request.getRequestCustomerId()));
            bool.must(term(g -> g.field("requestCustomerId").value(request.getRequestCustomerId())));
        }

        //订单编号
        if (StringUtils.isNotBlank(request.getOrderCode())) {
//            bool.must(QueryBuilders.wildcardQuery("orderCode", "*" + request.getOrderCode() + "*"));
            bool.must(wildcard(g -> g.field("orderCode").value("*" + request.getOrderCode() + "*")));
        }

        //下单开始时间
        //下单结束时间
        if (StringUtils.isNoneBlank(request.getFirstOrderStartTime(), request.getFirstOrderEndTime())) {
            /*bool.must(QueryBuilders.rangeQuery("firstOrderTime")
                    .gte(DateUtil.format(DateUtil.parseDay(request.getFirstOrderStartTime()), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(DateUtil.parseDay(request.getFirstOrderEndTime()), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("firstOrderTime")
                    .gte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getFirstOrderStartTime()), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getFirstOrderEndTime()), DateUtil.FMT_TIME_4)))));
        }

        //订单完成开始时间
        //订单完成结束时间
        if (StringUtils.isNoneBlank(request.getOrderFinishStartTime(), request.getOrderFinishEndTime())) {
//            bool.must(QueryBuilders.rangeQuery("orderFinishTime")
//                    .gte(DateUtil.format(DateUtil.parseDay(request.getOrderFinishStartTime()), DateUtil.FMT_TIME_4))
//                    .lte(DateUtil.format(DateUtil.parseDay(request.getOrderFinishEndTime()), DateUtil.FMT_TIME_4)));
            bool.must(range(g -> g.field("orderFinishTime")
                    .gte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getOrderFinishStartTime()), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getOrderFinishEndTime()), DateUtil.FMT_TIME_4)))));
        }

        //入账开始时间
        //入账结束时间
        if (StringUtils.isNoneBlank(request.getRewardRecordedStartTime(), request.getRewardRecordedEndTime())) {
            /*bool.must(QueryBuilders.rangeQuery("rewardRecordedTime")
                    .gte(DateUtil.format(DateUtil.parseDay(request.getRewardRecordedStartTime()), DateUtil.FMT_TIME_4))
                    .lte(DateUtil.format(DateUtil.parseDay(request.getRewardRecordedEndTime()), DateUtil.FMT_TIME_4)));*/
            bool.must(range(g -> g.field("rewardRecordedTime")
                    .gte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getRewardRecordedStartTime()), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(DateUtil.parseDay(request.getRewardRecordedEndTime()), DateUtil.FMT_TIME_4)))));
        }
        //排序
        /*FieldSortBuilder registerTime = SortBuilders.fieldSort("registerTime").order(SortOrder.DESC);
        return new NativeSearchQueryBuilder()
                .withSort(registerTime)
                .withPageable(request.getPageable())
                .withQuery(bool)
                .build();*/
        return NativeQuery.builder()
                .withQuery(g -> g.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(a -> a.field(b-> b.field("registerTime").order(SortOrder.Desc)))
                .build();
    }
}
