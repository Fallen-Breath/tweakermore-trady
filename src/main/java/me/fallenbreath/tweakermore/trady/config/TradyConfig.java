package me.fallenbreath.tweakermore.trady.config;

import com.chocohead.mm.api.ClassTinkerers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import me.fallenbreath.tweakermore.TweakerMoreMod;
import me.fallenbreath.tweakermore.config.Config;
import me.fallenbreath.tweakermore.config.options.TweakerMoreConfigBoolean;
import me.fallenbreath.tweakermore.config.options.TweakerMoreConfigBooleanHotkeyed;
import me.fallenbreath.tweakermore.config.options.TweakerMoreConfigStringList;
import me.fallenbreath.tweakermore.config.options.TweakerMoreIConfigBase;
import me.fallenbreath.tweakermore.util.RegistryUtil;
import net.minecraft.item.Items;

import java.lang.reflect.Field;
import java.util.Set;

import static me.fallenbreath.tweakermore.config.ConfigFactory.*;

public class TradyConfig
{
	public static final Config.Category TRADY_CATEGORY = ClassTinkerers.getEnum(Config.Category.class, "TRADY");

	////////////////////
	//    TIS Trady   //
	////////////////////

	@Config(type = Config.Type.TWEAK, category = Config.Category.MC_TWEAKS)
	public static final TweakerMoreConfigBooleanHotkeyed TWEAKM_TRADY_LAPIS = newConfigBooleanHotkeyed("tweakmTradyLapis");

	@Config(type = Config.Type.TWEAK,category = Config.Category.MC_TWEAKS)
	public static final TweakerMoreConfigBooleanHotkeyed TWEAKM_TRADY_FARMER = newConfigBooleanHotkeyed("tweakmTradyFarmer");

	@Config(type = Config.Type.GENERIC, category = Config.Category.MC_TWEAKS)
	public static final TweakerMoreConfigBoolean TRADY_THROW_IF_FULL = newConfigBoolean("tradyThrowIfFull", false);

	@Config(type = Config.Type.LIST, category = Config.Category.MC_TWEAKS)
	public static final TweakerMoreConfigStringList TRADY_FARMER_TARGETS = newConfigStringList("tradyFarmerTargets", ImmutableList.of(RegistryUtil.getItemId(Items.CARROT), RegistryUtil.getItemId(Items.POTATO), RegistryUtil.getItemId(Items.PUMPKIN)));

	//////// Implementation Details ////////

	public static final Set<TweakerMoreIConfigBase> TRADY_OPTIONS = Sets.newLinkedHashSet();

	static
	{
		for (Field field : TradyConfig.class.getDeclaredFields())
		{
			Config annotation = field.getAnnotation(Config.class);
			if (annotation != null)
			{
				try
				{
					Object config = field.get(null);
					if (!(config instanceof TweakerMoreIConfigBase))
					{
						TweakerMoreMod.LOGGER.warn("[TweakerMore-Trady] {} is not a subclass of TweakerMoreIConfigBase", config);
						continue;
					}
					TRADY_OPTIONS.add((TweakerMoreIConfigBase) config);
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
