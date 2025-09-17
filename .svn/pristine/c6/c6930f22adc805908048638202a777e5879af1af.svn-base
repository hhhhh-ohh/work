package com.wanmi.sbc.setting.provider.impl.payadvertisement;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementStoreVO;
import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisementStore;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.setting.api.provider.payadvertisement.PayAdvertisementQueryProvider;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementPageRequest;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementQueryRequest;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementPageResponse;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementListRequest;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementListResponse;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementByIdRequest;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementByIdResponse;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementPageVO;
import com.wanmi.sbc.setting.payadvertisement.service.PayAdvertisementService;
import com.wanmi.sbc.setting.payadvertisement.model.root.PayAdvertisement;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>支付广告页配置查询服务接口实现</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@RestController
@Validated
public class PayAdvertisementQueryController implements PayAdvertisementQueryProvider {
	@Autowired
	private PayAdvertisementService payAdvertisementService;

	/**
	 * 分页请求参数和筛选对象
	 * @param payAdvertisementPageReq 分页请求参数和筛选对象 {@link PayAdvertisementPageRequest}
	 * @return
	 */
	@Override
	public BaseResponse<PayAdvertisementPageResponse> page(@RequestBody @Valid PayAdvertisementPageRequest payAdvertisementPageReq) {
		PayAdvertisementQueryRequest queryReq = KsBeanUtil.convert(payAdvertisementPageReq, PayAdvertisementQueryRequest.class);
		Page<PayAdvertisement> payAdvertisementPage = payAdvertisementService.page(queryReq);
		Page<PayAdvertisementVO> newPage = payAdvertisementPage.map(entity -> payAdvertisementService.wrapperVo(entity));
		MicroServicePage<PayAdvertisementVO> microPage = new MicroServicePage<>(newPage, payAdvertisementPageReq.getPageable());
		PayAdvertisementPageResponse finalRes = new PayAdvertisementPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	/**
	 * 列表请求参数和筛选对象
	 * @param payAdvertisementListReq 列表请求参数和筛选对象 {@link PayAdvertisementListRequest}
	 * @return
	 */
	@Override
	public BaseResponse<PayAdvertisementListResponse> list(@RequestBody @Valid PayAdvertisementListRequest payAdvertisementListReq) {
		PayAdvertisementQueryRequest queryReq = KsBeanUtil.convert(payAdvertisementListReq, PayAdvertisementQueryRequest.class);
		List<PayAdvertisement> payAdvertisementList = payAdvertisementService.list(queryReq);
		List<PayAdvertisementVO> newList = payAdvertisementList.stream().map(entity -> payAdvertisementService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PayAdvertisementListResponse(newList));
	}

	/**
	 * 单个查询支付广告页配置请求参数
	 * @param payAdvertisementByIdRequest 单个查询支付广告页配置请求参数 {@link PayAdvertisementByIdRequest}
	 * @return
	 */
	@Override
	public BaseResponse<PayAdvertisementByIdResponse> getById(@RequestBody @Valid PayAdvertisementByIdRequest payAdvertisementByIdRequest) {
		PayAdvertisementByIdResponse payAdvertisementByIdResponse = new PayAdvertisementByIdResponse();
		PayAdvertisement payAdvertisement =
		payAdvertisementService.getOne(payAdvertisementByIdRequest.getId());
		PayAdvertisementVO payAdvertisementVO = payAdvertisementService.wrapperVo(payAdvertisement);
		payAdvertisementByIdResponse.setPayAdvertisementVO(payAdvertisementVO);
		if (Objects.equals(Constants.TWO,payAdvertisementVO.getStoreType())){
			List<PayAdvertisementStore> payAdvertisementStoreList = payAdvertisementService
					.getPayAdvertisementStore(payAdvertisementByIdRequest.getId());
			payAdvertisementByIdResponse.setStoreVOList(KsBeanUtil.convert(payAdvertisementStoreList, PayAdvertisementStoreVO.class));
		}
		return BaseResponse.success(payAdvertisementByIdResponse);
	}
}

