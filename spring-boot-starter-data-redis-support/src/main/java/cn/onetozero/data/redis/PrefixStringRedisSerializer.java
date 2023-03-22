package cn.onetozero.data.redis;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;

/**
 * 类描述： 支持自动根据前缀设置主键的缓存
 * 作者：徐卫超 (cc)
 * 时间 2022/10/14 10:26
 */

public class PrefixStringRedisSerializer extends StringRedisSerializer {

    private final String keyPrefix;

    public PrefixStringRedisSerializer(String keyPrefix) {
        if (StringUtils.hasText(keyPrefix)) {
            throw new BeanCreationException("keyPrefix is not null");
        }
        this.keyPrefix = keyPrefix + ":";
    }

    public PrefixStringRedisSerializer(Charset charset, String keyPrefix) {
        super(charset);
        if (StringUtils.hasText(keyPrefix)) {
            throw new BeanCreationException("keyPrefix is not null");
        }
        this.keyPrefix = keyPrefix + ":";
    }

    @Override
    public byte[] serialize(@Nullable String string) {
        return super.serialize(keyPrefix + string);
    }

}
