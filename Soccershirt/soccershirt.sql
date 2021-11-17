drop database soccershirt;
create database soccershirt;
use soccershirt;
create table noticia(

	idUnica int AUTO_INCREMENT,

	titulo text,

	subtitulo text,

	texto text,

	dataC date,

	horaC time,

	primary key (idUnica)

);

create table imagens(

	idNoticia int,

	imagem varchar(700),

	primary key(idNoticia, imagem),

	foreign key(idNoticia) references noticia(idUnica)

);

create table time(

	idUnica int AUTO_INCREMENT,

	camiseta varchar(700),

	nome varchar(1000),

	silaba varchar(10),

	primary key(idUnica)

);

create table timeNoticia(

	idNoticia int,

	idTime int,

	primary key(idNoticia, idTime),

	foreign key(idNoticia) references noticia(idUnica),

	foreign key(idTime) references time(idUnica)

);

create table liga(

	idUnica int AUTO_INCREMENT,

	simbolo varchar(700),

	paisOrigem varchar(500),

	nome varchar(500),

	primary key(idUnica)

);

create table marca(

	idUnica int AUTO_INCREMENT,

	logo varchar(700),

	nome varchar(500),

	primary key(idUnica)

);

create table administrador(

	idUnica int AUTO_INCREMENT,

	username varchar(500),

	senha varchar(500),

	primary key (idUnica)

);

create table gerencia(

	idAdm int,

	idNoticia int,

	hora time,

	data date,

	primary key(idAdm, idNoticia),

	foreign key(idAdm) references administrador(idUnica),

	foreign key(idNoticia) references noticia(idUnica)

);

create table marcaNoticia(

	idNoticia int,

	idMarca int,

	primary key(idNoticia, idMarca),

	foreign key(idNoticia) references noticia(idUnica),

	foreign key(idMarca) references marca(idUnica)

);

create table ligaNoticia(

	idNoticia int,

	idLiga int,

	primary key(idNoticia, idLiga),

	foreign key(idNoticia) references noticia(idUnica),

	foreign key(idLiga) references liga(idUnica)

);
insert into administrador (username, senha) values('admin', 'admin');
