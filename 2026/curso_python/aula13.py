nome = 'Allan Carlos'
altura = 1.93
peso = 92
imc = peso / altura ** 2 # Essas reticências no Python (...), são chamadas de Ellipsis. Pode ser usado como placeholder, ou um código que ainda não foi escrito

# F strings: habilita a possibilidade de usar variáveis dentro de um texto usando chave
# Obs.: dentro das mesmas chaves, é possível formatar um número decimal, usando a expressão após os : ':.1f'
# Outra coisa que pode colocar na formatação de string, antes do .1f, ou até mesmo sem ele, é possível colocar um caractere que servirá como separador de números maior que 1000. O mesmo número 1000, com
# isso, seria exibido da seguinte forma caso o format string estivesse dessa forma :, -> 1,000
# Obs.2: só aceita vírgula
linha_1 = f'{nome} tem {altura:,.2f} de altura'
linha_2 = f'pesa {peso} quilos e seu IMC é'
linha_3 = f'{imc}'

# Allan carlos tem 1.80 de altura,
# pesa 95 quilos e seu IMC é
# 29.320987654320987

print(linha_1)
print(linha_2)
print(linha_3)
