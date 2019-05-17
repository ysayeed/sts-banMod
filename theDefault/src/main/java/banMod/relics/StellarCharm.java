package banMod.relics;

import banMod.BanMod;
import banMod.util.Ban;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import banMod.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;


import java.util.ArrayList;


import static banMod.BanMod.makeRelicOutlinePath;
import static banMod.BanMod.makeRelicPath;

public class StellarCharm extends CustomRelic implements CustomSavable<String> {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Upon pickup, ban and remove all copies of one curse from your deck.
     */

    // ID, images, text.
    public static final String ID = BanMod.makeID("StellarCharm");
    private String curseID = null;

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
        if (this.curseID == null && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            this.curseID = card.cardID;
            Ban.banCard(card);

            for (AbstractCard c: AbstractDungeon.player.masterDeck.group){
                if (c.cardID == this.curseID){
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
            if (c.cardID == this.curseID){
                toRemove.add(c);
            }
        }

        for (AbstractCard c: toRemove){
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        }
    }
    @Override
    public String onSave(){
        return this.curseID;
    }
    @Override
    public void onLoad(String s){
        this.curseID = s;
    }
}
