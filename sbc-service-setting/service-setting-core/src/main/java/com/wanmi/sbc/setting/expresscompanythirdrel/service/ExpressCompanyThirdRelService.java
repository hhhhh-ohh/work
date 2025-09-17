package com.wanmi.sbc.setting.expresscompanythirdrel.service;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.request.expresscompany.ExpressCompanyQueryRequest;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.*;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyQueryRequest;
import com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDTO;
import com.wanmi.sbc.setting.bean.dto.ExpressCompanyThirdRelDetailDTO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelDetailVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyThirdRelVO;
import com.wanmi.sbc.setting.bean.vo.ExpressCompanyVO;
import com.wanmi.sbc.setting.expresscompany.model.root.ExpressCompany;
import com.wanmi.sbc.setting.expresscompany.repository.ExpressCompanyRepository;
import com.wanmi.sbc.setting.expresscompany.service.ExpressCompanyWhereCriteriaBuilder;
import com.wanmi.sbc.setting.expresscompanythirdrel.repository.ExpressCompanyThirdRelRepository;
import com.wanmi.sbc.setting.expresscompanythirdrel.root.ExpressCompanyThirdRel;
import com.wanmi.sbc.setting.thirdexpresscompany.repository.ThirdExpressCompanyRepository;
import com.wanmi.sbc.setting.thirdexpresscompany.root.ThirdExpressCompany;
import com.wanmi.sbc.setting.thirdexpresscompany.service.ThirdExpressCompanyWhereCriteriaBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 平台和第三方代销平台物流公司映射服务
 * @author malianfeng
 * @date 2022/4/26 17:10
 */
@Service("ExpressCompanyThirdRelService")
public class ExpressCompanyThirdRelService {

	@Autowired private ExpressCompanyThirdRelRepository expressCompanyThirdRelRepository;
	@Autowired private ThirdExpressCompanyRepository thirdExpressCompanyRepository;
	@Autowired private ExpressCompanyRepository expressionRepository;

	/**
	 * 查询平台与第三方平台物流公司映射列表
	 * @param queryReq
	 * @return
	 */
	public List<ExpressCompanyThirdRel> list(ExpressCompanyThirdRelQueryRequest queryReq) {
		return expressCompanyThirdRelRepository.findAll(ExpressCompanyThirdRelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 查询平台与第三方平台物流公司详情映射列表
	 * @param queryReq
	 * @return
	 */
	public List<ExpressCompanyThirdRelDetailVO> listWithDetail(ExpressCompanyThirdRelDetailQueryRequest queryReq) {
		List<ExpressCompanyThirdRelDetailDTO> dtoList = expressCompanyThirdRelRepository.listWithDetail(queryReq.getExpressCompanyId(), queryReq.getSellPlatformType());
		if (CollectionUtils.isNotEmpty(dtoList)) {
			return KsBeanUtil.convert(dtoList, ExpressCompanyThirdRelDetailVO.class);
		}
		return null;
	}

	/**
	 * @description  根据平台物流公司Id 查询代销渠道公司物流信息
	 * @author  wur
	 * @date: 2022/4/28 10:34
	 * @return
	 **/
	public List<ExpressCompanyThirdRelDetailVO> queryThirdExpress(ThirdExpressCompanyListRequest queryReq) {
		List<ExpressCompanyThirdRelDetailDTO> dtoList = expressCompanyThirdRelRepository.queryThirdExpress(queryReq.getExpressCompanyId(), queryReq.getSellPlatformType());
		if (CollectionUtils.isNotEmpty(dtoList)) {
			return KsBeanUtil.convert(dtoList, ExpressCompanyThirdRelDetailVO.class);
		}
		return null;
	}

	/**
	 * @description   根据平台物流公司Code 查询代销渠道公司物流信息
	 * @author  wur
	 * @date: 2022/4/28 10:35
	 * @return
	 **/
	public List<ExpressCompanyThirdRelDetailVO> listWithDetailByCode(ThirdExpressCompanyListByCodeRequest queryReq) {
		List<ExpressCompanyThirdRelDetailDTO> dtoList = expressCompanyThirdRelRepository.listWithDetailByExpressCode(queryReq.getExpressCode(), queryReq.getSellPlatformType());
		if (CollectionUtils.isNotEmpty(dtoList)) {
			return KsBeanUtil.convert(dtoList, ExpressCompanyThirdRelDetailVO.class);
		}
		return null;
	}

	/**
	 * @description    根据第三方平台类型查询 查询已经绑定的平台物流公司
	 * @author  wur
	 * @date: 2022/4/28 10:37
	 * @return
	 **/
	public List<ExpressCompanyThirdRelDetailDTO> queryExpress(ExpressCompanyListBySellTypeRequest queryReq) {
		return expressCompanyThirdRelRepository.queryExpress(queryReq.getExpressCompanyId(), queryReq.getSellPlatformType());
	}

	/**
	 * 将实体包装成VO
	 * @param expressCompanyThirdRel
	 * @return
	 */
	public ExpressCompanyThirdRelVO wrapperVo(ExpressCompanyThirdRel expressCompanyThirdRel) {
		if (expressCompanyThirdRel != null){
			ExpressCompanyThirdRelVO expressCompanyThirdRelVO = new ExpressCompanyThirdRelVO();
			KsBeanUtil.copyPropertiesThird(expressCompanyThirdRel, expressCompanyThirdRelVO);
			return expressCompanyThirdRelVO;
		}
		return null;
	}

	/**
	 * @description  封装平台物流公司信息
	 * @author  wur
	 * @date: 2022/4/28 10:46
	 * @param thirdRelDetailDTO
	 * @return
	 **/
	public ExpressCompanyVO wrapperSystemVo(ExpressCompanyThirdRelDetailDTO thirdRelDetailDTO) {
		if (thirdRelDetailDTO != null){
			ExpressCompanyVO expressCompanyVO = new ExpressCompanyVO();
			expressCompanyVO.setExpressCompanyId(thirdRelDetailDTO.getExpressCompanyId());
			expressCompanyVO.setExpressCode(thirdRelDetailDTO.getExpressCompanyCode());
			expressCompanyVO.setExpressName(thirdRelDetailDTO.getExpressCompanyName());
			return expressCompanyVO;
		}
		return null;
	}


	/**
	 * 平台与第三方平台物流公司映射关系批量保存
	 * @param request
	 */
	@Transactional(rollbackFor = Exception.class)
    public void batchSave(ExpressCompanyThirdRelBatchSaveRequest request) {
		List<ExpressCompanyThirdRelDTO> thirdRelList = request.getThirdRelList();
		// 构造实体列表
		List<ExpressCompanyThirdRel> entityList = KsBeanUtil.convert(thirdRelList, ExpressCompanyThirdRel.class);
		entityList.forEach(item -> {
			item.setSellPlatformType(request.getSellPlatformType());
			item.setCreateTime(LocalDateTime.now());
			item.setDelFlag(DeleteFlag.NO);
		});
		// 先批量删除
		expressCompanyThirdRelRepository.deleteBySellPlatformType(request.getSellPlatformType());
		// 再批量保存
		expressCompanyThirdRelRepository.saveAll(entityList);
    }

	@Transactional
	public void mapping(SellPlatformType sellPlatformType) {
		//查询已映射的信息
		ExpressCompanyThirdRelQueryRequest queryRequest = new ExpressCompanyThirdRelQueryRequest();
		queryRequest.setSellPlatformType(sellPlatformType);
		queryRequest.setDelFlag(DeleteFlag.NO);
		List<ExpressCompanyThirdRel>  relList = this.list(queryRequest);
		Set<Long> thirdSet = relList.stream().map(ExpressCompanyThirdRel::getThirdExpressCompanyId).filter(Objects::nonNull).collect(Collectors.toSet());
		Set<Long> expressSet = relList.stream().map(ExpressCompanyThirdRel::getExpressCompanyId).filter(Objects::nonNull).collect(Collectors.toSet());
		//查询第三方渠道快递公司
		ThirdExpressCompanyQueryRequest queryReq = new ThirdExpressCompanyQueryRequest();
		queryReq.setDelFlag(DeleteFlag.NO);
		queryReq.setSellPlatformType(sellPlatformType);
		Map<String, Long> thirdMap = thirdExpressCompanyRepository.findAll(ThirdExpressCompanyWhereCriteriaBuilder.build(queryReq)).stream()
				.filter(t -> !thirdSet.contains(t.getId())).collect(Collectors.toMap(ThirdExpressCompany::getExpressName, ThirdExpressCompany::getId, (a,b) -> a));
		//查询本地快递公司
		ExpressCompanyQueryRequest exRequest = new ExpressCompanyQueryRequest();
		exRequest.setDelFlag(DeleteFlag.NO);
		List<ExpressCompany> expressCompanies = expressionRepository.findAll(ExpressCompanyWhereCriteriaBuilder.build(exRequest));
		//映射，根据名称匹配
		List<ExpressCompanyThirdRel> entityList = new ArrayList<>();
		expressCompanies.stream()
				//非已映射的且名称匹配的
				.filter(t -> !expressSet.contains(t.getExpressCompanyId()) && thirdMap.containsKey(t.getExpressName()))
				.forEach(t -> {
					ExpressCompanyThirdRel rel = new ExpressCompanyThirdRel();
					rel.setCreateTime(LocalDateTime.now());
					rel.setExpressCompanyId(t.getExpressCompanyId());
					rel.setThirdExpressCompanyId(thirdMap.get(t.getExpressName()));
					rel.setSellPlatformType(sellPlatformType);
					rel.setUpdateTime(LocalDateTime.now());
					rel.setDelFlag(DeleteFlag.NO);
					entityList.add(rel);
				});
		expressCompanyThirdRelRepository.saveAll(entityList);
	}
}
