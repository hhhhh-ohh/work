package com.wanmi.sbc.setting.api.provider.pagemanage;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendDeleteByIdRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.GoodsInfoExtendModifyRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendModifyRequest;
import com.wanmi.sbc.setting.api.response.pagemanage.GoodsInfoExtendByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author houshuai
 * @date 2021/5/26 16:08
 * @description 商品推广 feign
 */
@FeignClient(value = "${application.setting.name}", contextId = "GoodsInfoExtendProvider")
public interface GoodsInfoExtendProvider {

    /**
     * 渠道链接修改
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/goods-info-extend/modify-goods-info-extend")
    BaseResponse modifyGoodsInfoExtend(
            @RequestBody GoodsInfoExtendModifyRequest request);


    /**
     * 渠道链接修改
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/goods-info-extend/delete-goods-info-extend")
    BaseResponse deleteGoodsInfoExtend(@RequestBody GoodsInfoExtendDeleteByIdRequest request);
}
