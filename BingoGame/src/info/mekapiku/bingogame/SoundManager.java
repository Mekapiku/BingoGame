package info.mekapiku.bingogame;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {
	// 再生ファイル
	private File file;
	// 再生用
	private AudioInputStream stream;
	private AudioFormat format;
	private DataLine.Info info;
	private SourceDataLine dataLine;

	public SoundManager(String name) {
		// 再生の準備
		file = new File(name);
		try {
			stream = AudioSystem.getAudioInputStream(file);

			format = stream.getFormat();

			info = new DataLine.Info(SourceDataLine.class, format);
			dataLine = (SourceDataLine) AudioSystem.getLine(info);
			dataLine.open(format);
		} catch (UnsupportedAudioFileException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void play(int vol) {
		// ボリュームの操作
		// FloatControlはdataLineをオープンした後にゲットする
		FloatControl ctrl = (FloatControl) dataLine
				.getControl(FloatControl.Type.MASTER_GAIN);
		ctrl.setValue(vol);

		dataLine.start();
		byte[] bytes = new byte[128000];
		try {
			while (stream.read(bytes, 0, bytes.length) != -1) {
				dataLine.write(bytes, 0, bytes.length);
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
            dataLine.drain();
            dataLine.close();
		}
	}

	public void stop() {
		dataLine.stop();
	}

	public void release() {
		dataLine.close();
	}
}