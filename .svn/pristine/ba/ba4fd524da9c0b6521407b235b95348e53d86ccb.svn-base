package com.wanmi.ares.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 客户查询请求信息
 * Created by daiyitian on 2017/9/21.
 */
@Schema
@Data
public class CustomerQueryRequest extends BaseRequest {

    /**
     * 页码
     */
    @Schema(description = "页码")
    private Long pageNum = 0L;

    /**
     * 页面大小
     */
    @Schema(description = "页面大小")
    private Long pageSize = 10L;

    /**
     * 搜索关键字
     * 范围仅限客户名称或账号
     */
    @Schema(description = "搜索关键字")
    private String keyWord;

    /**
     * levelId查询
     */
    @Schema(description = "levelId查询")
    private Long levelId;

    @Schema(description = "等级id集合")
    private List<Long> levelIds;

    /**
     * employeeId查询
     */
    @Schema(description = "员工id查询")
    private String employeeId;

    /**
     * 客户Id
     */
    @Schema(description = "客户Id")
    private String customerId;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private String companyInfoId;

    /**
     * 获取分页参数对象
     * @return
     */
    @Schema(description = "获取分页参数对象")
    public PageRequest getPageable(){
        return PageRequest.of(pageNum.intValue(),pageSize.intValue());
    }
}
