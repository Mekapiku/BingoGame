package info.mekapiku.bingogame

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*

class BingoFileReader internal constructor(fileName: String) {
    var fileName: String? = null

    /**
     * 八百長データ読み込み関数
     * データフォーマットは数字カンマ区切りの1行ファイル
     */
    val bingoDataByFile: ArrayList<Int>
        @Throws(NumberFormatException::class, IOException::class)
        get() {
            val list = ArrayList<Int>()
            val file = File(fileName)

            val reader = FileReader(file)
            val bufferedReader = BufferedReader(reader)
            val line = bufferedReader.readLine()

            for (n in line.split(",")) {
                list.add(Integer.parseInt(n))
            }

            bufferedReader.close()
            reader.close()
            return list
        }

    init {
        this.fileName = fileName
    }
}
