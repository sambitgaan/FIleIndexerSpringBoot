package rb.com.care.purge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Indexed;
import rb.com.care.purge.model.Users;

import java.util.Optional;

@Indexed
public interface UsersRepository extends CrudRepository<Users, Long> {

    Optional<Users> findByUserId(long userId);

    Optional<Users> findByUserName(String userName);
}
