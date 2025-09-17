package com.wanmi.sbc.setting.thirdexpresscompany.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyQueryRequest;
import com.wanmi.sbc.setting.bean.vo.ThirdExpressCompanyVO;
import com.wanmi.sbc.setting.thirdexpresscompany.repository.ThirdExpressCompanyRepository;
import com.wanmi.sbc.setting.thirdexpresscompany.root.ThirdExpressCompany;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description 第三方代销平台物流公司服务
 * @author malianfeng
 * @date 2022/4/26 17:30
 */
@Service("ThirdExpressCompanyService")
public class ThirdExpressCompanyService {

	@Autowired private ThirdExpressCompanyRepository thirdExpressCompanyRepository;

	/**
	 * 批量新增查询第三方平台物流公司
	 * @param thirdExpressCompanies
	 * @return
	 */
	@Transactional
	public void addBatch(List<ThirdExpressCompany> thirdExpressCompanies){
		if(CollectionUtils.isNotEmpty(thirdExpressCompanies)) {
			ThirdExpressCompanyQueryRequest queryRequest = new ThirdExpressCompanyQueryRequest();
			queryRequest.setSellPlatformType(thirdExpressCompanies.get(0).getSellPlatformType());
			queryRequest.setDelFlag(DeleteFlag.NO);
			Map<String, Long> map = this.list(queryRequest).stream().collect(Collectors.toMap(ThirdExpressCompany::getExpressCode, ThirdExpressCompany::getId));
			//如果存在，用原值
			if(MapUtils.isNotEmpty(map)) {
				thirdExpressCompanies.forEach(thirdExpressCompany -> {
					if (map.containsKey(thirdExpressCompany.getExpressCode())) {
						thirdExpressCompany.setId(map.get(thirdExpressCompany.getExpressCode()));
					}
				});
			}
		}
		thirdExpressCompanyRepository.saveAll(thirdExpressCompanies);
	}

	/**
	 * 查询第三方平台物流公司列表
	 * @param queryReq
	 * @return
	 */
	public List<ThirdExpressCompany> list(ThirdExpressCompanyQueryRequest queryReq) {
		return thirdExpressCompanyRepository.findAll(ThirdExpressCompanyWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @param thirdExpressCompany
	 * @return
	 */
	public ThirdExpressCompanyVO wrapperVo(ThirdExpressCompany thirdExpressCompany) {
		if (thirdExpressCompany != null){
			ThirdExpressCompanyVO thirdExpressCompanyVO = new ThirdExpressCompanyVO();
			KsBeanUtil.copyPropertiesThird(thirdExpressCompany, thirdExpressCompanyVO);
			return thirdExpressCompanyVO;
		}
		return null;
	}

}
