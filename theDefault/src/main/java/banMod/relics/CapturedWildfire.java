package banMod.relics;

import banMod.util.Ban;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import banMod.BanMod;
import banMod.util.TextureLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

import static banMod.BanMod.makeRelicOutlinePath;
import static banMod.BanMod.makeRelicPath;

public class CapturedWildfire extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy. Upon pickup, remove and ban all copies of 2 random cards from your deck. Prioritizes upgraded cards.
     */

    // ID, images, text.
    public static final String ID = BanMod.makeID("CapturedWildfire");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic3.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic3.png")); //change

    public CapturedWildfire() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
        CardGroup upgraded = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        CardGroup unupgraded = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        ArrayList<String> toBan = new ArrayList<>();
        for (AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if (c.upgraded){
                upgraded.addToRandomSpot(c);
            } else {
                unupgraded.addToRandomSpot(c);
            }
        }
        for (AbstractCard c: upgraded.group){
            if (toBan.size()<2 && !toBan.contains(c.cardID)){
                toBan.add(c.cardID);
            }
        }
        for (AbstractCard c : unupgraded.group) {
            if (toBan.size() < 2 && !toBan.contains(c.cardID)) {
                toBan.add(c.cardID);
            }
        }
        Iterator deckIterator = AbstractDungeon.player.masterDeck.group.iterator();

        while(deckIterator.hasNext()) {
            AbstractCard card = (AbstractCard)deckIterator.next();
            if (toBan.contains(card.cardID)){
                Ban.banCard(card);
                AbstractDungeon.player.masterDeck.removeCard(card);
            }
        }
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
