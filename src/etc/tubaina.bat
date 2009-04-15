@echo off

IF NOT DEFINED JAVA_HOME (
	echo "Enviroment variable JAVA_HOME must be set in order to run tubaina"
	goto end
)

IF NOT DEFINED TUBAINA_HOME (
	echo "Environment variable TUBAINA_HOME home must be set in order to run tubaina"
) ELSE (
	SET TUB_REL_PATH=%TUBAINA_HOME%
	"%JAVA_HOME%\bin\java" -classpath @CLASSPATH br.com.caelum.tubaina.Tubaina %*
)

:end