package hu.nye.rft.service;

import java.util.List;

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

    public User createUser(String userName) {
        User user = new User();
        user.setName(userName);
        this.userRepository.save(user);
        return user;
    }

    public List<Subject> findTakenSubjects() {
        return this.subjectRepository.findByTook(true);
    }

    public boolean testCode(String subjectCode) {
        return this.subjectRepository.findAll().stream().noneMatch(subject -> subject.getCode().equals(subjectCode));
    }

    public Subject takeSubject(String subjectCode) {
        if (!testCode(subjectCode)) {
            Subject subject = this.subjectRepository.findByCode(subjectCode);
            subject.setTook(true);
            this.subjectRepository.save(subject);
            return subject;
        } else {
            System.out.println("There is no Subject with this code!");
            return null;
        }
    }

    public boolean saveSubject(Subject subject) {
        subject.setTook(false);
        this.subjectRepository.save(subject);
        return subject.getTook();
    }

    public void createSubjects(Subject subject1, Subject subject2, Subject subject3) {
        this.subjectRepository.save(subject1);
        this.subjectRepository.save(subject2);
        this.subjectRepository.save(subject3);
    }
}
