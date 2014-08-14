package eu.anasta.bm.fail2ban;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.bluemind.core.common.utils.DOMUtils;
import net.bluemind.core.handler.host.IDomainTemplateCustomizer;

public class FailTagProvider implements IDomainTemplateCustomizer {

	public FailTagProvider() {
		
	}

	@Override
	public void decorate(Document doc) {
		Element r = doc.getDocumentElement();
		Element ht = DOMUtils.getUniqueElement(r, "hostTags");

		Element kind = DOMUtils.findElementWithUniqueAttribute(ht, "kind",
				"id", "fail2ban");

		if (kind == null) {
			kind = DOMUtils.createElement(ht, "kind");
			kind.setAttribute("id", "fail2ban");
			kind.setAttribute("visibility", "GLOBALDOMAINONLY");

			Element desc = DOMUtils.createElementAndText(kind, "desc",
					"Fail2Ban");
			desc.setAttribute("lang", "fr");

			desc = DOMUtils.createElementAndText(kind, "desc", "Fail2Ban");
			desc.setAttribute("lang", "en");

			desc = DOMUtils.createElementAndText(kind, "helpText",
					"Fail2Ban");
			desc.setAttribute("lang", "fr");
			desc = DOMUtils.createElementAndText(kind, "helpText",
					"Fail2Ban");
			desc.setAttribute("lang", "en");
		}

	}

}
