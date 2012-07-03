package info.mekapiku.bingogame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

final class BingoMainWindow {
	// ////////////////////////
	// メンバ変数
	// ////////////////////////
	
	// シングルトンにしておく
	private static final BingoMainWindow instance = new BingoMainWindow();
	// Swingコンポーネント
	private JFrame frame;
	private JLabel lblBingo;
	private JList list;
	private DefaultListModel model;
	// 表示するフォント設定
	private Font font = new Font("Arial", Font.BOLD, Consts.FONT_SIZE);
	// 初期化したかどうかのフラグ 
	private boolean isInit = false;
	// クリックしたかどうかのフラグ
	public boolean isClicked = false;
	// メインの処理が停止しているかを判断
	public boolean isStop;
	
	/**
	 * Create the application.
	 */
	public BingoMainWindow() {
		initialize();
	}

	/**
	 * シングルトンパターン
	 * 
	 * @return
	 */
	public static BingoMainWindow getInstance(){
		return BingoMainWindow.instance;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		// JFrameの設定
		frame = new JFrame();
		frame.setTitle(Consts.APP_NAME);
		// スクリーン最大サイズ取得
		Dimension dimSize = frame.getToolkit().getScreenSize();
		// 画面全体に設定
		frame.setBounds(0,0,dimSize.width,dimSize.height); 
		// 閉じる，最小化，最大化の設定
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 枠を消す
		frame.setUndecorated(true);
		// イベント設定
		frame.addMouseListener(new MouseListener() {			
			// ボタンを押したときに反応
			@Override
			public void mousePressed(MouseEvent e) {
				// ボタンを押した時の処理			
				// 動いてる
				if (!isStop) {
					isClicked = true;
				} else {
					// 停止中
					isClicked = false;
					Bingo4U.restart();
				}
			}
			
			// その他のイベント
			@Override
			public void mouseReleased(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) {	}
			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseClicked(MouseEvent e) { }
		});
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// ラベルについての設定
		lblBingo = new JLabel("Loading");
		lblBingo.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblBingo);
		// フォント
		lblBingo.setFont(font);
		
		// リストについての設定
		model = new DefaultListModel();
		model.addElement("履歴");
		list = new JList(model);
		list.setVisibleRowCount(0);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setFixedCellWidth(dimSize.width / 10);
		list.setFixedCellHeight(dimSize.height / 5);
		list.setFont(new Font("Arial", Font.BOLD, 64));
		JScrollPane sp = new JScrollPane(list);
		frame.getContentPane().add(sp, BorderLayout.EAST);	
		
		// 初期化完了
		isInit = true;
	}
	
	// 画面の更新
	void drawWindow(Integer num) {
		lblBingo.setText(Integer.toString(num));
	}

	
	// ////////////////////////
	// ゲッター・セッター
	// ////////////////////////
	public JFrame getWindow() {
		return frame;
	}
	
	public void addListItem(Integer num) {
		model.insertElementAt(Integer.toString(num), 0);
		list.ensureIndexIsVisible(0);
		
	}
	
	public void setLabelColor(Color c) {
		this.lblBingo.setForeground(c);
	}
	
	public boolean isInit() {
		return isInit;
	}
}
