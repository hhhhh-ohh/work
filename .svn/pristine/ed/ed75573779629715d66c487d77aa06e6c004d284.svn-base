package com.wanmi.sbc.message.api.request.smssign;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @ClassName SynchronizeSmsSignByNamesRequest
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2019/12/11 17:08
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncSmsSignByNamesRequest extends SmsBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 批量同步短信平台签名名称--签名名称list
     */
    @Schema(description = "批量同步短信平台签名名称--签名名称list")
    private List<String> signNameList;
}
