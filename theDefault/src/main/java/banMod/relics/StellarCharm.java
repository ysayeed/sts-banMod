package banMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import banMod.DefaultMod;
import banMod.util.TextureLoader;

import static banMod.DefaultMod.makeRelicOutlinePath;
import static banMod.DefaultMod.makeRelicPath;

public class StellarCharm extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Upon pickup, ban one curse.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("StellarCharm");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

    public StellarCharm() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
