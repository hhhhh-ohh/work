package com.wanmi.sbc.elastic.customer.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.ChildScoreMode;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailInitEsRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailListByCustomerIdsRequest;
import com.wanmi.sbc.customer.bean.dto.CustomerDetailFromEsDTO;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailFromEsVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailInitEsVO;
import com.wanmi.sbc.customer.bean.vo.EsPayingMemberLevelVO;
import com.wanmi.sbc.elastic.api.request.customer.*;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.dto.customer.EsCustomerDetailDTO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsEnterpriseInfoDTO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsStoreCustomerRelaDTO;
import com.wanmi.sbc.elastic.bean.vo.customer.EsCustomerDetailVO;
import com.wanmi.sbc.elastic.customer.mapper.EsCustomerDetailMapper;
import com.wanmi.sbc.elastic.customer.model.root.EsCustomerDetail;
import com.wanmi.sbc.elastic.customer.model.root.EsEnterpriseInfo;
import com.wanmi.sbc.elastic.customer.model.root.EsStoreCustomerRela;
import com.wanmi.sbc.elastic.customer.repository.EsCustomerDetailRepository;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * 会员详情Service
 */
@Slf4j
@Service
public class EsCustomerDetailService {

    @Autowired
    private EsCustomerDetailRepository esCustomerDetailRepository;

    @Autowired
    private EsCustomerDetailMapper esCustomerDetailMapper;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    protected EsBaseService esBaseService;

    @WmResource("mapping/esCustomerDetail.json")
    private Resource mapping;

    /**
     * 初始化ES数据
     */
    public void init(EsCustomerDetailInitRequest esCustomerDetailInitRequest){
        createIndexAddMapping();
        boolean initCustomerDetail = true;
        int pageNum = esCustomerDetailInitRequest.getPageNum();
        Integer pageSize = esCustomerDetailInitRequest.getPageSize();
        CustomerDetailInitEsRequest request = KsBeanUtil.convert(esCustomerDetailInitRequest,CustomerDetailInitEsRequest.class);
        try {
            while (initCustomerDetail) {
                if(CollectionUtils.isNotEmpty(request.getIdList())){
                    pageNum = 0;
                    pageSize = request.getIdList().size();
                    initCustomerDetail = false;
                }
                request.putSort("createTime", SortType.DESC.toValue());
                request.setPageNum(pageNum);
                request.setPageSize(pageSize);
                List<CustomerDetailInitEsVO> customerDetailInitEsVOList = customerQueryProvider.listByPage(request).getContext().getCustomerDetailInitEsVOList();
                if (CollectionUtils.isEmpty(customerDetailInitEsVOList)){
                    initCustomerDetail = false;
                    log.info("==========ES初始化会员详情结束，结束pageNum:{}==============",pageNum);
                }else {
                    List<EsCustomerDetail> esCustomerDetailList = esCustomerDetailMapper.customerDetailInitEsVOToEsCustomerDetail(customerDetailInitEsVOList);
                    this.saveAll(esCustomerDetailList);
                    log.info("==========ES初始化会员详情成功，当前pageNum:{}==============",pageNum);
                    pageNum++;
                }
            }
        }catch (Exception e){
            log.error("==========ES初始化会员详情异常，异常pageNum:{}==============",pageNum, e);
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129,new Object[]{pageNum});
        }

    }


    /**
     * 保存会员详情ES数据
     * @param customerDetailList
     * @return
     */
    public Iterable<EsCustomerDetail> saveAll(List<EsCustomerDetail> customerDetailList){
        this.createIndexAddMapping();
        return esCustomerDetailRepository.saveAll(customerDetailList);
    }

    /**
     * 保存会员详情ES数据
     * @param esCustomerDetailDTO
     * @return
     */
    public EsCustomerDetail save(EsCustomerDetailDTO esCustomerDetailDTO){
        this.createIndexAddMapping();
        EsCustomerDetail esCustomerDetail = esCustomerDetailMapper.customerDetailDTOToEsCustomerDetail(esCustomerDetailDTO);
        return esCustomerDetailRepository.save(esCustomerDetail);
    }

    /**
     * boss端修改会员信息
     * @param esCustomerDetailDTO
     * @return
     */
    public EsCustomerDetail modify(EsCustomerDetailDTO esCustomerDetailDTO){
        this.createIndexAddMapping();
        String customerId = esCustomerDetailDTO.getCustomerId();
        EsCustomerDetail esCustomerDetail = esCustomerDetailRepository.findById(customerId).orElse(null);
        if (Objects.isNull(esCustomerDetail)){
            return null;
        }
        esCustomerDetail.setCustomerAccount(esCustomerDetailDTO.getCustomerAccount());
        esCustomerDetail.setCustomerName(esCustomerDetailDTO.getCustomerName());
        esCustomerDetail.setProvinceId(esCustomerDetailDTO.getProvinceId());
        esCustomerDetail.setCityId(esCustomerDetailDTO.getCityId());
        esCustomerDetail.setAreaId(esCustomerDetailDTO.getAreaId());
        esCustomerDetail.setStreetId(esCustomerDetailDTO.getStreetId());
        esCustomerDetail.setCustomerAddress(esCustomerDetailDTO.getCustomerAddress());
        esCustomerDetail.setContactName(esCustomerDetailDTO.getContactName());
        esCustomerDetail.setContactPhone(esCustomerDetailDTO.getContactPhone());
        esCustomerDetail.setCustomerLevelId(esCustomerDetailDTO.getCustomerLevelId());
        esCustomerDetail.setCustomerType(esCustomerDetailDTO.getCustomerType());
        esCustomerDetail.setEmployeeId(esCustomerDetailDTO.getEmployeeId());
        EsEnterpriseInfo enterpriseInfo = esCustomerDetail.getEnterpriseInfo();
        EsEnterpriseInfoDTO enterpriseInfoDTO = esCustomerDetailDTO.getEnterpriseInfo();
        if (!Objects.equals(EnterpriseCheckState.INIT, esCustomerDetail.getEnterpriseCheckState()) && Objects.nonNull(enterpriseInfo) && Objects.nonNull(enterpriseInfoDTO)) {
            enterpriseInfo.setEnterpriseName(enterpriseInfoDTO.getEnterpriseName());
            enterpriseInfo.setBusinessNatureType(enterpriseInfoDTO.getBusinessNatureType());
        }
        return esCustomerDetailRepository.save(esCustomerDetail);
    }

    /**
     * 修改会员基础信息，同步ES
     * @param esCustomerDetailDTO
     * @return
     */
    public EsCustomerDetail modifyBaseInfo(EsCustomerDetailDTO esCustomerDetailDTO){
        this.createIndexAddMapping();
        String customerId = esCustomerDetailDTO.getCustomerId();
        EsCustomerDetail esCustomerDetail = esCustomerDetailRepository.findById(customerId).orElse(null);
        if (Objects.isNull(esCustomerDetail)){
            return null;
        }
        esCustomerDetail.setAreaId(esCustomerDetailDTO.getAreaId());
        esCustomerDetail.setCityId(esCustomerDetailDTO.getCityId());
        esCustomerDetail.setContactName(esCustomerDetailDTO.getContactName());
        esCustomerDetail.setContactPhone(esCustomerDetailDTO.getContactPhone());
        esCustomerDetail.setCustomerAddress(esCustomerDetailDTO.getCustomerAddress());
        esCustomerDetail.setCustomerName(esCustomerDetailDTO.getCustomerName());
        esCustomerDetail.setProvinceId(esCustomerDetailDTO.getProvinceId());
        esCustomerDetail.setStreetId(esCustomerDetailDTO.getStreetId());
        return esCustomerDetailRepository.save(esCustomerDetail);
    }

    /**
     * 修改会员账号，同步ES
     * @param esCustomerDetailDTO
     * @return
     */
    public EsCustomerDetail modifyCustomerAccount(EsCustomerDetailDTO esCustomerDetailDTO){
        this.createIndexAddMapping();
        String customerId = esCustomerDetailDTO.getCustomerId();
        EsCustomerDetail esCustomerDetail = esCustomerDetailRepository.findById(customerId).orElse(null);
        if (Objects.isNull(esCustomerDetail)){
            return null;
        }
        esCustomerDetail.setCustomerAccount(esCustomerDetailDTO.getCustomerAccount());
        return esCustomerDetailRepository.save(esCustomerDetail);
    }

    /**
     * 修改会员是否分销员，同步ES
     * @param esCustomerDetailDTO
     * @return
     */
    public EsCustomerDetail updateCustomerToDistributor(EsCustomerDetailDTO esCustomerDetailDTO){
        this.createIndexAddMapping();
        String customerId = esCustomerDetailDTO.getCustomerId();
        EsCustomerDetail esCustomerDetail = esCustomerDetailRepository.findById(customerId).orElse(null);
        if (Objects.isNull(esCustomerDetail)){
            return null;
        }
        esCustomerDetail.setIsDistributor(esCustomerDetailDTO.getIsDistributor());
        return esCustomerDetailRepository.save(esCustomerDetail);
    }

    /**
     * 修改账号状态
     * @param request
     * @return
     */
    public Iterable<EsCustomerDetail> modifyCustomerStateByCustomerId(EsCustomerStateBatchModifyRequest request){
        this.createIndexAddMapping();
        List<String> customerIds = request.getCustomerIds();
        CustomerStatus customerStatus = request.getCustomerStatus();
        String forbidReason = request.getForbidReason();
        Iterable<EsCustomerDetail> esCustomerDetails =  esCustomerDetailRepository.findAllById(customerIds);
        esCustomerDetails.iterator().forEachRemaining(esCustomerDetail -> {
            esCustomerDetail.setCustomerStatus(customerStatus);
            esCustomerDetail.setForbidReason(forbidReason);
        });
        return esCustomerDetailRepository.saveAll(esCustomerDetails);
    }

    /**
     * 修改审核状态
     * @param request
     * @return
     */
    public EsCustomerDetail modifyCustomerCheckState(EsCustomerCheckStateModifyRequest request){
        this.createIndexAddMapping();
        String customerId = request.getCustomerId();
        Integer checkState = request.getCheckState();
        String rejectReason = request.getRejectReason();
        EsCustomerDetail esCustomerDetail = esCustomerDetailRepository.findById(customerId).orElse(null);
        if (Objects.isNull(esCustomerDetail)){
            return null;
        }
        Boolean enterpriseCustomer = request.getEnterpriseCustomer();
        if (Objects.nonNull(enterpriseCustomer) && enterpriseCustomer ){
            esCustomerDetail.setEnterpriseCheckReason(request.getEnterpriseCheckReason());
            esCustomerDetail.setEnterpriseCheckState(request.getEnterpriseCheckState());
            checkState = EnterpriseCheckState.CHECKED.equals(request.getEnterpriseCheckState()) ?
                    CheckState.CHECKED.toValue() : CheckState.NOT_PASS.toValue();
            esCustomerDetail.setCheckState(checkState.equals(CheckState.CHECKED.toValue()) ? CheckState.CHECKED : CheckState.NOT_PASS);
        }else {
            esCustomerDetail.setRejectReason(rejectReason);
            esCustomerDetail.setCheckState(checkState.equals(CheckState.CHECKED.toValue()) ? CheckState.CHECKED : CheckState.NOT_PASS);
        }

        return esCustomerDetailRepository.save(esCustomerDetail);
    }

    /**
     * 添加平台客户
     * @param request
     * @return
     */
    public EsCustomerDetail addPlatformRelated(EsStoreCustomerRelaAddRequest request){
        this.createIndexAddMapping();
        EsStoreCustomerRelaDTO storeCustomerRelaDTO = request.getStoreCustomerRelaDTO();
        String customerId = storeCustomerRelaDTO.getCustomerId();
        EsCustomerDetail esCustomerDetail = esCustomerDetailRepository.findById(customerId).orElse(null);
        if (Objects.isNull(esCustomerDetail)){
            return null;
        }
        List<EsStoreCustomerRela> esStoreCustomerRelaList = esCustomerDetail.getEsStoreCustomerRelaList();
        EsStoreCustomerRela storeCustomerRela = KsBeanUtil.convert(storeCustomerRelaDTO,EsStoreCustomerRela.class);
        esStoreCustomerRelaList.add(storeCustomerRela);
        esCustomerDetail.setEsStoreCustomerRelaList(esStoreCustomerRelaList);
        return esCustomerDetailRepository.save(esCustomerDetail);
    }

    /**
     * 删除平台客户和商家之间的关联
     * @param request
     * @return
     */
    public EsCustomerDetail deletePlatformRelated(EsStoreCustomerRelaDeleteRequest request){
        this.createIndexAddMapping();
        String customerId = request.getCustomerId();
        EsCustomerDetail esCustomerDetail = esCustomerDetailRepository.findById(customerId).orElse(null);
        if (Objects.isNull(esCustomerDetail)){
            return null;
        }
        List<EsStoreCustomerRela> esStoreCustomerRelaList = esCustomerDetail.getEsStoreCustomerRelaList();
        Long companyInfoId = request.getCompanyInfoId();
        esStoreCustomerRelaList = esStoreCustomerRelaList.stream().filter(e -> !e.getCompanyInfoId().equals(companyInfoId)).collect(Collectors.toList());
        esCustomerDetail.setEsStoreCustomerRelaList(esStoreCustomerRelaList);
        return esCustomerDetailRepository.save(esCustomerDetail);
    }

    /**
     * 修改平台客户，只能修改等级
     *
     * @param request
     * @return
     */
    public EsCustomerDetail modifyByCustomerId(EsStoreCustomerRelaUpdateRequest request) {
        this.createIndexAddMapping();
        String customerId = request.getCustomerId();
        EsCustomerDetail esCustomerDetail = esCustomerDetailRepository.findById(customerId).orElse(null);
        if (Objects.isNull(esCustomerDetail)){
            return null;
        }
        if (esCustomerDetail.getCheckState() == CheckState.CHECKED ) {
            List<EsStoreCustomerRela> esStoreCustomerRelaList = esCustomerDetail.getEsStoreCustomerRelaList();
            Long companyInfoId = request.getCompanyInfoId();
            esStoreCustomerRelaList = esStoreCustomerRelaList.stream().filter(e -> e.getCompanyInfoId().equals(companyInfoId)).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(esStoreCustomerRelaList)){
                EsStoreCustomerRela esStoreCustomerRela = esStoreCustomerRelaList.get(0);
                esStoreCustomerRela.setStoreLevelId(request.getStoreLevelId());
                String employeeId = request.getEmployeeId();
                if (StringUtils.isNotBlank(employeeId)){
                    esCustomerDetail.setEmployeeId(employeeId);
                    esStoreCustomerRela.setEmployeeId(employeeId);
                }
            }
            return esCustomerDetailRepository.save(esCustomerDetail);
        }
        return null;
    }


    /**
     * 分页查询会员列表
     * @param request
     * @return
     */
    public Page<EsCustomerDetail> page(EsCustomerDetailPageRequest request){
        Query searchQuery = this.getSearchQuery(request);
        // 设置返回精准数量，以支持用户在页面上看到1W+数据
        searchQuery.setTrackTotalHits(Boolean.TRUE);
        return esBaseService.commonPage(searchQuery, EsCustomerDetail.class, EsConstants.DOC_CUSTOMER_DETAIL);
    }

    /**
     * 包装会员信息-会员等级名称、成长值、业务员名称
     * @param esCustomerDetailList
     * @param request
     */
    public List<EsCustomerDetailVO>  wrapperEsCustomerDetailVO(List<EsCustomerDetail> esCustomerDetailList,EsCustomerDetailPageRequest request){
        Long companyInfoId = request.getCompanyInfoId();
        Map<String, EsCustomerDetail>  esCustomerDetailMap = esCustomerDetailList.stream().collect(Collectors.toMap(EsCustomerDetail::getCustomerId,Function.identity()));
        List<EsCustomerDetailVO> esCustomerDetailVOList = esCustomerDetailMapper.customerDetailToEsCustomerDetailVO(esCustomerDetailList);
        if (MapUtils.isNotEmpty(esCustomerDetailMap)) {
            esCustomerDetailVOList.forEach(esCustomerDetailVO -> {
                String customerId = esCustomerDetailVO.getCustomerId();
                EsCustomerDetail esCustomerDetail = esCustomerDetailMap.get(customerId);
                if (Objects.nonNull(esCustomerDetail) && Objects.nonNull(esCustomerDetail.getEnterpriseInfo())){
                    EsEnterpriseInfo enterpriseInfo = esCustomerDetail.getEnterpriseInfo();
                    esCustomerDetailVO.setEnterpriseName(enterpriseInfo.getEnterpriseName());
                    esCustomerDetailVO.setBusinessNatureType(enterpriseInfo.getBusinessNatureType());
                }
            });
        }
        List<CustomerDetailFromEsDTO> dtoList = esCustomerDetailVOList.stream().map(c -> {
            CustomerDetailFromEsDTO vo = new CustomerDetailFromEsDTO();
            vo.setCustomerId(c.getCustomerId());
            vo.setCustomerLevelId(c.getCustomerLevelId());
            vo.setEmployeeId(c.getEmployeeId());
            return vo;
        }).collect(Collectors.toList());
        List<CustomerDetailFromEsVO> customerDetailFromEsVOS = customerQueryProvider.listByCustomerIds(new CustomerDetailListByCustomerIdsRequest(dtoList,companyInfoId))
                .getContext().getCustomerDetailFromEsVOS();
        if (CollectionUtils.isEmpty(customerDetailFromEsVOS)){
            return esCustomerDetailVOList;
        }
        Map<String, CustomerDetailFromEsVO> map = customerDetailFromEsVOS.stream().collect(Collectors.toMap(CustomerDetailFromEsVO::getCustomerId, Function.identity()));
        esCustomerDetailVOList.forEach(c -> {
            CustomerDetailFromEsVO esVO =  map.get(c.getCustomerId());
            c.setCustomerLevelId(esVO.getCustomerLevelId());
            c.setCustomerLevelName(esVO.getCustomerLevelName());
            if(CollectionUtils.isEmpty(c.getPayingMemberLevelList())){
                c.setPayingMemberLevelName("-");
            }else{
                c.setPayingMemberLevelName(c.getPayingMemberLevelList().stream().map(EsPayingMemberLevelVO::getPayingMemberLevelName).collect(Collectors.joining(",")));
            }
            c.setGrowthValue(esVO.getGrowthValue());
            c.setEmployeeName(esVO.getEmployeeName());
            c.setMyCustomer(esVO.getMyCustomer());
            c.setCustomerType(esVO.getCustomerType());
        });
        return esCustomerDetailVOList;
    }

    /**
     * 填充省市区
     * @param details
     */
    public void fillArea(List<EsCustomerDetailVO> details){
        if(CollectionUtils.isNotEmpty(details)){
            List<String> addrIds = new ArrayList<>();
            details.forEach(detail -> {
                addrIds.add(Objects.toString(detail.getProvinceId()));
                addrIds.add(Objects.toString(detail.getCityId()));
                addrIds.add(Objects.toString(detail.getAreaId()));
                addrIds.add(Objects.toString(detail.getStreetId()));
            });
            List<PlatformAddressVO> voList = platformAddressQueryProvider.list(PlatformAddressListRequest.builder().addrIdList(addrIds).build()).getContext().getPlatformAddressVOList();
            if(CollectionUtils.isNotEmpty(voList)){
                Map<String, String> addrMap = voList.stream().collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
                details.forEach(detail -> {
                    detail.setProvinceName(addrMap.get(Objects.toString(detail.getProvinceId())));
                    detail.setCityName(addrMap.get(Objects.toString(detail.getCityId())));
                    detail.setAreaName(addrMap.get(Objects.toString(detail.getAreaId())));
                    detail.setStreetName(addrMap.get(Objects.toString(detail.getStreetId())));
                });
            }
        }
    }


    /**
     * 修改会员等级，同步ES
     * @param esCustomerDetailDTO
     * @return
     */
    public void updateCustomerLevelAvailable(EsCustomerDetailDTO esCustomerDetailDTO){
        this.createIndexAddMapping();
        String customerId = esCustomerDetailDTO.getCustomerId();

        Document document = Document.create();
        document.put("customerLevelId", esCustomerDetailDTO.getCustomerLevelId());
        UpdateQuery updateQuery = UpdateQuery.builder(customerId).withDocument(document).build();
        elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.DOC_CUSTOMER_DETAIL));
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        esBaseService.existsOrCreate(EsConstants.DOC_CUSTOMER_DETAIL, mapping);
    }

    /**
     * 会员列表查询条件封装
     *
     * @return
     */
    private BoolQuery getBoolQueryBuilder(EsCustomerDetailPageRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();

        if (StringUtils.isNotBlank(request.getCustomerName())) {
            /*bool.must(QueryBuilders.wildcardQuery("customerName",
                    ElasticCommonUtil.replaceEsLikeWildcard(request.getCustomerName())));*/
            bool.must(wildcard(g -> g.field("customerName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getCustomerName()))));
        }

        if (StringUtils.isNotBlank(request.getCustomerAccount())) {
/*            bool.must(QueryBuilders.wildcardQuery("customerAccount",
                    ElasticCommonUtil.replaceEsLikeWildcard(request.getCustomerAccount())));*/
            bool.must(wildcard(g -> g.field("customerAccount").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getCustomerAccount()))));
        }

        if (Objects.nonNull(request.getProvinceId())) {
//            bool.must(termQuery("provinceId", request.getProvinceId()));
            bool.must(term(g -> g.field("provinceId").value(request.getProvinceId())));
        }
        if (Objects.nonNull(request.getCityId())) {
//            bool.must(termQuery("cityId", request.getCityId()));
            bool.must(term(g -> g.field("cityId").value(request.getCityId())));
        }
        if (Objects.nonNull(request.getAreaId())) {
//            bool.must(termQuery("areaId", request.getAreaId()));
            bool.must(term(g -> g.field("areaId").value(request.getAreaId())));
        }
        if (Objects.nonNull(request.getStreetId())) {
//            bool.must(termQuery("streetId", request.getStreetId()));
            bool.must(term(g -> g.field("streetId").value(request.getStreetId())));
        }

        if (Objects.nonNull(request.getCheckState())) {
//            bool.must(termQuery("checkState", request.getCheckState()));
            bool.must(term(g -> g.field("checkState").value(request.getCheckState())));
        }

        if (Objects.nonNull(request.getCustomerStatus())) {
//            bool.must(termQuery("customerStatus", request.getCustomerStatus().toValue()));
            bool.must(term(g -> g.field("customerStatus").value(request.getCustomerStatus().toValue())));
        }

        if (request.getEnterpriseCustomer()) {
            if (Objects.isNull(request.getEnterpriseCheckState())) {
                /*bool.must(QueryBuilders.termsQuery("enterpriseCheckState",
                        Lists.newArrayList(EnterpriseCheckState.CHECKED.toValue(),
                                EnterpriseCheckState.WAIT_CHECK.toValue(), EnterpriseCheckState.NOT_PASS.toValue())));*/
                bool.must(terms(g -> g.field("enterpriseCheckState")
                        .terms(t -> t.value(Lists.newArrayList(FieldValue.of(EnterpriseCheckState.CHECKED.toValue()),
                                FieldValue.of(EnterpriseCheckState.WAIT_CHECK.toValue()), FieldValue.of(EnterpriseCheckState.NOT_PASS.toValue()))))));
            }
            if (Objects.nonNull(request.getEnterpriseCheckState())) {
//                bool.must(termQuery("enterpriseCheckState", request.getEnterpriseCheckState().toValue()));
                bool.must(term(g -> g.field("enterpriseCheckState").value(request.getEnterpriseCheckState().toValue())));
            }
        }

        if (CollectionUtils.isNotEmpty(request.getCustomerIds())) {
//            bool.must(QueryBuilders.termsQuery("customerId", request.getCustomerIds()));
            bool.must(terms(g -> g.field("customerId").terms(t -> t.value(request.getCustomerIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (CollectionUtils.isNotEmpty(request.getCustomerAccountList())) {
//            bool.must(QueryBuilders.termsQuery("customerAccount", request.getCustomerAccountList()));
            bool.must(terms(g -> g.field("customerAccount").terms(t -> t.value(request.getCustomerAccountList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (Objects.isNull(request.getStoreId()) && Objects.nonNull(request.getCustomerLevelId())) {
//            bool.must(termQuery("customerLevelId", request.getCustomerLevelId()));
            bool.must(term(g -> g.field("customerLevelId").value(request.getCustomerLevelId())));
        }

        if (StringUtils.isNotBlank(request.getEmployeeId())) {
//            bool.must(termQuery("employeeId", request.getEmployeeId()));
            bool.must(term(g -> g.field("employeeId").value(request.getEmployeeId())));
        }

        if (CollectionUtils.isNotEmpty(request.getEmployeeIds())) {
//            bool.must(QueryBuilders.termsQuery("employeeId", request.getEmployeeIds()));
            bool.must(terms(g -> g.field("employeeId").terms(t -> t.value(request.getEmployeeIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (Objects.nonNull(request.getIsDistributor())) {
//            bool.must(termQuery("isDistributor", request.getIsDistributor().toValue()));
            bool.must(term(g -> g.field("isDistributor").value(request.getIsDistributor().toValue())));
        }

        if (Objects.nonNull(request.getPointsAvailableBegin())) {
//            bool.must(rangeQuery("pointsAvailable").gte(request.getPointsAvailableBegin()));
            bool.must(range(a -> a.field("pointsAvailable").gte(JsonData.of(request.getPointsAvailableBegin()))));
        }

        if (Objects.nonNull(request.getPointsAvailableEnd())) {
//            bool.must(rangeQuery("pointsAvailable").lte(request.getPointsAvailableEnd()));
            bool.must(range(a -> a.field("pointsAvailable").lte(JsonData.of(request.getPointsAvailableEnd()))));
        }

        if (Objects.nonNull(request.getStoreId())) {
//            bool.must(nestedQuery("esStoreCustomerRelaList",
//                    termQuery("esStoreCustomerRelaList.storeId", request.getStoreId()), ScoreMode.None));
            bool.must(nested(a -> a.path("esStoreCustomerRelaList").query(term(g -> g.field("esStoreCustomerRelaList.storeId").value(request.getStoreId()))).scoreMode(ChildScoreMode.None)));
        }
        if (Objects.nonNull(request.getStoreId()) && request.getCustomerLevelId() != null) {
//            bool.must(nestedQuery("esStoreCustomerRelaList",
//                    termQuery("esStoreCustomerRelaList.storeLevelId", request.getCustomerLevelId()), ScoreMode.None));
            bool.must(nested(a -> a.path("esStoreCustomerRelaList")
                    .query(term(g -> g.field("esStoreCustomerRelaList.storeLevelId").value(request.getCustomerLevelId())))
                    .scoreMode(ChildScoreMode.None)));
        }
        if (Objects.nonNull(request.getCompanyInfoId())) {
//            bool.must(nestedQuery("esStoreCustomerRelaList",
//                    termQuery("esStoreCustomerRelaList.companyInfoId", request.getCompanyInfoId()), ScoreMode.None));
            bool.must(nested(a -> a.path("esStoreCustomerRelaList")
                    .query(term(g -> g.field("esStoreCustomerRelaList.companyInfoId").value(request.getCompanyInfoId())))
                    .scoreMode(ChildScoreMode.None)));
        }
        if (Objects.nonNull(request.getCustomerType())) {
            if (CustomerType.SUPPLIER.equals(request.getCustomerType())) {
//                bool.must(termQuery("customerType", request.getCustomerType().toValue()));
                bool.must(term(g -> g.field("customerType").value(request.getCustomerType().toValue())));
            } else {
//                bool.mustNot(termQuery("customerType", CustomerType.SUPPLIER.toValue()));
                bool.mustNot(term(g -> g.field("customerType").value(CustomerType.SUPPLIER.toValue())));
            }
        }

        if (Objects.nonNull(request.getIsMyCustomer())) {
            if (request.getIsMyCustomer()) {
                if (PluginType.O2O == request.getPluginType()) {
//                    bool.must(termQuery("customerType", CustomerType.STOREFRONT.toValue()));
                    bool.must(term(g -> g.field("customerType").value(CustomerType.STOREFRONT.toValue())));
                } else {
                    // isMyCustomer=true，查询商家用户
//                    bool.must(termQuery("customerType", CustomerType.SUPPLIER.toValue()));
                    bool.must(term(g -> g.field("customerType").value(CustomerType.SUPPLIER.toValue())));
                }
            } else {
                // ==0表示平台用户
//                bool.must(termQuery("customerType", CustomerType.PLATFORM.toValue()));
                bool.must(term(g -> g.field("customerType").value(CustomerType.PLATFORM.toValue())));
            }
        }else {
            if (Objects.nonNull(request.getPluginType()) && PluginType.O2O == request.getPluginType()) {
//                bool.mustNot(termQuery("customerType", CustomerType.SUPPLIER.toValue()));
                bool.mustNot(term(g -> g.field("customerType").value(CustomerType.SUPPLIER.toValue())));
            }
        }



        if (StringUtils.isNotBlank(request.getEnterpriseName())) {
            /*bool.must(QueryBuilders.nestedQuery("enterpriseInfo",
                    QueryBuilders.wildcardQuery("enterpriseInfo.enterpriseName",
                            ElasticCommonUtil.replaceEsLikeWildcard(request.getEnterpriseName())), ScoreMode.None));*/
            bool.must(nested(a -> a.path("enterpriseInfo")
                    .query(wildcard(g -> g.field("enterpriseInfo.enterpriseName")
                            .value(ElasticCommonUtil.replaceEsLikeWildcard(request.getEnterpriseName()))))
                    .scoreMode(ChildScoreMode.None)));
        }

        if (Objects.nonNull(request.getBusinessNatureType())) {
            /*bool.must(QueryBuilders.nestedQuery("enterpriseInfo",
                    QueryBuilders.termQuery("enterpriseInfo.businessNatureType", request.getBusinessNatureType()), ScoreMode.None));*/
            bool.must(nested(a -> a.path("enterpriseInfo")
                    .query(term(g -> g.field("enterpriseInfo.businessNatureType").value(request.getBusinessNatureType())))
                    .scoreMode(ChildScoreMode.None)));
        }
        if (Objects.nonNull(request.getKeyword())) {
            /*bool.must(QueryBuilders.boolQuery()
                    .should(QueryBuilders.wildcardQuery("customerName", ElasticCommonUtil.replaceEsLikeWildcard(request.getKeyword())))
                    .should(QueryBuilders.wildcardQuery("customerAccount", ElasticCommonUtil.replaceEsLikeWildcard(request.getKeyword()))));*/
            bool.must(a -> a.bool(QueryBuilders.bool()
                    .should(wildcard(g -> g.field("customerName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getKeyword()))))
                    .should(wildcard(g -> g.field("customerAccount").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getKeyword())))).build()));
        }
        if (Objects.nonNull(request.getLogOutStatus())){
//            bool.must(termQuery("logOutStatus", request.getLogOutStatus().toValue()));
            bool.must(term(g -> g.field("logOutStatus").value(request.getLogOutStatus().toValue())));
        }else {
            // 不需要全部注销状态时，才将筛选非已注销的客户
            if (BooleanUtils.isFalse(request.getFilterAllLogOutStatusFlag())) {
//                bool.mustNot(termQuery("logOutStatus", LogOutStatus.LOGGED_OUT.toValue()));
                bool.mustNot(term(g -> g.field("logOutStatus").value(LogOutStatus.LOGGED_OUT.toValue())));
            }
        }
        if(Objects.nonNull(request.getLevelId())){
            /*bool.must(nestedQuery("payingMemberLevelList",
                    termQuery("payingMemberLevelList.levelId", request.getLevelId()), ScoreMode.None));*/
            bool.must(nested(a -> a.path("payingMemberLevelList")
                    .query(term(g -> g.field("payingMemberLevelList.levelId").value(request.getLevelId())))
                    .scoreMode(ChildScoreMode.None)));
        }
        return bool.build();

    }

    private Query getSearchQuery(EsCustomerDetailPageRequest request) {
        /*NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(getBoolQueryBuilder(request));
        builder.withPageable(request.getPageable());
        if (request.getPointsPage()) {
            builder.withSort(SortBuilders.fieldSort("pointsAvailable").order(SortOrder.DESC));
        } else {
            builder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        }
        return builder.build();*/
        NativeQueryBuilder builder =
                NativeQuery.builder()
                        .withQuery(g -> g.bool(getBoolQueryBuilder(request)))
                        .withPageable(request.getPageable());
        if (request.getPointsPage()) {
            builder = builder.withSort(g -> g.field(r -> r.field("pointsAvailable").order(SortOrder.Desc)));
        }else {
            builder = builder.withSort(g -> g.field(r -> r.field("createTime").order(SortOrder.Desc)));
        }
        return builder.build();
    }
}
