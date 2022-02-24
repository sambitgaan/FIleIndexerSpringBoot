package rb.com.care.purge.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "config")
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long configId;
    @Column(name = "dir_path", nullable = false)
    private String dirPath;
    @Column(name = "index_dir_path", nullable = false)
    private String indexDirPath;
    @Column(name = "removed_files_log_path", nullable = false)
    private String removedFilesLogPath;
    @Column(name = "files_log_path", nullable = false)
    private String filesLogPath;
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    public Config() {}


    public Config(String dirPath, String indexDirPath, String removedFilesLogPath, String filesLogPath, long userId, Timestamp createdAt, Timestamp updatedAt) {
        this.dirPath = dirPath;
        this.indexDirPath = indexDirPath;
        this.removedFilesLogPath = removedFilesLogPath;
        this.filesLogPath = filesLogPath;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

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

    public void setRemovedFilesLogPath(String removeFilesLogPath) {
        this.removedFilesLogPath = removeFilesLogPath;
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
