package cn.onetozero.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.PostConstruct;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/6/16 11:11
 */
public class ObjectRedisTemplate extends RedisTemplate<String, Object> {



    public ObjectRedisTemplate() {
        setKeySerializer(RedisSerializer.string());
        setHashKeySerializer(RedisSerializer.string());
    }

    /**
     * Constructs a new <code>StringRedisTemplate</code> instance ready to be used.
     *
     * @param connectionFactory connection factory for creating new connections
     */
    @Autowired
    public ObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }
}
