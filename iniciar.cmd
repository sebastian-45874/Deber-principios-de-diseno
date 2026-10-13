@echo off
cd /d "%~dp0"
echo Iniciando Northwind. Abre http://127.0.0.1:8080 cuando termine el arranque.
call mvnw.cmd spring-boot:run
pause
