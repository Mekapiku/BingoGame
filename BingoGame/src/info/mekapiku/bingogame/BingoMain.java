package info.mekapiku.bingogame;

import java.util.ArrayList;
import java.util.Collections;

final public class BingoMain {
	// ////////////////////////
	// メンバ変数
	// ////////////////////////

	// 八百長ビンゴの被害者のカード番号
	private ArrayList<Integer> someoneBingo = new ArrayList<Integer>();
	// すべてのビンゴ
	private ArrayList<Integer> allBingo = new ArrayList<Integer>();
	// 初期化フラグ
	private boolean isInit = false;
	// 八百長ゲーム中かどうか
	private boolean isFixGame = true;
	// 選択するビンゴ配列のID
	private static int bingoId = 0;
    // サウンド再生用
    // private SoundManager soundMgr = new SoundManager("/Users/Mitsuyasu/do.wav");
	
	
	// ////////////////////////
	// 初期化
	// ////////////////////////
	private void Initialize() {
		// 八百長データ読み込み
        try {
            BingoFileReader fr = new BingoFileReader(Consts.FILE_NAME);
            someoneBingo = fr.getBingoDataByFile();
        } catch (Exception ex) {
        	// ファイルが読み込めなかったら普通のビンゴに
        	someoneBingo.clear();
        }
		// リストに追加
		for (int i = 1; i < Consts.BINGO_MAX + 1; i++) {
			// すべてのビンゴをセット
			allBingo.add(i);
		}

		// リストをランダムに並び替え
		// Collections.shuffle(someoneBingo);
		Collections.shuffle(allBingo);

		// 初期化完了
		setInit(true);
	}

	// ////////////////////////
	// コンストラクタ
	// ////////////////////////
	BingoMain() {
		// 初期化でもするか
		Initialize();
	}

	// ////////////////////////
	// データの更新
	// ////////////////////////
	int process(long elapsedtime) {
		int num = 0;

		// クリックされてる？
		if (BingoMainWindow.getInstance().isClicked) {
			// 八百長なう？
			if (isFixGame) {
				// 八百長リストはあるか？
				if (!someoneBingo.isEmpty()) {
                    // num に八百長番号を返します．
                    num = someoneBingo.remove(0);
                    // 全体のビンゴから八百長番号を消す
                    allBingo.remove((Object) num);
                    BingoMainWindow.getInstance().addListItem(num);
                } else {
                	// 八百長停止
                    isFixGame = false;
                    num = allBingo.remove(0);
                    BingoMainWindow.getInstance().addListItem(num);
				}
			} else {
				// 八百長番号がなくなった全体のビンゴからポップ
				if (!allBingo.isEmpty()) {
					num = allBingo.remove(0);
					BingoMainWindow.getInstance().addListItem(num);
				}
			}
		} else {
			// 全体の番号から適当に表示
			num = allBingo.get(bingoId % (allBingo.size()));
			bingoId++;
		}
        
		// TODO 音楽再生
		// soundMgr.play(10);
		
		return num;
	}

	// ////////////////////////
	// ゲッター・セッター
	// ////////////////////////

	// 初期化
	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

	// 八百長ビンゴ
	public ArrayList<Integer> getSomeoneBingo() {
		return someoneBingo;
	}

	public void setSomeoneBingo(ArrayList<Integer> someoneBingo) {
		this.someoneBingo = someoneBingo;
	}

	// 全体のビンゴ
	public ArrayList<Integer> getAllBingo() {
		return allBingo;
	}

	public void setAllBingo(ArrayList<Integer> allBingo) {
		this.allBingo = allBingo;
	}
}
