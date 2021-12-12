package hu.nye.rft.service;

import hu.nye.rft.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final UserService userService;

    @Autowired
    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/takesubject/{userName}")
    public String takeSubjects(Model model, @PathVariable String userName) {
        if (!userName.equals("index.css")) {
            this.userService.createUser(userName);
            model.addAttribute("subjects", userService.findAllSubject(userName));
            model.addAttribute("tookSubjects", userService.findTakenSubjects(userName));
            model.addAttribute("userName", userName);
        }
        return "takesubject";
    }


    @GetMapping("/addsubjects/{userName}")
    public String getSubjectsPage(@PathVariable String userName) {
        return "addsubjects";
    }

    @PostMapping("/addsubjects/{userName}")
    public String addSubject(Subject subject, @PathVariable String userName) {
        this.userService.saveSubject(subject);
        return "addsubjects";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/takesubject/{userName}")
    public String takeSubject(@PathVariable String userName, @RequestParam("subjectCode") String subjectCode, Model model) {
        this.userService.takeSubject(subjectCode, userName);
        model.addAttribute("subjects", userService.findAllSubject(userName));
        model.addAttribute("tookSubjects", userService.findTakenSubjects(userName));
        return "takesubject";
    }
}
