package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.StringUtil;
import com.wanmi.sbc.customer.bean.enums.StoreResponseState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 店铺基本信息
 * (安全考虑只保留必要信息,隐藏前端会员无需知道的信息)
 * Created by bail on 2017/11/29.
 */
@Schema
@Data
public class StoreBaseInfoResponse extends BasicResponse {
    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 店铺店招
     */
    @Schema(description = "店铺店招")
    private String storeSign;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型",contentSchema = com.wanmi.sbc.customer.bean.enums.CompanyType.class)
    private Integer companyType;

    /**
     * 联系人名字
     */
    @Schema(description = "联系人名字")
    private String contactPerson;

    /**
     * 联系方式
     */
    @Schema(description = "联系方式")
    private String contactMobile;

    /**
     * 联系邮箱
     */
    @Schema(description = "联系邮箱")
    private String contactEmail;

    /**
     * 省份
     */
    @Schema(description = "省份")
    private Long provinceId;

    /**
     * 地市
     */
    @Schema(description = "地市")
    private Long cityId;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 扁平化商品信息，一对多关系
     */
    @Schema(description = "扁平化商品信息")
    private List<String> skuIds = new ArrayList<>();

    /**
     * 店铺响应状态
     */
    @Schema(description = "店铺响应状态")
    private StoreResponseState storeResponseState;

    /**
     * 店铺是否被关注
     */
    @Schema(description = "店铺是否被关注")
    private Boolean isFollowed = Boolean.FALSE;

    /**
     * 省份
     */
    @Schema(description = "省份")
    private String provinceName;

    /**
     * 地市
     */
    @Schema(description = "地市")
    private String cityName;

    /**
     * 区
     */
    @Schema(description = "区")
    private Long areaId;

    /**
     * 区名
     */
    @Schema(description = "区名")
    private String areaName;

    public StoreBaseInfoResponse convertFromEntity(StoreVO store){
        this.setStoreId(store.getStoreId());
        if(store.getCompanyInfo() != null) {
            this.setCompanyInfoId(store.getCompanyInfo().getCompanyInfoId());
        }
        this.setStoreName(store.getStoreName());
        this.setCompanyType(store.getCompanyType()!=null?store.getCompanyType().toValue():null);
        this.setStoreLogo(store.getStoreLogo());
        this.setStoreSign(store.getStoreSign());
        this.setContactPerson(store.getContactPerson());
        this.setContactMobile(store.getContactMobile());
        this.setContactEmail(store.getContactEmail());
        this.setStoreResponseState(StoreResponseState.OPENING);
        this.setSupplierName(store.getSupplierName());
        this.setCityId(store.getCityId());
        this.setProvinceId(store.getProvinceId());
        this.setAreaId(store.getAreaId());
        if (Objects.equals(DeleteFlag.YES, store.getDelFlag())){
            this.setStoreResponseState(StoreResponseState.NONEXISTENT);
        }else if(StoreState.CLOSED == store.getStoreState()){
            this.setStoreResponseState(StoreResponseState.CLOSED);
        }else if(LocalDateTime.now().isBefore(store.getContractStartDate()) || LocalDateTime.now().isAfter(store.getContractEndDate())){
            this.setStoreResponseState(StoreResponseState.EXPIRE);
        }

        return this;
    }


    /**
     * 从数据库实体转换为返回前端的用户信息
     * (字段顺序不可变)
     */
    public StoreBaseInfoResponse convertFromNativeSQLResult(Object result) {
        Object[] results = StringUtil.cast(result, Object[].class);
        if (results == null || results.length < 1) {
            return this;
        }
        this.setStoreId(StringUtil.cast(results, 0, Long.class));
        this.setStoreName(StringUtil.cast(results, 1, String.class));
        Byte companyTypeTemp = StringUtil.cast(results, 2, Byte.class);
        this.setCompanyType(companyTypeTemp != null ? companyTypeTemp.intValue() : null);
        this.setStoreLogo(StringUtil.cast(results, 3, String.class));
        this.setStoreSign(StringUtil.cast(results, 4, String.class));
        this.setContactPerson(StringUtil.cast(results, 5, String.class));
        this.setContactMobile(StringUtil.cast(results, 6, String.class));
        this.setContactEmail(StringUtil.cast(results, 7, String.class));
        this.setStoreResponseState(StoreResponseState.OPENING);
        Byte delFlag = StringUtil.cast(results, 8, Byte.class);
        Byte storeState = StringUtil.cast(results, 9, Byte.class);
        Timestamp startDate = StringUtil.cast(results, 10, Timestamp.class);
        Timestamp endDate = StringUtil.cast(results, 11, Timestamp.class);
        BigInteger cityIdTemp = StringUtil.cast(results, 12, BigInteger.class);
        this.setCityId(cityIdTemp != null ? cityIdTemp.longValue() : null);
        this.setSupplierName(StringUtil.cast(results, 13, String.class));
        BigInteger provinceIdTemp = StringUtil.cast(results, 14, BigInteger.class);
        this.setProvinceId(provinceIdTemp != null ? provinceIdTemp.longValue() : null);
        BigInteger areaIdTemp = StringUtil.cast(results, 15, BigInteger.class);
        this.setAreaId(areaIdTemp != null ? areaIdTemp.longValue() : null);

        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        if (delFlag == null || Objects.equals(delFlag, Byte.valueOf(String.valueOf(DeleteFlag.YES.toValue())))) {
            this.setStoreResponseState(StoreResponseState.NONEXISTENT);
        } else if (storeState == null || Objects.equals(Byte.valueOf(String.valueOf(StoreState.CLOSED.toValue())),
                storeState)) {
            this.setStoreResponseState(StoreResponseState.CLOSED);
        } else if (startDate == null || endDate == null || now.compareTo(startDate) < 0 || now.compareTo(endDate) > 0) {
            this.setStoreResponseState(StoreResponseState.EXPIRE);
        }
        return this;
    }
}
