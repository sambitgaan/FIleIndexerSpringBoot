package rb.com.care.purge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rb.com.care.purge.model.Config;
import rb.com.care.purge.model.ConfigRequestDto;
import rb.com.care.purge.service.ConfigService;
import rb.com.care.purge.util.ResourceNotFoundException;

import javax.validation.Valid;

@RestController
@RequestMapping("config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/{id}")
    public ResponseEntity<Config> getConfigDetails(@PathVariable(value = "id") Long configId)
            throws ResourceNotFoundException {
        Config config = configService.findByConfigId(configId)
                .orElseThrow(() -> new ResourceNotFoundException("Config not found for this id :: " + configId));
        return ResponseEntity.ok().body(config);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Config> getConfigDetailsByUserId(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        Config config = configService.findConfigByUserId(userId);
        return ResponseEntity.ok().body(config);
    }

    @PostMapping("/save")
    public Config saveConfig(@Valid @RequestBody ConfigRequestDto config) {
        return configService.saveOrUpdate(config);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Config> updateConfig(@PathVariable(value = "id") Long configId,
                                              @Valid @RequestBody ConfigRequestDto configDetails) throws ResourceNotFoundException {
        final Config updateConfig = configService.saveOrUpdate(configDetails);
        return ResponseEntity.ok(updateConfig);
    }
}
