Aspect = require "aspect"--cargar la biblioteca AspectLua

--Clase Account
Account = {balance = 0}

function Account:deposit(ammount)
	self.balance = self.balance + ammount
end

function Account:withdraw(c)
	if c > self.cantidad then
		error("saldo insuficiente")
	end
		self.cantidad = self.cantidad - c
end

function Account:logfuncion(a)
    print("Fue depositado ".. a)
end

asp = Aspect:new()
--id = asp:aspect({name = 'logaspect'}, {pointcutname = 'logdeposit', designator = 'call', list = {'Account.deposit'}},{type = 'before', action = logfuncion})
id = asp:aspect( {name = 'logaspect'},{pointcutname = 'logdeposit', designator = 'call', list = {'Account.deposit'}},{type = 'before', action = logfunction} )
local order = Aspect:getOrder('Account.deposit')
oldasp = asp:getAspect(id)
oldasp.advice.type = 'after'
asp:updateAspect(id, oldasp)
Aspect:setOrder('Account.deposit', {order[2], order[1]})
--Definimos el aspecto que queremos aplicar a deposit
--function secretNumber(c)
--    local numberSecret = 1234
--    c.proceder()--Llama a la funcion original
--    print("Introduce el numero secreto: ")
--    local userNumber = io.read()
--    if not (numberSecret == userNumber) then
--        error("Numero secreto incorrecto...")
--   end
--end

--Uso del aspecto
--s = Account:new{cantidad = 0.00}--Creamos una instancia de la clase Account
--local miAspecto = aspecto(secretNumber)
---s.deposit = miAspecto(s.deposit)
--s:deposit(100)    --Llamamos al metodo deposit con el aspecto añadido