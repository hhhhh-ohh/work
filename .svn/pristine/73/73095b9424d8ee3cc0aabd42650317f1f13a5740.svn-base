package com.wanmi.sbc.elastic.api.request.storeInformation;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 16:02 2020/12/11
 * @Description: Es查询商家列表Request
 */
@Schema
@Data
public class EsCompanyPageRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 8071214608591987184L;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 商家账号
     */
    @Schema(description = "商家账号")
    private String accountName;

    /**
     * 店铺名称或者商家编号(新增优惠券选择门店时所需字段)
     */
    @Schema(description = "商家账号")
    private String companyNameAndCode;

    /**
     * 是否分页
     */
    @Schema(description = "是否分页")
    private Integer isPage = 0;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String companyCode;

    /**
     * 签约结束日期
     */
    @Schema(description = "签约结束日期")
    private String contractEndDate;

    /**
     * 账户状态  -1:全部 0：启用   1：禁用
     */
    @Schema(description = "账户状态(-1:全部,0:启用,1:禁用)")
    private Integer accountState;

    /**
     * 店铺状态 -1：全部,0:开启,1:关店,2:过期
     */
    @Schema(description = "店铺状态(-1:全部,0:开启,1:关店,2:过期)")
    private Integer storeState;

    /**
     * 审核状态 -1全部 ,0:待审核,1:已审核,2:审核未通过
     */
    @Schema(description = "审核状态(-1:全部,0:待审核,1:已审核,2:审核未通过)")
    private Integer auditState;

    /**
     * 商家删除状态
     */
    @Schema(description = "商家删除状态")
    private DeleteFlag deleteFlag;

    /**
     * 商家类型 0、供应商 1、商家
     */
    @Schema(description = "商家类型 0、供应商 1、商家")
    private StoreType storeType;

    /**
     * 是否是主账号
     */
    @Schema(description = "是否是主账号", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isMasterAccount;

    /**
     * 商家类型 0、供应商 1、商家
     */
    @Schema(description = "商家类型 0、供应商 1、商家")
    private List<Long> companyInfoIds;

    /**
     * 多个店铺ID
     */
    @Schema(description = "多个店铺ID")
    private List<Long> storeIds;


    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型 0、平台自营 1、第三方商家")
    private Integer companyType;


    /**
     * 商家名称或者商家编号(新增优惠券选择门店时所需字段)
     */
    @Schema(description = "商家账号")
    private String likeNameAndCode;
}
