package hu.nye.rft.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;
    private String code;
    private String name;
    private String classRoom;
    private String time;
    private Boolean took;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Subject(String name, String code, String classRoom, String time, Boolean took, User user) {
        this.name = name;
        this.code = code;
        this.classRoom = classRoom;
        this.time = time;
        this.took = took;
        this.user = user;
    }
}
