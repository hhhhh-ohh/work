package com.wanmi.ares.request;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 商品品牌
 * Created by sunkun on 2017/9/21.
 */
@Data
public class GoodsBrandQueryRequest extends BaseRequest {

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 批量ID查询
     */
    private List<String> ids;

    /**
     * 页码
     */
    private Long pageNum = 0L;

    /**
     * 页面大小
     */
    private Long pageSize = 10L;

    /**
     * 获取分页参数对象
     * @return
     */
    public PageRequest getPageable(){
        return PageRequest.of(pageNum.intValue(),pageSize.intValue());
    }
}
