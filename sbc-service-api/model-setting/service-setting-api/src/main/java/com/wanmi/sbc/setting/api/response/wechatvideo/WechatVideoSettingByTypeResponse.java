package com.wanmi.sbc.setting.api.response.wechatvideo;

import com.wanmi.sbc.setting.bean.vo.WechatVideoSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）视频直播带货应用设置信息response</p>
 * @author zhaiqiankun
 * @date 2022-04-11 20:18:02
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoSettingByTypeResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 视频直播带货应用设置信息
     */
    @Schema(description = "视频直播带货应用设置信息")
    private WechatVideoSettingVO videoGoodsSettingVO;
}
