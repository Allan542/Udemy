# A mesma coisa que usar Scanner no Java com algum dos métodos next. Ou seja, pede um valor a ser digitado no terminal. Ela retorna o valor inputado por ser uma função
# Logo, pode ser colocado numa variável. E input sempre retorna umas string
# nome = input('Qual o seu nome? ')
# print(f'O seu nome é {nome=}') # Colocar entre as chaves, variavel=, mostra: 'nome_da_variavel=valor'

numero_1 = input('Digite um número: ')
numero_2 = input('Digite outro número: ')

# Não recomendado fazer casting direto no input para converter o valor de str para o tipo desejado. Sempre fazer um type checking após isso, até por questão de querer ver o valor inputado
int_numero_1 = int(numero_1)
int_numero_2 = int(numero_2)

print(f'A soma dos números é: {int_numero_1 + int_numero_2}')