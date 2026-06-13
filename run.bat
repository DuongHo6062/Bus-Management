@echo off
cls

if exist bin rmdir /s /q bin

mkdir bin

javac -d bin -cp src src\view\Main.java

java -cp bin view.Main

pause