package base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.RestAssured;

public class BaseTest
{
	protected static ObjectMapper objectMapper;

    @BeforeClass
	public void setup()
	{
		objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
		RestAssured.baseURI="https://restful-booker.herokuapp.com";
	}
}