package org.landal.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

@Path("/file")
@RequestScoped
public class UploadService {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(@MultipartForm FileUpload input) throws IOException {

		File file = new File(System.getProperty("user.home"), input.getName());
		if (!file.exists()) {
			file.createNewFile();
		}
		try (OutputStream output = new FileOutputStream(file)) {
			IOUtils.write(input.getData(), output);
		}

		return Response.status(200).entity("uploadFile is called, Uploaded file name : " + input.getName()).build();
	}

}
