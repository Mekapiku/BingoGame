package info.mekapiku.bingogame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BingoFileReader {
	private String fileName;
	
	BingoFileReader(String fileName) {
		this.setFileName(fileName);
	}
	
    // ファイルからビンゴデータを読み込み
	public ArrayList<Integer> getBingoDataByFile() throws NumberFormatException, IOException {
		// 八百長ビンゴデータを読み込み
		ArrayList<Integer> b = new ArrayList<Integer>();
		// ファイルの準備
		File f = new File(Consts.FILE_NAME);

			FileReader r = new FileReader(f);
            // カンマごとに数字を分断して配列に代入
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                for (String n : line.split(",")) {
                	b.add(Integer.parseInt(n));
                }
            }
            // 後始末
            br.close();
            r.close();		
		return b;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
