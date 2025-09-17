package com.wanmi.sbc.goods.provider.impl.goodscatethirdcaterel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodscatethirdcaterel.GoodsCateThirdCateRelProvider;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.*;
import com.wanmi.sbc.goods.api.request.goods.GoodsByThirdCateRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelAddRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelDelByIdListRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelDelByIdRequest;
import com.wanmi.sbc.goods.api.request.goodscatethirdcaterel.GoodsCateThirdCateRelModifyRequest;
import com.wanmi.sbc.goods.api.response.goodscatethirdcaterel.GoodsCateThirdCateRelAddResponse;
import com.wanmi.sbc.goods.api.response.goodscatethirdcaterel.GoodsCateThirdCateRelModifyResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.goodscatethirdcaterel.model.root.GoodsCateThirdCateRel;
import com.wanmi.sbc.goods.goodscatethirdcaterel.service.GoodsCateThirdCateRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>平台类目和第三方平台类目映射保存服务接口实现</p>
 *
 * @author
 * @date 2020-08-18 19:51:55
 */
@RestController
@Validated
public class GoodsCateThirdCateRelController implements GoodsCateThirdCateRelProvider {
    @Autowired
    private GoodsCateThirdCateRelService goodsCateThirdCateRelService;

    @Override
    public BaseResponse<GoodsCateThirdCateRelAddResponse> addBatch(@RequestBody @Valid GoodsCateThirdCateRelAddRequest goodsCateThirdCateRelAddRequest) {
        List<GoodsCateThirdCateRel> goodsCateThirdCateRels = KsBeanUtil.convert(goodsCateThirdCateRelAddRequest.getGoodsCateThirdCateRels(), GoodsCateThirdCateRel.class);
        ThirdPlatformType thirdPlatformType = goodsCateThirdCateRels.get(0).getThirdPlatformType();
        if (ThirdPlatformType.LINKED_MALL.equals(thirdPlatformType) || ThirdPlatformType.VOP.equals(thirdPlatformType)) {
            int source =
                    ThirdPlatformType.LINKED_MALL.equals(thirdPlatformType)
                            ? GoodsSource.LINKED_MALL.toValue()
                            : GoodsSource.VOP.toValue();
            return BaseResponse.success(goodsCateThirdCateRelService.addBatch(goodsCateThirdCateRels, source));
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse addBatchForWechat(@RequestBody @Valid GoodsCateThirdCateRelAddRequest goodsCateThirdCateRelAddRequest) {
        List<GoodsCateThirdCateRel> goodsCateThirdCateRels = KsBeanUtil.convert(goodsCateThirdCateRelAddRequest.getGoodsCateThirdCateRels(), GoodsCateThirdCateRel.class);
        goodsCateThirdCateRelService.addBatchForWechat(goodsCateThirdCateRels);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<GoodsCateThirdCateRelModifyResponse> modify(@RequestBody @Valid GoodsCateThirdCateRelModifyRequest goodsCateThirdCateRelModifyRequest) {
        GoodsCateThirdCateRel goodsCateThirdCateRel = KsBeanUtil.convert(goodsCateThirdCateRelModifyRequest, GoodsCateThirdCateRel.class);
        return BaseResponse.success(new GoodsCateThirdCateRelModifyResponse(
                goodsCateThirdCateRelService.wrapperVo(goodsCateThirdCateRelService.modify(goodsCateThirdCateRel))));
    }

    @Override
    public BaseResponse deleteById(@RequestBody @Valid GoodsCateThirdCateRelDelByIdRequest goodsCateThirdCateRelDelByIdRequest) {
        GoodsCateThirdCateRel goodsCateThirdCateRel = KsBeanUtil.convert(goodsCateThirdCateRelDelByIdRequest, GoodsCateThirdCateRel.class);
        goodsCateThirdCateRel.setDelFlag(DeleteFlag.YES);
        goodsCateThirdCateRelService.deleteById(goodsCateThirdCateRel);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteByIdList(@RequestBody @Valid GoodsCateThirdCateRelDelByIdListRequest goodsCateThirdCateRelDelByIdListRequest) {
        List<GoodsCateThirdCateRel> goodsCateThirdCateRelList = goodsCateThirdCateRelDelByIdListRequest.getIdList().stream()
                .map(Id -> {
                    GoodsCateThirdCateRel goodsCateThirdCateRel = KsBeanUtil.convert(Id, GoodsCateThirdCateRel.class);
                    goodsCateThirdCateRel.setDelFlag(DeleteFlag.YES);
                    return goodsCateThirdCateRel;
                }).collect(Collectors.toList());
        goodsCateThirdCateRelService.deleteByIdList(goodsCateThirdCateRelList);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse deleteWechat(@RequestBody @Valid DeleteWechatCateMapRequest request) {
        goodsCateThirdCateRelService.deleteWechat(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<GoodsCateThirdCateRelAddResponse> modifyThirdCate(@RequestBody @Valid GoodsByThirdCateRequest request) {
        GoodsCateThirdCateRelAddResponse response = goodsCateThirdCateRelService.modifyThirdCate(request);
        return BaseResponse.success(response);
    }

}

