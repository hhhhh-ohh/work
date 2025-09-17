package com.wanmi.sbc.marketing.newcomerpurchasecoupon.service;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon.NewcomerPurchaseCouponQueryRequest;
import com.wanmi.sbc.marketing.api.response.coupon.GetCouponGroupResponse;
import com.wanmi.sbc.marketing.bean.dto.NewcomerCouponStockDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.RangeDayType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.NewcomerPurchaseCouponVO;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivity;
import com.wanmi.sbc.marketing.coupon.model.root.CouponActivityConfig;
import com.wanmi.sbc.marketing.coupon.model.root.CouponInfo;
import com.wanmi.sbc.marketing.coupon.model.root.CouponMarketingScope;
import com.wanmi.sbc.marketing.coupon.repository.CouponActivityRepository;
import com.wanmi.sbc.marketing.coupon.repository.CouponMarketingScopeRepository;
import com.wanmi.sbc.marketing.coupon.service.CouponActivityConfigService;
import com.wanmi.sbc.marketing.coupon.service.CouponCodeService;
import com.wanmi.sbc.marketing.coupon.service.CouponInfoService;
import com.wanmi.sbc.marketing.newcomerpurchaseconfig.service.NewcomerPurchaseConfigService;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.model.root.NewcomerPurchaseCoupon;
import com.wanmi.sbc.marketing.newcomerpurchasecoupon.repository.NewcomerPurchaseCouponRepository;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>新人购优惠券业务逻辑</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@Service("NewcomerPurchaseCouponService")
public class NewcomerPurchaseCouponService {

	@Autowired
	private NewcomerPurchaseCouponRepository newcomerPurchaseCouponRepository;

	@Autowired
	private CouponActivityRepository couponActivityRepository;

	@Autowired
	private CouponMarketingScopeRepository couponMarketingScopeRepository;

	@Autowired
	private CouponInfoService couponInfoService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CouponCodeService couponCodeService;

	@Autowired
	private CustomerQueryProvider customerQueryProvider;

	@Autowired
	private NewcomerCouponStockTccInterface couponStockInterface;

	@Autowired
	private CouponActivityConfigService couponActivityConfigService;

	@Autowired
	private NewcomerPurchaseConfigService newcomerPurchaseConfigService;


	/**
	 * 新增新人购优惠券
	 * @author zhanghao
	 */
	@Transactional
	public NewcomerPurchaseCoupon add(NewcomerPurchaseCoupon entity) {
		newcomerPurchaseCouponRepository.save(entity);
		return entity;
	}

	/**
	 * 修改新人购优惠券
	 * @author zhanghao
	 */
	@Transactional
	public NewcomerPurchaseCoupon modify(NewcomerPurchaseCoupon entity) {
		newcomerPurchaseCouponRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除新人购优惠券
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(Integer id) {
		NewcomerPurchaseCoupon coupon = getOne(id);
		if (coupon != null) {
			newcomerPurchaseCouponRepository.deleteById(id);
			redisUtil.delete(RedisKeyConstant.NEW_CUSTOMER_COUPON_STOCK.concat(coupon.getCouponId()));
		}
	}

	/**
	 * 批量删除新人购优惠券
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Integer> ids) {
		ids.forEach(this::deleteById);
	}

	/**
	 * 单个查询新人购优惠券
	 * @author zhanghao
	 */
	public NewcomerPurchaseCoupon getOne(Integer id){
		return newcomerPurchaseCouponRepository.findByIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "新人购优惠券不存在"));
	}

	/**
	 * 分页查询新人购优惠券
	 * @author zhanghao
	 */
	public Page<NewcomerPurchaseCoupon> page(NewcomerPurchaseCouponQueryRequest queryReq){
		return newcomerPurchaseCouponRepository.findAll(
				NewcomerPurchaseCouponWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询新人购优惠券
	 * @author zhanghao
	 */
	public List<NewcomerPurchaseCoupon> list(NewcomerPurchaseCouponQueryRequest queryReq){
		return newcomerPurchaseCouponRepository.findAll(NewcomerPurchaseCouponWhereCriteriaBuilder.build(queryReq));
	}



	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public NewcomerPurchaseCouponVO wrapperVo(NewcomerPurchaseCoupon newcomerPurchaseCoupon) {
		if (newcomerPurchaseCoupon != null){
			NewcomerPurchaseCouponVO newcomerPurchaseCouponVO = KsBeanUtil.convert(newcomerPurchaseCoupon, NewcomerPurchaseCouponVO.class);
			return newcomerPurchaseCouponVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(NewcomerPurchaseCouponQueryRequest queryReq) {
		return newcomerPurchaseCouponRepository.count(NewcomerPurchaseCouponWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 批量保存新人购优惠券
	 * @param newcomerPurchaseCouponList
	 */
	@Transactional
	public void batchSave(List<NewcomerPurchaseCoupon> newcomerPurchaseCouponList) {
		LocalDateTime now = LocalDateTime.now();
		List<NewcomerCouponStockDTO> newcomerCouponStockDTOS = Lists.newArrayList();
		newcomerPurchaseCouponList.forEach(newcomerPurchaseCoupon -> {
			Integer id = newcomerPurchaseCoupon.getId();
			String couponId = newcomerPurchaseCoupon.getCouponId();
			CouponInfo couponInfo = couponInfoService.getCouponInfoById(couponId);
			DefaultFlag platformFlag = couponInfo.getPlatformFlag();
			Long couponStock = newcomerPurchaseCoupon.getCouponStock();
			Integer groupOfNum = newcomerPurchaseCoupon.getGroupOfNum();
			//获取增量
			if (Objects.isNull(couponStock)) {
				newcomerPurchaseCoupon.setCouponStock(BigDecimal.ZERO.longValue());
				couponStock = 0L;
			}
			if (Objects.isNull(groupOfNum)) {
				newcomerPurchaseCoupon.setGroupOfNum(Constants.ONE);
				groupOfNum = Constants.ONE;
			}
			if (couponStock < 0L || couponStock > 999999999L) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			if (groupOfNum < 1 || groupOfNum > 10) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			//如果不是平台券，则参数异常
			if (DefaultFlag.NO.equals(platformFlag)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			newcomerPurchaseCoupon.setCouponName(couponInfo.getCouponName());
			//如果传了id，需要编辑
			if (Objects.nonNull(id)) {
				newcomerPurchaseCouponRepository
						.modifyStockAndGroupNum(newcomerPurchaseCoupon.getCouponStock(),newcomerPurchaseCoupon.getGroupOfNum(),id);

			} else {
				newcomerPurchaseCoupon.setActivityStock(newcomerPurchaseCoupon.getCouponStock());
				newcomerPurchaseCoupon.setCreateTime(now);
				newcomerPurchaseCoupon.setDelFlag(DeleteFlag.NO);
				newcomerPurchaseCouponRepository.save(newcomerPurchaseCoupon);
			}
			newcomerCouponStockDTOS.add(NewcomerCouponStockDTO.builder()
					.couponId(newcomerPurchaseCoupon.getCouponId())
					.stock(newcomerPurchaseCoupon.getCouponStock())
					.build());
		});
		//找出新人购活动的id
		CouponActivity couponActivity = couponActivityRepository.findNewCustomerCouponActivity();
		// 如果为null，则默认为-999
		String activityId = Objects.isNull(couponActivity) ? "-999" : couponActivity.getActivityId();

		couponActivityConfigService.batchInsertCouponActivityConfig(newcomerCouponStockDTOS.parallelStream().map(newcomerCouponStockDTO -> {
			CouponActivityConfig config = new CouponActivityConfig();
			config.setActivityId(activityId);
			config.setCouponId(newcomerCouponStockDTO.getCouponId());
			config.setTotalCount(1L);
			config.setHasLeft(DefaultFlag.YES);
			return config;
		}).collect(Collectors.toList()));
		couponStockInterface.addStock(newcomerCouponStockDTOS);
	}

	/**
	 * 查询全部可使用的新人专享券
	 * @return
	 */
	public List<CouponInfoVO> getFetchCoupons() {
		return getFetchCoupons(null);
	}

	/**
	 * 查询可使用的新人专享券
	 * @return
	 */
	public List<CouponInfoVO> getFetchCoupons(List<String> ids) {
		List<CouponInfoVO> couponInfoVOS = new ArrayList<>();
		//检验活动是否存在、活动是否在进件中
		CouponActivity newComerActivity = couponActivityRepository.findNewComerActivity();
		LocalDateTime now = LocalDateTime.now();
		if (newComerActivity == null
				|| newComerActivity.getStartTime().isAfter(now)
				|| newComerActivity.getEndTime().isBefore(now)) {
			return couponInfoVOS;
		}

		List<NewcomerPurchaseCoupon> newcomerPurchaseCoupons = CollectionUtils.isNotEmpty(ids)
				? newcomerPurchaseCouponRepository.listFetchCoupons(ids)
				: newcomerPurchaseCouponRepository.listFetchCoupons();
		if (CollectionUtils.isEmpty(newcomerPurchaseCoupons)) {
			return couponInfoVOS;
		}

		Map<String, Integer> newcomerPurchaseCouponMap = newcomerPurchaseCoupons.stream()
				.collect(Collectors.toMap(NewcomerPurchaseCoupon::getCouponId, NewcomerPurchaseCoupon::getGroupOfNum));
        List<String> couponIds = Lists.newArrayList(newcomerPurchaseCouponMap.keySet());
		if (CollectionUtils.isNotEmpty(couponIds)) {
			//优惠券信息
			List<CouponInfo> couponInfoList = couponInfoService.queryByIds(couponIds);
			couponInfoVOS = KsBeanUtil.convertList(couponInfoList, CouponInfoVO.class);
			//过滤出可使用的优惠券
			couponInfoVOS = couponInfoVOS.stream().filter(couponInfoVO -> {
				//过滤有效期内的优惠券
				if (RangeDayType.RANGE_DAY == couponInfoVO.getRangeDayType()
						&& (couponInfoVO.getStartTime().isAfter(now) || couponInfoVO.getEndTime().isBefore(now))) {
					return false;
				}
				return true;
			}).map(couponInfoVO -> {
				//填充优惠券数量
				couponInfoVO.setTotal(newcomerPurchaseCouponMap.get(couponInfoVO.getCouponId()));
				return couponInfoVO;
			}).collect(Collectors.toList());

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
		return couponInfoVOS;
	}

	/**
	 * 领取优惠券
	 * @param customerId
	 */
	@Transactional
	public void fetchNewComerCoupons(String customerId) {
		LocalDateTime now = LocalDateTime.now();
		CouponActivity newComerActivity = couponActivityRepository.findNewComerActivity();

		//活动有效期校验
		if (newComerActivity == null || newComerActivity.getStartTime().isAfter(now) || newComerActivity.getEndTime().isBefore(now)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080054);
		}

		//新人校验
		Integer isNew = customerQueryProvider.getCustomerById(CustomerGetByIdRequest.builder().customerId(customerId).build()).getContext().getIsNew();
		if (!NumberUtils.INTEGER_ZERO.equals(isNew)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080060);
		}

		//领取校验
		Integer count = couponCodeService.countByCustomerIdAndActivityId(customerId, newComerActivity.getActivityId());
		if (count > NumberUtils.INTEGER_ZERO) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080091);
		}

		//查询可领取的优惠券
		List<NewcomerPurchaseCoupon> newcomerPurchaseCoupons = newcomerPurchaseCouponRepository.listFetchCoupons();
		if (CollectionUtils.isNotEmpty(newcomerPurchaseCoupons)) {
			List<String> couponIds = newcomerPurchaseCoupons.stream().map(NewcomerPurchaseCoupon::getCouponId).collect(Collectors.toList());
			List<CouponInfo> couponInfos = couponInfoService.queryByIds(couponIds);
			couponInfos = couponInfos.stream().filter(couponInfoVO -> {
				//过滤有效期内的优惠券
				if (RangeDayType.RANGE_DAY == couponInfoVO.getRangeDayType()
						&& (couponInfoVO.getStartTime().isAfter(now) || couponInfoVO.getEndTime().isBefore(now))) {
					return false;
				}
				return true;
			}).collect(Collectors.toList());

			List<GetCouponGroupResponse> getCouponGroupResponse = KsBeanUtil.copyListProperties(couponInfos, GetCouponGroupResponse.class);
			getCouponGroupResponse = getCouponGroupResponse.stream().peek(item -> newcomerPurchaseCoupons.forEach(newcomerPurchaseCoupon -> {
				if (item.getCouponId().equals(newcomerPurchaseCoupon.getCouponId())) {
					item.setTotalCount(newcomerPurchaseCoupon.getGroupOfNum().longValue());
				}
			})).collect(Collectors.toList());

			List<NewcomerCouponStockDTO> stockDTOS = getCouponGroupResponse.stream().map(item -> {
				NewcomerCouponStockDTO dto = new NewcomerCouponStockDTO();
				dto.setCouponId(item.getCouponId());
				dto.setStock(NumberUtils.LONG_ONE);
				return dto;
			}).collect(Collectors.toList());
			//扣减库存组数
			couponStockInterface.subStock(stockDTOS);
			// 批量发放优惠券
			couponCodeService.sendBatchCouponCodeByCustomer(getCouponGroupResponse, customerId, newComerActivity.getActivityId(), null);
		}

	}
}

