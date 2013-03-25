package br.com.caelum.tubaina.parser;

import java.util.List;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.tubaina.Book;
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

	public void inject(Chunk chunk) {
		Injector injector = Guice.createInjector(this);
		injector.injectMembers(chunk);
		if (chunk instanceof CompositeChunk<?>) {
			CompositeChunk<?> composite = (CompositeChunk<?>) chunk;
			for (Chunk other : composite.getBody()) {
				inject(other);
			}
		}
	}
	
	public void inject(Book book) {
		Injector injector = Guice.createInjector(this);
		List<Chapter> chapters = book.getChapters();
		for (Chapter chapter : chapters) {
			inject(injector, (Chunk) new Mirror().on(chapter).get().field("introduction"));
			for (Section section : chapter.getSections()) {
				for (Chunk chunk : section.getChunks()) {
					inject(injector, chunk);
				}
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
