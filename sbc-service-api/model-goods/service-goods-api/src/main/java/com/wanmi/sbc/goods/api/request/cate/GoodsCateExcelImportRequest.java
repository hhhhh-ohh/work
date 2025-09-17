package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: songhanlin
 * @Date: Created In 10:17 2018-12-18
 * @Description: 商品分类excel导入请求Request
 */
@Schema
@Data
public class GoodsCateExcelImportRequest extends BaseRequest {

    private static final long serialVersionUID = 5802763416338076956L;

    @Schema(description = "类目列表")
    private List<GoodsCateVO> goodsCateList;

}
