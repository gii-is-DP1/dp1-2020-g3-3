--Usuarios
--Admin
INSERT INTO users(username, password) VALUES ('admin','$2a$10$gPaqVBo4waGhKlUgf60zM.NGMcH2G7aWETHK5TIfMy1jLJskbfW/G');--4dm1n
INSERT INTO authorities(id,username,authority) VALUES (1,'admin','admin');

--Clientes
INSERT INTO users(username,password) VALUES ('01446551N','$2a$10$wj45hFhSMHd1pOClxqz7uu6FCpo17QTjbKkZNt50qqzBqNv8eKpLe');--Fly_High14&
INSERT INTO authorities(id,username,authority) VALUES (2,'01446551N','cliente');

INSERT INTO users(username,password) VALUES ('29565800A','$2a$10$TgksF0KdfWGU7OQuZDOfoedqOmH4xGGOiHiJyv8mmgNYs1c/H0ju2'); --Fly_Low14&
INSERT INTO authorities(id,username,authority) VALUES (3,'29565800A','cliente');

--Personal Control
INSERT INTO users(username,password) VALUES ('17579447H','$2a$10$4MjJVeCg8uvaj07m4Gkcx.sz3WHdEK0pxKqQ1RoBqxVRcBTENwkv6');--P9012%adsa
INSERT INTO authorities(id,username,authority) VALUES (4,'17579447H','personalControl');

INSERT INTO users(username,password) VALUES ('12355435L','$2a$10$4MjJVeCg8uvaj07m4Gkcx.sz3WHdEK0pxKqQ1RoBqxVRcBTENwkv6');
INSERT INTO authorities(id,username,authority) VALUES (5,'12355435L','personalControl');

INSERT INTO users(username,password) VALUES ('54425951Q','$2a$10$4MjJVeCg8uvaj07m4Gkcx.sz3WHdEK0pxKqQ1RoBqxVRcBTENwkv6');
INSERT INTO authorities(id,username,authority) VALUES (6,'54425951Q','personalControl');

INSERT INTO users(username,password) VALUES ('29353803F','$2a$10$4MjJVeCg8uvaj07m4Gkcx.sz3WHdEK0pxKqQ1RoBqxVRcBTENwkv6');
INSERT INTO authorities(id,username,authority) VALUES (7,'29353803F','personalControl');

INSERT INTO users(username,password) VALUES ('31123210R','$2a$10$4MjJVeCg8uvaj07m4Gkcx.sz3WHdEK0pxKqQ1RoBqxVRcBTENwkv6');
INSERT INTO authorities(id,username,authority) VALUES (8,'31123210R','personalControl');

INSERT INTO users(username,password) VALUES ('34109741M','$2a$10$4MjJVeCg8uvaj07m4Gkcx.sz3WHdEK0pxKqQ1RoBqxVRcBTENwkv6');
INSERT INTO authorities(id,username,authority) VALUES (9,'34109741M','personalControl');

--Azafato
INSERT INTO users(username,password) VALUES ('21333214R','$2a$10$/8LFKKoEqggJPTA5Q8KALuzwPfMoCG7kkI25KJrmGy94OT6ObXQ7y');--A1234%adsa
INSERT INTO authorities(id,username,authority) VALUES (10,'21333214R','azafato');

INSERT INTO users(username,password) VALUES ('65519676J','$2a$10$/8LFKKoEqggJPTA5Q8KALuzwPfMoCG7kkI25KJrmGy94OT6ObXQ7y');
INSERT INTO authorities(id,username,authority) VALUES (11,'65519676J','azafato');

INSERT INTO users(username,password) VALUES ('25416743H','$2a$10$/8LFKKoEqggJPTA5Q8KALuzwPfMoCG7kkI25KJrmGy94OT6ObXQ7y');
INSERT INTO authorities(id,username,authority) VALUES (12,'25416743H','azafato');

INSERT INTO users(username,password) VALUES ('17479248F','$2a$10$/8LFKKoEqggJPTA5Q8KALuzwPfMoCG7kkI25KJrmGy94OT6ObXQ7y');
INSERT INTO authorities(id,username,authority) VALUES (13,'17479248F','azafato');

INSERT INTO users(username,password) VALUES ('42693476W','$2a$10$/8LFKKoEqggJPTA5Q8KALuzwPfMoCG7kkI25KJrmGy94OT6ObXQ7y');
INSERT INTO authorities(id,username,authority) VALUES (14,'42693476W','azafato');

INSERT INTO users(username,password) VALUES ('15637561E','$2a$10$/8LFKKoEqggJPTA5Q8KALuzwPfMoCG7kkI25KJrmGy94OT6ObXQ7y');
INSERT INTO authorities(id,username,authority) VALUES (15,'15637561E','azafato');

INSERT INTO users(username,password) VALUES ('16199550Y','$2a$10$/8LFKKoEqggJPTA5Q8KALuzwPfMoCG7kkI25KJrmGy94OT6ObXQ7y');
INSERT INTO authorities(id,username,authority) VALUES (16,'16199550Y','azafato');

INSERT INTO users(username,password) VALUES ('51614708V','$2a$10$/8LFKKoEqggJPTA5Q8KALuzwPfMoCG7kkI25KJrmGy94OT6ObXQ7y');
INSERT INTO authorities(id,username,authority) VALUES (17,'51614708V','azafato');

--Personal Oficina
INSERT INTO users(username,password) VALUES ('76188332G','$2a$10$RZZrQPenMX1vtOkOBC9WxecSxjbWwKoU1SixOHyrgXqKMx7G9DL/S');--F1234%adsa
INSERT INTO authorities(id,username,authority) VALUES (18,'76188332G','personalOficina');

INSERT INTO users(username,password) VALUES ('39658948W','$2a$10$RZZrQPenMX1vtOkOBC9WxecSxjbWwKoU1SixOHyrgXqKMx7G9DL/S');
INSERT INTO authorities(id,username,authority) VALUES (19,'39658948W','personalOficina');


--Aeropuertos
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Sao Paulo Guarulhos','Sao Paulo, Brasil' , 'GRU', '+551124452945');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Madrid-Barajas','Madrid, España' , 'MAD', '+34913211000');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Londres-Heathrow','Londres, Reino Unido' , 'LHR', '+448443351801');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Paris-Orly','Paris, Francia' , 'ORY', '+33892563950');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto Internacional de Tokio','Tokio, Japon' , 'HND', '+81357578111');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto Internacional John F. Kennedy','Nueva York, Estados Unidos' , 'JFK', '+17182444444');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto Internacional de Los Angeles','Los Angeles, Estados Unidos' , 'LAX', '+18554635252');
INSERT INTO aeropuertos(nombre,localizacion,codigo_IATA,telefono)
VALUES ('Aeropuerto de Berlin-Brandeburgo Willy Brandt','Berlin, Alemania' , 'BER', '+4930609160910');

--Aviones
INSERT INTO aviones(tipo_avion,capacidad_pasajero, horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A320', 300, 400, '2015-09-24', true, '2017-09-24', 200, 80, 20);

INSERT INTO aviones(tipo_avion,capacidad_pasajero,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A220', 130, 100, '2019-10-24', true, '2020-01-24', 100, 25, 5);

INSERT INTO aviones(tipo_avion,capacidad_pasajero,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A350', 350, 0, '2016-05-05', true, '2020-12-23', 250, 80, 20);

INSERT INTO aviones(tipo_avion,capacidad_pasajero,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A330', 335 , 300, '2015-01-20', true, '2020-03-10', 250, 75, 10);

--Vuelos
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('11-01-2021 10:40','dd-MM-yyyy hh:mm'),parsedatetime('11-01-2021 21:30','dd-MM-yyyy hh:mm'),300,1,2,1);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('20-01-2015 8:30','dd-MM-yyyy hh:mm'),parsedatetime('20-01-2015 10:55','dd-MM-yyyy hh:mm'),40,2,3,2);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('17-01-2018 23:30','dd-MM-yyyy hh:mm'),parsedatetime('18-01-2018 13:40','dd-MM-yyyy hh:mm'),250,6,5,3);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('03-06-2019 7:10','dd-MM-yyyy hh:mm'),parsedatetime('03-06-2019 13:20','dd-MM-yyyy hh:mm'),300,6,7,4);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('07-01-2021 10:40','dd-MM-yyyy hh:mm'),parsedatetime('07-01-2021 21:30','dd-MM-yyyy hh:mm'),200,1,2,1);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('10-01-2021 10:40','dd-MM-yyyy hh:mm'),parsedatetime('10-01-2021 21:30','dd-MM-yyyy hh:mm'),150,1,6,1);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('06-02-2021 10:40','dd-MM-yyyy hh:mm'),parsedatetime('06-02-2021 21:30','dd-MM-yyyy hh:mm'),500,2,1,3);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('07-02-2021 10:40','dd-MM-yyyy hh:mm'),parsedatetime('07-02-2021 21:30','dd-MM-yyyy hh:mm'),300,1,2,4);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('07-02-2021 10:40','dd-MM-yyyy hh:mm'),parsedatetime('07-02-2021 21:30','dd-MM-yyyy hh:mm'),200,3,4,2);
INSERT INTO vuelos(fecha_salida,fecha_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id,avion_id) 
VALUES (parsedatetime('10-02-2021 10:40','dd-MM-yyyy hh:mm'),parsedatetime('10-02-2021 21:30','dd-MM-yyyy hh:mm'),350,2,3,3);


--PersonalControl
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Camila','Garcia Perez','17579447H','ES 1221089893201234561111',0,5345,'17579447H');
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Felipe','Espadas Monteserrin','12355435L','ES 6621000418401234567892',1,3600,'12355435L');
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Juan Carlos','Gomez Correa','54425951Q','ES 1200815983982678553411',2,4600,'54425951Q');
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Enrique','Garcia Dominguez','29353803F','ES 6014654763591734464249',0,5000,'29353803F');
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Henry','Dalgliesh Cavill','31123210R','ES 6623200418401234567892',0,6000,'31123210R');
INSERT INTO personal_control(nombre,apellidos,nif,iban,rol,salario,username)
VALUES ('Giancarlo','Fisichella','34109741M','ES 4131901222458149879764',0,5400,'34109741M');

INSERT INTO control_vuelo(personal_control_id,vuelos_id) VALUES (1, 1), (1, 2), (1, 3), 
																(2, 1), (2, 2), (2, 3), 
																(3, 1), (3, 2), (3, 3),
																(4, 1), (4, 2), (4, 3),
																(5, 1), (5, 2), (5, 3),
																(6, 1), (6, 2), (6, 3),
																(1, 5), (1, 6), (1, 7);
--Azafatos
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Ana', 'Silfes Guerrero', '21333214R', 'ES 8721000893201234567892', 2500, '21333214R');
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Juana', 'Marin Torres', '65519676J', 'ES 4730045887188485547854', 2400, '65519676J');
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Oliver', 'Potter', '25416743H', 'ES 6731904499194741489412', 2500, '25416743H');
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Jack', 'Wayne', '17479248F', 'ES 2021007488858432375725', 2400, '17479248F');
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Olivia', 'Smith', '42693476W', 'ES 8114655195699842443152', 2500, '42693476W');
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Elizabeth', 'Williams', '15637561E', 'ES 6100819982199212113975', 2400, '15637561E');
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Mia', 'Jones', '16199550Y', 'ES 2914653916811294226862', 2500, '16199550Y');
INSERT INTO azafatos(nombre,apellidos,nif,iban,salario,username)
VALUES ('Lily', 'Brown', '51614708V', 'ES 3300819669812211263641', 2400, '51614708V');

INSERT INTO azafatos_vuelo(azafatos_id,vuelos_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), 
														 (2, 1), (2, 2), (2, 3), (2, 4),
														 (3, 1), (3, 2), (3, 3), (3, 4),
														 (4, 1), (4, 3),
														 (5, 3), (5, 4),
														 (6, 1), (6, 2), (6, 3), (6, 4),
														 (7, 1), (7, 2), (7, 3), (7, 4),
														 (8, 1), (8, 2), (8, 3), (8, 4),
														 (1, 5), (2, 5), (3, 6), (4, 6), (3,7);

--Personal Oficina
INSERT INTO personal_oficina(nombre,apellidos,nif,iban,salario,username)
VALUES ('Angel','Soria Escalera','76188332G','ES 1020805593365438514879',1600,'76188332G');
INSERT INTO personal_oficina(nombre,apellidos,nif,iban,salario,username)
VALUES ('Sergio','Ferrero Quintan','39658948W','ES 4820381461196657997548',1800,'39658948W');

INSERT INTO oficina_vuelo(personal_oficina_id,vuelos_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),(1, 7),
																(2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6),(2, 7);

--Clientes
INSERT INTO clientes(nombre,apellidos,nif,direccion_facturacion,iban,fecha_nacimiento,email,username)
VALUES ('Maria', 'Soto Ramirez', '01446551N', 'C/Enladrillada,2 1ºB Sevilla', 'ES 7771056418401234567893', '1995-03-08','marisotoram@hotmail.com','01446551N');
INSERT INTO clientes(nombre,apellidos,nif,direccion_facturacion,iban,fecha_nacimiento,email,username)
VALUES ('Dolores', 'Ramos Ceballos', '29565800A', 'Calle Parera ,15 3ºB Sevilla', 'ES 4422000418403334567812', '1989-12-03','dolrace89@gmail.com','29565800A'); 

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
INSERT INTO idiomas_azafato VALUES (3, 1);
INSERT INTO idiomas_azafato VALUES (3, 2);
INSERT INTO idiomas_azafato VALUES (3, 3);
INSERT INTO idiomas_azafato VALUES (4, 4);
INSERT INTO idiomas_azafato VALUES (4, 5);
INSERT INTO idiomas_azafato VALUES (4, 6);
INSERT INTO idiomas_azafato VALUES (5, 1);
INSERT INTO idiomas_azafato VALUES (5, 2);
INSERT INTO idiomas_azafato VALUES (5, 3);
INSERT INTO idiomas_azafato VALUES (6, 4);
INSERT INTO idiomas_azafato VALUES (6, 5);
INSERT INTO idiomas_azafato VALUES (6, 6);
INSERT INTO idiomas_azafato VALUES (7, 1);
INSERT INTO idiomas_azafato VALUES (7, 2);
INSERT INTO idiomas_azafato VALUES (7, 3);
INSERT INTO idiomas_azafato VALUES (8, 4);
INSERT INTO idiomas_azafato VALUES (8, 5);
INSERT INTO idiomas_azafato VALUES (8, 6);

--Asientos
INSERT INTO asientos(nombre,libre,clase,vuelo_id) VALUES
				   ('F4',false,0,2),
				   ('A4',false,2,2),
				   ('A3',true,2,2),
				   ('A2',false,2,2);

--Billetes
INSERT INTO billetes(coste,asiento_id,fecha_reserva,cliente_id)
VALUES (80,1,'2020-04-06',1);
INSERT INTO billetes(coste,asiento_id,fecha_reserva,cliente_id)
VALUES (300,2,'2020-04-06',2);
INSERT INTO billetes(coste,asiento_id,fecha_reserva,cliente_id)
VALUES (300,4,'2020-04-06',2);


--PlatoType
INSERT INTO platos_types(name) VALUES ('primerPlato');
INSERT INTO platos_types(name) VALUES ('segundoPlato');
INSERT INTO platos_types(name) VALUES ('postre');

--Plato Base
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.98,1,'Sopa de miso');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.21,1,'Ensalada cesar');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (2,1,'Revuelto de setas y gambas');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (2,1,'Tortellini de ternera Sin gluten');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.98,1,'Fideua de almejas');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (2.32,1,'Salmon con citricos');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.26,1,'Tomate con mozzarella');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.74,1,'Pan con mantequilla');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.91,1,'Pan con mantequilla Sin gluten');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.56,1,'Cereales miniatura');

INSERT INTO platos_base(precio,plato_types_id,name) VALUES (4.78,2,'Pollo Tikka Masala');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (3.54,2,'Arroz con ternera al curry');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (3.12,2,'Arroz con pollo al curry');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (5.54,2,'Pato a la pekinesa');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (3.21,2,'Gnocchi de patata Sin gluten');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (4.76,2,'Ternera con patatas');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (3.45,2,'Merluza al horno');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (2.87,2,'Risotto vegetal');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (2.65,2,'Tortilla con verduras');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.97,2,'Bagel con jamon');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.65,2,'Cruasán con jamon cocido');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (2.01,2,'Rollo de canela');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.29,2,'Muffin de chocolate');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.29,2,'Muffin de vainilla');

INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.23,3,'Manzana');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.29,3,'Platano');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.19,3,'Pera');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.16,3,'Naranja');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.87,3,'Creme brulee');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.21,3,'Tiramisu');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (0.65,3,'Flan de huevo Sin gluten');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.43,3,'Panna cotta');
INSERT INTO platos_base(precio,plato_types_id,name) VALUES (1.74,3,'Macarons Sin gluten');

--Plato
INSERT INTO platos(platos_base_id) VALUES (1);
INSERT INTO platos(platos_base_id) VALUES (13);
INSERT INTO platos(platos_base_id) VALUES (27);

INSERT INTO platos(platos_base_id) VALUES (1);
INSERT INTO platos(platos_base_id) VALUES (14);
INSERT INTO platos(platos_base_id) VALUES (28);

INSERT INTO platos(platos_base_id) VALUES (4);
INSERT INTO platos(platos_base_id) VALUES (14);
INSERT INTO platos(platos_base_id) VALUES (29);

INSERT INTO platos(platos_base_id) VALUES (4);
INSERT INTO platos(platos_base_id) VALUES (16);
INSERT INTO platos(platos_base_id) VALUES (30);

INSERT INTO platos(platos_base_id) VALUES (1);
INSERT INTO platos(platos_base_id) VALUES (14);
INSERT INTO platos(platos_base_id) VALUES (31);

INSERT INTO platos(platos_base_id) VALUES (6);
INSERT INTO platos(platos_base_id) VALUES (18);
INSERT INTO platos(platos_base_id) VALUES (31);

--Menu
INSERT INTO menus(billete_id,plato1_id,plato2_id,plato3_id) VALUES (1,1,2,3);
INSERT INTO menus(billete_id,plato1_id,plato2_id,plato3_id) VALUES (1,4,5,6);
INSERT INTO menus(billete_id,plato1_id,plato2_id,plato3_id) VALUES (1,7,8,9);
INSERT INTO menus(billete_id,plato1_id,plato2_id,plato3_id) VALUES (2,10,11,12);
INSERT INTO menus(billete_id,plato1_id,plato2_id,plato3_id) VALUES (2,13,14,15);
INSERT INTO menus(billete_id,plato1_id,plato2_id,plato3_id) VALUES (2,16,17,18);

--Equipaje Base
INSERT INTO equipajes_base(name, dimensiones, precio) VALUES ('Grande','110x110x78',30.0);
INSERT INTO equipajes_base(name, dimensiones, precio) VALUES ('Mediano','60x60x42',22.0);
INSERT INTO equipajes_base(name, dimensiones, precio) VALUES ('Pequeño','36x36x26',15.0);

--Equipaje
INSERT INTO equipajes(billete_id, peso, equipajes_base_id) VALUES (1,15,1);