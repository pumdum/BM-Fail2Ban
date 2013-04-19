package eu.anasta.bm.fail2ban;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class Fail2BanTrial {

	private static final Logger logger = LoggerFactory
			.getLogger(Fail2BanTrial.class);

	private final Cache<String, AtomicInteger> trials;
	private static Fail2BanTrial instance;

	private Fail2BanTrial() {
		trials = CacheBuilder.newBuilder()
				.expireAfterAccess(15, TimeUnit.MINUTES).build();
	}

	public static Fail2BanTrial getInstance() {
		if (instance == null) {
			instance = new Fail2BanTrial();
		}
		return instance;
	}

	public AtomicInteger getAttendsFor(String user) {
		return trials.getIfPresent(user);
	}

	public void setAttendsFor(String user, AtomicInteger authCount) {
		if (authCount == null) {
			authCount = new AtomicInteger(1);
		}
		trials.put(user, authCount);
	}

	public void setAttendsFor(String user) {
		setAttendsFor(user, null);
	}

	public void clearAttendsFor(String user){
		trials.invalidate(user);
	}

}
