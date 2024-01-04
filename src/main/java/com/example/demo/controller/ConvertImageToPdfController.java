package com.example.demo.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.ConvertImageToPdfService;
import com.example.demo.service.ResponseFile;

@RestController
@RequestMapping("/convert")
public class ConvertImageToPdfController {
	
	private ConvertImageToPdfService convertImageToPdfService;
	private ResponseFile responseFile;

	@Autowired
	public ConvertImageToPdfController(ConvertImageToPdfService convertImageToPdfService,ResponseFile responseFile) {
		this.convertImageToPdfService = convertImageToPdfService;
		this.responseFile = responseFile;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,value = "/image-to-pdf")
	public ResponseEntity<InputStreamResource> resize(@RequestParam("file") MultipartFile multipartFile) throws Exception{
		
		ByteArrayOutputStream baos = convertImageToPdfService.convertImageToPdf(multipartFile);
		
		return responseFile.res("test.pdf", baos,MediaType.APPLICATION_PDF);
		
	}

}
