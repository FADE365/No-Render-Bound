package org.delta.norenderbound.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.delta.norenderbound.client.NoRenderConfig;
import org.delta.norenderbound.client.gui.NoRenderGuiScreen;
import org.lwjgl.glfw.GLFW;


public class NoRender implements ClientModInitializer {

    public static NoRenderConfig config;
    public static BlockPos pos1;
    public static BlockPos pos2;
    public static boolean renderEnabled;
    private static KeyBinding openGuiKey;

    @Override
    public void onInitializeClient() {
        // Загрузка конфигурации
        config = NoRenderConfig.load();
        pos1 = config.pos1;
        pos2 = config.pos2;
        renderEnabled = config.renderEnabled;

        // Регистрация клавиши для открытия GUI
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open Gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "No Render Bound"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openGuiKey.wasPressed()) {
                openGuiScreen(new NoRenderGuiScreen(Text.literal("No Render Settings")));
                config.save();
            }
        });

        // Сохранение конфигурации при завершении работы
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            config.pos1 = pos1;
            config.pos2 = pos2;
            config.renderEnabled = renderEnabled;
            config.save();
        }));
    }

    private void openGuiScreen(NoRenderGuiScreen screen) {
        MinecraftClient.getInstance().setScreen(screen);
    }
}
