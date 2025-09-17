package com.wanmi.sbc.marketing.api.request.appointmentsale;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class AppointmentForImmediateBuyQueryRequest extends BaseQueryRequest {

	@Schema(description = "<预约活动Id, skuId>")
	Map<Long, List<String>> appointmentSaleIdMap;
}