USE db_frota
GO

INSERT INTO tb_caminhoes (modelo, marca_id, placa, carga_maxima, ano, comprimento, largura, altura) VALUES
('Iveco Stralis 480', 1, 'HEJ-0951', 47620, 2011, 12.0, 2.5, 3.8),
('Volvo FMX 500', 2, 'NUA-8295', 46734, 2010, 11.5, 2.5, 3.7),
('Mercedes-Benz Actros 2651', 3, 'OAJ-0066', 38250, 2011, 12.0, 2.5, 3.9),
('Mercedes-Benz Arocs 3363', 3, 'VFR-6380', 40288, 2022, 11.8, 2.6, 3.8),
('Iveco Stralis 480', 1, 'FEU-0689', 46801, 2015, 11.7, 2.5, 3.7),
('Mercedes-Benz Arocs 3363', 3, 'FHK-1477', 44400, 2022, 12.0, 2.6, 3.9),
('Mercedes-Benz Arocs 3363', 3, 'RIP-4172', 38201, 2011, 11.6, 2.5, 3.6),
('Volvo FMX 500', 2, 'AML-1594', 44070, 2021, 11.9, 2.5, 3.8),
('Volvo FMX 500', 2, 'OEF-7958', 39090, 2023, 12.0, 2.6, 3.9),
('DAF CF 85', 4, 'MMK-7818', 41376, 2014, 11.4, 2.5, 3.7),
('DAF XF 105', 4, 'OBV-0785', 45309, 2023, 12.0, 2.6, 3.9),
('Iveco Hi-Way 490', 1, 'KTA-2314', 40633, 2013, 11.8, 2.5, 3.8);
GO