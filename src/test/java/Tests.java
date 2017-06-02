import com.disagree.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

/**
 * Created by disagree on 2017/5/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class Tests {

    @Test
    public void test() throws Exception {
        Jedis  redis = new Jedis("localhost",6379);
        // 保存字符串
       redis.hset("appId:2222:visit","1400","1");
       redis.hincrBy("appId:2222:visit","1401",1);
        System.out.println(redis.hget("appId:2333:visit","1400"));
    }
}
