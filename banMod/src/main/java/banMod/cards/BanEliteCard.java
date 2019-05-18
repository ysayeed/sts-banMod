package banMod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BanEliteCard extends AbstractCard {
    public static final String ID = "halation:DiaryChoiceCard";
    public String s;

    public BanEliteCard(String s) {
        super(ID, s, null, 0, "", CardType.POWER, CardColor.COLORLESS,
                CardRarity.SPECIAL, CardTarget.NONE);
    }

    @Override
    public void upgrade(){ }

    @Override
    public AbstractCard makeCopy(){
        return new BanEliteCard(this.s);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m){ }


}