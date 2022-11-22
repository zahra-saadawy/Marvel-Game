package controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.world.Direction;

public class MoveButton extends StackPane {
	public MoveButton(Direction d) throws Exception {
		Circle bg = new Circle(50);
		bg.setOpacity(0);
		switch (d) {
		case UP:
			InputStream Up = Files.newInputStream(Paths.get("res/images/up.png"));
			Image up = new Image(Up);
			Up.close();
			ImageView UpView = new ImageView(up);
			UpView.setPreserveRatio(true);
			UpView.setSmooth(true);
			UpView.setCache(true);
			UpView.setFitHeight(100);
			UpView.setFitWidth(100);

			getChildren().addAll(UpView, bg);
			break;
		case DOWN:
			InputStream Down = Files.newInputStream(Paths.get("res/images/down.png"));
			Image down = new Image(Down);
			Down.close();
			ImageView DownView = new ImageView(down);
			DownView.setPreserveRatio(true);
			DownView.setSmooth(true);
			DownView.setCache(true);
			DownView.setFitHeight(100);
			DownView.setFitWidth(100);

			getChildren().addAll(DownView, bg);
			break;
		case RIGHT:
			InputStream Right = Files.newInputStream(Paths.get("res/images/right.png"));
			Image right = new Image(Right);
			Right.close();
			ImageView RightView = new ImageView(right);
			RightView.setPreserveRatio(true);
			RightView.setSmooth(true);
			RightView.setCache(true);
			RightView.setFitHeight(100);
			RightView.setFitWidth(100);
			getChildren().addAll(RightView, bg);
			break;
		case LEFT:
			InputStream Left = Files.newInputStream(Paths.get("res/images/left.png"));
			Image left = new Image(Left);
			Left.close();
			ImageView LeftView = new ImageView(left);
			LeftView.setPreserveRatio(true);
			LeftView.setSmooth(true);
			LeftView.setCache(true);
			LeftView.setFitHeight(100);
			LeftView.setFitWidth(100);
			getChildren().addAll(LeftView, bg);
			break;

		}
		setOnMouseEntered(e ->{
			bg.setOpacity(0.2);
		});
		setOnMouseExited(e -> {
			bg.setOpacity(0);
		});

	}

}
