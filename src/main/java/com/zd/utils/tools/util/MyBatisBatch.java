package com.zd.utils.tools.util;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 能批量添加和更新
 * created by liurenjin on 2018/8/26
 */
@Component
public class MyBatisBatch {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisBatch.class);

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public <T> void doBatch(Class<T> daoClass, Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        if (sqlSessionFactory == null) {
            logger.error("无法获取mybatis sqlSessionFactory,请检查mybatis配置");
            throw new RuntimeException("mybatis配置错误");
        }
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            T mapper = sqlSession.getMapper(daoClass);
            consumer.accept(mapper);
            sqlSession.commit();
        } catch (Exception e) {
            logger.error("批量操作" + daoClass + "失败", e);
            throw new RuntimeException("批量操作失败");
        }
    }
}
