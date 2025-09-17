package com.wanmi.sbc.setting.pagemanage.model.root;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * 商品推广
 * Created by dyt on 2020/4/17.
 */
@Data
public class GoodsInfoExtend implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    private String goodsInfoId;


    private String goodsId;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 页面所属平台
     */
    private String platform;

    /**
     * 背景图
     */
    private String backgroundPic;

    /**
     * 使用类型  0:小程序 1:二维码
     */
    private Integer useType;

    /**
     * 小程序码
     */
    private String miniProgramQrCode;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 二维码
     */
    private String qrCode;

    /**
     * 访问地址
     */
    private String url;

    /**
     * 来源
     */
    private List<String> sources;
}
