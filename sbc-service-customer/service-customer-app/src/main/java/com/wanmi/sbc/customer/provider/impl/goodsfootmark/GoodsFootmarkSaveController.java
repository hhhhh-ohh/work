package com.wanmi.sbc.customer.provider.impl.goodsfootmark;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.goodsfootmark.GoodsFootmarkSaveProvider;
import com.wanmi.sbc.customer.api.request.goodsfootmark.*;
import com.wanmi.sbc.customer.goodsfootmark.model.root.GoodsFootmark;
import com.wanmi.sbc.customer.goodsfootmark.service.GoodsFootmarkService;
import com.wanmi.sbc.customer.bean.vo.GoodsFootmarkVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>我的足迹保存服务接口实现</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@RestController
@Validated
public class GoodsFootmarkSaveController implements GoodsFootmarkSaveProvider {

	@Autowired private GoodsFootmarkService goodsFootmarkService;

	/**
	 * 新增我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkAddRequest 我的足迹新增参数结构 {@link GoodsFootmarkAddRequest}
	 * @return 新增的我的足迹信息 {@link GoodsFootmarkVO}
	 */
	@Override
	public BaseResponse add(@RequestBody @Valid GoodsFootmarkAddRequest goodsFootmarkAddRequest) {
		if(StringUtils.isEmpty(goodsFootmarkAddRequest.getGoodsInfoId()) || "undefined".equalsIgnoreCase(goodsFootmarkAddRequest.getGoodsInfoId())
				|| "null".equalsIgnoreCase(goodsFootmarkAddRequest.getGoodsInfoId())){
//			throw new SbcRuntimeException("K-000918");
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
		}
		goodsFootmarkService.add(goodsFootmarkAddRequest);
		return BaseResponse.SUCCESSFUL();
	}
	/**
	 * 修改我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkModifyRequest 我的足迹修改参数结构 {@link GoodsFootmarkModifyRequest}
	 * @return 修改的我的足迹信息 {@link GoodsFootmarkVO}
	 */
	@Override
	public BaseResponse<GoodsFootmarkVO> modify(@RequestBody @Valid GoodsFootmarkModifyRequest goodsFootmarkModifyRequest) {
		GoodsFootmark goodsFootmark = new GoodsFootmark();
		KsBeanUtil.copyPropertiesThird(goodsFootmarkModifyRequest, goodsFootmark);
		return BaseResponse.success(goodsFootmarkService.wrapperVo(goodsFootmarkService.modify(goodsFootmark)));
	}
	/**
	 * 单个删除我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkDelByIdRequest 单个删除参数结构 {@link GoodsFootmarkDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@Override
	public BaseResponse deleteById(@RequestBody @Valid GoodsFootmarkDelByIdRequest goodsFootmarkDelByIdRequest) {
		goodsFootmarkService.deleteById(goodsFootmarkDelByIdRequest.getFootmarkId(), goodsFootmarkDelByIdRequest.getCustomerId());
		return BaseResponse.SUCCESSFUL();
	}
	/**
	 * 批量删除我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkDelByIdListRequest 批量删除参数结构 {@link GoodsFootmarkDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GoodsFootmarkDelByIdListRequest goodsFootmarkDelByIdListRequest) {
		goodsFootmarkService.deleteByIdList(goodsFootmarkDelByIdListRequest.getFootmarkIdList(), goodsFootmarkDelByIdListRequest.getCustomerId());
		return BaseResponse.SUCCESSFUL();
	}

    @Override
    public BaseResponse deleteByUpdateTime(@RequestBody @Valid GoodsFootmarkDelByTimeRequest goodsFootmarkDelByTimeRequest) {
		goodsFootmarkService.deleteByUpdateTime(goodsFootmarkDelByTimeRequest);
		return BaseResponse.SUCCESSFUL();
    }

}

