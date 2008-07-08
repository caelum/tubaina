package br.com.caelum.tubaina.resources;

public class ExerciseResource implements Resource {

	private final int id;

	public ExerciseResource(int id) {
		this.id = id;

	}

	public void copyTo(ResourceManipulator manipulator) {
		manipulator.copyExercise(id);
	}

}
