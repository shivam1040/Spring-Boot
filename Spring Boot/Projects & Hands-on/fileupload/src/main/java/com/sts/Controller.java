package com.sts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class Controller {
	
	@Autowired
	private UploadHelper uploadHelper;
	
	@PostMapping("/upload") //api for upload, check applicaton.properties for config
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile multipartFile){
		if(multipartFile.isEmpty())
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("empty");
		System.out.println(multipartFile.getOriginalFilename());
		System.out.println(this.uploadHelper.uploadFile(multipartFile));
		//return ResponseEntity.ok("working"); //return working to body and response code 200
		return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(multipartFile.getOriginalFilename()).toUriString()); //this can be used to return url of uploaded file
	}
}
