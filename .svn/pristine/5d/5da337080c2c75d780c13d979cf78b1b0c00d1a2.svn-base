package com.wanmi.sbc.marketing.api.provider.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.*;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>电子卡密表保存服务Provider</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@FeignClient(value = "${application.marketing.name}", contextId = "ElectronicCardProvider")
public interface ElectronicCardProvider {

    /**
     * 下载卡密导入模版API
     *
     * @author 许云鹏
     * @param
     * @return 卡密导入模版响应 {@link ElectronicExportTemplateResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/cards/export/template")
    BaseResponse<ElectronicExportTemplateResponse> exportTemplate();

    /**
     * 新增卡密导入记录表API
     *
     * @author 许云鹏
     * @param electronicImportRecordAddRequest 卡密导入记录表新增参数结构 {@link ElectronicImportRecordAddRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/import/record/add")
    BaseResponse<ElectronicImportRecordAddResponse> addImportRecord(@RequestBody @Valid ElectronicImportRecordAddRequest electronicImportRecordAddRequest);

    /**
     * 批量新增电子卡密表API
     *
     * @author 许云鹏
     * @param electronicCardAddRequest 电子卡密表新增参数结构 {@link ElectronicCardBatchAddRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/cards/batch/add")
    BaseResponse batchAdd(@RequestBody @Valid ElectronicCardBatchAddRequest electronicCardAddRequest);

    /**
     * 修改电子卡密表API
     *
     * @author 许云鹏
     * @param electronicCardModifyRequest 电子卡密表修改参数结构 {@link ElectronicCardModifyRequest}
     * @return 修改的电子卡密表信息 {@link ElectronicCardModifyResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/cards/modify")
    BaseResponse<ElectronicCardModifyResponse> modify(@RequestBody @Valid ElectronicCardModifyRequest electronicCardModifyRequest);

    /**
     * 批量删除电子卡密表API
     *
     * @author 许云鹏
     * @param electronicCardDelByIdListRequest 批量删除参数结构 {@link ElectronicCardDelByIdListRequest}
     * @return 删除结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/cards/delete-by-id-list")
    BaseResponse deleteByIdList(@RequestBody @Valid ElectronicCardDelByIdListRequest electronicCardDelByIdListRequest);

    /**
     * 根据卡券或批次删除电子卡密表API
     *
     * @author 许云鹏
     * @param electronicCardDelAllRequest 批量删除参数结构 {@link ElectronicCardDelAllRequest}
     * @return 删除结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/cards/delete-all")
    BaseResponse deleteAll(@RequestBody @Valid ElectronicCardDelAllRequest electronicCardDelAllRequest);

    /**
     * 批量失效卡密API
     *
     * @author 许云鹏
     * @param electronicCardInvalidRequest 批量失效参数结构 {@link ElectronicCardInvalidRequest}
     * @return 删除结果 {@link BaseResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/cards/invalid")
    BaseResponse updateCardInvalid(@RequestBody @Valid ElectronicCardInvalidRequest electronicCardInvalidRequest);

    /**
     * 添加发放记录API
     *
     * @author 许云鹏
     * @param electronicSendRecordAddRequest 电子卡密表新增参数结构 {@link ElectronicSendRecordAddRequest}
     * @return 添加结果 {@link ElectronicSendRecordBatchResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/cards/add-send-record")
    BaseResponse<ElectronicSendRecordBatchResponse> addSendRecord(@RequestBody @Valid ElectronicSendRecordAddRequest electronicSendRecordAddRequest);

    /**
     * 重发修改发放记录API
     *
     * @author 许云鹏
     * @param electronicSendRecordModifyRequest 电子卡密表新增参数结构 {@link ElectronicSendRecordModifyRequest}
     * @return 添加结果 {@link ElectronicSendRecordSendAgainResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/electronic/cards/modify-send-record")
    BaseResponse<ElectronicSendRecordSendAgainResponse> modifySendRecord(@RequestBody @Valid ElectronicSendRecordModifyRequest electronicSendRecordModifyRequest);



}

