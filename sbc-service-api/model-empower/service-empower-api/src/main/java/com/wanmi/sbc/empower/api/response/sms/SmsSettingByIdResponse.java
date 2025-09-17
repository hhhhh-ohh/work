package com.wanmi.sbc.empower.api.response.sms;

import com.wanmi.sbc.empower.bean.vo.SmsSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询任意（包含已删除）短信配置信息response</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:15:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSettingByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 短信配置信息
     */
    @Schema(description = "短信配置信息")
    private SmsSettingVO smsSettingVO;
}
