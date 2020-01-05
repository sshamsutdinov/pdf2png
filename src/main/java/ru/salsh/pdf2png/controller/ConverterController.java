package ru.salsh.pdf2png.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.salsh.pdf2png.service.ConverterService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class ConverterController {

	@Autowired
	private ConverterService converterService;

	@PostMapping(value = "/api/pdf2png", consumes = "multipart/form-data", produces = "application/zip")
	public ResponseEntity<Resource> pdf2png(@RequestParam("file") MultipartFile file, HttpServletResponse resp) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		Resource resource = converterService.convertToPng(file);

		String filename = filenameWithoutExt(file.getOriginalFilename());
		filename = new String(filename.getBytes(), StandardCharsets.ISO_8859_1);


		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s.zip\"", filename))
				.body(resource);
	}

	@PostMapping(value = "/apiv2/pdf2png", consumes = "multipart/form-data")
	public void pdf2pngV2(@RequestParam("file") MultipartFile file, HttpServletResponse resp) throws IOException {
		byte[] fileBytes = file.getBytes();
		String filename = filenameWithoutExt(file.getOriginalFilename());
		String extension = "png";

		resp.setContentType("application/zip");
		resp.addHeader("Content-Disposition", String.format("attachment; filename=\"%s.zip\"", filename));
		resp.setStatus(HttpStatus.OK.value());

		ServletOutputStream zipFile = resp.getOutputStream();
		converterService.zipImages(zipFile, filename, fileBytes, extension);
	}

	private String filenameWithoutExt(String filename) {
		return filename.substring(0, filename.lastIndexOf("."));
	}
}
