package com.wanmi.sbc.elastic.systemresource.service;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceInitRequest;
import com.wanmi.sbc.elastic.api.request.systemresource.EsSystemResourceSaveRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.bean.vo.systemresource.EsSystemResourceVO;
import com.wanmi.sbc.elastic.systemresource.model.root.EsSystemResource;
import com.wanmi.sbc.elastic.systemresource.repository.EsSystemResourceRepository;
import com.wanmi.sbc.setting.api.provider.systemresource.SystemResourceQueryProvider;
import com.wanmi.sbc.setting.api.request.systemresource.SystemResourcePageRequest;
import com.wanmi.sbc.setting.bean.vo.SystemResourceVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/14 14:57
 * @description <p> 资源素材 </p>
 */
@Slf4j
@Service
public class EsSystemResourceService {

    @Autowired
    private EsSystemResourceRepository esSystemResourceRepository;

    @Autowired
    private SystemResourceQueryProvider systemResourceQueryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/esSystemResource.json")
    private Resource mapping;

    /**
     * 新增资源素材
     *
     * @param request
     */
    public void add(EsSystemResourceSaveRequest request) {
        this.createIndexAddMapping();
        List<EsSystemResourceVO> systemResourceVOList = request.getSystemResourceVOList();
        if (CollectionUtils.isEmpty(systemResourceVOList)) {
            return;
        }
        List<EsSystemResource> esSystemResourceList = KsBeanUtil.convert(systemResourceVOList, EsSystemResource.class);
        esSystemResourceRepository.saveAll(esSystemResourceList);
    }

    /**
     * 初始化资源素材
     *
     * @param request
     */
    public void init(EsSystemResourceInitRequest request) {
        this.createIndexAddMapping();
        Boolean flg = Boolean.TRUE;
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        List<Long> idList = request.getIdList();
        SystemResourcePageRequest queryRequest = new SystemResourcePageRequest();
        try {
            while (flg) {
                if (CollectionUtils.isNotEmpty(idList)) {
                    queryRequest.setResourceIdList(idList);
                    pageSize = idList.size();
                    pageNum = 0;
                    flg = Boolean.FALSE;
                }
                queryRequest.putSort("createTime", SortType.DESC.toValue());
                queryRequest.setPageNum(pageNum);
                queryRequest.setPageSize(pageSize);
                List<SystemResourceVO> systemResourceVOList =
                        systemResourceQueryProvider.initEsPage(queryRequest).getContext().getSystemResourceVOPage().getContent();
                if (CollectionUtils.isEmpty(systemResourceVOList)) {
                    flg = Boolean.FALSE;
                    log.info("==========ES初始化资源素材列表，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsSystemResource> newInfos = KsBeanUtil.convert(systemResourceVOList, EsSystemResource.class);
                    esSystemResourceRepository.saveAll(newInfos);
                    log.info("==========ES初始化资源素材列表成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========ES初始化资源素材列表异常，异常pageNum:{}==============", pageNum);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040017, new Object[]{pageNum});
        }
    }

    /**
     * 创建索引以及mapping
     */
    private void createIndexAddMapping() {
        esBaseService.existsOrCreate(EsConstants.SYSTEM_RESOURCE, mapping);
    }
}
