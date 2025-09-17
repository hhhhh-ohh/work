package com.wanmi.sbc.marketing.provider.impl.bargainjoin;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.api.provider.bargainjoin.BargainJoinQueryProvider;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinByIdRequest;
import com.wanmi.sbc.marketing.api.request.bargainjoin.BargainJoinQueryRequest;
import com.wanmi.sbc.marketing.bargainjoin.model.root.BargainJoin;
import com.wanmi.sbc.marketing.bargainjoin.service.BargainJoinService;
import com.wanmi.sbc.marketing.bean.vo.BargainJoinVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>帮砍记录查询服务接口实现</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@RestController
@Validated
public class BargainJoinQueryController implements BargainJoinQueryProvider {
	@Autowired
	private BargainJoinService bargainJoinService;

	/**
	 * 分页查询帮砍记录API
	 *
	 * @param bargainJoinPageReq 分页请求参数和筛选对象 {@link BargainJoinQueryRequest}
	 * @author
	 */
	@Override
	public BaseResponse<MicroServicePage<BargainJoinVO>> page(@RequestBody @Valid BargainJoinQueryRequest bargainJoinPageReq) {
		Page<BargainJoin> bargainJoinPage = bargainJoinService.page(bargainJoinPageReq);
		Page<BargainJoinVO> newPage = bargainJoinPage.map(entity -> bargainJoinService.wrapperVo(entity));
		MicroServicePage<BargainJoinVO> microPage = new MicroServicePage<>(newPage, bargainJoinPageReq.getPageable());
		return BaseResponse.success(microPage);
	}

	/**
	 * 列表查询帮砍记录API
	 *
	 * @param bargainJoinListReq 列表请求参数和筛选对象 {@link BargainJoinQueryRequest}
	 * @author
	 */
	@Override
	public BaseResponse<List<BargainJoinVO>> list(@RequestBody @Valid BargainJoinQueryRequest bargainJoinListReq) {
		List<BargainJoin> bargainJoinList = bargainJoinService.list(bargainJoinListReq);
		List<BargainJoinVO> newList = bargainJoinList.stream().map(entity -> bargainJoinService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(newList);
	}

	/**
	 * 单个查询帮砍记录API
	 *
	 * @param bargainJoinByIdRequest 单个查询帮砍记录请求参数 {@link BargainJoinByIdRequest}
	 * @author
	 */
	@Override
	public BaseResponse<BargainJoinVO> getById(@RequestBody @Valid BargainJoinByIdRequest bargainJoinByIdRequest) {
		BargainJoin bargainJoin = bargainJoinService.getById(bargainJoinByIdRequest.getBargainJoinId());
		return BaseResponse.success(bargainJoinService.wrapperVo(bargainJoin));
	}

}

