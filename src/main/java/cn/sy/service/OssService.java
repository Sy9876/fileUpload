package cn.sy.service;

import cn.sy.configuration.OssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OssService {

    private OSS ossClient;
    private OssProperties ossProperties;

    public OssService(OSS ossClient, OssProperties ossProperties) {
        this.ossClient = ossClient;
        this.ossProperties = ossProperties;
    }

    public List<String> ossFiles() {
        System.out.println("OssService ossFiles.  listing " + ossProperties.getBucketName());

        ObjectListing objectListing = ossClient.listObjects(ossProperties.getBucketName());
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            System.out.println("\t" + s.getKey());
        }
        List<String> result = sums.stream()
                .map(OSSObjectSummary::getKey)
                .collect(Collectors.toList());

        return result;
    }

    public String downloadFile(String objectName) {
        if(objectName==null || "".equals(objectName)) {
            return "ERROR. objectName is null";
        }
        String bucketName = ossProperties.getBucketName();
        System.out.println("OssService downloadFile.  save file " + bucketName + "/" + objectName);

        boolean isExist = ossClient.doesObjectExist(bucketName, objectName);
        if(!isExist) {
            return "ERROR. " + objectName + " not exist.";
        }

        String baseDownloadPath = ossProperties.getBaseDownloadPath();
        if(!Files.isDirectory(Paths.get(baseDownloadPath))) {
            return "ERROR. baseDownloadPath " + baseDownloadPath + " not exist.";
        }

        String filename = objectName.replaceAll(".*/", "");

        Path filePath = Paths.get(baseDownloadPath, filename);
        if(Files.exists(filePath)) {
            return "ERROR. " + filePath.toAbsolutePath() + " already exist.";
        }
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), filePath.toFile());

        return "OK. " + objectName + " saved to " + filePath.toAbsolutePath();
    }
}
