package com.wanmi.ares.source.model.root;

import com.wanmi.ares.source.model.root.base.BaseData;
import com.wanmi.sbc.common.enums.StoreType;
import lombok.*;

/**
 * 商家基础信息
 * Created by sunkun on 2017/9/19.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CompanyInfo extends BaseData{

    private static final long serialVersionUID = 5936009945934541219L;
    /**
     * 名称
     */
    private String name;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司编码
     */
    private String companyCode;

    /**
     * 公司id
     */
    private int companyId;

    /**
     * 商家类型
     */
    private int storeType;

    /**
     * 省
     */
    private Long provinceId;

    /**
     * 市
     */
    private Long cityId;

    /**
     * 区
     */
    private Long areaId;

    /**
     * 详细地址
     */
    private String detailAddress;

}
