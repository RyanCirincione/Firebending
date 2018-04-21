
import static org.bytedeco.javacpp.opencv_core.cvFlip;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

public class FirebendingMain extends JPanel {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Firebending");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new FirebendingMain();
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				panel.repaint();
			}
		}, 0, 1000 / 60);
	}
	
	private static final long serialVersionUID = 4486604239167882738L;
	FrameGrabber grabber;
	OpenCVFrameConverter.ToIplImage converter;
	IplImage img;

	public FirebendingMain() {
		grabber = new VideoInputFrameGrabber(0);
		converter = new OpenCVFrameConverter.ToIplImage();
		try {
			grabber.start();
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
		
		this.setPreferredSize(new Dimension(640, 480));
	}
	
	public void paintComponent(Graphics gr) {
		Frame frame;
		try {
			frame = grabber.grab();
			img = converter.convert(frame);
			// the grabbed frame will be flipped, re-flip to make it right
			cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
			
			gr.drawImage(IplImageToBufferedImage(img), 0, 0, null);
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Copy/pasted, converts IplImage to BufferedImage
	 * 
	 * @param src
	 *            IplImage to convert
	 * @return Converted BufferedImage
	 */
	public static BufferedImage IplImageToBufferedImage(IplImage src) {
		OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
		Java2DFrameConverter paintConverter = new Java2DFrameConverter();
		Frame frame = grabberConverter.convert(src);
		return paintConverter.getBufferedImage(frame, 1);
	}
}