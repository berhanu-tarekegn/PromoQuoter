package com.kifiya.PromoQuoter.promotion;

public enum PromotionType  {
    PERCENT_OFF_CATEGORY(10),
    BUY_X_GET_Y(20);

    public final int order;
    PromotionType(int order) { this.order = order; }
}
