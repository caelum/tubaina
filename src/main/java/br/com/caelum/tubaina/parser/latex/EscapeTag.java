package br.com.caelum.tubaina.parser.latex;

import br.com.caelum.tubaina.TubainaException;

public class EscapeTag {

	private StringBuilder text = new StringBuilder();

	public String parse(String content) {
		int posicaoAtual = escapaAteVerbatim(content, 0);
		while (posicaoAtual < content.length()) {
			int fimVerbatim = content.indexOf("[/verbatim]", posicaoAtual);
			if(naoAchei(fimVerbatim))
				throw new TubainaException("Verbatim missing end tag");
			String literal = content.substring(posicaoAtual + "[verbatim]".length(), fimVerbatim);
			text.append(literal);
			posicaoAtual = escapaAteVerbatim(content, fimVerbatim + "[/verbatim]".length());
		}
		return text.toString();
	}

	private int escapaAteVerbatim(String content, int posicaoAtual) {
		int ateFimOuVerbatim = content.indexOf("[verbatim]", posicaoAtual);
		if (naoAchei(ateFimOuVerbatim)) {
			ateFimOuVerbatim = content.length();
		}
		text.append(escape(content.substring(posicaoAtual , ateFimOuVerbatim)));
		return ateFimOuVerbatim;
	}

	private boolean naoAchei(int indiceEncontrado) {
		return indiceEncontrado < 0;
	}

	private String escape(String content) {
		for (Escape esc : Escape.values()) {
			content = esc.escape(content);
		}
		return content;
	}

}
