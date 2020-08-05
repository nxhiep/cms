package com.hiepnx.cms.server.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.logging.Logger;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.hiepnx.cms.shared.Config;

public class GoogleCloudService {
	private static GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	public static final Logger log = Logger.getLogger(GoogleCloudService.class.getName());

	public GoogleCloudService() {
		if (gcsService == null)
			gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	}

	public String createObject(String bucket, String name, byte[] content) throws IOException {
		GcsFilename filename = new GcsFilename(bucket, name);
		return createObject(filename, content);
	}

	private String createObject(GcsFilename fileName, byte[] content) throws IOException {
		try {
			GcsFileOptions options = new GcsFileOptions.Builder().acl("public-read").build();
			GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, options);// GcsFileOptions.getDefaultInstance());
			outputChannel.write(ByteBuffer.wrap(content));
			outputChannel.close();
			System.out.println("Upload success");
			return Config.GOOGLE_CLOUD_STORAGE_URL + fileName.getBucketName() + "/" + fileName.getObjectName();
		} catch (Exception e) {
			log.warning("Upload fails: " + e.getMessage() + ":" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return Config.GOOGLE_CLOUD_STORAGE_URL + fileName.getBucketName() + "/" + fileName.getObjectName();
	}

	public String createObject(String bucket, String name, InputStream inputStream) throws IOException {
		GcsFilename filename = new GcsFilename(bucket, name);
		return createObject(filename, inputStream);
	}

	private String createObject(GcsFilename fileName, InputStream inputStream) throws IOException {
		try {
			GcsFileOptions options = new GcsFileOptions.Builder().acl("public-read").build();
			GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, options);// GcsFileOptions.getDefaultInstance());
			copy(inputStream, Channels.newOutputStream(outputChannel));
			outputChannel.close();
			System.out.println("Upload success");
			return Config.GOOGLE_CLOUD_STORAGE_URL + fileName.getBucketName() + "/" + fileName.getObjectName();
		} catch (Exception e) {
			log.warning("Upload fails: " + e.getMessage() + ":" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return Config.GOOGLE_CLOUD_STORAGE_URL + fileName.getBucketName() + "/" + fileName.getObjectName();
	}

	private void copy(InputStream input, OutputStream output) throws IOException {
		try {
			byte[] buffer = new byte[Config.FILE_SIZE_LIMIT];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}
		} finally {
			input.close();
			output.close();
		}
	}

	public void deleteObject(String bucket, String name) throws IOException {
		try {
			gcsService.delete(new GcsFilename(bucket, name));
			System.out.println("Delete success");
		} catch (Exception e) {
			log.warning("Upload fails: " + e.getMessage() + ":" + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}