package rb.com.care.purge.serviceImpl;

import org.springframework.stereotype.Service;
import rb.com.care.purge.service.DeleteService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

@Service
public class DeleteServiceImpl implements DeleteService {

    @Override
    public String deleteFiles() {

        findIndexAndDeleteFiles();
        return null;
    }

    private static void findIndexAndDeleteFiles() {
        int filesDeleted = 0;
        int filesNotDeleted = 0;
        try {
            File searchedFile = new File("target\\classes\\Search\\SearchFiles.txt");
            File logFile = new File("target\\classes\\Input\\List.txt");
            BufferedReader br = new BufferedReader(new FileReader(searchedFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(logFile));
            String fileName;
            while ((fileName = br.readLine()) != null) {
                if (Files.deleteIfExists(Paths.get(fileName))) {
                    filesDeleted++;
                    bw.write(fileName + " : deleted" + "\n");
                } else {
                    filesNotDeleted++;
                    bw.write(fileName + " : not deleted" + "\n");
                }
            }
            br.close();
            bw.write("Total files deleted: " + Integer.toString(filesDeleted) + "\n");
            bw.write("Total files not deleted: " + Integer.toString(filesNotDeleted) + "\n");
            bw.close();
        } catch(NoSuchFileException e) {
            filesNotDeleted++;
            System.out.println("No such file/directory exists: " + "target\\classes\\Search\\SearchFiles.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Files deleted: " + Integer.toString(filesDeleted) + " Files not deleted: " + Integer.toString(filesNotDeleted));
    }
}
