
CREATE TABLE IF NOT EXISTS TB_EQUIPO (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    city VARCHAR(250) NOT NULL,
    created_at DATE,
    updated_at DATE
);

CREATE TABLE IF NOT EXISTS TB_JUGADOR (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    goals INT,
    equipo_id INT NOT NULL,
    created_at DATE,
    updated_at DATE,
    foreign key (equipo_id) references TB_EQUIPO(ID)
);


