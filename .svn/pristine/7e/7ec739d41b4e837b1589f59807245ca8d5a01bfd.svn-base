package com.wanmi.sbc.common.redis.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.config.redis.RedisCondition;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.bean.RedisHIncrBean;
import com.wanmi.sbc.common.redis.bean.RedisHsetBean;

import jakarta.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * redis 工具类
 *
 * @author djk
 */
@Conditional(RedisCondition.class)
@Component
public class RedisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static RedisUtil redisUtil;

    @PostConstruct
    private void init() {
        setRedisUtil(this);
    }

    public static void setRedisUtil(RedisUtil redisUtil) {
        RedisUtil.redisUtil = redisUtil;
    }

    public static RedisUtil getInstance() {
        return redisUtil;
    }

    /**
     * 根据key删除缓存
     *
     * @param key
     * @return
     */
    public void delete(final String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.error("Delete cache fail and key : {}", key);
        }
    }

    /**
     * 保存数据到redis中
     */
    public boolean put(final String key, final Serializable value) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                connection.stringCommands().set(redisTemplate.getStringSerializer().serialize(key),
                        new JdkSerializationRedisSerializer().serialize(value));
                return Boolean.TRUE;
            });
        } catch (Exception e) {
            LOGGER.error("Put value to redis fail...", e);
        }

        return false;
    }

    /**
     * 保存字符串到redis中
     */
    public boolean setString(final String key, final String value) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                redisConnection.stringCommands().set(redisTemplate.getStringSerializer().serialize(key),
                        redisTemplate.getStringSerializer().serialize(value));
                return Boolean.TRUE;
            });
        } catch (Exception e) {
            LOGGER.error("putString value to redis fail...", e);
        }

        return false;
    }

    public boolean hset(final String key, final String field, final String value) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection ->
                    redisConnection.hashCommands().hSet(redisTemplate.getStringSerializer().serialize(key),
                            redisTemplate.getStringSerializer().serialize(field),
                            redisTemplate.getStringSerializer().serialize(value)));
        } catch (Exception e) {
            LOGGER.error("hset value to redis fail...", e);
        }

        return false;
    }

    public boolean hsetPipeline(final String key, final List<RedisHsetBean> fieldValues) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.openPipeline();
                    for (RedisHsetBean bean : fieldValues) {
                        redisConnection.hashCommands().hSet(redisTemplate.getStringSerializer().serialize(key),
                                redisTemplate.getStringSerializer().serialize(bean.getField()),
                                redisTemplate.getStringSerializer().serialize(bean.getValue()));
                    }
                    List<Object> objects = redisConnection.closePipeline();
                    return !CollectionUtils.isEmpty(objects);
                }
            });
        } catch (Exception e) {
            LOGGER.error("hsetPipeline value to redis fail...", e);
        }

        return false;
    }

    /***
     * 使用PipeLine累加数据
     * @param key       Key
     * @param value     累加值
     * @return
     */
    public boolean incrPipeline(final String key, final Long value) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                redisConnection.openPipeline();
                redisConnection.stringCommands().incrBy(redisTemplate.getStringSerializer().serialize(key), value);
                List<Object> objects = redisConnection.closePipeline();
                return !CollectionUtils.isEmpty(objects);
            });
        } catch (Exception e) {
            LOGGER.error("incrPipeline value to redis fail...", e);
        }

        return false;
    }

    public boolean hincrPipeline(final String key, final List<RedisHIncrBean> fieldValues) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                redisConnection.openPipeline();
                for (RedisHIncrBean bean : fieldValues) {
                    redisConnection.hashCommands().hIncrBy(redisTemplate.getStringSerializer().serialize(key),
                            redisTemplate.getStringSerializer().serialize(bean.getField()),
                            bean.getValue());
                }
                List<Object> objects = redisConnection.closePipeline();
                return !CollectionUtils.isEmpty(objects);
            });
        } catch (Exception e) {
            LOGGER.error("hsetPipeline value to redis fail...", e);
        }

        return false;
    }


    public String hget(final String key, final String field) {
        try {
            return redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                byte[] bytes = redisConnection.hashCommands().hGet(redisTemplate.getStringSerializer().serialize(key),
                        redisTemplate.getStringSerializer().serialize(field));
                return null != bytes ? redisTemplate.getStringSerializer().deserialize(bytes) : null;
            });
        } catch (Exception e) {
            LOGGER.error("hget value from redis fail...", e);
        }

        return null;
    }

    /**
     * 根据key删除缓存
     *
     * @param key
     * @return
     */
    public boolean hdelete(final String key, final String field) {
        try {
            redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                Long res = redisConnection.hashCommands().hDel(redisTemplate.getStringSerializer().serialize(key), redisTemplate
                        .getStringSerializer().serialize(field));
                return res != null ? Boolean.TRUE : Boolean.FALSE;
            });
        } catch (Exception e) {
            LOGGER.error("hget value from redis fail...", e);
        }
        return Boolean.FALSE;
    }

    /**
     * 从redis 中查询数据
     */
    public Object get(final String key) {
        try {
            return redisTemplate.execute((RedisCallback<Object>) connection ->
                    new JdkSerializationRedisSerializer()
                            .deserialize(connection.stringCommands().get(redisTemplate.getStringSerializer().serialize(key))));
        } catch (Exception e) {
            LOGGER.error("Get value from  redis fail...", e);
        }
        return null;
    }

    /**
     * 从redis 中查询字符串对象
     */
    public String getString(final String key) throws SbcRuntimeException {
        try {
            return redisTemplate.execute((RedisCallback<String>) connection -> {
                byte[] bytes = connection.stringCommands().get(redisTemplate.getStringSerializer().serialize(key));
                return null != bytes ? redisTemplate.getStringSerializer().deserialize(bytes) : null;
            });
        } catch (Exception e) {
            LOGGER.error("RedisUtil => getString fail , error => ", e);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     *  请正确使用，下列代码是典型错误使用。 建议直接 使用get方法
     *  boolean hasKey = redisService.hasKey(key);
     *  if ( StringUtils.isNotBlank(val)) {
     *     return JSONObject.parseObject(redisService.getString(key), SystemPointsConfigQueryResponse.class);
     *   }
     */
    public boolean hasKey(final String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 利用redis 分布式锁
     *
     * @param key
     * @return
     */
    public boolean setNx(final String key) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection ->
                redisConnection.stringCommands().setNX(redisTemplate.getStringSerializer().serialize(key),
                        redisTemplate.getStringSerializer().serialize(key)));
    }

    /**
     * 利用redis 分布式锁，加超时
     *
     * @param key
     * @return
     */
    public Boolean setNx(String key, String value, Long expireTime) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 以毫秒为单位设置key的超时时间
     *
     * @param key        key
     * @param expireTime 超时时间
     * @return boolean
     */
    public boolean expireByMilliseconds(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 以秒为单位设置key的超时时间
     *
     * @param key        key
     * @param expireTime 超时时间
     * @return boolean
     */
    public boolean expireBySeconds(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 以分钟为单位设置key的超时时间
     *
     * @param key        key
     * @param expireTime 超时时间
     * @return boolean
     */
    public boolean expireByMinutes(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 以分钟为单位设置key的超时时间
     *
     * @param key        key
     * @param expireTime 超时时间
     * @return boolean
     */
    public boolean expireByDays(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.DAYS);
    }

    /**
     * 对存储在指定key的数值执行原子的加1操作
     *
     * @param key key
     * @return
     */
    public Long incrKey(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.stringCommands().incr(redisTemplate.getStringSerializer().serialize(key))
        );
    }

    public Long decrKey(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.stringCommands().decr(redisTemplate.getStringSerializer().serialize(key))
        );
    }

    /**
     * 对存储在指定key的数值执行原子的加value操作
     *
     * @param key key
     * @return
     */
    public Long incrByKey(String key, Long value) {
        return redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.stringCommands().incrBy(redisTemplate.getStringSerializer().serialize(key), value)
        );
    }

    public Long decrByKey(String key, Long value) {
        return redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.stringCommands().decrBy(redisTemplate.getStringSerializer().serialize(key), value)
        );
    }


    /**
     * 从redis 中查询数据
     */
    public <T> T getObj(final String key, Class<T> clazz) {
        String value = getString(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return JSONObject.parseObject(value, clazz);
    }

    /**
     * 从redis 中查询数据
     */
    public <T> List<T> getList(final String key, Class<T> clazz) {
        String value = getString(key);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return JSONArray.parseArray(value, clazz);
    }

    /**
     * 保存对象字符串到redis中
     *
     * @param key
     * @param obj
     * @param seconds 失效时间，-1的时候表示持久化
     * @return
     */
    public boolean setObj(final String key, final Object obj, final long seconds) {
        String value = JSONObject.toJSONString(obj);
        return this.setString(key, value, seconds);
    }

    /**
     * 保存字符串到redis中,失效时间单位秒
     */
    public boolean setString(final String key, final String value, final long seconds) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                redisConnection.stringCommands().setEx(redisTemplate.getStringSerializer().serialize(key),
                        seconds, redisTemplate.getStringSerializer().serialize(value));
                return true;
            });
        } catch (Exception e) {
            LOGGER.error("putString value to redis fail...", e);
        }
        return false;
    }

    /**
     * 增加数值
     *
     * @param key   键
     * @param delta 浮点数
     * @return
     */
    public Double incrByFloat(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 减少数值
     *
     * @param key
     * @param delta
     * @return
     */
    public Double decrByFloat(String key, double delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 批量删除key
     *
     * @param list
     */
    public void del(List list) {
        redisTemplate.delete(list);
    }

    public Map<String, Object> hgetAllObj(final String key) {
        try {
            return redisTemplate.execute((RedisCallback<Map<String, Object>>) redisConnection -> {
                Map<String, Object> res = new HashMap<>();
                Map<byte[], byte[]> bytes =
                        redisConnection.hashCommands().hGetAll(redisTemplate.getStringSerializer().serialize(key));
                if (bytes != null) {
                    bytes.forEach((k, v) -> res.put(redisTemplate.getStringSerializer().deserialize(k),
                            redisTemplate.getValueSerializer().deserialize(v)));
                }
                return res;
            });
        } catch (Exception e) {
            LOGGER.error("hgetAll value from redis fail...", e);
        }
        return null;
    }

    public Map<String, String> hgetAllStr(final String key) {
        try {
            return redisTemplate.execute((RedisCallback<Map<String, String>>) redisConnection -> {
                Map<String, String> res = new HashMap<>();
                Map<byte[],byte[]> bytes =
                        redisConnection.hashCommands().hGetAll(redisTemplate.getStringSerializer().serialize(key));
                if(bytes != null) {
                    bytes.forEach((k, v) -> res.put(redisTemplate.getStringSerializer().deserialize(k),
                            redisTemplate.getStringSerializer().deserialize(v)));
                }
                return res;
            });
        } catch (Exception e) {
            LOGGER.error("hgetAll value from redis fail...", e);
        }
        return null;
    }

    public Map<String, String> hScanStr(final String key, final long count, final String pattern) {
        try {
            return redisTemplate.execute((RedisCallback<Map<String, String>>) redisConnection -> {
                Map<String, String> res = new HashMap<>();
                ScanOptions scanOptions = ScanOptions.scanOptions().count(count).match(pattern).build();
                Cursor<Map.Entry<byte[], byte[]>> cursor =
                        redisConnection.hashCommands().hScan(redisTemplate.getStringSerializer().serialize(key), scanOptions);
                while (cursor.hasNext()){
                    Map.Entry<byte[], byte[]> entry = cursor.next();
                    if(entry != null) {
                        res.put(redisTemplate.getStringSerializer().deserialize(entry.getKey()),
                                redisTemplate.getStringSerializer().deserialize(entry.getValue()));
                    }
                }
//                try {
                    cursor.close();
//                } catch (IOException e) {
//                    LOGGER.error("hScanStr cursor close fail...", e);
//                }
                return res;
            });
        } catch (Exception e) {
            LOGGER.error("hScanStr value from redis fail...", e);
        }
        return null;
    }

    /**
     * 从redis中批量查询字符串对象
     */
    public List<String> getMString(List<String> keys) throws SbcRuntimeException {
        try {
            return redisTemplate.execute((RedisCallback<List<String>>) connection -> {
                List<byte[]> keyList =
                        keys.stream().map(key -> redisTemplate.getStringSerializer().serialize(key)).collect(Collectors.toList());
                List<byte[]> resList = connection.stringCommands().mGet(keyList.toArray(new byte[keyList.size()][]));
                return resList.stream().map(
                        res -> null != res ? redisTemplate.getStringSerializer().deserialize(res) : null).collect(Collectors.toList());
            });
        } catch (Exception e) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 保存多个字符串到redis中,失效时间单位秒
     */
    public boolean setMString(Map<String, String> keyValues, final long seconds) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                keyValues.keySet().forEach(key ->
                        redisConnection.stringCommands().setEx(redisTemplate.getStringSerializer().serialize(key),
                                seconds, redisTemplate.getStringSerializer().serialize(keyValues.get(key))));
                return Boolean.TRUE;
            });
        } catch (Exception e) {
            LOGGER.error("putString value to redis fail...", e);
        }
        return false;
    }

    /**
     * 功能描述：返回 key 所关联的字符串值。
     *
     * @param key
     * @return
     */
    public Object getValueByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Map<String, String> hgetall(final String key) {
        try {
            return redisTemplate.execute((RedisCallback<Map<String, String>>) con -> {
                Map<byte[], byte[]> result = con.hashCommands().hGetAll(key.getBytes(StandardCharsets.UTF_8));
                if (CollectionUtils.isEmpty(result)) {
                    return new HashMap<>(0);
                }
                Map<String, String> ans = new HashMap<>(result.size());
                for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                    ans.put(new String(entry.getKey(), StandardCharsets.UTF_8), new String(entry.getValue(),
                            StandardCharsets.UTF_8));
                }
                return ans;
            });
        } catch (Exception e) {
            LOGGER.error("hget value from redis fail...", e);
        }
        return null;
    }

    /**
     * 保存多个字符串到redis中
     */
    public boolean setMString(Map<String, String> keyValues) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                keyValues.keySet().forEach(key ->
                        redisConnection.stringCommands().set(redisTemplate.getStringSerializer().serialize(key),
                                redisTemplate.getStringSerializer().serialize(keyValues.get(key))));
                return Boolean.TRUE;
            });
        } catch (Exception e) {
            LOGGER.error("putString value to redis fail...", e);
        }
        return false;
    }

    /**
     * @description  扣除库存
     * @author  wur
     * @date: 2022/6/29 15:50
     * @param key      商品库存KEY
     * @param stock    需要扣除的库存量
     * @return 返回 1：库存扣除成功  0：扣除失败
     **/
    public Long deductStock(String key, Long stock) {
        String lua = "if tonumber(redis.call('get', KEYS[1])) >= tonumber(ARGV[1]) " +
                "then" +
                " redis.call('decrby', KEYS[1], tonumber(ARGV[1]))" +
                " return 1 " +
                "else " +
                " return 0 " +
                "end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(lua);
        redisScript.setResultType(Long.class);
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        return (Long) redisTemplate.execute(redisScript, keyList, stock.toString());
    }

    public Long hIncr(final String key, final String field, final long value) {
        return redisTemplate.opsForHash().increment(
                key,
                field
                , value);
    }

    /**
     * @description  缓存增量
     * @author  wur
     * @date: 2022/8/29 15:50
     * @param key      KEY
     * @param max    增量的上限
     * @param value  增量值
     * @param expireTime  过期时间  毫秒单位
     * @return 返回 1：增量成功  0：增量失败
     **/
    public Long incrBy(String key, Long max, Long value, Long expireTime) {
        String lua = "if redis.call('get', KEYS[1]) " +
                "then " +
                    "if (tonumber(redis.call('get', KEYS[1])) + tonumber(ARGV[2])) <= tonumber(ARGV[1]) " +
                    "then "+
                        " redis.call('incrby', KEYS[1], tonumber(ARGV[2])) " +
                        " return 1 " +
                    "else " +
                        " return 0 " +
                    "end " +
                "else " +
                    " redis.call('incrby', KEYS[1], tonumber(ARGV[2])) " +
                    " redis.call('pexpire', KEYS[1], tonumber(ARGV[3])) " +
                    " return 1 " +
                "end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(lua);
        redisScript.setResultType(Long.class);
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        return (Long) redisTemplate.execute(redisScript, keyList, max.toString(), value.toString(), expireTime.toString());
    }

}
