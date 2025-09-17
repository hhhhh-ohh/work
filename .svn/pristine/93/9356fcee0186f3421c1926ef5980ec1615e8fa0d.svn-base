package com.wanmi.sbc.marketing.bargaingoods.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.JpaUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.api.response.store.StoreListForDistributionResponse;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreSimpleInfo;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.cate.GoodsCateQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.GoodsCateChildCateIdsByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdsRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoCountByConditionRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoAndOtherByIdsResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.request.bargaingoods.*;
import com.wanmi.sbc.marketing.bargain.model.root.Bargain;
import com.wanmi.sbc.marketing.bargain.repository.BargainRepository;
import com.wanmi.sbc.marketing.bargaingoods.model.root.BargainGoods;
import com.wanmi.sbc.marketing.bargaingoods.repository.BargainGoodsRepository;
import com.wanmi.sbc.marketing.bean.dto.BargainGoodsInfoForAddDTO;
import com.wanmi.sbc.marketing.bean.enums.BargainActivityState;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.api.response.systemconfig.SystemConfigTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>砍价商品业务逻辑</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Service("BargainGoodsService")
@Slf4j
public class BargainGoodsService {
	@Autowired
	private BargainGoodsRepository bargainGoodsRepository;

	@Autowired
	private BargainRepository bargainRepository;

	@Autowired
	private GoodsInfoQueryProvider goodsInfoQueryProvider;

	@Autowired
	private SystemConfigQueryProvider systemConfigQueryProvider;

	@Autowired
	private StoreQueryProvider storeQueryProvider;

	@Autowired
	private GoodsCateQueryProvider goodsCateQueryProvider;

	@Autowired private EntityManager entityManager;

	/**
	 * 每人最小砍价金额
	 */
	private static final BigDecimal MIN_BARGAIN_PRICE = BigDecimal.valueOf(0.01);

	/**
	 * @description 新增砍价商品
	 * @author  lipeixian
	 * @date 2022/5/21 2:39 下午
	 * @param addRequest
	 * @return void
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void add(BargainGoodsActivityAddRequest addRequest) {

		// 0. 收集skuIds
		List<BargainGoodsInfoForAddDTO> bargainGoodsInfos = addRequest.getGoodsInfos();
		List<String> goodsInfoIds = bargainGoodsInfos.stream().map(BargainGoodsInfoForAddDTO::getGoodsInfoId).collect(Collectors.toList());

		// 1.1 校验时间冲突：商品同一时间段仅可参与一个砍价活动
		this.checkConflictBargainGoods(addRequest);

		// 1.2 校验商品状态：商品删除、下架、禁售、不可售、电子卡券商品无法添加，
		// 1.3 校验商品价格：帮砍金额不能大于市场价、平均每人帮砍金额需大于0.01
		List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.originalListByIds(GoodsInfoListByIdsRequest.builder()
				.goodsInfoIds(goodsInfoIds).build()).getContext().getGoodsInfos();
		this.checkBargainGoodsAndPrice(goodsInfos, addRequest);

		// 2.1 确定审核状态，查询审核开关
		AuditStatus auditStatus = this.getAuditSetting() ? AuditStatus.WAIT_CHECK : AuditStatus.CHECKED;
		// 2.2 确定活动停止状态（根据店铺状态填充砍价商品活动状态，店铺关店或过期，应将活动设为停止）
		StoreVO storeVO = storeQueryProvider.getById(StoreByIdRequest.builder().storeId(addRequest.getStoreId()).build()).getContext().getStoreVO();
		Boolean stopped = Boolean.FALSE;
		if (StoreState.CLOSED.equals(storeVO.getStoreState()) || LocalDateTime.now().isAfter(storeVO.getContractEndDate())) {
			stopped = Boolean.TRUE;
		}
		// 2.3 收集虚拟商品skuIdSet，虚拟商品始终包邮，不受活动维度的包邮开关限制
		Set<String> virtualGoodsSkuIdSet = goodsInfos.stream().filter(item -> GoodsType.VIRTUAL_GOODS.toValue() == item.getGoodsType())
				.map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toSet());

		// 3. 构造实体列表
		List<BargainGoods> bargainGoodsSaveList = new ArrayList<>();
		// 将商品信息转成map
		Map<String, GoodsInfoVO> goodsInfoMap =
				goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        for (BargainGoodsInfoForAddDTO addDTO : bargainGoodsInfos) {
			String goodsInfoId = addDTO.getGoodsInfoId();
			// 确定商品是否包邮，虚拟商品始终包邮，否则取活动维度包邮开关
			DeleteFlag freightFreeFlag = virtualGoodsSkuIdSet.contains(goodsInfoId) ? DeleteFlag.YES : addRequest.getFreightFreeFlag();
			BargainGoods bargainGoods = KsBeanUtil.copyPropertiesThird(addDTO, BargainGoods.class);
			bargainGoods.setStoreId(storeVO.getStoreId());
			bargainGoods.setCompanyCode(storeVO.getCompanyCode());
			bargainGoods.setBeginTime(addRequest.getBeginTime());
			bargainGoods.setEndTime(addRequest.getEndTime());
			bargainGoods.setCreateTime(LocalDateTime.now());
			bargainGoods.setLeaveStock(bargainGoods.getBargainStock());
			bargainGoods.setAuditStatus(auditStatus);
			bargainGoods.setDelFlag(DeleteFlag.NO);
			bargainGoods.setGoodsStatus(DeleteFlag.YES);
			bargainGoods.setStoped(stopped);
			bargainGoods.setFreightFreeFlag(freightFreeFlag);
			bargainGoods.setNewUserWeight(bargainGoods.getNewUserWeight());
			bargainGoods.setOldUserWeight(bargainGoods.getOldUserWeight());
			// 填充商品相关信息
			GoodsInfoVO goodsInfoVO = goodsInfoMap.get(addDTO.getGoodsInfoId());
			if (Objects.nonNull(goodsInfoVO)) {
				bargainGoods.setGoodsInfoNo(goodsInfoVO.getGoodsInfoNo());
				bargainGoods.setGoodsInfoName(goodsInfoVO.getGoodsInfoName());
				bargainGoods.setGoodsCateId(goodsInfoVO.getCateId());
			}
			bargainGoodsSaveList.add(bargainGoods);
        }

		// 4. 批量保存
		bargainGoodsRepository.saveAll(bargainGoodsSaveList);
	}

	/**
	 * 校验砍价商品状态和砍价金额
	 */
	private void checkBargainGoodsAndPrice(List<GoodsInfoVO> goodsInfos, BargainGoodsActivityAddRequest addRequest) {
		if (CollectionUtils.isEmpty(goodsInfos) || goodsInfos.size() != addRequest.getGoodsInfos().size()) {
			// 商品不存在
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000008);
		}
		// 将关联的商品信息转成map
		Map<String, BargainGoodsInfoForAddDTO> addBargainGoodsMap =
				addRequest.getGoodsInfos().stream().collect(Collectors.toMap(BargainGoodsInfoForAddDTO::getGoodsInfoId, Function.identity()));
        for (GoodsInfoVO goodsInfo : goodsInfos) {
			BargainGoodsInfoForAddDTO addDTO = addBargainGoodsMap.get(goodsInfo.getGoodsInfoId());
			// 获取入参砍价金额、入参市场价、真实市场价
			BigDecimal bargainPrice = addDTO.getBargainPrice();
			BigDecimal addMarketPrice = addDTO.getMarketPrice();
			BigDecimal relMarketPrice = goodsInfo.getMarketPrice();

			// 1.1 市场价已发生更改（页面停留的情况）
			if (addMarketPrice.compareTo(relMarketPrice) != 0) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080156,
						new Object[] {String.format("存在[%s]市场价已更改", goodsInfo.getGoodsInfoNo())});
			}
			// 1.2 帮砍金额不能大于市场价
			if (bargainPrice.compareTo(addMarketPrice) > 0) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080156,
						new Object[] {String.format("存在[%s]砍价金额大于市场价", goodsInfo.getGoodsInfoNo())});
			}
			// 1.3 平均每人帮砍金额需大于0.01
			if (bargainPrice.compareTo(new BigDecimal(addDTO.getTargetJoinNum()).multiply(MIN_BARGAIN_PRICE)) <= 0) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080154,
						new Object[] {String.format("[%s]", goodsInfo.getGoodsInfoNo())});
			}

			// 2.1 不支持存在电子卡券商品
			if (GoodsType.ELECTRONIC_COUPON_GOODS.toValue() == goodsInfo.getGoodsType()) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080156,
						new Object[] {String.format("存在电子卡券商品[%s]", goodsInfo.getGoodsInfoNo())});
			}
			// 2.2 存在不可售的商品（已删除、已下架、禁售、不可售）
			if (DeleteFlag.YES == goodsInfo.getDelFlag()
					|| AddedFlag.NO.toValue() == goodsInfo.getAddedFlag()
					|| CheckStatus.FORBADE == goodsInfo.getAuditStatus()
					|| Constants.no.equals(goodsInfo.getVendibility())) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080156,
						new Object[] {String.format("存在不可售商品[%s]", goodsInfo.getGoodsInfoNo())});
			}
        }
	}

	/**
	 * 校验活动商品是否冲突
	 */
	private void checkConflictBargainGoods(BargainGoodsActivityAddRequest addRequest) {
		List<String> goodsInfoIds = addRequest.getGoodsInfos().stream().map(BargainGoodsInfoForAddDTO::getGoodsInfoId).collect(Collectors.toList());
		List<String> conflictSkuIds = this.findBargainGoodsInActivityTime(addRequest.getBeginTime(), addRequest.getEndTime(), goodsInfoIds);
		if (CollectionUtils.isNotEmpty(conflictSkuIds)) {
			// 若存在有时间冲突的商品，则查出 goodsInfoNo 填充错误消息
			List<GoodsInfoVO> conflictGoodsInfos = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder()
					.goodsInfoIds(conflictSkuIds).build()).getContext().getGoodsInfos();
			if (CollectionUtils.isNotEmpty(conflictGoodsInfos)) {
				List<String> conflictGoodsNoList = conflictGoodsInfos.stream().map(GoodsInfoVO::getGoodsInfoNo).collect(Collectors.toList());
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080151, new Object[] {conflictGoodsNoList});
			} else {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000008);
			}
		}
	}

	/**
	 * 获取砍价商品审核开关
	 */
	private boolean getAuditSetting() {
		ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
		configQueryRequest.setDelFlag(DeleteFlag.NO.toValue());
		configQueryRequest.setConfigType(ConfigType.BARGIN_GOODS_AUDIT.toValue());
		SystemConfigTypeResponse systemConfigResponse = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext();
		if (Objects.nonNull(systemConfigResponse) && systemConfigResponse.getConfig().getStatus() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 修改砍价商品
	 */
	@Transactional(rollbackFor = Exception.class)
	public void modify(BargainGoodsModifyRequest modifyRequest) {
		Long bargainGoodsId = modifyRequest.getBargainGoodsId();
		Long addBargainStock = modifyRequest.getAddBargainStock();
		// 验证砍价商品是否存在
		BargainGoods bargainGoods = bargainGoodsRepository.findById(bargainGoodsId).orElse(null);
		if (Objects.isNull(bargainGoods)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
		}
		// 校验已审核通过状态
		if (bargainGoods.getAuditStatus() != AuditStatus.CHECKED) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080155, new Object[] {"审核未通过"});
		}
		// 校验活动状态
		if (LocalDateTime.now().isAfter(bargainGoods.getEndTime()) || bargainGoods.getStoped()) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080155, new Object[] {"活动已结束"});
		}
		// 追加指定库存（独立库存和剩余库存）
		bargainGoodsRepository.addBargainStockAndLeaveStockById(bargainGoodsId, addBargainStock);
	}

	/**
	 * 单个查询砍价商品
	 *
	 * @author
	 */
	public BargainGoodsVO getById(BargainGoodsQueryRequest bargainGoodsPageReq) {
		BargainGoodsVO bargainGoodsVO = wrapperVo(bargainGoodsRepository.findById(bargainGoodsPageReq.getBargainGoodsId()).orElse(null));
		if (Objects.isNull(bargainGoodsVO)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		
		//如果是登录用于则查询是否有发起砍价
		if (StringUtils.isNotEmpty(bargainGoodsPageReq.getUserId())){
			List<Bargain> bargains = bargainRepository.forCustomer(bargainGoodsPageReq.getUserId(), Collections.singletonList(bargainGoodsPageReq.getBargainGoodsId()));
			if (CollectionUtils.isNotEmpty(bargains)) {
				bargainGoodsVO.setBargainVO(JSON.parseObject(JSON.toJSONString(bargains.get(0)), BargainVO.class));
			}
		}
		return bargainGoodsVO;
	}

	/**
	 * 分页查询砍价商品
	 *
	 * @author
	 */
	public Page<BargainGoods> page(BargainGoodsQueryRequest queryReq) {
		return bargainGoodsRepository.findAll(
				BargainGoodsWhereCriteriaBuilder.build(queryReq),
				queryReq.getPageRequest());
	}

	/**
	 * @description 分页查询砍价商品
	 * @author  lipeixian
	 * @date 2022/5/23 7:32 下午
	 * @param queryReq
	 * @return com.wanmi.sbc.common.base.MicroServicePage<com.wanmi.sbc.goods.bean.vo.BargainGoodsVO>
	 **/
	public MicroServicePage<BargainGoodsVO> pageNew(BargainGoodsQueryRequest queryReq) {

		// 1. 根据商家名称模糊查询
		if (StringUtils.isNotBlank(queryReq.getSupplierName())) {

			// 商家查询条件
			ListStoreByNameRequest listStoreByNameRequest = new ListStoreByNameRequest();
            listStoreByNameRequest.setSupplierName(queryReq.getSupplierName());
			StoreListForDistributionResponse storeListForDistributionResponse = storeQueryProvider.listBySupplierName(listStoreByNameRequest).getContext();

			if (Objects.isNull(storeListForDistributionResponse) || CollectionUtils.isEmpty(storeListForDistributionResponse.getStoreSimpleInfos())) {
				// 查询结果为空，直接返回
				return new MicroServicePage<>(Collections.emptyList(), queryReq.getPageable(), NumberUtils.LONG_ZERO);
			}

			// 收集并组装 storeIds 查询条件
			List<Long> storeIds = storeListForDistributionResponse.getStoreSimpleInfos().stream().map(StoreSimpleInfo::getStoreId).collect(Collectors.toList());
			queryReq.setStoreIds(storeIds);
		}

		// 2. 平台类目精确查询
		if (Objects.nonNull(queryReq.getCateId())) {

			// 构造类目id列表
			if (Objects.nonNull(queryReq.getCateId())) {
				// 根据目标类目id查询子类目ids
				List<Long> childCateIdList = goodsCateQueryProvider.getChildCateIdById(GoodsCateChildCateIdsByIdRequest.builder()
						.cateId(queryReq.getCateId()).build()).getContext().getChildCateIdList();
				childCateIdList.add(queryReq.getCateId());
				// 收集并组装 cateIds 查询条件
				queryReq.setCateIds(childCateIdList);
			}
		}

		// 查询砍价商品
		Page<BargainGoods> bargainGoodsPage = bargainGoodsRepository.findAll(BargainGoodsWhereCriteriaBuilder.build(queryReq), queryReq.getPageRequest());
		Page<BargainGoodsVO> newPage = bargainGoodsPage.map(this::wrapperVo);
        List<BargainGoodsVO> bargainGoodsList = newPage.getContent();

        if (CollectionUtils.isEmpty(bargainGoodsList)) {
			return new MicroServicePage<>(newPage, queryReq.getPageable());
		}

        // 查询 goodsInfo 信息
		List<String> goodsInfoIds = bargainGoodsList.stream().map(BargainGoodsVO::getGoodsInfoId).collect(Collectors.toList());
		GoodsInfoAndOtherByIdsResponse goodsInfoResponse =
				goodsInfoQueryProvider.getSkuAndOtherInfoByIds(GoodsInfoByIdsRequest.builder().goodsInfoIds(goodsInfoIds).build()).getContext();

		// 填充 goodsInfo 信息
		this.buildSku(bargainGoodsList, goodsInfoResponse.getGoodsInfos());
		// 填充平台类目名称
		this.buildCateName(bargainGoodsList, goodsInfoResponse.getGoodsCateVOList());
        // 填充商品规格
		this.buildGoodsInfoSpecName(bargainGoodsList, goodsInfoResponse.getSpecDetailRelVOList());
        // 转换活动状态
        this.buildBargainGoodsState(bargainGoodsList);
		// 填充商家名称、商家编号
		this.buildSupplierNameAndCode(bargainGoodsList, queryReq);
        return new MicroServicePage<>(newPage, queryReq.getPageable());
	}

	/**
	 * 自动审核 已过期 && 待审核 的砍价商品（商家自动审核自己的，平台自动审核所有的）
	 * @param storeId
	 */
	@Transactional(rollbackFor = Exception.class)
	public void autoBatchAuditForOverTime(Long storeId) {
		bargainGoodsRepository.autoBatchAuditForOverTime(AuditStatus.NOT_PASS.ordinal(), "活动已结束，审核失败", storeId);
	}

	/**
	 * @description
	 * @author  wur
	 * @date: 2022/8/17 19:06
	 * @param bargainGoodsList
	 * @return
	 **/
	private void buildSku(List<BargainGoodsVO> bargainGoodsList, List<GoodsInfoVO> goodsInfoVOList) {
		if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
			Map<String, GoodsInfoVO> goodsMap = goodsInfoVOList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
			bargainGoodsList.forEach(bargainGoodsVO -> bargainGoodsVO.setGoodsInfoVO(goodsMap.get(bargainGoodsVO.getGoodsInfoId())));
		}
	}

	/**
	 * 处理商品所属商家名称和编码
	 */
	private void buildSupplierNameAndCode(List<BargainGoodsVO> bargainGoodsList, BargainGoodsQueryRequest queryReq) {
		// BOSS查询，需要填充商家名称、编码，商家只要查询自己的店铺状态即可，通过queryReq是否有StoreId来区分
	    if (Objects.nonNull(queryReq.getStoreId())) {
			StoreByIdResponse storeByIdResponse =
					storeQueryProvider.getById(StoreByIdRequest.builder().storeId(queryReq.getStoreId()).build()).getContext();
			// 商家已删除、关店、过期，活动置为停止
			if (!isNormalStore(storeByIdResponse.getStoreVO())) {
				bargainGoodsList.forEach(bargainGoodsVO -> bargainGoodsVO.setBargainActivityState(BargainActivityState.TERMINATED_ACTIVITY));
			}
	        return;
        }
        List<Long> storeIdList = bargainGoodsList.stream().map(bargainGoodsVO -> bargainGoodsVO.getGoodsInfoVO().getStoreId()).collect(Collectors.toList());
        ListStoreByIdsResponse listStoreByIdsResponse = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIdList).build()).getContext();
        if (Objects.nonNull(listStoreByIdsResponse) && CollectionUtils.isNotEmpty(listStoreByIdsResponse.getStoreVOList())) {
			Map<Long, StoreVO> storeMap = listStoreByIdsResponse.getStoreVOList().stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
			bargainGoodsList.forEach(bargainGoodsVO -> {
				StoreVO storeVO = storeMap.get(bargainGoodsVO.getGoodsInfoVO().getStoreId());
				if (Objects.nonNull(storeVO)) {
					// 填充商家信息
					bargainGoodsVO.setSupplierName(storeVO.getSupplierName());
					bargainGoodsVO.setStoreName(storeVO.getStoreName());
					bargainGoodsVO.setCompanyCode(storeVO.getCompanyCode());
				}
				// 商家已删除、关店、过期，活动置为停止
				if (!isNormalStore(storeVO)) {
					bargainGoodsVO.setBargainActivityState(BargainActivityState.TERMINATED_ACTIVITY);
				}
			});
        }
    }

	/**
	 * 拼团互斥验证
	 * @param request 入参
	 * @return 验证结果
	 */
	public void validate(BargainGoodsValidateRequest request) {
		BargainGoodsQueryRequest queryRequest = new BargainGoodsQueryRequest();
		queryRequest.setDelFlag(DeleteFlag.NO);
		queryRequest.setStoreId(request.getStoreId());
		queryRequest.setNotId(request.getNotId());
		//活动结束时间 >= 交叉开始时间
		queryRequest.setEndTimeBegin(request.getCrossBeginTime());
		//活动开始时间 <= 交叉结束时间
		queryRequest.setBeginTimeEnd(request.getCrossEndTime());
		//仅限待审核、已审核
		queryRequest.setAuditStatusList(Arrays.asList(AuditStatus.WAIT_CHECK, AuditStatus.CHECKED));
		//未关闭
		queryRequest.setStoped(Boolean.FALSE);
		queryRequest.setPageSize(100);
		queryRequest.putSort("bargainGoodsId", SortType.ASC.toValue());
		boolean res = false;
		for (int pageNo = 0; ; pageNo++) {
			queryRequest.setPageNum(pageNo);
			Page<BargainGoods> activityPage = this.pageCols(queryRequest, Arrays.asList("goodsInfoId", "goodsCateId"));
			if (activityPage.getTotalElements() == 0) {
				break;
			}
			if (CollectionUtils.isNotEmpty(activityPage.getContent())) {
				//所有商品
				if(Boolean.TRUE.equals(request.getAllFlag())){
					res = true;
					break;
				}
				List<String> skuIds = activityPage.stream().map(BargainGoods::getGoodsInfoId).toList();
				//验证商品相关品牌是否存在
				if (CollectionUtils.isNotEmpty(request.getBrandIds())) {
					if (this.checkGoodsAndBrand(skuIds, request.getBrandIds())) {
						res = true;
						break;
					}
				} else if (CollectionUtils.isNotEmpty(request.getStoreCateIds())) {
					//验证商品相关店铺分类是否存在
					if (this.checkGoodsAndStoreCate(skuIds, request.getStoreCateIds())) {
						res = true;
						break;
					}
				} else if (CollectionUtils.isNotEmpty(request.getSkuIds())
						&& request.getSkuIds().stream().anyMatch(skuIds::contains)) {
					//验证自定义货品范围
					res = true;
					break;
				}
				// 最后一页，退出循环
				if (pageNo >= activityPage.getTotalPages() - 1) {
					break;
				}
			}
		}
		if(res){
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080026, new Object[]{"砍价"});
		}
	}

	/**
	 * 验证商品、品牌的重合
	 * @param skuIds 商品skuId
	 * @param brandIds 品牌Id
	 * @return 重合结果
	 */
	public Boolean checkGoodsAndBrand(List<String> skuIds, List<Long> brandIds) {
		GoodsInfoCountByConditionRequest count = new GoodsInfoCountByConditionRequest();
		count.setGoodsInfoIds(skuIds);
		count.setBrandIds(brandIds);
		count.setDelFlag(DeleteFlag.NO.toValue());
		return goodsInfoQueryProvider.countByCondition(count).getContext().getCount() > 0;
	}

	/**
	 * 验证商品、店铺分类的重合
	 * @param skuIds 商品skuId
	 * @param cateIds 店铺分类Id
	 * @return 重合结果
	 */
	public Boolean checkGoodsAndStoreCate(List<String> skuIds, List<Long> cateIds) {
		GoodsInfoCountByConditionRequest count = new GoodsInfoCountByConditionRequest();
		count.setGoodsInfoIds(skuIds);
		count.setStoreCateIds(cateIds);
		count.setDelFlag(DeleteFlag.NO.toValue());
		return goodsInfoQueryProvider.countByCondition(count).getContext().getCount() > 0;
	}

	/**
	 * 自定义字段的列表查询
	 * @param request 参数
	 * @param cols 列名
	 * @return 列表
	 */
	public Page<BargainGoods> pageCols(BargainGoodsQueryRequest request, List<String> cols) {
		CriteriaBuilder countCb = entityManager.getCriteriaBuilder();
		Specification<BargainGoods> spec = BargainGoodsWhereCriteriaBuilder.build(request);
		CriteriaQuery<Long> countCq = countCb.createQuery(Long.class);
		Root<BargainGoods> countRt = countCq.from(BargainGoods.class);
		countCq.select(countCb.count(countRt));
		Predicate countPredicate = spec.toPredicate(countRt, countCq, countCb);
		if (countPredicate != null) {
			countCq.where(countPredicate);
		}
		long sum = entityManager.createQuery(countCq).getResultList().stream().filter(Objects::nonNull)
				.mapToLong(s -> s).sum();
		if (sum == 0) {
			return PageableExecutionUtils.getPage(Collections.emptyList(), request.getPageable(), () -> sum);
		}
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<BargainGoods> rt = cq.from(BargainGoods.class);
		cq.multiselect(cols.stream().map(c -> rt.get(c).alias(c)).collect(Collectors.toList()));
		Predicate predicate = spec.toPredicate(rt, cq, cb);
		if (predicate != null) {
			cq.where(predicate);
		}
		cq.orderBy(QueryUtils.toOrders(request.getSort(), rt, cb));
		TypedQuery<Tuple> query = entityManager.createQuery(cq);
		query.setFirstResult((int) request.getPageRequest().getOffset());
		query.setMaxResults(request.getPageRequest().getPageSize());
		return PageableExecutionUtils.getPage(this.converter(query.getResultList(), cols), request.getPageable(), () -> sum);
	}

	/**
	 * 查询对象转换
	 * @param result
	 * @return
	 */
	private List<BargainGoods> converter(List<Tuple> result, List<String> cols) {
		return result.stream().map(item -> {
			BargainGoods activity = new BargainGoods();
			activity.setGoodsInfoId(JpaUtil.toString(item,"goodsInfoId", cols));
			activity.setGoodsCateId(JpaUtil.toLong(item,"goodsCateId", cols));
			return activity;
		}).collect(Collectors.toList());
	}

	/**
	 * 店铺是否正常
	 */
	private boolean isNormalStore(StoreVO storeVO) {
		if (Objects.isNull(storeVO)) {
			return false;
		}
		LocalDateTime now = LocalDateTime.now();
		if (!(Objects.equals(DeleteFlag.NO, storeVO.getDelFlag())
				&& Objects.equals(StoreState.OPENING, storeVO.getStoreState())
				&& (now.isBefore(storeVO.getContractEndDate()) || now.isEqual(storeVO.getContractEndDate()))
				&& (now.isAfter(storeVO.getContractStartDate()) || now.isEqual(storeVO.getContractStartDate())))
		) {
			return false;
		}
		return true;
	}

	/**
	 * 处理商品类目名称
	 */
	private void buildCateName(List<BargainGoodsVO> bargainGoodsList, List<GoodsCateVO> goodsCateVOList) {
		if (CollectionUtils.isNotEmpty(goodsCateVOList)) {
			Map<Long, String> cateNameMap = goodsCateVOList.stream().collect(Collectors.toMap(GoodsCateVO::getCateId, GoodsCateVO::getCateName));
			bargainGoodsList.forEach(bargainGoodsVO -> {
				if (Objects.nonNull(bargainGoodsVO.getGoodsInfoVO())) {
					bargainGoodsVO.getGoodsInfoVO().setCateName(cateNameMap.get(bargainGoodsVO.getGoodsInfoVO().getCateId()));
				}
			});
		}
	}

	/**
	 * 处理砍价商品活动状态
	 */
	private void buildBargainGoodsState(List<BargainGoodsVO> bargainGoodsList) {
        LocalDateTime now = LocalDateTime.now();
		bargainGoodsList.forEach(bargainGoodsVO -> {
            // 活动进行中:大于或等于 活动开始时间开始  &&  小于或等于 活动开始时间截止
            if (now.isAfter(bargainGoodsVO.getBeginTime()) && now.isBefore(bargainGoodsVO.getEndTime())) {
                bargainGoodsVO.setBargainActivityState(BargainActivityState.ONGOING_ACTIVITY);
			} else if (now.isAfter(bargainGoodsVO.getEndTime())) {
				// 活动已结束：大于或等于 活动结束时间开始
				bargainGoodsVO.setBargainActivityState(BargainActivityState.TERMINATED_ACTIVITY);
			} else if (now.isBefore(bargainGoodsVO.getBeginTime())) {
				// 活动未开始:小于或等于 活动开始时间开始
				bargainGoodsVO.setBargainActivityState(BargainActivityState.WAIT_ACTIVITY);
			} else {
				log.error("商品：{}活动状态异常", bargainGoodsVO);
			}
			// 针对手动终止、活动库存为0
			if (bargainGoodsVO.getStoped()) {
				bargainGoodsVO.setBargainActivityState(BargainActivityState.TERMINATED_ACTIVITY);
			}
			// 针对原始商品下架、删除、禁售、不可售 => 活动已结束
			if (bargainGoodsVO.getGoodsInfoVO().getAddedFlag() == AddedFlag.NO.toValue()
					|| Constants.no.equals(bargainGoodsVO.getGoodsInfoVO().getVendibility())
					|| bargainGoodsVO.getGoodsInfoVO().getDelFlag() == DeleteFlag.YES
					|| bargainGoodsVO.getGoodsInfoVO().getAuditStatus() == CheckStatus.FORBADE
					|| DeleteFlag.NO == bargainGoodsVO.getGoodsStatus()) {
				bargainGoodsVO.setBargainActivityState(BargainActivityState.TERMINATED_ACTIVITY);
			}
		});
    }

	/**
	 * 处理砍价商品规格信息
	 */
    private void buildGoodsInfoSpecName(List<BargainGoodsVO> bargainGoodsList, List<GoodsInfoSpecDetailRelVO> specDetailRelVOList) {
		if (CollectionUtils.isNotEmpty(specDetailRelVOList)) {
			Map<String, List<GoodsInfoSpecDetailRelVO>> goodsInfoSpecDetailRelMap = specDetailRelVOList.stream().collect(Collectors.groupingBy(GoodsInfoSpecDetailRelVO::getGoodsInfoId));
			bargainGoodsList.forEach(bargainGoodsVO -> {
				List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelsTemp = goodsInfoSpecDetailRelMap.get(bargainGoodsVO.getGoodsInfoId());
				if (CollectionUtils.isNotEmpty(goodsInfoSpecDetailRelsTemp)) {
					bargainGoodsVO.setSpecText(StringUtils.join(goodsInfoSpecDetailRelsTemp
							.stream().map(GoodsInfoSpecDetailRelVO::getDetailName).collect(Collectors.toList()), " "));
				}
			});
		}
	}

	/**
	 * C端列表查询砍价商品
	 *
	 * @author
	 */
	public List<BargainGoods> list(BargainGoodsQueryRequest queryReq) {
		return bargainGoodsRepository.findAll(
				BargainGoodsWhereCriteriaBuilder.build(queryReq),
				queryReq.getSort());
	}

	/**
	 * 查询在活动范围内的砍价商品信息
	 *
	 * @author
	 */
	public List<String> findBargainGoodsInActivityTime(LocalDateTime begin, LocalDateTime end, List<String> goodsInfoIds) {
		return bargainGoodsRepository.findBargainGoodsInActivityTime(begin, end, goodsInfoIds);
	}

	/**
	 * 将实体包装成VO
	 *
	 * @author
	 */
	public BargainGoodsVO wrapperVo(BargainGoods bargainGoods) {
		if (bargainGoods != null) {
			BargainGoodsVO bargainGoodsVO = new BargainGoodsVO();
			KsBeanUtil.copyPropertiesThird(bargainGoods, bargainGoodsVO);
			return bargainGoodsVO;
		}
		return null;
	}

	/**
	 * C端分页查询砍价商品
	 */
	public MicroServicePage<BargainGoodsVO> pageForCustomer(BargainGoodsQueryRequest bargainGoodsPageReq) {
		MicroServicePage<BargainGoodsVO> microPage = new MicroServicePage<>();
		//1. 分页查询砍价活动商品
		Page<BargainGoods> bargainGoodsPage = page(bargainGoodsPageReq);
		if (CollectionUtils.isEmpty(bargainGoodsPage.getContent())) {
			microPage.setNumber(bargainGoodsPageReq.getPageNum());
			microPage.setSize(bargainGoodsPageReq.getPageSize());
			microPage.setTotal(0);
			return microPage;
		}

		//2.1 查询关联活动的商品信息
		List<String> goodsInfoIdList = bargainGoodsPage.getContent().stream().map(BargainGoods :: getGoodsInfoId).collect(Collectors.toList());
		List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.originalListByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(goodsInfoIdList).build()).getContext().getGoodsInfos();
		if (CollectionUtils.isEmpty(goodsInfos)) {
			microPage.setNumber(bargainGoodsPageReq.getPageNum());
			microPage.setSize(bargainGoodsPageReq.getPageSize());
			microPage.setTotal(0);
			return microPage;
		}
		Map<String, GoodsInfoVO> goodsInfoMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
		Page<BargainGoodsVO> newPage = bargainGoodsPage.map(entity -> wrapperVo(entity));
		microPage = new MicroServicePage<>(newPage, bargainGoodsPageReq.getPageable());
		List<BargainGoodsVO> bargainGoodsList = microPage.getContent();
		//2.2 根据商品的可售性处理活动状态
		for (BargainGoodsVO bargainGoods : bargainGoodsList) {
			GoodsInfoVO goodsInfoVO = goodsInfoMap.get(bargainGoods.getGoodsInfoId());
			if (Objects.nonNull(goodsInfoVO)) {
				bargainGoods.setGoodsInfoVO(goodsInfoVO);
				if (Objects.equals(Constants.no, goodsInfoVO.getVendibility(Boolean.TRUE))) {
					bargainGoods.setGoodsStatus(DeleteFlag.NO);
				}
			}
		}

		//3. 登录用户处理是否已经发起砍价
		if (StringUtils.isNotBlank(bargainGoodsPageReq.getUserId())) {
			List<Bargain> bargainList = bargainRepository.forCustomer(bargainGoodsPageReq.getUserId(), bargainGoodsList.stream().map(bargainGoods -> bargainGoods.getBargainGoodsId()).collect(Collectors.toList()));
			if (CollectionUtils.isNotEmpty(bargainList)) {
				for (BargainGoodsVO bargainGoods : bargainGoodsList) {
					Optional<Bargain> optional = bargainList.stream().filter(v -> v.getBargainGoodsId().equals(bargainGoods.getBargainGoodsId())).findFirst();
					if (optional.isPresent()) {
						bargainGoods.setBargainVO(JSON.parseObject(JSON.toJSONString(optional.get()), BargainVO.class));
					}
				}
			}
		}
		return microPage;
	}

	/**
	 * @description 砍价商品审核
	 * @author  lipeixian
	 * @date 2022/5/23 8:05 下午
	 * @param bargainCheckRequest
	 * @return void
	 **/
    @Transactional(rollbackFor = Exception.class)
	public void bargainGoodsCheck(BargainCheckRequest bargainCheckRequest) {
		if (bargainCheckRequest.getAuditStatus() != AuditStatus.NOT_PASS) {
			bargainCheckRequest.setReasonForRejection(null);
		}
		List<BargainGoods> bargainGoods = bargainGoodsRepository.findBargainGoodsByIds(bargainCheckRequest.getBargainGoodsIds());
		if (CollectionUtils.isEmpty(bargainGoods)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
		}
		// 校验是否存在已审核的活动
		bargainGoods.forEach(item -> {
			if (AuditStatus.WAIT_CHECK != item.getAuditStatus()) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080157);
			}
		});
		// 批量审核活动
		bargainGoodsRepository.checkStauts(bargainCheckRequest.getBargainGoodsIds(), bargainCheckRequest.getAuditStatus().ordinal(), bargainCheckRequest.getReasonForRejection());
	}

	/**
	 * @description 砍价活动终止
	 * @author  lipeixian
	 * @date 2022/5/23 8:42 下午
	 * @param request
	 * @return void
	 **/
    @Transactional(rollbackFor = Exception.class)
	public void terminalActivity(TerminalActivityRequest request) {
        // 验证砍价商品是否存在
        BargainGoods bargainGoods = bargainGoodsRepository.findByIdAndStoreId(request.getBargainGoodsId(), request.getStoreId());
		if (Objects.isNull(bargainGoods)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
		}
		// 校验审核状态，未审核通过无法关闭
		if (bargainGoods.getAuditStatus() != AuditStatus.CHECKED) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080153, new Object[] {"未审核通过"});
		}
		// 校验活动状态，已结束无法关闭
		if (LocalDateTime.now().isAfter(bargainGoods.getEndTime()) || bargainGoods.getStoped()) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080153, new Object[] {"已结束"});
		}
		// 校验活动状态，商家操作，未开始无法关闭（平台操作，则允许，通过判断storeId是否为null区分）
		if (Objects.nonNull(request.getStoreId()) && LocalDateTime.now().isBefore(bargainGoods.getBeginTime())) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080153, new Object[] {"未开始"});
		}
		// 关闭活动
		bargainGoodsRepository.terminalActivity(request.getBargainGoodsId());
	}

	/**
	 * @description 砍价活动删除
	 * @author  lipeixian
	 * @date 2022/5/23 8:42 下午
	 * @param request
	 * @return void
	 **/
    @Transactional(rollbackFor = Exception.class)
	public void deleteBargainGoods(TerminalActivityRequest request) {
		// 验证砍价商品是否存在
		BargainGoods bargainGoods = bargainGoodsRepository.findByIdAndStoreId(request.getBargainGoodsId(), request.getStoreId());
		if (Objects.isNull(bargainGoods)) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
		}

		// 仅审核不通过和通过后未开始的砍价活动可删除
		AuditStatus auditStatus = bargainGoods.getAuditStatus();
		boolean isNotStarted = LocalDateTime.now().isBefore(bargainGoods.getBeginTime()) || bargainGoods.getStoped();

		// 待审核状态，不可删除
		if (AuditStatus.WAIT_CHECK == auditStatus) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080152, new Object[] {"待审核"});
		}
		// 已审核通过，进行中或已结束（非未开始）不可删除
		if (AuditStatus.CHECKED == auditStatus && !isNotStarted) {
			throw new SbcRuntimeException(MarketingErrorCodeEnum.K080152, new Object[] {"已审核通过，且活动处于进行中或已结束"});
		}

		// 删除砍价商品
		bargainGoodsRepository.deleteBargainGoodsById(request.getBargainGoodsId());
	}

	/**
	 * @description   更新商品可售状态
	 * @author  wur
	 * @date: 2022/8/25 11:03
	 * @param request
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void updateGoodsStatus(@Valid UpdateGoodsStatusRequest request) {
		bargainGoodsRepository.updateGoodsStatus(request.getGoodsInfoIds(), request.getGoodsStatus().toValue());
		return;
	}

    @Transactional(rollbackFor = Exception.class)
    public void updateGoodsInfo(List<GoodsInfoVO> goodsInfoVOList) {
        goodsInfoVOList.forEach(
                goodsInfoVO -> {
                    DeleteFlag goodsStatus = DeleteFlag.NO;
                    if (Objects.equals(DeleteFlag.NO, goodsInfoVO.getDelFlag())
                            && Objects.equals(CheckStatus.CHECKED, goodsInfoVO.getAuditStatus())
                            && Objects.equals(AddedFlag.YES.toValue(), goodsInfoVO.getAddedFlag())
                            && (StringUtils.isEmpty(goodsInfoVO.getProviderGoodsInfoId())
                                    || Objects.equals(
                                            Constants.yes, goodsInfoVO.getVendibility(true)))) {
                        goodsStatus = DeleteFlag.YES;
                    }

                    bargainGoodsRepository.updateGoodsInfo(
                            goodsInfoVO.getGoodsInfoId(),
                            goodsInfoVO.getGoodsInfoName(),
                            goodsInfoVO.getGoodsInfoNo(),
                            goodsInfoVO.getCateId(),
                            goodsStatus.toValue());
                });
        return;
    }

	/**
	 * @description 店铺-砍价活动终止
	 * @author  lipeixian
	 * @date 2022/5/23 8:42 下午
	 * @param storeTerminalActivityRequest
	 * @return void
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void terminalActivity(StoreTerminalActivityRequest storeTerminalActivityRequest) {
		bargainGoodsRepository.storeTerminalActivity(storeTerminalActivityRequest.getStoreId());
	}

	/**
	 * @description 砍价商品系统自动驳回
	 * @author  lipeixian
	 * @date 2022/5/23 8:20 下午
	 * @param bargainCheckRequest
	 * @return void
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void bargainGoodsSystemCheck(BargainCheckRequest bargainCheckRequest) {
		bargainGoodsRepository.checkStauts(bargainCheckRequest.getBargainGoodsIds(), bargainCheckRequest.getAuditStatus().ordinal(), bargainCheckRequest.getReasonForRejection());
		return;
	}
}
