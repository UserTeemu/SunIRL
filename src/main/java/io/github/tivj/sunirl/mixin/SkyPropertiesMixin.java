package io.github.tivj.sunirl.mixin;

import net.minecraft.client.render.SkyProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * This is to make twilight appear only when it should IRL
 * See https://en.wikipedia.org/wiki/Twilight#/media/File:Twilight-dawn_subcategories.svg for twilight angles.
 */
@Mixin(SkyProperties.class)
public class SkyPropertiesMixin {
    @ModifyConstant(method = "getSkyColor", constant = @Constant(floatValue = 0.4F, ordinal = 1))
    private float max(float og) {
        return 0.104503036F;
    }

    @ModifyConstant(method = "getSkyColor", constant = @Constant(floatValue = -0.4F, ordinal = 0))
    private float min(float og) {
        return -0.30894405F;
    }
}