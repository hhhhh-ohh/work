package com.wanmi.sbc.customer.api.response.wechatvideo;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.VideoUserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频号分页结果</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频号分页结果
     */
    @Schema(description = "视频号分页结果")
    private MicroServicePage<VideoUserVO> videoUserVOPage;
}
