# Lambdas
Para esta práctica hemos usado el lenguaje de programación Lua.
## Lambda en Lua
Lua puede crear una función lambda con la sintaxis de funciones anónimas. Por ejemplo, en nuestro caso, creamos una función lambda que dada una lista devuelve los valores pares de ésta.
```Lua
local pares = function(lista) par={} for _, e in pairs(lista) do if e % 2 == 0 then table.insert(par, e) end end return par end
```

La función pares recibirá un parámetro *lista* y devolverá la lista de los numeros pares de la lista dada.

Para ayudar a la legibilidad podemos mejorar la implementación anterior, para ello lo haremos en la clase *Lista*, dicha clase tiene los métodos **vacia**, **copia**, **getLista**, **mostrar**, **filtro** y **mostrarPares** en su implementación. Dichos métodos realizan las siguientes funcionalidades:

- **vacia() ->** Crea una lista vacía. Su implementación es muy simple, creamos la metatabla e iniciamos la lista como *null* (en Lua **null** se indica como **nil**). 

```Lua
    Lista = {}

    function Lista:vacia()
        local this = {}
        setmetatable(this, { __index = Lista})
        this.list = nil
        return this
    end
```
- **copia(lista) ->** Copia una lista. Dada la lista pasada como parametro en la función, asigna la lista que tiene en la "clase" a la lista proporcionada.
```Lua
    function Lista:copia(lista)
        self.list = lista
    end
```

- **getLista() ->** Devolvemos por valor la lista. Es un método observable, por lo que no modifica las variables internas, en su implementación solo devuelve la lista.

```Lua
    function Lista:getLista()
        return self.list
    end
```
- **mostrar() ->** Muestra los valores pares de la lista que tenemos almacenada. Esta es una de las funciones que tiene un poco de lógica, este método tiene como funcionalidad mostrar todos los valores de la lista por pantalla, por lo que si la lista esta vacía mostrará por pantalla un mensaje de error correspondiente. En caso contrario, mediante un bucle **for** mostrará todos los elementos mediante el método **io.write**.

```Lua
    function Lista:mostrar()
        if self.list ~= nil then
            io.write("La lista actual tiene el siguiente contenido: ( ")
            for _, element in pairs(self.list) do
                io.write(element .. " ")
            end
            print(")")
        else
            print("La lista esta vacia...")
        end
    end
```

- **mostrarPares() ->** Este método es muy sililar al anterior, con la diferencia que hacemos uso de la función lambda `function(element) return element % 2 == 0 end` Como dijimos anteriormente, ibamos a hacer de la implementación principal mas legible, pues aquí tenemos la función principal modificada. Como podemos observar creamos una nueva lista **listaPar**, donde almacenaremos los valores que son pares de nuestra lista. Esta lista le asignamos el valor obtenido por una función llamada **filtro**, la cúal devolverá los valores que cumpla el predicado pasado como segundo valor(lambda) de los valores de la lista pasado en el primer valor. La lógica principal sigue como el método **mostrar()**, si la lista es nula, devuelve un mensaje de error correspondiente. En caso contrario mostrará los valores por pantalla mediante el método **io.write**.
```Lua
    function Lista:mostrarPares()
        local listaPar = Lista:filtro(self.list, function(element) return element % 2 == 0 end)
        if listaPar ~= nil then
            io.write("Los valores pares de la lista son: ( ")
            for _, element in pairs(listaPar) do
                io.write(element .. " ") 
            end
            print(")")
        else
            print("La lista esta vacia...")
        end

    end
```

- **filtro() ->** Este método recibe una lista y un predicado(en este caso un lambda) y devolverá una lista (**result**), la cuál estará vacía al inicio del método. Si los valores de la lista cumplen con el predicado, se irá almacenado a la lista que devolveremos(result). Por último, solo nos queda devolver dicha lista.

```Lua
    function Lista:filtro(tbl, predicate)
        local result = {}
        for i, v in ipairs(tbl) do
            if predicate(v) then
                table.insert(result, v)
            end
        end
        return result
    end

```

Como podemos observar, hemos conseguido obtener una implementación de un ejemplo de lambda en Lua muy legible, el lambda se pasa como predicado a la función y es dicha función la que irá llamando a la función.

# Conclusión
En Lua el uso de lambdas es muy sencillo y fácil de implementar con funciones anonimas ya que este lenguaje no posee un soporte nativo para estas.

# Como compilar
Para compilar un código en Lua, primero debes comprobar que posees lua en su sistema, una vez lo tengas solo debes introducir el siguiente comando en el directorio donde se encuentra el código Lua:
```
    lua lambda.lua
```

Si el comando no te funciona y estás en la ruta correcta, debes instalar el compilador, por ello en ubuntu debemos escribir el siguiente comando en la terminal:
```
	sudo apt install lua5.3
```
En caso de que tengas un SO en el que no puedas ejecutar este comando le recomiendo que visite el siguiente enlace: https://www.solvetic.com/tutoriales/article/5802-como-instalar-lua-en-linux-o-windows-10/