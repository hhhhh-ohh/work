package com.wanmi.sbc.elastic.api.provider.standard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardPageRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.elastic.api.response.standard.EsStandardPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: dyt
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES标品库查询服务
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsStandardQueryProvider")
public interface EsStandardQueryProvider {

    /**
     * 根据条件分页查询商品库分页列表
     *
     * @param request 条件分页查询请求结构 {@link EsStandardPageRequest}
     * @return 分页列表 {@link EsStandardPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/standard/page")
    BaseResponse<EsStandardPageResponse> page(@RequestBody EsStandardPageRequest request);

    /**
     * 查询商品数量
     *
     * @param request 条件分页查询请求结构 {@link EsStandardPageRequest}
     * @return 分页列表 {@link EsStandardPageRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/standard/count")
    BaseResponse<Long> count (@RequestBody EsStandardPageRequest request);
}
