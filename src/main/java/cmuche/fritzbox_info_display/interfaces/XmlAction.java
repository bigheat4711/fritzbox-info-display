package cmuche.fritzbox_info_display.interfaces;

import org.w3c.dom.Element;

@FunctionalInterface
public interface XmlAction {
	public void run(Element node);
}
