package com.wanmi.sbc.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: songhanlin
 * @Date: Created In 16:02 2021/3/9
 * @Description: Es初始化类
 */
@Schema
@Data
public class EsInitRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 第几页
     */
    @Schema(description = "初始化开始页码")
    private Integer pageNum = 0;

    /**
     * 每页显示多少条
     */
    @Schema(description = "每批初始化数量")
    private Integer pageSize = 1000;
}
