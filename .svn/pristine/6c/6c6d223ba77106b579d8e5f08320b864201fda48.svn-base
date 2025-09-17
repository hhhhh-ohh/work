package com.wanmi.sbc.goods.contract.request;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.brand.request.ContractBrandSaveRequest;
import com.wanmi.sbc.goods.cate.request.ContractCateSaveRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;

/**
 * Created by sunkun on 2017/11/2.
 */
@Data
public class ContractRequest extends BaseRequest {

    /**
     * 签约分类
     */
    private List<ContractCateSaveRequest> cateSaveRequests;

    /**
     * 签约品牌
     */
    private List<ContractBrandSaveRequest> brandSaveRequests;

    /**
     * 待删除平台类目id
     */
    private List<Long> delCateIds;

    /**
     * 待删除签约品牌id
     */
    private List<Long> delBrandIds;

    /**
     * 店铺id
     */
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
        if (this.brandSaveRequests != null && this.brandSaveRequests.size() > Constants.ZERO) {
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
