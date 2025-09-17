package com.wanmi.sbc.elastic.distributioninvitenew.service;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionInviteNewQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.DistributionInviteNewPageRequest;
import com.wanmi.sbc.customer.api.response.customer.DistributionInviteNewPageResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewRecordVO;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewInitRequest;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewSaveRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.distributioninvitenew.model.root.EsInviteNewRecord;
import com.wanmi.sbc.elastic.distributioninvitenew.repository.EsDistributionInviteNewRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author houshuai
 * @date 2021/1/6 14:29
 * @description <p> 邀新记录 </p>
 */
@Slf4j
@Service
public class EsDistributionInviteNewService {

    @Autowired
    private EsDistributionInviteNewRepository esDistributionInviteNewRepository;

    @Autowired
    private DistributionInviteNewQueryProvider distributionInviteNewQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esInviteNewRecord.json")
    private Resource mapping;

    /**
     * 初始化邀新记录
     *
     * @param request
     */
    public void initDistributionInviteNewRecord(EsDistributionInviteNewInitRequest request) {
        boolean flag = true;
        Integer pageNum = request.getPageNum();
        Integer pageSize = request.getPageSize();
        DistributionInviteNewPageRequest pageRequest = KsBeanUtil.convert(request, DistributionInviteNewPageRequest.class);
        try {
            while (flag) {
                if(CollectionUtils.isNotEmpty(request.getIdList())){
                    pageNum = 0;
                    pageSize = request.getIdList().size();
                    flag = false;
                }
                pageRequest.setPageNum(pageNum);
                pageRequest.setPageSize(pageSize);
                pageRequest.putSort("registerTime", SortType.DESC.toValue());
                DistributionInviteNewPageResponse context = distributionInviteNewQueryProvider.findDistributionInviteNewRecord(pageRequest).getContext();
                List<DistributionInviteNewForPageVO> recordList = context.getRecordList();
                if (CollectionUtils.isEmpty(recordList)) {
                    flag = false;
                    log.info("==========ES初始化邀新记录结束，结束pageNum:{}==============",pageNum);
                }else {
                    List<EsInviteNewRecord> newInfoList = KsBeanUtil.convert(recordList, EsInviteNewRecord.class);
                    saveAll(newInfoList);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化邀新记录列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040007, new Object[]{pageNum});
        }
    }

    private void saveAll(List<EsInviteNewRecord> newInfoList) {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.INVITE_NEW_RECORD, mapping);
        esDistributionInviteNewRepository.saveAll(newInfoList);
    }

    /**
     * 新增邀新记录
     *
     * @param request
     */
    public void addInviteNewRecord(EsDistributionInviteNewSaveRequest request) {
        List<DistributionInviteNewRecordVO> inviteNewRecordVOList = request.getInviteNewRecordVOList();
        if(CollectionUtils.isEmpty(inviteNewRecordVOList)){
            return;
        }
        List<EsInviteNewRecord> esInviteNewRecords = KsBeanUtil.convertList(inviteNewRecordVOList, EsInviteNewRecord.class);
        saveAll(esInviteNewRecords);
    }
}
