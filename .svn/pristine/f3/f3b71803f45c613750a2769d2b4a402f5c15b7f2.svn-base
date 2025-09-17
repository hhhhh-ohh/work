package com.wanmi.sbc.setting.api.request.operatedatalog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>系统日志同步参数</p>
 * @author guanfl
 * @date 2020-04-21 14:57:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperateDataLogSynRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商家商品id
	 */
	@Schema(description = "商家商品id")
	private String supplierGoodsId;

	/**
	 * 供应商商品id
	 */
	@Schema(description = "供应商商品id")
	private String providerGoodsId;

}