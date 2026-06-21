"""
Python = Linguagem de programação
Tipo de tipagem = Dinâmica / Forte
Nota: ter tipagem dinâmica, significa que o Python entende o tipo ao escrever a informação no script. Ou seja, não precisa que declare a variável estaticamente do tipo necessário
previamente. Ele já entende se for um int se passar apenas números inteiros, por exemplo.
Python tem tipagem forte, ou seja, se uma variável é de um determinado tipo, não pode ser mudado para outro tipo que nem é no JS
str -> string -> texto
Strings são textos que estão dentro de aspas
"""
print(1234)

# Aspas simples
print('Allan Carlos')
print(1, 'Allan "Carlos"') # É possível no python, colocar aspas duplas dentro de aspas simples

# Aspas duplas
print("Allan Carlos")
print(2, "Allan 'Carlos'") # É possível no python, colocar aspas simples dentro de aspas duplas

# Escape
print("Allan \"Carlos\"")

# r: Exibe o caractere de escape
print(r"Allan \"Carlos\"")