# Operadores lógicos
# and (e) or (ou)  not (não)
# and - Todas as condições precisam ser verdadeiras.
# Se qualquer valor for considerado falso, a expressão inteira será avaliada naquele valor
# São considerados falsy
# 0 0.0 '' False
# Também existe o tipo None que é usado para representar um não valor

# entrada = input('[E]ntrar [S]air: ')
# senha_digitada = input('Senha: ')

# senha_permitida = '123456'
# if entrada == 'E' and senha_digitada == senha_permitida:
#     print('Entrar')
# else:
#     print('Sair')

# Falsys
# Avaliação de curto circuito (já para e retorna o primeiro false que encontrar)
print(True and False and True)
print(True and 0 and True) # Para no 0 por ser um Falsy
# print(bool(0.0))