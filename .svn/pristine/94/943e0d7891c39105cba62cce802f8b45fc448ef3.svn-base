package com.wanmi.ares.response;

import com.wanmi.ares.view.paymember.PayMemberGrowthNewView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className PayMemberGrowthResponse
 * @description
 * @date 2022/5/26 3:49 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PayMemberGrowthNewResponse implements Serializable {
    private static final long serialVersionUID = 6777747009192739196L;

    /**
     * 会员新增数据
     */
    @Schema(description = "会员新增数据")
    private List<PayMemberGrowthNewView> growthViewList;

    private Integer pageNum;


    private Integer pageSize;

    private Integer total;
}
