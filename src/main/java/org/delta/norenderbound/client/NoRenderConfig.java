package org.delta.norenderbound.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.math.BlockPos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NoRenderConfig {
    public boolean renderEnabled = true;
    public BlockPos pos1;
    public BlockPos pos2;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/norenderbound.json");

    // Метод для загрузки конфигурации
    public static NoRenderConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, NoRenderConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new NoRenderConfig(); // Если конфигурация не найдена, возвращаем конфигурацию по умолчанию
    }

    // Метод для сохранения конфигурации
    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
