package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <Description> <br>
 *
 * @author hejiawen<br>
 * @version 1.0<br>
 * @taskId <br>
 * @createTime 2018-09-12 10:33 <br>
 * @see com.wanmi.sbc.customer.api.response.store.vo <br>
 * @since V1.0<br>
 */
@Schema
@Data
public class StoreCustomerVO extends BasicResponse {


    private static final long serialVersionUID = 1L;

    /**
     * 会员ID
     */
    @Schema(description = "会员ID")
    private String customerId;

    /**
     * 账户
     */
    @Schema(description = "账户")
    private String customerAccount;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String customerName;

    /**
     * 客户等级ID
     */
    @Schema(description = "客户等级ID")
    private Long customerLevelId;

    /**
     * 客户等级名称
     */
    @Schema(description = "客户等级名称")
    private String customerLevelName;

    /**
     * 从数据库实体转换为返回前端的用户信息
     * (字段顺序不可变)
     */
    public static StoreCustomerVO convertFromNativeSQLResult(Object result) {
        StoreCustomerVO response = new StoreCustomerVO();
        response.setCustomerId((String) ((Object[]) result)[0]);
        response.setCustomerAccount((String) ((Object[]) result)[1]);
        response.setCustomerName((String) ((Object[]) result)[2]);
        if (((Object[]) result)[3] != null) {
            response.setCustomerLevelId(((Long) ((Object[]) result)[3]));
        }
        response.setCustomerLevelName((String) ((Object[]) result)[4]);
        return response;
    }
}
