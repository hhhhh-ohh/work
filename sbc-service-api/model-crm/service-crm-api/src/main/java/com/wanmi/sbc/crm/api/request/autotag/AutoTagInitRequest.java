package com.wanmi.sbc.crm.api.request.autotag;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.*;

/**
 * <p>批量初始化标签请求参数</p>
 * @author dyt
 * @date 2020-03-11 14:47:32
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutoTagInitRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量选择-标签ids
	 */
	@Schema(description = "批量选择-系统标签ids")
	@NotEmpty
	private List<Long> tagIds;

    /**
     * 创建人
     */
    @Schema(description = "创建人", hidden = true)
    private String createPerson;
}
