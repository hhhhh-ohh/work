package com.wanmi.sbc.elastic.distributionrecord.service;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordAddRequest;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordInitRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.distributionrecord.model.root.EsDistributionRecord;
import com.wanmi.sbc.elastic.distributionrecord.repository.EsDistributionRecordRepository;
import com.wanmi.sbc.marketing.api.provider.distributionrecord.DistributionRecordQueryProvider;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordPageRequest;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author houshuai
 * @date 2021/1/5 10:13
 * @description <p> 分销记录service </p>
 */
@Slf4j
@Service
public class EsDistributionRecordService {

    @Autowired
    private EsDistributionRecordRepository esDistributionRecordRepository;

    @Autowired
    private DistributionRecordQueryProvider distributionRecordQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esDistributionRecord.json")
    private Resource mapping;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 添加分销记录
     *
     * @param request
     */
    public void add(EsDistributionRecordAddRequest request) {
        List<DistributionRecordVO> recordVOList = request.getDistributionRecordVOs();
        if (CollectionUtils.isEmpty(recordVOList)) {
            return;
        }
        List<EsDistributionRecord> esDistributionRecords = KsBeanUtil.convertList(recordVOList, EsDistributionRecord.class);
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        esDistributionRecordRepository.saveAll(esDistributionRecords);
    }

    /**
     * 删除分销记录
     *
     * @param tradeId
     */
    public void deleteByTradeId(String tradeId) {
//        EsDistributionRecord esDistributionRecord = esDistributionRecordRepository.findByTradeIdAndDeleteFlag(tradeId, DeleteFlag.NO);
//        if (Objects.nonNull(esDistributionRecord)) {
//            esDistributionRecord.setDeleteFlag(DeleteFlag.YES);
//            esDistributionRecord.setOrderGoodsCount(0L);
//            //手动删除索引时，重新设置mapping
//            createIndexAddMapping();
//            esDistributionRecordRepository.save(esDistributionRecord);
//        }
        log.info("类名：EsDistributionRecordService, 方法名: deleteByTradeId.参数：{}", tradeId);
        UpdateQuery updateQuery =
                UpdateQuery.builder(
                                NativeQuery.builder()
                                        .withQuery(
                                                a -> a.term(b -> b.field("tradeId").value(tradeId)))
                                        .build())
                        .withScript("ctx._source.deleteFlag=1")
                        .withScriptType(ScriptType.INLINE)
                        .build();
        elasticsearchTemplate.updateByQuery(
                updateQuery, IndexCoordinates.of(EsConstants.DISTRIBUTION_RECORD));
    }

    /**
     * 分销记录初始化
     *
     * @param request {@link EsDistributionRecordInitRequest}
     */
    public void init(EsDistributionRecordInitRequest request) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        Boolean flg = Boolean.TRUE;
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        List<String> idList = request.getIdList();

        DistributionRecordPageRequest pageRequest = new DistributionRecordPageRequest();
        try {
            while (flg) {
                if(CollectionUtils.isNotEmpty(idList)){
                    pageRequest.setRecordIdList(idList);
                    pageSize = idList.size();
                    pageNum = 0;
                    flg = Boolean.FALSE;
                }
                pageRequest.setPageNum(pageNum);
                pageRequest.setPageSize(pageSize);
                List<DistributionRecordVO> content = distributionRecordQueryProvider.page(pageRequest).getContext()
                        .getDistributionRecordVOPage().getContent();

                if (CollectionUtils.isEmpty(content)) {
                    flg = Boolean.FALSE;
                    log.info("==========ES初始化分销记录列表，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsDistributionRecord> newInfoList = KsBeanUtil.convert(content, EsDistributionRecord.class);
                    esDistributionRecordRepository.saveAll(newInfoList);
                    log.info("==========ES初始化分销记录列表成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化分销记录列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040015, new Object[]{pageNum});
        }
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.DISTRIBUTION_RECORD, mapping);
    }
}
