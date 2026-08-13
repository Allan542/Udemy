"""
Fatiamento de string
 012345678 -> índices positivos da string
 Olá mundo
-987654321 -> índices negativos da string (de trás para frente)
Fatiamento [i:f:p] [::] -> pega uma fatia da string ([::] é uma exceção, pois pega a string inteira)
i -> início
f -> fim (ps.: Nunca inclua o índice final. Sempre um a mais, mesmo nos índices negativos)
p -> passo (Quantos caracteres, o fatiamento pula até chegar no fim)
Se omitir qualquer um dos 3 atributos do fatiamento: caso início, pega do começo da string; caso fim, pega até o final da string;
caso passo, passa caractere a caractere por padrão.
[::-1] -> pega string invertida. Ps.: não funciona se colocar números positivos no início e fim, deixando em branco. Necessário colocar os índices negativos
obs.: a função len retorna a qtd
de caracteres da str
"""
variavel = 'Olá mundo'
print(variavel[-1:-10:-1])
print(len(variavel))