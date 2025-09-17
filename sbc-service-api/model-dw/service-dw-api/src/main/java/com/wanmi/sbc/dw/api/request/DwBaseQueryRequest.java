package com.wanmi.sbc.dw.api.request;

import com.wanmi.sbc.common.base.BaseRequest;


import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Schema
public class DwBaseQueryRequest extends BaseRequest {
    @Schema(description = "第几页")
    private Integer pageNum = 0;
    @Schema(description = "每页显示多少条")
    private Integer pageSize = 10;
    @Schema(description = "排序字段")
    private String sortColumn;
    @Schema(description = "排序规则 desc asc")
    private String sortRole;
    @Schema(description = "排序类型")
    private String sortType;
    @Schema(description = "多重排序, 内容：key:字段,value:desc或asc")
    private Map<String, String> sortMap = new LinkedHashMap();


    public void putSort(String column, String sort) {
        this.sortMap.put(column, sort);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DwBaseQueryRequest)) {
            return false;
        } else {
            DwBaseQueryRequest other = (DwBaseQueryRequest) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$pageNum = this.getPageNum();
                Object other$pageNum = other.getPageNum();
                if (this$pageNum == null) {
                    if (other$pageNum != null) {
                        return false;
                    }
                } else if (!this$pageNum.equals(other$pageNum)) {
                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                label71:
                {
                    Object this$sortColumn = this.getSortColumn();
                    Object other$sortColumn = other.getSortColumn();
                    if (this$sortColumn == null) {
                        if (other$sortColumn == null) {
                            break label71;
                        }
                    } else if (this$sortColumn.equals(other$sortColumn)) {
                        break label71;
                    }

                    return false;
                }

                label64:
                {
                    Object this$sortRole = this.getSortRole();
                    Object other$sortRole = other.getSortRole();
                    if (this$sortRole == null) {
                        if (other$sortRole == null) {
                            break label64;
                        }
                    } else if (this$sortRole.equals(other$sortRole)) {
                        break label64;
                    }

                    return false;
                }

                Object this$sortType = this.getSortType();
                Object other$sortType = other.getSortType();
                if (this$sortType == null) {
                    if (other$sortType != null) {
                        return false;
                    }
                } else if (!this$sortType.equals(other$sortType)) {
                    return false;
                }

                Object this$sortMap = this.getSortMap();
                Object other$sortMap = other.getSortMap();
                if (this$sortMap == null) {
                    if (other$sortMap != null) {
                        return false;
                    }
                } else if (!this$sortMap.equals(other$sortMap)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DwBaseQueryRequest;
    }

    public int hashCode() {
        int result = super.hashCode();
        Object $pageNum = this.getPageNum();
        result = result * 59 + ($pageNum == null ? 43 : $pageNum.hashCode());
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $sortColumn = this.getSortColumn();
        result = result * 59 + ($sortColumn == null ? 43 : $sortColumn.hashCode());
        Object $sortRole = this.getSortRole();
        result = result * 59 + ($sortRole == null ? 43 : $sortRole.hashCode());
        Object $sortType = this.getSortType();
        result = result * 59 + ($sortType == null ? 43 : $sortType.hashCode());
        Object $sortMap = this.getSortMap();
        result = result * 59 + ($sortMap == null ? 43 : $sortMap.hashCode());
        return result;
    }

    public DwBaseQueryRequest() {
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public String getSortColumn() {
        return this.sortColumn;
    }

    public String getSortRole() {
        return this.sortRole;
    }

    public String getSortType() {
        return this.sortType;
    }

    public Map<String, String> getSortMap() {
        return this.sortMap;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public void setSortRole(String sortRole) {
        this.sortRole = sortRole;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public void setSortMap(Map<String, String> sortMap) {
        this.sortMap = sortMap;
    }

    public String toString() {
        return "BaseQueryRequest(pageNum=" + this.getPageNum() + ", pageSize=" + this.getPageSize() + ", sortColumn=" + this.getSortColumn() + ", sortRole=" + this.getSortRole() + ", sortType=" + this.getSortType() + ", sortMap=" + this.getSortMap() + ")";
    }
}