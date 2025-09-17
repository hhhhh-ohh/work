package com.wanmi.sbc.marketing.provider.impl.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardProvider;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.*;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCardVO;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicCard;
import com.wanmi.sbc.marketing.electroniccoupon.model.root.ElectronicImportRecord;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicCardService;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicImportRecordService;
import com.wanmi.sbc.marketing.electroniccoupon.service.ElectronicSendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>电子卡密表保存服务接口实现</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@RestController
@Validated
public class ElectronicCardController implements ElectronicCardProvider {
	@Autowired
	private ElectronicCardService electronicCardService;

	@Autowired
	private ElectronicImportRecordService electronicImportRecordService;

	@Autowired
	private ElectronicSendRecordService electronicSendRecordService;

	@Override
	public BaseResponse<ElectronicExportTemplateResponse> exportTemplate() {
		String file = electronicImportRecordService.exportTemplate();
		return BaseResponse.success(ElectronicExportTemplateResponse.builder().templateFile(file).build());
	}

	@Override
	public BaseResponse<ElectronicImportRecordAddResponse> addImportRecord(@RequestBody @Valid ElectronicImportRecordAddRequest electronicImportRecordAddRequest) {
		ElectronicImportRecord electronicImportRecord = KsBeanUtil.convert(electronicImportRecordAddRequest, ElectronicImportRecord.class);
		return BaseResponse.success(new ElectronicImportRecordAddResponse(
				electronicImportRecordService.wrapperVo(electronicImportRecordService.add(electronicImportRecord))));
	}

	@Override
	public BaseResponse batchAdd(@RequestBody @Valid ElectronicCardBatchAddRequest electronicCardAddRequest) {
		List<ElectronicCard> electronicCards = KsBeanUtil.convertList(electronicCardAddRequest.getDtoList(), ElectronicCard.class);
		electronicCardService.saveAll(electronicCards);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<ElectronicCardModifyResponse> modify(@RequestBody @Valid ElectronicCardModifyRequest electronicCardModifyRequest) {
		ElectronicCard electronicCard = KsBeanUtil.convert(electronicCardModifyRequest, ElectronicCard.class);
		return BaseResponse.success(new ElectronicCardModifyResponse(
				electronicCardService.wrapperVo(electronicCardService.modify(electronicCard,electronicCardModifyRequest.getBaseStoreId()))));
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid ElectronicCardDelByIdListRequest electronicCardDelByIdListRequest) {
		electronicCardService.deleteByIdList(electronicCardDelByIdListRequest.getIdList(),electronicCardDelByIdListRequest.getBaseStoreId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteAll(@RequestBody @Valid ElectronicCardDelAllRequest electronicCardDelAllRequest) {
		electronicCardService.deleteAll(electronicCardDelAllRequest.getCouponId(), electronicCardDelAllRequest.getRecordId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateCardInvalid(@RequestBody @Valid ElectronicCardInvalidRequest electronicCardInvalidRequest) {
		electronicCardService.updateCardInvalid(electronicCardInvalidRequest.getTime());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<ElectronicSendRecordBatchResponse> addSendRecord(@RequestBody @Valid ElectronicSendRecordAddRequest electronicSendRecordAddRequest) {
		List<ElectronicCardVO> cards = KsBeanUtil.convertList(
				electronicSendRecordService.batchAdd(electronicSendRecordAddRequest.getDtoList()),
				ElectronicCardVO.class);
		return BaseResponse.success(ElectronicSendRecordBatchResponse.builder().electronicCardVOList(cards).build());
	}

	@Override
	public BaseResponse<ElectronicSendRecordSendAgainResponse> modifySendRecord(@RequestBody @Valid ElectronicSendRecordModifyRequest electronicSendRecordModifyRequest) {
		Map<String, List<ElectronicCardVO>> cardMap = electronicSendRecordService.sendAgain(electronicSendRecordModifyRequest.getRecordIds());
		return BaseResponse.success(ElectronicSendRecordSendAgainResponse.builder().cardMap(cardMap).build());
	}
}

