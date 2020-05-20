package com.example.jersey.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<MyException> {
	@Override
	public Response toResponse(MyException ex) {
		return Response	.status(Status.NOT_FOUND)
						.build();
	}
}
