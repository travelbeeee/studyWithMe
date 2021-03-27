package study.signUp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "study.signUp.repository")
public class MyBatisConfiguration {
}
