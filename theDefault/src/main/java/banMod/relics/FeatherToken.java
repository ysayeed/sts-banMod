package banMod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import banMod.DefaultMod;
import banMod.util.TextureLoader;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Iterator;
import java.util.Map;

import static banMod.DefaultMod.makeRelicOutlinePath;
import static banMod.DefaultMod.makeRelicPath;

public class FeatherToken extends CustomRelic implements CustomSavable<String> {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Pick a status. Whenever you draw that card, exhaust it and draw a new one.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("FeatherToken");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

    private String statusID = null;

    public FeatherToken() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        if (statusID == null){
            return DESCRIPTIONS[0];
        } else {
            return DESCRIPTIONS[1] + statusID + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onEquip(){

        CardGroup statusPool  = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        Iterator i = CardLibrary.cards.entrySet().iterator();

        while(i.hasNext()) {
            Map.Entry<String, AbstractCard> c = (Map.Entry)i.next();
            AbstractCard card = c.getValue();
            if (card.type == AbstractCard.CardType.STATUS) {
                statusPool.addToTop(card);
            }
        }
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(statusPool, 1,"", false, false, false, true);
    }

    @Override
    public void update(){
        super.update();
        if (this.statusID == null && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {

            this.statusID = AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public void onCardDraw(AbstractCard c){
        if (c.cardID == this.statusID){
            flash();
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
        }
    }

    @Override
    public String onSave(){
        return this.statusID;
    }
    @Override
    public void onLoad(String s){
        this.statusID = s;
    }
}
