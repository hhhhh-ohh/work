package com.wanmi.sbc.customer.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询回调需要补偿的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class LedgerCallbackVO {

    /**
     * 电子合同申请id集合
     */
    @Schema(description = "电子合同申请id集合")
    private List<String> ecApplyIdList;

    @Schema(description = "电子合同EC003申请id集合")
    private List<String> ec003ApplyIdList;

    /**
     * 进件id集合
     */
    @Schema(description = "进件id集合")
    private List<String> contractIdList;

    /**
     * 拉卡拉内部商户号集合
     */
    @Schema(description = "拉卡拉内部商户号集合")
    private List<String> merInnerNoList;


    /**
     * 分账绑定申请id集合
     */
    @Schema(description = "分账绑定申请id集合")
    private List<String> bindApplyIdList;
}
