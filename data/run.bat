@echo off

if "%2"=="init" goto INIT

java -Denv=%1 -cp "data-1.0-SNAPSHOT.jar;dependency/*" org.springframework.shell.Bootstrap --disableInternalCommands
goto DONE

:INIT
java -Denv=%1 -cp "data-1.0-SNAPSHOT.jar;dependency/*" org.springframework.shell.Bootstrap --disableInternalCommands --cmdfile init.commands
goto DONE

:DONE