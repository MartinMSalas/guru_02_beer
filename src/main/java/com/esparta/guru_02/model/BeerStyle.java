package com.esparta.guru_02.model;

import java.util.Locale;

/*
 * Author: M
 * Date: 24-Jan-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public enum BeerStyle {

    // =========================
    // ALE FAMILY
    // =========================
    AMBER_ALE,
    AMERICAN_PALE_ALE,
    AMERICAN_STRONG_ALE,
    BARLEYWINE,
    BELGIAN_PALE_ALE,
    BELGIAN_STRONG_ALE,
    BITTER,
    BROWN_ALE,
    CREAM_ALE,
    EXTRA_SPECIAL_BITTER,
    PALE_ALE,
    SCOTCH_ALE,
    STRONG_ALE,

    // =========================
    // IPA
    // =========================
    IPA,
    AMERICAN_IPA,
    DOUBLE_IPA,
    IMPERIAL_IPA,
    BLACK_IPA,
    SESSION_IPA,

    // =========================
    // LAGER
    // =========================
    LAGER,
    AMERICAN_LAGER,
    AMERICAN_LIGHT_LAGER,
    AMERICAN_AMBER_LAGER,
    AMERICAN_DARK_LAGER,
    VIENNA_LAGER,
    OKTOBERFEST,
    MARZEN,
    HELLES,
    DUNKEL,
    SCHWARZBIER,

    // =========================
    // PILSNER
    // =========================
    PILSNER,
    CZECH_PILSNER,
    GERMAN_PILSNER,

    // =========================
    // STOUT
    // =========================
    STOUT,
    AMERICAN_STOUT,
    DRY_STOUT,
    SWEET_STOUT,
    OATMEAL_STOUT,
    FOREIGN_EXTRA_STOUT,
    IMPERIAL_STOUT,

    // =========================
    // PORTER
    // =========================
    PORTER,
    AMERICAN_PORTER,
    BALTIC_PORTER,
    ROBUST_PORTER,

    // =========================
    // WHEAT / BELGIAN
    // =========================
    WHEAT,
    AMERICAN_WHEAT,
    HEFEWEIZEN,
    DUNKELWEIZEN,
    WITBIER,
    BELGIAN_WHITE,
    SAISON,
    FARMHOUSE_ALE,
    BIERE_DE_GARDE,

    // =========================
    // SOUR / WILD
    // =========================
    GOSE,
    BERLINER_WEISSE,
    LAMBIC,
    GUEUZE,
    FRUIT_BEER,
    SOUR_ALE,

    // =========================
    // SPECIALTY / OTHER
    // =========================
    KOLSCH,
    ALTBIER,
    SMOKED_BEER,
    HERBED_BEER,
    SPICE_BEER,
    PUMPKIN_ALE,
    CHILE_BEER,
    RYE_BEER,
    SAHTI,

    // =========================
    // FALLBACK
    // =========================
    UNKNOWN;

    /**
     * Converts CSV style strings into enum values safely.
     * This is used by OpenCSV and MapStruct helpers.
     */
    public static BeerStyle fromCsv(String raw) {
        if (raw == null || raw.isBlank()) {
            return UNKNOWN;
        }

        String normalized = raw.trim()
                .toUpperCase(Locale.ROOT)
                .replace("&", "AND")
                .replace("/", "_")
                .replace("-", "_")
                .replace(" ", "_")
                .replaceAll("[^A-Z_]", "");

        try {
            return BeerStyle.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            return UNKNOWN;
        }
    }
}