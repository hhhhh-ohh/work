package com.wanmi.sbc.elastic.distributionmatter.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.DistributionCommissionUtils;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.company.CompanyInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoQueryByIdsRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeePageRequest;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoQueryByIdsResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeePageResponse;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.customer.bean.vo.EmployeeListByIdsVO;
import com.wanmi.sbc.customer.bean.vo.EmployeePageVO;
import com.wanmi.sbc.elastic.api.request.distributionmatter.EsDistributionGoodsMatterPageRequest;
import com.wanmi.sbc.elastic.api.response.distributionmatter.EsDistributionGoodsMatterPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.distributionmatter.model.root.EsDistributionGoodsMatter;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.goods.GoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsInfoSpecDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.goods.api.request.goods.GoodsByConditionRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsInfoSpecDetailRelBySkuIdsRequest;
import com.wanmi.sbc.goods.api.response.cate.GoodsCateChildCateIdsByIdResponse;
import com.wanmi.sbc.goods.api.response.goods.GoodsByConditionResponse;
import com.wanmi.sbc.goods.api.response.spec.GoodsInfoSpecDetailRelBySkuIdsResponse;
import com.wanmi.sbc.goods.bean.enums.MatterType;
import com.wanmi.sbc.goods.bean.vo.DistributionGoodsMatterPageVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author houshuai
 * @date 2021/1/8 15:37
 * @description <p> 商品素材查询 </p>
 */
@Service
public class EsDistributionGoodsMatterQueryService {

    @Autowired
    private GoodsCateQueryProvider goodsCateQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private CompanyInfoQueryProvider companyInfoQueryProvider;

    @Autowired
    private GoodsQueryProvider goodsQueryProvider;

    @Autowired
    private DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired
    private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

    @Autowired
    private EsBaseService esBaseService;


    /**
     * 分页查询商品素材
     *
     * @param request 查询条件
     * @return {@link EsDistributionGoodsMatterPageResponse}
     */
    public BaseResponse<EsDistributionGoodsMatterPageResponse> pageNewForBoss(EsDistributionGoodsMatterPageRequest request) {
        // 判断name参数是否为空
        // Modify by zhengyang for issue 【ID1071160】
        if (StringUtils.isNotBlank(request.getOperatorName())
                || StringUtils.isNotBlank(request.getOperatorAccount())) {
            List<String> idList = null;
            EmployeePageRequest employeeReq = new EmployeePageRequest();
            employeeReq.setUserName(request.getOperatorName());
            employeeReq.setAccountName(request.getOperatorAccount());
            employeeReq.setPageNum(0);
            employeeReq.setPageSize(Integer.MAX_VALUE);
            BaseResponse<EmployeePageResponse> response = employeeQueryProvider.page(employeeReq);
            if (Objects.nonNull(response)
                    && Objects.nonNull(response.getContext())
                    && Objects.nonNull(response.getContext().getEmployeePageVOPage())
                    && CollectionUtils.isNotEmpty(response.getContext().getEmployeePageVOPage().getContent())) {
                idList = response.getContext().getEmployeePageVOPage().getContent().stream()
                        .map(EmployeePageVO::getEmployeeId).collect(Collectors.toList());
            }
            // 查询为空直接返回
            if (CollectionUtils.isEmpty(idList)) {
                EsDistributionGoodsMatterPageResponse emptyResponse = new EsDistributionGoodsMatterPageResponse();
                emptyResponse.setDistributionGoodsMatterPage(new MicroServicePage<>(new ArrayList<>(), request.getPageable(), 0));
                return BaseResponse.success(emptyResponse);
            }
            request.setOperatorIdList(idList);
        }

        Page<EsDistributionGoodsMatter> esDistributionGoodsMatters = this.pageEsDistributionGoodsMatter(request);

        //根据id查询操作员，查询操作员的名称和账号
        List<String> ids = esDistributionGoodsMatters.getContent().stream().map(EsDistributionGoodsMatter::getOperatorId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            EsDistributionGoodsMatterPageResponse response = new EsDistributionGoodsMatterPageResponse();
            response.setDistributionGoodsMatterPage(KsBeanUtil.convertPage(esDistributionGoodsMatters, DistributionGoodsMatterPageVO.class));
            return BaseResponse.success(response);
        }
        EmployeeListByIdsRequest employeeRequest = new EmployeeListByIdsRequest();
        employeeRequest.setEmployeeIds(ids);
        List<EmployeeListByIdsVO> employeeResponse = employeeQueryProvider.listByIds(employeeRequest).getContext().getEmployeeList();
        //设值
        List<DistributionGoodsMatterPageVO> pageVO = esDistributionGoodsMatters.getContent().stream().map(item -> {
            DistributionGoodsMatterPageVO vo = KsBeanUtil.copyPropertiesThird(item, DistributionGoodsMatterPageVO.class);
            if (item.getEsObjectGoodsInfo() != null) {
                GoodsInfoVO goodsInfoVO = KsBeanUtil.copyPropertiesThird(item.getEsObjectGoodsInfo(), GoodsInfoVO.class);
                vo.setGoodsInfo(goodsInfoVO);
            }
            for (EmployeeListByIdsVO employee : employeeResponse) {
                if (employee.getEmployeeId().equals(item.getOperatorId())) {
                    vo.setName(employee.getEmployeeName());
                    vo.setAccount(employee.getAccountName());
                    break;
                }
            }
            return vo;
        }).collect(Collectors.toList());
        EsDistributionGoodsMatterPageResponse response = new EsDistributionGoodsMatterPageResponse();
        response.setDistributionGoodsMatterPage(new MicroServicePage<>(pageVO, request.getPageable(), esDistributionGoodsMatters.getTotalElements()));

        if (Objects.isNull(response.getDistributionGoodsMatterPage()) || response.getDistributionGoodsMatterPage().getTotalElements() < 1 || CollectionUtils.isEmpty(response.getDistributionGoodsMatterPage().getContent())) {
            return BaseResponse.success(response);
        }

        //分页商品信息
        List<GoodsInfoVO> goodsInfoVOList = response.getDistributionGoodsMatterPage()
                .getContent().stream().filter(f -> f.getGoodsInfo() != null).map(DistributionGoodsMatterPageVO::getGoodsInfo).collect(Collectors.toList());

        if (goodsInfoVOList.size() > 0) {
            //查询所有sku的spu信息
            List<String> goodsIds = goodsInfoVOList.stream().map(GoodsInfoVO::getGoodsId).collect(Collectors.toList());
            GoodsByConditionRequest goodsByConditionRequest = new GoodsByConditionRequest();
            goodsByConditionRequest.setGoodsIds(goodsIds);
            GoodsByConditionResponse context = goodsQueryProvider.listByCondition(goodsByConditionRequest).getContext();

            List<GoodsVO> goodsVOList = context.getGoodsVOList();

            //拿到商家相关消息
            List<Long> companyInfoIds = goodsInfoVOList.stream().map(GoodsInfoVO::getCompanyInfoId).collect(Collectors.toList());
            if (companyInfoIds.size() > 0) {
                CompanyInfoQueryByIdsRequest companyInfoQueryByIdsRequest = new CompanyInfoQueryByIdsRequest();
                companyInfoQueryByIdsRequest.setCompanyInfoIds(companyInfoIds);
                companyInfoQueryByIdsRequest.setDeleteFlag(DeleteFlag.NO);
                BaseResponse<CompanyInfoQueryByIdsResponse> companyInfoQueryByIdsResponseBaseResponse =
                        companyInfoQueryProvider.queryByCompanyInfoIds(companyInfoQueryByIdsRequest);
                response.setCompanyInfoList(companyInfoQueryByIdsResponseBaseResponse.getContext().getCompanyInfoList());
            }

            //查询所有SKU规格值关联
            List<String> goodsInfoIds =
                    goodsInfoVOList.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
            GoodsInfoSpecDetailRelBySkuIdsRequest skuIdsRequest = new GoodsInfoSpecDetailRelBySkuIdsRequest(goodsInfoIds);
            GoodsInfoSpecDetailRelBySkuIdsResponse skuIdsResponse = goodsInfoSpecDetailRelQueryProvider.listBySkuIds(skuIdsRequest).getContext();

            List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetails = skuIdsResponse.getGoodsInfoSpecDetailRelVOList();

            //获取登录人的佣金比例
            if (StringUtils.isNotBlank(request.getCustomerId())) {
                BaseResponse<DistributorLevelByCustomerIdResponse> resultBaseResponse =
                        distributorLevelQueryProvider.getByCustomerId(new DistributorLevelByCustomerIdRequest
                                (request.getCustomerId()));
                DistributorLevelVO distributorLevelVO = Objects.isNull(resultBaseResponse) ? null :
                        resultBaseResponse.getContext().getDistributorLevelVO();
                if (Objects.nonNull(distributorLevelVO) && Objects.nonNull(distributorLevelVO.getCommissionRate())) {
                    BigDecimal commissionRate = distributorLevelVO.getCommissionRate();
                    //填充每个SKU
                    response.getDistributionGoodsMatterPage()
                            .getContent().forEach(distributionGoodsMatterPageVO -> {
                        if (distributionGoodsMatterPageVO.getGoodsInfo() != null) {
                            //sku商品图片为空，则以spu商品主图
                            if (StringUtils.isBlank(distributionGoodsMatterPageVO.getGoodsInfo().getGoodsInfoImg())) {
                                distributionGoodsMatterPageVO.getGoodsInfo().setGoodsInfoImg(
                                        goodsVOList.stream().filter(goods -> goods.getGoodsId()
                                                .equals(distributionGoodsMatterPageVO.getGoodsInfo().getGoodsId())).findFirst()
                                                .orElseGet(GoodsVO::new).getGoodsImg());
                            }
                            //填充分销员佣金
                            distributionGoodsMatterPageVO.getGoodsInfo().setDistributionCommission(
                                    DistributionCommissionUtils.calDistributionCommission(distributionGoodsMatterPageVO.getGoodsInfo().getDistributionCommission(), commissionRate));
                            //填充每个SKU的规格关系
                            distributionGoodsMatterPageVO.getGoodsInfo().setSpecDetailRelIds(goodsInfoSpecDetails.stream()
                                    .filter(specDetailRel -> specDetailRel.getGoodsInfoId().equals(distributionGoodsMatterPageVO.getGoodsInfo().getGoodsInfoId()))
                                    .map(GoodsInfoSpecDetailRelVO::getSpecDetailRelId).collect(Collectors.toList()));
                        }
                    });
                }
            } else {
                //填充每个SKU
                response.getDistributionGoodsMatterPage()
                        .getContent().forEach(distributionGoodsMatterPageVO -> {
                    if (distributionGoodsMatterPageVO.getGoodsInfo() != null) {
                        //sku商品图片为空，则以spu商品主图
                        if (StringUtils.isBlank(distributionGoodsMatterPageVO.getGoodsInfo().getGoodsInfoImg())) {
                            distributionGoodsMatterPageVO.getGoodsInfo().setGoodsInfoImg(
                                    goodsVOList.stream().filter(goods -> goods.getGoodsId()
                                            .equals(distributionGoodsMatterPageVO.getGoodsInfo().getGoodsId())).findFirst()
                                            .orElseGet(GoodsVO::new).getGoodsImg());
                        }
                        //填充每个SKU的规格关系
                        distributionGoodsMatterPageVO.getGoodsInfo().setSpecDetailRelIds(goodsInfoSpecDetails.stream()
                                .filter(specDetailRel -> specDetailRel.getGoodsInfoId().equals(distributionGoodsMatterPageVO.getGoodsInfo().getGoodsInfoId()))
                                .map(GoodsInfoSpecDetailRelVO::getSpecDetailRelId).collect(Collectors.toList()));
                    }
                });
            }

            if (CollectionUtils.isNotEmpty(goodsInfoSpecDetails)) {
                response.setGoodsInfoSpecDetails(goodsInfoSpecDetails);
            }
        }

        return BaseResponse.success(response);
    }


    /**
     * 分页查询 EsDistributionGoodsMatter
     *
     * @param request
     * @return
     */
    private Page<EsDistributionGoodsMatter> pageEsDistributionGoodsMatter(EsDistributionGoodsMatterPageRequest request) {
        //获取该分类的所有子分类
        if (Objects.nonNull(request.getCateId()) && request.getCateId() > 0) {
            GoodsCateChildCateIdsByIdRequest cateIdsByIdRequest = new GoodsCateChildCateIdsByIdRequest();
            cateIdsByIdRequest.setCateId(request.getCateId());
            GoodsCateChildCateIdsByIdResponse response = goodsCateQueryProvider.getChildCateIdById(cateIdsByIdRequest).getContext();
            request.setCateIds(response.getChildCateIdList());
            if (CollectionUtils.isNotEmpty(request.getCateIds())) {
                request.getCateIds().add(request.getCateId());
            } else {
                request.setCateIds(Stream.of(request.getCateId()).collect(Collectors.toList()));
            }
            request.setCateId(null);
        }

        return esBaseService.commonPage(this.searchQuery(request), EsDistributionGoodsMatter.class,
                EsConstants.DISTRIBUTION_GOODS_MATTER);
    }

    /**
     * 查询条件
     *
     * @return NativeSearchQuery
     */
    public NativeQuery searchQuery(EsDistributionGoodsMatterPageRequest request) {

//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        BoolQuery.Builder builder = QueryBuilders.bool();
//        boolQuery.must(QueryBuilders.termQuery("delFlag", DeleteFlag.NO.toValue()));
        builder.must(term(g -> g.field("delFlag").value(DeleteFlag.NO.toValue())));

        // skuId
        if (StringUtils.isNotEmpty(request.getGoodsInfoId())) {
//            boolQuery.must(QueryBuilders.termQuery("esObjectGoodsInfo.goodsInfoId", request.getGoodsInfoId()));
            builder.must(term(g -> g.field("esObjectGoodsInfo.goodsInfoId").value(request.getGoodsInfoId())));
        }
        // 店铺id
        if (Objects.nonNull(request.getStoreId())) {
//            boolQuery.must(QueryBuilders.termQuery("esObjectGoodsInfo.storeId", request.getGoodsInfoId()));
            builder.must(term(g -> g.field("esObjectGoodsInfo.storeId").value(request.getStoreId())));
        }
        // 发布者id
        if (request.getOperatorId() != null) {
//            boolQuery.must(QueryBuilders.termQuery("operatorId", request.getOperatorId()));
            builder.must(term(g -> g.field("operatorId").value(request.getOperatorId())));
        }
        // 发布者id集合
        if (CollectionUtils.isNotEmpty(request.getOperatorIdList())) {
//            boolQuery.must(QueryBuilders.termsQuery("operatorId", request.getOperatorIdList()));
            List<FieldValue> v = new ArrayList<>();
            request.getOperatorIdList().forEach(g -> {
                v.add(FieldValue.of(g));
            });
            builder.must(terms(g -> g.field("operatorId").terms(x -> x.value(v))));
        }
        // 引用次数范围
        if (request.getRecommendNumMin() != null) {
//            boolQuery.must(QueryBuilders.rangeQuery("recommendNum").gte(request.getRecommendNumMin()));
            builder.must(range(g -> g.field("recommendNum").gte(JsonData.of(request.getRecommendNumMin()))));

        }
        if (request.getRecommendNumMax() != null) {
//            boolQuery.must(QueryBuilders.rangeQuery("recommendNum").lte(request.getRecommendNumMax()));
            builder.must(range(g -> g.field("recommendNum").lte(JsonData.of(request.getRecommendNumMax()))));
        }

        //商品名称
        if (StringUtils.isNotEmpty(request.getGoodsInfoName())) {
//            boolQuery.must(QueryBuilders.wildcardQuery("esObjectGoodsInfo.goodsInfoName", "*" + request.getGoodsInfoName() + "*"));
            builder.must(wildcard(g -> g.field("esObjectGoodsInfo.goodsInfoName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getGoodsInfoName().trim()))));
        }
        //商品编码
        if (StringUtils.isNotEmpty(request.getGoodsInfoNo())) {
//            boolQuery.must(QueryBuilders.wildcardQuery("esObjectGoodsInfo.goodsInfoNo", "*" + request.getGoodsInfoNo() + "*"));
            builder.must(wildcard(g -> g.field("esObjectGoodsInfo.goodsInfoNo").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getGoodsInfoNo().trim()))));
        }
        //品牌id
        if (request.getBrandId() != null && request.getBrandId() > 0) {
//            boolQuery.must(QueryBuilders.termQuery("esObjectGoodsInfo.brandId", request.getBrandId()));
            builder.must(term(g -> g.field("esObjectGoodsInfo.brandId").value(request.getBrandId())));
        }
        //商品类目
        if (CollectionUtils.isNotEmpty(request.getCateIds())) {
//            boolQuery.must(QueryBuilders.termsQuery("esObjectGoodsInfo.cateId", request.getCateIds()));
            List<FieldValue> v = new ArrayList<>();
            request.getCateIds().forEach(g -> {
                v.add(FieldValue.of(g));
            });
            builder.must(terms(g -> g.field("esObjectGoodsInfo.cateId").terms(x -> x.value(v))));
        }

        //素材类型
        if (request.getMatterType() != null) {
//            boolQuery.must(QueryBuilders.termQuery("matterType", request.getMatterType().toValue()));
            builder.must(term(g -> g.field("matterType").value(request.getMatterType().toValue())));
            //商品素材-分销商品的审核状态
            if (request.getMatterType() == MatterType.GOODS && request.getDistributionGoodsAudit() != null) {
//                boolQuery.must(QueryBuilders.termQuery("esObjectGoodsInfo.distributionGoodsAudit", request.getDistributionGoodsAudit().toValue()));
                builder.must(term(g -> g.field("esObjectGoodsInfo.distributionGoodsAudit").value(request.getDistributionGoodsAudit().toValue())));
            }
        }

        //商品类型
        if (request.getGoodsType() != null) {
//            boolQuery.must(QueryBuilders.termQuery("esObjectGoodsInfo.goodsType", request.getGoodsType()));
            builder.must(term(g -> g.field("esObjectGoodsInfo.goodsType").value(request.getGoodsType())));
        }

        String sort = StringUtils.isNotBlank(request.getSortColumn()) ? request.getSortColumn() : "updateTime";
//        SortOrder orderBy = "asc".equals(request.getSortRole()) ? SortOrder.ASC : SortOrder.DESC;
//        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(sort).order(orderBy);
//        return new NativeSearchQueryBuilder()
//                .withQuery(boolQuery)
//                .withSort(fieldSortBuilder)
//                .withPageable(request.getPageable())
//                .build();
        SortOrder sortOrder = StringUtils.equals(request.getSortRole(), "asc") ? SortOrder.Asc : SortOrder.Desc;
        return NativeQuery.builder()
                .withQuery(g -> g.bool(builder.build()))
                .withPageable(request.getPageable())
                .withSort(g -> g.field(r -> r.field(sort).order(sortOrder)))
                .build();
    }
}
