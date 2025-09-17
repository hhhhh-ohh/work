local currentTime = ARGV[1]
local result = {}

-- 遍历所有传入的SKU键
for j = 1, #KEYS do
    local skuKey = KEYS[j]
    skuKey = 'goods_info_marketing:' .. skuKey
    redis.log(redis.LOG_WARNING, 'Processing SKU Key: ' .. skuKey)
    local bulk = redis.call('HGETALL', skuKey)
    local v = {}
    local skuId = nil  -- 定义一个外部变量来存储 skuId
    for i = 1, #bulk, 2 do
        local nextKey = bulk[i]
        local data = bulk[i + 1]
        local value = cjson.decode(data)
        local endTime = value.endTime

        if endTime < currentTime then
            redis.log(redis.LOG_WARNING, 'Deleting expired key: ' .. nextKey)
            redis.call('HDEL', skuKey, nextKey)
        else
            v[nextKey] = data
            redis.log(redis.LOG_WARNING, 'Keeping valid key: ' .. nextKey)
        end
        redis.log(redis.LOG_WARNING, 'Keeping valid key end: ' .. nextKey)
        skuId = value.skuId  -- 将 skuId 提取出来
    end
    if skuId then  -- 检查 skuId 是否存在
        result[skuId] = cjson.encode(v)
    end
end
local str = cjson.encode(result)
redis.log(redis.LOG_WARNING, 'Final result: ' .. str)
return str