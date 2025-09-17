package com.wanmi.sbc.customer.provider.impl.payingmemberlevel;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.levelrights.CustomerLevelRightsQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberCustomerRelQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberdiscountrel.PayingMemberDiscountRelQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberlevel.*;
import com.wanmi.sbc.customer.api.request.payingmemberprice.PayingMemberPriceQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberrecommendrel.PayingMemberRecommendRelQueryRequest;
import com.wanmi.sbc.customer.api.request.payingmemberstorerel.PayingMemberStoreRelQueryRequest;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.*;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.customer.levelrights.model.root.CustomerLevelRights;
import com.wanmi.sbc.customer.levelrights.service.CustomerLevelRightsService;
import com.wanmi.sbc.customer.payingmembercustomerrel.service.PayingMemberCustomerRelService;
import com.wanmi.sbc.customer.payingmemberdiscountrel.model.root.PayingMemberDiscountRel;
import com.wanmi.sbc.customer.payingmemberdiscountrel.service.PayingMemberDiscountRelService;
import com.wanmi.sbc.customer.payingmemberlevel.model.root.PayingMemberLevel;
import com.wanmi.sbc.customer.payingmemberlevel.service.PayingMemberLevelService;
import com.wanmi.sbc.customer.payingmemberprice.service.PayingMemberPriceService;
import com.wanmi.sbc.customer.payingmemberrecommendrel.model.root.PayingMemberRecommendRel;
import com.wanmi.sbc.customer.payingmemberrecommendrel.service.PayingMemberRecommendRelService;
import com.wanmi.sbc.customer.payingmemberstorerel.model.root.PayingMemberStoreRel;
import com.wanmi.sbc.customer.payingmemberstorerel.service.PayingMemberStoreRelService;
import com.wanmi.sbc.customer.store.model.root.Store;
import com.wanmi.sbc.customer.store.service.StoreService;

import jakarta.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>付费会员等级表查询服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@RestController
@Validated
public class PayingMemberLevelQueryController implements PayingMemberLevelQueryProvider {

	@Autowired
	private PayingMemberLevelService payingMemberLevelService;

	@Autowired
	private PayingMemberPriceService payingMemberPriceService;

	@Autowired
	private PayingMemberStoreRelService payingMemberStoreRelService;

	@Autowired
	private PayingMemberDiscountRelService payingMemberDiscountRelService;

	@Autowired
	private PayingMemberRecommendRelService payingMemberRecommendRelService;

	@Autowired
	private CustomerLevelRightsService customerLevelRightsService;

	@Autowired
	private PayingMemberCustomerRelService payingMemberCustomerRelService;

	@Autowired
	private StoreService storeService;

	@Override
	public BaseResponse<PayingMemberLevelPageResponse> page(@RequestBody @Valid PayingMemberLevelPageRequest payingMemberLevelPageReq) {
		PayingMemberLevelQueryRequest queryReq = KsBeanUtil.convert(payingMemberLevelPageReq, PayingMemberLevelQueryRequest.class);
		Page<PayingMemberLevel> payingMemberLevelPage = payingMemberLevelService.page(queryReq);
		Page<PayingMemberLevelVO> newPage = payingMemberLevelPage.map(entity -> {
			PayingMemberLevelVO payingMemberLevelVO = payingMemberLevelService.wrapperVo(entity);
			//查询付费设置
			List<PayingMemberPriceVO> list = payingMemberPriceService.list(PayingMemberPriceQueryRequest.builder()
					.levelId(entity.getLevelId())
					.delFlag(DeleteFlag.NO)
					.build());
			StringBuilder stringBuilder = new StringBuilder();
			List<PayingMemberRightsRelVO> payingMemberRightsRelVOS = Lists.newArrayList();
			list.forEach(payingMemberPriceVO -> {
				stringBuilder.append(payingMemberPriceVO.getPriceNum());
				stringBuilder.append("个月");
				stringBuilder.append("¥");
				stringBuilder.append(payingMemberPriceVO.getPriceTotal());
				stringBuilder.append(",");
				payingMemberRightsRelVOS.addAll(payingMemberPriceVO.getPayingMemberRightsRelVOS());
			});
			String priceDesc = stringBuilder.toString();
			payingMemberLevelVO.setPriceDesc(priceDesc.substring(0, priceDesc.length() - 1));
			List<Integer> rightsIds = payingMemberRightsRelVOS.parallelStream().map(PayingMemberRightsRelVO::getRightsId)
					.distinct().collect(Collectors.toList());
			List<CustomerLevelRights> customerLevelRightsList = customerLevelRightsService.list(CustomerLevelRightsQueryRequest.builder()
					.rightsIdList(rightsIds)
					.delFlag(DeleteFlag.NO)
					.build());
			//清空
			stringBuilder.delete(0,stringBuilder.length());
			customerLevelRightsList.forEach(customerLevelRights -> {
				stringBuilder.append(customerLevelRights.getRightsName());
				stringBuilder.append(",");
			});
			String rightsDesc = stringBuilder.toString();
			payingMemberLevelVO.setRightsDesc(rightsDesc.substring(0, rightsDesc.length() - 1));
			Long count = payingMemberCustomerRelService.count(PayingMemberCustomerRelQueryRequest.builder()
					.expirationDateBegin(LocalDate.now())
					.levelId(entity.getLevelId())
					.delFlag(DeleteFlag.NO)
					.build());
			payingMemberLevelVO.setPayingMemberCount(count);
			return payingMemberLevelVO;
		});
		MicroServicePage<PayingMemberLevelVO> microPage = new MicroServicePage<>(newPage, payingMemberLevelPageReq.getPageable());
		PayingMemberLevelPageResponse finalRes = new PayingMemberLevelPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<PayingMemberLevelListResponse> list(@RequestBody @Valid PayingMemberLevelListRequest payingMemberLevelListReq) {
		PayingMemberLevelQueryRequest queryReq = KsBeanUtil.convert(payingMemberLevelListReq, PayingMemberLevelQueryRequest.class);
		List<PayingMemberLevel> payingMemberLevelList = payingMemberLevelService.list(queryReq);
		List<PayingMemberLevelVO> newList = payingMemberLevelList.stream().map(entity -> payingMemberLevelService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayingMemberLevelListResponse(newList));
	}

	@Override
	public BaseResponse<PayingMemberLevelByIdResponse> getById(@RequestBody @Valid PayingMemberLevelByIdRequest payingMemberLevelByIdRequest) {
		//是否是客户查询
		boolean isCustomer = payingMemberLevelByIdRequest.isCustomer();
		PayingMemberLevel payingMemberLevel =
				payingMemberLevelService.getOne(payingMemberLevelByIdRequest.getLevelId());
		PayingMemberLevelVO payingMemberLevelVO = payingMemberLevelService.wrapperVo(payingMemberLevel);
		//查询付费设置
		List<PayingMemberPriceVO> list = payingMemberPriceService.list(PayingMemberPriceQueryRequest.builder()
				.levelId(payingMemberLevel.getLevelId())
				.delFlag(DeleteFlag.NO)
				.build());
		payingMemberLevelVO.setPayingMemberPriceVOS(list);
		//付费会员等级商家范围：1.自定义选择 查询商家与付费会员等级关联
		if (!isCustomer && payingMemberLevel.getLevelStoreRange() == Constants.ONE) {
			List<PayingMemberStoreRel> payingMemberStoreRelList = payingMemberStoreRelService.list(PayingMemberStoreRelQueryRequest.builder()
					.levelId(payingMemberLevel.getLevelId())
					.delFlag(DeleteFlag.NO)
					.build());
			List<Long> storeIds= payingMemberStoreRelList.parallelStream().map(PayingMemberStoreRel::getStoreId).collect(Collectors.toList());
			List<Store> storeList = storeService.findAllList(storeIds);
			List<PayingMemberStoreRelVO> payingMemberStoreRelVOS = KsBeanUtil.convert(payingMemberStoreRelList, PayingMemberStoreRelVO.class);
			payingMemberLevelVO.setPayingMemberStoreRelVOS(payingMemberStoreRelVOS.parallelStream().peek(payingMemberStoreRelVO -> {
				Optional<Store> optional = storeList.parallelStream()
						.filter(store -> store.getStoreId().equals(payingMemberStoreRelVO.getStoreId())).findFirst();
				if (optional.isPresent()) {
					Store store = optional.get();
					payingMemberStoreRelVO.setStoreName(store.getStoreName());
					payingMemberStoreRelVO.setSupplierName(store.getSupplierName());
					payingMemberStoreRelVO.setCompanyType(store.getCompanyType());
				}
			}).collect(Collectors.toList()));
		}
		// 付费会员等级折扣类型 1.自定义商品设置
		if (payingMemberLevel.getLevelDiscountType() == Constants.ONE) {
			//查询折扣商品关联
			List<PayingMemberDiscountRel> payingMemberDiscountRelList = payingMemberDiscountRelService.list(PayingMemberDiscountRelQueryRequest.builder()
					.levelId(payingMemberLevel.getLevelId())
					.delFlag(DeleteFlag.NO)
					.build());
			payingMemberLevelVO.setPayingMemberDiscountRelVOS(KsBeanUtil.convert(payingMemberDiscountRelList, PayingMemberDiscountRelVO.class));
		}
		//查询推荐商品关联
		List<PayingMemberRecommendRel> payingMemberRecommendRelList = payingMemberRecommendRelService.list(PayingMemberRecommendRelQueryRequest.builder()
				.levelId(payingMemberLevel.getLevelId())
				.delFlag(DeleteFlag.NO)
				.build());
		payingMemberLevelVO.setPayingMemberRecommendRelVOS(KsBeanUtil.convert(payingMemberRecommendRelList,PayingMemberRecommendRelVO.class));
		return BaseResponse.success(new PayingMemberLevelByIdResponse(payingMemberLevelVO));
	}

	@Override
	public BaseResponse<Long> countForExport(@Valid PayingMemberLevelExportRequest request) {
		PayingMemberLevelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberLevelQueryRequest.class);
		Long total = payingMemberLevelService.count(queryReq);
		return BaseResponse.success(total);
	}

	@Override
	public BaseResponse<PayingMemberLevelExportResponse> exportPayingMemberLevelRecord(@RequestBody @Valid PayingMemberLevelExportRequest request) {
		PayingMemberLevelQueryRequest queryReq = KsBeanUtil.convert(request, PayingMemberLevelQueryRequest.class);
		List<PayingMemberLevelPageVO> data = KsBeanUtil.convert(payingMemberLevelService.page(queryReq).getContent(),PayingMemberLevelPageVO.class);
		return BaseResponse.success(new PayingMemberLevelExportResponse(data));
	}

	@Override
	public BaseResponse<Long> countLevels(@RequestBody @Valid PayingMemberLevelQueryRequest payingMemberLevelQueryRequest) {
		Long total = payingMemberLevelService.count(payingMemberLevelQueryRequest);
		return BaseResponse.success(total);
	}

	@Cacheable(value = CacheConstants.GLOBAL_CACHE_NAME, key = "'PayingMemberLevelList:'+#payingMemberLevelCustomerRequest.getCustomerId()")
	@Override
	public BaseResponse<PayingMemberLevelListResponse> listByCustomerId(@RequestBody @Valid PayingMemberLevelCustomerRequest payingMemberLevelCustomerRequest) {
		Boolean defaultFlag = ObjectUtils.defaultIfNull(payingMemberLevelCustomerRequest.getDefaultFlag(), Boolean.FALSE);
		List<PayingMemberLevelVO> levelList = payingMemberLevelService.listByCustomerId(payingMemberLevelCustomerRequest.getCustomerId(), defaultFlag);
		levelList.forEach(level -> {
			//付费会员等级商家范围：1.自定义选择 查询商家与付费会员等级关联
			if (NumberUtils.INTEGER_ONE.equals(level.getLevelStoreRange())) {
				List<PayingMemberStoreRel> payingMemberStoreRelList = payingMemberStoreRelService.list(PayingMemberStoreRelQueryRequest.builder()
						.levelId(level.getLevelId())
						.delFlag(DeleteFlag.NO)
						.build());
				level.setPayingMemberStoreRelVOS(KsBeanUtil.convert(payingMemberStoreRelList, PayingMemberStoreRelVO.class));
			}
			// 付费会员等级折扣类型 1.自定义商品设置
			if (NumberUtils.INTEGER_ONE.equals(level.getLevelDiscountType())) {
				//查询折扣商品关联
				List<PayingMemberDiscountRel> payingMemberDiscountRelList = payingMemberDiscountRelService.list(PayingMemberDiscountRelQueryRequest.builder()
						.levelId(level.getLevelId())
						.delFlag(DeleteFlag.NO)
						.build());
				level.setPayingMemberDiscountRelVOS(KsBeanUtil.convert(payingMemberDiscountRelList, PayingMemberDiscountRelVO.class));
			}
		});
		return BaseResponse.success(new PayingMemberLevelListResponse(levelList));
	}

	@Override
	public BaseResponse<PayingMemberLevelListResponse> listForSku(@RequestBody @Valid PayingMemberLevelListRequest payingMemberLevelListReq) {
		PayingMemberLevelQueryRequest queryReq = KsBeanUtil.convert(payingMemberLevelListReq, PayingMemberLevelQueryRequest.class);
		List<PayingMemberLevel> payingMemberLevelList = payingMemberLevelService.list(queryReq);
		List<PayingMemberLevelVO> newList = payingMemberLevelList.stream().map(entity -> payingMemberLevelService.wrapperVo(entity)).collect(Collectors.toList());
		newList.forEach(level -> {
			//查询付费设置
			List<PayingMemberPriceVO> list = payingMemberPriceService.list(PayingMemberPriceQueryRequest.builder()
					.levelId(level.getLevelId())
					.delFlag(DeleteFlag.NO)
					.build());
			level.setPayingMemberPriceVOS(list);
			//付费会员等级商家范围：1.自定义选择 查询商家与付费会员等级关联
			if (level.getLevelStoreRange() == Constants.ONE) {
				List<PayingMemberStoreRel> payingMemberStoreRelList = payingMemberStoreRelService.list(PayingMemberStoreRelQueryRequest.builder()
						.levelId(level.getLevelId())
						.delFlag(DeleteFlag.NO)
						.build());
				level.setPayingMemberStoreRelVOS(KsBeanUtil.convert(payingMemberStoreRelList, PayingMemberStoreRelVO.class));
			}
			// 付费会员等级折扣类型 1.自定义商品设置
			if (level.getLevelDiscountType() == Constants.ONE) {
				//查询折扣商品关联
				List<PayingMemberDiscountRel> payingMemberDiscountRelList = payingMemberDiscountRelService.list(PayingMemberDiscountRelQueryRequest.builder()
						.levelId(level.getLevelId())
						.delFlag(DeleteFlag.NO)
						.build());
				level.setPayingMemberDiscountRelVOS(KsBeanUtil.convert(payingMemberDiscountRelList, PayingMemberDiscountRelVO.class));
			}
		});
		return BaseResponse.success(new PayingMemberLevelListResponse(newList));
	}


	@Override
	public BaseResponse<PayingMemberLevelListNewResponse> listAllPayingMemberLevelNew() {
		List<PayingMemberLevel> payingMemberLevels = payingMemberLevelService.list(PayingMemberLevelQueryRequest.builder().delFlag(DeleteFlag.NO).build());
		List<PayingMemberLevelBaseVO> payingMemberLevelVOList = payingMemberLevels.stream().map(payingMemberLevel -> {
			PayingMemberLevelBaseVO vo = new PayingMemberLevelBaseVO();
			vo.setLevelId(payingMemberLevel.getLevelId());
			vo.setPayingMemberLevelName(payingMemberLevel.getLevelName());
			return vo;
		}).collect(Collectors.toList());
		return BaseResponse.success(PayingMemberLevelListNewResponse.builder().payingMemberLevelVOList(payingMemberLevelVOList).build());
	}
}

