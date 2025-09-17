package com.wanmi.sbc.setting.api.request.yunservice;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;
import com.wanmi.sbc.common.enums.ResourceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>云上传文件参数</p>
 *
 * @author yang
 * @date 2019-11-05 18:33:04
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class YunUploadResourceRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 上传素材分类id
     */
    @Schema(description = "上传素材分类id")
    private Long cateId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 素材类型
     */
    @Schema(description = "素材类型")
    private ResourceType resourceType;

    /**
     * 素材地址
     */
    @Schema(description = "素材地址")
    private String artworkUrl;

    /**
     * 素材KEY
     */
    @Schema(description = "素材KEY")
    private String resourceKey;

    /**
     * 素材名称
     */
    @Schema(description = "素材名称")
    private String resourceName;

    /**
     * 素材内容
     */
    @Schema(description = "素材内容")
    private byte[] content;

    /**
     * 素材排序
     */
    @Schema(description = "素材排序")
    private Integer sort;

}