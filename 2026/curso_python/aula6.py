# conversão de tipos, coerção
# type convertion, typecasting, coercion
# é o ato de converter um tipo em outro
# tipos imutáveis e primitivos
# str, int, float, bool
# print(1 + 1) # soma
# print('1' + 1) # erro
# print('a' + 'b') # concatenação
print(int('1'), type(int('1')))
print(type(float('1') + 1)) # Somar int com float retorna um float
print(bool('')) # String sem valor convertendo num bool vira False (?) e quando tem um espaço vira True (??)
print(str(11) + 'b') # Python diferente de java, quando se coloca um número para concatenar, ele não entende e joga exception. Tem que converter o valor primeiro