Lista = {}

function Lista:vacia()
    local this = {}
    setmetatable(this, { __index = Lista})
    this.list = nil
    return this
end

function Lista:copia(lista)
    self.list = lista
end

function Lista:getLista()
    return self.list
end

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

function Lista:filtro(tbl, predicate)
    local result = {}
    for i, v in ipairs(tbl) do
        if predicate(v) then
            table.insert(result, v)
        end
    end
    return result
end

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

function Lista:mostrarDivisores(n)
    return function()
        local listaPar = Lista:filtro(self.list, function(element) return element % n == 0 end)
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
end


local lista = Lista.vacia({})
lista:copia({0,1,2,3,4,5,6,7,8,9})
lista:mostrar()
lista:mostrarPares()
local valor = lista:mostrarDivisores(3)
valor()