package cn.onetozero.data.redis;

import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 类描述：用于添加RedisTemplate通用特性的抽象类
 * 作者：徐卫超 (cc)
 * 时间 2022/6/16 11:11
 */

public abstract class AbstractRedisTemplate<K, V> extends RedisTemplate<K, V> {

    /**
     * redis的集群数量
     */
    private volatile int clusterNodeNumber = -1;


    public int getClusterNodeNumber() {
        return this.clusterNodeNumber;
    }

    /**
     * 获取集群的数量
     */
    public synchronized void initClusterNodeNumber() {
        RedisConnectionFactory connectionFactory = this.getConnectionFactory();
        if (connectionFactory == null) {
            this.clusterNodeNumber = 1;
            return;
        }
        try {
            RedisClusterConnection clusterConnection = connectionFactory.getClusterConnection();
            Iterable<RedisClusterNode> redisClusterNodes = clusterConnection.clusterGetNodes();
            clusterNodeNumber = 0;
            for (RedisClusterNode redisClusterNode : redisClusterNodes) {
                if (redisClusterNode.isMaster()) {
                    clusterNodeNumber++;
                }
            }
        } catch (Exception e) {
            this.clusterNodeNumber = 1;
        }
    }
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.initClusterNodeNumber();
    }
}
