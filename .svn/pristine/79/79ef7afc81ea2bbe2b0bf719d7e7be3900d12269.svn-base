package com.wanmi.sbc.empower.pay.model.root;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * 网关
 * Created by sunkun on 2017/8/3.
 */
@Entity
@Table(name = "wechat_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WechatConfig implements Serializable {

    private static final long serialVersionUID = 477937222178679525L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    /**
     * 商户id-boss端取默认值
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 微信AppId
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * 微信 - secret
     */
    @Column(name = "secret")
    private String secret;

    /**
     * 场景；H5微信设置（0：扫码 ，H5，微信网页-JSAPI），1：小程序（miniJSAPI），2：APP
     */
    @Column(name = "scene_type")
    private Integer sceneType;
}
