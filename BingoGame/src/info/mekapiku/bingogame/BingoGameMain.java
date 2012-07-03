package info.mekapiku.bingogame;

import java.awt.Color;

class BingoGameMain implements Runnable {
	// 各クラスにアクセスできるようにしておく
	private BingoMain bingoData;
	private BingoMainWindow window;

	// 停止フラグ
	private boolean isStop;

	BingoGameMain(BingoMainWindow window) {
		this.window = window;
		this.bingoData = new BingoMain();
		isStop = true;
	}

	// ゲームスタート
    void start() {
        if (isStop) {
			isStop = false;
			BingoMainWindow.getInstance().isStop = false;
			// スレッドの開始
			(new Thread(this)).start();
		}
	}

    // ゲーム終了
	void stop() {
		isStop = true;
		BingoMainWindow.getInstance().isStop = true;
		BingoMainWindow.getInstance().setLabelColor(Color.red);
	}

	@Override
	public void run() {
		// 開始時間
		long lasttime = 0;
		// 表示する数値
		int showNumber = 0;
		// クリックした時のエフェクト用
		boolean state = false;
		int count = 0;
		long frameRate = 60;
		
		// 繰り返し処理開始
		while (!isStop) {
			// ループ開始時刻の保持
			long now = System.currentTimeMillis();
			// 次の処理を開始しなければならない時間を設定
			long nextTime = now + frameRate;

			// 画面が初期化されているか
			if (window.isInit()) {
				// 経過時間を求める
				long elapsedtime = 0;
				if (lasttime != 0) {
					elapsedtime = now - lasttime;
				}

				// 終了条件
				if (bingoData.getAllBingo().isEmpty()) {
					stop();
				} else if (BingoMainWindow.getInstance().isClicked || state) {
					BingoMainWindow.getInstance().isClicked = false;
					// もっさり動作へ移行
					if (!state) state = true;
                    
					if (count < 23) {
						// だんだん遅くなっていく
						frameRate += count;
                        // カウントを増やす
						count++;
					} else {
						// 初期化
						count = 0;
						state = false;
						// フラグを戻す
						BingoMainWindow.getInstance().isClicked = true;
						// 処理を止める
						stop();
					}
                }            
				
				// この時間における処理を実行
				showNumber = bingoData.process(elapsedtime);

				// 次の経過時間のために値を設定する
				lasttime = now;

				// 処理に応じて，画面を描写
				window.drawWindow(showNumber);
			} else {
				window.initialize();
			}

			// 60fpsになるように調整
			while (true) {
				long sleeptime = nextTime - System.currentTimeMillis();
				if (sleeptime < 0) {
					break;
				} else {
					try {
						Thread.sleep(sleeptime);
					} catch (InterruptedException e) {

					}
				}
			}
		}
	}
}
