a = 'A'
b = 'B'
c = 1.1
# Detalhe curioso: F string abstrai a soma dessa string ao lado e o método abaixo em uma coisa só, com diferença desse método format ser posicional
# Outro detalhe: também, é possível pegar valor de um format, usando a posição. Mas até em métodos tradicionais da programação, pegar posição não é muito confiável
string = 'b={nome2} a={nome1} a={nome1} c ={nome3:.2f}'
# Parecido com java, com a diferença que os argumentos do format string e a própria string a ser formatada, é passada no mesmo método e no java, na formatação se utiliza %s para texto por exemplo
# No python há a existência de uma coisa chamada parâmetro nomeado. Quando o primeiro argumento recebe um parâmetro nomeado, os argumentos subsequentes também tem que receber
# Porém, não é necessário nomear os argumentos anteriores quando o argumento subsequente a argumentos anteriores, vira um parâmetro nomeado
# Detalhe: parâmetro é o nome da variável; argumento é o valor que esse parâmetro/variável está recebendo
# Outro detalhe: com a existência de parâmetro nomeado, ele acaba sendo mais confiável do que usar a posição do argumento
# Mais um detalhe: quando usado um parâmetro nomeado, as posições se tornam irrelevantes e até mesmo dão erro de compilação
formato = string.format(
   nome1=a, 
   nome2=b, 
   nome3=c)

print(formato)