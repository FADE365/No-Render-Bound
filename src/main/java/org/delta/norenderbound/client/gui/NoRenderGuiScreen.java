package org.delta.norenderbound.client.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.delta.norenderbound.client.NoRender;

import java.util.Objects;

public class NoRenderGuiScreen extends Screen {

    private TextFieldWidget pos1XField, pos1YField, pos1ZField;
    private TextFieldWidget pos2XField, pos2YField, pos2ZField;
    private ButtonWidget renderToggleButton, bindKeyButton;

    public NoRenderGuiScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Инициализация полей значениями из глобальных переменных
        pos1XField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY, 40, 20, Text.literal("X1"));
        pos1YField = new TextFieldWidget(this.textRenderer, centerX - 55, centerY, 40, 20, Text.literal("Y1"));
        pos1ZField = new TextFieldWidget(this.textRenderer, centerX - 10, centerY, 40, 20, Text.literal("Z1"));
        this.addSelectableChild(pos1XField);
        this.addSelectableChild(pos1YField);
        this.addSelectableChild(pos1ZField);

        if (NoRender.pos1 != null) {
            pos1XField.setText(String.valueOf(NoRender.pos1.getX()));
            pos1YField.setText(String.valueOf(NoRender.pos1.getY()));
            pos1ZField.setText(String.valueOf(NoRender.pos1.getZ()));
        }

        pos2XField = new TextFieldWidget(this.textRenderer, centerX - 100, centerY + 30, 40, 20, Text.literal("X2"));
        pos2YField = new TextFieldWidget(this.textRenderer, centerX - 55, centerY + 30, 40, 20, Text.literal("Y2"));
        pos2ZField = new TextFieldWidget(this.textRenderer, centerX - 10, centerY + 30, 40, 20, Text.literal("Z2"));
        this.addSelectableChild(pos2XField);
        this.addSelectableChild(pos2YField);
        this.addSelectableChild(pos2ZField);

        if (NoRender.pos2 != null) {
            pos2XField.setText(String.valueOf(NoRender.pos2.getX()));
            pos2YField.setText(String.valueOf(NoRender.pos2.getY()));
            pos2ZField.setText(String.valueOf(NoRender.pos2.getZ()));
        }

        // Кнопка включения/выключения рендеринга
        renderToggleButton = ButtonWidget.builder(Text.literal(NoRender.renderEnabled ? "Disable Render" : "Enable Render"), button -> {
            NoRender.renderEnabled = !NoRender.renderEnabled;
            button.setMessage(Text.literal(NoRender.renderEnabled ? "Disable Render" : "Enable Render"));
        }).dimensions(centerX - 100, centerY - 60, 200, 20).build();
        this.addDrawableChild(renderToggleButton);

        // Кнопка My Pos для позиции 1
        this.addDrawableChild(ButtonWidget.builder(Text.literal("My Pos 1"), button -> {
            BlockPos pos = getCurrentPlayerPosition();
            pos1XField.setText(String.valueOf(pos.getX()));
            pos1YField.setText(String.valueOf(pos.getY()));
            pos1ZField.setText(String.valueOf(pos.getZ()));
            // Сохраняем координаты в глобальные переменные
            NoRender.pos1 = pos;
        }).dimensions(centerX + 45, centerY, 80, 20).build());

        // Кнопка My Pos для позиции 2
        this.addDrawableChild(ButtonWidget.builder(Text.literal("My Pos 2"), button -> {
            BlockPos pos = getCurrentPlayerPosition();
            pos2XField.setText(String.valueOf(pos.getX()));
            pos2YField.setText(String.valueOf(pos.getY()));
            pos2ZField.setText(String.valueOf(pos.getZ()));
            // Сохраняем координаты в глобальные переменные
            NoRender.pos2 = pos;
        }).dimensions(centerX + 45, centerY + 30, 80, 20).build());
    }

    private BlockPos getCurrentPlayerPosition() {
        assert Objects.requireNonNull(this.client).player != null;
        assert this.client.player != null;
        return this.client.player.getBlockPos();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        // Рендерим текстовые поля для позиций
        pos1XField.render(context, mouseX, mouseY, delta);
        pos1YField.render(context, mouseX, mouseY, delta);
        pos1ZField.render(context, mouseX, mouseY, delta);

        pos2XField.render(context, mouseX, mouseY, delta);
        pos2YField.render(context, mouseX, mouseY, delta);
        pos2ZField.render(context, mouseX, mouseY, delta);

        // Отрисовка заголовка окна
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }
}
