package hu.nye.rft.service;

import java.util.List;

import hu.nye.rft.model.Subject;
import hu.nye.rft.model.User;
import hu.nye.rft.repository.SubjectRepository;
import hu.nye.rft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    public List<Subject> findAllSubject(String userName) {
        User user = this.userRepository.findByName(userName).get(0);
        return subjectRepository.findByTookAndUser(false, user);
    }

    public void createUser(String userName) {
        User user = new User();
        user.setName(userName);
        List<Subject> subjects = this.createSubjects(user);
        user.setSubjects(subjects);
        this.userRepository.save(user);
        this.subjectRepository.saveAll(subjects);
    }

    public List<Subject> findTakenSubjects(String userName) {
        User user = this.userRepository.findByName(userName).get(0);
        return this.subjectRepository.findByTookAndUser(true, user);
    }

    public boolean testCode(String subjectCode) {
        return this.subjectRepository.findAll().stream().noneMatch(subject -> subject.getCode().equals(subjectCode));
    }

    public void takeSubject(String subjectCode, String userName) {
        User user = this.userRepository.findByName(userName).get(0);
        if (!testCode(subjectCode)) {
            Subject subject = this.subjectRepository.findByCodeAndUser(subjectCode, user);
            subject.setTook(true);
            subject.setUser(user);
            user.getSubjects().add(subject);
            this.subjectRepository.save(subject);
            System.out.println("Subject has been created!");
        } else {
            System.out.println("There is no Subject with this code!");
        }

    }

    public void saveSubject(Subject subject) {
        subject.setTook(false);
        this.subjectRepository.save(subject);
    }

    public List<Subject> createSubjects(User user) {
        return List.of(
                new Subject("Rendszerközeli Programozás", "BPI2119", "Discord", "19:00", false, user),
                new Subject("RFT", "BPI2118", "Teams", "11:00", false, user),
                new Subject("Analízis", "BPI1111", "D9", "15:00", false, user)
        );

    }
}
