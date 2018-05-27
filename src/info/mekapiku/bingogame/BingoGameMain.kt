package info.mekapiku.bingogame

import java.awt.Color

internal class BingoGameMain(private val window: BingoMainWindow) : Runnable {
    // 各クラスにアクセスできるようにしておく
    private val bingoData: BingoMain = BingoMain()

    // 停止フラグ
    private var isStop: Boolean = false

    init {
        isStop = true
    }

    // ゲームスタート
    fun start() {
        if (isStop) {
            isStop = false
            BingoMainWindow.instance.isStop = false
            // スレッドの開始
            Thread(this).start()
        }
    }

    // ゲーム終了
    fun stop() {
        isStop = true
        BingoMainWindow.instance.isStop = true
        BingoMainWindow.instance.setLabelColor(Color.red)
    }

    override fun run() {
        // 表示する数値
        var showNumber: Int
        // クリックした時のエフェクト用
        var state = false
        var count = 0
        var frameRate: Long = 60

        // 繰り返し処理開始
        while (!isStop) {
            // ループ開始時刻の保持
            val now = System.currentTimeMillis()
            // 次の処理を開始しなければならない時間を設定
            val nextTime = now + frameRate

            // 画面が初期化されているか
            if (window.isInit) {
                // 終了条件
                if (bingoData.allBingo.isEmpty()) {
                    stop()
                } else if (BingoMainWindow.instance.isClicked || state) {
                    BingoMainWindow.instance.isClicked = false
                    // もっさり動作へ移行
                    if (!state) state = true

                    if (count < 23) {
                        // だんだん遅くなっていく
                        frameRate += count.toLong()
                        // カウントを増やす
                        count++
                    } else {
                        // 初期化
                        count = 0
                        state = false
                        // フラグを戻す
                        BingoMainWindow.instance.isClicked = true
                        // 処理を止める
                        stop()
                    }
                }

                // この時間における処理を実行
                showNumber = bingoData.process()

                // 処理に応じて，画面を描写
                window.drawWindow(showNumber)
            } else {
                window.initialize()
            }

            // 60fpsになるように調整
            while (true) {
                val sleeptime = nextTime - System.currentTimeMillis()
                if (sleeptime < 0)
                    break

                Thread.sleep(sleeptime)
            }
        }
    }
}
