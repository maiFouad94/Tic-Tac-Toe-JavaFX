
package application;

import core.Space;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Mahmo
 */
public class ReplaySceneController implements Initializable {

    @FXML
    private Button NextBtn;
    @FXML
    private Button perviousBtn;
    @FXML
    private ImageView _1;
    @FXML
    private ImageView _2;
    @FXML
    private ImageView _3;
    @FXML
    private ImageView _4;
    @FXML
    private ImageView _5;
    @FXML
    private ImageView _6;
    @FXML
    private ImageView _7;
    @FXML
    private ImageView _8;
    @FXML
    private ImageView _9;
private int var1,var2,var3;
Database myDB;
    Space symb;
MainController Mc;
   /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     myDB = new Database();
    int x,y;
    var1=0;
    var2=0;
    var3=0;
    
    
    symb=myDB.SelectMovesSymbal(myDB.getGameID(), var1=0);
     Mc= new MainController();
    System.out.println(symb);
    y=myDB.SelectMovesX(myDB.getGameID(), var2=0);
    System.out.println(y);
    x=myDB.SelectMovesY(myDB.getGameID(), var3=0);
    System.out.println(x);
        }
    
    
    
          /**
           * handle replay 
           * @param event 
           */

    @FXML
    private void NextBtnHandle(ActionEvent event) {
    
   
    int x,y;
  
    
    symb=myDB.SelectMovesSymbal(myDB.getGameID(), var1++);
    
    y=myDB.SelectMovesX(myDB.getGameID(), var2++);
    System.out.println(y);

    x=myDB.SelectMovesY(myDB.getGameID(), var3++);
       
   
        if (symb==Space.X) {
            if(x==0&&y==0)
            {
                _1.setImage(Mc.currentImageSet.getXImage());
            }
            if(x==0&&y==1)
            {
                _2.setImage(Mc.currentImageSet.getXImage());
            }
            if(x==0&&y==2)
            {
                _3.setImage(Mc.currentImageSet.getXImage());
            }
            if(x==1&&y==0)
            {
                _4.setImage(Mc.currentImageSet.getXImage());
            }
            if(x==1&&y==1)
            {
                _5.setImage(Mc.currentImageSet.getXImage());
            }
            if(x==1&&y==2)
            {
                _6.setImage(Mc.currentImageSet.getXImage());
            }
            if(x==2&&y==0)
            {
                _7.setImage(Mc.currentImageSet.getXImage());
            }
            if(x==2&&y==1)
            {
                _8.setImage(Mc.currentImageSet.getXImage());
            }
            if(x==2&&y==2)
            {
                _9.setImage(Mc.currentImageSet.getXImage());
            }
        } else if(symb==Space.O)
        {
            if(x==0&&y==0)
            {
                _1.setImage(Mc.currentImageSet.getOImage());
            }
            if(x==0&&y==1)
            {
                _2.setImage(Mc.currentImageSet.getOImage());
            }
            if(x==0&&y==2)
            {
                _3.setImage(Mc.currentImageSet.getOImage());
            }
            if(x==1&&y==0)
            {
                _4.setImage(Mc.currentImageSet.getOImage());
            }
            if(x==1&&y==1)
            {
                _5.setImage(Mc.currentImageSet.getOImage());
            }
            if(x==1&&y==2)
            {
                _6.setImage(Mc.currentImageSet.getOImage());
            }
            if(x==2&&y==0)
            {
                _7.setImage(Mc.currentImageSet.getOImage());
            }
            if(x==2&&y==1)
            {
                _8.setImage(Mc.currentImageSet.getOImage());
            }
            if(x==2&&y==2)
            {
                _9.setImage(Mc.currentImageSet.getOImage());
            }
            
            
        }
        else if(symb.equals(null))
        {

        }
    }

}
    
    
    


   