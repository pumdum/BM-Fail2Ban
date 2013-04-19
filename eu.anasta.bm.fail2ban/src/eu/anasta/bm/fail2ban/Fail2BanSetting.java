package eu.anasta.bm.fail2ban;

import java.util.Collection;
import java.util.HashMap;

import net.bluemind.core.api.custom.settings.CustomSetting;
import net.bluemind.core.api.custom.settings.SettingType;
import net.bluemind.core.custom.settings.ICustomSettingRequirements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fail2BanSetting implements ICustomSettingRequirements {
	private static final Logger logger = LoggerFactory
			.getLogger(Fail2BanSetting.class);
	private final HashMap<String, CustomSetting> props;
	
	public Fail2BanSetting() {
		props = new HashMap<String, CustomSetting>();
		CustomSetting cs = null;

		// faild attend
		cs = new CustomSetting();
		cs.setRequesterId(getRequesterId());
		cs.setApp("login");
		cs.setName("fail");
		cs.setType(SettingType.INT);
		cs.addNameTranslation("fr", "tentative de connection");
		cs.addNameTranslation("en", "tentative login");

		props.put(cs.getApp() + "::" + cs.getName(), cs);

	}

	@Override
	public String getRequesterId() {
		return "net.bluemind.custom.settings.calendar";
	}

	@Override
	public Collection<CustomSetting> getRequirements() {
		return props.values();
	}

	@Override
	public CustomSetting getByName(String setting) {
		CustomSetting ret = props.get(setting);

		if (ret == null) {
			logger.warn("Setting not found: " + setting);
		}

		return ret;
	}

}
