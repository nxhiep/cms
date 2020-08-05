package com.hiepnx.cms.server.upload;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.Utils;

public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger("UploadServlet");

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
			fileUpload.setSizeMax(Config.FILE_SIZE_LIMIT);

			List<FileItem> items = fileUpload.parseRequest(req);

			for (FileItem item : items) {
				if (item.isFormField()) {
					logger.log(Level.INFO, "Received form field:");
					logger.log(Level.INFO, "Name: " + item.getFieldName());
					logger.log(Level.INFO, "Value: " + item.getString());
				} else {
					logger.log(Level.INFO, "Received file:");
					logger.log(Level.INFO, "Name: " + item.getName());
					logger.log(Level.INFO, "Size: " + item.getSize());
					logger.log(Level.INFO, "ContentType: " + item.getContentType());
					logger.log(Level.INFO, "FieldName: " + item.getFieldName());
				}

				if (!item.isFormField()) {
					if (item.getSize() > Config.FILE_SIZE_LIMIT) {
						resp.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "File size exceeds limit");

						return;
					}
					String bucket = Config.BUCKET_NAME + "/" + Config.BUCKET_NAME_FOLDER_OTHER;
					if(Utils.isAudio(item.getContentType())) {
						bucket = Config.BUCKET_NAME + "/" + Config.BUCKET_NAME_FOLDER_AUDIO;
					}
					if(Utils.isDocument(item.getContentType())) {
						bucket = Config.BUCKET_NAME + "/" + Config.BUCKET_NAME_FOLDER_DOCUMENT;
					}
					if(Utils.isImage(item.getContentType())) {
						bucket = Config.BUCKET_NAME + "/" + Config.BUCKET_NAME_FOLDER_IMAGE;
					}
					if(Utils.isVideo(item.getContentType())) {
						bucket = Config.BUCKET_NAME + "/" + Config.BUCKET_NAME_FOLDER_VIDEO;
					}
					GoogleCloudService googleCloudService = new GoogleCloudService();
					String url = googleCloudService.createObject(bucket, item.getName(), item.getInputStream());
					
					final PrintWriter writer = resp.getWriter();
					writer.write(url);
					if (!item.isInMemory())
						item.delete();
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Throwing servlet exception for unhandled exception", e);
			throw new ServletException(e);
		}
	}

}