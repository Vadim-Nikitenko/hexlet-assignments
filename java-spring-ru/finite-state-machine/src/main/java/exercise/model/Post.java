package exercise.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.GenerationType;

import static exercise.model.PostState.*;
import static exercise.model.PostState.CREATED;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Lob
    private String body;

    private PostState state = CREATED;

    // BEGIN
    public boolean publish() {
        if (state.equals(CREATED)) {
            this.state = PUBLISHED;
            return true;
        }
        return false;
    }

    public boolean archive() {
        if (state.equals(CREATED) || state.equals(PUBLISHED)) {
            this.state = ARCHIVED;
            return true;
        }
        return false;
    }
    // END
}
