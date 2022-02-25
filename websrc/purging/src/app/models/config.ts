export class Config {
    configId!: string;
    dirPath!: string;
    indexDirPath!: string;
    searchedFilesPathlog!: string;
    removedFilesLogPath!: string;
    filesLogPath!: string;
    userId!: string;
}

export class IndexResponse {
    data!: object;
    message!: string;
    status!: string;
}