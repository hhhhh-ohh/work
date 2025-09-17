package com.wanmi.sbc.elastic.storeInformation.request;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

import static co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders.*;

/**
 * @Author yangzhen
 * @Description // 商家店铺信息
 * @Date 18:30 2020/12/7
 * @Param
 * @return
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class StoreInfoQueryRequest extends BaseRequest {

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String supplierName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String companyCode;

    /**
     * 商家账号
     */
    @Schema(description = "商家账号")
    private String accountName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private Integer companyType;

    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState auditState;

    /**
     * 审核未通过原因
     */
    @Schema(description = "审核未通过原因")
    private String auditReason;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    private AccountState accountState;

    /**
     * 账号禁用原因
     */
    @Schema(description = "账号禁用原因")
    private String accountDisableReason;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态")
    private StoreState storeState;

    /**
     * 账号关闭原因
     */
    @Schema(description = "账号关闭原因")
    private String storeClosedReason;

    /**
     * 商家类型0品牌商城，1商家
     */
    @Schema(description = "商家类型0品牌商城，1商家")
    private StoreType storeType;

    /**
     * 是否是主账号
     */
    @Schema(description = "店铺名称")
    private String isMasterAccount;

    /**
     * 店铺删除状态
     */
    @Schema(description = "店铺删除状态")
    private DeleteFlag storeDelFlag;

    /**
     * 公司删除状态
     */
    @Schema(description = "公司删除状态")
    private DeleteFlag companyInfoDelFlag;

    /**
     * 员工删除状态
     */
    @Schema(description = "员工删除状态")
    private DeleteFlag employeeDelFlag;

    /**
     * 是否确认打款 (-1:全部,0:否,1:是)
     */
    @Schema(description = "是否确认打款(-1:全部,0:否,1:是)")
    private Integer remitAffirm;

    /**
     * 入驻时间
     */
    @Schema(description = "入驻时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTimeStartDate;

    /**
     * 入驻时间
     */
    @Schema(description = "入驻时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTimeEndDate;

    public BoolQuery getWhereCriteria() {
//        BoolQueryBuilder boolQueryBuilder = boolQuery();
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        //根据店铺id查询
        if (Objects.nonNull(storeId)) {
//            boolQueryBuilder.must(termQuery("storeId", storeId));
            boolQueryBuilder.must(term(a -> a.field("storeId").value(storeId)));
        }
        //店铺名称模糊查询
        if (StringUtils.isNotBlank(storeName)) {
//            boolQueryBuilder.must(matchPhraseQuery("storeName", storeName));
            boolQueryBuilder.must(matchPhrase(a -> a.field("storeName").query(storeName)));
        }
        //是否打款确认
        if (Objects.nonNull(remitAffirm)) {
//            boolQueryBuilder.must(termQuery("remitAffirm", remitAffirm));
            boolQueryBuilder.must(term(a -> a.field("remitAffirm").value(remitAffirm)));
        }
        /**
         * 入驻时间 开始
         */
        if (Objects.nonNull(applyEnterTimeStartDate)) {
//            boolQueryBuilder.must(rangeQuery("applyEnterTime").lte(applyEnterTimeStartDate));
            boolQueryBuilder.must(range(a -> a.field("applyEnterTime").lte(JsonData.of(applyEnterTimeStartDate))));
        }

        /**
         * 入驻时间 结束
         */
        if (Objects.nonNull(applyEnterTimeEndDate)) {
//            boolQueryBuilder.must(rangeQuery("applyEnterTime").gte(applyEnterTimeEndDate));
            boolQueryBuilder.must(range(a -> a.field("applyEnterTime").gte(JsonData.of(applyEnterTimeEndDate))));
        }
        return boolQueryBuilder.build();
    }

}
