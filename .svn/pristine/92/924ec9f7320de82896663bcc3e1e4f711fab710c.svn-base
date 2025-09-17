package com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingQueryRequest;
import com.wanmi.sbc.vas.bean.enums.recommen.StatisticalRangeType;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsCorrelationModelSettingVO;
import com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.model.root.GoodsCorrelationModelSetting;
import com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.repository.GoodsCorrelationModelSettingRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>业务逻辑</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Service("GoodsCorrelationModelSettingService")
public class GoodsCorrelationModelSettingService {
	@Autowired
	private GoodsCorrelationModelSettingRepository goodsCorrelationModelSettingRepository;

	/**
	 * 新增
	 * @author zhongjichuan
	 */
	@Transactional
	public GoodsCorrelationModelSetting add(GoodsCorrelationModelSetting entity) {
		goodsCorrelationModelSettingRepository.save(entity);
		return entity;
	}

	/**
	 * 修改
	 * @author zhongjichuan
	 */
	@Transactional
	public List<GoodsCorrelationModelSetting> modify(List<GoodsCorrelationModelSetting> settingList) {
		Optional<GoodsCorrelationModelSetting> optional = settingList.stream().filter(v -> BoolFlag.YES == v.getCheckStatus()).findFirst();
		GoodsCorrelationModelSetting goodsCorrelationModelSetting = optional.orElse(null);
    if (Objects.isNull(goodsCorrelationModelSetting)) {
      return Collections.emptyList();
    }
		Map<StatisticalRangeType, GoodsCorrelationModelSetting> settingMap
				= settingList.stream().collect(Collectors.toMap(GoodsCorrelationModelSetting::getStatisticalRange, Function.identity()));
		List<GoodsCorrelationModelSetting> oldList = this.list(GoodsCorrelationModelSettingQueryRequest.builder().delFlag(DeleteFlag.NO).build());
		oldList.forEach(v -> {
			GoodsCorrelationModelSetting setting = settingMap.get(v.getStatisticalRange());
			v.setSupport(goodsCorrelationModelSetting.getSupport());
			v.setConfidence(goodsCorrelationModelSetting.getConfidence());
			v.setLift(goodsCorrelationModelSetting.getLift());
			v.setCheckStatus(setting.getCheckStatus());
			v.setUpdateTime(setting.getUpdateTime());
			v.setUpdatePerson(setting.getUpdatePerson());
		});
		goodsCorrelationModelSettingRepository.saveAll(oldList);
		return oldList;
	}

	/**
	 * 单个删除
	 * @author zhongjichuan
	 */
	@Transactional
	public void deleteById(GoodsCorrelationModelSetting entity) {
		goodsCorrelationModelSettingRepository.save(entity);
	}

	/**
	 * 批量删除
	 * @author zhongjichuan
	 */
	@Transactional
	public void deleteByIdList(List<GoodsCorrelationModelSetting> infos) {
		goodsCorrelationModelSettingRepository.saveAll(infos);
	}

	/**
	 * 单个查询
	 * @author zhongjichuan
	 */
	public GoodsCorrelationModelSetting getOne(Integer id){
		return goodsCorrelationModelSettingRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "不存在"));
	}

	/**
	 * 分页查询
	 * @author zhongjichuan
	 */
	public Page<GoodsCorrelationModelSetting> page(GoodsCorrelationModelSettingQueryRequest queryReq){
		return goodsCorrelationModelSettingRepository.findAll(
				GoodsCorrelationModelSettingWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询
	 * @author zhongjichuan
	 */
	public List<GoodsCorrelationModelSetting> list(GoodsCorrelationModelSettingQueryRequest queryReq){
		List<GoodsCorrelationModelSetting> settingList = goodsCorrelationModelSettingRepository.findAll(GoodsCorrelationModelSettingWhereCriteriaBuilder.build(queryReq));
		if (CollectionUtils.isEmpty(settingList)){
			settingList.add(new GoodsCorrelationModelSetting(StatisticalRangeType.ONE_MONTH, 0L, new BigDecimal(5),
					new BigDecimal(10), new BigDecimal("1.2"), BoolFlag.NO, DeleteFlag.NO, LocalDateTime.now()));
			settingList.add(new GoodsCorrelationModelSetting(StatisticalRangeType.THREE_MONTHS, 0L, new BigDecimal(5),
					new BigDecimal(10), new BigDecimal("1.2"), BoolFlag.YES, DeleteFlag.NO, LocalDateTime.now()));
			settingList.add(new GoodsCorrelationModelSetting(StatisticalRangeType.SIX_MONTHS, 0L, new BigDecimal(5),
					new BigDecimal(10), new BigDecimal("1.2"), BoolFlag.NO, DeleteFlag.NO, LocalDateTime.now()));
			settingList.add(new GoodsCorrelationModelSetting(StatisticalRangeType.ONE_YEAR, 0L, new BigDecimal(5),
					new BigDecimal(10), new BigDecimal("1.2"), BoolFlag.NO, DeleteFlag.NO, LocalDateTime.now()));
			goodsCorrelationModelSettingRepository.saveAll(settingList);
		}
		return settingList;
	}

	/**
	 * 将实体包装成VO
	 * @author zhongjichuan
	 */
	public GoodsCorrelationModelSettingVO wrapperVo(GoodsCorrelationModelSetting goodsCorrelationModelSetting) {
		if (goodsCorrelationModelSetting != null){
			GoodsCorrelationModelSettingVO goodsCorrelationModelSettingVO = KsBeanUtil.convert(goodsCorrelationModelSetting, GoodsCorrelationModelSettingVO.class);
			return goodsCorrelationModelSettingVO;
		}
		return null;
	}
}

