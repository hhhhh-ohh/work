package com.wanmi.sbc.empower.wechatshareset.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.bean.vo.WechatShareSetVO;
import com.wanmi.sbc.empower.wechatshareset.model.root.WechatShareSet;
import com.wanmi.sbc.empower.wechatshareset.repository.WechatShareSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>微信分享配置业务逻辑</p>
 *
 * @author lq
 * @date 2019-11-05 16:15:54
 */
@Service("WechatShareSetService")
public class WechatShareSetService {

    @Autowired
    private WechatShareSetRepository wechatShareSetRepository;

    /**
     * 新增微信分享配置
     *
     * @author lq
     */
    @Transactional
    public void add(WechatShareSet entity) {
        WechatShareSet wechatSet;
        wechatSet = wechatShareSetRepository.findByStoreId(entity.getStoreId()).orElse(null);
        if (null != wechatSet) {
            entity.setShareSetId(wechatSet.getShareSetId());
            entity.setUpdateTime(LocalDateTime.now());
        } else {
            entity.setCreateTime(LocalDateTime.now());
        }
        wechatShareSetRepository.save(entity);
    }

    /**
     * 单个查询微信分享配置
     *
     * @author lq
     */
    public WechatShareSet getById(String id) {
        return wechatShareSetRepository.findById(id).orElse(null);
    }
    /**
     * 查询微信分享配置
     *
     * @param
     * @return
     */
    @Transactional
    public WechatShareSet getInfo(WechatShareSet wechatShareSet) {
        WechatShareSet wechatSet = wechatShareSetRepository.findByStoreId(wechatShareSet.getStoreId()).orElse(null);
        if (Objects.isNull(wechatSet)) {
            wechatSet = new WechatShareSet();
            wechatSet.setCreateTime(LocalDateTime.now());
            wechatSet.setOperatePerson(wechatShareSet.getOperatePerson());
            return wechatShareSetRepository.saveAndFlush(wechatSet);
        } else {
            return wechatSet;
        }
    }

    /**
     * 门店id查询微信分享配置
     *
     * @param
     * @return
     */
    public WechatShareSet getInfoByStoreId(Long storeId) {
        return wechatShareSetRepository.findByStoreId(storeId).orElse(null);
    }


    /**
     * 将实体包装成VO
     *
     * @author lq
     */
    public WechatShareSetVO wrapperVo(WechatShareSet wechatShareSet) {
        if (wechatShareSet != null) {
            WechatShareSetVO wechatShareSetVO = new WechatShareSetVO();
            KsBeanUtil.copyPropertiesThird(wechatShareSet, wechatShareSetVO);
            return wechatShareSetVO;
        }
        return null;
    }
}
