package io.github.tivj.sunirl;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;

public class SunIRL implements ModInitializer, ClientModInitializer {
	public static SunIRL instance;
	public long dayLength = 24000L;

	public CalculationHelper positionCalculator;
	public SunConfig config;
	public SunConfigOld oldConfig;

	@Override
	public void onInitialize() {
		instance = this;

		AutoConfig.register(SunConfig.class, GsonConfigSerializer::new);
		this.config = AutoConfig.getConfigHolder(SunConfig.class).getConfig();
		this.oldConfig = new SunConfigOld(this.config);
		this.positionCalculator = new CalculationHelper();
	}

	public void initClient() {
		ClientTickCallback.EVENT.register(this::onClientTick);
	}

	public void onClientTick(MinecraftClient client) {
		if (client.world != null) {
			if (positionCalculator.ticksUntilNextCalculation > 0) {
				positionCalculator.ticksUntilNextCalculation--;
			} else {
				positionCalculator.ticksUntilNextCalculation = this.config.updateDelay;
				positionCalculator.recalculate(client.world.getTimeOfDay() % dayLength);
			}
		}
	}

	@Override
	public void onInitializeClient() {
		SunIRL.instance.initClient(); // Make sure there are no more than 1 instances
	}
}
