package com.wanmi.sbc.empower.api.response.sms;

import com.wanmi.sbc.empower.bean.vo.SmsSettingVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>短信配置列表结果</p>
 * @author lvzhenwei
 * @date 2019-12-03 15:15:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSettingListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 短信配置列表结果
     */
    @Schema(description = "短信配置列表结果")
    private List<SmsSettingVO> smsSettingVOList;
}
