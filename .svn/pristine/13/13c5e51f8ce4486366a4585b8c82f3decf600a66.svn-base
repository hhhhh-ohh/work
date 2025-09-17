package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import com.wanmi.sbc.common.annotation.CanEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

/**
 * 拉卡拉商户进件申请request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Schema
public class LakalaAddMerRequest extends LakalaBaseRequest{

    /**
     * 进件POS类型 —按接入系统做控制
     * posType：进件POS类型WECHAT_PAY 专业化扫码
     *
     * 2023-08-16 适配拉卡拉收银台支付，拉卡拉要求把此参数从专业化扫码改为B2B收银台
     */
    @Schema(description = "进件POS类型")
    @Builder.Default
    @Max(32)
//    private String posType = "WECHAT_PAY";
    private String posType = "B2B_CASHIER_DESK";

    /**
     * 商户注册名称
     * 长度不小于7个汉字；营业执照商户可填营业执照名称，小微商户入网不得含有“有限公司”。【14~80字符，不可为纯数字】
     */
    @Schema(description = "商户注册名称")
    @Max(80)
    @NotBlank
    private String merRegName;

    /**
     * 商户地区代码
     */
    @Schema(description = "商户地区代码")
    @Max(8)
    @NotBlank
    private String merRegDistCode;


    /**
     * 商户详细地址
     * 去除省，市，区后的详细地址
     */
    @Schema(description = "商户详细地址")
    @Max(80)
    @NotBlank
    private String merRegAddr;


    /**
     * 商户MCC编号
     * 银联商户类别代码，参见【MCC对照表】
     */
    @Schema(description = "商户MCC编号")
    @Max(8)
    @NotBlank
    private String mccCode;


    /**
     * 营业执照名称
     * 小微商户可不传，其它必传
     */
    @Schema(description = "营业执照名称")
    @Max(80)
    @NotBlank
    private String merBlisName;

    /**
     * 营业执照号
     * 小微商户可不传，对公进件必传，且不可与法人证件相同
     */
    @Schema(description = "营业执照号")
    @Max(40)
    @NotBlank
    private String merBlis;

    /**
     * 营业执照开始日期
     * 格式（yyyy-MM-dd）有营业执照时必传，否则微信实名认证会失败
     */
    @Schema(description = "营业执照开始日期")
    @Max(10)
    @NotBlank
    private String merBlisStDt;


    /**
     * 营业执照有效期
     * 格式（yyyy-MM-dd）有营业执照时必传，否则微信实名认证会失败
     */
    @Schema(description = "营业执照有效期")
    @Max(10)
    @NotBlank
    private String merBlisExpDt;

    /**
     * 商户经营内容
     * 参看【经营内容字典表】文档
     */
    @Schema(description = "商户经营内容")
    @Max(64)
    @NotBlank
    private String merBusiContent;


    /**
     * 商户法人姓名
     */
    @Schema(description = "商户法人姓名")
    @Max(20)
    @NotBlank
    private String larName;

    /**
     * 法人证件类型
     */
    @Schema(description = "法人证件类型")
    @Max(8)
    @Builder.Default
    private String larIdType = "01";


    /**
     * 法人身份证号码
     */
    @Schema(description = "法人身份证号码")
    @Max(40)
    @NotBlank
    private String larIdcard;


    /**
     * 法人身份证开始日期 yyyy-MM-dd
     */
    @Schema(description = "法人身份证开始日期 yyyy-MM-dd")
    @Max(10)
    @NotBlank
    private String larIdcardStDt;

    /**
     * 法人身份证有效期 yyyy-MM-dd
     */
    @Schema(description = "法人身份证有效期 yyyy-MM-dd")
    @Max(10)
    @NotBlank
    private String larIdcardExpDt;

    /**
     * 商户联系人手机号码
     */
    @Schema(description = "商户联系人手机号码")
    @Max(20)
    @NotBlank
    private String merContactMobile;

    /**
     * 商户联系人
     */
    @Schema(description = "商户联系人")
    @Max(32)
    @NotBlank
    private String merContactName;


    /**
     * 结算账户开户行号
     * 可根据结算卡信息进行查询，（仅支持对私结算卡查询）参见【卡BIN信息查询】
     */
    @Schema(description = "结算账户开户行号")
    @Max(20)
    @NotBlank
    private String openningBankCode;


    /**
     * 结算账户开户行名称
     * 可根据结算卡信息进行查询，（仅支持对私结算卡查询）参见【卡BIN信息查询】
     */
    @Schema(description = "结算账户开户行名称")
    @Max(40)
    @NotBlank
    private String openningBankName;


    /**
     * 结算账户清算行号
     * 可根据结算卡信息进行查询，（仅支持对私结算卡查询）参见【卡BIN信息查询】
     */
    @Schema(description = "结算账户清算行号")
    @Max(20)
    @NotBlank
    private String clearingBankCode;


    /**
     * 结算账户账号
     */
    @Schema(description = "结算账户账号")
    @Max(40)
    @NotBlank
    private String acctNo;

    /**
     * 结算账户名称
     */
    @Schema(description = "结算账户名称")
    @Max(40)
    @NotBlank
    private String acctName;


    /**
     * 结算账户性质
     * 57 对公
     * 58 对私
     */
    @Schema(description = "结算账户性质")
    @Max(8)
    @Builder.Default
    private String acctTypeCode = "57";


    /**
     * 结算周期
     * 结算周期默认传D+1
     */
    @Schema(description = "结算周期")
    @Max(8)
    @Builder.Default
    private String settlePeriod = "D+1";

    /**
     * 回调地址
     */
    @Schema(description = "回调地址")
    @Max(64)
    @CanEmpty
    private String retUrl;

    /**
     * 	费率信息集合
     * 	 费率信息集合默认传302、303、300，手续费比例内置两种：千米机构客户和拉卡拉机构客户，如客户不要求修改则直接默认传，如要求修改则在交付前修改
     */
    @Schema(description = "费率信息集合")
    @CanEmpty
    private Set<FeeData> feeData;

    /**
     * 附件列表
     */
    @Schema(description = "附件列表")
    @CanEmpty
    private Set<FileData> fileData;

    @NotBlank
    @Schema(description = "电子合同编号")
    private String contractNo;


}
