package hu.nye.rft.model;

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
    private String code;
    private Long id;
    private String name;
    private String classRoom;
    private String time;
    private Boolean took;

    public Subject(String name, String code, String classRoom, String time, Boolean took) {
        this.name = name;
        this.code = code;
        this.classRoom = classRoom;
        this.time = time;
        this.took = took;
    }
}
