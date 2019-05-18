package banMod.util;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.*;

public class Ban {
    public static void banCard(AbstractCard c){
        Exordium.removeCardFromPool(c.cardID, c.name, c.rarity, c.color);
        TheCity.removeCardFromPool(c.cardID, c.name, c.rarity, c.color);
        TheBeyond.removeCardFromPool(c.cardID, c.name, c.rarity, c.color);
        TheEnding.removeCardFromPool(c.cardID, c.name, c.rarity, c.color);
    }
    public static void unbanCard(){
        //TODO
    }
}
