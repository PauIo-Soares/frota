USE db_frota
GO

INSERT INTO tb_marcas (nome, pais) VALUES
('Volvo', 'Suécia'),
('Scania', 'Suécia'),
('Mercedes-Benz', 'Alemanha'),
('DAF', 'Holanda'),
('Iveco', 'Itália'),
('MAN', 'Alemanha'),
('Volkswagen Truck', 'Brasil');
GO
INSERT INTO tb_clientes (nome, email, telefone, cpf_cnpj, endereco, cep) VALUES
('João Silva', 'joao@email.com', '11999990000', '12345678901', 'Rua A, 100', '01000-000'),
('Maria Souza', 'maria@email.com', '11999880000', '98765432100', 'Rua B, 200', '02000-000'),
('Carlos Lima', 'carlos@email.com', '11999770000', '11122233344', 'Rua C, 300', '03000-000'),
('Ana Costa', 'ana@email.com', '11999660000', '55566677788', 'Rua D, 400', '04000-000'),
('Ricardo Gomes', 'ricardo@email.com', '11999550000', '99988877766', 'Rua E, 500', '05000-000'),
('Fernanda Alves', 'fernanda@email.com', '11999440000', '22233344455', 'Rua F, 600', '06000-000'),
('Pedro Santos', 'pedro@email.com', '11999330000', '66677788899', 'Rua G, 700', '07000-000');
GO
INSERT INTO tb_caixas (comprimento, largura, altura, limite_peso) VALUES
(1.0, 1.0, 1.0, 30.0),
(1.2, 1.0, 1.0, 40.0),
(1.5, 1.2, 1.0, 60.0),
(2.0, 1.2, 1.5, 100.0),
(0.8, 0.8, 0.8, 20.0),
(1.8, 1.5, 1.2, 90.0),
(2.2, 1.5, 1.5, 120.0);
GO
INSERT INTO tb_solicitacoes (peso, comprimento, largura, altura, cep_origem, cep_destino, caixa_id) VALUES
(20.0, 1.0, 1.0, 1.0, '01000-000', '02000-000', 1),
(35.0, 1.2, 1.0, 1.0, '02000-000', '03000-000', 2),
(50.0, 1.5, 1.2, 1.0, '03000-000', '04000-000', 3),
(80.0, 2.0, 1.2, 1.5, '04000-000', '05000-000', 4),
(15.0, 0.8, 0.8, 0.8, '05000-000', '06000-000', 5),
(70.0, 1.8, 1.5, 1.2, '06000-000', '07000-000', 6),
(90.0, 2.2, 1.5, 1.5, '07000-000', '01000-000', 7);
GO
INSERT INTO tb_orcamentos (valor_frete, solicitacao_id) VALUES
(150.0, 1),
(180.0, 2),
(210.0, 3),
(350.0, 4),
(120.0, 5),
(300.0, 6),
(400.0, 7);
GO
INSERT INTO tb_caminhoes (modelo, marca_id, placa, carga_maxima, ano, comprimento, largura, altura) VALUES
('FH 540', 1, 'AAA1A11', 30000, 2020, 7.0, 2.5, 3.0),
('R450', 2, 'BBB2B22', 28000, 2021, 6.8, 2.6, 3.0),
('Actros 2651', 3, 'CCC3C33', 32000, 2019, 7.2, 2.5, 3.1),
('XF105', 4, 'DDD4D44', 29000, 2022, 7.0, 2.5, 3.0),
('Stralis 570', 5, 'EEE5E55', 31000, 2020, 7.1, 2.6, 3.1),
('TGX 29.510', 6, 'FFF6F66', 33000, 2021, 7.3, 2.5, 3.2),
('Constellation 30.330', 7, 'GGG7G77', 27000, 2018, 6.9, 2.5, 3.0);
GO
INSERT INTO tb_caminhao_km (caminhao_id, km_atual, km_ultima_manutencao, km_ultima_troca_pneus) VALUES
(1, 10500, 500, 2000),
(2, 20000, 11000, 3000),
(3, 35000, 25000, 5000),
(4, 5000, NULL, NULL),
(5, 75000, 65000, 10000),
(6, 90000, 80000, 15000),
(7, 15000, 5000, NULL);
GO
INSERT INTO tb_entregas
(solicitacao_id, caminhao_id, cliente_id, status,
 data_coleta, data_processamento, data_em_transito, data_entregue,
 horario_retirada_solicitado, feedback_cliente, nota_recebedor, comentario_recebedor)
VALUES
(1, 1, 1, 'COLETA', GETDATE(), NULL, NULL, NULL, DATEADD(HOUR, 2, GETDATE()), NULL, NULL, NULL),
(2, 2, 2, 'EM_PROCESSAMENTO', GETDATE(), GETDATE(), NULL, NULL, DATEADD(HOUR, 3, GETDATE()), NULL, NULL, NULL),
(3, 3, 3, 'A_CAMINHO_DA_ENTREGA', GETDATE(), GETDATE(), GETDATE(), NULL, DATEADD(HOUR, 1, GETDATE()), NULL, NULL, NULL),
(4, 4, 4, 'ENTREGUE', GETDATE(), GETDATE(), GETDATE(), GETDATE(), DATEADD(HOUR, 4, GETDATE()), 'Ótimo serviço!', 5, 'Muito bom atendimento'),
(5, 5, 5, 'COLETA', GETDATE(), NULL, NULL, NULL, DATEADD(HOUR, 2, GETDATE()), NULL, NULL, NULL),
(6, 6, 6, 'A_CAMINHO_DA_ENTREGA', GETDATE(), GETDATE(), GETDATE(), NULL, DATEADD(HOUR, 5, GETDATE()), NULL, NULL, NULL),
(7, 7, 7, 'EM_PROCESSAMENTO', GETDATE(), GETDATE(), NULL, NULL, DATEADD(HOUR, 2, GETDATE()), NULL, NULL, NULL);
GO
INSERT INTO tb_percursos
(caminhao_id, entrega_id, km_saida, km_chegada, combustivel_gasto, data_saida, data_chegada)
VALUES
(1, 1, 1000, 1200, 50, GETDATE(), DATEADD(HOUR, 5, GETDATE())),
(2, 2, 2000, 2200, 60, GETDATE(), DATEADD(HOUR, 6, GETDATE())),
(3, 3, 3000, 3300, 70, GETDATE(), DATEADD(HOUR, 4, GETDATE())),
(4, 4, 4000, 4500, 80, GETDATE(), DATEADD(HOUR, 8, GETDATE())),
(5, 5, 5000, 5300, 55, GETDATE(), DATEADD(HOUR, 5, GETDATE())),
(6, 6, 6000, 6500, 90, GETDATE(), DATEADD(HOUR, 9, GETDATE())),
(7, 7, 7000, 7300, 65, GETDATE(), DATEADD(HOUR, 4, GETDATE()));
GO
INSERT INTO tb_manutencoes
(caminhao_id, tipo, km_realizada, data_manutencao, custo, observacoes) VALUES
(1, 'PREVENTIVA_10K', 10500, GETDATE(), 800.00, 'Troca de óleo e filtros'),
(2, 'TROCA_PNEUS', 20000, GETDATE(), 3500.00, 'Troca de todos os pneus'),
(3, 'CORRETIVA', 35000, GETDATE(), 1500.00, 'Reparo no sistema de freio'),
(4, 'PREVENTIVA_10K', 5000, GETDATE(), 700.00, 'Manutenção inicial'),
(5, 'TROCA_PNEUS', 75000, GETDATE(), 4000.00, 'Pneus gastos'),
(6, 'CORRETIVA', 90000, GETDATE(), 2500.00, 'Problema na suspensão'),
(7, 'PREVENTIVA_10K', 15000, GETDATE(), 900.00, 'Revisão completa');
GO