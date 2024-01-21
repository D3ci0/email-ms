package com.gamma.email;

import com.gamma.email.model.dto.EmailResponseMessage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EmailApplicationTests {
	private static final String REST_SERVICE_PORT_PATTERN = "########";
	private static final String REST_SERVICE_URI = "http://localhost:" + REST_SERVICE_PORT_PATTERN;
	private static final Logger logger = LoggerFactory.getLogger(EmailApplicationTests.class);
	@LocalServerPort
	private int port;
	private TestRestTemplate restTemplate;

	@ParameterizedTest
	@CsvSource({"1,1,2024-01-01T19:00:00.000,2024-01-20T14:47:55.892,0,2",
			"1,1,2024-01-01T19:05:00.000,2024-01-20T14:47:55.892,0,1"})
	void getEmailsWithAttachmentsByFilterTest(String tenantId, String userId, LocalDateTime from, LocalDateTime to,
											  String expectedResultCode, int expectedEmailSize) {

		StringBuilder url = new StringBuilder(REST_SERVICE_URI.replaceAll(REST_SERVICE_PORT_PATTERN, Integer.toString(port)))
				.append("/v1/emails/");
		logger.info("BASE URL: {}", url);

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url.toString())
				.path("/{tenantId}/{userId}")
				.queryParam("from", from)
				.queryParam("to", to)
				.encode()
				.build(tenantId, userId)
				.toString();

		logger.info("ENRICHED URL: {}", urlTemplate);

		EmailResponseMessage emailResponseMessage = restTemplate.getForObject(urlTemplate,
				EmailResponseMessage.class);

		logger.info(emailResponseMessage.toString());

		Assert.isTrue(emailResponseMessage.getResult().getResultCode().equals(expectedResultCode), "Expected result code differs from actual result code");
		Assert.isTrue(emailResponseMessage.getEmails().size()==expectedEmailSize, "Expected emails size differs from actual emails size");

	}

	@ParameterizedTest
	@CsvSource({"1,1,1,prova.pdf",
			"1,1,2,prova.txt"})
	void getEmailAttachmentTest(String tenantId, String userId, String emailId, String fileName){
		StringBuilder url = new StringBuilder(REST_SERVICE_URI.replaceAll(REST_SERVICE_PORT_PATTERN, Integer.toString(port)))
				.append("/v1/attachment/");;
		logger.info("BASE URL: {}", url);

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url.toString())
				.path("/{tenantId}/{userId}/{attachmentId}")
				.encode()
				.build(tenantId, userId, emailId)
				.toString();

		logger.info("ENRICHED URL: {}", urlTemplate);

		ResponseEntity<Resource> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Resource.class);

		Assert.isTrue(response.getBody() != null, "Response body is null");
		Assert.isTrue(response.getBody().getFilename() != null, "File name is null");
		Assert.isTrue(response.getBody().getFilename().equals(fileName), "Expected file name differs from actual file name");
	}

	@Autowired
	public void setRestTemplate(TestRestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
}
