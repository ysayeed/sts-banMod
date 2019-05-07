package banMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import banMod.DefaultMod;
import banMod.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;


import java.util.ArrayList;


import static banMod.DefaultMod.makeRelicOutlinePath;
import static banMod.DefaultMod.makeRelicPath;

public class StellarCharm extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Upon pickup, ban and remove all copies of one curse from your deck.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("StellarCharm");
    private String curseName = "";
    private boolean cardSelected = true;

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

    public StellarCharm() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip(){
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.curseCardPool, 1,"", false, false, false, true);
    }
    @Override
    public void update(){
        ArrayList<AbstractCard> toRemove = new ArrayList<>();
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            this.cardSelected = true;
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            this.curseName = AbstractDungeon.gridSelectScreen.selectedCards.get(0).name;

            for (AbstractCard c: AbstractDungeon.player.masterDeck.group){
                if (c.name == this.curseName){
                    toRemove.add(c);
                }
            }

            for (AbstractCard c: toRemove){
                AbstractDungeon.player.masterDeck.removeCard(c);
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }
    @Override
    public void onMasterDeckChange(){ //shortcut that will interact poorly with Omamori. Fix with spirepatch later instead.
        ArrayList<AbstractCard> toRemove = new ArrayList<>();
        for (AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if (c.name == this.curseName){
                toRemove.add(c);
            }
        }

        for (AbstractCard c: toRemove){
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }
}
