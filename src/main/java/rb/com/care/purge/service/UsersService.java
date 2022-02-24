package rb.com.care.purge.service;

import rb.com.care.purge.model.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    List<Users> findAll();

    Users saveOrUpdate(Users user);

    Optional<Users> findByUserId(long userId);

    Optional<Users> findByUserName(String userName);
}
