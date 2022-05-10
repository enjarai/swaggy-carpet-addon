package nl.enjarai.swaggycarpet;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.FEATURE;
import static carpet.settings.RuleCategory.SURVIVAL;

public class SwaggySettings {
    @Rule(
            desc = "Lets dispensers pick up fish using water buckets.",
            category = {"swaggy", SURVIVAL, FEATURE}
    )
    public static boolean dispensersPickUpFish = false;

    @Rule(
            desc = "Lets dispensers pick up fish and water at the same time using an empty bucket.",
            category = {"swaggy", SURVIVAL, FEATURE}
    )
    public static boolean dispensersPickUpBothFishAndWater = false;
}
