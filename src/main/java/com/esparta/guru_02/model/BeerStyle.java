package com.esparta.guru_02.model;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

import java.util.Map;

/*
 * Author: M
 * Date: 24-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Slf4j
public enum BeerStyle {

    // =======================
    // IPA
    // =======================
    AMERICAN_IPA,
    AMERICAN_DOUBLE_IMPERIAL_IPA,
    AMERICAN_WHITE_IPA,
    BELGIAN_IPA,
    ENGLISH_INDIA_PALE_ALE_IPA,

    // =======================
    // PALE / AMBER / BROWN ALE
    // =======================
    AMERICAN_PALE_ALE_APA,
    AMERICAN_AMBER_RED_ALE,
    AMERICAN_BLACK_ALE,
    AMERICAN_BLONDE_ALE,
    AMERICAN_BROWN_ALE,
    ENGLISH_PALE_ALE,
    ENGLISH_BROWN_ALE,
    IRISH_RED_ALE,
    SCOTTISH_ALE,
    SCOTCH_ALE,

    // =======================
    // STRONG / BARLEYWINE
    // =======================
    AMERICAN_STRONG_ALE,
    ENGLISH_STRONG_ALE,
    AMERICAN_BARLEYWINE,
    ENGLISH_BARLEYWINE,
    OLD_ALE,
    WINTER_WARMER,

    // =======================
    // BELGIAN & FARMHOUSE
    // =======================
    BELGIAN_DARK_ALE,
    BELGIAN_PALE_ALE,
    BELGIAN_STRONG_DARK_ALE,
    BELGIAN_STRONG_PALE_ALE,
    DUBBEL,
    TRIPEL,
    QUADRUPEL_QUAD,
    SAISON_FARMHOUSE_ALE,
    BIERE_DE_GARDE,
    GRISSETTE,

    // =======================
    // STOUT & PORTER
    // =======================
    AMERICAN_STOUT,
    AMERICAN_DOUBLE_IMPERIAL_STOUT,
    RUSSIAN_IMPERIAL_STOUT,
    IRISH_DRY_STOUT,
    ENGLISH_STOUT,
    FOREIGN_EXPORT_STOUT,
    MILK_SWEET_STOUT,
    OATMEAL_STOUT,
    AMERICAN_PORTER,
    BALTIC_PORTER,

    // =======================
    // WHEAT BEER
    // =======================
    HEFEWEIZEN,
    DUNKELWEIZEN,
    KRISTALWEIZEN,
    WHEAT_ALE,
    AMERICAN_PALE_WHEAT_ALE,
    AMERICAN_DARK_WHEAT_ALE,
    WITBIER,
    BERLINER_WEISSBIER,
    GOSE,
    ROGGENBIER,

    // =======================
    // LAGER
    // =======================
    AMERICAN_ADJUNCT_LAGER,
    AMERICAN_PALE_LAGER,
    AMERICAN_AMBER_RED_LAGER,
    AMERICAN_PILSNER,
    AMERICAN_DOUBLE_IMPERIAL_PILSNER,
    AMERICAN_INDIA_PALE_LAGER,
    EURO_PALE_LAGER,
    EURO_DARK_LAGER,
    LIGHT_LAGER,
    MUNICH_HELLES_LAGER,
    MUNICH_DUNKEL_LAGER,
    VIENNA_LAGER,
    DORTMUNDER_EXPORT_LAGER,
    CZECH_PILSENER,
    GERMAN_PILSENER,
    MARZEN_OKTOBERFEST,
    SCHWARZBIER,
    KELLER_BIER_ZWICKEL_BIER,
    RAUCHBIER,
    BOCK,
    MAIBOCK_HELLES_BOCK,
    DOPPELBOCK,

    // =======================
    // SOUR / WILD
    // =======================
    AMERICAN_WILD_ALE,
    FLANDERS_RED_ALE,
    FLANDERS_OUD_BRUIN,

    // =======================
    // SPECIALTY / OTHER
    // =======================
    CALIFORNIA_COMMON_STEAM_BEER,
    CREAM_ALE,
    ALTBIER,
    KOLSCH,
    RYE_BEER,
    FRUIT_VEGETABLE_BEER,
    HERBED_SPICED_BEER,
    CHILE_BEER,
    PUMPKIN_ALE,
    SMOKED_BEER,
    LOW_ALCOHOL_BEER,
    OTHER,

    // =======================
    // NON-BEER
    // =======================
    CIDER,
    MEAD,
    RADLER,
    SHANDY,

    // =======================
    // DEFENSIVE FALLBACK
    // =======================
    UNKNOWN;

    // =======================
    // CSV → ENUM MAPPING
    // =======================
    private static final Map<String, BeerStyle> CSV_MAP = new HashMap<>();

    static {
        CSV_MAP.put("Abbey Single Ale", BELGIAN_PALE_ALE);
        CSV_MAP.put("Altbier", ALTBIER);
        CSV_MAP.put("American Adjunct Lager", AMERICAN_ADJUNCT_LAGER);
        CSV_MAP.put("American Amber / Red Ale", AMERICAN_AMBER_RED_ALE);
        CSV_MAP.put("American Amber / Red Lager", AMERICAN_AMBER_RED_LAGER);
        CSV_MAP.put("American Barleywine", AMERICAN_BARLEYWINE);
        CSV_MAP.put("American Black Ale", AMERICAN_BLACK_ALE);
        CSV_MAP.put("American Blonde Ale", AMERICAN_BLONDE_ALE);
        CSV_MAP.put("American Brown Ale", AMERICAN_BROWN_ALE);
        CSV_MAP.put("American Dark Wheat Ale", AMERICAN_DARK_WHEAT_ALE);
        CSV_MAP.put("American Double / Imperial IPA", AMERICAN_DOUBLE_IMPERIAL_IPA);
        CSV_MAP.put("American Double / Imperial Pilsner", AMERICAN_DOUBLE_IMPERIAL_PILSNER);
        CSV_MAP.put("American Double / Imperial Stout", AMERICAN_DOUBLE_IMPERIAL_STOUT);
        CSV_MAP.put("American IPA", AMERICAN_IPA);
        CSV_MAP.put("American India Pale Lager", AMERICAN_INDIA_PALE_LAGER);
        CSV_MAP.put("American Malt Liquor", AMERICAN_STRONG_ALE);
        CSV_MAP.put("American Pale Ale (APA)", AMERICAN_PALE_ALE_APA);
        CSV_MAP.put("American Pale Lager", AMERICAN_PALE_LAGER);
        CSV_MAP.put("American Pale Wheat Ale", AMERICAN_PALE_WHEAT_ALE);
        CSV_MAP.put("American Pilsner", AMERICAN_PILSNER);
        CSV_MAP.put("American Porter", AMERICAN_PORTER);
        CSV_MAP.put("American Stout", AMERICAN_STOUT);
        CSV_MAP.put("American Strong Ale", AMERICAN_STRONG_ALE);
        CSV_MAP.put("American White IPA", AMERICAN_WHITE_IPA);
        CSV_MAP.put("American Wild Ale", AMERICAN_WILD_ALE);
        CSV_MAP.put("Baltic Porter", BALTIC_PORTER);
        CSV_MAP.put("Belgian Dark Ale", BELGIAN_DARK_ALE);
        CSV_MAP.put("Belgian IPA", BELGIAN_IPA);
        CSV_MAP.put("Belgian Pale Ale", BELGIAN_PALE_ALE);
        CSV_MAP.put("Belgian Strong Dark Ale", BELGIAN_STRONG_DARK_ALE);
        CSV_MAP.put("Belgian Strong Pale Ale", BELGIAN_STRONG_PALE_ALE);
        CSV_MAP.put("Berliner Weissbier", BERLINER_WEISSBIER);
        CSV_MAP.put("Bière de Garde", BIERE_DE_GARDE);
        CSV_MAP.put("Bock", BOCK);
        CSV_MAP.put("Braggot", OTHER);
        CSV_MAP.put("California Common / Steam Beer", CALIFORNIA_COMMON_STEAM_BEER);
        CSV_MAP.put("Chile Beer", CHILE_BEER);
        CSV_MAP.put("Cider", CIDER);
        CSV_MAP.put("Cream Ale", CREAM_ALE);
        CSV_MAP.put("Czech Pilsener", CZECH_PILSENER);
        CSV_MAP.put("Doppelbock", DOPPELBOCK);
        CSV_MAP.put("Dortmunder / Export Lager", DORTMUNDER_EXPORT_LAGER);
        CSV_MAP.put("Dubbel", DUBBEL);
        CSV_MAP.put("Dunkelweizen", DUNKELWEIZEN);
        CSV_MAP.put("English Barleywine", ENGLISH_BARLEYWINE);
        CSV_MAP.put("English Bitter", ENGLISH_PALE_ALE);
        CSV_MAP.put("English Brown Ale", ENGLISH_BROWN_ALE);
        CSV_MAP.put("English India Pale Ale (IPA)", ENGLISH_INDIA_PALE_ALE_IPA);
        CSV_MAP.put("English Pale Ale", ENGLISH_PALE_ALE);
        CSV_MAP.put("English Stout", ENGLISH_STOUT);
        CSV_MAP.put("English Strong Ale", ENGLISH_STRONG_ALE);
        CSV_MAP.put("Euro Dark Lager", EURO_DARK_LAGER);
        CSV_MAP.put("Euro Pale Lager", EURO_PALE_LAGER);
        CSV_MAP.put("Extra Special / Strong Bitter (ESB)", ENGLISH_STRONG_ALE);
        CSV_MAP.put("Flanders Oud Bruin", FLANDERS_OUD_BRUIN);
        CSV_MAP.put("Flanders Red Ale", FLANDERS_RED_ALE);
        CSV_MAP.put("Foreign / Export Stout", FOREIGN_EXPORT_STOUT);
        CSV_MAP.put("Fruit / Vegetable Beer", FRUIT_VEGETABLE_BEER);
        CSV_MAP.put("German Pilsener", GERMAN_PILSENER);
        CSV_MAP.put("Gose", GOSE);
        CSV_MAP.put("Grisette", GRISSETTE);
        CSV_MAP.put("Hefeweizen", HEFEWEIZEN);
        CSV_MAP.put("Herbed / Spiced Beer", HERBED_SPICED_BEER);
        CSV_MAP.put("Irish Dry Stout", IRISH_DRY_STOUT);
        CSV_MAP.put("Irish Red Ale", IRISH_RED_ALE);
        CSV_MAP.put("Keller Bier / Zwickel Bier", KELLER_BIER_ZWICKEL_BIER);
        CSV_MAP.put("Kristalweizen", KRISTALWEIZEN);
        CSV_MAP.put("Kölsch", KOLSCH);
        CSV_MAP.put("Light Lager", LIGHT_LAGER);
        CSV_MAP.put("Low Alcohol Beer", LOW_ALCOHOL_BEER);
        CSV_MAP.put("Maibock / Helles Bock", MAIBOCK_HELLES_BOCK);
        CSV_MAP.put("Mead", MEAD);
        CSV_MAP.put("Milk / Sweet Stout", MILK_SWEET_STOUT);
        CSV_MAP.put("Munich Dunkel Lager", MUNICH_DUNKEL_LAGER);
        CSV_MAP.put("Munich Helles Lager", MUNICH_HELLES_LAGER);
        CSV_MAP.put("Märzen / Oktoberfest", MARZEN_OKTOBERFEST);
        CSV_MAP.put("Oatmeal Stout", OATMEAL_STOUT);
        CSV_MAP.put("Other", OTHER);
        CSV_MAP.put("Pumpkin Ale", PUMPKIN_ALE);
        CSV_MAP.put("Quadrupel (Quad)", QUADRUPEL_QUAD);
        CSV_MAP.put("Radler", RADLER);
        CSV_MAP.put("Rauchbier", RAUCHBIER);
        CSV_MAP.put("Roggenbier", ROGGENBIER);
        CSV_MAP.put("Russian Imperial Stout", RUSSIAN_IMPERIAL_STOUT);
        CSV_MAP.put("Rye Beer", RYE_BEER);
        CSV_MAP.put("Saison / Farmhouse Ale", SAISON_FARMHOUSE_ALE);
        CSV_MAP.put("Schwarzbier", SCHWARZBIER);
        CSV_MAP.put("Scotch Ale / Wee Heavy", SCOTCH_ALE);
        CSV_MAP.put("Scottish Ale", SCOTTISH_ALE);
        CSV_MAP.put("Shandy", SHANDY);
        CSV_MAP.put("Smoked Beer", SMOKED_BEER);
        CSV_MAP.put("Tripel", TRIPEL);
        CSV_MAP.put("Vienna Lager", VIENNA_LAGER);
        CSV_MAP.put("Wheat Ale", WHEAT_ALE);
        CSV_MAP.put("Winter Warmer", WINTER_WARMER);
        CSV_MAP.put("Witbier", WITBIER);
    }

    /**
     * Safe CSV → enum conversion.
     * Never throws, never returns null.
     */
    public static BeerStyle fromCsv(String csvStyle) {

        return CSV_MAP.getOrDefault(csvStyle, UNKNOWN);
    }
}
