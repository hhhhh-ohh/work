package com.wanmi.sbc.message.api.response.smssenddetail;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.message.bean.vo.SmsSendDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>短信发送列表结果</p>
 * @author zgl
 * @date 2019-12-03 15:43:37
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendDetailListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 短信发送列表结果
     */
    @Schema(description = "短信发送列表结果")
    private List<SmsSendDetailVO> smsSendDetailVOList;
}
