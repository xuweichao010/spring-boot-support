package cn.onetozero.data.redis.config;

import cn.onetozero.data.redis.JacksonRedisTemplate;
import cn.onetozero.data.redis.ObjectRedisTemplate;
import cn.onetozero.data.redis.PrefixJacksonRedisTemplate;
import cn.onetozero.data.redis.PrefixStringRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/12/23 15:48
 */

@Configuration
@ConditionalOnClass(RedisOperations.class)
public class RedisSupportAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "jacksonRedisTemplate")
    public JacksonRedisTemplate jacksonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new JacksonRedisTemplate(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean(name = "objectRedisTemplate")
    public ObjectRedisTemplate objectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new ObjectRedisTemplate(redisConnectionFactory);
    }


    @Bean
    @ConditionalOnProperty("spring.application.name")
    @ConditionalOnMissingBean(name = "applicationPrefixJacksonRedisTemplate")
    public PrefixJacksonRedisTemplate applicationPrefixJacksonRedisTemplate(
            @Value("spring.application.name") String application,
            RedisConnectionFactory redisConnectionFactory) {
        PrefixStringRedisSerializer prefixStringRedisSerializer = new PrefixStringRedisSerializer(application);
        return new PrefixJacksonRedisTemplate(redisConnectionFactory, prefixStringRedisSerializer);
    }
}
