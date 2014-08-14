package eu.anasta.bm.fail2ban;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.bluemind.core.api.custom.props.CustomProperty;
import net.bluemind.core.api.ui.Extender;
import net.bluemind.core.api.ui.FormExtension;
import net.bluemind.core.handler.ui.IExtensionProvider;

public class SystemExtensionProvider implements IExtensionProvider {
	private final HashMap<String, Collection<Extender>> sysExts;
	public SystemExtensionProvider() {
		sysExts = new HashMap<String, Collection<Extender>>();

		Fail2BanPropertyRequirements failpr = new Fail2BanPropertyRequirements();
		
		Extender failParameters = new Extender();
		failParameters.setName("fr", "Notification de connection");
		failParameters.setName("en", "Alert connection");
		
		CustomProperty mail = failpr.getByName("domain::mailAlert");
		mail.setGlobalAdminOnly(true);
		


		failParameters
				.setProperties(Arrays.asList(fe(mail)));

		sysExts.put("editDomain::TAB::kind_fail2ban",
				Arrays.asList(failParameters));
	}

	@Override
	public Map<String, Collection<Extender>> getUiExtensions() {
		return sysExts;
	}
//	private FormExtension fe(CustomAction ca) {
//		return new FormExtension(ca);
//	}

	private FormExtension fe(CustomProperty cp) {
		return new FormExtension(cp);
	}
}
