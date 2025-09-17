package com.wanmi.sbc.customer.provider.impl.payingmembercustomerrel;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.*;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.PayingMemberCustomerRelAddResponse;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.PayingMemberCustomerRelModifyResponse;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.PayingMemberLevelListNewResponse;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelBaseVO;
import com.wanmi.sbc.customer.payingmembercustomerrel.model.root.PayingMemberCustomerRel;
import com.wanmi.sbc.customer.payingmembercustomerrel.service.PayingMemberCustomerRelService;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>客户与付费会员等级关联表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:48
 */
@RestController
@Validated
public class PayingMemberCustomerRelController implements PayingMemberCustomerRelProvider {
	@Autowired
	private PayingMemberCustomerRelService payingMemberCustomerRelService;

	@Autowired
	private SystemConfigQueryProvider systemConfigQueryProvider;

	@Autowired
	private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;


	@Override
	public BaseResponse<PayingMemberCustomerRelAddResponse> add(@RequestBody @Valid PayingMemberCustomerRelAddRequest payingMemberCustomerRelAddRequest) {
		PayingMemberCustomerRel payingMemberCustomerRel = KsBeanUtil.convert(payingMemberCustomerRelAddRequest, PayingMemberCustomerRel.class);
		return BaseResponse.success(new PayingMemberCustomerRelAddResponse(
				payingMemberCustomerRelService.wrapperVo(payingMemberCustomerRelService.add(payingMemberCustomerRel))));
	}

	@Override
	public BaseResponse<PayingMemberCustomerRelModifyResponse> modify(@RequestBody @Valid PayingMemberCustomerRelModifyRequest payingMemberCustomerRelModifyRequest) {
		PayingMemberCustomerRel payingMemberCustomerRel = KsBeanUtil.convert(payingMemberCustomerRelModifyRequest, PayingMemberCustomerRel.class);
		return BaseResponse.success(new PayingMemberCustomerRelModifyResponse(
				payingMemberCustomerRelService.wrapperVo(payingMemberCustomerRelService.modify(payingMemberCustomerRel))));
	}

	@Override
	public BaseResponse updateExpirationDate(@RequestBody @Valid PayingMemberCustomerRelModifyRequest payingMemberCustomerRelModifyRequest) {
		payingMemberCustomerRelService.updateExpirationDate(payingMemberCustomerRelModifyRequest.getExpirationDate(),
				payingMemberCustomerRelModifyRequest.getOpenTime(),payingMemberCustomerRelModifyRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid PayingMemberCustomerRelDelByIdRequest payingMemberCustomerRelDelByIdRequest) {
		PayingMemberCustomerRel payingMemberCustomerRel = KsBeanUtil.convert(payingMemberCustomerRelDelByIdRequest, PayingMemberCustomerRel.class);
		payingMemberCustomerRel.setDelFlag(DeleteFlag.YES);
		payingMemberCustomerRelService.deleteById(payingMemberCustomerRel);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid PayingMemberCustomerRelDelByIdListRequest payingMemberCustomerRelDelByIdListRequest) {
		payingMemberCustomerRelService.deleteByIdList(payingMemberCustomerRelDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateCustomerDiscount(@RequestBody @Valid PayingMemberUpdateDiscountRequest payingMemberUpdateDiscountRequest) {
		payingMemberCustomerRelService.updateCustomerDiscount(payingMemberUpdateDiscountRequest.getCustomerId(),
				payingMemberUpdateDiscountRequest.getLevelId(), payingMemberUpdateDiscountRequest.getDiscount());
		return BaseResponse.SUCCESSFUL();
	}

    @Override
    public BaseResponse degradation(PayingMemberDegradationRequest request) {
		payingMemberCustomerRelService.deleteByCustomerId(request.getCustomerId());

		if(request.getLevelId() == null){
			return BaseResponse.SUCCESSFUL();
		}
		PayingMemberCustomerRelAddRequest payingMemberCustomerRelAddRequest = PayingMemberCustomerRelAddRequest.builder()
				.levelId(request.getLevelId())
				.customerId(request.getCustomerId())
				.openTime(LocalDateTime.now())
				.expirationDate(LocalDate.now().plusMonths(12).minusDays(1L))
				.discountAmount(BigDecimal.ZERO)
				.delFlag(DeleteFlag.NO)
				.build();
		PayingMemberCustomerRel payingMemberCustomerRel = KsBeanUtil.convert(payingMemberCustomerRelAddRequest, PayingMemberCustomerRel.class);
		payingMemberCustomerRelService.add(payingMemberCustomerRel);
		return BaseResponse.SUCCESSFUL();
    }

	@Override
	public BaseResponse upgrade(PayingMemberUpgradeRequest request) {


		PayingMemberCustomerRel byCustomerId = payingMemberCustomerRelService.findByCustomerId(request.getCustomerId());
		if(byCustomerId == null){
			return BaseResponse.SUCCESSFUL();
		}
		//判断用户当前等级
		BaseResponse<PayingMemberLevelListNewResponse> payingMemberLevelListNewResponseBaseResponse =
				payingMemberLevelQueryProvider.listAllPayingMemberLevelNew();

		List<PayingMemberLevelBaseVO> payingMemberLevelVOList =
				payingMemberLevelListNewResponseBaseResponse.getContext().getPayingMemberLevelVOList();

		PayingMemberLevelBaseVO payingMemberLevelBaseVO = payingMemberLevelVOList.stream()
				.filter(payingMemberLevelVO -> payingMemberLevelVO.getLevelId().equals(byCustomerId.getLevelId()))
				.findFirst().get();

		String payingMemberLevelName = payingMemberLevelBaseVO.getPayingMemberLevelName();

		 if(payingMemberLevelName.equals("V3")){
			//最高级了，不用升级
			return BaseResponse.SUCCESSFUL();
		}

		//判断是否升级


		Integer levelId = null;

		BigDecimal upgradePay = BigDecimal.ZERO;
		if(payingMemberLevelName.equals("V1")){
			upgradePay = BigDecimal.valueOf(1000);
			levelId = payingMemberLevelVOList.stream()
					.filter(payingMemberLevelVO ->
							payingMemberLevelVO.getPayingMemberLevelName().equals("V2")).findFirst()
					.get().getLevelId();
		} else if(payingMemberLevelName.equals("V2")){
			upgradePay = BigDecimal.valueOf(2000);

			levelId = payingMemberLevelVOList.stream()
					.filter(payingMemberLevelVO ->
							payingMemberLevelVO.getPayingMemberLevelName().equals("V3")).findFirst()
					.get().getLevelId();
		}
		if(request.getTotalAmount().compareTo(upgradePay) < 0){
			//消费不够，不升级
			return BaseResponse.SUCCESSFUL();
		}

		//执行升级
		payingMemberCustomerRelService.deleteByCustomerId(request.getCustomerId());
		PayingMemberCustomerRelAddRequest payingMemberCustomerRelAddRequest = PayingMemberCustomerRelAddRequest.builder()
				.levelId(levelId)
				.customerId(request.getCustomerId())
				.openTime(LocalDateTime.now())
				.expirationDate(LocalDate.now().plusMonths(12).minusDays(1L))
				.discountAmount(BigDecimal.ZERO)
				.delFlag(DeleteFlag.NO)
				.build();
		PayingMemberCustomerRel payingMemberCustomerRel = KsBeanUtil.convert(payingMemberCustomerRelAddRequest, PayingMemberCustomerRel.class);
		payingMemberCustomerRelService.add(payingMemberCustomerRel);

		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse assign(PayingMemberAssignRequest request) {
		PayingMemberCustomerRel byCustomerId = payingMemberCustomerRelService.findByCustomerId(request.getCustomerId());
		if(byCustomerId != null){
			return BaseResponse.SUCCESSFUL();
		}

		BaseResponse<PayingMemberLevelListNewResponse> payingMemberLevelListNewResponseBaseResponse =
				payingMemberLevelQueryProvider.listAllPayingMemberLevelNew();

		List<PayingMemberLevelBaseVO> payingMemberLevelVOList =
				payingMemberLevelListNewResponseBaseResponse.getContext().getPayingMemberLevelVOList();

		PayingMemberLevelBaseVO payingMemberLevelBaseVO = payingMemberLevelVOList.stream()
				.filter(payingMemberLevelVO -> payingMemberLevelVO.getPayingMemberLevelName().equals(request.getLevenName()))
				.findFirst().get();

		PayingMemberCustomerRelAddRequest payingMemberCustomerRelAddRequest = PayingMemberCustomerRelAddRequest.builder()
				.levelId(payingMemberLevelBaseVO.getLevelId())
				.customerId(request.getCustomerId())
				.openTime(LocalDateTime.now())
				.expirationDate(LocalDate.now().plusMonths(12).minusDays(1L))
				.discountAmount(BigDecimal.ZERO)
				.delFlag(DeleteFlag.NO)
				.build();
		PayingMemberCustomerRel payingMemberCustomerRel = KsBeanUtil.convert(payingMemberCustomerRelAddRequest, PayingMemberCustomerRel.class);
		payingMemberCustomerRelService.add(payingMemberCustomerRel);
		return BaseResponse.SUCCESSFUL();
	}

}

