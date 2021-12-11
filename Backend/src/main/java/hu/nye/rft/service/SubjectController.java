package hu.nye.rft.service;

import hu.nye.rft.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubjectController {
    private UserService userService;

    @Autowired
    public SubjectController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addsubjects")
    public void addSubject(Subject subject) {
        this.userService.saveSubject(subject);
    }



}
