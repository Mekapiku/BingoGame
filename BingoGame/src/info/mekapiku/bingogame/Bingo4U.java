package info.mekapiku.bingogame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Bingo4U {
	static BingoGameMain gamemain;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (Utils.isMac()) {
		 // JFrameにメニューをつけるのではなく、一般的なOSXアプリ同様に画面上端のスクリーンメニューにする.
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            // スクリーンメニュー左端に表記されるアプリケーション名を設定する
            // (何も設定しないとクラス名になる。)
            System.setProperty(
                    "com.apple.mrj.application.apple.menu.about.name",
                    Consts.APP_NAME);
        }

        // システム標準のL&Fを設定.
        // MacOSXならAqua、WindowsXPならLuna、Vista/Windows7ならばAeroになる.
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
        
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
                try {
					// ゲームの画面生成
                    BingoMainWindow window = BingoMainWindow.getInstance();
					window.getWindow().setVisible(true);
					
					// ゲームの進行担当
                    gamemain = new BingoGameMain(window);
					// ゲーム開始
					gamemain.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// 再開
	public static void restart() {
		BingoMainWindow.getInstance().setLabelColor(Color.black);
		gamemain.start();
	}
}
