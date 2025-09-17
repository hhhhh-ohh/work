package com.wanmi.sbc.empower.api.response.customerservice;

import com.wanmi.sbc.empower.bean.vo.CustomerServiceSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）在线客服配置信息response</p>
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerServiceSettingByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 在线客服配置信息
     */
    @Schema(description = "在线客服配置信息")
    private CustomerServiceSettingVO customerServiceSettingVO;
}
