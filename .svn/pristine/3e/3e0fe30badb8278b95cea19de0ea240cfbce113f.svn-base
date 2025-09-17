package com.wanmi.sbc.vas.recommend.filterrulessetting.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.filterrulessetting.FilterRulesSettingQueryRequest;
import com.wanmi.sbc.vas.bean.enums.recommen.FilterRulesType;
import com.wanmi.sbc.vas.bean.vo.recommend.FilterRulesSettingVO;
import com.wanmi.sbc.vas.recommend.filterrulessetting.model.root.FilterRulesSetting;
import com.wanmi.sbc.vas.recommend.filterrulessetting.repository.FilterRulesSettingRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>业务逻辑</p>
 * @author zhongjichuan
 * @date 2020-11-26 16:32:49
 */
@Service("FilterRulesSettingService")
public class FilterRulesSettingService {
	@Autowired
	private FilterRulesSettingRepository filterRulesSettingRepository;

	/**
	 * 新增
	 * @author zhongjichuan
	 */
	@Transactional
	public FilterRulesSetting add(FilterRulesSetting entity) {
		filterRulesSettingRepository.save(entity);
		return entity;
	}

	/**
	 * 修改
	 * @author zhongjichuan
	 */
	@Transactional
	public List<FilterRulesSetting> modify(List<FilterRulesSetting> filterRulesSettingList) {
		Map<FilterRulesType, FilterRulesSetting> filterRulesSettingMap
				= filterRulesSettingList.stream().collect(Collectors.toMap(FilterRulesSetting::getType, Function.identity()));
		List<FilterRulesSetting> oldList = this.list(FilterRulesSettingQueryRequest.builder().delFlag(DeleteFlag.NO).build());
		oldList.forEach(v -> {
			FilterRulesSetting filterRulesSetting = filterRulesSettingMap.get(v.getType());
			v.setDayNum(filterRulesSetting.getDayNum());
			v.setNum(filterRulesSetting.getNum());
			v.setUpdateTime(filterRulesSetting.getUpdateTime());
			v.setUpdatePerson(filterRulesSetting.getUpdatePerson());
		});
		filterRulesSettingRepository.saveAll(oldList);
		return oldList;
	}

	/**
	 * 单个删除
	 * @author zhongjichuan
	 */
	@Transactional
	public void deleteById(FilterRulesSetting entity) {
		filterRulesSettingRepository.save(entity);
	}

	/**
	 * 批量删除
	 * @author zhongjichuan
	 */
	@Transactional
	public void deleteByIdList(List<FilterRulesSetting> infos) {
		filterRulesSettingRepository.saveAll(infos);
	}

	/**
	 * 单个查询
	 * @author zhongjichuan
	 */
	public FilterRulesSetting getOne(Integer id){
		return filterRulesSettingRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "不存在"));
	}

	/**
	 * 分页查询
	 * @author zhongjichuan
	 */
	public Page<FilterRulesSetting> page(FilterRulesSettingQueryRequest queryReq){
		return filterRulesSettingRepository.findAll(
				FilterRulesSettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询
	 * @author zhongjichuan
	 */
	public List<FilterRulesSetting> list(FilterRulesSettingQueryRequest queryReq){
		List<FilterRulesSetting> filterRulesSettingList = filterRulesSettingRepository.findAll(FilterRulesSettingWhereCriteriaBuilder.build(queryReq));
		if (CollectionUtils.isEmpty(filterRulesSettingList)){
			filterRulesSettingList.add(new FilterRulesSetting(1, 1000, FilterRulesType.SHOWED, DeleteFlag.NO, LocalDateTime.now()));
			filterRulesSettingList.add(new FilterRulesSetting(7, 500, FilterRulesType.CLICKED, DeleteFlag.NO, LocalDateTime.now()));
			filterRulesSettingList.add(new FilterRulesSetting(30, 200, FilterRulesType.PURCHASED, DeleteFlag.NO, LocalDateTime.now()));
			filterRulesSettingList = filterRulesSettingRepository.saveAll(filterRulesSettingList);
		}
		return filterRulesSettingList;
	}

	/**
	 * 将实体包装成VO
	 * @author zhongjichuan
	 */
	public FilterRulesSettingVO wrapperVo(FilterRulesSetting filterRulesSetting) {
		if (filterRulesSetting != null){
			FilterRulesSettingVO filterRulesSettingVO = KsBeanUtil.convert(filterRulesSetting, FilterRulesSettingVO.class);
			return filterRulesSettingVO;
		}
		return null;
	}
}

