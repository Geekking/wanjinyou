package com.example.bbalbum;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvSVM;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bbalbum.Tools;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	 	private Button btnProc;  
	    private ImageView imageView;  
	    private Bitmap bmp;  
	    private TextView textView1;
	    private TextView textView2;
	    private TextView textView3;
	    
	    List<Mat> testImgs = null;
	    int[] testLabel = null;
	    
	    private PcaNetOperation pcaOperation = null;
	    
	    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {  
	        @Override  
	        public void onManagerConnected(int status) {  
	            switch (status) {  
	                case LoaderCallbackInterface.SUCCESS:{  
	                	pcaOperation = new PcaNetOperation();
	                } break;  
	                default:{  
	                    super.onManagerConnected(status);  
	                } break;  
	            }  
	        }  
	    };  
	      
	    @Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.activity_main);  
	        btnProc = (Button) findViewById(R.id.btn_gray_process);  
	        imageView = (ImageView) findViewById(R.id.image_view);  
	        textView1 = (TextView) findViewById(R.id.textView1);
	        textView2 = (TextView) findViewById(R.id.textView2);
	        textView3 = (TextView) findViewById(R.id.textView3);
	        
	        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.zuoye);  
	        imageView.setImageBitmap(bmp);  
	        btnProc.setOnClickListener(this);  
	    }  
	  /*
	    private void initBitmapArray() {
	    	testImgs = new ArrayList<Mat>(); 
	    	testLabel = new int[120];
	    	Bitmap  bitmap = null;
	    	int index = 0;
	    	int bitmapId =  2130837591;
	    	
	    	
	    	Mat temp = new Mat();
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.children01);
	    	Utils.bitmapToMat(bitmap, temp);  
	    	Imgproc.cvtColor(temp, temp, Imgproc.COLOR_RGB2GRAY);  
	    	
	    	Mat change = new Mat(temp.rows(), temp.cols(), CvType.CV_64F);
	    	temp.convertTo(change, CvType.CV_64F, 1.0 / 255);
	    
	    	testImgs.add(change);
	    	testLabel[index] = 1;
	    	
	    	/*for(int i=1; i<5; i++){
	    		for(int j=0 ; j<30; j++){
	    			Mat temp = new Mat();
	    			bitmap = BitmapFactory.decodeResource(getResources(), bitmapId);
	    	    	Utils.bitmapToMat(bitmap, temp);  
	    	    	Imgproc.cvtColor(temp, temp, Imgproc.COLOR_RGB2GRAY);  
	    	    	
	    	    	Mat change = new Mat(temp.rows(), temp.cols(), CvType.CV_64F);
	    	    	temp.convertTo(change, CvType.CV_64F, 1.0 / 255);
	    	    	//Core.convertScaleAbs(src, dst, alpha, beta);
	    	    	//Core.convertScaleAbs(change, change, 1.0 / 255, 0);
	    	    	
	    	    	//Log.e("diao", "" + change.type());
	    	    	testImgs.add(change);
	    	    	testLabel[index] = i == 1 ? 1 : i == 2 ? 4 : i == 3 ? 2 : 1;
	    	    	index ++;
	    	    	bitmapId ++;
	    		}
	    	}*/
	    	
	    	
		//}

		@Override  
	    public void onClick(View v) {  
			
			testImgs = new ArrayList<Mat>(); 
	    	Bitmap  bitmap = null;
	    	Mat temp = new Mat();
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.children01);
	    	Utils.bitmapToMat(bitmap, temp);  
	    	Imgproc.cvtColor(temp, temp, Imgproc.COLOR_RGB2GRAY);  
	    	Mat change = new Mat(temp.rows(), temp.cols(), CvType.CV_64F);
	    	temp.convertTo(change, CvType.CV_64F, 1.0 / 255);
	    	testImgs.add(change);
	    	 
	    	//pcaOperation = new PcaNetOperation();
	    	new PcaNetOp().execute("");
	    	
	    }  
	      
	    @Override  
	    public void onResume(){  
	        super.onResume();  
	       
	        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);
	      
	      
	    }  
        
        private String readInputWriteToFile(InputStream is, String fname) throws IOException {
    		
    		int num = 0;
    		
    		String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/saved_svms");    
            myDir.mkdirs();
    		
            File file = new File (myDir, fname);
            if (file.exists ()) file.delete (); 
            
    		 FileOutputStream os = new FileOutputStream(file);
    		 byte[] buffer = new byte[4096];
    		 int bytesRead;
    		 while ((bytesRead = is.read(buffer)) != -1) {
    			 os.write(buffer, 0, bytesRead);
    		 }
    		 is.close();
    		 os.close();

            return file.getAbsolutePath();
        }
        
        private class PcaNetOperation {
        	private CvSVM SVM = new CvSVM(); 
	    	private List<Mat> Filters = new ArrayList<Mat>(); 
	    	PCANet pcaNet = new PCANet();
	    		
	    	public PcaNetOperation(){
	    		
	        	pcaNet.NumStages = 2;
	        	pcaNet.PatchSize = 7;
	        	pcaNet.NumFilters[0] = 8;
	         	pcaNet.NumFilters[1] = 8;
	         	pcaNet.HistBlockSize[0] = 14;
	         	pcaNet.HistBlockSize[1] = 9;
	         	pcaNet.BlkOverLapRatio = 0.5;
	        	
	   
	            InputStream XmlFileInputStream = getResources().openRawResource(R.raw.all_age_48_svm_56x36_2); // getting XM
	            String svmPath = "";
	            InputStream FiltersFileInputStream = getResources().openRawResource(R.raw.all_age_48_filters_56x36_2); // getting XM
	            String FiltersPath = "";
	            Mat temp = null;
	           
				try {
					svmPath = readInputWriteToFile(XmlFileInputStream, "svms.xml");
					SVM.load(svmPath);
					FiltersPath = readInputWriteToFile(FiltersFileInputStream, "Filters.xml");
					TaFileStorage storage = new TaFileStorage();
					storage.open(FiltersPath);
					temp = storage.readMat("filter1");
					Filters.add(temp);
					temp = storage.readMat("filter2");
					Filters.add(temp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	public List<Boolean> RecogChild(List<Mat> Imgs){
	    		
	    		Hashing_Result hashing_r;
	        	PCA_Out_Result out;

	        	int ImgsSIze = Imgs.size();
	        	
	        	List<Boolean> result = new ArrayList<Boolean>();
	        	
	        	long e1 = Core.getTickCount();
	        	for(int i=0; i<ImgsSIze; i++){
	        		out = new PCA_Out_Result();
	        		out.OutImgIdx.add(0);
	        		out.OutImg.add(testImgs.get(i));
	        		out = Tools.PCA_output(out.OutImg, out.OutImgIdx, pcaNet.PatchSize, 
	        			pcaNet.NumFilters[0], Filters.get(0), 2);
	        		for(int j=1; j<pcaNet.NumFilters[1]; j++)
	        			out.OutImgIdx.add(j);

	        		out = Tools.PCA_output(out.OutImg, out.OutImgIdx, pcaNet.PatchSize, 
	        			pcaNet.NumFilters[1], Filters.get(1), 2);		
	        		hashing_r = Tools.HashingHist(pcaNet, out.OutImgIdx, out.OutImg);	
	        		hashing_r.Features.convertTo(hashing_r.Features, CvType.CV_32F);
	        		result.add(SVM.predict(hashing_r.Features) <= 3 ? true : false);
	        	}
	        	long e2 = Core.getTickCount();
	        	double time = (e2 - e1)/ Core.getTickFrequency();	
	        	Log.e("time usage: ", ""+time);
				return result;
	    		
	    	}
        }

	    
	    private class PcaNetOp extends AsyncTask<String, Void, String> {

	    	
	        @Override
	        protected String doInBackground(String... params) {
	        	List<Boolean> result = pcaOperation.RecogChild(testImgs);
	        	Log.e("result", "" + result.get(0));
	            return "";
	        }
	        

			@Override
	        protected void onPostExecute(String result) {
				
	        }

	        @Override
	        protected void onPreExecute() {}

	        @Override
	        protected void onProgressUpdate(Void... values) {}
	    }
}
