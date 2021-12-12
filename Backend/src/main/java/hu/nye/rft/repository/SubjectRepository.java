package hu.nye.rft.repository;

import java.util.List;

import hu.nye.rft.model.Subject;
import hu.nye.rft.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, String> {
    List<Subject> findAll();
    Subject findByCodeAndUser(String code, User user);
    List<Subject> findByTookAndUser(Boolean took, User user);
}
