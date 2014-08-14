package eu.anasta.bm.fail2ban;

import java.util.Collection;
import java.util.HashMap;

import net.bluemind.core.api.custom.props.CustomProperty;
import net.bluemind.core.api.custom.props.CustomPropertyType;
import net.bluemind.core.custom.props.ICustomPropertyRequirements;

public class Fail2BanPropertyRequirements implements
		ICustomPropertyRequirements {
	private HashMap<String, CustomProperty> props;
	
	public Fail2BanPropertyRequirements() {
		props = new HashMap<String, CustomProperty>();
		CustomProperty cp = null;

		// system
		cp = new CustomProperty();
		cp.setRequesterId(getRequesterID());
		cp.setEntity("domain");
		cp.setName("mailAlert");
		cp.setType(CustomPropertyType.STRING);
		cp.setGlobalAdminOnly(true);
		cp.addNameTranslation("fr", "E-mail notifiaction de connexion");
		cp.addNameTranslation("en", "Email alert connection");
		props.put(cp.getEntity() + "::" + cp.getName(), cp);

	}

	@Override
	public String getRequesterID() {
		return "fail2ban";
	}

	@Override
	public Collection<CustomProperty> getRequirements() {
		return props.values();
	}

	@Override
	public CustomProperty getByName(String pName) {
		return props.get(pName);
	}

}
