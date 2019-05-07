package banMod.relics;

import banMod.cards.BanEliteCard;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import banMod.DefaultMod;
import banMod.util.TextureLoader;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


import static banMod.DefaultMod.makeRelicOutlinePath;
import static banMod.DefaultMod.makeRelicPath;

public class CrashTestDummy extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Pick an Elite. The next time you would encounter that Elite, generate a different random elite instead.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("CrashTestDummy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

    private String eliteName = null;
    private int uses = 1;

    public CrashTestDummy() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip(){
        flash();
        if (eliteName==null) {
            CardGroup elites = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (String m : AbstractDungeon.eliteMonsterList){
                elites.addToTop(new BanEliteCard(m));
            }
            AbstractDungeon.gridSelectScreen.open(elites, 1, "", false);
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
    }

    @Override
    public void update(){
        super.update();
        if (eliteName == null && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()){
            eliteName = ((BanEliteCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0)).name;
        }
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom().eliteTrigger && this.uses > 0) { //add in a check for the elite
            this.flash();
            this.uses--;
        }
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }
}
