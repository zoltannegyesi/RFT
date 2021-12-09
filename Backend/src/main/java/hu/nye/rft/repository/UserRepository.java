package hu.nye.rft.repository;

import hu.nye.rft.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
