# Minewold Menu (mod de Fabric 1.21.6)

Reemplaza el menú principal de Minecraft por solo dos botones:
- **Jugar en Minewold**: entra directo a `minewold.net`, sin pasar por multijugador.
- **Un jugador**: abre el menú de mundos (para crear o abrir un mundo).

Además, si Minecraft intenta abrir la lista de multijugador (por ejemplo al salir del servidor), vuelve al menú principal.

## Compilar (automático con GitHub)
1. Crea un repositorio, por ejemplo `minewold-mod`, y sube TODO el contenido de esta carpeta (incluida `.github`).
2. En GitHub: pestaña **Actions** → espera a que termine "Compilar y publicar" (unos 3-5 minutos).
3. Se crea un **Release** con `minewold-menu.jar`.
4. En el `manifest.json` del launcher agrega: `{ "github": "TU_USUARIO/minewold-mod", "asset": "minewold-menu.jar" }`.

Cada vez que hagas cambios y los subas, se compila y publica una versión nueva, y el launcher la descarga solo.

## Compilar en tu PC (opcional)
Con Java 21 y Gradle 8.14: `gradle build` → `build/libs/minewold-menu-1.0.0.jar`.

## Opciones que pasa el launcher
`-Dminewold.server=IP` y `-Dminewold.start=single`.
