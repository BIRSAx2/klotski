package dev.plagarizers.klotski.gui.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.HashMap;
import java.util.Map;

/**
 * The FontGenerator class is responsible for generating and managing fonts and label styles.
 * It follows the Singleton design pattern to ensure only one instance is created.
 */
public class FontHandler {
    private static FontHandler instance = null;
    private final HashMap<FontType, BitmapFont> fonts;
    private final HashMap<LabelStyleType, Label.LabelStyle> labelStyles;
    private String fontPath;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the fonts and label styles HashMaps and sets up the fonts and label styles.
     */
    private FontHandler() {
        fontPath = "fonts/monogram.ttf";
        fonts = new HashMap<>();
        labelStyles = new HashMap<>();
        setupFonts();
        setupLabelStyles();
    }

    /**
     * Retrieves the singleton instance of the FontGenerator class.
     * If the instance is null, creates a new instance.
     *
     * @return The FontGenerator instance.
     */
    public static FontHandler getInstance() {
        if (instance == null) instance = new FontHandler();
        return instance;
    }

    /**
     * Sets up the different label styles with associated fonts and colors.
     * Creates and configures label styles for titles, buttons, menus, information, and alerts.
     */
    private void setupLabelStyles() {
        // Title style
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = fonts.get(FontType.Title);
        titleStyle.fontColor = Color.GOLD;
        labelStyles.put(LabelStyleType.TitleStyle, titleStyle);

        // Button style
        Label.LabelStyle buttonStyle = new Label.LabelStyle();
        buttonStyle.font = fonts.get(FontType.Button);
        buttonStyle.fontColor = Color.WHITE;
        labelStyles.put(LabelStyleType.ButtonStyle, buttonStyle);

        // Menu style
        Label.LabelStyle menuStyle = new Label.LabelStyle();
        menuStyle.font = fonts.get(FontType.Menu);
        menuStyle.fontColor = Color.GOLD;
        labelStyles.put(LabelStyleType.MenuStyle, menuStyle);

        // Info style
        Label.LabelStyle infoStyle = new Label.LabelStyle();
        infoStyle.font = fonts.get(FontType.Info);
        infoStyle.fontColor = Color.WHITE;
        labelStyles.put(LabelStyleType.InfoStyle, infoStyle);

        // Alert style
        Label.LabelStyle alertStyle = new Label.LabelStyle();
        alertStyle.font = fonts.get(FontType.Info);
        alertStyle.fontColor = Color.RED;
        labelStyles.put(LabelStyleType.AlertStyle, alertStyle);
    }

    /**
     * Sets up the different fonts with their respective parameters.
     * Generates fonts for titles, buttons, menus, and information labels using a FreeTypeFontGenerator.
     */
    private void setupFonts() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));

        // Title font
        FreeTypeFontGenerator.FreeTypeFontParameter tileFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        tileFontParameter.size = 50;
        tileFontParameter.shadowOffsetX = 3;
        tileFontParameter.shadowOffsetY = 3;
        fonts.put(FontType.Title, fontGenerator.generateFont(tileFontParameter));

        // Button font
        FreeTypeFontGenerator.FreeTypeFontParameter buttonFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        buttonFontParameter.size = 30;
        fonts.put(FontType.Button, fontGenerator.generateFont(buttonFontParameter));

        // Menu font
        FreeTypeFontGenerator.FreeTypeFontParameter menuFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        menuFontParameter.size = 40;
        menuFontParameter.shadowOffsetX = 3;
        menuFontParameter.shadowOffsetY = 3;
        fonts.put(FontType.Menu, fontGenerator.generateFont(menuFontParameter));

        // Info font
        FreeTypeFontGenerator.FreeTypeFontParameter infoFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        infoFontParameter.size = 35;
        fonts.put(FontType.Info, fontGenerator.generateFont(infoFontParameter));
        fontGenerator.dispose();
    }

    /**
     * Retrieves the specified font based on the provided font type.
     *
     * @param fontType The type of the font.
     * @return The corresponding BitmapFont object.
     */
    public BitmapFont getFont(FontType fontType) {
        return fonts.get(fontType);
    }

    /**
     * Retrieves the specified label style based on the provided label style type.
     *
     * @param labelStyleType The type of the label style.
     * @return The corresponding LabelStyle object.
     */
    public Label.LabelStyle getLabelStyle(LabelStyleType labelStyleType) {
        return labelStyles.get(labelStyleType);
    }

    public void dispose() {
        for(Map.Entry<FontType, BitmapFont> entry : fonts.entrySet()) {
            entry.getValue().dispose();
        }
    }

    /**
     * Enumerates the different font types available.
     */
    public static enum FontType {
        Title, Button, Info, Menu, Alert
    }

    /**
     * Enumerates the different label style types available.
     */
    public static enum LabelStyleType {
        TitleStyle, ButtonStyle, MenuStyle, InfoStyle, AlertStyle
    }
}
