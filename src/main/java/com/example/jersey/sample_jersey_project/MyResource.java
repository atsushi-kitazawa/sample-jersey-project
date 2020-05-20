package com.example.jersey.sample_jersey_project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.BodyPartEntity;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import com.example.jersey.exception.MyException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Path("getit")
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got it! Got it!";
	}

	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_HTML)
	public Response hello() {
		String html = "<html><body>hello!</body></html>";
		return Response.status(Status.OK).entity(html).build();
	}

	@GET
	@Path("person")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson() {
		return new Person("hoge", 20);
	}

	// map in primitive type test
	@GET
	@Path("map")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> getMap() {
		Map<String, String> map = new HashMap<>();
		map.put("key0", "value0");
		map.put("key1", "value1");
		return map;
	}

	// map in object type test
	@GET
	@Path("map/mytype")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Person> getMapInMytype() {
		Map<String, Person> map = new HashMap<>();
		map.put("0", new Person("hoge", 15));
		map.put("1", new Person("uga", 25));
		return map;
	}

	// POST Test
	@POST
	@Path("create")
	@Produces(MediaType.TEXT_PLAIN)
	public String create(@FormParam("id") String id, @FormParam("name") String name) {
		return id + ":" + name;
	}

	// Exception Test
	@GET
	@Path("exception")
	@Produces(MediaType.TEXT_PLAIN)
	public String exception(@QueryParam("flg") String exFlg) throws Exception {
		if ("true".equals(exFlg)) {
			throw new MyException();
		} else {
			return "exception";
		}
	}

	// file upload test
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public String upload(final FormDataMultiPart multipart) throws IOException {
		List<BodyPart> bodypart = multipart.getBodyParts();
		for (BodyPart bp : bodypart) {
			System.out.println(bp.getContentDisposition().getFileName());
			BodyPartEntity entity = bp.getEntityAs(BodyPartEntity.class);
			Files.copy(entity.getInputStream(),
					new File("C:\\devspace\\workspace45\\sample-jersey-project\\upload.txt").toPath(),
					StandardCopyOption.REPLACE_EXISTING);
		}
		return "complete file upload.";
	}

	class Person {
		String name;
		int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}
}
