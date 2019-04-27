package com.feign;

import com.config.FeignConfig;
import com.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

/**
 * 带hystrix熔断机制和feign负载均衡机制的Service
 */
@FeignClient(value = "service-hi", configuration = FeignConfig.class, fallback = FeignHystrixClient.FeignHystrixClientFallback.class)
public interface FeignHystrixClient {

    @RequestMapping(path = "/hi")
    String welcome(@RequestParam("name") String name);

//    @RequestMapping(value = "/{id}")
//    User findById(@PathVariable("id") Integer id);
//
//    @RequestMapping(value = "/findAll")
//    List<User> findAll();

    @Component
    class FeignHystrixClientFallback implements FeignHystrixClient {
        private final Logger logger = LoggerFactory.getLogger(FeignHystrixClientFallback.class);
        @Override
        public String welcome(@RequestParam  String name) {
            logger.info("调用服务失败，短路器执行");
            return "调用服务失败，短路器执行";
        }
//        @Override
//        public User findById(Integer id) {
//            return new User(id, "调用服务失败，短路器执行");
//        }
//        @Override
//        public List<User> findAll() {
//            return Collections.emptyList();
//        }
    }

}