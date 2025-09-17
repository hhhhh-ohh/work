package com.wanmi.ares.response;

import com.wanmi.ares.view.paymember.PayMemberGrowthRenewalView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className PayMemberGrowthRenewalResponse
 * @description
 * @date 2022/5/26 5:40 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayMemberGrowthRenewalResponse implements Serializable {

    private static final long serialVersionUID = 1132037315085983288L;
    /**
     * 会员新增数据
     */
    @Schema(description = "会员新增数据")
    private List<PayMemberGrowthRenewalView> growthViewList;

    private Integer pageNum;


    private Integer pageSize;

    private Integer total;
}
