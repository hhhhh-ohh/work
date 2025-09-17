package com.wanmi.sbc.goods.provider.impl.goodstemplate;

import com.wanmi.sbc.goods.api.request.goodstemplate.*;
import com.wanmi.sbc.goods.goodstemplate.service.GoodsTemplateRelService;
import com.wanmi.sbc.goods.info.model.root.Goods;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.goodstemplate.GoodsTemplateProvider;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateAddResponse;
import com.wanmi.sbc.goods.api.response.goodstemplate.GoodsTemplateModifyResponse;
import com.wanmi.sbc.goods.goodstemplate.service.GoodsTemplateService;
import com.wanmi.sbc.goods.goodstemplate.model.root.GoodsTemplate;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>GoodsTemplate保存服务接口实现</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@RestController
@Validated
public class GoodsTemplateController implements GoodsTemplateProvider {
	@Autowired
	private GoodsTemplateService goodsTemplateService;

	@Autowired
	private GoodsTemplateRelService goodsTemplateRelService;

	@Override
	public BaseResponse<GoodsTemplateAddResponse> add(@RequestBody @Valid GoodsTemplateAddRequest goodsTemplateAddRequest) {
		return BaseResponse.success(new GoodsTemplateAddResponse(
				goodsTemplateService.wrapperVo(goodsTemplateService.add(goodsTemplateAddRequest))));
	}

	@Override
	public BaseResponse<GoodsTemplateModifyResponse> modify(@RequestBody @Valid GoodsTemplateModifyRequest goodsTemplateModifyRequest) {
		return BaseResponse.success(new GoodsTemplateModifyResponse(
				goodsTemplateService.wrapperVo(goodsTemplateService.modify(goodsTemplateModifyRequest))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid GoodsTemplateDelByIdRequest goodsTemplateDelByIdRequest) {
		goodsTemplateService.deleteById(goodsTemplateDelByIdRequest);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GoodsTemplateDelByIdListRequest goodsTemplateDelByIdListRequest) {
		goodsTemplateService.deleteByIdList(goodsTemplateDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse joinGoods(GoodsTemplateJoinRequest request) {
		goodsTemplateRelService.joinGoods(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByGoodsId(GoodsTemplateByGoodsIdRequest request) {
		goodsTemplateRelService.deleteByGoodsId(request.getGoodsId());
		return BaseResponse.SUCCESSFUL();
	}

}

