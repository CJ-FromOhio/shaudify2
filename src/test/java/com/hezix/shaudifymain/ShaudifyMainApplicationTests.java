package com.hezix.shaudifymain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class ShaudifyMainApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void concatTest(){
        String str1 = "hello ";
        String str2 = "world";
        Assertions.assertEquals("hello world", str1.concat(str2));
    }
}
