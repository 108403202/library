package book_use;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc//配置將攔截器註冊進去
public class SpringbootLibraryApplication {
    public static void main(String[] args) {

        SpringApplication.run(SpringbootLibraryApplication.class, args);
    }
}
