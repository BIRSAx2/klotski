package dev.plagarizers.klotski.gui.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.HashMap;

public class FontGenerator {

    private static FontGenerator instance = null;
    private final HashMap<FontType, BitmapFont> fonts;
    private final HashMap<LabelStyleType, Label.LabelStyle> labelStyles;

    private FontGenerator() {
        fonts = new HashMap<>();
        labelStyles = new HashMap<>();
        setupFonts();
        setupLabelStyles();
    }


    public static FontGenerator getInstance() {
        if (instance == null) instance = new FontGenerator();
        return instance;
    }

    private void setupLabelStyles() {
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = fonts.get(FontType.Title);
        titleStyle.fontColor = Color.GOLD;
        labelStyles.put(LabelStyleType.TitleStyle, titleStyle);

        Label.LabelStyle buttonStyle = new Label.LabelStyle();
        buttonStyle.font = fonts.get(FontType.Button);
        buttonStyle.fontColor = Color.WHITE;
        labelStyles.put(LabelStyleType.ButtonStyle, buttonStyle);

        Label.LabelStyle menuStyle = new Label.LabelStyle();
        menuStyle.font = fonts.get(FontType.Menu);
        menuStyle.fontColor = Color.GOLD;
        labelStyles.put(LabelStyleType.MenuStyle, menuStyle);

        Label.LabelStyle infoStyle = new Label.LabelStyle();
        infoStyle.font = fonts.get(FontType.Info);
        infoStyle.fontColor = Color.WHITE;
        labelStyles.put(LabelStyleType.InfoStyle, infoStyle);

        Label.LabelStyle alertStyle = new Label.LabelStyle();
        alertStyle.font = fonts.get(FontType.Info);
        alertStyle.fontColor = Color.RED;
        labelStyles.put(LabelStyleType.AlertStyle, alertStyle);
    }

    private void setupFonts() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/monogram.ttf"));


        FreeTypeFontGenerator.FreeTypeFontParameter tileFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        tileFontParameter.size = 50;
        tileFontParameter.shadowOffsetX = 3;
        tileFontParameter.shadowOffsetY = 3;

        fonts.put(FontType.Title, fontGenerator.generateFont(tileFontParameter));


        FreeTypeFontGenerator.FreeTypeFontParameter buttonFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonFontParameter.size = 30;
        fonts.put(FontType.Button, fontGenerator.generateFont(buttonFontParameter));


        FreeTypeFontGenerator.FreeTypeFontParameter menuFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        menuFontParameter.size = 40;
        menuFontParameter.shadowOffsetX = 3;
        menuFontParameter.shadowOffsetY = 3;
        fonts.put(FontType.Menu, fontGenerator.generateFont(menuFontParameter));

        FreeTypeFontGenerator.FreeTypeFontParameter infoFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        infoFontParameter.size = 35;
        fonts.put(FontType.Info, fontGenerator.generateFont(infoFontParameter));
    }

    public BitmapFont getFont(FontType fontType) {
        return fonts.get(fontType);
    }

    public Label.LabelStyle getLabelStyle(LabelStyleType labelStyleType) {
        return labelStyles.get(labelStyleType);
    }

    public static enum FontType {
        Title, Button, Info, Menu, Alert
    }

    public static enum LabelStyleType {
        TitleStyle, ButtonStyle, MenuStyle, InfoStyle, AlertStyle
    }

}
