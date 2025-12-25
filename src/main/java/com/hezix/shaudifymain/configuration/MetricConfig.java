package com.hezix.shaudifymain.configuration;

import com.hezix.shaudifymain.controller.rest.UserRestController;
import com.hezix.shaudifymain.controller.web.UserController;
import com.hezix.shaudifymain.entity.user.Role;
import com.hezix.shaudifymain.service.user.UserService;
import com.hezix.shaudifymain.util.filter.UserFilter;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfig {
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
    @Bean
    MeterBinder meterBinder(UserService userService) {
        return registry -> {
            Counter.builder("mainController_count")
                    .description("количество обращений на главную страницу")
                    .register(registry);
            Counter.builder("mainController_count_by_username")
                    .description("количество обращений по username")
                    .tag("username","none")
                    .register(registry);
            Gauge.builder("users_count", userService, (service) -> (double) service.countAllUsers())
                    .description("Количество аккаунтов")
                    .register(registry);
        };
    }
}
