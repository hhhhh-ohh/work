//package com.wanmi.sbc.message.algorithm;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//
//import java.util.Collection;
//
///**
// * @author
// * 商家消息详情表-分表规则
// * PreciseShardingAlgorithm:精确分片算法，用于=、in场景
// */
//@Slf4j
//public class StoreMessageDetailShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
//
//	@Override
//	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> preciseShardingValue) {
//		log.info("=======商家消息分表开始=========");
//		String logicTableName= preciseShardingValue.getLogicTableName();
//		Object valueObj = preciseShardingValue.getValue();
//		int intValue;
//		if (valueObj instanceof Integer) {
//			// 按理storeId应该是Long，但是Sharding会根据数值大小自动选择填充Integer还是Long，所以要判断转换一下
//			intValue = (Integer) valueObj;
//		} else {
//			intValue = preciseShardingValue.getValue().intValue();
//		}
//		int suffix = Math.abs(intValue % availableTargetNames.size());
//		log.info("=======商家消息分表结束，最终路由表名：{}_{}=========", logicTableName, suffix);
//		return logicTableName  + "_" + suffix;
//	}
//}
