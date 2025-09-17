package com.wanmi.sbc.elastic.configuration;

import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.customer.bean.enums.*;
import com.wanmi.sbc.customer.bean.enums.GenderType;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.enums.AuditStatus;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** @Author: songhanlin @Date: Created In 下午3:41 2021/11/29 @Description: TODO */
@Configuration
public class ElasticsearchConfiguration extends ElasticsearchConfigurationSupport {

  @Bean
  @Override
  public ElasticsearchCustomConversions elasticsearchCustomConversions() {
    List<Converter> converters = new ArrayList<>();
    // 枚举类型converter
    converters.add(AccountState.AccountStateToIntegerConverter.INSTANCE);
    converters.add(AccountState.IntegerToAccountStateConverter.INSTANCE);

    converters.add(AccountType.AccountTypeToIntegerConverter.INSTANCE);
    converters.add(AccountType.IntegerToAccountTypeConverter.INSTANCE);

    converters.add(AuditStatus.AuditStatusToIntegerConverter.INSTANCE);
    converters.add(AuditStatus.IntegerToAuditStatusConverter.INSTANCE);

    converters.add(BoolFlag.BoolFlagToIntegerConverter.INSTANCE);
    converters.add(BoolFlag.IntegerToBoolFlagConverter.INSTANCE);

    converters.add(CheckState.CheckStateToIntegerConverter.INSTANCE);
    converters.add(CheckState.IntegerToCheckStateConverter.INSTANCE);

    converters.add(CheckStatus.CheckStatusToIntegerConverter.INSTANCE);
    converters.add(CheckStatus.IntegerToCheckStatusConverter.INSTANCE);

    converters.add(CommissionReceived.CommissionReceivedToIntegerConverter.INSTANCE);
    converters.add(CommissionReceived.IntegerToCommissionReceivedConverter.INSTANCE);

    converters.add(CouponActivityType.CouponActivityTypeToIntegerConverter.INSTANCE);
    converters.add(CouponActivityType.IntegerToCouponActivityTypeConverter.INSTANCE);

    converters.add(CouponType.CouponTypeToIntegerConverter.INSTANCE);
    converters.add(CouponType.IntegerToCouponTypeConverter.INSTANCE);

    converters.add(CouponMarketingType.CouponMarketingTypeToIntegerConverter.INSTANCE);
    converters.add(CouponMarketingType.IntegerToCouponMarketingTypeConverter.INSTANCE);

    converters.add(CouponDiscountMode.CouponDiscountModeToIntegerConverter.INSTANCE);
    converters.add(CouponDiscountMode.IntegerToCouponDiscountModeConverter.INSTANCE);

    converters.add(DefaultFlag.DefaultFlagToIntegerConverter.INSTANCE);
    converters.add(DefaultFlag.IntegerToDefaultFlagConverter.INSTANCE);

    converters.add(DeleteFlag.DeleteFlagToIntegerConverter.INSTANCE);
    converters.add(DeleteFlag.IntegerToDeleteFlagConverter.INSTANCE);

    converters.add(DistributionGoodsAudit.DistributionGoodsAuditToIntegerConverter.INSTANCE);
    converters.add(DistributionGoodsAudit.IntegerToDistributionGoodsAuditConverter.INSTANCE);

    converters.add(FailReasonFlag.FailReasonFlagToIntegerConverter.INSTANCE);
    converters.add(FailReasonFlag.IntegerToFailReasonFlagConverter.INSTANCE);

    converters.add(FullBuyType.FullBuyTypeToIntegerConverter.INSTANCE);
    converters.add(FullBuyType.IntegerToFullBuyTypeConverter.INSTANCE);

    converters.add(GenderType.GenderTypeToIntegerConverter.INSTANCE);
    converters.add(GenderType.IntegerToGenderTypeConverter.INSTANCE);

    converters.add(GoodsPropertyEnterType.GoodsPropertyEnterTypeToIntegerConverter.INSTANCE);
    converters.add(GoodsPropertyEnterType.IntegerToGoodsPropertyEnterTypeConverter.INSTANCE);

    converters.add(GoodsStatus.GoodsStatusToIntegerConverter.INSTANCE);
    converters.add(GoodsStatus.IntegerToGoodsStatusConverter.INSTANCE);

    converters.add(InvalidFlag.InvalidFlagToIntegerConverter.INSTANCE);
    converters.add(InvalidFlag.IntegerToInvalidFlagConverter.INSTANCE);

    converters.add(MatterType.MatterTypeToIntegerConverter.INSTANCE);
    converters.add(MatterType.IntegerToMatterTypeConverter.INSTANCE);

    converters.add(PluginType.PluginTypeToIntegerConverter.INSTANCE);
    converters.add(PluginType.IntegerToPluginTypeConverter.INSTANCE);

    converters.add(PriceType.PriceTypeToIntegerConverter.INSTANCE);
    converters.add(PriceType.IntegerToPriceTypeConverter.INSTANCE);

    converters.add(RangeDayType.RangeDayTypeToIntegerConverter.INSTANCE);
    converters.add(RangeDayType.IntegerToRangeDayTypeConverter.INSTANCE);

    converters.add(ResourceType.ResourceTypeToIntegerConverter.INSTANCE);
    converters.add(ResourceType.IntegerToResourceTypeConverter.INSTANCE);

    converters.add(RewardFlag.RewardFlagToIntegerConverter.INSTANCE);
    converters.add(RewardFlag.IntegerToRewardFlagConverter.INSTANCE);

    converters.add(ScopeType.ScopeTypeToIntegerConverter.INSTANCE);
    converters.add(ScopeType.IntegerToScopeTypeConverter.INSTANCE);

    converters.add(SettleStatus.SettleStatusToIntegerConverter.INSTANCE);
    converters.add(SettleStatus.IntegerToSettleStatusConverter.INSTANCE);

    converters.add(LakalaLedgerStatus.SettleStatusToIntegerConverter.INSTANCE);
    converters.add(LakalaLedgerStatus.IntegerToSettleStatusConverter.INSTANCE);

    converters.add(StoreState.StoreStateToIntegerConverter.INSTANCE);
    converters.add(StoreState.IntegerToStoreStateConverter.INSTANCE);

    converters.add(StoreType.StoreTypeToIntegerConverter.INSTANCE);
    converters.add(StoreType.IntegerToStoreTypeConverter.INSTANCE);

    converters.add(ThirdPlatformType.ThirdPlatformTypeToIntegerConverter.INSTANCE);
    converters.add(ThirdPlatformType.IntegerToThirdPlatformTypeConverter.INSTANCE);

    converters.add(CustomerStatus.CustomerStatusToIntegerConverter.INSTANCE);
    converters.add(CustomerStatus.IntegerToCustomerStatusConverter.INSTANCE);

    converters.add(CustomerType.CustomerTypeToIntegerConverter.INSTANCE);
    converters.add(CustomerType.IntegerToCustomerTypeConverter.INSTANCE);

    converters.add(EnterpriseCheckState.EnterpriseCheckStateToIntegerConverter.INSTANCE);
    converters.add(EnterpriseCheckState.IntegerToEnterpriseCheckStateConverter.INSTANCE);

    // 时间类型converter
    converters.add(StringToLocalDateTimeConverter.INSTANCE);
    converters.add(LocalDateTimeToStringConverter.INSTANCE);
    converters.add(StringToLocalDateConverter.INSTANCE);
    converters.add(LocalDateToStringConverter.INSTANCE);

    converters.add(ParticipateType.ParticipateTypeToIntegerConverter.INSTANCE);
    converters.add(ParticipateType.IntegerToParticipateTypeConverter.INSTANCE);

    converters.add(InvoiceStyle.InvoiceStyleToIntegerConverter.INSTANCE);
    converters.add(InvoiceStyle.IntegerToInvoiceStyleConverter.INSTANCE);
    return new ElasticsearchCustomConversions(converters);
  }

  // 格式化后保存结果为String类型
  @ReadingConverter
  enum StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    INSTANCE;

    @Override
    public LocalDateTime convert(String source) {
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
      return LocalDateTime.parse(source, df);
    }
  }

  @WritingConverter
  enum LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
    INSTANCE;
    @Override
    public String convert(LocalDateTime date) {
      return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
  }

  // 格式化后保存结果为String类型
  @ReadingConverter
  enum StringToLocalDateConverter implements Converter<String, LocalDate> {
    INSTANCE;

    @Override
    public LocalDate convert(String source) {
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      return LocalDate.parse(source, df);
    }
  }

  @WritingConverter
  enum LocalDateToStringConverter implements Converter<LocalDate, String> {
    INSTANCE;
    @Override
    public String convert(LocalDate date) {
      return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
  }
}
