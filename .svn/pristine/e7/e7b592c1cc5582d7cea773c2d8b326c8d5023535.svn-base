package com.wanmi.ares.report.flow.model.request;

import com.wanmi.ares.base.BaseRequest;
import com.wanmi.ares.enums.SortOrder;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by sunkun on 2017/9/26.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FlowReportRequest extends BaseRequest {

    /**
     * 开始时候
     * 20170925
     */
    @NotBlank
    private LocalDate beginDate;

    private String beginDateStr;

    /**
     * 结束时间
     * 20170926
     */
    @NotBlank
    private LocalDate endDate;

    private String endDateStr;

    /**
     * 统计时间
     */
    private LocalDate flowDate;

    /**
     * 商户id
     */
    private String companyId;

    private List<String> companyIds;

    /**
     * 商家类型，0全部1商家2门店
     */
    private int storeSelectType = -1;

    /**
     * 主页标识
     */
    private boolean homePage = true;

    private Boolean isWeek = Boolean.FALSE;

    /**
     * 排序字段 (默认日期排序)
     */
    private String sortName = "date";

    /**
     * 排序: 默认倒序
     */
    private SortOrder sortOrder = SortOrder.ASC;

    private int pageNum = 1;

    private int pageSize = 10;



    public Pageable getPageable() {
        return PageRequest.of(pageNum, pageSize);
    }


}
