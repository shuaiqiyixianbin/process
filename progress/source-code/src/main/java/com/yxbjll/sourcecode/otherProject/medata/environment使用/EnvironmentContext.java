package com.yxbjll.sourcecode.otherProject.medata.environment使用;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author yxbjll
 * @date 2020/2/29
 * @desc 配置变量，用于
 * 说明：@PropertySource value = "classpath:properties/config.properties" 意味着从该 classpath 下面找配置文件
 *       ignoreResourceNotFound = true ：资源找不到不报错
 */
@Data
@Component
@PropertySource(value = "classpath:properties/config.properties", ignoreResourceNotFound = true)
public class EnvironmentContext {

    @Autowired
    private Environment environment;

    public String getBrokerList() {
        return environment.getProperty("kafka.broker.list");
    }

    public String getHiveDdlTopic() {
        return environment.getProperty("kafka.etl.hive.ddl", "bigdata_service_etl_hive_ddl");
    }

    public String getEtlSqlTopic() {
        return environment.getProperty("kafka.etl.sql.topic", "bigdata_service_etl_sql");
    }

    public String getMetaStoreTopic() {
        return environment.getProperty("kafka.hive.metastore.topic", "bigdata_service_etl_hive_metastore");
    }

    public String getKafkaGroupId() {
        return environment.getProperty("kafka.group.id", "bigdata_medata");
    }

    public Integer getKafkaConsumerPollDuration() {
        return Integer.parseInt(environment.getProperty("kafka.consumer.poll.duration", "5"));
    }

    public Integer getKafkaPartitionNum() {
        return Integer.parseInt(environment.getProperty("kafka.partition.num", "6"));
    }

    public String getMasterLock() {
        return environment.getProperty("master.lock", "medata_master_lock");
    }

    public Integer getClusterNum() {
        return Integer.parseInt(environment.getProperty("cluster.num", "1"));
    }

    public String getClickHouseJdbc() {
        return environment.getProperty("clickhouse.jdbc");
    }

    public String getClickHouseUserName() {
        return environment.getProperty("clickhouse.userName");
    }

    public String getClickHousePassword() {
        return environment.getProperty("clickhouse.password");
    }

    public String getClickHouseDatabase() {
        return environment.getProperty("clickhouse.database");
    }

    public String getRedisMasterName() {
        return environment.getProperty("redis.master.name");
    }

    public String getRedisSentinelNodes() {
        return environment.getProperty("redis.sentinel.nodes");
    }

    public String getRedisPassword() {
        return environment.getProperty("redis.password");
    }

    public Integer getRedisDatabase() {
        return Integer.parseInt(environment.getProperty("redis.database", "10"));
    }

    public int getRedisTimeout() {
        return Integer.parseInt(environment.getProperty("redis.timeout", "3000"));
    }

    public int getMaxIdle() {
        return Integer.parseInt(environment.getProperty("redis.max.idle", "30"));
    }

    public int getMaxTotal() {
        return Integer.parseInt(environment.getProperty("redis.max.total", "50"));
    }

    public int getMaxWaitMills() {
        return Integer.parseInt(environment.getProperty("redis.max.wait..millis", "1000"));
    }
}
