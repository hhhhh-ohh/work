package com.wanmi.sbc.customer.api.response.wechatvideo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>视频号数量结果</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserCountResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据数量
     */
    @Schema(description = "数据数量")
    private Long count;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Integer pageCount;

    /**
     * 是否存在数据
     */
    @Schema(description = "是否存在数据")
    private Boolean exit;
}
