package travelbeeee.communityPjt.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "travelbeeee.communityPjt.repository")
public class MyBatisConfiguration {
}
