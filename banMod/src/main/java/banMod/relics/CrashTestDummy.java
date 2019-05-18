package banMod.relics;

import banMod.cards.BanEliteCard;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import banMod.BanMod;
import banMod.util.TextureLoader;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


import static banMod.BanMod.makeRelicOutlinePath;
import static banMod.BanMod.makeRelicPath;

public class CrashTestDummy extends CustomRelic implements CustomSavable<String> {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Pick an Elite. The next time you would encounter that Elite, generate a different random elite instead.
     */

    // ID, images, text.
    public static final String ID = BanMod.makeID("CrashTestDummy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png")); //change
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png")); //change

    private String eliteName = null;

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
        this.counter = 1;
        if (eliteName==null) {
            CardGroup elites = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            //manually add in default Elites, and use BaseMod to pick up mod-added elites
            elites.addToTop(new BanEliteCard("Gremlin Nob"));
            elites.addToTop(new BanEliteCard("Lagavulin"));
            elites.addToTop(new BanEliteCard("3 Sentries"));
            elites.addToTop(new BanEliteCard("Gremlin Leader"));
            elites.addToTop(new BanEliteCard("Slavers"));
            elites.addToTop(new BanEliteCard("Book of Stabbing"));
            elites.addToTop(new BanEliteCard("Giant Head"));
            elites.addToTop(new BanEliteCard("Nemesis"));
            elites.addToTop(new BanEliteCard("Reptomancer"));
            elites.addToTop(new BanEliteCard("Shield and Spear"));

            for (MonsterInfo m : BaseMod.getEliteEncounters("Exordium")){
                elites.addToTop(new BanEliteCard(m.name));
            }
            for (MonsterInfo m : BaseMod.getEliteEncounters("TheCity")){
                elites.addToTop(new BanEliteCard(m.name));
            }
            for (MonsterInfo m : BaseMod.getEliteEncounters("TheBeyond")){
                elites.addToTop(new BanEliteCard(m.name));
            }
            for (MonsterInfo m : BaseMod.getEliteEncounters("TheEnding")){
                elites.addToTop(new BanEliteCard(m.name));
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
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom().eliteTrigger && this.counter > 0) { //add in a check for the elite
            this.flash();
            this.counter--;
        }
    }

    @Override
    public String onSave(){
        return this.eliteName;
    }

    @Override
    public void onLoad(String s){
        this.eliteName = s;
    }
}
