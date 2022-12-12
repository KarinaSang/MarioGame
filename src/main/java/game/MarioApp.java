package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.handlers.CollectibleHandler;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MarioApp extends GameApplication {
    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(30 * 32);
        settings.setHeight(20 * 32);
        settings.setTitle("Mario Game");
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("jump") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initGameVars(Map<String, Object> context) {
        getGameWorld().addEntityFactory(new MarioFactory());
    }

    @Override
    protected void initGame() {
        FXGL.setLevelFromMap("marioMap.tmx");
        player = getGameWorld().spawn("player", 50, 50);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER, MarioType.COIN) {
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                System.out.println("Coin");
                coin.removeFromWorld();
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(MarioType.PLAYER, MarioType.DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity door) {
//                System.out.println("Level Completed!");
                getDialogService().showMessageBox("Level Completed!", () -> {
                    System.out.println("Dialog closed");
                });
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
