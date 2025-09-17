package com.wanmi.sbc.giftcard;

import com.wanmi.osd.OsdClient;
import com.wanmi.osd.bean.OsdClientParam;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.giftcard.service.GiftCardBatchSendExcelService;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBatchProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBatchQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.*;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBatchType;
import com.wanmi.sbc.marketing.bean.vo.GiftCardBatchVO;
import com.wanmi.sbc.setting.api.provider.yunservice.YunServiceProvider;
import com.wanmi.sbc.setting.api.response.yunservice.YunAvailableConfigResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.common.util.CollectionUtils;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name =  "礼品卡批次管理API", description =  "GiftCardBatchController")
@RestController
@Validated
@RequestMapping(value = "/giftCardBatch")
public class GiftCardBatchController {

    @Autowired
    private GiftCardBatchQueryProvider giftCardBatchQueryProvider;

    @Autowired
    private GiftCardBatchProvider giftCardBatchProvider;

    @Autowired
    private GiftCardBatchSendExcelService giftCardBatchSendExcelService;

    @Autowired
    private YunServiceProvider yunServiceProvider;

    @Autowired
    private CommonUtil commonUtil;

    @ReturnSensitiveWords(functionName = "f_gift_card_batch_list_sign_word")
    @Operation(summary = "分页查询礼品卡批次")
    @PostMapping("/page")
    public BaseResponse<GiftCardBatchPageResponse> getPage(@RequestBody @Valid GiftCardBatchPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        if (StringUtils.isBlank(pageReq.getSortColumn())) {
            pageReq.putSort("createTime", "desc");
        }
        BaseResponse<GiftCardBatchPageResponse> page = giftCardBatchQueryProvider.page(pageReq);
        // 批量发卡数据，需要拼接文件地址前缀，构成完整的地址
        if (GiftCardBatchType.SEND == pageReq.getBatchType()) {
            List<GiftCardBatchVO> content = page.getContext().getGiftCardBatchVOPage().getContent();
            if (CollectionUtils.isNotEmpty(content)) {
                String filePrefix = this.getFilePrefix();
                content.stream()
                        .filter(item -> Objects.nonNull(item.getExcelFilePath()))
                        .forEach(item -> item.setExcelFilePath(filePrefix.concat(item.getExcelFilePath())));
            }
        }
        return page;
    }

    @MultiSubmit
    @Operation(summary = "批量制卡-生成")
    @PostMapping("/batchMake/create")
    public BaseResponse batchMakeCreate(@RequestBody @Valid GiftCardBatchMakeCreateRequest request) {
        // 填充制卡人
        request.setCreatePerson(commonUtil.getOperatorId());
        giftCardBatchProvider.batchMakeCreate(request);
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @Operation(summary = "批量制卡-审核")
    @PutMapping("/batchMake/audit")
    public BaseResponse batchMakeAudit(@RequestBody @Valid GiftCardBatchAuditRequest request) {
        // 填充操作人
        request.setUserId(commonUtil.getOperatorId());
        request.setBatchType(GiftCardBatchType.MAKE);
        giftCardBatchProvider.batchAudit(request);
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @Operation(summary = "批量发卡-审核")
    @PutMapping("/batchSend/audit")
    public BaseResponse batchSendAudit(@RequestBody @Valid GiftCardBatchAuditRequest request) {
        // 填充操作人
        request.setUserId(commonUtil.getOperatorId());
        request.setBatchType(GiftCardBatchType.SEND);
        giftCardBatchProvider.batchAudit(request);
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @Operation(summary = "审核前校验批次")
    @PutMapping("/checkForAudit")
    public BaseResponse checkForAudit(@RequestBody @Valid GiftCardBatchCheckForAuditRequest request) {
        // 填充操作人
        request.setUserId(commonUtil.getOperatorId());
        giftCardBatchProvider.checkForAudit(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "批量发卡-下载模板")
    @RequestMapping(value = "/batchSend/excel/template/{encrypted}", method = RequestMethod.GET)
    public void template(@PathVariable String encrypted) {
        giftCardBatchSendExcelService.exportTemplate();
    }

    @Operation(summary = "批量发卡-下载错误文档")
    @RequestMapping(value = "/batchSend/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void batchSendDownErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        giftCardBatchSendExcelService.downErrExcel(commonUtil.getOperatorId(), ext);
    }

    @Operation(summary = "批量发卡-上传模板")
    @RequestMapping(value = "/batchSend/excel/upload/{giftCardId}", method = RequestMethod.POST)
    public BaseResponse<String> batchSendUpload(@RequestParam("uploadFile") MultipartFile uploadFile, @PathVariable Long giftCardId) {
        return BaseResponse.success(giftCardBatchSendExcelService.upload(uploadFile, commonUtil.getOperatorId(), giftCardId));
    }

    @MultiSubmit
    @Operation(summary = "批量发卡-导入")
    @RequestMapping(value = "/batchSend/import/{ext}/{giftCardId}", method = RequestMethod.GET)
    public BaseResponse<Boolean> batchSendImport(@PathVariable String ext, @PathVariable Long giftCardId) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        giftCardBatchSendExcelService.importBatchSend(commonUtil.getOperatorId(), giftCardId);
        return BaseResponse.success(Boolean.TRUE);
    }
    /**
     * @description 获取云存储文件地址前缀
     * @author malianfeng
     * @date 2022/12/19 17:29
     * @return java.lang.String
     */
    public String getFilePrefix() {
        YunAvailableConfigResponse configResponse = yunServiceProvider.getAvailableYun().getContext();
        OsdClientParam osdClientParam = KsBeanUtil.convert(configResponse,OsdClientParam.class);
        return Optional.ofNullable(OsdClient.instance().getPrefix(osdClientParam)).orElse(StringUtils.EMPTY);
    }
}
