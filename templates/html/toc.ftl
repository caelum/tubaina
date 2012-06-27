<#assign relative = '.'>
<#include "header.ftl">

<div id="tubaina">
    <div class="toc">
        <#assign chapterCount = 1>
        <ul>
        <#assign curdir = 1>
        <#list chapters as chapter>
            <#assign chapdir = dirTree[curdir]>
            <#assign curdir = curdir + 1>
            <li>
                <a href="${chapter.titleSlug}/index.html">
                    ${chapterCount} - ${sanitizer.sanitize(chapter.getTitle())}
                </a>
            </li>
            <#assign sectionCount = 1>
            <ul>
                <#list chapter.sections as section>
                    <li>
                        <a href="${chapter.titleSlug}/index.html#${chapter.chapterNumber}-${sectionCount}-${section.titleSlug}">
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