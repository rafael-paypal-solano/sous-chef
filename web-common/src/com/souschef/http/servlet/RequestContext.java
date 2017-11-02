package com.souschef.http.servlet;

import java.io.Serializable;

public class RequestContext implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4864602370332747155L;

	private ServiceRequestAction serviceRequestAction = ServiceRequestAction.GET;
	
	String uri;
	
	String path;
	
	String uriParts[];
	
	public RequestContext() {
		
	}

	
	public ServiceRequestAction getServiceRequestAction() {
		return serviceRequestAction;
	}


	public String getUri() {
		return uri;
	}

	

	public void setUri(String uri) {
		if(uri == null) {
			uriParts = null;
		}
		
		String relativeUri = uri.substring(uri.indexOf(path)+path.length()+1);
		uriParts = relativeUri.split("\\/");
		this.uri = uri;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String[] getUriParts() {
		return uriParts;
	}

	
	
}
