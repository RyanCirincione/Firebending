
import static org.bytedeco.javacpp.opencv_core.cvFlip;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
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
	ArrayList<Vertex> vertices;
	BufferedImage background;
	FrameGrabber grabber;
	OpenCVFrameConverter.ToIplImage converter;
	IplImage img;
	int bgTimer;

	public FirebendingMain() {
		vertices = new ArrayList<Vertex>();
		bgTimer = 0;
		background = null;
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
		BufferedImage image = null;
		try {
			Frame frame = grabber.grab();
			img = converter.convert(frame);
			// the grabbed frame will be flipped, re-flip to make it right
			cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise

			image = IplImageToBufferedImage(img);
			if(bgTimer < 30) {
				bgTimer++;
			} else if(bgTimer == 30) {
				bgTimer++;
				background = deepCopy(image);
			}
		} catch (org.bytedeco.javacv.FrameGrabber.Exception e) {
			e.printStackTrace();
		}

		boolean[][] pixels = new boolean[640][480];
		if(background != null) {
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					int bgRGB = background.getRGB(x, y), iRGB = image.getRGB(x, y);
					if (Math.abs((bgRGB >> 16) & 0xFF - (iRGB >> 16) & 0xFF) + Math.abs((bgRGB >> 8) & 0xFF - (iRGB >> 8) & 0xFF) +
							Math.abs(bgRGB & 0xFF - iRGB & 0xFF) > 160) {
						if(vertices.size() == 0) {
							vertices.add(new Vertex(x, y));
						}
						pixels[x][y] = true;
					}
				}
			}
		}
		
		Firegenerator.paintFire(image.getGraphics(), vertices);
		//good spot image.getGraphics
		
		gr.drawImage(image, 0, 0, null);
	}

	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
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