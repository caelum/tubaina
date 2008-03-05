package br.com.caelum.tubaina.util;

public class HtmlSanitizer {

	public String sanitize(String text) {
		final StringBuffer sane = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			switch (text.charAt(i)) {
			case '\u00C0': // À
				sane.append("&Agrave;");
				break;
			case '\u00C1': // Á
				sane.append("&Aacute;");
				break;
			case '\u00C2': // Â
				sane.append("&Acirc;");
				break;
			case '\u00C3': // Ã
				sane.append("&Atilde;");
				break;
			case '\u00C4': // Ä
				sane.append("&Auml;");
				break;
			case '\u00C5': // Å
				sane.append("&Aring;");
				break;
			case '\u00C6': // Æ
				sane.append("&AElig;");
				break;
			case '\u00C7': // Ç
				sane.append("&Ccedil;");
				break;
			case '\u00C8': // È
				sane.append("&Egrave;");
				break;
			case '\u00C9': // É
				sane.append("&Eacute;");
				break;
			case '\u00CA': // Ê
				sane.append("&Ecirc;");
				break;
			case '\u00CB': // Ë
				sane.append("&Euml;");
				break;
			case '\u00CC': // Ì
				sane.append("&Igrave;");
				break;
			case '\u00CD': // Í
				sane.append("&Iacute;");
				break;
			case '\u00CE': // Î
				sane.append("&Icirc;");
				break;
			case '\u00CF': // Ï
				sane.append("&Iuml;");
				break;
			case '\u00D0': // Ð
				sane.append("&#272;");
				break;
			case '\u00D1': // Ñ
				sane.append("&Ntilde;");
				break;
			case '\u00D2': // Ò
				sane.append("&Ograve;");
				break;
			case '\u00D3': // Ó
				sane.append("&Oacute;");
				break;
			case '\u00D4': // Ô
				sane.append("&Ocirc;");
				break;
			case '\u00D5': // Õ
				sane.append("&Otilde;");
				break;
			case '\u00D6': // Ö
				sane.append("&Ouml;");
				break;
			case '\u00D8': // Ø
				sane.append("&Oslash;");
				break;
			case '\u0152': // Œ
				sane.append("&OElig;");
				break;
			case '\u00DE': // Þ
				sane.append("&THORN;");
				break;
			case '\u00D9': // Ù
				sane.append("&Ugrave;");
				break;
			case '\u00DA': // Ú
				sane.append("&Uacute;");
				break;
			case '\u00DB': // Û
				sane.append("&Ucirc;");
				break;
			case '\u00DC': // Ü
				sane.append("&Uuml;");
				break;
			case '\u00DD': // Ý
				sane.append("&Yacute;");
				break;
			case '\u0178': // Ÿ
				sane.append("&Ycirc;");
				break;
			case '\u00E0': // à
				sane.append("&agrave;");
				break;
			case '\u00E1': // á
				sane.append("&aacute;");
				break;
			case '\u00E2': // â
				sane.append("&acirc;");
				break;
			case '\u00E3': // ã
				sane.append("&atilde;");
				break;
			case '\u00E4': // ä
				sane.append("&auml;");
				break;
			case '\u00E5': // å
				sane.append("&aring;");
				break;
			case '\u00E6': // æ
				sane.append("&aelig;");
				break;
			case '\u00E7': // ç
				sane.append("&ccedil;");
				break;
			case '\u00E8': // è
				sane.append("&egrave;");
				break;
			case '\u00E9': // é
				sane.append("&eacute;");
				break;
			case '\u00EA': // ê
				sane.append("&ecirc;");
				break;
			case '\u00EB': // ë
				sane.append("&euml;");
				break;
			case '\u00EC': // ì
				sane.append("&igrave;");
				break;
			case '\u00ED': // í
				sane.append("&iacute;");
				break;
			case '\u00EE': // î
				sane.append("&icirc;");
				break;
			case '\u00EF': // ï
				sane.append("&iuml;");
				break;
			case '\u00F0': // ð
				sane.append("&eth;");
				break;
			case '\u00F1': // ñ
				sane.append("&ntilde;");
				break;
			case '\u00F2': // ò
				sane.append("&ograve;");
				break;
			case '\u00F3': // ó
				sane.append("&oacute;");
				break;
			case '\u00F4': // ô
				sane.append("&ocirc;");
				break;
			case '\u00F5': // õ
				sane.append("&otilde;");
				break;
			case '\u00F6': // ö
				sane.append("&ouml;");
				break;
			case '\u00F8': // ø
				sane.append("&oslash;");
				break;
			case '\u0153': // œ
				sane.append("&oelig;");
				break;
			case '\u00DF': // ß
				sane.append("&szlig;");
				break;
			case '\u00FE': // þ
				sane.append("&thorn;");
				break;
			case '\u00F9': // ù
				sane.append("&ugrave;");
				break;
			case '\u00FA': // ú
				sane.append("&uacute;");
				break;
			case '\u00FB': // û
				sane.append("&ucirc;");
				break;
			case '\u00FC': // ü
				sane.append("&uuml;");
				break;
			case '\u00FD': // ý
				sane.append("&yacute;");
				break;
			case '\u00FF': // ÿ
				sane.append("&yuml;");
				break;
			case '<':
				sane.append("&lt;");
				break;
			case '>':
				sane.append("&gt;");
				break;
			case '&':
				sane.append("&amp;");
				break;
			default:
				sane.append(text.charAt(i));
			}
		}
		return sane.toString();
	}

}
