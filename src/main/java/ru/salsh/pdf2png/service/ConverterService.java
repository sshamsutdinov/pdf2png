package ru.salsh.pdf2png.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface ConverterService {

	Resource convertToPng(MultipartFile file);

	Resource convertToJpeg(MultipartFile file);

	void zipImages(OutputStream outputStream, String filename, byte[] fileBytes, String extension);
}
