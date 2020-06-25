package io.github.tivj.sunirl.mixin;

import net.minecraft.client.render.SkyProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SkyProperties.class)
public class SkyPropertiesMixin {
    // This is to make twilight appear only when it should IRL
    // See https://en.wikipedia.org/wiki/Twilight#/media/File:Twilight-dawn_subcategories.svg for twilight angles.

    //if (g >= -0.9781476F && g <= 1F)
    @ModifyConstant(method = "getSkyColor", constant = @Constant(floatValue = 0.4F, ordinal = 1))
    private float max(float og) {
        return og;
    }

    @ModifyConstant(method = "getSkyColor", constant = @Constant(floatValue = -0.4F, ordinal = 0))
    private float min(float og) {
        return og;
    }
}