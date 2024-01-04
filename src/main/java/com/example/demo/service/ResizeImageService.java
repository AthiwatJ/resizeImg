package com.example.demo.service;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResizeImageService {

	public  Integer calculateTargetSize(double sizePicture) {
		int commonDpi = 72;
		int pdfDpi = 300;
//	 	(sizePicture/commonDpi)* pdfDpi
		return  (int) Math.round((sizePicture/commonDpi)*pdfDpi);
	}
	
	public  Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

		 double widthRatio = boundary.getWidth() / imgSize.getWidth();
		 double heightRatio = boundary.getHeight() / imgSize.getHeight();
		 double ratio = Math.min(widthRatio, heightRatio);
		 double new_width = imgSize.getWidth() * ratio;
		 double new_height = imgSize.getHeight() * ratio;
		 return new Dimension(calculateTargetSize(new_width),calculateTargetSize(new_height));
	}
	
	
	public  BufferedImage resizeImage(BufferedImage originalImage,Dimension targetScaled) throws IOException {
		int imageWidth  = originalImage.getWidth();
		int imageHeight = originalImage.getHeight();
		if(imageWidth > targetScaled.width && imageHeight > targetScaled.height) {	
			Image resultingImage = originalImage.getScaledInstance(targetScaled.width, targetScaled.height, Image.SCALE_AREA_AVERAGING); 
			BufferedImage outputImage = new BufferedImage(targetScaled.width, targetScaled.height, BufferedImage.TYPE_INT_RGB); 
			outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
			return outputImage;
		}
		return originalImage;

	}
	
	public  ByteArrayOutputStream resizeImage(MultipartFile multipartFile, int widthTarget, int heightTarget,
			String imgType) throws IOException {
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(multipartFile.getBytes()));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Dimension imgSize = new Dimension(img.getWidth(), img.getHeight());
		Dimension boundary = new Dimension(widthTarget, heightTarget);
		Dimension targetScaled = getScaledDimension(imgSize,boundary);
		ImageIO.write(resizeImage(img,targetScaled),imgType, baos);
		return baos;
	}
}
