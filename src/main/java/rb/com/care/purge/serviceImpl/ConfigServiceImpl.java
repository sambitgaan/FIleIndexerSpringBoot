package rb.com.care.purge.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import rb.com.care.purge.model.Config;
import rb.com.care.purge.model.ConfigRequestDto;
import rb.com.care.purge.repository.ConfigRepository;
import rb.com.care.purge.repository.UsersRepository;
import rb.com.care.purge.service.ConfigService;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Configuration
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public Config saveOrUpdate(Config configDetails){

        Config config = configRepository.findByUserId(configDetails.getUserId());
        if(config == null){
            config = new Config();
        }
        config.setDirPath(configDetails.getDirPath());
        config.setIndexDirPath(configDetails.getIndexDirPath());
        config.setRemovedFilesLogPath(configDetails.getRemovedFilesLogPath());
        config.setFilesLogPath(configDetails.getFilesLogPath());
        config.setUserId(configDetails.getUserId());
        config.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        config.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        configRepository.save(config);
        return config;
    }

    @Override
    public Optional<Config> findByConfigId(long configId){
        return configRepository.findByConfigId(configId);
    }

    @Override
    public Config findConfigByUserId(long userId){
        return configRepository.findByUserId(userId);
    }
}
