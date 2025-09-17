package com.wanmi.sbc.empower.api.response.ledger.lakala;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 拉卡拉卡bin查询返回参数
 */
@Schema
@Data
public class CardBinResponse extends LakalaBaseResponse{

    /**
     * 银行卡号
     */
    @Schema(description = "银行卡号")
    private String cardNo;


    /**
     * 开户行号
     */
    @Schema(description = "开户行号")
    private String bankCode;


    /**
     * 开户行名称
     */
    @Schema(description = "开户行名称")
    private String clearingBankCode;


    /**
     * 开户银行
     */
    @Schema(description = "开户行名称")
    private String bankName;

    /**
     * 银行卡类别
     */
    @Schema(description = "银行卡类别")
    private String cardType;


    /**
     * 卡种名称
     */
    @Schema(description = "卡种名称")
    private String cardName;

}
