package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreModifyResponse extends StoreVO {
    private static final long serialVersionUID = 1L;
}
