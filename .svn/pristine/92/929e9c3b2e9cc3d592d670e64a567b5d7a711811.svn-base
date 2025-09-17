package com.wanmi.sbc.setting.sensitivewords.service;


import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.SensitiveUtils;
import com.wanmi.sbc.setting.api.request.SensitiveWordsQueryRequest;
import com.wanmi.sbc.setting.sensitivewords.model.root.SensitiveWords;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: jiaojiao
 * @Date: 2019/2/28 09:53
 * @Description:
 */
@Service("BadWordService")
public class BadWordService {

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private SensitiveWordsService sensitiveWordsService;

    private Map<String,String> wordMap = new HashMap<>();

    public static final int minMatchTYpe = 1;      //最小匹配规则


//    {
//        addBadWordToHashMap();
//    }
    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    public int checkBadWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = wordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if(minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        /*“粉饰”匹配词库：“粉饰太平”竟然说是敏感词
         * “个人”匹配词库：“个人崇拜”竟然说是敏感词
         * if(matchFlag < 2 && !flag){
            matchFlag = 0;
        }*/
        if(!flag){
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
     * 获取文字中的敏感词
     * @param txt 文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    public  Set<String> getBadWord(String txt , int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();
        // 将文本中的网络资源地址处理成空串，避免地址中包含敏感词报错
        txt = SensitiveUtils.handlerTxtWithNetworkSourceAddress(txt);
        for(int i = 0 ; i < txt.length() ; i++){
            int length = checkBadWord(txt, i, matchType);    //判断是否包含敏感字符
            if(length > 0){    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }
        Set<String> sensitiveWordsSet = new HashSet<>();
        for (String word : sensitiveWordList) {
            String regex = "(?<![a-zA-Z0-9_])" + Pattern.quote(word) + "(?:[\\u4e00-\\u9fa5]+)?";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(txt);
            while (matcher.find()) {
                sensitiveWordsSet.add(word);
            }
        }
        return sensitiveWordsSet;
    }

    /**
     * 将我们的敏感词库构建成了一个类似与一颗一颗的树，这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。
     * @param refreshFlag  是否刷新static缓存
     * @author yqwang0907
     * @date 2018年2月28日下午5:28:08
     */
    public void addBadWordToHashMap(Boolean refreshFlag) {
        if((!refreshFlag) && MapUtils.isNotEmpty(wordMap)){
            return;
        }

        String badWords = redisService.getString(CacheKeyConstant.BAD_WORD);
        //判断redis里面是否有值 没有的话重新放一次
        if (StringUtils.isEmpty(badWords)) {
        //查询数据库里面的
            String badWordsStr = getBadWordsFromDB();
            redisService.setString(CacheKeyConstant.BAD_WORD,badWordsStr);
            badWords = badWordsStr;
        }
        //循环redis里的值 放入到keyWordSet

        String[] words = badWords.split(",");
        Set<String> keyWordSet = new HashSet<>(words.length);
        for(String word:words){
            keyWordSet.add(word);
        }

        wordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作

        String key ;
        Map nowMap ;
        Map<String, String> newWorMap ;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = wordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }

    /**
     * 获取敏感词字符串
     * @return
     */
    public String getBadWordsFromDB(){

        SensitiveWordsQueryRequest queryRequest = new SensitiveWordsQueryRequest();
        queryRequest.setDelFlag(DeleteFlag.NO);
        List<SensitiveWords> sensitiveWords = sensitiveWordsService.list(queryRequest);
        StringBuilder sbf = new StringBuilder();
        for(SensitiveWords words:sensitiveWords){
            sbf.append(words.getSensitiveWords()).append(',');
        }
        return sbf.toString();
    }

}
