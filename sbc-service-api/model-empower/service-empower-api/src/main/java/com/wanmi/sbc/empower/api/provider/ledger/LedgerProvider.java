package com.wanmi.sbc.empower.api.provider.ledger;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.Ledger.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分账服务Provider</p>
 * @author zhanghao
 * @date 2019-12-03 15:36:05
 */
@FeignClient(value = "${application.empower.name}",contextId = "LedgerProvider")
public interface LedgerProvider {


    /**
     * 电子合同申请
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/ec-apply")
    BaseResponse ecApply(@RequestBody @Valid EcApplyRequest ecApplyRequest);

    /**
     * 电子合同下载
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/ec-download")
    BaseResponse ecDownLoad(@RequestBody @Valid EcDownloadRequest ecDownloadRequest);


    /**
     * 电子合同查询
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/ec-q-status")
    BaseResponse ecQStatus(@RequestBody @Valid EcQStatusRequest ecQStatusRequest);

    /**
     * 附件上传
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/upload-file")
    BaseResponse uploadFile(@RequestBody @Valid UploadRequest uploadRequest);

    /**
     * 进件校验
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/verify-contract-info")
    BaseResponse  verifyContractInfo(@RequestBody @Valid VerifyContractInfoRequest verifyContractInfoRequest);

    /**
     * 新增商户进件
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/add-mer")
    BaseResponse addMer(@RequestBody @Valid AddMerRequest addMerRequest);


    /**
     * 进件复议提交
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/reconsider-submit")
    BaseResponse reconsiderSubmit(@RequestBody @Valid ReconsiderSubmitRequest reconsiderSubmitRequest);


    /**
     * 进件信息查询
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/query-contract")
    BaseResponse queryContract(@RequestBody @Valid QueryContractRequest queryContractRequest);


    /**
     * 卡bin查询
     */
    @PostMapping("/empower/${application.empower.version}/card-bin")
    BaseResponse cardBin(@RequestBody @Valid CardBinRequest cardBinRequest);

    /**
     * 商户分账业务开通申请
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/apply-split-mer")
    BaseResponse applySplitMer(@RequestBody @Valid ApplySplitMerRequest applySplitMerRequest);


    /**
     * 商户分账业务开通申请
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/query-split-mer")
    BaseResponse querySplitMer(@RequestBody @Valid QuerySplitMerRequest querySplitMerRequest);

    /**
     * 分账接收方创建申请
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/apply-split-receiver")
    BaseResponse applySplitReceiver(@RequestBody @Valid ApplySplitReceiverRequest applySplitReceiverRequest);

    /**
     * 分账关系绑定申请
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/apply-bind")
    BaseResponse applyBind(@RequestBody @Valid ApplyBindRequest applyBindRequest);


    /**
     * 新增线上业务类型
     * @param addB2bBusiRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/add-B2B-Busi")
    BaseResponse addB2bBusi(@RequestBody @Valid AddB2bBusiRequest addB2bBusiRequest);
}
