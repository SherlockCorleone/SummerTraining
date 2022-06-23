package test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


//配置spring和junit整合，junit启动时加载springIOC容器


@RunWith(SpringJUnit4ClassRunner.class)//测试时运行的线程
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})//测试时运行的配置文件
public class BaseTest {

}
