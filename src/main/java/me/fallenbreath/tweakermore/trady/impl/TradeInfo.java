package me.fallenbreath.tweakermore.trady.impl;

import org.jetbrains.annotations.Nullable;

public class TradeInfo
{
	public final int offerIndex;
	public final boolean tradeAll;
	public final @Nullable String comment;

	public TradeInfo(int offerIndex, boolean tradeAll, @Nullable String comment)
	{
		this.offerIndex = offerIndex;
		this.tradeAll = tradeAll;
		this.comment = comment;
	}
}
