package wooteco.subway.web.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import wooteco.subway.doc.MemberDocumentation;
import wooteco.subway.domain.member.Member;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.subway.service.member.MemberServiceTest.*;

public class MemberControllerTest extends MemberDocumentationTest {
	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.addFilter(new ShallowEtagHeaderFilter())
				.apply(documentationConfiguration(restDocumentation))
				.build();
	}

	@Test
	public void createMember() throws Exception {
		Member member = Member.of(1L, TEST_USER_EMAIL, TEST_USER_NAME, TEST_USER_PASSWORD);
		given(memberService.createMember(any())).willReturn(member);

		String inputJson = "{\"email\":\"" + TEST_USER_EMAIL + "\"," +
				"\"name\":\"" + TEST_USER_NAME + "\"," +
				"\"password\":\"" + TEST_USER_PASSWORD + "\"}";

		this.mockMvc.perform(post("/members")
				.content(inputJson)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print())
				.andDo(MemberDocumentation.createMember());
	}

	@DisplayName("유저 email이 빈 값이면 예외 처리한다.")
	@Test
	public void createMember2() throws Exception {
		final String email = "";
		Member member = Member.of(1L, email, TEST_USER_NAME, TEST_USER_PASSWORD);
		given(memberService.createMember(any())).willReturn(member);

		String inputJson = "{\"email\":\"" + email + "\"," +
				"\"name\":\"" + TEST_USER_NAME + "\"," +
				"\"password\":\"" + TEST_USER_PASSWORD + "\"}";

		this.mockMvc.perform(post("/members")
				.content(inputJson)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}

	@DisplayName("유저 email 형식이 잘못되면 예외 처리한다.")
	@Test
	public void createMember3() throws Exception {
		final String email = "brown";
		Member member = Member.of(1L, email, TEST_USER_NAME, TEST_USER_PASSWORD);
		given(memberService.createMember(any())).willReturn(member);

		String inputJson = "{\"email\":\"" + email + "\"," +
				"\"name\":\"" + TEST_USER_NAME + "\"," +
				"\"password\":\"" + TEST_USER_PASSWORD + "\"}";

		this.mockMvc.perform(post("/members")
				.content(inputJson)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}

	@DisplayName("유저 name이 빈 값이면 예외 처리한다.")
	@Test
	public void createMember4() throws Exception {
		final String name = "";
		Member member = Member.of(1L, TEST_USER_EMAIL, name, TEST_USER_PASSWORD);
		given(memberService.createMember(any())).willReturn(member);

		String inputJson = "{\"email\":\"" + TEST_USER_EMAIL + "\"," +
				"\"name\":\"" + name + "\"," +
				"\"password\":\"" + TEST_USER_PASSWORD + "\"}";

		this.mockMvc.perform(post("/members")
				.content(inputJson)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}

	@DisplayName("유저 password가 빈 값이면 예외 처리한다.")
	@Test
	public void createMember5() throws Exception {
		final String password = "";
		Member member = Member.of(1L, TEST_USER_EMAIL, TEST_USER_NAME, password);
		given(memberService.createMember(any())).willReturn(member);

		String inputJson = "{\"email\":\"" + TEST_USER_EMAIL + "\"," +
				"\"name\":\"" + TEST_USER_NAME + "\"," +
				"\"password\":\"" + password + "\"}";

		this.mockMvc.perform(post("/members")
				.content(inputJson)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
}
