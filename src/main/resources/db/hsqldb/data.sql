INSERT INTO users(username,password,enabled) VALUES ('28976897W','*Fly_High14&',TRUE);

INSERT INTO authorities(id,username,authority) VALUES (1,'28976897W','cliente');

INSERT INTO users(username,password,enabled) VALUES ('21333214R','A3224*234232%adsa',TRUE);

INSERT INTO authorities(id,username,authority) VALUES (2,'21333214R','azafato');

INSERT INTO aeropuertos VALUES (1,'Aeropuerto de São Paulo Guarulhos','São Paulo, Brasil' , 'GRU', '+55 11 2445 2945');

INSERT INTO aviones(tipo_avion,capacidad_pasajero,peso_maximo_equipaje,horas_acumuladas,fecha_fabricacion,disponibilidad,fecha_revision,plazas_economica,plazas_ejecutiva,plazas_primera)
VALUES ('Airbus A320', 300, 2, 1700, '2015-09-24', true, '2017-09-24', 200, 80, 20);

INSERT INTO azafatos
VALUES ('Ana', 'Silfes Guerrero', '21333214R', 'ES 8721000893201234567892', 'Inglés, Francés, Español', 2500,'21333214R');

INSERT INTO billetes
VALUES (1,80,'F4','2020/04/06',Clase.ECONOMICA);

INSERT INTO clientes
VALUES (1,'Juan Jesús', 'Ferrero Gutiérrez', '28976897W', 'Calle Carbón', '35 - 41007 Sevilla', 'ES 6621000418401234567893', 03/06/1997,'28976897W');


INSERT INTO vuelos(id,fecha_vuelo,hora_salida,hora_llegada,coste) 
VALUES (1,'2040-12-11','10:40','14:40',64);



