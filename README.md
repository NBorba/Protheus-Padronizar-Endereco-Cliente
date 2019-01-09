# Protheus - Padronização de Endereco do Cliente

Este código foi criado com o intuito de automatizar a padronização do endereço de clientes no Protheus. 
Por ser um código que foi desenvolvido em um curto período de tempo, pode conter alguns erros, e pelo curto espaço de tempo foi desenvolvido em cima de servlets, para rodar esta servlet recomendo utilizar um servidor TomCat. 

Para o seu funcionamento correto, uma tabela temporária deve ser criada no banco de dados e conter todos os clientes que se busca atualizar.

**A tabela deve ter o seguinte nome "TEMP_REVISAOENDERECO" e deve conter os seguintes campos:** 
* Código do cliente - **A1_COD** _varchar(6)_
* Loja do cliente - **A1_LOJA** _varchar(4)_
*	CEP do cliente - **A1_CEP** _varchar(8)_ 
*	Endereço do cliente - **A1_END** _varchar(40)_
*	Endereço encontrado pelo programa sem abreviações - **ENDERECO_NAOABREVIADO** _varchar(400)_
*	Endereço encontrado já abreviado pelo programa - **NOVO_ENDERECO** _varchar(200)_
*	Recno do cliente - **R_E_C_N_O_** _int_

Todos os endereços encontrados pelo programa serão atualizados no cliente na tabela temporária e após validações podem ser migrados para o banco de dados do Protheus. Caso o programa não encontre um endereço com o CEP, será retornado "VAZIO" nos campos de endereço

Após configurado o servidor, o projeto deve ser rodado nele no seguinte link: **"servidor:porta/PadronizacaoEnderecoProtheus/PadronizacaoServlet"**

**OBS: o arquivo "hibernate.cfg.xml" que faz o mapeamento da classe "Temp" para o Hibernate e configura os dados de acesso ao banco não se encontra presente no projeto, pois cada banco necessita de um arquivo específico.**
