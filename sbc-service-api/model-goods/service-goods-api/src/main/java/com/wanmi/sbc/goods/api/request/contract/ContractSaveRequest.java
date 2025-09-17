package com.wanmi.sbc.goods.api.request.contract;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.goods.bean.dto.ContractBrandSaveDTO;
import com.wanmi.sbc.goods.bean.dto.ContractCateSaveDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * 签约信息保存请求结构
 * Created by daiyitian on 2017/11/2.
 */
@Schema
@Data
public class ContractSaveRequest extends BaseRequest {

    private static final long serialVersionUID = -5341524577216456499L;

    /**
     * 签约分类
     */
    @Schema(description = "签约分类")
    private List<ContractCateSaveDTO> cateSaveRequests;

    /**
     * 签约品牌
     */
    @Schema(description = "签约品牌")
    private List<ContractBrandSaveDTO> brandSaveRequests;

    /**
     * 待删除平台类目id
     */
    @Schema(description = "待删除平台类目id")
    private List<Long> delCateIds;

    /**
     * 待删除签约品牌id
     */
    @Schema(description = "待删除签约品牌id")
    private List<Long> delBrandIds;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    @Override
    public void checkParam() {
        if (this.cateSaveRequests != null && this.cateSaveRequests.size() > 0) {
            this.cateSaveRequests.forEach(info -> {
                if (StringUtils.isNotBlank(info.getQualificationPics()) && info.getQualificationPics().split(",").length > Constants.TEN) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                if (info.getCateId() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            });
        }
        if (this.brandSaveRequests != null && this.brandSaveRequests.size() > 0) {
            this.brandSaveRequests.forEach(info -> {
                if (StringUtils.isNotBlank(info.getAuthorizePic()) && info.getAuthorizePic().split(",").length > Constants.TWO) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030009);
                }
                if (info.getBrandId() == null) {
                    Validate.notBlank(info.getName(), ValidateUtil.BLANK_EX_MESSAGE, "name");
                    Validate.notBlank(info.getLogo(), ValidateUtil.BLANK_EX_MESSAGE, "logo");
                }
            });
        }
    }
}
