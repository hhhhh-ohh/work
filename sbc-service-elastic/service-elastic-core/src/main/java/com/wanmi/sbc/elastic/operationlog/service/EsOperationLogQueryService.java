package com.wanmi.sbc.elastic.operationlog.service;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.operationlog.EsOperationLogInitRequest;
import com.wanmi.sbc.elastic.api.request.operationlog.EsOperationLogListRequest;
import com.wanmi.sbc.elastic.api.response.operationlog.EsOperationLogPageResponse;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.bean.vo.operationlog.EsOperationLogVO;
import com.wanmi.sbc.elastic.operationlog.model.root.EsOperationLog;
import com.wanmi.sbc.elastic.operationlog.repository.EsOperationLogRepository;
import com.wanmi.sbc.setting.api.provider.OperationLogQueryProvider;
import com.wanmi.sbc.setting.api.request.OperationLogListRequest;
import com.wanmi.sbc.setting.bean.vo.OperationLogVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @author houshuai
 * @date 2020/12/16 10:27
 * @description <p> 操作日志 </p>
 */
@Slf4j
@Service
public class EsOperationLogQueryService {

    @Autowired
    private EsOperationLogRepository esOperationLogRepository;

    @Autowired
    private OperationLogQueryProvider operationLogQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esOperationLog.json")
    private Resource mapping;

    /**
     * 操作日志分页查询
     * @param queryRequest
     * @return
     */
    public BaseResponse<EsOperationLogPageResponse> queryOpLogByCriteria(EsOperationLogListRequest queryRequest) {
        NativeQuery searchQuery = this.esCriteria(queryRequest);
        Page<EsOperationLog> operationLogPage = esBaseService.commonPage(searchQuery, EsOperationLog.class, EsConstants.SYSTEM_OPERATION_LOG);
        Page<EsOperationLogVO> page = operationLogPage.map(source -> {
            EsOperationLogVO target = EsOperationLogVO.builder().build();
            KsBeanUtil.copyProperties(source, target);
            return target;
        });
        MicroServicePage<EsOperationLogVO> microServicePage = new MicroServicePage<>(page, queryRequest.getPageable());
        EsOperationLogPageResponse pageResponse = new EsOperationLogPageResponse();
        pageResponse.setOpLogPage(microServicePage);
        return BaseResponse.success(pageResponse);
    }

    /**
     * @description 查询数据量
     * @author  xuyunpeng
     * @date 2021/6/7 5:00 下午
     * @param queryRequest
     * @return
     */
    public Long count(EsOperationLogListRequest queryRequest) {
        NativeQuery searchQuery = this.esCriteria(queryRequest);
        Page<EsOperationLog> operationLogPage = esBaseService.commonPage(searchQuery, EsOperationLog.class, EsConstants.SYSTEM_OPERATION_LOG);
        return operationLogPage.getTotalElements();
    }


    /**
     * 操作日志初始化
     * @param request
     */
    public void init(EsOperationLogInitRequest request) {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.SYSTEM_OPERATION_LOG, mapping);
        boolean flg = true;
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        List<Long> idList = request.getIdList();

        OperationLogListRequest queryRequest = new OperationLogListRequest();
        try {
            while (flg) {
                if(CollectionUtils.isNotEmpty(idList)){
                    queryRequest.setIdList(idList);
                    pageSize = idList.size();
                    pageNum = 0;
                    flg = false;
                }
                queryRequest.putSort("createTime", SortType.DESC.toValue());
                queryRequest.setPageNum(pageNum);
                queryRequest.setPageSize(pageSize);
                List<OperationLogVO> operationLogVOList = operationLogQueryProvider.queryOpLogByCriteria(queryRequest)
                        .getContext().getOpLogPage().getContent();
                if (CollectionUtils.isEmpty(operationLogVOList)) {
                    flg = false;
                    log.info("==========ES初始化操作日志列表，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsOperationLog> newInfos = KsBeanUtil.convert(operationLogVOList, EsOperationLog.class);
                    esOperationLogRepository.saveAll(newInfos);
                    log.info("==========ES初始化操作日志列表成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化操作日志列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040009, new Object[]{pageNum});
        }

    }

    /**
     * 查询条件
     * @return
     */
    private NativeQuery esCriteria(EsOperationLogListRequest request) {
//        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQuery.Builder bool = QueryBuilders.bool();

        // 批量查询-id 主键List
        if (CollectionUtils.isNotEmpty(request.getIdList())) {
//            bool.must(QueryBuilders.termsQuery("id", request.getIdList()));
            bool.must(terms(g -> g.field("id").terms(t -> t.value(request.getIdList().stream().map(FieldValue::of).collect(Collectors.toList())))));
        }

        if (Objects.nonNull(request.getStoreId())) {
//            bool.must(QueryBuilders.termQuery("storeId", request.getStoreId()));
            bool.must(term(g -> g.field("storeId").value(request.getStoreId())));
        }
        if (Objects.nonNull(request.getCompanyInfoId())) {
//            bool.must(QueryBuilders.termQuery("companyInfoId", request.getCompanyInfoId()));
            bool.must(term(g -> g.field("companyInfoId").value(request.getCompanyInfoId())));
        }
        if (StringUtils.isNotBlank(request.getOpModule())) {
//            bool.must(QueryBuilders.termQuery("opModule", request.getOpModule()));
            bool.must(term(g -> g.field("opModule").value(request.getOpModule())));
        }

        if (StringUtils.isNotBlank(request.getBeginTime()) && StringUtils.isNotBlank(request.getEndTime())) {
//            bool.must(QueryBuilders.rangeQuery("opTime").gte(request.getBeginTime()).lte(request.getEndTime()));
            bool.must(range(g -> g.field("opTime").gte(JsonData.of(request.getBeginTime())).lte(JsonData.of(request.getEndTime()))));
        }

        if (StringUtils.isNotBlank(request.getOpAccount())) {
//            bool.must(QueryBuilders.wildcardQuery("opAccount", "*" + request.getOpAccount() + "*"));
            bool.must(wildcard(g -> g.field("opAccount").wildcard("*" + request.getOpAccount() + "*")));
        }

        if (StringUtils.isNotBlank(request.getOpName())) {
//            bool.must(QueryBuilders.wildcardQuery("opName", "*" + request.getOpName() + "*"));
            bool.must(wildcard(g -> g.field("opName").wildcard("*" + request.getOpName() + "*")));
        }

        if (StringUtils.isNotBlank(request.getOpCode())) {
//            bool.must(QueryBuilders.wildcardQuery("opCode", "*" + request.getOpCode() + "*"));
            bool.must(wildcard(g -> g.field("opCode").wildcard("*" + request.getOpCode() + "*")));
        }

        if (StringUtils.isNotBlank(request.getOpContext())) {
//            bool.must(QueryBuilders.wildcardQuery("opContext", "*" + request.getOpContext() + "*"));
            bool.must(wildcard(g -> g.field("opContext").wildcard("*" + request.getOpContext() + "*")));
        }

        log.info("{}", bool);
        /*FieldSortBuilder opTime = SortBuilders.fieldSort("opTime").order(SortOrder.DESC);
        return new NativeSearchQueryBuilder()
                .withQuery(bool)
                .withPageable(request.getPageable())
                .withSort(opTime)
                .build();*/
        return NativeQuery.builder()
                .withQuery(a -> a.bool(bool.build()))
                .withPageable(request.getPageable())
                .withSort(a -> a.field(b -> b.field("opTime").order(SortOrder.Desc)))
                .build();
    }
}
