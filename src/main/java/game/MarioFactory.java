package game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MarioFactory implements EntityFactory {
    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MarioType.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(MarioType.DOOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }


    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        int width = data.get("width");
        return FXGL.entityBuilder(data)
                .type(MarioType.COIN)
                .view(new Circle(width/2, Color.GOLD))
                .bbox(new HitBox(new Point2D(0,0), BoundingShape.circle(width/2)))
                .with(new CollidableComponent(true))
                .build();
    }


    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        // physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(16, 38), BoundingShape.box(6, 8)));

        return FXGL.entityBuilder(data)
                .type(MarioType.PLAYER)
                .view(new Rectangle(30, 30, Color.BLUE))
                .bbox(new HitBox(new Point2D(0,0), BoundingShape.box(30, 30)))
                .with(physics)
                .with(new PlayerComponent())
                .with(new CollidableComponent(true))
                .build();
    }
}
