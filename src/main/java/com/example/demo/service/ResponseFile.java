package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResponseFile {

	public ResponseEntity<InputStreamResource> res(String fileName,ByteArrayOutputStream baos,MediaType contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("inline"+"filename=\"" + fileName + "\"").build());
		headers.setCacheControl(CacheControl.noCache().cachePrivate().mustRevalidate());
		headers.setContentType(contentType);
	return ResponseEntity.ok().headers(headers).body(new InputStreamResource(new ByteArrayInputStream(baos.toByteArray())));
	}
}