package com.wanmi.sbc.elastic.customerInvoice.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ElasticCommonUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.invoice.CustomerInvoiceQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerSimplifyByIdRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoicePageRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerSimplifyByIdResponse;
import com.wanmi.sbc.customer.api.response.invoice.CustomerInvoicePageResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvalidFlag;
import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceRejectRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.customerInvoice.mapper.EsCustomerInvoiceMapper;
import com.wanmi.sbc.elastic.customerInvoice.model.root.EsCustomerInvoice;
import com.wanmi.sbc.elastic.customerInvoice.repository.EsCustomerInvoiceRepository;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.response.InvoiceConfigGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

@Service
@Slf4j
public class EsCustomerInvoiceService {

    @Autowired
    EsCustomerInvoiceRepository esCustomerInvoiceRepository;

    @Autowired
    CustomerInvoiceQueryProvider customerInvoiceQueryProvider;

    @Autowired
    EsCustomerInvoiceMapper esCustomerInvoiceMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esCustomerInvoice.json")
    private Resource mapping;

    public Page<EsCustomerInvoice> page(EsCustomerInvoicePageRequest request) {
        return esBaseService.commonPage(this.esCriteria(request), EsCustomerInvoice.class, EsConstants.ES_CUSTOMER_INVOICE);
    }

    public void saveAll(List<EsCustomerInvoice> esCustomerInvoices) {
        esCustomerInvoiceRepository.saveAll(esCustomerInvoices);
    }

    /**
     * 数据初始化
     *
     * @param pageRequest
     */
    public void init(CustomerInvoicePageRequest pageRequest) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        List<CustomerInvoiceVO> customerInvoiceVOList;
        List<Long> idList = pageRequest.getIdList();
        try {
            if (CollectionUtils.isNotEmpty(idList)) {
                if (pageRequest.getPageNum() > 0) {
                    return;
                }
                pageRequest.setPageSize(idList.size());
            }
            CustomerInvoicePageResponse customerInvoicePageResponse =
                    customerInvoiceQueryProvider.page(pageRequest).getContext();
            if (null == customerInvoicePageResponse) {
                return;
            }
            customerInvoiceVOList = customerInvoicePageResponse.getCustomerInvoiceVOPage().getContent();
            customerInvoiceVOList = customerInvoiceVOList.parallelStream().filter(customerInvoiceVO -> Objects.nonNull(customerInvoiceVO) &&
                    Objects.nonNull(customerInvoiceVO.getCustomerInvoiceId())).collect(Collectors.toList());
            List<EsCustomerInvoice> esCustomerInvoices = esCustomerInvoiceMapper.customerInvoiceVOListToEsCustomerInvoiceList(customerInvoiceVOList);
            //如果不是最后一页，继续执行
            if (CollectionUtils.isNotEmpty(esCustomerInvoices)) {
                saveAll(esCustomerInvoices.parallelStream().peek(esCustomerInvoice -> {
                    if (Objects.isNull(esCustomerInvoice.getDelFlag())) {
                        esCustomerInvoice.setDelFlag(DeleteFlag.NO);
                    }
                }).collect(Collectors.toList()));
                Integer pageNum = pageRequest.getPageNum() + 1;
                pageRequest.setPageNum(pageNum);
                init(pageRequest);
            } else {
                log.info("==========ES初始化增票资质结束，结束pageNum:{}==============", pageRequest.getPageNum());
            }
        } catch (Exception e) {
            log.error("==========ES初始化增票资质异常，异常pageNum:{}==============", pageRequest.getPageNum());
            log.error(e.getMessage());
        }


    }

    public void auditing(CheckState checkState, List<Long> customerInvoiceIds) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        customerInvoiceIds.parallelStream().forEach(id -> {
            Document document = Document.create();
            document.put("checkState", checkState.toValue());
            UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(id)).withDocument(document).build();
            elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.ES_CUSTOMER_INVOICE));
        });
        esBaseService.refresh(EsConstants.ES_CUSTOMER_INVOICE);
    }


    public void reject(EsCustomerInvoiceRejectRequest esCustomerInvoiceRejectRequest) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        Document document = Document.create();
        document.put("checkState", CheckState.NOT_PASS.toValue());
        document.put("rejectReason", esCustomerInvoiceRejectRequest.getRejectReason());
        UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(esCustomerInvoiceRejectRequest.getCustomerInvoiceId())).withDocument(document).build();
        elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.ES_CUSTOMER_INVOICE));
        esBaseService.refresh(EsConstants.ES_CUSTOMER_INVOICE);
    }

    public void invalid(List<Long> customerInvoiceIds) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        customerInvoiceIds.parallelStream().forEach(id -> {
            Document document = Document.create();
            document.put("checkState", CheckState.NOT_PASS.toValue());
            document.put("invalidFlag", InvalidFlag.YES.toValue());
            UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(id)).withDocument(document).build();
            elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.ES_CUSTOMER_INVOICE));
        });
        esBaseService.refresh(EsConstants.ES_CUSTOMER_INVOICE);
    }

    public void delete(List<Long> customerInvoiceIds) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        customerInvoiceIds.parallelStream().forEach(id -> {
            Document document = Document.create();
            document.put("delFlag", DeleteFlag.YES.toValue());
            UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(id)).withDocument(document).build();
            elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(EsConstants.ES_CUSTOMER_INVOICE));
        });
        esBaseService.refresh(EsConstants.ES_CUSTOMER_INVOICE);
    }

    public void add(EsCustomerInvoice esCustomerInvoice, String employeeId) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        //如果操作人是客户自己，则是客户端添加增专票信息
        esCustomerInvoice.setCustomerName(getCustomerName(esCustomerInvoice.getCustomerId()));
        if (StringUtils.isEmpty(employeeId)) {
            //如果开启审核开关，状态设为待审核
            if (Constants.yes.equals(queryInvoiceConfig().getStatus())) {
                esCustomerInvoice.setCheckState(CheckState.WAIT_CHECK);
            }
            esCustomerInvoice.setCreatePerson(esCustomerInvoice.getCustomerId());
        } else {
            //boss端添加增专票信息
            esCustomerInvoice.setCreatePerson(employeeId);
            // 用户申请处于待审核状态。boss可直接覆盖用户申请数据
//            NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//            boolQueryBuilder.must(QueryBuilders.termQuery("customerId",esCustomerInvoice.getCustomerId()));
//            builder.withQuery(boolQueryBuilder);
            elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
            NativeQuery builder = NativeQuery.builder()
                    .withQuery(a -> a.term(b -> b.field("customerId").value(esCustomerInvoice.getCustomerId())))
                    .build();
            elasticsearchTemplate.delete(builder, EsCustomerInvoice.class,
                    IndexCoordinates.of(EsConstants.ES_CUSTOMER_INVOICE));
        }
        esCustomerInvoiceRepository.save(esCustomerInvoice);
    }

    /**
     * 查询
     *
     * @return
     */
    private InvoiceConfigGetResponse queryInvoiceConfig() {
//        return configRepository.findByConfigTypeAndDelFlag(ConfigType.TICKETAUDIT.toValue(), DeleteFlag.NO);
        return auditQueryProvider.getInvoiceConfig().getContext();
    }

    public void modify(EsCustomerInvoice esCustomerInvoice, String employeeId) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        Optional<EsCustomerInvoice> customerInvoiceOptional = esCustomerInvoiceRepository.findById(esCustomerInvoice.getCustomerInvoiceId());
        if (customerInvoiceOptional.isPresent()) {
            esCustomerInvoice.setCheckState(CheckState.CHECKED);//默认状态为已审核
            esCustomerInvoice.setInvalidFlag(InvalidFlag.NO);// 修改后将作废状态取消
            //如果操作人是客户自己，则是客户端添加增专票信息
            if (StringUtils.isEmpty(employeeId)) {
                //如果开启审核开关，状态设为待审核
                if (Constants.yes.equals(queryInvoiceConfig().getStatus())) {
                    esCustomerInvoice.setCheckState(CheckState.WAIT_CHECK);
                }
                esCustomerInvoice.setUpdatePerson(esCustomerInvoice.getCustomerId());
            } else {
                esCustomerInvoice.setUpdatePerson(employeeId);
            }
            esCustomerInvoice.setCustomerName(getCustomerName(esCustomerInvoice.getCustomerId()));
            esCustomerInvoice.setUpdateTime(LocalDateTime.now());
            esCustomerInvoice.setCreateTime(customerInvoiceOptional.get().getCreateTime());
            esCustomerInvoice.setCreatePerson(customerInvoiceOptional.get().getCreatePerson());
            esCustomerInvoice.setDelFlag(customerInvoiceOptional.get().getDelFlag());
            esCustomerInvoiceRepository.save(esCustomerInvoice);
        }
    }


    public String getCustomerName(String customerId) {
        CustomerSimplifyByIdRequest customerSimplifyByIdRequest = new CustomerSimplifyByIdRequest(customerId);
        CustomerSimplifyByIdResponse response = customerQueryProvider.simplifyById(customerSimplifyByIdRequest).getContext();
        return response.getCustomerDetail().getCustomerName();
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.ES_CUSTOMER_INVOICE, mapping);
    }

    private Query esCriteria(EsCustomerInvoicePageRequest request) {
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        //会员名称
        if (StringUtils.isNotEmpty(request.getCustomerName())) {
//            boolQueryBuilder.must(QueryBuilders.wildcardQuery("customerName", "*" + request.getCustomerName()+ "*"));
            boolQueryBuilder.must(wildcard(g -> g.field("customerName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getCustomerName().trim()))));
        }
        //单位全称
        if (StringUtils.isNotEmpty(request.getCompanyName())) {
//            boolQueryBuilder.must(QueryBuilders.wildcardQuery("companyName", "*" + request.getCompanyName()+ "*"));
            boolQueryBuilder.must(wildcard(g -> g.field("companyName").value(ElasticCommonUtil.replaceEsLikeWildcard(request.getCompanyName().trim()))));
        }
        //增票资质审核状态
        if (request.getCheckState() != null) {
//            boolQueryBuilder.must(QueryBuilders.termQuery("checkState", request.getCheckState().toValue()));
            boolQueryBuilder.must(term(g -> g.field("checkState").value(request.getCheckState().toValue())));
        }

        // 批量查询-客户ID
        if (CollectionUtils.isNotEmpty(request.getCustomerIds())) {
//            boolQueryBuilder.must(QueryBuilders.termsQuery("customerId",request.getCustomerIds()));
            boolQueryBuilder.must(terms(g -> g.field("customerId").terms(v -> v.value(request.getCustomerIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 批量查询-业务员相关会员id
        if (CollectionUtils.isNotEmpty(request.getEmployeeCustomerIds())) {
//            boolQueryBuilder.must(QueryBuilders.termsQuery("customerId",request.getEmployeeCustomerIds()));
            boolQueryBuilder.must(terms(g -> g.field("customerId").terms(v -> v.value(request.getEmployeeCustomerIds().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        // 批量查询-发票样式
        if (request.getInvoiceStyle() != null) {
//            boolQueryBuilder.must(QueryBuilders.termQuery("invoiceStyle",request.getInvoiceStyle().toValue()));
            boolQueryBuilder.must(term(g -> g.field("invoiceStyle").value(request.getInvoiceStyle().toValue())));
        }

//        boolQueryBuilder.must(QueryBuilders.termQuery("delFlag", DeleteFlag.NO.toValue()));
        boolQueryBuilder.must(term(g -> g.field("delFlag").value(DeleteFlag.NO.toValue())));
//        queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
//        queryBuilder.withQuery(boolQueryBuilder);
//        queryBuilder.withPageable(request.getPageable());
//        return queryBuilder.build();
        return NativeQuery.builder()
                .withQuery(g -> g.bool(boolQueryBuilder.build()))
                .withPageable(request.getPageable())
                .withSort(g -> g.field(r -> r.field("createTime").order(SortOrder.Desc)))
                .build();
    }
}
