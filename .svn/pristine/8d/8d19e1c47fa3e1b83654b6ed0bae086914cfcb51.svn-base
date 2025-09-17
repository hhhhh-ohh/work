package com.wanmi.sbc.marketing.giftcard.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBatchType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardSourceType;
import com.wanmi.sbc.marketing.bean.vo.GiftCardDetailJoinVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.persistence.Query;

/**
 * <p>礼品卡单卡明细动态查询条件构建器</p>
 * @author edy
 * @date 2023-10-24 10:58:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDetailJoinWhereCriteriaBuilder extends BaseQueryRequest {

    /**
     *  礼品卡卡号1
     */
    @Schema(description = "礼品卡卡号1")
    private String giftCardNoStart;

    /**
     *  礼品卡卡号2
     */
    @Schema(description = "礼品卡卡号2")
    private String giftCardNoEnd;

    /**
     *  礼品卡名称
     */
    @Schema(description = "礼品卡名称")
    private String mainName;

    /**
     *  卡类型: 0 现金卡、1 全选提货卡、2 任选提货卡、3 平台储值卡、4 企业储值卡
     */
    @Schema(description = "卡类型")
    private Integer giftCardType;

    /**
     *  发卡/兑卡会员
     */
    @Schema(description = "发卡/兑卡会员")
    private String belongPerson;

    /**
     *  激活会员
     */
    @Schema(description = "激活会员")
    private String activationPerson;

    /**
     *  卡销售单号
     */
    @Schema(description = "卡销售单号")
    private String businessNo;

    @Schema(description = " 制卡批次编号")
    private String makeCardBatchNo;

    @Schema(description = " 发卡批次编号")
    private String sendCardBatchNo;

    /**
     * 批次类型 0:制卡 1:发卡
     */
    @Schema(description = " 批次类型 0:制卡 1:发卡")
    private GiftCardBatchType batchType;

    /**
     * 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数
     */
    @Schema(description = " 批次编号(制/发卡批次)，年月日时分秒毫秒+3位随机数")
    private String batchNo;

    /**
     * 搜索条件:卡过期时间开始
     */
    @Schema(description = "搜索条件:卡过期时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expirationTimeBegin;
    /**
     * 搜索条件:卡过期时间截止
     */
    @Schema(description = "搜索条件:卡过期时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expirationTimeEnd;

    /**
     * 礼品卡详情状态 0：未兑换 1：未激活 2：已激活 3：已作废 4：已过期 5：未开通 6：已用完
     */
    @Schema(description = "礼品卡详情状态 0：未兑换 1：未激活 2：已激活 3：已作废 4：已过期 5：未开通 6：已用完")
    private Integer cardDetailStatus;

    @Schema(description = "礼品卡卡号")
    private String giftCardNo;

    @Schema(description = "兑换吗")
    private String exchangeCode;

    @Schema(description = "制卡人id")
    private String  generatePerson;

    /**
     * 批次数量(制/发卡数量)
     */
    @Schema(description = "批次数量(制/发卡数量)")
    private Long batchNum;

    @Schema(description = "批次数量(制/发卡数量)")
    private AuditStatus auditStatus;

    @Schema(description = "制卡时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime generateTime;

    @Schema(description = "制卡批次id")
    private Long giftCardBatchId;

    @Schema(description = "礼品卡卡号List")
    private List<String> giftCardNoList;


    /**
     * 搜索条件:兑换时间开始
     */
    @Schema(description = "搜索条件:兑换时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireTimeBegin;
    /**
     * 搜索条件:兑换时间截止
     */
    @Schema(description = "搜索条件:兑换时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime acquireTimeEnd;

    /**
     * 搜索条件:激活时间开始
     */
    @Schema(description = "搜索条件:激活时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activationTimeBegin;
    /**
     * 搜索条件:激活时间截止
     */
    @Schema(description = "搜索条件:激活时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime activationTimeEnd;

    @Schema(description = "发卡状态，0：待发 1：成功 2：失败")
    private Integer sendStatus;

    @Schema(description = "礼品卡来源类型 0：制卡 1：发卡")
    private GiftCardSourceType sourceType;

    /**
     * 礼品卡id
     */
    @Schema(description = "礼品卡id")
    private Long giftCardId;


    /**
     * request构建查询单卡明细对象
     * @return
     */
    public String getQuerySql() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.gift_card_id as `giftCardId`, ");
        sb.append("      d.gift_card_no as `giftCardNo`, ");
        sb.append("      d.exchange_code as `exchangeCode`, ");
        sb.append("      b.batch_type as `batchType`, ");
        sb.append("      b.batch_no as `batchNo`, ");
        sb.append("      b.gift_card_trade_id as `giftCardTradeId`, ");
        sb.append("      b.export_mini_code_type as `exportMiniCodeType`, ");
        sb.append("      b.export_web_code_type as `exportWebCodeType`, ");
        sb.append("      d.belong_person as `belongPerson`, ");
        sb.append("      d.activation_person as `activationPerson`, ");
        sb.append("      c.main_name as `mainName`, ");
        sb.append("      c.par_value as `parValue`, ");
        sb.append("      c.gift_card_type as `giftCardType`, ");
        sb.append("      u.card_status as `cardStatus`, ");
        sb.append("      u.balance as `balance`, ");
        sb.append("      ( CASE WHEN u.user_gift_card_id IS NULL THEN c.expiration_type ELSE u.expiration_type END ) AS `expirationType`, ");
        sb.append("      c.range_month as `rangeMonth`, ");
        sb.append("      (case when u.user_gift_card_id is null then d.expiration_time else u.expiration_time end) AS `expirationTime`, ");
        sb.append("      (case when u.user_gift_card_id is null then d.expiration_start_time else u.expiration_start_time end) AS `expirationStartTime`, ");
        sb.append("      d.card_detail_status as  `cardDetailStatus`, ");
        sb.append("      d.acquire_time as  `acquireTime`, ");
        sb.append("      d.activation_time as  `activationTime`, ");
        sb.append("      d.send_person_type as  `sendPersonType`, ");
        sb.append("      d.send_status as  `sendStatus`, ");

        sb.append("      b.generate_person as  `generatePerson`, ");
        sb.append("      b.generate_time as  `generateTime`, ");
        sb.append("      b.batch_num as  `batchNum`, ");
        sb.append("      b.audit_status as  `auditStatus` ");
        return sb.toString();
    }

    /**
     * 分页查询时查询单卡明细总数
     * @return
     */
    public String getQueryTotalCountSql(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(1) AS `totalCount` from (SELECT d.gift_card_no ");

        return sb.toString();
    }

    /**
     * 查询列表总数子查询
     *
     * @return
     */
    public String getQueryTotalTemp() {
        StringBuilder sb = new StringBuilder();
        sb.append(" group by d.gift_card_no ) as temp");

        return sb.toString();
    }

    /**
     * 构建查询条件
     * @return
     */
    public String getQueryConditionSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("      FROM gift_card_detail AS d ");
        sb.append("      left join gift_card AS c on d.gift_card_id = c.gift_card_id ");
        sb.append("      left join gift_card_batch AS b on d.batch_no = b.batch_no ");
        sb.append("      left join user_gift_card AS u on d.gift_card_no = u.gift_card_no");
        sb.append("      WHERE 1=1 AND d.del_flag = 0");

        if (StringUtils.isNotBlank(giftCardNoStart) && StringUtils.isNotBlank(giftCardNoEnd)){
            sb.append("  and d.gift_card_no between :giftCardNoStart and :giftCardNoEnd ");
        } else if (StringUtils.isNotBlank(giftCardNoStart)){
            sb.append("  and d.gift_card_no >= :giftCardNoStart ");
        } else if (StringUtils.isNotBlank(giftCardNoEnd)){
            sb.append("  and d.gift_card_no <= :giftCardNoEnd ");
        }

        if (StringUtils.isNotBlank(giftCardNo)){
            sb.append("  and d.gift_card_no like concat('%', :giftCardNo ,'%')");
        }

        if (StringUtils.isNotBlank(mainName)){
            sb.append("  and c.main_name like concat('%', :mainName ,'%')");
        }

        if (Objects.nonNull(giftCardId)){
            sb.append("  AND d.gift_card_id = :giftCardId");
        }

        if (Objects.nonNull(giftCardType)){
            sb.append("  AND c.gift_card_type = :giftCardType");
        }

        if (StringUtils.isNotBlank(belongPerson)){
            sb.append("  and d.belong_person = :belongPerson ");
        }

        if (StringUtils.isNotBlank(activationPerson)){
            sb.append("  and d.activation_person = :activationPerson ");
        }

        if (Objects.nonNull(batchType)){
            sb.append("  AND b.batch_type = :batchType");
        }

        if (StringUtils.isNotBlank(batchNo)){
            sb.append("  AND b.batch_no like concat('%', :batchNo ,'%')");
        }

        if (StringUtils.isNotBlank(makeCardBatchNo)){
            sb.append("  AND b.batch_no like concat('%', :makeCardBatchNo ,'%') and b.batch_type = 0" );
        }

        if (StringUtils.isNotBlank(sendCardBatchNo)){
            sb.append("  AND b.batch_no like concat('%', :sendCardBatchNo ,'%') and b.batch_type = 1" );
        }

        if (Objects.nonNull(cardDetailStatus)){
            switch (cardDetailStatus){
                case 0:
                    //未兑换 状态为未兑换，未过期
                    sb.append(" AND ( d.card_detail_status = :cardDetailStatus AND (d.expiration_time >= now() or c.expiration_time is null or c.expiration_type = 0 )) ");
                    break;
                case 1 :
                    //未激活 状态为未激活，未过期
                    sb.append(" AND ( d.card_detail_status = :cardDetailStatus AND (u.expiration_time >= now() or u.expiration_type = 0 or u.expiration_time is null )) ");
                    break;
                case 2 :
                    //已激活 状态为已激活，未过期
                    sb.append(" AND ( d.card_detail_status = :cardDetailStatus AND (u.expiration_time >= now() or u.expiration_type = 0 )) ");
                    break;
                case 3 :
                    sb.append(" AND d.card_detail_status = :cardDetailStatus");
                    break;
                case 5 :
                    sb.append(" AND d.card_detail_status = :cardDetailStatus AND (d.expiration_time >= now() or c.expiration_type = 0 ) ");
                    break;
                case 4 :
                    sb.append(" and (u.expiration_type != 0 or u.expiration_type is null) AND (u.expiration_time < now() or u.expiration_time is null and d.expiration_time < NOW()) and (u.card_status != 2  or u.card_status IS NULL) " );
                    break;
                case 6 :
                    //已用完 礼品卡余额=0 且 礼品卡状态=已激活
                    sb.append(" AND ( u.balance = 0 and u.card_status = 1 AND (u.expiration_time >= now() or u.expiration_type = 0 )) ");
                    break;
                default:
                    break;
            }
        }

        // 大于或等于 搜索条件:有效期开始
        if (Objects.nonNull(expirationTimeBegin)) {
            sb.append(" AND (u.expiration_time >= :expirationTimeBegin ) ");
        }
        // 小于或等于 搜索条件:有效期截止
        if (Objects.nonNull(expirationTimeEnd)) {
            sb.append(" AND (u.expiration_time <= :expirationTimeEnd ) ");
        }


        // 大于或等于 搜索条件:有效期开始
        if (Objects.nonNull(acquireTimeBegin)) {
            sb.append(" AND (u.acquire_time >= :acquireTimeBegin) ");
        }
        // 小于或等于 搜索条件:有效期截止
        if (Objects.nonNull(acquireTimeEnd)) {
            sb.append(" AND (u.acquire_time <= :acquireTimeEnd) ");
        }

        // 大于或等于 搜索条件:有效期开始
        if (Objects.nonNull(activationTimeBegin)) {
            sb.append(" AND (d.activation_time >= :activationTimeBegin ) ");
        }
        // 小于或等于 搜索条件:有效期截止
        if (Objects.nonNull(activationTimeEnd)) {
            sb.append(" AND (d.activation_time <= :activationTimeEnd ) ");
        }

        if(Objects.nonNull(giftCardBatchId)){
            sb.append("  AND b.gift_card_batch_id = :giftCardBatchId");
        }

        if(CollectionUtils.isNotEmpty(giftCardNoList)){
            sb.append("  AND d.gift_card_no in ( :giftCardNoList ) ");
        }

        // 发卡状态
        if (Objects.nonNull(sendStatus)) {
            sb.append(" AND (d.send_status = :sendStatus ) ");
        }

        // 礼品卡来源类型
        if (Objects.nonNull(sourceType)) {
            sb.append(" AND (d.source_type = :sourceType ) ");
        }


        return sb.toString();
    }

    public String getQuerySort() {
        StringBuilder sb = new StringBuilder();
        sb.append(" group by d.gift_card_no order by d.create_time desc,d.gift_card_no desc ");
        return sb.toString();
    }

    /**
     * 构建查询条件
     * @return
     */
    public void wrapperQueryParam(Query query) {
        if (StringUtils.isNotBlank(giftCardNoStart) && StringUtils.isNotBlank(giftCardNoEnd)){
            query.setParameter("giftCardNoStart", giftCardNoStart);
            query.setParameter("giftCardNoEnd", giftCardNoEnd);
        } else if (StringUtils.isNotBlank(giftCardNoStart)){
            query.setParameter("giftCardNoStart", giftCardNoStart);
        } else if (StringUtils.isNotBlank(giftCardNoEnd)){
            query.setParameter("giftCardNoEnd", giftCardNoEnd);
        }
        if(StringUtils.isNotBlank(giftCardNo)){
            query.setParameter("giftCardNo", giftCardNo);
        }
        if (StringUtils.isNotBlank(mainName)){
            query.setParameter("mainName", mainName);
        }

        if (Objects.nonNull(giftCardType)){
            query.setParameter("giftCardType", giftCardType);
        }

        if (Objects.nonNull(giftCardId)){
            query.setParameter("giftCardId", giftCardId);
        }

        if (StringUtils.isNotBlank(belongPerson)){
            query.setParameter("belongPerson", belongPerson);
        }

        if (StringUtils.isNotBlank(activationPerson)){
            query.setParameter("activationPerson", activationPerson);
        }

        if (Objects.nonNull(batchType)){
            query.setParameter("batchType", batchType);
        }

        if (StringUtils.isNotBlank(batchNo)){
            query.setParameter("batchNo", batchNo);
        }


        if (StringUtils.isNotBlank(makeCardBatchNo)){
            query.setParameter("makeCardBatchNo", makeCardBatchNo);
        }

        if (StringUtils.isNotBlank(sendCardBatchNo)){
            query.setParameter("sendCardBatchNo", sendCardBatchNo);
        }

        if (Objects.nonNull(cardDetailStatus) && cardDetailStatus != 6 && cardDetailStatus != 4) {
            query.setParameter("cardDetailStatus", cardDetailStatus);
        }

        // 大于或等于 搜索条件:有效期开始
        if (Objects.nonNull(expirationTimeBegin)) {
            query.setParameter("expirationTimeBegin", expirationTimeBegin);
        }
        // 小于或等于 搜索条件:有效期截止
        if (Objects.nonNull(expirationTimeEnd)) {
            query.setParameter("expirationTimeEnd", expirationTimeEnd);
        }

        // 大于或等于 搜索条件:有效期开始
        if (Objects.nonNull(acquireTimeBegin)) {
            query.setParameter("acquireTimeBegin", acquireTimeBegin);
        }
        // 小于或等于 搜索条件:有效期截止
        if (Objects.nonNull(acquireTimeEnd)) {
            query.setParameter("acquireTimeEnd", acquireTimeEnd);
        }

        // 大于或等于 搜索条件:有效期开始
        if (Objects.nonNull(activationTimeBegin)) {
            query.setParameter("activationTimeBegin", activationTimeBegin);
        }
        // 小于或等于 搜索条件:有效期截止
        if (Objects.nonNull(activationTimeEnd)) {
            query.setParameter("activationTimeEnd", activationTimeEnd);
        }

        if(Objects.nonNull(giftCardBatchId)){
            query.setParameter("giftCardBatchId", giftCardBatchId);
        }
        if(CollectionUtils.isNotEmpty(giftCardNoList)){
            query.setParameter("giftCardNoList", giftCardNoList);
        }
        // 发卡状态
        if (Objects.nonNull(sendStatus)) {
            query.setParameter("sendStatus", sendStatus);
        }
        if (Objects.nonNull(sourceType)) {
            query.setParameter("sourceType", sourceType.toValue());
        }
    }

    /**
     * 查询对象转换
     *
     * @param sqlResult
     * @return
     */
    public static List<GiftCardDetailJoinVO> converter(List<Map<String, Object>> sqlResult) {
        return sqlResult.stream().map(item ->
                GiftCardDetailJoinVO.builder()
                        .giftCardId(toLong(item, "giftCardId"))
                        .giftCardNo(toStr(item, "giftCardNo"))
                        .exchangeCode(toStr(item, "exchangeCode"))
                        .batchType(toInteger(item, "batchType"))
                        .batchNo(toStr(item, "batchNo"))
                        .exportMiniCodeType(toInteger(item, "exportMiniCodeType"))
                        .exportWebCodeType(toInteger(item, "exportWebCodeType"))
                        .belongPerson(toStr(item, "belongPerson"))
                        .activationPerson(toStr(item, "activationPerson"))
                        .mainName(toStr(item, "mainName"))
                        .parValue(toBigDecimal(item, "parValue"))
                        .giftCardType(toInteger(item, "giftCardType"))
                        .balance(toBigDecimal(item, "balance"))
                        .cardStatus(toInteger(item, "cardStatus"))
                        .expirationType(toInteger(item, "expirationType"))
                        .rangeMonth(toLong(item, "rangeMonth"))
                        .expirationTime(toLocalDateTime(item, "expirationTime"))
                        .expirationStartTime(toLocalDateTime(item, "expirationStartTime"))
                        .cardDetailStatus(toInteger(item, "cardDetailStatus"))
                        .acquireTime(toLocalDateTime(item, "acquireTime"))
                        .activationTime(toLocalDateTime(item, "activationTime"))
                        .sendPersonType(toInteger(item, "sendPersonType"))
                        .sendStatus(toInteger(item, "sendStatus"))
                        .generatePerson(toStr(item, "generatePerson"))
                        .batchNum(toLong(item, "batchNum"))
                        .generateTime(toLocalDateTime(item, "generateTime"))
                        .auditStatus(toInteger(item, "auditStatus"))
                        .build()
        ).collect(Collectors.toList());
    }

    private static LocalDateTime toLocalDateTime(Map<String, Object> map, String key) {
        return map.get(key) != null ? Timestamp.valueOf(map.get(key).toString()).toLocalDateTime() : null;
    }

    private static String toStr(Map<String, Object> map, String key) {
        return map.get(key) != null ? map.get(key).toString() : null;
    }

    private static Integer toInteger(Map<String, Object> map, String key) {
        return map.get(key) != null ? Integer.valueOf(map.get(key).toString()) : null;
    }

    private static Long toLong(Map<String, Object> map, String key) {
        return map.get(key) != null ? Long.valueOf(map.get(key).toString()) : null;
    }

    private static BigDecimal toBigDecimal(Map<String, Object> map, String key) {
        return map.get(key) != null ? new BigDecimal(map.get(key).toString()) : null;
    }
}
