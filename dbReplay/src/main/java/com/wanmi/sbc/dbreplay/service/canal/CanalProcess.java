package com.wanmi.sbc.dbreplay.service.canal;

import com.wanmi.sbc.dbreplay.config.canal.CanalConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CanalProcess {
    private static List<Map.Entry<String,String>> REPLAY_TABLE_LIST = null;
    private static Map<String,String> CACHE_MAP = new ConcurrentHashMap<>();

    private String replayTable;

    public CanalProcess(CanalConfig canalConfig){
        this.replayTable=canalConfig.getReplayTable();
        initTableNameMap();
    }
    public void initTableNameMap(){
        Map<String,String> map = new HashMap<>();
        if(StringUtils.isNotBlank(replayTable)){
            String[] tables = replayTable.split(",");
            for(String table : tables){
                if(StringUtils.isNotBlank(table)){
                    String[] detail = table.split(":");
                    if(detail.length>1) {
                        String _sourceTableName = detail[0];
                        String _replayTableName = detail[1];
                        map.put(_sourceTableName, _replayTableName);
                    }
                }
            }
        }
        REPLAY_TABLE_LIST = new ArrayList<Map.Entry<String,String>>(map.entrySet());
        //按照长度进行到排序，test*,tes*优先匹配test*
        Collections.sort(REPLAY_TABLE_LIST,new Comparator<Map.Entry<String,String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                if (o1.getKey().length() > o2.getKey().length()) {
                    return -1;
                } else if (o1.getKey().length() < o2.getKey().length()) {
                    return 1;
                } else {
                    return 0;
                }
            }

        });
    }


    public String getReplayTableName(String sourceTableName){
        if(REPLAY_TABLE_LIST == null){         //如果配置为空则直接返回原表名
            return sourceTableName;
        }
        String replayTableName = CACHE_MAP.get(sourceTableName);
        if(StringUtils.isNotEmpty(replayTableName)){
            return replayTableName;
        }
        for(Map.Entry<String,String> entry : REPLAY_TABLE_LIST){

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("^")
                    .append(entry.getKey());
            Pattern pattern = Pattern.compile(stringBuffer.toString());
            Matcher matcher = pattern.matcher(sourceTableName);
            if(matcher.find()){
                CACHE_MAP.put(sourceTableName,entry.getValue());
                replayTableName = entry.getValue();
                break;
            }
        }
        if(StringUtils.isEmpty(replayTableName)){
            CACHE_MAP.put(sourceTableName,sourceTableName);
            replayTableName = CACHE_MAP.get(sourceTableName);
        }
        return replayTableName;
    }

    public static void main(String[] args) {
        String str = "foo*:test1,foot*:test2,test1:test2";
        CanalConfig canalConfig = new CanalConfig();
        canalConfig.setReplayTable(str);
        CanalProcess canalProcess = new CanalProcess(canalConfig);
        String replay = canalProcess.getReplayTableName("foot1");
        System.out.printf(replay);
        System.out.printf(canalProcess.getReplayTableName("test"));
        System.out.printf(canalProcess.getReplayTableName("test"));
    }
}
