package com.wanmi.sbc.marketing.api.provider.bargainjoin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinAddRequest;
import com.wanmi.sbc.marketing.api.request.bargainjoin.JoinRequest;
import com.wanmi.sbc.marketing.bean.vo.BargainJoinVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>帮砍记录保存服务Provider</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@FeignClient(value = "${application.marketing.name}", contextId = "BargainJoinSaveProvider")
public interface BargainJoinSaveProvider {

    /**
     * 新增帮砍记录API
     *
     * @param bargainJoinAddRequest 帮砍记录新增参数结构 {@link BargainJoinAddRequest}
     * @return 新增的帮砍记录信息 {@link BargainJoinVO}
     * @author
     */
    @PostMapping("/marketing/${application.marketing.version}/bargainjoin/add")
    BaseResponse<BargainJoinVO> add(@RequestBody @Valid BargainJoinAddRequest bargainJoinAddRequest);

    @PostMapping("/marketing/${application.marketing.version}/bargainjoin/join")
    BaseResponse<BargainJoinVO> join(@RequestBody @Valid JoinRequest request);

}

