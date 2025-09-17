package com.wanmi.sbc.setting.payadvertisement.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementAddRequest;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementByIdRequest;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementModifyRequest;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisementStore;
import com.wanmi.sbc.setting.payadvertisement.repository.PayAdvertisementStoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.wanmi.sbc.setting.payadvertisement.repository.PayAdvertisementRepository;
import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisement;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementQueryRequest;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.enums.DeleteFlag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>支付广告页配置业务逻辑</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@Service("PayAdvertisementService")
public class PayAdvertisementService {

	@Autowired
	private PayAdvertisementRepository payAdvertisementRepository;

	@Autowired
	private PayAdvertisementStoreRepository payAdvertisementStoreRepository;

	/**
	 * 新增支付广告页配置
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void add(PayAdvertisementAddRequest request) {
		checkPayAdvertisement(null, request.getStoreType(),request.getStoreIds(),request.getStartTime(),request.getEndTime());
		PayAdvertisement payAdvertisement = KsBeanUtil.convert(request, PayAdvertisement.class);
		payAdvertisement.setCreateTime(LocalDateTime.now());
		payAdvertisement.setIsPause(Constants.ZERO);
		payAdvertisement.setOrderPrice(Objects.isNull(request.getOrderPrice())? BigDecimal.ZERO:request.getOrderPrice());
		PayAdvertisement save = payAdvertisementRepository.save(payAdvertisement);
		if (Objects.equals(Constants.TWO,payAdvertisement.getStoreType())){
			List<PayAdvertisementStore> list = new ArrayList<>();
			request.getStoreIds().forEach(s -> {
				PayAdvertisementStore store = new PayAdvertisementStore();
				store.setStoreId(s);
				store.setPayAdvertisementId(save.getId());
				list.add(store);
			});
			payAdvertisementStoreRepository.saveAll(list);
		}
	}

	/**
	 * 校验同时间段同店铺是否存在支付广告页
	 * @param id
	 * @param storeType
	 * @param storeIds
	 * @param startTime
	 * @param endTime
	 */
	private void checkPayAdvertisement(Long id, Integer storeType, List<Long> storeIds, LocalDateTime startTime, LocalDateTime endTime) {
		int count = Constants.ZERO;
		if (Objects.isNull(id)){
			count = payAdvertisementRepository.findSameTimeAdvertisement(storeType,storeIds,startTime,endTime);
		}else {
			count = payAdvertisementRepository.findSameTimeAdvertisement(id,storeType,storeIds,startTime,endTime);
		}

		if (count>0){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070027);
		}
	}

	/**
	 * 修改支付广告页配置
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void modify(PayAdvertisementModifyRequest request) {
		checkPayAdvertisement(request.getId(),request.getStoreType(),request.getStoreIds(),request.getStartTime(),request.getEndTime());
		PayAdvertisement payAdvertisement = payAdvertisementRepository.findById(request.getId())
				.orElseThrow(() -> new SbcRuntimeException(SettingErrorCodeEnum.K070028));
		if ((!Objects.equals(Constants.ONE,payAdvertisement.getIsPause())
				&& payAdvertisement.getStartTime().isBefore(LocalDateTime.now()))
		|| payAdvertisement.getEndTime().isBefore(LocalDateTime.now())){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070104);
		}
		List<PayAdvertisementStore> storeList = payAdvertisementStoreRepository.findByPayAdvertisementId(request.getId());
		if (Objects.equals(Constants.ONE,payAdvertisement.getIsPause())){
			//校验暂停中广告活动
			checkPausePayAdvertisement(request, payAdvertisement, storeList);

			payAdvertisement.setStoreType(request.getStoreType());
			payAdvertisement.setJoinLevel(request.getJoinLevel());
			payAdvertisement.setEndTime(request.getEndTime());
			payAdvertisement.setUpdateTime(LocalDateTime.now());
      		payAdvertisement.setUpdatePerson(request.getUpdatePerson());

		}else {
			if (request.getStartTime().withSecond(Constants.NUM_59).isBefore(LocalDateTime.now())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			KsBeanUtil.copyPropertiesThird(request,payAdvertisement);
			payAdvertisement.setOrderPrice(Objects.isNull(request.getOrderPrice())? BigDecimal.ZERO:request.getOrderPrice());
		}

		payAdvertisementRepository.save(payAdvertisement);

		payAdvertisementStoreRepository.deleteInBatch(storeList);

		if (Objects.equals(Constants.TWO,request.getStoreType())){
			List<PayAdvertisementStore> newStoreList = request.getStoreIds().stream().map(id -> {
				PayAdvertisementStore store = new PayAdvertisementStore();
				store.setStoreId(id);
				store.setPayAdvertisementId(request.getId());
				return store;
			}).collect(Collectors.toList());
			payAdvertisementStoreRepository.saveAll(newStoreList);
		}
	}

	/**
	 * 校验暂停中广告页设置
	 * @param request
	 * @param payAdvertisement
	 * @param storeList
	 */
	private void checkPausePayAdvertisement(PayAdvertisementModifyRequest request, PayAdvertisement payAdvertisement, List<PayAdvertisementStore> storeList) {
		//结束时间早于原结束时间
		if (request.getEndTime().isBefore(payAdvertisement.getEndTime())){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}

		//适用店铺少于原适用店铺
		if (Objects.equals(Constants.ONE, payAdvertisement.getStoreType())){
			if (!Objects.equals(Constants.ONE, request.getStoreType())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}else {
			if (!Objects.equals(Constants.ONE, request.getStoreType())
					&& !request.getStoreIds().containsAll(storeList.stream().map(PayAdvertisementStore::getStoreId).collect(Collectors.toList()))){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}

		//目标客户少于原目标客户
		if (Objects.equals(Constants.STR_0, payAdvertisement.getJoinLevel())){
			if (!Objects.equals(Constants.STR_0, request.getJoinLevel())){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}else {
			List<String> oldJoinLevels = Arrays.asList(payAdvertisement.getJoinLevel().split(","));
			List<String> newJoinLevels = Arrays.asList(request.getJoinLevel().split(","));
			if (!Objects.equals(Constants.STR_0,request.getJoinLevel()) && !newJoinLevels.containsAll(oldJoinLevels)){
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
	}

	/**
	 * 单个删除支付广告页配置
	 * @author 黄昭
	 */
	@Transactional(rollbackFor = {Exception.class})
	public void deleteById(PayAdvertisementByIdRequest request) {
		PayAdvertisement payAdvertisement = payAdvertisementRepository.findById(request.getId())
				.orElseThrow(()->new SbcRuntimeException(SettingErrorCodeEnum.K070028));
		if (payAdvertisement.getStartTime().isBefore(LocalDateTime.now())){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070029);
		}
		payAdvertisement.setDelFlag(DeleteFlag.YES);
		payAdvertisement.setUpdatePerson(request.getUserId());
		payAdvertisement.setUpdateTime(LocalDateTime.now());
		payAdvertisementRepository.save(payAdvertisement);
	}

	/**
	 * 单个查询支付广告页配置
	 * @author 黄昭
	 */
	public PayAdvertisement getOne(Long id){
		PayAdvertisement payAdvertisement = payAdvertisementRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
				.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "支付广告页配置不存在"));
		return payAdvertisement;
	}

	/**
	 * 分页查询支付广告页配置
	 * @author 黄昭
	 */
	public Page<PayAdvertisement> page(PayAdvertisementQueryRequest queryReq){
		return payAdvertisementRepository.findAll(
				PayAdvertisementWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询支付广告页配置
	 * @author 黄昭
	 */
	public List<PayAdvertisement> list(PayAdvertisementQueryRequest queryReq){
		return payAdvertisementRepository.findAll(PayAdvertisementWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 将实体包装成VO
	 * @author 黄昭
	 */
	public PayAdvertisementVO wrapperVo(PayAdvertisement payAdvertisement) {
		if (payAdvertisement != null){
			PayAdvertisementVO payAdvertisementVO = KsBeanUtil.convert(payAdvertisement, PayAdvertisementVO.class);
			return payAdvertisementVO;
		}
		return null;
	}

	/**
	 * 查询支付页广告关联店铺
	 * @param id
	 * @return
	 */
	public List<PayAdvertisementStore> getPayAdvertisementStore(Long id) {
		return payAdvertisementStoreRepository.findByPayAdvertisementId(id);
	}

	/**
	 * 暂停活动
	 * @param request
	 */
	public void pauseById(PayAdvertisementByIdRequest request) {
		PayAdvertisement payAdvertisement = payAdvertisementRepository.findById(request.getId())
				.orElseThrow(()->new SbcRuntimeException(SettingErrorCodeEnum.K070028));
		if (payAdvertisement.getStartTime().isAfter(LocalDateTime.now())
				|| payAdvertisement.getEndTime().isBefore(LocalDateTime.now())){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070030);
		}
		payAdvertisement.setIsPause(Constants.ONE);
		payAdvertisement.setUpdatePerson(request.getUserId());
		payAdvertisement.setUpdateTime(LocalDateTime.now());
		payAdvertisementRepository.save(payAdvertisement);
	}

	/**
	 * 开启活动
	 * @param request
	 */
	public void startById(PayAdvertisementByIdRequest request) {
		PayAdvertisement payAdvertisement = payAdvertisementRepository.findById(request.getId())
				.orElseThrow(()->new SbcRuntimeException(SettingErrorCodeEnum.K070028));
		if (!Objects.equals(Constants.ONE,payAdvertisement.getIsPause())){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070031);
		}
		payAdvertisement.setIsPause(Constants.ZERO);
		payAdvertisement.setUpdatePerson(request.getUserId());
		payAdvertisement.setUpdateTime(LocalDateTime.now());
		payAdvertisementRepository.save(payAdvertisement);
	}

	public void closeById(PayAdvertisementByIdRequest request) {
		PayAdvertisement payAdvertisement = payAdvertisementRepository.findById(request.getId())
				.orElseThrow(()->new SbcRuntimeException(SettingErrorCodeEnum.K070028));
		if (LocalDateTime.now().isBefore(payAdvertisement.getStartTime())){
			throw new SbcRuntimeException(SettingErrorCodeEnum.K070032);
		}
		payAdvertisement.setEndTime(LocalDateTime.now());
		payAdvertisement.setUpdatePerson(request.getUserId());
		payAdvertisement.setUpdateTime(LocalDateTime.now());
		payAdvertisementRepository.save(payAdvertisement);

	}
}

