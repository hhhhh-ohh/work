package com.wanmi.sbc.common.cache;

import com.google.errorprone.annotations.FormatMethod;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className CacheSpec
 * @description
 * @date 2022/5/6 19:32
 */
@Slf4j
public class CacheSpec {

    static final int UNSET_INT = -1;
    static final String SPLIT_OPTIONS = ",";
    static final String SPLIT_KEY_VALUE = "=";
    Map<String, Object> specMap = new HashMap<>();

    String spec;
    // 初始的缓存空间大小
    int initialCapacity = UNSET_INT;
    // 缓存的最大条数
    long maximumSize = UNSET_INT;
    // 过期时间
    long expireTime = UNSET_INT;

    CacheSpec(String spec) {
        log.debug("cache spec: {}", spec);
        this.spec = spec;
    }

    public static CacheSpec parse(@NonNull String spec) {
        CacheSpec cacheSpec = new CacheSpec(spec);
        cacheSpec.build();
        return cacheSpec;
    }

    void build() {
        for (String option : this.spec.split(SPLIT_OPTIONS)) {
            this.parseOption(option);
        }
    }

    void parseOption(String option) {
        if (option.isEmpty()) {
            return;
        }

        @SuppressWarnings("StringSplitter")
        String[] keyAndValue = option.split(SPLIT_KEY_VALUE);
        requireArgument(
                keyAndValue.length <= 2,
                "key-value pair %s with more than one equals sign",
                option);

        String key = keyAndValue[0].trim();
        String value = (keyAndValue.length == 1) ? null : keyAndValue[1].trim();

        configure(key, value);
    }

    void configure(String key, @Nullable String value) {
        switch (key) {
            case "initialCapacity":
                initialCapacity(key, value);
                return;
            case "maximumSize":
                maximumSize(key, value);
                return;
            case "expireTime":
                expireTime(key, value);
                return;
            default:
                specMap.put(key, value);
        }
    }

    void initialCapacity(String key, @Nullable String value) {
        requireArgument(
                initialCapacity == UNSET_INT,
                "initial capacity was already set to %,d",
                initialCapacity);
        initialCapacity = parseInt(key, value);
    }

    /** Configures the maximum size. */
    void maximumSize(String key, @Nullable String value) {
        requireArgument(
                maximumSize == UNSET_INT, "maximum size was already set to %,d", maximumSize);
        maximumSize = parseLong(key, value);
    }
    /** Configures the expireTime size. */
    void expireTime(String key, @Nullable String value) {
        requireArgument(expireTime == UNSET_INT, "maximum size was already set to %,d", expireTime);
        expireTime = parseLong(key, value);
    }

    public Long getExpireTime() {
        return this.expireTime;
    }

    public String getCaffeineSpec() {
        String caffeineSpec = "";
        List<String> specList = new ArrayList<>();
        if (initialCapacity != UNSET_INT) {
            specList.add("initialCapacity" + SPLIT_KEY_VALUE + initialCapacity);
        }
        if (maximumSize != UNSET_INT) {
            specList.add("maximumSize" + SPLIT_KEY_VALUE + maximumSize);
        }
        if (expireTime != UNSET_INT) {
            specList.add("expireAfterWrite" + SPLIT_KEY_VALUE + expireTime + "s");
        }
        if (MapUtils.isNotEmpty(specMap)) {
            specMap.entrySet()
                    .forEach(
                            entry -> {
                                specList.add(entry.getKey() + SPLIT_KEY_VALUE + entry.getValue());
                            });
        }
        if (CollectionUtils.isNotEmpty(specList)) {
            caffeineSpec = String.join(SPLIT_OPTIONS, specList);
        }
        return caffeineSpec;
    }

    static void requireArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    @FormatMethod
    static void requireArgument(boolean expression, String template, @Nullable Object... args) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(template, args));
        }
    }

    static int parseInt(String key, @Nullable String value) {
        requireArgument((value != null) && !value.isEmpty(), "value of key %s was omitted", key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("key %s value was set to %s, must be an integer", key, value), e);
        }
    }

    /** Returns a parsed long value. */
    static long parseLong(String key, @Nullable String value) {
        requireArgument((value != null) && !value.isEmpty(), "value of key %s was omitted", key);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    String.format("key %s value was set to %s, must be a long", key, value), e);
        }
    }
}
