package com.wanmi.sbc.dw.api.provider;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.dw.api.request.RelationRecommendRequest;
import com.wanmi.sbc.dw.bean.recommend.RecommendData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;


/**
 * @ClassName: com.wanmi.sbc.dw.api.provider.RecommendListProvider
 * @Description:
 * @Author: 何军红
 * @Time: 2020/12/1 14:04
 * @Version: 1.0
 */
@FeignClient(value = "${application.dw.name}", contextId = "RecommendListProvider")
public interface RecommendListProvider {

    /**
     * 商品相关性推荐查询接口
     *
     * @param request
     * @return
     */
    @PostMapping("/dw/${application.dw.version}/recommend/query")
    BaseResponse<RecommendData> queryGoodsRecommend(@RequestBody @Valid RelationRecommendRequest request);

}
