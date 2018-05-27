package info.mekapiku.bingogame

import java.awt.Color
import java.awt.EventQueue
import javax.swing.UIManager

object Bingo4U {
    private lateinit var gamemain: BingoGameMain

    /**
     * @param args
     */
    @JvmStatic
    fun main(args: Array<String>) {
        if (Utils.isMac) {
            // JFrameにメニューをつけるのではなく、一般的なOSXアプリ同様に画面上端のスクリーンメニューにする.
            System.setProperty("apple.laf.useScreenMenuBar", "true")

            // スクリーンメニュー左端に表記されるアプリケーション名を設定する
            // (何も設定しないとクラス名になる。)
            System.setProperty(
                    "com.apple.mrj.application.apple.menu.about.name",
                    Consts.APP_NAME)
        }

        // システム標準のL&Fを設定.
        // MacOSXならAqua、WindowsXPならLuna、Vista/Windows7ならばAeroになる.
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())

        EventQueue.invokeLater {
            // ゲームの画面生成
            val window = BingoMainWindow.instance
            window.window?.isVisible = true

            // ゲームの進行担当
            gamemain = BingoGameMain(window)
            // ゲーム開始
            gamemain.start()
        }
    }

    // 再開
    fun restart() {
        BingoMainWindow.instance.setLabelColor(Color.black)
        gamemain.start()
    }
}
