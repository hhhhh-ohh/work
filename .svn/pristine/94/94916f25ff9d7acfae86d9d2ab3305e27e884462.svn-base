package com.wanmi.ares.request.flow;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.ares.enums.StoreSelectType;
import com.wanmi.sbc.common.util.Constants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Objects;

import static java.util.Objects.nonNull;

/***
 * 统计查询请求
 * @className FlowRequest
 * @author sunkun
 * @date 2017/10/13 15:59
 **/
@Schema
@Data
public class FlowRequest extends BaseRequest {

    @Schema(description = "公司ID", required = true)
    private String companyId = "0";

    @Schema(description = "开始时间")
    private String beginDate;

    @Schema(description = "结束时间")
    private String endDate;

    @Schema(description = "星期", required = true)
    private boolean isWeek = false;

    @Schema(description = "排序字段")
    private String sortName = "date";

    @Schema(description = "排序顺序", contentSchema = com.wanmi.ares.enums.SortOrder.class)
    private com.wanmi.ares.enums.SortOrder sortOrder =
            com.wanmi.ares.enums.SortOrder.DESC;

    private String selectType = "0";

    @Schema(description = "当前页数")
    private int pageNum = 1;

    @Schema(description = "每页条数")
    private int pageSize = 10;

    @Schema(description = "0全部，1商家，2门店")
    private int storeSelectType = -1;

    /** 主页标识 */
    private boolean homePage = true;

    /***
     * 填充请求的companyId
     * @param paramCompanyId  登录公司ID
     */
    public void populateRequestCompanyId(Long paramCompanyId){
        // 如果request中有值直接返回
        if (!"0".equals(companyId) && Objects.isNull(paramCompanyId)) {
            return;
        }

        if (nonNull(paramCompanyId)) {
            this.setCompanyId(paramCompanyId.toString());
        } else if(nonNull(this.getStoreSelectType())){
            if(this.getStoreSelectType() == 1){
                this.setCompanyId(StoreSelectType.SUPPLIER.getMockCompanyInfoId());
            }else if (this.getStoreSelectType() == 2){
                this.setCompanyId(StoreSelectType.O2O.getMockCompanyInfoId());
            }
        }
    }

    public String getSortName() {
        if(Constants.SORT_NAMES.contains(this.sortName)){
            return this.sortName;
        }
        return this.sortName = "date";
    }
}
