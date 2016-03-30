import com.ant.beans.User;
import com.ant.user.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

    public class UserTest {
        @Autowired
        private UserService userService;

        public void before(){
            @SuppressWarnings("resource")
            ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml"
                    ,"classpath:conf/spring-mybatis.xml"});
            userService = (UserService) context.getBean("userServiceImpl");
        }

        @Test
        public void addUser(){
            User user = new User();
           /* user.setNickname("你好");
            user.setState(2);
            System.out.println(userService.insertUser(user));*/
        }
    }