//package com.cmis.cooperative.web;
//
//import com.cmis.cooperative.CooperativeServiceApplicationTests;
//import com.cmis.cooperative.model.dataType.*;
//import com.cmis.cooperative.model.dto.*;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static com.cmis.cooperative.wireMocks.ConsumerControllerFileServiceTest.setupMockGetDocumentRule;
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.*;
//
//@Slf4j
//public class SocietyRestControllerTests extends CooperativeServiceApplicationTests {
//
//    @Autowired
//    private HandleEvents handleEvents;
//
//    @Test
//    public void testReserveNameSearchAndCreateSocietyWorks() throws IOException {
//        NameDto nameDto = new NameDto(null, "TAI 101 SAVING SACCO");
//
//        String publicId = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(nameDto)
//                .post("/nameSearches/reserve")
//                .then()
//                .statusCode(200)
//                .body("name", equalToIgnoringCase("TAI 101 SAVING SACCO"))
//                .body("status", equalToIgnoringCase("PENDING_PAYMENT"))
//                .body("userCodeNumber", notNullValue())
//                .body("paid", equalTo(false))
//                .extract().path("publicId");
//
//        handleEvents.handleProformaReturnEvent(new PaymentEventDto(UUID.fromString(publicId), "PAID", SubProcess.Name_Search));
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("paid", equalTo(true))
//                .body("status", equalTo("SUBMITTED"));
//
//        AssignOfficerDto req = new AssignOfficerDto(Arrays.asList(UUID.fromString("7a40fd66-36a6-4487-9509-e9b12a61bff9"), UUID.fromString("2d7c44c0-3e3c-415d-b4e3-feb799f41e04")));
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(req)
//                .put("/nameSearches/{publicId}/assign", UUID.fromString(publicId))
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("assignedOffice.size()", equalTo(2));
//
//        NameSearchReservedDto nameSearchReservedDto = new NameSearchReservedDto("name",
//                "MARK_PENDING_OFFICER_REGISTRY",
//                UUID.fromString("23c47941-0ac1-4022-a933-ae465e1f2ddd"),
//                UUID.fromString(publicId),
//                "String description");
//
//        NameSearchTaskDto nameSearchTaskDto = new NameSearchTaskDto(nameSearchReservedDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "name approved", "MARK_PENDING_OFFICER_REGISTRY");
//        handleEvents.handleNameReservedApprovalEvent(nameSearchTaskDto);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalTo("PENDING_OFFICER_VERIFICATION"));
//
//        nameSearchTaskDto = new NameSearchTaskDto(nameSearchReservedDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "name approved", "MARK_PENDING_HEAD_APPROVAL");
//        handleEvents.handleNameReservedApprovalEvent(nameSearchTaskDto);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalTo("PENDING_HEAD_APPROVAL"));
//
//        nameSearchTaskDto = new NameSearchTaskDto(nameSearchReservedDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "name approved", "APPROVED");
//        handleEvents.handleNameReservedApprovalEvent(nameSearchTaskDto);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalTo("APPROVED"));
//
//        String societyPublicId = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies")
//                .then()
//                .statusCode(200)
//                .body("content[0].status", equalToIgnoringCase("NEW"))
//                .extract().path("content[0].publicId");
//
//        SocietyRequestDto societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyPublicId(UUID.fromString(societyPublicId));
//        societyRequestDto.setActionType(SocietyActionType.BASIC_DETAIL);
//        societyRequestDto.setSocietyType(SocietyType.SACCO);
//        societyRequestDto.setActivity(SocietyActivity.FARMING);
//        societyRequestDto.setBookLanguage(BookLanguage.ENGLISH);
//        societyRequestDto.setLiability("Yes");
//        societyRequestDto.setAreaOfOperation("Mayanja Market");
//        societyRequestDto.setCountyPublicId(UUID.fromString("fdaf79d1-2152-4ec3-8a90-056756cfdce1"));
//        societyRequestDto.setSubCountyPublicId(UUID.fromString("c4a7dfd0-0cde-4f4c-ba77-ee7f55b12beb"));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .patch("/societies")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue())
//                .body("subCounty", notNullValue())
//                .body("societyType", notNullValue())
//                .body("activity", notNullValue())
//                .body("bookLanguage", notNullValue())
//                .body("liability", notNullValue())
//                .body("areaOfOperation", notNullValue());
//
//        Contact contact = new Contact();
//        contact.setEmailAddress("email23@gmail.com");
//        contact.setWebsite("web@co.com");
//        contact.setPhoneNumber("0754321234");
//        contact.setPostalCode("10000");
//        contact.setPostalAddress("Box 234");
//
//        societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyPublicId(UUID.fromString(societyPublicId));
//        societyRequestDto.setActionType(SocietyActionType.CONTACT);
//        societyRequestDto.setContact(contact);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .patch("/societies")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue());
//
//        PromoterDto nancyMutenyo = new PromoterDto("Nancy Mutenyo", "99999993", "0798765676", Gender.FEMALE, LocalDate.now().minusYears(30));
//        PromoterDto meterCason = new PromoterDto("Meter Cason", "1111113", "0763456723", Gender.MALE, LocalDate.now().minusYears(21));
//        PromoterDto kevinCason = new PromoterDto("Kevin Cason", "222223", "0876546787", Gender.MALE, LocalDate.now().minusYears(17));
//        List<PromoterDto> promoterList = Arrays.asList(nancyMutenyo, meterCason, kevinCason);
//
//        societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyPublicId(UUID.fromString(societyPublicId));
//        societyRequestDto.setActionType(SocietyActionType.PROMOTERS);
//        societyRequestDto.setPromoterDto(promoterList);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .patch("/societies")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue());
//
//        String memberPublicId = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}/promoters", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200)
//                .body("content.size()", greaterThanOrEqualTo(2))
//                .extract().path("content[0].publicId");
//
//        setupMockGetDocumentRule(mockFileService, societyPublicId, EntitySubType.Society_Registration);
//        given()
//                .contentType(ContentType.JSON)
//                .queryParam("entitySubType", EntitySubType.Society_Registration)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}/checkFileUploadedAll", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200)
//                .body("All_Uploaded", equalTo(true));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("COMPLETE"));
//
//        handleEvents.handleProformaReturnEvent(new PaymentEventDto(UUID.fromString(societyPublicId), "PAID", SubProcess.Society_Registration));
//
//        SocietyDto societyDto = new SocietyDto();
//        societyDto.setName("MANMO SACCO");
//        societyDto.setPublicId(UUID.fromString(societyPublicId));
//
//        SocietyTaskDto societyTaskDto = new SocietyTaskDto(societyDto, "7ba79c3b-f16b-44da-9696-0174e36d7021", "Approved", "MARK_PENDING_HEAD_REGISTRATION_ASSIGN");
//        handleEvents.handleSocietyApprovalEvents(societyTaskDto);
//
//        req = new AssignOfficerDto(Arrays.asList(UUID.fromString("7a40fd66-36a6-4487-9509-e9b12a61bff9"), UUID.fromString("2d7c44c0-3e3c-415d-b4e3-feb799f41e04")));
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(req)
//                .put("/societies/{publicId}/assign", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies")
//                .then()
//                .statusCode(200)
//                .body("content[0].status", equalToIgnoringCase("PENDING_HEAD_REGISTRATION_ASSIGN"));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}/assign/users", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200)
//                .body("content.size()", equalTo(2));
//
//        societyTaskDto = new SocietyTaskDto(societyDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "Approved", "MARK_APPROVED");
//        handleEvents.handleSocietyApprovalEvents(societyTaskDto);
//
//        String accessCode = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies")
//                .then()
//                .statusCode(200)
//                .extract().path("content[0].accessCode");
//
//        String societyNumber = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies")
//                .then().log().all()
//                .statusCode(200)
//                .extract().path("content[0].societyNumber");
//
//        log.info("societyNumber {}", societyNumber);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/mySocieties/{userCodeNumber}", UUID.fromString(accessCode))
//                .then()
//                .statusCode(200)
//                .body("content[0].accessCode", equalTo(accessCode));
//
//        Response response = given()
//                .auth()
//                .oauth2(accessToken)
//                .get("/reports/sampleSocietyCertificate/{societyPublicId}", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200)
//                .extract().response();
////
////        saveExport(response.getBody().asByteArray(), "sampleSocietyCertificate.pdf");
//
//        Response response_1 = given()
//                .auth()
//                .oauth2(accessToken)
//                .get("/reports/societyCertificate/{societyPublicId}", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//
////        saveExport(response_1.getBody().asByteArray(), "SignedSocietyCertificate.pdf");
//
//        Response response_officials = given()
//                .queryParam("societyNumber", societyNumber)
//                .auth()
//                .oauth2(accessToken)
//                .get("/reports/societyOfficial")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
////        saveExport(response_officials.getBody().asByteArray(), "SocietyOfficials.pdf");
//
////        given()
////                .contentType(ContentType.JSON)
////                .queryParam("societyNumber", societyNumber)
////                .auth()
////                .oauth2(accessToken)
////                .post("/officialSearches/{publicId}/attachFile", UUID.fromString(societyPublicId))
////                .then()
////                .statusCode(200);
//
//        Response responseB = given()
//                .queryParam("societyPublicId", UUID.fromString(societyPublicId))
//                .queryParam("amount", BigDecimal.valueOf(4000000))
//                .queryParam("amountInWord", "Four Million")
//                .queryParam("memberPublicId", UUID.fromString(memberPublicId))
//                .auth()
//                .oauth2(accessToken)
//                .get("/reports/indemnityForm")
//                .then()
//                .statusCode(200)
//                .extract().response();
//
//        saveExport(responseB.getBody().asByteArray(), "indemnity_form.pdf");
//
//        Response societiesResponse = given()
//                .queryParam("exportType", StatementExportType.EXCEL)
//                .auth()
//                .oauth2(accessToken)
//                .get("/reports/societyExport")
//                .then().log().all()
//                .statusCode(200)
//                .extract().response();
//
//        saveExport(societiesResponse.getBody().asByteArray(), "societies_export.xlsx");
//    }
//
//    @Test
//    @Ignore
//    public void testDownloadPromoterRequestTemplateWorks() throws IOException {
//
//        InputStream inputStream = given()
//                .auth().oauth2(accessToken)
//                .get("/quotations/downloadPromotersTemplate")
//                .then()
//                .statusCode(200)
//                .extract().asInputStream();
//
//        File fileResponse = new File("src/test/resources/promoter_template.xlsx");
//        FileUtils.copyInputStreamToFile(inputStream, fileResponse);
//    }
//
//    @Test
//    public void testCreateBylawAmendmentWorks() throws IOException {
//        String mainSocietyPublicId = getReserveNameSearchAndCreateSocietyWorks();
//        SocietyRequestDto societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyAmendmentName("TAI SACCO");
//        societyRequestDto.setActionType(SocietyActionType.CREATE_AMENDMENT);
//        societyRequestDto.setSocietyType(SocietyType.SACCO);
//        societyRequestDto.setActivity(SocietyActivity.FARMING);
//        societyRequestDto.setBookLanguage(BookLanguage.ENGLISH);
//        societyRequestDto.setLiability("Yes");
//        societyRequestDto.setAreaOfOperation("Mayanja Market");
//        societyRequestDto.setCountyPublicId(UUID.fromString("fdaf79d1-2152-4ec3-8a90-056756cfdce1"));
//        societyRequestDto.setSubCountyPublicId(UUID.fromString("c4a7dfd0-0cde-4f4c-ba77-ee7f55b12beb"));
//        societyRequestDto.setSocietyPublicId(UUID.fromString(mainSocietyPublicId));
//        societyRequestDto.setAccessCode(UUID.fromString(mainSocietyPublicId));
//
//        String societyAmendmentPublicId = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .post("/societies/amendments")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue())
//                .body("subCounty", notNullValue())
//                .body("societyType", notNullValue())
//                .body("activity", notNullValue())
//                .body("bookLanguage", notNullValue())
//                .body("liability", notNullValue())
//                .body("areaOfOperation", notNullValue())
//                .extract().path("publicId");
//
//        Contact contact = new Contact();
//        contact.setEmailAddress("melon23@gmail.com");
//        contact.setWebsite("web@co.com");
//        contact.setPhoneNumber("0754321234");
//        contact.setPostalCode("122222");
//        contact.setPostalAddress("Box 234");
//
//        societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyAmendmentPublicId(UUID.fromString(societyAmendmentPublicId));
//        societyRequestDto.setActionType(SocietyActionType.CONTACT);
//        societyRequestDto.setContact(contact);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .post("/societies/amendments")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue());
//
//        PromoterDto nancyMutenyo = new PromoterDto("Nancy Mutenyo", "444444", "0798765676", Gender.FEMALE, LocalDate.now().minusYears(30));
//        PromoterDto meterCason = new PromoterDto("Meter Cason", "333333", "0763456723", Gender.MALE, LocalDate.now().minusYears(21));
//        PromoterDto kevinCason = new PromoterDto("Kevin Cason", "222622", "0876546787", Gender.MALE, LocalDate.now().minusYears(17));
//        List<PromoterDto> promoterList = Arrays.asList(nancyMutenyo, meterCason, kevinCason);
//
//        societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyAmendmentPublicId(UUID.fromString(societyAmendmentPublicId));
//        societyRequestDto.setActionType(SocietyActionType.PROMOTERS);
//        societyRequestDto.setPromoterDto(promoterList);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .post("/societies/amendments")
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}/promoters", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200)
//                .body("content.size()", greaterThanOrEqualTo(2));
//
//        setupMockGetDocumentRule(mockFileService, societyAmendmentPublicId, EntitySubType.Society_Bylaw);
//        given()
//                .contentType(ContentType.JSON)
//                .queryParam("entitySubType", EntitySubType.Society_Bylaw)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}/checkFileUploadedAll", UUID.fromString(societyAmendmentPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("All_Uploaded", equalTo(true));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("COMPLETE"));
//
//        handleEvents.handleProformaReturnEvent(new PaymentEventDto(UUID.fromString(societyAmendmentPublicId), "PAID", SubProcess.Society_Amendment));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("PENDING_COUNTY_APPROVAL"));
//
//        SocietyAmendmentDto societyAmendmentDto = new SocietyAmendmentDto();
//        societyAmendmentDto.setPublicId(UUID.fromString(societyAmendmentPublicId));
//
//        SocietyAmendmentTaskDto societyAmendmentTaskDto = new SocietyAmendmentTaskDto(societyAmendmentDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "Approved", "MARK_PENDING_HEAD_REGISTRATION_ASSIGN");
//        handleEvents.handleSocietyAmendmentApprovalReturnEvents(societyAmendmentTaskDto);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("PENDING_HEAD_REGISTRATION_ASSIGN"));
//
//        AssignOfficerDto req = new AssignOfficerDto(Arrays.asList(UUID.fromString("7a40fd66-36a6-4487-9509-e9b12a61bff9"), UUID.fromString("2d7c44c0-3e3c-415d-b4e3-feb799f41e04")));
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(req)
//                .put("/societies/amendments/{publicId}/assign", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}/assign/users", UUID.fromString(societyAmendmentPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("content.size()", equalTo(2));
//
//        societyAmendmentDto = new SocietyAmendmentDto();
//        societyAmendmentDto.setPublicId(UUID.fromString(societyAmendmentPublicId));
//        societyAmendmentTaskDto = new SocietyAmendmentTaskDto(societyAmendmentDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "Approved", "MARK_APPROVED");
//        handleEvents.handleSocietyAmendmentApprovalReturnEvents(societyAmendmentTaskDto);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("APPROVED"));
//    }
//
//    @Test
//    public void testReserveNameSearchAndCreateSocietyAndRequestMoreInformationWorks() throws IOException {
//        NameDto nameDto = new NameDto(null, "TAI 10 SAVING SACCO");
//
//        String publicId = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(nameDto)
//                .post("/nameSearches/reserve")
//                .then()
//                .statusCode(200)
//                .body("name", equalToIgnoringCase("TAI 10 SAVING SACCO"))
//                .body("status", equalToIgnoringCase("PENDING_PAYMENT"))
//                .body("userCodeNumber", notNullValue())
//                .body("paid", equalTo(false))
//                .extract().path("publicId");
//
//        handleEvents.handleProformaReturnEvent(new PaymentEventDto(UUID.fromString(publicId), "PAID", SubProcess.Name_Search));
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("paid", equalTo(true))
//                .body("status", equalTo("SUBMITTED"));
//
//        AssignOfficerDto req = new AssignOfficerDto(Arrays.asList(UUID.fromString("7a40fd66-36a6-4487-9509-e9b12a61bff9"), UUID.fromString("2d7c44c0-3e3c-415d-b4e3-feb799f41e04")));
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(req)
//                .put("/nameSearches/{publicId}/assign", UUID.fromString(publicId))
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("assignedOffice.size()", equalTo(2));
//
//        NameSearchReservedDto nameSearchReservedDto = new NameSearchReservedDto("name",
//                "MARK_PENDING_OFFICER_REGISTRY",
//                UUID.fromString("23c47941-0ac1-4022-a933-ae465e1f2ddd"),
//                UUID.fromString(publicId),
//                "String description");
//
//        NameSearchTaskDto nameSearchTaskDto = new NameSearchTaskDto(nameSearchReservedDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "name approved", "MARK_PENDING_OFFICER_REGISTRY");
//        handleEvents.handleNameReservedApprovalEvent(nameSearchTaskDto);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("status", equalTo("PENDING_OFFICER_VERIFICATION"));
//
//        nameSearchTaskDto = new NameSearchTaskDto(nameSearchReservedDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "name approved", "MARK_PENDING_HEAD_APPROVAL");
//        handleEvents.handleNameReservedApprovalEvent(nameSearchTaskDto);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalTo("PENDING_HEAD_APPROVAL"));
//
//        nameSearchTaskDto = new NameSearchTaskDto(nameSearchReservedDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "name approved", "APPROVED");
//        handleEvents.handleNameReservedApprovalEvent(nameSearchTaskDto);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/nameSearches/{publicId}", UUID.fromString(publicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalTo("APPROVED"));
//
//        String societyPublicId = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies")
//                .then()
//                .statusCode(200)
//                .body("content[0].status", equalToIgnoringCase("NEW"))
//                .extract().path("content[0].publicId");
//
//        SocietyRequestDto societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyPublicId(UUID.fromString(societyPublicId));
//        societyRequestDto.setActionType(SocietyActionType.BASIC_DETAIL);
//        societyRequestDto.setSocietyType(SocietyType.SACCO);
//        societyRequestDto.setActivity(SocietyActivity.FARMING);
//        societyRequestDto.setBookLanguage(BookLanguage.ENGLISH);
//        societyRequestDto.setLiability("Yes");
//        societyRequestDto.setAreaOfOperation("Mayanja Market");
//        societyRequestDto.setCountyPublicId(UUID.fromString("fdaf79d1-2152-4ec3-8a90-056756cfdce1"));
//        societyRequestDto.setSubCountyPublicId(UUID.fromString("c4a7dfd0-0cde-4f4c-ba77-ee7f55b12beb"));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .patch("/societies")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue())
//                .body("subCounty", notNullValue())
//                .body("societyType", notNullValue())
//                .body("activity", notNullValue())
//                .body("bookLanguage", notNullValue())
//                .body("liability", notNullValue())
//                .body("areaOfOperation", notNullValue());
//
//        Contact contact = new Contact();
//        contact.setEmailAddress("email23@gmail.com");
//        contact.setWebsite("web@co.com");
//        contact.setPhoneNumber("0754321234");
//        contact.setPostalCode("10000");
//        contact.setPostalAddress("Box 234");
//
//        societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyPublicId(UUID.fromString(societyPublicId));
//        societyRequestDto.setActionType(SocietyActionType.CONTACT);
//        societyRequestDto.setContact(contact);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .patch("/societies")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue());
//
//        PromoterDto nancyMutenyo = new PromoterDto("Nancy Mutenyo", "9999999", "0798765676", Gender.FEMALE, LocalDate.now().minusYears(30));
//        PromoterDto meterCason = new PromoterDto("Meter Cason", "111111", "0763456723", Gender.MALE, LocalDate.now().minusYears(21));
//        PromoterDto kevinCason = new PromoterDto("Kevin Cason", "22222", "0876546787", Gender.MALE, LocalDate.now().minusYears(17));
//        List<PromoterDto> promoterList = Arrays.asList(nancyMutenyo, meterCason, kevinCason);
//
//        societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyPublicId(UUID.fromString(societyPublicId));
//        societyRequestDto.setActionType(SocietyActionType.PROMOTERS);
//        societyRequestDto.setPromoterDto(promoterList);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .patch("/societies")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue());
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}/promoters", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200)
//                .body("content.size()", greaterThanOrEqualTo(2));
//
//        setupMockGetDocumentRule(mockFileService, societyPublicId, EntitySubType.Society_Registration);
//        given()
//                .contentType(ContentType.JSON)
//                .queryParam("entitySubType", EntitySubType.Society_Registration)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}/checkFileUploadedAll", UUID.fromString(societyPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("All_Uploaded", equalTo(true));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}", UUID.fromString(societyPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("COMPLETE"));
//
//        handleEvents.handleProformaReturnEvent(new PaymentEventDto(UUID.fromString(societyPublicId), "PAID", SubProcess.Society_Registration));
//
//        SocietyDto societyDto = new SocietyDto();
//        societyDto.setName("MANMO SACCO");
//        societyDto.setPublicId(UUID.fromString(societyPublicId));
//
//        SocietyTaskDto societyTaskDto = new SocietyTaskDto(societyDto, "7ba79c3b-f16b-44da-9696-0174e36d7021", "Approved", "MARK_PENDING_HEAD_REGISTRATION_ASSIGN");
//        handleEvents.handleSocietyApprovalEvents(societyTaskDto);
//
//        req = new AssignOfficerDto(Arrays.asList(UUID.fromString("7a40fd66-36a6-4487-9509-e9b12a61bff9"), UUID.fromString("2d7c44c0-3e3c-415d-b4e3-feb799f41e04")));
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(req)
//                .put("/societies/{publicId}/assign", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies")
//                .then()
//                .statusCode(200)
//                .body("content[0].status", equalToIgnoringCase("PENDING_HEAD_REGISTRATION_ASSIGN"));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}/assign/users", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200)
//                .body("content.size()", equalTo(2));
//
//        societyTaskDto = new SocietyTaskDto(societyDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "More information", "MARK_REQUEST_MORE_INFO");
//        handleEvents.handleSocietyApprovalEvents(societyTaskDto);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}", UUID.fromString(societyPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("MORE_INFORMATION_NEEDED"));
//
//        ResubmitMessage resubmitMessage = new ResubmitMessage("Added the required File", SubProcess.Society_Registration);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(resubmitMessage)
//                .put("/societies/{publicId}/resubmit", UUID.fromString(societyPublicId))
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}", UUID.fromString(societyPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("RESUBMITTED"));
//
//        Response response_1 = given()
//                .auth()
//                .oauth2(accessToken)
//                .get("/reports/societyCertificate/{societyPublicId}", UUID.fromString(societyPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .extract().response();
//
//        saveExport(response_1.getBody().asByteArray(), "SignedSocietyCertificate.pdf");
//
//    }
//
//    @Test
//    public void testCreateBylawAmendmentAndRequestMoreInformationWorks() throws IOException {
//        String mainSocietyPublicId = getReserveNameSearchAndCreateSocietyWorks();
//        SocietyRequestDto societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyAmendmentName("TAI SACCO");
//        societyRequestDto.setActionType(SocietyActionType.CREATE_AMENDMENT);
//        societyRequestDto.setSocietyType(SocietyType.SACCO);
//        societyRequestDto.setActivity(SocietyActivity.FARMING);
//        societyRequestDto.setBookLanguage(BookLanguage.ENGLISH);
//        societyRequestDto.setLiability("Yes");
//        societyRequestDto.setAreaOfOperation("Mayanja Market");
//        societyRequestDto.setCountyPublicId(UUID.fromString("fdaf79d1-2152-4ec3-8a90-056756cfdce1"));
//        societyRequestDto.setSubCountyPublicId(UUID.fromString("c4a7dfd0-0cde-4f4c-ba77-ee7f55b12beb"));
//        societyRequestDto.setSocietyPublicId(UUID.fromString(mainSocietyPublicId));
//        societyRequestDto.setAccessCode(UUID.fromString(mainSocietyPublicId));
//
//        String societyAmendmentPublicId = given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .post("/societies/amendments")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue())
//                .body("subCounty", notNullValue())
//                .body("societyType", notNullValue())
//                .body("activity", notNullValue())
//                .body("bookLanguage", notNullValue())
//                .body("liability", notNullValue())
//                .body("areaOfOperation", notNullValue())
//                .extract().path("publicId");
//
//        Contact contact = new Contact();
//        contact.setEmailAddress("melon23@gmail.com");
//        contact.setWebsite("web@co.com");
//        contact.setPhoneNumber("0754321234");
//        contact.setPostalCode("122222");
//        contact.setPostalAddress("Box 234");
//
//        societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyAmendmentPublicId(UUID.fromString(societyAmendmentPublicId));
//        societyRequestDto.setActionType(SocietyActionType.CONTACT);
//        societyRequestDto.setContact(contact);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .post("/societies/amendments")
//                .then()
//                .statusCode(200)
//                .body("name", notNullValue());
//
//        PromoterDto nancyMutenyo = new PromoterDto("Nancy Mutenyo", "4444441", "0798765676", Gender.FEMALE, LocalDate.now().minusYears(30));
//        PromoterDto meterCason = new PromoterDto("Meter Cason", "3333331", "0763456723", Gender.MALE, LocalDate.now().minusYears(21));
//        PromoterDto kevinCason = new PromoterDto("Kevin Cason", "2226221", "0876546787", Gender.MALE, LocalDate.now().minusYears(17));
//        List<PromoterDto> promoterList = Arrays.asList(nancyMutenyo, meterCason, kevinCason);
//
//        societyRequestDto = new SocietyRequestDto();
//        societyRequestDto.setSocietyAmendmentPublicId(UUID.fromString(societyAmendmentPublicId));
//        societyRequestDto.setActionType(SocietyActionType.PROMOTERS);
//        societyRequestDto.setPromoterDto(promoterList);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(societyRequestDto).log().all()
//                .post("/societies/amendments")
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}/promoters", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200)
//                .body("content.size()", greaterThanOrEqualTo(2));
//
//        setupMockGetDocumentRule(mockFileService, societyAmendmentPublicId, EntitySubType.Society_Bylaw);
//        given()
//                .contentType(ContentType.JSON)
//                .queryParam("entitySubType", EntitySubType.Society_Bylaw)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/{publicId}/checkFileUploadedAll", UUID.fromString(societyAmendmentPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("All_Uploaded", equalTo(true));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("COMPLETE"));
//
//        handleEvents.handleProformaReturnEvent(new PaymentEventDto(UUID.fromString(societyAmendmentPublicId), "PAID", SubProcess.Society_Amendment));
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("PENDING_COUNTY_APPROVAL"));
//
//        SocietyAmendmentDto societyAmendmentDto = new SocietyAmendmentDto();
//        societyAmendmentDto.setPublicId(UUID.fromString(societyAmendmentPublicId));
//
//        SocietyAmendmentTaskDto societyAmendmentTaskDto = new SocietyAmendmentTaskDto(societyAmendmentDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "Approved", "MARK_PENDING_HEAD_REGISTRATION_ASSIGN");
//        handleEvents.handleSocietyAmendmentApprovalReturnEvents(societyAmendmentTaskDto);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("PENDING_HEAD_REGISTRATION_ASSIGN"));
//
//        AssignOfficerDto req = new AssignOfficerDto(Arrays.asList(UUID.fromString("7a40fd66-36a6-4487-9509-e9b12a61bff9"), UUID.fromString("2d7c44c0-3e3c-415d-b4e3-feb799f41e04")));
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(req)
//                .put("/societies/amendments/{publicId}/assign", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}/assign/users", UUID.fromString(societyAmendmentPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("content.size()", equalTo(2));
//
//        societyAmendmentDto = new SocietyAmendmentDto();
//        societyAmendmentDto.setPublicId(UUID.fromString(societyAmendmentPublicId));
//        societyAmendmentTaskDto = new SocietyAmendmentTaskDto(societyAmendmentDto, "e76345b6-8c1e-4aa2-9512-39467e6ec336", "More data needed", "MARK_REQUEST_MORE_INFO");
//        handleEvents.handleSocietyAmendmentApprovalReturnEvents(societyAmendmentTaskDto);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("MORE_INFORMATION_NEEDED"));
//
//        ResubmitMessage resubmitMessage = new ResubmitMessage("Added the required Data", SubProcess.Society_Amendment);
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .body(resubmitMessage)
//                .put("/societies/{publicId}/resubmit", UUID.fromString(societyAmendmentPublicId))
//                .then()
//                .statusCode(200);
//
//        given()
//                .contentType(ContentType.JSON)
//                .auth()
//                .oauth2(accessToken)
//                .get("/societies/amendments/{publicId}", UUID.fromString(societyAmendmentPublicId))
//                .then().log().all()
//                .statusCode(200)
//                .body("status", equalToIgnoringCase("RESUBMITTED"));
//    }
//}
