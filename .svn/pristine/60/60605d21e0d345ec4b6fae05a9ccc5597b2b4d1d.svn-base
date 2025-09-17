package com.wanmi.sbc.setting.sensitivewords.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.SensitiveWordsDelByIdListRequest;
import com.wanmi.sbc.setting.api.request.SensitiveWordsDeleteResponse;
import com.wanmi.sbc.setting.api.request.SensitiveWordsQueryRequest;
import com.wanmi.sbc.setting.bean.vo.SensitiveWordsVO;
import com.wanmi.sbc.setting.sensitivewords.model.root.SensitiveWords;
import com.wanmi.sbc.setting.sensitivewords.repository.SensitiveWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>业务逻辑</p>
 *
 * @author wjj
 * @date 2019-02-22 16:09:48
 */
@Service("SensitiveWordsService")
public class SensitiveWordsService {
    @Autowired
    private SensitiveWordsRepository sensitiveWordsRepository;

    @Autowired
    private RedisUtil redisService;

    /**
     * 新增
     *
     * @author wjj
     */
    @Transactional
    public List<SensitiveWordsVO> add(List<SensitiveWords> entityList) {
        List<SensitiveWords> sensitiveWords = sensitiveWordsRepository.saveAll(entityList);
        //更新redis
        refushBadWordRedis();
        return KsBeanUtil.convert(sensitiveWords, SensitiveWordsVO.class);
    }

    /**
     * 修改
     *
     * @author wjj
     */
    @Transactional
    public SensitiveWords modify(SensitiveWords entity) {
        SensitiveWords sensitiveWords = sensitiveWordsRepository.findById(entity.getSensitiveId()).orElseThrow(() ->
                new SbcRuntimeException(CommonErrorCodeEnum.K000003 ,new Object[] {"敏感词不存在"},
                        getDeleteIndex(String.valueOf(entity.getSensitiveId()))));
        if(DeleteFlag.YES.equals(sensitiveWords.getDelFlag())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003, new Object[]{"敏感词不存在"},
                    getDeleteIndex(String.valueOf(entity.getSensitiveId())));
        }
        sensitiveWordsRepository.save(entity);
        //更新redis
        refushBadWordRedis();
        return entity;
    }

    /**
     * 单个删除
     *
     * @author wjj
     */
    @Transactional
    public void deleteById(Long id) {
        sensitiveWordsRepository.deleteById(id);
        //更新redis
        refushBadWordRedis();
    }

    /**
     * 批量删除
     *
     * @author wjj
     */
    @Transactional
    public SensitiveWordsDeleteResponse deleteByIdList(SensitiveWordsDelByIdListRequest delByIdListRequest) {
        Integer count = sensitiveWordsRepository.deleteSensitiveWordsByIds(DeleteFlag.YES, delByIdListRequest.getDeleteUser(),
                delByIdListRequest.getDeleteTime(), delByIdListRequest.getSensitiveIdList());
        if (Objects.isNull(count)) {
            return SensitiveWordsDeleteResponse.builder()
                    .sensitiveWordsList(Collections.emptyList())
                    .build();
        }
        List<SensitiveWords> sensitiveWords = sensitiveWordsRepository.findAllById(delByIdListRequest.getSensitiveIdList());
        List<SensitiveWordsVO> newList = KsBeanUtil.convert(sensitiveWords, SensitiveWordsVO.class);
        //更新redis
        refushBadWordRedis();
//		sensitiveWordsRepository.delete(ids.stream().map(id -> {
//			SensitiveWords entity = new SensitiveWords();
//			entity.setSensitiveId(id);
//			return entity;
//		}).collect(Collectors.toList()));
        return SensitiveWordsDeleteResponse.builder()
                .sensitiveWordsList(newList)
                .build();
    }

    /**
     * 单个查询
     *
     * @author wjj
     */
    public SensitiveWords getById(Long id) {
        SensitiveWords sensitiveWords = sensitiveWordsRepository.findById(id).orElse(null);
        if(sensitiveWords == null || DeleteFlag.YES.equals(sensitiveWords.getDelFlag())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003,new Object[] {"敏感词不存在"},
                    getDeleteIndex(String.valueOf(id)));
        }
        return sensitiveWords;
    }

    /**
     * 分页查询
     *
     * @author wjj
     */
    public Page<SensitiveWords> page(SensitiveWordsQueryRequest queryReq) {
        return sensitiveWordsRepository.findAll(
                SensitiveWordsWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询
     *
     * @author wjj
     */
    public List<SensitiveWords> list(SensitiveWordsQueryRequest queryReq) {
        return sensitiveWordsRepository.findAll(SensitiveWordsWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author wjj
     */
    public SensitiveWordsVO wrapperVo(SensitiveWords sensitiveWords) {
        if (sensitiveWords != null) {
            SensitiveWordsVO sensitiveWordsVO = new SensitiveWordsVO();
            KsBeanUtil.copyPropertiesThird(sensitiveWords, sensitiveWordsVO);
            return sensitiveWordsVO;
        }
        return null;
    }

    /**
     * 刷新敏感词redis
     */
    private void refushBadWordRedis() {
        String badWords = this.getBadWordsFromDB();
        redisService.delete(CacheKeyConstant.BAD_WORD);
        redisService.setString(CacheKeyConstant.BAD_WORD, badWords);
    }

    /**
     * 获取敏感词字符串
     * @return
     */
    public String getBadWordsFromDB(){

        SensitiveWordsQueryRequest queryRequest = new SensitiveWordsQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO);
        List<SensitiveWords> sensitiveWords = this.list(queryRequest);
        StringBuilder sbf = new StringBuilder();
        for(SensitiveWords words:sensitiveWords){
            sbf.append(words.getSensitiveWords()).append(',');
        }
        return sbf.toString();
    }

    /**
     * 拼凑删除es-提供给findOne去调
     * @param id 编号
     * @return "{index}:{id}"
     */
    private Object getDeleteIndex(String id){
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.SENSITIVE_WORDS, id);
    }
}
