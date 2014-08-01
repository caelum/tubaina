<#assign relative = "..">
<#include "header.ftl">

<div id="tubaina">
    <div class="chapter referenceable">
        <h1 class="referenceableTitle">${sanitizer.sanitize(introductionChapter.title)}</h1>
		<a id="${introductionChapter.label}" class='label'></a>
    	   		${introductionChapter.introduction}
    </div>
</div>
    
<#include "footer.ftl">