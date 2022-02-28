package com.sts;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class UploadHelper {
	//public final String upload="\\fileupload\\src\\main\\resources\\static\\image";
	
	
	
	
	public boolean uploadFile(MultipartFile multipartFile) {
		boolean f=false;
		try {
			String upload=new ClassPathResource("").getFile().getAbsolutePath(); //uploading file to non static location, that is same location can be used on multiple systems
			System.out.println(upload);
			Files.copy(multipartFile.getInputStream(), Paths.get(upload+File.separator+multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING); //way to save uploaded file
			f=true;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return f;
	}
}
