package cn.onetozero.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;

/**
 * 类描述：具有序列化Key前缀的RedisTemplate
 * 作者：徐卫超 (cc)
 * 时间 2022/6/16 11:11
 */

public class PrefixJacksonRedisTemplate extends AbstractRedisTemplate<String, Object> {

    /**
     * Constructs a new <code>StringRedisTemplate</code> instance. {@link #setConnectionFactory(RedisConnectionFactory)}
     * and {@link #afterPropertiesSet()} still need to be called.
     */
    public PrefixJacksonRedisTemplate(RedisSerializer<String> keyRedisSerializer) {
        setKeySerializer(keyRedisSerializer);
        setStringSerializer(keyRedisSerializer);
        setValueSerializer(RedisSerializer.json());
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(RedisSerializer.json());
    }


    /**
     * Constructs a new <code>StringRedisTemplate</code> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    @Autowired
    public PrefixJacksonRedisTemplate(RedisConnectionFactory connectionFactory, RedisSerializer<String> keyRedisSerializer) {
        this(keyRedisSerializer);
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
