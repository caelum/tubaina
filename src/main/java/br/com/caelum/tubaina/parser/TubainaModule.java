package br.com.caelum.tubaina.parser;

import java.util.List;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.tubaina.Book;
import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.Chunk;
import br.com.caelum.tubaina.CompositeChunk;
import br.com.caelum.tubaina.Section;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class TubainaModule extends AbstractModule {

	public TubainaModule() {
		super();
	}

	// TODO: remove this if/else, i.e., chapters always belong to a part
	public void inject(Book book) {
		Injector injector = Guice.createInjector(this);
		List<Chapter> chapters = book.getIntroductionChapters();
		List<BookPart> parts = book.getParts();
		if (parts.size() != 0) {
			for (BookPart part : parts) {
				inject(part, injector);
			}
		} else {
			for (Chapter chapter : book.getChapters()) {
				inject(chapter);
			}
		}
		for (Chapter introductionChapter : chapters) {
			inject(introductionChapter);
		}
	}
	
	private void inject(BookPart part, Injector injector) {
		Chunk introductionChunk = (Chunk) new Mirror().on(part).get().field("introductionChunk");
		if (introductionChunk != null)
			inject(injector, introductionChunk);
		List<Chapter> chapters = part.getChapters();
		for (Chapter chapter : chapters) {
			inject(chapter);
		}
	}

	private void inject(Chapter chapter, Injector injector) {
		inject(injector, (Chunk) new Mirror().on(chapter).get().field("introduction"));
		for (Section section : chapter.getSections()) {
			inject(section);
		}
	}

	private void inject(Section section, Injector injector) {
		for (Chunk chunk : section.getChunks()) {
			inject(injector, chunk);
		}
	}

	private void inject(Chunk chunk, Injector injector) {
		injector.injectMembers(chunk);
		if (chunk instanceof CompositeChunk<?>) {
			CompositeChunk<?> composite = (CompositeChunk<?>) chunk;
			for (Chunk other : composite.getBody()) {
				inject(other);
			}
		}
	}

	private void inject(Injector injector, Chunk chunk) {
		injector.injectMembers(chunk);
		if (chunk instanceof CompositeChunk<?>) {
			CompositeChunk<?> composite = (CompositeChunk<?>) chunk;
			for (Chunk other : composite.getBody()) {
				inject(injector, other);
			}
		}
	}

	// I'm not proud of this. This is tests infrastructure, though.
	public void inject(BookPart part) {
		Injector injector = Guice.createInjector(this);
		inject(part, injector);
	}
	
	public void inject(Chapter chapter) {
		Injector injector = Guice.createInjector(this);
		inject(chapter, injector);
	}
	
	public void inject(Section section) {
		Injector injector = Guice.createInjector(this);
		inject(section, injector);
	}
	
	public void inject(Chunk chunk) {
		Injector injector = Guice.createInjector(this);
		inject(chunk, injector);
	}
}
