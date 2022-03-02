package rb.com.care.purge.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rb.com.care.purge.model.Users;
import rb.com.care.purge.repository.UsersRepository;
import rb.com.care.purge.service.ConfigService;
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

    @Autowired
    private ConfigService configService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public List<Users> findAll(){
        List<Users> users = new ArrayList<Users>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public Users saveOrUpdate(Users user){
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        usersRepository.save(user);


//        Config config = new Config();
//        config.setUserId(user.getUserId());
//        config.setDirPath("");
//        config.setIndexDirPath("");
//        config.setRemovedFilesLogPath("");
//        config.setFilesLogPath("");
//        config.setSearchedFilesPathlog("");
//        config.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//        config.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//        configService.saveOrUpdate(config);
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
