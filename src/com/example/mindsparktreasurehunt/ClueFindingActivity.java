package com.example.mindsparktreasurehunt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;



import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ClueFindingActivity extends BaseActivity implements CvCameraViewListener2 {

	private static final String  TAG = "TREASURE_HUNTER";
	private CameraBridgeViewBase mOpenCvCameraView;
	
    private Mat featuredImg;
    private MatOfKeyPoint keypoints1;
    private Mat descriptors;
    private Mat matchedDescriptors;
    private MatOfKeyPoint matchKeypoints;
    

    private FeatureDetector detector;
    private DescriptorExtractor extractor;
    private DescriptorMatcher matcher;
    
    private Mat cameraImage;
    private Mat matchImage;

    private Clue clue;
    
    private TextView helpText1;
    private TextView helpText2;
   
    
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    
                    featuredImg = new Mat();
                    keypoints1 = new MatOfKeyPoint();
                    detector = FeatureDetector.create(FeatureDetector.ORB);
                    matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
                    extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
                    Log.e("LOADING", "mindsparks/pictures/"+Persistence.sharedInstance.getSelectedClue().getPhoto().getUuid()+".jpg");
                    matchImage = Highgui.imread(clue.imagePath());
                    Log.e("FSD", matchImage.toString());
                    matchKeypoints = new MatOfKeyPoint();
                    matchedDescriptors = new Mat();
                    detector.detect(matchImage, matchKeypoints);   
                    extractor.compute(matchImage, matchKeypoints, matchedDescriptors);
                  Imgproc.cvtColor(matchImage, matchImage, Imgproc.COLOR_RGBA2RGB);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
	
    
	private void trigger() {
		
		final View whiteFlash = findViewById(R.id.whiteFlash);
		whiteFlash.setVisibility(View.VISIBLE);
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(300);
		
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) { }
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				View polaroid = findViewById(R.id.polaroidContent);
				polaroid.setVisibility(View.VISIBLE);
				ImageView polaroidImageView = (ImageView) findViewById(R.id.polaroidImage);
				File imgFile = new File(clue.imagePath());
				if(imgFile.exists()){
				    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				    polaroidImageView.setImageBitmap(myBitmap);
				}
				TextView polaroidText = (TextView) findViewById(R.id.textViewName);
				polaroidText.setText(clue.getName());
				
				helpText1.setText("Congratulations on finding the clue!");
				helpText2.setText("To collect more clues, press the back button");
				
				clue.setComplete(getApplicationContext());
				
				View greyout = findViewById(R.id.greyout);
				greyout.setVisibility(View.VISIBLE);
				
				AlphaAnimation animation2 = new AlphaAnimation(1.0f, 0.0f);
				animation2.setDuration(300);
				animation2.setFillAfter(true); 
				whiteFlash.startAnimation(animation2);
			}
		});
		
		whiteFlash.startAnimation(animation);
	}

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        
        setContentView(R.layout.activity_clue_finding);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.tutorial1_activity_java_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        
        Log.v("geiojg", "this code runs");
        
        clue = Persistence.sharedInstance.getSelectedClue();
        
        
        helpText1 = (TextView) findViewById(R.id.helpText);
        helpText2 = (TextView) findViewById(R.id.helpText2);
        
        helpText2.setText(clue.getDescription());
    }
    


    
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        //first image
        cameraImage = inputFrame.rgba();
        Imgproc.cvtColor(cameraImage, cameraImage, Imgproc.COLOR_RGBA2RGB);

        detector.detect(cameraImage, keypoints1);        
        descriptors = new Mat();

        extractor.compute(cameraImage, keypoints1, descriptors);
        
        MatOfDMatch matches = new MatOfDMatch();
        
        List<KeyPoint> referenceKeypointsList =
                matchKeypoints.toList();
        
        matcher.match(descriptors,matchedDescriptors, matches);

        List<DMatch> matchesList = matches.toList();
        if (matchesList.size() < 4) {
            // There are too few matches to find the homography.
            return cameraImage;
        }
        
        // Calculate the max and min distances between keypoints.
        double maxDist = 0.0;
        double minDist = Double.MAX_VALUE;
        for(DMatch match : matchesList) {
            double dist = match.distance;
            if (dist < minDist) {
                minDist = dist;
            }
            if (dist > maxDist) {
                maxDist = dist;
            }
        }

        // The thresholds for minDist are chosen subjectively
        // based on testing. The unit is not related to pixel
        // distances; it is related to the number of failed tests
        // for similarity between the matched descriptors.
        if (minDist > 50.0) {
            // The target is completely lost.
            // Discard any previously found corners.
            return cameraImage;
        } else if (minDist > 20.0) {
            // The target is lost but maybe it is still close.
            // Keep any previously found corners.
            return cameraImage;
        }
        
        ArrayList<Point> goodReferencePointsList =
                new ArrayList<Point>();


        double maxGoodMatchDist = 1.75 * minDist;
        for(DMatch match : matchesList) {
            if (match.distance < maxGoodMatchDist) {
               goodReferencePointsList.add(
                       referenceKeypointsList.get(match.trainIdx).pt);

            }
        }
        	
        Log.e("", "MATCHED "+ matchesList.size());
        Log.e("", "Good: "+ goodReferencePointsList.size());

        if (goodReferencePointsList.size() > 5) {
        	runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					trigger();
				}
			});
        	
        }



        return cameraImage;
    }

	@Override
	public void onCameraViewStarted(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCameraViewStopped() {
		// TODO Auto-generated method stub
		
	}

}
