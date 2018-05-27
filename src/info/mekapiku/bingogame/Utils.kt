package info.mekapiku.bingogame

object Utils {
    val isMac: Boolean
        get() = System.getProperty("os.name").indexOf("Mac") != -1
}
