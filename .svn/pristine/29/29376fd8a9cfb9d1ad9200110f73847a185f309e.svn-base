package com.wanmi.sbc.marketing.provider.impl.bargain;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainQueryProvider;
import com.wanmi.sbc.marketing.api.request.bargain.BargainByIdRequest;
import com.wanmi.sbc.marketing.api.request.bargain.BargainQueryRequest;
import com.wanmi.sbc.marketing.bargain.model.root.BargainJoinGoodsInfo;
import com.wanmi.sbc.marketing.bargain.service.BargainService;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>砍价查询服务接口实现</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@RestController
@Validated
public class BargainQueryController implements BargainQueryProvider {
	@Autowired
	private BargainService bargainService;

	/**
	 * 分页查询砍价API
	 *
	 * @param bargainPageReq 分页请求参数和筛选对象 {@link BargainQueryRequest}
	 * @author
	 */
	@Override
	public BaseResponse<MicroServicePage<BargainVO>> page(@RequestBody @Valid BargainQueryRequest bargainPageReq) {
		return BaseResponse.success(new MicroServicePage<>(bargainService.page(bargainPageReq), bargainPageReq.getPageable()));
	}

	@Override
	public BaseResponse<MicroServicePage<BargainVO>> pageForPlatForm(@RequestBody @Valid BargainQueryRequest bargainPageReq) {
		return BaseResponse.success(new MicroServicePage<>(bargainService.pageForPlatForm(bargainPageReq), bargainPageReq.getPageable()));
	}

	/**
	 * 列表查询砍价API
	 *
	 * @param bargainListReq 列表请求参数和筛选对象 {@link BargainQueryRequest}
	 * @author
	 */
	@Override
	public BaseResponse<List<BargainVO>> list(@RequestBody @Valid BargainQueryRequest bargainListReq) {
		List<BargainJoinGoodsInfo> bargainJoinGoodsInfoList = bargainService.list(bargainListReq);
		List<BargainVO> newList = bargainJoinGoodsInfoList.stream().map(entity -> bargainService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(newList);
	}

    /**
     * 单个查询砍价API
     *
     * @param bargainByIdRequest 单个查询砍价请求参数 {@link BargainByIdRequest}
     * @author
     */
    @Override
    public BaseResponse<BargainVO> getById(
            @RequestBody @Valid BargainByIdRequest bargainByIdRequest) {
        BargainVO bargainVO = bargainService.getById(bargainByIdRequest.getBargainId());
        if (Objects.nonNull(bargainVO)
                && Objects.equals(bargainVO.getCustomerId(), bargainByIdRequest.getCustomerId())) {
            bargainVO.setCreateFlag(Boolean.TRUE);
        }
        return BaseResponse.success(bargainVO);
    }

	@Override
	public BaseResponse<BargainVO> getByIdWithBargainGoods(@RequestBody @Valid BargainByIdRequest bargainByIdRequest) {
		return BaseResponse.success(bargainService.getByIdWithBargainGoods(bargainByIdRequest.getBargainId()));
	}

	@Override
	public BaseResponse<BargainVO> getByIdForPlatForm(@RequestBody @Valid BargainByIdRequest bargainByIdRequest) {
		return BaseResponse.success(bargainService.getByIdForPlatForm(bargainByIdRequest.getBargainId()));
	}

	@Override
	public BaseResponse canCommit(@RequestBody Long bargainId) {
		bargainService.canCommit(bargainId);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<Long> getStock(@RequestBody Long bargainId) {
		return BaseResponse.success(bargainService.getStock(bargainId));
	}

}

