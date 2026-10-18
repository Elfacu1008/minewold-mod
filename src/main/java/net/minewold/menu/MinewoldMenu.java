package net.minewold.menu;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;

/**
 * Menú principal de Minewold: solo dos botones.
 *  - Jugar en Minewold: entra directo al servidor (sin pasar por la lista de multijugador).
 *  - Un jugador: abre el menú de mundos (donde se crea un mundo nuevo).
 *
 * El launcher puede pasar:
 *  -Dminewold.server=minewold.net   (IP del servidor)
 *  -Dminewold.start=single          (abrir directo el menú de un jugador)
 */
public class MinewoldMenu implements ClientModInitializer {
    private static final String SERVER = System.getProperty("minewold.server", "minewold.net");
    private static boolean startedSingle = false;

    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (screen instanceof TitleScreen) {
                setupTitle(client, screen, width, height);
            } else if (screen instanceof JoinMultiplayerScreen) {
                // Nunca mostrar la lista de multijugador (por ejemplo, al salir de un servidor)
                client.execute(() -> client.setScreen(new TitleScreen()));
            }
        });
    }

    private static void setupTitle(Minecraft client, Screen screen, int width, int height) {
        List<AbstractWidget> buttons = Screens.getButtons(screen);

        // Quita todos los botones de Minecraft; deja solo el texto de copyright de abajo
        for (AbstractWidget w : new ArrayList<>(buttons)) {
            if (w.getY() < height - 14) buttons.remove(w);
        }

        int x = width / 2 - 110;
        int y = height / 4 + 48;

        buttons.add(Button.builder(Component.literal("Jugar en Minewold"), b -> joinServer(client, screen))
                .bounds(x, y, 220, 26).build());
        buttons.add(Button.builder(Component.literal("Un jugador"), b -> client.setScreen(new SelectWorldScreen(screen)))
                .bounds(x, y + 36, 220, 26).build());

        if ("single".equals(System.getProperty("minewold.start")) && !startedSingle) {
            startedSingle = true;
            client.execute(() -> client.setScreen(new SelectWorldScreen(screen)));
        }
    }

    private static void joinServer(Minecraft client, Screen parent) {
        ServerData data = new ServerData("Minewold", SERVER, ServerData.Type.OTHER);
        ConnectScreen.startConnecting(parent, client, ServerAddress.parseString(SERVER), data, false, null);
    }
}
