package rb.com.care.purge.model;

import org.apache.catalina.User;

import javax.persistence.*;
import java.sql.Timestamp;

public class ConfigRequestDto {

    private String dirPath;
    private String indexDirPath;
    private String removedFilesLogPath;
    private String filesLogPath;
    private long userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getIndexDirPath() {
        return indexDirPath;
    }

    public void setIndexDirPath(String indexDirPath) {
        this.indexDirPath = indexDirPath;
    }

    public String getRemovedFilesLogPath() {
        return removedFilesLogPath;
    }

    public void setRemovedFilesLogPath(String removedFilesLogPath) {
        this.removedFilesLogPath = removedFilesLogPath;
    }

    public String getFilesLogPath() {
        return filesLogPath;
    }

    public void setFilesLogPath(String filesLogPath) {
        this.filesLogPath = filesLogPath;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
