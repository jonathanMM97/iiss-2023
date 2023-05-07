-- Cargar la biblioteca AspectLua
local aspect = require("aspect")

-- Definir la función que queremos registrar
local function mi_funcion()
  print("Ejecutando mi_funcion...")
  for i = 1, 1000000 do
    -- hacer algo
  end
  print("Mi_funcion ha terminado.")
end

-- Definir el aspecto que queremos aplicar a la función
local function registrar_tiempo_de_ejecucion(contexto)
  local tiempo_inicio = os.clock()
  contexto.proceder() -- Llamada a la función original
  local tiempo_fin = os.clock()
  print("La función tardó", tiempo_fin - tiempo_inicio, "segundos en ejecutarse.")
end

-- Crear un nuevo objeto Aspecto y aplicarlo a la función
local mi_aspecto = aspect(registrar_tiempo_de_ejecucion)
mi_funcion = mi_aspecto(mi_funcion)

-- Llamar a la función para probar el aspecto
mi_funcion()
