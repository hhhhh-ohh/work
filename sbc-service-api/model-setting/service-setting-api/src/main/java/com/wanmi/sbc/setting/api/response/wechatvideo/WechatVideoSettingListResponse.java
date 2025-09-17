package com.wanmi.sbc.setting.api.response.wechatvideo;

import com.wanmi.sbc.setting.bean.vo.WechatVideoSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频直播带货应用设置列表结果</p>
 * @author zhaiqiankun
 * @date 2022-04-11 20:18:02
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频直播带货应用设置列表结果
     */
    @Schema(description = "视频直播带货应用设置列表结果")
    private List<WechatVideoSettingVO> videoGoodsSettingVOList;
}
