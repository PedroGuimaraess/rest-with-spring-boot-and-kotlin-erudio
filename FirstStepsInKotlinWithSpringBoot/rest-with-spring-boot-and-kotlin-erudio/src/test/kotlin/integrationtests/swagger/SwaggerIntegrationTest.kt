package integrationtests.swagger

import br.com.erudio.Startup
import integrationtests.ConfigsTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(classes = [Startup::class], webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest(): AbstractIntegrationTest() {

	@Test
	fun shouldReturnSwaggerUiPage() {
		val content = given()
			.basePath("/swagger-ui/index.html")
			.port(ConfigsTest.SERVER_PORT)
			.`when`()
			.get()
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString()
		assertTrue(content.contains("Swagger UI"))
	}
}
