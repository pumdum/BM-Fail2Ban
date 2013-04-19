package eu.anasta.bm.fail2ban;

import net.bluemind.core.ICore;
import net.bluemind.core.api.AccessToken;
import net.bluemind.core.api.ResultList;
import net.bluemind.core.api.system.Domain;
import net.bluemind.core.api.user.User;
import net.bluemind.core.api.user.UserQuery;
import net.bluemind.core.server.auth.DoAuthStatus;
import net.bluemind.core.server.auth.IAuthenticationService;
import net.bluemind.core.utils.TokenHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailAuthentificationService implements IAuthenticationService {
	private static final String FAIL2BANCOUNTER = "login::fail";
	private static final String ADMIN0_LOGIN = "admin0@global.virt";
	private ICore core;
	private static final Logger logger = LoggerFactory
			.getLogger(FailAuthentificationService.class);

	public FailAuthentificationService(ICore cs) {
		this.core = cs;
	}

	public String getBmDomain(String userLogin) {
		// TODO Auto-generated method stub
//		return super.getBmDomain(userLogin);
		return null;
	}

	public DoAuthStatus doAuth(String userLogin, Domain domain, String password) {
//		DoAuthStatus res = super.doAuth(userLogin, domain, password);
		int userid = -1;
		AccessToken token = null;
		try {
			UserQuery userQuery = new UserQuery();
			userQuery.setLogin(userLogin);
			userQuery.setDomainId(domain.getId());

			token = TokenHelper.createToken(ADMIN0_LOGIN);
			ResultList<User> bmUsers = core.getUser().find(token, userQuery);

			if (bmUsers.size() == 1) {
				userid = bmUsers.get(0).getId();
			}
		} catch (Exception e) {
			logger.error(
					"Fail to check if user: " + userLogin + "@"
							+ domain.getName() + " exist in BM DB", e);
		}

		logger.info("user failed to log must be tested");
		if (token != null && userid != -1) {
			if (DoAuthStatus.NO.equals(null)) {

				try {
					core.getSetting().setSetting(token, userid,
							FAIL2BANCOUNTER, "0");
					logger.info("user fail to log must be counted");
				} catch (Exception e) {
					logger.error("impossible to set fail tentative");
					e.printStackTrace();
				}
			} else if (DoAuthStatus.YES.equals(null)) {
				try {
					core.getSetting().setSetting(token, userid,
							FAIL2BANCOUNTER, "0");
					logger.info("user fail to log must be claired");
				} catch (Exception e) {
					logger.error("impossible to set fail tentative");
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return  FAIL2BANCOUNTER;
	}

	public boolean isValid(Domain bmDomain) {
		return true;
	}

}
