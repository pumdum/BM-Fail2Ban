package eu.anasta.bm.fail2ban;

import java.util.concurrent.atomic.AtomicInteger;

import net.bluemind.core.ICore;
import net.bluemind.core.api.system.Domain;
import net.bluemind.core.server.auth.DoAuthStatus;
import net.bluemind.core.server.auth.IAuthenticationService;
import net.bluemind.core.server.auth.IAuthenticationServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fail2BanAuthentification implements IAuthenticationServiceFactory,
		IAuthenticationService {

	private static final Logger logger = LoggerFactory
			.getLogger(Fail2BanAuthentification.class);

	private static final String TYPE = "FAIL2BAN";

	public Fail2BanAuthentification() {
	}


	@Override
	public String getBmDomain(String userLogin) {
		return null;
	}

	@Override
	public DoAuthStatus doAuth(String userLogin, Domain domain, String password) {
		String latd = userLogin + "@" + domain.getName();
		AtomicInteger authCount = Fail2BanTrial.getInstance().getAttendsFor(latd);
		if (authCount == null) {
			logger.info("First attempt for {}", latd);
			Fail2BanTrial.getInstance().setAttendsFor(latd);
			return DoAuthStatus.UNKNOWN;
		} else {
			int val = authCount.incrementAndGet();
			if (val > 3) {
				logger.warn(
						"Too many ({}) attempts for {}. Wait 15min to retry",
						val, latd);
				return DoAuthStatus.NO;
			} else {
				logger.info("** Attempt {} for {}", val, latd);
				return DoAuthStatus.UNKNOWN;
			}
		}
	}

	@Override
	public String getType() {

		return Fail2BanAuthentification.TYPE;
	}

	@Override
	public boolean isValid(Domain bmDomain) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public IAuthenticationService create(ICore cs) {
		return this;
	}

	@Override
	public void shutdown() {

	}

	@Override
	public int getPriority() {
		return Integer.MAX_VALUE;
	}

}
