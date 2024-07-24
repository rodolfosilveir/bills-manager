CREATE TABLE conta (
    id SERIAL PRIMARY KEY,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    descricao TEXT,
    situacao VARCHAR(20) NOT NULL
);