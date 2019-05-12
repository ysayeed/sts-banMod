package banMod.relics;

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
     * Obtain a selection of a rare card. If you choose one, ban the unchosen ones.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("FoolsGold");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

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
        CardGroup rewards  = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        for (int i=0; i<Math.min(cardCounter.size(), AbstractDungeon.rareCardPool.size()); ++i){
            rewards.addToTop(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE));
            while (rewards.group.subList(0, i).contains(rewards.group.get(i))){
                rewards.group.set(i, AbstractDungeon.getCard(AbstractCard.CardRarity.RARE)); //keep trying until it succeeds
            }
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
    }
}
