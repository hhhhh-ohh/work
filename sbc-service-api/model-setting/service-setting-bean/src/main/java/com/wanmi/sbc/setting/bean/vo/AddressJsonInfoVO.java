package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>地址json信息VO</p>
 * @author chenyufei
 * @date 2019-05-10 14:39:59
 */
@Schema
@Data
public class AddressJsonInfoVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    private String id;

    /**
     * name
     */
    @Schema(description = "name")
    private String name;

    /**
     * 省
     */
    @Schema(description = "省")
    private String province;

    /**
     * 市
     */
    @Schema(description = "市")
    private String city;
}
