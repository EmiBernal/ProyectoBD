--Manu Barbieri
SET search_path TO ciudad_de_los_ninos;

SELECT dni
FROM aporte
GROUP BY dni
HAVING COUNT(DISTINCT nomprog) > 2;