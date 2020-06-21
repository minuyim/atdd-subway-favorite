package wooteco.subway.web.member.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wooteco.subway.infra.JwtTokenProvider;
import wooteco.subway.web.member.AuthorizationExtractor;
import wooteco.subway.web.member.InvalidAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {
	private static final String LOGIN_MEMBER_EMAIL = "loginMemberEmail";

	private final AuthorizationExtractor authExtractor;
	private final JwtTokenProvider jwtTokenProvider;

	public BearerAuthInterceptor(AuthorizationExtractor authExtractor, JwtTokenProvider jwtTokenProvider) {
		this.authExtractor = authExtractor;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) {
		final String bearer = authExtractor.extract(request, "bearer");
		if (!jwtTokenProvider.validateToken(bearer)) {
			throw new InvalidAuthenticationException("유효하지 않은 토큰값입니다.");
		}
		String email = jwtTokenProvider.getSubject(bearer);

		request.setAttribute(LOGIN_MEMBER_EMAIL, email);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response,
						   Object handler,
						   ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
								Exception ex) throws Exception {

	}
}
