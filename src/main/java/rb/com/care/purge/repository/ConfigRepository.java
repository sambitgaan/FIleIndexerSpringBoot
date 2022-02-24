package rb.com.care.purge.repository;

import org.springframework.data.repository.CrudRepository;
import rb.com.care.purge.model.Config;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository extends CrudRepository<Config, Long> {

    List<Config> findAll();

    Optional<Config> findByConfigId(Long configId);

    Config findByUserId(Long userId);
}
