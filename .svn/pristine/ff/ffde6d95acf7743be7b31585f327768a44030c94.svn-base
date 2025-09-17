package com.wanmi.sbc.marketing.provider.impl.bargaingoods;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsQueryRequest;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsValidateRequest;
import com.wanmi.sbc.marketing.bargain.model.root.Bargain;
import com.wanmi.sbc.marketing.bargain.repository.BargainRepository;
import com.wanmi.sbc.marketing.bargaingoods.model.root.BargainGoods;
import com.wanmi.sbc.marketing.bargaingoods.service.BargainGoodsService;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>砍价商品查询服务接口实现</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@RestController
@Validated
public class BargainGoodsQueryController implements BargainGoodsQueryProvider {
	@Autowired
	private BargainGoodsService bargainGoodsService;

	@Autowired
	private BargainRepository bargainRepository;

	@Autowired
	private GoodsInfoQueryProvider goodsInfoQueryProvider;

	/**
	 * 分页查询砍价商品API
	 *
	 * @param bargainGoodsPageReq 分页请求参数和筛选对象 {@link BargainGoodsQueryRequest}
	 * @author
	 */
	@Override
	public BaseResponse<MicroServicePage<BargainGoodsVO>> page(@RequestBody @Valid BargainGoodsQueryRequest bargainGoodsPageReq) {
		Page<BargainGoods> bargainGoodsPage = bargainGoodsService.page(bargainGoodsPageReq);
		Page<BargainGoodsVO> newPage = bargainGoodsPage.map(entity -> bargainGoodsService.wrapperVo(entity));
		MicroServicePage<BargainGoodsVO> microPage = new MicroServicePage<>(newPage, bargainGoodsPageReq.getPageable());
		return BaseResponse.success(microPage);
	}

	/**
	 * 分页查询砍价商品API
	 *
	 * @param bargainGoodsPageReq 分页请求参数和筛选对象 {@link BargainGoodsQueryRequest}
	 * @author
	 */
	@Override
	public BaseResponse<MicroServicePage<BargainGoodsVO>> pageNew(@RequestBody @Valid BargainGoodsQueryRequest bargainGoodsPageReq) {
		// 0. 自动审核 已过期 && 待审核 的砍价商品（商家自动审核自己的，平台自动审核所有的）
		// 审核结果是：驳回，原因：'活动已结束，审核失败'
		bargainGoodsService.autoBatchAuditForOverTime(bargainGoodsPageReq.getStoreId());
		return BaseResponse.success(bargainGoodsService.pageNew(bargainGoodsPageReq));
	}

	/**
	 * 列表查询砍价商品API
	 *
	 * @param bargainGoodsListReq 列表请求参数和筛选对象 {@link BargainGoodsQueryRequest}
	 * @author
	 */
	@Override
	public BaseResponse<List<BargainGoodsVO>> listForCustomer(@RequestBody BargainGoodsQueryRequest bargainGoodsListReq) {
		//1. 查询砍价活动商品
		List<BargainGoods> bargainGoodsList = bargainGoodsService.list(bargainGoodsListReq);
		if (CollectionUtils.isEmpty(bargainGoodsList)) {
			return BaseResponse.success(Collections.EMPTY_LIST);
		}
		List<BargainGoodsVO> newList = bargainGoodsList.stream().map(entity -> bargainGoodsService.wrapperVo(entity)).collect(Collectors.toList());

		//2.1 查询关联的商品
		List<String> goodsInfoIdList = bargainGoodsList.stream().map(BargainGoods::getGoodsInfoId).collect(Collectors.toList());
		List<GoodsInfoVO> goodsInfos = goodsInfoQueryProvider.originalListByIds(GoodsInfoListByIdsRequest.builder().goodsInfoIds(goodsInfoIdList).build()).getContext().getGoodsInfos();
		if (CollectionUtils.isEmpty(goodsInfos)) {
			return BaseResponse.success(Collections.EMPTY_LIST);
		}
		Map<String, GoodsInfoVO> goodsInfoMap = goodsInfos.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
		//2.2 根据商品的可售性处理活动状态
		for (BargainGoodsVO bargainGoods : newList) {
			GoodsInfoVO goodsInfoVO = goodsInfoMap.get(bargainGoods.getGoodsInfoId());
			if (Objects.nonNull(goodsInfoVO)) {
				bargainGoods.setGoodsInfoVO(goodsInfoVO);
				if (Objects.equals(Constants.no, goodsInfoVO.getVendibility(Boolean.TRUE))) {
					bargainGoods.setGoodsStatus(DeleteFlag.NO);
				}
			}
		}

		//3. 登录用户封装用户已发起砍价数据
		if (CollectionUtils.isNotEmpty(newList) && StringUtils.isNotBlank(bargainGoodsListReq.getUserId())) {
			List<Bargain> bargainList = bargainRepository.forCustomer(bargainGoodsListReq.getUserId(), newList.stream().map(bargainGoods -> bargainGoods.getBargainGoodsId()).collect(Collectors.toList()));
			if (CollectionUtils.isNotEmpty(bargainList)) {
				for (BargainGoodsVO bargainGoods : newList) {
					Optional<Bargain> optional = bargainList.stream().filter(v -> v.getBargainGoodsId().equals(bargainGoods.getBargainGoodsId())).findFirst();
					if (optional.isPresent()) {
						bargainGoods.setBargainVO(JSON.parseObject(JSON.toJSONString(optional.get()), BargainVO.class));
					}
				}
			}
		}
		return BaseResponse.success(newList);
	}

	@Override
	public BaseResponse<MicroServicePage<BargainGoodsVO>> pageForCustomer(@RequestBody @Valid BargainGoodsQueryRequest bargainGoodsPageReq) {
		MicroServicePage<BargainGoodsVO> page = bargainGoodsService.pageForCustomer(bargainGoodsPageReq);
		return BaseResponse.success(page);
	}

	/**
	 * 单个查询砍价商品API
	 * @author
	 */
	@Override
	public BaseResponse<BargainGoodsVO> getById(@RequestBody @Valid BargainGoodsQueryRequest request) {
		BargainGoodsVO bargainGoodsVO = bargainGoodsService.getById(request);
		return BaseResponse.success(bargainGoodsVO);
	}

	/**
	 * 互斥验证
	 *
	 * @author dyt
	 * @param request 互斥请求参数 {@link BargainGoodsValidateRequest}
	 */
	@Override
	public BaseResponse validate(@RequestBody @Valid BargainGoodsValidateRequest request) {
		bargainGoodsService.validate(request);
		return BaseResponse.SUCCESSFUL();
	}
}

