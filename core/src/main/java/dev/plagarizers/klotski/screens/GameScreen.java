package dev.plagarizers.klotski.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.plagarizers.klotski.KlotskiGame;
import dev.plagarizers.klotski.actors.BoardWidget;
import dev.plagarizers.klotski.game.state.State;
import dev.plagarizers.klotski.game.util.SavesManager;

public class GameScreen implements Screen {

  private Stage stage;
  private State state;

  private BoardWidget grid;
  private OrthographicCamera cam;
  private Skin skin;

  private Table table;
  private ImageButton backButton;
  private ImageButton nextMoveButton;
  private ImageButton saveButton;

  private Texture background;

  private KlotskiGame game;

  private SpriteBatch spriteBatch = new SpriteBatch();

  private SavesManager savesManager = new SavesManager();


  private int numberOfMoves = 0;
  private Label numberOfMovesLabel;


  public GameScreen(KlotskiGame game, State state) {

    this.game = game;
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();


    cam = new OrthographicCamera(screenWidth, screenHeight);
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();

    stage = new Stage(new FitViewport(screenWidth, screenHeight, cam));

    if (state == null) this.state = State.fromRandomConfiguration();
    else this.state = state;


    skin = new Skin(Gdx.files.internal(game.getSkinPath()));


    grid = new BoardWidget(state, skin);

    // Create buttons
//    backButton = new TextButton("Back", skin);

    TextureRegionDrawable buttonBackground = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("textures/buttons/button.png"))));

    // Create the ImageButton style
    ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
    buttonStyle.up = buttonBackground;
    buttonStyle.down = buttonBackground.tint(Color.LIGHT_GRAY);

    // Create the ImageButton with text and style
    backButton = new ImageButton(buttonStyle);
    backButton.add(new Label("Back", skin)); // Add the button text label

    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        dispose();
        game.setScreen(new MainMenuScreen(game));
      }
    });

    nextMoveButton = new ImageButton(buttonStyle);
    nextMoveButton.add(new Label("Next Move", skin));

    // Add click listener to nextMoveButton
    nextMoveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        numberOfMoves++;
        grid.playBestMove();
      }
    });
    saveButton = new ImageButton(buttonStyle);
    saveButton.add(new Label("Save", skin));

    saveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        savesManager.saveState(grid.getState());
      }
    });

    // Create table
    table = new Table();
    table.setFillParent(true);
    numberOfMovesLabel = new Label("Number of moves: " + numberOfMoves, skin);
    table.add(numberOfMovesLabel).colspan(3).pad(10).row();
    // Add widgets to the table
    table.add(grid).expand().center().colspan(3).row();
    table.row();
    table.row();
    table.add(backButton).bottom().fillX().pad(10);
    table.add(nextMoveButton).bottom().fillX().pad(10);
    table.add(saveButton).bottom().fillX().pad(10);

    stage.addActor(table);

    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.valueOf("#72751B"));

    grid.handleInput();
    stage.act(delta / 60f);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    stage.getViewport().update(width, height);
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
    cam.update();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
    spriteBatch.dispose();
  }
}
