package ru.salsh.pdf2png.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ConverterService {

	Resource convertToPng(MultipartFile file);

	Resource convertToJpeg(MultipartFile file);
}
