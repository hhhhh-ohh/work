package com.wanmi.sbc.message.api.response.smssignfileinfo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.SmsSignFileInfoVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）短信签名文件信息信息response</p>
 * @author lvzhenwei
 * @date 2019-12-04 14:19:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignFileInfoByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 短信签名文件信息信息
     */
    @Schema(description = "短信签名文件信息信息")
    private SmsSignFileInfoVO smsSignFileInfoVO;
}
