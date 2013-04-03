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

	private Injector injector;

	public TubainaModule() {
		super();
		injector = Guice.createInjector(this);
	}

	public void inject(Book book) {
		List<BookPart> parts = book.getParts();
		for (BookPart part : parts) {
			inject(part);
		}
	}

	public void inject(BookPart part) {
		Chunk introductionChunk = (Chunk) new Mirror().on(part).get().field("introductionChunk");
		if (introductionChunk != null)
			inject(injector, introductionChunk);
		List<Chapter> chapters = part.getChapters();
		for (Chapter chapter : chapters) {
			inject(chapter);
		}
	}

	public void inject(Chapter chapter) {
		inject(injector, (Chunk) new Mirror().on(chapter).get().field("introduction"));
		for (Section section : chapter.getSections()) {
			inject(section);
		}
	}

	public void inject(Section section) {
		for (Chunk chunk : section.getChunks()) {
			inject(injector, chunk);
		}
	}

	public void inject(Chunk chunk) {
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
}
