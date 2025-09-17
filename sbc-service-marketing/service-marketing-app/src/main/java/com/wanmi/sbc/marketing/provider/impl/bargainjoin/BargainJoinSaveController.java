package com.wanmi.sbc.marketing.provider.impl.bargainjoin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.bargainjoin.BargainJoinSaveProvider;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinAddRequest;
import com.wanmi.sbc.marketing.api.request.bargainjoin.JoinRequest;
import com.wanmi.sbc.marketing.bargainjoin.model.root.BargainJoin;
import com.wanmi.sbc.marketing.bargainjoin.service.BargainJoinService;
import com.wanmi.sbc.marketing.bean.vo.BargainJoinVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>帮砍记录保存服务接口实现</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@RestController
@Validated
public class BargainJoinSaveController implements BargainJoinSaveProvider {
	@Autowired
	private BargainJoinService bargainJoinService;

	/**
	 * 新增帮砍记录API
	 *
	 * @param bargainJoinAddRequest 帮砍记录新增参数结构 {@link BargainJoinAddRequest}
     * @return 新增的帮砍记录信息 {@link BargainJoinVO}
     * @author
     */
    @Override
    public BaseResponse<BargainJoinVO> add(@RequestBody @Valid BargainJoinAddRequest bargainJoinAddRequest) {
        BargainJoin bargainJoin = new BargainJoin();
        KsBeanUtil.copyPropertiesThird(bargainJoinAddRequest, bargainJoin);
        return BaseResponse.success(bargainJoinService.wrapperVo(bargainJoinService.add(bargainJoin)));
    }

    @Override
    public BaseResponse<BargainJoinVO> join(@RequestBody @Valid JoinRequest request) {
        return BaseResponse.success(bargainJoinService.join(request));
    }

}

