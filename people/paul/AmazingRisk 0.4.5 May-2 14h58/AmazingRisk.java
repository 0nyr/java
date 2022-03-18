import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AmazingRisk {

	public static void main(String[] args) throws Exception{
		UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		System.out.println(TitleOf.version) ;
		(new SplashScreen()).openSplashScreen(1) ; //one second before opening
	}
}
