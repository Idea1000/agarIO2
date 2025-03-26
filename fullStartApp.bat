@echo off
call mvn clean install compile
call mvn -pl Client javafx:run