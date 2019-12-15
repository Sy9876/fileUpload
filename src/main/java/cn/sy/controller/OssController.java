package cn.sy.controller;

import cn.sy.configuration.OssProperties;
import cn.sy.service.OssService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class OssController {

	@Autowired
	private OSS ossClient;

	@Autowired
	private OssService ossService;

	/*
	 * request:
	 *     http://localhost:8080/
	 */
    @ResponseBody
    @RequestMapping("/oss/files")
    public List<String> ossFiles() {
		System.out.println("OssController ossFiles.");

		return ossService.ossFiles();
    }

	@ResponseBody
	@RequestMapping("/oss/download")
	public String downloadFile(String objectName) {
		System.out.println("OssController downloadFile. " + objectName);

		return ossService.downloadFile(objectName);
	}

}
