package com.wanmi.sbc.goods.provider.impl.xsitegoodscate;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.xsitegoodscate.XsiteGoodsCateProvider;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateAddRequest;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateDeleteRequest;
import com.wanmi.sbc.goods.api.request.xsitegoodscate.XsiteGoodsCateQueryByCateUuidRequest;
import com.wanmi.sbc.goods.api.response.xsitegoodscate.XsiteGoodsCateByCateUuidResponse;
import com.wanmi.sbc.goods.bean.vo.XsiteGoodsCateVO;
import com.wanmi.sbc.goods.xsitegoodscate.model.root.XsiteGoodsCate;
import com.wanmi.sbc.goods.xsitegoodscate.service.XsiteGoodsCateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>魔方商品分类表保存服务接口实现</p>
 * @author xufeng
 * @date 2022-02-21 14:54:31
 */
@RestController
@Validated
public class XsiteGoodsCateController implements XsiteGoodsCateProvider {
	@Autowired
	private XsiteGoodsCateService xsiteGoodsCateService;

	@Override
	public BaseResponse add(@RequestBody @Valid XsiteGoodsCateAddRequest xsiteGoodsCateAddRequest) {
		// 若是数据存在则先删除
		if (xsiteGoodsCateService.findCountByPageCode(xsiteGoodsCateAddRequest.getPageCode()) > 0) {
			xsiteGoodsCateService.delAllByPageCode(xsiteGoodsCateAddRequest.getPageCode());
		}
		List<XsiteGoodsCate> entitys = Lists.newArrayList();
		List<XsiteGoodsCateVO> xsiteGoodsCateVOS = xsiteGoodsCateAddRequest.getXsiteGoodsCateVOS();
		xsiteGoodsCateVOS.forEach(xsiteGoodsCateVO -> {
			List<String> goodsIds = Lists.newArrayList();
			List<String> goodsInfoIds = Lists.newArrayList();
			xsiteGoodsCateVO.getGoodsInfoVOS().forEach(goodsInfoVO -> {
				goodsIds.add(goodsInfoVO.getGoodsId());
				goodsInfoIds.add(goodsInfoVO.getGoodsInfoId());
			});
			XsiteGoodsCate entity = new XsiteGoodsCate();
			entity.setPageCode(xsiteGoodsCateAddRequest.getPageCode());
			entity.setCateUuid(xsiteGoodsCateVO.getCateUuid());
			entity.setGoodsIds(JSON.toJSONString(goodsIds));
			entity.setGoodsInfoIds(JSON.toJSONString(goodsInfoIds));
			entitys.add(entity);
		});
		xsiteGoodsCateService.saveAll(entitys);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse delete(@RequestBody @Valid XsiteGoodsCateDeleteRequest xsiteGoodsCateDeleteRequest) {
		// 若是数据存在则删除
		if (xsiteGoodsCateService.findCountByPageCode(xsiteGoodsCateDeleteRequest.getPageCode()) > 0) {
			xsiteGoodsCateService.delAllByPageCode(xsiteGoodsCateDeleteRequest.getPageCode());
		} else {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<XsiteGoodsCateByCateUuidResponse> findByCateUuid(@RequestBody @Valid XsiteGoodsCateQueryByCateUuidRequest xsiteGoodsCateQueryByCateUuidRequest) {
		XsiteGoodsCate xsiteGoodsCate =
				xsiteGoodsCateService.findByCateUuid(xsiteGoodsCateQueryByCateUuidRequest.getCateUuid());
		if (Objects.isNull(xsiteGoodsCate)){
			return BaseResponse.success(null);
		}
		XsiteGoodsCateVO xsiteGoodsCateVO = new XsiteGoodsCateVO();
		KsBeanUtil.copyPropertiesThird(xsiteGoodsCate, xsiteGoodsCateVO);
		return BaseResponse.success(new XsiteGoodsCateByCateUuidResponse(xsiteGoodsCateVO));
	}

}

