primeiro_valor = input('Digite um valor: ')
segundo_valor = input('Digite outro valor: ')

if primeiro_valor > segundo_valor:
    print(f'{primeiro_valor=} é maior do que {segundo_valor=}')
    # print(f'{primeiro_valor=} é maior '
    #       f'do que {segundo_valor=}')
elif segundo_valor > primeiro_valor:
    print(f'{segundo_valor=} é maior do que {primeiro_valor=}')
    # print(f'{segundo_valor=} é maior '
    #       f'do que {primeiro_valor=}') # é possível colocar mais de uma F string em um print
else:
    print(f'Os valores {primeiro_valor=} e {segundo_valor} são iguais')
