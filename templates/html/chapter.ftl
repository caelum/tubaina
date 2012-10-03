<#assign relative = "..">
<#include "header.ftl">
<#include "navigation.ftl">

<div id="tubaina">
    <div class="chapter referenceable">
        <div class="chapterHeader">Cap&iacute;tulo<span>${chapter.chapterNumber}</span></div>
        <h1 class="referenceableTitle">${sanitizer.sanitize(chapter.title)}</h1>
		<a id="${chapter.label}" class='label'></a>
		
        ${chapter.getIntroduction(parser)}
        
        <#assign sectionCount = 1>
        <#list chapter.sections as section>
            <div class="section referenceable" id="${chapter.chapterNumber}-${sectionCount}-${section.titleSlug}">
                <h2 class="referenceableTitle">${chapter.chapterNumber}.${sectionCount} - ${sanitizer.sanitize(section.title)}</h2>
                
                <#list section.chunks as chunk>
                    ${chunk.getContent(parser)!""}
                </#list>
                    
                <br/>
            </div>
            <#assign sectionCount = sectionCount + 1>
        </#list>
    </div>
</div>
    
<#include "navigation.ftl">
<#include "footer.ftl">