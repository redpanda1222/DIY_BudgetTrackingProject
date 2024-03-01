/*
 * Team Periwinkle
 */
package tcss360.diybuilder;
/**
 * About Main.
 *
 * @author Soe Lin
 */
import tcss360.diybuilder.SystemControl.Controller;
import tcss360.diybuilder.ui.DIYControl;


/**
 * Main UI.
 */
public class App 
{
    public static void main( String[] args )
    {
        //read in json file
        Controller controller = new Controller();

        DIYControl jFrame = new DIYControl();
        jFrame.display();
    }
}
