adicao = 10 + 10
print('Adição', adicao)

subtracao = 10 - 5
print('Subtração', subtracao)

multiplicacao = 10 * 10
print('Multiplicação', multiplicacao)

divisao = 10 / 3  # sempre retorna float e também é impreciso igual no Java
print('Divisão', divisao)

# retorna inteiro se os dois números do cálculo forem inteiros, mesmo que o cálculo dê um valor flutuante. Caso não, retorna float, porém trunca o número e retorna o decimal como 0
divisao_inteira = 10 // 3
print('Divisão inteira', divisao_inteira)

exponenciacao = 2 ** 10
print('Exponenciação', exponenciacao)

modulo = 25 % 5  # resto da divisão: o que sobrou de valor após o cálculo da divisão e não o resultado da divisão (quociente)
print('Módulo', modulo)

print(10 % 8 == 0)
print(16 % 8 == 0)
print(10 % 2 == 0) # é par ou não
print(15 % 2 == 0)
print(16 % 2 == 0)