package fun.haolo.bigLandlord;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author haolo
 * @Date 2022-10-11 12:05
 * @Description springBoot 启动器
 */
@SpringBootApplication(scanBasePackages = {"fun.haolo.bigLandlord"})
@MapperScan({"fun.haolo.bigLandlord.db.mapper"})
public class BigLandlordApplication {
    public static void main(String[] args) {
        SpringApplication.run(BigLandlordApplication.class, args);
    }
}