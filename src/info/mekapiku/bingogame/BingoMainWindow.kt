package info.mekapiku.bingogame

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*

internal class BingoMainWindow {
    // Swingコンポーネント
    var window: JFrame? = null
        private set
    private var lblBingo: JLabel? = null
    private var list: JList<*>? = null
    private var model: DefaultListModel<String>? = null
    // 表示するフォント設定
    private val font = Font("Arial", Font.BOLD, Consts.FONT_SIZE)
    // 初期化したかどうかのフラグ
    var isInit = false
        private set
    // クリックしたかどうかのフラグ
    var isClicked = false
    // メインの処理が停止しているかを判断
    var isStop: Boolean = false

    /**
     * Create the application.
     */
    init {
        initialize()
    }

    /**
     * Initialize the contents of the frame.
     */
    fun initialize() {
        // JFrameの設定
        window = JFrame()
        window!!.title = Consts.APP_NAME
        // スクリーン最大サイズ取得
        val dimSize = window!!.toolkit.screenSize
        // 画面全体に設定
        window!!.setBounds(0, 0, dimSize.width, dimSize.height)
        // 閉じる，最小化，最大化の設定
        window!!.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        // 枠を消す
        window!!.isUndecorated = true
        // イベント設定
        window!!.addMouseListener(object : MouseListener {
            // ボタンを押したときに反応
            override fun mousePressed(e: MouseEvent) {
                // ボタンを押した時の処理
                // 動いてる
                if (!isStop) {
                    isClicked = true
                } else {
                    // 停止中
                    isClicked = false
                    Bingo4U.restart()
                }
            }

            // その他のイベント
            override fun mouseReleased(e: MouseEvent) {}

            override fun mouseExited(e: MouseEvent) {}
            override fun mouseEntered(e: MouseEvent) {}
            override fun mouseClicked(e: MouseEvent) {}
        })
        window!!.contentPane.layout = BorderLayout(0, 0)

        // ラベルについての設定
        lblBingo = JLabel("Loading")
        lblBingo!!.horizontalAlignment = SwingConstants.CENTER
        window!!.contentPane.add(lblBingo)
        // フォント
        lblBingo!!.font = font

        // リストについての設定
        model = DefaultListModel<String>()
        model!!.addElement("履歴")
        list = JList(model!!)
        list!!.visibleRowCount = 0
        list!!.layoutOrientation = JList.HORIZONTAL_WRAP
        list!!.fixedCellWidth = dimSize.width / 10
        list!!.fixedCellHeight = dimSize.height / 5
        list!!.font = Font("Arial", Font.BOLD, 64)
        window!!.contentPane.add(JScrollPane(list), BorderLayout.EAST)

        // 初期化完了
        isInit = true
    }

    // 画面の更新
    fun drawWindow(num: Int?) {
        lblBingo!!.text = Integer.toString(num!!)
    }

    fun addListItem(num: Int?) {
        model!!.insertElementAt(Integer.toString(num!!), 0)
        list!!.ensureIndexIsVisible(0)

    }

    fun setLabelColor(c: Color) {
        this.lblBingo!!.foreground = c
    }

    companion object {
        /**
         * シングルトンパターン
         *
         * @return
         */
        val instance = BingoMainWindow()
    }
}
