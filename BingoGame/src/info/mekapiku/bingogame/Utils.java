package info.mekapiku.bingogame;
public final class Utils {
	public static final boolean isMac() {
        if (System.getProperty("os.name").indexOf("Mac") != -1)
            return true;
        else
            return false;
	}
}
