package com.wanmi.sbc.empower.api.provider.channel.vop.cate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.vop.category.VopGetCategoryRequest;
import com.wanmi.sbc.empower.api.response.vop.category.VopGetCategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author EDZ
 * @interfaceName VopGetCategorysProvider
 * @description 京东vop分类查询（类目）
 * @date 2021/5/10 13:50
 **/
@FeignClient(value = "${application.empower.name}", contextId = "VopCategoryProvider")
public interface VopCategoryProvider {

    /**
     * 京东vop分类信息
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/vopCategory/getCategory")
    BaseResponse<VopGetCategoryResponse> getCategory(@RequestBody VopGetCategoryRequest request);
}
