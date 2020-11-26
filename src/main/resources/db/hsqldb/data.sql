INSERT INTO users(username,password,enabled) VALUES ('01446551N','Fly_High14&',TRUE);

INSERT INTO authorities(id,username,authority) VALUES (1,'01446551N','cliente');

INSERT INTO users(username,password,enabled) VALUES ('21333214R','A3224234232%adsa',TRUE);

INSERT INTO authorities(id,username,authority) VALUES (2,'21333214R','azafato');


--Aeropuertos
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de São Paulo Guarulhos','São Paulo, Brasil' , 'GRU', '11 2445 2945');

INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Barajas','São Paulo, Sevilla' , 'MAD', '913 21 10 00');

--Aviones
INSERT INTO aviones(tipo_avion,capacidad_pasajero,peso_maximo_equipaje,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A320', 300, 2, 1700, '2015-09-24', true, '2017-09-24', 200, 80, 20);

--Azafatos
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Ana', 'Silfes Guerrero', '21333214R', 'ES 8721000893201234567892', 2500, '21333214R');

INSERT INTO idiomas VALUES (1, 'Español');
INSERT INTO idiomas VALUES (2, 'Inglés');
INSERT INTO idiomas VALUES (3, 'Francés');
INSERT INTO idiomas VALUES (4, 'Alemán');
INSERT INTO idiomas VALUES (5, 'Japonés');
INSERT INTO idiomas VALUES (6, 'Chino');
INSERT INTO idiomas VALUES (7, 'Ruso');
INSERT INTO idiomas VALUES (8, 'Polaco');

INSERT INTO idiomas_azafato VALUES (1, 1);
INSERT INTO idiomas_azafato VALUES (1, 2);
INSERT INTO idiomas_azafato VALUES (1, 3);

--Clientes
INSERT INTO clientes(nombre,apellidos,nif,direccion_facturacion,iban,fecha_nacimiento,username)
VALUES ('María', 'Soto Ramírez', '01446551N', 'C/Enladrillada,2 1ºB Sevilla', 'ES 7771056418401234567893', '1995-03-08','01446551N');

--Billetes
INSERT INTO billetes(coste,asiento,fecha_reserva,clase)
VALUES (80,'F4','2020-04-06',0);

--Vuelos
INSERT INTO vuelos(fecha_vuelo,hora_salida,hora_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id) 
VALUES ('2040-12-11','10:40','14:40',64,1,2);

