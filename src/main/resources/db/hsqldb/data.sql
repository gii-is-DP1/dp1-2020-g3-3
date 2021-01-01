--Usuarios
--Admin
INSERT INTO users(username,password,enabled) VALUES ('admin','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin','admin');

--Clientes
INSERT INTO users(username,password,enabled) VALUES ('marSoRa','Fly_High14&',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'marSoRa','cliente');

INSERT INTO users(username,password,enabled) VALUES ('dolRamCe','Fly_Low14&',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'dolRamCe','cliente');

--Personal Control
INSERT INTO users(username,password,enabled) VALUES ('48736253T','P1234%adsa',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'48736253T','personalControl');

INSERT INTO users(username,password,enabled) VALUES ('12355435L','P5678%adsa',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'12355435L','personalControl');

INSERT INTO users(username,password,enabled) VALUES ('54425951Q','P9012%adsa',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'54425951Q','personalControl');

--Azafato
INSERT INTO users(username,password,enabled) VALUES ('21333214R','A1234%adsa',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'21333214R','azafato');

INSERT INTO users(username,password,enabled) VALUES ('65519676J','A5678%adsa',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'65519676J','azafato');

--Personal Oficina
INSERT INTO users(username,password,enabled) VALUES ('76188332G','F1234%adsa',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'76188332G','personalOficina');

INSERT INTO users(username,password,enabled) VALUES ('39658948W','F5678%adsa',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'39658948W','personalOficina');


--Aeropuertos
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de São Paulo Guarulhos','São Paulo, Brasil' , 'GRU', '+551124452945');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Madrid-Barajas','Madrid, España' , 'MAD', '+34913211000');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Londres-Heathrow','Londres, Reino Unido' , 'LHR', '+448443351801');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de París-Orly','París, Francia' , 'ORY', '+33892563950');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto Internacional de Tokio','Tokio, Japón' , 'HND', '+81357578111');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto Internacional John F. Kennedy','Nueva York, Estados Unidos' , 'JFK', '+17182444444');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto Internacional de Los Ángeles','Los Ángeles, Estados Unidos' , 'LAX', '+18554635252');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Berlín-Brandeburgo Willy Brandt','Berlín, Alemania' , 'BER', '+4930609160910');

--Aviones
INSERT INTO aviones(tipo_avion,capacidad_pasajero,peso_maximo_equipaje,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A320', 300, 2, 1700, '2015-09-24', true, '2017-09-24', 200, 80, 20);

INSERT INTO aviones(tipo_avion,capacidad_pasajero,peso_maximo_equipaje,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A220', 130, 2, 100, '2019-10-24', true, '2020-01-24', 100, 25, 5);

INSERT INTO aviones(tipo_avion,capacidad_pasajero,peso_maximo_equipaje,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A350', 350, 2, 0, '2016-05-05', true, '2020-12-23', 250, 80, 20);

INSERT INTO aviones(tipo_avion,capacidad_pasajero,peso_maximo_equipaje,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A330', 335 , 2, 300, '2015-01-20', true, '2020-03-10', 250, 75, 10);

--Vuelos
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('11-12-2020 10:40','dd-MM-yyyy hh:mm'),parsedatetime('11-12-2020 21:30','dd-MM-yyyy hh:mm'),30000,1,2,1);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('20-01-2015 8:30','dd-MM-yyyy hh:mm'),parsedatetime('20-01-2015 10:55','dd-MM-yyyy hh:mm'),10000,2,3,2);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('17-01-2018 23:30','dd-MM-yyyy hh:mm'),parsedatetime('18-01-2018 13:40','dd-MM-yyyy hh:mm'),25000,6,5,3);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('03-06-2019 7:10','dd-MM-yyyy hh:mm'),parsedatetime('03-06-2019 13:20','dd-MM-yyyy hh:mm'),30000,6,7,4);


--PersonalControl
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Camila','García Pérez','48736253T','ES 1221089893201234561111',0,5345,'48736253T');
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Felipe','Espadas Monteserrín','12355435L','ES 6621000418401234567892',1,3600,'12355435L');
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Juan Carlos','Gómez Correa','54425951Q','ES 1200815983982678553411',2,4600,'54425951Q');

INSERT INTO control_vuelo(personal_control_id,vuelos_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), 
																 (2, 1), (2, 2), (2, 3), (2, 4),
																 (3, 1), (3, 2), (3, 3), (3, 4);
--Azafatos
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Ana', 'Silfes Guerrero', '21333214R', 'ES 8721000893201234567892', 2500, '21333214R');
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Juana', 'Marín Torres', '65519676J', 'ES 4730045887188485547854', 2400, '65519676J');

INSERT INTO azafatos_vuelo(azafatos_id,vuelos_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), 
														 (2, 1), (2, 2), (2, 3), (2, 4);

--Personal Oficina
INSERT INTO personal_oficina(nombre,apellidos,nif,iban,salario,username)
VALUES ('Ángel','Soria Escalera','76188332G','ES 1020805593365438514879',1600,'76188332G');
INSERT INTO personal_oficina(nombre,apellidos,nif,iban,salario,username)
VALUES ('Sergio','Ferreo Quintán','39658948W','ES 4820381461196657997548',1800,'39658948W');

--Clientes
INSERT INTO clientes(nombre,apellidos,nif,direccion_facturacion,iban,fecha_nacimiento,email,username)
VALUES ('María', 'Soto Ramírez', '01446551N', 'C/Enladrillada,2 1ºB Sevilla', 'ES 7771056418401234567893', '1995-03-08','marisotoram@hotmail.com','marSoRa');
INSERT INTO clientes(nombre,apellidos,nif,direccion_facturacion,iban,fecha_nacimiento,email,username)
VALUES ('Dolores', 'Ramos Ceballos', '29565800A', 'Calle Parera ,15 - 41011 Sevilla', 'ES 4422000418403334567812', '1989-12-03','dolrace89@gmail.com','dolRamCe'); 

--Idiomas
INSERT INTO idioma_types(idioma) VALUES ('ES');
INSERT INTO idioma_types(idioma) VALUES ('EN');
INSERT INTO idioma_types(idioma) VALUES ('FR');
INSERT INTO idioma_types(idioma) VALUES ('DE');
INSERT INTO idioma_types(idioma) VALUES ('JA');
INSERT INTO idioma_types(idioma) VALUES ('ZH');
INSERT INTO idioma_types(idioma) VALUES ('RU');
INSERT INTO idioma_types(idioma) VALUES ('PL');

INSERT INTO idiomas_azafato VALUES (1, 1);
INSERT INTO idiomas_azafato VALUES (1, 2);
INSERT INTO idiomas_azafato VALUES (1, 3);
INSERT INTO idiomas_azafato VALUES (2, 4);
INSERT INTO idiomas_azafato VALUES (2, 5);
INSERT INTO idiomas_azafato VALUES (2, 6);

--Idiomas
INSERT INTO idioma_types(idioma) VALUES ('cat');
INSERT INTO idioma_types(idioma) VALUES ('dog');
INSERT INTO idioma_types(idioma) VALUES ('lizard');
INSERT INTO idioma_types(idioma) VALUES ('snake');
INSERT INTO idioma_types(idioma) VALUES ('bird');
INSERT INTO idioma_types(idioma) VALUES ('hamster');

--Asientos
INSERT INTO asientos(nombre,libre,vuelo_id) VALUES
				   ('F4',true,2),
				   ('A4',true,2);

--Billetes
INSERT INTO billetes(coste,asiento_id,fecha_reserva,clase,cliente_id, vuelo_id)
VALUES (80,1,'2020-04-06',0,1,2);
INSERT INTO billetes(coste,asiento_id,fecha_reserva,clase,cliente_id,vuelo_id)
VALUES (300,2,'2020-04-06',2,2,2);



