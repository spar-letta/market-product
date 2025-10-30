package com.cmis.cooperative;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@EnableConfigurationProperties
public abstract class ProductApplicationTests {

    public String accessToken = "eyJraWQiOiI1Mjc2Mzk0ZC1hZDc0LTQ4YTUtYjkxZS00MmRlOTk0ZjY4MWYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzYxODA0MDQ5LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwidXNlclB1YmxpY0lkIjoiMzMwZDVjY2EtMDgyZi00M2VhLTkwMWYtZDI5YzhkYmZlYjAxIiwiZXhwIjoxNzYxODkwNDQ5LCJpYXQiOjE3NjE4MDQwNDksImp0aSI6IjVkMzNkZTJjLTAwNjQtNDljMS1iOWI1LWY1NDJiYWNhY2M2MiIsImF1dGhvcml0aWVzIjpbIlJFQURfVVNFUlMiLCJWSUVXX0ZJTEUiLCJDUkVBVEVfVVNFUiIsIkRFTEVURV9VU0VSIiwiQ1JFQVRFX1BST0RVQ1QiLCJVUERBVEVfVVNFUiIsIlJFQURfUFJPRFVDVFMiLCJSRUFEX0xJS0VTIiwiUkVBRF9GSUxFUyIsIlVQTE9BRF9GSUxFIiwiVVBEQVRFX1BST0RVQ1QiLCJERUxFVEVfUFJPRFVDVCIsIkNSRUFURV9MSUtFIl0sInVzZXJuYW1lIjoidGVzdGluZ0B5b3BtYWlsLmNvbSJ9.DXY_Dq1fxTHtJ9boHvsI0pfrIdW1QtGvAj_MfRXF8lM3oMUVNYAMt5oPAeivInO38hkF6xXlPTUyyCxqkzt1dp-OVQ_tcwHnWjbXupluGNmjiH2LdcNvqYrjScVBCH3JmxLzLZwnvJw5rT6J7QfYlX7s8nzxdzjTssKTZzlaAyc3lRpkAnzIGKdv2YIN2sKJlTX1JWw6cSM561MSYfIUAgNi8HAdy_yWmq84E-sS89V4o90EPfqjpGJVcGn6_BvOrExm4c5QOyTdnyy0PyTmd2En98rQsXXu6etE68fwd4H-3SuURzaPDHk7HZZb3MfzfvbfNxYEyNAtj3Dl4Y8YMA";

    @Value("${local.server.port}")
    public int port;

    @Autowired
    private WebApplicationContext context;

    public MockMvc mvc;

//    @Autowired
//    public WireMockServer mockFileService;

    @Before
    public void setUpGlobal() throws IOException {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}