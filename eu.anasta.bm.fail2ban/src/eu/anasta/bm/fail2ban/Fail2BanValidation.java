package eu.anasta.bm.fail2ban;

import net.bluemind.core.ILoginValidationListener;
import net.bluemind.core.api.system.Domain;
import net.bluemind.core.server.auth.IAuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fail2BanValidation implements ILoginValidationListener {

	private static final Logger logger = LoggerFactory
			.getLogger(Fail2BanValidation.class);

	private static final String TYPE = "FAIL2BAN";


	public Fail2BanValidation() {
	}

	@Override
	public void loginValidated(IAuthenticationService iAuthenticationService,
			String userLogin, Domain bmDomain, String password) {

		Fail2BanTrial.getInstance().clearAttendsFor(userLogin + "@" + bmDomain.getName());

	}


}
