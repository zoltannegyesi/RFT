package hu.nye.rft;

import hu.nye.rft.model.Subject;
import hu.nye.rft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Main {

    private static final Subject SUBJECT_1 = new Subject("Rendszerközeli Programozás", "BPI2119", "Discord", "19:00", false);
    private static final Subject SUBJECT_2 = new Subject("RFT", "BPI2118", "Teams", "11:00", false);
    private static final Subject SUBJECT_3 = new Subject("Analízis", "BPI1111", "D9", "15:00", false);

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createSubjects() {
       userService.createSubjects(SUBJECT_1, SUBJECT_2, SUBJECT_3);
    }


}
