//package com.wanmi.sbc.marketing.algorithm;
//
//import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;
//import java.util.Arrays;
//import java.util.Collection;
///**
// * @type ManualShardingAlgorithm.java
// * @desc
// * @author zhanggaolei
// * @date 2023/4/3 14:42
// * @version
// */
//public class ManualShardingAlgorithm implements HintShardingAlgorithm<String> {
//
//    @Override
//    public Collection<String> doSharding(
//            Collection<String> collection, HintShardingValue<String> hintShardingValue) {
//        Object index = hintShardingValue.getValues().toArray()[0];
//        if(index instanceof Number){
//
//        }
//        String key =
//                hintShardingValue.getLogicTableName()
//                        + "_"
//                        + hintShardingValue.getValues().toArray()[0];
//        if (collection.contains(key)) {
//            return Arrays.asList(key);
//        }
//        throw new UnsupportedOperationException(
//                "route " + key + " is not supported ,please check your config");
//    }
//}
