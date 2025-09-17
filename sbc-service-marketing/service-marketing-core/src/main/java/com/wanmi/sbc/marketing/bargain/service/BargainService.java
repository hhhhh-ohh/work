package com.wanmi.sbc.marketing.bargain.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerListByConditionRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.spec.GoodsInfoSpecDetailRelQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.request.spec.GoodsInfoSpecDetailRelBySkuIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoOriginalResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.bargain.BargainQueryRequest;
import com.wanmi.sbc.marketing.api.request.bargain.OriginateRequest;
import com.wanmi.sbc.marketing.api.request.bargain.UpdateTradeRequest;
import com.wanmi.sbc.marketing.api.request.bargaingoods.UpdateStockRequest;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinQueryRequest;
import com.wanmi.sbc.marketing.bargain.model.root.Bargain;
import com.wanmi.sbc.marketing.bargain.model.root.BargainJoinBargainGoods;
import com.wanmi.sbc.marketing.bargain.model.root.BargainJoinGoodsInfo;
import com.wanmi.sbc.marketing.bargain.repository.BargainJoinBargainGoodsRepository;
import com.wanmi.sbc.marketing.bargain.repository.BargainJoinGoodsInfoRepository;
import com.wanmi.sbc.marketing.bargain.repository.BargainRepository;
import com.wanmi.sbc.marketing.bargaingoods.model.root.BargainGoods;
import com.wanmi.sbc.marketing.bargaingoods.repository.BargainGoodsRepository;
import com.wanmi.sbc.marketing.bargainjoin.service.BargainJoinService;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.wanmi.sbc.marketing.bean.vo.BargainJoinVO;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>砍价业务逻辑</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Service("BargainService")
public class BargainService {
	@Autowired
	private BargainJoinGoodsInfoRepository bargainJoinGoodsInfoRepository;

	@Autowired
	private BargainRepository bargainRepository;

	@Autowired
	private BargainGoodsRepository bargainGoodsRepository;

	@Autowired
	private BargainJoinBargainGoodsRepository bargainJoinBargainGoodsRepository;


	@Autowired
	private BargainJoinService bargainJoinService;

	@Autowired
	private CustomerQueryProvider customerQueryProvider;


	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private GoodsInfoSpecDetailRelQueryProvider goodsInfoSpecDetailRelQueryProvider;

	@Autowired
	private GoodsInfoQueryProvider goodsInfoQueryProvider;

	@Autowired
	private SystemConfigQueryProvider systemConfigQueryProvider;


	/**
	 * 新增砍价
	 *
	 * @author
	 */
	@Transactional
	public Bargain add(Bargain entity) {
		entity.setCreateTime(LocalDateTime.now());
		return bargainRepository.save(entity);
	}

	/**
	 * 修改砍价
	 *
	 * @author
	 */
	@Transactional
	public BargainJoinGoodsInfo modify(BargainJoinGoodsInfo entity) {
		bargainJoinGoodsInfoRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除砍价
	 *
	 * @author
	 */
	@Transactional
	public void deleteById(Long id) {
		bargainJoinGoodsInfoRepository.deleteById(id);
	}

	/**
	 * 批量删除砍价
	 *
	 * @author
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(id -> bargainJoinGoodsInfoRepository.deleteById(id));
	}

	/**
	 * 砍价详情
	 *
	 * @author
	 */
	public BargainVO getById(Long bargainId) {
		Optional<BargainJoinGoodsInfo> optional =
                bargainJoinGoodsInfoRepository.findOne(
                        BargainWhereCriteriaBuilder.buildJoinGoodsInfo(
                                BargainQueryRequest.builder().bargainId(bargainId).build()));
		if (!optional.isPresent()) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		BargainJoinGoodsInfo bargainJoinGoodsInfo = optional.get();
		if (Objects.isNull(bargainJoinGoodsInfo) || Objects.isNull(bargainJoinGoodsInfo.getBargainId())) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		BargainVO bargainVO = JSON.parseObject(JSON.toJSONString(bargainJoinGoodsInfo), BargainVO.class);
        // 查询商品信息
		GoodsInfoOriginalResponse response = goodsInfoQueryProvider.getOriginalById(GoodsInfoByIdRequest.builder().goodsInfoId(bargainJoinGoodsInfo.getGoodsInfoId()).build()).getContext();
        if (Objects.isNull(response) || Objects.isNull(response.getGoodsInfo())) {
        	throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
        bargainVO.setGoodsInfoVO(response.getGoodsInfo());
		BargainGoodsVO bargainGoodsVO = JSON.parseObject(JSON.toJSONString(bargainJoinGoodsInfo.getBargainGoods()), BargainGoodsVO.class);
		if (Objects.equals(Constants.no, response.getGoodsInfo().getVendibility(Boolean.TRUE))) {
			bargainGoodsVO.setGoodsStatus(DeleteFlag.NO);
		}
		bargainVO.setBargainGoodsVO(bargainGoodsVO);
		//查询砍价记录
		List<BargainJoinVO> bargainJoinVOS = JSON.parseArray(JSON.toJSONString(bargainJoinService.list(BargainJoinQueryRequest.builder().bargainId(bargainVO.getBargainId()).build())), BargainJoinVO.class);
		if (CollectionUtils.isNotEmpty(bargainJoinVOS)) {
			List<CustomerVO> customerVOList = customerQueryProvider.listCustomerByCondition(CustomerListByConditionRequest.builder().customerIds(bargainJoinVOS.stream().map(v -> v.getJoinCustomerId()).collect(Collectors.toList())).build()).getContext().getCustomerVOList();
			for (BargainJoinVO bargainJoinVO : bargainJoinVOS) {
				Optional<CustomerVO> opt = customerVOList.stream().filter(v -> v.getCustomerId().equals(bargainJoinVO.getJoinCustomerId())).findFirst();
				if (opt.isPresent()) {
					CustomerVO customerVO = opt.get();
					bargainJoinVO.setCustomerAccount(customerVO.getCustomerAccount());
					bargainJoinVO.setHeadImg(customerVO.getHeadImg());
					if(Objects.nonNull(customerVO.getCustomerDetail())) {
						bargainJoinVO.setCustomerName(customerVO.getCustomerDetail().getCustomerName());
					}
				}
			}
			bargainVO.setBargainJoinVOs(bargainJoinVOS);
		}
		return bargainVO;
	}

	public BargainVO getByIdWithBargainGoods(Long id) {
		BargainJoinBargainGoods bargainJoinBargainGoods = bargainJoinBargainGoodsRepository.getOne(id);
		BargainVO bargainVO = JSON.parseObject(JSON.toJSONString(bargainJoinBargainGoods), BargainVO.class);
		bargainVO.setBargainGoodsVO(JSON.parseObject(JSON.toJSONString(bargainJoinBargainGoods.getBargainGoods()), BargainGoodsVO.class));
		return bargainVO;
	}

	public BargainVO getByIdForPlatForm(Long id) {
		BargainVO bargainVO = getById(id);
		CustomerGetByIdResponse customerGetByIdResponse = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(bargainVO.getCustomerId())).getContext();
		bargainVO.setCustomerAccount(customerGetByIdResponse.getCustomerAccount());
		bargainVO.setCustomerName(customerGetByIdResponse.getCustomerDetail().getCustomerName());
		bargainVO.setContactPhone(customerGetByIdResponse.getCustomerDetail().getContactPhone());
		return bargainVO;
	}

	/**
	 * 分页查询砍价
	 *
	 * @author
	 */
	public Page<BargainVO> page(BargainQueryRequest queryReq) {
		//1. 查询我的砍价记录
		Page<BargainJoinGoodsInfo> bargainPage = bargainJoinGoodsInfoRepository.findAll(
				BargainWhereCriteriaBuilder.buildJoinGoodsInfo(queryReq),
				queryReq.getPageRequest());
		if (CollectionUtils.isEmpty(bargainPage.getContent())) {
			return Page.empty();
		}
		// 查询商品信息
		List<String> skuIdList = bargainPage.getContent().stream().map(BargainJoinGoodsInfo :: getGoodsInfoId).collect(Collectors.toList());
		List<GoodsInfoVO> goodsInfoVOList = goodsInfoQueryProvider.originalListByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(skuIdList).build()).getContext().getGoodsInfos();
		if (CollectionUtils.isEmpty(goodsInfoVOList)) {
			return Page.empty();
		}
		Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoVOList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));

		// 查询商品规格
		List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRels =
				goodsInfoSpecDetailRelQueryProvider
						.listBySkuIds(
								new GoodsInfoSpecDetailRelBySkuIdsRequest(skuIdList))
						.getContext()
						.getGoodsInfoSpecDetailRelVOList();
		Map<String, List<GoodsInfoSpecDetailRelVO>> goodsInfoSpecDetailRelMap = goodsInfoSpecDetailRels.stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRelVO::getGoodsInfoId));
		Page<BargainVO> newPage = bargainPage.map(bargainJoinBargainGoods -> {
			BargainVO bargainVO = JSON.parseObject(JSON.toJSONString(bargainJoinBargainGoods), BargainVO.class);
			bargainVO.setGoodsInfoVO(goodsInfoMap.get(bargainVO.getGoodsInfoId()));
			BargainGoodsVO bargainGoodsVO = KsBeanUtil.convert(bargainJoinBargainGoods.getBargainGoods(), BargainGoodsVO.class);
			List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelsTemp = goodsInfoSpecDetailRelMap.get(bargainGoodsVO.getGoodsInfoId());
			if (CollectionUtils.isNotEmpty(goodsInfoSpecDetailRelsTemp)) {
				bargainGoodsVO.setSpecText(StringUtils.join(goodsInfoSpecDetailRelsTemp.stream().map(GoodsInfoSpecDetailRelVO::getDetailName).collect(Collectors.toList()), " "));
				bargainVO.getGoodsInfoVO().setSpecText(StringUtils.join(goodsInfoSpecDetailRelsTemp.stream().map(GoodsInfoSpecDetailRelVO::getDetailName).collect(Collectors.toList()), " "));
			}
			if(Objects.nonNull(bargainVO.getGoodsInfoVO())
					&& Objects.equals(Constants.no, bargainVO.getGoodsInfoVO().getVendibility(Boolean.TRUE))) {
				bargainGoodsVO.setGoodsStatus(DeleteFlag.NO);
			}
			bargainVO.setBargainGoodsVO(bargainGoodsVO);
			return bargainVO;
		});
		return newPage;
	}

	public Page<BargainVO> pageForPlatForm(BargainQueryRequest queryReq) {
		return this.page(queryReq);
	}

	/**
	 * 列表查询砍价
	 *
	 * @author
	 */
	public List<BargainJoinGoodsInfo> list(BargainQueryRequest queryReq) {
		return bargainJoinGoodsInfoRepository.findAll(
				BargainWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 *
	 * @author
	 */
	public BargainVO wrapperVo(BargainJoinGoodsInfo bargainJoinGoodsInfo) {
		if (bargainJoinGoodsInfo != null) {
			BargainVO bargainVO = new BargainVO();
			KsBeanUtil.copyPropertiesThird(bargainJoinGoodsInfo, bargainVO);
			return bargainVO;
		}
		return null;
	}

	/**
	 * 发起砍价
	 *
	 * @param request
	 */
	public BargainVO originate(OriginateRequest request) {
		//验证活动是否有效
		BargainGoods bargainGoods = bargainGoodsRepository.getOne(request.getBargainGoodsId());
        if (bargainGoods.getEndTime().isBefore(LocalDateTime.now())
				|| bargainGoods.getBeginTime().isAfter(LocalDateTime.now())
                || bargainGoods.getStoped().equals(true)
                || !Objects.equals(AuditStatus.CHECKED, bargainGoods.getAuditStatus())
                || Objects.equals(DeleteFlag.NO, bargainGoods.getGoodsStatus())) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080158);
		}
		if (bargainGoods.getLeaveStock() < 1L) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080165);
		}
		//获取商品信息验证商品是否可售
		GoodsInfoOriginalResponse response = goodsInfoQueryProvider.getOriginalById(GoodsInfoByIdRequest.builder().goodsInfoId(bargainGoods.getGoodsInfoId()).build()).getContext();
		if(Objects.isNull(response)
				|| Objects.isNull(response.getGoodsInfo())
				|| response.getGoodsInfo().getDelFlag().equals(DeleteFlag.YES)
				|| response.getGoodsInfo().getAddedFlag().equals(0)
				|| !response.getGoodsInfo().getAuditStatus().equals(CheckStatus.CHECKED)
				|| Objects.equals(Constants.no, response.getGoodsInfo().getVendibility(Boolean.TRUE))) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080158);
		}

		String lockKey = CacheKeyConstant.MARKETING_BARGAIN_ORIGINATE_LOCK.concat(request.getBargainGoodsId().toString()).concat(":").concat(request.getCustomerId());
		RLock lock = redissonClient.getFairLock(lockKey);
		lock.lock();
		try {
			if (bargainRepository.originated(request.getBargainGoodsId(), request.getCustomerId()) > 0) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080159);
			}
			LocalDateTime now = LocalDateTime.now();
			Bargain bargain = new Bargain();
			bargain.setMarketPrice(bargainGoods.getMarketPrice());
			bargain.setBargainNo(Long.valueOf(String.valueOf(System.currentTimeMillis()) + (new Random().nextInt(900) + 100)));
			bargain.setBargainGoodsId(bargainGoods.getBargainGoodsId());
			bargain.setGoodsInfoId(bargainGoods.getGoodsInfoId());
			bargain.setStoreId(bargainGoods.getStoreId());
			LocalDateTime endTime = bargainGoods.getEndTime();
			LocalDateTime bargainEndTime = now.plusHours(1);
			//查询活动有效期
			ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
			configQueryRequest.setConfigType(ConfigType.BARGIN_ACTIVITY_TIME.toValue());
			configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
			SystemConfigTypeResponse configResponse =
					systemConfigQueryProvider
							.findByConfigTypeAndDelFlag(configQueryRequest)
							.getContext();
			if (Objects.nonNull(configResponse.getConfig()) && StringUtils.isNotBlank(configResponse.getConfig().getContext())) {
				bargainEndTime = now.plusHours(Integer.valueOf(configResponse.getConfig().getContext()).intValue());
			}
			bargain.setBeginTime(now);
			bargain.setEndTime(bargainEndTime.compareTo(endTime) < 0 ? bargainEndTime : endTime);
			bargain.setCustomerId(request.getCustomerId());
			bargain.setCustomerAccount(request.getCustomerAccount());
			bargain.setJoinNum(0);
			bargain.setTargetJoinNum(bargainGoods.getTargetJoinNum());
			bargain.setBargainedAmount(BigDecimal.ZERO);
			bargain.setTargetBargainPrice(bargainGoods.getBargainPrice());
			return JSON.parseObject(JSON.toJSONString(add(bargain)), BargainVO.class);
		} finally {
			lock.unlock();
		}
	}

	@Transactional
	public void commitTrade(UpdateTradeRequest request) {
		if (bargainRepository.commitTrade(request.getBargainId(), request.getOrderId()) < 1) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080164);
		}
	}

	@Transactional
	public void cancelTrade(Long bargainId) {
		Optional<BargainJoinGoodsInfo> optional =
				bargainJoinGoodsInfoRepository.findOne(
						BargainWhereCriteriaBuilder.buildJoinGoodsInfo(
								BargainQueryRequest.builder().bargainId(bargainId).build()));
		if (!optional.isPresent()) {
			return;
		}
		Long bargainGoodsId = optional.get().getBargainGoodsId();
		bargainRepository.cancelTrade(bargainId);
		bargainGoodsRepository.addStock(bargainGoodsId, 1L);
	}

	@Transactional
	public void subStock(UpdateStockRequest request) {
		Optional<BargainJoinGoodsInfo> optional =
				bargainJoinGoodsInfoRepository.findOne(
						BargainWhereCriteriaBuilder.buildJoinGoodsInfo(
								BargainQueryRequest.builder().bargainId(request.getBargainId()).build()));
		if (!optional.isPresent()) {
			return;
		}
		if (bargainGoodsRepository.subStock(optional.get().getBargainGoodsId(), request.getStock()) < 1) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080165);
		}
	}

	@Transactional
	public void addStock(UpdateStockRequest request) {
		Optional<BargainJoinGoodsInfo> optional =
				bargainJoinGoodsInfoRepository.findOne(
						BargainWhereCriteriaBuilder.buildJoinGoodsInfo(
								BargainQueryRequest.builder().bargainId(request.getBargainId()).build()));
		if (!optional.isPresent()) {
			return;
		}
		bargainGoodsRepository.addStock(optional.get().getBargainGoodsId(), request.getStock());
	}

	/**
	 * 检查砍价成功的商品是否下过单
	 *
	 * @param bargainId
	 */
	public void canCommit(Long bargainId) {
		if (bargainRepository.canCommit(bargainId) <= 0) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080166);
		}
	}

	public Long getStock(Long bargainId) {
		return bargainJoinBargainGoodsRepository.getOne(bargainId).getBargainGoods().getLeaveStock();
	}
}
