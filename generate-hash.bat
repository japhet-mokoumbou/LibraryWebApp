@echo off
cd /d "%~dp0"
javac -cp "src/main/webapp/WEB-INF/lib/jbcrypt-0.4.jar" -d target/classes src/main/java/com/library/util/PasswordUtil.java
java -cp "target/classes;src/main/webapp/WEB-INF/lib/jbcrypt-0.4.jar" com.library.util.PasswordUtil admin123

