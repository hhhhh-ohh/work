package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName RedisRequest
 * @Description TODO
 * @Author qiyuanzhao
 * @Date 2022/5/26 16:25
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisRequest extends BaseRequest {

    /**
     * redis key
     **/
    @Schema(description = "key")
    @NotBlank
    private String key;

    /**
     * redis values
     **/
    @Schema(description = "values")
    @NotEmpty
    private List<String> values;
}
