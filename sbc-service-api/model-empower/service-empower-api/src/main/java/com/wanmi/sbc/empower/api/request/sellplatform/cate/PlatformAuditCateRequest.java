package com.wanmi.sbc.empower.api.request.sellplatform.cate;

import com.wanmi.sbc.empower.api.request.sellplatform.ThirdBaseRequest;
import com.wanmi.sbc.empower.bean.vo.sellplatform.cate.PlatformAuditCateVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
*
 * @description  类目审核
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class PlatformAuditCateRequest extends ThirdBaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     *  营业执照或组织机构代码证，图片url
     */
    @NotEmpty
    @Schema(description = "营业执照或组织机构代码证，图片url")
    private List<String> license;

    /**
     * 类目信息
     */
    @Schema(description = "类目信息")
    @NotNull
    @Valid
    private PlatformAuditCateVO category_info;

    /**
     * 商品使用场景,1:视频号，3:订单中
     */
    @NotNull
    @Schema(description = "商品使用场景,1:视频号，3:订单中")
    private List<Integer> scene_group_list;
}
