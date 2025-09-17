package com.wanmi.sbc.goods.cate.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 签约分类更新请求
 * @author wangchao
 */
@Data
public class ContractCateAuditSaveRequest extends BaseRequest {

    private static final long serialVersionUID = -258002602482876633L;

    /**
     * 主键
     */
    private Long contractCateId;

    /**
     * 店铺主键
     */
    private Long storeId;

    /**
     * 商品分类标识
     */
    private Long cateId;

    /**
     * 分类扣率
     */
    private BigDecimal cateRate;

    /**
     * 资质图片路径
     */
    private String qualificationPics;

    /**
     * 是否需要删除标识
     */
    private DeleteFlag deleteFlag;
}
