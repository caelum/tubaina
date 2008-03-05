package br.com.caelum.tubaina.resources;


public class IndexResource implements Resource {

	private final String name;
	private final int dirNumber;

	public IndexResource(String name, int dirNumber) {
		this.name = name.toUpperCase();
		this.dirNumber = dirNumber;
		
	}

	public void copyTo(ResourceManipulator manipulator) {
		manipulator.copyIndex(name, dirNumber);
	}

}
