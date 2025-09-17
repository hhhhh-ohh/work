package com.wanmi.sbc.marketing.newcomerpurchaseconfig.service;

import com.google.common.base.Splitter;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponActivityProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponActivityAddRequest;
import com.wanmi.sbc.elastic.bean.dto.coupon.EsCouponActivityDTO;
import com.wanmi.sbc.goods.api.provider.newcomerpurchasegoods.NewcomerPurchaseGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.newcomerpurchasegoods.NewcomerPurchaseGoodsListRequest;
import com.wanmi.sbc.goods.bean.vo.NewcomerPurchaseGoodsVO;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponActivityConfigSaveRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigModifyRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.NewcomerPurchaseCouponQueryRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseDetailResponse;
import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.CouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.NewcomerPurchaseConfigVO;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivity;
import com.wanmi.sbc.marketing.coupon.model.root.CouponCode;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.repository.CouponActivityRepository;
import com.wanmi.sbc.marketing.coupon.repository.CouponMarketingScopeRepository;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityConfigService;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityService;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import com.wanmi.sbc.marketing.coupon.service.CouponInfoService;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.model.root.NewcomerPurchaseConfig;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.repository.NewcomerPurchaseConfigRepository;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.model.root.NewcomerPurchaseCoupon;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.service.NewcomerPurchaseCouponService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>新人专享设置业务逻辑</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@Service("NewcomerPurchaseConfigService")
public class NewcomerPurchaseConfigService {

	@Autowired
	private NewcomerPurchaseConfigRepository newcomerPurchaseConfigRepository;

	@Autowired
	private CouponActivityRepository couponActivityRepository;

	@Autowired
	private NewcomerPurchaseCouponService newcomerPurchaseCouponService;

	@Autowired
	private CouponActivityService couponActivityService;



	@Autowired
	private NewcomerPurchaseGoodsQueryProvider newcomerPurchaseGoodsQueryProvider;

	@Autowired
	private CouponCodeService couponCodeService;

	@Autowired
	private EsCouponActivityProvider esCouponActivityProvider;

	@Autowired
	private CouponActivityConfigService couponActivityConfigService;

	@Autowired
	private CouponInfoService couponInfoService;

	@Autowired
	private CouponMarketingScopeRepository couponMarketingScopeRepository;

	/**
	 * 新增新人专享设置
	 * @author zhanghao
	 */
	@Transactional
	public NewcomerPurchaseConfig add(NewcomerPurchaseConfig entity) {
		newcomerPurchaseConfigRepository.save(entity);
		return entity;
	}

	/**
	 * 修改新人专享设置
	 * @author zhanghao
	 */
	@Transactional
	public NewcomerPurchaseConfig modify(NewcomerPurchaseConfig entity) {
		newcomerPurchaseConfigRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除新人专享设置
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(NewcomerPurchaseConfig entity) {
		newcomerPurchaseConfigRepository.save(entity);
	}

	/**
	 * 批量删除新人专享设置
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Integer> ids) {
		newcomerPurchaseConfigRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询新人专享设置
	 * @author zhanghao
	 */
	public NewcomerPurchaseConfig getOne(){
		return newcomerPurchaseConfigRepository.findFirstByDelFlag(DeleteFlag.NO);
	}

	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public NewcomerPurchaseConfigVO wrapperVo(NewcomerPurchaseConfig newcomerPurchaseConfig) {
		if (newcomerPurchaseConfig != null){
			NewcomerPurchaseConfigVO newcomerPurchaseConfigVO = KsBeanUtil.convert(newcomerPurchaseConfig, NewcomerPurchaseConfigVO.class);
			return newcomerPurchaseConfigVO;
		}
		return null;
	}

	/**
	 * 获取新人优惠券
	 * @param customerId
	 * @return
	 */
	public List<CouponInfoVO> getNewcomerCouponInfos(String customerId) {
		CouponActivity newComerActivity = couponActivityRepository.findNewComerActivity();

		LocalDateTime now = LocalDateTime.now();
		if (Objects.isNull(newComerActivity)
				|| newComerActivity.getStartTime().isAfter(now)
				|| newComerActivity.getEndTime().isBefore(now)) {
			return new ArrayList<>();
		}

		List<CouponInfoVO> couponInfoVOS = newcomerPurchaseCouponService.getFetchCoupons();
		//排序 条件金额生序、面值金额降序、创建时间降序
		Comparator<CouponInfoVO> comparator = Comparator.comparing(CouponInfoVO::getFullBuyType)
				.thenComparing(CouponInfoVO::getFullBuyPrice, Comparator.nullsFirst(BigDecimal::compareTo))
				.thenComparing(Comparator.comparing(CouponInfoVO::getDenomination).reversed())
				.thenComparing(Comparator.comparing(CouponInfoVO::getCreateTime).reversed());
		List<CouponCode> couponCodes = couponCodeService.findByCustomerIdAndActivityId(customerId, newComerActivity.getActivityId());
		Boolean fetchFlag = CollectionUtils.isNotEmpty(couponCodes) ? Boolean.TRUE : Boolean.FALSE;
		if (fetchFlag) {
			List<String> couponIds = couponCodes.parallelStream().map(CouponCode::getCouponId).collect(Collectors.toList());
			List<CouponInfo> couponInfoList = couponInfoService.queryByIds(couponIds);
			couponInfoVOS = KsBeanUtil.convertList(couponInfoList, CouponInfoVO.class);
			couponInfoVOS.forEach(couponInfoVO -> {
				long count = couponCodes.parallelStream().filter(couponCode -> couponCode.getCouponId().equals(couponInfoVO.getCouponId())).count();
				couponInfoVO.setTotal(Integer.valueOf(String.valueOf(count)));
			});
			//优惠券-商品范围
			List<CouponMarketingScope> scopes = couponMarketingScopeRepository.findByCouponIdIn(couponIds);
			Map<String, List<CouponMarketingScope>> scopeMap = scopes.stream().collect(Collectors.groupingBy(CouponMarketingScope::getCouponId));
			if (CollectionUtils.isNotEmpty(couponInfoVOS) && !scopeMap.isEmpty()) {
				couponInfoVOS.forEach(couponInfo -> {
					List<CouponMarketingScope> list = scopeMap.get(couponInfo.getCouponId());
					if (CollectionUtils.isNotEmpty(list)) {
						List<String> scopeIds = list.stream().map(CouponMarketingScope::getScopeId).collect(Collectors.toList());
						couponInfo.setScopeIds(scopeIds);
					}
				});
			}

			//查询优惠券使用范围
			List<CouponMarketingScope> scopeList = couponMarketingScopeRepository.findByCouponIdIn(couponIds);
			Map<String, List<String>> scopeNamesMap = new HashMap<>();
			if (CollectionUtils.isNotEmpty(scopeList)) {
				Set<String> scopeSet = scopeList.stream().map(CouponMarketingScope::getCouponId).collect(Collectors.toSet());
				List<CouponInfoVO> filtedCouponInfo =
						couponInfoVOS.stream()
								.filter(couponInfo -> scopeSet.contains(couponInfo.getCouponId()))
								.collect(Collectors.toList());
				scopeNamesMap = couponInfoService.couponDetail(KsBeanUtil.convert(filtedCouponInfo,CouponInfo.class), scopeList);
			}
			for (CouponInfoVO couponInfoVO : couponInfoVOS) {
				couponInfoVO.setActivityId(newComerActivity.getActivityId());
				List<String> names = scopeNamesMap.get(couponInfoVO.getCouponId());
				if (CollectionUtils.isNotEmpty(names)) {
					couponInfoVO.setScopeNames(names);
				}
			}
		}
		couponInfoVOS = couponInfoVOS.stream().sorted(comparator).collect(Collectors.toList());
		return couponInfoVOS;
	}

	/**
	 * 新人专享页
	 * @param customerId
	 * @return
	 */
	public NewcomerPurchaseDetailResponse detailForMobile(String customerId) {
		NewcomerPurchaseDetailResponse response = new NewcomerPurchaseDetailResponse();
		NewcomerPurchaseConfig config = newcomerPurchaseConfigRepository.findFirstByDelFlag(DeleteFlag.NO);
		if (config == null) {
			return null;
		}
		List<CouponInfoVO> couponInfoVOS = getNewcomerCouponInfos(customerId);
		if (CollectionUtils.isEmpty(couponInfoVOS)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080054);
		}
		CouponActivity newComerActivity = couponActivityRepository.findNewComerActivity();
		List<CouponCode> couponCodes = couponCodeService.findByCustomerIdAndActivityId(customerId, newComerActivity.getActivityId());
		Boolean fetchFlag = CollectionUtils.isNotEmpty(couponCodes) ? Boolean.TRUE : Boolean.FALSE;
		List<NewcomerPurchaseGoodsVO> goodsVOS = newcomerPurchaseGoodsQueryProvider
				.list(NewcomerPurchaseGoodsListRequest.builder().delFlag(DeleteFlag.NO).build())
				.getContext().getNewcomerPurchaseGoodsVOList();
		List<String> skuIds = goodsVOS.stream().map(NewcomerPurchaseGoodsVO::getGoodsInfoId).collect(Collectors.toList());

		response.setNewcomerPurchaseConfig(wrapperVo(config));
		response.setCoupons(KsBeanUtil.convertList(couponInfoVOS, CouponInfoVO.class));
		response.setSkuIds(skuIds);
		response.setFetchFlag(fetchFlag);
		return response;
	}

	/**
	 * 获取新人购
	 * @return
	 */
	public NewcomerPurchaseConfigVO detail() {
		NewcomerPurchaseConfig newcomerPurchaseConfig = newcomerPurchaseConfigRepository.findFirstByDelFlag(DeleteFlag.NO);
		if (Objects.isNull(newcomerPurchaseConfig)) {
			return null;
		}
		NewcomerPurchaseConfigVO newcomerPurchaseConfigVO = wrapperVo(newcomerPurchaseConfig);
		CouponActivity couponActivity = couponActivityRepository.findNewCustomerCouponActivity();
		newcomerPurchaseConfigVO.setStartTime(couponActivity.getStartTime());
		newcomerPurchaseConfigVO.setEndTime(couponActivity.getEndTime());
		return newcomerPurchaseConfigVO;
	}

	/**
	 * 保存新人购配置
	 */
	@Transactional
	public void save(NewcomerPurchaseConfigModifyRequest request) {
		Integer id = request.getId();
		NewcomerPurchaseConfig newcomerPurchaseConfig = newcomerPurchaseConfigRepository.findFirstByDelFlag(DeleteFlag.NO);
		CouponActivity couponActivity = couponActivityRepository.findNewCustomerCouponActivity();
		//如果为空则为新增,否则为编辑
		if (Objects.isNull(id)) {
			//如果新增时,数据库已经有配置，则参数异常
			if (Objects.nonNull(newcomerPurchaseConfig)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			List<NewcomerPurchaseCoupon> couponList = newcomerPurchaseCouponService.list(NewcomerPurchaseCouponQueryRequest.builder()
					.delFlag(DeleteFlag.NO)
					.build());
			CouponActivityAddRequest couponActivityAddRequest = CouponActivityAddRequest.builder()
					.activityName("新人专享")
					.startTime(request.getStartTime())
					.endTime(request.getEndTime())
					.couponActivityType(CouponActivityType.NEW_CUSTOMER_COUPON)
					.receiveType(DefaultFlag.NO)
					.joinLevel("-1")
					.pauseFlag(DefaultFlag.NO)
					.platformFlag(DefaultFlag.YES)
					.storeId(Constant.BOSS_DEFAULT_STORE_ID)
					.couponActivityConfigs(getCouponActivityConfigSaveRequests(couponList))
					.build();
			if (Objects.isNull(couponActivity)) {
				CouponActivityVO couponActivityVO = couponActivityService.addCouponActivity(couponActivityAddRequest).getCouponActivity();
				couponActivity = KsBeanUtil.convert(couponActivityVO,CouponActivity.class);
				//新增活动后，刷新活动配置表的活动id
				couponActivityConfigService.updateActivityId(couponActivity.getActivityId());
			} else {
				couponActivityService.modifyNewCustomerCouponActivity(request.getStartTime(),request.getEndTime());
				//修改时间后方便es同步
				couponActivity.setStartTime(request.getStartTime());
				couponActivity.setEndTime(request.getEndTime());
			}
			NewcomerPurchaseConfig config = KsBeanUtil.convert(request, NewcomerPurchaseConfig.class);
			config.setDelFlag(DeleteFlag.NO);
			add(config);
		} else {
			//如果编辑时,数据库配置id与传入id不一致，则参数异常
			if (!id.equals(newcomerPurchaseConfig.getId())) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			couponActivityService.modifyNewCustomerCouponActivity(request.getStartTime(),request.getEndTime());
			//修改时间后方便es同步
			couponActivity.setStartTime(request.getStartTime());
			couponActivity.setEndTime(request.getEndTime());
			NewcomerPurchaseConfig config = KsBeanUtil.convert(request, NewcomerPurchaseConfig.class);
			config.setDelFlag(DeleteFlag.NO);
			modify(config);
		}
		//同步es
		EsCouponActivityDTO esCouponActivityDTO = KsBeanUtil.convert(couponActivity, EsCouponActivityDTO.class);
		List<String> joinLevels = Splitter.on(",").trimResults().splitToList(couponActivity.getJoinLevel());
		esCouponActivityDTO.setJoinLevels(joinLevels);
		esCouponActivityProvider.add(new EsCouponActivityAddRequest(esCouponActivityDTO));
	}

	private List<CouponActivityConfigSaveRequest> getCouponActivityConfigSaveRequests(List<NewcomerPurchaseCoupon> couponList) {
		List<CouponActivityConfigSaveRequest> activityConfigs = new ArrayList<>();
		couponList.forEach(coupon -> {
			CouponActivityConfigSaveRequest config = new CouponActivityConfigSaveRequest();
			config.setCouponId(coupon.getCouponId());
			config.setTotalCount(BigDecimal.ONE.longValue());
			activityConfigs.add(config);
		});
		return activityConfigs;
	}

	/**
	 * 检查活动是否有效
	 * @return
	 */
	public Boolean checkActive(){
		LocalDateTime now = LocalDateTime.now();

		NewcomerPurchaseConfig config = newcomerPurchaseConfigRepository.findFirstByDelFlag(DeleteFlag.NO);
		if (config == null) {
			return Boolean.FALSE;
		}

		CouponActivity newComerActivity = couponActivityRepository.findNewComerActivity();
		if (Objects.isNull(newComerActivity) || newComerActivity.getStartTime().isAfter(now) || newComerActivity.getEndTime().isBefore(now)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}

