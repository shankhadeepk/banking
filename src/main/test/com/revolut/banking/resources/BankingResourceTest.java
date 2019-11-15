package com.revolut.banking.resources;

import com.google.gson.Gson;
import com.revolut.banking.config.H2DatabaseFactory;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankingResourceTest extends JerseyTest{
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BankingResource.class);
	}

	@Before
	public void setUpTest(){
		H2DatabaseFactory.populateData();
	}

	@Test
	public void testHealth() {
		Response response=target("/health").request().get();
		System.out.println(response);
		assertEquals("should return 200",200,response.getStatus());
	}
	
	@Test
	public void testCreateAccount() throws GeneralBankingException {
		BankAccount account=new BankAccount("Shankhadeep",new BigDecimal(1000.00),"EUR","hansin@gmail.com","RRRTY","878772727");
		account.setStrAccountType("SAV");
		Gson gson=new Gson();
		String requestJson=gson.toJson(account);
		System.out.println("Request Payload:"+requestJson);
		WebTarget webTarget=target("/account");
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.json(requestJson));
		System.out.println("Response:"+ response.readEntity(String.class));
		assertEquals("should return 201",201,response.getStatus());

		webTarget=target("/account/1");
		invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();
		System.out.println("Response:"+ response.readEntity(String.class));
	}

	@Test
	public void testDeleteAccount() throws GeneralBankingException {
		WebTarget webTarget=target("/account/1");
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.delete();
		System.out.println("Response:"+response.readEntity(String.class));
		assertEquals("should return 200",200,response.getStatus());
	}

	@Test
	public void testUpdateAccount() throws GeneralBankingException {
		BankAccount account=new BankAccount("Shankhadeep",new BigDecimal(1000.00),"EUR","hansin@gmail.com","RRRTY","878772727");
		account.setStrAccountType("SAV");
		Gson gson=new Gson();
		String requestJson=gson.toJson(account);
		System.out.println("Create Account Request Payload:"+requestJson);
		WebTarget webTarget=target("/account");
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.json(requestJson));
		System.out.println("Response:"+ response.readEntity(String.class));

		webTarget=target("/account/1");
		invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		BigDecimal addTobalance=new BigDecimal(1000.00);
		requestJson=gson.toJson(addTobalance);
		System.out.println("Update Request Payload: "+requestJson);
		response = invocationBuilder.put(Entity.json(requestJson));
		System.out.println("Response:"+response.readEntity(String.class));
		assertEquals("should return 200",200,response.getStatus());

		webTarget=target("/account/1");
		invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();
		System.out.println("Response:"+ response.readEntity(String.class));


	}

}
