package me.fallenbreath.tweakermore.trady.mixins;

import me.fallenbreath.tweakermore.config.Config;
import me.fallenbreath.tweakermore.config.TweakerMoreOption;
import me.fallenbreath.tweakermore.config.options.TweakerMoreIConfigBase;
import me.fallenbreath.tweakermore.trady.config.TradyConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TweakerMoreOption.class)
public abstract class TweakerMoreOptionMixin
{
	@Shadow(remap = false) @Final
	private TweakerMoreIConfigBase config;

	@Inject(
			method = "getCategory",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private void appendTradyStuffs(CallbackInfoReturnable<Config.Category> cir)
	{
		if (TradyConfig.TRADY_OPTIONS.contains(this.config))
		{
			cir.setReturnValue(TradyConfig.TRADY_CATEGORY);
		}
	}
}
