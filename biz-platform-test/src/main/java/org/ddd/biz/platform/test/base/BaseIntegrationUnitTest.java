package org.ddd.biz.platform.test.base;

import org.junit.Before;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;


public abstract class BaseIntegrationUnitTest extends BaseDaoUnitTest {

//    @Configuration
//    public static class ElasticSearchConfiguration {
//
//        @Bean
//        public ElasticClient elasticClient() {
//            ElasticClient elasticClient = new ElasticClient();
//            elasticClient.setHost("172.16.248.134,172.16.248.135");
//            elasticClient.setPort(9210);
//            elasticClient.setClusterName("Search_Cluster");
//            return elasticClient;
//        }
//
//        @Bean
//        public ElasticSearchSimpleClient elasticSearchSimpleClient(ElasticClient elasticClient) {
//            return new ElasticSearchSimpleClient(elasticClient);
//        }
//    }

    @Configuration
    @ComponentScan(basePackages = "org.ddd.biz.platform.framework.converter")
    public static class ConverterConfiguration {

        @Bean
        public String test() {
            return "";
        }
    }

    @Before
    public void init() {
        super.init();
    }


}
