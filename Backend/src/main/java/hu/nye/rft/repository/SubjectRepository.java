package hu.nye.rft.repository;

import java.util.List;

import hu.nye.rft.model.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, String> {
    List<Subject> findAll();
    Subject findByCode(String code);
    List<Subject> findByTook(Boolean took);
}
