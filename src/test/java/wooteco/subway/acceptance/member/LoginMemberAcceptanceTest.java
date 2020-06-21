package wooteco.subway.acceptance.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.AcceptanceTest;
import wooteco.subway.service.member.dto.LoginRequest;
import wooteco.subway.service.member.dto.MemberResponse;
import wooteco.subway.service.member.dto.TokenResponse;
import wooteco.subway.service.member.dto.UpdateMemberRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginMemberAcceptanceTest extends AcceptanceTest {
	/*
	 * Feature : 로그인 멤버 관리
	 *
	 * Scenario : 로그인 멤버를 관리한다.
	 * When : 로그인을 한다.
	 * Then : 로그인이 되었다.
	 *
	 * When : 나의 정보를 조회 요청한다.
	 * Then : 이메일과 이름을 응답받는다.
	 *
	 * When : 나의 정보를 수정 요청한다.
	 * Then : 나의 정보가 수정되었다.
	 *
	 * When : 계정 탈퇴 요청을 한다.
	 * Then : 탈퇴가 되었다.
	 */
	@DisplayName("로그인 멤버를 관리한다.")
	@Test
	void manageLoginMember() {
		createMember(TEST_USER_EMAIL, TEST_USER_NAME, TEST_USER_PASSWORD);

		final TokenResponse tokenResponse = login(new LoginRequest(TEST_USER_EMAIL, TEST_USER_PASSWORD));
		assertThat(tokenResponse).isNotNull();
		assertThat(tokenResponse.getAccessToken()).isNotBlank();
		assertThat(tokenResponse.getTokenType()).isEqualTo("bearer");

		MemberResponse memberOfMineBasic = getMemberOfMineBasic(tokenResponse.getAccessToken());
		assertThat(memberOfMineBasic).isNotNull();
		assertThat(memberOfMineBasic.getEmail()).isEqualTo(TEST_USER_EMAIL);
		assertThat(memberOfMineBasic.getName()).isEqualTo(TEST_USER_NAME);

		final String updatedName = "사자";
		final UpdateMemberRequest updateMemberRequest = new UpdateMemberRequest(updatedName, null);
		updateMemberOfMineBasic(tokenResponse.getAccessToken(), updateMemberRequest);

		memberOfMineBasic = getMemberOfMineBasic(tokenResponse.getAccessToken());
		assertThat(memberOfMineBasic.getName()).isEqualTo(updatedName);

		deleteMemberOfMineBasic(tokenResponse.getAccessToken());
	}
}
