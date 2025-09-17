package com.wanmi.sbc.elastic.bean.vo.goods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.goods.bean.vo.GoodsCateShenceBurialSiteVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品分类实体类
 * Created by dyt on 2017/4/11.
 */
@Data
@Schema
public class GoodsCateNestVO extends BasicResponse {

    /**
     * 分类编号
     */
    @Schema(description = "分类编号")
    private Long cateId;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    private String cateName;

    /**
     * 拼音
     */
    @Schema(description = "拼音")
    private String pinYin;

    /**
     * 简拼
     */
    @Schema(description = "简拼")
    private String sPinYin;

    /**
     * 一二级分类信息
     */
    @Schema(description = "一二级分类信息")
    private GoodsCateShenceBurialSiteVO goodsCateShenceBurialSite;
}
