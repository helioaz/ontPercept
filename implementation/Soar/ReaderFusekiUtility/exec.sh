#! /bin/bash

#
#  EXECUTA arquivo class Java fornecido como parametro na linha de comandos $1
#  
#  Tanto a compilação quanto a execução devem tratar com carinho dos pacotes associados ao seu executável.
#  Ao contrario do pensamento comum, a forma de identificar o caminho depende se voce tem archives (zip, jar).
#  Classes em unnamed or named package:
#       - For a JAR or zip file that contains class files, the class path ends with the name of 
#         the zip or JAR file. If there are more than one archive you can use wildcards
#       - For class files in an unnamed package, the class path ends with the directory 
#         that contains the class files.
#       - For class files in a named package, the class path ends with the directory that 
#         contains the root package, which is the first package in the full package name.
#         No nosso caso o inicio da cadeia de packages é: Dropbox/ToolTesterArea/Jena
#       source info: https://docs.oracle.com/javase/8/docs/technotes/tools/windows/classpath.html
#
# ATENÇÃO 1:
# O parametro $1 não deve contar a extensão .class
#
# ATENÇÃO 2:
# O pacote de logger usado pelo Jena é o log4j. Esse pacote necessita de uma configuração para executar.
# Esse configuração é representada por uma variavel de ambiente denominada  log4j.configuration 
# No nosso caso estamos setando essa variavel diretamente na linha de comando do compilador com a opção -D
#
# ATENÇÃO 3:
# Note que nosso class path possui 3 componentes
#    - default package path: ./
#    - our package path: /home/hazevedo/Dropbox/02_Tese_Doutorado/DesenvolvimentoTese/Implementacao/OntSenseJavaAPI/
#    - Jena package path: /home/hazevedo/localbin/apache-jena-3.2.0/lib/*
#
# ATENÇÃO 4:
# Para executar com debugger Java basta trocar java por jdb. O arquivo jdb.cmd, presente neste diretório, 
# contem alguns comandos que voce pode usar

#jdb -classpath "./:/home/hazevedo/Dropbox/02_Tese_Doutorado/DesenvolvimentoTese/Implementacao/OntSenseJavaAPI/:/home/hazevedo/localbin/apache-jena-3.2.0/lib/*" -Dlog4j.configuration=file:///home/hazevedo/localbin/apache-jena-3.2.0/jena-log4j.properties $1




java -classpath "./:/home/hazevedo/Dropbox/02_Tese_Doutorado/DesenvolvimentoTese/Implementacao/OntSenseJavaAPI/:/home/hazevedo/localbin/apache-jena-3.2.0/lib/*" -Dlog4j.configuration=jena-log4j-ontSense.properties $1


