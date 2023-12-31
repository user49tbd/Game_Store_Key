--USE MASTER 
CREATE 
--DROP 
DATABASE DB02GS
GO
USE DB02GS
GO
CREATE TABLE CONTA (
NOMEUSR	VARCHAR(100) NOT NULL,
EMAIL	VARCHAR(50)	NOT NULL,
SENHA	VARCHAR(MAX)	 NOT NULL,
CSTATUS	VARCHAR(01) DEFAULT('V') NOT NULL,
TIPO CHAR(01) NOT NULL
CONSTRAINT TIPV CHECK(TIPO = 'P' OR TIPO = 'J')
PRIMARY KEY (NOMEUSR)
)
GO
CREATE TABLE EMAILR(
NOMEUSR	VARCHAR(100) NOT NULL,
EMAIL	VARCHAR(50)	NOT NULL
PRIMARY KEY (NOMEUSR,EMAIL)
)
GO
CREATE 
--DROP
TABLE JOGO (
NOMEUSR	VARCHAR(100) NOT NULL,
TITULO	VARCHAR(100) NOT NULL,
DESCRICAO VARCHAR(MAX)	NOT NULL,
ANO		INT			NOT NULL,
JSTATUS	VARCHAR(01) DEFAULT('V') NOT NULL,
CHECK (LEN(TITULO) > 1),
CHECK (ANO > 1957),
PRIMARY KEY(TITULO,ANO)
)
GO
CREATE 
--DROP
TABLE GENERO(
NOMEGEN	VARCHAR(100) NOT NULL,
DESCRICAO VARCHAR(MAX) NULL
PRIMARY KEY (NOMEGEN)
)
GO
CREATE 
--DROP
TABLE JOGOGEN(
TITULO	VARCHAR(100) NOT NULL,
ANO	   INT	NOT NULL,
NOMEGEN	VARCHAR(100) NOT NULL
PRIMARY KEY(TITULO,ANO,NOMEGEN)
FOREIGN KEY (TITULO,ANO) REFERENCES JOGO,
FOREIGN KEY (NOMEGEN) REFERENCES GENERO(NOMEGEN)
)
GO
CREATE 
--DROP
TABLE AVALIACAO(
USRNOME VARCHAR(100)	NOT NULL,
TITULO	VARCHAR(100)	NOT NULL,
ANO		INT				NOT NULL,
NOTA	DECIMAL(4,2)	NOT NULL,
DESCRICAO VARCHAR(MAX)	NOT NULL
PRIMARY KEY (USRNOME,TITULO,ANO)
FOREIGN KEY (TITULO,ANO) REFERENCES JOGO,
FOREIGN KEY (USRNOME) REFERENCES CONTA(NOMEUSR)
)
GO
CREATE 
--DROP
TABLE JKEY(
TITULO	VARCHAR(100)	NOT NULL,
ANO		INT				NOT NULL,
CHAVECOD VARCHAR(16) UNIQUE NOT NULL,
STATUS VARCHAR(01)	DEFAULT('V')
)
GO
CREATE 
--DROP
TABLE BIBLIOTECA(
USRNOME VARCHAR(100)	NOT NULL,
TITULO	VARCHAR(100)	NOT NULL,
ANO		INT				NOT NULL,
GEN    VARCHAR(MAX),
JKEY  VARCHAR(16)		NOT NULL
PRIMARY KEY (USRNOME,TITULO,ANO)
)
GO
CREATE TABLE WRD (
IDW  INT		IDENTITY(1,1) NOT NULL,
WORD VARCHAR(01) NOT NULL
PRIMARY KEY (IDW)
)
GO
INSERT INTO WRD(WORD)
VALUES
('A'),
('B'),
('C'),
('D'),
('E'),
('F'),
('G'),
('H'),
('I'),
('J'),
('K'),
('L'),
('M'),
('N'),
('O'),
('P'),
('Q'),
('R'),
('S'),
('T'),
('U'),
('V'),
('W'),
('X'),
('Y'),
('Z')
GO
CREATE 
--DROP
PROCEDURE RND @VALKEY VARCHAR(16) OUTPUT
AS
BEGIN
	DECLARE @RNDOP INT, @RNDV VARCHAR(01), @I INT
	SET @I = 0
	SET @VALKEY = ''
	WHILE(@I < 16)
		BEGIN
		SET @RNDOP = (ROUND(((RAND()*1)+1),0))
		IF(@RNDOP = 1)
		BEGIN
			SET @RNDV = (CAST(ROUND(RAND()*9,0) AS VARCHAR(02)))
		END
		ELSE
		BEGIN
			SET @RNDV = (SELECT W.WORD FROM WRD W WHERE W.IDW = (ROUND(RAND()*25+1,0)))
		END
		SET @VALKEY = CONCAT(@VALKEY,@RNDV)
		SET @I = @I + 1
		END
END
-----------------------------------------------------------------------------------------
GO
CREATE 
--DROP
PROCEDURE P_ISRCONTA(@NOMEUSR VARCHAR(100),@EMAIL VARCHAR(50),@SENHA	VARCHAR(MAX),@TIPO CHAR(01))
AS
BEGIN
	DECLARE @MSG VARCHAR(MAX),
			@MSGP VARCHAR(MAX),
			@VL VARCHAR(MAX)
	SET @MSG = 'CADASTRADO'
	--SET @MSGP = ''
	--EXEC P_PASSCHECK @SENHA,@MSGP OUTPUT
	BEGIN TRY
		IF(LEN(@NOMEUSR) = 0 OR LEN(@EMAIL) = 0)
		BEGIN
			SET @MSG = 'VALORES INCORRETOS'
		END
		ELSE
		BEGIN
			IF((SELECT EM.EMAIL FROM EMAILR EM WHERE EM.EMAIL = @EMAIL) IS NOT NULL)
			BEGIN
				SET @MSG = 'EMAIL JA CADASTRADO SELECIONE OUTRO EMAIL'
			END
			ELSE
			BEGIN
				INSERT INTO CONTA VALUES (@NOMEUSR,@EMAIL,@SENHA,DEFAULT,@TIPO)
				INSERT INTO EMAILR VALUES (@NOMEUSR,@EMAIL)
			END
		END
	END TRY
	BEGIN CATCH
		SET @MSG = 'ERRO AO CADASTRAR'
		IF((SELECT C.CSTATUS FROM CONTA C WHERE C.NOMEUSR = @NOMEUSR) = 'F')
		BEGIN
			SET @MSG = 'CONTA INDISPONIVEL'
		END
		IF((SELECT C.CSTATUS FROM CONTA C WHERE C.NOMEUSR = @NOMEUSR) = 'V')
		BEGIN
			SET @MSG = 'CONTA JA CADASTRADA ESCOLHA OUTRO NOME'
		END
	END CATCH
	SELECT @MSG AS MENSAGEM
END
GO
CREATE 
--DROP
PROCEDURE P_ISRCHAVE(@NOMEUSR VARCHAR(100),@TITULO	VARCHAR(100), @ANO INT,@QTD INT)
AS
BEGIN
	DECLARE @I INT,
			@VL VARCHAR(16),
			@MSG VARCHAR(MAX)
	SET @I = 0
	SET @MSG = 'QUANTIDADE INSERIDA: '+CAST(@QTD AS VARCHAR(50))
	IF((SELECT J.TITULO FROM JOGO J WHERE J.NOMEUSR = @NOMEUSR AND J.TITULO = @TITULO AND J.ANO = @ANO) IS NOT NULL)
	BEGIN
		BEGIN TRY
		WHILE (@I < @QTD)
			BEGIN
				EXEC RND @VL OUTPUT
				IF((SELECT J.CHAVECOD FROM JKEY J WHERE J.CHAVECOD = @VL) IS NULL)
				BEGIN
					INSERT INTO JKEY VALUES(@TITULO,@ANO,@VL,DEFAULT)
					SET @I = @I + 1
				END
			END
		END TRY
		BEGIN CATCH
			SET @MSG = 'ERRO'
		END CATCH
	END
	ELSE
	BEGIN
		SET @MSG = 'JOGO NAO ENCONTRADO'
	END
	SELECT @MSG AS MENSAGEM
END
GO
CREATE 
--DROP
PROCEDURE P_DELCHAVE(@NOMEUSR VARCHAR(100),@TITULO	VARCHAR(100), @ANO INT,@QTD INT)--,@MSG VARCHAR(50) OUTPUT)
AS
BEGIN
	DECLARE @I INT,
			@VL VARCHAR(16),
			@TOT INT,
			@QTDF INT,
			@DELKEY VARCHAR(50),
			@MSG VARCHAR(MAX)
				
	SET @I = 0
	SET @MSG = 'QUANTIDADE DELETADA: '
	--SELECT * FROM JKEY

	SET @TOT = (SELECT COUNT(J.CHAVECOD) FROM JKEY J WHERE J.STATUS = 'V' AND J.ANO = @ANO AND J.TITULO = @TITULO)

	SET @QTDF  = @TOT
	IF(@QTDF > @QTD)
	BEGIN
		SET @QTDF = @QTD
	END
	SET @MSG = CONCAT(@MSG,CAST(@QTDF AS VARCHAR(50)))
	IF((SELECT J.TITULO FROM JOGO J WHERE J.NOMEUSR = @NOMEUSR AND J.TITULO = @TITULO AND J.ANO = @ANO) IS NOT NULL)
	BEGIN
		BEGIN TRY
		WHILE (@I < @QTDF)
			BEGIN
				SET @DELKEY = (SELECT TOP 1 J.CHAVECOD FROM JKEY J WHERE J.STATUS = 'V' AND J.ANO = @ANO AND J.TITULO = @TITULO)
				UPDATE JKEY
				SET STATUS = 'F'
				WHERE JKEY.CHAVECOD = @DELKEY
				--DELETE FROM JKEY WHERE JKEY.CHAVECOD = @DELKEY
				SET @I = @I + 1
			END
		END TRY
		BEGIN CATCH
			SET @MSG = 'ERRO'
		END CATCH
	END
	ELSE
	BEGIN
		SET @MSG = 'JOGO NAO ENCONTRADO'
	END
	SELECT @MSG AS MENSAGEM
END
GO
INSERT INTO GENERO VALUES
('Acao','reflexos r�pidos, desafio, coordena��o e rea��o.'),
('Aventura','jogador assume o papel de um protagonista em uma hist�ria interativa com explora��o e resolu��o de quebra-cabe�as.'),
('Estrategia','jogos que enfatizam habilidades de pensamento e planejamento para alcan�ar a vit�ria.'),
('RPG','jogos que se assimilam aos RPGs de mesa, sua caracter�stica principal � o controle de um personagem que se desenvolve ao longo do jogo.'),
('Esporte','jogos que simulam a pr�tica de esportes individuais ou em equipe.'),
('Corrida','Jogos de corrida s�o aqueles em que o jogador entra em uma competi��o de corrida usando ve�culos de terra, �gua ou ar.'),
('Jogo on-line','jogos que, para serem jogados, precisam estar conectados em uma rede, local ou internet.'),
('Simulacao','jogos com o objetivo de simular um mundo real ou fict�cio.')
GO
CREATE 
--DROP
PROCEDURE OP_AVALIACAO @OP VARCHAR(01) ,@USRNOME VARCHAR(100),@TITULO	VARCHAR(100),
@ANO INT,@NOTA	DECIMAL(4,2),@DESCRICAO VARCHAR(MAX)--, @MSG VARCHAR(MAX) OUTPUT
AS
BEGIN
	DECLARE @MSG VARCHAR(MAX)
	SET @MSG='EXECUTADO COM SUCESSO'
	BEGIN TRY
		IF(@OP = 'I')
		BEGIN
			IF((SELECT J.JSTATUS FROM JOGO J WHERE J.ANO = @ANO AND J.TITULO = @TITULO) = 'F')
			BEGIN
				SET @MSG='JOGO NAO ESTA MAIS DISPONIVEL'
			END
			ELSE
			BEGIN
				INSERT INTO AVALIACAO VALUES (@USRNOME,@TITULO,@ANO,@NOTA,@DESCRICAO)
			END
		END
		IF((SELECT AV.USRNOME FROM AVALIACAO AV WHERE AV.USRNOME = @USRNOME AND AV.TITULO = @TITULO AND AV.ANO = @ANO) IS NOT NULL )
		BEGIN
			IF(@OP = 'D')
			BEGIN
				DELETE FROM AVALIACAO WHERE AVALIACAO.USRNOME = @USRNOME AND AVALIACAO.TITULO = @TITULO AND AVALIACAO.ANO = @ANO
			END
			IF(@OP = 'U')
			BEGIN
				UPDATE AVALIACAO
				SET AVALIACAO.NOTA = @NOTA, AVALIACAO.DESCRICAO=@DESCRICAO
				WHERE AVALIACAO.USRNOME = @USRNOME AND AVALIACAO.TITULO = @TITULO AND AVALIACAO.ANO = @ANO
			END
		END
		ELSE
		BEGIN
			SET @MSG='AVALIACAO DO JOGO NAO FOI FEITA'
			/*
			IF((SELECT J.JSTATUS FROM JOGO J WHERE J.ANO = @ANO AND J.TITULO = @TITULO) != 'F')
			BEGIN
				PRINT ('HERE-3BB')
				SET @MSG='AVALIACAO DO JOGO NAO FOI FEITA'
			END
			*/
		END
	END TRY
	BEGIN CATCH
		SET @MSG='ERRO AO REALIZAR OPERACAO'
	END CATCH
	--PRINT(@MSG)
	SELECT @MSG AS MENSAGEM
END
GO
CREATE 
--DROP
PROCEDURE ISR_JOGEN @JG VARCHAR(MAX),@TITULO VARCHAR(100),@ANO INT
AS
BEGIN
	DECLARE @I INT,
			@I2 INT,
			@IAP INT,
			@TOT INT,
			@AP VARCHAR(MAX),
			@OAP VARCHAR(MAX),
			@TOTGEN VARCHAR(MAX),
			@CAP VARCHAR(MAX),
			@GEN VARCHAR(50)
	SET @I = 1
	SET @I2 = 1
	SET @IAP = 1
	SET @TOT  = LEN(@JG) - LEN(REPLACE(@JG,'|',''))
	SET @OAP = @JG
	WHILE @I <= @TOT
	BEGIN
		SET @AP = SUBSTRING(@JG,@IAP,LEN(@JG))
		SET @CAP = SUBSTRING(@AP,@I2,1)
		IF(@CAP = '|')
		BEGIN
			SET @IAP = @IAP + @I2
			SET @GEN = SUBSTRING(@AP,1,@I2-1) 
			IF((SELECT JG.NOMEGEN FROM JOGOGEN JG WHERE JG.ANO=@ANO AND JG.TITULO = @TITULO AND JG.NOMEGEN =@GEN) IS NULL)
			BEGIN
				INSERT INTO JOGOGEN VALUES(@TITULO,@ANO,@GEN)
				SET @TOTGEN = CONCAT(@TOTGEN,@GEN+' ')
			END
			SET @I = @I + 1
			SET @I2 = 0
		END
		ELSE
		BEGIN
			SET @I2 = @I2 + 1
		END
	END
	UPDATE BIBLIOTECA
	SET GEN = @TOTGEN
	WHERE BIBLIOTECA.TITULO = @TITULO AND BIBLIOTECA.ANO = @ANO
END
GO
CREATE 
--DROP
PROCEDURE OP_JOGOM  @OP VARCHAR(01) ,@NOMEUSR	VARCHAR(100),@TITULO VARCHAR(100),@DESCRICAO VARCHAR(MAX),@ANO INT,@JG VARCHAR(MAX)
AS
BEGIN
	DECLARE @MSG VARCHAR(MAX)
	SET @MSG='EXECUTADO COM SUCESSO'
	BEGIN TRY
		IF(@OP = 'I')
		BEGIN
			IF((SELECT J.TITULO FROM JOGO J WHERE J.TITULO=@TITULO AND J.ANO = @ANO) IS NULL)
			BEGIN
				INSERT INTO JOGO VALUES(@NOMEUSR,@TITULO,@DESCRICAO,@ANO,DEFAULT)
				EXEC ISR_JOGEN @JG,@TITULO,@ANO
			END
			ELSE
			BEGIN
				SET @MSG='JOGO JA CADASTRADO NA PLATAFORMA INSIRA OUTRO NOME OU ANO'
			END
		END
		ELSE
		BEGIN
			IF((SELECT J.TITULO FROM JOGO J WHERE J.TITULO=@TITULO AND J.ANO = @ANO AND J.NOMEUSR = @NOMEUSR) IS NOT NULL )
			BEGIN
				IF(@OP = 'D')
				BEGIN
					DELETE FROM JOGOGEN WHERE JOGOGEN.ANO = @ANO AND JOGOGEN.TITULO = @TITULO
					--DELETE FROM JOGO WHERE JOGO.TITULO = @TITULO AND JOGO.ANO = @ANO
					UPDATE JOGO
					SET JOGO.JSTATUS = 'F'
					WHERE JOGO.TITULO = @TITULO AND JOGO.ANO = @ANO
					DELETE FROM AVALIACAO WHERE AVALIACAO.ANO = @ANO AND AVALIACAO.TITULO = @TITULO
					UPDATE JKEY
					SET JKEY.STATUS = 'F'
					WHERE JKEY.ANO = @ANO AND JKEY.TITULO = @TITULO
				END
				IF(@OP = 'U')
				BEGIN
					DELETE FROM JOGOGEN WHERE JOGOGEN.ANO = @ANO AND JOGOGEN.TITULO = @TITULO
					EXEC ISR_JOGEN @JG,@TITULO,@ANO
					UPDATE JOGO
					SET JOGO.DESCRICAO = @DESCRICAO
					WHERE JOGO.TITULO = @TITULO AND JOGO.ANO = @ANO AND JOGO.NOMEUSR = @NOMEUSR
				END
			END
			ELSE
			BEGIN
				SET @MSG='JOGO NAO CADASTRADO'
			END
		END
	END TRY
	BEGIN CATCH
		SET @MSG='ERRO AO REALIZAR OPERACAO TITULO NAO PODE SER NULO, ANO DEVE SER MAIOR QUE 1957'
		IF((SELECT J.JSTATUS FROM JOGO J WHERE J.ANO = @ANO AND J.TITULO = @TITULO) = 'F')
			BEGIN
				SET @MSG='JOGO NAO ESTA MAIS DISPONIVEL'
			END
	END CATCH
	SELECT @MSG AS MENSAGEM
END
GO
CREATE 
--DROP
PROCEDURE ISRBIB @USRNOME VARCHAR(100),@TITULO VARCHAR(100),@ANO INT--, @MSG VARCHAR(MAX) OUTPUT
AS
BEGIN
	DECLARE @I INT,
			@QTD INT,
			@GEN VARCHAR(MAX),
			@JKEY VARCHAR(16),
			@MSG VARCHAR(MAX)
	SET @I = 1
	SET @QTD = 0
	SET @GEN = ''
	IF((SELECT B.TITULO FROM BIBLIOTECA B WHERE B.USRNOME = @USRNOME AND B.TITULO = @TITULO AND B.ANO = @ANO) IS NULL)
	BEGIN
		BEGIN TRY
			SET @QTD = (SELECT COUNT(JG.NOMEGEN) FROM JOGOGEN JG WHERE JG.ANO = @ANO AND JG.TITULO = @TITULO)
			WHILE(@I <= @QTD)
			BEGIN
				SET @GEN = CONCAT(@GEN,(SELECT NMG FROM (SELECT ROW_NUMBER() OVER(ORDER BY  JG.NOMEGEN) AS RN, JG.NOMEGEN AS NMG,JG.ANO,JG.TITULO 
			FROM JOGOGEN JG WHERE JG.TITULO = @TITULO AND JG.ANO = @ANO) AS VL WHERE RN= @I))+' '
				SET @I = @I + 1
			END
			SET @JKEY = (SELECT TOP 1 J.CHAVECOD FROM JKEY J WHERE J.ANO = @ANO AND J.TITULO = @TITULO AND J.STATUS = 'V')
			IF(@JKEY IS NOT NULL OR @JKEY != '')
			BEGIN
				INSERT INTO BIBLIOTECA VALUES (@USRNOME,@TITULO,@ANO,@GEN,@JKEY)
				SET @MSG = 'CHAVE ADQUIRIDA'
				UPDATE JKEY
				SET STATUS = 'F'
				WHERE JKEY.CHAVECOD = @JKEY
			END
			ELSE
			BEGIN
				SET @MSG = 'NAO HA CHAVES DISPONIVEIS'
			END
		END TRY
		BEGIN CATCH
			SET @MSG= 'ERRO AO ADQUIRIR CHAVE DO JOGO'
		END CATCH
	END
	ELSE
	BEGIN
		SET @MSG= 'CHAVE DO JOGO JA ADQUIRIDA'
	END
	SELECT @MSG AS MENSAGEM
END
GO
CREATE 
--DROP
PROCEDURE OP_UPDELCONTA @OP VARCHAR(01), @NOMEUSR VARCHAR(100),@EMAIL VARCHAR(50),@SENHA	VARCHAR(MAX),@TIPO CHAR(01)
AS
BEGIN
	DECLARE @ANO INT,
			@TITULO VARCHAR(100),
			@QTDJ INT,
			@I INT,
			@MSG VARCHAR(MAX)
			--@MSGP VARCHAR(MAX)
	--EXEC P_PASSCHECK @SENHA,@MSGP OUTPUT
	SET @MSG = 'EXECUTADO COM SUCESSO'
	SET @I = 1
	BEGIN TRY
	IF(@OP LIKE 'A')
	BEGIN
		IF(LEN(@EMAIL) = 0 OR LEN(@SENHA) = 0)
		BEGIN
			SET @MSG = 'VALORES INCORRETOS'
		END
		ELSE
		BEGIN
			--UPDATE CONTA
			--SET CONTA.EMAIL = @EMAIL, CONTA.SENHA = @SENHA
			--WHERE CONTA.NOMEUSR = @NOMEUSR AND CONTA.TIPO = @TIPO
			IF((SELECT C.NOMEUSR FROM CONTA C WHERE C.NOMEUSR = @NOMEUSR AND C.TIPO = @TIPO) IS NOT NULL)
			BEGIN
				IF((SELECT E.EMAIL FROM EMAILR E WHERE E.EMAIL = @EMAIL AND E.NOMEUSR = @NOMEUSR) IS NULL)
					BEGIN
						IF((SELECT E.EMAIL FROM EMAILR E WHERE E.EMAIL = @EMAIL) IS NULL)
						BEGIN
							UPDATE CONTA
							SET CONTA.EMAIL = @EMAIL, CONTA.SENHA = @SENHA
							WHERE CONTA.NOMEUSR = @NOMEUSR AND CONTA.TIPO = @TIPO
							INSERT INTO EMAILR VALUES (@NOMEUSR,@EMAIL)
						END
						ELSE
						BEGIN
							SET @MSG = 'EMAIL JA CADASTRADO SELECIONE OUTRO EMAIL'
						END
					END
					ELSE
					BEGIN
						UPDATE CONTA
						SET CONTA.EMAIL = @EMAIL, CONTA.SENHA = @SENHA
						WHERE CONTA.NOMEUSR = @NOMEUSR AND CONTA.TIPO = @TIPO
					END
			END

		END
	END
	IF(@OP LIKE 'D')
	BEGIN
		UPDATE CONTA
		SET CONTA.CSTATUS = 'F'
		WHERE CONTA.NOMEUSR = @NOMEUSR
		IF(@TIPO = 'J')
		BEGIN
			DELETE FROM BIBLIOTECA WHERE BIBLIOTECA.USRNOME = @NOMEUSR
			DELETE FROM AVALIACAO WHERE AVALIACAO.USRNOME = @NOMEUSR
		END
		IF(@TIPO = 'P')
		BEGIN
			SET @QTDJ = (SELECT COUNT(*) FROM JOGO J WHERE J.NOMEUSR = @NOMEUSR)
			WHILE(@I < = @QTDJ)
			BEGIN
				SET @ANO =(SELECT A FROM (SELECT ROW_NUMBER() OVER(ORDER BY J.TITULO,J.ANO) AS RN, J.ANO AS A,J.TITULO AS T
				FROM JOGO J WHERE J.NOMEUSR = @NOMEUSR) AS VL WHERE RN = @I)
				SET @TITULO =(SELECT T FROM (SELECT ROW_NUMBER() OVER(ORDER BY J.TITULO,J.ANO) AS RN, J.ANO AS A,J.TITULO AS T
				FROM JOGO J WHERE J.NOMEUSR = @NOMEUSR) AS VL WHERE RN = @I)
				EXEC OP_JOGOM 'D', @NOMEUSR,@TITULO,'',@ANO,''
				SET @I = @I + 1
			END
		END
	END
	END TRY
	BEGIN CATCH
		SET @MSG = 'ERRO'
	END CATCH
	SELECT @MSG AS MENSAGEM
END
GO
CREATE
--DROP 
PROCEDURE CALCMED @TITULO VARCHAR(MAX),@ANO INT
AS
BEGIN
	IF((SELECT AVG(AV.NOTA) FROM AVALIACAO AV WHERE AV.TITULO = @TITULO AND AV.ANO = @ANO) IS NULL)
	BEGIN
		SELECT 0 AS MED
	END
	ELSE
	BEGIN
		SELECT AVG(AV.NOTA) AS MED FROM AVALIACAO AV WHERE AV.TITULO = @TITULO AND AV.ANO = @ANO
	END
END





--LOGIN--------------------------------------------------------------------------------------------------------------------------------------------------
--P_ISRCONTA(@NOMEUSR VARCHAR(100),@EMAIL VARCHAR(50),@SENHA	VARCHAR(30),@TIPO CHAR(01), @MSG VARCHAR(50) OUTPUT)
--P
EXEC P_ISRCONTA 'U1','U1@GMAIL','U1','P'

--ATUALIZAR
EXEC OP_UPDELCONTA 'A', 'A1','U1@GMAILLL','agoraVAI','J'
--DELETAR
EXEC OP_UPDELCONTA 'D', 'U1','U1@GMAIL','U1','P'

--J
EXEC P_ISRCONTA 'UJ1','U1J@GMAIL','UJ1','J'
--ATUALIZAR
EXEC OP_UPDELCONTA 'A', 'UJ1','U1@GMAIL','U1','J'
--DELETAR
EXEC OP_UPDELCONTA 'D', 'UJ1','U1@GMAIL','U1','J'

SELECT * FROM CONTA
--MANTER JOGO--------------------------------------------------------------------------------------------------------------------------------------------
--EXEC OP_JOGOM  @OP VARCHAR(01) ,@NOMEUSR	VARCHAR(100),@TITULO VARCHAR(100),
--@DESCRICAO VARCHAR(MAX),@ANO INT,@JG VARCHAR(MAX), @MSG VARCHAR(MAX) OUTPUT

--INSERIR JOGO
EXEC OP_JOGOM 'I','U1','J4','DESCRICAO-J3',2002,'ACAO|AVENTURA|ESTRATEGIA|CORRIDA|'
--ATUALIZAR JOGO
EXEC OP_JOGOM 'U','U1','J1','DESCRICAO-J1',2000,'ACAO|AVENTURA|ESTRATEGIA|CORRIDA|'
--DELETAR JOGO
EXEC OP_JOGOM 'D','U1','J2','DESCRICAO-J1',2000,'ACAO|AVENTURA|ESTRATEGIA|CORRIDA|'

SELECT * FROM JOGO
SELECT * FROM JOGOGEN
--DELETAR
--MANTER CHAVES------------------------------------------------------------------------------------------------------------------------------------------
--INSERIR QUANTIDADE DE CHAVES
EXEC P_ISRCHAVE 'U1','J1',2000,2
GO
--DELETAR QUANTIDADE DE CHAVES
EXEC P_DELCHAVE 'U1','J1',2000,100

SELECT * FROM JKEY

SELECT * FROM JOGO

--MANTER AVALIACAO---------------------------------------------------------------------------------------------------------------------------------------
--INSERIR
EXEC OP_AVALIACAO 'I' ,'UJ1','J1',2000,10,'TEXTO-USR'
--ATUALIZAR
EXEC OP_AVALIACAO 'U' ,'UJ1','J1',2000,9,'TEXTO-USR'
--DELETAR
EXEC OP_AVALIACAO 'D' ,'UJ1','J1',2000,9,'TEXTO-USR'

SELECT * FROM AVALIACAO

--BIBLIOTECA---------------------------------------------------------------------------------------------------------------------------------------------
--RESGATAR
EXEC ISRBIB 'UJ1','J2',2000

SELECT * FROM BIBLIOTECA

SELECT * FROM JOGO

--EXEC CALCMED 'J1',2001
-----------------------------
SELECT * FROM CONTA
SELECT * FROM JKEY
SELECT * FROM JOGO
SELECT * FROM JOGOGEN
SELECT * FROM BIBLIOTECA
SELECT * FROM AVALIACAO
SELECT * FROM EMAILR
--EXEC CALCMED 'doom',2001
--2A4A3EA4095C1F5453B5463C3A746C99
--0CA65DB59665C1F211CEA5BD4673821B


SELECT J.TITULO+' | '+ CAST(J.ANO AS VARCHAR(MAX)) AS JOGO FROM JOGO J WHERE J.NOMEUSR = 'usr01P' AND J.JSTATUS = 'V'