package com.app;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
public class JenkinsCommand {
	
	private String url;
	private String username;
	private String password;
	private String jobName;
	private String response;
	
	
	
	
	
	
	public JenkinsCommand(String url, String username, String password,
			String jobName) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
		this.jobName = jobName;
		this.response = "";
	}




	public void build(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append("job/");
		buffer.append(jobName);
		buffer.append("/build");
		String url = buffer.toString();
		
		Client client = Client.create();
		client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(this.username, this.password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.post(ClientResponse.class);
		String jsonResponse = response.getEntity(String.class);
		client.destroy();
		this.response = jsonResponse;
	}
	
	

	public void status(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(url);
		buffer.append("job/");
		buffer.append(jobName);
		buffer.append("/lastBuild");
		buffer.append("/consoleText");
		String url = buffer.toString();
		
		Client client = Client.create();
		client.addFilter(new com.sun.jersey.api.client.filter.HTTPBasicAuthFilter(this.username, this.password));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.post(ClientResponse.class);
		String jsonResponse = response.getEntity(String.class);
		client.destroy();
		this.response = jsonResponse;
	}
	
	
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}




	public String getJobName() {
		return jobName;
	}




	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
