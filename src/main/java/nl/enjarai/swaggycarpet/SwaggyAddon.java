package nl.enjarai.swaggycarpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;

public class SwaggyAddon implements CarpetExtension {
    public static void noop() { }

    static
    {
        CarpetServer.manageExtension(new SwaggyAddon());
    }

    @Override
    public void onGameStarted()
    {
        CarpetServer.settingsManager.parseSettingsClass(SwaggySettings.class);
    }
}
