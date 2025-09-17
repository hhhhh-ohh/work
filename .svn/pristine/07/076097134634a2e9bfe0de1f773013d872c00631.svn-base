package com.wanmi.sbc.setting.country.service;

import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.country.PlatformCountryQueryRequest;
import com.wanmi.sbc.setting.bean.vo.PlatformCountryVO;
import com.wanmi.sbc.setting.country.model.root.PlatformCountry;
import com.wanmi.sbc.setting.country.repository.PlatformCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/4/26 15:27
 * @description
 *     <p>国家地区查询
 */
@Service
public class PlatformCountryService {

    @Autowired private PlatformCountryRepository platformCountryRepository;

    public List<PlatformCountryVO> findPlatformCountryList() {
        List<PlatformCountry> platformCountryList = platformCountryRepository.findAll();
        return KsBeanUtil.convert(platformCountryList, PlatformCountryVO.class);
    }

    /**
     * 分页查询物流公司
     *
     * @author chenli
     */
    public List<PlatformCountry> list(PlatformCountryQueryRequest queryReq) {
        return platformCountryRepository.findAll(
                PlatformCountryWhereCriteriaBuilder.build(queryReq));
    }

    /**
     * 将实体包装成VO
     *
     * @author chenli
     */
    public PlatformCountryVO wrapperVo(PlatformCountry platformCountry) {
        if (platformCountry != null) {
            PlatformCountryVO platformCountryVO = new PlatformCountryVO();
            KsBeanUtil.copyPropertiesThird(platformCountry, platformCountryVO);
            return platformCountryVO;
        }
        return null;
    }
}
