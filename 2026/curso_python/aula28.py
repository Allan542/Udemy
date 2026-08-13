"""
Exercício
Peça ao usuário para digitar seu nome
Peça ao usuário digitar sua idade
Se nome e idade forem digitados:
    Exiba:
        Seu nome é {nome}
        Seu nome invertido é {nome invertido}
        Se nome contém (ou não) espaços
        Seu nome tem {n} letras
        A primeira letra do seu nome é {letra}
        A última letra do seu nome é {letra}
Se nada for digitado em seu nome ou idade:
    exiba "Desculpe, você deixou campos vazios."
"""
nome = input('Digite seu nome: ') or 'Sem dado'
idade = input('Digite sua idade: ') or 'Sem dado'

if 'Sem dado' not in (nome, idade): # Esqueci do Falsy. if nome and idade também funcionaria
    int_idade = int(idade)
    valida_nome = "Contém" if ' ' in nome else 'Não contém'
    print(f'Seu nome é {nome}')
    print(f'Seu nome invertido é {nome[::-1]}')
    print(f'Seu nome {valida_nome} espaços')
    print(f'Seu nome tem {len(nome)} letras')
    print(f'A primeira letra do seu nome é {nome[0]}')
    print(f'A última letra do seu nome é {nome[-1]}')
else:
    print('Desculpe, você deixou campos vazios.')
