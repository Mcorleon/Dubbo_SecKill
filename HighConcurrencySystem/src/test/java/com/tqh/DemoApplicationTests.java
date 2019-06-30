package com.tqh;

import com.tqh.util.SnowFlake;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages={"com.tqh"})
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        String hashAlgorithmName = "MD5";
        String credentials = "123456";
        int hashIterations = 10;
        ByteSource credentialsSalt = ByteSource.Util.bytes("18579113150");
        Object obj = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
        System.out.println(obj);
    }


    @Test
    public void snowFlake() {
        SnowFlake snowFlake=new SnowFlake(1,1);
        for(int i=0;i<10 ;i++){
            System.out.println(snowFlake.nextId());
        }
    }

}
