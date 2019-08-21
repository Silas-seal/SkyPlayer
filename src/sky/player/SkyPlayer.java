
package sky.player;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.media.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.util.Duration;
//import javafx.scene.media.MediaPlayer.Status;
//import javafx.stage.*;

/**
 *
 * @author THE SKYLINE DIGITAL
 */
public class SkyPlayer extends Application {
    private static MediaPlayer player; private ObservableList<File> olst; private static Media media; private List<File> list;
    private int nxt; private Label lb; private ListView lv; private File file; private Slider vl; private Label spc; private Label tto;
    private Button lib; private Button pl; private Label fname; private ImageView pb; private ImageView pb2; private GridPane ft;
    private static Stage video; private GridPane mvw; private static MediaView mview; private Scene vsn;
    
    public void play(){
        file=new File(lv.getItems().get(nxt).toString()); 
        media=new Media(file.toURI().toString()); player=new MediaPlayer(media); mview=new MediaView(player);
        
        mvw=new GridPane(); mvw.setPadding(new Insets(0,0,0,0)); mvw.setAlignment(Pos.CENTER);mvw.add(mview,0,0);
        mvw.prefHeightProperty().bind(video.heightProperty()); mvw.prefWidthProperty().bind(video.widthProperty());
        mview.fitHeightProperty().bind(mvw.heightProperty()); mview.fitWidthProperty().bind(mvw.widthProperty()); 
        vsn=new Scene(mvw); video.setScene(vsn);
        
        if(!video.isFullScreen()){
            mview.setOnMouseClicked(ev->{
                if(ev.getClickCount()==2){video.setFullScreen(true); }
            });
        } 
        
        player.setVolume(vl.getValue()); player.setAutoPlay(false); player.play(); fname.setText(file.getName());
        
        player.setOnReady(()->{ 
            if(file.toString().endsWith(".mp4")) {video.show(); video.setTitle("sky music - "+file.getName());}
            tto.setText(player.getTotalDuration().toString());
        });
        player.setOnPlaying(()->{
            lb.textProperty().bind(player.currentTimeProperty().asString());
        });
        player.setOnEndOfMedia(()->{ video.hide();
            nxt=nxt+1; if(nxt>lv.getItems().size()-1){ player.stop(); ft.getChildren().remove(pb2);ft.add(pb,2,0);} else{ play(); }
        });
        
        vl.valueProperty().bind(player.volumeProperty());
    }
    
    @Override
    public void start(Stage Stage) throws Exception {
        Image icon=new Image(new FileInputStream("Icons/icon.png")); Stage.getIcons().add(icon);
        
        Stage.initStyle(StageStyle.DECORATED);Stage.setOpacity(1);Stage.setResizable(true);Stage.setHeight(450);Stage.setWidth(700);
        
        BackgroundFill bf1=new BackgroundFill(Color.CADETBLUE,new CornerRadii(5),new Insets(0,0,0,0)); Background bg1=new Background(bf1);
        BackgroundFill bf2=new BackgroundFill(Color.DARKSLATEGREY,new CornerRadii(5),new Insets(0,0,0,0)); Background bg2=new Background(bf2);
        BackgroundFill bf3=new BackgroundFill(Color.DARKRED,new CornerRadii(8),new Insets(0,0,0,0)); Background bg3=new Background(bf3);
        BackgroundFill bf4=new BackgroundFill(Color.BLACK,new CornerRadii(3),new Insets(0,0,0,0)); Background bg4=new Background(bf4);
        
        Image plb=new Image(new FileInputStream("Icons/play.png"));
        pb=new ImageView(plb);pb.setFitHeight(45);pb.setFitWidth(40);
        
        Image psb=new Image(new FileInputStream("Icons/pause.png"));
        pb2=new ImageView(psb);pb2.setFitHeight(45);pb2.setFitWidth(40);
        
        Image nb=new Image(new FileInputStream("Icons/next.png"));
        ImageView next=new ImageView(nb);next.setFitHeight(30);next.setFitWidth(30);
        
        Image prb=new Image(new FileInputStream("Icons/prev.png"));
        ImageView prev=new ImageView(prb);prev.setFitHeight(30);prev.setFitWidth(30);
        
        Image frb=new Image(new FileInputStream("Icons/forward.png"));
        ImageView frwd=new ImageView(frb);frwd.setFitHeight(30);frwd.setFitWidth(30);
        
        Image bb=new Image(new FileInputStream("Icons/back.png"));
        ImageView back=new ImageView(bb);back.setFitHeight(30);back.setFitWidth(30);
        
        Image im=new Image(new FileInputStream("Icons/mx.png"));
        ImageView mx=new ImageView(im); mx.setFitHeight(150); mx.setFitWidth(150);
        ImageView mx2=new ImageView(icon); mx2.setFitHeight(150); mx2.setFitWidth(150);
        
        lib=new Button("Library");lib.setStyle("-fx-background-color:navy;-fx-text-fill:azure;-fx-font:bold bold 18px 'Bell MT'");
        pl=new Button("Player");pl.setStyle("-fx-background-color:purple;-fx-text-fill:azure;-fx-font:bold bold 18px 'Bell MT'");
        vl=new Slider();vl.setPrefWidth(150);vl.setMin(0);vl.setMax(1);vl.setDisable(true);vl.setStyle("-fx-background-color:aliceblue");
        vl.setValue(0.1);
        
        Button vup=new Button("+");vup.setStyle("-fx-background-color:purple;-fx-text-fill:azure;-fx-font:bold bold 13px 'Bell MT'");
        Button vd=new Button("-");vd.setStyle("-fx-background-color:purple;-fx-text-fill:azure;-fx-font:bold bold 13px 'Bell MT'");
        Label vol=new Label("Volume");vol.setStyle("-fx-font:bold bold 21px 'Bell MT'");vol.setTextFill(Color.ALICEBLUE);
        
        Label log=new Label("Sky Music \n     Player");log.setStyle("-fx-font:bold 53px 'Bell MT'");log.setTextFill(Color.ALICEBLUE);
        Label stat=new Label("Excellent Music Sound\n\n  []Music feeling");stat.setStyle("-fx-font: normal 23px 'serif'");stat.setTextFill(Color.PLUM);
        
        GridPane fft2=new GridPane();fft2.setPadding(new Insets(7,7,7,7));fft2.setVgap(7);fft2.setHgap(17);fft2.setAlignment(Pos.CENTER);
        fft2.setBackground(bg2);
        GridPane fft=new GridPane();fft.setPadding(new Insets(3,3,3,3));fft.setVgap(4);fft.setHgap(4);fft.setAlignment(Pos.CENTER);
        fft.setBackground(bg2);
        ft=new GridPane();ft.setPadding(new Insets(7,7,7,7));ft.setVgap(7);ft.setHgap(17);ft.setAlignment(Pos.CENTER);
        ft.setBackground(bg1);ft.setOpacity(1);ft.add(prev,0,0);ft.add(back,1,0);ft.add(pb,2,0);ft.add(frwd,3,0);ft.add(next,4,0);
        fft.add(vol,0,0);fft.add(vd,1,0);fft.add(vl,2,0);fft.add(vup,3,0); fft2.add(fft,0,0);fft2.add(ft,0,1);
        
        GridPane gt=new GridPane();gt.setPadding(new Insets(2,2,2,2));gt.setVgap(4);gt.setHgap(1);gt.setAlignment(Pos.CENTER);
        gt.setBackground(bg1);gt.setOpacity(0.8);gt.add(lib,1,0);gt.add(pl,0,0);
        
        tto=new Label("0000");tto.setStyle("-fx-font:normal 17px 'Times new roman'");tto.setTextFill(Color.ALICEBLUE);
        spc=new Label(" :: ");spc.setStyle("-fx-font:normal 17px 'Times new roman'");spc.setTextFill(Color.ALICEBLUE);
        lb=new Label("0000");lb.setStyle("-fx-font:normal 17px 'Times new roman'");lb.setTextFill(Color.AZURE);
        
        fname=new Label("-----  sky muxic  -----");fname.setStyle("-fx-font:normal 17px 'Times new roman'");tto.setTextFill(Color.ALICEBLUE);
        GridPane art=new GridPane(); art.setBackground(bg4); art.add(fname,0,0);art.setMinWidth(320);art.setMaxWidth(320);
        GridPane tym=new GridPane();tym.setHgap(4);tym.setVgap(1);tym.setBackground(bg3);tym.add(lb,0,0);tym.add(spc,1,0);tym.add(tto,2,0);
        tym.setMinWidth(320);tym.setMaxWidth(320);
        
        GridPane show=new GridPane();show.setPadding(new Insets(1,1,1,1));show.setVgap(1);show.setHgap(1);show.setAlignment(Pos.CENTER);
        show.setBackground(bg2);show.setOpacity(0.9);show.add(log,0,0);show.add(stat,0,1);show.add(tym,0,2);show.add(art,0,3);
        
        GridPane ms=new GridPane();ms.setVgap(9);ms.setHgap(9);ms.setAlignment(Pos.BOTTOM_LEFT);ms.setBackground(bg2);
        ms.setPadding(new Insets(1,1,1,1)); ms.add(mx,0,0);
        
        GridPane ms2=new GridPane();ms2.setVgap(9);ms2.setHgap(9);ms2.setAlignment(Pos.BOTTOM_LEFT);ms2.setBackground(bg2);
        ms2.setPadding(new Insets(1,1,1,1)); ms2.add(mx2,0,0);
        
        BorderPane brd=new BorderPane(); brd.setBackground(bg2); brd.setBottom(fft2); brd.setTop(gt); brd.setCenter(show);
        brd.setLeft(ms); brd.setRight(ms2);
        
        Scene sn=new Scene(brd);Stage.setTitle("Sky Music");Stage.setScene(sn);Stage.show(); 
        
        //adding video showing tool for mp4 files
        video=new Stage(); video.setHeight(500); video.setWidth(750); video.initStyle(StageStyle.DECORATED); video.getIcons().add(icon);
        
        //Adding functionality to the icons and buttons
        olst=FXCollections.observableArrayList(); lv=new ListView();
        lib.setOnAction((new EventHandler<ActionEvent>(){ 
            @Override
            public void handle(ActionEvent ev){ 
                list=new ArrayList();
                if(list!=null){
                    olst.addAll(list); lv.setItems(olst);lv.setStyle("-fx-control-inner-background:lightblue,10%;-fx-control-inner-text:aliceblue;");
                    show.getChildren().clear();show.add(lv,0,0);
                } } }));
        
        lv.setOnMouseClicked(e1->{
            if(e1.getClickCount()==2&&lv.getSelectionModel().getSelectedItem()!=null){ if(player!=null){player.stop();}
                        
            nxt=lv.getSelectionModel().getSelectedIndex(); play();
            show.getChildren().clear();show.add(log,0,0);show.add(stat,0,1);show.add(tym,0,2);show.add(art,0,3);
            fft2.getChildren().clear();fft2.add(fft,0,0);fft2.add(ft,0,1);brd.setBottom(fft2);
                        
            ft.getChildren().remove(pb); ft.add(pb2, 2, 0);
                }
                        //end o ftrack on audio first
                        /*player.setOnEndOfMedia(()->{
                            ft.getChildren().remove(pb2);ft.add(pb,2,0);  
                            nxt=nxt+1;File fl=new File(lv.getItems().get(nxt).toString());if(fl.toString().endsWith(".mp4")){
                                
                            media=new Media(fl.toURI().toString()); player=new MediaPlayer(media); player.setAutoPlay(false); 
                           player.setVolume(vl.getValue());
                           mview=new MediaView(player); player.play(); show.getChildren().clear();
                           mview.setFitHeight(670);mview.setFitWidth(900);
                           show.add(mview, 0, 0);ft.getChildren().remove(pb); ft.add(pb2, 2, 0);vl.setValue(player.getVolume());
                           
                            } else { show.getChildren().clear();show.add(log,0,0);show.add(stat,0,1);show.add(tym,0,2);
                            media=new Media(fl.toURI().toString()); player=new MediaPlayer(media);player.setAutoPlay(false);
                            if(media!=null){ft.getChildren().remove(pb);ft.add(pb2,2,0);player.play();tto.setText(player.getTotalDuration().toString());
                            lb.textProperty().bind(player.currentTimeProperty().asString()); player.setVolume(vl.getValue());}  }
                        });*/
                        
        });
                    
        lv.setOnDragEntered(e2->{
            Dragboard db=e2.getDragboard();
            if(db.hasFiles()){List<File>lst2=db.getFiles(); olst.addAll(lst2);  }
            });
            lv.prefHeightProperty().bind(show.heightProperty());lv.prefWidthProperty().bind(show.widthProperty());
                    
            lv.setCellFactory(listv->{
            return new ListCell<File>(){
                @Override
                public void updateItem(File fi,boolean empty){
                    super.updateItem(fi,empty);
                    setText(fi==null ? null : fi.getName());
                }
            };
        }); 
        
        pb.setOnMouseClicked((new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent ev){
                player.play(); ft.getChildren().remove(pb);ft.add(pb2,2,0);
                if(file.toString().endsWith(".mp4")){ video.show(); }
            }
        }));
        
        pb2.setOnMouseClicked((new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent ev){
                player.pause(); ft.getChildren().remove(pb2);ft.add(pb,2,0);
            }
        }));
        
        frwd.setOnMouseClicked((new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent ev){
                player.seek(player.getCurrentTime().add(Duration.seconds(7)));
            }
        }));
        
        back.setOnMouseClicked((new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent ev){
                player.seek(player.getCurrentTime().subtract(Duration.seconds(7)));
            }
        }));
        
        next.setOnMouseClicked((new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent ev){
                player.seek(player.getStopTime()); player.stop(); nxt=nxt+1; play();
            }
        }));
        
        prev.setOnMouseClicked((new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent ev){
                player.seek(player.getStartTime());
                if(ev.getClickCount()==2){ nxt=nxt-1; player.stop(); play(); }
            }
        }));
        
        vup.setOnAction((new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                player.setVolume(player.getVolume()+0.02); vl.setValue(player.getVolume());
            }
        }));
        
        vd.setOnAction((new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                player.setVolume(player.getVolume()-0.02); vl.setValue(player.getVolume());
            }
        }));
        
        pl.setOnAction((new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent ev){
                show.getChildren().clear();show.add(log,0,0);show.add(stat,0,1);show.add(tym,0,2);show.add(art,0,3);
                fft2.getChildren().clear();fft2.add(fft,0,0);fft2.add(ft,0,1);brd.setBottom(fft2);  }
        }));
                           
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
