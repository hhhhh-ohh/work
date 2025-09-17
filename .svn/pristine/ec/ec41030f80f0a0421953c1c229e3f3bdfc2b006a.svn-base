package com.wanmi.ares.report.customer.model.root;

import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>客户维度统计视图</p>
 * Created by of628-wenzhi on 2017-09-27-下午6:20.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReport extends CustomerBaseReport {

    private static final long serialVersionUID = -8258671858408352513L;

    /**
     * 客户Id
     */
    private String customerId;

    /**
     * 名称
     */
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String name;

    /**
     * 账号
     */
    @SensitiveWordsField(signType = SignWordType.PHONE)
    private String account;

    /**
     * 店铺类型
     */
    private int storeType;

    /**
     * 注销状态
     */
    private int logOutStatus;
}
