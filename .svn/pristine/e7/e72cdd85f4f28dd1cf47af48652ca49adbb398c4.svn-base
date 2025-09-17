package com.wanmi.sbc.elastic.sensitivewords.service;

import com.wanmi.sbc.common.annotation.WmResource;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.request.sensitivewords.EsSensitiveWordsInitRequest;
import com.wanmi.sbc.elastic.api.request.sensitivewords.EsSensitiveWordsSaveRequest;
import com.wanmi.sbc.elastic.base.service.EsBaseService;
import com.wanmi.sbc.elastic.bean.enums.ElasticErrorCodeEnum;
import com.wanmi.sbc.elastic.bean.vo.sensitivewords.EsSensitiveWordsVO;
import com.wanmi.sbc.elastic.sensitivewords.model.root.EsSensitiveWords;
import com.wanmi.sbc.elastic.sensitivewords.repository.EsSensitiveWordsRepository;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.SensitiveWordsQueryProvider;
import com.wanmi.sbc.setting.api.request.SensitiveWordsQueryRequest;
import com.wanmi.sbc.setting.bean.vo.SensitiveWordsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author houshuai
 * @date 2020/12/11 18:09
 * @description <p> 敏感词service </p>
 */
@Slf4j
@Service
public class EsSensitiveWordsService {

    @Autowired
    private EsSensitiveWordsRepository esSensitiveWordsRepository;

    @Autowired
    private SensitiveWordsQueryProvider queryProvider;

    @Autowired
    private EsBaseService esBaseService;

    @WmResource("mapping/EsSensitiveWords.json")
    private Resource mapping;

    /**
     * 新增敏感词库到es
     *
     * @param request
     */
    public void addSensitiveWords(EsSensitiveWordsSaveRequest request) {
        List<EsSensitiveWordsVO> sensitiveWordsList = request.getSensitiveWordsList();
        if (CollectionUtils.isEmpty(sensitiveWordsList)) {
            return;
        }
        List<EsSensitiveWords> newList = KsBeanUtil.convert(sensitiveWordsList, EsSensitiveWords.class);
        saveAll(newList);
    }

    /**
     * 初始化敏感词
     * @param request
     */
    public void init(EsSensitiveWordsInitRequest request) {
        boolean flg = true;
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();

        SensitiveWordsQueryRequest queryRequest = KsBeanUtil.convert(request, SensitiveWordsQueryRequest.class);
        try {
            while (flg) {
                if(CollectionUtils.isNotEmpty(request.getIdList())){
                    queryRequest.setSensitiveIdList(request.getIdList());
                    pageNum = 0;
                    pageSize = request.getIdList().size();
                    flg = false;
                }
                queryRequest.putSort("createTime", SortType.DESC.toValue());
                queryRequest.setPageNum(pageNum);
                queryRequest.setPageSize(pageSize);
                List<SensitiveWordsVO> sensitiveWordsVOList = queryProvider.page(queryRequest).getContext().getSensitiveWordsVOPage().getContent();
                if (CollectionUtils.isEmpty(sensitiveWordsVOList)) {
                    flg = false;
                    log.info("==========ES初始化敏感词库列表，结束pageNum:{}==============", pageNum);
                } else {
                    List<EsSensitiveWords> newInfos = KsBeanUtil.convert(sensitiveWordsVOList, EsSensitiveWords.class);
                    saveAll(newInfos);
                    log.info("==========ES初始化敏感词库列表成功，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.error("==========ES初始化敏感词库列表异常，异常pageNum:{}==============", pageNum, e);
            throw new SbcRuntimeException(ElasticErrorCodeEnum.K040006, new Object[]{pageNum});
        }


    }

    private void saveAll(List<EsSensitiveWords> newInfos) {
        //手动删除索引时，重新设置mapping
        esBaseService.existsOrCreate(EsConstants.SENSITIVE_WORDS, mapping);
        esSensitiveWordsRepository.saveAll(newInfos);
    }
}
