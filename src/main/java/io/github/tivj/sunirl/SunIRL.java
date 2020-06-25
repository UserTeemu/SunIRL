package io.github.tivj.sunirl;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;

public class SunIRL implements ModInitializer, ClientModInitializer {
	public static SunIRL instance;
	public static final long ticksIn24h = 1728000L;
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

		/* Debug Purposes
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			dispatcher.register(CommandManager.literal("sundebug").then(CommandManager.argument("arg", FloatArgumentType.floatArg()).executes(context -> {
				commandValue = FloatArgumentType.getFloat(context, "arg");
				return 1;
			})).executes(context -> {
				commandValue++;
				return 1;
			}));
		});*/
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
				positionCalculator.recalculate(client.world.getTimeOfDay());
			}
		}
	}

	@Override
	public void onInitializeClient() {
		SunIRL.instance.initClient(); // Make sure there are no more than 1 instances
	}
}
