package com.hezix.shaudifymain.annotations;

import com.hezix.shaudifymain.ShaudifyMainApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest()
@ActiveProfiles("test")
@Transactional
@Sql({
        "classpath:sql/data.sql"
})
public @interface Integration {
}
