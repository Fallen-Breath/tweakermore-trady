package me.fallenbreath.tweakermore.trady.impl;

import com.google.common.collect.Lists;
import fi.dy.masa.malilib.util.InfoUtils;
import me.fallenbreath.tweakermore.trady.config.TradyConfig;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TraderOfferList;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LapisBuyTradingHelper extends AbstractTradingHelper
{
	private static final ItemStack LAPIS_LAZULI_2x = new ItemStack(Items.LAPIS_LAZULI, 2);

	public LapisBuyTradingHelper(MerchantScreen merchantScreen)
	{
		super(merchantScreen);
	}

	@Override
	public boolean isEnabled()
	{
		return !TradyConfig.TWEAKM_TRADY_UNLOCK_LAPIS.getBooleanValue() && TradyConfig.TWEAKM_TRADY_BUY_LAPIS.getBooleanValue() && this.testProfession("entity.minecraft.villager.cleric");
	}

	@Override
	protected boolean shouldCloseContainerAfterTrade()
	{
		return true;
	}

	@Override
	public void checkOffer()
	{
		TraderOfferList traderOfferList = this.container.getRecipes();
		int validLapisIndex = -1;
		for (int i = 0; i < traderOfferList.size(); i++)
		{
			TradeOffer tradeOffer = traderOfferList.get(i);
			boolean thisOfferIsLapis = offerMatches(tradeOffer, Items.EMERALD, EMPTY, Items.LAPIS_LAZULI);
			boolean thisOfferIsNiceLapis = offerMatches(tradeOffer, EMERALD_1x, EMPTY, LAPIS_LAZULI_2x);
			if (thisOfferIsNiceLapis)
			{
				if (!tradeOffer.isDisabled())
				{
					validLapisIndex = i;
					break;
				}
			}
			else if (thisOfferIsLapis)
			{
				InfoUtils.printActionbarMessage("[Trady Lapis Buy] Abort: %1$s is profiteer", this.merchantScreen.getTitle());
				return;
			}
		}
		if (validLapisIndex != -1)
		{
			this.prepareTrade(validLapisIndex, true);
			return;
		}

		List<Item> unlockerBuyItems = TradyConfig.TWEAKM_TRADY_BUY_LAPIS_UNLOCKER_ITEMS.getStrings().stream().
				map(itemId -> Registry.ITEM.getOrEmpty(new Identifier(itemId)).orElse(null)).
				filter(Objects::nonNull).
				collect(Collectors.toList());
		List<UnlockerChoice> unlockerChoices = Lists.newArrayList();
		for (Item toBuy : unlockerBuyItems)
		{
			for (int i = 0; i < traderOfferList.size(); i++)
			{
				TradeOffer tradeOffer = traderOfferList.get(i);
				if (!tradeOffer.isDisabled() && offerMatches(tradeOffer, Items.EMERALD, EMPTY, toBuy))
				{
					unlockerChoices.add(new UnlockerChoice(i, tradeOffer.getMaxUses() - tradeOffer.getUses(), tradeOffer.getAdjustedFirstBuyItem(), tradeOffer.getSellItem()));
					break;
				}
			}
		}
		if (unlockerChoices.isEmpty())
		{
			InfoUtils.printActionbarMessage("[Trady Lapis Buy] No valid unlocker trades");
			return;
		}

		for (UnlockerChoice choice : unlockerChoices)
		{
			int priceToBuyAll = choice.getEmeraldCountToBuyAll();
			if (hasEnoughItemsInInventory(new ItemStack(Items.EMERALD, priceToBuyAll)))
			{
				this.prepareTrade(choice.index, true, "unlocker");
				return;
			}
		}

		unlockerChoices.sort(Comparator.comparingInt(UnlockerChoice::getEmeraldCountToBuyAll));
		UnlockerChoice cheapest = unlockerChoices.get(0);
		InfoUtils.printActionbarMessage(
				"[Trady Lapis Buy] No enough emerald. Cheapest unlocker: %1$s (need %2$s emeralds)",
				cheapest.sell.getName(), cheapest.getEmeraldCountToBuyAll()
		);
	}

	private static class UnlockerChoice
	{
		public final int index;
		public final int remainingUse;
		public final ItemStack price;
		public final ItemStack sell;

		private UnlockerChoice(int index, int remainingUse, ItemStack price, ItemStack sell)
		{
			this.index = index;
			this.remainingUse = remainingUse;
			this.price = price;
			this.sell = sell;
		}

		public int getEmeraldCountToBuyAll()
		{
			return this.remainingUse * this.price.getCount();
		}
	}
}
