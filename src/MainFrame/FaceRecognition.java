package MainFrame;

import Database.DatabaseApi;
import Database.Student;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.FisherFaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

class FaceRecognition {

    DatabaseApi api = new DatabaseApi();
    private FaceRecognizer recognizer;
    private CascadeClassifier faceCascade;
    /**
     * Stores all the training images.
     */
    private List<Mat> images;
    private ArrayList<Integer> labelList;
    private ArrayList<String> imagePathList;
    private MatOfInt labels;
    /**
     * Temp list to store recognized list of Mat.
     */
    private static ArrayList<Mat> tmpMatList;
    /**
     * Temp list to store recognized list of label.
     */
    private static ArrayList<Integer> tmpLabelList;
    private final Size standardSize;
    private int counter;

    private static final String trainingPath = "src/Resources/TrainingImage";
    private static final String labelTextPath = "src/Resources/labels.txt";

    FaceRecognition() {
        // 1, 8, 8, 8, 85
        recognizer = LBPHFaceRecognizer.create(1, 8, 8, 8, 88);
        faceCascade = new CascadeClassifier();
        labelList = new ArrayList<>();
        labels = new MatOfInt();
        images = new ArrayList<>();
        imagePathList = new ArrayList<>();
        tmpMatList = new ArrayList<>();
        tmpLabelList = new ArrayList<>();
        standardSize = new Size(200, 200);
        counter = 0;
    }

    /**
     * Generates label text file.
     */
    private void generateLabelText() {
        File outputFile = new File(labelTextPath);
        PrintWriter pw = null;
        File sourceFile = new File(trainingPath);
        try {
            pw = new PrintWriter(outputFile);
            getFiles(sourceFile, pw);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            pw.flush();
            pw.close();
        }
    }

    /**
     * Extracts label and path to image from given directory and writes to file.
     *
     * @param dir
     * @param pw
     */
    private void getFiles(File dir, PrintWriter pw) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    getFiles(file, pw);
                }
            } else {
                if (!dir.isHidden()) {
                    String parentPath = dir.getParent();
                    String[] dirs = parentPath.split("/");
                    String parentFolder = dirs[dirs.length - 1];
                    String label = parentFolder.substring(0, parentFolder.length());
                    String outputString = dir.toString() + ";" + label + "\n";
                    pw.write(outputString);
                }
            }
        }
    }

    /**
     * Read images path and labels.
     *
     * @return Returns a list of images
     */
    private void loadLabelAndImagePath() {
        File file = new File(labelTextPath);
//        imagePathList.clear();
//        labelList.clear();
        imagePathList = new ArrayList<>();
        labelList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";

            while ((line = br.readLine()) != null) {
                String[] sp = line.split(";");

                imagePathList.add(sp[0]);
                labelList.add(Integer.parseInt(sp[1]));

                counter++;
            }
        } catch (IOException e) {
            System.out.println("Fail to open label file!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the training images from a photo path list.
     *
     * @param imagePath An list with image path.
     */
    void loadTrainingImages(ArrayList<String> imagePath) {
        images.clear();

        if (!imagePath.isEmpty()) {
            counter = 0;
            imagePath.stream().map((path) -> Imgcodecs.imread(path, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE)).map((trainingFaceMat) -> {
                Imgproc.equalizeHist(trainingFaceMat, trainingFaceMat);
                return trainingFaceMat;
            }).map((Mat trainingFaceMat) -> {
                Size imgSize = trainingFaceMat.size();
                System.out.println(imgSize);

                Imgproc.resize(trainingFaceMat, trainingFaceMat, standardSize, 1, 1, Imgproc.INTER_CUBIC);
                return trainingFaceMat;
            }).map((trainingFaceMat) -> {

                images.add(counter, trainingFaceMat);
                return trainingFaceMat;
            }).forEachOrdered((_item) -> {
                counter++;
            });
            System.out.println("load end");
        }

    }

    /**
     * Training the model.
     */
    void trainModel() {

        generateLabelText();
        loadLabelAndImagePath();
        loadTrainingImages(imagePathList);

        labels.fromList(labelList);
//        System.out.println(labels);

        loadTrainingImages(imagePathList);

        recognizer.train(images, labels);
//			String trainingDir = System.getProperty("user.dir") + "/att_faces";
//			Mat testImage = Imgcodecs.imread(trainingDir, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//			File root = new File(trainingDir);
//			FilenameFilter imgFilter = (dir, name) -> {
//				name = name.toLowerCase();
//				return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
//			};
//			File[] imageFiles = root.listFiles(imgFilter);
//			if (imageFiles != null) {
//				List<Mat> images = new ArrayList<>(imageFiles.length);
//				Mat labels = new Mat(imageFiles.length, 1, CvType.CV_32SC1);
//				//IntBuffer labelsBuf = labels.createBuffer();
//				int counter = 0;
//				for (File image : imageFiles) {
//					Mat img = Imgcodecs.imread(image.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
//
//					int label = Integer.parseInt(image.getName());
//
//					//images.put(counter, img);
//					images.add(counter, img);
//
//					//labelsBuf.put(counter, label);
//					labels.put(counter, counter / 10, label);
//
//					counter++;
//				}
//			}
//			IntPointer label = new IntPointer(1);
//			DoublePointer confidence = new DoublePointer(1);
//			rec.predict(testImage, label, confidence);
//			int predictedLabel = label.get(0);
    }

    /**
     * Each time add a new entity, update the model.
     */
    void updateModel() {
        // add captures to dataset(assign an id)

        // add labels to file
        // read images to images
        // add label to labels
        // retrain model
    }

    public void detect(Mat frame) {
        MatOfRect faces = new MatOfRect();
        faceCascade.load(getClass().getResource("/Resources/haarcascade_frontalface_alt.xml").getPath());
        Mat frameToGray = new Mat();
        Imgproc.cvtColor(frame, frameToGray, Imgproc.COLOR_BGR2GRAY);

        faceCascade.detectMultiScale(frameToGray, faces, 1.1, 1, Objdetect.CASCADE_SCALE_IMAGE,
                standardSize, new Size());

        Rect[] facesArray = faces.toArray();

        for (Rect face : facesArray) {
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
        }

//        return faces;
    }

    /**
     *
     * @param frame
     * @return Returns an arraylist including all the recognized labels.
     */
    public HashMap<Integer, Mat> detectAndRecognize(Mat frame) {
        System.out.println("start detect");
        ArrayList<Mat> tmpMatList = new ArrayList<>();
        HashMap<Integer, Mat> map = new HashMap<Integer, Mat>();

        tmpLabelList = new ArrayList<>();

        MatOfRect faces = new MatOfRect();

//        faceCascade.load(getClass().getResource("/Resources/haarcascade_frontalface_alt.xml").getPath());
        faceCascade.load("src/Resources/haarcascade_frontalface_alt.xml");

        Mat frameToGray = new Mat();
        Imgproc.cvtColor(frame, frameToGray, Imgproc.COLOR_BGR2GRAY);
        faceCascade.detectMultiScale(frameToGray, faces, 1.1, 1, Objdetect.CASCADE_SCALE_IMAGE,
                standardSize, new Size());
        Rect[] facesArray = faces.toArray();

        Mat submat = null;
        int label = 0;
        double confidence = 0.0;
        int[] recLabels = new int[1];
        double[] recConfidences = new double[1];

        for (Rect face : facesArray) {
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
            submat = frameToGray.submat(face).clone();
            Imgproc.equalizeHist(submat, submat);
            Imgproc.resize(submat, submat, standardSize, 1, 1, Imgproc.INTER_CUBIC);

            recognizer.predict(submat, recLabels, recConfidences);

            System.out.println(recLabels[0]);
//            System.out.println(recConfidences[0]);
            label = recLabels[0];
            confidence = recConfidences[0];

            Mat matFace = new Mat(frame, face);
//            tmpLabelList.add(label);
            tmpMatList.add(matFace);
            // if no label or no label info, return empty string
            System.out.print("label = " + label);
            System.out.println("conf = " + confidence);

            int x = (int) Math.max(face.tl().x - 10, 0);
            int y = (int) Math.max(face.tl().y - 10, 0);

//            String labelInfo = recognizer.getLabelInfo(label);
            String labelName = "";
//            System.out.print("NameMai = ");
            System.out.println(labelName);
            if (label == -1) {
                labelName = "Stranger";
                map.put(-100, matFace);
            } else {
                labelName = getName(label);
                map.put(label, matFace);

            }

            System.out.print("Name = ");
            System.out.println(labelName);
//            String name = Integer.toString(label);

            Imgproc.putText(frame, labelName, new Point(x, y), Core.FONT_HERSHEY_PLAIN, 6.0, new Scalar(255, 0, 0), 2);
            //frame, label string, point, Core.font, font size, color, thickness, linetype, bottom left origin
//            Imgproc.putText(frame, "Hang", new Point(x, y), Core.FONT_HERSHEY_DUPLEX, 8.0, new Scalar(0, 255, 0), 2, 1, false);
        }

//        if (tmpMatList.isEmpty()) {
//            return null;
//        }
        if (tmpMatList.isEmpty()) {
            return null;
        }
        return map;
//        return tmpLabelList;
    }

    public ArrayList<Integer> loginDetect(Mat frame) {
        MatOfRect faces = new MatOfRect();

//        faceCascade.load(getClass().getResource("src/Resources/haarcascade_frontalface_alt.xml").getPath());
        faceCascade.load("src/Resources/haarcascade_frontalface_alt.xml");

        Mat frameToGray = new Mat();
        Imgproc.cvtColor(frame, frameToGray, Imgproc.COLOR_BGR2GRAY);

        faceCascade.detectMultiScale(frameToGray, faces, 1.1, 0, Objdetect.CASCADE_SCALE_IMAGE,
                standardSize, new Size());

        Rect[] facesArray = faces.toArray();

        Mat submat = null;
        ArrayList<Integer> returnLabels = new ArrayList<>();
//        ArrayList<Mat> returnMats = new ArrayList<>();
        int label = 0;
        int[] recLabels = new int[1];
        double[] recConfidences = new double[1];

        for (Rect face : facesArray) {
            Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
            submat = frameToGray.submat(face).clone();
            Imgproc.equalizeHist(submat, submat);
            Imgproc.resize(submat, submat, standardSize, 1, 1, Imgproc.INTER_CUBIC);

            recognizer.predict(submat, recLabels, recConfidences);

            label = recLabels[0];
            returnLabels.add(label);
//            returnMats.add(submat);

        }
        return returnLabels;
    }

    /**
     * Gets the first label of the recognized list.
     *
     * @param labels
     * @return Returns the first label.
     */
    public int recID(ArrayList<Integer> labels) {
        int label = labels.get(0);
        return label;
    }

    /**
     * Gives the image for training
     *
     * @param Mats
     * @return
     */
    public Image getTraining(ArrayList<Mat> Mats) {
        Mat mat = Mats.get(0);
        Image image = Utils.mat2Image(mat);
        return image;
    }

    FaceRecognizer getRecognizer() {
        return recognizer;
    }

    public ArrayList<Integer> getLabelList() {
        return labelList;
    }

    public List<Mat> getImageList() {
        return images;
    }

    public ArrayList<Integer> getTmpLabelList() {
        return tmpLabelList;
    }

    /**
     * Uses label to search member in the database.
     *
     * @param label
     * @return
     */
    private String getName(int label) {
        String firstName = "";
        String lastName = "";
        System.out.println("entered label" + label);

        Student student = api.findStudent(label);
        firstName = student.getFirstname();
        lastName = student.getLastname();
        System.out.print("fName :" + firstName);

        return firstName + " " + lastName;
    }

    /**
     * Gets member ID in the database.
     *
     * @param label
     * @return
     */
    private int getID(int label) {
        int id = 0;
        Student student = api.findStudent(label);
        id = student.getAndrewNumID();
        return id;
    }
}
