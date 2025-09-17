package com.wanmi.sbc.goods.provider.impl.pointsgoodscate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.pointsgoodscate.PointsGoodsCateSaveProvider;
import com.wanmi.sbc.goods.api.request.pointsgoods.PointsGoodsQueryRequest;
import com.wanmi.sbc.goods.api.request.pointsgoodscate.PointsGoodsCateAddRequest;
import com.wanmi.sbc.goods.api.request.pointsgoodscate.PointsGoodsCateDelByIdRequest;
import com.wanmi.sbc.goods.api.request.pointsgoodscate.PointsGoodsCateModifyRequest;
import com.wanmi.sbc.goods.api.request.pointsgoodscate.PointsGoodsCateSortRequest;
import com.wanmi.sbc.goods.api.response.pointsgoodscate.PointsGoodsCateAddResponse;
import com.wanmi.sbc.goods.api.response.pointsgoodscate.PointsGoodsCateModifyResponse;
import com.wanmi.sbc.goods.pointsgoods.model.root.PointsGoods;
import com.wanmi.sbc.goods.pointsgoods.service.PointsGoodsService;
import com.wanmi.sbc.goods.pointsgoodscate.model.root.PointsGoodsCate;
import com.wanmi.sbc.goods.pointsgoodscate.service.PointsGoodsCateService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>积分商品分类表保存服务接口实现</p>
 * @author yang
 * @date 2019-05-13 09:50:07
 */
@RestController
@Validated
public class PointsGoodsCateSaveController implements PointsGoodsCateSaveProvider {
	@Autowired
	private PointsGoodsCateService pointsGoodsCateService;

	@Autowired
	private PointsGoodsService pointsGoodsService;

	@Override
	public BaseResponse<PointsGoodsCateAddResponse> add(@RequestBody @Valid PointsGoodsCateAddRequest pointsGoodsCateAddRequest) {
		PointsGoodsCate pointsGoodsCate = new PointsGoodsCate();
		KsBeanUtil.copyPropertiesThird(pointsGoodsCateAddRequest, pointsGoodsCate);
		return BaseResponse.success(new PointsGoodsCateAddResponse(
				pointsGoodsCateService.wrapperVo(pointsGoodsCateService.add(pointsGoodsCate))));
	}

	@Override
	public BaseResponse<PointsGoodsCateModifyResponse> modify(@RequestBody @Valid PointsGoodsCateModifyRequest pointsGoodsCateModifyRequest) {
		PointsGoodsCate pointsGoodsCate = new PointsGoodsCate();
		KsBeanUtil.copyPropertiesThird(pointsGoodsCateModifyRequest, pointsGoodsCate);
		return BaseResponse.success(new PointsGoodsCateModifyResponse(
				pointsGoodsCateService.wrapperVo(pointsGoodsCateService.modify(pointsGoodsCate))));
	}

    @Override
	@Transactional(rollbackFor = {Exception.class})
    public BaseResponse<List<String>> deleteById(@RequestBody @Valid PointsGoodsCateDelByIdRequest pointsGoodsCateDelByIdRequest) {
		List<PointsGoods> list = pointsGoodsService.list(PointsGoodsQueryRequest.builder()
				.cateId(pointsGoodsCateDelByIdRequest.getCateId()).delFlag(DeleteFlag.NO).build());
		List<String> idList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			pointsGoodsService.updateCateId(pointsGoodsCateDelByIdRequest.getCateId());
            idList = list.stream().map(PointsGoods::getPointsGoodsId).collect(Collectors.toList());
        }

        pointsGoodsCateService.deleteById(pointsGoodsCateDelByIdRequest.getCateId());
        return BaseResponse.success(idList);
    }

	@Override
	public BaseResponse editSort(@RequestBody PointsGoodsCateSortRequest queryRequest) {
		pointsGoodsCateService.editSort(queryRequest);
		return BaseResponse.SUCCESSFUL();
	}
}

