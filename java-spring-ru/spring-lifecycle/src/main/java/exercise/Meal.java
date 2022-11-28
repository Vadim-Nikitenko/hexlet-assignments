package exercise;

import exercise.daytimes.Daytime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;

// BEGIN
@Component
// END
public class Meal {
    public String getMealForDaytime(Daytime daytime) {
        switch (daytime.getName()) {
            case "morning":
                return "breakfast";
            case "day":
                return "lunch";
            case "evening":
                return "dinner";
            default:
                return "nothing :)";
        }
    }

    // Для самостоятельной работы
    // BEGIN
    
    // END
}
