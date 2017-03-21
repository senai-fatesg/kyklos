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
   
   
   
   
   
   