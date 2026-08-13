# Operadores lógicos
# and (e) or (ou)  not (não)
# or - Qualquer condição verdadeira avalia toda a expressão como verdadeira.
# Se qualquer valor for considerado verdadeiro, a expressão inteira será avaliada naquele valor.
# São considerados falsy
# 0 0.0 '' False
# São considerados truthy
# 1 1.0 ' ' True
# Também existe o tipo None que é usado para representar um não valor

# entrada = input('[E]ntrar [S]air: ')
# senha_digitada = input('Senha: ')

# senha_permitida = '123456'
# if (entrada == 'E' or entrada == 'e') and senha_digitada == senha_permitida:
#     print('Entrar')
# else:
#     print('Sair')

# Falsys and Truthys
# Avaliação de curto circuito (já para e retorna o primeiro true que encontrar)
senha = input('Senha: ') or 'Sem senha' # Assim que se faz uma avaliação de curto circuito de um valor inputado em Python e já lança uma "Mensagem de erro"
print(0 or False or 0 or 'abc' or True)