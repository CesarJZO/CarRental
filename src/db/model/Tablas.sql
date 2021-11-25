conn System/its;
create user adminRen identified by adminRen;
grant connect, resource to adminRen;
conn adminRen/adminRen;

/*-----------------------------------------------------------------------------*/

create table EstadosAuto(
    idEstadoAuto numeric(2) not null,
    descripcion varchar(30) not null,
    constraint pk_estAu primary key (idEstadoAuto)
);

create table Marcas(
    idMarca numeric(2) not null,
    nombreMarca varchar(30) not null,
    constraint pk_marca primary key (idMarca)
);

create table Modelos(
    idModelo numeric(2) not null,
    idMarca numeric(2) not null,
    nombreModelo varchar(30) not null,
    precioDia numeric(5) not null,
    precioGarantia numeric(5) not null,
    constraint pk_mode primary key (idModelo),
    constraint fk_marc foreign key (idMarca) references Marcas (idMarca)
);

create table Autos(
    placa varchar(10) not null,
    idModelo numeric(2) not null,
    idEstadoAuto numeric(2) not null,
    capacidad numeric(2) not null,
    disponible varchar(2) not null,
    constraint pk_plac primary key (placa),
    constraint fk_model foreign key (idModelo) references Modelos (idModelo),
    constraint fk_estadA foreign key (idEstadoAuto) references EstadosAuto (idEstadoAuto)
);

create table Estados(
    idEstado numeric(3) not null,
    nombreEstado varchar(45) not null,
    constraint pk_estd primary key (idEstado)
);

create table Ciudades(
    idEstado numeric(3) not null,
    idCiudad numeric(3) not null,
    nombreCiudad varchar(45) not null,
    constraint pk_ciud primary key (idEstado, idCiudad),
    constraint fk_direcc foreign key (idEstado) references Estados (idEstado)
);

create table Colonias(
    idColonia numeric(3) not null,
    idEstado numeric(3) not null,
    idCiudad numeric(3) not null,
    nombreColonia varchar(45) not null,
    constraint pk_colo primary key (idColonia, idCiudad, idEstado),
    constraint fk_direcb foreign key (idCiudad, idEstado) references Ciudades (idCiudad, idEstado)
);

create table Sucursales(
    idSucursal numeric(3) not null,
    idEstado numeric(3) not null,
    idCiudad numeric(3) not null,
    nombreSucursal varchar(45) not null,
    idColonia numeric(3) not null,
    calle varchar(45) not null,
    constraint pk_sucu primary key (idSucursal),
    constraint fk_direca foreign key (idColonia, idCiudad, idEstado) references Colonias (idColonia, idCiudad, idEstado)
);

create table LogAdministradores(
    usuario varchar(10) not null,
    contrasena varchar(10) not null,
    constraint pk_log primary key (usuario)
);

create table Empleados(
    idEmpleado numeric(10) not null,
    idSucursal numeric(3) not null,
    nombreEmp varchar(30) not null,
    paternoEmp varchar(30) not null,
    maternoEmp varchar(30) not null,
    telefonoEmp numeric(10) not null,
    correoEmp varchar(30) not null,
    constraint pk_emp primary key (idEmpleado),
    constraint fk_suc foreign key (idSucursal) references Sucursales (idSucursal)
);

create table Clientes(
    noLicencia numeric(11) not null,
    nombreCli varchar(30) not null,
    paternoCli varchar(30) not null,
    maternoCli varchar(30) not null,
    telefonoCli numeric(10) not null,
    correoCli varchar(30) not null,
    idColonia numeric(3) not null,
    idEstado numeric(3) not null,
    idCiudad numeric(3) not null,
    calle varchar(45) not null,
    noCasa varchar(10) not null,
    constraint pk_cliea primary key (noLicencia),
    constraint fk_dircli foreign key (idColonia, idCiudad, idEstado) references Colonias (idColonia, idCiudad, idEstado)
);

create table Rentas(
    idRenta numeric(3) not null,
    placa varchar(10) not null,
    idEmpleado numeric(10) not null,
    noLicencia numeric(11) not null,
    fechaPrestamo date,
    fechaDevolucion date,
    diasRenta numeric(2),
    montoTotal numeric(10),
    montoTotalGarantia numeric(10),
    constraint pk_ren primary key (idRenta),
    constraint fk_plaa foreign key (placa) references Autos (placa),
    constraint fk_empa foreign key (idEmpleado) references Empleados (idEmpleado),
    constraint fk_clia foreign key (noLicencia) references Clientes (noLicencia)
);
