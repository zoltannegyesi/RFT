package hu.nye.rft.repository;

import java.util.List;

import hu.nye.rft.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);
}
