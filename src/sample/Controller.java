package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

	public class Controller {
		@FXML
		private Button camera_btn;
		@FXML
		private ImageView currentFrame;

		private VideoCapture capture;
		private boolean cameraActive;
		private static int cameraId = 0;
		private ScheduledExecutorService timer;
		private CascadeClassifier faceCascade;
		private int absoluteFacesize;

		public void initialize() {
			this.capture = new VideoCapture();
			this.cameraActive = false;
			this.faceCascade = new CascadeClassifier();
			this.absoluteFacesize = 0;
		}

		@FXML
		protected void startCamera(ActionEvent event) {
			// preserve image ratio
			this.currentFrame.setPreserveRatio(true);

			if (!this.cameraActive) {

				this.capture.open(cameraId);

				if (this.capture.isOpened()) {
					this.cameraActive = true;

					// grab a 30 frames/sec
					Runnable frameGrabber = () -> {
						// effectively grab and process a single frame
						Mat frame = grabFrame();
						// convert and show the frame
						Image imageToShow = Utils.mat2Image(frame);
						updateImageView(currentFrame, imageToShow);
					};

					this.timer = Executors.newSingleThreadScheduledExecutor();
					this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

					// Update the button text
					this.camera_btn.setText("Stop Camera");
				} else {
					System.err.println("Impossible to open the camera connection...");
				}
			} else {
				this.cameraActive = false;
				this.camera_btn.setText("Start Camera");

				// stop timer
				this.stopAcquisition();
			}
		}

		private Mat grabFrame() {
			Mat frame = new Mat();
			if (this.capture.isOpened()) {
				try {
					// read current frame
					this.capture.read(frame);

					if (!frame.empty()) {

						detectAndDisplay(frame);

					}
				} catch (Exception e) {
					System.err.println("Exception during the image elaboration: " + e);
				}
			}
			return frame;
		}

		private void detectAndDisplay(Mat frame) {
			MatOfRect faces = new MatOfRect();

			// Minimum face size is 20% of the frame height.
			if (this.absoluteFacesize == 0) {
				int height = frame.rows();
				if (Math.round(height * 0.2f) > 0) {
					this.absoluteFacesize = Math.round(height * 0.2f);
				}
			}

			this.faceCascade.load(getClass().getResource("/sample/haarcascade_frontalface_alt.xml").getPath());
			// start video capture
			this.camera_btn.setDisable(false);

			// Detect faces, faces detected are stored in faces.
			this.faceCascade.detectMultiScale(frame, faces, 1.1, 1, Objdetect.CASCADE_SCALE_IMAGE,
					new Size(this.absoluteFacesize, this.absoluteFacesize), new Size());

			Rect[] facesArray = faces.toArray();
			for (Rect aFacesArray : facesArray) {
				Imgproc.rectangle(frame, aFacesArray.tl(), aFacesArray.br(), new Scalar(0, 255, 0), 2);
			}
		}

		private void stopAcquisition() {
			if (this.timer != null && !this.timer.isShutdown()) {
				try {
					// Shut down the timer.
					this.timer.shutdown();
					this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					System.err.println("Exception in stopping the frame capture, trying to release the camera now..." + e);
				}
			}

			if (this.capture.isOpened()) {
				// Close the camera.
				this.capture.release();
			}
		}

		private void updateImageView(ImageView view, Image image) {
			Utils.onFXThread(view.imageProperty(), image);
		}

		void setClosed() {
			this.stopAcquisition();
		}
	}
