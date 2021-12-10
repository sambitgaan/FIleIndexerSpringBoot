package rb.com.care.purge.serviceImpl;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DeleteServiceImplTest {

    @Test
    void deleteFiles() {
        System.out.println("Deleting files, please wait ...");
        Instant deleteStart = Instant.now();
        DeleteServiceImpl deleteService = new DeleteServiceImpl();
        deleteService.deleteFiles();
        Instant deleteStop = Instant.now();
        Duration timeElapsedDelete = Duration.between(deleteStart, deleteStop);
        System.out.println("Time taken for deleting: " + timeElapsedDelete.getSeconds());
    }
}