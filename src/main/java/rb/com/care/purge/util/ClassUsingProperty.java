package rb.com.care.purge.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClassUsingProperty {

    @Value( "${file.data.directory}" )
    private String directory;

    @Value( "${file.data.index}" )
    private String indexDirectory;

    @Value( "${file.data.mergedDir}" )
    private String mergedDir;

    @Value( "${file.data.list}" )
    private String fileList;

    @Value( "${file.data.search}" )
    private String searchList;

    @Value( "${file.data.mergedSearch}" )
    private String mergedSearch;

    public String getMergedDir() {
        return mergedDir;
    }

    public String getFileList() {
        return fileList;
    }

    public String getSearchList() {
        return searchList;
    }

    public String getMergedSearch() {
        return mergedSearch;
    }

    public String getDirectory() {
        return directory;
    }

    public String getIndexDirectory() {
        return indexDirectory;
    }
}
