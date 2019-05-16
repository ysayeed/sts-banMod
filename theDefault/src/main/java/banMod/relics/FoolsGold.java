package banMod.relics;

import banMod.util.Ban;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import banMod.DefaultMod;
import banMod.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static banMod.DefaultMod.makeRelicOutlinePath;
import static banMod.DefaultMod.makeRelicPath;

public class FoolsGold extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Obtain a selection of rare cards. If you choose one, ban the unchosen ones.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("FoolsGold");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

    private CardGroup rewards = null;

    public FoolsGold() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        ArrayList<AbstractCard> cardCounter = AbstractDungeon.getRewardCards();
        this.rewards  = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (int i=0; i<Math.min(cardCounter.size(), AbstractDungeon.rareCardPool.size()); ++i){
            this.rewards.addToTop(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE));
            while (this.rewards.group.subList(0, i).contains(this.rewards.group.get(i))){
                this.rewards.group.set(i, AbstractDungeon.getCard(AbstractCard.CardRarity.RARE)); //keep trying until it succeeds
            }
        }
        AbstractDungeon.gridSelectScreen.open(rewards, 1,"", false, false, true, false);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
    }

    @Override
    public void update() {
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 1){
            AbstractCard keep = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            for (AbstractCard c: this.rewards.group){
                if (c.cardID != keep.cardID){
                    Ban.banCard(c);
                }
            }
            AbstractDungeon.player.masterDeck.addToTop(keep);
        }
    }
}
