#! /bin/bash

#
#  compila arquivo Java fornecido como parametro na linha de comandos $1
#  
#  Tanto a compilação quanto a execução devem tratar com carinho dos pacotes associados ao seu executável.
#  Ao contrario do pensamento comum, a forma de identificar o caminho depende se voce tem archives (zip, jar),
#  classes em unnamed or named package:
#       - For a JAR or zip file that contains class files, the class path ends with the name of 
#         the zip or JAR file. If there are more than one archive you can use wildcards
#       - For class files in an unnamed package, the class path ends with the directory 
#         that contains the class files.
#       - For class files in a named package, the class path ends with the directory that 
#         contains the root package, which is the first package in the full package name.
#       source info: https://docs.oracle.com/javase/8/docs/technotes/tools/windows/classpath.html
#
#
#
#  Observe que o parametro "-d ./" seta o diretório para armazenar os arquivos .class como o diretório corrente, contudo
#  o compilador java expande esse diretório para atender ao package definido no arquivo java e cria o path de forma apropriada.
#  No nosso caso temos um unico package, a saber "br.usp.ontSenseJavaAPI", assim esse é path expandido.
#  Note que, caso o arquivo não tenha a diretiva package o .class será gerado no diretório corrente!!! No nosso caso
#  o arquivo Tutorial0x.java
#
#  




#javac -d ./ -classpath "/home/hazevedo/Dropbox/02_Tese_Doutorado/DesenvolvimentoTese/Implementacao/*:/home/hazevedo/localbin/apache-jena-3.2.0/lib/*" *.java

#javac -d ./ -classpath "./:/home/hazevedo/Dropbox/02_Tese_Doutorado/DesenvolvimentoTese/Implementacao/OntSenseJavaAPI/*:/home/hazevedo/localbin/apache-jena-3.2.0/lib/*"  *.java

#javac -d ./ -classpath "/home/hazevedo/Dropbox/02_Tese_Doutorado/DesenvolvimentoTese/Implementacao/OntSenseJavaAPI/*:/home/hazevedo/localbin/apache-jena-3.2.0/lib/*"  *.java

javac -g -d ./ -classpath "/home/hazevedo/localbin/apache-jena-3.2.0/lib/*"  *.java
