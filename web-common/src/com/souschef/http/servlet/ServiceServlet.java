package com.souschef.http.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.souschef.json.encoder.Bean2JSONEncoder;
import com.souschef.json.parser.JSONParserException;
import com.souschef.json.parser.bean.JSON2BeanParser;
import com.souschef.json.parser.bean.JSON2BeanParserContext;
import com.souschef.json.parser.bean.JSON2BeanParserEventListener;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class ServiceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6018053448380111248L;
	private Map<String, Method> methods = new HashMap<String, Method>();	
	protected JSON2BeanParser parser;
	protected Bean2JSONEncoder encoder;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Method[] methods = this.getClass().getMethods();
		try {
			for(Method method : methods) {
				if(method.getParameterCount() == 1 &&
				   method.getReturnType()  != java.lang.Void.TYPE && 
				   RequestContext.class.isAssignableFrom(method.getParameters()[0].getType())) {
					EndPoint endPoint = method.getAnnotation(EndPoint.class);
					
					if(endPoint != null){
						this.methods.put(endPoint.path(), method);
					}
				}
			}
			parser = new JSON2BeanParser(new JSON2BeanParserEventListener());
			encoder = new Bean2JSONEncoder();
		}catch(JSONParserException e) {
			throw new ServletException(e);
		}
	}

	public String getPath(HttpServletRequest req) {
		String servletPath = req.getServletPath();
		String uri = req.getRequestURI();
		int pos = uri.indexOf(servletPath);
		String path = uri.substring(pos + servletPath.length());
		return path;
	}
	
	public Method getMethod(String Path) {
		Method method = null;
		for(String key : methods.keySet()) {
			if(Path.matches(key)){
				method = methods.get(key);
				break;
			}
			
		}
			
		
		return method;
	}
	

	
	protected void doRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = getPath(req);
		Method method = getMethod(path);
		Object result;
		RequestContext request;
		JSON2BeanParserContext context;
		try{
			if(method != null) {				
				request = (RequestContext) method.getParameters()[0].getType().getConstructor().newInstance();
				context = new JSON2BeanParserContext(request);
				parser.parse(context, new BufferedReader(new InputStreamReader(req.getInputStream())));
				request.setPath(req.getServletPath());
				request.setUri(req.getRequestURI());				
				result = method.invoke(this, request);
				encoder.encode(result, resp.getWriter());
				resp.getWriter().flush();
			}else
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}catch(IOException e) {
			throw e;
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doRequest(req, resp);
	}

	
}
