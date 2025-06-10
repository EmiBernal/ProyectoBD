#!/bin/bash

echo "Compilando app.java..."
javac -cp ".:jdbc/postgresql-42.7.5.jar" app.java

if [ $? -ne 0 ]; then
    echo "Error al compilar. Verifica que no haya errores en el código."
    exit 1
fi

echo "Ejecutando la aplicación..."
java -cp ".:jdbc/postgresql-42.7.5.jar" app
