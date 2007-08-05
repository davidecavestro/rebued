@echo off

IF ."%JAVA_HOME%"==. GOTO JdkError

set JAVACMD=%JAVA_HOME%\bin\java.exe

IF NOT EXIST "%JAVACMD%" goto JdkError

set PAR1=%1%
set PAR2=%2%
set PAR32=%3%
set PATH=$PATH;..\lib\

"%JAVACMD%" -Xmx64m -jar ..\urbe.jar %PAR1% %PAR2% %PAR3%

GOTO End

:JdkError
ECHO Could not find a JDK.
ECHO Either you have to install JDK 1.4 (or up),
ECHO or you have to set JAVA_HOME to your JDK installation directory.
pause

:End
