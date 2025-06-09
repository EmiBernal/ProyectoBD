--Emiliano Bernal
SET search_path TO ciudad_de_los_ninos;

SELECT d.dni, p.nombre_apellido, d.cuit_cuil, d.ocupacion,
       a.nomProg AS programa_aporte, a.frecuencia, m.nombre_titular,
       tc.id_medioPago AS tarjeta, dt.id_medioPago AS debito
FROM  Donante d
JOIN  Padrino p ON d.dni = p.dni
JOIN  Aporte a ON d.dni = a.dni
JOIN  MedioPago m ON a.id_medioPago = m.id_medioPago
LEFT JOIN  TarjetaCredito tc ON m.id_medioPago = tc.id_medioPago
LEFT JOIN  DebitoTransferencia dt ON m.id_medioPago = dt.id_medioPago
WHERE a.frecuencia = 'MENSUAL';