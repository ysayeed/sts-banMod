package banMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import banMod.BanMod;
import banMod.util.TextureLoader;

import static banMod.BanMod.makeRelicOutlinePath;
import static banMod.BanMod.makeRelicPath;

public class RakkoonTail extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Pick one Elite. Pick one boss. You won't encounter either in the next act.
     */

    // ID, images, text.
    public static final String ID = BanMod.makeID("RakkoonTail");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

    public RakkoonTail() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
