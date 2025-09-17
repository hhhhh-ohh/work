package com.wanmi.sbc.customer.api.response.wechatvideo;

import com.wanmi.sbc.customer.bean.vo.VideoUserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频号列表结果</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频号列表结果
     */
    @Schema(description = "视频号列表结果")
    private List<VideoUserVO> videoUserVOList;
}
