package com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig;

import com.wanmi.sbc.marketing.bean.vo.NewcomerPurchaseConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>新人专享设置修改结果</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseConfigModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的新人专享设置信息
     */
    @Schema(description = "已修改的新人专享设置信息")
    private NewcomerPurchaseConfigVO newcomerPurchaseConfigVO;
}
