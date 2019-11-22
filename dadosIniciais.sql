/*
 * Dados iniciais a serem adicionados ao Banco de Dados quando este é recem criado
 * 
 * Após a criação do banco de dados e suas tabelas. Inserir os seguintes dados:
 */

INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (1, 'Ent. 30 60', '0:30:60');
INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (2, 'Ent. 30', '0:30');
INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (3, '30 60', '30:60');
INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (4, '30 60 90', '30:60:90');
INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (5, 'Ent. 30 60 90', '0:30:60:90');
INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (6, 'Ent. 30 60 90 120', '0:30:60:90:120');
INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (7, '30 60 90 120', '30:60:90:120');
INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (8, 'Ent. 30 60 90 120 150 180 210', '0:30:60:90:120:150:180:210');
INSERT INTO prazopagamento (id, descricao, listadiasstring) VALUES (9, '30 60 90 120 150 180 210', '30:60:90:120:150:180:210');


INSERT INTO instrumentorecebimento (id, descricao, instrumentorecebimentodisponivel) VALUES ((select nextval('gerador_instrumento_recebimento')), 'Cheque de Terceiros', 'CHEQUE_TERCEIROS');
INSERT INTO instrumentorecebimento (id, descricao, instrumentorecebimentodisponivel) VALUES ((select nextval('gerador_instrumento_recebimento')), 'Cheque do cliente', 'CHEQUE');
INSERT INTO instrumentorecebimento (id, descricao, instrumentorecebimentodisponivel) VALUES ((select nextval('gerador_instrumento_recebimento')), 'Boleto Bancário', 'BOLETO');
INSERT INTO instrumentorecebimento (id, descricao, instrumentorecebimentodisponivel) VALUES ((select nextval('gerador_instrumento_recebimento')), 'Depósito em Conta', 'DEPOSITO_EM_CONTA');
INSERT INTO instrumentorecebimento (id, descricao, instrumentorecebimentodisponivel) VALUES ((select nextval('gerador_instrumento_recebimento')), 'Dinheiro', 'DINHEIRO');
INSERT INTO instrumentorecebimento (id, descricao, instrumentorecebimentodisponivel) VALUES ((select nextval('gerador_instrumento_recebimento')), 'Crédito em Carteira', 'CREDITO_EM_CARTEIRA');
INSERT INTO instrumentorecebimento (id, descricao, instrumentorecebimentodisponivel) VALUES ((select nextval('gerador_instrumento_recebimento')), 'Cartão Credito/Debito', 'CARTAO_CREDITO');
INSERT INTO instrumentorecebimento (id, descricao, instrumentorecebimentodisponivel) VALUES ((select nextval('gerador_instrumento_recebimento')), 'Crédito', 'CREDITO');


/**
 * SQL DE ESQUEMA DE CORES PADRAO 
 * 
 */

INSERT INTO cor(id, codigo, descricao) VALUES (1,1,'Abobora');
INSERT INTO cor(id, codigo, descricao) VALUES (2,2,'Acafrao');
INSERT INTO cor(id, codigo, descricao) VALUES (3,3,'Amarelo');
INSERT INTO cor(id, codigo, descricao) VALUES (4,4,'Ambar');
INSERT INTO cor(id, codigo, descricao) VALUES (5,5,'Ameixa');
INSERT INTO cor(id, codigo, descricao) VALUES (6,6,'Amendoa');
INSERT INTO cor(id, codigo, descricao) VALUES (7,7,'Ametista');
INSERT INTO cor(id, codigo, descricao) VALUES (8,8,'Anil');
INSERT INTO cor(id, codigo, descricao) VALUES (9,9,'Azul');
INSERT INTO cor(id, codigo, descricao) VALUES (10,10,'Bege');
INSERT INTO cor(id, codigo, descricao) VALUES (11,11,'Bordo');
INSERT INTO cor(id, codigo, descricao) VALUES (12,12,'Branco');
INSERT INTO cor(id, codigo, descricao) VALUES (13,13,'Bronze');
INSERT INTO cor(id, codigo, descricao) VALUES (14,14,'Caqui');
INSERT INTO cor(id, codigo, descricao) VALUES (15,15,'Caramelo');
INSERT INTO cor(id, codigo, descricao) VALUES (16,16,'Carmesim');
INSERT INTO cor(id, codigo, descricao) VALUES (17,17,'Carmim');
INSERT INTO cor(id, codigo, descricao) VALUES (18,18,'Castanho');
INSERT INTO cor(id, codigo, descricao) VALUES (19,19,'Cereja');
INSERT INTO cor(id, codigo, descricao) VALUES (20,20,'Chocolate');
INSERT INTO cor(id, codigo, descricao) VALUES (21,21,'Ciano ');
INSERT INTO cor(id, codigo, descricao) VALUES (22,22,'Cinza');
INSERT INTO cor(id, codigo, descricao) VALUES (23,23,'Cinzento');
INSERT INTO cor(id, codigo, descricao) VALUES (24,24,'Cobre');
INSERT INTO cor(id, codigo, descricao) VALUES (25,25,'Coral');
INSERT INTO cor(id, codigo, descricao) VALUES (26,26,'Creme');
INSERT INTO cor(id, codigo, descricao) VALUES (27,27,'Damasco');
INSERT INTO cor(id, codigo, descricao) VALUES (28,28,'Dourado');
INSERT INTO cor(id, codigo, descricao) VALUES (29,29,'Escarlate');
INSERT INTO cor(id, codigo, descricao) VALUES (30,30,'Esmeralda');
INSERT INTO cor(id, codigo, descricao) VALUES (31,31,'Ferrugem');
INSERT INTO cor(id, codigo, descricao) VALUES (32,32,'Fucsia');
INSERT INTO cor(id, codigo, descricao) VALUES (33,33,'Gelo');
INSERT INTO cor(id, codigo, descricao) VALUES (34,34,'Grena');
INSERT INTO cor(id, codigo, descricao) VALUES (35,35,'Gris');
INSERT INTO cor(id, codigo, descricao) VALUES (36,36,'Indigo');
INSERT INTO cor(id, codigo, descricao) VALUES (37,37,'Jade');
INSERT INTO cor(id, codigo, descricao) VALUES (38,38,'Jambo');
INSERT INTO cor(id, codigo, descricao) VALUES (39,39,'Laranja');
INSERT INTO cor(id, codigo, descricao) VALUES (40,40,'Lavanda');
INSERT INTO cor(id, codigo, descricao) VALUES (41,41,'Lilas ');
INSERT INTO cor(id, codigo, descricao) VALUES (42,42,'Limao');
INSERT INTO cor(id, codigo, descricao) VALUES (43,43,'Loiro');
INSERT INTO cor(id, codigo, descricao) VALUES (44,44,'Magenta');
INSERT INTO cor(id, codigo, descricao) VALUES (45,45,'Malva');
INSERT INTO cor(id, codigo, descricao) VALUES (46,46,'Marfim');
INSERT INTO cor(id, codigo, descricao) VALUES (47,47,'Marrom');
INSERT INTO cor(id, codigo, descricao) VALUES (48,48,'Mostarda');
INSERT INTO cor(id, codigo, descricao) VALUES (49,49,'Negro');
INSERT INTO cor(id, codigo, descricao) VALUES (50,50,'Ocre');
INSERT INTO cor(id, codigo, descricao) VALUES (51,51,'Oliva');
INSERT INTO cor(id, codigo, descricao) VALUES (52,52,'Ouro');
INSERT INTO cor(id, codigo, descricao) VALUES (53,53,'Pessego');
INSERT INTO cor(id, codigo, descricao) VALUES (54,54,'Prata');
INSERT INTO cor(id, codigo, descricao) VALUES (55,55,'Preto');
INSERT INTO cor(id, codigo, descricao) VALUES (56,56,'Purpura');
INSERT INTO cor(id, codigo, descricao) VALUES (57,57,'Rosa');
INSERT INTO cor(id, codigo, descricao) VALUES (58,58,'Roxo');
INSERT INTO cor(id, codigo, descricao) VALUES (59,59,'Rubro');
INSERT INTO cor(id, codigo, descricao) VALUES (60,60,'Salmao');
INSERT INTO cor(id, codigo, descricao) VALUES (61,61,'Sepia');
INSERT INTO cor(id, codigo, descricao) VALUES (62,62,'Terracota');
INSERT INTO cor(id, codigo, descricao) VALUES (63,63,'Tijolo');
INSERT INTO cor(id, codigo, descricao) VALUES (64,64,'Turquesa');
INSERT INTO cor(id, codigo, descricao) VALUES (65,65,'Uva');
INSERT INTO cor(id, codigo, descricao) VALUES (66,66,'Verde');
INSERT INTO cor(id, codigo, descricao) VALUES (67,67,'Vermelho');
INSERT INTO cor(id, codigo, descricao) VALUES (68,68,'Vinho');
INSERT INTO cor(id, codigo, descricao) VALUES (69,69,'Violeta');




/*
 * Criar SQL para as Unidades de medidas abaixo
 * Unidade
 */
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'UNIDADE', 'UNID.');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'MILHEIRO', 'MIL');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'PACOTE', 'PAC');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'PECA', 'PC');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'KIT', 'KT');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'JOGO', 'JG');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'CONJUNTO', 'CJ');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'CAIXA', 'CX');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'CHAPA', 'CH');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'PAR', 'PAR');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'GRS', 'GROSA');  

	/**
	 * Peso(Massa)
	 */
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'QUILO', 'KG');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'TONELADA', 'T');  
	
	/**
    * Volume
    */
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'LITRO', 'L');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'MILILITRO', 'ML');
insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'METRO_CUBICO', 'M3');  

	/**
    * Comprimento
    */
   insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'METRO', 'M');
   
   /**
    * Área
    */
   insert into unidademedida (id, descricao, sigla) values ((select nextval('unidademedida_seq')), 'METRO_QUADRADO', 'M2');


   /*
    * Parametros Sistema
    */
   
   
   insert into ParametroSistema (chave, valor) values ('urlToth', 'http://a4t.in/toth');

   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'urlGestorNfe', 'http://localhost:8180/nfeserver',1);
   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'usuarioGestorNfe', '07080737000100',1);
   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'senhaGestorNfe', 'TI0Z53J4SSFXL5X3XIBY4VOQF4G53KOX3QBLIWAJWOEKPIFWPB',1);
   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'cfop-venda-interna','5101',1);
   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'cfop-venda-externa','6101',1);
   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'modBc','0',1);
   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'tipoAmbienteNfe','HOMOLOGACAO',1);
   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'tipoEmissao','7',1);
   insert into ParametroEmpresa (id, chave, valor, empresacliente_id) values ((select nextval('parametroempresa_seq')),'justicativaContigencia','INDISPONIBILIDADE DE RECEPCAO PELO AMBIENTE DE GOIAS',1);
   
   
   
   
   
   