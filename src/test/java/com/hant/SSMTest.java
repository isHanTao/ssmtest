package com.hant;


import com.hant.bean.Book;
import com.hant.bean.User;
import com.hant.repository.BookElasticSearchService;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　＞　　　＜　┃
 * ┃　　　　　　　┃
 * ┃...　⌒　...　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * ┃　　　┃
 * ┃　　　┃
 * ┃　　　┃
 * ┃　　　┃  神兽保佑
 * ┃　　　┃  代码无bug
 * ┃　　　┃
 * ┃　　　┗━━━┓
 * ┃　　　　　　　┣┓
 * ┃　　　　　　　┏┛
 * ┗┓┓┏━┳┓┏┛
 * ┃┫┫　┃┫┫
 * ┗┻┛　┗┻┛
 *
 * @author ：Hant
 * @date ：Created in 2019/5/27 11:13
 * @description：
 */
@RunWith(SpringJUnit4ClassRunner.class)//表示整合JUnit4进行测试
@ContextConfiguration(locations={"classpath:config/spring-redis.xml",
        "classpath:config/spring-dao.xml",
        "classpath:config/spring-elasticsearch.xml"})//加载spring配置文件

public class SSMTest {
        @Autowired
        RedisTemplate<String,Object> redisTemplate;
        @Autowired
        BookElasticSearchService service;

        @Test
        public void elasticClient() throws UnknownHostException {
                Settings settings = Settings.builder().put("cluster.name", "docker-cluster").build();
                // 创建client
                TransportClient client = new PreBuiltTransportClient(settings)
                        .addTransportAddresses(new TransportAddress(InetAddress.getByName("www.hant.live"), 9300));
                User user = new User();
                user.setEmail("937626747@qq.com");
                user.setName("hant");
                user.setPassword("123456789");
                client.prepareIndex("test","student","1").setSource(user);
                System.out.println(client);
        }


        @Test
        public void DaoTest(){
                Jedis jedis =  new Jedis("47.107.228.235",6379);
                System.out.println();
        }
        @Test
        public void RedisTest(){
                User user = new User();
                user.setEmail("937626747@qq.com");
                user.setName("hant");
                user.setPassword("123456789");
                redisTemplate.opsForValue().set("hant",user);
                System.out.println(redisTemplate.opsForValue().get("hant"));
        }

        @Test
        public void contextLoads() {
                Book book = new Book();
                book.setName("西游记");
                book.setPrice(123f);
                book.setId(1);
                service.save(book);
        }
        @Test
        public void search(){
                Iterable<Book> all = service.findAll();
                for (Book book :
                        all) {
                        System.out.println(book);
                }
        }
}
