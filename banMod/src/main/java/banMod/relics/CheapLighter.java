package banMod.relics;

import banMod.BanMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import banMod.util.TextureLoader;

import static banMod.BanMod.makeRelicOutlinePath;
import static banMod.BanMod.makeRelicPath;

public class CheapLighter extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At campfires, you may ban a card as a free action.
     */

    // ID, images, text.
    public static final String ID = BanMod.makeID("CheapLighter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

    public CheapLighter() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onRest(){
        //TODO
    }

}
