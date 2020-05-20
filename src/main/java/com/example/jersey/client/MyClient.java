package com.example.jersey.client;

import java.io.File;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

public class MyClient {
	private static final String BASE_URL = "http://localhost:8080/myapp";

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.register(JacksonFeature.class).target(BASE_URL).path("/myresource/person");
		try {
			Person response = target.request().get(Person.class);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		target = client.register(JacksonFeature.class).target(BASE_URL).path("/myresource/map/mytype");
		try {
			Map<String, Person> response = target.request().get(Map.class);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		target = client.register(MultiPartFeature.class).target(BASE_URL).path("/myresource/upload");
		FormDataMultiPart multipart = new FormDataMultiPart();
		multipart.bodyPart(new FileDataBodyPart("test.txt",
				new File("C:\\devspace\\workspace45\\sample-jersey-project\\test.txt")));
		try {
			Response response = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
			System.out.println(response.readEntity(String.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
