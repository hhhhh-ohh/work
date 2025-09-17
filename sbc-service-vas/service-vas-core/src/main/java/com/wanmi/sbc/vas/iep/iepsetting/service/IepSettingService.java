package com.wanmi.sbc.vas.iep.iepsetting.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.iep.iepsetting.IepSettingQueryRequest;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;
import com.wanmi.sbc.vas.iep.iepsetting.model.root.IepSetting;
import com.wanmi.sbc.vas.iep.iepsetting.repository.IepSettingRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>企业购设置业务逻辑</p>
 *
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Service("IepSettingService")
public class IepSettingService {
    @Autowired
    private IepSettingRepository iepSettingRepository;

    @Autowired
    private RedisUtil redisService;

    /**
     * 新增企业购设置
     *
     * @author 宋汉林
     */
    @Transactional
    public IepSetting add(IepSetting entity) {
        iepSettingRepository.save(entity);
        return entity;
    }

    /**
     * 修改企业购设置
     *
     * @author 宋汉林
     */
    @Transactional
    public IepSetting modify(IepSetting entity) {
        IepSetting iepSetting = this.getOne(entity.getId());
        iepSetting.setUpdatePerson(entity.getUpdatePerson());
        iepSetting.setEnterpriseCustomerRegisterContent(entity.getEnterpriseCustomerRegisterContent());
        iepSetting.setEnterpriseGoodsAuditFlag(entity.getEnterpriseGoodsAuditFlag());
        iepSetting.setEnterpriseCustomerAuditFlag(entity.getEnterpriseCustomerAuditFlag());
        iepSetting.setEnterpriseCustomerName(entity.getEnterpriseCustomerName());
        iepSetting.setEnterprisePriceName(entity.getEnterprisePriceName());
        iepSetting.setEnterpriseCustomerLogo(entity.getEnterpriseCustomerLogo());
        iepSettingRepository.save(iepSetting);
        // 缓存到redis中
        redisService.setString(CacheKeyConstant.IEP_SETTING, JSONObject.toJSONString(iepSetting));
        return iepSetting;
    }

    /**
     * 单个删除企业购设置
     *
     * @author 宋汉林
     */
    @Transactional
    public void deleteById(IepSetting entity) {
        iepSettingRepository.save(entity);
    }

    /**
     * 批量删除企业购设置
     *
     * @author 宋汉林
     */
    @Transactional
    public void deleteByIdList(List<IepSetting> infos) {
        iepSettingRepository.saveAll(infos);
    }

    /**
     * 单个查询企业购设置
     *
     * @author 宋汉林
     */
    public IepSetting getOne(String id) {

        IepSetting iepSetting = iepSettingRepository.findByIdAndDelFlag(id, DeleteFlag.NO);
        if  (Objects.isNull(iepSetting)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "企业购设置不存在");
        }
        return iepSetting;
    }

    /**
     * 分页查询企业购设置
     *
     * @author 宋汉林
     */
    public Page<IepSetting> page(IepSettingQueryRequest queryReq) {
        return iepSettingRepository.findAll(
                IepSettingWhereCriteriaBuilder.build(queryReq),
                queryReq.getPageRequest());
    }

    /**
     * 列表查询企业购设置
     *
     * @author 宋汉林
     */
    public List<IepSetting> list(IepSettingQueryRequest queryReq) {
        return iepSettingRepository.findAll(IepSettingWhereCriteriaBuilder.build(queryReq));
    }

	/**
	 * 缓存企业购信息
	 */
	public IepSetting cacheIepSetting() {
        IepSetting iepSetting = this.findTopOne();
        redisService.setString(CacheKeyConstant.IEP_SETTING, JSONObject.toJSONString(iepSetting));
        return iepSetting;
    }

    /**
     * 查询第一个企业购设置信息
     */
    public IepSetting findTopOne() {
        List<IepSetting> iepSetting = iepSettingRepository.findDelFlag(DeleteFlag.NO);
        if  (CollectionUtils.isEmpty(iepSetting)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "企业购设置不存在");
        }
        return iepSetting.get(0);
    }

    /**
     * 将实体包装成VO
     *
     * @author 宋汉林
     */
    public IepSettingVO wrapperVo(IepSetting iepSetting) {
        if (iepSetting != null) {
            IepSettingVO iepSettingVO = KsBeanUtil.convert(iepSetting, IepSettingVO.class);
            return iepSettingVO;
        }
        return null;
    }
}

