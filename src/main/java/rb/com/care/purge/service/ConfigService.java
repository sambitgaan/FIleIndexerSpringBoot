package rb.com.care.purge.service;

import rb.com.care.purge.model.Config;
import rb.com.care.purge.model.ConfigRequestDto;

import java.util.Optional;

public interface ConfigService {

    Config saveOrUpdate(ConfigRequestDto dto);

    Optional<Config> findByConfigId(long configId);

    Config findConfigByUserId(long userId);
}
