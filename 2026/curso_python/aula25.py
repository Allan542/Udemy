"""
Interpolação básica de strings
s - string
d e i - int
f - float
x e X - Hexadecimal (ABCDEF0123456789) (minúsculo e maiúsculo)
"""
nome = 'Allan'
preco = 1000.95473423
variavel = '%s, o preço total foi R$%.2f' % (nome, preco) # interpolação de string, usa o % para identificar o valor de uma variável a ser colocada. Se tiver mais de um, não é necessário parenteses
print(variavel)
print('O hexadecimal de %d é %04X' % (1500, 1500)) # %04X -> o 0 significa qual valor será colocado na hora de preencher o espaço faltante. O 4 é para dizer quantos espaços esse hexadecimal terá