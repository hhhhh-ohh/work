package com.wanmi.sbc.message.api.request.pushsend;

import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除会员推送信息请求参数</p>
 * @author Bob
 * @date 2020-01-08 17:15:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushSendDelByIdListRequest extends SmsBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-idList
	 */
	@Schema(description = "批量删除-idList")
	@NotEmpty
	private List<Long> idList;
}
