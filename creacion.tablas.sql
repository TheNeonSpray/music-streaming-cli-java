-- Creación de la base de datos
CREATE DATABASE bd_music_app;
CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;


-- Activación de la base de datos
USE bd_music_app;

CREATE TABLE t_usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    correo_electronico VARCHAR(150) NOT NULL UNIQUE,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasenia VARCHAR(100) NOT NULL,
    tipo_usuario VARCHAR(20) NOT NULL
);

CREATE TABLE t_usuarios_finales (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL UNIQUE,
    nombre_completo VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    nacionalidad VARCHAR(80) NOT NULL,
    cedula VARCHAR(50) NOT NULL UNIQUE,
    avatar VARCHAR(255) DEFAULT 'img/Avatar-icon.png',
    saldo DOUBLE DEFAULT 4.99,
    FOREIGN KEY (id_usuario) REFERENCES t_usuarios(id) ON DELETE CASCADE
);

CREATE TABLE t_canciones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    artista VARCHAR(100) NOT NULL,
    compositor VARCHAR(100) NOT NULL,
    fecha_lanzamiento DATE NOT NULL,
    album VARCHAR(100),
    ruta_caratula VARCHAR(255) DEFAULT 'img/Record-icon.png',
    calificacion DOUBLE DEFAULT 0.0,
    precio DOUBLE NOT NULL,
    cantidad_compras INT DEFAULT 0,
    cantidad_inclusiones_en_listas INT DEFAULT 0,
    cantidad_calificaciones INT DEFAULT 0,
    suma_calificaciones DOUBLE DEFAULT 0.0
CONSTRAINT uk_cancion_nombre_artista UNIQUE (nombre, artista),
    CONSTRAINT chk_cancion_calificacion CHECK (calificacion BETWEEN 0.0 AND 5.0),
    CONSTRAINT chk_cancion_precio CHECK (precio >= 0.0),
    CONSTRAINT chk_cancion_compras CHECK (cantidad_compras >= 0),
    CONSTRAINT chk_cancion_inclusiones CHECK (cantidad_inclusiones_en_listas >= 0),
    CONSTRAINT chk_cancion_cantidad_calificaciones CHECK (cantidad_calificaciones >= 0),
    CONSTRAINT chk_cancion_suma_calificaciones CHECK (
        suma_calificaciones >= 0.0
            AND suma_calificaciones <= cantidad_calificaciones * 5.0
            AND (cantidad_calificaciones > 0 OR suma_calificaciones = 0.0)
        )
);

CREATE TABLE t_listas_reproduccion (
   id INT PRIMARY KEY AUTO_INCREMENT,
   id_usuario_final INT NOT NULL,
   nombre VARCHAR(100) NOT NULL,
   fecha_creacion DATE NOT NULL,
   calificacion DOUBLE DEFAULT 0.0,
   FOREIGN KEY (id_usuario_final) REFERENCES t_usuarios_finales(id) ON DELETE CASCADE
);

CREATE TABLE t_coleccion_usuario (
    id_usuario_final INT NOT NULL,
    id_cancion INT NOT NULL,
    fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_usuario_final, id_cancion),
    FOREIGN KEY (id_usuario_final) REFERENCES t_usuarios_finales(id) ON DELETE CASCADE,
    FOREIGN KEY (id_cancion) REFERENCES t_canciones(id) ON DELETE CASCADE
);

CREATE TABLE t_lista_cancion (
    id_lista INT NOT NULL,
    id_cancion INT NOT NULL,
    PRIMARY KEY (id_lista, id_cancion),
    FOREIGN KEY (id_lista) REFERENCES t_listas_reproduccion(id) ON DELETE CASCADE,
    FOREIGN KEY (id_cancion) REFERENCES t_canciones(id) ON DELETE CASCADE
);
