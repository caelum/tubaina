var Tubaina = (function() {
	var initialTimestamp = new Date().getTime()
	
	/** Configuration options **/
	var defaultOptions = {
		title: 'Tubaina Book',
		header: 'Caelum - www.caelum.com.br - {title}',
		footer: 'Chapter {chapterNumber} - {chapter} - {section} - Page {page}',
		
		showIndex: true,
		indexHeader: '',
		indexFooter: '{page}',
		
		showAnswers: false,
		answersHeader: '',
		answersFooter: '{page}',
		
		showNotes: false,
		showTodos: false,
		
		debug: false
	};
	
	/**
	 * Tuba main object. Holds important variables and init code
	 */
	var tuba = {		
		config: function(options) {
			this.options = helpers.mergeObjects(defaultOptions, options);
		},
		
		init: function() {
			this.initTubainaElements();
			this.extractPageHeightFromCSS();
			this.createProgressBar();
			
			// create elements
			this.book = document.createElement('div');
			this.book.id = 'book';
			document.body.appendChild(this.book);
			
			this.index      = new Index();
			this.bookPages  = new Pages({
					parent: this.book, 
					id: 'book-content', 
					headerTpl: this.options.header,
					footerTpl: this.options.footer
			});
		},
		
		initTubainaElements: function() {
			this.tubaina = document.getElementById('tubaina');
			this.parts = this.tubaina.children.length
			this.elements_processed = 0;
		},
		
		createProgressBar: function() {
			this.progressbar = document.createElement('div');
			this.progressbar.id = 'progressbar';
			
			this.progressbar_value = document.createElement('span');
			this.progressbar_value.textContent = 'Done ';
			
			this.progressbar.appendChild(this.progressbar_value);
			document.body.appendChild(this.progressbar);
		},
		
		extractPageHeightFromCSS: function() {
			// TODO remove jquery
			var temp = $('<div class=page><div class=content>').appendTo('body').find('.content')
			this.MAX_WIDTH = temp.width();
			this.MAX_HEIGHT = temp.height();
			temp.parent().remove();
		}
	};
	
	/* Process while loop */
	function process() {
		var current = null;
		while (current = tuba.tubaina.firstChild) {
			current = tuba.tubaina.removeChild(current);
			
			// not an element
			if (current.nodeType != 1) continue;

			tuba.elements_processed++;

			 // hidden element, such as notes
			if (helpers.computedStyle(current, "display") == 'none') continue;

			// special tags treatment
			var tagName = current.tagName;
			if (tagName == "H1") {

				// chapters always start in new pages			
				if (tuba.bookPages.currentHeight() != 0) {
					tuba.bookPages.newBlankPageIfOdd();
					tuba.bookPages.newPage();
				}

				tuba.bookPages.addPageClass('newchapter')
			}

			tuba.bookPages.appendToCurrentPage(current);
			tuba.bookPages.checkPageOverflow(current);
			
			// index
			if (tagName == "H1") {
				tuba.index.newChapter(current, tuba.bookPages.pageNumber)
			} else if (tagName == "H2") {
				tuba.index.newSection(current, tuba.bookPages.pageNumber)
			}

			tuba.progressbar_value.style.width = (tuba.elements_processed * 100 / tuba.parts) + '%';

			if (tuba.bookPages.pageNumber % 50 == 0) {
				setTimeout(process, 0)
				return
			}
		}

		tuba.bookPages.finish()
		tuba.index.finish()
		
		tuba.tubaina.style.display = 'none';
		tuba.progressbar.className = 'done';
		
		if (tuba.options.debug) {
			var time = new Date().getTime()
			console.log("Total Tubaina time: " + (time - initialTimestamp) + "ms")
		}
	}
	
	/**
	 * Pages class 
	 */
	function Pages(config) {
		var pagesContainer = document.createElement('div');
		if (config.id) pagesContainer.id = config.id;
		config.parent.appendChild(pagesContainer);
		
		this.pagesContainer = pagesContainer;
		this.pageNumber = 0;
		this.config = config;

		this.init();
	}
	Pages.prototype = {
		init: function() {
			this.newPage();	
		},
		
		newPage: function(className) {
			if (this._pageEl) {
				this.endPage()
			}
			this.pageNumber++;

			// create a new page elements
			var page = helpers.createElement('div', {
					class: 'page' + (className? ' ' + className : ''), 
					'data-page-number': this.pageNumber
			});

			var content = helpers.createElement('div', {class: 'content'});
			content.style.height = 'auto';
			
			page.appendChild(this._header = helpers.createElement('div', {class: 'header'}));
			page.appendChild(content);
			page.appendChild(this._footer = helpers.createElement('div', {class: 'footer'}));
			
			// insert new page
			this.pagesContainer.appendChild(page);

			// save elements
			this._pageEl = page;
			this._pageContentEl = content;
		},
		
		newBlankPageIfOdd: function() {
			if (this.pageNumber % 2 == 1) {
				this.newPage()
				this.addPageClass('blank');
			}
		},

		addPageClass: function(className) {
			this._pageEl.className += ' ' + className;
		},

		appendToCurrentPage: function(element) {
			this._pageContentEl.appendChild(element);
		},

		removeFromCurrentPage: function(element) {
			this._pageContentEl.removeChild(element);
		},

		currentHeight: function() {
			return parseFloat(helpers.computedStyle(this._pageContentEl, 'height').replace('px',''))
		},

		endPage: function() {
			this._pageContentEl.style.height = '';
			this._updateHeaderAndFooter()
		},

		finish: function() {
			this.endPage();
		},
		
		_updateHeaderAndFooter: function() {
			var tplContext = this._assembleTemplateContext();
			this._updateTemplateBlock(this._header, this.config.headerTpl, tplContext);
			this._updateTemplateBlock(this._footer, this.config.footerTpl, tplContext);
		},
		_assembleTemplateContext: function() {
			var ctx = {
				title: tuba.options.title,
				page: this.pageNumber	
			}
			if (tuba.index) {
				ctx.chapter = tuba.index.currentChapter;				
				ctx.chapterNumber = tuba.index.chapters;
				ctx.section = tuba.index.currentSection;
				ctx.sectionNumber = tuba.index.sections;
			}
			return ctx;
		},
		_updateTemplateBlock: function(el, template, tplContext) {
			if (typeof template === 'string')
				el.innerHTML = helpers.applyTemplate(template, tplContext);
			else if (typeof template === 'function')
				template.apply(window, [el, tplContext]);
			else
				console.error(className + ' must be string or function');
		}
	}
	Pages.prototype.checkPageOverflow = function(current) {
		if (this.currentHeight() >= tuba.MAX_HEIGHT) {

			// copies last element
			var duplicated = current.cloneNode(true);
			duplicated.style.marginTop = 0;
			duplicated.style.paddingTop = 0;

			// how much overflowed?
			current.style.marginBottom = 0;
			current.style.paddingBottom = 0;

			// do different things depending on element tag
			var tagName = current.tagName;
			if (tagName == "P") {
				//  paragraphs should be splitted between the two pages

				var overflow = this.currentHeight() - tuba.MAX_HEIGHT;
				var height = helpers.computedDimension(current, 'height');
				var lineHeight = helpers.computedDimension(current, 'line-height');

				var totalLines = height / lineHeight;
				var overflowedLines = Math.ceil(overflow / lineHeight);
				var firstLines = totalLines - overflowedLines;

				var newPHeight = firstLines * lineHeight;
				if (newPHeight != 0) {
					current.style.height = newPHeight + 'px';
					current.style.overflow = 'hidden';					
				} else {
					this.removeFromCurrentPage(current);	
				}

				this.newPage();

				if (overflow > 0) {	
					duplicated.style.marginTop = '-' + newPHeight + 'px';
					this.appendToCurrentPage(duplicated);
				}
			} else {
				// default strategy: move the entire element to the new page
				// TODO rearrange elements like latex to avoid big empty space in the bottom
				this.removeFromCurrentPage(current);
				this.newPage();
				this.appendToCurrentPage(duplicated);
			}
		}
	}
	
	/**
	 * Index class
	 */
	function Index(){
		this.chapters = 0;
		this.sections = 0;
		this.indexPages = new Pages({
				parent: tuba.book, 
				id: 'index',
				headerTpl: tuba.options.indexHeader,
				footerTpl: tuba.options.indexFooter
		});
	}
	Index.prototype = {
		newChapter: function(element, page) {
			this.chapters++;
			this.sections = 0;
			this.currentChapter = element.textContent;
		
			this._newEntry(element, 'chapter', page, this.chapters);
		},
		newSection: function(element, page) {
			this.sections++;
			this.currentSection = element.textContent;
			this._newEntry(element, 'section', page, this.chapters+'-'+this.sections);	
		},
		_newEntry: function(element, className, pageNumber, entryCode) {
			var text = element.textContent;
			
			// insert index anchor
			var anchor = helpers.createElement('a', {name: className+'-'+entryCode});
			helpers.prependChild(element, anchor);

			// insert index link
			var p = helpers.createElement('p', {class: className});
			var link = helpers.createElement('a', {href: '#'+className+'-'+entryCode});
			link.textContent = text;
			
			var pageNum = helpers.createElement('span');
			pageNum.textContent = pageNumber;
			
			p.appendChild(link);
			p.appendChild(pageNum);
			
			this.indexPages.appendToCurrentPage(p);
			this.indexPages.checkPageOverflow(p);	
		},
		finish: function() {
			this.indexPages.newBlankPageIfOdd();
			this.indexPages.finish();
		}
	}
	
	/* Helpers */
	var helpers = {
		computedStyle: function(element, property) {
			return window.getComputedStyle(element,null).getPropertyValue(property); // no IE support
		},
		computedDimension: function(element, property) {
			return parseFloat(this.computedStyle(element, property).replace('px',''))
		},
		createElement: function(tagName, attributes) {
			var el = document.createElement(tagName);
			for (var attr in attributes)
				el.setAttribute(attr, attributes[attr])
			return el;
		},
		prependChild: function(parent, element) {
			parent.insertBefore(element, parent.firstChild)
		},
		mergeObjects: function(obj1, obj2) {
			var result = {}, name;
			for (name in obj1) result[name] = obj1[name];
			for (name in obj2) result[name] = obj2[name];
			return result;
		},
		hasClass: function(element, className) {
			return (" " + element.className + " ").indexOf(" " + className + " ") > -1;
		},
		applyTemplate: function(template, context) {
			var result = template;
			for (var key in context) {
				if (context[key])
				result = result.replace('{'+key+'}',context[key]);
			}
			return result;
		}
	}
	
	function highlight() {
		// TODO SyntaxHighlighter.defaults['html-script'] = true; // use only in HTML
		SyntaxHighlighter.defaults['toolbar'] = false;
		SyntaxHighlighter.defaults['auto-links'] = false;

		var elements = document.querySelectorAll('pre.code');
		for (var i = 0; i < elements.length; i++) {
			var el = elements[i];
			var opt = { };

			if (helpers.hasClass(el, 'text')) continue;
			
			if (helpers.hasClass(el, 'numbered')) 
				opt.gutter = true;
			else
				opt.gutter = false;

			var h = el.getAttribute('data-highlight');
			if (h != undefined) {
				opt.highlight = h.split(',');
			}

			opt.brush = (" "+el.className+" ").replace(' code ','').replace(' numbered ','').replace(' ','');
			
			SyntaxHighlighter.highlight(opt, el);
		}
	}
	
	/* exports the public API */
	return {
		init: function(options) {
			tuba.config(options);
			highlight();
			tuba.init();
			setTimeout(process,0);
		}
	}
})();