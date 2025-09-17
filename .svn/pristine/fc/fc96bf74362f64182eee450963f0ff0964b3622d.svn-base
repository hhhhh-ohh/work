package com.wanmi.sbc.customer.payingmemberlevel.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.PayingMemberLevelQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelModifyRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrightsrel.PayingMemberRightsRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelAddRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelModifyRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.customer.level.repository.CustomerLevelRepository;
import com.wanmi.sbc.customer.payingmembercustomerrel.model.root.PayingMemberCustomerRel;
import com.wanmi.sbc.customer.payingmembercustomerrel.repository.PayingMemberCustomerRelRepository;
import com.wanmi.sbc.customer.payingmemberdiscountrel.model.root.PayingMemberDiscountRel;
import com.wanmi.sbc.customer.payingmemberdiscountrel.repository.PayingMemberDiscountRelRepository;
import com.wanmi.sbc.customer.payingmemberlevel.model.root.PayingMemberLevel;
import com.wanmi.sbc.customer.payingmemberlevel.repository.PayingMemberLevelRepository;
import com.wanmi.sbc.customer.payingmemberprice.repository.PayingMemberPriceRepository;
import com.wanmi.sbc.customer.payingmemberprice.service.PayingMemberPriceService;
import com.wanmi.sbc.customer.payingmemberrecommendrel.model.root.PayingMemberRecommendRel;
import com.wanmi.sbc.customer.payingmemberrecommendrel.repository.PayingMemberRecommendRelRepository;
import com.wanmi.sbc.customer.payingmemberrightsrel.repository.PayingMemberRightsRelRepository;
import com.wanmi.sbc.customer.payingmemberstorerel.model.root.PayingMemberStoreRel;
import com.wanmi.sbc.customer.payingmemberstorerel.repository.PayingMemberStoreRelRepository;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.PayingMemberSettingResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>付费会员等级表业务逻辑</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Service("PayingMemberLevelService")
public class PayingMemberLevelService {
	@Autowired
	private PayingMemberLevelRepository payingMemberLevelRepository;

	@Autowired
	private PayingMemberPriceService payingMemberPriceService;

	@Autowired
	private PayingMemberStoreRelRepository payingMemberStoreRelRepository;

	@Autowired
	private PayingMemberDiscountRelRepository payingMemberDiscountRelRepository;

	@Autowired
	private PayingMemberRecommendRelRepository payingMemberRecommendRelRepository;

	@Autowired
	private PayingMemberPriceRepository payingMemberPriceRepository;

	@Autowired
	private PayingMemberRightsRelRepository payingMemberRightsRelRepository;

	@Autowired
	private PayingMemberCustomerRelRepository payingMemberCustomerRelRepository;

	@Autowired
	private SystemConfigQueryProvider systemConfigQueryProvider;

	@Autowired
	private CustomerLevelRepository customerLevelRepository;
	/**
	 * 新增付费会员等级表
	 * @author zhanghao
	 */
	@Transactional
	public void add(PayingMemberLevelAddRequest payingMemberLevelAddRequest) {
		Long count = count(PayingMemberLevelQueryRequest.builder().delFlag(DeleteFlag.NO).build());
		if (count > 1) {
			throw new SbcRuntimeException(CustomerErrorCodeEnum.K010129);
		} else {
			PayingMemberLevel payingMemberLevel = KsBeanUtil.convert(payingMemberLevelAddRequest, PayingMemberLevel.class);
			payingMemberLevel.setLevelName("V1");
			payingMemberLevel.setDelFlag(DeleteFlag.NO);
			payingMemberLevel.setLevelState(NumberUtils.INTEGER_ZERO);
			payingMemberLevel.setCreateTime(LocalDateTime.now());
			//保存基本信息，保存完获取levelId
			payingMemberLevelRepository.save(payingMemberLevel);
			//保存付费设置
			List<PayingMemberPriceAddRequest> payingMemberPriceAddRequests = payingMemberLevelAddRequest.getPayingMemberPriceAddRequests();
			payingMemberPriceAddRequests.forEach(payingMemberPriceAddRequest -> {
				payingMemberPriceAddRequest.setLevelId(payingMemberLevel.getLevelId());
				payingMemberPriceAddRequest.setCreatePerson(payingMemberLevel.getCreatePerson());
				payingMemberPriceService.add(payingMemberPriceAddRequest);
			});
			//付费会员等级商家范围 自定义选择
			if (payingMemberLevel.getLevelStoreRange() == Constants.ONE) {
				List<PayingMemberStoreRelAddRequest> payingMemberStoreRelAddRequests = payingMemberLevelAddRequest.getPayingMemberStoreRelAddRequests();
				//保存商家与付费会员等级关联
				payingMemberStoreRelRepository.saveAll(payingMemberStoreRelAddRequests.parallelStream().map(payingMemberStoreRelAddRequest -> {
					PayingMemberStoreRel payingMemberStoreRel = KsBeanUtil.convert(payingMemberStoreRelAddRequest, PayingMemberStoreRel.class);
					payingMemberStoreRel.setDelFlag(DeleteFlag.NO);
					payingMemberStoreRel.setLevelId(payingMemberLevel.getLevelId());
					payingMemberStoreRel.setCreateTime(LocalDateTime.now());
					payingMemberStoreRel.setCreatePerson(payingMemberLevel.getCreatePerson());
					return payingMemberStoreRel;
				}).collect(Collectors.toList()));
			}
			//付费会员等级折扣类型 自定义商品设置
			if (payingMemberLevel.getLevelDiscountType() == Constants.ONE) {
				List<PayingMemberDiscountRelAddRequest> payingMemberDiscountRelAddRequests = payingMemberLevelAddRequest.getPayingMemberDiscountRelAddRequests();
				//保存折扣商品与付费会员等级关联
				payingMemberDiscountRelRepository.saveAll(payingMemberDiscountRelAddRequests.parallelStream().map(payingMemberDiscountRelAddRequest -> {
					PayingMemberDiscountRel payingMemberDiscountRel = KsBeanUtil.convert(payingMemberDiscountRelAddRequest, PayingMemberDiscountRel.class);
					payingMemberDiscountRel.setLevelId(payingMemberLevel.getLevelId());
					payingMemberDiscountRel.setDelFlag(DeleteFlag.NO);
					payingMemberDiscountRel.setCreateTime(LocalDateTime.now());
					payingMemberDiscountRel.setCreatePerson(payingMemberLevel.getCreatePerson());
					return payingMemberDiscountRel;
				}).collect(Collectors.toList()));
			}
			//保存推荐商品与付费会员等级关联
			List<PayingMemberRecommendRelAddRequest> payingMemberRecommendRelAddRequests = payingMemberLevelAddRequest.getPayingMemberRecommendRelAddRequests();
			payingMemberRecommendRelRepository.saveAll(payingMemberRecommendRelAddRequests.parallelStream().map(payingMemberRecommendRelAddRequest -> {
				PayingMemberRecommendRel payingMemberRecommendRel = KsBeanUtil.convert(payingMemberRecommendRelAddRequest, PayingMemberRecommendRel.class);
				payingMemberRecommendRel.setLevelId(payingMemberLevel.getLevelId());
				payingMemberRecommendRel.setDelFlag(DeleteFlag.NO);
				payingMemberRecommendRel.setCreateTime(LocalDateTime.now());
				payingMemberRecommendRel.setCreatePerson(payingMemberLevel.getCreatePerson());
				return payingMemberRecommendRel;
			}).collect(Collectors.toList()));
		}
	}

	/**
	 * 修改付费会员等级表
	 * @author zhanghao
	 */
	@Transactional
	public void modify(PayingMemberLevelModifyRequest payingMemberLevelModifyRequest) {
		PayingMemberLevel payingMemberLevel = KsBeanUtil.convert(payingMemberLevelModifyRequest, PayingMemberLevel.class);
		payingMemberLevel.setLevelName("V1");
		payingMemberLevel.setDelFlag(DeleteFlag.NO);
		payingMemberLevel.setUpdateTime(LocalDateTime.now());
		// 保存基本信息
		payingMemberLevelRepository.save(payingMemberLevel);
		// 删除该等级下的付费设置
		payingMemberPriceRepository.deleteByLevelId(payingMemberLevel.getLevelId());
		// 删除该等级下的权益
		payingMemberRightsRelRepository.deleteByLevelId(payingMemberLevel.getLevelId());
		//保存付费设置
		List<PayingMemberPriceModifyRequest> payingMemberPriceModifyRequests = payingMemberLevelModifyRequest.getPayingMemberPriceModifyRequests();
		payingMemberPriceModifyRequests.forEach(payingMemberPriceModifyRequest -> {
			PayingMemberPriceAddRequest payingMemberPriceAddRequest = KsBeanUtil.convert(payingMemberPriceModifyRequest, PayingMemberPriceAddRequest.class);
			payingMemberPriceAddRequest.setPayingMemberRightsRelAddRequests(KsBeanUtil.convertList(payingMemberPriceModifyRequest.getPayingMemberRightsRelModifyRequests(), PayingMemberRightsRelAddRequest.class));
			payingMemberPriceAddRequest.setLevelId(payingMemberLevel.getLevelId());
			payingMemberPriceAddRequest.setCreatePerson(payingMemberLevel.getCreatePerson());
			payingMemberPriceService.add(payingMemberPriceAddRequest);
		});
		// 删除商家关联
		payingMemberStoreRelRepository.deleteByLevelId(payingMemberLevel.getLevelId());
		//付费会员等级商家范围 自定义选择
		if (payingMemberLevel.getLevelStoreRange() == Constants.ONE) {
			List<PayingMemberStoreRelModifyRequest> payingMemberStoreRelModifyRequests = payingMemberLevelModifyRequest.getPayingMemberStoreRelModifyRequests();
			//保存商家与付费会员等级关联
			payingMemberStoreRelRepository.saveAll(payingMemberStoreRelModifyRequests.parallelStream().map(payingMemberStoreRelModifyRequest -> {
				PayingMemberStoreRel payingMemberStoreRel = KsBeanUtil.convert(payingMemberStoreRelModifyRequest, PayingMemberStoreRel.class);
				payingMemberStoreRel.setDelFlag(DeleteFlag.NO);
				payingMemberStoreRel.setLevelId(payingMemberLevel.getLevelId());
				payingMemberStoreRel.setCreateTime(LocalDateTime.now());
				payingMemberStoreRel.setCreatePerson(payingMemberLevel.getCreatePerson());
				return payingMemberStoreRel;
			}).collect(Collectors.toList()));
		}
		//删除 折扣商品关联
		payingMemberDiscountRelRepository.deleteByLevelId(payingMemberLevel.getLevelId());
		//付费会员等级折扣类型 自定义商品设置
		if (payingMemberLevel.getLevelDiscountType() == Constants.ONE) {
			//重置付费会员等级所有商品统一设置 折扣
			payingMemberLevel.setLevelAllDiscount(null);
			List<PayingMemberDiscountRelModifyRequest> payingMemberDiscountRelModifyRequests = payingMemberLevelModifyRequest.getPayingMemberDiscountRelModifyRequests();
			//保存折扣商品与付费会员等级关联
			payingMemberDiscountRelRepository.saveAll(payingMemberDiscountRelModifyRequests.parallelStream().map(payingMemberDiscountRelModifyRequest -> {
				PayingMemberDiscountRel payingMemberDiscountRel = KsBeanUtil.convert(payingMemberDiscountRelModifyRequest, PayingMemberDiscountRel.class);
				payingMemberDiscountRel.setLevelId(payingMemberLevel.getLevelId());
				payingMemberDiscountRel.setDelFlag(DeleteFlag.NO);
				payingMemberDiscountRel.setCreateTime(LocalDateTime.now());
				payingMemberDiscountRel.setCreatePerson(payingMemberLevel.getCreatePerson());
				return payingMemberDiscountRel;
			}).collect(Collectors.toList()));
		}
		//删除推荐商品关联
		payingMemberRecommendRelRepository.deleteByLevelId(payingMemberLevel.getLevelId());
		//保存推荐商品与付费会员等级关联
		List<PayingMemberRecommendRelModifyRequest> payingMemberRecommendRelModifyRequests = payingMemberLevelModifyRequest.getPayingMemberRecommendRelModifyRequests();
		payingMemberRecommendRelRepository.saveAll(payingMemberRecommendRelModifyRequests.parallelStream().map(payingMemberRecommendRelModifyRequest -> {
			PayingMemberRecommendRel payingMemberRecommendRel = KsBeanUtil.convert(payingMemberRecommendRelModifyRequest, PayingMemberRecommendRel.class);
			payingMemberRecommendRel.setLevelId(payingMemberLevel.getLevelId());
			payingMemberRecommendRel.setDelFlag(DeleteFlag.NO);
			payingMemberRecommendRel.setCreateTime(LocalDateTime.now());
			payingMemberRecommendRel.setCreatePerson(payingMemberLevel.getCreatePerson());
			return payingMemberRecommendRel;
		}).collect(Collectors.toList()));
	}

	/**
	 * 单个删除付费会员等级表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteById(PayingMemberLevel entity) {
		payingMemberLevelRepository.save(entity);
	}

	/**
	 * 批量删除付费会员等级表
	 * @author zhanghao
	 */
	@Transactional
	public void deleteByIdList(List<Integer> ids) {
		payingMemberLevelRepository.deleteByIdList(ids);
	}

	/**
	 * 单个查询付费会员等级表
	 * @author zhanghao
	 */
	public PayingMemberLevel getOne(Integer id){
		return payingMemberLevelRepository.findByLevelIdAndDelFlag(id, DeleteFlag.NO)
		.orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K999999, "付费会员等级表不存在"));
	}

	/**
	 * 分页查询付费会员等级表
	 * @author zhanghao
	 */
	public Page<PayingMemberLevel> page(PayingMemberLevelQueryRequest queryReq){
		return payingMemberLevelRepository.findAll(
				PayingMemberLevelWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * 列表查询付费会员等级表
	 * @author zhanghao
	 */
	public List<PayingMemberLevel> list(PayingMemberLevelQueryRequest queryReq){
		return payingMemberLevelRepository.findAll(PayingMemberLevelWhereCriteriaBuilder.build(queryReq));
	}



	/**
	 * 将实体包装成VO
	 * @author zhanghao
	 */
	public PayingMemberLevelVO wrapperVo(PayingMemberLevel payingMemberLevel) {
		if (payingMemberLevel != null){
			PayingMemberLevelVO payingMemberLevelVO = KsBeanUtil.convert(payingMemberLevel, PayingMemberLevelVO.class);
			return payingMemberLevelVO;
		}
		return null;
	}

	/**
	 * @description 查询总数量
	 * @author zhanghao
	 */
	public Long count(PayingMemberLevelQueryRequest queryReq) {
		return payingMemberLevelRepository.count(PayingMemberLevelWhereCriteriaBuilder.build(queryReq));
	}

	/**
	 * 批量调整等级状态
	 * @param levelState
	 */
	@Transactional
	public void modifyLevelState(Integer levelState) {
		payingMemberLevelRepository.modifyLevelStatus(levelState);
	}

	/**
	 * 根据会员id查询等级
	 * @param customerId
	 * @return
	 */
	public List<PayingMemberLevelVO> listByCustomerId(String customerId, Boolean defaultFlag) {
		//查询付费会员标签
		ConfigQueryRequest request = new ConfigQueryRequest();
		request.setConfigType(ConfigType.PAYING_MEMBER.toString());
		request.setDelFlag(DeleteFlag.NO.toValue());
		ConfigVO config = systemConfigQueryProvider.findByConfigTypeAndDelFlag(request).getContext().getConfig();
		PayingMemberSettingResponse res = JSONObject.parseObject(config.getContext(), PayingMemberSettingResponse.class);
		//查询等级
		List<PayingMemberLevel> levelList = new ArrayList<>();
		//拥有的等级列表
		List<Integer> ownList = new ArrayList<>();
		//指定客户查询付费会员等级
		if (StringUtils.isNotBlank(customerId)) {
			List<PayingMemberCustomerRel> customerRels = payingMemberCustomerRelRepository.findByCustomer(customerId, LocalDate.now());
			if (CollectionUtils.isNotEmpty(customerRels)) {
				ownList = customerRels.stream().map(PayingMemberCustomerRel::getLevelId).distinct().collect(Collectors.toList());
				levelList = payingMemberLevelRepository.findAllByDelFlagAndLevelIdIn(DeleteFlag.NO, ownList);
			}
		}
		//没有会员关联的等级，则查询全部
		if (defaultFlag && CollectionUtils.isEmpty(levelList)) {
			levelList = payingMemberLevelRepository.findActiveLevel();
		}

		List<Integer> finalOwnList = ownList;
		List<PayingMemberLevelVO> levelVOS = levelList.stream().map(entity -> {
			PayingMemberLevelVO levelVO = this.wrapperVo(entity);
			boolean ownFlag = finalOwnList.contains(levelVO.getLevelId());
			levelVO.setOwnFlag(ownFlag);
			levelVO.setLabel(res.getLabel());
			return levelVO;
		}).collect(Collectors.toList());
		return levelVOS;
	}
}

