<#assign relative = '.'>
<#include "header.ftl">

<div id="tubaina">

	<#list book.introductionChapters as introductionChapter>
		<#include "introduction.ftl">
	</#list>

    <div class="toc">
    	<h1>Sumário</h1>
        <#assign chapterCount = 1>
        <ul>
        <#assign curdir = 1>
        <#list chapters as chapter>
            <#assign chapdir = dirTree[curdir]>
            <#assign curdir = curdir + 1>
            <li>
                <a class="indexChapter" href="${chapter.titleSlug}/index.html">
                    ${chapterCount} - ${sanitizer.sanitize(chapter.getTitle())}
                </a>
            </li>
            <#assign sectionCount = 1>
            <ul>
                <#list chapter.sections as section>
                    <li>
                        <a class="indexSection" href="${chapter.titleSlug}/index.html#${chapter.chapterNumber}-${sectionCount}-${section.titleSlug}">
                            ${chapterCount}.${sectionCount} - ${sanitizer.sanitize(section.getTitle())}
                        </a>
                    </li>
                    <#assign sectionCount = sectionCount + 1>
                </#list>
            </ul>
            <#assign chapterCount = chapterCount + 1>
        </#list>
        </ul>
    </div>
</div>

<#include "footer.ftl">