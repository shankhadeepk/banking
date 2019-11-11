package com.revolut.banking.resources;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;

public class BankingResourceTest extends JerseyTest{
	
	@Override
	public Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(BankingResource.class);
	}

	@Test
	public void testHealth() {
		Response response=target("/health").request().get();
		assertEquals("should return 200",200,response.getStatus());
	}
	
	@Test
	public void testCreateAccount() throws GeneralBankingException {
		BankAccount account=new BankAccount("Shankhadeep",new BigDecimal(1000.00),"EUR","hansin@gmail.com","RRRTY","878772727");
		account.setStrAccountType("SAV");
		Response response=target("/account").request().post(Entity.entity(account, MediaType.APPLICATION_JSON));
		System.out.println(response);
		assertEquals("should return 201",201,response.getStatus());
	}

}
