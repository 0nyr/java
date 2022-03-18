import java.awt.*;


//cette classe sert comme bibliothèque afin de transformer un String en Color
public class ColorFactory {

    public static Color getColorFromString(String col) {
        Color color;
        switch (col.toLowerCase()) {
			case "gray":
				color =  Color.GRAY ;
				break;
			case "green":
				color = Color.GREEN ;
				break;
			case "blue":
				color =  Color.BLUE ;
				break;
			case "red":
				color = Color.RED ;
				break;
			case "orange":
				color =  Color.ORANGE ;
				break;
			case "yellow":
				color = Color.YELLOW ;
				break;
			case "pink":
				color = Color.PINK ;
				break;
			case "white":
				color = Color.WHITE ;
				break;
			case "magenta":
				color = Color.MAGENTA ;
				break;
			case "cyan":
				color = Color.CYAN ;
				break;

			default:
				color = null;
		}
		return color;
    }
}
