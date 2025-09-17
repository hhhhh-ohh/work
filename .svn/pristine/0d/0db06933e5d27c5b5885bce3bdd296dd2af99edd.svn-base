package com.wanmi.sbc.setting.api.request.expresscompanythirdrel;

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
 * @description 平台与第三方平台物流公司映射关系查询
 * @author malianfeng
 * @date 2022/4/26 17:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpressCompanyThirdRelQueryRequest extends BaseQueryRequest {

    private static final long serialVersionUID = -5202589574723837167L;

    /**
     * 自增主键ID列表
     */
    @Schema(description = "自增主键ID列表")
    private List<Long> idList;

    /**
     * 自增主键ID
     */
    @Schema(description = "自增主键ID")
    private Long id;

    /**
     * 平台物流ID
     */
    @Schema(description = "平台物流ID")
    private Long expressCompanyId;

    /**
     * 平台物流ID列表
     */
    @Schema(description = "平台物流ID列表")
    private List<Long> expressCompanyIdList;

    /**
     * 第三方平台物流ID
     */
    @Schema(description = "第三方平台物流ID")
    private Long thirdExpressCompanyId;


    /**
     * 第三方平台物流ID列表
     */
    @Schema(description = "第三方平台物流ID列表")
    private List<Long> thirdExpressCompanyIdList;

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

