package br.com.caelum.tubaina.util;

import java.util.List;

public interface CommandExecutor {

	public abstract String execute(String command, String input);

	public abstract String execute(List<String> args, String input);

}