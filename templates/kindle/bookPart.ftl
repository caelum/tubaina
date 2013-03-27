<#if part.printable>
	<h1>Part ${partNumber} - ${sanitizer.sanitize(part.title)}</h1>
	<#if !part.getIllustrationPath().isEmpty()>	
    	<img src="$$RELATIVE$$/${part.illustrationPath}" />
	</#if>
	${part.getIntroduction()}
</#if>

${chaptersContent}

		