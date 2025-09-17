//package com.wanmi.sbc.message.algorithm;
//
//import com.wanmi.sbc.common.util.DateUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.Collection;
//
///**
// * @author
// * VOP发送表-分表规则
// * PreciseShardingAlgorithm:精确分片算法，用于=、in场景
// */
//@Slf4j
//public class VopLogShardingAlgorithm implements PreciseShardingAlgorithm<Date> {
//
//	@Override
//	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {
//		log.info("=======VOP日志记录分表开始=========");
//		String logicTableName= preciseShardingValue.getLogicTableName();
//		LocalDate localDate = DateUtil.dateToLocalDate(preciseShardingValue.getValue());
//		int value = Math.abs(localDate.getDayOfMonth() % availableTargetNames.size());
//		log.info("=======VOP日志记录分表结束，最终路由表名：{}_{}=========",logicTableName, value);
//		return logicTableName  + "_" + value;
//	}
//}
