package exercise;

import exercise.daytimes.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {

    @Autowired
    Meal meal;

    @Autowired
    Daytime dayTime;

    @GetMapping("/daytime")
    public String daytime() {
        return String.format("It is %s now. Enjoy your %s", dayTime.getName(), meal.getMealForDaytime(dayTime));
    }
}
// END
