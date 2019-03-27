package cn.sy.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

	/**
	 * 上传文件保存目录
	 */
	@Value("${sy.uploaddir}")
	private String dir;

	/*
	 * request:
	 *     http://localhost:8080/
	 */
    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
		System.out.println("UploadController upload. getOriginalFilename=" + file.getOriginalFilename());
		System.out.println("UploadController upload. getName=" + file.getName());
		System.out.println("UploadController upload. getSize=" + file.getSize());

		Path savedPath = null;
		try {
			Path uploadPath = Paths.get(dir);
//			System.out.println("save to path: " + uploadPath.toAbsolutePath());
//			String originalFilename = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
			String originalFilename = file.getOriginalFilename();
			savedPath = uploadPath.resolve(originalFilename);
			if(Files.exists(uploadPath)) {
				if(Files.isDirectory(uploadPath)) {
				}
				else {
					// uploadPath存在但却不是文件夹，报错
					System.out.println("upload failed. " + uploadPath.toString() + " is not directory");
					return "upload failed." ;
				}
			}
			else {
				// uploadPath不存在则创建文件夹
				Files.createDirectories(uploadPath);
			}
			System.out.println("save to " + savedPath.toAbsolutePath().toString());

			// transferTo 传入的File对象一定是绝对路径才行
			file.transferTo(savedPath.toAbsolutePath().toFile());
			System.out.println("upload success.");
		} catch (Exception e) {
			System.out.println("upload failed.");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
    	return "upload success. save to " + savedPath.toAbsolutePath().toString();
    }
}
