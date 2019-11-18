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

public class TransactionResourceTest extends JerseyTest {


    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(TransactionResource.class,BankingResource.class);
    }

    @Before
    public void setUpTest(){
        H2DatabaseFactory.populateData();
    }


    @Test
    public void testHealth() {
        Response response=target("/transaction/health").request().get();
        System.out.println(response);
        assertEquals("should return 200",200,response.getStatus());
    }

    @Test
    public void testGetAllTransactions() throws GeneralBankingException {
        // Create a new account
        BankAccount account=new BankAccount("Shankhadeep",new BigDecimal(1000.00),"EUR","hansin@gmail.com","RRRTY","878772727");
        account.setStrAccountType("SAV");
        Gson gson=new Gson();
        String requestJson=gson.toJson(account);
        System.out.println("Create Account Request Payload:"+requestJson);
        WebTarget webTarget=target("/account");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.json(requestJson));
        System.out.println("Response:"+ response.readEntity(String.class));

        //Get All transactions
        webTarget=target("/transaction");
        invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        response = invocationBuilder.get();
        System.out.println("Response:"+ response.readEntity(String.class));
    }
}
