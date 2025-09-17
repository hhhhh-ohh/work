package com.wanmi.sbc.order.purchase.request;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.order.enums.FollowFlag;
import com.wanmi.sbc.order.purchase.Purchase;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Enumerated;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 商品客户收藏请求
 * Created by daiyitian on 2017/5/17.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest extends BaseQueryRequest {

    private static final long serialVersionUID = 3590025584368358090L;
    /**
     * 当前客户等级
     */
    CustomerLevelVO customerLevel;
    /**
     * 编号
     */
    private List<Long> followIds;
    /**
     * SKU编号
     */
    @NotBlank
    private String goodsInfoId;
    /**
     * 批量SKU编号
     */
    private List<String> goodsInfoIds;
    /**
     * 批量sku
     */
    private List<GoodsInfoDTO> goodsInfos;
    /**
     * 会员编号
     */
    private String customerId;
    /**
     * 购买数量
     */
    @Range(min = 1)
    private Long goodsNum;
    /**
     * 收藏标识
     */
    @Enumerated
    private FollowFlag followFlag;
    /**
     * 校验库存
     */
    @Default
    private Boolean verifyStock = Boolean.TRUE;
    /**
     * 是否赠品 true 是 false 否
     */
    @Default
    private Boolean isGift = Boolean.FALSE;

    /**
     * 购买数量是否覆盖
     */
    @Default
    private Boolean isCover = Boolean.FALSE;

    private LocalDateTime createTime;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;
    /**
     * 门店id
     */
    @Schema(description = "门店id")
    private Long storeId;



    /**
     * 终端来源
     */
    private TerminalSource terminalSource;

    /**
     * 是否是pc端访问或者社交分销关闭
     */
    @Schema(description = "是否是pc端访问或者社交分销关闭")
    private Boolean pcAndNoOpenFlag;

    @Schema(description = "区的区域码")
    private Long AreaId;

    @Schema(description = "是否更新时间")
    private BoolFlag updateTimeFlag;

//    @Schema(description = "类型： 0-正常商品 1-跨境商品 2-o2o商品")
//    private PluginType pluginType;

    @Schema(description = "是否根据pluginType查询")
    private BoolFlag pluginTypeFlag;

    @Schema(description = "类型： 0-正常商品 1-跨境商品 2-o2o商品")
    private List<PluginType> pluginTypes;

    /**
     * 第一次从商品列表和详情加入购物车时的价格
     **/
    @Schema(description = "加入购物车时的价格")
    private BigDecimal firstPurchasePrice;

    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<Purchase> getWhereCriteria() {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            //客户编号
            if (StringUtils.isNotBlank(customerId)) {
                predicates.add(cbuild.equal(root.get("customerId"), customerId));
            }
            //SKU编号
            if (StringUtils.isNotBlank(goodsInfoId)) {
                predicates.add(cbuild.equal(root.get("goodsInfoId"), goodsInfoId));
            }

            //批量SKU编号
            if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
                predicates.add(root.get("goodsInfoId").in(goodsInfoIds));
            }

            //分销员编号
            if (StringUtils.isNotBlank(inviteeId)) {
                predicates.add(cbuild.equal(root.get("inviteeId"), inviteeId));
            } else {
                predicates.add(cbuild.equal(root.get("inviteeId"), NumberUtils.INTEGER_ZERO.toString()));
            }

            //分销员编号
            if (Objects.nonNull(companyInfoId)) {
                predicates.add(cbuild.equal(root.get("companyInfoId"), companyInfoId));
            }

            if(Objects.nonNull(pluginTypeFlag) &&  pluginTypeFlag == BoolFlag.YES) {
                // 类型： 0-正常商品 1-跨境商品 2-o2o商品
//                if (Objects.nonNull(pluginType)) {
//                    predicates.add(cbuild.equal(root.get("pluginType"), pluginType));
//                } else {
//                    Predicate p1 = cbuild.isNull(root.get("pluginType"));
//                    Predicate p2 = cbuild.notEqual(root.get("pluginType"), PluginType.O2O);
//                    predicates.add(cbuild.or(p1,p2));
//                }

                // 类型： 0-正常商品 1-跨境商品 2-o2o商品
                if (CollectionUtils.isNotEmpty(pluginTypes)) {
                    CriteriaBuilder.In in = cbuild.in(root.get("pluginType"));
                    pluginTypes.forEach(p->in.value(p));
                    predicates.add(in);
                }

            }




            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }

    public Boolean getVerifyStock() {
        return this.verifyStock == null ? Boolean.TRUE : this.verifyStock;
    }

    public Boolean getIsGift() {
        return this.isGift == null ? Boolean.FALSE : this.isGift;
    }

    public Boolean getIsCover() {
        return this.isCover == null ? Boolean.FALSE : this.isCover;
    }
}
