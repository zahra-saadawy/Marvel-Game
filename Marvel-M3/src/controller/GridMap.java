package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.text.Position;

import engine.Player;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;

public class GridMap extends GridPane {
	Object[][] map = Console.getBoard();
	Player player1 = Console.player1;
	Player player2 = Console.player2;

	public GridMap() throws Exception {
		updateBoard();
	}

	public void updateBoard() throws IOException {
		this.getChildren().clear();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Rectangle rect = new Rectangle(0, 0, 120, 140);
				rect.setStyle("-fx-stroke: white; -fx-stroke-width: 1;");

				add(rect, i, j);
			}
		}
		for (int i = 4; i >= 0; i--) {
			for (int j = 4; j >= 0; j--) {
				if (map[i][j] != null) {
					if (map[i][j] instanceof Cover) {
						InputStream CoverP = Files.newInputStream(Paths.get("res/images/Cover.png"));
						Image Cover = new Image(CoverP);
						CoverP.close();
						ImageView CoverView = new ImageView(Cover);
						CoverView.setFitHeight(140);
						CoverView.setFitWidth(120);
						add(CoverView, j, 4 - i);
						// HP BAR------------------------------------------
						double MaxHp = ((double) ((Cover) map[i][j]).getCurrentHP());
						double percentage = (double) (((double) ((Cover) map[i][j]).getCurrentHP()) / MaxHp);
						ProgressBar hp = new ProgressBar();
						hp.setStyle("-fx-accent: green;");
						hp.setProgress(percentage);
						hp.setPrefSize(100, 20);
						add(hp, j, 4 - i);
						hp.setTranslateY(50);
						hp.setTranslateX(10);
						int CurrentHp = ((Cover) map[i][j]).getCurrentHP();
						Text hptext = new Text("" + CurrentHp);
						hptext.setWrappingWidth(100);
						hptext.setTextAlignment(TextAlignment.CENTER);
						hptext.setFill(Color.BLACK);
						hptext.setTranslateY(50);
						hptext.setTranslateX(10);
						add(hptext, j, 4 - i);
					}
					if (map[i][j] instanceof Champion) {
						Rectangle rect = new Rectangle(0, 0, 120, 140);
						rect.setStyle("-fx-stroke: white; -fx-stroke-width: 1;");
						rect.setOpacity(0.4);
						if (player1.getTeam().contains(((Champion) map[i][j]))) {
							rect.setFill(Color.BLUE);
							add(rect, j, 4 - i);
						} else if (player2.getTeam().contains(((Champion) map[i][j]))) {
							rect.setFill(Color.PURPLE);
							add(rect, j, 4 - i);
						}
						Champion c = (Champion) map[i][j];
						String name = c.getName();
						switch (name) {
						case "Captain America":
							InputStream CaptainP = Files.newInputStream(Paths.get("res/images/CaptainAmerica.png"));
							Image Captain = new Image(CaptainP);
							CaptainP.close();
							ImageView CaptainView = new ImageView(Captain);
							CaptainView.setPreserveRatio(true);
							CaptainView.setSmooth(true);
							CaptainView.setCache(true);
							CaptainView.setFitHeight(140);
							CaptainView.setFitWidth(120);

							add(CaptainView, j, 4 - i);
							break;
						case "Deadpool":
							InputStream DeadpoolP = Files.newInputStream(Paths.get("res/images/DeadPool.png"));
							Image Deadpool = new Image(DeadpoolP);
							DeadpoolP.close();
							ImageView DeadpoolView = new ImageView(Deadpool);
							DeadpoolView.setPreserveRatio(true);
							DeadpoolView.setSmooth(true);
							DeadpoolView.setCache(true);
							DeadpoolView.setFitHeight(140);
							DeadpoolView.setFitWidth(120);
							add(DeadpoolView, j, 4 - i);
							break;
						case "Dr Strange":
							InputStream DrStrangeP = Files.newInputStream(Paths.get("res/images/DrStrange.png"));
							Image DrStrange = new Image(DrStrangeP);
							DrStrangeP.close();
							ImageView DrStrangeView = new ImageView(DrStrange);
							DrStrangeView.setFitHeight(140);
							DrStrangeView.setFitWidth(120);
							add(DrStrangeView, j, 4 - i);
							break;
						case "Electro":
							InputStream ElectroP = Files.newInputStream(Paths.get("res/images/Electro.png"));
							Image Electro = new Image(ElectroP);
							ElectroP.close();
							ImageView ElectroView = new ImageView(Electro);
							ElectroView.setFitHeight(140);
							ElectroView.setFitWidth(120);
							add(ElectroView, j, 4 - i);
							break;
						case "Ghost Rider":
							InputStream GhostRiderP = Files.newInputStream(Paths.get("res/images/GhostRider.png"));
							Image GhostRider = new Image(GhostRiderP);
							GhostRiderP.close();
							ImageView GhostRiderView = new ImageView(GhostRider);
							GhostRiderView.setFitHeight(140);
							GhostRiderView.setFitWidth(120);
							add(GhostRiderView, j, 4 - i);
							break;
						case "Hela":
							InputStream HelaP = Files.newInputStream(Paths.get("res/images/Hela.png"));
							Image Hela = new Image(HelaP);
							HelaP.close();
							ImageView HelaView = new ImageView(Hela);
							HelaView.setFitHeight(140);
							HelaView.setFitWidth(120);
							add(HelaView, j, 4 - i);
							break;
						case "Hulk":
							InputStream HulkP = Files.newInputStream(Paths.get("res/images/Hulk.png"));
							Image Hulk = new Image(HulkP);
							HulkP.close();
							ImageView HulkView = new ImageView(Hulk);
							HulkView.setFitHeight(140);
							HulkView.setFitWidth(120);
							add(HulkView, j, 4 - i);
							break;
						case "Iceman":
							InputStream IcemanP = Files.newInputStream(Paths.get("res/images/IceMan.png"));
							Image Iceman = new Image(IcemanP);
							IcemanP.close();
							ImageView IcemanView = new ImageView(Iceman);
							IcemanView.setFitHeight(140);
							IcemanView.setFitWidth(120);
							add(IcemanView, j, 4 - i);
							break;
						case "Ironman":
							InputStream IronmanP = Files.newInputStream(Paths.get("res/images/IronMan.png"));
							Image Ironman = new Image(IronmanP);
							IronmanP.close();
							ImageView IronmanView = new ImageView(Ironman);
							IronmanView.setFitHeight(140);
							IronmanView.setFitWidth(120);
							add(IronmanView, j, 4 - i);
							break;
						case "Loki":
							InputStream LokiP = Files.newInputStream(Paths.get("res/images/Loki.png"));
							Image Loki = new Image(LokiP);
							LokiP.close();
							ImageView LokiView = new ImageView(Loki);
							LokiView.setFitHeight(140);
							LokiView.setFitWidth(120);
							add(LokiView, j, 4 - i);
							break;
						case "Quicksilver":
							InputStream QuicksilverP = Files.newInputStream(Paths.get("res/images/QuickSilver.png"));
							Image Quicksilver = new Image(QuicksilverP);
							QuicksilverP.close();
							ImageView QuicksilverView = new ImageView(Quicksilver);
							QuicksilverView.setFitHeight(140);
							QuicksilverView.setFitWidth(120);
							add(QuicksilverView, j, 4 - i);
							break;
						case "Spiderman":
							InputStream SpidermanP = Files.newInputStream(Paths.get("res/images/SpiderMan.png"));
							Image Spiderman = new Image(SpidermanP);
							SpidermanP.close();
							ImageView SpidermanView = new ImageView(Spiderman);
							SpidermanView.setFitHeight(140);
							SpidermanView.setFitWidth(120);
							add(SpidermanView, j, 4 - i);
							break;
						case "Thor":
							InputStream ThorP = Files.newInputStream(Paths.get("res/images/Thor.png"));
							Image Thor = new Image(ThorP);
							ThorP.close();
							ImageView ThorView = new ImageView(Thor);
							ThorView.setFitHeight(140);
							ThorView.setFitWidth(120);
							add(ThorView, j, 4 - i);
							break;
						case "Venom":
							InputStream VenomP = Files.newInputStream(Paths.get("res/images/Venom.png"));
							Image Venom = new Image(VenomP);
							VenomP.close();
							ImageView VenomView = new ImageView(Venom);
							VenomView.setFitHeight(140);
							VenomView.setFitWidth(120);
							add(VenomView, j, 4 - i);
							break;
						case "Yellow Jacket":
							InputStream YellowJacketP = Files.newInputStream(Paths.get("res/images/YellowJacket.png"));
							Image YellowJacket = new Image(YellowJacketP);
							YellowJacketP.close();
							ImageView YellowJacketView = new ImageView(YellowJacket);
							YellowJacketView.setFitHeight(140);
							YellowJacketView.setFitWidth(120);
							add(YellowJacketView, j, 4 - i);
							break;
						}
						// HPBar
						float percentage = (float) (((double) ((Champion) map[i][j]).getCurrentHP())
								/ ((Champion) map[i][j]).getMaxHP());
						ProgressBar hp = new ProgressBar();
						hp.setStyle("-fx-accent: green;");
						hp.setProgress(percentage);
						hp.setPrefSize(100, 20);
						hp.setTranslateY(50);
						hp.setTranslateX(10);
						add(hp, j, 4 - i);
						int CurrentHp = ((Champion) map[i][j]).getCurrentHP();
						Text hptext = new Text("" + CurrentHp);
						hptext.setWrappingWidth(100);
						hptext.setTextAlignment(TextAlignment.CENTER);
						hptext.setFill(Color.BLACK);
						hptext.setTranslateY(50);
						hptext.setTranslateX(10);
						add(hptext, j, 4 - i);
						// Fill Colors

					}
				}
			}
		}
	}
}
