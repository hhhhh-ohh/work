package com.wanmi.sbc.elastic.api.provider.spu;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuIdListReponse;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: dyt
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES商品SPU服务
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsSpuQueryProvider")
public interface EsSpuQueryProvider {

    /**
     * 根据条件分页查询商品SPU分页列表
     *
     * @param request 条件分页查询请求结构 {@link EsSpuPageRequest}
     * @return 分页列表 {@link EsSpuPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/spu/page")
    BaseResponse<EsSpuPageResponse> page(@RequestBody EsSpuPageRequest request);

    /**
     * 查询商品数量
     *
     * @param request 条件分页查询请求结构 {@link EsSpuPageRequest}
     * @return 分页列表 {@link EsSpuPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/spu/count")
    BaseResponse<Long> count(@RequestBody EsSpuPageRequest request);

    /**
     * 根据条件分页查询商品SPU分页列表
     *
     * @param request 条件分页查询请求结构 {@link EsSpuPageRequest}
     * @return 分页列表 {@link EsSpuPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/spu-id-list")
    BaseResponse<EsSpuIdListReponse> spuIdList(@RequestBody EsSpuPageRequest request);
}
