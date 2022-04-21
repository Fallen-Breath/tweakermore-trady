package me.fallenbreath.tweakermore.trady.mixins;

import com.google.common.collect.Lists;
import me.fallenbreath.tweakermore.config.TweakerMoreConfigs;
import me.fallenbreath.tweakermore.trady.config.TradyConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Mixin(TweakerMoreConfigs.class)
public abstract class TweakerMoreConfigsMixin
{
	@Redirect(
			method = "<clinit>",
			at = @At(
					value = "INVOKE",
					target = "Ljava/lang/Class;getDeclaredFields()[Ljava/lang/reflect/Field;",
					remap = false
			),
			require = 0,
			remap = false
	)
	private static Field[] appendTradyStuffs(Class<?> instance)
	{
		List<Field> list = Lists.newArrayList();
		list.addAll(Arrays.asList(instance.getDeclaredFields()));
		list.addAll(Arrays.asList(TradyConfig.class.getDeclaredFields()));
		return list.toArray(new Field[0]);
	}
}
