package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
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

import com.example.demo.service.ConvertImageToPdfService;

@RestController
@RequestMapping("/merge-pdf")
public class MergePdfController {
	
	private ConvertImageToPdfService convertImageToPdfService;

	@Autowired
	public MergePdfController(ConvertImageToPdfService convertImageToPdfService) {
		this.convertImageToPdfService = convertImageToPdfService;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<InputStreamResource> post(@RequestParam("fistFile") MultipartFile fistFile,
													@RequestParam("secondFile") MultipartFile secondFile) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PDFMergerUtility PDFmerger = new PDFMergerUtility();
		if(fistFile.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
			PDFmerger.addSource(fistFile.getInputStream());
		}else if(fistFile.getContentType().equals(MediaType.IMAGE_JPEG_VALUE) ||fistFile.getContentType().equals(MediaType.IMAGE_PNG_VALUE) ) {
			byte[] pdf = convertImageToPdfService.convertImageToPdf(fistFile).toByteArray();
			PDFmerger.addSource(new ByteArrayInputStream(pdf));
		}else {
			return null;
		}
		
		if(secondFile.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
			PDFmerger.addSource(secondFile.getInputStream());
		}else if(secondFile.getContentType().equals(MediaType.IMAGE_JPEG_VALUE) ||secondFile.getContentType().equals(MediaType.IMAGE_PNG_VALUE) ) {
			byte[] pdf = convertImageToPdfService.convertImageToPdf(secondFile).toByteArray();
			PDFmerger.addSource(new ByteArrayInputStream(pdf));
		}else {
			return null;
		}
		
		
		
		PDFmerger.setDestinationStream(baos);
		PDFmerger.mergeDocuments();
		baos.close();
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("inline"+"filename=\"" +"merge.pdf"+ "\"").build());
		headers.setCacheControl(CacheControl.noCache().cachePrivate().mustRevalidate());
		headers.setContentType(MediaType.APPLICATION_PDF);
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(new ByteArrayInputStream(baos.toByteArray())));
	}
	
}
