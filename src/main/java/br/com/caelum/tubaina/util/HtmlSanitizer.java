package br.com.caelum.tubaina.util;

import java.util.HashMap;
import java.util.Map;

public class HtmlSanitizer implements Sanitizer {

	private Map<Character, String> map;

	public HtmlSanitizer() {
		//read from sanitizer.properties
		map = new HashMap<Character, String>();
		map.put('\u00C0', "&Agrave;");
		map.put('\u00C1', "&Aacute;");
		map.put('\u00C2', "&Acirc;");
		map.put('\u00C3', "&Atilde;");
		map.put('\u00C4', "&Auml;");
		map.put('\u00C5', "&Aring;");
		map.put('\u00C6', "&AElig;");
		map.put('\u00C7', "&Ccedil;");
		map.put('\u00C8', "&Egrave;");
		map.put('\u00C9', "&Eacute;");
		map.put('\u00CA', "&Ecirc;");
		map.put('\u00CB', "&Euml;");
		map.put('\u00CC', "&Igrave;");
		map.put('\u00CD', "&Iacute;");
		map.put('\u00CE', "&Icirc;");
		map.put('\u00CF', "&Iuml;");
		map.put('\u00D0', "&#272;");
		map.put('\u00D1', "&Ntilde;");
		map.put('\u00D2', "&Ograve;");
		map.put('\u00D3', "&Oacute;");
		map.put('\u00D4', "&Ocirc;");
		map.put('\u00D5', "&Otilde;");
		map.put('\u00D6', "&Ouml;");
		map.put('\u00D8', "&Oslash;");
		map.put('\u0152', "&OElig;");
		map.put('\u00DE', "&THORN;");
		map.put('\u00D9', "&Ugrave;");
		map.put('\u00DA', "&Uacute;");
		map.put('\u00DB', "&Ucirc;");
		map.put('\u00DC', "&Uuml;");
		map.put('\u00DD', "&Yacute;");
		map.put('\u0178', "&Ycirc;");
		map.put('\u00E0', "&agrave;");
		map.put('\u00E1', "&aacute;");
		map.put('\u00E2', "&acirc;");
		map.put('\u00E3', "&atilde;");
		map.put('\u00E4', "&auml;");
		map.put('\u00E5', "&aring;");
		map.put('\u00E6', "&aelig;");
		map.put('\u00E7', "&ccedil;");
		map.put('\u00E8', "&egrave;");
		map.put('\u00E9', "&eacute;");
		map.put('\u00EA', "&ecirc;");
		map.put('\u00EB', "&euml;");
		map.put('\u00EC', "&igrave;");
		map.put('\u00ED', "&iacute;");
		map.put('\u00EE', "&icirc;");
		map.put('\u00EF', "&iuml;");
		map.put('\u00F0', "&eth;");
		map.put('\u00F1', "&ntilde;");
		map.put('\u00F2', "&ograve;");
		map.put('\u00F3', "&oacute;");
		map.put('\u00F4', "&ocirc;");
		map.put('\u00F5', "&otilde;");
		map.put('\u00F6', "&ouml;");
		map.put('\u00F8', "&oslash;");
		map.put('\u0153', "&oelig;");
		map.put('\u00DF', "&szlig;");
		map.put('\u00FE', "&thorn;");
		map.put('\u00F9', "&ugrave;");
		map.put('\u00FA', "&uacute;");
		map.put('\u00FB', "&ucirc;");
		map.put('\u00FC', "&uuml;");
		map.put('\u00FD', "&yacute;");
		map.put('\u00FF', "&yuml;");
		map.put('—', "&mdash;");
		map.put('–', "&ndash;");
		map.put('”', "&rdquo;");
		map.put('“', "&ldquo;");
		map.put('‘', "&lsquo;");
		map.put('’', "&rsquo;");
		map.put('×', "&times;");
		map.put('<', "&lt;");
		map.put('>', "&gt;");
		map.put('&', "&amp;");
	}

	@Override
	public String sanitize(String text) {
		if (text == null) 
		    return "";
		final StringBuilder sane = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char current = text.charAt(i);
			// this is for the rubyhack
			if (current == '<' && i < text.length() - 2) {
				String next = text.substring(i + 1, i + 3);
				if (next.equals("::")) {
					continue;
				}
			}
			if (map.containsKey(current)) {
				sane.append(map.get(current));
			} else {
				sane.append(current);
			}
		}
		return sane.toString();
	}

}
