package com.wanmi.sbc.elastic.communityleader.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.api.provider.quicklogin.ThirdLoginRelationQueryProvider;
import com.wanmi.sbc.customer.api.request.quicklogin.ThirdLoginRelationByCustomerRequest;
import com.wanmi.sbc.customer.api.response.quicklogin.ThirdLoginRelationResponse;
import com.wanmi.sbc.customer.bean.enums.ThirdLoginType;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.customer.bean.vo.ThirdLoginRelationVO;
import com.wanmi.sbc.elastic.api.request.communityleader.EsCommunityLeaderPageRequest;
import com.wanmi.sbc.elastic.api.response.communityleader.CommunityLeaderExportResponse;
import com.wanmi.sbc.elastic.api.response.communityleader.EsCommunityLeaderPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.vo.communityleader.EsCommunityLeaderVO;
import com.wanmi.sbc.elastic.communityleader.repository.EsCommunityLeaderRepository;
import com.wanmi.sbc.elastic.communityleader.root.EsCommunityLeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author: HouShuai
 * @date: 2020/12/7 11:29
 * @description: 社区团长查询
 */
@Slf4j
@Service
public class EsCommunityLeaderQueryService {

    @Autowired
    private EsCommunityLeaderRepository esCommunityLeaderRepository;

    @Autowired
    private ThirdLoginRelationQueryProvider thirdLoginRelationQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    /**
     * 查询社区团长列表
     *
     * @param request
     * @return
     */
    public EsCommunityLeaderPageResponse page(EsCommunityLeaderPageRequest request) {

        Query searchQuery = this.esCriteria(request);

        Page<EsCommunityLeader> esCommunityLeaderPage = esBaseService.commonPage(searchQuery,
                EsCommunityLeader.class, EsConstants.COMMUNITY_LEADER);
        List<EsCommunityLeader> esCommunityLeaderList = esCommunityLeaderPage.getContent();

        if (CollectionUtils.isEmpty(esCommunityLeaderList)) {
            return EsCommunityLeaderPageResponse.builder()
                    .communityLeaderVOS(new MicroServicePage<>())
                    .build();
        }
        Page<EsCommunityLeaderVO> newPage = esCommunityLeaderPage.map(entity -> {
            EsCommunityLeaderVO esCommunityLeaderVO = EsCommunityLeaderVO.builder().build();
            BeanUtils.copyProperties(entity, esCommunityLeaderVO);
            return esCommunityLeaderVO;
        });

        MicroServicePage<EsCommunityLeaderVO> microServicePage = new MicroServicePage<>(newPage, request.getPageable());

        return EsCommunityLeaderPageResponse.builder()
                .communityLeaderVOS(microServicePage)
                .build();
    }

    /**
     * 导出社区团长信息
     *
     * @param request
     * @return
     */
    public BaseResponse<CommunityLeaderExportResponse> export(EsCommunityLeaderPageRequest request) {

        //分批取出数据
        List<CommunityLeaderVO> dataRecords = this.getDataRecords(request);
        CommunityLeaderExportResponse finalRes = new CommunityLeaderExportResponse(dataRecords);
        return BaseResponse.success(finalRes);
    }

    /**
     * @description
     * @author  wc
     * @date 2021/6/7 3:49 下午
     * @param request
     * @return
     */
    public Long count(EsCommunityLeaderPageRequest request) {
        Query searchQuery = this.esCriteria(request);
        Page<EsCommunityLeader> esCommunityLeaderPage = esBaseService.commonPage(searchQuery,
                EsCommunityLeader.class, EsConstants.COMMUNITY_LEADER);
        return esCommunityLeaderPage.getTotalElements();
    }


    /**
     * 分批取数据
     *
     * @param request
     * @return
     */
    private List<CommunityLeaderVO> getDataRecords(EsCommunityLeaderPageRequest request) {
        Query searchQuery = this.esCriteria(request);
        Page<EsCommunityLeader> esCommunityLeaderPage = esBaseService.commonPage(searchQuery,
                EsCommunityLeader.class, EsConstants.COMMUNITY_LEADER);
        Page<CommunityLeaderVO> newPage = this.copyPage(esCommunityLeaderPage);
        return newPage.getContent();
    }


    /**
     * CommunityLeader转CommunityLeaderVO
     *
     * @param esCommunityLeaderPage
     * @return
     */
    private Page<CommunityLeaderVO> copyPage(Page<EsCommunityLeader> esCommunityLeaderPage) {
        return esCommunityLeaderPage.map(entity -> {
            CommunityLeaderVO communityLeaderVO = new CommunityLeaderVO();
            BeanUtils.copyProperties(entity, communityLeaderVO);
            ThirdLoginRelationByCustomerRequest relationByCustomerRequest = ThirdLoginRelationByCustomerRequest.builder()
                    .customerId(entity.getCustomerId())
                    .thirdLoginType(ThirdLoginType.WECHAT)
                    .build();
            BaseResponse<ThirdLoginRelationResponse> thirdLoginRelationResponseBaseResponse = thirdLoginRelationQueryProvider.listThirdLoginRelationByCustomer(relationByCustomerRequest);
            ThirdLoginRelationVO thirdLoginRelation = thirdLoginRelationResponseBaseResponse.getContext().getThirdLoginRelation();
            if (Objects.nonNull(thirdLoginRelation)) {
                communityLeaderVO.setHeadImg(thirdLoginRelation.getHeadimgurl());
            }
            return communityLeaderVO;
        });
    }


    /**
     * 社区团长列表查询条件
     *
     * @return
     */
    public NativeQuery esCriteria(EsCommunityLeaderPageRequest request) {

//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();


        if (CollectionUtils.isNotEmpty(request.getLeaderIdList())) {
//            bool.must(QueryBuilders.termsQuery("leaderId", request.getLeaderIdList()));
            bool.must(terms(a -> a.field("leaderId").terms(v -> v.value(request.getLeaderIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 社区团长标识UUID
        if (StringUtils.isNotEmpty(request.getLeaderId())) {
//            bool.must(QueryBuilders.termQuery("leaderId", request.getLeaderId()));
            bool.must(term(a -> a.field("leaderId").value(request.getLeaderId())));
        }

        // 模糊查询 - 会员ID
        if (StringUtils.isNotEmpty(request.getCustomerId())) {
//            bool.must(QueryBuilders.wildcardQuery("customerId", "*" + request.getCustomerId() + "*"));
            bool.must(wildcard(a -> a.field("customerId").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getCustomerId()))));
        }

        // 模糊查询 - 会员名称
        if (StringUtils.isNotEmpty(request.getLeaderName())) {
//            bool.must(QueryBuilders.wildcardQuery("leaderName", "*" + request.getLeaderName() + "*"));
            bool.must(wildcard(a -> a.field("leaderName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLeaderName()))));
        }

        // 模糊查询 - 会员登录账号|手机号
        if (StringUtils.isNotEmpty(request.getLeaderAccount())) {
//            bool.must(QueryBuilders.wildcardQuery("leaderAccount", "*" + request.getLeaderAccount() + "*"));
            bool.must(wildcard(a -> a.field("leaderAccount").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getLeaderAccount()))));
        }

        // 模糊查询 - 会员ID
        if (CollectionUtils.isNotEmpty(request.getEmployeeCustomerIds())) {
//            bool.must(QueryBuilders.termsQuery("customerId", request.getEmployeeCustomerIds()));
            bool.must(terms(a -> a.field("customerId").terms(v -> v.value(request.getEmployeeCustomerIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 大于或等于 搜索条件:创建时间开始
        // 小于或等于 搜索条件:创建时间截止
        if (ObjectUtils.allNotNull(request.getCreateTimeEnd(), request.getCreateTimeEnd())) {
//            bool.must(QueryBuilders.rangeQuery("createTime")
//                    .gte(DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4))
//                    .lte(DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_4)));
            bool.must(range(a -> a.field("createTime")
                    .gte(JsonData.of(DateUtil.format(request.getCreateTimeBegin(), DateUtil.FMT_TIME_4)))
                    .lte(JsonData.of(DateUtil.format(request.getCreateTimeEnd(), DateUtil.FMT_TIME_4)))));
        }

        // 是否帮卖
        if (Objects.nonNull(request.getAssistFlag())) {
//            bool.must(QueryBuilders.termQuery("assistFlag", request.getAssistFlag()));
            bool.must(term(a -> a.field("assistFlag").value(request.getAssistFlag())));
        }

        // 是否删除标志 0：否，1：是
        if (Objects.nonNull(request.getDelFlag())) {
//            bool.must(QueryBuilders.termQuery("delFlag", request.getDelFlag().toValue()));
            bool.must(term(a -> a.field("delFlag").value(request.getDelFlag().toValue())));
        }

//        SortOrder sortOrder = StringUtils.equals(request.getSortRole(), "asc") ? SortOrder.ASC : SortOrder.DESC;
//        FieldSortBuilder order = SortBuilders.fieldSort(request.getSortColumn()).order(sortOrder);
//        return new NativeSearchQueryBuilder()
//                .withQuery(bool)
//                .withPageable(request.getPageable())
//                .withSort(order)
//                .build();
        SortOrder sortOrder = StringUtils.equals(request.getSortRole(), "asc") ? SortOrder.Asc : SortOrder.Desc;
        NativeQuery builder = NativeQuery.builder()
                .withQuery(a -> a.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(a -> a.field(b -> b.field(request.getSortColumn()).order(sortOrder))).build();
        return builder;
    }
}
