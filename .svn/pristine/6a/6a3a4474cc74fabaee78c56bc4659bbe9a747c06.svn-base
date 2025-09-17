package com.wanmi.sbc.goods.bean.vo.wechatvideo;

import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;
import com.wanmi.sbc.goods.bean.vo.ThirdGoodsCateVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WechatCateDTO extends ThirdGoodsCateVO {

   public WechatCateDTO(Long cateId,String cateName, Integer cateGrade, ThirdPlatformType thirdPlatformType, Long cateParentId,Integer qualificationType,String qualification,Integer productQualificationType,String productQualification,String catePath,String rejectReason,AuditStatus auditStatus,String cateIds,String productQualificationUrls){
        super(cateId,cateName,cateGrade,thirdPlatformType,cateParentId,qualificationType,qualification,productQualificationType,productQualification,catePath);
        this.auditStatus = auditStatus;
        this.rejectReason = rejectReason;
        this.cateIds = cateIds;
       if (StringUtils.isNotBlank(productQualificationUrls)) {
           this.productQualificationUrls = Arrays.asList(productQualificationUrls.split(","));
       }
    }

    private List<GoodsCateVO> goodsCateVOS;

    @Schema(description = "映射的平台类目路径")
    private List<String> catePathList;

    /**
     * 审核不通过原因
     */
    @Schema(description = "审核不通过原因")
    private String rejectReason;

    private AuditStatus auditStatus;

    @Schema(description = "映射的平台类目id")
    private String cateIds;


    /**
     * 子微信类目
     */
    @Schema(description = "子微信类目")
    private List<WechatCateDTO> children;
    /**
     * 商品资质
     */
    private List<String> productQualificationUrls;
}
