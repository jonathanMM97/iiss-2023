# Lambdas
Para esta practica hemos usado el lenguaje de programación Lua.
## Lambda en Lua
Lua puede crear una función lambda con la sintaxis de funciones anonimas. Por ejemplo, en nuestro caso, creamos una función lambda que dada una lista devuelve los valores pares de ésta.
```Lua
local pares = function(lista) par={} for _, e in pairs(lista) do if e % 2 == 0 then table.insert(par, e) end end return par end
```

La función pares recibirá un parámetro *lista* y devolverá la lista de los numeros pares de la lista dada.

Para ayudar a la legibilidad podemos mejorar la implementación anterior, para ello lo haremos en la clase Lista