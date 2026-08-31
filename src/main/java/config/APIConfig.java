package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

public class APIConfig {
	
    public static RequestSpecification getRequestSpec() {    	
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);	
    	
        int connectionTimeout =
                Integer.parseInt(
                        ConfigReader.get("connectionTimeout"));

        int responseTimeout =
                Integer.parseInt(
                        ConfigReader.get("responseTimeout"));

        return new RequestSpecBuilder()
                .setBaseUri(
                        ConfigReader.get("baseUri"))
                .setContentType(ContentType.JSON)
                .setConfig(
                        RestAssured.config()
                                .httpClient(
                                        HttpClientConfig.httpClientConfig()
                                                .setParam(
                                                        "http.connection.timeout",
                                                        connectionTimeout)
                                                .setParam(
                                                        "http.socket.timeout",
                                                        responseTimeout)
                                )
                )
                .build();
    }
}