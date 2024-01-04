package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.ResizeImageService;


@RestController
@RequestMapping("/demo")
public class demoController {

	private ResizeImageService resizeImageService;
	
	@Autowired
	public demoController(ResizeImageService resizeImageService) {
		this.resizeImageService = resizeImageService;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,value = "/resize")
	public ResponseEntity<InputStreamResource> resize(@RequestParam("file") MultipartFile multipartFile,
													@RequestParam int widthTarget,
													@RequestParam int heightTarget,
													@RequestParam String imgType
													) throws Exception{
		
		ByteArrayOutputStream baos = resizeImageService.resizeImage(multipartFile, widthTarget, heightTarget, imgType);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("inline"+"filename=\"" + multipartFile.getName() + "\"").build());
		headers.setCacheControl(CacheControl.noCache().cachePrivate().mustRevalidate());
		headers.setContentType(MediaType.valueOf(multipartFile.getContentType()));
	return ResponseEntity.ok().headers(headers).body(new InputStreamResource(new ByteArrayInputStream(baos.toByteArray())));
		
	}

	
	
}
