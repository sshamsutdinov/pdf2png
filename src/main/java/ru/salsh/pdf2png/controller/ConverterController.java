package ru.salsh.pdf2png.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.salsh.pdf2png.service.ConverterService;

import java.nio.charset.StandardCharsets;

@Controller
public class ConverterController {

	private static final Logger logger = LoggerFactory.getLogger(ConverterController.class);

	@Autowired
	private ConverterService converterService;

	@PostMapping(value = "/api/pdf2png", consumes = "multipart/form-data",produces = "application/zip")
	public ResponseEntity<Resource> pdf2png(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		Resource resource = converterService.convertToPng(file);

		String originalFilename = file.getOriginalFilename();
		String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
		filename = new String(filename.getBytes(), StandardCharsets.ISO_8859_1);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s.zip\"", filename))
				.body(resource);
	}

	@RequestMapping(value = "/api/test", method = RequestMethod.GET)
	public String test() {
		return "testView";
	}
}
