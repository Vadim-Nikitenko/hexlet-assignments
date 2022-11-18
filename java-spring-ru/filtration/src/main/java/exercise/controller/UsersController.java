package exercise.controller;

import com.querydsl.core.types.Predicate;
import exercise.model.QUser;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static java.util.Collections.emptyList;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // BEGIN
    @GetMapping
    public Iterable<User> getUsers(@QuerydslPredicate(root = User.class) Predicate predicate) {
        return userRepository.findAll(predicate);
    }
    // END
}

