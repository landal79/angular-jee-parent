package org.landal.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUpload {

	@FormParam("fileName")
	private String name;

	@FormParam("file")
	@PartType(MediaType.APPLICATION_OCTET_STREAM)
	private byte[] data;

	public FileUpload() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}



}
