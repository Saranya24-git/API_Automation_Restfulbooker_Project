package base;

import org.testng.annotations.BeforeClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.RestAssured;
import utils.ConfigReader;

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
        
		RestAssured.baseURI=ConfigReader.get("baseUri");
	}
}