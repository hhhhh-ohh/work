package com.wanmi.sbc.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 查询返回对象基类
 * Created by aqlu on 15/12/2.
 */
@Schema
@Data
@AllArgsConstructor
@Builder
public class BaseQueryResponse<T> implements Serializable {

    @Schema(description = "总条数")
    private Long total;

    @Schema(description = "查询到的数据")
    private List<T> data;

    @Schema(description = "每页展示多少条")
    private Integer pageSize;

    @Schema(description = "第几页")
    private Integer pageNum;

    public BaseQueryResponse() {
    }

    public BaseQueryResponse(Page<T> page) {
        if (null != page) {
            total = page.getTotalElements();
            data = page.getContent();
            pageSize = page.getSize();
            pageNum = page.getNumber();
        }
    }
}
