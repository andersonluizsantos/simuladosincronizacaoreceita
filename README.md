# 1.	O Projeto
Esta projeto vai fazer a simulação do processo automático de arquivos CSV usando um serviço fake da receita federal.<br> 

# 2.	Requisitos
Dentre as funcionalidades, temos: 
  a. Criação uma aplicação SprintBoot standalone. Que possibilita a execução por linha de comando seguindo o seguinte comando: java -jar SincronizacaoReceita <input-file><br>
  b. Processa um arquivo CSV de entrada com o formato abaixo.<br>
      Formato CSV:<br>
          agencia;conta;saldo;status<br>
          0101;12225-6;100,00;A<br>
          0101;12226-8;3200,50;A<br>
          3202;40011-1;-35,12;I<br>
          3202;54001-2;0,00;P<br>
          3202;00321-2;34500,00;B<br>
  c. Envia a atualização para a Receita através do serviço fake (SIMULADO pela classe ReceitaService).<br>
  d. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma nova coluna.<br>

# 3.  Solução
O projeto foi construido utilizando Spring Boot Standalone, simulando a chamada do serviço da receita federal

# 4.	Instruções para executar o projeto<br>
  a.	Instalar um cliente git na maquina<br>
  b.	Baixar o projeto do repositório remoto - https://github.com/andersonluizsantos/simuladosincronizacaoreceita<br>
  c.	Importar a Aplicação no STS ou Eclipse;<br>
  d.	Utilizar java8 para build<br>
  e.	Clicar com botão direito sobre o projeto > Run Configurations... e seguir com as configurações abaixo<br>
  ![Configuração do SpringBootConfigurationArguments](https://github.com/andersonluizsantos/simuladosincronizacaoreceita/blob/master/imagens/SpringBootConfigurationArguments.png)<br>
  f. Será gerado um arquivo, com o mesmo nome do arquivo de entrada acrescentado do sufixo "-saida.csv". Esse arquivo, terá uma nova coluna, informando se o registro foi processado ou não
  ![Configuração do SpringBootConfigurationArguments](https://github.com/andersonluizsantos/simuladosincronizacaoreceita/blob/master/imagens/ArquivoSaida.png)
  

# 5.	Instruções para executar o jar do projeto
  a. Para gerar o jar do projeto será necessário executar o comando <b>"mvn clean package"</b> na pasta do projeto.<br>
  ![Comando para gerar o JAR do Projeto](https://github.com/andersonluizsantos/simuladosincronizacaoreceita/blob/master/imagens/ComandoArquivoJAR.png)
  
  b. Copiar o arquivo JAR para a mesma pasta do arquivo CSV. que será processado.<br>
  ![Pasta com os arquivos JAR e CSV](https://github.com/andersonluizsantos/simuladosincronizacaoreceita/blob/master/imagens/PastaJAReCSV.png)
  
  c. executar o comando <b><i>"java - jar <NOME_ARQUIVO_JAR> "<NOME_ARQUIVO_CVS>"</i></b>, comforme exemplo abaixo
  ![Executando o JAR para gerar o arquivo de saída](https://github.com/andersonluizsantos/simuladosincronizacaoreceita/blob/master/imagens/ExecutandoJAR.png)
  
  d. Será será gerado um arquivo de saída com a coluna de status de processamento
  ![Arquivo gerado com status de processamento](https://github.com/andersonluizsantos/simuladosincronizacaoreceita/blob/master/imagens/ArquivoSaidaGerado.png)
  
  

