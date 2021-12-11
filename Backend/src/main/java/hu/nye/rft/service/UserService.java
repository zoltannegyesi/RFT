package hu.nye.rft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hu.nye.rft.model.Subject;
import hu.nye.rft.model.User;
import hu.nye.rft.repository.SubjectRepository;
import hu.nye.rft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    public List<Subject> findAllSubject() {
        return subjectRepository.findByTook(false);
    }

    public void createUser(String userName) {
        User user = new User();
        user.setName(userName);
        this.userRepository.save(user);
    }

    public List<Subject> findTakenSubjects(String userName) {
        return this.subjectRepository.findByTook(true);
    }

    public User findUserByName(String name) {
        return this.userRepository.findByName(name).get(0);
    }

    public boolean testCode(String subjectCode) {
        return this.subjectRepository.findAll().stream().noneMatch(subject -> subject.getCode().equals(subjectCode));
    }

    public void takeSubject(String subjectCode) {
        if (!testCode(subjectCode)) {
            Subject subject = this.subjectRepository.findByCode(subjectCode);
            subject.setTook(true);
            this.subjectRepository.save(subject);
        } else {
            System.out.println("There is no Subject with this code!");
        }

    }

    public void saveSubject(Subject subject) {
        subject.setTook(false);
        this.subjectRepository.save(subject);
    }

    public void createSubjects() {
        this.subjectRepository.save(new Subject("Rendszerközeli Programozás", "BPI2119", "Discord", "19:00", false));
        this.subjectRepository.save(new Subject("RFT", "BPI2118", "Teams", "11:00", false));
        this.subjectRepository.save(new Subject("Analízis", "BPI1111", "D9", "15:00", false));
    }
}
