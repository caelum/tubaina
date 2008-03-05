<#assign relative = '.'>
<#include "header.ftl">

	<div class="docInfo">
		<!-- Something you'd like people to see on the frontpage of your document, -->
		<!-- right above the Table of Contents. Information such as copyrights and -->
		<!-- other important stuff...-->
	</div>
		
		<div class="contentBox">

			<h1 class="bookName">
				${name}
			</h1>
			<hr />
						
			<#assign curdir = 1>
			<#list chapters as chapter>
				<#assign chapdir = dirTree[curdir]>
				<#assign curdir = curdir + 1>
				<h2 class="indexChapter"><a href="../${chapdir}/">Chapter ${chapter_index + 1} - ${chapter.title}</a></h2>
				
				<#assign sectionIndex = 1>
				<#list chapter.sections as section>
					<#assign title = section.title!"" >
					<#if title != "">
						<#assign secdir = dirTree[curdir]>
						<#assign curdir = curdir + 1>
						<h3 class="indexSection"><a href="../${secdir}/">${chapter_index + 1}.${sectionIndex} - ${title}</a></h3>
						<#assign sectionIndex = sectionIndex + 1>
					</#if>
				</#list>
			</#list>
			<h2 class="indexChapter"><a href="index/">Index</a></h2>
													
			<hr/>
													
		</div><!-- contentBox -->
	
		Version: ${textbookVersion}

<#include "footer.ftl">