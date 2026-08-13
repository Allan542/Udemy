"""
Formatação básica de strings
s - string
d - int
f - float
.<número de dígitos>f
x ou X - Hexadecimal
(Caractere)(><^)(quantidade)
> - Esquerda
< - Direita
^ - Centro
= - Força o número a aparecer antes dos zeros
Sinal - + ou -
Ex.: 0>-100,.1f
Conversion flags - !r !s !a __repr__ __str__ __asc__ -> métodos da string/depende do tipo da variável
"""
variavel = 'ABC'
print(f'{variavel}')
print(f'{variavel: >10}') # quero que coloque 10 caracteres de espaço a esquerda, como se fosse um padding
print(f'{variavel: <10}') # quero que coloque 10 caracteres de espaço a direita, como se fosse um padding
print(f'{variavel:0^10}') # quero que coloque 10 caracteres de espaço a direita, como se fosse um padding
# Ps.: que coisa estranha...
print(f'{-1000.5646324672364:0=+10,.1f}') # é possível colocar antes do ponto, vírgula para exibir a partir do milhar separado por vírgula. O + é para o python mostrar o sinal se for positivo. Negativo ele já mostra por padrão
print(f'O hexadecimal de 1500 é {1500:08X}')
print(f'{variavel!r}')