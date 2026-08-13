# Operadores in e not in
# Strings são iteráveis (Ou, uma lista)
#  0 1 2 3 4 5 # Posição
#  O t á v i o # String
# -6-5-4-3-2-1 # Posição inversa

# nome = 'Otávio'
# print(nome[2])
# print(nome[-4]) # Para pegar pela posição inversa à posição 2
# print('vio' in nome)
# print('zero' in nome)
# print(10 * '-') # Lembrete que é possível colocar o mesmo caractere n vezes usando essa expressão
# print('vio' not in nome)
# print('zero' not in nome)

nome = input('Digite seu nome: ')
encontrar = input('Digite o que deseja encontrar: ')

if encontrar in nome:
    print(f'{encontrar} está em {nome}')
else:
    print(f'{encontrar} não está em {nome}')