package com.wanmi.sbc.customer.api.response.wechatvideo;

import com.wanmi.sbc.customer.bean.vo.VideoUserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）视频号信息response</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频号信息
     */
    @Schema(description = "视频号信息")
    private VideoUserVO videoUserVO;
}
