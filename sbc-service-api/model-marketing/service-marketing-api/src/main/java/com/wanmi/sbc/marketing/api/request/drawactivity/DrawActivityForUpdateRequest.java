package com.wanmi.sbc.marketing.api.request.drawactivity;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @author: qiyong
 * @create: 2021/4/20 16:42
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrawActivityForUpdateRequest implements Serializable {

    private static final long serialVersionUID = 7756207003589453401L;
    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private Long id;
}
