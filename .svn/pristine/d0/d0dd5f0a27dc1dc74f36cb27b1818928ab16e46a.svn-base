package com.wanmi.sbc.marketing.bargainjoin.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoOriginalResponse;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsQueryRequest;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinQueryRequest;
import com.wanmi.sbc.marketing.api.request.bargainjoin.JoinRequest;
import com.wanmi.sbc.marketing.bargain.model.root.Bargain;
import com.wanmi.sbc.marketing.bargain.repository.BargainRepository;
import com.wanmi.sbc.marketing.bargainjoin.model.root.BargainJoin;
import com.wanmi.sbc.marketing.bargainjoin.repository.BargainJoinRepository;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.wanmi.sbc.marketing.bean.vo.BargainJoinVO;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;

import jakarta.persistence.Column;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * <p>帮砍记录业务逻辑</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@Service("BargainJoinService")
public class BargainJoinService {
	@Autowired
	private BargainJoinRepository bargainJoinRepository;

	@Autowired
	private BargainRepository bargainRepository;

	@Autowired
	private RedissonClient redissonClient;

	@Autowired
	private SystemConfigQueryProvider systemConfigQueryProvider;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RedisUtil redisService;

	@Autowired
	private GoodsInfoQueryProvider goodsInfoQueryProvider;

	@Autowired
	private BargainGoodsQueryProvider bargainGoodsQueryProvider;

	@Autowired
	private CustomerQueryProvider customerQueryProvider;

	private static final Long ONE_DAY = 24*60*60*1000L;


	/**
	 * 新增帮砍记录
	 *
	 * @author
	 */
	@Transactional
	public BargainJoin add(BargainJoin entity) {
		entity.setCreateTime(LocalDateTime.now());
		return bargainJoinRepository.save(entity);
	}

	/**
	 * 修改帮砍记录
	 *
	 * @author
	 */
	@Transactional
	public BargainJoin modify(BargainJoin entity) {
		bargainJoinRepository.save(entity);
		return entity;
	}

	/**
	 * 单个删除帮砍记录
	 *
	 * @author
	 */
	@Transactional
	public void deleteById(Long id) {
		bargainJoinRepository.deleteById(id);
	}

	/**
	 * 批量删除帮砍记录
	 *
	 * @author
	 */
	@Transactional
	public void deleteByIdList(List<Long> ids) {
		ids.forEach(id -> bargainJoinRepository.deleteById(id));
	}

	/**
	 * 单个查询帮砍记录
	 *
	 * @author
	 */
	public BargainJoin getById(Long id) {
		return bargainJoinRepository.findById(id).orElse(null);
	}

	/**
	 * 分页查询帮砍记录
	 *
	 * @author
	 */
	public Page<BargainJoin> page(BargainJoinQueryRequest queryReq) {
		return bargainJoinRepository.findAll(
				BargainJoinWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询帮砍记录
	 *
	 * @author
	 */
	public List<BargainJoin> list(BargainJoinQueryRequest queryReq) {
		return bargainJoinRepository.findAll(
				BargainJoinWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 将实体包装成VO
	 *
	 * @author
	 */
	public BargainJoinVO wrapperVo(BargainJoin bargainJoin) {
		if (bargainJoin != null) {
			BargainJoinVO bargainJoinVO = new BargainJoinVO();
			KsBeanUtil.copyPropertiesThird(bargainJoin, bargainJoinVO);
			return bargainJoinVO;
		}
		return null;
	}

	/**
	 * 帮砍砍价商品
	 *
	 * @param request
	 */
	public BargainJoinVO join(JoinRequest request) {
		//1.查询砍价记录
		Optional<Bargain> optional = bargainRepository.findById(request.getBargainId());
		if (!optional.isPresent()) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080158);
		}
		//2.1 验证是否自己帮砍
		Bargain bargain = optional.get();
		String customerId = request.getCustomerId();
		if (Objects.equals(customerId, bargain.getCustomerId())) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080160);
		}
		LocalDateTime now = LocalDateTime.now();
        //2.2 验证活动时间
        if (bargain.getEndTime().isBefore(now)
				|| Objects.equals(bargain.getTargetJoinNum(), bargain.getJoinNum())) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080158);
		}

		//2.3验证商品状态
		GoodsInfoOriginalResponse response = goodsInfoQueryProvider.getOriginalById(GoodsInfoByIdRequest.builder().goodsInfoId(bargain.getGoodsInfoId()).build()).getContext();
		if(Objects.isNull(response)
				|| Objects.isNull(response.getGoodsInfo())
				|| response.getGoodsInfo().getDelFlag().equals(DeleteFlag.YES)
				|| response.getGoodsInfo().getAddedFlag().equals(0)
				|| !response.getGoodsInfo().getAuditStatus().equals(CheckStatus.CHECKED)
				|| Objects.equals(Constants.no, response.getGoodsInfo().getVendibility(Boolean.TRUE))) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080158);
		}

		//2.4 验证用户今日已帮砍次数
		//查询次数限制
		Long incrMax = 1L;
		ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
		configQueryRequest.setConfigType(ConfigType.BARGIN_MAX_NUM_EVERY_DAY.toValue());
		configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
		SystemConfigTypeResponse configResponse =
				systemConfigQueryProvider
						.findByConfigTypeAndDelFlag(configQueryRequest)
						.getContext();
		if (Objects.nonNull(configResponse.getConfig())
				&& StringUtils.isNotBlank(configResponse.getConfig().getContext())) {
			incrMax = Long.valueOf(configResponse.getConfig().getContext());
		}
		String joinNumKey = CacheKeyConstant.MARKETING_BARGAIN_JOIN_NUM.concat(customerId).concat(":").concat(DateUtil.format(now, DateUtil.FMT_TIME_5));
		Long joinNum = redisService.incrBy(joinNumKey, incrMax, 1L, ONE_DAY);
        if (joinNum == 0) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080161);
		}

		// 3.处理帮砍
		BaseResponse<CustomerGetByIdResponse> customerByIdResponse = customerQueryProvider.getCustomerById(CustomerGetByIdRequest.builder().customerId(customerId).build());
		LocalDateTime customerCreateTime = customerByIdResponse.getContext().getCreateTime();
		//判断注册时间是否大于24小时
		boolean isNew =now.isBefore(customerCreateTime.plusHours(24));
		bargain.getGoodsInfoId();
		//查询砍价权重
		BargainGoodsVO bargainGoodsVO = bargainGoodsQueryProvider
				.getById(BargainGoodsQueryRequest.builder().bargainGoodsId(bargain.getBargainGoodsId()).build()).getContext();

		BargainJoin bargainJoin1;
		String joinLock = CacheKeyConstant.MARKETING_BARGAIN_JOIN_LICK.concat(request.getBargainId().toString());
		RLock bargainLock = redissonClient.getLock(joinLock);
		bargainLock.lock();
		try {
			//验证用户是否已帮砍
			BargainJoin bargainJoinOld = bargainJoinRepository.findByJoinCustomerIdAndBargainId(customerId, request.getBargainId());
			if (Objects.nonNull(bargainJoinOld)) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080162);
			}
			//处理帮砍
			bargainJoin1 = transactionTemplate.execute(transactionStatus -> {
				BargainJoin bargainJoin = new BargainJoin();
				BigDecimal bargainedAmount;
				int leaveNum = bargain.getTargetJoinNum() - bargain.getJoinNum();
				if (leaveNum > 0) {
					if (leaveNum == 1) {//最后一个人
						bargainedAmount = bargain.getTargetBargainPrice().subtract(bargain.getBargainedAmount());
					} else {
						//bargainedAmount = calculatePrice(bargain.getTargetBargainPrice().subtract(bargain.getBargainedAmount()), leaveNum);
						if(isNew){
							bargainedAmount = calculatePriceByNewCustomer(bargain.getTargetBargainPrice().subtract(bargain.getBargainedAmount()), leaveNum, bargainGoodsVO.getNewUserWeight(), bargainGoodsVO.getOldUserWeight());
						} else {
							bargainedAmount = calculatePriceByOldCustomer(bargain.getTargetBargainPrice().subtract(bargain.getBargainedAmount()), leaveNum, bargainGoodsVO.getNewUserWeight(), bargainGoodsVO.getOldUserWeight());
						}
					}
					if (bargainRepository.join(bargain.getBargainId(), bargainedAmount) <= 0) {
						throw new SbcRuntimeException(MarketingErrorCodeEnum.K080163);
					}
				} else {
					throw new SbcRuntimeException(MarketingErrorCodeEnum.K080163);
				}
				bargainJoin.setBargainId(bargain.getBargainId());
				bargainJoin.setGoodsInfoId(bargain.getGoodsInfoId());
				bargainJoin.setCustomerId(bargain.getCustomerId());
				bargainJoin.setJoinCustomerId(request.getCustomerId());
				bargainJoin.setBargainAmount(bargainedAmount);
				bargainJoin.setCreateTime(LocalDateTime.now());
				ConfigQueryRequest configQueryRequest1 = new ConfigQueryRequest();
				configQueryRequest1.setDelFlag(DeleteFlag.NO.toValue());
				configQueryRequest1.setConfigType(ConfigType.BARGIN_GOODS_RANDOM_WORDS.toValue());
				List<JSONObject> jsonObjects = JSON.parseArray(systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest1).getContext().getConfig().getContext(), JSONObject.class);
				bargainJoin.setBarginGoodsRandomWords((String) jsonObjects.get(new Random().nextInt(jsonObjects.size())).get("word"));
				return bargainJoinRepository.save(bargainJoin);
			});
		} catch (Exception e) {
			//帮砍失败 回写用户今日帮砍次数
			redisService.decrKey(joinNumKey);
			if (e instanceof SbcRuntimeException) {
				throw e;
			} else {

				throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
			}
		} finally {
			if (bargainLock.isHeldByCurrentThread()) {
				bargainLock.unlock();
			}
		}
		return JSON.parseObject(JSON.toJSONString(add(bargainJoin1)), BargainJoinVO.class);
	}

	/**
	 * 砍价，产生随机金额
	 *
	 * @param total
	 * @param num
	 */
	private BigDecimal calculatePrice(BigDecimal total, int num) {
		BigDecimal price = total.multiply(new BigDecimal(String.valueOf(new Random().nextDouble() * 0.4 + 0.8))).divide(new BigDecimal(num), 2, RoundingMode.DOWN);
		if (price.compareTo(BigDecimal.ZERO) == 0) {
			price = new BigDecimal("0.01");
		}
		return price;
	}

	/**
	 * 砍价，产生随机金额
	 *
	 * @param total
	 * @param num
	 */
	private BigDecimal calculatePriceByNewCustomer(BigDecimal total, int num, Double newUserWeight, Double oldUserWeight ) {
		//新用户砍价公式：（当前段剩余金额*新用户权重）/（新用户权重+（当前段剩余人数-1）*老用户权重）
		BigDecimal denominator = total.multiply(new BigDecimal(newUserWeight));
		BigDecimal numerator = new BigDecimal(num-1).multiply(new BigDecimal(oldUserWeight)).add(new BigDecimal(newUserWeight));
		BigDecimal price = denominator.divide(numerator, 2, RoundingMode.DOWN);
		if (price.compareTo(BigDecimal.ZERO) == 0) {
			price = new BigDecimal("0.01");
		}
		return price;
	}

	private BigDecimal calculatePriceByOldCustomer(BigDecimal total, int num, Double newUserWeight, Double oldUserWeight ) {
		//老用户砍价公式：（当前段剩余金额*老用户权重）/（老用户权重+（当前段剩余人数-1）*老用户权重）
		BigDecimal denominator = total.multiply(new BigDecimal(oldUserWeight));
		BigDecimal numerator = new BigDecimal(num-1).multiply(new BigDecimal(oldUserWeight)).add(new BigDecimal(oldUserWeight));
		BigDecimal price = denominator.divide(numerator, 2, RoundingMode.DOWN);
		if (price.compareTo(BigDecimal.ZERO) == 0) {
			price = new BigDecimal("0.01");
		}
		return price;
	}

}
