package com.wanmi.sbc.empower.api.request.map;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className GetDistinctRequest
 * @description 查询行政区列表
 * @author    xufeng
 * @date      2022/10/10 11:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDistinctRequest implements Serializable {

    @Schema(description = "请求服务权限标识", hidden = true)
    private String key;

    /**
     * 查询关键字, 规则： 多个关键字用“|”分割
     *
     * <p>若不指定city，并且搜索的为泛词（例如“美食”）的情况下，返回的内容为城市列表以及此城市内有多少结果符合要求。
     */
    @Schema(description = "查询关键字，多个关键字用“|”分割(查询周边poi时可以不传)", required =
            true)
    private String keywords;

    @Schema(description = "子级行政区", hidden = true)
    private Integer subdistrict;

    /**
     * 每页记录数据
     */
    @Schema(description = "每页记录数据", hidden = true)
    private Integer offset = 10;

    /**
     * 当前页数
     */
    @Schema(description = "当前页数", hidden = true)
    private Integer page = 1;

    /**
     * 此项控制行政区信息中返回行政区边界坐标点； 可选值：base、all;
     * base:不返回行政区边界坐标点；
     * all:只返回当前查询district的边界值，不返回子节点的边界值；
     * 目前不能返回乡镇/街道级别的边界值
     */
    @Schema(description = "返回结果控制", hidden = true)
    private String extensions;

}
