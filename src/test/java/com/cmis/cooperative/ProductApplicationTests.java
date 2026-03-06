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

    public String accessToken = "eyJraWQiOiIyY2QwODdmYy1iNDY4LTQwOWEtOWU0ZC05MDljZmU3ZWVmMTQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJicm93c2VyLWNsaWVudCIsImF1ZCI6ImJyb3dzZXItY2xpZW50IiwibmJmIjoxNzcxOTQ0NTA2LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwidXNlclB1YmxpY0lkIjoiMzMwZDVjY2EtMDgyZi00M2VhLTkwMWYtZDI5YzhkYmZlYjAxIiwiZXhwIjoxNzcyMDMwOTA2LCJpYXQiOjE3NzE5NDQ1MDYsImp0aSI6ImIzNTVkMjJlLTQzMTItNDJjNy1hODI3LTQzMWQ1ODQyOWQwYSIsImF1dGhvcml0aWVzIjpbIlJFQURfVVNFUlMiLCJWSUVXX0ZJTEUiLCJDUkVBVEVfVVNFUiIsIkRFTEVURV9VU0VSIiwiQ1JFQVRFX1BST0RVQ1QiLCJVUERBVEVfVVNFUiIsIlJFQURfUFJPRFVDVFMiLCJSRUFEX0NPTU1FTlQiLCJSRUFEX0xJS0VTIiwiVVBEQVRFX0NPTU1FTlQiLCJSRUFEX0ZJTEVTIiwiREVMRVRFX0NPTU1FTlQiLCJVUExPQURfRklMRSIsIlVQREFURV9QUk9EVUNUIiwiREVMRVRFX1BST0RVQ1QiLCJDUkVBVEVfTElLRSIsIkNSRUFURV9DT01NRU5UIl0sInVzZXJuYW1lIjoidGVzdGluZ0B5b3BtYWlsLmNvbSJ9.iJ53Mk_eVrM8l-g2NSxaR2PZbmv-v872lI870G487Zr5_wv3noKKCWf0qF9W7hqLsOAD4ORyh_e1bGnZxQ15iybnpFcuj6WPXBFkyklzSzZ2WphrcRJ9Cv5K-DTty58Kq9IUfauwzM8ub_mY-ka6awrjZiLj3QjuJHs53ZDMKhvTLuOB9YO96vBlbMr_cmnFU5SsoWyPe5HJY3FzbSqtgKhryy05FZ2Plg_vvftBpKvcHyB8cnFuBLnbJbtWJL93J63knxjbhvbjM4DIQp-jNjwEBNfZHZpvpmcgxhOnj_uI5lM9hzcRoHX-D7nvmT4tWuCuq0Gjsne54N87KswQww";

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