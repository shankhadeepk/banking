package com.revolut.banking.resources;

import com.revolut.banking.config.H2Factory;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
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
		H2Factory.populateData();
	}

	@After
	public void destroyTest(){
		H2Factory.closeConnection();
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
		WebTarget webTarget=target("/account");
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
		String requestJson = "{\n" + 
				"				\"bankAccHolderName\":\"Shankha\",\n" + 
				"				\"balance\":\"1000\",\n" + 
				"				\"currencyCode\":\"EUR\",\n" + 
				"				\"emailId\":\"shankha@gmail.com\",\n" + 
				"				\"SSID\":\"TTTT\",\n" + 
				"				\"contact\":\"+918787667676\"\n" + 
				"			}";
		Response response = invocationBuilder.post(Entity.json(requestJson));
		//Response response=target("/account").request().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).post(Entity.entity(account, MediaType.APPLICATION_JSON));
		System.out.println(response);
		assertEquals("should return 201",201,response.getStatus());
	}

}
