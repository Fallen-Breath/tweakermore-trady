package me.fallenbreath.tweakermore.trady.mixins.tweakmTrady;

import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MerchantScreen.class)
public interface MerchantScreenAccessor
{
	@Accessor
	void setSelectedIndex(int value);

	@Invoker
	void invokeSyncRecipeIndex();
}
