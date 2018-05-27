package info.mekapiku.bingogame

import java.util.*

class BingoMain
// ////////////////////////
// コンストラクタ
// ////////////////////////
internal constructor() {
    // ////////////////////////
    // メンバ変数
    // ////////////////////////

    // 八百長ビンゴの被害者のカード番号
    // 八百長ビンゴ
    var someoneBingo = ArrayList<Int>()
    // 全体のビンゴ
    var allBingo = ArrayList<Int>()
    // 初期化
    var isInit = false
    // 八百長ゲーム中かどうか
    private var isFixGame = true

    // ////////////////////////
    // 初期化
    // ////////////////////////
    private fun Initialize() {
        // 八百長データ読み込み
        try {
            val fr = BingoFileReader(Consts.FILE_NAME)
            someoneBingo = fr.bingoDataByFile
        } catch (ex: Exception) {
            // ファイルが読み込めなかったら普通のビンゴに
            someoneBingo.clear()
        }

        // リストに追加
        for (i in 1 until Consts.BINGO_MAX + 1) {
            // すべてのビンゴをセット
            allBingo.add(i)
        }

        // リストをランダムに並び替え
        // Collections.shuffle(someoneBingo);
        Collections.shuffle(allBingo)

        // 初期化完了
        isInit = true
    }

    init {
        // 初期化でもするか
        Initialize()
    }

    // ////////////////////////
    // データの更新
    // ////////////////////////
    internal fun process(): Int {
        var num = 0

        // クリックされてる？
        if (BingoMainWindow.instance.isClicked) {
            // 八百長なう？
            if (isFixGame) {
                // 八百長リストはあるか？
                if (!someoneBingo.isEmpty()) {
                    // num に八百長番号を返します．
                    num = someoneBingo.removeAt(0)
                    // 全体のビンゴから八百長番号を消す
                    allBingo.remove(num as Any)
                    BingoMainWindow.instance.addListItem(num)
                } else {
                    // 八百長停止
                    isFixGame = false
                    num = allBingo.removeAt(0)
                    BingoMainWindow.instance.addListItem(num)
                }
            } else {
                // 八百長番号がなくなった全体のビンゴからポップ
                if (!allBingo.isEmpty()) {
                    num = allBingo.removeAt(0)
                    BingoMainWindow.instance.addListItem(num)
                }
            }
        } else {
            // 全体の番号から適当に表示
            num = allBingo[bingoId % allBingo.size]
            bingoId++
        }

        return num
    }

    companion object {
        // 選択するビンゴ配列のID
        private var bingoId = 0
    }
}
