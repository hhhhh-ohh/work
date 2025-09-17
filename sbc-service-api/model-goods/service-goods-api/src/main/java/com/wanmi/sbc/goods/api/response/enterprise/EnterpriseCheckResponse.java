package com.wanmi.sbc.goods.api.response.enterprise;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: sbc-micro-service
 * @description:
 * @create: 2019-03-11 17:18
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseCheckResponse extends BasicResponse {

    /**
     * 检查的标识
     */
    private Boolean checkFlag;
}
