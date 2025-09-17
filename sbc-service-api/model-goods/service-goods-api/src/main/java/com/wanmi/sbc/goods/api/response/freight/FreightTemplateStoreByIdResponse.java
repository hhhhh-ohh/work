package com.wanmi.sbc.goods.api.response.freight;

import com.wanmi.sbc.goods.bean.vo.FreightTemplateStoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询店铺运费模板响应
 * Created by daiyitian on 2018/11/1.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class FreightTemplateStoreByIdResponse extends FreightTemplateStoreVO implements Serializable {
    private static final long serialVersionUID = -226764051551204987L;
}
