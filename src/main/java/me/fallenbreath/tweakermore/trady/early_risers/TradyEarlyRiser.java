package me.fallenbreath.tweakermore.trady.early_risers;

import com.chocohead.mm.api.ClassTinkerers;

public class TradyEarlyRiser implements Runnable
{
	private static final String CONFIG_CATEGORY_CLASS_PATH = "me.fallenbreath.tweakermore.config.Config$Category";

	@Override
	public void run()
	{
		// private FeatureToggle(String name, boolean defaultValue, String defaultHotkey, String comment, String prettyName)
		ClassTinkerers.enumBuilder(CONFIG_CATEGORY_CLASS_PATH).
				addEnum("TRADY").
				build();
	}
}
