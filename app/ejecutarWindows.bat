@echo off
echo Compilando app.java...
javac -cp ".;jdbc/postgresql-42.7.5.jar" app.java

if errorlevel 1 (
    echo Error al compilar. Verifica que no haya errores en el codigo.
    pause
    exit /b
)

echo Ejecutando la aplicacion...
java -cp ".;jdbc/postgresql-42.7.5.jar" app
pause
