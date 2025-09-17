package com.wanmi.sbc.elastic.groupon.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.groupon.*;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.groupon.model.root.EsGrouponActivity;
import com.wanmi.sbc.elastic.groupon.repository.EsGrouponActivityRepository;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityPageRequest;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.EsGrouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2020/12/12 19:01
 * @description <p> </p>
 */
@Slf4j
@Service
public class EsGrouponActivityService {

    @Autowired
    private EsGrouponActivityRepository esGrouponActivityRepository;

    @Autowired
    private GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esGrouponActivity.json")
    private Resource mapping;

    public void add(EsGrouponActivityAddReqquest request) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();

        List<EsGrouponActivityVO> esGrouponActivityVOList = request.getEsGrouponActivityVOList();

        if (CollectionUtils.isEmpty(esGrouponActivityVOList)) {
            return;
        }
        List<EsGrouponActivity> newList = KsBeanUtil.convert(esGrouponActivityVOList, EsGrouponActivity.class);
        esGrouponActivityRepository.saveAll(newList);
    }

    /**
     * 初始化拼团列表数据
     *
     * @param request
     */
    public void init(EsGrouponActivityInitRequest request) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        boolean flg = true;
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        List<String> idList = request.getIdList();
        GrouponActivityPageRequest pageRequest = new GrouponActivityPageRequest();
        try {
            while (flg) {
                if(CollectionUtils.isNotEmpty(idList)){
                    pageRequest.setGrouponActivityIdList(idList);
                    pageSize = idList.size();
                    pageNum = 0;
                    flg = false;
                }
                pageRequest.putSort("createTime", SortType.DESC.toValue());
                pageRequest.setPageNum(pageNum);
                pageRequest.setPageSize(pageSize);
                List<GrouponActivityVO> vos = grouponActivityQueryProvider.page(pageRequest).getContext().getGrouponActivityVOPage().getContent();
                if (CollectionUtils.isEmpty(vos)) {
                    flg = false;
                    log.info("==========ES初始化拼团列表，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsGrouponActivity> newInfos = KsBeanUtil.convert(vos, EsGrouponActivity.class);
                    esGrouponActivityRepository.saveAll(newInfos);
                    log.info("==========ES初始化拼团列表成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化拼团列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040013, new Object[]{pageNum});
        }


    }

    public void deleteById(String id) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        Optional<EsGrouponActivity> activity = esGrouponActivityRepository.findById(id);
        activity.ifPresent(entity -> {
            entity.setDelFlag(DeleteFlag.YES);
            esGrouponActivityRepository.save(entity);
        });
    }

    public void batchStickyMarketing(EsGrouponActivityBatchStickyRequest request) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        List<String> ids = request.getGrouponActivityIdList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        Iterable<EsGrouponActivity> grouponActivitiesList = esGrouponActivityRepository.findAllById(ids);

        List<EsGrouponActivity> esGrouponActivities = Lists.newArrayList(grouponActivitiesList);

        List<EsGrouponActivity> newList = esGrouponActivities.stream().peek(entity ->
                entity.setSticky(request.getSticky())
        ).collect(Collectors.toList());

        esGrouponActivityRepository.saveAll(newList);

    }

    public void batchRefuseMarketing(EsGrouponActivityRefuseRequest request) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        Optional<EsGrouponActivity> esGrouponActivity = esGrouponActivityRepository.findById(request.getGrouponActivityId());
        esGrouponActivity.ifPresent(entity -> {
            entity.setAuditStatus(AuditStatus.NOT_PASS);
            entity.setAuditFailReason(request.getAuditReason());
            esGrouponActivityRepository.save(entity);
        });

    }

    public void batchCheckMarketing(EsGrouponActivityBatchCheckRequest request) {
        //手动删除索引时，重新设置mapping
        createIndexAddMapping();
        Iterable<EsGrouponActivity> iterable = esGrouponActivityRepository.findAllById(request.getGrouponActivityIdList());
        List<EsGrouponActivity> esGrouponActivities = Lists.newArrayList(iterable);
        List<EsGrouponActivity> newList = Optional.of(esGrouponActivities)
                .orElseGet(Lists::newArrayList).stream()
                .peek(entity -> entity.setAuditStatus(AuditStatus.CHECKED))
                .collect(Collectors.toList());
        esGrouponActivityRepository.saveAll(newList);
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.GROUPON_ACTIVITY, mapping);
    }

}
