package com.wanmi.sbc.goods.api.request.freight;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.goods.bean.dto.FreightTemplateGoodsExpressSaveDTO;
import com.wanmi.sbc.goods.bean.dto.FreightTemplateGoodsFreeSaveDTO;
import com.wanmi.sbc.goods.bean.enums.ConditionType;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.goods.bean.enums.ValuationType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 单品运费模板保存数据结构
 * Created by daiyitian on 2018/11/1.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class FreightTemplateGoodsSaveRequest extends BaseRequest {


    private static final long serialVersionUID = 3956560016021462113L;

    /**
     * 运费模板id
     */
    @Schema(description = "运费模板id")
    private Long freightTempId;

    /**
     * 运费模板名称
     */
    @Schema(description = "运费模板名称")
    private String freightTempName;

    /**
     * 发货地-省份
     */
    @Schema(description = "发货地-省份")
    @NotNull
    private Long provinceId;

    /**
     * 发货地-地市
     */
    @Schema(description = "发货地-地市")
    @NotNull
    private Long cityId;

    /**
     * 发货地-区镇
     */
    @Schema(description = "发货地-区镇")
    @NotNull
    private Long areaId;

    /**
     * 发货地-街道
     */
    @Schema(description = "发货地-街道")
    private Long streetId;

    /**
     * 是否包邮(0:不包邮,1:包邮)
     */
    @Schema(description = "是否包邮，0:不包邮,1:包邮")
    @NotNull
    private DefaultFlag freightFreeFlag;

    /**
     * 计价方式(0:按件数,1:按重量,2:按体积) {@link ValuationType}
     */
    @Schema(description = "计价方式，0:按件数,1:按重量,2:按体积")
    @NotNull
    private ValuationType valuationType;

    /**
     * 是否默认(0:否,1:是)
     */
    @Schema(description = "是否默认，0:否,1:是")
    @NotNull
    private DefaultFlag defaultFlag;

    /**
     * 运送方式(1:快递配送) {@link DeliverWay}
     */
    @Schema(description = "运送方式，0: 其他, 1: 快递")
    private DeliverWay deliverWay = DeliverWay.EXPRESS;

    /**
     * 是否指定条件包邮(0:不指定,1:指定)
     */
    @Schema(description = "是否指定条件包邮，0:不指定,1:指定")
    private DefaultFlag specifyTermFlag = DefaultFlag.NO;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 单品运费模板快递运送 {@link FreightTemplateGoodsExpressSaveDTO}
     */
    @Schema(description = "单品运费模板快递运送")
    @NotNull
    @Size(min = 1)
    private List<FreightTemplateGoodsExpressSaveDTO> freightTemplateGoodsExpressSaveRequests;

    /**
     * 单品运费模板指定包邮条件 {@link FreightTemplateGoodsFreeSaveDTO}
     */
    @Schema(description = "单品运费模板指定包邮条件")
    private List<FreightTemplateGoodsFreeSaveDTO> freightTemplateGoodsFreeSaveRequests = new ArrayList<>();

    @Override
    public void checkParam() {
        if (this.defaultFlag == DefaultFlag.NO) {
            Validate.notBlank(this.freightTempName, ValidateUtil.BLANK_EX_MESSAGE, "freightTempName");
        } else {
            this.freightTempName = "";
        }
        // 单品运费模板快递运送 参数校验
        freightTemplateGoodsExpressSaveRequests.forEach(info -> {
            if(info.getFreightPlusNum() == null || info.getFreightStartNum() == null || info.getFreightPlusPrice() == null || info.getFreightStartPrice() == null || info.getDefaultFlag() == null){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (info.getDefaultFlag() == DefaultFlag.NO) {
                Validate.notNull(info.getDestinationArea(), ValidateUtil.NULL_EX_MESSAGE, "destinationArea");
                Validate.notNull(info.getDestinationAreaName(), ValidateUtil.NULL_EX_MESSAGE, "destinationAreaName");
                if (info.getDestinationArea().length == 0 || info.getDestinationAreaName().length == 0 || info.getDestinationArea().length != info.getDestinationAreaName().length) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
            boolean flag = false;
            switch (this.valuationType) {
                case NUMBER:
                    if (info.getFreightPlusNum().compareTo(new BigDecimal(1)) < 0 || info.getFreightPlusNum().compareTo(new BigDecimal("9999")) > 0 ||
                            info.getFreightStartNum().compareTo(new BigDecimal(1)) < 0 || info.getFreightStartNum().compareTo(new BigDecimal("9999")) > 0) {
                        flag = true;
                    }
                    if(info.getFreightStartPrice().compareTo(BigDecimal.ZERO) < 0 || info.getFreightStartPrice().compareTo(new BigDecimal("9999.99")) > 0 ||
                            info.getFreightPlusPrice().compareTo(BigDecimal.ZERO) < 0 || info.getFreightPlusPrice().compareTo(new BigDecimal("9999.99")) > 0){
                        flag = true;
                    }
                    break;
                case WEIGHT:
                    if(info.getFreightPlusNum().compareTo(new BigDecimal("0.1")) < 0 || info.getFreightPlusNum().compareTo(new BigDecimal("9999.9")) > 0 ||
                            info.getFreightStartNum().compareTo(new BigDecimal("0.1")) < 0 || info.getFreightStartNum().compareTo(new BigDecimal("9999.9")) > 0){
                        flag = true;
                    }
                    if(info.getFreightStartPrice().compareTo(BigDecimal.ZERO) < 0 || info.getFreightStartPrice().compareTo(new BigDecimal("9999.99")) > 0 ||
                            info.getFreightPlusPrice().compareTo(BigDecimal.ZERO) < 0 || info.getFreightPlusPrice().compareTo(new BigDecimal("9999.99")) > 0){
                        flag = true;
                    }
                    break;
                default:
                    if(info.getFreightStartNum().compareTo(new BigDecimal("0.1")) < 0 || info.getFreightStartNum().compareTo(new BigDecimal("999.9")) > 0 ||
                            info.getFreightPlusNum().compareTo(new BigDecimal("0.1")) < 0 || info.getFreightPlusNum().compareTo(new BigDecimal("999.9")) > 0){
                        flag = true;
                    }
                    if(info.getFreightStartPrice().compareTo(BigDecimal.ZERO) < 0 || info.getFreightStartPrice().compareTo(new BigDecimal("9999.99")) > 0 ||
                            info.getFreightPlusPrice().compareTo(BigDecimal.ZERO) < 0 || info.getFreightPlusPrice().compareTo(new BigDecimal("9999.99")) > 0){
                        flag = true;
                    }
                    break;
            }
            if (flag) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        });

        if(CollectionUtils.isNotEmpty(freightTemplateGoodsFreeSaveRequests)){
            freightTemplateGoodsFreeSaveRequests.forEach(info->{
                if(info.getValuationType() == null || info.getConditionType() == null || info.getDestinationArea() == null || info.getDestinationAreaName() == null ||
                        info.getDestinationArea().length == 0 || info.getDestinationAreaName().length == 0 ||
                        info.getDestinationAreaName().length != info.getDestinationArea().length){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                if (info.getConditionType() == ConditionType.VALUATION) {
                    Validate.notNull(info.getConditionOne(), ValidateUtil.NULL_EX_MESSAGE, "conditionOne");
                } else if (info.getConditionType() == ConditionType.MONEY) {
                    Validate.notNull(info.getConditionTwo(), ValidateUtil.NULL_EX_MESSAGE, "conditionTwo");
                }else{
                    Validate.notNull(info.getConditionOne(), ValidateUtil.NULL_EX_MESSAGE, "conditionOne");
                    Validate.notNull(info.getConditionTwo(), ValidateUtil.NULL_EX_MESSAGE, "conditionTwo");
                }
                BigDecimal conditionOne = info.getConditionOne() == null ? new BigDecimal(0) : info.getConditionOne();
                BigDecimal conditionTwo = info.getConditionTwo() == null ? new BigDecimal(0) : info.getConditionTwo();
                if(BigDecimal.ZERO.compareTo(conditionOne) > 0 || BigDecimal.ZERO.compareTo(conditionTwo) > 0 ||
                        new BigDecimal("9999.9").compareTo(conditionOne) < 0 || new BigDecimal("9999999999.99").compareTo(conditionTwo) < 0){
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            });
        }
    }
}
