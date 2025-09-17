package com.wanmi.sbc.empower.legder;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.Ledger.*;

/**
 * 对接第三方分账接口
 */
public interface LedgerService {

    /**
     * 电子合同申请
     * @return
     */
    BaseResponse ecApply(EcApplyRequest ecApplyRequest);

    /**
     * 电子合同下载
     * @return
     */
    BaseResponse ecDownLoad(EcDownloadRequest ecDownloadRequest);

    /**
     * 电子合同查询
     * @param ecQStatusRequest
     * @return
     */
    BaseResponse ecQStatus(EcQStatusRequest ecQStatusRequest);

    /**
     * 附件上传
     * @return
     */
    BaseResponse uploadFile(UploadRequest uploadRequest);

    /**
     * 进件校验
     * @return
     */
    BaseResponse  verifyContractInfo(VerifyContractInfoRequest verifyContractInfoRequest);

    /**
     * 新增商户进件
     * @return
     */
    BaseResponse addMer(AddMerRequest addMerRequest);


    /**
     * 进件复议提交
     * @return
     */
    BaseResponse reconsiderSubmit(ReconsiderSubmitRequest reconsiderSubmitRequest);

    /**
     * 进件信息查询
     * @return
     */
    BaseResponse queryContract(QueryContractRequest queryContractRequest);


    /**
     * 卡bin查询
     */
    BaseResponse cardBin(CardBinRequest cardBinRequest);

    /**
     * 商户分账业务开通申请
     * @return
     */
    BaseResponse applySplitMer(ApplySplitMerRequest applySplitMerRequest);

    /**
     * 分账商户信息查询
     */
    BaseResponse querySplitMer(QuerySplitMerRequest querySplitMerRequest);

    /**
     * 分账接收方创建申请
     * @return
     */
    BaseResponse applySplitReceiver(ApplySplitReceiverRequest applySplitReceiverRequest);

    /**
     * 分账关系绑定申请
     * @return
     */
    BaseResponse applyBind(ApplyBindRequest applyBindRequest);

    BaseResponse addB2bBusi(AddB2bBusiRequest addB2bBusiRequest);

}
