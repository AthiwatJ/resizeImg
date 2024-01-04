package com.example.demo.service;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;





@Service
public class ConvertImageToPdfService {

	private ResizeImageService resizeImageService;
	
	@Autowired
	public ConvertImageToPdfService(ResizeImageService resizeImageService) {
		this.resizeImageService = resizeImageService;
	}
	
	public ByteArrayOutputStream convertImageToPdf(MultipartFile file) throws Exception {
//		ByteArrayOutputStream imageResize = resizeImageService.resizeImage(file, 595, 842, "jpg");
		
		//itext5
//		int indentation = 0;
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		Document document = new Document(PageSize.A4, 20, 20, 20, 20);
//		PdfWriter.getInstance(document, outputStream);
//		document.open();
//		Image image = Image.getInstance(file);
//		float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
//	               - document.rightMargin() - indentation) / image.getWidth()) * 100;
//		image.scalePercent(scaler);
//		document.add(image);
//		document.close();
		
		//itext7
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		PdfDocument pdfDocument = new PdfDocument(new com.itextpdf.kernel.pdf.PdfWriter(outputStream));
//		Document document = new Document(pdfDocument, PageSize.A4);
////		ImageData imageData = ImageDataFactory.create(imageResize.toByteArray());
//		ImageData imageData = ImageDataFactory.create(file);
//		Image image = new Image(imageData);
//		document.add(image);
//		document.close();
		
		return null;
//		return  outputStream;
	}
}
