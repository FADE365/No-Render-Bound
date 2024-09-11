package org.delta.norenderbound.mixin;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.entity.BlockEntity;
import org.delta.norenderbound.client.NoRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class NoRenderEntityMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(BlockEntity blockEntity, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider bufferSource, CallbackInfo ci) {
        // Получаем позицию блока
        BlockPos blockPos = blockEntity.getPos();

        // Проверяем, находится ли блок в пределах заданного квадрата и отключён ли рендеринг
        if (NoRender.pos1 != null && NoRender.pos2 != null && !NoRender.renderEnabled && isWithinBounds(blockPos, NoRender.pos1, NoRender.pos2)) {
            ci.cancel(); // Отменяем рендеринг блока
        }
    }

    // Функция для проверки, находится ли позиция в пределах заданного квадрата
    @Unique
    private boolean isWithinBounds(BlockPos pos, BlockPos pos1, BlockPos pos2) {
        int minX = Math.min(pos1.getX(), pos2.getX());
        int maxX = Math.max(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());
        int maxY = Math.max(pos1.getY(), pos2.getY());
        int minZ = Math.min(pos1.getZ(), pos2.getZ());
        int maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return pos.getX() >= minX && pos.getX() <= maxX &&
                pos.getY() >= minY && pos.getY() <= maxY &&
                pos.getZ() >= minZ && pos.getZ() <= maxZ;
    }
}
