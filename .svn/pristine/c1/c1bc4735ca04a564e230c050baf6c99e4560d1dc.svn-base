package com.wanmi.sbc.goods.provider.impl.goodsaudit;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodsaudit.GoodsAuditProvider;
import com.wanmi.sbc.goods.api.request.goodsaudit.EditLevelPriceRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditDelByIdListRequest;
import com.wanmi.sbc.goods.api.request.goodsaudit.GoodsAuditModifyRequest;
import com.wanmi.sbc.goods.api.response.goodsaudit.GoodsAuditModifyResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsPropertyDetailRelSaveDTO;
import com.wanmi.sbc.goods.goodsaudit.request.GoodsAuditSaveRequest;
import com.wanmi.sbc.goods.goodsaudit.service.GoodsAuditService;
import com.wanmi.sbc.goods.provider.impl.goods.GoodsController;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>商品审核保存服务接口实现</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@RestController
@Validated
public class GoodsAuditController implements GoodsAuditProvider {

	@Resource
	private GoodsAuditService goodsAuditService;

	@Resource
	private GoodsController goodsController;

	@Override
	public BaseResponse<GoodsAuditModifyResponse> modify(@RequestBody @Valid GoodsAuditModifyRequest request) {
		GoodsAuditSaveRequest goodsAuditSaveRequest = KsBeanUtil.convert(request, GoodsAuditSaveRequest.class);
		assert goodsAuditSaveRequest != null;
		List<GoodsPropertyDetailRelDTO> goodsDetailRel = request.getGoodsDetailRel();
		// 处理商品与属性关联关系数据
		if (CollectionUtils.isNotEmpty(goodsDetailRel)){
			// 把属性值为空的数据去除掉
			List<GoodsPropertyDetailRelSaveDTO> propertyDetailRels = goodsController.getGoodsPropertyDetailList(goodsDetailRel);
			goodsAuditSaveRequest.setGoodsPropertyDetailRel(propertyDetailRels);
		}
		goodsAuditSaveRequest.getGoodsAudit().setAddedTimingTime(request.getGoodsAudit().getAddedTimingTime());

		GoodsAuditModifyResponse response = goodsAuditService.modify(goodsAuditSaveRequest);

		return BaseResponse.success(response);
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GoodsAuditDelByIdListRequest request) {
		List<String> goodsIdList = request.getGoodsIdList();
		if (Objects.equals(Constants.ONE,request.getGoodsIdType())){
			List<String> goodsIds = goodsAuditService.getByOldGoodsIds(goodsIdList);
			if (CollectionUtils.isNotEmpty(goodsIds)){
				request.setGoodsIdList(goodsIds);
			}
		}
		if (CollectionUtils.isNotEmpty(request.getGoodsIdList())) {
			goodsAuditService.deleteByIdList(request.getGoodsIdList());
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse editLevelPrice(EditLevelPriceRequest request) {
		goodsAuditService.editLevelPrice(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse editStockPrice(EditLevelPriceRequest request) {
		goodsAuditService.editStockPrice(request);
		return BaseResponse.SUCCESSFUL();
	}

}

