package ru.salsh.pdf2png.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.salsh.pdf2png.exception.ConvertionFailedException;
import ru.salsh.pdf2png.exception.UploadedFileIsNotPdfException;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class PDFBoxConverterService implements ConverterService {

	private static final Logger logger = LoggerFactory.getLogger(PDFBoxConverterService.class);
	private static final int DPI = 200;

	@Override
	public Resource convertToPng(MultipartFile file) {
		return convert(file, "png");
	}

	@Override
	public Resource convertToJpeg(MultipartFile file) {
		return convert(file, "jpeg");
	}

	private Resource convert(MultipartFile file, String extension) {
		if (!"application/pdf".equals(file.getContentType())) {
			throw new UploadedFileIsNotPdfException("File content type is not application/pdf");
		}

		Resource resource = null;

		try {
			ByteArrayOutputStream zipFile = getZippedImages(file.getOriginalFilename(), file.getBytes(), extension);
			byte[] bytes = zipFile.toByteArray();
			if (bytes.length != 0) {
				resource = new ByteArrayResource(bytes);
			}
		} catch (IOException e) {
			logger.error("{}", e);
		}

		if (resource == null) {
			throw new ConvertionFailedException(file.getOriginalFilename() + "file convertion is failed");
		}

		return resource;
	}

	private ByteArrayOutputStream getZippedImages(String filename, byte[] fileBytes, String extension) {
		ByteArrayOutputStream zipFile = new ByteArrayOutputStream();
		zipImages(zipFile, filename, fileBytes, extension);

		return zipFile;
	}

	public void zipImages(OutputStream outputStream, String filename, byte[] fileBytes, String extension) {
		try (ZipOutputStream zipOut = new ZipOutputStream(outputStream);
		     PDDocument pdf = PDDocument.load(fileBytes)) {

			PDFRenderer pdfRenderer = new PDFRenderer(pdf);

			for (int page = 0; page < pdf.getNumberOfPages(); page++) {
				BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, DPI);

				String imageFilename = String.format("%s-%d.%s", filename, page, extension);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIOUtil.writeImage(bufferedImage, extension, baos, DPI);

				zipOut.putNextEntry(new ZipEntry(imageFilename));
				zipOut.write(baos.toByteArray());

				baos.close();
			}
		} catch (IOException e) {
			logger.error("", e);
		}
	}
}
