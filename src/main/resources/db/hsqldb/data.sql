
INSERT INTO users(username,password,enabled) VALUES ('28976897W','Fly_High14&',TRUE);

INSERT INTO authorities(id,username,authority) VALUES (1,'28976897W','cliente');

INSERT INTO users(username,password,enabled) VALUES ('21333214R','A3224234232%adsa',TRUE);

INSERT INTO authorities(id,username,authority) VALUES (2,'21333214R','azafato');


INSERT INTO aeropuertos(id,nombre,localizacion,codigo_IATA,telefono)
VALUES (1,'Aeropuerto de São Paulo Guarulhos','São Paulo, Brasil' , 'GRU', '11 2445 2945');

INSERT INTO aeropuertos(id,nombre,localizacion,codigo_IATA,telefono)
VALUES (2,'Aeropuerto de Barajas','São Paulo, Sevilla' , 'MAD', '913 21 10 00');



INSERT INTO aviones(tipo_avion,capacidad_pasajero,peso_maximo_equipaje,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A320', 300, 2, 1700, '2015-09-24', true, '2017-09-24', 200, 80, 20);


INSERT INTO azafatos(nombre,apellidos,nif,iban,idiomas,salario,username)
VALUES ('Ana', 'Silfes Guerrero', '21333214R', 'ES 8721000893201234567892', 'Inglés, Francés, Español', 2500,'21333214R');

INSERT INTO clientes(nombre,apellidos,nif,direccion_facturacion,iban,fecha_nacimiento,username)
VALUES ('Juan Jesús', 'Ferrero Gutiérrez', '28976897W', 'Calle Carbón ,35 - 41007 Sevilla', 'ES 6621000418401234567893', '1997-06-03','28976897W');


INSERT INTO billetes(coste,asiento,fecha_reserva,clase)
VALUES (80,'F4','2020-04-06',0);



INSERT INTO vuelos(fecha_vuelo,hora_salida,hora_llegada,coste,aeropuerto_origen_id,aeropuerto_destino_id) 
VALUES ('2040-12-11','10:40','14:40',64,1,2);



