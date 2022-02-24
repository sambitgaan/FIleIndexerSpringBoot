package rb.com.care.purge.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import rb.com.care.purge.model.Users;
import rb.com.care.purge.repository.UsersRepository;
import rb.com.care.purge.service.UsersService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Configuration
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> findAll(){
        List<Users> users = new ArrayList<Users>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public Users saveOrUpdate(Users user){
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        usersRepository.save(user);
        return user;
    }

    @Override
    public Optional<Users> findByUserId(long userId){
        return usersRepository.findByUserId(userId);
    }

    @Override
    public Optional<Users> findByUserName(String userName){
        return usersRepository.findByUserName(userName);
    }

}
