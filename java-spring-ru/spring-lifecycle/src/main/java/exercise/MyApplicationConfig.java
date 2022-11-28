package exercise;

import exercise.daytimes.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

// BEGIN
@Configuration
public class MyApplicationConfig {

    @Bean
    public Daytime getDayTime() {
        int hour = LocalDateTime.now().getHour();
        if (hour >= 6 && hour < 12) {
            return new Morning();
        }
        if (hour >= 12 && hour < 18) {
            return new Day();
        }
        if (hour >= 18 && hour < 23) {
            return new Evening();
        }
        return new Night();
    }

}
// END
