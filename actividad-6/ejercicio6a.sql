--Leonardo Campos
SET search_path TO ciudad_de_los_ninos;

SELECT nomProg, SUM(monto) AS total_mensual
FROM aporte
WHERE frecuencia = 'MENSUAL'
GROUP BY nomProg
ORDER BY nomProg;