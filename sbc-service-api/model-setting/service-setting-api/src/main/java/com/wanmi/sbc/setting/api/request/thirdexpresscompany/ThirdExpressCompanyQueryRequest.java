package com.wanmi.sbc.setting.api.request.thirdexpresscompany;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SellPlatformType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 第三方平台物流公司保存请求
 * @author malianfeng
 * @date 2022/4/26 17:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThirdExpressCompanyQueryRequest extends BaseQueryRequest {

    /**
     * 主键ID列表
     */
    @Schema(description = "主键ID List")
    private List<Long> idList;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 物流公司名称
     */
    @Schema(description = "物流公司名称")
    private String expressName;

    /**
     * 物流公司代码
     */
    @Schema(description = "物流公司代码")
    private String expressCode;

    /**
     * 第三方代销平台
     */
    @Schema(description = "第三方代销平台")
    private SellPlatformType sellPlatformType;

    /**
     * 删除标志 默认0：未删除 1：删除
     */
    @Schema(description = "删除标志 默认0：未删除 1：删除")
    private DeleteFlag delFlag;
}

