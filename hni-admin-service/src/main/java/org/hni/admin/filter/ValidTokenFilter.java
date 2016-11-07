package org.hni.admin.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.hni.security.service.DefaultUserSecurityService;
import org.hni.security.service.UserSecurityService;
import org.hni.user.om.User;


public class ValidTokenFilter extends AuthorizationFilter {
	private static final String TOKEN_HEADER = "X-hni-token";
	private static final UserSecurityService userSecurityService = new DefaultUserSecurityService();

	/*
	 * TODO: may be able to get away with an
	 * 
	 * @Inject UserSecurityService userSecurityService
	 */

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader(TOKEN_HEADER);
		return isValid(token);
	}

	private boolean isValid(String token) {
		User user = userSecurityService.validateToken(token);
		return (0 != user.getId() && 0 != user.getOrganizationId());
	}
}