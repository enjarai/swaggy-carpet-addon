package net.fabricmc.example;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.FEATURE;
import static carpet.settings.RuleCategory.SURVIVAL;

public class SwaggySettings {
    @Rule(
            desc = "Lets dispensers pick up fish using water buckets.",
            category = {"swaggy", SURVIVAL, FEATURE}
    )
    public static boolean dispensersPickUpFish = false;
}
